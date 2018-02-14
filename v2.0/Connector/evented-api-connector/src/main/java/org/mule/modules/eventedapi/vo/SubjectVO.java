package org.mule.modules.eventedapi.vo;

import java.util.List;

public class SubjectVO {
	
	private String subjectId;
	private String subjectNS;
	private String subjectName;
	private String subjectType;
	private String eventPattern;
	private boolean enablePolicies;
	private List transportList;
	private List supportedEventList;
	private List policyList;
	
	
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getEventPattern() {
		return eventPattern;
	}
	public void setEventPattern(String eventPattern) {
		this.eventPattern = eventPattern;
	}
	public List getTransportList() {
		return transportList;
	}
	public void setTransportList(List transportList) {
		this.transportList = transportList;
	}
	public List getSupportedEventList() {
		return supportedEventList;
	}
	public void setSupportedEventList(List supportedEventList) {
		this.supportedEventList = supportedEventList;
	}
	public String getSubjectNS() {
		return subjectNS;
	}
	public void setSubjectNS(String subjectNS) {
		this.subjectNS = subjectNS;
	}
	public String getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public List getPolicyList() {
		return policyList;
	}
	public void setPolicyList(List policyList) {
		this.policyList = policyList;
	}
	public boolean isEnablePolicies() {
		return enablePolicies;
	}
	public void setEnablePolicies(boolean enablePolicies) {
		this.enablePolicies = enablePolicies;
	}
	
	

}
