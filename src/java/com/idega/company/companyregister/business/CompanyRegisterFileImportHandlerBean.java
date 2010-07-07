package com.idega.company.companyregister.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.idega.block.importer.business.ImportFileHandler;
import com.idega.block.importer.data.ImportFile;
import com.idega.business.IBOServiceBean;
import com.idega.company.CompanyConstants;
import com.idega.company.data.IndustryCode;
import com.idega.company.data.IndustryCodeHome;
import com.idega.company.data.OperationForm;
import com.idega.company.data.OperationFormHome;
import com.idega.company.data.UnregisterType;
import com.idega.company.data.UnregisterTypeHome;
import com.idega.data.IDOLookup;
import com.idega.user.data.Group;

public class CompanyRegisterFileImportHandlerBean extends IBOServiceBean 
		implements CompanyRegisterFileImportHandler, ImportFileHandler {
	
	private static final long serialVersionUID = 2628917943901423114L;
	
	public final static int COLUMN_UNIQUE_ID = 0;
	public final static int COLUMN_NOT_USED1 = 1;
	public final static int COLUMN_PERSONAL_ID = 2;
	public final static int COLUMN_COMMUNE = 3;
	public final static int COLUMN_POSTAL_CODE = 4;
	public final static int COLUMN_OLD_OPERATION_FORM = 5;
	public final static int COLUMN_WORKING_AREA = 6;
	public final static int COLUMN_ORDER_AREA_FOR_NAME = 7;
	public final static int COLUMN_NAME = 8;
	public final static int COLUMN_NOT_USED2 = 9;
	public final static int COLUMN_ADDRESS = 10;
	public final static int COLUMN_CEO_ID = 11;
	public final static int COLUMN_NOT_USED3 = 12;
	public final static int COLUMN_DATE_OF_LAST_CHANGE = 13;
	public final static int COLUMN_OPERATION_FORM = 14;
	public final static int COLUMN_NOT_USED4 = 15;
	public final static int COLUMN_VAT_NUMBER = 16;
	public final static int COLUMN_LEGAL_ADDRESS = 17;
	public final static int COLUMN_REGISTER_DATE = 18;
	public final static int COLUMN_OPERATION = 19;
	public final static int COLUMN_NOT_USED5 = 20;
	public final static int COLUMN_RECIPIENT_PERSONAL_ID = 21;
	public final static int COLUMN_RECIPIENT_NAME = 22;
	public final static int COLUMN_NOT_USED6 = 23;
	public final static int COLUMN_INDUSTRY_CODE = 24;
	public final static int COLUMN_NOT_USED7 = 25;
	public final static int COLUMN_E_FROM_FILE = 26;
	public final static int COLUMN_TYPE_OF_UNREGISTRATION = 27;
	public final static int COLUMN_DATE_OF_UNREGISTRATION = 28;
	public final static int COLUMN_NOT_USED8 = 29;
	public final static int COLUMN_BAN_MARKING = 30;
	public final static int COLUMN_NOT_USED9 = 31;

	private ImportFile file;
	private Logger logger = Logger.getLogger(CompanyRegisterFileImportHandlerBean.class.getName());
	private ArrayList failedRecordList = new ArrayList();
	private ArrayList valueList;
	private CompanyRegisterBusiness comp_reg_biz;

	public List getFailedRecords() throws RemoteException {
		return failedRecordList;
	}
	
	public List getSuccessRecords() throws RemoteException {
		return new ArrayList();
	}

	public boolean handleRecords() throws RemoteException {
		
		String item;
		
		try {
			checkCodesStartData();
			
			comp_reg_biz = (CompanyRegisterBusiness) getServiceInstance(CompanyRegisterBusiness.class);
			
			while (!(item = (String)file.getNextRecord()).equals("")) {
				
				if (!processRecord(item)) {
					failedRecordList.add(item);
				}
			}
			file.close();
			
			printFailedRecords();
	
			return true;
		
		} catch (Exception ex) {
			
			logger.log(Level.SEVERE, "Exception while handling company register records", ex);

			return false;
		}
	}

	public void printFailedRecords() {
		
		if (!this.failedRecordList.isEmpty())
			logger.log(Level.WARNING, "Import failed for these records (total: "+failedRecordList.size()+"), please fix and import again: ");

		Iterator iter = this.failedRecordList.iterator();
		
		while (iter.hasNext())
			logger.log(Level.WARNING, (String) iter.next());
	}

	public void setImportFile(ImportFile file) throws RemoteException {
		this.file = file;
	}

	public void setRootGroup(Group rootGroup) throws RemoteException {
	}
	
	private String getProperty(int columnIndex) {
		String value = null;

		if (this.valueList != null) {

			try {
				value = (String) this.valueList.get(columnIndex);
			} catch (RuntimeException e) {
				return null;
			}
			if (this.file.getEmptyValueString().equals(value)) {
				return null;
			}
			else {
				return value;
			}
		}
		else {
			return null;
		}
	}
	
	private boolean processRecord(String record) throws RemoteException,
	CreateException {
		try {
			this.valueList = this.file.getValuesFromRecordString(record);
			
		} catch (Exception e) {
			return false;
		}

		boolean success = storeCompanyRegisterEntry();

		this.valueList = null;

		return success;
	}
	
	private void importOperationForms(InputStream is) throws IOException, CreateException {
	    HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
	    
	    HSSFSheet sheet = wb.getSheetAt(4);
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
	
	private void importIndustryCodes(InputStream is) throws IOException, CreateException {
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
	}
	
	private void importUnregisterTypes(InputStream is) throws IOException, CreateException {
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
		
	
	private void checkCodesStartData() {
		InputStream is = null;
		try {
			is = new FileInputStream(getIWMainApplication().getBundlesRealPath()+"/"+CompanyConstants.IW_BUNDLE_IDENTIFIER+".bundle/resources/startdata/Codes.xls");
		} catch(Exception e) {
			logger.log(Level.SEVERE, "Exception while retrieving codes.xls resource from: "+getIWMainApplication().getBundle(CompanyConstants.IW_BUNDLE_IDENTIFIER).getResourcesRealPath(), e);
			return;
		}
		try {
			Collection operationForms = null;
			try {
				operationForms = ((OperationFormHome) IDOLookup.getHome(OperationForm.class)).findAllOperationForms();
				if(operationForms.isEmpty()) {
					logger.log(Level.ALL, "No Operation forms, importing new ones");
					importOperationForms(is);
				}
			} catch(FinderException re) {
				logger.log(Level.ALL, "No Operation forms, importing new ones");
				importOperationForms(is);
			}
			Collection unregisterTypes = null;
			try {
				unregisterTypes = ((UnregisterTypeHome) IDOLookup.getHome(UnregisterType.class)).findAllUnregisterTypes();
				if(unregisterTypes.isEmpty()) {
					logger.log(Level.ALL, "No unregister types, importing new ones");
					importUnregisterTypes(is);
				}
			} catch(FinderException re) {
				logger.log(Level.ALL, "No unregister types, importing new ones");
				importUnregisterTypes(is);
			}
			Collection industryCodes = null;
			try {
				industryCodes = ((IndustryCodeHome) IDOLookup.getHome(IndustryCode.class)).findAllIndustryCodes();
				if(industryCodes.isEmpty()) {
					logger.log(Level.ALL, "No industry codes, importing new ones");
					importIndustryCodes(is);
				}
			} catch(FinderException re) {
				logger.log(Level.ALL, "No industry codes, importing new ones");
				importIndustryCodes(is);
			}
		} catch(Exception re) {
			logger.log(Level.SEVERE, "Exception importing new initial data", re);
		}
	}
	
	private OperationFormHome getOperationFormHome() throws RemoteException {
		return (OperationFormHome) IDOLookup.getHome(OperationForm.class);
	}
	
	private UnregisterTypeHome getUnregisterTypeHome() throws RemoteException {
		return (UnregisterTypeHome) IDOLookup.getHome(UnregisterType.class);
	}
	
	private IndustryCodeHome getIndustryCodeHome() throws RemoteException {
		return (IndustryCodeHome) IDOLookup.getHome(IndustryCode.class);
	}
	
	protected boolean storeCompanyRegisterEntry() throws RemoteException, CreateException {

		String personal_id = getProperty(COLUMN_PERSONAL_ID);
		String commune = getProperty(COLUMN_COMMUNE);
		String postalCode = getProperty(COLUMN_POSTAL_CODE);
		String workingArea = getProperty(COLUMN_WORKING_AREA);
		String orderAreaForName = getProperty(COLUMN_ORDER_AREA_FOR_NAME);
		String name = getProperty(COLUMN_NAME);
		String address = getProperty(COLUMN_ADDRESS);
		String ceoId = getProperty(COLUMN_CEO_ID);
		String dateOfLastChange = getProperty(COLUMN_DATE_OF_LAST_CHANGE);
		String operationForm = getProperty(COLUMN_OPERATION_FORM);
		String vatNumber = getProperty(COLUMN_VAT_NUMBER);
		String legalAddress = getProperty(COLUMN_LEGAL_ADDRESS);
		String registerDate = getProperty(COLUMN_REGISTER_DATE);
		String operation = getProperty(COLUMN_OPERATION);
		String recipientPersonalId = getProperty(COLUMN_RECIPIENT_PERSONAL_ID);
		String recipientName = getProperty(COLUMN_RECIPIENT_NAME);
		String industryCode = getProperty(COLUMN_INDUSTRY_CODE);
		String unregistrationType = getProperty(COLUMN_TYPE_OF_UNREGISTRATION);
		String unregistrationDate = getProperty(COLUMN_DATE_OF_UNREGISTRATION);
		String banMarking = getProperty(COLUMN_BAN_MARKING);
		
		//System.out.println("vatNumber: -"+vatNumber+"-");
		
		/*System.out.println("---------entry----------------");
		System.out.println("personal_id:"+personal_id+"==");
		System.out.println("commune:"+commune+"==");
		System.out.println("postalCode:"+postalCode+"==");
		System.out.println("workingArea:"+workingArea+"==");
		System.out.println("orderAreaForName:"+orderAreaForName+"==");
		System.out.println("name:"+name+"==");
		System.out.println("address:"+address+"==");
		System.out.println("ceoId:"+ceoId+"==");
		System.out.println("dateOfLastChange:"+dateOfLastChange+"==");
		System.out.println("operationForm:"+operationForm+"==");
		System.out.println("vatNumber:"+vatNumber+"==");
		System.out.println("legalAddress:"+legalAddress+"==");
		System.out.println("registerDate:"+registerDate+"==");
		System.out.println("operation:"+operation+"==");
		System.out.println("recipientPersonalId:"+recipientPersonalId+"==");
		System.out.println("recipientName:"+recipientName+"==");
		System.out.println("industryCode:"+industryCode+"==");
		System.out.println("unregistrationType:"+unregistrationType+"==");
		System.out.println("unregistrationDate:"+unregistrationDate+"==");
		System.out.println("banMarking:"+banMarking+"==");
		System.out.println("-------------------------");
		
		
		*/
		
//		System.out.println("x: "+CompanyRegisterImportFile.getSQLDateFromStringFormat(dateOfLastChange, null, CompanyRegisterBusinessBean.RECORDS_DATE_FORMAT));
//		System.out.println("y: "+CompanyRegisterImportFile.getSQLDateFromStringFormat(registerDate, null, CompanyRegisterBusinessBean.RECORDS_DATE_FORMAT));
		
//		return true;
		return comp_reg_biz.updateEntry(personal_id, commune, postalCode, workingArea, orderAreaForName, name, address, ceoId, dateOfLastChange, operationForm, vatNumber, legalAddress, registerDate, operation, recipientPersonalId, recipientName, industryCode, unregistrationType, unregistrationDate, banMarking);
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("handling file");
			CompanyRegisterFileImportHandler handler = new CompanyRegisterFileImportHandlerBean();
			
			CompanyRegisterImportFile file = new CompanyRegisterImportFile();
			file.setFile(new File("/Users/civilis/Documents/work/company_import/fyrirtaeki-daglegt-ferli.dat"));
			handler.setImportFile(file);
			handler.handleRecords();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}