package org.mule.modules.eventedapi.messaging;

public class Event 
{
	private String eventId;
	private String transportType;
	private Object messagePayload;
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public Object getMessagePayload() {
		return messagePayload;
	}
	public void setMessagePayload(Object messagePayload) {
		this.messagePayload = messagePayload;
	}
	

}
