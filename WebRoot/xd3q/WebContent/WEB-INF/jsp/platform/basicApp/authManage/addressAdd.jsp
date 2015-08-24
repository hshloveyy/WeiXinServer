<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年01月26日 09点27分57秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象地址信息(Address)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/addressAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/addressAddGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 17(调试用)序号:2;formRowNo:1 ;rowNo: 1;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.country}：</td>
		<td  width="30%" >
			<div id="div_country_sh"></div>
			<fisc:searchHelp divId="div_country_sh" boName="Address" boProperty="country" value="${address.country}"></fisc:searchHelp>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 17(调试用)序号:3;formRowNo:1 ;rowNo: 1;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.mobilephone}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Address.mobilephone" name="Address.mobilephone" value="${address.mobilephone}" <fisc:authentication sourceName="Address.mobilephone" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 17(调试用)序号:4;formRowNo:2 ;rowNo: 2;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.area}：</td>
		<td  width="30%" >
			<div id="div_area_sh"></div>
			<fisc:searchHelp divId="div_area_sh" boName="Address" boProperty="area" value="${address.area}"></fisc:searchHelp>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 17(调试用)序号:5;formRowNo:2 ;rowNo: 2;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.fax}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Address.fax" name="Address.fax" value="${address.fax}" <fisc:authentication sourceName="Address.fax" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 17(调试用)序号:6;formRowNo:3 ;rowNo: 3;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.city}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Address.city" name="Address.city" value="${address.city}" <fisc:authentication sourceName="Address.city" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 17(调试用)序号:7;formRowNo:3 ;rowNo: 3;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.website}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Address.website" name="Address.website" value="${address.website}" <fisc:authentication sourceName="Address.website" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 17(调试用)序号:8;formRowNo:4 ;rowNo: 4;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.zipcode}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Address.zipcode" name="Address.zipcode" value="${address.zipcode}" <fisc:authentication sourceName="Address.zipcode" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 17(调试用)序号:9;formRowNo:4 ;rowNo: 4;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.qq_msn}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Address.qq_msn" name="Address.qq_msn" value="${address.qq_msn}" <fisc:authentication sourceName="Address.qq_msn" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 17(调试用)序号:10;formRowNo:5 ;rowNo: 5;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.email}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Address.email" name="Address.email" value="${address.email}" <fisc:authentication sourceName="Address.email" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 17(调试用)序号:11;formRowNo:5 ;rowNo: 5;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.homephone}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Address.homephone" name="Address.homephone" value="${address.homephone}" <fisc:authentication sourceName="Address.homephone" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 17(调试用)序号:12;formRowNo:6 ;rowNo: 6;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.phone}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Address.phone" name="Address.phone" value="${address.phone}" <fisc:authentication sourceName="Address.phone" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 17(调试用)序号:13;formRowNo:6 ;rowNo: 6;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.addresstype}：</td>
		<td   width="40%" >
			<div id="div_addresstype_dict"></div>
			<fisc:dictionary boName="Address" boProperty="addresstype" dictionaryName="YDADDRESSTYPE" divId="div_addresstype_dict" isNeedAuth="false"  value="${address.addresstype}"></fisc:dictionary>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 17(调试用)序号:14;formRowNo:7 ;rowNo: 7;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.extnumber}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Address.extnumber" name="Address.extnumber" value="${address.extnumber}" <fisc:authentication sourceName="Address.extnumber" taskId=""/> >
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 17(调试用)序号:16;formRowNo:8 ;rowNo: 8;columnNo: 1;1X -->
		<td align="right"  width="15%" valign="top"><font color="red">★</font>${vt.property.address}：</td>
		<td  width="30%"  colspan="3" >
			<textarea rows="4" cols="54" id="Address.address" name="Address.address" <fisc:authentication sourceName="Address.address" taskId=""/>>${address.address}</textarea>
		</td>
	</tr>

	<input type="hidden" id="Address.client" name="Address.client" value="${address.client}">
	<input type="hidden" id="Address.addressid" name="Address.addressid" value="${address.addressid}">
</table>
</form>
</div>

<div id="div_south" class="x-hide-display"></div>

</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

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
							layout: 'anchor',
				            height:600,
				            border:false,
				            autoScroll: true,
				            items:[{
				            		title: '${vt.boTextInfo}',
				            		layout:'fit',
				            		autoScroll: true,
				            		contentEl:'div_center'
						}

				  ]
				 }]
				}
				 ]
	});
	var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					          
          
          
          
					{id:'_save',text:'${vt.mSaveOrUpdate}',handler:_save,iconCls:'icon-table-save'},'-',
          
					{id:'_cancel',text:'${vt.mCancel}',handler:_cancel,iconCls:'icon-undo'},
					' '],  
			renderTo:"div_toolbar"
	});
//});
</script>
