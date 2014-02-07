package databeans;

import java.util.Date;

import org.genericdao.PrimaryKey;

@PrimaryKey("date_id")
public class TransitionDate {
	private int date_id;
	private Date date;
	
	public void setDate_id(int id) { date_id = id; }
	public void setDate(Date _date) { date = _date; }
	public int getDate_id() { return date_id; }
	public Date getDate() { return date; }
}
