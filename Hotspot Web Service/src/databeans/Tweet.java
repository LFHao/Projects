package databeans;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.json.JsonObject;

public class Tweet {
	String content;
	String time;
	String name;
	String id;
	String url;
	
	public void setContent(String s) { content = s; }
	public void setTime(String d) { time = d; }
	public void setName(String s) { name = s; }
	public void setId(String s) { id = s; }
	public void setUrl(String s) { url = s; }
	
	public String getContent() { return content; }
	public String getTime() { return time; }
	public String getName() { return name; }
	public String getId() { return id; }
	public String getUrl() { return url; }
		
	@SuppressWarnings("deprecation")
	public Tweet(JsonObject ob) {
		name = ob.getJsonObject("user").getString("screen_name");
		Date d = new Date(ob.getString("created_at")); 
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		time = df.format(d);
		content = ob.getString("text");
		id = ob.getString("id_str");
		url = "https://twitter.com/" + name+ "/statuses/" + id;
	}
	
	public void printTweet() {
		System.out.println(name + " posted an tweet in " + time);
		System.out.println("\t" + content);
		System.out.println();
	}
}
