package com.autho.authservice.model.responses;

import java.io.Serializable;
import java.util.List;

public class ListResponse implements Serializable {

	private final List<?> collection;

	public ListResponse(List<?> collection) {
		this.collection = collection;
	}

	public List<?> getCollection() {
		return collection;
	}

}
