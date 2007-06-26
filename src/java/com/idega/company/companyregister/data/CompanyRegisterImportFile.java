/*
 * $Id: CompanyRegisterImportFile.java,v 1.2 2007/06/26 15:26:04 civilis Exp $
 * Created on Nov 20, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.company.companyregister.data;

import java.util.ArrayList;
import com.idega.block.importer.data.GenericImportFile;
import com.idega.block.importer.data.ImportFile;
import com.idega.company.companyregister.business.CompanyRegisterFileImportHandlerBean;


public class CompanyRegisterImportFile extends GenericImportFile implements ImportFile {

	/**
	 * @see com.idega.block.importer.data.ImportFile#getValuesFromRecordString(java.lang.String)
	 */
	public ArrayList getValuesFromRecordString(String recordString) {
		ArrayList values = new ArrayList();
		for (int i = 0; i < 9; i++) {
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
				case CompanyRegisterFileImportHandlerBean.COLUMN_SSN : return recordString.substring(2,12);
				case CompanyRegisterFileImportHandlerBean.COLUMN_DATE_OF_DEATH : return recordString.substring(12, 20);
				case CompanyRegisterFileImportHandlerBean.COLUMN_NAME : return recordString.substring(20,51);
				case CompanyRegisterFileImportHandlerBean.COLUMN_STREET : return recordString.substring(51,72);
				case CompanyRegisterFileImportHandlerBean.COLUMN_COMMUNE : return recordString.substring(72,93);
				case CompanyRegisterFileImportHandlerBean.COLUMN_GENDER : return recordString.substring(93,94);
				case CompanyRegisterFileImportHandlerBean.COLUMN_MARITIAL_STATUS : return recordString.substring(94,95);
				case CompanyRegisterFileImportHandlerBean.COLUMN_SPOUSE_SSN : return recordString.substring(95,105);
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
