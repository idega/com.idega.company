package com.idega.company.companyregister.business;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.CreateException;
import com.idega.block.importer.business.ImportFileHandler;
import com.idega.block.importer.data.ImportFile;
import com.idega.business.IBOServiceBean;
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

	public boolean handleRecords() throws RemoteException {
		
		String item;
		
		try {
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