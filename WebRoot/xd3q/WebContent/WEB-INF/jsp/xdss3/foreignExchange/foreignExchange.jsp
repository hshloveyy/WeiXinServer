<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>外汇调汇</title>
</head>
<body>
	
	<div id="toolBar" style="overflow: hidden ;width: 100%"></div>
	<form id="mainForm" name="mainForm">
		<table border="0">
			<tr>
				<td width="15%" align="right">公司代码：</td>
				<td >
					<div id="div_bukrs_sh"></div>
					<fisc:searchHelp divId="div_bukrs_sh" searchHelpName="YHXD3QCOMPANY" boName="" boProperty="" searchType="field" hiddenName="p_bukrs" valueField="COMPANYID" displayField="COMANYNAME"></fisc:searchHelp>
				</td>
				
					<td width="15%" align="right">部门：</td>
				 
				<td >
					<div id="div_gsber_sh"></div>
					<fisc:searchHelp divId="div_gsber_sh" searchHelpName="YHXD3QDEPT" boName="" boProperty="" searchType="field" hiddenName="p_gsber" valueField="DEPTID" displayField="DEPTNAME"></fisc:searchHelp>
					<input type="checkbox" id="ck_sybm" name="ck_sybm" value="sybm">所有部门
				</td>
				
				<td width="20%" align="left"></td>
				<td width="20%" align="left"></td>
				<td width="20%" align="left"></td>
			</tr>
			<tr>
				<td width="15%" align="right">会计年度：</td>
				<td >
					<input type="text" class="inputText" id="p_gjahr" name="p_gjahr" value="" maxlength="4">
				</td>
				
				<td width="20%" align="right">会计期间：</td>
				<td width="15%" align="right">
				<input type="text" class="inputText" id="p_monat" name="p_monat" value="" maxlength="2">
				</td>
				<td width="20%" align="left"></td>
				<td width="20%" align="left"></td>
				<td width="20%" align="left"></td>
			</tr>
			
			<tr>
				<td width="15%" align="right">币别：</td>
				<td >
					<input type="text" class="inputText" id="p_waers" name="p_waers" value="">
				</td>
				<td width="20%" align="left"></td>
				<td width="15%" align="right"></td>
				<td >
					
				</td>
				<td width="20%" align="left"></td>
				<td width="20%" align="left"></td>
			</tr>
			<tr>
				<td width="15%" align="right">总账科目：</td>
				<td  width="25%" align="left">从<input type="text" class="inputText" id="s_hkont_st" name="s_hkont_st" value="" maxlength="10"></td>				
				<td width="20%" align="left">到<input type="text" class="inputText" id="s_hkont_end" name="s_hkont_end" value="" maxlength="10"></td>
				<td width="15%" align="left"></td>
				<td ></td>
				<td width="20%" align="left"></td>
				<td width="20%" align="left"></td>
			</tr>
			
			<tr>
				<td width="15%" align="right">客户：</td>
				<td  width="25%" align="left">从	<input type="text" class="inputText" id="s_kunnr_st" name="s_kunnr_st" value="" maxlength="10"></td>
				<td width="20%" align="left">到<input type="text" class="inputText" id="s_kunnr_end" name="s_kunnr_end" value="" maxlength="10"></td>
	
				<td width="15%" align="right"></td>
				<td >
					
				</td>
				<td width="20%" align="left"></td>
				<td width="20%" align="left"></td>
			</tr>
			
			<tr>		
	
				<td width="15%" align="right">供应商：</td>
				<td  width="25%" align="left">从	<input type="text" class="inputText" id="s_lifnr_st" name="s_lifnr_st" value="" maxlength="10"></td>
				<td width="20%" align="left">到<input type="text" class="inputText" id="s_lifnr_end" name="s_lifnr_end" value="" maxlength="10"></td>
				<td width="15%" align="right"></td>
				<td >
					
				</td>
				<td width="20%" align="left"></td>
			</tr>
			<tr/>
			<tr>
				<td width="15%" align="right"><font  color="red">输出格式：</font></td>
				<td  width="25%">
					<input type="checkbox" id="ck_zz" name="ck_zz" value="1">总账科目
					<input type="checkbox" id="ck_kh" name="ck_kh" value="2">客户
					<input type="checkbox" id="ck_gys" name="ck_gys" value="3">供应商
					<input type="checkbox" id="ck_wbzj" name="ck_wbzj" value="4">外币资金
				</td>
				<td width="20%" align="left"></td>
				<td width="15%" align="right">				
				</td>
				<td >
					
				</td>
				<td width="20%" align="left">&quot;</td>
				
			</tr>
		
		</table>
		<input type="hidden" id="businessid" name="businessid" value="${businessid}">
	
	</form>
  
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/foreignExchange/foreignExchange.js"></script>
<script type="text/javascript" defer="defer">
var context = '<%= request.getContextPath() %>';
Ext.onReady(function(){
 	// 工具栏
	var toolbars = new Ext.Toolbar({
		items:['-',
				{id:'voucherPreview',text:'模拟凭证',handler:voucherPreview,iconCls:'icon-search'},'-',
				{id:'_clear' ,text:'清空',handler:_clear ,iconCls:'icon-clean'} ,'-'
				],
		renderTo:"toolBar"
	});
	
	// 查询主体框
    var viewport = new Ext.Viewport({
        layout:'border',
        items:[{
			region:"center",
			items:[{
					region:"north",
					contentEl:'toolBar',
					border:false,
					height:26
				},{
					region:'center',
					border:false,
					autoScroll:true,
					contentEl:'mainForm',
					bodyStyle:"background-color:#DFE8F6"
				}]
			}]
    });
    viewport.doLayout();
});



</script> 
