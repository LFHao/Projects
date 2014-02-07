package databeans;

//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;

import org.genericdao.PrimaryKey;


@PrimaryKey("username")
public class Employee {
//	public static final List<String> EXTENSIONS = Collections.unmodifiableList(Arrays.asList( new String[] {
//			".jpg", ".gif", ".JPG"
//	} ));

	private String username		   = null; 
	private String password        = null;
	private String firstname 	   = null;
	private String lastname        = null;
	
    

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
