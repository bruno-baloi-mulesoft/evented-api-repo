package org.mule.modules.eventing.persistence;

import org.raml.v2.api.model.v10.api.Api;

public class ModelPersistence implements IPersistence
{
	private static ModelPersistence instance=null;
	private boolean modelPersisted=false;
	
	private void ModelPersistence()
	{
		
	}
	public static ModelPersistence getInstance()
	{
		if(instance==null)
			instance = new ModelPersistence();
		
		return instance;
		
		
	}
	
	
			
	public String storeModel(Api pAPI) {
		// TODO Auto-generated method stub
		
		if(!modelPersisted)
		{
			
		}
		else
		{
			
		}
		return null;
	}
	

}
