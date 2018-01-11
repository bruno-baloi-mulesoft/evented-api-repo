package org.mule.modules.eventedapi.vo;

import java.util.List;

public class ConsumerVO 
{
	private String consumerName;
	private String consumerNS;
	private String consumerType;
	private String applicationId;
	private String userId;
	private String consumerId;
	private ChannelVO channel;
	private List subjectList;
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public String getConsumerNS() {
		return consumerNS;
	}
	public void setConsumerNS(String consumerNS) {
		this.consumerNS = consumerNS;
	}
	public String getConsumerType() {
		return consumerType;
	}
	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public ChannelVO getChannel() {
		return channel;
	}
	public void setChannel(ChannelVO channel) {
		this.channel = channel;
	}
	public List getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List subjectList) {
		this.subjectList = subjectList;
	}
	
	
	
}
