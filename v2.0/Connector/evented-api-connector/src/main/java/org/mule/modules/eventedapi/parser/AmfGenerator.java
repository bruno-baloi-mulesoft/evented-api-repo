package org.mule.modules.eventedapi.parser;


import amf.model.document.BaseUnit;


import amf.model.document.Document;
import amf.model.document.*;
import amf.plugins.document.vocabularies.spec.Dialect;
import amf.core.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AmfGenerator {
	
	public static Dialect registerDialect(String url) throws ExecutionException, InterruptedException {
        return amf.plugins.document.Vocabularies.registerDialect(url).get();
    }
	
	 public Document parseAmfFile(String path) throws ParserException {
	        return handleFuture(amf.Core.parser("AMF Graph", "application/ld+json").parseFileAsync(filePrefix+path));
	    }
	 private static Document handleFuture(CompletableFuture<BaseUnit> f) throws ParserException {
	        try {
	            return (Document)f.get();
	        } catch (InterruptedException | ExecutionException e) {
	            throw new ParserException("An error happend while parsing the api. Message: " + e.getMessage(), e);
	        }
	    }
	    
	    private static final String filePrefix = "file://";
	    
	    
	    public static Document parseAmfString(String api) throws ParserException {
	       
	    	Document _doc=null;
	    	

	    	//_doc=  handleFuture(amf.Core.parser("AMF Graph", "application/ld+json").parseStringAsync(api));
	    	
	    	return _doc;
	        
	        
	    }
	    
	    public Document parseAmfFromJSONLD(String jsonldPath)
	    {
	    	Document parsedAmfApi = null;
	    	String workingDirectory;
	    	
	    	try {
	            workingDirectory = new File(".").getCanonicalPath() +  "/src/main/resources/";
	          
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    	/*
	        try {
	            parsedAmfApi = new Document().parseAmfFile(workingDirectory+"examples/banking-api.json");
	        } catch (ParserException e) {
	            e.printStackTrace();
	        }
	        */
	        return parsedAmfApi;
	        
	    }
	    

}
