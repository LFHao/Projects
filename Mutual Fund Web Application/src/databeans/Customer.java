package databeans;

//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;

import org.genericdao.PrimaryKey;


@PrimaryKey("customer_id")
public class Customer {

	private int    customer_id     = 0;
	private String username		   = null; 
	private String password        = null;
	private String firstname 	   = null;
	private String lastname 	   = null;
	private String addr_line1      = null;
	private String addr_line2  	   = null;
	private String city 		   = null;
	private String state 		   = null;
	private int    zip 			   = 0;
	private long   cash 		   = 0;
    
	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

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

	public String getAddr_line1() {
		return addr_line1;
	}

	public void setAddr_line1(String addr_line1) {
		this.addr_line1 = addr_line1;
	}

	public String getAddr_line2() {
		return addr_line2;
	}

	public void setAddr_line2(String addr_line2) {
		this.addr_line2 = addr_line2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public long getCash() {
		return cash;
	}

	public void setCash(long d) {
		this.cash = d;
	}
}
