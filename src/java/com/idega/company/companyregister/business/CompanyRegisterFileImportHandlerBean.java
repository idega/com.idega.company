package com.idega.company.companyregister.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.NumberFormat;
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
import org.apache.poi.ss.usermodel.Row;

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
import com.idega.util.CoreConstants;
import com.idega.util.IOUtil;
import com.idega.util.IWTimestamp;
import com.idega.util.ListUtil;
import com.idega.util.Timer;

public class CompanyRegisterFileImportHandlerBean extends IBOServiceBean implements CompanyRegisterFileImportHandler, ImportFileHandler {

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

	private Logger LOGGER = Logger.getLogger(CompanyRegisterFileImportHandlerBean.class.getName());

	private List<String> failedRecordList = new ArrayList<String>(), successRecords = new ArrayList<String>();
	private List<String> valueList;

	private CompanyRegisterBusiness comp_reg_biz;

	private final static int BYTES_PER_RECORD = 301;

	private NumberFormat precentNF = NumberFormat.getPercentInstance();

	private NumberFormat twoDigits = NumberFormat.getNumberInstance();

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getFailedRecords() throws RemoteException {
		return failedRecordList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSuccessRecords() throws RemoteException {
		return successRecords;
	}

	@Override
	public boolean handleRecords() throws RemoteException {
		String item = null;

		Timer clock = new Timer();
		clock.start();
		try {
			checkCodesStartData();

			comp_reg_biz = getServiceInstance(CompanyRegisterBusiness.class);

			int count = 0;
			long totalBytes = file.getFile().length();
			long totalRecords = totalBytes / BYTES_PER_RECORD;
			if (totalRecords == 0) {
				Collection<String> allRecords = file.getRecords();
				totalRecords = ListUtil.isEmpty(allRecords) ? 1 : allRecords.size();
			}
			long beginTime = System.currentTimeMillis();
			long lastTimeCheck = beginTime;
			long averageTimePerUser100 = 0;
			long timeLeft100 = 0;
			long estimatedTimeFinished100 = beginTime;
			IWTimestamp stamp;
			double progress = 0;
			int intervalBetweenOutput = 100;
			LOGGER.info("CompRegImport processing RECORD [0] time: " + IWTimestamp.getTimestampRightNow().toString());
			while (!(item = (String) file.getNextRecord()).equals(CoreConstants.EMPTY)) {
				count++;

				if (processRecord(item)) {
					successRecords.add(item);
				} else {
					failedRecordList.add(item);
				}

				if ((count % intervalBetweenOutput) == 0) {
					averageTimePerUser100 = (System.currentTimeMillis() - lastTimeCheck) / intervalBetweenOutput;
					lastTimeCheck = System.currentTimeMillis();
					timeLeft100 = averageTimePerUser100 * (totalRecords - count);
					estimatedTimeFinished100 = System.currentTimeMillis() + timeLeft100;
					progress = ((double) count) / ((double) totalRecords);
					LOGGER.info("CompRegImport " + IWTimestamp.getTimestampRightNow().toString() + ", processing RECORD [" + count + " / " + totalRecords + "]");
					stamp = new IWTimestamp(estimatedTimeFinished100);
					LOGGER.info(" | " + this.precentNF.format(progress) + " done, guestimated time left of PHASE 1 : " + getTimeString(timeLeft100) + "  finish at " + stamp.getTime().toString());
				}
			}
			file.close();
			LOGGER.info("CompRegImport processed RECORD [" + count + "] time: " + IWTimestamp.getTimestampRightNow().toString());
			clock.stop();
			long msTime = clock.getTime();
			long secTime = msTime / 1000;
			LOGGER.info("Time to handleRecords: " + msTime + " ms  OR " + secTime + " s, averaging " + (count > 0 ? (msTime / count) : 0) + "ms per record");

			printFailedRecords();

			return true;
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Exception while handling company register records from " + file + (file == null ? CoreConstants.EMPTY : file.getFile()), ex);
			return false;
		}
	}

	public String getTimeString(long time) {
		long t = time;
		int milli = (int) (t % 1000);
		t = (t - milli) / 1000;
		int second = (int) (t % 60);
		t = (t - second) / 60;
		int minut = (int) (t) % 60;
		int hour = (int) ((t - minut) / 60);
		return this.twoDigits.format(hour) + ":" + this.twoDigits.format(minut) + ":" + this.twoDigits.format(second) + "." + milli;
	}

	@Override
	public void printFailedRecords() {
		if (!ListUtil.isEmpty(failedRecordList)) {
			LOGGER.log(Level.WARNING, "Import failed for these records (total: " + failedRecordList.size() + "), please fix and import again: ");
		}

		Iterator<String> iter = this.failedRecordList.iterator();
		while (iter.hasNext()) {
			LOGGER.log(Level.WARNING, iter.next());
		}
	}

	@Override
	public void setImportFile(ImportFile file) throws RemoteException {
		this.file = file;
	}

	@Override
	public void setRootGroup(Group rootGroup) throws RemoteException {
	}

	private String getProperty(int columnIndex) {
		String value = null;

		if (this.valueList != null) {

			try {
				value = this.valueList.get(columnIndex);
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

	private boolean processRecord(String record) throws RemoteException, CreateException {
		try {
			this.valueList = this.file.getValuesFromRecordString(record);
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Failed to get values from record: " + record, e);
			return false;
		}

		boolean success = false;
		try {
			success = storeCompanyRegisterEntry();
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Failed to process record: '" + record + "'", e);
		}
		if (success) {
			getLogger().info("Successfully processed company " + getProperty(COLUMN_PERSONAL_ID));
		} else {
			getLogger().warning("Failed to process record: " + record);
		}

		this.valueList = null;

		return success;
	}

	private void importOperationForms(InputStream is) throws IOException, CreateException {
	    HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));

	    HSSFSheet sheet = wb.getSheetAt(4);
	    if (sheet != null) {
	    	Iterator<Row> it = sheet.rowIterator();
	    	if (it.hasNext()) {
	    		it.next();
	    	}
	    	while (it.hasNext()) {
	    		HSSFRow row = (HSSFRow) it.next();
	    		if(row != null) {
	    			HSSFCell codeCell = row.getCell(0);
	    			if(codeCell != null) {
	    				OperationForm operationForm = getOperationFormHome().create();
	    				operationForm.setCode(codeCell.getStringCellValue());

	    				HSSFCell descriptionCell = row.getCell(1);
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
	    	Iterator<Row> it = sheet.rowIterator();
	    	if (it.hasNext()) {
	    		it.next();
	    	}
	    	while (it.hasNext()) {
	    		HSSFRow row = (HSSFRow) it.next();
	    		if (row != null) {
	    			HSSFCell codeCell = row.getCell(0);
	    			if (codeCell != null) {
	    				IndustryCode industryCode = getIndustryCodeHome().create();
	    				industryCode.setISATCode(codeCell.getStringCellValue());

	    				HSSFCell descriptionCell = row.getCell(1);
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
	    	Iterator<Row> it = sheet.rowIterator();
	    	if(it.hasNext()) {
	    		it.next();
	    	}
	    	while (it.hasNext()) {
	    		HSSFRow row = (HSSFRow) it.next();
	    		if (row != null) {
	    			HSSFCell codeCell = row.getCell(0);
	    			if (codeCell != null) {
	    				UnregisterType unregisterType = getUnregisterTypeHome().create();
	    				unregisterType.setCode(codeCell.getStringCellValue());

	    				HSSFCell descriptionCell = row.getCell(1);
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
		String filePath = "resources/startdata/Codes.xls";
		try {
			is = IOUtil.getStreamFromJar(CompanyConstants.IW_BUNDLE_IDENTIFIER, filePath);
		} catch (Exception e) {}
		if (is == null) {
			try {
				is = new FileInputStream(getIWMainApplication().getBundlesRealPath() + File.separator + CompanyConstants.IW_BUNDLE_IDENTIFIER + ".bundle" + File.separator + filePath);
			} catch (Exception e) {}
		}
		if (is == null) {
			LOGGER.warning("Failed to retrieve Codes.xls (" + filePath + ") resource from: " + getIWMainApplication().getBundle(CompanyConstants.IW_BUNDLE_IDENTIFIER).getResourcesRealPath());
			return;
		}

		try {
			Collection<?> operationForms = null;
			try {
				operationForms = ((OperationFormHome) IDOLookup.getHome(OperationForm.class)).findAllOperationForms();
				if (operationForms.isEmpty()) {
					LOGGER.log(Level.ALL, "No Operation forms, importing new ones");
					importOperationForms(is);
				}
			} catch (FinderException re) {
				LOGGER.log(Level.ALL, "No Operation forms, importing new ones");
				importOperationForms(is);
			}

			Collection<?> unregisterTypes = null;
			try {
				unregisterTypes = ((UnregisterTypeHome) IDOLookup.getHome(UnregisterType.class)).findAllUnregisterTypes();
				if (unregisterTypes.isEmpty()) {
					LOGGER.log(Level.ALL, "No unregister types, importing new ones");
					importUnregisterTypes(is);
				}
			} catch (FinderException re) {
				LOGGER.log(Level.ALL, "No unregister types, importing new ones");
				importUnregisterTypes(is);
			}

			Collection<?> industryCodes = null;
			try {
				industryCodes = ((IndustryCodeHome) IDOLookup.getHome(IndustryCode.class)).findAllIndustryCodes();
				if(industryCodes.isEmpty()) {
					LOGGER.log(Level.ALL, "No industry codes, importing new ones");
					importIndustryCodes(is);
				}
			} catch (FinderException re) {
				LOGGER.log(Level.ALL, "No industry codes, importing new ones");
				importIndustryCodes(is);
			}
		} catch (Exception re) {
			LOGGER.log(Level.SEVERE, "Exception importing new initial data", re);
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