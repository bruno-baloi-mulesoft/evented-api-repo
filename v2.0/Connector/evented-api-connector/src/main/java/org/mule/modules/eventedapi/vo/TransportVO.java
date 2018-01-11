package org.mule.modules.eventedapi.vo;

public class TransportVO 
{
	private String transportId;
	private String transportName;
	private String transportNS;
	private String transportType;
	private String transportVendor;
	private ConnectionVO connection;
	
	public String getTransportId() {
		return transportId;
	}
	public void setTransportId(String transportId) {
		this.transportId = transportId;
	}
	public String getTransportNS() {
		return transportNS;
	}
	public void setTransportNS(String transportNS) {
		this.transportNS = transportNS;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public String getTransportVendor() {
		return transportVendor;
	}
	public void setTransportVendor(String transportVendor) {
		this.transportVendor = transportVendor;
	}
	public ConnectionVO getConnection() {
		return connection;
	}
	public void setConnection(ConnectionVO connection) {
		this.connection = connection;
	}
	public String getTransportName() {
		return transportName;
	}
	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}
	
	

}
