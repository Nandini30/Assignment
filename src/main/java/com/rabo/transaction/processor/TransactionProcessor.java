package com.rabo.transaction.processor;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.rabo.transaction.model.Transaction;

/**
 * @author Nandini
 *
 */
@Component
public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {
	

    private static final Logger log = LoggerFactory.getLogger(TransactionProcessor.class);
    
    Set<Transaction> failedTransactions = new HashSet<Transaction>();
    Set<Transaction> transactions = new HashSet<Transaction>();

	@Override
    public Transaction process(final Transaction transaction) throws Exception {
        final String transaction_reference = transaction.getTransaction_reference();
        final String description = transaction.getDescription();
        final Double end_Balance = transaction.getEnd_Balance();
        
        Transaction transformedTransaction = null;
        Double endBalance = Double.sum(transaction.getMutation(), transaction.getStart_Balance());
		DecimalFormat df = new DecimalFormat("0.00");    
		/**
		 * Check for Duplicate references
		 */
		for (Iterator<Transaction> it = transactions.iterator(); it.hasNext();) {
			Transaction dub = it.next();
				if (dub.getTransaction_reference().equals(transaction.getTransaction_reference())) {
					transformedTransaction = new Transaction(transaction_reference, description, end_Balance);
			        log.info("Converting (" + transaction + ") into (" + transformedTransaction + ")");
					log.info("Adding failed transactions to failRecords List" + transaction.getTransaction_reference());
					failedTransactions.add(transformedTransaction);
				}
			}
		/**
		 * End Balance Validation
		 */
		if (!transaction.getEnd_Balance().equals(Double.parseDouble(df.format(endBalance)))) {
			transformedTransaction = new Transaction(transaction_reference, description, end_Balance);
	        log.info("Converting (" + transaction + ") into (" + transformedTransaction + ")");
			log.info("Adding failed transactions to failRecords List");
			failedTransactions.add(transaction);
		}
		transactions.add(transaction);
        return transformedTransaction;

    }
}
