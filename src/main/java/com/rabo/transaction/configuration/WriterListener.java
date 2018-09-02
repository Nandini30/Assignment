package com.rabo.transaction.configuration;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.rabo.transaction.model.Transaction;

/**
 * @author Nandini
 *
 */
@Component
public class WriterListener implements ItemWriteListener<Transaction> {
	
	private static final Logger LOG = LoggerFactory.getLogger(WriterListener.class);

	@Override
	public void beforeWrite(List<? extends Transaction> items) {
			try {
				if (!items.isEmpty()) {
					LOG.info("Write process initiated");
				}
			} catch (NullPointerException e) {
				LOG.error("Empty Input File:" + e.getMessage());
			}
	}

	@Override
	public void afterWrite(List<? extends Transaction> items) {
		File file = new File("output/records_output.csv");
		if(file.exists()) {
			LOG.info("Write process success");
		} else {
			LOG.error("Problem occurred on write process");
		}
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Transaction> items) {
		LOG.error("Exception Occurred while writing File:" + exception.getMessage());
	}

}
