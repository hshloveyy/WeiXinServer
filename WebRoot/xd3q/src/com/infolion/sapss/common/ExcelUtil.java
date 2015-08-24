package com.infolion.sapss.common;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.infolion.platform.core.BusinessException;

public class ExcelUtil {

	/**
	 * 
	 * @param datas //数据集
	 * @param titles	//标题
	 * @param fieldNames //字段
	 * @param os //输出流，tmd别忘记关闭
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static void parseData(final Collection datas, final String[] titles,
			final String[] fieldNames, OutputStream os)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, IOException {
		if (datas == null || datas.size() == 0 || titles == null
				|| titles.length == 0 || fieldNames == null
				|| fieldNames.length == 0 || os == null) {
			return;
		}
		Object data = datas.iterator().next();

		List validMethods = new ArrayList();

		parseMethod(data, fieldNames, validMethods);

		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sh = wb.createSheet();

		wb.setSheetName(0, "PrintSheet", HSSFWorkbook.ENCODING_UTF_16);

		HSSFRow r = null;

		HSSFCell c = null;

		r = sh.createRow(0);

		for (short cellnum = (short) 0; cellnum < titles.length; cellnum++) {
			c = r.createCell(cellnum);

			byte[] bs = titles[cellnum].getBytes("UTF-8");

			String newTitle = new String(bs, "UTF-8");

			c.setCellValue(newTitle);
		}
		short rownum = (short) 1;
		for (Iterator itr = datas.iterator(); itr.hasNext()
				&& rownum <= datas.size(); rownum++) {
			Object obj = itr.next();
			r = sh.createRow(rownum);

			for (short cellnum = (short) 0; cellnum < validMethods.size(); cellnum++) {
				Method method = (Method) validMethods.get(cellnum);
				Object retVal = method.invoke(obj, new Object[] {});
				if (retVal == null) {
					retVal = new String("");
				}
				c = r.createCell(cellnum);
				String temp = new String(retVal.toString().getBytes("UTF-8"),
						"UTF-8");
				c.setCellValue(temp);
			}
		}

		wb.write(os);
	}
	
	/**
	 * 
	 * @param datas //数据集
	 * @param titles	//标题
	 * @param fieldNames //字段
	 * @param os //输出流，tmd别忘记关闭
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static void parseDataWithATitle(final Collection datas, final String[] titles,
			final String[] fieldNames, OutputStream os,final String title)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, IOException {
		if (titles == null|| titles.length == 0 || fieldNames == null
				|| fieldNames.length == 0 || os == null) {
			return;
		}
		
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sh = wb.createSheet();

		wb.setSheetName(0, "PrintSheet", HSSFWorkbook.ENCODING_UTF_16);

		HSSFRow r = null;

		HSSFCell c = null;
		//创建标题
		r = sh.createRow(0);
		c = r.createCell((short)4);
		byte[] titlebs = title.getBytes("UTF-8");
		String tempTitle = new String(titlebs, "UTF-8");
		c.setCellValue(tempTitle);
		//
		
		List validMethods = new ArrayList();

		if(datas!=null&&datas.size()>0){
			Object data = datas.iterator().next();
			parseMethod(data, fieldNames, validMethods);
		}
		
		r = sh.createRow(1);
		for (short cellnum = (short) 0; cellnum < titles.length; cellnum++) {
			c = r.createCell(cellnum);

			byte[] bs = titles[cellnum].getBytes("UTF-8");

			String newTitle = new String(bs, "UTF-8");

			c.setCellValue(newTitle);
		}
		short rownum = (short) 2;
		for (Iterator itr = datas.iterator(); itr.hasNext()
				&& rownum <= datas.size()+1; rownum++) {
			Object obj = itr.next();
			r = sh.createRow(rownum);

			for (short cellnum = (short) 0; cellnum < validMethods.size(); cellnum++) {
				Method method = (Method) validMethods.get(cellnum);
				Object retVal = method.invoke(obj, new Object[] {});
				if (retVal == null) {
					retVal = new String("");
				}
				c = r.createCell(cellnum);
				String temp = new String(retVal.toString().getBytes("UTF-8"),
						"UTF-8");
				c.setCellValue(temp);
			}
		}

		wb.write(os);
	}

	@SuppressWarnings("unchecked")
	protected static void parseMethod(final Object obj,
			final String[] fieldNames, List retVal) {
		String[] newFieldNames = new String[fieldNames.length];

		for (int i = 0; i < fieldNames.length; i++) {
			char[] cs = fieldNames[i].toCharArray();
			cs[0] = Character.toUpperCase(cs[0]);
			newFieldNames[i] = "get" + (new String(cs));
		}

		Method[] methods = obj.getClass().getMethods();
		Map ms = new HashMap();
		for (int i = 0; i < methods.length; i++) {
			ms.put(methods[i].getName(), methods[i]);
		}

		for (int i = 0; i < newFieldNames.length; i++) {
			retVal.add(ms.get(newFieldNames[i]));
		}
	}

}
