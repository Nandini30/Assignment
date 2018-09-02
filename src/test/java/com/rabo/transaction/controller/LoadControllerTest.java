package com.rabo.transaction.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rabo.transaction.configuration.ReaderListener;
import com.rabo.transaction.configuration.TransactionConfiguration;
import com.rabo.transaction.configuration.WriterListener;
import com.rabo.transaction.processor.RecordValidator;
import com.rabo.transaction.util.TransactionUtil;

import junit.framework.TestCase;

/**
 * @author Nandini
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {LoadController.class, ReaderListener.class, WriterListener.class, 
		TransactionConfiguration.class, JobLauncherTestUtils.class, RecordValidator.class, TransactionUtil.class})
public class LoadControllerTest extends TestCase {
	
	@Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
	
    @Test
    public void launchJob() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        Assert.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

}
