package org.bgcokehack.github_recommender.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bgcokehack.github_recommender.controller.UserCommand;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.springframework.stereotype.Component;

@Component
public class XingApiService {
	
	private String apiKey;
	private String apiSecret;
	
	private static final String PROFILE_URL = "https://api.xing.com/v1/users/me";
	private static final String USER_FIELD_KEY = "users";
	private static final String OFFERS_FIELD_KEY = "haves";

	public XingApiService() {
		this.apiKey = "0d640e73bb4266f3b284";
		this.apiSecret = "44d8402996c9106f8c656a269bf1c49bcc359e55";
	}
	
	public String fetchInterests(Token requestToken, UserCommand user) {
		String interests = "";

		OAuthService service = new ServiceBuilder()
			.provider(XingApi.class)
			.apiKey(apiKey)
			.apiSecret(apiSecret)
			.build();

		Verifier verifier = new Verifier(user.getVerifierCode());

		// Trade the Request Token and Verifier for the Access Token
		Token accessToken = service.getAccessToken(requestToken, verifier);

		OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_URL);
		service.signRequest(accessToken, request);
		Response response = request.send();
		String jsonResponse = response.getBody();

		try {
			@SuppressWarnings("unchecked")
			Map<String, ArrayList<Map<String, String>>> results = new ObjectMapper()
			.readValue(jsonResponse, HashMap.class);
			interests = results.get(USER_FIELD_KEY).get(0).get(OFFERS_FIELD_KEY);

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return interests;
	}


}
