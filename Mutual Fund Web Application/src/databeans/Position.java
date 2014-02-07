package databeans;

import org.genericdao.PrimaryKey;

@PrimaryKey("position_id")

public class Position {
	private int 	position_id = -1;
	private int    	customer_id = -1;
	private int 	fund_id 	= -1; 
	private long 	shares     	= -1;
	
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public int getFund_id() {
		return fund_id;
	}
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	public long getShares() {
		return shares;
	}
	public void setShares(long shares) {
		this.shares = shares;
	}
	public int getPosition_id() {
		return position_id;
	}
	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}
	
}
