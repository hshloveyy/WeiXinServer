<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内贸货款信息表</title>
<style type="text/css">
.add {
	background-image: url("<%= request.getContextPath() %>/images/fam/add.gif") !important;
}

.delete {
	background-image: url("<%= request.getContextPath() %>/images/fam/delete.gif") !important;
}

.update {
	background-image: url("<%= request.getContextPath() %>/images/fam/refresh.gif" ) !important;
}

.find {
	background-image: url("<%= request.getContextPath() %>/images/fam/find.png") !important;
}

  
</style>
<script language="JavaScript" src="<%=request.getContextPath()%>/js/lib/maxlength.js"></script>
<script type="text/javascript">
//操作类别  
var payMethod = '${main.payMethod}';
//贸易类型
var payType = '${main.payType}';
//是否可以提交
var submitHidden = ${submitHidden}; 
//是否可以保存
var saveDisabled = ${saveDisabled};
//保存时调用的函数
var saveFunction = '${saveFunction}';
//是否可以关闭
var closeHidden = ${closeHidden};

var strOperType = '';

var strTitle = '';
if (payType == '1') {
	switch(payMethod){
		case '1':
			strTitle = '国内信用证';
			break;
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '9':
			strTitle = '现金/背书/转账/电汇/网银/银行即期汇票';
			break;
		case '7':
			strTitle = '银行/商业承兑汇票';
			break;
	}
}else if(payType == '2'){
	if(payMethod=='2')
		strTitle = '期货保证金';
	else
		strTitle = '普通非货款';
}else if(payType == '3'){
	strTitle = '进口预付款';
}
if(${isRelateProject} && ${saveDisabled}==true){
//	$('selectProjectBtn').disabled=true;
}
document.title = '相关信息';
//付款合同相关信息
var innerPayContractInfogrid; 
var innerPayContractInfods;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	   	
    function addInnerPayContractCallBack(){
		var returnValues = top.ExtModalWindowUtil.getReturnValue();
		//var payValue = new Number(Ext.getDom('payValue').value);
		if (returnValues != null && returnValues.length > 0) {
			innerPayContractInfogrid.stopEditing();
			for (var i = 0; i < returnValues.length; i++) {
				var returnValue = returnValues[i].data;
				var p = new innerPayContractInfoPlant({
					CONTRACT_NO: returnValue.CONTRACT_NO,
					CONTRACT_TYPE: returnValue.CONTRACT_TYPE,
					CONTRACT_NAME: returnValue.CONTRACT_NAME,
					APPROVED_TIME: returnValue.APPROVED_TIME,
					PAPER_NO: returnValue.PAPER_NO,
					CONTRACT_PURCHASE_ID: returnValue.CONTRACT_PURCHASE_ID,
					SAP_ORDER_NO: returnValue.SAP_ORDER_NO,
					CMD: returnValue.CMD,
					ORDER_STATE_D_ORDER_STATE: returnValue.ORDER_STATE_D_ORDER_STATE,
					CONTRACT_INFO: returnValue.CONTRACT_INFO,
					TOTAL: returnValue.TOTAL
				});
				innerPayContractInfods.insert(innerPayContractInfods.getCount(), p);
				//payValue += new Number(returnValue.TOTAL);
			}
			//$('recBank').value = returnValues[0].data.PROVIDER;
		}
		innerPayContractInfogrid.startEditing(0, 0);
		Ext.getDom('payValue').value = payValue;
    }  

    //选择内贸合同
    var addInnerPayContract = new Ext.Toolbar.Button({
   		text:'选择合同',
	    iconCls:'add',
	    hidden:${saveDisabled},
		handler:function(){
		    var projectId = Ext.getDom('projectId').value;
		    if(projectId==''){
		       showMsg('请先选择立项信息！');
		       return;
		    }
			top.ExtModalWindowUtil.show(strTitle+'相关合同选择',
			'innerTradePayController.spr?action=selectInnerPayContract&tradeType=7&payType='+payType+'&projectId='+projectId+'&projectNo='+Ext.getDom('projectNo').value,
			'',
			addInnerPayContractCallBack,
			{width:800,height:500});
		}
   	});
   	//删除
   	var deleteInnerPayContract = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
	    hidden:${saveDisabled},
		handler:function(){
			if (innerPayContractInfogrid.selModel.hasSelection()){
				var records = innerPayContractInfogrid.selModel.getSelections();
				top.Ext.Msg.show({
					title:'提示',
						msg: '是否确定删除选择的记录!   ',
						buttons: {yes:'是', no:'否'},
						fn: function(buttonId,text){
							//var payValue = new Number(Ext.getDom('payValue').value);
							for (var i=0; i<records.length; i++){
								innerPayContractInfods.remove(records[i]);
								//payValue -= new Number(records[i].data.TOTAL);
							}
							//Ext.getDom('payValue').value = payValue;
						},
						icon: Ext.MessageBox.QUESTION
				});
		}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的相关合同！');
			}
		}
   	});
    
    var innerPayContractInfotbar = new Ext.Toolbar({
		items:[addInnerPayContract,'-',deleteInnerPayContract]
	});
	
   	var innerPayContractInfoPlant = Ext.data.Record.create([
		{name:'CONTRACT_NO'},				//合同号
		{name:'CONTRACT_TYPE'},				//合同类别
		{name:'CONTRACT_NAME'},				//合同名称
		{name:'PAY_VALUE'},				//付款金额
		{name:'PAPER_NO'},				//纸质合同号
		{name:'APPROVED_TIME'},				//合同时间
		{name:'CONTRACT_PURCHASE_ID'},		//采购合同ID
		{name:'SAP_ORDER_NO'},				//SAP订单号
		{name:'TOTAL'},						//金额
		{name:'CMD'},						//备注
		{name:'ORDER_STATE_D_ORDER_STATE'},	//审批状态
		{name:'CONTRACT_INFO'}				//详情
	]);

	innerPayContractInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'innerTradePayController.spr?action=queryPaymentContract&paymentId='
						+Ext.getDom('paymentId').value+"&payType="+payType}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},innerPayContractInfoPlant)
    });
    innerPayContractInfods.load();
    /*var innerPayContractInfobbar = new Ext.PagingToolbar({
        pageSize: 100,
        store:innerPayContractInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });*/
    var innerPayContractInfosm = new Ext.grid.CheckboxSelectionModel();
	var innerPayContractInfocm = new Ext.grid.ColumnModel([
	new Ext.grid.RowNumberer(),
	innerPayContractInfosm,
		{
			header: '合同类型',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_TYPE'
		},
		{
			header: '合同编号',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_NO'
		},
		{
			header: '合同名称',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_NAME'
		},
		{header: '<font color="red">▲</font>付款金额',
           width: 100,
           sortable: true,
           dataIndex: 'PAY_VALUE',
           editor: new Ext.form.NumberField({
        	   blankText:'付款金额必填',
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:3,
               maxValue: 999999999999
           })           
           },
		{
			header: '合同时间',
			width: 100,
			sortable: true,
			dataIndex: 'APPROVED_TIME'
		},
		{
			header: '纸质合同号',
			width: 100,
			sortable: true,
			dataIndex: 'PAPER_NO'
		},
		{
			header: '合同ID',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_PURCHASE_ID',
			hidden: true
		},
		{
			header: 'SAP订单号',
			width: 100,
			sortable: true,
			dataIndex: 'SAP_ORDER_NO'
		},
		{
			header: '合同金额',
			width: 100,
			sortable: true,
			dataIndex: 'TOTAL'
		},
		{
			header: '备注',
			width: 100,
			sortable: true,
			dataIndex: 'CMD'
		},
		{
			header: '合同状态',
			width: 100,
			sortable: true,
			dataIndex: 'ORDER_STATE_D_ORDER_STATE'
		},
		{
			header: '合同详情',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_INFO',
			renderer:contractOper
		}
	]);
    //付款合同相关信息
	innerPayContractInfogrid = new Ext.grid.EditorGridPanel({
    	id:'innerPayContractInfogrid',
        ds: innerPayContractInfods,
        cm: innerPayContractInfocm,
		sm: innerPayContractInfosm,
        //bbar: innerPayContractInfobbar,
        tbar: innerPayContractInfotbar,
        border:false,
        //hidden:${!isRelateProject}, 
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_innerPayContractInfogrid',
        autowidth:true,
		height:120,
        clicksToEdit:1,
        layout:'fit'
    });
   
   var toolbars = new Ext.Toolbar({
			items:[	' ','->',	{id:'_viewCredit',text:'查看信用额度',handler:_viewCredit,iconCls:'icon-view'}],
			renderTo:Ext.getBody()
	});
	
	/**
	 * 查看信用额度
	 * @return
	 */
	function  _viewCredit(){
		
		var shipId = '';//Ext.getDom("shipId").value;
		var projectId = Ext.getDom("projectNo").value;
		
		if (projectId == ''){
			_getMainFrame().showInfo('立项信息不可为空!');
			return ;
		}	
		var value = 0;		
		var requestUrl = 'xdss3/payment/importPaymentController.spr?action=checkCredit&providerid=a' + 
				'&projectno='+ projectId +
				'&value='+value.toFixed(2);	
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){				
  				var jsonData = Ext.util.JSON.decode(response.responseText);
				var andFlag = jsonData.result.split("&");
				if (andFlag[0] == 'false' && andFlag[1] == 'false'){
					_getMainFrame().showInfo('该立项没有授信!');
				}else{
					_getMainFrame().showInfo(andFlag[0]);
				}
			},
			failure:function(response, options){
			}
		});	
	}
        
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[new Ext.Panel({  
		  title:'查看信用额度',
		  height:25,
		  tbar:toolbars
		  }),{
				region:'north',
				collapsible: true,
				height:110,
				border:false,
				margins: '25 0 0 0',
				contentEl: 'div_headForm'
			  },
			  {
				region:"center",
				layout:'fit',
				buttonAlign:'center',
				border:false,
				items:[{
					region:'center',
					xtype:'tabpanel',
					id:'infotype',
					name:'infotype',
					plain:true,
		            //height:190,
		            activeTab: ${isRelateProject==false?2:0},
		            items:[{
		            	title:strTitle+'相关信息',
		            	contentEl: '',
		            	id:'creditEntryInfo',
						name:'creditEntryInfo',
						//disabled:${!isRelateProject},
						autoScroll:true,
		            	layout:'fit',
		            	items:[innerPayContractInfogrid]
		            	},{contentEl:'rule',
			               id:'fileEl', 
			               title: '附件信息',
			               autoScroll:'true',
			               listeners:{activate:handlerActivate}
			            },{
		            	title:'审批历史记录',
		            	contentEl: 'div_history',
		            	id:'historyinfo',
						name:'historyinfo',
						autoScroll:true,
						disabled:${!saveDisabled},
		            	layout:'fit'
            			}
            			<c:if test="${main.examineState=='7'||main.examineState=='8'||main.examineState=='9'}">
            			,{
		            	title:'特批审批历史记录',
		            	contentEl: 'div_pur_history',
		            	id:'purhistoryinfo',
						name:'purhistoryinfo',
						autoScroll:true,
		            	layout:'fit'
            			}
            			</c:if>
            			]
			   }]
		       },
		       {
		       	    border:false,
				    region:'south',
				    height:210,
					collapsible: true,
					autoScroll:true,
					contentEl: 'div_main'
			  }]
	});
    function contractOper(value, p, record){
    	return String.format('<a href="#" onclick="openContractWin()">{0}</a>',value);
    }
	
 //按钮
   var btnSave = new Ext.Button(
	    {
	     	text:'保存',
	     	id:'btnSave',
	     	name:'btnSave',
	     	handler:${saveFunction},
			disabled:saveDisabled,
	     	renderTo:'div-Save'
	    }
    )
    
    var btnSubmitworkflow = new Ext.Button(
	    {
	     	text:'提交',
	     	id:'btnSubmit',
	     	name:'btnSubmit',
	     	handler:submitWorkflow,
			hidden:submitHidden,
	     	renderTo:'div-submit'
	    }
    )
   
    var btnclose = new Ext.Button(
	    {
	     	text:'关闭',
	     	id:'btnclose',
	     	name:'btnclose',
	     	handler:closeForm,
			hidden:closeHidden,
	     	renderTo:'div-close'
	    }
    )
	
	//payDateDiv
	var payRealTime = new Ext.form.DateField({
		format:'Ymd',
		id:'payRealTime',
		name:'payRealTime',
		width: 160,
		readOnly:true,
		renderTo:'payRealTimeDiv',
		value:'${main.payRealTime}'
	});
	<c:if test="${main.payType!=2}">
	var maturityDate = new Ext.form.DateField({
		format:'Y-m-d',
		id:'maturityDate',
		name:'maturityDate',
		width: 160,
		disabled:true,
		renderTo:'maturityDateDiv',
		value:'${main.maturityDate}'
	});
	</c:if>
	var replaceDate = new Ext.form.DateField({
		format:'Y-m-d',
		id:'replaceDate',
		name:'replaceDate',
		width: 160,
		disabled:false,
		renderTo:'replaceDateDiv',
		value:'${main.replaceDate}'
	});
	
	if (Ext.getDom('creatorTime').value == '') {
		var dt = new Date();
		Ext.getDom('creatorTime').value = dt.format('Y-m-d');
	}
	setPreSecurity();
	setDeposit();
	if(Ext.getDom("payMethod").value==''){
	 Ext.getDom("payMethod").value= payMethod;
	}
	var currency_combo = new Ext.form.ComboBox({
        typeAhead: true,
        triggerAction: 'all',
        transform:'currency',
        width:157,
        allowBlank:false,
        blankText:'请输入货币',
        forceSelection:true
     });
     currency_combo.setValue('${main.currency}'); 	
});//end of Ext.onReady 

function openContractWin(){
	var records = innerPayContractInfogrid.selModel.getSelections();
	var contractType= records[0].data.CONTRACT_TYPE;
	var contractNo= records[0].data.CONTRACT_NO;
	var contractName= records[0].data.CONTRACT_NAME;
	var state= records[0].data.ORDER_STATE_D_ORDER_STATE;
	
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		top.ExtModalWindowUtil.show(contractType+'-'+contractNo+'-'+contractName+'-'+state,
			'contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+records[0].data.CONTRACT_PURCHASE_ID,
			'',
			'',
			{width:800,height:550});
	}

	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		top.ExtModalWindowUtil.show(contractType+'-'+contractNo+'-'+contractName+'-'+state,
		'contractController.spr?action=addSaleContractView&contractid='+records[0].data.CONTRACT_PURCHASE_ID,
		'',
		'',
		{width:800,height:600});
	}
}

function setPreSecurity(){
	try{
		if('${main.preSecurity}'=='1'){
			document.getElementById('preSecurity0').checked = true;
		}else if('${main.preSecurity}'=='0'){
			document.getElementById('preSecurity1').checked = true;
		}else{
			document.getElementById('preSecurity0').checked = false;
			document.getElementById('preSecurity1').checked = false;
		}
	}catch(e){
	}
}
var needCheckDeposit;
function setDeposit(){
	try {
		var preSecurity = document.getElementsByName('preSecurity');
		var deposit = document.getElementById('deposit');
		var amount = document.getElementById('amount');
		if (preSecurity[1].checked) {
			deposit.disabled = false;
			deposit.style.borderColor = "#A0A0A0";
			deposit.style.borderWidth = "1px";
			deposit.onchange = function(){
				judgeFloat(deposit, '保证金');
			};
			amount.style.color = "#FF2020";
			needCheckDeposit = 1;
		}
		else {
			deposit.disabled = true;
			deposit.style.borderColor = "#FFFFFF";
			deposit.style.borderWidth = "0px";
			deposit.onclick = "";
			deposit.value = "";
			amount.style.color = "#FFFFFF";
			needCheckDeposit = 0;
		}
	}catch(e){
		
	}
}
//金额检查
function judgeFloat(obj,name){
	var v = obj.value;
	if(!Utils.isFloatValue(v)){
		showMsg(name+'必需为数字且大于 0');
		obj.focus();
		return false;
		//top.Ext.Msg.show({title:'提示',msg:'<font color="red">'+name+'</font>含有非数字字符',buttons:Ext.Msg.CANCEL,fn:function(btn,text){
		//		obj.focus();
		//		return false;
		//	}});
	}
	return true;
}

// 保存
function save()
{
    /**if(payType!='2'&&innerPayContractInfogrid.getStore().getCount()<=0){
		showMsg('请选择至少一个合同！');
		return false;
	}**/
	if(payType!='2'){
		if($('goodsName').value==''|| $('quantity').value==''){
			top.Ext.Msg.alert('提示','品名和数量必填');
			return;
		}	
	}
	if(payType=='2'){
		if($('isNetPay').value==''){
			top.Ext.Msg.alert('提示','是否网上电子口岸支付必填！');
			return;
		}	
		<c:if test="${main.payType==2}">
		if($('isPayForAnother').value==''){
			top.Ext.Msg.alert('提示','是否纯代理进口代收代付必填！');
			return;
		}	
		</c:if>
	}
	if(!judgeFloat(Ext.getDom('payValue'),'申请付款金额')){
		return false;
	}
	var contractNumber
	if(${isRelateProject}){
		var projectId = Ext.getDom("projectId").value
    	if(projectId=='') {
       		showMsg('请选择立项信息！');
       		return ;
    	}	
		

        /*检查合同金额*/
        innerPayContractInfogrid.getSelectionModel().selectAll();
	    var recoder = innerPayContractInfogrid.selModel.getSelections();
	    if(recoder.length>0){
	        var total = 0;
			for (var i=0; i< recoder.length; i++) {
				var returnValue = recoder[i].data;
				total+=returnValue.PAY_VALUE;
			};
			var payValue = parseFloat(Ext.getDom('payValue').value);

			if(total.toFixed(2)!=payValue.toFixed(2)){
			    showMsg('合同金额累加和付款金额不相等！');
			    return;
			}
		}
		contractNumber = writeAllContract();
    }
	
	if(needCheckDeposit&&!judgeFloat(Ext.getDom('deposit'),'保证金')){
		return false;
	}
	if (Ext.getDom('creatorTime').value == '') {
		var dt = new Date();
		Ext.getDom('creatorTime').value = dt.format('Y-m-d');
	}
	
	strOperType = '1';
	var param1 = Form.serialize(headForm);
	var param2 = Form.serialize(mainForm);
	var param = '?action=save&' + param1+'&'+param2;
	new AjaxEngine('innerTradePayController.spr', 
				{method:"post", parameters: param, onComplete: callBackHandle});
	//innerPayContractInfods.reload();
	if(${isRelateProject}){
		removeAllContract(contractNumber);
		}
}

// 审批中修改内贸付款内容
function examineModify()
{
	/**if(innerPayContractInfogrid.getStore().getCount()<=0){
		showMsg('请选择至少一个合同！');
		return false;
	}**/
	if(!judgeFloat(Ext.getDom('payValue'),'申请付款金额')){
		return false;
	}
	if(needCheckDeposit&&!judgeFloat(Ext.getDom('deposit'),'保证金')){
		return false;
	}
	/*检查合同金额*/
    innerPayContractInfogrid.getSelectionModel().selectAll();
    var recoder = innerPayContractInfogrid.selModel.getSelections();
    if(recoder.length>0){
        var total = 0;
		for (var i=0; i< recoder.length; i++) {
			var returnValue = recoder[i].data;
			total+=returnValue.PAY_VALUE;
		};
		var payValue = parseFloat(Ext.getDom('payValue').value);

		if(total.toFixed(2)!=payValue.toFixed(2)){
			    showMsg('合同金额累加和付款金额不相等！');
			    return;
		}
	}
	var contractNumber = writeAllContract();
	var param1 = Form.serialize(headForm);
	var param2 = Form.serialize(mainForm);
	var param = '?action=save&' + param1+'&'+param2;
	new AjaxEngine('innerTradePayController.spr', 
				{method:"post", parameters: param, onComplete: callBackHandle});
	removeAllContract(contractNumber);
}

function writeAllContract(){
	innerPayContractInfogrid. getSelectionModel().selectAll();
	var recoder = innerPayContractInfogrid.selModel.getSelections();
	var myform = document.getElementById("mainForm");
	for (var i=0; i< recoder.length; i++) {
		var returnValue = recoder[i].data;
		//alert(returnValue);
		var myinput = document.createElement('input');
		myinput.setAttribute('name','contractPurchaseId');
		myinput.setAttribute('id','contractPurchaseId'+i);
		myinput.setAttribute('type','hidden');
		myinput.value=returnValue.CONTRACT_PURCHASE_ID+'/'+returnValue.PAY_VALUE;
		myform.appendChild(myinput);
	};
	return recoder.length;
}

function removeAllContract(contractNumber){
	for (var i = 0; i < contractNumber; i++) {
		var node = document.getElementById("contractPurchaseId" + i);
		if (node) {
			node.parentNode.removeChild(node);
		}
	}
}

//提示窗口
function showMsg(msg){
	top.Ext.Msg.show({
			title:'提示',
			closable:false,
			msg:msg,
			buttons:{yes:'关闭'},
			fn:Ext.emptyFn,
			icon:Ext.MessageBox.INFO
	});
}

//提交工作流审批
function submitWorkflow(){
	judgeFloat(Ext.getDom('payValue'),'申请付款金额');
	if(payType!='2'){
		if($('goodsName').value==''|| $('quantity').value==''){
			top.Ext.Msg.alert('提示','品名和数量必填');
			return;
		}	
	}
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否确定提交流程审批 ',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
   	   			if(${isRelateProject})
					writeAllContract();
				strOperType = '3';
				var dt = new Date();
				Ext.getDom('applyTime').value = dt.format('Y-m-d');
				var param1 = Form.serialize(headForm);
				var param2 = Form.serialize(mainForm);
				var param = "?action=submitAndSave&"+param1+"&"+param2;
				new AjaxEngine('innerTradePayController.spr',
					 {method:"post", parameters: param, onComplete: callBackHandle});
			
   			}
   		},
   		icon: Ext.MessageBox.QUESTION
	});
}	

//关闭窗体
function closeForm(){
	//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
}

//清空窗口
function resetForm(form){
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否清空窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
   				form.reset()
   			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}



//提交审批流程回调函数
function submitWorkflowCallBackHandle(transport){
	//var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	//var customField = responseUtil1.getCustomField("coustom");
	//top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	//showMsg(customField.returnMessage);
	//setTimeout(function(){
	//	top.ExtModalWindowUtil.markUpdated();
	//	top.ExtModalWindowUtil.close();
	//},1500);
	customCallBackHandle(transport);
}

function handlerActivate(tab){
	rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
}

//回调函数
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	top.ExtModalWindowUtil.alert('[提示]',customField.returnMessage);
	if (strOperType == '1') {
		if (Ext.getDom('paymentId').value == '') {
			//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
			top.ExtModalWindowUtil.markUpdated();
			top.ExtModalWindowUtil.close();
		}
		else {
			if(${isRelateProject})
				innerPayContractInfods.reload();
		}
	}
	if (strOperType == '3'){
		//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	}	
}
//立项选择回调函数
function selectProjectInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	document.headForm.projectId.value = returnvalue.PROJECT_ID + "";
	document.headForm.projectNo.value = returnvalue.PROJECT_NO + "";
	innerPayContractInfods.removeAll();
}

//立项选择窗体
function selectProjectInfo(){
	top.ExtModalWindowUtil.show('查询立项信息',
	'projectController.spr?action=selectProjrctInfo',
	'',
	selectProjectInfoCallBack,
	{width:800,height:400});
}

//立项仓储协议回调函数
function selectProtocolInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	document.getElementById("protocolId").value = returnvalue.INFO_ID ;
	document.getElementById("protocolNo").value = returnvalue.PROTOCOL_NO;
}

//仓储协议选择窗体
function selectProtocolInfo(){
	top.ExtModalWindowUtil.show('查询仓储协议信息',
	'storageProtocolController.spr?action=selectProtocolInfo',
	'',
	selectProtocolInfoCallBack,
	{width:800,height:400});
}

function accountFocus(obj){
	if(obj.value==''&&Ext.getDom('recBank').value!=''){
	   var param = 'innerTradePayController.spr?action=queryBankAndAccount&';
		Ext.Ajax.request({
			url: param,
			params:Form.serialize('mainForm'),
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);

                var openAccountBank = responseArray.openAccountBank;
                var openAccountNo = responseArray.openAccountNo;
                obj.value=openAccountBank;
                Ext.getDom('openAccountNo').value=openAccountNo;
               
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});  
	}
}
function showFindSupplier(){
	top.ExtModalWindowUtil.show('查找供应商','masterQueryController.spr?action=toFindSupplier','',supplierCallback,{width:755,height:410});
}
function showFindCustomer(){
	top.ExtModalWindowUtil.show('查找客户','masterQueryController.spr?action=toFindCustomer','',customerCallback,{width:755,height:410});
}
function customerCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	if(cb==''){
	    Ext.getDom('recBank').value='';
		Ext.getDom('unitNo').value='';
		Ext.getDom('unitType').value='';
	}
	else {
		Ext.getDom('recBank').value=cb.NAME1;
		Ext.getDom('unitNo').value=cb.KUNNR;
		Ext.getDom('unitType').value='1';
	}
}
function supplierCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	if(cb==''){
		Ext.getDom('recBank').value='';
		Ext.getDom('unitNo').value='';
		Ext.getDom('unitType').value='';
	}
	else {
    	Ext.getDom('recBank').value=cb.NAME1;
		Ext.getDom('unitNo').value=cb.LIFNR;
		Ext.getDom('unitType').value='2';
	}
}
</script>
</head>
<body>
<div id="div_headForm">
	<form id="headForm" action="" name="headForm">
		<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
			<tr>
				<td width="11%" align="right">创建时间：</td>
				<td width="22%" align="left">
					<!--div id="applyTimeDiv"></div-->
					<input type="text" name="creatorTime" id="creatorTime" value="${main.creatorTime}" size="18" readonly="readonly">
					<input type="hidden" name="applyTime" id="applyTime" size="18" value="${main.applyTime}" readonly="readonly">
				</td>
				<td width="11%" align="right"><nobr>是否网上电子口岸支付：<font color="red">▲</font></nobr></td>
				<td width="22%" align="left">
				    <select name="isNetPay" id="isNetPay">
				      <option value="">请选择</option>
				      <option value="0">否</option>
				      <option value="1">是</option>
				    </select>
				    <script type="text/javascript">
				    $('isNetPay').value='${main.isNetPay}';
				    </script>
					<input name="financeNo" id="financeNo" type="hidden" value="${main.financeNo}"/>
				</td>
				<td width="11%" align="right">申请部门：</td>
				<td width="22%">
					<input type="text" id="deptName" name="deptName" size="18" value="${main.deptName}" readonly="readonly">
					<input type="hidden" id="deptId" name="deptId" value="${main.deptId}">
				</td>
			</tr>
			<tr>
				<td width="11%" align="right">申请人：</td>
				<td width="22%" align="left">
					<input type="text" id="realName" name="realName" size="18" value="${main.creatorName}" readonly="readonly"></input>
					<input type="hidden" id="creatorName" name="creatorName" value="${main.creatorName}">
					<input type="hidden" id="creator" name="creator" value="${main.creator}">
				</td>
				<td width="11%" align="right">付款形式：<font color="red">▲</font></td>
				<td width="22%" align="left">
					${payMethodControl}
				</td>
				<td width="11%" align="right">付款银行：</td>
				<td width="22%" align="left">
					<input name="payBank" id="payBank" type="text" size="18" tabindex="1" value="${main.payBank}" maxlength="100"/>
				</td>
			</tr>
			<tr>
				<td width="11%" align="right">付款帐号：</td>
				<td width="22%" align="left">
					<input name="payAccount" id="payAccount" type="text" size="18" tabindex="1" value="${main.payAccount}" maxlength="100"/>
				</td>
				<c:if test="${main.payType=='2'}">
				<td width="11%" align="right"><nobr>是否纯代理进口代收代付：<font color="red">▲</font></nobr></td>
				<td width="22%" align="left">
				    <select name="isPayForAnother" id="isPayForAnother">
				      <option value="">请选择</option>
				      <option value="1">是</option>
				      <option value="0">否</option>
				    </select>
				    <script type="text/javascript">
				    $('isPayForAnother').value='${main.isPayForAnother}';
				    </script>
				 </td>
				 </c:if>
				 <c:if test="${main.payType!='2'}">
				<td width="11%" align="right">到期日期 </td>
				<td width="22%" align="left">
					<div id="maturityDateDiv"></div>
				</td>
				</c:if>
				<td width="11%" align="right">立项号：<font color="red">▲</font></td>
				<td width="22%" align="left">
					<input name="projectNo" id="projectNo" type="text" size="8" tabindex="1" value="${main.projectNo}" readonly="readonly" />
					<input id="selectProjectBtn" type="button" value="..." onclick="selectProjectInfo()" ${isRelateProject&&!saveDisabled?"":"disabled=true"}/>
					<input type="hidden" value="${main.projectId }" name="projectId"/>
					<a href="#" onclick="viewProjectForm()">查看</a>
				</td>
			</tr>
			<tr>
				<td width="11%" align="right">品名：<font color="red">▲</font></td>
				<td width="22%" align="left">
					<input type="text" name="goodsName" id="goodsName" value="${main.goodsName}" size="18" >
				</td>
				<td width="11%" align="right">数量：<font color="red">▲</font></td>
				<td width="22%" align="left">
					<input name="quantity" id="quantity" type="text" size="18" tabindex="1" value="${main.quantity}"/>
				</td>
				<td width="11%" align="right">代垫到期日：</td>
				<td width="22%" align="left">
					<div id="replaceDateDiv"></div>
				</td>
			</tr>
			
			<script>
			function viewProjectForm(){
			        var projectId = Ext.getDom("projectId").value
			        if(projectId=='') {
			           showMsg('请选择立项信息！');
			           return ;
			        }
					top.ExtModalWindowUtil.show('查看立项申请','projectController.spr?action=modify&from=view&projectId='+projectId,'','',{width:800,height:500});
		    }
		    </script>
		</table>
	</form>
</div> <!-- //end div_headForm -->
<div id="div_innerPayContractInfogrid"></div>
<div id="div_history" class="x-hide-display"></div>
<div id="div_pur_history" class="x-hide-display"></div>
<div id="div_main" class="x-hide-display">
	<form id="mainForm" action="" name="mainForm">
		<input type="hidden" name="paymentId" id="paymentId" value="${main.paymentId}">
		<!--input type="hidden" name="creatorTime" value="${main.creatorTime}"-->
		<input type="hidden" name="payType" value="${main.payType}">
		<input type="hidden" name="tradeType" value="7">
		<input type="hidden" name="payTime" value="${main.payTime}">
		<input type="hidden" name="approvedTime" value="${main.approvedTime}">
		<input type="hidden" name="isAvailable" value="1">
		<input type="hidden" name="examineState" value="${main.examineState}">
${isNotCreateBank}
		
		<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
			<tr>
				<td width="12%" align="right">申请付款金额：<font color="red">▲</font></td>
				<td width="21%" align="left">
					<input name="payValue" id="payValue" type="text" size="20" tabindex="1" value="${payValue}" onchange="judgeFloat(this,'申请付款金额');" maxlength="20"/>
				</td>
				<td align="right">币别:</td>
				<td colspan="3">
					<div id="div_currency">
						<select name="currency" id="currency">
							<c:forEach items="${Currency}" var="row">
								<option value="${row.code}">${row.code}</option>
							</c:forEach>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<td width="12%" align="right" valign="middle">汇率：<font color="red">▲</font></td>
				<td width="21%" align="left">
					<input name="exchangeRate" id="exchangeRate" type="text" size="20" tabindex="1" value="${empty main.exchangeRate ?1.0:main.exchangeRate}" maxlength="20"/>
				</td>
				<td width="12%" align="right">仓储|货运|保险|报关协议：</td>
				<td width="21%" align="left" colspan="3">
					<input name="protocolNo" id="protocolNo" type="text" size="20" tabindex="1" value="${main.protocolNo}" readonly="readonly" />
					<input id="selectProtocolBtn" type="button" value="..." onclick="selectProtocolInfo()" />
					<input type="hidden" value="${main.protocolId }" name="protocolId"/>
					<a href="#" onclick="viewProtocolForm()">查看</a>
					<script>
			        function viewProtocolForm(){
			        var protocolId = Ext.getDom("protocolId").value
			        if(protocolId=='') {
			           showMsg('请选择仓储协议信息！');
			           return ;
			        }
					top.ExtModalWindowUtil.show('查看仓储协议申请','/storageProtocolController.spr?action=view&infoId='+protocolId,'','',{width:800,height:500});
		    }
		    </script>
				</td>
			</tr>
			<tr>
				<td width="12%" align="right" valign="middle">付款用途：</td>
				<td width="21%" align="left" colspan="5">
					<textarea cols="80" rows="2" id="payUse" name="payUse">${main.payUse}</textarea>
				</td>
			</tr>
				${isCreateBank}
			<tr>
				<td width="12%" align="right">收款单位：<font color="red">▲</font></td>
				<td width="21%" align="left" colspan="5">
					<input name="recBank" id="recBank" type="text" size="50" tabindex="1" value="${main.recBank}" maxlength="100"/>
					<input type="hidden" id="unitNo" name="unitNo" value="${main.unitNo}"/>
      	            <input type="hidden" id="unitType" name="unitType" value="${main.unitType}"/>
      	            <!-- 
      	            <input type="button" name="客户" value="客户" onclick="showFindCustomer();"/>/
      	            <input type="button" name="供应商" value="供应商" onclick="showFindSupplier();"/>
      	             -->
				</td>
			</tr>
			<tr>	
				<td width="12%" align="right">收款单位开户银行：<font color="red">▲</font></td>
				<td width="21%" align="left" >
					<input name="openAccountBank" id="openAccountBank" type="text" size="40" tabindex="1" value="${main.openAccountBank}" onfocus="accountFocus(this)"/>
				</td>
				<td width="12%" align="right">收款单位账户：<font color="red">▲</font></td>
				<td width="21%" align="left" colspan="3">
					<input name="openAccountNo" id="openAccountNo" type="text" size="40" tabindex="1" value="${main.openAccountNo}" />
				</td>
			</tr>
			<tr>
				<td width="12%" align="right">付款人：</td>
				<td width="21%" align="left">
					<input name="payer" id="payer" type="text" size="20" tabindex="1" value="${main.payer}" maxlength="50"/>
				</td>
				<td width="12%" align="right">付款日：</td>
				<td width="21%" align="left" colspan="3">
					<div id="payRealTimeDiv"></div>
				</td>
			</tr>
			<tr>
				<td width="12%" align="right"  valign="middle">备注：</td>
				<td width="21%" align="left" colspan="5">
					<textarea cols="80" rows="2" id="cmd" name="cmd">${main.cmd}</textarea>
				</td>
			</tr>
		</table>
		<table width="100%" >
		    <tr>
		    	<td align="right" width="30%" colspan="5" height=10>
				</td>
		    </tr>
			<tr>
				<td align="right" width="30%">
					<div id="div-save" name="div-save"></div>
				</td>
				<td width="5%"></td>
				<td align="left" width="5%">
					<div id="div-submit" name="div-submit"></div>
				</td>
				<td width="5%"></td>
				<td align="left" width="30%">
					<div id="div-close" name="div-close"></div>
				</td>
			</tr>
	    </table>

	</form>
</div> <!-- end div_main -->
<div id="rule" class="x-hide-display" ></div>
</body>
</html>
<fiscxd:fileUpload divId="rule" recordId="${main.paymentId}" erasable="${!saveDisabled}"  increasable="${!saveDisabled}" 
 resourceId="2222" resourceName="33333" recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>
<fiscxd:workflow-taskHistory divId="div_history" businessRecordId="${main.paymentId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:workflow-taskHistory divId="div_pur_history" businessRecordId="${purId}" width="750"></fiscxd:workflow-taskHistory>