package org.mule.modules.eventedapi.validation;

import java.io.IOException;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.analysis.Analyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.CollectorManager;

//import org.apache.lucene.search.Hits;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SimpleCollector;
import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.TextFragment;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchEngine 
{
	
	private static Logger logger = LoggerFactory.getLogger(SearchEngine.class);
	
	  private RAMDirectory idx;
	  private IndexWriter writer;
	  private IndexSearcher searcher;
	  private ArrayList segmentList;
	  private IndexWriterConfig conf; 
	  private IndexReader reader = null;
	  private StandardAnalyzer analyzer;
	  
	  private boolean init=false;
	  
	 // private static String fragmentSeparator="***";
	  
	  public SearchEngine()
	  {
		 			 	
			try {
				 idx=new RAMDirectory();
				 analyzer = new StandardAnalyzer();
				 conf = new IndexWriterConfig(analyzer);
				 
				  //searcher = new IndexSearcher(reader);
				//reader = DirectoryReader.open(idx);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		  
		     
	  }	  
	  
	  public long searchText(String pQuery, String pContentTitle, String pContent) throws Exception
	  {
		  long retVal=0;
		 
	    try
		  {
	   
	    		
	    	//writer = new IndexWriter(idx,analyzer, true);
	    	 if(!init)
	    	 {

		    		writer = new IndexWriter(idx, conf); 
		            writer.addDocument(createDocument(pContentTitle,pContent));
		            writer.commit();
		            reader = DirectoryReader.open(idx);
				  	searcher = new IndexSearcher(reader);
		           
		            //reader = DirectoryReader.open(idx);
				  	//searcher = new IndexSearcher(reader);
		            
				  	init=true;
	    	 }
			
	    	 	
	            retVal = search(pQuery,pContent);
	                     
	        
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
	  
	  
	  private static Document createDocument(String title, String content)
	  {
		  
	        Document doc = new Document();
	        
	        //doc.add(new Field("TITLE", title,Field.Store.YES,Field.Index.UN_TOKENIZED));
	       // doc.add(new Field("CONTENT", new StringReader(content)));
	        /*
	        FieldType _ft = new FieldType();
	        //_ft.setStored(true);
	        _ft.setTokenized(false);
	        _ft.setIndexOptions(IndexOptions.DOCS);
	        
	        doc.add(new Field("TITLE", title, _ft ));
	        Reader _rdr = new StringReader(content);
	        FieldType _ftc = new FieldType();
	        _ft.setStored(true);
	        _ft.setTokenized(false);
	        _ft.setIndexOptions(IndexOptions.DOCS);
	        doc.add(new Field("CONTENT", _rdr,_ftc));
	        */
	        
	        doc.add(new TextField("TITLE", title, Field.Store.NO));
	        doc.add(new TextField("CONTENT", content, Field.Store.YES));
	       
	       // logger.info(" ***** Searching on DOC: "+content);
	        
	        return doc;
	   }
	  
	  
	  private long search(String pQueryString,String pContent) throws ParseException, IOException
	  {
		  TextFragment results []=null;
		  long retVal=0;
		  // Build a Query object
		  String docTitle=null;
		  QueryParser qParser = new QueryParser("CONTENT",analyzer);
		  Query query = qParser.parse(pQueryString);
		  // Search for the query
		 // Hits hits = searcher.search(query);
		  
		  TopDocs _hits = searcher.search(query, 10);
		  
		  long hitCount2 = reader.docFreq(new Term(pQueryString));
		 
		  // Examine the Hits object to see if there were any matches
		  long hitCount = _hits.totalHits;
		  
		 logger.info("****** Found "+ hitCount2+" hits for pattern: "+pQueryString);
		 
		 //if (hitCount2 == 0) 
		if (hitCount == 0) 
		  {
			  logger.info( " **** No matches were found for \"" + pQueryString + "\"");
			  
		  }
	      else 
	      {
	          logger.info(" **** "+hitCount+" Hits for \"" +pQueryString + "\" were found !");	   
	          retVal=hitCount;
	        
		        
	      }	    
		  return retVal;
	  }
	  public void close()
	  {
		  try
		  {
			  writer.close();
			  reader.close();
			  idx.close();
		  }
		  catch(Exception excp)
		  {
			  excp.printStackTrace();
		  }
	  }
	 
}
