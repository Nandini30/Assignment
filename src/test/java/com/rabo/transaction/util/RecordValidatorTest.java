package com.rabo.transaction.util;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rabo.transaction.exception.TransactionException;
import com.rabo.transaction.model.Transaction;
import com.rabo.transaction.processor.RecordValidator;

/**
 * @author Nandini
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RecordValidatorTest {
	
	@Mock
    TransactionUtil util;
	
	@InjectMocks
	RecordValidator validator;
	
	@Test
	public void testValidation() throws TransactionException {
		Transaction record = new Transaction();
		record.setAccount_number("NL87889787323");
		record.setDescription("Sample description");
		record.setStart_Balance(23.38);
		record.setMutation(+10.00);
		record.setEnd_Balance(Double.valueOf("33.00"));
		when(util.isEmpty(Mockito.anyString())).thenReturn(false);
		when(util.isValidNumber(Mockito.anyString())).thenReturn(true);
		Assert.assertNotNull(validator.validate(record));
	}
	
	@Test
	public void testValidationNegative() throws TransactionException {
		Transaction record = new Transaction();
		record.setAccount_number("NL87889787323");
		record.setDescription(null);
		record.setStart_Balance(Double.valueOf(23.30));
		record.setMutation(Double.valueOf("+10.00"));
		record.setEnd_Balance(Double.valueOf("33.00"));
		Assert.assertFalse(validator.validate(record));
	}

}
