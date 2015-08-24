<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月08日 08点18分08秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象固定资产(Fixedasset)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>固定资产增加页面</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/fixedasset/fixedassetAdd.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/fixedasset/fixedassetAddGen.js"></script>
</head>
<body>
<div id="div_east" class="x-hide-display"></div>
<fisc:relationship divId="div_east" boName="Fixedasset"></fisc:relationship><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:3;formRowNo:15 ;rowNo: 15;columnNo: 1;2X -->
		<td align="right"  width="15%" > 公司代码：</td>
		<td  width="30%" >
			<div id="div_bukrs_sh"></div>
			<fisc:searchHelp divId="div_bukrs_sh" boName="Fixedasset" boProperty="bukrs" value="${fixedasset.bukrs}"></fisc:searchHelp>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:4;formRowNo:15 ;rowNo: 15;columnNo: 2;2X -->
		<td align="right"  width="15%" >资产次级编号：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Fixedasset.anln2" name="Fixedasset.anln2" value="${fixedasset.anln2}" <fisc:authentication sourceName="Fixedasset.anln2" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:5;formRowNo:17 ;rowNo: 17;columnNo: 1;2X -->
		<td align="right"  width="15%" >资产分类：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Fixedasset.anlkl" name="Fixedasset.anlkl" value="${fixedasset.anlkl}" <fisc:authentication sourceName="Fixedasset.anlkl" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:6;formRowNo:17 ;rowNo: 17;columnNo: 2;2X -->
		<td align="right"  width="15%" >技术资产号：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Fixedasset.gegst" name="Fixedasset.gegst" value="${fixedasset.gegst}" <fisc:authentication sourceName="Fixedasset.gegst" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:7;formRowNo:19 ;rowNo: 19;columnNo: 1;2X -->
		<td align="right"  width="15%" >资产类型：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Fixedasset.anlar" name="Fixedasset.anlar" value="${fixedasset.anlar}" <fisc:authentication sourceName="Fixedasset.anlar" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:8;formRowNo:19 ;rowNo: 19;columnNo: 2;2X -->
		<td align="right"  width="15%" > 负责人：</td>
		<td   width="40%" >
			<div id="div_assetcharge_sh"></div>
			<fisc:searchHelp divId="div_assetcharge_sh" boName="Fixedasset" boProperty="assetcharge" value="${fixedasset.assetcharge}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:9;formRowNo:21 ;rowNo: 21;columnNo: 1;2X -->
		<td align="right"  width="15%" > 产品/物料：</td>
		<td  width="30%" >
			<div id="div_matnr_sh"></div>
			<fisc:searchHelp divId="div_matnr_sh" boName="Fixedasset" boProperty="matnr" value="${fixedasset.matnr}"></fisc:searchHelp>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:10;formRowNo:21 ;rowNo: 21;columnNo: 2;2X -->
		<td align="right"  width="15%" >创建人：</td>
		<td   width="40%" >
			<fisc:user boProperty="creator" boName="Fixedasset" userId="${fixedasset.creator}"></fisc:user>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:11;formRowNo:23 ;rowNo: 23;columnNo: 1;2X -->
		<td align="right"  width="15%" >创建时间：</td>
		<td  width="30%" >
		    <input type="text" class="inputText" id="Fixedasset.createtime" name="Fixedasset.createtime" value="${fixedasset.createtime}"  readonly="readonly" <fisc:authentication sourceName="Fixedasset.createtime" taskId=""/>>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:12;formRowNo:23 ;rowNo: 23;columnNo: 2;2X -->
		<td align="right"  width="15%" >最后修改者：</td>
		<td   width="40%" >
			<fisc:user boProperty="lastmodifyer" boName="Fixedasset" userId="${fixedasset.lastmodifyer}"></fisc:user>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:13;formRowNo:25 ;rowNo: 25;columnNo: 1;2X -->
		<td align="right"  width="15%" >最后修改时间：</td>
		<td  width="30%" >
		    <input type="text" class="inputText" id="Fixedasset.lastmodifytime" name="Fixedasset.lastmodifytime" value="${fixedasset.lastmodifytime}"  readonly="readonly" <fisc:authentication sourceName="Fixedasset.lastmodifytime" taskId=""/>>
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="Fixedasset.client" name="Fixedasset.client" value="${fixedasset.client}">
	<input type="hidden" id="Fixedasset.anln1" name="Fixedasset.anln1" value="${fixedasset.anln1}">
	<input type="hidden" id="Fixedasset.processstate" name="Fixedasset.processstate" value="${fixedasset.processstate}">
</table>
</form>
</div>

<div id="div_south" class="x-hide-display"></div>

</body>
</html>
<script type="text/javascript">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

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
				            		title: '固定资产信息',
				            		layout:'fit',
				            		autoScroll: true,
			            			anchor: '-20',
				            		contentEl:'div_center'
						}

				  ]
				 }]
				}
				,{
					region:'east',
					width:200,
					contentEl:'div_east',
					collapsible: true,
					collapsed:true,
					autoScroll: true,
					title:'相关操作'
				}]
	});
	var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					          
          
          
          
					{id:'_save',text:'保存',handler:_save,iconCls:'icon-table-save'},'-',
          
					{id:'_cancel',text:'取消',handler:_cancel,iconCls:'icon-undo'},'-',
          
					'-'],  
			renderTo:"div_toolbar"
	});
//});
</script>
