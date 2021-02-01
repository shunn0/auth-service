package com.autho.authservice.model.request;

import java.io.Serializable;
import java.util.List;

public class ListRequest implements Serializable {

	private List<CodeDescRequest> collection;

	public ListRequest() {
	}

	public ListRequest(List<CodeDescRequest> collection) {
		this.collection = collection;
	}

	public List<CodeDescRequest> getCollection() {
		return collection;
	}

}
