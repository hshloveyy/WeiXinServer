package com.infolion.xdss3.cashFlow.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExportExel<T> {
	
	 protected Log log = LogFactory.getLog(getClass());
	 

	public void exprot(){
		
	}
	
	public InputStream createExcelFile(String[] cols,String[] colNames,List<T> list,List<?> dataList){
	       WritableWorkbook workbook;   
	       try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			workbook = Workbook.createWorkbook(baos);
			WritableSheet sheet = workbook.createSheet("详细信息", 0);
			this.col_row(sheet, cols, list, colNames,dataList);
			workbook.write();
			workbook.close();
			byte[] ba = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(ba);
			baos.close();
			return bais;
		}catch(Exception e){   
	        e.printStackTrace();   
	    }   
	    return null;
		
	}
	
	private WritableSheet col_row(WritableSheet sheet,String[] cols,List<T> list,String[] colNames,List<?> dataList) throws RowsExceededException, WriteException, IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException{
		Label label = null;
		jxl.write.Number labelNF = null;
		WritableFont fontNormal= new WritableFont(WritableFont.ARIAL,12,WritableFont.BOLD);
		WritableCellFormat cellFormat=new WritableCellFormat(fontNormal);
		for (int i = 0; i < colNames.length; i++) {
			label = new Label(i, 0, colNames[i],cellFormat);
			sheet.addCell(label);
		}
		Map<?,?> map=null;
		Object obj;
		if(dataList==null){
			Object obj2;
			for (int i = 0; i < list.size(); i++) {
				obj2 = list.get(i);		
				for (int j = 0; j < cols.length; j++) {
					Field field=obj2.getClass().getDeclaredField(cols[j]);					
					if(null !=field){
						field.setAccessible(true);
						obj =field.get(obj2);	
						if (obj instanceof BigDecimal){
							labelNF = new jxl.write.Number(j, i + 1, ((BigDecimal) obj).doubleValue());
							sheet.addCell(labelNF);
						}
						else{
							label = new Label(j, i + 1, (String) obj);
							sheet.addCell(label);
						}						
					}
				}				
			}
		}else{	
			for (int i = 0; i < dataList.size(); i++) {
				map = (Map<?,?>) dataList.get(i);
				for (int j = 0; j < cols.length; j++) {
					obj = map.get(cols[j]);
					if (obj instanceof BigDecimal){
						labelNF = new jxl.write.Number(j, i + 1, ((BigDecimal) obj).doubleValue());
						sheet.addCell(labelNF);
					}
					else{
						label = new Label(j, i + 1, (String) obj);
						sheet.addCell(label);
					}
				}
			}
		}
		return sheet;
	}
	
}
