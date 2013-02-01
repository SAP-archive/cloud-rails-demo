package com.sap.photo.app;

import com.sap.ecm.api.AbstractCmisProxyServlet;


/*
 * Take a look here: <link to NW Trial help page for exposing document service>
 * 
 */
public class CMISProxyServlet extends AbstractCmisProxyServlet  {

	@Override
	protected String getRepositoryKey() {		
		return "very_very_secret_key";
	}

	@Override
	protected String getRepositoryUniqueName() {		
		return "photo.uploader.application";
	}	
	@Override
	protected String getDestinationName() {		
		return "ecmdest";
	}
}