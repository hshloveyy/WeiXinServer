<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>物料主数据修改</title>
</head>
<body>
<div id="div_main" class="x-hide-display">
<form name="mainForm">
<table width="900" border="0" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
  <tr>
  
    <td width="900" height="100%" valign="top"><table width="900" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
    <tr>
    	<td colspan="2" align="center" width="450">
    		增加<input type="radio" value="add" name="radio" onclick="addRadio()" id="add"/>
    		 修改<input type="radio" value="update" name="radio" onclick="updateRadio()" id="update" />
    		 <input type="hidden" name="addType" id="addType"/> 
    	</td>
    	<td colspan="2" width="450">注:选择增加或者修改,提交后走的流程不同.</td>
    </tr>
    
      <tr>
        <td><div align="right" width="150">物料代码:</div></td>
        <td colspan="1"  width="300"><div id="updateDiv"><input type="text" name="materialCode" value="${main.materialCode}"/></div></td>
         <td  width="180"><div align="right">销售组织:<font color="red">▲</font></div></td>
        <td colspan="1" width="270"><input type="text" name="saleOrg" value="${main.saleOrg}"/></td>
        </tr>
      <tr>
        <td><div align="right">物料描述:<font color="red">▲</font></div></td>
        <td colspan="3"><input type="text" name="materialName" value="${main.materialName}" size="100"/></td>
        </tr>
      <tr>
        <td><div align="right">单位:<font color="red">▲</font></div></td>
         <td colspan="3"><input type="text" name="materialUnit" value="${main.materialUnit}"/></td>
        <td></td>
        <td></td>
      </tr>
      <tr>
        <td><div align="right">物料组:<font color="red">▲</font></div></td>
        <td colspan="3"><div id="div_Group"></div></td>
        </tr>
      <tr>
        <td><div align="right">物料销项税：</div></td>
        <td><div id="div_SaleTax"></div></td>
        <td><div align="right">是否既有出口又有内销:<font color="red">▲</font></div>
        </td>
        <td>  
            <input type="radio" name="hasRecWrite" id="hasRecWrite1" onclick="IS_EXP_IMP(1)" value="1" <c:if test="${main.isExpImp=='1'}">checked</c:if> />是
            <input type="radio" name="hasRecWrite" id="hasRecWrite0" onclick="IS_EXP_IMP(0)" value="0" <c:if test="${main.isExpImp=='0'}">checked</c:if> />否      </td>
      </tr>
      <tr>
        <td><div align="right">出口订单销项税:</div></td>
        <td><input type="text" name="expSaleTax" value="${main.expSaleTax}"/></td>
        <td><div align="right">进口订单销项税:</div></td>
        <td><input type="text" name="impSaleTax" value="${main.impSaleTax}"/></td>
      </tr>
      <tr>
        <td><div align="right">内销订单销项税:</div></td>
        <td><input type="text" name="withinSaleTax" value="${main.withinSaleTax}"/></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><div align="right">备注:</div></td>
        <td colspan="3" ><textarea name="cmd" rows="2" cols="100" style="overflow-y:visible;word-break:break-all;">${main.cmd}</textarea></td>
        </tr>
      <tr>
        <td colspan="4" align="center">
        <table><tr>
        <td><div id="save"><input type="button"  name="save" value="保存" onclick="saveForm()"/></div></td>
        <td><div id="commit"><input type="button" name="commit" value="提交" onclick="commitForm()"/></div></td>
        <td><div id="close"><input type="button" name="close" value="关闭" onclick="closeForm()"/></div></td>
        </tr></table>
          <input type="hidden" name="materialId" value="${main.materialId}"/>
          <input type="hidden" name="isExpImp" value="${main.isExpImp}"/>
          	<input type="hidden" name="examineState" value="${main.examineState}"/>
          	<input type="hidden" name="approvedTime" value="${main.approvedTime}"/>
          	<input type="hidden" name="isAvailable" value="${main.isAvailable}"/>
          	<input type="hidden" name="creatorTime" value="${main.creatorTime}"/>
          	<input type="hidden" name="creator" value="${main.creator}"/>     
          </td>
        </tr>
      <tr>
        <td colspan="4"><font color="#FF0000">注：各类型销项税率为必填项，如为进口只填进口销项税即可，以此类推。出口订单，如有退税，则填0。</font></td>
        </tr>
    </table></td>
  </tr>
</table>
</form>
</div>
<div id="div_history" class="x-hide-display"></div>
</body>
<script type="text/javascript">
   	Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
 	var tabs = new Ext.TabPanel({
		id:'mainTabPanel',
        renderTo: document.body,
        autoWidth:true,
        activeTab: 0,
        loadMask:true,
        frame:true,
        defaults:{autoHeight: true},
        items:[
            {contentEl:'div_main', title: '主数据修改'},
            {contentEl:'div_history', title: '审批历史记录'}
        ]
    });  	
	 var addType = '${main.addType}';
  	 if(addType=='add'){
  	  	 Ext.getDom('add').checked='checked';
  	  	 addRadio();
  	 } 	 
  	 else if(addType=='update'){
  		Ext.getDom('update').checked='checked';
  		updateRadio();
  	 }	
 	var type='${Type}';
    if(type == "true"){
       var arr= Ext.query('input[type=text]');
   	 	for(var i=0;i<arr.length;i++){
   	 		arr[i].readOnly=true;
   	 	}
   	 	$('save').innerHTML='';
   	 	$('commit').innerHTML='';
   	 	$('close').innerHTML='';
   	 	$('cmd').readOnly=true;
   	 	$('add').disabled=true;
   	 	$('update').disabled=true;
   	 	if($('btn3')!=null){
   	 		$('btn3').disabled=true;
   	   	 }
   	 }
	 var modify = '${modify}';
  	 if(modify=='workflow'){
   	 		$('add').disabled=true;
   	 		$('update').disabled=true;
    	 	$('commit').innerHTML='';
       	 	$('close').innerHTML='';
  	 }
});
    function saveForm()
    {
        var parm="?action=save&"+Form.serialize('mainForm')+'&update=${modify}';
	new AjaxEngine('materialController.spr',
			 {method:"post", parameters: parm,onComplete: callBackHandle});
    }
    function commitForm()
    {
        var parm="?action=saveAndsubmit&"+Form.serialize('mainForm');
	new AjaxEngine('materialController.spr',
			 {method:"post", parameters: parm,onComplete: callBackHandle});
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
    function selectMaterial(){
		top.ExtModalWindowUtil.show('查找物料','masterQueryController.spr?action=toFindMaterial','',materialCallback,{width:755,height:450});
    }
    function materialCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('alertMaterialCode').value=cb.MATNR;
	Ext.getDom('alertMaterialName').value=cb.MAKTX;
    }
    function IS_EXP_IMP(val1)
    {
       document.all.isExpImp.value = val1;
    }
    function addRadio(){
    	$('addType').value='add';
    	disabledText(false);
    	disableDropSelection(false);
        $('updateDiv').innerHTML='<input type="text" name="materialCode" value="${main.materialCode}"/>';
     }
    function updateRadio(){
        $('addType').value='update';
    	disabledText(true);
    	disableDropSelection(true);
    	$('updateDiv').innerHTML='<input type="text" name="materialCode" value="${main.materialCode}" readOnly="readOnly"/><input type="button" id="btn3" value="..." onclick="selectMaterial()"></input>';
        $('materialName').readOnly=false;
    }
    function disabledText(bool){
   	 	var arr= Ext.query('input[type=text]');
   	 	for(var i=0;i<arr.length;i++){
   	 		arr[i].readOnly=bool;
   	 	}
    }
    function disableDropSelection(bool){
		dict_div_Group.disable(bool);
		dict_div_SaleTax.disable(bool);
		$('hasRecWrite0').disabled=bool;
		$('hasRecWrite1').disabled=bool;
    }
    function materialCallback(){
    	var cb = top.ExtModalWindowUtil.getReturnValue();
    	$('materialCode').value=cb.MATNR;//供应商编码
    	$('materialName').value=cb.MAKTX;//供应商名称1
    }
    function selectMaterial(){
    	top.ExtModalWindowUtil.show('查找物料','masterQueryController.spr?action=toFindMaterial','',materialCallback,{width:755,height:450});
    }
    
</script>
</html>
<fiscxd:workflow-taskHistory divId="div_history" businessRecordId="${main.materialId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:dictionary divId="div_Group" fieldName="materialGroupName" dictionaryName="YMATGROUP" selectedValue="${main.materialGroupName}" width="153" disable="${Type}" needDisplayCode="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_SaleTax" fieldName="materialSaleTax" dictionaryName="BM_SALE_TAX" selectedValue="${main.materialSaleTax}" width="153" disable="${Type}" needDisplayCode="true"></fiscxd:dictionary>