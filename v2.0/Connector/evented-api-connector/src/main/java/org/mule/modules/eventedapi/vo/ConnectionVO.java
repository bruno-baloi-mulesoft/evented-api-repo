package org.mule.modules.eventedapi.vo;

public class ConnectionVO 
{
	private String connectionId;
	private String connectionNS;
	private String host;
	private Integer port;
	private String user;
	private String password;
	public String getConnectionId() {
		return connectionId;
	}
	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}
	public String getConnectionNS() {
		return connectionNS;
	}
	public void setConnectionNS(String connectionNS) {
		this.connectionNS = connectionNS;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
