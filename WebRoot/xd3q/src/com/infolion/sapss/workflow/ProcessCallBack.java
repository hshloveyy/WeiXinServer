/*
 * @(#)ProcessCallBack.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-20
 *  描　述：创建
 */

package com.infolion.sapss.workflow;

/**
 * 
 * <pre>
 * 流程回调接口(目前适合流程结束回调)
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public interface ProcessCallBack {
	// 审批结果通过
	public static String EXAMINE_SUCCESSFUL = "pass";
	// 审批结果不通过
	public static String EXAMINE_FAILE = "faile";
	// 未经审批
	public static String NO_EXAMINE = "no_examine";

	/**
	 * 
	 * @param businessRecordId
	 *            业务记录id
	 * @param examineResult
	 *            审批结果
	 */
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo);
}
