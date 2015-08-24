<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投资管理</title>
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
        <td align="right" width="300">投资类型<font color="red">▲<font></td>
        <td align="left">
        	<select name="investmentType" ${readable==true?"disabled=true":""}>
        		<option value="">请选择</option>
        		<option value="A">A 类</option>
        		<option value="B">B 类</option>
        	</select>
        </td>
      </tr>
      <tr>
        <td align="right">审批子类<font color="red">▲<font></td>
        <td align="left">
        	<div id="childTypeDiv"></div>
        </td>
      </tr>
      <tr>
		<td align="right">子流程<font color="red">▲<font></td>
		<td>
			<div id="subFlowDiv"></div>
		</td>
      </tr>
     <tr>
		<td align="right">创建日期</td>
		<td>
			<input type="text" name="createTime" value="${createTime}" readonly="readonly"/>
		</td>
      </tr>
     <tr>
		<td align="right">生效日期</td>
		<td>
			<input type="text" name="availableTime" value="${info.availableTime}" readonly="readonly"/>
		</td>
      </tr>
      <tr>
        <td align="right">申请部门</td>
        <td align="left">
           <input type="text" name="deptName" value="${deptName}" readonly="readonly"/>
        </td>
      </tr>
      
      <tr>
      <td align="right">备注:</td>
      <td colspan="5">
      	<textarea rows="3" name="cmd" style="width:600;overflow-y:visible;word-break:break-all;" ${readable==true?"readonly='readonly'":''}>${info.cmd}</textarea>
      </td>
      </tr>
      </table>
      		<input type="hidden" name="infoId" value="${info.infoId}"/>
 </form>
</div>
<div id="paymentInfo" ></div>
<div id="paymentInfoDiv" class="x-hide-display">
<form id="paymentInfoForm" name="paymentInfoForm">
	 <input type="hidden" name="ipId" value="${payment.ipId}"/>
	 <table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
	 <tr>
	 	<td width="100" align="right">申请时间:</td>
	 	<td width="150"> <input type="text" name="applyTime" value="${payment.applyTime}"/></td>
	 	<td width="100" align="right">财务编号:</td>
	 	<td width="150"><input type="text" name="accountCode" value="${payment.accountCode}"/></td>
	 </tr>
	 <tr>
	 	<td align="right">申请部门:</td>
	 	<td><input type="text" name="applyDept" value="${payment.applyDept}"/></td>
	 	<td align="right">申请人:</td>
	 	<td><input type="text" name="applier" value="${payment.applier}"/></td>
	 </tr>
	 <tr>
	 	<td align="right">付款账号:</td>
	 	<td><input type="text" name="paymentAccount" value="${payment.paymentAccount}"/></td>
	 	<td align="right">到期日期:</td>
	 	<td><input type="text" name="expireDate" value="${payment.expireDate}"/></td>
	 </tr>
	 <tr>
	 	<td align="right">申请付款金额:</td>
	 	<td><input type="text" name="applyCount" value="${payment.applyCount}"/></td>
	 	<td align="right">付款形式:</td>
	 	<td>现金</td>
	 </tr>
	 <tr>
	 	<td colspan="1" align="right">付款用途:</td>
	 	<td colspan="3">
	 		<textarea rows="2" cols="60" name="paymentUse">${payment.paymentUse}</textarea>
	 	</td>
	 </tr>
	 <tr>
	 	<td align="right">收款单位:</td>
	 	<td><input type="text" name="incomeCompany" value="${payment.incomeCompany}"/></td>
	 	<td align="right">开户银行:</td>
	 	<td><input type="text" name="openBank" value="${payment.openBank}"/></td>
	 </tr>
	 <tr>
	 	<td align="right">收款单位账户:</td>
		<td colspan="3">
			<input type="text" name="incomeAccount" value="${payment.incomeAccount}"/>
		</td>
	 </tr>
	 <tr>
	 	<td align="right">付款人:</td>
	 	<td><input type="text" name="payer" value="${payment.payer}"/></td>
	 	<td align="right">付款日期:</td>
	 	<td><input type="text" name="paymentDate" value="${payment.paymentDate}"/></td>
	 </tr>
	 <tr>
	 	<td align="right">备注:</td>
		<td colspan="3">
			<textarea rows="2" cols="60" name="cmd">${payment.cmd}</textarea>
		</td>
	 </tr>
	 </table>
 </form>	
</div>
<div id="paymentBtnDiv" align="center" class="x-hide-display">
	<input type="button" value="保存付款信息" onclick="savePayment()"/>
</div>
<div id="paymentSumDiv" align="center" class="x-hide-display">
	<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
	<tr align="right">
		<td>总计:<input type="text" name="paymentTotal" value="${payment.paymentTotal}" id="paymentTotal" onclick="sumPaymentf()"/></td>
	</tr>
	</table>
</div>
<div id="investmentInfoGridDiv"></div>
<div id="fileDiv" class="x-hide-display"></div>
<div id="workflowHistory"  style="position: relative" class="x-hide-display"></div>
<fiscxd:fileUpload divId="fileDiv" erasable="false"  increasable="true" recordId="${info.infoId}"
 resourceId="2222" resourceName="33333" recordIdHiddenInputName="attacheFile"></fiscxd:fileUpload>
</body>

<script type="text/javascript">

var strOperType;
var tabs;
var itemStore;
var itemGrid;
var subFlow;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	$('attacheFile').value='${info.infoId}';
   	$('investmentType').value='${info.investmentType}';
   	fileDiv_ns_ds.load({params:{start:0,limit:5,recordId:'${info.infoId}'}});
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
    	            	param1=param1+'&childType='+childType.getValue()+'&subFlow='+subFlow.getValue()+'&examineType=${TYPE}';
    					var param = param1 + "&action=saveInfo";
    					new AjaxEngine('investmentController.spr', 
    							   {method:"post", parameters: param, onComplete: callBackHandle});
    				}
    			},{
                	text:'提交',
                	hidden:${info.infoId=="" || info.infoId==null || readable==true || disableBtn==true},
                	handler:function(){
	        	            if(fileDiv_ns_ds.getTotalCount()<1){
								top.Ext.Msg.alert('提示','请上传附件');
								return;
	            	        }
                    		if(subFlow.getValue().indexOf('付款')!=-1 && $('ipId').value==''){
								top.Ext.Msg.alert('提示','请填写付款信息');
								return;
                        	}
							top.Ext.Msg.show({
   								title:'提示',
   								msg: '是否确定提交审批',
   								buttons: {yes:'是', no:'否'},
   								fn: function(buttonId,text){
 									if(buttonId=='yes'){
 			                			strOperType = 'submit';
 			                			var param1 = Form.serialize('mainForm');
 			    						var param = param1  + "&action=firstSubmit&infoId=${infoId}";
 			    						new AjaxEngine('investmentController.spr', 
 			    						   {method:"post", parameters: param, onComplete: callBackHandle});
									}
   								},
   							    animEl: 'elId',
   								icon: Ext.MessageBox.QUESTION
							});
                		}
    			 },{
                	text:'关闭',
                	hidden:${readable==true || disableBtn==true},
                	handler:function(){
                		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
               			top.ExtModalWindowUtil.markUpdated();
   				 		top.ExtModalWindowUtil.close();
    	            }
                }]    
            },{
				title:'付款信息',
				id:'paymentEl',
				disabled:true,
				contentEl:'paymentInfo'
            },{
				title:'附件信息',
				disabled:${info.infoId=="" || info.infoId==null},
				contentEl:'fileDiv',
				id:'fileEl',
				listeners:{
					activate:handlerActivate
				}
            },{
				title:'审批信息',
				contentEl:'workflowHistory',
				listeners:{
					activate:handlerWorkflowActivate
				}
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
	var d1 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:"applyTime",
		name:"applyTime",
		width: 150,
		disabled:${readable==true},
		applyTo:'applyTime'
   	});
	var d3 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:"expireDate",
		name:"expireDate",
		width: 150,
		disabled:${readable==true},
		applyTo:'expireDate'
   	});
	var d4 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:"paymentDate",
		name:"paymentDate",
		width: 150,
		disabled:${readable==true},
		applyTo:'paymentDate'
   	});
   	var childType_ds = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:'investmentController.spr?action=rtnChildTypeDS&module=${TYPE}'}),
		reader: new Ext.data.JsonReader({
    		root: 'root',
    		totalProperty: 'totalProperty'
    	},[
    		{name:'CHILD_TYPE'},
    		{name:'CHILD_TYPE_NAME'}
  		])
	});
	var subFlow_ds = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:'investmentController.spr?action=rtnSubFlowDS&module=${TYPE}'}),
		reader: new Ext.data.JsonReader({
    		root: 'root',
    		totalProperty: 'totalProperty'
    	},[
    		{name:'SUB_FLOW'},
    		{name:'SUB_FLOW_NAME'}
  		])
	});
	childType_ds.load();
	subFlow_ds.load();

   var childType = new Ext.form.ComboBox({ 
	   width :200,
        store: childType_ds, 
        displayField:'CHILD_TYPE_NAME', 
        valueField: 'CHILD_TYPE_NAME' , 
        typeAhead: true, 
        mode: 'local', 
        triggerAction: 'query', 
        emptyText:'请选择...', 
        renderTo:'childTypeDiv',
        disabled:${readable==true},
        selectOnFocus:true 
    }); 
   childType.setValue('${info.childType}');
   subFlow = new Ext.form.ComboBox({ 
	   width :200,
        store: subFlow_ds, 
        displayField:'SUB_FLOW_NAME', 
        valueField: 'SUB_FLOW_NAME' , 
        typeAhead: true, 
        mode: 'local', 
        triggerAction: 'query',
        emptyText:'请选择...', 
        renderTo:'subFlowDiv',
        disabled:${readable==true},
        selectOnFocus:true 
        });
   subFlow.setValue('${info.subFlow}');
   childType.on('select', function() { 
	   subFlow.reset();
	   subFlow_ds.proxy= new Ext.data.HttpProxy({url:encodeURI('investmentController.spr?action=rtnSubFlowDS&module=${TYPE}&childType=' + childType.getValue())});
	   subFlow_ds.load(); 
    });
/////////////////////////////////////////////////////////
	var paymentInfo=new Ext.form.FormPanel({
		applyTo:'paymentInfo',
		frame:true,
		baseCls:'x-plain',
		autoHeight:true,
		labelWidth:40,
		width:800,
		bodyStyle:'padding: 10px 10px 0 10px;',
		defaults:{
			anchor:'95%',
			msgTarget:'side'
		},
		items:[{
			contentEl:'paymentInfoDiv',
			title:'付款信息'
		},{
			contentEl:'investmentInfoGridDiv',
			title:'投资信息'
		},{
			contentEl:'paymentSumDiv'
		},{
			contentEl:'paymentBtnDiv',
			hidden:${readable==true}
		}]
	});
	itemStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:'investmentController.spr?action=findProject&ipId='+$('ipId').value}),
		reader: new Ext.data.JsonReader({
    		root: 'root',
    		totalProperty: 'totalProperty'
    	},[
    		{name:'PID'},
    		{name:'INVESTMENT_CODE'},
			{name:'PROJECT_TIME'},
			{name:'COUNT'}
  		])
	});
	itemStore.load();
    var itemSm = new Ext.grid.CheckboxSelectionModel();
    var itemCm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		itemSm,
		{
			header: 'ID',
            sortable: false,
            hidden:true,
            dataIndex: 'PID'
        },{
			header: '投资项目号',
            sortable: false,
            dataIndex: 'INVESTMENT_CODE'
        },{
			header: '项目时间',
            sortable: false,
            dataIndex: 'PROJECT_TIME'
        },{
			header: '金额',
            sortable: false,
            dataIndex: 'COUNT'
        }
    ]);
    itemCm.defaultSortable = true;
    var add = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
	    hidden:${readable==true},
		handler:function(){
			if($('ipId').value==''){
				top.Ext.Msg.alert('提示','请先保存付款信息!');
				return;
			}
			top.ExtModalWindowUtil.show('增加','investmentController.spr?action=addProject&ipId='+$('ipId').value,'',findProject,{closable:false,width:400,height:300});
		}
   	});
   	
   	var del = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
	    hidden:${readable==true},
		handler:function(){
   				if (itemGrid.selModel.hasSelection()){
					var records = itemGrid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
					}else{
							top.Ext.Msg.show({
   								title:'提示',
   								msg: '是否确定删除记录',
   								buttons: {yes:'是', no:'否'},
   								fn: function(buttonId,text){
   									strOperType = 'delProject';
 									if(buttonId=='yes'){
 	 									var parm ='?action=delProject&pid='+records[0].json.PID; 
 										new AjaxEngine('investmentController.spr',
 												 {method:"post", parameters: parm,onComplete: callBackHandle});
									}
   								},
   							    animEl: 'elId',
   								icon: Ext.MessageBox.QUESTION
							});
					}
					}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
				//selectWindow.hide();
		}
   	});

    var itemTbar = new Ext.Toolbar({
		items:[add,'-',del]
	});
    var paging = new Ext.PagingToolbar({
        pageSize: 10,
        store:itemStore,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: '没有记录'
    });

    itemGrid = new Ext.grid.GridPanel({
    	id:'investmentInfoGrid',
        ds: itemStore,
        cm: itemCm,
        sm: itemSm,
        bbar: paging,
        tbar: itemTbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        applyTo:'investmentInfoGridDiv',
        autowidth:true,
        height:150,
		viewConfig : {  
                forceFit : true,  
                enableRowBody:true,
                getRowClass : 
                	function(record,rowIndex,rowParams,store){ 
                		if(rowIndex%2==0)
                			return 'x-grid-row-bgcolor'; 
                		else
                			return '';	
                	}  
        },          
        layout:'fit'
    });
///initization///
	if('${info.subFlow}'.indexOf('付款')!=-1){
		tabs.getItem('paymentEl').enable();
	}
////////////////////////////    
 });//end of Ext.onReady  
function handlerActivate(tab){
	fileDiv_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('attacheFile').value}});
}
function handlerWorkflowActivate(tab){
	workflowHistory_grid.getStore().load();	
}
function findProject(){
	itemStore.proxy= new Ext.data.HttpProxy({url:'investmentController.spr?action=findProject&ipId='+$('ipId').value}),
	itemStore.load();
	//sumPaymentf();
}
function sumPaymentf(){
	var sum = 0;
	var i;
	for(i=0;i<itemStore.getCount();i++){
		sum = sum + parseFloat(itemStore.getAt(i).get('COUNT'));
	}	
	$('paymentTotal').value=sum;
}
//回调函数
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if (strOperType == 'save'){
		tabs.getItem('fileEl').enable();
		$('infoId').value=customField.INFO_ID;
		$('attacheFile').value=customField.INFO_ID;
		if(customField.enablePayment)
			tabs.getItem('paymentEl').enable();
	}else	
	if (strOperType == 'savePayment'){
		$('ipId').value=customField.IPID;
		sumPaymentf();
	}else 	
	if (strOperType == 'submit'){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
	    top.ExtModalWindowUtil.close();
	}else
	if(strOperType =='delProject'){
		findProject();
		sumPaymentf();
	}	
}
//
function savePayment(){
	strOperType='savePayment';
	var param='?action=savePayment&'+Form.serialize('paymentInfoForm')+'&infoId='+$('infoId').value+'&paymentTotal='+$('paymentTotal').value;
	new AjaxEngine('investmentController.spr', 
			   {method:"post", parameters: param, onComplete: callBackHandle});
}
</script>
</html>
<fiscxd:workflow-taskHistory divId="workflowHistory" businessRecordId="${info.infoId}" width="800"></fiscxd:workflow-taskHistory>
