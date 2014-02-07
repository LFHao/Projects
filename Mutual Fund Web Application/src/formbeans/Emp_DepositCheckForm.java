package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import utility.AmountCheck;

public class Emp_DepositCheckForm extends FormBean {
	private String deposit;

	public String getDeposit()   { return deposit;   }
	
	public void setDeposit(String s)   { deposit  = trimAndConvert(s,"<>\"");   }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (deposit == null || deposit.length() == 0) {
			errors.add("Deposit amount is required");
		}
		if (errors.size() > 0)
			return errors;
		
		if (deposit.matches(".*[<>\"].*")) errors.add("You may not input angle brackets, quotes, semicolons or stars in textfields");
		long ec = AmountCheck.checkShareString(deposit);
		if (ec < 0)
			errors.add(AmountCheck.getErrorByCode(deposit, ec));
		
		return errors;
	}
}
