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

public class IndustryCodeBMPBean extends GenericEntity implements IndustryCode {

	private static Logger logger = Logger.getLogger(IndustryCodeBMPBean.class.getName());
	
	private static final long serialVersionUID = 5579820162509715518L;

	protected static final String ENTITY_NAME = "com_industry_code_isat";
	
	protected static final String CODE = "code";

	protected static final String DESCRIPTION = "description";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());
		addAttribute(CODE, "ISAT Code", true, true, java.lang.String.class, 5);
		addAttribute(DESCRIPTION, "Description", true, true, java.lang.String.class);
		
		addIndex(CODE);
		setUnique(CODE, true);
	}

	public String getISATCode() {
		return getStringColumnValue(CODE);
	}

	public String getISATDescription() {
		return getStringColumnValue(DESCRIPTION);
	}

	public void setISATCode(String code) {
		setColumn(CODE, code);
	}

	public void setISATDescription(String description) {
		setColumn(DESCRIPTION, description);
	}
	
	public Collection ejbFindAllIndustryCodes() throws FinderException, RemoteException {
		return super.idoFindAllIDsBySQL();
	}
	
	public Integer ejbFindIndustryByUniqueCode(String uniqueId) throws FinderException {
		IDOQuery query = idoQueryGetSelect();
		query.appendWhereEqualsQuoted(CODE, uniqueId);

		return (Integer) idoFindOnePKByQuery(query);
	}

	public void insertStartData() throws Exception {
		super.insertStartData();
		InputStream is = getCodesInputStream();
		
		if (is == null) {
			return;
		}
		
		try {
		    HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
		    
		    HSSFSheet sheet = wb.getSheetAt(0);
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
		    				IndustryCode industryCode = getIndustryCodeHome().create();
		    				industryCode.setISATCode(codeCell.getStringCellValue());
		    				
		    				HSSFCell descriptionCell = row.getCell((short) 1);
		    				if(descriptionCell != null) {
		    					industryCode.setISATDescription(descriptionCell.getStringCellValue());
		    				}
		    				industryCode.store();
		    			}
		    		}
		    	}
		    }
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			is.close();
		}
	}
	
	private InputStream getCodesInputStream() {
		String path = getIWMainApplication().getBundlesRealPath()+"/"+CompanyConstants.IW_BUNDLE_IDENTIFIER+".bundle/resources/startdata/Codes.xls";
		try {
			return new FileInputStream(path);
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Exception while retrieving codes from: " + path, e);
			return null;
		}
	}
	
	protected IndustryCodeHome getIndustryCodeHome() throws RemoteException {
		return (IndustryCodeHome) IDOLookup.getHome(IndustryCode.class);
	}
}