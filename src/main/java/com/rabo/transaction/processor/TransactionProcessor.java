package com.rabo.transaction.processor;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabo.transaction.exception.TransactionException;
import com.rabo.transaction.model.Transaction;

/**
 * @author Nandini
 * 	
 *	Processor that handles main validations like : Unique reference and End Balance
 */
@Component
public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {
	
	@Autowired 
	RecordValidator validator;

    private static final Logger LOG = LoggerFactory.getLogger(TransactionProcessor.class);
    
    Set<Transaction> failedTransactions = new HashSet<Transaction>();
    Set<Transaction> transactions = new HashSet<Transaction>();

	@Override
    public Transaction process(final Transaction transaction) throws TransactionException {
        final String transaction_reference = transaction.getTransaction_reference();
        final String description = transaction.getDescription();
        final Double end_Balance = transaction.getEnd_Balance();
        Transaction transformedTransaction = null;
        boolean validate = validator.validate(transaction);
		if (validate) {
			Double endBalance = Double.sum(transaction.getMutation(), transaction.getStart_Balance());
			DecimalFormat df = new DecimalFormat("0.00");
			/**
			 * Check for Duplicate references
			 */
			for (Iterator<Transaction> it = transactions.iterator(); it.hasNext();) {
				Transaction record = it.next();
				if (record.getTransaction_reference().equals(transaction.getTransaction_reference())) {
					transformedTransaction = new Transaction(transaction_reference, description, end_Balance);
					LOG.info("Adding failed transactions to failRecords List" + transaction.getTransaction_reference());
					failedTransactions.add(transformedTransaction);
					return transformedTransaction;
				}
			}
			/**
			 * End Balance Validation
			 */
			if (!transaction.getEnd_Balance().equals(Double.parseDouble(df.format(endBalance)))) {
				transformedTransaction = new Transaction(transaction_reference, description, end_Balance);
				LOG.info("Adding failed transactions to failRecords List" + transaction.getTransaction_reference());
				failedTransactions.add(transaction);
				return transformedTransaction;
			}
		}
		transactions.add(transaction);
        return transformedTransaction;
	}
}
