package com.service.client;

import java.util.Arrays;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import serilogj.Log;

public class OauthTokenVerifier {
	

	private static final String CLIENT_ID1 = "1059176547192-jq81i94a7dccnpklm5ph4gauim29t0dg.apps.googleusercontent.com"; //microsoft key	
	private static final String CLIENT_ID2 = "784894623300-gmkq3hut99f16n220kjimotv0os7vt2e.apps.googleusercontent.com"; //java key
	
	/**
	 * Method to validate a google id token with Oauth
	 * @param token
	 * @return
	 */
	public static GoogleIdToken checkGoogleToken(String token){
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new ApacheHttpTransport(), new JacksonFactory())
				.setAudience(Arrays.asList(CLIENT_ID1, CLIENT_ID2))
				.build();
		GoogleIdToken idToken = null;
		try {
			idToken = verifier.verify(token);
		} catch (Exception e) {
			Log
			.forContext("MemberName", "registerPerson")
			.forContext("Service", "OauthTokenVerifier")
			.error(e,"Exception");
		}
		return idToken;
	}

}
