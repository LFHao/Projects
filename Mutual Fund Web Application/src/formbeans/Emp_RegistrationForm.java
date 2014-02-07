package formbeans;

import java.util.ArrayList;

import org.mybeans.form.FormBean;

public class Emp_RegistrationForm extends FormBean{
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	
	public String getFirstName()    { return firstname;}
	public String getLastName()     { return lastname; }
	public String getUsername() 	{ return username; }
	public String getPassword() 	{ return password; }
	
	public void setUsername(String s)	{ username = trimAndConvert(s,"<>\""); }
	public void setPassword(String s) 	{ password = trimAndConvert(s,"<>\""); }
	public void setFirstname(String s)	{ firstname = trimAndConvert(s, "<>\"");}
	public void setLastname(String s)	{ lastname = trimAndConvert(s, "<>\"");}
	
	
	
	// Check whether the form is valid. Returns an arraylist of error strings if the form is not valid. 
	public ArrayList<String> getValidationErrors() {
        ArrayList<String> errors = new ArrayList<String>();
        if (firstname == null || firstname.length() == 0) errors.add("First Name is required");
        if (lastname == null || lastname.length() == 0) errors.add("Last Name is required");
        if (username == null || username.length() == 0) errors.add("Username is required");
        if (password == null || password.length() == 0) errors.add("Password is required");

        if (errors.size() > 0) return errors;

        if (username.matches(".*[<>\"].*")) errors.add("UserName may not contain angle brackets or quotes");
        if (firstname.matches(".*[<>\"].*")) errors.add("First Name may not contain angle brackets or quotes");
        if (lastname.matches(".*[<>\"].*")) errors.add("Last Name may not contain angle brackets or quotes");
        return errors;
    }
	
	public void print() {
		System.out.println("email: " + username);
		System.out.println("pswd: " + password);
	}


}
