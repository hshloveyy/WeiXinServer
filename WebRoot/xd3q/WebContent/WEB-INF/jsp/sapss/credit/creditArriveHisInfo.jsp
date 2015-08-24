<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信用证到证审查登记表</title>
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
<script type="text/javascript">
//操作类别  
var operate = '${operate}';
//贸易类型
var tradeType = '${tradeType}';
//是否可以提交
var submitHidden = '${submitHidden}'; 
//是否可以保存
var saveDisabled = '${saveDisabled}';
var fileDisable = (operate=="" ||operate==null) ? true:false;

var changeSubmitDisabled = true;

var creditEntryHisInfogrid;
var creditEntryHisInfods;

var viewHistory = true; 
if(operate =="view" )
{
    viewHistory = false;
}

var creditArriveHisInfogrid;
var creditArriveHisInfods;

var strcreditArriveTitle;
strcreditArriveTitle = Utils.getTradeTypeTitle(tradeType);


Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

    if(operate=='change' && submitHidden=='false')
    {
      if(Ext.getDom('versionNo').value=='0' )
       {
       	 changeSubmitDisabled = true;
       	 submitHidden = true;
       }
       else
       {
         changeSubmitDisabled = false;
         submitHidden = false;
       }
    }
    
   	var creditArriveHisInfoPlant = Ext.data.Record.create([
   	    {name:'CREDIT_HIS_ID'},       //信用证历史ID CREDIT_ID
   	    {name:'CREDIT_ID'},           //信用证ID 
   	   	{name:'VERSION_NO'},          //版本号
   	   	{name:'CHANGE_TIME'},         //变更时间
   	   	{name:'CHANGE_EXEC_TIME'},    //变更执行时间
   	   	{name:'CHANGE_STATE'},        //变更状态
   	   	{name:'IS_EXECUTED'},         //变更是否执行
		{name:'CREDIT_NO'},           //信用证编号
		{name:'CREATE_DATE'},         //开证日期
		{name:'DEPT_NAME'},           //部门名称
		{name:'PROJECT_NO'},          //立项号
		{name:'CONTRACT_NO'},         //合同编码
		{name:'SAP_ORDER_NO'},        //SAP订单编号 
		{name:'CREATE_BANK'},         //开证行
	    {name:'CREDIT_STATE_D_CREDIT_STATE'}, //信用证状态
	    {name:'TRADE_TYPE'}, 
	    {name:'CREDIT_STATE'}, 
	    {name:'PROJECT_NAME'},
	    {name:'CREATE_OR_REC'},
		{name:'CUSTOM_CREATE_DATE'},    
		{name:'CREDIT_REC_DATE'},    
		{name:'VALID_DATE'},    
		{name:'COUNTRY'}, 
		{name:'REQUEST'}, 			
		{name:'BENEFIT'}, 		
		{name:'BENEFIT_CERTIFICATION'}, 	
		{name:'PAYMENT_TYPE'}, 		
		{name:'AMOUNT'}, 		
		{name:'RATE'}, 		
		{name:'CURRENCY'}, 		
		{name:'GOODS'}, 		
		{name:'SPECIFICATION'}, 		
		{name:'MARK'}, 			
		{name:'INVOICE'}, 			
		{name:'BILL_OF_LADING'}, 		
		{name:'BILL_OF_INSURANCE'}, 			
		{name:'BILL_OF_QUALITY'}, 	
		{name:'CERTIFICATE_OF_ORIGIN'}, 	
		{name:'PACKING_SLIP'}, 
		{name:'ELECTRIC_SHIP'}, 
		{name:'DISPATCH_ELECTRIC'}, 
		{name:'OTHER_DOCUMENTS'}, 
		{name:'LOADING_PERIOD'}, 
		{name:'PERIOD'}, 
		{name:'OTHER_DOCUMENTS'}, 
		{name:'LOADING_PERIOD'}, 
		{name:'PERIOD'}, 
		{name:'PLACE'}, 	
		{name:'CAN_BATCHES'}, 		
		{name:'TRANS_SHIPMENT'}, 		
		{name:'PORT_OF_SHIPMENT'}, 	
		{name:'PORT_OF_DESTINATION'}, 
		{name:'PAYMENT_DATE'}, 		
		{name:'PRE_SECURITY'}, 		
		{name:'WRITE_OFF_SINGLE_NO'}, 
		{name:'SPECIAL_CONDITIONS'}, 
		{name:'BILL_CONDITIONS'}, 
		{name:'MATTERS_SHOULD_BE_AMENDED'}, 
		{name:'CMD'}, 
		{name:'DEPT_ID'}, 
		{name:'APPLY_TIME'}, 
		{name:'APPROVED_TIME'}, 
		{name:'CREATOR_TIME'}, 										
		{name:'CREATOR'},
		{name:'IS_AVAILABLE'},
		{name:'CREDIT_INFO'},
		{name:'BAIL_DATE'},
		{name:'AVAIL_DATE'},
		{name:'YMAT_GROUP'}
	]);

	creditArriveHisInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'creditArriveHisController.spr?action=queryCreditHisInfo&creditId='+'${creditId}'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},creditArriveHisInfoPlant)
    });
    
    var creditArriveHisInfosm = new Ext.grid.CheckboxSelectionModel();   
    
    var creditArriveHisInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		creditArriveHisInfosm,
		  {
			   header: '信用证历史ID',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CREDIT_HIS_ID',
	           hidden:true
          },
          {
			   header: '信用证ID',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CREDIT_ID',
	           hidden:true 
           },
          {
			   header: '版本号',
	           width: 100,
	           sortable: true,
	           dataIndex: 'VERSION_NO'
          },
		  {
			   header: '变更时间',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CHANGE_TIME'
           },
		   {
			   header: '变更执行时间',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CHANGE_EXEC_TIME'
           },
		　 {
			   header: '信用证号',
	           width: 120,
	           sortable: true,
	           dataIndex: 'CREDIT_NO'
           },
		　 {
			   header: '开证日期',
	           width: 100,
	           sortable: false,
	           dataIndex: 'CREATE_DATE'
           },
           {
	           header: '部门名称',
	           width: 100,
	           sortable: true,
	           dataIndex: 'DEPT_NAME',
	           renderer:renderHallName
	           // hidden:true
           },
           {
	           header: '立项号',
	           width: 100,
	           sortable: true,
	           dataIndex: 'PROJECT_NO',
	           renderer:renderHallName
           },
           {
	           header: '合同编码',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CONTRACT_NO',
	           renderer:renderHallName
           },
           {
	           header: 'SAP订单编号',
	           width: 100,
	           sortable: true,
	           dataIndex: 'SAP_ORDER_NO'
           },
           {
	           header: '开证行',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CREATE_BANK'
           },
           {
	       		header: '信用证状态',
	       		width: 100,
	       		sortable: false,
	       		dataIndex: 'CREDIT_STATE_D_CREDIT_STATE'
           },
           {
	           header: '操作',
	           width: 140,
	           sortable: true,
	           dataIndex: 'operaRD',
	           renderer: operaRD
           },
           {
	       		header: '变更是否执行',
	       		width: 100,
	       		sortable: false,
	       		dataIndex: 'IS_EXECUTED'
           }      
           ]);
           
    creditArriveHisInfocm.defaultSortable = true;  
    
   var creditArriveHisInfobbar = new Ext.PagingToolbar({
        pageSize: 1000,
        store:creditArriveHisInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    //        tbar: '',
	creditArriveHisInfogrid = new Ext.grid.EditorGridPanel({
    	id:'creditArriveHisInfogrid',
        ds: creditArriveHisInfods,
        cm: creditArriveHisInfocm,
        sm: creditArriveHisInfosm,
        bbar: creditArriveHisInfobbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_creditArriveHisInfogrid',
        autowidth:true,
        height:250,
        layout:'fit'
    });
    
    creditArriveHisInfods.load({
    params:{start:0, limit:1000},
    callback:function(records, options, success){
      var sm =creditArriveHisInfogrid.getSelectionModel();
      sm.selectRow(0, true);
    }});
    
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'fit',
			buttonAlign:'center',
			items:[{
				region:'center',
				xtype:'tabpanel',
				id:'infotype',
				name:'infotype',
				plain:true,
	            height:350,
	            autoScroll:'true',
	            activeTab: 0,
	            items:[{
	            	title:'信用证到证审查登记表',
	            	contentEl: 'div_main',
	            	id:'creditArriveInfo',
					name:'creditArriveInfo',
					autoScroll:'true',
	            	layout:'fit'
	               	},
	            	{
	                title: '信用证附件',
	                contentEl:'rule',
	                id:'fileEl', 
	                disabled:fileDisable,
	                listeners:{activate:handlerActivate}
	                }]
		       }],
				buttons:[{
	            	text:'改证',
	            	id:'btn_Save',
	            	name:'btn_Save',
	            	disabled :${saveDisabled},
	            	handler:function(){
	            		var materialOrg=dict_div_ymatGroup.getActualValue();			
						if(materialOrg==""){
							top.ExtModalWindowUtil.alert('提示','请选择物料组！');
							return;
						}
	    				var amount =Ext.getDom("amount").value; 
	    			    if(Utils.isEmpty(amount)==false && Utils.isFloatValue(amount) == false  )
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[金额]必须为数字！');
		    			    return;
	    			    }
	    				var rate =Ext.getDom("rate").value;
	    			    if(Utils.isEmpty(rate)==false && Utils.isFloatValue(rate) == false)
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[汇率]必须为数字！');
		    			    return;
	    			    }
	    			    
	            		var param1 = Form.serialize('mainForm');
						var param = param1 + "&versionNoOld=" + Ext.getDom("versionNo").value +"&action=saveCreditArriveHisInfo";
						
						new AjaxEngine('creditArriveHisController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
					}

	            },{
	            	text:'保存原信用证状态',
	            	hidden: ${!CHANGE_CREDIT_STATUS},
	            	handler:saveCreditStatus
	            },{
		        	text:'提交改证',
		        	id:'btn_Submit',
		        	name:'btn_Submit',
		        	hidden:${submitHidden},
		        	//disabled:changeSubmitDisabled,
		        	handler:submitWorkflow
		        },{
	            	text:'关闭',
	            	id:'btn_close',
	            	name:'btn_close',
	            	handler:closeForm
	            }]
		       }
		        ,{
	            	region:"south",
	            	title:'信用证修改历史',
	            	contentEl: '',
	            	height:150,
	            	collapsible: true,
	            	collapsed:false,
	            	id:'creditArriveHisInfo',
					name:'creditArriveHisInfo',
					layout:'fit',
	            	items:[creditArriveHisInfogrid]
		        }]
	});

	if(operate=='view')
	{
		creditArriveHisInfogrid.addListener('rowclick', creditArriveHisInfogridrowclick);
	}
	else if(operate=='change' || operate=='iframe')
	{
		creditArriveHisInfogrid.addListener('rowclick', creditArriveHisInfogridrowclick1);
	}
	

    function creditArriveHisInfogridrowclick1(grid, rowIndex, e)
    {
		 var sm =grid.getSelectionModel();
		 sm.selectFirstRow();
    }

    function creditArriveHisInfogridrowclick(grid, rowIndex, e)
    {
     if (grid.selModel.hasSelection()){
         
   			var records = grid.selModel.getSelections();
   			if (records.length > 1)
   			{
   				top.ExtModalWindowUtil.alert('提示','只能选择一条记录查看！');
   			}
   			else
   			{
   				var records = grid.getStore().getAt(rowIndex);
   		    	var CREDIT_ID = records.get('CREDIT_ID');
   		    	var CREATOR =records.get('CREATOR'); 
  
   		    	var ProcessingHint = new MessageBoxUtil();
   			    ProcessingHint.waitShow();
   		    	
   		    	Ext.getDom("tradeType").value = (records.get('TRADE_TYPE')==null) ? "" : records.get('TRADE_TYPE');  
   		    	Ext.getDom("createOrRec").value = (records.get('CREATE_OR_REC')==null) ? "" : records.get('CREATE_OR_REC');   
   		    	Ext.getDom("deptId").value = (records.get('DEPT_ID')==null) ? "" : records.get('DEPT_ID');  
   		    	Ext.getDom("creditId").value = (records.get('CREDIT_ID')==null) ? "" : records.get('CREDIT_ID');  
   		    	Ext.getDom("creditState").value = (records.get('CREDIT_STATE')==null) ? "" : records.get('CREDIT_STATE');  
   		    	Ext.getDom("isAvailable").value = (records.get('IS_AVAILABLE')==null) ? "" : records.get('IS_AVAILABLE');
   		    	Ext.getDom("applyTime").value = (records.get('APPLY_TIME')==null) ? "" : records.get('APPLY_TIME'); 
   		    	Ext.getDom("approvedTime").value = (records.get('APPROVED_TIME')==null) ? "" : records.get('APPROVED_TIME'); 
   		    	Ext.getDom("creatorTime").value = (records.get('CREATOR_TIME')==null) ? "" : records.get('CREATOR_TIME'); 
   		    	
   		    	Ext.getDom("creditHisId").value = (records.get('CREDIT_HIS_ID')==null) ? "" : records.get('CREDIT_HIS_ID'); 
   		    	Ext.getDom("versionNo").value = (records.get('VERSION_NO')==null) ? "" : records.get('VERSION_NO');
   		    	Ext.getDom("isExecuted").value = (records.get('IS_EXECUTED')==null) ? "" : records.get('IS_EXECUTED');
   		    	Ext.getDom("changeTime").value = (records.get('CHANGE_TIME')==null) ? "" : records.get('CHANGE_TIME');
   		    	Ext.getDom("changeExecTime").value = (records.get('CHANGE_EXEC_TIME')==null) ? "" : records.get('CHANGE_EXEC_TIME');
   		    	Ext.getDom("changeState").value = (records.get('CHANGE_STATE')==null) ? "" : records.get('CHANGE_STATE');
   		    	
   		    	Ext.getDom("contractNo").value = (records.get('CONTRACT_NO')==null) ? "" : records.get('CONTRACT_NO');
   		    	Ext.getDom("sapOrderNo").value = (records.get('SAP_ORDER_NO')==null) ? "" : records.get('SAP_ORDER_NO');
   		    	Ext.getDom("projectNo").value = (records.get('PROJECT_NO')==null) ? "" : records.get('PROJECT_NO');
   		    	Ext.getDom("projectName").value = (records.get('PROJECT_NAME')==null) ? "" : records.get('PROJECT_NAME');
   		    	
   		    	Ext.getDom("creditNo").value = (records.get('CREDIT_NO')==null) ? "" : records.get('CREDIT_NO');
   		    	Ext.getDom("customCreateDate").value = (records.get('CUSTOM_CREATE_DATE')==null) ? "" : records.get('CUSTOM_CREATE_DATE');
   		    	Ext.getDom("createBank").value = (records.get('CREATE_BANK')==null) ? "" : records.get('CREATE_BANK');
   		    	Ext.getDom("request").value = (records.get('REQUEST')==null) ? "" : records.get('REQUEST');
   		    	Ext.getDom("country").value = (records.get('COUNTRY')==null) ? "" : records.get('COUNTRY');
   		    	Ext.getDom("benefit").value = (records.get('BENEFIT')==null) ? "" : records.get('BENEFIT');
   		    	
   		    	Ext.getDom("rate").value = (records.get('RATE')==null) ? "" : records.get('RATE');
   		    	Ext.getDom("amount").value = (records.get('AMOUNT')==null) ? "" : records.get('AMOUNT');
   		    	Ext.getDom("paymentType").value = (records.get('PAYMENT_TYPE')==null) ? "" : records.get('PAYMENT_TYPE');
   		    	
   		    	Ext.getDom("goods").value = (records.get('GOODS')==null) ? "" : records.get('GOODS'); 
   		    	Ext.getDom("validDate").value = (records.get('VALID_DATE')==null) ? "" : records.get('VALID_DATE'); 
   		    	Ext.getDom("invoice").value = (records.get('INVOICE')==null) ? "" : records.get('INVOICE');
   		    	Ext.getDom("specification").value = (records.get('SPECIFICATION')==null) ? "" : records.get('SPECIFICATION'); 
   		    	Ext.getDom("mark").value = (records.get('MARK')==null) ? "" : records.get('MARK'); 
   		    	Ext.getDom("billOfLading").value = (records.get('BILL_OF_LADING')==null) ? "" : records.get('BILL_OF_LADING'); 
   		    	Ext.getDom("billOfInsurance").value = (records.get('BILL_OF_INSURANCE')==null) ? "" : records.get('BILL_OF_INSURANCE'); 
   		
   		    	Ext.getDom("billOfQuality").value = (records.get('BILL_OF_QUALITY')==null) ? "" : records.get('BILL_OF_QUALITY');
   		    	Ext.getDom("benefitCertification").value = (records.get('BENEFIT_CERTIFICATION')==null) ? "" : records.get('BENEFIT_CERTIFICATION')
   		    	Ext.getDom("certificateOfOrigin").value = (records.get('CERTIFICATE_OF_ORIGIN')==null) ? "" : records.get('CERTIFICATE_OF_ORIGIN')
   		    	Ext.getDom("packingSlip").value =  (records.get('PACKING_SLIP')==null) ? "" : records.get('PACKING_SLIP')
   		    	Ext.getDom("loadingPeriod").value =(records.get('LOADING_PERIOD')==null) ? "" : records.get('LOADING_PERIOD'); 
   		    	
   		    	Ext.getDom("portOfShipment").value = (records.get('PORT_OF_SHIPMENT')==null) ? "" : records.get('PORT_OF_SHIPMENT'); 
   		    	Ext.getDom("portOfDestination").value = (records.get('PORT_OF_DESTINATION')==null) ? "" : records.get('PORT_OF_DESTINATION'); 
   		    	Ext.getDom("otherDocuments").value = (records.get('OTHER_DOCUMENTS')==null) ? "" : records.get('OTHER_DOCUMENTS'); 
   		    	//Ext.getDom("paymentDate").value = (records.get('PAYMENT_DATE')==null) ? "" : records.get('PAYMENT_DATE'); 付款日期 已经去掉
   		    	Ext.getDom("place").value = (records.get('PLACE')==null) ? "" : records.get('PLACE'); 
   		    	Ext.getDom("transShipment").value = (records.get('TRANS_SHIPMENT')==null) ? "" : records.get('TRANS_SHIPMENT'); 
   		    	Ext.getDom("canBatches").value = (records.get('CAN_BATCHES')==null) ? "" : records.get('CAN_BATCHES'); 
   		    	Ext.getDom("specialConditions").value = (records.get('SPECIAL_CONDITIONS')==null) ? "" : records.get('SPECIAL_CONDITIONS'); 
   		    	
   		    	Ext.getDom("billConditions").value = (records.get('BILL_CONDITIONS')==null) ? "" : records.get('BILL_CONDITIONS'); 
   		    	Ext.getDom("creditRecDate").value = (records.get('CREDIT_REC_DATE')==null) ? "" : records.get('CREDIT_REC_DATE'); 
   		    	Ext.getDom("electricShip").value = (records.get('ELECTRIC_SHIP')==null) ? "" : records.get('ELECTRIC_SHIP'); 
                
                Ext.getDom("CREDIT_STATE").value = (records.get('CREDIT_STATE')==null) ? "" : records.get('CREDIT_STATE'); 
                Ext.getDom("availDate").value = (records.get('AVAIL_DATE')==null) ? "" : records.get('AVAIL_DATE'); 
   			    dict_div_currency.setComboValue((records.get('CURRENCY')==null) ? "":records.get('CURRENCY'));
   			    dict_div_ymatGroup.setComboValue((records.get('YMAT_GROUP')==null) ? "":records.get('YMAT_GROUP'));
   		        ProcessingHint.Close();

   			}
   		}
       }

   //到证日期
   	var creditRecDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'creditRecDate',
	    name:'creditRecDate',
		width: 130,
	    readOnly:true,
		applyTo:'creditRecDate'
   	});  
   	 	
   	//客户开证日期
   	var customCreateDate = new Ext.form.DateField({
	  	format:'Y-m-d',
		id:'customCreateDate',
	    name:'customCreateDate',
		width: 130,
	    readOnly:true,
		applyTo:'customCreateDate'
   	});
   	
   	//validDate
   	var validDate = new Ext.form.DateField({
	  	format:'Y-m-d',
		id:'validDate',
	    name:'validDate',
		width: 130,
	    readOnly:true,
		applyTo:'validDate'
   	});

  //装期
   	var loadingPeriod = new Ext.form.DateField({
	  	format:'Ymd',
		id:'loadingPeriod',
	    name:'loadingPeriod',
		width: 130,
	    readOnly:true,
		applyTo:'loadingPeriod'
   	});
   	
   	if(operate != null || operate !="" )
    {
	   Ext.getDom('recordhdinputname').value='<%=request.getAttribute("creditId")%>';
    }

    Ext.getDom('lbCREDIT_STATE').style.display = "none";
    Ext.getDom('CREDIT_STATE').style.display = "none";

    
   	if(operate != null || operate !="" )
    {
	   Ext.getDom('recordhdinputname').value='<%=request.getAttribute("creditId")%>';
    }
    
     //如果是执行修改操作 modify workflow iframe
    if(operate=='iframe')
    {
        Ext.getDom('btn_Save').style.display = "none";
    	Ext.getDom('btn_Submit').style.display = "none";
    	Ext.getDom('btn_close').style.display = "none";

        var taskName = parent.document.getElementById("workflowCurrentTaskName").value;
        if(taskName=="贸管人员更改信用证状态")
        {
        	Ext.getDom('lbCREDIT_STATE').style.display = "";
        	Ext.getDom('CREDIT_STATE').style.display = "";
        }
    }
    else if(operate=='change')
    {

    }
    
    if(Ext.getDom('btn_Save').disabled)  //但无改证权限时候，则不能选择合同信息。
    {
       Ext.getDom('btnselectContractInfo').disabled = "true";
       Ext.getDom('btnselectProjectInfo').disabled = "true";
       Ext.getDom('btnselectDept').disabled = "true";
      // Ext.getDom('btnselectContractInfo').style.display = "none";
    }
    if(${CHANGE_CREDIT_STATUS}){
		Ext.getDom('lbCREDIT_STATE').style.display = "";
  		Ext.getDom('CREDIT_STATE').style.display = "";
  		$('CREDIT_STATE').value='${main.creditState}';
  		Ext.getDom('btn_Submit').style.display = "none";
    }
    
});//end of Ext.onReady   

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

//关闭窗体
function closeForm(){
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否退出并关闭窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
 			if(buttonId=='yes'){
 				//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
				top.ExtModalWindowUtil.markUpdated();
				top.ExtModalWindowUtil.close();
			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
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
//保存信用证状态
var saveCreditStatus=function(){
	var parm='?action=saveCreditStatus&creditId=${creditId}&creditStatus='+$('CREDIT_STATE').value;
	new AjaxEngine('creditArriveController.spr',
			 {method:"post", parameters:parm, onComplete:callBackHandle});
}

//提交工作流审批
function submitWorkflow(){

	Ext.Msg.show({
   		title:'提示',
   		msg: '是否确定提交流程审批 ',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
				new AjaxEngine('creditArriveHisController.spr?action=submitWorkflow&doWorkflow=mainForm',
					 {method:"post", parameters: Form.serialize('mainForm'), onComplete: submitWorkflowCallBackHandle});
   			}
   		},
   		icon: Ext.MessageBox.QUESTION
	});
}	

//提交审批流程回调函数
function submitWorkflowCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
//	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	showMsg(customField.returnMessage);
	setTimeout(function(){
		//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	},1000);	
}

//改证 保存的 自定义回调函数
function customCallBackHandle(transport)
{
  //alert(transport.responseText);
  
  var materialOrg=dict_div_ymatGroup.getActualValue();			
  if(materialOrg==""){
	top.ExtModalWindowUtil.alert('提示','请选择物料组！');
	return;
  }
  var responseUtil = new AjaxResponseUtils(transport.responseText);
  var result = responseUtil.getCustomField("coustom");
  var creditHisId = result.creditHisId
 
 if(creditHisId!= null && creditHisId != "")
 {
 	Ext.getDom("creditHisId").value = result.creditHisId; 
 }
//  alert("原始版本号值：" + Ext.getDom("versionNo").value);
//  alert("最新版本号值:" + result.versionNo);

  Ext.getDom("versionNo").value =result.versionNo;
  Ext.getDom("changeState").value =result.changeState;

  //creditEntryHisInfods.reload();
  creditArriveHisInfods.load({
    params:{start:0, limit:1000},
    callback:function(records, options, success){
      var sm =creditArriveHisInfogrid.getSelectionModel();
      sm.selectRow(0, true);
   }});
   
   if(result.versionNo!='0')
   {
     //Ext.getDom("btn_Submit").style.display = "";
     //var btn_Submit = Ext.getDom("btn_Submit");
     // btn_Submit.style.display = "";
     //btn_Submit.disabled=false;
      var btn_Submit1=Ext.getCmp("btn_Submit");
      btn_Submit1.show();
   }
   
  var msg = responseUtil.getMessage();

  var promptMessagebox = new MessageBoxUtil();
  promptMessagebox.show('info',msg);
  
}


//合同选择窗体
function selectContractInfo()
{
    var contractNo=  document.mainForm.contractNo.value;
    //orderState=3 为审核通过的采购订单合同
 	top.ExtModalWindowUtil.show('查询所属登录员工部门的合同信息',
	'creditArriveController.spr?action=selectContractInfo&tradeType=' + ${tradeType} +'&deptid=&orderState=3&contractType=P&contractNo='+ contractNo,
	'',
	selectContractInfoCallBack,
	{width:880,height:500});	
}

//合同选择窗体 回调函数。
function selectContractInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    var contractNo = "";
    var sapOrderNo = "";
    var projectNo = "";
    var projectName = "";
    var contractSalesId =""; //合同ID CONTRACT_SALES_ID 
     var ymatGroup = "";//物料组
     
    if(returnvalue.length >0)
    {
        for(i=0;i<=returnvalue.length -1;i++)
        {
          if(returnvalue[i].data.CONTRACT_NO != null && returnvalue[i].data.CONTRACT_NO != "")
          {
         	 contractNo = contractNo + returnvalue[i].data.CONTRACT_NO + ",";
          }
          else
          {
         	 contractNo = contractNo + "" + ",";
          }
          
          //销售合同ID
          if(returnvalue[i].data.CONTRACT_SALES_ID != null && returnvalue[i].data.CONTRACT_SALES_ID != "")
          {
         	 contractSalesId = contractSalesId + returnvalue[i].data.CONTRACT_SALES_ID + ",";
          }
          else
          {
         	 contractSalesId = contractSalesId + "" + ",";
          }
          
          if(returnvalue[i].data.SAP_ORDER_NO != null && returnvalue[i].data.SAP_ORDER_NO != "")
          {
         	sapOrderNo = sapOrderNo + returnvalue[i].data.SAP_ORDER_NO + ",";
          }
          else if(returnvalue.length==1)
          {
            sapOrderNo = "";
          }
          else
          {
             sapOrderNo = sapOrderNo + "" + ",";
          }
          
          if(returnvalue[i].data.PROJECT_NO != null && returnvalue[i].data.PROJECT_NO != "")
          {
         	 projectNo = projectNo + returnvalue[i].data.PROJECT_NO + "," ;
          }
          else if(returnvalue.length==1)
          {
            projectNo = "";
          }
          else
          {
             projectNo = projectNo + "" + "," ;
          }
          
          if(returnvalue[i].data.PROJECT_NAME != null && returnvalue[i].data.PROJECT_NAME != "")
          {
         	 	projectName = projectName + returnvalue[i].data.PROJECT_NAME+ ",";
          }
          else if(returnvalue.length==1)
          {
            projectName = "";
          }
          else
          {
             projectName = projectName + "" + ",";
          }
          if(returnvalue[i].data.YMAT_GROUP != null && returnvalue[i].data.YMAT_GROUP != "")
          {
         	 ymatGroup = ymatGroup + returnvalue[i].data.YMAT_GROUP + ",";
          }
          else
          {
         	 ymatGroup = ymatGroup + "" + ",";
          }
        }
        
        if(contractSalesId.length>0)
          contractSalesId = contractSalesId.substr(0,contractSalesId.length-1);
        if(contractNo.length>0)
          contractNo = contractNo.substr(0,contractNo.length-1);
        if(sapOrderNo.length>0)
          sapOrderNo = sapOrderNo.substr(0,sapOrderNo.length-1);
        if(projectNo.length>0)
          projectNo = projectNo.substr(0,projectNo.length-1);
        if(projectName.length>0)
          projectName = projectName.substr(0,projectName.length-1);   
        if(ymatGroup.length>0)
          ymatGroup = ymatGroup.substr(0,ymatGroup.length-1);  
    }

	dict_div_ymatGroup.setComboValue(ymatGroup);
    document.mainForm.contractSalesId.value = contractSalesId;
	document.mainForm.contractId.value = contractSalesId;
    document.mainForm.contractNo.value = contractNo;
	document.mainForm.sapOrderNo.value = sapOrderNo;
 	document.mainForm.projectNo.value = projectNo;
	document.mainForm.projectName.value = projectName;
}

function operaRD(value,metadata,record){
	var state = record.data.CREDIT_STATE_D_CREDIT_STATE;
	var changeState = record.data.CHANGE_STATE;

	if(changeState=='2' || changeState=='4' || changeState=='5')
	{
	return '<a href="#" onclick="viewChangeHistory();" >流程跟踪</a> <c:if test="${deptIndex>=0}"><a href="#" onclick="prePrint()">打印</a> </c:if>';
	}
	else if(record.data.VERSION_NO=='0'){
	  return '<a href="#" onclick="prePrint()">打印</a>'
	}
	else
	{
		return '';
	}
}
function prePrint()
{
   if(creditArriveHisInfogrid.selModel.hasSelection())
   {
	   var records = creditArriveHisInfogrid.selModel.getSelections();
	   var creditHisId = records[0].json.CREDIT_HIS_ID;
       window.open('creditArriveHisController.spr?action=dealPrint&creditHisId='+creditHisId,'_blank','location=no,resizable=yes');
   }
}
//信用证改证，流程跟踪
function viewChangeHistory()
{
	var records = creditArriveHisInfogrid.selModel.getSelections();
//	alert("CREDIT_HIS_ID:" + records[0].json.CREDIT_HIS_ID);
	top.ExtModalWindowUtil.show(strcreditArriveTitle + '审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.CREDIT_HIS_ID,
		'',	'',	{width:880,height:400});
}
//立项选择窗体
function selectProjectInfo(){
	var deptid = Ext.getDom("deptid").value;
	top.ExtModalWindowUtil.show('查询所属登陆员工总门的立项信息',
	'creditArriveController.spr?action=selectProjrctInfo&tradeType=' + ${tradeType} +'&deptid=' + deptid,
	'',
	selectProjectInfoCallBack,
	{width:560,height:300});
}

//立项选择回调函数
function selectProjectInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	document.mainForm.projectName.value = returnvalue.PROJECT_NAME;
	document.mainForm.projectNo.value = returnvalue.PROJECT_NO;
	dict_div_ymatGroup.setComboValue(returnvalue.YMAT_GROUP);
}

//部门(受益人)选择窗体
function selectDeptInfo()
{
 	top.ExtModalWindowUtil.show('选择受益人',
	'creditArriveController.spr?action=selectDeptInfo',
	'',
	selectDeptInfoCallBack,
	{width:410,height:300});	
}

function selectDeptInfoCallBack()
{	
	 var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	 document.mainForm.benefit.value = returnvalue.DEPT_NAME ;
}

function handlerActivate(tab){
	rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
} 
function renderHallName(value, meta, rec, rowIdx, colIdx, ds)
 {
    return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
 }  
</script>
</head>
<body>
<div id='div_progressBar'></div>
<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
	<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
	    <tr>
			<td width="10%" align="right">合同号：</td>
			<td width="15%" align="left"><nobr><input name="contractNo"
				type="text" size="12" tabindex="2" value="${main.contractNo}" readonly="readonly" /> 
				<input type="button" value="..." id="btnselectContractInfo" name="btnselectContractInfo" onclick="selectContractInfo()">
				<input name="contractSalesId" type="hidden"/>
				<input name="contractId" type="hidden" value="${main.contractId}" />
				<a href="#" onclick="Utils.showSaleContract('${main.contractId}')">查看</a>
				</nobr>
			</td>
			<td width="10%" align="right"><nobr>SAP订单号：</nobr></td>
			<td width="15%" align="left"><input name="sapOrderNo"
				type="text" size="14" tabindex="2" value="${main.sapOrderNo}" readonly="readonly" /></td>
			<td width="10%" align="right">立项号：</td>
			<td width="15%" align="left"><input name="projectNo"
				type="text" size="12" tabindex="2" value="${main.projectNo}" readonly="readonly" />
				<input type="button" value="..." id="btnselectProjectInfo" name="btnselectProjectInfo" onclick="selectProjectInfo()">
				<a href="#" onclick="Utils.showProjectByNo('${main.projectNo}')">查看</a>
			</td>
			<td width="10%" align="right"><nobr>立项名称：</nobr></td>
			<td width="15%" align="left"><input
				name="projectName" type="text" size="14" tabindex="2" value="${main.projectName}" readonly="readonly" /></td>
		</tr>
      <tr>
      <td width="11%" align="right">审证人：</td>
        <td width="22%" align="left">
			<input name="applyer" type="text" size="14" tabindex="1" value="${main.applyer}"/>
        </td>
        <td width="11%" align="right">信用证号：</td>
        <td width="22%" align="left">
        	<input name="creditNo" type="text" size="14" tabindex="1" value="${main.creditNo}" />
        </td>
        <td width="11%" align="right"><nobr>客户开(改)证日期：</nobr></td>
        <td width="22%" align="left">
        	<input name="customCreateDate" type="text" size="14" tabindex="1" value="${main.customCreateDate}" readonly="readonly" />
        </td>
        <td width="11%" align="right">收(改)证到证日期：</td>
        <td width="22%" align="left">
            <input name="creditRecDate" type="text" size="14" tabindex="1" value="${main.creditRecDate}" readonly="readonly" />
        </td>
        
      </tr>
      <tr>
        <td width="11%" align="right"><nobr>国别/地区：</nobr></td>
        <td width="22%" align="left">
			<input name="country" type="text" size="14" tabindex="1" value="${main.country}" />
        </td>
        <td width="11%" align="right">付款方式：</td>
        <td width="22%" align="left">
			<input name="paymentType" type="text" size="14" tabindex="1" value="${main.paymentType}" />
        </td>
        <td width="11%" align="right">开证行：</td>
		<td width="22%" align="left">
			<input name="createBank" type="text" size="14" tabindex="2" value="${main.createBank}" />
		</td>
		
        <td width="11%" align="right">
        <label id="lbCREDIT_STATE">信用证状态：</label>
        </td>
        <td width="22%" align="left">
            <select id="CREDIT_STATE" name="CREDIT_STATE">
				<option value="">请选择</option>
				<option value="5">备用</option>
				<option value="7">撤销</option>
				<option value="8">关闭</option>
				<option value="11">改证通过</option>
				<option value="15">接受</option>
				<option value="16">不接受</option>
			</select>
        </td>
      </tr>
     <tr>
     
        <td width="11%" align="right">币别：</td>
        <td width="22%" align="left">
            <div id="div_currency" name="div_currency"></div>
        </td>
        <td width="11%" align="right">金额：</td>
        <td width="22%" align="left">
			<input name="amount" type="text" size="14" tabindex="1" value="${main.amount}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>汇率：</td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
            <input name="rate" type="text" size="14" tabindex="1" value="${main.rate}" />
        </td>
        <td width="11%" align="right"><font color="red">*</font>受益人：</td>
        <td width="22%" align="left">
			<input name="benefit" type="text" size="14" tabindex="2" value="${main.benefit}" readonly="readonly"/><input type="button" value="..." name="btnselectDept" title="选择部门" onclick="selectDeptInfo()">
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">开证申请人：</td>
        <td width="22%" align="left">
			<input name="request" type="text" size="14" tabindex="1" value="${main.request}"/>
        </td>
        <td width="11%" align="right">货物品名：</td>
        <td width="22%" align="left">
			<input name="goods" type="text" size="14" tabindex="1" value="${main.goods}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>规格：</td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="specification" type="text" size="14" tabindex="1" value="${main.specification}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>唛头：</td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
        	<input name="mark" type="text" size="14" tabindex="1" value="${main.mark}"/>
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">发票：</td>
        <td width="22%" align="left">
			<input name="invoice" type="text" size="14" tabindex="1" value="${main.invoice}" />
        </td>
        <td width="11%" align="right"><c:if test="${saleTradeType=='2'}">运输单据/货物收据：</c:if><c:if test="${saleTradeType=='3'}">提单：</c:if></td>
        <td width="22%" align="left">
			<input name="billOfLading" type="text" size="14" tabindex="1" value="${main.billOfLading}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>保险单：</td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="billOfInsurance" type="text" size="14" tabindex="1" value="${main.billOfInsurance}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>><nobr>品质(分析证)：</nobr></td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="billOfQuality" type="text" size="14" tabindex="1" value="${main.billOfQuality}" />
        </td>
      </tr>
      <tr>
        
        <td width="11%" align="right"><nobr>受益人证明：</nobr></td>
        <td width="22%" align="left">
			<input name="benefitCertification" type="text" size="14" tabindex="1" value="${main.benefitCertification}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>产地证：</td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="certificateOfOrigin" type="text" size="14" tabindex="1" value="${main.certificateOfOrigin}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>装箱单：</td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="packingSlip" type="text" size="14" tabindex="1" value="${main.packingSlip}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>装船电：</td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="electricShip" type="text" size="14" tabindex="1" value="${main.electricShip}" />
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">其他单据：</td>
        <td width="22%" align="left" colspan="5">
			<input name="otherDocuments" type="text" size="93" tabindex="1" value="${main.otherDocuments}" />
        </td>
        <td align="right" width="11%">	
		<font color="red">*</font>物料组:
		</td>
		<td width="22%" >	
			<div id="div_ymatGroup"></div>
		</td> 
      </tr>
      <tr>
        <td width="11%" align="right">装期：</td>
        <td width="22%" align="left">
			<input name="loadingPeriod" type="text" size="14" tabindex="1" value="${main.loadingPeriod}" />
        </td>
        <td width="11%" align="right"><c:if test="${saleTradeType=='2'}">装运地</c:if><c:if test="${saleTradeType=='3'}">装运港</c:if><font color="red">▲</font></td>
        <td width="22%" align="left">
			<input name="portOfShipment" type="text" size="14" tabindex="1" value="${main.portOfShipment}" />
        </td>
        <td width="11%" align="right"><c:if test="${saleTradeType=='2'}">目的地</c:if><c:if test="${saleTradeType=='3'}">目的港</c:if></td>
        <td width="22%" align="left">
			<input name="portOfDestination"	type="text" size="14" tabindex="1" value="${main.portOfDestination}" />
        </td>
        <td width="11%" align="right">有效期：</td>
        <td width="22%" align="left">
            <input name="validDate" type="text" size="14" tabindex="1" value="${main.validDate}" readonly="readonly" />
        </td>
      </tr>
      <tr>
      
      	  
	      <td width="11%" align="right">可否分批：</td>
		<td width="22%" align="left">
			<select id="canBatches" name="canBatches">
				<option value="">请选择</option>
				<option value="1"
					<c:if test="${main.canBatches=='1'}"> selected </c:if>>是</option>
				<option value="0"
					<c:if test="${main.canBatches=='0'}"> selected </c:if>>否</option>
			</select>
		</td>
	    <td width="11%" align="right"><c:if test="${saleTradeType=='2'}">可否转运</c:if><c:if test="${saleTradeType=='3'}">可否转船</c:if>：</td>
        <td width="22%" align="left">
			<select id="transShipment" name="transShipment">
				<option value="">请选择</option>
				<option value="1"
					<c:if test="${main.transShipment=='1'}"> selected </c:if>>是</option>
				<option value="0"
					<c:if test="${main.transShipment=='0'}"> selected </c:if>>否</option>
			</select>
        </td>
        <td width="11%" align="right">期限：</td>
	      <td width="22%" align="left">
			 <input name=availDate type="text" size="14" tabindex="31" value="${main.availDate}" />
	      </td>
        <td width="11%" align="right">到期地点：</td>
	      <td width="22%" align="left">
			 <input name="place" type="text" size="14" tabindex="1" value="${main.place}" />
	      </td>
      </tr>
     <tr>
        <td width="11%" align="right" valign="top">特别条款：</td>
        <td width="88%" align="left" colspan="7">
        	<textarea cols="97" rows="10" id="specialConditions" name="specialConditions">${main.specialConditions}</textarea>
        </td>
     </tr>
    <tr>
		<td width="11%" align="right" valign="top"><nobr>应修改事项：</nobr></td>
		<td width="88%" align="left" colspan="7">
        	<textarea cols="97" rows="3" id="billConditions" name="billConditions">${main.billConditions}</textarea>
		</td>
    </tr>

	</table>

<div id="div_CreditArriveInfo"></div>
<input name="tradeType" type="hidden" size="20" value="${main.tradeType}" /> 
<input name="createOrRec" type="hidden" size="20" value="${main.createOrRec}" /> 
<input name="deptId" type="hidden" size="20" value="${main.deptId}" /> 
<input name="creditId" type="hidden" size="40" value="${creditId}" />
<input name="creditState" type="hidden" size="40" value="${main.creditState}" />
<input name="isAvailable" type="hidden" size="40" value="${main.isAvailable}" />
<input name="applyTime" type="hidden" size="40" value="${main.applyTime}" />
<input name="approvedTime" type="hidden" size="40" value="${main.approvedTime}" /> 
<input name="creatorTime" type="hidden" size="40" value="${main.creatorTime}" />
<input name="creator" type="hidden" size="40" value="${main.creator}" />


<input name="creditHisId" type="hidden" size="40" value="${main.creditHisId}" />
<input name="versionNo" type="hidden" size="40" value="${main.versionNo}" />
<input name="isExecuted" type="hidden" size="40" value="${main.isExecuted}" />
<input name="changeTime" type="hidden" size="40" value="${main.changeTime}" />
<input name="changeExecTime" type="hidden" size="40" value="${main.changeExecTime}" />
<input name="changeState" type="hidden" size="40" value="${main.changeState}" />
</form>
</div>

<div id="div_creditArriveHisInfogrid"></div>
<div id="rule" class="x-hide-display" ></div>
</body>
</html>
<fiscxd:fileUpload divId="rule" erasable="${revisable}"  increasable="${revisable}"
 resourceId="CREDITARRIVE" resourceName="CREDITARRIVE" recordId="${creditId}" recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>
<fiscxd:dictionary divId="div_currency" fieldName="currency" dictionaryName="bm_currency" 
width="150" selectedValue="${main.currency}" disable="false" >
</fiscxd:dictionary>
    

<fiscxd:dictionary divId="div_ymatGroup" fieldName="ymatGroup" dictionaryName="YMATGROUP" width="150" selectedValue="${main.ymatGroup}" disable="false" ></fiscxd:dictionary>
    