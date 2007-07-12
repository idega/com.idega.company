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

public class OperationFormBMPBean extends GenericEntity implements
		OperationForm {
	
	private static final long serialVersionUID = 418489784193670774L;
	private static Logger logger = Logger.getLogger(OperationFormBMPBean.class.getName());

	protected static final String ENTITY_NAME = "com_operation_form";
	
	protected static final String CODE = "code";

	protected static final String DESCRIPTION = "description";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());
		addAttribute(CODE, "Code", true, true, java.lang.String.class, 2);
		addAttribute(DESCRIPTION, "Description", true, true, java.lang.String.class);
		
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
	
	public Collection ejbFindAllOperationForms() throws FinderException, RemoteException {
		return super.idoFindAllIDsBySQL();
	}
	
	public Integer ejbFindOperationFormByUniqueCode(String uniqueId) throws FinderException {
		IDOQuery query = idoQueryGetSelect();
		query.appendWhereEqualsQuoted(CODE, uniqueId);

		return (Integer) idoFindOnePKByQuery(query);
	}
	
	public void insertStartData() throws Exception {
		super.insertStartData();
		logger.log(Level.ALL, "OperationFormBMP after super.insertStartData");
		InputStream is = getCodesInputStream();
		
		if(is == null)
			return;
		logger.log(Level.ALL, "OperationFormBMP get inputStream");
	    HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
	    
	    HSSFSheet sheet = wb.getSheetAt(4);
	    if(sheet != null) {
	    	logger.log(Level.ALL, "OperationFormBMP get 4th sheet in XLS file");
	    	Iterator it = sheet.rowIterator();
	    	if(it.hasNext()) {
	    		it.next();
	    	}
	    	while(it.hasNext()) {
	    		HSSFRow row = (HSSFRow) it.next();
	    		if(row != null) {
	    			HSSFCell codeCell = row.getCell((short) 0);
	    			if(codeCell != null) {
	    				logger.log(Level.ALL, "OperationFormBMP create and store new entity");
	    				OperationForm operationForm = getOperationFormHome().create();
	    				operationForm.setCode(codeCell.getStringCellValue());
	    				
	    				HSSFCell descriptionCell = row.getCell((short) 1);
	    				if(descriptionCell != null) {
	    					operationForm.setDescription(descriptionCell.getStringCellValue());
	    				}
	    				
	    				operationForm.store();
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
	
	protected OperationFormHome getOperationFormHome() throws RemoteException {
		return (OperationFormHome) IDOLookup.getHome(OperationForm.class);
	}
}