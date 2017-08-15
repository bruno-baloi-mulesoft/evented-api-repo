package org.mule.modules.eventing.raml;

import java.util.List;

import org.mule.modules.eventing.util.MuleConstants;
import org.mule.modules.eventing.util.Util;
import org.mule.modules.eventing.vo.ConnectorConfigVO;
import org.mule.modules.eventing.vo.TransportConfigConnectorVO;
import org.mule.modules.eventing.vo.TransportConfigVO;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.common.ValidationResult;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.api.Library;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.declarations.AnnotationRef;
import org.raml.v2.api.model.v10.resources.Resource;

public class RAMLParserWrapper 
{
	private static RAMLParserWrapper instance=null;
	private Api consumerApi=null;
	

	private Api producerApi=null;
	
	private RAMLParserWrapper()
	{
		
	}
	public static RAMLParserWrapper getInstance()
	{
		if (instance ==null)
			instance = new RAMLParserWrapper();
		return instance;
		
	}
	
	public TransportConfigConnectorVO parseConfigurationFiles(ConnectorConfigVO vo)
	{
		System.out.println("---Parsing RAML Specification; consumer file="+vo.getConsumerRAML()+",producer file="+vo.getProducerRAML());
		TransportConfigConnectorVO _transport;
		
		_transport = generateTransportData(vo);
		
		return _transport;
	
	}
	private TransportConfigConnectorVO generateTransportData(ConnectorConfigVO pVo)
	{
		TransportConfigConnectorVO _transport=new TransportConfigConnectorVO();
		
		TransportConfigVO _consumer = parseConsumer(pVo.getConsumerRAML());
		TransportConfigVO _producer = parseProducer(pVo.getProducerRAML());
		
		_transport.setConsumerTransport(_consumer);
		_transport.setProducerTransport(_producer);
		
		Util.getInstance().setTransportConfigConsumer(_consumer);
		Util.getInstance().setTransportConfigProducer(_producer);
		
		return _transport;
	}
	private TransportConfigVO parseConsumer(String pFile)
	{
		
		TransportConfigVO _retVal=new TransportConfigVO();
		
		RamlModelResult ramlModelResultConsumer = new RamlModelBuilder().buildApi(pFile);
		if (ramlModelResultConsumer.hasErrors())
		{
		    for (ValidationResult validationResult : ramlModelResultConsumer.getValidationResults())
		    {
		        System.out.println(validationResult.getMessage());
		    }
		}
		else
		{
		    consumerApi = ramlModelResultConsumer.getApiV10();
		    handleRAML(consumerApi,MuleConstants.consumer,_retVal);

		}
				
				return _retVal;
	}
	private TransportConfigVO parseProducer(String pFile)
	{
		TransportConfigVO _retVal=new TransportConfigVO();
		
			RamlModelResult ramlModelResultProducer = new RamlModelBuilder().buildApi(pFile);
			if (ramlModelResultProducer.hasErrors())
			{
			    for (ValidationResult validationResult : ramlModelResultProducer.getValidationResults())
			    {
			        System.out.println(validationResult.getMessage());
			    }
			}
			else
			{
			    producerApi = ramlModelResultProducer.getApiV10();
			    handleRAML(producerApi,MuleConstants.producer,_retVal);
			}
		
			return _retVal;
	}
	
	
	private void handleRAML(Api pAPI,String pType,TransportConfigVO pTransport)
	{
		List <AnnotationRef> _anotationList = pAPI.annotations();
		List <TypeDeclaration> _anotationTypesList = pAPI.annotationTypes();
		List <String> _protocolList = pAPI.protocols();
		List <Resource> _resourceList = pAPI.resources();
		List <TypeDeclaration> _typeList = pAPI.types();
		List <Library> _libraryList = pAPI.uses();
	
		
		Util util = Util.getInstance();
		util.displayResourceList(_resourceList,pType);
		util.displayLibraryList(_libraryList,pType);
		//util.displayTypesList(_typeList,pType);
		//util.displayAnnotationList(_anotationList,pType);
		//util.displayAnnotationTypesList(_anotationTypesList,pType);
		//util.displayProtocolList(_protocolList,pType);
		
	}
	public Api getConsumerApi() {
		return consumerApi;
	}
	public Api getProducerApi() {
		return producerApi;
	}

}
