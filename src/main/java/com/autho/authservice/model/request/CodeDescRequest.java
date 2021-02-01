package com.autho.authservice.model.request;

import java.io.Serializable;

public class CodeDescRequest implements Serializable {
	private String code;
	private String description;

	public CodeDescRequest() {

	}

	public CodeDescRequest(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
