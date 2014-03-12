package twitterapi;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

import utility.GetPopularWords;
import databeans.Mapping;
import databeans.Tweet;
import auth.TwitterAuth;

public class TwitterGetHotWords {
	public static final int MAX_DATES = 5;

	private static String getTwittersByDate(String topic, Date date) {
		final String RESOURCE_URL = "https://api.twitter.com/1.1/search/tweets.json";

		OAuthService service = new ServiceBuilder()
				.provider(TwitterApi.SSL.class).apiKey(TwitterAuth.API_KEY)
				.apiSecret(TwitterAuth.API_SECRET).build();

		Token accessToken = new Token(TwitterAuth.ACCESS_TOKEN,
				TwitterAuth.ACCESS_SECRET);

		// Now let's go and ask for a protected resource!
		OAuthRequest request = new OAuthRequest(Verb.GET, RESOURCE_URL);
		String encodedTopic = null;
		try {
			encodedTopic = URLEncoder.encode(topic, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (encodedTopic == null)
			encodedTopic = topic;

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		request.addQuerystringParameter("until", df.format(date));
		request.addQuerystringParameter("q", encodedTopic);
		request.addQuerystringParameter("count", "100");
		request.addQuerystringParameter("lang", "en");
		service.signRequest(accessToken, request);
		Response response = request.send();
		return response.getBody();
	}

	public static ArrayList<Tweet> getTweets(String topic) {
		ArrayList<Tweet> ret = new ArrayList<Tweet>();
		Calendar Cal = Calendar.getInstance();

		for (int i = 0; i < MAX_DATES; i++) {
			Date time = Cal.getTime();
			String respStr = getTwittersByDate(topic, time);

			try (InputStream is = new ByteArrayInputStream(
					respStr.getBytes("UTF-8"));
					JsonReader rdr = Json.createReader(is)) {
				JsonArray results = rdr.readObject().getJsonArray("statuses");
				for (JsonObject result : results.getValuesAs(JsonObject.class))
					ret.add(new Tweet(result));
			} catch (Exception e) {
				e.printStackTrace();
			}
			Cal.add(Calendar.DATE, -1);
		}
		return ret;
	}
	
	public static ArrayList<Mapping> getPopularWords(String topic) {
		ArrayList<Mapping> ret = new ArrayList<Mapping>();
		ArrayList<Tweet> r = getTweets(topic);
		StringBuilder sb = new StringBuilder();
		for (Tweet t : r)
			sb.append(t.getContent());
		System.out.println(r.size());
		sb = new StringBuilder(sb.toString().replaceAll("\\n", ""));
		
		GetPopularWords g = new GetPopularWords();
		ret.addAll(g.mostPopularWords(sb.toString(), topic));
		return ret;
	}
}
