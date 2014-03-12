package twitterapi;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import sun.misc.BASE64Encoder;
import auth.TwitterAuth;
import databeans.AuthResult;

public class TwitterSharePic {
	private static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/statuses/update_with_media.json";

	public static AuthResult getAccessResult() {
		OAuthService service = new ServiceBuilder().provider(TwitterApi.class)
				.provider(TwitterApi.SSL.class).apiKey(TwitterAuth.API_KEY)
				.apiSecret(TwitterAuth.API_SECRET).build();

		Token requestToken = service.getRequestToken();
		String url = service.getAuthorizationUrl(requestToken);

		return new AuthResult(requestToken, url, service);
	}
	
	public static boolean postTweetWithMedia(AuthResult res, String verifyStr,
			String status, BufferedImage img) throws MalformedURLException, IOException{
		//create a temp pic from the URL
		File f = new File("temp.png");
		ImageIO.write(img, "png", f);
		// FileUtils.copyURLToFile(new URL(url), f);
				
		OAuthRequest request = new OAuthRequest(Verb.POST,
				PROTECTED_RESOURCE_URL);
		request.addHeader("Content-Type", "multipart/form-data");  
		
		OAuthService service = res.getService();
		Token requestToken = res.getAccessToken();
		Verifier verifier = new Verifier(verifyStr);
		Token accessToken = service.getAccessToken(requestToken, verifier);
		
		//add tweets and photos to the entity
		MultipartEntity entity = new MultipartEntity();

	    try {
	    	entity.addPart("status", new StringBody(status));
	        entity.addPart("media", new FileBody(f));

	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        entity.writeTo(out);

	        request.addPayload(out.toByteArray());
	        request.addHeader(entity.getContentType().getName(), entity.getContentType().getValue());

	        service.signRequest(accessToken, request);
	        Response response = request.send();

	        if (response.isSuccessful())
	        	return true;
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
}
