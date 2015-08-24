/*
 * @(#)MasterDataRfcTable.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-5-26
 *  描　述：创建
 */

package com.infolion.xdss3.masterData.domain;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 主数据RFC返回的table抽象。
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class MasterDataRfcTable
{
	/**
	 * 新增的数据集合
	 */
	private List<Map<String, String>> addDataList;

	/**
	 * 修改的数据集合
	 */
	private List<Map<String, String>> modifyDataList;
	
	/**
	 * 新增的明细集合（开票，发票校验，未清客户，供应商数据用）
	 */
	private List<Map<String, String>> addDetailDataList;

	/**
	 * 输出table, 未清客户供应商数据用凭证号
	 */
	private  List<Map<String, String>> outputTableDataList;
	
	/**
	 * 错误信息
	 */
	private List<Map<String, String>> errorMsgs;

	/**
	 * 同步时间
	 */
	private String syncDate;

	/**
	 * 标志是否实时调用，同步未清数据用
	 */
	private boolean isRealtimeCall;
	
	/**
	 * 标志是否第一次同步，同步未清数据产生代办用
	 */
	private boolean isFirstTimeSyn;
	
	/**
	 * 同步时间
	 * 
	 * @return the syncDate
	 */
	public String getSyncDate()
	{
		return syncDate;
	}

	/**
	 * 同步时间
	 * 
	 * @param syncDate
	 *            the syncDate to set
	 */
	public void setSyncDate(String syncDate)
	{
		this.syncDate = syncDate;
	}

	/**
	 * 新增的数据集合
	 * 
	 * @return the addDataList
	 */
	public List<Map<String, String>> getAddDataList()
	{
		return addDataList;
	}

	/**
	 * 新增的数据集合
	 * 
	 * @param addDataList
	 *            the addDataList to set
	 */
	public void setAddDataList(List<Map<String, String>> addDataList)
	{
		this.addDataList = addDataList;
	}

	/**
	 * 修改的数据集合
	 * 
	 * @return the modifyDataList
	 */
	public List<Map<String, String>> getModifyDataList()
	{
		return modifyDataList;
	}

	/**
	 * 修改的数据集合
	 * 
	 * @param modifyDataList
	 *            the modifyDataList to set
	 */
	public void setModifyDataList(List<Map<String, String>> modifyDataList)
	{
		this.modifyDataList = modifyDataList;
	}

	/**
	 * 错误信息
	 * 
	 * @return the errorMsgs
	 */
	public List<Map<String, String>> getErrorMsgs()
	{
		return errorMsgs;
	}

	/**
	 * 错误信息
	 * 
	 * @param errorMsgs
	 *            the errorMsgs to set
	 */
	public void setErrorMsgs(List<Map<String, String>> errorMsgs)
	{
		this.errorMsgs = errorMsgs;
	}

	/**
	 * @return the addDetailDataList
	 */
	public List<Map<String, String>> getAddDetailDataList() {
		return addDetailDataList;
	}

	/**
	 * @param addDetailDataList the addDetailDataList to set
	 */
	public void setAddDetailDataList(List<Map<String, String>> addDetailDataList) {
		this.addDetailDataList = addDetailDataList;
	}

	/**
	 * @param outputTableDataList the outputTableDataList to set
	 */
	public void setOutputTableDataList(List<Map<String, String>> outputTableDataList) {
		this.outputTableDataList = outputTableDataList;
	}

	/**
	 * @return the outputTableDataList
	 */
	public List<Map<String, String>> getOutputTableDataList() {
		return outputTableDataList;
	}

	/**
	 * @return the isRealtimeCall
	 */
	public boolean isRealtimeCall() {
		return isRealtimeCall;
	}

	/**
	 * @param isRealtimeCall the isRealtimeCall to set
	 */
	public void setRealtimeCall(boolean isRealtimeCall) {
		this.isRealtimeCall = isRealtimeCall;
	}

	/**
	 * @return the isFirstTimeSyn
	 */
	public boolean isFirstTimeSyn() {
		return isFirstTimeSyn;
	}

	/**
	 * @param isFirstTimeSyn the isFirstTimeSyn to set
	 */
	public void setFirstTimeSyn(boolean isFirstTimeSyn) {
		this.isFirstTimeSyn = isFirstTimeSyn;
	}
	
	

}
