package org.mule.modules.eventedapi.messaging;

import org.mule.modules.eventedapi.vo.ConnectionVO;

public abstract class  MessagingTransport implements Runnable, ITransport
{
	protected String subject;
	protected String pattern;
	protected String host;
	protected int port;
	protected String user;
	protected String password;
	protected String transportName;
	protected String transportType;
	protected String connectionId;
	protected String transportId;

	
	
	public MessagingTransport(String pTransportId,String pTransportName, String pTrasnportType,String pSubjectName, String pPattern, ConnectionVO pConnection)
	{
		subject=pSubjectName;
		pattern=pPattern;
		host=pConnection.getHost();
		port = pConnection.getPort().intValue();
		user = pConnection.getUser();
		password=pConnection.getPassword();
		transportName=pTransportName;
		transportType=pTrasnportType;
		connectionId = pConnection.getConnectionId();
		transportId = pTransportId;
		
		init();
	}
	
	protected abstract void init();
		

	public String getSubject() {
		return subject;
	}

	public void setSubject(String _subject) {
		this.subject = _subject;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String _pattern) {
		this.pattern = _pattern;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String _host) {
		this.host = _host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int _port) {
		this.port = _port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String _user) {
		this.user = _user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String _password) {
		this.password = _password;
	}

	public String getTransportName() {
		return transportName;
	}
	public String getTransportType() {
		return transportType;
	}

	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}
	

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	
	public String getTransportId() {
		return transportId;
	}

	public void setTransportId(String transportId) {
		this.transportId = transportId;
	}

	@Override
	public abstract int publishEvent(Event pEvent) throws Exception;

	@Override
	public abstract void registerConsumer(ICallback pConsumer) throws Exception ;

	@Override
	public abstract Event getNextEvent() ;
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(true);
		
	}
	
	

}
