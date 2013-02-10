package com.projectmaxwell.service.dao.impl.sendloop;

import org.codehaus.jackson.annotate.JsonProperty;

public class SendloopBrowseListResponse extends SendloopResponse{

	private SendloopSubscriber[] subscribers;

	@JsonProperty("Subscribers")
	public SendloopSubscriber[] getSubscribers() {
		return subscribers;
	}

	@JsonProperty("Subscribers")
	public void setSubscribers(SendloopSubscriber[] subscribers) {
		this.subscribers = subscribers;
	}
	
	
}
