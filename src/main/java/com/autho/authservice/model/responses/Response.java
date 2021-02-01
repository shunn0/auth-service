package com.autho.authservice.model.responses;

import java.io.Serializable;

public class Response implements Serializable {

	private final String message;

	public Response(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
