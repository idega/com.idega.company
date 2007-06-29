package com.idega.company.companyregister.business;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.idega.block.importer.data.GenericImportFile;
import com.idega.block.importer.data.ImportFile;
import com.idega.util.text.TextSoap;


public class CompanyRegisterImportFile extends GenericImportFile implements ImportFile {

	private static final int ENTRIES_COUNT = 32;
	private int offset = 0;
	
	public ArrayList getValuesFromRecordString(String recordString) {
		
		ArrayList values = new ArrayList();
		offset = 0;
		
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
	
	/**
	 * GREAT IMPORTANCE WARNING (really): you need to read records in the order of the file, because of the bug in national registry exported files
	 */
	public String getValueAtIndexFromRecordString(int index, String recordString) {
		try {
			switch(index) {
			
				case CompanyRegisterFileImportHandlerBean.COLUMN_UNIQUE_ID : return recordString.substring(0+offset,2+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED1 : return recordString.substring(2+offset,10+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_PERSONAL_ID : return recordString.substring(10+offset,20+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_COMMUNE : return recordString.substring(20+offset,24+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_POSTAL_CODE : return recordString.substring(24+offset,27+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_OLD_OPERATION_FORM : return recordString.substring(27+offset,28+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_WORKING_AREA : return recordString.substring(28+offset,32+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_ORDER_AREA_FOR_NAME : return recordString.substring(32+offset,63+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NAME : return recordString.substring(63+offset,94+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED2 : return recordString.substring(94+offset,125+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_ADDRESS : return recordString.substring(125+offset,146+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_CEO_ID : 

//					workaround - address length is not constant in the record files. (bug from national registry)
					
					String ceo_id = recordString.substring(146+offset,156+offset);
					
					if(ceo_id.trim().length() == 0)
						return "";
					
					int idx = TextSoap.getIndexOfFirstNumberInString(ceo_id);
					
					if(idx == 0 && !TextSoap.numericString(ceo_id))
						throw new IllegalArgumentException("Record string provided in bad format: "+recordString);
					
					if(idx == 0)
						return ceo_id;
					
					offset = offset+idx;
					
					ceo_id = recordString.substring(146+offset,156+offset);
					
					if(!TextSoap.numericString(ceo_id))
//						give up
						throw new IllegalArgumentException("Record string provided in bad format: "+recordString);
					
					return ceo_id;
					
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED3 : return recordString.substring(156+offset,164+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_DATE_OF_LAST_CHANGE : 
					
					String date_str = recordString.substring(164+offset,170+offset);
					
					if(!isDateCool(date_str))
						throw new IllegalArgumentException("Record string provided in bad format: "+recordString);
					
					return date_str;
					
				case CompanyRegisterFileImportHandlerBean.COLUMN_OPERATION_FORM : return recordString.substring(170+offset,172+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED4 : return recordString.substring(172+offset,175+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_VAT_NUMBER : return recordString.substring(175+offset,180+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_LEGAL_ADDRESS : return recordString.substring(180+offset,184+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_REGISTER_DATE : 
					
					date_str = recordString.substring(184+offset,192+offset);
					
					if(!isDateCool(date_str))
						throw new IllegalArgumentException("Record string provided in bad format: "+recordString);
					
					return date_str;
					
				case CompanyRegisterFileImportHandlerBean.COLUMN_OPERATION : return recordString.substring(192+offset,208+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED5 : return recordString.substring(208+offset,216+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_RECIPIENT_PERSONAL_ID : return recordString.substring(216+offset,226+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_RECIPIENT_NAME : return recordString.substring(226+offset,257+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED6 : return recordString.substring(257+offset,260+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_INDUSTRY_CODE : return recordString.substring(260+offset,265+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED7 : return recordString.substring(265+offset,273+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_E_FROM_FILE : return recordString.substring(273+offset,274+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_TYPE_OF_UNREGISTRATION : return recordString.substring(274+offset,278+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_DATE_OF_UNREGISTRATION : 
					
					date_str = recordString.substring(278+offset,286+offset);
					
					if(!isDateCool(date_str))
						throw new IllegalArgumentException("Record string provided in bad format: "+recordString);
					
					return date_str;
					
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED8 : return recordString.substring(286+offset,291+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_BAN_MARKING : return recordString.substring(291+offset,292+offset);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NOT_USED9 : return recordString.substring(292+offset,300+offset);
					
				default : return null;
			}
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	private boolean isDateCool(String date_str) {
		
		if(date_str == null || date_str.trim().length() == 0)
			return true;

		return getSQLDateFromStringFormat(date_str, null, CompanyRegisterBusinessBean.RECORDS_DATE_FORMAT) == null ? false : true;
	}
	
	public static Date getSQLDateFromStringFormat(String str, Logger logger, String RECORDS_DATE_FORMAT) {
		
		if(str == null || str.trim().length() == 0 || str.length() < RECORDS_DATE_FORMAT.length())
			return null;
		str = str.substring(0, RECORDS_DATE_FORMAT.length());
		
		try {
			SimpleDateFormat date_format = new SimpleDateFormat(RECORDS_DATE_FORMAT);
			return new Date(date_format.parse(str).getTime());
			
		} catch (Exception e) {
			
			if(logger != null)
				logger.log(Level.SEVERE, "Exception while parsing date string using format: "+RECORDS_DATE_FORMAT+" for a record string: "+str, e);
			
			return null;
		}
	}
}