package org.mule.modules.eventing.persistence;

import org.raml.v2.api.model.v10.api.Api;

public interface IPersistence 
{
	public String storeModel(Api pAPI);
}
