<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/common/commons.jsp"%>
    <%@page import="com.infolion.sapss.Constants"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>客户供应商部门权限</title>

</head>

<body>

<form name="mainForm">
<table width="900" border="0" cellpadding="0" cellspacing="0">
  
  <tr>
    <td width="900" height="100%" valign="top"><table width="900" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
   	<tr>
   	<td><div align="right">客户编码:</div></td>
   	<td  width="30%" >
			<div id="div_custom_sh"></div>
			<fisc:searchHelp divId="div_custom_sh" searchHelpName="YHGETKUNNR" boName="" boProperty="" searchType="field" hiddenName="guestcode" valueField="KUNNR" displayField="NAME1" value="${main.guestcode}"></fisc:searchHelp>
		</td>
   	<td><div align="right">供应商编码:</div></td>
   	<td  width="30%" >
			<div id="div_supplier_sh"></div>
			<fisc:searchHelp divId="div_supplier_sh" searchHelpName="YHGETLIFNR" boName="" boProperty="" searchType="field" hiddenName="suppliercode" valueField="LIFNR" displayField="NAME1" value="${main.suppliercode}"></fisc:searchHelp>
	</td>
   	</tr>
      
 <tr>
 <td><div align="right">业务范围编码:</div></td>
 	<td  width="30%" >
			<div id="div_gsber_sh"></div>
			<fisc:searchHelp divId="div_gsber_sh" searchHelpName="YHXD3QDEPT" boName="" boProperty="" searchType="field" hiddenName="gsber" valueField="DEPTID" displayField="DEPTNAME" value="${main.gsber}"></fisc:searchHelp>
		</td>
		<td><div align="right">部门:</div></td>
		<td width="30%">
			<div id="div_deptid_sh"></div>
			<fisc:searchHelp divId="div_deptid_sh" searchHelpName="YHORGANIZATION" boName="" boProperty="" searchType="field" hiddenName="deptid" valueField="ORGANIZATIONID" displayField="ORGANIZATIONNAME" value="${main.deptid}"></fisc:searchHelp>
		</td>
 </tr>
        
   <tr>
   <td><div align="right">公司代码:</div></td>
   <td   width="40%" >
			<div id="div_company_code_sh"></div>
			<fisc:searchHelp divId="div_company_code_sh" searchHelpName="YHXD3QCOMPANY" boName="" boProperty="" searchType="field" hiddenName="company_code" valueField="COMPANYID" displayField="COMANYNAME" value="${main.company_code}"></fisc:searchHelp>
		</td>
   </tr>   
   
   <tr>
       <td colspan="4" align="center">
          <table><tr><td><div id="save"><input type="button"  name="save" value="保存" onclick="saveForm()"/></div></td>        
        <td><div id="close"><input type="button" name="close" value="关闭" onclick="closeForm()"/></div></td>
        </tr></table>
         <input type="hidden" name="deptByCusuId" value="${main.deptByCusuId}"/>
            
        </td>
        </tr>
    </table></td>
  </tr>  
  
</table>
 
 
</form>

</body>

<script type="text/javascript">
	
   	Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   		 	var type='${Type}';
 		if(type=='view'){
	 		$('save').innerHTML='';	   	 	
	   	 	$('close').innerHTML='';   	 	
   	 	}
	});

	
    function saveForm()
    {
    	  	if (div_custom_sh_sh.getValue() == '' && div_supplier_sh_sh.getValue() == '')
			{
				_getMainFrame().showInfo("必须先选择客户或供应商！");
				return;
			}
			if (div_custom_sh_sh.getValue() != '' && div_supplier_sh_sh.getValue() != '')
			{
				_getMainFrame().showInfo("客户或供应商不能同时选！");
				return;
			}
			if (div_gsber_sh_sh.getValue() == '')
			{
				_getMainFrame().showInfo("必须先选择[业务范围]！");
				return;
			}
			/**
			if (div_deptid_sh_sh.getValue() == '')
			{
				_getMainFrame().showInfo("必须先选择[部门]！");
				return;
			}
			**/
			if (div_company_code_sh_sh.getValue() == '')
			{
				_getMainFrame().showInfo("必须先选择[公司代码]！");
				return;
			}
			
	    	operateType = 'save';
	        var parm="?action=save&"+Form.serialize('mainForm')+'&update=${modify}';
			new AjaxEngine('deptByCusuController.spr',{method:"post", parameters: parm,onComplete: callBackHandle});
	
    }    
    var customCallBackHandle=function(trans){
		var responseUtil1 = new AjaxResponseUtils(trans.responseText);
		var customField = responseUtil1.getCustomField("coustom");//{"infolion-json":{"message":"操作成功","type":"info","coustom":{"ok":"保存成功"}}}
		top.ExtModalWindowUtil.alert('提示',customField.ok);
		var modify = '${modify}';
		if(modify!='workflow'){
			top.ExtModalWindowUtil.markUpdated();
			top.ExtModalWindowUtil.close();
		}
	}
	
    function closeForm()
    {
	    Ext.Msg.show({
	   		title:'提示',
	   		msg: '是否退出并关闭窗口',
	   		buttons: {yes:'确定', no:'取消'},
	   		fn: function(buttonId,text){
	 			if(buttonId=='yes'){
					top.ExtModalWindowUtil.markUpdated();
					top.ExtModalWindowUtil.close();
				}
	   		},
	   		animEl: 'elId',
	   		icon: Ext.MessageBox.QUESTION
		});
    }
      
   
   
	

    
</script>
</html>
