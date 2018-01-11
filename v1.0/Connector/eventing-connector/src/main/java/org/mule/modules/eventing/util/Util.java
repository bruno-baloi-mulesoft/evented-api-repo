package org.mule.modules.eventing.util;

import java.util.Iterator;
import java.util.List;

import org.mule.modules.eventing.EventingConnector;
import org.mule.modules.eventing.vo.TransportConfigVO;
import org.raml.v2.api.model.v10.api.Library;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.TypeInstance;
import org.raml.v2.api.model.v10.datamodel.TypeInstanceProperty;
import org.raml.v2.api.model.v10.declarations.AnnotationRef;
import org.raml.v2.api.model.v10.methods.Method;
import org.raml.v2.api.model.v10.methods.MethodBase;
import org.raml.v2.api.model.v10.resources.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util
{
	private static Logger logger = LoggerFactory.getLogger(Util.class);
	
	private static Util instance=null;
	private TransportConfigVO transportConfigConsumer;
	private TransportConfigVO transportConfigProducer;
	
	private Util()
	{
		
	}
			
	public static Util getInstance()
	{
		if (instance==null)
			instance = new Util();
		return instance;
	}
	
	public void displayResourceList(List <Resource> pList, String pType)
	{
		logger.info("-----"+pType+": Resources-------");
		Iterator it = pList.iterator();
		while(it.hasNext())
		{
			Resource _res = (Resource) it.next();
			logger.info("Resuorce name:"+_res.displayName().value());
			List <AnnotationRef> annotationList = _res.annotations();
			
			Iterator ait = annotationList.iterator();
			while(ait.hasNext())
			{
				AnnotationRef _an = (AnnotationRef) ait.next();
				String name = _an.annotation().name();
				if(name.equals("transport") || name.equals("jmsTransportVendor") || name.equals("pattern") || name.equals("endpoint"))
				{
				//logger.info("Annotation name: "+_an.annotation().name()+",value: "+_an.structuredValue().value().toString());
				logger.info("Annotation name: "+_an.annotation().name()+",value: "+_an.structuredValue().value());
				}
				if(name.equals("persistenceProperties") || name.equals("connectionProperties"))
				{
					logger.info("Annotation name: "+_an.annotation().name());
					List <TypeInstanceProperty> _propertyList = _an.structuredValue().properties();
					Iterator _pit = _propertyList.iterator();
					while(_pit.hasNext())
					{
						TypeInstanceProperty _prop = (TypeInstanceProperty) _pit.next();
						logger.info("	property - name: "+_prop.name()+",value: "+_prop.value().value());
					}
				}
				if(name.equals("permittedEvents"))
				{
					logger.info("Annotation name: "+_an.annotation().name());
					
					List <TypeInstanceProperty> _propertyList = _an.structuredValue().properties();
				
					Iterator _epit = _propertyList.iterator();
					while(_epit.hasNext())
					{
						TypeInstanceProperty _prop = (TypeInstanceProperty) _epit.next();
						if(_prop.isArray()) 
						{
							logger.info("This is an Array");
							//Object [] eventArray = (Object[]) _prop.values().toArray();
							List <TypeInstance> eventList = _prop.values();
							Iterator _evIt = eventList.iterator();
							while(_evIt.hasNext())
							{
								TypeInstance event = (TypeInstance) _evIt.next();
								//logger.info("Event value:"+ event.toString());
								logger.info("Event value size:"+ event.properties().size());
								List <TypeInstanceProperty> propList = event.properties();
								Iterator propIt = propList.iterator();
								while(propIt.hasNext())
								{
									TypeInstanceProperty inst = (TypeInstanceProperty) propIt.next();
									logger.info("PropName:"+inst.name()+"Prop Value:"+inst.value().value());
								}
								
							}
		
							
						
							
						}
						
					}
					
				}
				
			}
			List <Method> methodList = _res.methods();
			Iterator mit = methodList.iterator();
			
			while(mit.hasNext())
			{
				Method _method = (Method) mit.next();
				
				logger.info("Method: "+_method.displayName().value());
			}
			
			
			
		}
			
			
		
	}
	public void displayLibraryList(List <Library> pList, String pType)
	{
		Iterator _it = pList.iterator();
		while(_it.hasNext())
		{
			Library _lib = (Library) _it.next();
			logger.info("Library name:"+_lib.name()+", value size:"+_lib.types().size());
			List <TypeDeclaration> _typeList = _lib.types();
			Iterator _tit = _typeList.iterator();
			while(_tit.hasNext())
			{
				TypeDeclaration _type = (TypeDeclaration) _tit.next();
				logger.info("   data_type:"+ _type.name());
			}
			
		
		}
	}
	public void displayTypesList(List <TypeDeclaration> pList, String pType)
	{
		
	}
	public void displayAnnotationList(List <AnnotationRef> pList,String pType)
	{
		
	}
	public void displayAnnotationTypesList(List <TypeDeclaration> pList ,String pType)
	{
		
	}
	public void displayProtocolList(List <String> pList, String pType)
	{
		
	}

	public TransportConfigVO getTransportConfigConsumer() {
		return transportConfigConsumer;
	}

	public void setTransportConfigConsumer(TransportConfigVO transportConfig) {
		this.transportConfigConsumer = transportConfig;
	}

	public TransportConfigVO getTransportConfigProducer() {
		return transportConfigProducer;
	}

	public void setTransportConfigProducer(TransportConfigVO transportConfigProducer) {
		this.transportConfigProducer = transportConfigProducer;
	}
	
}