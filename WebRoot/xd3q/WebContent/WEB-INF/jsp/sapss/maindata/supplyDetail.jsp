<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/common/commons.jsp"%>
    <%@page import="com.infolion.sapss.Constants"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>
<body>
<div id="div_main" class="x-hide-display">
<form name="mainForm">
<table width="900" border="0" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
  <tr>
    <td width="900" height="100%" valign="top"><table width="900" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
    <tr>
    	<td width="450" colspan="2" align="center">
    		增加<input type="radio" value="add" name="radio" onclick="addRadio()" id="add" disabled="true" />
    		 修改<input type="radio" value="update" name="radio" onclick="updateRadio()" id="update" disabled="true" />
    		 更新年检年度<input type="radio" value="update2" name="radio" onclick="updateRadio2()" id="update2" disabled="true"  />
    		 <input type="hidden" name="addType" id="addType"/> 
    	</td>
    	<td width="450" colspan="2">注:选择增加或者修改,提交后走的流程不同.<font color="green">●</font>财务增加必填;<font color="red">▲</font>业务增加必填</td>
    </tr>
     <tr>
        <td width="900" colspan="4" align="center"><div align="center" ><font  size=5><b>基本信息</b></font></div></td>        
     </tr>
     
      <tr>
        <td><div align="right">供应商编码:</div></td>
        <td><div id="updateDiv"><input type="text" name="suppliersCode" value="${main.suppliersCode}"/></div></td>
        <td><div align="right">搜索条件*</div></td>
        <td><input type="text" name="searchTerm" value="${main.searchTerm}"/></td>
      </tr>
      <tr>
        <td><div align="right">供应商名称1<font color="red">▲</font></div></td>
        <td><input type="text" name="suppliersName1" value="${main.suppliersName1}" size="35"/></td>
        <td><div align="right">供应商名称2</div></td>
        <td><input type="text" name="suppliersName2" value="${main.suppliersName2}" size="35"/></td>
      </tr>
      <tr>
        <td><div align="right">中间供应商名称</div></td>
        <td colspan="3"><input type="text" name="longText" value="${main.longText}" size="100"/></td>
        </tr>
      <tr>
        <td><div align="right">地址(街道)<font color="red">▲</font></div></td>
        <td colspan="3"><input type="text" name="street" value="${main.street}" size="100"/></td>
        </tr>
      <tr>
        <td><div align="right">地址(区域)</div></td>
        <td colspan="3"><input type="text" name="region" value="${main.region}"size="100"/></td>
        </tr>
      <tr>
        <td><div align="right">城市</div></td>
        <td><input type="text" name="city" value="${main.city}"/></td>
         <td><div align="right">国家<font color="red">▲</font></div></td>
        <td>
        	<input type="text" name="country" value="${main.country}"/>
        </td>
      </tr>
      <tr>
        <td><div align="right">地区<font color="red">▲</font></div></td>
        <td>
        <input type="text" name="area" value="${main.area}"/>
        </td>
        <td><div align="right">邮政编码<font color="red">▲</font></div></td>
        <td><input type="text" name=vatNum value="${main.vatNum}"/></td>
      </tr>
      <tr>
        <td><div align="right">电话:</div></td>
        <td><input type="text" name="phone" value="${main.phone}"/></td>
        <td><div align="right">电话分机:</div></td>
        <td><input type="text" name="phoneExt" value="${main.phoneExt}"/></td>
      </tr>
      <tr>
        <td><div align="right">传真:</div></td>
        <td><input type="text" name="fax" value="${main.fax}"/></td>
        <td><div align="right">联系人:</div></td>
        <td><input type="text" name="contactMan" value="${main.contactMan}"/></td>
      </tr>
      
      <tr>
        <td><div align="right">EMAI:</div></td>
        <td><input type="text" name="email" value="${main.email}"/></td>
       	<td><div align="right">类型:<font color="red">▲</font></div></td>
        <td>
         <select id="type" name="type" tabindex="27">
				<option value="">请选择</option>
				<option value="业务"
					<c:if test="${main.type=='业务'}"> selected </c:if>>业务</option>
				<option value="物流"
					<c:if test="${main.type=='物流'}"> selected </c:if>>物流</option>			
					
			</select>
        </td>
      </tr>
       <tr>
       	<td><div align="right">是否为信达系统内供应商:<font color="red">▲</font></div></td>
      <td>
         <select id="inside" name="inside" tabindex="27">
				<option value="">请选择</option>
				<option value="是"
					<c:if test="${main.inside=='是'}"> selected </c:if>>是</option>
				<option value="否"
					<c:if test="${main.inside=='否'}"> selected </c:if>>否</option>			
					
			</select>
        </td>
        <td/>
        <td/>
      </tr>
       <tr>   
        <td width="900" colspan="4" align="center"><div align="center" ><font  size=5><b>SAP主数据信息</b></font></div></td>        
     </tr>   
      <tr>
        <td width="150"><div align="right">账户组<font color="red">▲</font></div></td>
        <td width="300"><div id="div_AccountGroup"></div></td>
        <td width="180"><div align="right">采购组织<font color="red">▲</font></div></td>
        <td width="270"><div id="div_PurchaseOrg"></div></td>
      </tr>
      <tr>
        <td><div align="right">公司代码<font color="red">▲</font></div></td>
        <td><div id="div_companyCode"></div></td>
        <td><div align="right">标题:</div></td>
        <td><input type="text" name="title" value="${main.title}"/></td>
      </tr>
      <tr>
        <td><div align="right">订单货币<font color="red">▲</font></div></td>
        <td><div id="div_currency"></div></td>
        <td><div align="right">统驭科目<font color="green">●</font></div></td>
        <td><div id="div_subjects"></div></td>
      </tr>
      <tr>
         <td><div align="right">现金组管理<font color="green">●</font></div></td>
        <td><div id="div_cashGroup"></div></td>
        <td><div align="right">付款方式<font color="red">▲</font></td>
        <td><div id="div_payTerm"></div></td>
      </tr>
      <tr>
        <td><div align="right">付款条件<font color="red">▲</font></div></td>
        <td><input type="text" name="payWay" value="${main.payWay}"/><input type="button" name="btn2" value="..." onclick="selectPayTerm()"/></td>
         <td><div align="right">对应客户编码
        </div></td>
        <td><input type="text" name="sapGuestCode" value="${main.sapGuestCode}"/><input type="button" name="btn1" value="..." onclick="selectSupply()"/></td>
      </tr>
     
      <tr>
        <td><div align="right">备注</div></td>
        <td colspan="3" ><textarea name="cmd" rows="2" cols="100" style="overflow-y:visible;word-break:break-all;">${main.cmd}</textarea></td>
        </tr>
         <tr>
        <td width="900" colspan="4" align="center"><div align="center" ><font  size=5><b>注册资料</b></font></div></td>        
     </tr>
      <tr>
         <td><div align="right">税务登记证号:<font color="red">▲</font></div></td>
        <td ><input type="text" name="zipCode" value="${main.zipCode}"/></td>
       	<td><div align="right">营业执照号:<font color="red">▲</font></div></td>
        <td ><input type="text" name="businessNo" value="${main.businessNo}"/></td>
      </tr>
     <tr>
        <td><div align="right">成立时间:<font color="red">▲</font></div></td>
        <td><input type="text" name="formed" value="${main.formed}"/></td>
         <td><div align="right">注册资本（单位万元）:<font color="red">▲</font></div></td>
        <td><input type="text" name="capital" value="${main.capital}"/></td>
        </tr>
         <tr>
        <td><div align="right">公司性质:<font color="red">▲</font></div></td>
        <td>
        <select id="nature" name="nature" tabindex="27">
				<option value="">请选择</option>
				<option value="国有"
					<c:if test="${main.nature=='国有'}"> selected </c:if>>国有</option>
				<option value="民营"
					<c:if test="${main.nature=='民营'}"> selected </c:if>>民营</option>
					<option value="合资"
					<c:if test="${main.nature=='合资'}"> selected </c:if>>合资</option>
					<option value="外资"
					<c:if test="${main.nature=='外资'}"> selected </c:if>>外资</option>
			</select>
        </td>
         <td><div align="right">公司类型:<font color="red">▲</font></div></td>
        <td><input type="text" name="companytype" value="${main.companytype}"/></td>
         
        </tr>
         <tr>
        <td><div align="right">经营期限:<font color="red">▲</font></div></td>
        <td ><table><tr><td><input type="text" name="periodStart" value="${main.periodStart}"/></td>
      <td >至</td>
      <td ><input type="text" name="periodEnd" value="${main.periodEnd}"/></td></tr></table>
        </td>
         <td><div align="right">营业执照最新年检年度:<font color="red">▲</font></div></td>
        <td><input type="text" name="annualInspection" value="${main.annualInspection}"/></td>
        </tr>
          <tr>
        <td><div align="right">组织机构代码:<font color="red">▲</font></div></td>
        <td><input type="text" name="code" value="${main.code}"/></td>
       	 <td><div align="right">注册资本币别<font color="red">▲</font></div></td>
        <td><div id="div_capitalCurrency"></div></td>
      </tr>
      <tr>
         <td width="900" colspan="4" align="center"><div align="center" ><font  size=5><b>其他信息(营业规模,财务状况,合作历史,履约情况等信息)</b></font></div></td>        
     </tr>
      <tr>
        <td><div align="right">其他信息:<font color="red">▲</font></div></td>
        <td colspan="3"><textarea name="otherinfo" rows="10" cols="100" style="overflow-y:visible;word-break:break-all;" >${main.otherinfo}</textarea></td>
        </tr>
     
      <tr>
          <td colspan="4" align="center">
          <table><tr><td><div id="save"><input type="button"  name="save" value="保存" onclick="saveForm()"/></div></td>
        <td><div id="commit"><input type="button" name="commit" value="提交" onclick="commitForm()"/></div></td>
        <td><div id="close"><input type="button" name="close" value="关闭" onclick="closeForm()"/></div></td>
        </tr></table>
          <input type="hidden" name="suppliersId" value="${main.suppliersId}"/>
          	<input type="hidden" name="examineState" value="${main.examineState}"/>
          	<input type="hidden" name="approvedTime" value="${main.approvedTime}"/>
          	<input type="hidden" name="isAvailable" value="${main.isAvailable}"/>
          	<input type="hidden" name="creatorTime" value="${main.creatorTime}"/>
          	<input type="hidden" name="creator" value="${main.creator}"/>  
          	<input type="hidden" name="deptId" value="${main.deptId}"/>     
          	<input type="hidden" name="licensePath" value="${main.licensePath}"/>  
          	<input type="hidden" name="organizationPath" value="${main.organizationPath}"/>
          	<input type="hidden" name="taxationPath" value="${main.taxationPath}"/>
          	<input type="hidden" name="surveyPath" value="${main.surveyPath}"/>
          	<input type="hidden" name="financialPath" value="${main.financialPath}"/>
          		<input type="hidden" name="creditPath" value="${main.creditPath}"/>  
          	<input type="hidden" name="otherPath" value="${main.otherPath}"/>  
        </td>
        </tr>
      
    </table></td>
  </tr>
</table>
</form>
</div>
<div id="rule" class="x-hide-display" >

<table width="900" border="0" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
  <tr>
    <td width="900" height="100%" valign="top"><table width="900" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
  <tr>
    
    	<td colspan="2" align="right"> 企业法人营业执照<font color="red">▲</font></td>
    	<td  ><input type="text"  name="license"  id="license" value="${main.licensePath}"  readonly="true"/><input id="up1" type="button" value="上传" onclick="upload('license')"/></td>
    	<td ><input type="button" value="下载" onclick="download('license')"/><input type="button" id="del1" value="删除" onclick="delFile('license')"/></td>
    	<td></td>
    
    </tr>
	<tr>
	
    	<td colspan="2" align="right"> 组织机构代码证<font color="red">▲</font></td>
    	<td  ><input type="text"  name="organization"  id="organization" value="${main.organizationPath}" readonly="true"/><input id="up2" type="button" value="上传" onclick="upload('organization')"/></td>
    	<td ><input type="button" value="下载" onclick="download('organization')"/><input type="button" id="del2" value="删除" onclick="delFile('organization')"/></td>
    	<td></td>
    	
    </tr>
    <tr>
   
    	<td colspan="2" align="right"> 税务登记证<font color="red">▲</font></td>
    	<td  ><input type="text"  name="taxation"  id="taxation" value="${main.taxationPath}"  readonly="true"/><input id="up3" type="button" value="上传" onclick="upload('taxation')"/></td>
    	<td ><input type="button" value="下载" onclick="download('taxation')"/><input type="button" id="del3" value="删除" onclick="delFile('taxation')"/></td>
    	<td></td>
    	
    </tr>
    <tr>
    
    	<td colspan="2" align="right"> 资信调查报告<font color="red"></font></td>
    	<td  ><input type="text"  name="survey"  id="survey" value="${main.surveyPath}"  readonly="true"/><input id="up4" type="button" value="上传" onclick="upload('survey')"/></td>
    	<td ><input type="button" value="下载" onclick="download('survey')"/><input type="button" id="del4" value="删除" onclick="delFile('survey')"/></td>
    	<td></td>
    	
    </tr>
    <tr>
     
    	<td colspan="2" align="right"> 财务报表<font color="red"></font></td>
    	<td  ><input type="text"  name="financial"  id="financial" value="${main.financialPath}"  readonly="true"/><input id="up5" type="button" value="上传" onclick="upload('financial')"/></td>
    	<td ><input type="button" value="下载" onclick="download('financial')"/><input type="button" id="del5" value="删除" onclick="delFile('financial')"/></td>
    	<td></td>
    	
    </tr>
     <tr>
     
    	<td colspan="2" align="right"> 授信额度申请表<font color="red"></font></td>
    	<td  ><input type="text"  name="credit"  id="credit" value="${main.creditPath}"  readonly="true"/><input id="up7" type="button" value="上传" onclick="upload('credit')"/></td>
    	<td ><input type="button" value="下载" onclick="download('credit')"/><input type="button" id="del7" value="删除" onclick="delFile('credit')"/></td>
    	<td></td>
    	
    </tr>
    <tr>
     
    	<td colspan="2" align="right"> 其他</td>
    	<td  ><input type="text"  name="other"  id="other" value="${main.otherPath}"  readonly="true"/><input id="up6" type="button" value="上传" onclick="upload('other')"/></td>
    	<td ><input type="button" value="下载" onclick="download('other')"/><input type="button" id="del6" value="删除" onclick="delFile('other')"/></td>
    	<td></td>
    
    </tr>
 	
  </table>
    </td>
    </tr>
    
</table>
</div>
<div id="div_history" class="x-hide-display"></div>
</body>

<script type="text/javascript">
	var combo,combo2;
	var win;
	var uploadform;
	var boname;
   	Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	var ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'masterQueryController.spr?action=rtnFindContry'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'ID'},
            		{name:'TITLE'},
            		{name:'CMD'}									
          		])
    });
    var ds1 = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'masterQueryController.spr?action=rtnFindArea'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'ID'},
            		{name:'TITLE'},
            		{name:'CMD'}									
          		])
    });
       ds.load();
       ds1.load();
        combo = new Ext.form.ComboBox({ 
        store: ds, 
        displayField:'TITLE', 
        valueField: 'ID' , 
        typeAhead: true, 
        mode: 'local', 
        triggerAction: 'query', 
        emptyText:'请选择...', 
        applyTo:'country',
        selectOnFocus:true 
        }); 
        combo2 = new Ext.form.ComboBox({ 
        store: ds1, 
        displayField:'TITLE', 
        valueField: 'ID' , 
        typeAhead: true, 
        mode: 'local', 
        triggerAction: 'query', 
        emptyText:'请选择...', 
        applyTo:'area',
        selectOnFocus:true 
        });
        combo.on('select', function() { 
        combo2.reset();
        ds1.proxy= new Ext.data.HttpProxy({url: 'masterQueryController.spr?action=rtnFindArea&id=' + combo.getValue()});
        ds1.load(); 
    });
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
            {contentEl:'rule',
               id:'fileEl', 
               title: '附件信息',
               autoScroll:'true'
            }    ,
            {contentEl:'div_history', title: '审批历史记录'}
        ]
    });   	
	 
  	 
  	  uploadform = new Ext.form.FormPanel({
		baseCls: 'x-plain',
		labelWidth: 80,
		url:'customerMasterController.spr?action=upload',
		fileUpload:true,
		defaultType: 'textfield',

		items: [{
			xtype: 'textfield',
			fieldLabel: '文件名',
			name: 'uploadFile',
			inputType: 'file',
			allowBlank: false,
			startValue:'',
			blankText: '文件不能为空.',
			anchor: '90%'  
		}]
	});

	 win = new Ext.Window({
		title: '上传文件',
		width: 400,
		height:200,
		minWidth: 300,
		minHeight: 100,
		layout: 'fit',
		plain:true,
		bodyStyle:'padding:5px;',
		buttonAlign:'center',
		items: uploadform,

		buttons: [{
			text: '上传',
			handler: function() {
				if(uploadform.form.isValid()){
					Ext.MessageBox.show({
						   title: '请等待..',
						   msg: '上传中...',
						   progressText: '',
						   width:300,
						   progress:true,
						   closable:false,
						   animEl: '加载中'
					   });
					uploadform.getForm().submit({    
						url:uploadform.url,
						success: function(form, action){
						     new AjaxEngine(encodeURI('<%=Constants.FILE_LOCATION_URL%>/attachementController.spr?action=getFilePath&id=' + Ext.getDom('suppliersId').value + '&boname=' + boname),
						   {method:"post", parameters: '',onComplete: upcallBackHandle});
						},    
						waitMsg: '正在上传文件...',
					   failure: function(){    
						  Ext.Msg.alert('错误', '上传错误.');    
					   }
					})		       
				}
		   }
		},{
			text: '关闭',
			handler:function(){win.hide();}
		}]
	});
	
	var addType = '${main.addType}';
  	 if(addType=='add'){
  	  	 Ext.getDom('add').checked='checked';
  	  	 addRadio();
  	 } 	 
  	 else if(addType=='update'){
  		Ext.getDom('update').checked='checked';
  		$('suppliersCode').readOnly=true;
  	 }	 else if(addType=='update2'){
  		Ext.getDom('update2').checked='checked';
  		updateRadio2();
  		$('suppliersCode').readOnly=true;
  	 }else{
  		 Ext.getDom('add').checked='checked';
  	  	 addRadio();
  	 }		
 	var type='${Type}';
    if(type == "true")
    {  
    	disabledText(true);
    	disableDropSelection(true);
   	 	$('save').innerHTML='';
   	 	$('commit').innerHTML='';
   	 	$('close').innerHTML='';
   	 	$('cmd').readOnly=true;
  	 	$('add').disabled=true;
   	 	$('update').disabled=true;
   	 	if($('btn3')!=null){
   	 		$('btn3').disabled=true;
   	   	 }
   	   	 $('up1').disabled=true;
   	 	$('up2').disabled=true;
   	 	$('up3').disabled=true;
   	 	$('up4').disabled=true;
   	 	$('up5').disabled=true;
   	 	$('up6').disabled=true;
   	 	$('up7').disabled=true;
   	 	$('del1').disabled=true;
   	 	$('del2').disabled=true;
   	 	$('del3').disabled=true;
   	 	$('del4').disabled=true;
   	 	$('del5').disabled=true;
   	 	$('del6').disabled=true;
   	 	$('del7').disabled=true;
   	 }
 	 var modify = '${modify}';
  	 if(modify=='workflow'){
   	 		$('add').disabled=true;
   	 		$('update').disabled=true;
    	 	$('commit').innerHTML='';
       	 	$('close').innerHTML='';
  	 }
  	 if($('otherinfo').value=='') $('otherinfo').value ='\r\n\r\n\r\n\r\n';
  	 var nf= new Ext.form.NumberField({  
                fieldLabel:'小数',  
                allowDecimals : true,//不允许输入小数  
                allowNegative : false,//不允许输入负数  
                nanText :'请输入有效的整数或小数',//无效数字提示  
                applyTo:'capital'
            });
     if(dict_div_capitalCurrency.getSelectedValue() ==''){
     	dict_div_capitalCurrency.setComboValue('CNY');//货币
     }
});

		var operateType='';
		
	function upcallBackHandle(xhr){
    	
	    var promptMessagebox = new MessageBoxUtil();
		promptMessagebox.Close();
		var responseUtil = new AjaxResponseUtils(xhr.responseText);
		var result = responseUtil.getCustomField("coustom");
    	 var pa = boname+"Path";
    	Ext.getDom(boname).value =  result.path;
		Ext.getDom(pa).value =  result.path;
	  operateType='save';
        var parm="?action=save&"+Form.serialize('mainForm')+'&update=${modify}';
		new AjaxEngine('supplyController.spr',{method:"post", parameters: parm,onComplete: callBackHandle2});
    }
    
    function callBackHandle2(xhr){
   	   Ext.Msg.alert('上传成功','上传成功');
	   win.hide();  
   	}
   	
   	
   
    function saveForm()
    {
    	if($('suppliersCode').value !=''){
    		var suppliersCode =$('suppliersCode').value;
    		if(suppliersCode.length != 10){
    			Ext.Msg.alert('提示','供应商编号不足十位数请在前面补0，');
    			return;
    		}
    	}
    	if(!checkData()){
    		Ext.Msg.alert('提示','请填写必填项！包括上传附件');
    		return;
    	}
    	operateType='save';
        var parm="?action=save&"+Form.serialize('mainForm')+'&update=${modify}';
		new AjaxEngine('supplyController.spr',{method:"post", parameters: parm,onComplete: callBackHandle});
    }
    function commitForm()
    {
    	if(!checkData('commit')){
    		Ext.Msg.alert('提示','请填写必填项！包括上传附件');
    		return;
    	}
    	operateType='submit';
        var parm="?action=saveAndsubmit&"+Form.serialize('mainForm');
		new AjaxEngine('supplyController.spr',{method:"post", parameters: parm,onComplete: callBackHandle});
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
	function selectPayTerm(){
		top.ExtModalWindowUtil.show('查找付款方式','masterQueryController.spr?action=toFindPayTerm','',payTermCallback,{width:755,height:450});
    }
    function payTermCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('payWay').value=cb;
    }
    function selectSupply(){
		top.ExtModalWindowUtil.show('查找客户','masterQueryController.spr?action=toFindCustomer','',supplyCallback,{width:755,height:450});
    }
    function supplyCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('sapGuestCode').value=cb.KUNNR;
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
    function addRadio(){
    	$('addType').value='add';
    	disabledText(false);
    	disableDropSelection(false);
        $('updateDiv').innerHTML='<input type="text" name="suppliersCode" value="${main.suppliersCode}"/>';
     }
    function updateRadio(){
        $('addType').value='update';
    	disabledText(true);
    	disableDropSelection(true);
    	$('updateDiv').innerHTML='<input type="text" name="suppliersCode" value="${main.suppliersCode}" readOnly="readOnly"/><input type="button" id="btn3" value="..." onclick="selectSupplier()"></input>';
        $('suppliersName1').readOnly=false;
    }
    function updateRadio2(){
       disabledText(true);
    	disableDropSelection(true); 
        dict_div_SaleGroup.disable(true);
        dict_div_companyCode.disable(true);
        $('type').disable=true;    
        $('type').readOnly=true;        
        formed.disabled=true;
        periodStart.disabled=true;
        periodEnd.disabled=true;        
        $('otherinfo').readOnly=true;
        $('cmd').readOnly=true;
    }
    function disabledText(bool){
   	 	var arr= Ext.query('input[type=text]');
   	 	for(var i=0;i<arr.length;i++){
   	 		arr[i].readOnly=bool;
   	 	}
    }
    
    function supplierCallback(){
    	var cb = top.ExtModalWindowUtil.getReturnValue();
    	$('suppliersCode').value=cb.LIFNR;//供应商编码
    	$('suppliersName1').value=cb.NAME1;//供应商名称1
    }
    function selectSupplier(){
    	top.ExtModalWindowUtil.show('查找供应商','masterQueryController.spr?action=toFindSupplier','',supplierCallback,{width:755,height:450});
    }
   
   
	
	//日期
   	var formed = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'formed',
	    name:'formed',
		width: 160,
	    editable:false,
		applyTo:'formed'
   	});   	
   	//日期
   	var periodStart = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'periodStart',
	    name:'periodStart',
	    editable:false,
		width: 160,
		applyTo:'periodStart'
   	});  	
   	var periodEnd = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'periodEnd',
	    name:'periodEnd',
	    editable:false,
		width: 160,
		applyTo:'periodEnd'
   	}); 
   	
   		var annualInspection = new Ext.form.DateField({
   		format:'Y',
		id:'annualInspection',
	    name:'annualInspection',
		width: 160,
		applyTo:'annualInspection'
   	}); 
   	
   	function checkData(type){
		if(Ext.getDom('suppliersName1').value == "") return false ;
		if(Ext.getDom('street').value == "") return false ;
		if(Ext.getDom('country').value == "") return false ;
		if(Ext.getDom('area').value == "") return false ;
		if(Ext.getDom('vatNum').value == "") return false ;
		if(Ext.getDom('type').value == "") return false ;
		if(Ext.getDom('inside').value == "") return false ;
		
		if(dict_div_PurchaseOrg.getComboValue == "") return false ;
		if(dict_div_companyCode.getComboValue == "") return false ;
		if(dict_div_payTerm.getComboValue == "") return false ;
		if(dict_div_currency.getComboValue == "") return false ;		
		if(Ext.getDom('payWay').value == "") return false ;
		var account = dict_div_AccountGroup.getSelectedValue();
	//	if(dict_div_AccountGroup.getComboValue == "") return false ;	
	//	if(dict_div_cashGroup.getComboValue == "") return false ;	
	//	if(dict_div_subjects.getComboValue == "") return false ;	
		if(type=='commit' || Ext.getDom('suppliersId').value != ""){
		//如果是个人或员工不检查
			if( account == '8000' || account == '9000' )return true;
			if(Ext.getDom('country').value == '中国' && Ext.getDom('inside').value =='否'){
				if(Ext.getDom('licensePath').value == "") return false ;
				if(Ext.getDom('organizationPath').value == "") return false ;
				if(Ext.getDom('taxationPath').value == "") return false ;
				
				if(Ext.getDom('formed').value == "") return false ;
				if(Ext.getDom('capital').value == "") return false ;
				if(Ext.getDom('nature').value == "") return false ;
				if(Ext.getDom('companytype').value == "") return false ;
				if(Ext.getDom('periodStart').value == "") return false ;
				if(Ext.getDom('periodEnd').value == "") return false ;
				if(Ext.getDom('annualInspection').value == "") return false ;
				if(Ext.getDom('businessNo').value == "") return false ;
				if(Ext.getDom('zipCode').value == "") return false ;
				if(Ext.getDom('code').value == "") return false ;
			}
		//if(Ext.getDom('surveyPath').value == "") return false ;
		//if(Ext.getDom('financialPath').value == "") return false ;
		}
		return true;
	}
	
	function upload(id){
	boname = id;			
		if(Ext.getDom('suppliersId').value == ""){
			top.ExtModalWindowUtil.alert('提示',"请先保存主数据在上传附件！");	
		}else{
			var url ='<%= request.getContextPath() %>/attachementController.spr?action=uploadManger&id=' + Ext.getDom('suppliersId').value + '&userId=' + Ext.getDom('creator').value + '&boname=' + boname;
			var fileUrl = '<%=Constants.FILE_LOCATION_URL%>';
			//正式系统没有端口号，加上80
			if(fileUrl.indexOf(":80") == -1){
					fileUrl = fileUrl + ":80";
			}
			var sername = '<%= request.getServerName() %>';
			var port ='<%= request.getServerPort() %>';
			if(fileUrl.indexOf(sername) != -1 && fileUrl.indexOf(port) != -1){
				win.show();
				uploadform.url=encodeURI('<%=Constants.FILE_LOCATION_URL%>/attachementController.spr?action=upload3&cburl=http://<%= request.getServerName() %>:<%= request.getServerPort() %><%= request.getContextPath() %>/attachementController.spr&callback=upcallBackHandle&id=' + Ext.getDom('suppliersId').value + '&userId=' + Ext.getDom('creator').value + "&boname=" + boname);
			}else{
				var path=openwindow(url,'上传文件',300,200);
				if(path != '' && typeof(path) != 'undefined' ){
					
					upcallBack(path);
				}	
			}
		}
	}
	
	function upcallBack(path){    
    	 var pa = boname+"Path";
    	Ext.getDom(boname).value =  path;
		Ext.getDom(pa).value =  path;
	  operateType='save';
	        var parm="?action=save&"+Form.serialize('mainForm')+'&update=${modify}';
			new AjaxEngine('supplyController.spr',{method:"post", parameters: parm,onComplete: callBackHandle2}); 
    }
	function openwindow(url,name,iWidth,iHeight)
	{
	var url; //转向网页的地址;
	var name; //网页名称，可为空;
	var iWidth; //弹出窗口的宽度;
	var iHeight; //弹出窗口的高度;
	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	//window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=no,resizeable=no,location=no,status=no,depended=no');
	var msg=window.showModalDialog(url,name,'dialogHeight:'+iHeight+',dialogWidth:'+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=no,resizeable=no,location=no,status=no,depended=no');
		return msg;
	}
	
	
	function download(id){
	 var fileName = Ext.getDom(id).value;
	 if(fileName !=''){
	 var url=  encodeURI('<%=Constants.FILE_LOCATION_URL%>/attachementController.spr?action=proceeFileDownload&path='+ id +'&fileName=') + fileName ;
	 top.location.href(url);
	 }
	}
	function delFile(id){
		var creator = Ext.getDom('creator').value;
		var suppliersId = Ext.getDom('suppliersId').value;
		var fileName = Ext.getDom(id).value;
		var path = id+'Path';
		if(fileName !=''){
			if(creator ==''){		
				Ext.getDom(id).value = '';
				Ext.getDom(path).value = '';
				top.ExtModalWindowUtil.alert('提示',"删除成功！");	
			}else{
				var fileUrl = '<%=Constants.FILE_LOCATION_URL%>';
				//正式系统没有端口号，加上80
				if(fileUrl.indexOf(":80") == -1){
						fileUrl = fileUrl + ":80";
				}
				var sername = '<%= request.getServerName() %>';
				var port ='<%= request.getServerPort() %>';
				if(fileUrl.indexOf(sername) != -1 && fileUrl.indexOf(port) != -1){
					Ext.Ajax.request({
						url: encodeURI('<%=Constants.FILE_LOCATION_URL%>/attachementController.spr?action=proceeFileDelete&path='+id+'&fileName=' + fileName + '&creator=' + creator +'&masterid=') + suppliersId,
						params:'',
						scriptTag: true,
						success: function(response){
							new AjaxEngine('supplyController.spr?action=delAtt&id=' + Ext.getDom('suppliersId').value + "&boname=" + id,{method:"post", parameters: '',onComplete: delcallBackHandle});					
						}	
					});
				}else{
					Ext.getDom(id).value = '';
					Ext.getDom(path).value = '';
					new AjaxEngine('supplyController.spr?action=delAtt&id=' + Ext.getDom('suppliersId').value + "&boname=" + id,{method:"post", parameters: '',onComplete: delcallBackHandle});
				}
			}
		}
	}
	
	function delcallBackHandle(response){
		var promptMessagebox = new MessageBoxUtil();
		promptMessagebox.Close();
		var responseUtil1 = new AjaxResponseUtils(response.responseText);
		var customField = responseUtil1.getCustomField("coustom");
		if(customField.success){
		var id = customField.boname;
		var path = id+'Path';
			Ext.getDom(id).value = '';
			Ext.getDom(path).value = '';
		}
		top.ExtModalWindowUtil.alert('提示',customField.msg);
	}
	
	function disableDropSelection(bool){
		dict_div_AccountGroup.disable(bool);
		dict_div_PurchaseOrg.disable(bool);
	    dict_div_companyCode.disable(bool);
	    dict_div_cashGroup.disable(bool);
	    dict_div_subjects.disable(bool);
	    dict_div_currency.disable(bool);
	    dict_div_payTerm.disable(bool);
	    $('btn1').disabled=bool;
	    $('btn2').disabled=bool;
	    if(bool){
	   	 	combo.disable();
	    	combo2.disable();
	    }else{
	   	 	combo.enable();
	    	combo2.enable();
		}
    }
    
</script>
</html>
<fiscxd:workflow-taskHistory divId="div_history" businessRecordId="${main.suppliersId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:dictionary divId="div_AccountGroup" fieldName="accountGroup" dictionaryName="BM_ACCOUNT_GROUP" selectedValue="${main.accountGroup}" width="153" disable="${Type}" needDisplayCode="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_PurchaseOrg" fieldName="purchaseOrg" dictionaryName="BM_PURCHASE_ORG" selectedValue="${main.purchaseOrg}" width="153" disable="${Type}" needDisplayCode="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_companyCode" fieldName="companyCode" dictionaryName="Bm_Company_Code" selectedValue="${main.companyCode}" width="153" disable="${Type}" needDisplayCode="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_subjects" fieldName="subjects" dictionaryName="BM_SUBJECT" selectedValue="${main.subjects}" width="153" disable="${Type}" needDisplayCode="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_currency" fieldName="currency" dictionaryName="BM_CURRENCY" selectedValue="${main.currency}" width="153" disable="${Type}"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_cashGroup" fieldName="cashGroup" dictionaryName="BM_CASH_GROUP" selectedValue="${main.cashGroup}" width="153" disable="${Type}" needDisplayCode="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_payTerm" fieldName="payTerm" dictionaryName="BM_PAYMENT_TYPE" selectedValue="${main.payTerm}" width="153" disable="${Type}" needDisplayCode="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_capitalCurrency" fieldName="capitalCurrency" dictionaryName="BM_CURRENCY" selectedValue="${main.capitalCurrency}" width="153" disable="${Type}"></fiscxd:dictionary>
