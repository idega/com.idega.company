package com.idega.company.data;

import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.FinderException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.idega.company.CompanyConstants;
import com.idega.data.GenericEntity;
import com.idega.data.IDOLookup;
import com.idega.data.IDOQuery;

public class UnregisterTypeBMPBean extends GenericEntity implements
		UnregisterType {
	
	private static final long serialVersionUID = 665161649041782603L;
	private static Logger logger = Logger.getLogger(UnregisterTypeBMPBean.class.getName());

	protected static final String ENTITY_NAME = "com_unregister_type";
	
	protected static final String CODE = "code";

	protected static final String DESCRIPTION = "description";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());
		addAttribute(CODE, "code", true, true, java.lang.String.class, 4);
		addAttribute(DESCRIPTION, "description", true, true, java.lang.String.class);
		
		addIndex(CODE);
		setUnique(CODE, true);
	}

	public String getDescription() {
		return getStringColumnValue(DESCRIPTION);
	}

	public String getCode() {
		return getStringColumnValue(CODE);
	}

	public void setDescription(String description) {
		setColumn(DESCRIPTION, description);
	}

	public void setCode(String code) {
		setColumn(CODE, code);
	}
	
	public Collection ejbFindAllUnregisterTypes() throws FinderException, RemoteException {
		return super.idoFindAllIDsBySQL();
	}
	
	public Integer ejbFindUnregisterTypeByUniqueCode(String uniqueId) throws FinderException {
		IDOQuery query = idoQueryGetSelect();
		query.appendWhereEqualsQuoted(CODE, uniqueId);

		return (Integer) idoFindOnePKByQuery(query);
	}

	public void insertStartData() throws Exception {
		super.insertStartData();
		
		InputStream is = getCodesInputStream();
		
		if(is == null)
			return;
	    HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
	    
	    HSSFSheet sheet = wb.getSheetAt(5);
	    if(sheet != null) {
	    	Iterator it = sheet.rowIterator();
	    	if(it.hasNext()) {
	    		it.next();
	    	}
	    	while(it.hasNext()) {
	    		HSSFRow row = (HSSFRow) it.next();
	    		if(row != null) {
	    			HSSFCell codeCell = row.getCell((short) 0);
	    			if(codeCell != null) {
	    				UnregisterType unregisterType = getUnregisterTypeHome().create();
	    				unregisterType.setCode(codeCell.getStringCellValue());
	    				
	    				HSSFCell descriptionCell = row.getCell((short) 1);
	    				if(descriptionCell != null) {
	    					unregisterType.setDescription(descriptionCell.getStringCellValue());
	    				}
	    				unregisterType.store();
	    			}
	    		}
	    	}
	    }
	}
	
	private InputStream getCodesInputStream() {
		
		try {
			return new FileInputStream(getIWMainApplication().getBundlesRealPath()+"/"+CompanyConstants.IW_BUNDLE_IDENTIFIER+".bundle/resources/startdata/Codes.xls");
			
		} catch (Exception e) {
			
			logger.log(Level.SEVERE, "Exception while retrieving codes.xls resource from: "+getIWMainApplication().getBundle(CompanyConstants.IW_BUNDLE_IDENTIFIER).getResourcesRealPath(), e);
			return null;
		}
	}
	
	protected UnregisterTypeHome getUnregisterTypeHome() throws RemoteException {
		return (UnregisterTypeHome) IDOLookup.getHome(UnregisterType.class);
	}	
}