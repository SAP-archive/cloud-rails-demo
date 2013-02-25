package cmis.client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisNameConstraintViolationException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;

import com.sap.ecm.api.EcmFactory;
import com.sap.ecm.api.RepositoryOptions;
import com.sap.ecm.api.RepositoryOptions.Visibility;
import com.sap.ecm.api.ServiceException;

/*
 * This is a Java adapter that the Photo Uploader application uses to interact with the
 * SAP NetWeaver Cloud document service. It provides with several static methods for
 * creating and deleting pictures in the application's ECM repository.
 * 
 * Consuming them from JRuby code is straightforward.
 *
 * Basic parameters for establishing an ECM session here are: repository key, unique name
 * and the user name on behalf of whom the ECM connection will be established. You can 
 * find them in the source code of the createEcmSession private method.
 */


public class CmisClient {		
	
	private static Session ecmSession = null;
	
	public static void createPhoto(String filename, String originalName) {
		
		// access the root folder of the repository
		Folder root = getRootFolder();
		
		// create a new file in the root folder
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
		properties.put(PropertyIds.NAME, originalName);
		
		File file = new File(filename);
		FileInputStream is = null;
		byte[] array = null;	
		try {
			is = new FileInputStream(file);
			array = new byte[is.available()];
			is.read(array);
		
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
		InputStream stream = new ByteArrayInputStream(array);		
		
		ContentStream contentStream = getEcmSession().getObjectFactory()
				.createContentStream(originalName, array.length, "image/jpeg", stream);
		try {
			root.createDocument(properties, contentStream, VersioningState.NONE);
		} catch (CmisNameConstraintViolationException e) {
			// Document exists already, nothing to do
		}
		
	}
	
	public static String getRepositoryID() {		
		return getEcmSession().getRepositoryInfo().getId();
	}
	
	
	public static String getObjectId(String name) {	
		Document doc = findObject(name);
		if (doc != null) {
			return doc.getId();
		}
		return null;
	}
	
	public static void deletePhoto(String name) {
		Document doc = findObject(name);
		if (doc != null) {
			doc.deleteAllVersions();
		}
	}
	
	
	// ------------------------------------------------ PRIVATE METHODS -------------------------------------------------
	
	private static Session getEcmSession() {
		
		if (ecmSession == null) {
			ecmSession = createEcmSession();
			return ecmSession;		
		} else {
			return ecmSession;
		}
	}

	private static Session createEcmSession() {
		String uniqueName = "photo.uploader.application";
		String secretKey = "very_very_secret_key";
		Session openCmisSession = null;
		String userName = "<your_ECM_user>";
		
		try {		 
		// connect to my repository
		  openCmisSession = EcmFactory.connectForUser(uniqueName, secretKey, userName);
		}
		catch (CmisObjectNotFoundException e) {
		  // repository does not exist, so try to create it
		  RepositoryOptions options = new RepositoryOptions();
		  options.setUniqueName(uniqueName);
		  options.setRepositoryKey(secretKey);
		  options.setVisibility(Visibility.PROTECTED);
		  options.setMultiTenantCapable(true);
		  try { 
			  EcmFactory.createRepository(options);
		  } catch (ServiceException sExc) {
			  System.out.println(sExc);
			  sExc.printStackTrace(System.out);
			  throw sExc;
		  }
		  // should be created now, so connect to it
		  openCmisSession = EcmFactory.connectForUser(uniqueName, secretKey, userName);
		}
		return openCmisSession;
	}
	
	private static Folder getRootFolder() {
		return getEcmSession().getRootFolder();
	}
	
	private static Document findObject(String name) {
		Folder rootFolder = getRootFolder();
		// list root folder children
		ItemIterable<CmisObject> children = rootFolder.getChildren();
		for (CmisObject object : children) {
			if (object.getName().equals(name)) {
				return (Document)object;
			}
		}
		return null;
	}
	
}