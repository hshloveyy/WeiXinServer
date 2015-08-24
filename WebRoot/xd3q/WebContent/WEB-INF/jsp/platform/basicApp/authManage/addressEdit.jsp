<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年01月26日 09点27分57秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>地址信息(Address)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/addressEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/addressEditGen.js"></script>
</head>
<body>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${address.addressid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.country}：</td>
		<td width="30%">
			<div id="div_country_sh"></div>
			<fisc:searchHelp divId="div_country_sh" boName="Address" boProperty="country"  value="${address.country}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%" >${vt.property.mobilephone}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Address.mobilephone" name="Address.mobilephone" value="${address.mobilephone}"   <fisc:authentication sourceName="Address.mobilephone" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.area}：</td>
		<td width="30%">
			<div id="div_area_sh"></div>
			<fisc:searchHelp divId="div_area_sh" boName="Address" boProperty="area"  value="${address.area}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%" >${vt.property.fax}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Address.fax" name="Address.fax" value="${address.fax}"   <fisc:authentication sourceName="Address.fax" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.city}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Address.city" name="Address.city" value="${address.city}"   <fisc:authentication sourceName="Address.city" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" >${vt.property.website}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Address.website" name="Address.website" value="${address.website}"   <fisc:authentication sourceName="Address.website" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.zipcode}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Address.zipcode" name="Address.zipcode" value="${address.zipcode}"   <fisc:authentication sourceName="Address.zipcode" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" >${vt.property.qq_msn}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Address.qq_msn" name="Address.qq_msn" value="${address.qq_msn}"   <fisc:authentication sourceName="Address.qq_msn" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >${vt.property.email}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Address.email" name="Address.email" value="${address.email}"   <fisc:authentication sourceName="Address.email" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" >${vt.property.homephone}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Address.homephone" name="Address.homephone" value="${address.homephone}"   <fisc:authentication sourceName="Address.homephone" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.phone}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Address.phone" name="Address.phone" value="${address.phone}"   <fisc:authentication sourceName="Address.phone" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" >${vt.property.addresstype}：</td>
		<td  width="40%">
			<div id="div_addresstype_dict"></div>
			<fisc:dictionary boName="Address" boProperty="addresstype" dictionaryName="YDADDRESSTYPE" divId="div_addresstype_dict" isNeedAuth="false"  value="${address.addresstype}"></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >${vt.property.extnumber}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Address.extnumber" name="Address.extnumber" value="${address.extnumber}"   <fisc:authentication sourceName="Address.extnumber" taskId="${workflowTaskId}"/>>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td align="right" width="15%" valign="top"><font color="red">★</font>${vt.property.address}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="Address.address" name="Address.address"  <fisc:authentication sourceName="Address.address" taskId="${workflowTaskId}"/>>${address.address}</textarea>
		</td>
	</tr>

	<input type="hidden" id="Address.client" name="Address.client" value="${address.client}">
	<input type="hidden" id="Address.addressid" name="Address.addressid" value="${address.addressid}">
</table>
</form>
</div>

<div id="div_south" class="x-hide-display"></div>

<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var addressid = '${address.addressid}';	

//页面文本
var Txt={
	//地址信息
	address:'${vt.boText}',
	//boText复制创建
	address_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	address_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	address_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
	// '提交成功！'
	submitSuccess:'${vt.submitSuccess}',
	// '操作成功！'
	operateSuccess:'${vt.operateSuccess}',
	// 提示
	cue:'${vt.sCue}',
	// 确定
	ok:'${vt.sOk}',
	// 取消
	cancel:'${vt.sCancel}'
};

/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
					region:'center',
					layout:'border',
					border:false,
					items:[{
							region:'north',
							layout:'fit',
							height:26,
							border:false,
							contentEl:'div_toolbar'
						},{
							region:'center',
							layout:'anchor',
				            height:600,
				            border:false,
				            autoScroll:true,
				            items:[
				              {
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		autoScroll: true,
				            		border:false,
				            		//height:272.5,
				            		contentEl:'div_center'
						}
							   ]
				   }]
				}
                 ]
	});

	var toolbars = new Ext.Toolbar({
			items:[
					'-',
{id:'_copyCreate',text:'${vt.mCopyCreate}',handler:_copyCreateAddress,iconCls:'icon-copyCreate'},'-',
{id:'_delete',text:'${vt.mDelete}',handler:_deleteAddress,iconCls:'icon-delete'},'-',
{id:'_create',text:'${vt.mCreate}',handler:_createAddress,iconCls:'icon-add'},'-',
'->','-',
{id:'_saveOrUpdate',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateAddress,iconCls:'icon-table-save'},'-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelAddress,iconCls:'icon-undo'},'-',
' '
],
			renderTo:'div_toolbar'
	});

//});
</script>