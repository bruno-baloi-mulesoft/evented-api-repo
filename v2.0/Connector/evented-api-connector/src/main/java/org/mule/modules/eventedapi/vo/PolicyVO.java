package org.mule.modules.eventedapi.vo;

public class PolicyVO 
{
	
	private String policyID;
	private String policyType;
	private String enforcementCriteria;
	private String policyName;
	private String policyNS;
	private String direction;
	
	
	public String getPolicyID() {
		return policyID;
	}
	public void setPolicyID(String policyID) {
		this.policyID = policyID;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getEnforcementCriteria() {
		return enforcementCriteria;
	}
	public void setEnforcementCriteria(String enforcementCriteria) {
		this.enforcementCriteria = enforcementCriteria;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getPolicyNS() {
		return policyNS;
	}
	public void setPolicyNS(String policyNS) {
		this.policyNS = policyNS;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	

}
