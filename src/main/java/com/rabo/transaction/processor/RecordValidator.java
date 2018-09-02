package com.rabo.transaction.processor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabo.transaction.exception.TransactionException;
import com.rabo.transaction.model.Transaction;
import com.rabo.transaction.util.TransactionUtil;

/**
 * @author Nandini
 *
 */
@Component
public class RecordValidator {
	
	@Autowired
	TransactionUtil util;
	
	private static final Logger LOG = LoggerFactory.getLogger(RecordValidator.class);
	
	public boolean validate(Transaction transaction) throws TransactionException {
		boolean validator = true;
		if (util.isEmpty(transaction.getTransaction_reference())) {
			validator = false;
			LOG.error("Reference", new TransactionException("Invalid Transaction Reference..."));
		}
		if (util.isEmpty(transaction.getAccount_number())) {
			validator = false;
			LOG.error("Account", new TransactionException("Invalid Account Number..."));
		}
		if (util.isEmpty(transaction.getDescription())) {
			validator = false;
			LOG.error("Description", new TransactionException("Invalid Description..."));
		}
		if (!util.isValidNumber(String.valueOf(transaction.getStart_Balance()))) {
			validator = false;
			LOG.error("StartBalance", new TransactionException("Invalid Start Balance..."));
		}
		if (!util.isValidNumber(String.valueOf(transaction.getMutation()))) {
			validator = false;
			LOG.error("Mutation", new TransactionException("Invalid Mutation..."));
		}
		if (!util.isValidNumber(String.valueOf(transaction.getEnd_Balance()))) {
			validator = false;
			LOG.error("EndBalance", new TransactionException("Invalid End Balance..."));
		}
		return validator;
	}
}
