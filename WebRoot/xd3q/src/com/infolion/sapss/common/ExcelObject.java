package com.infolion.sapss.common;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.infolion.platform.core.BusinessException;

public class ExcelObject{

	HSSFWorkbook wb = null;

	HSSFSheet sh = null;

	HSSFRow r = null;

	HSSFCell c = null;

	int rowNum = 1 ;

	String[] titles = null;
	
	private ExcelObject(){}

	public ExcelObject(String[] titles) throws IllegalArgumentException, IllegalAccessException,
	InvocationTargetException, IOException{
		this.titles = titles;
		wb = new HSSFWorkbook();
		sh = wb.createSheet();
		wb.setSheetName(0, "Sheet1", HSSFWorkbook.ENCODING_UTF_16);
		r = sh.createRow(0);
		for (short cellnum = (short) 0; cellnum < titles.length; cellnum++) {
			c = r.createCell(cellnum);
			c.setEncoding((short)1);
			c.setCellValue(titles[cellnum]);
		}
	}

	public void addRow(String[] values)throws IllegalArgumentException, IllegalAccessException,
	InvocationTargetException, IOException{
		if(titles.length!=values.length) throw new BusinessException("数据不一致！");
		r = sh.createRow(rowNum++);
		for (short cellnum = (short) 0; cellnum < values.length; cellnum++) {
			c = r.createCell(cellnum);
			c.setEncoding((short)1);
			c.setCellValue(values[cellnum]);
		}
	}

	public void write(OutputStream os) throws IOException{
		wb.write(os);
	}
	
	private String emptyDeal(String value){
		if(value==null) return "";
		else return value;
	}

}