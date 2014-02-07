package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import utility.AmountCheck;

public class Cus_SellFundForm extends FormBean{
	private String fundSymbol;
	private String shares;
	
	
	public void setShares(String shares) {
		this.shares = trimAndConvert(shares,"<>\";*");
	}


	public String getFundSymbol() {
		return fundSymbol;
	}
	
	public void setFundSymbol(String fundName) {
		this.fundSymbol = trimAndConvert(fundName,"<>\";*");
	}
	public String getShares() {
		return shares;
	}


	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		if (fundSymbol == null || fundSymbol.length() == 0) {
			errors.add("Fund symbol is required.");
		}
		
		if (shares == null || shares.length() == 0) {
			errors.add("shares is required");
		} else {
			long ec = AmountCheck.checkShareString(shares);
			if (ec < 0)
				errors.add(AmountCheck.getErrorByCode(shares, ec));
		}
		if (errors.size() > 0)
			return errors;
		
		if (fundSymbol.matches(".*[<>\"].*")) errors.add("You may not input angle brackets, quotes, semicolons or stars in textfields");
		if (shares.matches(".*[<>\"].*")) errors.add("You may not input angle brackets, quotes, semicolons or stars in textfields");
		
		return errors;
	}


	

	
}
