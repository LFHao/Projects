package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;


public class Cus_SearchFundForm extends FormBean{
	private String fund_id;

	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if(Integer.parseInt(fund_id)<1)
			errors.add("Fund ID is not valid.");
		if (fund_id.matches(".*[<>\"].*")) errors.add("You may not input angle brackets, quotes, semicolons or stars in textfields");
		return errors;
	}


	public String getFund_id() {
		return fund_id;
	}


	public void setFund_id(String fund_id) {
		this.fund_id = trimAndConvert(fund_id,"<>\";*");
	}
}
