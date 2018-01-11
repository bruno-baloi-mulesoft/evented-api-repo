package org.mule.modules.eventedapi.parser;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.mule.modules.eventedapi.util.AmfParseUtil;
import org.mule.modules.eventedapi.vo.ConnectionVO;
import org.mule.modules.eventedapi.vo.EventVO;
import org.mule.modules.eventedapi.vo.TransportVO;
import org.mule.modules.eventedapi.util.AmfConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class AmfReferencesParser 
{
	private static Logger logger = LoggerFactory.getLogger(AmfReferencesParser.class);
	
	public HashMap  processReferences(ArrayList pRef)
	{
		//logger.info("+++Processing References !");
		
		List _eventList=null;
		List _transportList=null;
		
		HashMap _refMap = new HashMap();
		
		
		try
		{
			Iterator _valIt2 = pRef.iterator();
			while(_valIt2.hasNext())
			{
				Object _valObj2 = _valIt2.next();
				
				if(_valObj2 instanceof LinkedHashMap)
				{
					LinkedHashMap _map = (LinkedHashMap) _valObj2;	
					
					Set _keySet = _map.keySet();
					Iterator _keyIt = _keySet.iterator();
					while(_keyIt.hasNext())
					{
						String _key = (String) _keyIt.next();
						
						
						if(_key.equals(AmfConstants.DECLARES_NS))
						{
							ArrayList _declList = (ArrayList) _map.get(AmfConstants.DECLARES_NS);
							LinkedHashMap _declMap = (LinkedHashMap)_declList.get(0);
							
							//logger.info("Ref Type:"+_declMap.get(AmfConstants.TYPE)+", class="+_declMap.get(AmfConstants.TYPE).getClass());
							
							String _type = (String) ((ArrayList) _declMap.get(AmfConstants.TYPE)).get(0);
							
							if(_type.equals(AmfConstants.BASE_EVENT))
							{
								_eventList = processEvents(_declList);
								_refMap.put(AmfConstants.EVENT_REF, _eventList);
							}
							if(_type.equals(AmfConstants.BASE_TRANSPORT))
							{
								_transportList = processTransports(_declList);
								_refMap.put(AmfConstants.TRANPORT_REF, _transportList);
							}
						}
							
						
					}
					
					
					
					/*
					ArrayList _list = (ArrayList) _map.get(AmfConstants.DECLARES_NS);
			
					Iterator _it = _list.iterator();
					while(_it.hasNext())
					{
						Object _obj = _it.next();
						System.out.println("Ref Objclass="+_obj.getClass()+", value="+_obj);
						if(_obj instanceof LinkedHashMap)
						{
							LinkedHashMap _declMap = (LinkedHashMap) _obj;
							
							if(_declMap.containsKey(AmfConstants.EVENT_LIB_NS))
							{
								_eventList = processEvents((ArrayList) _declMap.get(AmfConstants.EVENT_LIB_NS));
								_refMap.put(AmfConstants.EVENT_REF, _eventList);
								
							}
							if(_declMap.containsKey(AmfConstants.TRANSPORT_LIB_NS))
							{
								_transportList = processTransports((ArrayList) _declMap.get(AmfConstants.TRANSPORT_LIB_NS));
								_refMap.put(AmfConstants.TRANPORT_REF, _transportList);
							}
							
						}
					
					}
					*/
				}
				
				
			}
				
		
			
		}
		catch(Exception pExcp)
		{
			pExcp.printStackTrace();
		}
		
		return _refMap;
	}
	
	public static List processEvents(ArrayList pList)
	{
		ArrayList _evList = new ArrayList();
		
		//logger.info("---Processing Events Lib----");
		Iterator _eventIt = pList.iterator();
		while(_eventIt.hasNext())
		{
			Object _eObj = _eventIt.next();
		//	System.out.println("-Event class:"+ _eObj.getClass()+", value="+_eObj);
			
			if(_eObj instanceof LinkedHashMap)
			{
				LinkedHashMap _eventMap = (LinkedHashMap) _eObj;
				
				String _eventId =(String) _eventMap.get(AmfConstants.ID);
				ArrayList _eTypeList = (ArrayList) _eventMap.get(AmfConstants.TYPE);
				String _eventType =(String) _eTypeList.get(0);
				
				
				ArrayList _eCatList = (ArrayList)_eventMap.get(AmfConstants.EVENT_CATEGORY);
				LinkedHashMap _eCatMap = (LinkedHashMap) _eCatList.get(0);
				String _eCat = (String) _eCatMap.get(AmfConstants.VALUE);
				
				ArrayList _eDomainList = (ArrayList)_eventMap.get(AmfConstants.EVENT_DOMAIN);
				LinkedHashMap _eDomainMap = (LinkedHashMap) _eDomainList.get(0);
				String _eDomain = (String) _eDomainMap.get(AmfConstants.VALUE);
				
				ArrayList _eIdList = (ArrayList)_eventMap.get(AmfConstants.EVENT_ID);
				LinkedHashMap _eIdMap = (LinkedHashMap) _eIdList.get(0);
				String _eId = (String) _eIdMap.get(AmfConstants.VALUE);
				
				ArrayList _ePayloadTypeList = (ArrayList)_eventMap.get(AmfConstants.EVENT_PAYLOAD_TYPE);
				LinkedHashMap _ePayloadTypenMap = (LinkedHashMap)_ePayloadTypeList.get(0);
				String _ePayloadType = (String) _ePayloadTypenMap.get(AmfConstants.VALUE);
				
				ArrayList _eNameList = (ArrayList)_eventMap.get(AmfConstants.EVENT_NAME);
				LinkedHashMap _eNameMap = (LinkedHashMap) _eNameList.get(0);
				String _eName = (String) _eNameMap.get(AmfConstants.VALUE);
				
				ArrayList _evTypeList = (ArrayList)_eventMap.get(AmfConstants.EVENT_TYPE);
				LinkedHashMap _evTypeMap = (LinkedHashMap) _evTypeList.get(0);
				String _evType = (String) _evTypeMap.get(AmfConstants.VALUE);
					
				EventVO _evVo = new EventVO();
				_evVo.setEventCategory(_eCat);
				_evVo.setEventDomain(_eDomain);
				_evVo.setEventId(_eId);
				_evVo.setEventName(_eName);
				_evVo.setEventNS(_eventId);
				_evVo.setEventPayloadType(_ePayloadType);
				_evVo.setEventType(_evType);
				
				//logger.info("Event Id="+_evVo.getEventId()+", Category="+_evVo.getEventCategory()+",Domain="+_evVo.getEventDomain()+",ID="+_evVo.getEventNS()+",Payload Type="+_evVo.getEventPayloadType()+", Name="+_evVo.getEventName() +", Type="+_evVo.getEventType());
				
				_evList.add(_evVo);
				
				
			}
		}
		
		return _evList;
	}
	public static List processTransports(ArrayList pList)
	{
		//logger.info("---Processing Transports Lib----");
		ArrayList _transportList = new ArrayList();
		
		Iterator _transportIt = pList.iterator();
		while(_transportIt.hasNext())
		{
			Object _tObj = _transportIt.next();
			
			if(_tObj instanceof LinkedHashMap)
			{
				LinkedHashMap _transportMap = (LinkedHashMap) _tObj;
				

				String _transportId =(String) _transportMap.get(AmfConstants.ID);
				ArrayList _tTypeList = (ArrayList) _transportMap.get(AmfConstants.TYPE);
				String _trasnportType =(String) _tTypeList.get(0);
				
				
				ArrayList _tIdList = (ArrayList)_transportMap.get(AmfConstants.TRANSPORT_ID);
				LinkedHashMap _tIdMap = (LinkedHashMap) _tIdList.get(0);
				String _tId = (String) _tIdMap.get(AmfConstants.VALUE);
				
				ArrayList _trTypeList = (ArrayList)_transportMap.get(AmfConstants.TRANSPORT_TYPE);
				LinkedHashMap _tTypeMap = (LinkedHashMap) _trTypeList.get(0);
				String _tType = (String) _tTypeMap.get(AmfConstants.VALUE);
				
				ArrayList _tVendorList = (ArrayList)_transportMap.get(AmfConstants.TRANSPORT_VENDOR);
				LinkedHashMap _tVendorMap = (LinkedHashMap) _tVendorList.get(0);
				String _tVendor = (String) _tVendorMap.get(AmfConstants.VALUE);
				
				ArrayList _tNameList = (ArrayList)_transportMap.get(AmfConstants.TRANSPORT_NAME);
				LinkedHashMap _tNameMap = (LinkedHashMap) _tNameList.get(0);
				String _tName = (String) _tNameMap.get(AmfConstants.VALUE);
					
				//--------Connection-------
				
				ArrayList _tConnection = (ArrayList) _transportMap.get(AmfConstants.TRANSPORT_CONNECTION);
				LinkedHashMap _connectionMap = (LinkedHashMap) _tConnection.get(0);
				
				String _connId = (String) _connectionMap.get(AmfConstants.ID);
				ArrayList _connTypeList = (ArrayList) _connectionMap.get(AmfConstants.TYPE);
				String _connType =(String) _connTypeList.get(0);
				
				ArrayList _conIdList = (ArrayList)_connectionMap.get(AmfConstants.CONNECTION_ID);
				LinkedHashMap _cIdMap = (LinkedHashMap) _conIdList.get(0);
				String _cId = (String) _cIdMap.get(AmfConstants.VALUE);
				
				
				ArrayList _conHostList = (ArrayList)_connectionMap.get(AmfConstants.CONNECTION_HOST);
				LinkedHashMap _cHostMap = (LinkedHashMap) _conHostList.get(0);
				String _cHost = (String) _cHostMap.get(AmfConstants.VALUE);
				
				ArrayList _conPortList = (ArrayList)_connectionMap.get(AmfConstants.CONNECTION_PORT);
				LinkedHashMap _cPortMap = (LinkedHashMap) _conPortList.get(0);
				Integer _cPort = (Integer) _cPortMap.get(AmfConstants.VALUE);
				
				ArrayList _conUserList = (ArrayList)_connectionMap.get(AmfConstants.CONNECTION_USER);
				LinkedHashMap _cUserMap = (LinkedHashMap) _conUserList.get(0);
				String _cUser = (String) _cUserMap.get(AmfConstants.VALUE);
				
				ArrayList _conPassList = (ArrayList)_connectionMap.get(AmfConstants.CONNECTION_PASSWORD);
				LinkedHashMap _cPassMap = (LinkedHashMap) _conPassList.get(0);
				String _cPass = (String) _cPassMap.get(AmfConstants.VALUE);
				
				
				ConnectionVO _connVo = new ConnectionVO();
				TransportVO _tranVo = new TransportVO();
				
				_tranVo.setTransportNS(_transportId);
				_tranVo.setTransportId(_tId);
				_tranVo.setTransportType(_tType);
				_tranVo.setTransportName(_tName);
				_tranVo.setTransportVendor(_tVendor);
				
				_connVo.setConnectionNS(_connId);
				_connVo.setConnectionId(_cId);
				_connVo.setHost(_cHost);
				_connVo.setPassword(_cPass);
				_connVo.setPort(_cPort);
				_connVo.setUser(_cUser);
				
				
				//logger.info("Transport Id:"+_tranVo.getTransportId()+", TrasnportName:"+_tranVo.getTransportName()+",Transport NS="+_tranVo.getTransportNS()+", Type="+_tranVo.getTransportType()+",Vendor="+_tranVo.getTransportVendor()+",Name="+_tranVo.getTransportName());
				
				//logger.info("Connection NS:"+_connId+",Connectiont Id="+_cId+", Host="+_cHost+",Port="+_cPort+",User="+_cUser+", Pass="+_cPass);
			
				
				_tranVo.setConnection(_connVo);
				
				_transportList.add(_tranVo);
				
				
			}
		}
		
		return _transportList;
	}
	private List processPolicies()
	{
		return null;
	}
	private List processPersistence()
	{
		return null;
	}
	
	

}
