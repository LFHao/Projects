package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class Emp_ResetPwdForm extends FormBean{
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String s) {
		this.password = trimAndConvert(s,"<>\";*");
	}
	public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();
        if(password == null || password.length() == 0){
        	errors.add("Password can not be empty");
        }
        if (!password.matches("[0-9a-zA-Z]{1,12}$")) {
            errors.add("Password must be alphanumeric character of length 1~12");
        }
        
        if (password.matches(".*[<>\"].*")) errors.add("You may not input angle brackets, quotes, semicolons or stars in textfields");
        return errors;
    }
}
