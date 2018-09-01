/**
 * 
 */
package com.rabo.transaction.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author NEW
 *
 */
@XmlRootElement(name = "record")
public class Transaction {
	
	private String transaction_reference;
	
    private String account_number;
    
    private String description;
    
    private Double start_Balance;
    
    private Double mutation;
    
    private Double end_Balance;

	/**
	 * Default Constructor
	 */
	public Transaction() {
		
	}
	/**
	 * Parametrised Constructor
	 */
	public Transaction(String transaction_reference, String description, Double end_Balance) {
		super();
		this.transaction_reference = transaction_reference;
		this.end_Balance = end_Balance;
		this.description = description;
	}
	
	@XmlAttribute(name = "reference")
	public String getTransaction_reference() {
		return transaction_reference;
	}
    
	public void setTransaction_reference(String transaction_reference) {
		this.transaction_reference = transaction_reference;
	}
	
	@XmlElement(name = "accountNumber")
	public String getAccount_number() {
		return account_number;
	}
	
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	
	@XmlElement(name = "startBalance")
	public Double getStart_Balance() {
		return start_Balance;
	}
	
	public void setStart_Balance(Double start_Balance) {
		this.start_Balance = start_Balance;
	}
	
	public Double getMutation() {
		return mutation;
	}
	
	public void setMutation(Double mutation) {
		this.mutation = mutation;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlElement(name = "endBalance")
	public Double getEnd_Balance() {
		return end_Balance;
	}
	
	public void setEnd_Balance(Double end_Balance) {
		this.end_Balance = end_Balance;
	}
}
