/**
 * 
 */
package com.rabo.transaction.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.rabo.transaction.model.Transaction;
import com.rabo.transaction.processor.TransactionProcessor;

/**
 * @author Nandini
 *
 */
@Configuration
@EnableBatchProcessing
public class TransactionConfiguration {
	
	    @Autowired
	    public JobBuilderFactory jobBuilderFactory;

	    @Autowired
	    public StepBuilderFactory stepBuilderFactory;
	    
	    @Value("${input}/*.csv")
	    private Resource[] csvResources;
	    
	    @Value("${input}/*.xml")
	    private Resource[] xmlResources;

	    @Bean
	    public FlatFileItemReader<Transaction> csvReader() {
	        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<Transaction>();
	        reader.setStrict(false);
	        //reader.setResource(new ClassPathResource("input/records.csv"));
	        reader.setLinesToSkip(1);
	        reader.setLineMapper(new DefaultLineMapper<Transaction>() {{
	            setLineTokenizer(new DelimitedLineTokenizer() {{
	                setNames(new String[] { "transaction_reference", "account_number", 
	                		"description", "start_Balance", "mutation", "end_Balance"});
	            }});
	            setFieldSetMapper(new BeanWrapperFieldSetMapper<Transaction>() {{
	                setTargetType(Transaction.class);
	            }});	
	        }});
	        return reader;
	    }

	    @Bean
	    public MultiResourceItemReader<Transaction> multiResourceCsvItemReader(Resource[] resource, FlatFileItemReader<Transaction> flatFileItemReader){
	    	MultiResourceItemReader<Transaction> multiResourceItemReader = new MultiResourceItemReader<Transaction>();
	    	multiResourceItemReader.setResources(resource);
	    	multiResourceItemReader.setDelegate(flatFileItemReader);
			return multiResourceItemReader;
	    }
	    
	    @Bean
	    public MultiResourceItemReader<Transaction> multiResourceXmlItemReader(Resource[] resource, StaxEventItemReader<Transaction> staxEventItemReader){
	    	MultiResourceItemReader<Transaction> multiResourceItemReader = new MultiResourceItemReader<Transaction>();
	    	multiResourceItemReader.setResources(resource);
	    	multiResourceItemReader.setDelegate(staxEventItemReader);
			return multiResourceItemReader;
	    }
	    
	    @Bean
	    public TransactionProcessor csvProcessor() {
	        return new TransactionProcessor();
	    }

	    @Bean
		public FlatFileItemWriter<Transaction> csvWriter(){
			FlatFileItemWriter<Transaction> writer = new FlatFileItemWriter<Transaction>();
			
			String exportFileHeader = "Reference,Description,EndBalance";
	        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
	        writer.setHeaderCallback(headerWriter);
	        writer.setShouldDeleteIfExists(true);
			writer.setResource(new ClassPathResource("output/records_output.csv"));
			DelimitedLineAggregator<Transaction> lineAggregator = new DelimitedLineAggregator<Transaction>();
			lineAggregator.setDelimiter(",");
			
			BeanWrapperFieldExtractor<Transaction>  fieldExtractor = new BeanWrapperFieldExtractor<Transaction>();
			fieldExtractor.setNames(new String[]{"transaction_reference", "description", "end_Balance"});
			lineAggregator.setFieldExtractor(fieldExtractor);
			
			writer.setLineAggregator(lineAggregator);
			return writer;
		}
	    
	    @Bean
		public FlatFileItemWriter<Transaction> xmlWriter(){
			FlatFileItemWriter<Transaction> writer = new FlatFileItemWriter<Transaction>();
	        writer.setAppendAllowed(true);
			writer.setResource(new ClassPathResource("output/records_output.csv"));
			DelimitedLineAggregator<Transaction> lineAggregator = new DelimitedLineAggregator<Transaction>();
			lineAggregator.setDelimiter(",");
			
			BeanWrapperFieldExtractor<Transaction>  fieldExtractor = new BeanWrapperFieldExtractor<Transaction>();
			fieldExtractor.setNames(new String[]{"transaction_reference", "description", "end_Balance"});
			lineAggregator.setFieldExtractor(fieldExtractor);
			
			writer.setLineAggregator(lineAggregator);
			return writer;
		}
	    
	    @Bean
	    StaxEventItemReader<Transaction> xmlFileItemReader() {
	        StaxEventItemReader<Transaction> xmlFileReader = new StaxEventItemReader<>();
	        xmlFileReader.setStrict(false);
	        xmlFileReader.setFragmentRootElementName("record");
	 
	        Jaxb2Marshaller transactionMarshaller = new Jaxb2Marshaller();
	        transactionMarshaller.setClassesToBeBound(Transaction.class);
	        xmlFileReader.setUnmarshaller(transactionMarshaller);
	 
	        return xmlFileReader;
	    }
	    
	    @Bean
	    public TransactionProcessor processor() {
	        return new TransactionProcessor();
	    }
	    
	    @Bean
	    public Step csvStepExecution() {
	        return stepBuilderFactory.get("csv")
	                .<Transaction, Transaction> chunk(10)
	                .reader(multiResourceCsvItemReader(csvResources, csvReader()))
	                .processor(processor())
	                .writer(csvWriter())
	                .build();
	    }
	    
	    @Bean
	    public Step xmlStepExecution() {
	        return stepBuilderFactory.get("xml")
	                .<Transaction, Transaction> chunk(10)
	                .reader(multiResourceXmlItemReader(xmlResources, xmlFileItemReader()))
	                .processor(processor())
	                //.listener();
	                .writer(xmlWriter())
	                .build();
	    }
	    
	    @Bean
	    public Job myJob() {
	        return jobBuilderFactory.get("myJob")
	                .incrementer(new RunIdIncrementer())
	                .flow(csvStepExecution())
	                .next(xmlStepExecution())
	                .end()
	                .build();
	    }

}
