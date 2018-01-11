package org.mule.modules.eventedapi.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mule.modules.eventedapi.util.AmfConstants;

public class ChannelVO 
{
	private String channelId;
	private String channelNS;
	private String channelType;
	private String channelName;
	private List subjectList;
	
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public List getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List subjectList) {
		this.subjectList = subjectList;
	}
	public String getChannelNS() {
		return channelNS;
	}
	public void setChannelNS(String channelNS) {
		this.channelNS = channelNS;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	
}
