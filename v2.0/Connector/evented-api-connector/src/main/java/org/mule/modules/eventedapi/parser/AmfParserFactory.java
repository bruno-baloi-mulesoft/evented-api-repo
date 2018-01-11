package org.mule.modules.eventedapi.parser;

public class AmfParserFactory
{
	
	private static AmfParserFactory _instance=null;
	static int _refParserCreated=0, _encodingParserCreated=0;
	
	public static AmfParserFactory getInstance()
	{
		if(_instance==null)
			_instance= new AmfParserFactory();
		
		return _instance;
	}
	
	public AmfReferencesParser getReferenceParser()
	{
		_refParserCreated++;
		return new AmfReferencesParser();
	}
	public AmfEncodingParser getEncodingParser()
	{
		_encodingParserCreated++;
		return new AmfEncodingParser();
	}
	public int getNumRefParsers()
	{
		return _refParserCreated;
	}
	public int getNumEncodingParsers()
	{
		return _encodingParserCreated;
	}

}
