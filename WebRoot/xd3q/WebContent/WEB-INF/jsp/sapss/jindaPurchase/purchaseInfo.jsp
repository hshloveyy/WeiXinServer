<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>广西金达采购合同信息</title>
<style type="text/css">
.add{
	background-image:url(<%= request.getContextPath()%>/images/fam/add.gif) !important;
}
.delete{
	background-image:url(<%= request.getContextPath()%>/images/fam/delete.gif) !important;
}
.update{
	background-image:url(<%= request.getContextPath()%>/images/fam/refresh.gif) !important;
}
.find{
	background-image:url(<%= request.getContextPath()%>/images/fam/find.png) !important;
}
</style>
</head>
<body>
<div id="div_main" class="x-hide-display">
<form id="mainForm" name="mainForm">
	 <table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
      <tr>
        <td align="right">申请者<font color="red">▲<font></td>
        <td align="left">
        	<input  type="text" value="${info.applier}" name="applier"/>
        </td>
      </tr>
      <tr>
        <td align="right">申请日期:</td>
        <td align="left">
        	<input  type="text" value="${applyTime}" readonly="readonly"/>
        </td>
      </tr>
      <tr>
        <td align="right">SAP采购合同号码<font color="red">▲<font></td>
        <td align="left">
        	<input  type="text" name="sapPurchaseNo" value="${info.sapPurchaseNo}" />
        </td>
      </tr>
      <tr>
        <td align="right">供应商名称<font color="red">▲<font></td>
        <td align="left">
           <input type="text" name="supplier" value="${info.supplier}"/>
        </td>
      </tr>
      <tr>
		<td align="right">总金额<font color="red">▲<font></td>
		<td>
			<input type="text" name="applyAccount" value="${info.applyAccount}" />
		</td>
      </tr>
      <tr>
      <td align="right">备注:</td>
      <td colspan="5">
      	<textarea rows="3" name="cmd" style="width:600;overflow-y:visible;word-break:break-all;" ${readable==true?"readonly='readonly'":''}>${info.cmd}</textarea>
      </td>
      </tr>
      </table>
      		<input type="hidden" name="purchaseId" value="${info.purchaseId}"/>
 </form>
</div>
<div id="fileDiv" class="x-hide-display" ></div>
<div id="workflowHistory"  style="position: relative" class="x-hide-display"></div>
</body>
<script type="text/javascript">

var strOperType;
var tabs;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	$('jindaPurchaseFile').value='${info.purchaseId}';
   	//----------------------------------------------------------------------
	tabs = new Ext.TabPanel({
		id:'mainTabPanel',
        autoWidth:true,
        activeTab: 0,
        autoScroll:true,
        loadMask:true,
        frame:true,
        defaults:{autoHeight: true},
        items:[{
            	title:'业务信息',
            	layout:'form',
            	buttonAlign:'center',
                items:[{
                		miniHeight:80,
                		autoHeight:true,
        				contentEl: 'div_main'
                }],
                buttons:[{
    	            text:'保存',
    	            hidden:${readable==true},
    	            handler:function(){
    	            	strOperType='save';
    	            	var param1 = Form.serialize('mainForm');
    					var param = param1 + "&action=savePurchase";
    					new AjaxEngine('jdPurchaseController.spr', 
    							   {method:"post", parameters: param, onComplete: callBackHandle});
    				}
    			},{
                	text:'提交',
                	hidden:${info.purchaseId=="" || info.purchaseId==null || readable==true},
                	handler:function(){
                			strOperType = 'submit';
                			var param1 = Form.serialize('mainForm');
    						var param = param1  + "&action=firstSubmit&scrappedId=${scrappedId}";
    						new AjaxEngine('jdPurchaseController.spr', 
    						   {method:"post", parameters: param, onComplete: callBackHandle});
                		}
    			 },{
                	text:'关闭',
                	hidden:${readable==true},
                	handler:function(){
                			top.ExtModalWindowUtil.markUpdated();
    				 		top.ExtModalWindowUtil.close();
    	            }
                }]    
            },{
				title:'合同附件',
				disabled:${info.purchaseId=="" || info.purchaseId==null},
				contentEl:'fileDiv',
				id:'fileEl',
				listeners:{
					activate:handlerActivate
				}
            },{
				title:'审批信息',
				contentEl:'workflowHistory'
            }]
	});
  	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'fit',
			items:[tabs]
		}]
	});

});//end of Ext.onReady  
function handlerActivate(tab){
	fileDiv_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('jindaPurchaseFile').value}});
}

//回调函数
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if (strOperType == 'save'){
		tabs.getItem('fileEl').enable();
		$('purchaseId').value=customField.PURCHASE_ID;
		$('jindaPurchaseFile').value=customField.PURCHASE_ID;
	}	
	if (strOperType == 'submit'){
		top.ExtModalWindowUtil.markUpdated();
	    top.ExtModalWindowUtil.close();
	}	
}
</script>
</html>
<fiscxd:workflow-taskHistory divId="workflowHistory" businessRecordId="${scrappedId}" width="800"></fiscxd:workflow-taskHistory>
<fiscxd:fileUpload divId="fileDiv" erasable="false"  increasable="true"
 resourceId="2222" resourceName="33333" recordIdHiddenInputName="jindaPurchaseFile"></fiscxd:fileUpload>



