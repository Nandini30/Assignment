package com.rabo.transaction.processor;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rabo.transaction.model.Transaction;

/**
 * @author Nandini
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionProcessorTest {

	@Mock
	RecordValidator validator;

	@InjectMocks
	TransactionProcessor processor;

	@Test
	public void testProcessor() throws Exception {
		Transaction record = new Transaction();
		record.setAccount_number("NL87889787323");
		record.setDescription("Sample description");
		record.setStart_Balance(Double.valueOf(23.30));
		record.setMutation(Double.valueOf("+10.00"));
		record.setEnd_Balance(Double.valueOf("33.00"));
		when(validator.validate(record)).thenReturn(true);
		Assert.assertNotNull(processor.process(record));
	}

	@Test
	public void testUnhappyFlow() throws Exception {
		Transaction record = new Transaction();
		record.setAccount_number("NL87889787323");
		record.setDescription("Sample description");
		record.setStart_Balance(null);
		record.setMutation(Double.valueOf("+10.00"));
		record.setEnd_Balance(Double.valueOf("33.00"));
		when(validator.validate(record)).thenReturn(false);
		Assert.assertNull(processor.process(record));
	}

	@Test
	public void testUnhappyFlow1() throws Exception {
		Transaction record = new Transaction();
		record.setAccount_number("NL87889787323");
		record.setDescription("Sample description");
		record.setStart_Balance(null);
		record.setMutation(Double.valueOf("+10.00"));
		record.setEnd_Balance(Double.valueOf("33.00"));
		Assert.assertNull(processor.process(record));
	}
}
