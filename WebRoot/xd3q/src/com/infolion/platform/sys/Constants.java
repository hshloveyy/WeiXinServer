/*
 * @(#)Constons.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-1
 *  描　述：创建
 */

package com.infolion.platform.sys;

/**
 * <pre>
 * XDSS旧系统。
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
public class Constants
{
	/**
	 * 单层数据字典名称
	 */
	public final static String DICT_TABLE_SIGNAL_LAYER_DEFAULT_NAME = "signal_layer_code_dict";
	/**
	 * 多层数据字典名称
	 */
	public final static String DICT_TABLE_MUTIL_LAYER_DEFAULT_NAME = "mutil_layer_code_dict";
	/**
	 * 默认字典表分组名
	 */
	public static final String DICT_TABLE_DEFAULT_GROUP_NAME = "_default_group".toUpperCase();
	/**
	 * 用户上下文存储名称
	 */
	public static final String USER_CONTEXT_NAME = "fisc_xdss_user_context";
	/**
	 * 工作流节点被授权用户
	 */
	public static final String WORKFLOW_ISSUE_USER = "_workflow_issue_user";
	/**
	 * 工作流用户关键字段值
	 */
	public static final String WORKFLOW_USER_KEY_VALUE = "_workflow_user_key_value";
	/**
	 * 工作流扩展流程实例字段值
	 */
	public static final String WORKFLOW_COMMON_PROCESS_ID = "_workflow_common_process_id";

	/**
	 * 工作流子扩展流程实例字段值
	 */
	public static final String WORKFLOW_SUB_COMMON_PROCESS_ID = "_workflow_sub_common_process_id";
	/**
	 * 工作流审批人字段值
	 */
	public static final String WORKFLOW_EXAMINE_PERSON = "_workflow_examine_person";
	/**
	 * 工作流审批意见字段值
	 */
	public static final String WORKFLOW_EXAMINE = "_workflow_examine";

	/**
	 * 工作流审批意见 是否同意 字段值
	 */
	public static final String WORKFLOW_IS_ALLOW = "_workflow_is_allow";
	/**
	 * 功能模块id
	 */
	public static final String WORKFLOW_MODEL_ID = "_workflow_model_id";
	/**
	 * 功能模块名
	 */
	public static final String WORKFLOW_MODEL_NAME = "_workflow_model_name";
	/**
	 * 重新登录的url
	 */
	public static final String RE_LONGIN_URL = "_re_login_url";

}
