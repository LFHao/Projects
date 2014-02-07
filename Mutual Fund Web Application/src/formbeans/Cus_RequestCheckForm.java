package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import utility.AmountCheck;

public class Cus_RequestCheckForm extends FormBean {
    private String withdraw;
	
	public String getWithdraw()   { return withdraw;   }
	
	public void setWithdraw(String s)   { withdraw  = trimAndConvert(s,"<>\";*");}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (withdraw == null || withdraw.length() == 0) {
			errors.add("Withdraw amount is required");
		}

		// missing validation for cash 
		// withdraw must be valid number
		// no need to consider overflow here
		if (withdraw.matches(".*[<>\"].*")) errors.add("You may not input angle brackets, quotes, semicolons or stars in textfields");
		long ec = AmountCheck.checkValueString(withdraw);
		if (ec < 0)
			errors.add(AmountCheck.getErrorByCode(withdraw, ec));
		
		return errors;
	}
}
