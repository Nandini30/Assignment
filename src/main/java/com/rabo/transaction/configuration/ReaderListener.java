package com.rabo.transaction.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import com.rabo.transaction.model.Transaction;

/**
 * @author Nandini
 *
 */
@Component
public class ReaderListener implements ItemReadListener<Transaction> {
	
	private static final Logger LOG = LoggerFactory.getLogger(ReaderListener.class);

	@Override
	public void beforeRead() {
		LOG.trace("Read process initiated");
		
	}

	@Override
	public void onReadError(Exception ex) {
		LOG.error("Exception Occurred on reading a file:" + ex.getMessage());
	}

	@Override
	public void afterRead(Transaction item) {
		LOG.trace("Read process completed");
		
	}

}
