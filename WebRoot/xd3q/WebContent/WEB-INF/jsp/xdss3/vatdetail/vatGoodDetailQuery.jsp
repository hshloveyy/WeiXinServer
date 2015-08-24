<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增值税商品明细汇总表综合查询</title>
</head>
<body>
	<fisc:grid 
		   divId="div_southForm" 
		   pageSize="10000" 
		   tableName="YVATDETAIL "
		   handlerClass="com.infolion.xdss3.vatdetail.web.VatGoodDetailGrid"
 		   whereSql="" 
 		   needCheckBox="true"
 		   height="400"
 		   needAutoLoad="false"  >
	</fisc:grid>

<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
			<td width="" align="right"><font color="red">★</font>公司：</td>
			<td width="" colspan="3">
				<div id="div_bukrs_sh"></div>
				<fisc:searchHelp divId="div_bukrs_sh" searchHelpName="YHXD3QCOMPANY" boName="" boProperty="" searchType="field" hiddenName="bukrs" valueField="COMPANYID" displayField="COMANYNAME"></fisc:searchHelp>
			</td>
			<td></td>
			
			<td width="" align="right">业务范围：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="t_gsber" name="t_gsber" value="*" >
				<div id="div_gsber_bt"></div>
<!--				<div id="div_gsber_sh"></div>-->
<!--				<fisc:searchHelp divId="div_gsber_sh" searchHelpName="YHXD3QDEPT" boName="" boProperty="" searchType="field" hiddenName="t_gsber" valueField="DEPTID" displayField="DEPTNAME"></fisc:searchHelp>-->
			</td>
			<td></td>
			
			<td width="" align="right"><font color="red">★</font>日期（期间）：</td>
			<td width="92px" >
				<input type="text" id="begda" name="begda" value=""> 
				<fisc:calendar applyTo="begda"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
			</td>
			<td align="left">至</td>
			<td>
				<input type="text" id="endda" name="endda" value="">
				<fisc:calendar applyTo="endda"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
			</td>
			<td></td>
		</tr>
		
		<tr>
			<td width="" align="right">	物料号：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="t_matnr" name="t_matnr" value="*"><div id="div_matnr_bt"></div><a>多个值请用,号隔开(半角字符)</a>
			</td>
			<td></td>
						
			<td width="" align="right">物料组：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="matGroupMultiselect" name="matGroupMultiselect" value="*" ><input type="button"  onclick="Ext.getDom('matGroupMultiselect').value='';" value="清除"/>
			    <div id="div_ymatGroup"  style="display:none"></div>
			    <fisc:dictionary width="100" hiddenName = "s_matgroup" isTextValue="true" dictionaryName="YDMATGROUP" divId="div_ymatGroup" defaultValue="*"   ></fisc:dictionary>
			</td>
			<td></td>
			
			<td width="" align="right">业务类型：</td>
			<td  width="" colspan="3">
				<input type="text" class="inputText" id="t_typeMultiselect" name="t_typeMultiselect" value="*" ><input type="button"  onclick="Ext.getDom('t_typeMultiselect').value='';" value="清除"/>
			</td>
			<td></td>

		</tr>
		
		<tr>
			<td width="" align="right">税率：</td>
			<td  width="" colspan="3">
				<input type="text" class="inputText" id="t_taxp" name="t_taxp" value="*" ><a>多个值请用,号隔开(半角字符)</a>
			</td>
			<td></td>
			<td width="" align="right"></td>
			<td  width="" colspan="3">
			</td>
			<td></td>
			
			<td width="" colspan="3">
			</td>
			<td></td>
		</tr>
		
		<tr>
		<!-- 隐藏域 -->
			<input type="hidden" class="inputText" id="t_type" name="t_type" value="*">
		</tr>
</table>
</form>
</div>
<div id="div_southForm"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/vatdetail/vatGoodDetailQuery.js"></script>
<script type="text/javascript" defer="defer">
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

/**
 * EXT 布局
 */
Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			items:[{
					region:"north",
					contentEl:'div_toolbar',
					border:false,
					height:26
				},{
					region:'center',
					border:false,
					contentEl:'div_center'
				},		
				{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            title:'增值税商品明细汇总表综合查询',
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});
});


function _manager(value,metadata,record){
	var hrefStr = '';
	hrefStr = '<a href="#" style="color: green;" onclick="_commonMethodOperation(\'1\',\'期初已到税票未进仓(税额)\',div_southForm_grid,\'xdss3/vatdetail/vatDetailController.spr?action=_manage\',\'_viewBillPurchasedpCallBack\',\'_view\',\'false\')">查看</a>  ';
	return hrefStr;
}

</script>
