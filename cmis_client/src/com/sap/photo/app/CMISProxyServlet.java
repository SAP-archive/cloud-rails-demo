package com.sap.photo.app;

import com.sap.ecm.api.AbstractCmisProxyServlet;


/* This is a Proxy Servlet for accessing the SAP HANA Cloud document service as described here:
 * https://help.hana.ondemand.com/help/frameset.htm?76135da6711e1014839a8273b0e91070.html
 *
 * For using it we specify the key and unique name of our ECM repository that we want to be proxied to.
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
}