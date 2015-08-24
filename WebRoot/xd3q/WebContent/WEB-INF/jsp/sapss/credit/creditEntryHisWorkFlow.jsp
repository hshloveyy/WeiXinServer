<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信用证开证审查登记表修改历史记录</title>
<style type="text/css">
.add {
	background-image: url(<%= request.getContextPath() %>/images/fam/add.gif ) !important;
}

.delete {
	background-image: url(<%= request.getContextPath() %>/images/fam/delete.gif ) !important;
}

.update {
	background-image: url(<%= request.getContextPath() %>/images/fam/refresh.gif ) !important;
}

.find {
	background-image: url(<%= request.getContextPath() %>/images/fam/find.png ) !important;
}
</style>
<script type="text/javascript">
//操作类别  
var operate = '${operate}';
//贸易类型
var tradeType = '${tradeType}';
//工作流当前节点名称标识码
var taskType = '${taskType}';

var fileDisable = (operate=="" ||operate==null) ? true:false;
var changeSubmitDisabled = true;

var creditEntryHisInfogrid;
var creditEntryHisInfods;

var viewHistory = true; 
if(operate =="view" )
{
    viewHistory = false;
}

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

   	var creditEntryHisInfoPlant = Ext.data.Record.create([
   	    {name:'CREDIT_HIS_ID'},       //信用证历史ID CREDIT_ID
   	    {name:'CREDIT_ID'},           //信用证ID 
   	   	{name:'VERSION_NO'},          //版本号
   	   	{name:'CHANGE_TIME'},         //变更时间
   	   	{name:'CHANGE_EXEC_TIME'},    //变更执行时间
   	   	{name:'IS_EXECUTED'},         //变更是否执行
		{name:'CREDIT_NO'},           //信用证编号
		{name:'CREATE_DATE'},         //开证日期
		{name:'DEPT_NAME'},           //部门名称
		{name:'PROJECT_NO'},          //立项号
		{name:'CONTRACT_NO'},         //合同编码
		{name:'CONTRACT_ID'},         //合同编码
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
		{name:'MODIFY_INFO'},
		{name:'AVAIL_DATE'},
		{name:'IS_AVAILABLE'},
		{name:'CREDIT_INFO'}																																																											
	    
	    //CREDIT_HIS_ID,CREDIT_ID,VERSION_NO,IS_EXECUTED,CHANGE_TIME,CHANGE_EXEC_TIME,TRADE_TYPE,CREDIT_STATE,CREDIT_NO,PROJECT_NO,PROJECT_NAME,SAP_ORDER_NO,CONTRACT_NO,CREATE_OR_REC,CUSTOM_CREATE_DATE,CREDIT_REC_DATE,CREATE_BANK,CREATE_DATE,VALID_DATE,COUNTRY,REQUEST,BENEFIT,BENEFIT_CERTIFICATION,PAYMENT_TYPE,AMOUNT,RATE,CURRENCY,GOODS,SPECIFICATION,MARK,INVOICE,BILL_OF_LADING,BILL_OF_INSURANCE,BILL_OF_QUALITY,CERTIFICATE_OF_ORIGIN,PACKING_SLIP,ELECTRIC_SHIP,DISPATCH_ELECTRIC,OTHER_DOCUMENTS,LOADING_PERIOD,PERIOD,PLACE,CAN_BATCHES,TRANS_SHIPMENT,PORT_OF_SHIPMENT,PORT_OF_DESTINATION,PAYMENT_DATE,PRE_SECURITY,WRITE_OFF_SINGLE_NO,SPECIAL_CONDITIONS,BILL_CONDITIONS,MATTERS_SHOULD_BE_AMENDED,CMD,DEPT_ID,APPLY_TIME,APPROVED_TIME,IS_AVAILABLE,CREATOR_TIME,CREATOR
	    
	]);

	creditEntryHisInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'creditEntryHisController.spr?action=queryCreditHisInfo&creditId='+'${creditId}'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},creditEntryHisInfoPlant)
    });
    
    var creditEntryHisInfosm = new Ext.grid.CheckboxSelectionModel();   
    
    var creditEntryHisInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		creditEntryHisInfosm,
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
	           width: 50,
	           sortable: true,
	           dataIndex: 'VERSION_NO'
          },
		  {
			   header: '变更时间',
	           width: 115,
	           sortable: true,
	           dataIndex: 'CHANGE_TIME'
           },
		   {
			   header: '变更执行时间',
	           width: 115,
	           sortable: true,
	           dataIndex: 'CHANGE_EXEC_TIME'
           },
		　 {
			   header: '信用证号',
	           width: 100,
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
	       		header: '变更是否执行',
	       		width: 100,
	       		sortable: false,
	       		dataIndex: 'IS_EXECUTED'
           }      
           ]);
           
    creditEntryHisInfocm.defaultSortable = true;  
    
   var creditEntryHisInfobbar = new Ext.PagingToolbar({
        pageSize: 1000,
        store:creditEntryHisInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    //        tbar: '',
	creditEntryHisInfogrid = new Ext.grid.EditorGridPanel({
    	id:'creditEntryHisInfogrid',
        ds: creditEntryHisInfods,
        cm: creditEntryHisInfocm,
        sm: creditEntryHisInfosm,
        bbar: creditEntryHisInfobbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_creditEntryHisInfogrid',
        autowidth:true,
        height:250,
        layout:'fit'
    });
    
    creditEntryHisInfods.load({
    params:{start:0, limit:1000},
    callback:function(records, options, success){
      var sm =creditEntryHisInfogrid.getSelectionModel();
      sm.selectRow(0, true);
    }});
        	
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'fit',
			buttonAlign:'center',
			collapsible: true,
			border:false,
			items:[{
				region:'center',
				xtype:'tabpanel',
				id:'infotype',
				name:'infotype',
				plain:true,
	            height:350,
	            autoScroll:'true',
	            activeTab: 0,
	            border:false,
	            items:[{
	            	title:'信用证开证改证',
	            	contentEl: 'div_main',
	            	id:'creditEntryInfo',
					name:'creditEntryInfo',
					autoScroll:'true',
	            	layout:'fit',
	            	border:false
	            	},
	            	{
	                title: '信用证附件',
	                contentEl:'rule',
	                id:'fileEl', 
	                disabled:fileDisable,
	                autoScroll:'true',
	                layout:'fit',
	                border:false,
	                listeners:{activate:handlerActivate}
	                },
	                {
	               	 contentEl:'history',
	               	 id:'historyEl', 
	               	 title: '开证审批历史记录',
	               	 disabled:false
	                }
	                ]
		       }]
		       }
	       	  ,{
	       	  		region:"south",
	            	title:'信用证修改历史',
	            	contentEl: '',
	            	height:150,
	            	collapsible: true,
	            	collapsed:false,
	            	id:'creditEntryHisInfo',
					name:'creditEntryHisInfo',
					layout:'fit',
	            	items:[creditEntryHisInfogrid]
		        }
		       ]
	});
	
	if(operate=='view')
	{
		creditEntryHisInfogrid.addListener('rowclick', creditEntryHisInfogridrowclick);
	}
	else if(operate=='change' || operate=='iframe')
	{
		creditEntryHisInfogrid.addListener('rowclick', creditEntryHisInfogridrowclick);
	}
	
	function creditEntryHisInfogridrowclick1(grid, rowIndex, e)
	{
		 var sm =grid.getSelectionModel();
		 sm.selectFirstRow();
		 //sm.selectRow(0, true);
	}
	
	//Grid 行,单击事件.    
    function creditEntryHisInfogridrowclick(grid, rowIndex, e){
    
      if (grid.selModel.hasSelection()){
			var records = grid.selModel.getSelections();
			if (records.length > 1)
			{
				top.ExtModalWindowUtil.alert('提示','只能选择一条记录进行修改！');
			}
			else
			{
				var records = grid.getStore().getAt(rowIndex);
		    	var CREDIT_ID = records.get('CREDIT_ID');
		    	var CREATOR =records.get('CREATOR'); 
		    	//alert("当前版本号:" + records.get('VERSION_NO'));
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
		    	
		    	//Ext.getDom("creditHisId").value = (records.get('CREDIT_HIS_ID')==null) ? "" : records.get('CREDIT_HIS_ID'); 
		    	Ext.getDom("versionNo").value = (records.get('VERSION_NO')==null) ? "" : records.get('VERSION_NO');
		    	Ext.getDom("isExecuted").value = (records.get('IS_EXECUTED')==null) ? "" : records.get('IS_EXECUTED');
		    	Ext.getDom("changeTime").value = (records.get('CHANGE_TIME')==null) ? "" : records.get('CHANGE_TIME');
		    	Ext.getDom("changeExecTime").value = (records.get('CHANGE_EXEC_TIME')==null) ? "" : records.get('CHANGE_EXEC_TIME');
		    	
		    	Ext.getDom("contractId").value = (records.get('CONTRACT_ID')==null) ? "" : records.get('CONTRACT_ID');
		    	Ext.getDom("contractNo").value = (records.get('CONTRACT_NO')==null) ? "" : records.get('CONTRACT_NO');
		    	Ext.getDom("sapOrderNo").value = (records.get('SAPORDER_NO')==null) ? "" : records.get('SAPORDER_NO');
		    	Ext.getDom("projectNo").value = (records.get('PROJECT_NO')==null) ? "" : records.get('PROJECT_NO');
		    	Ext.getDom("projectName").value = (records.get('PROJECT_NAME')==null) ? "" : records.get('PROJECT_NAME');
		    	
		    	Ext.getDom("creditNo").value = (records.get('CREDIT_NO')==null) ? "" : records.get('CREDIT_NO');
		    	Ext.getDom("createDate").value = (records.get('CREATE_DATE')==null) ? "" : records.get('CREATE_DATE');
		    	//Ext.getDom("createBank").value = (records.get('CREATE_BANK')==null) ? "" : records.get('CREATE_BANK');
		    	dict_div_createBank.setComboValue((records.get('CREATE_BANK')==null) ? "" : records.get('CREATE_BANK'));
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
		    	Ext.getDom("dispatchElectric").value = (records.get('DISPATCH_ELECTRIC')==null) ? "" : records.get('DISPATCH_ELECTRIC'); 
		    	Ext.getDom("loadingPeriod").value =(records.get('LOADING_PERIOD')==null) ? "" : records.get('LOADING_PERIOD'); 
		    	
		    	//Ext.getDom("period").value = (records.get('PERIOD')==null) ? "" : records.get('PERIOD');效期  已经无用。
		    	Ext.getDom("creditInfo").value = (records.get('creditInfo')==null) ? "" : records.get('creditInfo'); 
		    	
		    	Ext.getDom("portOfShipment").value = (records.get('PORT_OF_SHIPMENT')==null) ? "" : records.get('PORT_OF_SHIPMENT'); 
		    	Ext.getDom("portOfDestination").value = (records.get('PORT_OF_DESTINATION')==null) ? "" : records.get('PORT_OF_DESTINATION'); 
		    	Ext.getDom("otherDocuments").value = (records.get('OTHER_DOCUMENTS')==null) ? "" : records.get('OTHER_DOCUMENTS'); 
		    	Ext.getDom("writeOffSingleNo").value = (records.get('WRITE_OFF_SINGLE_NO')==null) ? "" : records.get('WRITE_OFF_SINGLE_NO'); 
		    	//Ext.getDom("paymentDate").value = (records.get('PAYMENT_DATE')==null) ? "" : records.get('PAYMENT_DATE'); 付款日期 已经去掉
		    	Ext.getDom("place").value = (records.get('PLACE')==null) ? "" : records.get('PLACE'); 
		    	Ext.getDom("transShipment").value = (records.get('TRANS_SHIPMENT')==null) ? "" : records.get('TRANS_SHIPMENT'); 
		    	Ext.getDom("canBatches").value = (records.get('CAN_BATCHES')==null) ? "" : records.get('CAN_BATCHES'); 
		    	Ext.getDom("preSecurity").value = (records.get('PRE_SECURITY')==null) ? "" : records.get('PRE_SECURITY'); 
		    	Ext.getDom("specialConditions").value = (records.get('SPECIAL_CONDITIONS')==null) ? "" : records.get('SPECIAL_CONDITIONS'); 
		    	Ext.getDom("modifyInfo").value = (records.get('MODIFY_INFO')==null) ? "" : records.get('MODIFY_INFO'); 
		    	Ext.getDom("creditInfo").value = (records.get('CREDIT_INFO')==null) ? "" : records.get('CREDIT_INFO'); 
		    	
		    	Ext.getDom("availDate").value = (records.get('AVAIL_DATE')==null) ? "" : records.get('AVAIL_DATE'); 
			    dict_div_currency.setComboValue((records.get('CURRENCY')==null) ? "":records.get('CURRENCY'));
			        	    	    	
		        ProcessingHint.Close();

			}
		}
    }

   //开证日期north south center
   	var createDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'createDate',
	    name:'createDate',
		width: 160,
	    readOnly:true,
		disabled:false,
		applyTo:'createDate'
   	});

   	//保证金日期 "bailDate"
    var bailDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'bailDate',
	    name:'bailDate',
		width: 160,
	    readOnly:true,
		disabled:true,
		applyTo:'bailDate'
   	});	
   /*	
   //付款日期
   var paymentDate = new Ext.form.DateField({
	  	format:'Y-m-d',
		id:'paymentDate',
	    name:'paymentDate',
		width: 160,
	    readOnly:true,
		applyTo:'paymentDate'
   	});
   	*/
    //validDate
   	var validDate = new Ext.form.DateField({
	  	format:'Y-m-d',
		id:'validDate',
	    name:'validDate',
		width: 160,
		disabled:true,
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

   	if(taskType=="5") //填写开证申请表及预保
    {
        Ext.getDom("btnselectContractInfo").disabled= false;
        Ext.getDom("request").readOnly = false;
        Ext.getDom("country").readOnly = false;
        Ext.getDom("paymentType").readOnly = false;
        Ext.getDom("benefit").readOnly = false;
        Ext.getDom("amount").readOnly = false;
        Ext.getDom("rate").readOnly = false;
        Ext.getDom("goods").readOnly = false;
        Ext.getDom("specification").readOnly = false;
        Ext.getDom("mark").readOnly = false;
        Ext.getDom("invoice").readOnly = false;
        Ext.getDom("billOfLading").readOnly = false;
        Ext.getDom("billOfInsurance").readOnly = false;
        Ext.getDom("billOfQuality").readOnly = false;
        Ext.getDom("benefitCertification").readOnly = false;
        Ext.getDom("certificateOfOrigin").readOnly = false;
        Ext.getDom("packingSlip").readOnly = false;
        Ext.getDom("dispatchElectric").readOnly = false;
        Ext.getDom("packingSlip").readOnly = false;
        Ext.getDom("otherDocuments").readOnly = false;
        Ext.getDom("portOfShipment").readOnly = false;
        Ext.getDom("portOfDestination").readOnly = false;
        Ext.getDom("loadingPeriod").readOnly = false;
        Ext.getDom("place").readOnly = false;
        Ext.getDom("modifyInfo").readOnly = false;
        Ext.getDom("availDate").readOnly = false;
        var validDate1=Ext.getCmp("validDate");
        validDate1.setDisabled(false);
        
        Ext.getDom("specialConditions").readOnly = false;
        Ext.getDom("specialConditions").readOnly = false;

        Ext.getDom("transShipment").disabled= false;
        Ext.getDom("canBatches").disabled= false;
        Ext.getDom("preSecurity").disabled= false;

        var bailDate1 =Ext.getCmp("bailDate");
        bailDate1.setDisabled(false);
        
        Ext.getDom("creditInfo").readOnly= false;
        dict_div_currency.disable(false);
    }

    
      
});//end of Ext.onReady   


//改证 保存的 自定义回调函数
function customCallBackHandle(transport)
{
  //alert(transport.responseText);
  var responseUtil = new AjaxResponseUtils(transport.responseText);
  var result = responseUtil.getCustomField("coustom");
  var creditHisId = result.creditHisId

 Ext.getDom('recordhdinputname').value=result.creditHisId;
 if(creditHisId!= null && creditHisId != "")
 {
 	Ext.getDom("creditHisId").value = result.creditHisId; 
 }
  //alert("原始版本号值：" + Ext.getDom("versionNo").value);
  //alert("最新版本号值:" + result.versionNo);

  Ext.getDom("versionNo").value =result.versionNo;
  Ext.getDom("changeState").value =result.changeState;

  //creditEntryHisInfods.reload();
  creditEntryHisInfods.load({
    params:{start:0, limit:1000},
    callback:function(records, options, success){
      var sm =creditEntryHisInfogrid.getSelectionModel();
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

	Ext.Msg.show({
   		title:'提示',
   		msg: '是否确定提交流程审批 ',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
				new AjaxEngine('creditEntryHisController.spr?action=submitWorkflow&doWorkflow=mainForm',
					 {method:"post", parameters: Form.serialize('mainForm'), onComplete: submitWorkflowCallBackHandle});
   			}
   		},
   		icon: Ext.MessageBox.QUESTION
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
 				_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
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

//合同选择窗体
function selectContractInfo()
{
    var contractNo=  document.mainForm.contractNo.value;
    //alert(contractNo);
    //orderState=3 为审核通过的采购订单合同
 	top.ExtModalWindowUtil.show('查询所属登陆员工部门的合同信息',
	'creditEntryController.spr?action=selectContractInfo&tradeType=' + ${tradeType} +'&deptid=&orderState=3&contractType=P&contractNo='+ contractNo,
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
    var contractPurchaseId =""; //合同ID CONTRACT_PURCHASE_ID 
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
          //采购合同ID
          if(returnvalue[i].data.CONTRACT_PURCHASE_ID != null && returnvalue[i].data.CONTRACT_PURCHASE_ID != "")
          {
         	 contractPurchaseId = contractPurchaseId + returnvalue[i].data.CONTRACT_PURCHASE_ID + ",";
          }
          else
          {
         	 contractPurchaseId = contractPurchaseId + "" + ",";
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
        
        if(contractPurchaseId.length>0)
          contractPurchaseId = contractPurchaseId.substr(0,contractPurchaseId.length-1);
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
    document.mainForm.contractPurchaseId.value = contractPurchaseId;
	document.mainForm.contractId.value = contractPurchaseId;
    document.mainForm.contractNo.value = contractNo;
	document.mainForm.sapOrderNo.value = sapOrderNo;
 	document.mainForm.projectNo.value = projectNo;
	document.mainForm.projectName.value = projectName;
}

//提交审批流程回调函数
function submitWorkflowCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
//	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	showMsg(customField.returnMessage);
	setTimeout(function(){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	},1000);	
}

function handlerActivate(tab){
	rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
} 

function renderHallName(value, meta, rec, rowIdx, colIdx, ds)
 {
    return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
 }  
 function viewPurchaseForm(){
	        var contractPurchaseId = Ext.getDom("contractId").value
	        if(contractPurchaseId=='') {
	           showMsg('请选择采购合同信息！');
	           return ;
	        }
	        top.ExtModalWindowUtil.show('采购合同信息','contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+contractPurchaseId,'','',{width:900,height:550});
       }
</script>
</head>
<body>
<div id='div_progressBar'></div>
<div id="div_basrForm">
</div>
<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
	<tr>
		<td width="10%" align="right"><font color="red">*</font>合同号：</td>
		<td width="15%" align="left"><input name="contractNo"
			type="text" size="12" tabindex="2" value="${main.contractNo}" readonly="readonly" />
			 <input type="button" value="..." id="btnselectContractInfo" name="btnselectContractInfo" onclick="selectContractInfo()" disabled="disabled">
			<input name="contractPurchaseId" type="hidden"/>
			<input name="contractId" type="hidden"  value="${main.contractId}"/>
			<a href="#" onclick="viewPurchaseForm()">查看</a></nobr>
		</td>
		<td width="10%" align="right"><nobr>SAP订单号：</nobr></td>
		<td width="15%" align="left"><input name="sapOrderNo"
			type="text" size="15" tabindex="2" value="${main.sapOrderNo}"
			readonly="readonly" /></td>
		<td width="10%" align="right">立项号：</td>
		<td width="15%" align="left"><input name="projectNo"
			type="text" size="15" tabindex="2" value="${main.projectNo}"
			readonly="readonly" /></td>
		<td width="10%" align="right"><nobr>立项名称：</nobr></td>
		<td width="15%" align="left"><input
			name="projectName" type="text" size="15" tabindex="2"
			value="${main.projectName}" readonly="readonly" /></td>
	</tr>
	<tr>
	    <td width="11%" align="right">申请人：</td>
		<td width="22%" align="left">
			<input name="request" type="text" size="15" tabindex="1" value="${main.request}" readonly="readonly"/>
		</td>
		<td width="11%" align="right">信用证号：</td>
		<td width="22%" align="left"><input name="creditNo" type="text"
			size="15" tabindex="1" value="${main.creditNo}" title="信用证号要等最后才输入！" readonly="readonly" /></td>
		<td width="11%" align="right"><nobr>开证日期：</nobr></td>
		<td width="22%" align="left">
		<input name="createDate" type="text" size="15" tabindex="1"
			value="${main.createDate}" readonly="readonly" /></td>
		<td width="11%" align="right">开证行：</td>
		<td width="22%" align="left"><div id="div_createBank"></div></td>

	</tr>
	<tr>
	    
		<td width="11%" align="right"><nobr>国别/地区：</nobr></td>
		<td width="22%" align="left">
			<input name="country" type="text" size="15" tabindex="1" value="${main.country}" readonly="readonly" />
		</td>
		<td width="11%" align="right">付款方式：</td>
		<td width="22%" align="left">
			<input name="paymentType" type="text" size="15" tabindex="1" value="${main.paymentType}" readonly="readonly" />
		</td>
		<td width="11%" align="right">受益人：</td>
		<td width="22%" align="left" colspan="3">
			<input name="benefit" type="text" size="53" tabindex="2" value="${main.benefit}" readonly="readonly" />
		</td>
	</tr>

	<tr>
		<td width="11%" align="right">币别：</td>
		<td width="22%" align="left">
		 	<div id="div_currency" id="div_currency"></div>
		</td>
		<td width="11%" align="right">金额：</td>
		<td width="22%" align="left">
			<input name="amount" type="text" size="15" tabindex="1" value="${main.amount}" readonly="readonly" />
		</td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>汇率：</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>
		 	<input name="rate" type="text" size="15" tabindex="1" value="${main.rate}" readonly="readonly" />
		</td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>核销单号：</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>><input name="writeOffSingleNo"
			type="text" size="15" tabindex="1" value="${main.writeOffSingleNo}" readonly="readonly" />
		</td>
		
	</tr>
	<tr>
		
		<td width="11%" align="right">货物品名：</td>
		<td width="22%" align="left">
			<input name="goods" type="text" size="15" tabindex="1" value="${main.goods}" readonly="readonly" />
		</td>
		<td width="11%" align="right">规格：</td>
		<td width="22%" align="left">
			<input name="specification" type="text" size="15" tabindex="1" value="${main.specification}" readonly="readonly" />
		</td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>唛头：</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>><input name="mark" type="text"
			size="15" tabindex="1" value="${main.mark}" readonly="readonly" /></td> 
        <td width="11%" align="right">期限：</td>
	      <td width="22%" align="left">
			 <input name=availDate type="text" size="15" tabindex="31" value="${main.availDate}" readonly="readonly"/>
	      </td>        
	</tr>
	<tr>
		<td width="11%" align="right">发票：</td>
		<td width="22%" align="left">
			<input name="invoice" type="text" size="15" tabindex="1" value="${main.invoice}" readonly="readonly" />
		</td>
		<td width="11%" align="right"><c:if test="${purTradeType=='1'}">提单：</c:if><c:if test="${purTradeType=='2'}">运输单据或货物收据：</c:if></td>
		<td width="22%" align="left">
			<input name="billOfLading" type="text" size="15" tabindex="1" value="${main.billOfLading}" readonly="readonly" />
		</td>
		<td width="11%" align="right">保险单：</td>
		<td width="22%" align="left">
			<input name="billOfInsurance" type="text" size="15" tabindex="1" value="${main.billOfInsurance}" readonly="readonly" />
		</td>
		<td width="11%" align="right"><nobr>品质(分析证)：</nobr></td>
		<td width="22%" align="left">
			<input name="billOfQuality" type="text" size="15" tabindex="1" value="${main.billOfQuality}" readonly="readonly" />
		</td>
	</tr>
	<tr>
		<td width="11%" align="right"><nobr>受益人证明：</nobr></td>
		<td width="22%" align="left">
			<input name="benefitCertification" type="text" size="15" tabindex="1" value="${main.benefitCertification}" readonly="readonly" />
		</td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>产地证:</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="certificateOfOrigin" type="text" size="15" tabindex="1" value="${main.certificateOfOrigin}" readonly="readonly" />
		</td>
		<td width="11%" align="right">装箱单：</td>
		<td width="22%" align="left">
			<input name="packingSlip" type="text" size="15" tabindex="1" value="${main.packingSlip}" readonly="readonly" />
		</td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>起运电：</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="dispatchElectric" type="text" size="15" tabindex="1" value="${main.dispatchElectric}" readonly="readonly" />
		</td>
	</tr>
	<tr>
	    <td width="11%" align="right" valign="top">其他单据:</td>
		<td width="22%" align="left" colspan="7">
		    <textarea cols="96" rows="5" name="otherDocuments" readonly="readonly" >${main.otherDocuments}</textarea>
		</td>

	</tr>
	<tr>
		<td width="11%" align="right"><c:if test="${purTradeType=='1'}">装运港：</c:if><c:if test="${purTradeType=='2'}">装运地：</c:if></td>
		<td width="22%" align="left">
			<input name="portOfShipment" type="text" size="15" tabindex="1" value="${main.portOfShipment}" readonly="readonly" />
		</td>
		<td width="11%" align="right"><c:if test="${purTradeType=='1'}">目的港：</c:if><c:if test="${purTradeType=='2'}">目的地：</c:if></td>
		<td width="22%" align="left">
			<input name="portOfDestination"	type="text" size="15" tabindex="1" value="${main.portOfDestination}" readonly="readonly" />
		</td>
		<td width="11%" align="right">装期：</td>
		<td width="22%" align="left">
			<input name="loadingPeriod" type="text" size="15" tabindex="1" value="${main.loadingPeriod}" readonly="readonly" />
		</td>
		<td width="11%" align="right">有效期：</td>
        <td width="22%" align="left">
            <input name="validDate" type="text" size="15" tabindex="1" value="${main.validDate}"  readonly="readonly" />
        </td>
		
	</tr>
	<tr>
        <td width="11%" align="right"><c:if test="${purTradeType=='1'}">可否转船：</c:if><c:if test="${purTradeType=='2'}">可否转运：</c:if></td>
		<td width="22%" align="left">
			<select id="transShipment" name="transShipment" disabled="disabled">
				<option value="">请选择</option>
				<option value="Y"
					<c:if test="${main.transShipment=='Y'}"> selected </c:if>>是</option>
				<option value="N"
					<c:if test="${main.transShipment=='N'}"> selected </c:if>>否</option>
			</select>
		</td>
		<td width="11%" align="right">可否分批：</td>
		<td width="22%" align="left">
			<select id="canBatches" name="canBatches" disabled="disabled">
				<option value="">请选择</option>
				<option value="Y"<c:if test="${main.canBatches=='Y'}"> selected </c:if>>是</option>
				<option value="N"<c:if test="${main.canBatches=='N'}"> selected </c:if>>否</option>
			</select>
		</td>
		<td width="11%" align="right">是否预保：</td>
		<td width="22%" align="left">
			<select id="preSecurity" name="preSecurity" disabled="disabled">
				<option value="">请选择</option>
				<option value="Y"
					<c:if test="${main.preSecurity=='Y'}"> selected </c:if>>是</option>
				<option value="N"
					<c:if test="${main.preSecurity=='N'}"> selected </c:if>>否</option>
			</select>
		</td>
		<td width="11%" align="right">到期地点：</td>
		<td width="22%" align="left">
			<input name="place" type="text" size="15" tabindex="1" value="${main.place}" readonly="readonly" />
	   </td>
	   
	</tr>
	<tr>
		<td width="11%" align="right" valign="top">特别条款：</td>
		<td width="88%" align="left" colspan="7">
			<textarea cols="96" rows="4" id="specialConditions" name="specialConditions" readonly="readonly" >${main.specialConditions}</textarea>
		</td>
	</tr>
	<tr>
		<td width="11%" align="right" valign="top">修改事项：</td>
		<td width="88%" align="left" colspan="7">
			<textarea cols="96" rows="4" id="modifyInfo" name="modifyInfo" readonly="readonly" >${main.modifyInfo}</textarea>
		</td>
	</tr>
	<tr>
		<td width="11%" align="right" valign="top">保证金情况：</td>
		<td width="88%" align="left" >
			<input type="text" size="30"  name="creditInfo" value="${main.creditInfo}" readonly="readonly" />
		</td>
	    <td width="11%" align="right"><nobr>保证金日期：</nobr></td>
		<td width="22%" align="left" >
			<input name="bailDate" type="text" size="15" tabindex="1" value="${main.bailDate}" readonly="readonly" />
		</td> 
		<td align="right" width="11%">	
		<font color="red">*</font>物料组:
		</td>
		<td width="22%" >	
			<div id="div_ymatGroup"></div>
		</td> 
		<td align="right" colspan="4">	</td>	 	
	</tr>
</table>

<div id="div_CreditEntryInfo"></div>

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
<div id="div_creditEntryHisInfogrid"></div>
<div id="rule" class="x-hide-display" ></div>
<div id="history" class="x-hide-display" ></div>
</body>
</html>
<fiscxd:fileUpload divId="rule" erasable="${revisable}"  increasable="true"
 resourceId="CREDITENTRY" resourceName="CREDITENTRY" recordId="${creditId}" recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>
<fiscxd:workflow-taskHistory divId="history" businessRecordId="${main.creditId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:dictionary divId="div_currency" fieldName="currency" dictionaryName="bm_currency" 
width="150" selectedValue="${main.currency}" disable="true" >
</fiscxd:dictionary>


<fiscxd:dictionary divId="div_ymatGroup" fieldName="ymatGroup" dictionaryName="YMATGROUP" width="150" selectedValue="${main.ymatGroup}" disable="false" ></fiscxd:dictionary>
<fiscxd:dictionary divId="div_createBank" fieldName="createBank" dictionaryName="BM_BANK_INFO" width="150" selectedValue="${main.createBank}" disable="true"></fiscxd:dictionary>
