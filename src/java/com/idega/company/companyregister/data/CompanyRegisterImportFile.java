package com.idega.company.companyregister.data;

import com.idega.company.companyregister.business.CompanyRegisterFileImportHandlerBean;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import com.idega.block.importer.data.GenericImportFile;
import com.idega.block.importer.data.ImportFile;

/**
 * @author palli
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CompanyRegisterImportFile extends GenericImportFile implements ImportFile {
	public CompanyRegisterImportFile() {
		super();
		setRecordDilimiter("\n");
	}
	
	public CompanyRegisterImportFile(File file) {
		this();
		setFile(file);
	}
	
	/**
	 * @see com.idega.block.importer.data.ImportFile#getRecords()
	 */
	public Collection getRecords() {
		return super.getRecords();
	}
	
	/**
	 * @see com.idega.block.importer.data.ImportFile#getValuesFromRecordString(java.lang.String)
	 */
	public ArrayList getValuesFromRecordString(String recordString) {
		ArrayList values = new ArrayList();
		for (int i = 0; i < 20; i++) {
			String value = getValueAtIndexFromRecordString(i,recordString);
			if (value != null) {
				values.add(value);
			}
			else {
				values.add("");
			}
		}
		
		return values;
	}
		
	/* (non-Javadoc)
	 * @see com.idega.block.importer.data.ImportFile#getValueAtIndexFromRecordString(int, java.lang.String)
	 */
	public String getValueAtIndexFromRecordString(int index, String recordString) {
		try {
			switch(index) {
				case CompanyRegisterFileImportHandlerBean.COLUMN_SYMBOL : return recordString.substring(0,2);
				case CompanyRegisterFileImportHandlerBean.COLUMN_OLD_ID : return recordString.substring(2,10);
				case CompanyRegisterFileImportHandlerBean.COLUMN_SSN : return recordString.substring(10,20);
				case CompanyRegisterFileImportHandlerBean.COLUMN_FAMILY_ID : return recordString.substring(20,30);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NAME : return recordString.substring(30,61);
				case CompanyRegisterFileImportHandlerBean.COLUMN_COMMUNE : return recordString.substring(61,65);
				case CompanyRegisterFileImportHandlerBean.COLUMN_STREET : return recordString.substring(65,69);
				case CompanyRegisterFileImportHandlerBean.COLUMN_BUILDING : return recordString.substring(69,73);
				case CompanyRegisterFileImportHandlerBean.COLUMN_FLOOR : return recordString.substring(73,76);
				case CompanyRegisterFileImportHandlerBean.COLUMN_SEX : return recordString.substring(76,77);
				case CompanyRegisterFileImportHandlerBean.COLUMN_MARITIAL_STATUS : return recordString.substring(77,78);
				case CompanyRegisterFileImportHandlerBean.COLUMN_EMPTY : return recordString.substring(78,80);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NO_MAIL : return recordString.substring(80,81);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NATIONALITY : return recordString.substring(81,83);
				case CompanyRegisterFileImportHandlerBean.COLUMN_PLACE_OF_BIRTH : return recordString.substring(83,87);
				case CompanyRegisterFileImportHandlerBean.COLUMN_SPOUSE_SSN : return recordString.substring(87,97);
				case CompanyRegisterFileImportHandlerBean.COLUMN_STATUS : return recordString.substring(97,101);
				case CompanyRegisterFileImportHandlerBean.COLUMN_PARISH : return recordString.substring(101,104);
				case CompanyRegisterFileImportHandlerBean.COLUMN_PO : return recordString.substring(104,107);
				case CompanyRegisterFileImportHandlerBean.COLUMN_ADDRESS : return recordString.substring(107,128);
				default : return null;
			}
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
}