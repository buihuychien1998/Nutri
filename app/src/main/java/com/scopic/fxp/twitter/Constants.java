package com.scopic.fxp.twitter;

public class Constants {

	public static final String CONSUMER_KEY = "Eekr2m9yz1zek9JT6cDqj5Qom";
	public static final String CONSUMER_SECRET= "XifOrnQgNDrlXM9AqMSzkmFSFm0Fwp9ZGTXDGN1RgkdVigq7R7";
	
	public static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	public static final String ACCESS_URL = "https://api.twitter.com/oauth/access_token";
	public static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
	
	public static final String	OAUTH_CALLBACK_SCHEME	= "x-oauthflow-twitter";
	public static final String	OAUTH_CALLBACK_HOST		= "callback";
	public static final String	OAUTH_CALLBACK_URL		= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;

}

