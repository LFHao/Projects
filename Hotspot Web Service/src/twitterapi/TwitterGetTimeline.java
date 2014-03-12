package twitterapi;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import databeans.Tweet;
import auth.TwitterAuth;

public class TwitterGetTimeline {
	private static String getTimeLineString(String username) {
		final String RESOURCE_URL = "https://api.twitter.com/1.1/statuses/user_timeline.json";
		
		OAuthService service = new ServiceBuilder()
		.provider(TwitterApi.SSL.class)
		.apiKey(TwitterAuth.API_KEY)
		.apiSecret(TwitterAuth.API_SECRET)
		.build();

		Token accessToken = new Token(TwitterAuth.ACCESS_TOKEN, TwitterAuth.ACCESS_SECRET);

		// Now let's go and ask for a protected resource!
		OAuthRequest request = new OAuthRequest(Verb.GET, RESOURCE_URL);
		request.addQuerystringParameter("screen_name", username);
		request.addQuerystringParameter("count", "200");
		service.signRequest(accessToken, request);
		Response response = request.send();
		return response.getBody();
	}
	
	public static ArrayList<Tweet> getTimeLine(String username) {
		ArrayList<Tweet> ret = new ArrayList<Tweet>();
		String respStr = getTimeLineString(username);
		
		try (InputStream is = new ByteArrayInputStream(respStr.getBytes("UTF-8")); JsonReader rdr = Json.createReader(is)) {
			JsonArray results = rdr.readArray();
			for (JsonObject result : results.getValuesAs(JsonObject.class))
				ret.add(new Tweet(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
