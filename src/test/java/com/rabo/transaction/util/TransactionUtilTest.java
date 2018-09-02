package com.rabo.transaction.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Nandini
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionUtilTest {
	
	@InjectMocks
	TransactionUtil util;

	@Test
	public void isEmptyTest() {
		String a = "Sample";
		Assert.assertTrue(!util.isEmpty(a));
		a = "";
		Assert.assertTrue(util.isEmpty(a));
		a = null;
		Assert.assertTrue(util.isEmpty(a));
	}
	
	@Test
	public void isNotNumber() {
		String a = "11.22";
		Assert.assertTrue(util.isValidNumber(String.valueOf(11.22)));	
		a = "+11.22";
		Assert.assertTrue(util.isValidNumber(a));
		a = "-11.22";
		Assert.assertTrue(util.isValidNumber(a));
		a = "11";
		Assert.assertTrue(util.isValidNumber(a));
		a = "0.22";
		Assert.assertTrue(util.isValidNumber(a));
		a = "";
		Assert.assertFalse(util.isValidNumber(a));
		a = null;
		Assert.assertFalse(util.isValidNumber(a));
	}

}
