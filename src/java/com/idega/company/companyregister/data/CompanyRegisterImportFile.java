package com.idega.company.companyregister.data;

import java.util.ArrayList;

import com.idega.block.importer.data.GenericImportFile;
import com.idega.block.importer.data.ImportFile;
import com.idega.company.companyregister.business.CompanyRegisterFileImportHandlerBean;


public class CompanyRegisterImportFile extends GenericImportFile implements ImportFile {

	private static final int ENTRIES_COUNT = 32;
	
	public ArrayList getValuesFromRecordString(String recordString) {
		ArrayList values = new ArrayList();
		for (int i = 0; i < ENTRIES_COUNT; i++) {
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
	
	public String getValueAtIndexFromRecordString(int index, String recordString) {
		try {
			switch(index) {
			
				case CompanyRegisterFileImportHandlerBean.COLUMN_UNIQUE_ID : return recordString.substring(0,2);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED1 : return recordString.substring(2,10);
				case CompanyRegisterFileImportHandlerBean.COLUMN_PERSONAL_ID : return recordString.substring(10,20);
				case CompanyRegisterFileImportHandlerBean.COLUMN_COMMUNE : return recordString.substring(20,24);
				case CompanyRegisterFileImportHandlerBean.COLUMN_POSTAL_CODE : return recordString.substring(24,27);
				case CompanyRegisterFileImportHandlerBean.COLUMN_OLD_OPERATION_FORM : return recordString.substring(27,28);
				case CompanyRegisterFileImportHandlerBean.COLUMN_WORKING_AREA : return recordString.substring(28,32);
				case CompanyRegisterFileImportHandlerBean.COLUMN_ORDER_AREA_FOR_NAME : return recordString.substring(32,63);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NAME : return recordString.substring(63,94);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED2 : return recordString.substring(94,125);
				case CompanyRegisterFileImportHandlerBean.COLUMN_ADDRESS : return recordString.substring(125,146);
				case CompanyRegisterFileImportHandlerBean.COLUMN_CEO_ID : return recordString.substring(146,156);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED3 : return recordString.substring(156,164);
				case CompanyRegisterFileImportHandlerBean.COLUMN_DATE_OF_LAST_CHANGE : return recordString.substring(164,170);
				case CompanyRegisterFileImportHandlerBean.COLUMN_OPERATION_FORM : return recordString.substring(170,172);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED4 : return recordString.substring(172,175);
				case CompanyRegisterFileImportHandlerBean.COLUMN_VAT_NUMBER : return recordString.substring(175,180);
				case CompanyRegisterFileImportHandlerBean.COLUMN_LEGAL_ADDRESS : return recordString.substring(180,184);
				case CompanyRegisterFileImportHandlerBean.COLUMN_REGISTER_DATE : return recordString.substring(184,192);
				case CompanyRegisterFileImportHandlerBean.COLUMN_OPERATION : return recordString.substring(192,208);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED5 : return recordString.substring(208,216);
				case CompanyRegisterFileImportHandlerBean.COLUMN_RECIPIENT_PERSONAL_ID : return recordString.substring(216,226);
				case CompanyRegisterFileImportHandlerBean.COLUMN_RECIPIENT_NAME : return recordString.substring(226,257);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED6 : return recordString.substring(257,260);
				case CompanyRegisterFileImportHandlerBean.COLUMN_INDUSTRY_CODE : return recordString.substring(260,265);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED7 : return recordString.substring(265,273);
				case CompanyRegisterFileImportHandlerBean.COLUMN_E_FROM_FILE : return recordString.substring(273,274);
				case CompanyRegisterFileImportHandlerBean.COLUMN_TYPE_OF_UNREGISTRATION : return recordString.substring(274,278);
				case CompanyRegisterFileImportHandlerBean.COLUMN_DATE_OF_UNREGISTRATION : return recordString.substring(278,286);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED8 : return recordString.substring(286,291);
				case CompanyRegisterFileImportHandlerBean.COLUMN_BAN_MARKING : return recordString.substring(291,292);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED9 : return recordString.substring(292,300);
					
				default : return null;
			}
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

	public String getEncoding() {
		return "ISO-8859-1";
	}
}