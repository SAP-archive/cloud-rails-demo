package com.sap.photo.app;

import com.sap.ecm.api.AbstractCmisProxyServlet;


/* This is a Proxy Servlet for accessing the SAP NetWeaver Cloud document service as described here:
 * https://help.netweaver.ondemand.com/default.htm?expose_document_service.html#concept_F2D88F26B8674DD3A9EC737D37824C0D_96
 *
 * For using it we specify the key and unique name of our ECM repository that we want to be proxied to.
 * In case we consume the ECM service via a service user, we need to specify the name of the destination which
 * is needed for this type of consumption.
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