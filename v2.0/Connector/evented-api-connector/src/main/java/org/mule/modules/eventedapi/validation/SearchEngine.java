package org.mule.modules.eventedapi.validation;

import java.io.IOException;



import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;


//import org.apache.lucene.search.Hits;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
//import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.TextFragment;
import org.apache.lucene.store.RAMDirectory;

import org.apache.log4j.Logger;

public class SearchEngine 
{
	  private RAMDirectory idx;
	  private IndexWriter writer;
	  private IndexSearcher searcher;
	  private StandardAnalyzer analyzer;
	  private ArrayList segmentList;
	 
	  private static String fragmentSeparator="***";
	  
	  public SearchEngine()
	  {
		  idx=new RAMDirectory();
		  analyzer = new StandardAnalyzer();			 		  
	  }	  
	  /*
	  public int searchText(String pQuery, String pContentTitle, String pContent) throws Exception
	  {
		  int retVal=0;
		 
	    try
		  {
	    	
	    		writer = new IndexWriter(idx,analyzer, true);
	            writer.addDocument(createDocument(pContentTitle,pContent));
	            writer.optimize();
	            writer.close();
			  	searcher = new IndexSearcher(idx);
	            retVal = search(searcher, pQuery,pContent);
	            searcher.close();	
	                     
	        
		  }  
		  catch(IOException ioe) 
		  {
            //throw new BullseyeException(ioe,"Got memory writing problem");
		  }
		  catch(ParseException pe) 
		  {
			  pe.printStackTrace();
			  //throw new BullseyeException(pe,"Got a Parsing error");				  
		  }
		  catch(Exception excp)
		  {
			  excp.printStackTrace();
		  }
		  
		  return retVal;
	  }
	  */
	  /*
	  private static Document createDocument(String title, String content)
	  {
		  
	        Document doc = new Document();
	        
	        doc.add(new Field("TITLE", title,Field.Store.YES,Field.Index.UN_TOKENIZED));
	        doc.add(new Field("CONTENT", new StringReader(content)));
	        
	        return doc;
	   }
	  */
	  /*
	  private int search(Searcher pSearcher, String pQueryString,String pContent) throws ParseException, IOException
	  {
		  TextFragment results []=null;
		  int retVal=0;
		  // Build a Query object
		  String docTitle=null;
		  QueryParser qParser = new QueryParser("CONTENT",analyzer);
		  Query query = qParser.parse(pQueryString);
		  // Search for the query
		  Hits hits = searcher.search(query);
		  // Examine the Hits object to see if there were any matches
		  int hitCount = hits.length();
		  if (hitCount == 0) 
		  {
			  System.out.println( "No matches were found for \"" + pQueryString + "\"");
			  
		  }
	      else 
	      {
	          System.out.println(hitCount+" Hits for \"" +pQueryString + "\" were found !");	   
	          retVal=hitCount;
	        
		        
	      }	    
		  return retVal;
	  }
	 */
}
