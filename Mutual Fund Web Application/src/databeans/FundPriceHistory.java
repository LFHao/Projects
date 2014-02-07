package databeans;

//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;

import java.util.Date;

import org.genericdao.PrimaryKey;


@PrimaryKey("hist_id")
public class FundPriceHistory {
	private int hist_id 		= -1;
	private int fund_id     	= -1;
	private Date price_date		= null; 
	private long price     		= 0L;
	
	public int getHist_id() { return hist_id; }
	public void setHist_id(int id) { hist_id = id; }
	public int getFund_id() {
		return fund_id;
	}
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	public Date getPrice_date() {
		return price_date;
	}
	public void setPrice_date(Date price_date) {
		this.price_date = price_date;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	
}
