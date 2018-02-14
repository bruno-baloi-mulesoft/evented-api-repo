package org.mule.modules.eventedapi.util;


import java.io.BufferedReader;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.jena.atlas.json.JSON;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RiotException;
import org.mule.modules.eventedapi.EventedApiConnector;
import org.mule.modules.eventedapi.parser.AmfReferencesParser;
import org.mule.modules.eventedapi.parser.AmfEncodingParser;
import org.mule.modules.eventedapi.parser.AmfParserFactory;
import org.mule.modules.eventedapi.vo.ChannelVO;
import org.mule.modules.eventedapi.vo.ConsumerVO;
import org.mule.modules.eventedapi.vo.EventedAPIConnectorConfigVO;
import org.mule.modules.eventedapi.vo.ProducerVO;
import org.mule.modules.eventedapi.vo.SubjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;

import amf.model.document.*;

//import amf.client.*;
//import amf.client.commands.*;

import static org.apache.jena.riot.RDFLanguages.JSONLD ;



public class AmfParseUtil {
	
	private static Logger logger = LoggerFactory.getLogger(AmfParseUtil.class);
	
	//command = java -jar amf.jar parse --dialects file://Dialects/EventDialect.raml -in "RAML 1.0" -mime-in "application/yaml" file://examples/EventedAPI_Banking.raml > evented_api_banking2.json

	
	private static AmfParseUtil instance=null;
	private Document amfDoc=null;
	
	public static AmfParseUtil getInstance()
	{
		if(instance==null)
			instance = new AmfParseUtil();
		return instance;
	}
	
	public EventedAPIConnectorConfigVO getConnectorConfiguration(String pJSONLD)
	{
		
		EventedAPIConnectorConfigVO _retVo= new EventedAPIConnectorConfigVO();
		
		logger.info("----Extracting JSON-LD data-----");
			
			//_retVo=getOntologyBased( pJSONLD);
		_retVo = getJSONLDBased(pJSONLD);
			
			
		
		return _retVo;
	}
	
	public String parseAMFDoc(String pAMFDoc, String pDialectDir, String pAmfLib,String pParseCmdPath)
	{
		String _jsonPayload=new String();
		char [] charBuff = new char [1000000];
	
		try
		{
			List<String> _dialectList = getDialects(pDialectDir);
			
			//String command=buildAMFParseCommand(pAMFDoc ,_dialectList,pAmfLib);
			//String [] command =  buildAMFParseCommandArr(pAMFDoc ,_dialectList,pAmfLib);
			String command=buildAMFParseCommandSh(pAMFDoc ,_dialectList,pAmfLib,pParseCmdPath);
		
			
			// Get runtime
	        java.lang.Runtime rt = java.lang.Runtime.getRuntime();
	        java.lang.Process p = rt.exec(command);
	        
	        
	        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String line;
			while ((line=reader.readLine()) != null)
			{
				_jsonPayload+=line+"\r\n";
			}
	        p.waitFor();
	        
	        logger.info("Process exited with code = " +  p.exitValue());
	        if(p.exitValue() >0)
	        {
	        	InputStream _is = p.getErrorStream();
	        	logger.error("Failed to parse AMF Doc:"+ IOUtils.toString(_is, StandardCharsets.UTF_8));
	        	System.exit(1);
	        }
	        else
	        {
	        	//InputStream _is = p.getInputStream();
	        	//_jsonPayload = IOUtils.toString(_is, StandardCharsets.UTF_8);
	        	
	        	logger.info("AMF Parser output: "+ _jsonPayload);
	        	
	        }
	        
	        
		}
		catch(Exception pExcp)
		{
			pExcp.printStackTrace();
		}
		
		return _jsonPayload;
	}
	
	private List<String> getDialects(String pDialectDir)
	{
		ArrayList<String> fileList = new ArrayList();
		
		try
		{
				
				File rootDir = new File(pDialectDir);
				File[] files = rootDir.listFiles();
				
				 for(File f : files){
			            if(f.isFile())
			            {
			            	String _canonicalPath = f.getCanonicalPath();
			            	
			            	if(_canonicalPath.contains(".raml") || _canonicalPath.contains(".RAML") || _canonicalPath.contains(".json"))
			            	{
			            		logger.info("Dialect:" + f.getCanonicalPath());
			            		fileList.add(f.getCanonicalPath());
			            	}
			            	
			            }
			        }
		
				
		}
		catch(Exception pExcp)
		{
			pExcp.printStackTrace();
		}
		
		return fileList;
	}
	
	
	
	private String buildAMFParseCommandSh(String pAMFDoc, List<String> pDialects, String pAmfLib,String pParseCmdPath)
	{
		//  java -jar amf.jar parse -in "RAML 1.0" -mime-in "application/yaml" --dialects file://Dialects/EventDialect.raml file://examples/EventedAPI_Banking.raml
		
		//String _command="java -jar /"+pAmfLib+" parse --dialects ";
		String _command="/"+pParseCmdPath + " /"+pAmfLib;
		
		Iterator _it= pDialects.iterator();
		int i=0;
		while(_it.hasNext())
		{
			String _dialect = (String ) _it.next();
			if(i+1==pDialects.size())
			{
				//_command+="/"+_dialect;
				_command+=" file://"+_dialect;
			}
			else
				_command+=_dialect+",";
			
			
			_command+= " file://"+pAMFDoc;
			//_command=_command +  " --format-in 'RAML 1.0' --media-type-in 'application/yaml' file://"+pAMFDoc;
			
			logger.info("+++Command to execute: "+ _command);
		
			
		}
		
		return _command;
	}
	
	private String [] buildAMFParseCommandArr(String pAMFDoc ,List<String> pDialects,String pAmfLib)
	{
		String [] _command = new String [11];
		
		_command[0]="java";
		_command[1]="-jar";
		_command[2]="/"+pAmfLib;
		_command[3]="parse";
		_command[4]="--dialects";
		
		String _dialects="";
		Iterator _it= pDialects.iterator();
		int i=0;
		while(_it.hasNext())
		{
			String _dialect = (String ) _it.next();
			if(i+1==pDialects.size())
			{
				//_command+="/"+_dialect;
				_dialects+="file://"+_dialect;
			}
			else
				_dialects=_dialect+",";
		}
		_command[5]=_dialects;
		_command[6]="-in";
		_command[7]="'RAML 1.0'";
		_command[8]="-mime-in";
		_command[9]="\"application/yaml\"";
		_command[10]="file://"+pAMFDoc;
		
		String _cmd="";
		for(int x=0;x< _command.length;x++)
		{
			_cmd+=_command[i]+" ";
			i++;
		}
		System.out.println("Command to execute: "+ _cmd);
		
		return _command;
	}
	
	private String buildAMFParseCommand(String pAMFDoc, List<String> pDialects, String pAmfLib)
	{
		//  java -jar amf.jar parse -in "RAML 1.0" -mime-in "application/yaml" --dialects file://Dialects/EventDialect.raml file://examples/EventedAPI_Banking.raml
		
		//String _command="java -jar /"+pAmfLib+" parse --dialects ";
		String _command="java -jar /"+pAmfLib+" parse --dialects ";
		
		Iterator _it= pDialects.iterator();
		int i=0;
		while(_it.hasNext())
		{
			String _dialect = (String ) _it.next();
			if(i+1==pDialects.size())
			{
				//_command+="/"+_dialect;
				_command+="file://"+_dialect;
			}
			else
				_command+=_dialect+",";
			
			//_command+=" -in RAML /"+pAMFDoc;
			_command+= " -in \"RAML 1.0\" -mime-in \"application/yaml\" file://"+pAMFDoc;
			//_command=_command +  " --format-in 'RAML 1.0' --media-type-in 'application/yaml' file://"+pAMFDoc;
			
			logger.info("+++Command to execute: "+ _command);
		
			
		}
		
		return _command;
	}
	
	
	private EventedAPIConnectorConfigVO getOntologyBased(String pJSONLD)
	{
		EventedAPIConnectorConfigVO _retVo=new EventedAPIConnectorConfigVO();
		
		try {
	           
			OntModel _model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
            ByteArrayInputStream r  = new ByteArrayInputStream(pJSONLD.getBytes());
            RDFDataMgr.read(_model, r, null, JSONLD) ;
                
                
              StmtIterator _it = _model.listStatements();
              System.out.println("-------AMF Model Statements------");
              while(_it.hasNext())
              {
            	  Statement _stmt = (Statement) _it.next();
            	  System.out.println(_stmt.getString());
              }
                
            
           
        } catch (Exception e) {
           
             e.printStackTrace();
        }
		return _retVo;
	}
	
	private EventedAPIConnectorConfigVO getJSONLDBased(String pJSONLD)
	{
		EventedAPIConnectorConfigVO _configVo=new EventedAPIConnectorConfigVO();
		AmfReferencesParser _refParser = AmfParserFactory.getInstance().getReferenceParser();
		AmfEncodingParser _docParser = AmfParserFactory.getInstance().getEncodingParser();
		
		try
		{
			InputStream in = new ByteArrayInputStream(pJSONLD.getBytes("UTF-8"));
			Object jsonObject = JsonUtils.fromInputStream(in);
			// Create a context JSON map containing prefixes and definitions
			Map context = new HashMap();
			// Create an instance of JsonLdOptions with the standard JSON-LD options
			JsonLdOptions options = new JsonLdOptions();
			// Customise options...
			// Call whichever JSONLD function you want! (e.g. compact)
			List  _docList = JsonLdProcessor.expand(jsonObject, options);
			
			Iterator _docIt = _docList.iterator();
			
			while(_docIt.hasNext())
			{
				//There is only One Linked HashMap
				
				LinkedHashMap _map = (LinkedHashMap) _docIt.next();
				
				logger.info("-----Values--------");
				
				HashMap _refMap = _refParser.processReferences((ArrayList) _map.get(AmfConstants.DOC_REFERENCES));
				HashMap _docMap = _docParser.processCoreDoc((ArrayList) _map.get(AmfConstants.DOC_ENCODING));
				
				//---assemble the Connector Config VO-----
				
				
				_configVo.setPersona((String)_docMap.get(AmfConstants.PERSONA_REF));
				_configVo.setConsumer((ConsumerVO) _docMap.get(AmfConstants.CONSUMER_REF_EVENT));
				_configVo.setProducer((ProducerVO) _docMap.get(AmfConstants.PRODUCER_REF_EVENT));
				
				
				
				/*
				Set _keySet = _map.keySet();
				Set _valSet = _map.entrySet();
				Iterator _keyIt = _keySet.iterator();
				Iterator _valIt = _valSet.iterator();
				
				while(_valIt.hasNext())
				{
					Entry _obj = (Entry)_valIt.next();
					System.out.println(_obj.getKey()+":"+_obj.getValue().getClass()+":"+_obj.getValue());
		
					String _key = (String)_obj.getKey();
					Object _valObj = _obj.getValue();
					
					if(_valObj instanceof ArrayList)
					{
						
						if(_key.equals(MuleConstants.DOC_ENCODING))
							_docParser.processCoreDoc((ArrayList)_valObj);
						if(_key.equals(MuleConstants.DOC_REFERENCES))
							_refParser.processReferences((ArrayList)_valObj);
						
					}
					
				}
				*/
				
			}
			
			
		}
		catch(Exception pExcp)
		{
			pExcp.printStackTrace();
		}
	
		return _configVo;
	}

	public Document getAmfDoc() {
		return amfDoc;
	}

	public void setAmfDoc(Document amfDoc) {
		this.amfDoc = amfDoc;
	}
	
	
}