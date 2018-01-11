package org.mule.modules.eventedapi.messaging;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.mule.modules.eventedapi.EventedApiConnector;
import org.mule.modules.eventedapi.util.GenUtil;
import org.mule.modules.eventedapi.util.MessagingConstants;
import org.mule.modules.eventedapi.vo.ConnectionVO;
import org.mule.modules.eventedapi.vo.TransportVO;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;



public class MessagingTransportFactory 
{
	private HashMap _transportMap;
	
	private static MessagingTransportFactory instance;
	
	private static Logger logger = LoggerFactory.getLogger(MessagingTransportFactory.class);
	
	public static MessagingTransportFactory getInstance()
	{
		if (instance==null)
			instance = new MessagingTransportFactory();
		return instance;
	}
	public ITransport createTransport(String pSubjId,String pSubjName,String pPattern, TransportVO pVo, ISubject pSubject)
	{
		
		MessagingTransport _transport=null;
		
		String _tId = pVo.getTransportId();
		String _tName = pVo.getTransportName();
		String _tType = pVo.getTransportType();
		String _tVendor = pVo.getTransportVendor();
		ConnectionVO _conn = pVo.getConnection();
		
		//String _transportClassName ="org.mule.modules.eventedapi.messaging."+ _tType+_tVendor+"Transport";
		//String _callbackClassName ="org.mule.modules.eventedapi.messaging."+ _tType+_tVendor+"CallbackHandler";
		String _transportClassName ="org.mule.modules.eventedapi.messaging."+ _tType+_tVendor+GenUtil.getInstance().getConnectorProps().getProperty(MessagingConstants.transport_postfix);
		String _callbackClassName ="org.mule.modules.eventedapi.messaging."+ _tType+_tVendor+GenUtil.getInstance().getConnectorProps().getProperty(MessagingConstants.callback_postfix);;
		
		logger.info("Loading transport class:"+_transportClassName);
		logger.info("Loading transport class:"+_callbackClassName);
		
		try {
		
			Class<MessagingTransport> _transportClass = (Class) Class.forName(_transportClassName);
			Constructor _transportConstructor = _transportClass.getConstructor(String.class,String.class,String.class, String.class,String.class,ConnectionVO.class);
			//ITransport _transport = _transportClass.newInstance();
			_transport = (MessagingTransport) _transportConstructor.newInstance(_tId,_tName,_tType,pSubjName,pPattern,_conn);
			
			//create callback Handler
			Class<ICallback> _callbackClass = (Class) Class.forName(_callbackClassName);
			ICallback _callback = _callbackClass.newInstance();
			_callback.setSubject(pSubject);
			_transport.registerConsumer(_callback);
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}//Class.forName(name, initialize, loader)
		
		
		
		return _transport;
	}
	 
	
}
