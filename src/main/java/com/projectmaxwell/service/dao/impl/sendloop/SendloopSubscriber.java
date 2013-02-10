package com.projectmaxwell.service.dao.impl.sendloop;

import org.codehaus.jackson.annotate.JsonProperty;

public class SendloopSubscriber {

	private String subscriberId;
	private String emailAddress;
	private String bounceType;
	private String subscriptionStatus;
	private String subscriptionDate;
	private String subscriptionIp;
	private String unsubscriptionDate;
	private String unsubscriptionIp;
	private String optInDate;
	private String name;

	@JsonProperty("SubscriberID")
	public String getSubscriberId() {
		return subscriberId;
	}

	@JsonProperty("SubscriberID")
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	
	@JsonProperty("BounceType")
	public String getBounceType() {
		return bounceType;
	}

	@JsonProperty("BounceType")
	public void setBounceType(String bounceType) {
		this.bounceType = bounceType;
	}

	@JsonProperty("EmailAddress")
	public String getEmailAddress() {
		return emailAddress;
	}

	@JsonProperty("EmailAddress")
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@JsonProperty("SubscriptionStatus")
	public String getSubscriptionStatus() {
		return subscriptionStatus;
	}

	@JsonProperty("SubscriptionStatus")
	public void setSubscriptionStatus(String subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	@JsonProperty("SubscriptionDate")
	public String getSubscriptionDate() {
		return subscriptionDate;
	}

	@JsonProperty("SubscriptionDate")
	public void setSubscriptionDate(String subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	@JsonProperty("SubscriptionIP")
	public String getSubscriptionIp() {
		return subscriptionIp;
	}

	@JsonProperty("SubscriptionIP")
	public void setSubscriptionIp(String subscriptionIp) {
		this.subscriptionIp = subscriptionIp;
	}

	@JsonProperty("UnsubscriptionDate")
	public String getUnsubscriptionDate() {
		return unsubscriptionDate;
	}

	@JsonProperty("UnsubscriptionDate")
	public void setUnsubscriptionDate(String unsubscriptionDate) {
		this.unsubscriptionDate = unsubscriptionDate;
	}

	@JsonProperty("UnsubscriptionIP")
	public String getUnsubscriptionIp() {
		return unsubscriptionIp;
	}

	@JsonProperty("UnsubscriptionIP")
	public void setUnsubscriptionIp(String unsubscriptionIp) {
		this.unsubscriptionIp = unsubscriptionIp;
	}

	@JsonProperty("OptInDate")
	public String getOptInDate() {
		return optInDate;
	}

	@JsonProperty("OptInDate")
	public void setOptInDate(String optInDate) {
		this.optInDate = optInDate;
	}

	@JsonProperty("CustomField2")
	public String getName() {
		return name;
	}

	@JsonProperty("CustomField2")
	public void setName(String name) {
		this.name = name;
	}
	
	
}
