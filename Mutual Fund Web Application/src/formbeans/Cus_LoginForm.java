/** 
 * Author: 			Bo ZHANG
 * Last Edit:		01/20/2014
 * Class Name:		Cus_LoginForm
 * Annotation:		NULL
 */

package formbeans;

import java.util.ArrayList;
import org.mybeans.form.FormBean;

public class Cus_LoginForm extends FormBean {
	private String username;
	private String password;
	
	public String getUsername() 	{ return username; }
	public String getPassword() 	{ return password; }
	
	public void setUsername(String s)	{ username = trimAndConvert(s,"<>\""); }
	public void setPassword(String s) 	{ password = trimAndConvert(s,"<>\""); }
	
	// Check whether the form is valid. Returns an arraylist of error strings if the form is not valid. 
	public ArrayList<String> getValidationErrors() {
        ArrayList<String> errors = new ArrayList<String>();

        if (username == null || username.length() == 0) errors.add("Username is required");
        if (password == null || password.length() == 0) errors.add("Password is required");
        if (errors.size() > 0) return errors;

        if (username.matches(".*[<>\"].*")) errors.add("E-mail address may not contain angle brackets or quotes");
		
        return errors;
    }
	
	public void print() {
		System.out.println("email: " + username);
		System.out.println("pswd: " + password);
	}
}
