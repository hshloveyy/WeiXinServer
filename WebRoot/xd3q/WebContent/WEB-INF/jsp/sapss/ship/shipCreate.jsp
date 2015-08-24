<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>货物出仓单</title>
<style type="text/css">
.add{
	background-image:url(<%=request.getContextPath()%>/images/fam/add.gif) !important;
}
.delete{
	background-image:url(<%=request.getContextPath()%>/images/fam/delete.gif) !important;
}
.update{
	background-image:url(<%=request.getContextPath()%>/images/fam/refresh.gif) !important;
}
.find{
	background-image:url(<%=request.getContextPath()%>/images/fam/find.png) !important;
}
</style>
<script type="text/javascript">
//var from = '<%=request.getAttribute("popfrom")%>';
//贸易类型
var tradeType ='${tradeType}';

//贸易名称
var strshipTitle ='';

var Enabled='false';
if ('${submit}'!='true')Enabled='submit';
if ('${save}'!='true')Enabled='save';
<c:if test="${not empty tShipInfo.oldShipId}">
var Enabled='false';
</c:if>

//取贸易名称
strshipTitle = Utils.getTradeTypeTitle(tradeType);

//document.title = strshipTitle + "货物出仓单" ;

var shipDetailInfogrid;		//出仓单明细列表
var shipDetailInfods;

function selectExportApplyWin(){
	if (Enabled=='false')return;	//Wang Yipu 2009.4.9
	top.ExtModalWindowUtil.show('查询所属登陆员工部门的出仓通知单信息',
	'shipController.spr?action=selectExportApply&tradeType=' + '${tShipInfo.tradeType}' +'&examineState=3&deptid=',
	'',
	selectExportApplyCallBack,
	{width:900,height:450});
}

function selectBillApply(){
	top.ExtModalWindowUtil.show('查询所属登陆员工部门的开票信息信息',
	'shipController.spr?action=selectBillApply&tradeType=' + '${tShipInfo.tradeType}' +'&examineState=3&deptid=',
	'',
	selectBillApplyCallBack,
	{width:900,height:450});
}
function selectBillApplyCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('billApplyNo').value=returnvalue.BILL_APPLY_NO;
	Ext.getDom('billApplyId').value=returnvalue.BILL_APPLY_ID;
}
function selectExportApplyCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('noticeNo').value=returnvalue.NOTICE_NO;
	Ext.getDom('exportApplyId').value=returnvalue.EXPORT_APPLY_ID;
	Ext.getDom('contractSalesId').value=returnvalue.CONTRACT_SALES_ID;
	Ext.getDom('salesNo').value=returnvalue.SALES_NO;
	Ext.getDom('projectNo').value = returnvalue.PROJECT_NO;
	Ext.getDom('projectName').value = returnvalue.PROJECT_NAME;
	Ext.getDom('contractGroupNo').value = returnvalue.CONTRACT_GROUP_NO;	
    Ext.getDom('sapOrderNo').value = returnvalue.SAP_ORDER_NO;	
    Ext.getDom('contractPaperNo').value = returnvalue.VBKD_IHREZ!=null?returnvalue.VBKD_IHREZ:'';	
	Ext.getDom('unitName').value = returnvalue.KUWEV_KUNNR_NAME;	
	var requesturl = 'shipController.spr?action=initShipInfo&exportApplyId=' + returnvalue.EXPORT_APPLY_ID;
	requesturl = requesturl + '&contractSalesId=' + returnvalue.CONTRACT_SALES_ID;
	requesturl = requesturl + '&shipId=' + '${tShipInfo.shipId}';
	requesturl = requesturl + '&intendGatherTime=' + Ext.getDom('intendGatherTime').value;
	requesturl = requesturl + '&makeInvoiceTime=' + Ext.getDom('makeInvoiceTime').value;
	
	//alert(requesturl);
	Ext.Ajax.request({
		url: encodeURI(requesturl),
		success: function(response, options){
  			 shipDetailInfods.load({params:{start:0, limit:10},arg:[]});
		},
		failure:function(response, options){
			top.ExtModalWindowUtil.alert('提示','与服务器交互失败请重新再试！');
		}
	});	
}

function selectSalesWin(){
	if (Enabled=='false')return;	//Wang Yipu 2009.4.9
	top.ExtModalWindowUtil.show('查询合同信息',
	'shipNotifyController.spr?action=selectSalesInfo&tradeType=' + '${tShipInfo.tradeType}' +'&orderState=3,5,7,8,9,12&&deptid=',
	'',
	selectSalesInfoCallBack,
	{width:900,height:450});
}

function selectSalesInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('contractSalesId').value=returnvalue.CONTRACT_SALES_ID;
	Ext.getDom('salesNo').value=returnvalue.CONTRACT_NO;
	Ext.getDom('projectNo').value = returnvalue.PROJECT_NO;
	Ext.getDom('projectName').value = returnvalue.PROJECT_NAME;
	Ext.getDom('contractGroupNo').value = returnvalue.CONTRACT_GROUP_NO;
	Ext.getDom('sapOrderNo').value = returnvalue.SAP_ORDER_NO;		
	Ext.getDom('contractPaperNo').value = returnvalue.VBKD_IHREZ!=null?returnvalue.VBKD_IHREZ:'';	
	Ext.getDom('unitName').value = returnvalue.KUWEV_KUNNR_NAME;	
	if(returnvalue.COLLECTION_DATE){
	   var cd = returnvalue.COLLECTION_DATE;
	   Ext.getDom('intendGatherTime').value = cd.substring(0,4)+'-'+cd.substring(4,6)+'-'+cd.substring(6,8);
	}
	var requesturl = 'shipController.spr?action=initShipInfo&contractSalesId=' + returnvalue.CONTRACT_SALES_ID + '&exportApplyId='+'${tExportApply.exportApplyId}';
	requesturl = requesturl + '&shipId=' + '${tShipInfo.shipId}';
	requesturl = requesturl + '&intendGatherTime=' + Ext.getDom('intendGatherTime').value;
	requesturl = requesturl + '&makeInvoiceTime=' + Ext.getDom('makeInvoiceTime').value;
	
	Ext.Ajax.request({
		url: encodeURI(requesturl),
		success: function(response, options){
  			 shipDetailInfods.load({params:{start:0, limit:10},arg:[]});
		},
		failure:function(response, options){
			top.ExtModalWindowUtil.alert('提示','与服务器交互失败请重新再试！');
		}
	});	
}

function selectPurchaseWin(){
	if (Enabled=='false')return;	//Wang Yipu 2009.4.9
	top.ExtModalWindowUtil.show('查询采购合同信息',
	'shipController.spr?action=selectPurchaseInfo&tradeType=' + '${tShipInfo.tradeType}' +'&orderState=3,5,7,8,9,12&deptid=',
	'',
	selectPurchaseInfoCallBack,
	{width:900,height:450});
}

function selectPurchaseInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('contractSalesId').value=returnvalue.CONTRACT_PURCHASE_ID;
	Ext.getDom('contractPurchaseId').value=returnvalue.CONTRACT_PURCHASE_ID;
	Ext.getDom('salesNo').value=returnvalue.CONTRACT_NO;
	Ext.getDom('projectNo').value = returnvalue.PROJECT_NO;
	Ext.getDom('projectName').value = returnvalue.PROJECT_NAME;
	Ext.getDom('contractGroupNo').value = returnvalue.CONTRACT_GROUP_NO;
	Ext.getDom('sapOrderNo').value = returnvalue.SAP_ORDER_NO;		
	Ext.getDom('contractPaperNo').value = returnvalue.EKKO_UNSEZ!=null?returnvalue.EKKO_UNSEZ:'';	
	Ext.getDom('unitName').value = returnvalue.EKKO_LIFNR_NAME;	
	var requesturl = 'shipController.spr?action=initShipBOMInfo&contractPurchaseId=' + returnvalue.CONTRACT_PURCHASE_ID ;
	requesturl = requesturl + '&shipId=' + '${tShipInfo.shipId}';
	requesturl = requesturl + '&intendGatherTime=' + Ext.getDom('intendGatherTime').value;
	requesturl = requesturl + '&makeInvoiceTime=' + Ext.getDom('makeInvoiceTime').value;
	Ext.Ajax.request({
		url: encodeURI(requesturl),
		success: function(response, options){
  			 shipDetailInfods.load({params:{start:0, limit:10},arg:[]});
		},
		failure:function(response, options){
			top.ExtModalWindowUtil.alert('提示','与服务器交互失败请重新再试！');
		}
	});	
}

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var sReturnMessage=customField.returnMessage;
	top.ExtModalWindowUtil.alert('提示',sReturnMessage);
    if (sReturnMessage.indexOf("过帐成功")>-1){
	    $('sapReturnNo').value=sReturnMessage.substring(sReturnMessage.indexOf("过帐成功")+5,sReturnMessage.length);
	}
//	if (strOperType == '1'){
		shipDetailInfods.reload();
//	}	
	if (strOperType == '3'){
		//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
	    top.ExtModalWindowUtil.close();
	}	
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';

   	var fm = Ext.form;

   	   
    //增加出仓单明细资料(物料)的回调函数
    function funAddshipDetailCallBack(){
	 	var returnValue = top.ExtModalWindowUtil.getReturnValue(); 
	 	var requestUrl = "";
    	requestUrl = 'shipController.spr?action=addDetail&mtRowsId=' + returnValue.SALES_ROWS_ID;	
    	requestUrl = requestUrl + "&shipId=" + "${tShipInfo.shipId}";  
    	requestUrl = requestUrl + "&exportApplyId="  + Ext.getDom('exportApplyId').value ;//+ "${tShipInfo.exportApplyId}";  LJX 20090427 Modify 
    	requestUrl = requestUrl + "&contractSalesId=" + Ext.getDom('contractSalesId').value;   
    	requestUrl = requestUrl + "&hasQuantity=0";// + returnValue.HAS_QUANTITY;//20120607
    	requestUrl = requestUrl + "&tradeType=" + tradeType;
    	requestUrl = requestUrl + '&intendGatherTime=' + Ext.getDom('intendGatherTime').value;
    	requestUrl = requestUrl + '&makeInvoiceTime=' + Ext.getDom('makeInvoiceTime').value;

		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				if(responseArray.error!=null){
					Ext.MessageBox.alert('提示', responseArray.error);
					return;
				}
				var p = new shipDetailInfoPlant({
	                    SHIP_DETAIL_ID: responseArray.shipDetailId,            
						MATERIAL_CODE: responseArray.materialCode,               
				    	MATERIAL_NAME: responseArray.materialName,                
						MATERIAL_UNIT: responseArray.materialUnit,               
						QUANTITY: responseArray.quantity,    
					    BATCH_NO: responseArray.batchNo,                              
						PRICE: responseArray.price,                
						CURRENCY: responseArray.currency,              
						TOTAL: responseArray.total,              
						CMD: responseArray.cmd,               
						IS_AVAILABLE: responseArray.isAvailable,
						CREATOR_TIME: responseArray.creatorTime,             
						CREATOR: responseArray.creator,	                    
						EKPO_PEINH: responseArray.ekpoPeinh,
						SALE_PRICE: responseArray.salePrice,
						SALE_TOTAL: responseArray.saleTotal,
						SALE_CURRENCY: responseArray.saleCurrency,
						VBAP_KPEIN:  responseArray.vbapKpein
	                });
				shipDetailInfogrid.stopEditing();
				shipDetailInfods.insert(0, p);
				shipDetailInfogrid.startEditing(0, 0);
				shipDetailInfogrid.getView().refresh();
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});    
    }
    
    function funAddshipDetail2CallBack(){
	 	var returnValue = top.ExtModalWindowUtil.getReturnValue(); 
	 	var requestUrl = 'shipController.spr?action=addDetail&mtRowsId=' + returnValue.EXPORT_MATE_ID;
    	requestUrl = requestUrl + '&shipId=' + '${tShipInfo.shipId}';  
    	requestUrl = requestUrl + '&exportApplyId=' + Ext.getDom('exportApplyId').value ;//+ '${tShipInfo.exportApplyId}';  LJX 20090427 Modify 
    	requestUrl = requestUrl + '&contractSalesId=' + Ext.getDom('contractSalesId').value; 
    	requestUrl = requestUrl + '&hasQuantity=0'; //+ returnValue.HAS_QUANTITY; //20120607    

    	//alert(requestUrl);
    	//alert(Ext.getDom('exportApplyId').value);
    	
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);

				var p = new shipDetailInfoPlant({
	                    SHIP_DETAIL_ID: responseArray.shipDetailId,            
						MATERIAL_CODE: responseArray.materialCode,               
				    	MATERIAL_NAME: responseArray.materialName,                
						MATERIAL_UNIT: responseArray.materialUnit,               
						QUANTITY: responseArray.quantity, 
						BATCH_NO: responseArray.batchNo,                  
						PRICE: responseArray.price,                
						CURRENCY: responseArray.currency,              
						TOTAL: responseArray.total,              
						CMD: responseArray.cmd,               
						IS_AVAILABLE: responseArray.isAvailable,
						CREATOR_TIME: responseArray.creatorTime,             
						CREATOR: responseArray.creator,	                    
						EKPO_PEINH: responseArray.ekpoPeinh,
						SALE_PRICE: responseArray.salePrice,
						SALE_TOTAL: responseArray.saleTotal,
						SALE_CURRENCY: responseArray.saleCurrency,
						VBAP_KPEIN:  responseArray.vbapKpein
	                });
				shipDetailInfogrid.stopEditing();
				shipDetailInfods.insert(0, p);
				shipDetailInfogrid.startEditing(0, 0);
				shipDetailInfogrid.getSelectionModel(0);
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});    
    }  

    //增加"进料加工业务"出仓单明细资料(物料)的回调函数
    function funAddshipDetail3CallBack(){
	 	var returnValue = top.ExtModalWindowUtil.getReturnValue(); 
	 	var requestUrl = "";
    	requestUrl = 'shipController.spr?action=addPurchaseDetail&bomId=' + returnValue.BOM_ID;	
    	requestUrl = requestUrl + "&purchaseRowsId=" + returnValue.PURCHASE_ROWS_ID;
    	requestUrl = requestUrl + "&shipId=" + "${tShipInfo.shipId}";  
    	requestUrl = requestUrl + "&contractPurchaseId=" + Ext.getDom('contractPurchaseId').value;
    	requesturl = requesturl + '&intendGatherTime=' + Ext.getDom('intendGatherTime').value;
    	requesturl = requesturl + '&makeInvoiceTime=' + Ext.getDom('makeInvoiceTime').value;   

		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);

				var p = new shipDetailInfoPlant({
	                    SHIP_DETAIL_ID: responseArray.shipDetailId,            
						MATERIAL_CODE: responseArray.materialCode,               
				    	MATERIAL_NAME: responseArray.materialName,                
						MATERIAL_UNIT: responseArray.materialUnit,               
						QUANTITY: responseArray.quantity,    
					    BATCH_NO: responseArray.batchNo,                              
						PRICE: responseArray.price,                
						CURRENCY: responseArray.currency,              
						TOTAL: responseArray.total,              
						CMD: responseArray.cmd,               
						IS_AVAILABLE: responseArray.isAvailable,
						CREATOR_TIME: responseArray.creatorTime,             
						CREATOR: responseArray.creator,	                    
						EKPO_PEINH: responseArray.ekpoPeinh,
						SALE_PRICE: responseArray.salePrice,
						SALE_TOTAL: responseArray.saleTotal,
						SALE_CURRENCY: responseArray.saleCurrency,
						VBAP_KPEIN:  responseArray.vbapKpein
	                });
				shipDetailInfogrid.stopEditing();
				shipDetailInfods.insert(0, p);
				shipDetailInfogrid.startEditing(0, 0);
				shipDetailInfogrid.getView().refresh();
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});    
    }
        
    //增加出仓单明细资料(物料)
   	   var addshipDetailInfo = new Ext.Toolbar.Button({
	   		text:'增加',
		    iconCls:'add',
			handler:function(){
				var contractSalesId = Ext.getDom('contractSalesId').value;
				var contractPurchaseId = Ext.getDom('contractPurchaseId').value;
				var exportApplyId = Ext.getDom('exportApplyId').value;
				var isProduct = Ext.getDom('isProduct').value;
				//alert("A:" + exportApplyId);
				if (contractSalesId == '' && exportApplyId == ''&& contractPurchaseId==''){
					top.ExtModalWindowUtil.alert('提示','请先选择合同或通知单才能增加物料信息！');
					return;				
				}
				if (exportApplyId != ''){
					//alert("1");
					top.ExtModalWindowUtil.show(strshipTitle+'货物出仓单登记表',
					'shipController.spr?action=toFindExportApplyMaterial&exportApplyId='+exportApplyId + "&tradeType=" + tradeType,
					'',
					funAddshipDetail2CallBack,
					{width:800,height:500});			
					return;
				}
				if(isProduct=='0' && (tradeType=='8'||tradeType=='12')) {
					//alert("2");
					top.ExtModalWindowUtil.show(strshipTitle+'货物出仓单登记表',
					'shipController.spr?action=toFindPurchaseMaterial&contractPurchaseId='+contractPurchaseId + "&tradeType=" + tradeType,
					'',
					funAddshipDetail3CallBack,
					{width:800,height:500});
					return;
				}
				top.ExtModalWindowUtil.show(strshipTitle+'货物出仓单登记表',
					'shipNotifyController.spr?action=toFindSalesMaterial&shipType=1&contractSalesId='+contractSalesId + "&tradeType=" + tradeType,
					'',
					funAddshipDetailCallBack,
					{width:800,height:500});
			}
	   	});
   	
   	//删除
   	var deleteshipDetailInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (shipDetailInfogrid.selModel.hasSelection()){
				var records = shipDetailInfogrid.selModel.getSelections();
				var shipDetailIds='' ;
				for(i=0;i<records.length ;i++){
				   shipDetailIds+=records[i].data.SHIP_DETAIL_ID+',';
				}
				top.Ext.Msg.show({
					title:'提示',
  						msg: '是否确定删除记录',
  						buttons: {yes:'是', no:'否'},
  						fn: function(buttonId,text){
  							if(buttonId=='yes'){
  								var param = "?action=deleteShipMateriel";
							param = param + "&shipDetailIds=" + shipDetailIds;

							new AjaxEngine('shipController.spr', 
					   			{method:"post", parameters: param, onComplete: callBackHandle});
					   			
					   		strOperType = '1';
  							}
  						},
  						icon: Ext.MessageBox.QUESTION
				});
				//}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的出仓单明细信息！');
			}
		}
   	});
   	
   	
   	//工具栏---添加、删除按钮。
   	if (Enabled!='false')	//Wang Yipu 2009.4.9
    var shipDetailInfotbar = new Ext.Toolbar({
		items:[addshipDetailInfo,'-',deleteshipDetailInfo]
	  });

   	var checkColumn = new Ext.grid.CheckColumn({
        header: "是否已清",
        dataIndex: 'IS_CLEAR_FINISH',
        width: 55
     });
	  
	var shipDetailInfoPlant = Ext.data.Record.create([
		{name:'SHIP_DETAIL_ID'},                 //行ID
		{name:'SHIP_ID'},                        //主表ID
		{name:'SAP_ROW_NO'},                        //主表ID
		{name:'MATERIAL_CODE'},                 //物料编码
	    {name:'MATERIAL_NAME'},                 //物料名称
		{name:'MATERIAL_UNIT'},                 //物料单位
		{name:'INVENTORY_NUM'},                 //库存数量		
		{name:'SEQUANTITY'},	
		{name:'QUANTITY'},                      //数量
		{name:'BATCH_NO'},                      //批次号
		{name:'PRICE'},                         //单价
		{name:'CURRENCY'},                     //币别
		{name:'TOTAL'},                        //总计
		{name:'CMD'},                            //备注
		{name:'EKPO_MATNR'},                       //上级物料编码
		{name:'EKPO_TXZ01'},                        //上级物料名称
		{name:'EKPO_PEINH'},                       //单价定价单位
		{name:'INTEND_GATHER_TIME'},
		{name:'MAKE_INVOICE_TIME'},
		{name:'CURRENT_CLEAR_CHARGE'},		//本次清款金额
		{name:'IS_CLEAR_FINISH'},			//是否已清
		{name:'SALE_PRICE'},				//单价(销售)VBAP_KPEIN
		{name:'VBAP_KPEIN'},
		{name:'SALE_CURRENCY'},
		{name:'SALE_TOTAL'}					//总计(销售)
	]);

	shipDetailInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'shipController.spr?action=queryMaterial&shipId='+'${tShipInfo.shipId}'
        			+'&contractPurchaseId='+'${tShipInfo.contractPurchaseId}'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},shipDetailInfoPlant)
    });
    
    var shipDetailInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var shipDetailInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		shipDetailInfosm,
		   {header: '物料行ID',
           width: 100,
           sortable: true,
           dataIndex: 'SHIP_DETAIL_ID',
            hidden:true
           },
		   {header: '主表ID',
           width: 100,
           sortable: true,
           dataIndex: 'SHIP_ID',
            hidden:true
           },           
           {header: '物料行ID',
           width: 50,
           sortable: true,
           dataIndex: 'SAP_ROW_NO'
           },           
           {header: '物料编码',
           width: 100,
           sortable: true,
           dataIndex: 'MATERIAL_CODE'
           },
		　 {header: '物料名称',
           width: 200,
           sortable: true,
           dataIndex: 'MATERIAL_NAME'
           },
		　 {header: '物料单位',
           width: 50,
           sortable: false,
           dataIndex: 'MATERIAL_UNIT'
           },
//		　 {
//			header: '库存数量',
//           	dataIndex: 'INVENTORY_NUM',
//        	hidden:true
//           }, 
  		　 
           {
           header: '批次号<font color="red">▲</font>',
           width: 100,
           sortable: true,
           dataIndex: 'BATCH_NO',
           editor: new fm.TextField({
               allowBlank: false,
               maxLength:10
           })           
           },             
           {header: '申请数量<font color="red">▲</font>',
           width: 80,
           sortable: true,
           dataIndex: 'QUANTITY',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:4,
               maxValue: 10000000000,
               allowNegative:true,
               disabled:${tShipInfo.examineState=='5'}
           })            
           },
           {header: '二次结算调整数量',
           width: 120,
           sortable: false,
           dataIndex: 'SEQUANTITY',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:3,
               maxValue: 10000000000,
               allowNegative:true
           }),
           hidden:${tShipInfo.examineState=='5'?"false":"true"}
           },
           {
           header: '条件定价单位<font color="red">▲</font>',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_PEINH',
           editor: new fm.NumberField({
                   allowBlank: false,
                   allowNegative: false,
                   maxValue: 10000
               })           
           },
           {
           header: '单价(采购)<font color="red">▲</font>',
           width: 100,
           sortable: true,
           dataIndex: 'PRICE',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 10000000000
           })            
           },
           {header: '币别(采购)<font color="red">▲</font>',
           width: 120,
           sortable: true,
           dataIndex: 'CURRENCY',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               allowBlank: false,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'currency',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })           
           },
           {
           header: '总计(采购)',
           width: 100,
           sortable: true,
           dataIndex: 'TOTAL'
           },
           {
           header: '备注',
           width: 100,
           sortable: true,
           dataIndex: 'CMD',
           editor: new fm.TextField({
               allowBlank: true
           })            
           },
           {
           header: '上级物料编码',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_MATNR'
           },
           {
           header: '上级物料名称',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_TXZ01'         
           },
           {
               header: '单价(含税销售)<font color="red">▲</font>',
               width: 100,
               sortable: true,
               dataIndex: 'SALE_PRICE',
               editor: new fm.NumberField({
                   allowBlank: false,
                   allowNegative:true,
                   maxValue: 10000000000
               })
           },
           {
           header: '定价单位(含税销售)<font color="red">▲</font>',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_KPEIN',
           editor: new fm.NumberField({
                   allowBlank: false,
                   allowNegative: false,
                   maxValue: 10000
               })           
           }
           ,
           {
               header: '总计(含税销售)<font color="red">▲</font>',
               width: 100,
               sortable: true,
               dataIndex: 'SALE_TOTAL'
           },
           {header: '币别(含税销售)<font color="red">▲</font>',
           width: 120,
           sortable: true,
           dataIndex: 'SALE_CURRENCY',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               allowBlank: false,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'salecurrency',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })           
           }
           ,{
        	   header: '预计收款日期',
               width: 100,
               sortable: true,
               dataIndex: 'INTEND_GATHER_TIME',
               editor:new Ext.form.DateField({
              		format:'Y-m-d'
               })
           },{
        	   header: '预计开票日',
               width: 100,
               sortable: true,
               dataIndex: 'MAKE_INVOICE_TIME',
               editor:new Ext.form.DateField({
             		format:'Y-m-d'
              })
           }/*,清帐上线前先关掉
           {
               header: '本次清款金额',
               width: 100,
               sortable: true,
               dataIndex: 'CURRENT_CLEAR_CHARGE'         
           },checkColumn*/
    ]);
    
    shipDetailInfocm.defaultSortable = true;
    
    //if (tradeType=='5'||tradeType=='6')			//代理进出口业务
    	//shipDetailInfocm.setHidden(7,false);
    //else
    	//shipDetailInfocm.setHidden(7,true);
    
    var shipDetailInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:shipDetailInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
	 
   shipDetailInfods.load({params:{start:0, limit:10},arg:[]});
   
 /*  	if (Enabled=='false') //Wang Yipu 2009.4.9
	   	//出仓单明细列表
		shipDetailInfogrid = new Ext.grid.GridPanel({
	    	id:'shipDetailInfoGrid',
	        ds: shipDetailInfods,
	        cm: shipDetailInfocm,
	        sm: shipDetailInfosm,
	        bbar: shipDetailInfobbar,
	        tbar: shipDetailInfotbar,
	        border:false,
	        loadMask:true,
	        autoScroll:true,
	        region:'center',
	        el: 'div_center',
	        autowidth:true,
	        clicksToEdit:1,
	        layout:'fit'
	    });
    else*/
    	//出仓单明细列表
		shipDetailInfogrid = new Ext.grid.EditorGridPanel({
	    	id:'shipDetailInfoGrid',
	        ds: shipDetailInfods,
	        cm: shipDetailInfocm,
	        sm: shipDetailInfosm,
	        bbar: shipDetailInfobbar,
	        tbar: shipDetailInfotbar,
	        border:false,
	        loadMask:true,
	        autoScroll:true,
	        region:'center',
	        el: 'div_center',
	        autowidth:true,
	        plugins:checkColumn,
	        clicksToEdit:1,
	        layout:'fit'
	    });
 
    shipDetailInfogrid.render();

    /*清帐上线前先关掉
    shipDetailInfogrid.addListener('rowdblclick',onRowDoubleClick);

    function onRowDoubleClick(grid, rowIndex, e){
    	//alert(grid.getStore().getAt(rowIndex).data.CURRENT_CLEAR_CHARGE);
    	
    	var strShipDetailId=grid.getStore().getAt(rowIndex).data.SHIP_DETAIL_ID;

    	top.ExtModalWindowUtil.show('未清收款',
    			'shipController.spr?action=shipClearChargeView&shipDetailId='+strShipDetailId,
    			'',
    			'',
    			{width:900,height:250});
    }

    function ClearcallbackFunction(){
    }*/
    
    shipDetailInfogrid.on("afteredit", afterEdit, shipDetailInfogrid);

    function afterEdit(obj){
        if ('${saveMaterial}'==''&&Enabled=='false') return ;
    	var row = obj.record;//获取被修改的行
        var colName = obj.field;//获取被修改的列
        //财务部只允许改采购单价和定价单位
        if ('${saveMaterial}'=='true'){
            if(colName!='PRICE'&&colName!='EKPO_PEINH') return ;
        }
        var shipDetailId = row.get("SHIP_DETAIL_ID");
        var colValue = row.get(colName);
        var isUpdate = true;
		//批号,更新采购价，币别等
		if (colName == 'QUANTITY'||colName == 'SEQUANTITY'){
		    if(row.get("BATCH_NO")==''){
		       Ext.MessageBox.alert('提示', '请先填写批次号');
			   row.set("QUANTITY",'');
		       return;
		    }
		    var hasShipQuqlity = 0;
		    var requestUrl ="shipController.spr?action=getShipQuality&" + Form.serialize('mainForm');
	        var materialCode = row.get("MATERIAL_CODE");
	        var batchNo = row.get("BATCH_NO");
	        var shipDetailId = row.get("SHIP_DETAIL_ID");
			requestUrl += "&materialCode=" +　materialCode;
			requestUrl += "&batchNo=" +　batchNo;
			requestUrl += "&shipDetailId=" +　shipDetailId;
		    Ext.Ajax.request({
				url: requestUrl,
				sync:true,
				success: function(response, options){
					var responseUtil1 = new AjaxResponseUtils(response.responseText);
					var customField = responseUtil1.getCustomField("coustom");   
	  				hasShipQuqlity=customField.hasShipQuqlity;
	  				if(colValue>hasShipQuqlity){
	  				   isUpdate = false;
	  				   Ext.MessageBox.alert('提示', '库存数量不足，最大可用库存数量为'+hasShipQuqlity+'');
	  				}
				},
				failure:function(response, options){}
			});	
		    if(!isUpdate){
		       updateMaterielValue(row,shipDetailId,colName,hasShipQuqlity);
		       row.set(colName,hasShipQuqlity);
		    }
		    else updateMaterielValue(row,shipDetailId,colName,colValue);
		}
		
        else if (colName == 'BATCH_NO'){
        	if($('warehouse').value==''){
			   Ext.MessageBox.alert('提示', '请先选择仓库');
			   row.set("BATCH_NO",'');
		       return;
			}
			var requestUrl ="shipController.spr?action=queryInventoryNum&" + Form.serialize('mainForm');
	        var materialCode = row.get("MATERIAL_CODE");
	        var batchNo = row.get("BATCH_NO");
	        var shipDetailId = row.get("SHIP_DETAIL_ID");
			requestUrl += "&materialCode=" +　materialCode;
			requestUrl += "&batchNo=" +　batchNo;
			requestUrl += "&shipDetailId=" +　shipDetailId;
	        requestUrl += "&examState=" +　${tShipInfo.examineState};
			Ext.Ajax.request({
				url: requestUrl,
				sync:true,
				success: function(response, options){
					var responseUtil1 = new AjaxResponseUtils(response.responseText);
					var customField = responseUtil1.getCustomField("coustom");   
	  				Ext.getDom('declarationsNo').value=customField.declarationsNo;
	  				//Ext.getDom('hasShipQuqlity').value=customField.hasShipQuqlity;
	  				shipDetailInfods.reload();
	  				//if(customField.tiaojiandingjiadanwei=='1'&&customField.price=='0'&&customField.currency==' '){
	  				if(customField.hasShipQuqlity<=0){
	  				   isUpdate = false;
	  				   Ext.MessageBox.alert('提示', '该仓库、批次号项下物料尚未进仓或无库存可用，请检查！');
	  				}
				},
				failure:function(response, options){}
			});	
			if(!isUpdate) updateMaterielValue(row,shipDetailId,'BATCH_NO','');
			else updateMaterielValue(row,shipDetailId,colName,colValue);
        }
        else updateMaterielValue(row,shipDetailId,colName,colValue);

    }     
    
     //刷新库存数量
     function　queryInventoryNum(row){

		
     }
     
    //更新物料信息
    function updateMaterielValue(row,shipDetailId,colName,colValue){
    	var requestUrl = 'shipController.spr?action=updateShipMateriel';
		requestUrl = requestUrl + '&shipDetailId=' + shipDetailId;
		requestUrl = requestUrl + '&colName=' + colName;
		requestUrl = requestUrl + '&colValue=' + colValue;
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
		        var responseUtil1 = new AjaxResponseUtils(response.responseText);
				var customField = responseUtil1.getCustomField("coustom");   
			    var stateCode = customField.stateCode;
			    if (stateCode == '0'){
			        row.set("TOTAL",customField.total);
					row.set("SALE_TOTAL",customField.sale_total);						
					shipDetailInfods.commitChanges();		
			    }
			    /**
				var responseUtil1 = new AjaxResponseUtils(response.responseText);
				var customField = responseUtil1.getCustomField("coustom");   
				var stateCode = customField.stateCode;
				var hasQuantity = customField.hasQuantity;
				if (stateCode == '0'){
					if (colName == "QUANTITY"){
						top.ExtModalWindowUtil.alert('提示','输入的申请数量超过合同物料数量！' + hasQuantity);					
						//row.set("QUANTITY",row.get("OLD_QUANTITY"));
					}				
				}
				else {
					row.set("TOTAL",customField.total);
					row.set("SALE_TOTAL",customField.sale_total);						
					shipDetailInfods.commitChanges();				
				}     
				**/
			},
			failure:function(response, options){
			    /**					
				if (colName == "QUANTITY"){
					top.ExtModalWindowUtil.alert('提示','输入的申请数量超过合同物料数量！');					
					row.set("QUANTITY",row.get("OLD_QUANTITY"));
				}
				**/
			}
		});    
    }    
        //设置金额
     function setMaterielTotal(row){   
        var shipDetailId = row.get("SHIP_DETAIL_ID");
     	var price = row.get("PRICE");
     	var quantity = row.get("QUANTITY");  
     	var ekpo_peinh  =row.get("EKPO_PEINH");	
     	if (ekpo_peinh == null || ekpo_peinh == ''){
     		ekpo_peinh = "1";
     		row.set("EKPO_PEINH","1");	
     	}
     	if (quantity == null || quantity == ''){
     		quantity = "0";
     	}
     	if (price == null || price == ''){
     		price = "0";
     	}    
     	var total = parseFloat(quantity) *  parseFloat(price) / ekpo_peinh;
     	
     	row.set("TOTAL",formatnumber(total,2));	
     	updateMaterielValue(row,shipDetailId,"TOTAL",formatnumber(total,2));
     }
	 function formatnumber(value,num) {
		var a,b,c,i
		a = value.toString();
		b = a.indexOf('.');
		c = a.length;
		if (num==0){
		if (b!=-1)
			a = a.substring(0,b);
		}
		else{
			if (b==-1){
				a = a + ".";
				for (i=1;i<=num;i++)
				a = a + "0";
			}
				else{
				a = a.substring(0,b+num+1);
				for (i=c;i<=b+num;i++)
				a = a + "0";
			}
		}
		return a
	}  
	
	var toolbars = new Ext.Toolbar({
			items:[	' ','->',		
					{id:'_viewCredit',text:'查看信用额度',handler:_viewCredit,iconCls:'icon-view'}								
					
					],
			renderTo:Ext.getBody()
	});
	/**
	 * 查看信用额度
	 * @return
	 */
	function  _viewCredit(){
		
		var shipId = Ext.getDom("shipId").value;
		var projectId = Ext.getDom("projectNo").value;
		
		if (projectId == ''){
			_getMainFrame().showInfo('立项信息不可为空!');
			
			return ;
		}
		
		if(shipId==''){
	        _getMainFrame().showInfo("出仓单号不可为空！");
	        return;
		}
	
		
		var value = 0;		
		var requestUrl = 'shipController.spr?action=checkCredit&shipId='+ shipId + 
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
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});	
		
	}
	var produceTtitle = '';
	if('${tShipInfo.isProduct }'=='0') produceTtitle='料件调拨';
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[new Ext.Panel({  
		  title:'查看信用额度',
		  //autoHeight:true,
		  height:25,
		 
		  tbar:toolbars
		  }),{
			region:"north",
			height:215,
			margins: '25 0 0 0',
			xtype:'tabpanel',
			id:'infotype',
			name:'infotype',
			plain:true,
            autoScroll:'true',
            activeTab: 0,			
            items:[{
            	title:produceTtitle+'出仓单信息',
            	contentEl: 'div_main',
            	id:'maininfo',
				name:'maininfo',
				autoScroll:'true',
            	layout:'fit'
            },{
            	title:'审批历史记录',
            	contentEl: 'div_history',
            	id:'historyinfo',
				name:'historyinfo',
				autoScroll:'true',
            	layout:'fit'            
            }]
		},{
			region:"center",
			layout:'fit',
			buttonAlign:'center',
			autoScroll:'true',
			items:[{
				region:'center',
				xtype:'tabpanel',
				id:'infotype',
				name:'infotype',
				plain:true,
	            height:300,
	            autoScroll:'true',
	            activeTab: 0,
	            items:[{
			            	title:'物料信息',
			            	contentEl: '',
			            	id:'shipDetailInfo',
							name:'shipDetailInfo',
							autoScroll:'true',
			            	layout:'fit',
			            	items:[shipDetailInfogrid]
	                  },{contentEl:'rule',
			               id:'fileEl', 
			               title: '附件信息',
			               autoScroll:'true',
			               listeners:{activate:handlerActivate}
			            }]
	             }],
	             buttons:[{
		            	text:'保存',
		            	hidden:${save},
		            	handler:function(){
		            	    shipDetailInfogrid.getSelectionModel().selectAll();
							var recoder = shipDetailInfogrid.selModel.getSelections();
							var batch_no;
							for (var i=0; i< recoder.length; i++) {
								batch_no = recoder[i].data.BATCH_NO;
								if(alertNull(batch_no,'批次号'))return;
								if(alertNull(recoder[i].data.QUANTITY,'申请数量'))return;;
								if(alertNull(recoder[i].data.EKPO_PEINH,'条件定价单位'))return;
								if(alertNull(recoder[i].data.PRICE,'单价'))return;
								
								if(recoder[i].data.CURRENCY==null||recoder[i].data.CURRENCY.length<2){
									 top.ExtModalWindowUtil.alert('提示','请填写币别！');
								     return ;
								}
								
							};
		            		var param1 = Form.serialize('mainForm');
		            		if (param1.indexOf("&warehouse=&")>-1)
		        			{
		        				Ext.MessageBox.alert('提示', '仓库必填！');
		            			return;
		            		}
		            		if (param1.indexOf("&contractPaperNo=&")>-1)
		        			{
		        				Ext.MessageBox.alert('提示', '纸质合同号必填！');
		            			return;
		            		}
		            		if (param1.indexOf("&unitName=&")>-1)
		        			{
		        				Ext.MessageBox.alert('提示', '购（领）货单位必填！');
		            			return;
		            		}
							var param = param1 + "&action=updateShipInfo";
							new AjaxEngine('shipController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
						}
		
		            },{
		        	text:'提交',
		        	hidden:${submit},
		        	handler:function(){
		        		var param1 = Form.serialize('mainForm');
		        		if (param1.indexOf("&isHasInv=1&")>-1&&param1.indexOf("&billApplyId=&")>-1)
		       			{
		       				Ext.MessageBox.alert('提示', '请选择开票申请单号');
		        			return;
		        		}
		        		if (param1.indexOf("&warehouse=&")>-1)
		       			{
		       				Ext.MessageBox.alert('提示', '仓库必填！');
		        			return;
		        		}
		        		if (param1.indexOf("&contractPaperNo=&")>-1)
		       			{
		       				Ext.MessageBox.alert('提示', '纸质合同号必填！');
		        			return;
		        		}
		        		if (param1.indexOf("&unitName=&")>-1)
		       			{
		       				Ext.MessageBox.alert('提示', '购（领）货单位必填！');
		        			return;
		        		}
		
		       			if (shipDetailInfods.getCount() > 0){
		        		
						var param = param1  + "&action=submitAndSaveShipInfo";
						new AjaxEngine('shipController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
							   
						strOperType = '3';
						
						} else{
							top.Ext.Msg.show({
								title:'提示',
			  					msg: '没有增加物料信息,请确认是否真的要提交审批?',
			  					buttons: {yes:'是', no:'否'},
			  					fn: function(buttonId,text){
			  						if(buttonId=='yes'){
										var param = param1  + "&action=submitAndSaveShipInfo";
										new AjaxEngine('shipController.spr', 
								   			{method:"post", parameters: param, onComplete: callBackHandle});
								   				
								   		strOperType = '3';
			  						}
		  					},
		  					icon: Ext.MessageBox.QUESTION
						});
						}          	    	
		        	    
		        	}
		        },{
		            text:'保存SAP交货单号',
		            hidden:${!specialDepart},
		            handler:function(){
		                var param='?action=saveSpecialDepartAction&shipId=${tShipInfo.shipId}&sapReturnNo='+$('sapReturnNo').value+'&cmd='+$('cmd').value;
		            	new AjaxEngine('shipController.spr',{method:"post", parameters: param, onComplete: callBackHandle});
		            }
		         },
		         {
		            	text:'过帐',
		            	hidden:${!postDateEdit},            	
		        		handler:function(){
		        		   top.Ext.Msg.show({
										title:'提示',
										msg: '是否确定过帐该出仓单',
										buttons: {yes:'是', no:'否'},
										fn: function(buttonId,text){
											callbackFlag = 'del';
											if(buttonId=='yes'){
			 									var parm ='?action=dealPost&shipId=${tShipInfo.shipId}'; 
												new AjaxEngine('shipController.spr',
														 {method:"post", parameters: parm,onComplete: callBackHandle});
										}
										},
									    animEl: 'elId',
										icon: Ext.MessageBox.QUESTION
								});
		        		}
		            }
		         ,{
		        	text:'关闭',
		        	hidden:${close},
		        	handler:function(){
		        		//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		        	    top.ExtModalWindowUtil.markUpdated();
		                top.ExtModalWindowUtil.close();
		            }
		        },{
					text:'保存日期',
					hidden:${!saveDate},
					handler:function(){
						var param='?action=saveDate&shipId=${tShipInfo.shipId}&intendGatherTime='+$('intendGatherTime').value+'&makeInvoiceTime='+$('makeInvoiceTime').value;
						new AjaxEngine('shipController.spr', 
								   {method:"post", parameters: param, onComplete: callBackHandle});

						}
		            },{
					text:'预确认',
					hidden:${fn:indexOf(taskName,"财务会计审核*")>=0?"false":fn:indexOf(taskName,"财务审核*")>=0?"false":"true"},
					handler:function(){
						var param='?action=preSeConfig&shipId=${tShipInfo.shipId}';
						new AjaxEngine('shipController.spr', 
								   {method:"post", parameters: param, onComplete: callBackHandle});

						}
		            }]
		}]
	});//end fo viewPort
	
	

    var applyTime = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 130,
		applyTo:'creatorTime'
   	});

    var intendGatherTime = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 130,
		applyTo:'intendGatherTime'
   	});

    var makeInvoiceTime = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 130,
		applyTo:'makeInvoiceTime'
   	});
   	/*
    var wareHouse_combo = new Ext.form.ComboBox({
        typeAhead: true,
        triggerAction: 'all',
        transform:'warehouse',
        width:157,
        allowBlank:false,
        blankText:'请选择仓库',
        forceSelection:true
     });
    wareHouse_combo.setValue('${tShipInfo.warehouse}'); 
    wareHouse_combo.on({'change':
        					{
								fn:function(){
												alert($('warehouse').selectedIndex);
												var str = document.mainForm.warehouse.options[document.mainForm.warehouse.selectedIndex].value;
												alert(str);
												var pos = str.split('-');
												$('warehouseAdd').value=pos[1];
											},
								scope: this
        					}
					  });
	  */
	  $('warehouse').value='${tShipInfo.warehouse}';

	//---------------------------------------
		new Ext.ToolTip({
	        target:'sapReturnNo',
	        width:200,
	        html: $('sapReturnNo').value==''?'':$('sapReturnNo').value,
	        trackMouse:true
	    });
		new Ext.ToolTip({
	        target:'sapOrderNo',
	        width:200,
	        html: $('sapOrderNo').value==''?'':$('sapOrderNo').value,
	        trackMouse:true
	    });

	  
});//end of Ext.onReady   

Ext.grid.CheckColumn = function(config){
    Ext.apply(this, config);
    if(!this.id){
        this.id = Ext.id();
    }
    this.renderer = this.renderer.createDelegate(this);
};

Ext.grid.CheckColumn.prototype ={
    init : function(grid){
        this.grid = grid;
        this.grid.on('render', function(){
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
        }, this);
    },

    onMouseDown : function(e, t){
        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
            e.stopEvent();
            var index = this.grid.getView().findRowIndex(t);
            var record = this.grid.store.getAt(index);
            record.set(this.dataIndex, !record.data[this.dataIndex]);

			var read_text = '';
            if (!record.data[this.dataIndex])
            	read_text = 'N';
            else
            	read_text = 'Y';

            var shipDetailId = record.data["SHIP_DETAIL_ID"];

            var requestUrl = 'shipController.spr?action=updateShipMateriel';
    		requestUrl = requestUrl + '&shipDetailId=' + shipDetailId;
    		requestUrl = requestUrl + '&colName=IS_CLEAR_FINISH';
    		requestUrl = requestUrl + '&colValue=' + read_text;
    		Ext.Ajax.request({
    			url: encodeURI(requestUrl),
    			success: function(response, options){					
    				shipDetailInfods.commitChanges();				     
    			},
    			failure:function(response, options){					
    			}
    		});  
       
            //updateMaterielValue(record,shipDetailId,"IS_CLEAR_FINISH",read_text);
        }
    },

    renderer : function(v, p, record){
        if (v == 'Y')
            v = true;
        if (v == 'N')
            v = false;
        if (v == '')
        	v = false;
        p.css += ' x-grid3-check-col-td'; 
        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
    }
};



function alertNull(v,txt){
	if(v==null||v==''){
	   top.ExtModalWindowUtil.alert('提示','请填写'+txt+'！');
       return true;
	}
	return false;
}

function warehouse_chang(){
	var str =document.mainForm.warehouse.options[document.mainForm.warehouse.selectedIndex].text;
	var v = new Array();
	v = str.split('-');
	$('warehouseAdd').value=v[1];
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

//关闭窗体
function closeForm(){
	//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
	return;
	
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
function handlerActivate(tab){
	rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
}
</script>
</head>
<body>
<div id='div_progressBar'></div>
<!-- <div id="div_basrForm">  -->

<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
	 <table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
      <tr>
      	<input type="hidden" id = "shipId" name = "shipId" value="${tShipInfo.shipId }"/>
      	<input type="hidden" id = "contractSalesId" name = "contractSalesId" value="${tShipInfo.contractSalesId }"/>
      	<input type="hidden" id = "contractPurchaseId" name = "contractPurchaseId" value="${tShipInfo.contractPurchaseId }"/>
      	<input type="hidden" id = "tradeType" name = "tradeType" value="${tShipInfo.tradeType }"/>
      	<input type="hidden" id = "billState" name = "billState" value="${tShipInfo.billState }"/>    
      	<input type="hidden" id = "applyTime" name = "applyTime" value="${tShipInfo.applyTime }"/>
      	<input type="hidden" id = "approvedTime" name = "approvedTime" value="${tShipInfo.approvedTime }"/>
      	<input type="hidden" id = "isAvailable" name = "isAvailable" value="${tShipInfo.isAvailable }"/>
      	<input type="hidden" id = "creator" name = "creator" value="${tShipInfo.creator }"/>  
      	<input type="hidden" id = "exportApplyId" name = "exportApplyId" value="${tShipInfo.exportApplyId }"/> 
      	<input type="hidden" id = "isProduct" name = "isProduct" value="${tShipInfo.isProduct }"/>   
      	<input type="hidden" id = "isProduct" name = "isHome" value="${tShipInfo.isHome }"/>  
        <input type="hidden" id="shipOperator" name="shipOperator"  value="${tShipInfo.shipOperator}"  />   
        <input type="hidden" id="shipNote" name="shipNote"  value="${tShipInfo.shipNote}"  /> 
        <input name="seConfigTime" type="hidden" size="40" value="${tShipInfo.seConfigTime}"/>
        
         <c:choose>
            <c:when test="${sysDept.deptcode == '2301'||sysDept.deptcode == '2601'||(tShipInfo.tradeType == '8' && tShipInfo.isProduct == '1')}">
		        <td width="11%" align="right">销售合同号：</td>
		        <td width="22%" align="left">
		        	<input type="text" id="salesNo" name="salesNo" value="${tShipInfo.salesNo}" size="12" readonly="readonly" />
		        	 <input type="button" value="..." onclick="selectSalesWin()"></input>
		        	 <a href="#" onclick="viewSalesForm()">查看</a>
		        </td>
      		</c:when>
         	<c:when test="${tShipInfo.tradeType=='2' || tShipInfo.tradeType == '4' || tShipInfo.tradeType == '5' || (tShipInfo.tradeType == '12' && tShipInfo.isProduct == '1')}">
		        <td width="11%" align="right">通知单号：</td>
		        <td width="22%" align="left">
		        	<input type="text" id="noticeNo" name="noticeNo" value="${tExportApply.noticeNo}" size="12" readonly="readonly" />
		        	 <input type="button" value="..." onclick="selectExportApplyWin()"></input>
		        	 <input type="hidden" id="salesNo" name="salesNo" value="${tShipInfo.salesNo}" readonly="readonly" />
		        	 <a href="#" onclick="viewExportApplyForm()">查看</a>
		        </td>
      		</c:when>
         	<c:when test="${(tShipInfo.tradeType == '8'||tShipInfo.tradeType == '12') && tShipInfo.isProduct == '0'}">
		        <td width="11%" align="right">采购合同号：</td>
		        <td width="22%" align="left">
		        	<input type="text" id="salesNo" name="salesNo" value="${tShipInfo.salesNo}" size="12" readonly="readonly" />
		        	 <input type="button" value="..." onclick="selectPurchaseWin()"></input>
		        	 <a href="#" onclick="viewPurchaseForm()">查看</a>
		        </td>     			
      		</c:when>
       		<c:otherwise>
		        <td width="11%" align="right">销售合同号：</td>
		        <td width="22%" align="left">
		        	<input type="text" id="salesNo" name="salesNo" value="${tShipInfo.salesNo}" size="12" readonly="readonly" />
		        	 <input type="button" value="..." onclick="selectSalesWin()"></input>
		        	 <a href="#" onclick="viewSalesForm()">查看</a>
		        </td>     			
       		</c:otherwise>
         </c:choose>	      	

        <td width="11%" align="right">序号：</td>
        <td width="22%" align="left">
            <input id="serialNo" name="serialNo"  value="${tShipInfo.serialNo }"<c:if test="${not empty tShipInfo.oldShipId}">style='background-color: red;'</c:if>/>
            <input type="hidden" id="shipNo" name="shipNo"  value="${tShipInfo.shipNo }"/>
        </td>
        <td width="11%" align="right">单据状态：</td>
        <td width="22%" align="left">
        	<div id="div_examineState"></div>
        </td>
      </tr>
      <script>
   	    function viewExportApplyForm(){
	        var exportApplyId = Ext.getDom("exportApplyId").value
	        if(exportApplyId=='') {
	           showMsg('请选择通知单信息！');
	           return ;
	        }
			top.ExtModalWindowUtil.show('出口货物通知单信息','shipNotifyController.spr?action=addShipNotifyView&exportApplyId='+ exportApplyId + '&operType=00','','',{width:900,height:500});
       }
       function viewPurchaseForm(){
	        var contractPurchaseId = Ext.getDom("contractPurchaseId").value
	        if(contractPurchaseId=='') {
	           showMsg('请选择采购合同信息！');
	           return ;
	        }
	        top.ExtModalWindowUtil.show('采购合同信息','contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+contractPurchaseId,'','',{width:900,height:550});
       }
       function viewSalesForm(){
	        var contractSalesId = Ext.getDom("contractSalesId").value
	        if(contractSalesId=='') {
	           showMsg('请选择销售合同信息！');
	           return ;
	        }
	        top.ExtModalWindowUtil.show('销售合同信息','contractController.spr?action=addSaleContractView&contractid='+contractSalesId,'',	'',{width:900,height:500});
       }
       function viewBillApplyForm(){
	        var billApplyId = Ext.getDom("billApplyId").value
	        if(billApplyId=='') {
	           showMsg('请选择开票信息！');
	           return ;
	        }
	        top.ExtModalWindowUtil.show('开票信息','billApplyController.spr?action=addBillApplyView&operType=001&from=composite&billApplyId='+billApplyId,'',	'',{width:900,height:500});
       }
       
      </script>
      
      <c:choose>
      <c:when test="${not empty tShipInfo.oldShipId}">
      <tr>
        <td width="11%" align="right"></td>
        <td width="22%" align="left"><input name="oldShipId" type="hidden" size="40" value="${tShipInfo.oldShipId}"/>
        </td>
        <td width="11%" align="right">原出仓单号：</td>
        <td width="22%" align="left">
			<input type="text" name="oldShipNo" id="oldShipNo" size="20" value="${tShipInfo.oldShipNo}" readonly="readonly"/>
        </td>
        <td width="11%" align="right">原SAP交货单号</td>
        <td width="22%" align="left">
        	<input type="text" name="oldSapReturnNo" id="oldSapReturnNo" size="20" value="${tShipInfo.oldSapReturnNo}" readonly="readonly"/>
        </td>
      </tr>
      </c:when>
      <c:otherwise>
       <input name="oldShipId" type="hidden" value="${tShipInfo.oldShipId}"/>
	   <input name="oldShipNo" type="hidden" value="${tShipInfo.oldShipNo}"/>
	   <input name="oldSapReturnNo" type="hidden" value="${tShipInfo.oldSapReturnNo}"/>
      </c:otherwise>
      </c:choose>
     <tr>
        <td width="11%" align="right">纸质合同号<font color="red">▲</font>：</td>
        <td width="22%" align="left">
        	<input type="text" name="contractPaperNo" id="contractPaperNo" value="${tShipInfo.contractPaperNo }"/>
        </td>
        <td width="11%" align="right">购（领）货单位<font color="red">▲</font>：</td>
        <td width="22%" align="left">
			<input type="text" name="unitName" id="unitName" size="20" value="${tShipInfo.unitName }"/>
        </td>
        <td width="11%" align="right">SAP交货单号</td>
        <td width="22%" align="left"><input name="sapReturnNo" id="sapReturnNo" type="text" size="20" value="${tShipInfo.sapReturnNo }"/>
        </td>
      </tr>
      <tr>
        <td align="right">立项号：</td>
        <td  align="left">
        	<input id="projectNo" name="projectNo" type="text" value="${tShipInfo.projectNo}" readonly="readonly" />
        </td>
        <td align="right">立项名称：</td>
        <td align="left">
        	<input id="projectName" name="projectName" type="text" value="${tShipInfo.projectName}" readonly="readonly" />
        </td>
        <td align="right">合同组编码：</td>
        <td align="left">
        	<input id="contractGroupNo" name="contractGroupNo" type="text" value="${tShipInfo.contractGroupNo}" readonly="readonly" />
        </td>
      </tr>
      <tr>
        <td align="right">SAP订单号：</td>
        <td  align="left">
        	<input id="sapOrderNo" name="sapOrderNo" type="text" value="${tShipInfo.sapOrderNo}" readonly="readonly" />
        </td>
        <td align="right">部门：</td>
        <td align="left">
        	<input id="deptId" name="deptId" type="hidden" value="${tShipInfo.deptId}"  />
        <input id="deptName" name="deptname" type="text" value="${sysDept.deptname}"  readonly="readonly" />
        </td>
        <td align="right">报关单号：</td>
        <td align="left">
        	<input id="declarationsNo" name="declarationsNo" type="text" value="${tShipInfo.declarationsNo}"  />
        </td>        
      </tr>      
      <tr>
      <tr>
        <td  align="right">仓库<font color="red">▲</font></td>
        <td  align="left">
        	<select name="warehouse" id="warehouse" onchange="warehouse_chang()">
				<option value="">请选择</option>
				<c:forEach items="${warehouse}" var="row">
					<option value="${row.code}">${row.code}-${row.title}</option>
				</c:forEach>
			</select>
        </td>
        <td  align="right">仓库地址：</td>
        <td  align="left">
        	<input id="warehouseAdd" name="warehouseAdd" type="text" value="${tShipInfo.warehouseAdd}"  />        	
        </td>
        <td  align="right">申报日期：</td>
        <td  align="left">
            <input id="creatorTime" name="creatorTime" type="text" value="${tShipInfo.creatorTime}"/>
        </td>        
      </tr>
      <tr>
      	<td align="right">
      		业务类型：
      	</td>
      	<td align="left">
      		<input type="text" value="${tradeTypeName}" readonly="readonly"/> 
      	</td>
      	<td colspan="2"><font color="red">如出仓物料为石材，请在备注栏填写石材块数</font></td>
      	<td align="right">是否已开票</td>
      	<td align="left"><select id="isHasInv" name="isHasInv"><option value="0">否</option><option value="1">是</option></select>
      	<font color="red">如该单已开票，请选择是</font>
      	<script>
      	$('isHasInv').value='${tShipInfo.isHasInv}';
      	</script>
      	</td>
      </tr>
      
      <tr>
      	<td align="right">预计收款日期<font color="red">▲</font>:</td>
      	<td align="left">
      		<input type="text" id="intendGatherTime" name="intendGatherTime" value="${tShipInfo.intendGatherTime}" />
      	</td>
      	
      	<td align="right">预计开票日期<font color="red">▲</font>:</td>
      	<td align="left">
      		<input type="text" id="makeInvoiceTime" name="makeInvoiceTime" value="${tShipInfo.makeInvoiceTime}" />
      	</td>
      	<td align="right">开票申请单号:</td>
      	<td align="left">
      		<input type="text" id="billApplyNo" name="billApplyNo" value="${tShipInfo.billApplyNo}" readonly="readonly" size="12"/>
      		<input type="hidden" id="billApplyId" name="billApplyId" value="${tShipInfo.billApplyId}" />
      		<input type="button" value="..." onclick="selectBillApply()"></input>
		    <a href="#" onclick="viewBillApplyForm()">查看</a>
      	</td>
      </tr>
      
      <tr>
        <td align="right">备注：</td>
        <td colspan="5">
        	 <textarea cols="80" rows="1" id="cmd" name="cmd" style="width:95%;overflow-y:visible;word-break:break-all;">${tShipInfo.cmd}</textarea>
        </td>
    </tr>
	</table>
</form>
</div>
<!-- </div> -->
<div id="div_center"></div>
<div id="div_history" class="x-hide-display" ></div>
<div id="" class="x-hide-display">
<div id="rule" class="x-hide-display" ></div>
<select name="currency" id="currency" style="display: none;">
		<option value="">请选择</option>
		<c:forEach items="${Currency}" var="row">
			<option value="${row.code}">${row.code}-${row.title}</option>
		</c:forEach>
</select>
</div>
<div id="rule1" class="x-hide-display" >
<select name="salecurrency" id="salecurrency" style="display: none;">
		<option value="">请选择</option>
		<c:forEach items="${Currency}" var="row">
			<option value="${row.code}">${row.code}-${row.title}</option>
		</c:forEach>
</select>
</div>
</body>
</html>
<fiscxd:fileUpload divId="rule" recordId="${tShipInfo.shipId}" erasable="${!save}"  increasable="${!save}" 
 resourceId="2222" resourceName="33333" recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>
<fiscxd:workflow-taskHistory divId="div_history" businessRecordId="${tShipInfo.shipId}" width="750" url="shipController.spr?action=queryTaskHis&businessid=${tShipInfo.shipId}"></fiscxd:workflow-taskHistory>
<fiscxd:dictionary divId="div_examineState" fieldName="examineState" dictionaryName="BM_EXAMINE_STATE" width="150" selectedValue="${tShipInfo.examineState}" disable="true" ></fiscxd:dictionary>
    
