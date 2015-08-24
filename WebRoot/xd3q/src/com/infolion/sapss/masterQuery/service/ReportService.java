/*
 * @(#)ReportService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-10-23
 *  描　述：创建
 */

package com.infolion.sapss.masterQuery.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.masterQuery.dao.ReportDao;
import com.infolion.sapss.masterQuery.web.ReportUtil;

@Service
public class ReportService extends BaseJdbcService{
	@Autowired
	private ReportDao reportDao;

	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
	}
	/**
	 * 
	 * @return
	 */
	public List rtnShipGood(Map<?,?> map){
		List<Map> saleContractInfo = this.reportDao.getSaleContract(map);
		Map saleInfo = (Map)saleContractInfo.get(0);

		List<Map> shipInfo = this.reportDao.getShip(map);
		List<Map> rtn = new ArrayList();
		double total=0;
		double amount=0;
		double amountContrat=0;
//		BigDecimal m1 ;
//		BigDecimal m2 ;
//		BigDecimal s1 ;
		
		for(Map<String,Object> ship:shipInfo){
			ship.putAll(saleInfo);
//			m1 = (BigDecimal)ship.get("PRICE");
//			m2 = (BigDecimal)ship.get("QUANTITY");
//			s1 = new BigDecimal((String)ship.get("EKPO_PEINH"));
//			total += m1.multiply(m2).divide(s1).doubleValue();
			total +=((BigDecimal)ship.get("TOTAL")).doubleValue();
			amount +=((BigDecimal)ship.get("QUANTITY")).doubleValue();
			amountContrat +=((BigDecimal)ship.get("sale_row_quantity")).doubleValue();
			rtn.add(ship);
		}
		if(shipInfo==null || shipInfo.size()==0){
			rtn.addAll(saleContractInfo);
		}
		Map<String,String> addRow= new HashMap();
		BigDecimal t = new BigDecimal(total);
		t = t.divide(new BigDecimal(1),2,RoundingMode.HALF_UP);
		BigDecimal a = new BigDecimal(amount);
		a = a.divide(new BigDecimal(1),3,RoundingMode.HALF_UP);
		addRow.put("TOTAL","计:"+t.toString()+"");
		addRow.put("QUANTITY","计:"+a.toString());
		addRow.put("SALE_ROW_QUANTITY","计:"+amountContrat);
		rtn.add(rtn.size(), addRow);
		return rtn;
	}
	/**
	 * 
	 * @param purchaseId
	 * @return
	 */
	public List rtnExportPayMoney(Map<String,String> map) {
		List exportHeader = this.reportDao.getPayMoney(map);
		Map header=null;
		List res = new ArrayList();
		for(int i=0;i<exportHeader.size();i++){
			header = (Map)exportHeader.get(i);
			header.put("INNER_PAY_DATE", this.reportDao.getMaxDate(header.get("contract_purchase_id")));
			header.put("PAYED_VALUE", this.reportDao.getSumValue(header.get("contract_purchase_id")));
			res.add(header);
		}
		return res;
	}
	/**
	 * 
	 * @param map
	 * @return
	 */
	public List rtnExportShipGoods(Map map) {
		return this.reportDao.getExportShipGoods(map);
	}
	/**
	 * 
	 * @param map
	 * @return
	 */
	public List rtnCreditPicklist(Map map) {
		return this.reportDao.getCreditPicklist(map);
	}
	/**
	 * 
	 * @param map
	 * @return
	 */
	public List rtnDrawback(Map map) {
		return this.reportDao.getDrawback(map);
	}
	/**
	 * 
	 * @param map
	 * @throws IOException 
	 */
	public InputStream exportToExcel(String saleSql,String[] columns,String path,String[] colsName) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		wb.setSheetName(0, "详细信息", HSSFWorkbook.ENCODING_UTF_16);
        HSSFRow row=null;
        HSSFCell cell=null;
        List saleList = this.reportDao.getExportData(saleSql);
		row = sheet.createRow((short)(0));
		for(int i=0;i<columns.length;i++){
	        cell=row.createCell((short)(i));
	        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
	        cell.setCellValue(colsName[i]);
		}
		Map map=null;
		Object obj=null;
       for(int i=0;i<saleList.size();i++){
    	   row = sheet.createRow((short)(i+1));
    	   map = (Map)saleList.get(i);
    	   for(int j=0;j<columns.length;j++){
    		   cell=row.createCell((short)(j));
    		   obj = map.get(columns[j]);
    		   if(obj instanceof BigDecimal)
    			   cell.setCellValue(((BigDecimal)obj).doubleValue());
    		   else{
    			   cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
    			   cell.setCellValue((String)obj);
    		   }
    	   }
       }
    
       ByteArrayOutputStream baos = new ByteArrayOutputStream();  
       wb.write(baos);
       byte[] ba = baos.toByteArray();
       ByteArrayInputStream bais = new ByteArrayInputStream(ba);
       return bais;
       
//        FileOutputStream fileOut = new FileOutputStream(path+"/export.xls");
//        
//        wb.write(fileOut);
//        fileOut.close(); 
//        return null;
	}
	/**
	 * 
	 * @param path
	 * @param sheetName
	 * @param dataTitles
	 */
	public InputStream createExcelFile(String[] cols,String[] colNames,String saleSql,String[] colp,String[] colNamep,String purchaseSql){   
	       WritableWorkbook workbook;   
	       try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			workbook = Workbook.createWorkbook(baos);
			WritableSheet sheet = workbook.createSheet("销售", 0);
			this.col_row(sheet, cols, saleSql, colNames,null);
			sheet = workbook.createSheet("采购", 1);
			this.col_row(sheet, colp, purchaseSql, colNamep,null);
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
	/**
	 * 
	 * @param cols
	 * @param colNames
	 * @param saleSql
	 * @return
	 */
	public InputStream createExcelFile(String[] cols,String[] colNames,String sql,List<?> dataList){
	       WritableWorkbook workbook;   
	       try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			workbook = Workbook.createWorkbook(baos);
			WritableSheet sheet = workbook.createSheet("详细信息", 0);
			this.col_row(sheet, cols, sql, colNames,dataList);
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
	/**
	 * 
	 * @param sheet
	 * @param cols
	 * @param sql
	 * @param colNames
	 * @return
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private WritableSheet col_row(WritableSheet sheet,String[] cols,String sql,String[] colNames,List<?> dataList) throws RowsExceededException, WriteException{
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
		if(dataList==null)
		   dataList = this.reportDao.getExportData(sql);
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
		return sheet;
	}
	/**
	 * 
	 * @param map
	 * @param cols
	 * @return
	 */
	public List<?> rtnDetailData(String detailName,Map map) {
		return this.rtnDetailData(false, detailName, map);
	}
	/**
	 * 
	 * @param map
	 * @param cols
	 * @return
	 */
	public List<?> rtnDetailData(boolean isTable,String detailName,Map map) {
		String cols = ReportUtil.getProperty(detailName);
		return this.reportDao.getDetailData(isTable,detailName, map, cols);
	}
	
}
