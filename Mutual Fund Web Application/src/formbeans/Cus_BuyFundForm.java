package formbeans;
import java.util.ArrayList;
import java.util.List;
import org.mybeans.form.FormBean;
import utility.*;

public class Cus_BuyFundForm extends FormBean{
	private String fundSymbol;
	private String amount;
	
	public String getFundSymbol() {
		return fundSymbol;
	}
	public void setFundSymbol(String fundSymbol) {
		this.fundSymbol = trimAndConvert(fundSymbol,"<>\";*");
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = trimAndConvert(amount,"<>\";*");
	}
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		if (fundSymbol == null || fundSymbol.length() == 0) {
			errors.add("Fund is required.");
		}
		
		if (amount == null || amount.length() == 0) {
			errors.add("Amount is required");
		}
		if(errors.size()>0)
			return errors;
		
		if (fundSymbol.matches(".*[<>\";*].*")) errors.add("You may not input angle brackets, quotes, semicolons or stars in textfields");
		if (amount.matches(".*[<>\";*].*")) errors.add("You may not input angle brackets, quotes, semicolons or stars in textfields");
		long ec = AmountCheck.checkValueString(amount);
		if (ec < 0)
			errors.add(AmountCheck.getErrorByCode(amount, ec));
		return errors;
	}
}
