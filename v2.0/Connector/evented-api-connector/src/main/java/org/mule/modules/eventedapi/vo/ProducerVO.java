package org.mule.modules.eventedapi.vo;

import java.util.List;

public class ProducerVO 
{
	private String producerName;
	private String producerNS;
	private String producerType;
	private String applicationId;
	private String userId;
	private String producerId;
	private ChannelVO channel;
	private List subjectList;
	
	
	
	public String getProducerName() {
		return producerName;
	}
	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}
	public String getProducerNS() {
		return producerNS;
	}
	public void setProducerNS(String producerNS) {
		this.producerNS = producerNS;
	}
	public String getProducerType() {
		return producerType;
	}
	public void setProducerType(String producerType) {
		this.producerType = producerType;
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
	public String getProducerId() {
		return producerId;
	}
	public void setProducerId(String producerId) {
		this.producerId = producerId;
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
