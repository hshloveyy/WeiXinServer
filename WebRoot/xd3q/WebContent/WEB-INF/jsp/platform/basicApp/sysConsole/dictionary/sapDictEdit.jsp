<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年11月04日 14点50分05秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>字典表(SapDict)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典表编辑页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/sysConsole/dictionary/sapDictEdit.js"></script>
<style type="text/css">
  .x-grid3-header-offset {width: auto;}
</style>
</head>
<body>
<fisc:grid divId="div_sapDictDetail" tableName="(select  b.* from DD07T b  left outer join TADIR c  on b.DOMNAME =c.OBJ_NAME where  b.DDLANGUAGE='1' and c.DEVCLASS in(select DOMVALUE_L as DEVCLASS  from DD07T where DD07T.DOMNAME='YDDEVCLASS') ) YDICTDETAIL"
 handlerClass="com.infolion.platform.basicApp.sysConsole.dictionary.web.grid.SapDictDetailGrid"
 entityName="SapDictDetail" needCheckBox="true" editable="true" defaultCondition="YDICTDETAIL.DOMNAME='${sapDict.domName}'" 
 orderBySql="valPos" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>

<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%" >字典表名：</td>
		<td width="30%">
			<input type="text" class="inputText" id="SapDict.domName" name="SapDict.domName"  readonly="readonly" value="${sapDict.domName}"   <fisc:authentication sourceName="SapDict.devClass" taskId=""/>>
		</td>
		<td align="right" width="15%" >字典表描述：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="SapDict.ddText" name="SapDict.ddText" readonly="readonly" value="${sapDict.ddText}"   <fisc:authentication sourceName="SapDict.ddText" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >数据类型：</td>
		<td width="30%">
			<input type="text" class="inputText" id="SapDict.dataType" name="SapDict.dataType" readonly="readonly" value="${sapDict.dataType}"   <fisc:authentication sourceName="SapDict.dataType" taskId=""/>>
		</td>
		<td align="right" width="15%" >长度：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="SapDict.leng" name="SapDict.leng" readonly="readonly" value="${sapDict.leng}"   <fisc:authentication sourceName="SapDict.leng" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >开发类：</td>
		<td width="30%">
			<div id="div_devClass_dict"></div>
			<fisc:dictionary hiddenName="SapDict.devClass" dictionaryName="YDDEVCLASS" divId="div_devClass_dict" isNeedAuth="false" editable="false" value="${sapDict.devClass}"></fisc:dictionary>
		</td>
		<td align="right" width="15%" ></td>
		<td  width="40%">
		</td>
	</tr>
</table>
</form>
</div>       	         
<div id="div_sapDictDetail"></div>
<div id="div_top_north" class="search">
</div>
<div id="div_top_south" class="x-hide-display"></div>
<div id="div_east" class="x-hide-display"></div>
<div id="div_west" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//(select  b.* from DD07L a left outer join DD07T b  on  a.domname=b.domname and a.domvalue_l=b.domvalue_l and  b.ddlanguage='1'  left outer join TADIR c  on a.domname =c.obj_name where  c.devclass in(select DOMVALUE_L as devclass  from DD07T where DD07T.Domname='YDDEVCLASS') and a.domname='${sapDict.domName}')YDICTDETAIL"
// handlerClass="com.infolion.platform.basicApp.sysConsole.dictionary.web.grid.SapDictDetailGrid
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var domName = '${sapDict.domName}';	

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
				            		title:'字典表信息',
				            		layout:'fit',
				            		autoScroll: true,
				            		border:false,
				            		//height:85,
				            		contentEl:'div_center'
						}
						,{
									xtype:'tabpanel',
					            	height:310,
					            	defaults:{bodyStyle:"background-color:#DFE8F6"},
					            	autoScroll: true,
									activeTab:0,
						           items:[
				          						                {
						            		title:'字典表明细',
						            		layout:'fit',
						            		contentEl:'div_sapDictDetail'
						            	}
				          
						    ]}
						   ]}
]
				}
                 ]
	});

	var toolbars = new Ext.Toolbar({
			items:[
					'-',
'->','-',
{id:'_saveOrUpdate',text:'保存',handler:_saveOrUpdateSapDict,iconCls:'icon-table-save'},'-',
{id:'_cancel',text:'取消',handler:_cancelSapDict,iconCls:'icon-undo'},'-',
'-'
],
			renderTo:'div_toolbar'
	});

//});

</script>