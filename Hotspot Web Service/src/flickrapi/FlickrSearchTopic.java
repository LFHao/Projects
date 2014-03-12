package flickrapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import org.json.*;

import auth.FlickrAuth;


public class FlickrSearchTopic {	
	public static List<String> getPhotos(String searchParam){
		FlickrAuth authObj = new FlickrAuth();
		int per_page = 30;
		int page = 1;
		List<String> result = new ArrayList<String>();
		String method = "flickr.photos.search";
		String strFlickrAPI  =
				"http://api.flickr.com/services/rest?" + 
				"method=" + method + 
				"&api_key=" + authObj.getKey()  + 
				"&tags=" + searchParam.replace(" ", "+") + 
				"&sort=interestingness-desc&format=json&nojsoncallback=1&per_page="+ per_page + 
				"&page=" + page ;
		//Get the response from Flickr, the return in JSON format.
		String jsonString = getDoc(strFlickrAPI);
		try {
			//Get photo info from responsed JSON string.
			JSONArray photos = loadJSONFromString(jsonString);
			
			//Use the photos JSONArray, to get each photo url.
			for (int i = 0; i < photos.length(); i++) {
				JSONObject n = photos.getJSONObject(i);
				try {
					// Build the image URL as specified in the Flickr API
					String url = "http://farm" + n.getInt("farm") + ".staticflickr.com/" + 
							n.getString("server") + "/" + n.getString("id") + "_" + 
							n.getString("secret") + "_z.jpg";
					result.add(url);					
				}
				catch (Exception e) {
					System.out.println("couldn't load image" + e.toString());
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private static JSONArray loadJSONFromString(String jsonString) throws JSONException {
		JSONObject rootObj = new JSONObject(jsonString.trim()); 
		JSONObject photoObj = rootObj.getJSONObject("photos");
		JSONArray photos = photoObj.getJSONArray("photo");
	
		return photos;	
	}

	public static String getDoc(String urlToRead) {
		 
	    URL url;
	    HttpURLConnection conn;
	    BufferedReader rd;
	    String line;
	    String result = "";
	 
	    try { 
	        url = new URL(urlToRead);
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	 
	        rd = new BufferedReader(
	                new InputStreamReader(conn.getInputStream()));
	        while ((line = rd.readLine()) != null) {
	            result += line;
	        }
	        rd.close();
	 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return result;
	}
}
