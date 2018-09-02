package com.rabo.transaction.util;

import org.springframework.stereotype.Component;

/**
 * @author Nandini
 *
 *	Utility class that is customized to use for project validations
 */
@Component
public class TransactionUtil {
	
	/**
	 * Check if input parameter is null or empty
	 * 
	 * @param input
	 * @return
	 */
	public boolean isEmpty(String input) {
		if (input == null) {
			return true;
		} else if (input.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if input parameter is a valid number
	 * 
	 * @param input
	 * @return
	 */
	public boolean isValidNumber(String input) {
		if (!this.isEmpty(input)) {
			if (input.matches("^[+|-]*[0-9]*\\.[0-9]*") || input.matches("[0-9]*")) {
				return true;
			}
		}
		return false;
	}

}
