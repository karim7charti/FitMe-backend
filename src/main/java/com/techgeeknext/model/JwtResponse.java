package com.techgeeknext.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String username;
	private String refreshToken;

	public JwtResponse(String jwttoken,String username,String refreshToken) {
		this.jwttoken = jwttoken;
		this.username=username;
		this.refreshToken=refreshToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getUsername() {
		return username;
	}

	public String getToken() {
		return this.jwttoken;
	}
}