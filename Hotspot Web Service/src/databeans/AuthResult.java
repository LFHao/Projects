package databeans;

import org.scribe.model.*;
import org.scribe.oauth.*;

public class AuthResult {
	Token accessToken;
	String authUrl;
	OAuthService service;
	
	public AuthResult(Token _t, String _u, OAuthService _s) {
		accessToken = _t;
		authUrl = _u.replace("http:", "https:");
		service = _s;
	}
	
	public void setAccessToken(Token t) { accessToken = t; }
	public void setAuthUrl(String s) { authUrl = s; }
	public void setService(OAuthService s) { service = s; }
	
	public Token getAccessToken() { return accessToken; }
	public String getAuthUrl() { return authUrl; }
	public OAuthService getService() { return service; }
}
