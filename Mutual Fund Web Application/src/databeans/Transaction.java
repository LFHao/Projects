package databeans;

//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;

import java.util.Date;

import org.genericdao.PrimaryKey;


@PrimaryKey("transaction_id")
public class Transaction {
//	public static final List<String> EXTENSIONS = Collections.unmodifiableList(Arrays.asList( new String[] {
//			".jpg", ".gif", ".JPG"
//	}

	private int transaction_id    = -1;
	private int customer_id		  = -1; 
	private int fund_id     	  = -1;
	private Date execute_date 	  = null;
	private long shares 		  = 0;
	private String transaction_type = null;
	private long amount = 0L;
	private String status = null;
    
	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}

	public int getFund_id() {
		return fund_id;
	}

	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}

	public Date getExecute_date() {
		return execute_date;
	}

	public void setExecute_date(Date execute_date) {
		this.execute_date = execute_date;
	}

	public long getShares() {
		return shares;
	}

	public void setShares(long shares) {
		this.shares = shares;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}

