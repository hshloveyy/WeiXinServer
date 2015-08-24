<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ taglib prefix="ffcs" tagdir="/WEB-INF/tags/infolion"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>货物出仓单登记表</title>
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
//'<%=request.getAttribute("tradeType")%>'
//贸易名称
var strshipTitle ='';

var Enabled='false';
if ('${submit}'!='true')Enabled='submit';
if ('${save}'!='true')Enabled='save';

//取贸易名称
strshipTitle = Utils.getTradeTypeTitle(tradeType);


function selectSalesWin(){
	top.ExtModalWindowUtil.show('查询所属登陆员工总门的合同信息',
	'shipNotifyController.spr?action=selectSalesInfo&tradeType=' + '${tExportApply.tradeType}' +'&deptid=',
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
	Ext.getDom('customer').value=returnvalue.KUWEV_KUNNR_NAME;
	dict_div_tradeTerms.setComboValue(returnvalue.VBKD_INCO1);
	Ext.getDom('tradeTerms2').value = returnvalue.VBKD_INCO2;
	Ext.getDom('contractPaperNo').value=returnvalue.VBKD_IHREZ;
	//
	$('exchangeType').value=returnvalue.VBKD_ZLSCH_D_PAY_TYPE;
	$('shipmentDate').value=returnvalue.SHIPMENT_DATE;
	//$('exportPort').value=returnvalue.SHIPMENT_PORT;
	$('destinations').value=returnvalue.DESTINATION_PORT;
	
	//Ext.getDom('sapOrderNo').value
	//alert(returnvalue.VBKD_INCO1);
	var requesturl = 'shipNotifyController.spr?action=initExportApply&contractSalesId=' + returnvalue.CONTRACT_SALES_ID + '&exportApplyId='+'${tExportApply.exportApplyId}';
	requesturl = requesturl + "&projectNo=" + returnvalue.PROJECT_NO + '&projectName=' + returnvalue.PROJECT_NAME;
	requesturl = requesturl + "&contractGroupNo=" + returnvalue.CONTRACT_GROUP_NO + '&sapOrderNo=' + returnvalue.SAP_ORDER_NO;
	Ext.Ajax.request({
		url: encodeURI(requesturl),
		success: function(response, options){
  			 shipDetailInfods.load({params:{start:0, limit:10},arg:[]});
		},
		failure:function(response, options){
			top.ExtModalWindowUtil.alert('提示','与服务器交付失败请重新再试！');
		}
	});
	
	
}
function selectLcNoWin(){
	if (Enabled=='false') return;	//Wang Yipu 2009.4.9
	var contractNo = Ext.getDom('salesNo').value;
	if (contractNo == ""){
			top.ExtModalWindowUtil.alert('提示','请先选择销售合同号!');
			return;	
	}
	top.ExtModalWindowUtil.show('查询信用证号',
	'shipNotifyController.spr?action=toFindLcNo&contractNo=' + contractNo+'&tradeType=${tradeType}',
	'',
	lcNoCallback,
	{width:850,height:450});
}

function selectCusWin(){
	if (Enabled=='false') return;	
	top.ExtModalWindowUtil.show('查询外客户',
	'shipNotifyController.spr?action=toFindCus',
	'',
	cusCallback,
	{width:850,height:450});
}

function lcNoCallback(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('lcno').value=returnvalue.CREDIT_NO;	
}
function cusCallback(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('customer').value=returnvalue.TITLE;	
}
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if (strOperType == '1'){
		shipDetailInfods.reload();
	}	
	if (strOperType == '3'){
		//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
	    top.ExtModalWindowUtil.close();
	}	
}

var shipDetailInfogrid;		//出仓单明细列表
var shipDetailInfods;

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';
   	
   	var fm = Ext.form;   
    //增加出货通知单明细资料(物料)的回调函数
    function funAddshipDetailCallBack(){
	 	var returnValue = top.ExtModalWindowUtil.getReturnValue();   
    	var requestUrl = 'shipNotifyController.spr?action=addDetail&salesRowsId=' + returnValue.SALES_ROWS_ID;
    	requestUrl = requestUrl + "&exportApplyId=" + "${tExportApply.exportApplyId}";  
    	requestUrl = requestUrl + "&tradeType=" + "${tExportApply.tradeType}";  
    	requestUrl = requestUrl + "&hasQuantity=" + returnValue.HAS_QUANTITY ;
    	//alert(requestUrl);
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);

				var p = new shipDetailInfoPlant({
	                    EXPORT_MATE_ID: responseArray.exportMateId,
						EXPORT_APPLY_ID: responseArray.exportApplyId,                
						MATERIAL_CODE: responseArray.materialCode,               
				    	MATERIAL_NAME: responseArray.materialName,                
						MATERIAL_UNIT: responseArray.materialUnit, 
						OLD_QUANTITY: responseArray.quantity,                
						QUANTITY: responseArray.quantity,                 
						GROSS_WEIGHT: responseArray.grossWeight,               
						NET_WEIGHT: responseArray.netWeight,                 
				    	RATE: responseArray.rate,                
						PRICE: responseArray.price, 
						PEINH: responseArray.peinh,                
						CURRENCY: responseArray.currency,              
						TOTAL: responseArray.total,
						//TOTAL:  parseFloat(responseArray.price) * parseFloat(responseArray.quantity) / parseFloat(responseArray.peinh)         
						CMD: responseArray.cmd,               
						IS_AVAILABLE: responseArray.isAvailable,
						CREATOR_TIME: responseArray.creatorTime,             
						CREATOR: responseArray.creator	                    
	                });
				shipDetailInfogrid.stopEditing();
				shipDetailInfods.insert(0, p);
				shipDetailInfogrid.startEditing(0, 0);
				setMaterielTotal(p);
				//var records = shipDetailInfogrid.getStore().getAt(0);
				//records.set("VBAP_WERKS_D_FACTORY",getTitle('vbapWerks11',responseArray.vbapWerks));
				//records.set("VBKD_ZTERM_D_PAYMENT_TYPE",getTitle('vbkdZterm11',responseArray.vbkdZterm));
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
				if (contractSalesId == ''){
					top.ExtModalWindowUtil.alert('提示','请先选择销售合同才能增加物料信息！');
					return;				
				}
				top.ExtModalWindowUtil.show(strshipTitle+'货物通知单登记表',
				'shipNotifyController.spr?action=toFindSalesMaterial&shipType=0&contractSalesId='+contractSalesId + "&tradeType=" + tradeType,
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
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个出仓单明细信息！');
				}else{
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=deleteDetail";
								param = param + "&exportMateId=" + records[0].data.EXPORT_MATE_ID;

								new AjaxEngine('shipNotifyController.spr', 
						   			{method:"post", parameters: param, onComplete: callBackHandle});
						   			
						   		strOperType = '1';
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
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
	  
	var shipDetailInfoPlant = Ext.data.Record.create([
		{name:'EXPORT_MATE_ID'},              
		{name:'EXPORT_APPLY_ID'},                 
		{name:'MATERIAL_CODE'},                
	    {name:'MATERIAL_NAME'},                 
		{name:'MATERIAL_UNIT'},  
		{name:'INVENTORY_NUM'},		              
		{name:'OLD_QUANTITY'},		              
		{name:'QUANTITY'},                 
		{name:'GROSS_WEIGHT'},               
		{name:'NET_WEIGHT'},                 
		{name:'RATE'},                
		{name:'PRICE'},
		{name:'PEINH'},		                
		{name:'CURRENCY'},              
		{name:'TOTAL'},               
		{name:'CMD'},                
		{name:'IS_AVAILABLE'},
		{name:'CREATOR_TIME'},              
		{name:'CREATOR'}	               
	]);

	shipDetailInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'shipNotifyController.spr?action=queryMaterial&exportApplyId='+'${tExportApply.exportApplyId}'}),
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
           dataIndex: 'EXPORT_MATE_ID',
            hidden:true
           },
		　 {header: '申请单ID',
           width: 100,
           sortable: true,
           dataIndex: 'EXPORT_APPLY_ID',
           hidden:true
           },
           {header: '物料编码',
           width: 100,
           sortable: true,
           dataIndex: 'MATERIAL_CODE'
           },
		　 {header: '物料名称',
           width: 100,
           sortable: true,
           dataIndex: 'MATERIAL_NAME'
           },
		　 {header: '物料单位',
           width: 100,
           sortable: false,
           dataIndex: 'MATERIAL_UNIT'
           },
		　 {header: '库存数量',
           width: 100,
           sortable: false,
           dataIndex: 'INVENTORY_NUM',
           hidden: ${inventoryNum}
           }, 
   		　 {header: '原数量',
           width: 100,
           sortable: false,
           dataIndex: 'OLD_QUANTITY',
           hidden: true
           },           
           {header: '申请数量',
           width: 100,
           sortable: true,
           dataIndex: 'QUANTITY',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:3,
               maxValue: 10000000000
           })            
           },
           {
           header: '毛重(公斤)',
           width: 100,
           sortable: true,
           dataIndex: 'GROSS_WEIGHT',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 100000
           })             
           },
           {
           header: '净重(公斤)',
           width: 100,
           sortable: true,
           dataIndex: 'NET_WEIGHT',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 1000000
           })             
           },
           {
           header: '税率',
           width: 100,
           sortable: true,
           dataIndex: 'RATE'
           },           
           {
           header: '单价',
           width: 100,
           sortable: true,
           dataIndex: 'PRICE',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 100000000000
           })            
           },
           {
           header: '每单价单位',
           width: 100,
           sortable: true,
           dataIndex: 'PEINH',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 100000
           })
           },           
           {
           header: '币别',
           width: 100,
           sortable: true,
           dataIndex: 'CURRENCY'
           },
           {
           header: '总价',
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
           }
    ]);
    
    shipDetailInfocm.defaultSortable = true;
    
    var shipDetailInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:shipDetailInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
	 
   shipDetailInfods.load({params:{start:0, limit:10},arg:[]});
   
   if (Enabled=='false')
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
   else
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
	        clicksToEdit:1,
	        layout:'fit'
	    });
    
    shipDetailInfogrid.render();
    
    shipDetailInfogrid.on("afteredit", afterEdit, shipDetailInfogrid);
    
    function afterEdit(obj){
    	var row = obj.record;//获取被修改的行
        var colName = obj.field;//获取被修改的列
        var exportMateId = row.get("EXPORT_MATE_ID");
        var colValue = row.get(colName);
		
		//批号
        if (colName == 'BATCH_NO')
        	queryInventoryNum(row);
        	        
        //数量或单价
        if (colName == 'QUANTITY' || colName == 'PRICE' ){
        	//setMaterielTotal(row);
//        	setContractSaleMoney();
        }       
        updateMaterielValue(row,exportMateId,colName,colValue);

    }    
       
     //刷新库存数量
     function　queryInventoryNum(row){
		if ('${inventoryNum}'=='true')return;
        
		var requestUrl ="shipNotifyController.spr?action=queryInventoryNum&" + Form.serialize('mainForm');

        var materialCode = row.get("MATERIAL_CODE");
        var batchNo = row.get("BATCH_NO");

		requestUrl += "&materialCode=" +　materialCode;
		requestUrl += "&batchNo=" +　batchNo;

		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				var responseUtil1 = new AjaxResponseUtils(response.responseText);
				var customField = responseUtil1.getCustomField("coustom");   
				var inventoryNum = customField.inventoryNum;
  				row.set("INVENTORY_NUM",formatnumber(inventoryNum,2));				
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});	
     }
     
    //更新物料信息
    function updateMaterielValue(row,exportMateId,colName,colValue){
    	var requestUrl = 'shipNotifyController.spr?action=updateExportApplyMateriel';
		requestUrl = requestUrl + '&exportMateId=' + exportMateId;
		requestUrl = requestUrl + '&colName=' + colName;
		requestUrl = requestUrl + '&colValue=' + colValue;
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseUtil1 = new AjaxResponseUtils(response.responseText);
				var customField = responseUtil1.getCustomField("coustom");   
				var stateCode = customField.stateCode;
				if (stateCode == '0'){
					if (colName == "QUANTITY"){
						top.ExtModalWindowUtil.alert('提示','输入的申请数量超过合同物料数量！');					
						row.set("QUANTITY",row.get("OLD_QUANTITY"));
					}				
				}
				else {
					if (colName == "QUANTITY" || colName == "PRICE" || colName == "PEINH" ){
						row.set("TOTAL",customField.total);
						//row.set("TOTAL","1234");						
					}
					shipDetailInfods.commitChanges();				
				}     
			},
			failure:function(response, options){					
				if (colName == "QUANTITY"){
					top.ExtModalWindowUtil.alert('提示','输入的申请数量超过合同物料数量！');					
					row.set("QUANTITY",row.get("OLD_QUANTITY"));
				}
			}
		});  
		 
    }    
        //设置金额
     function setMaterielTotal(row){   
        var exportMateId = row.get("EXPORT_MATE_ID");
     	var price = row.get("PRICE");
     	var quantity = row.get("QUANTITY");   
     	var PEINH = row.get("PEINH");       //add by zxj 20090508 	
     	if (quantity == null || quantity == ''){
     		quantity = "0";
     	}
     	if (price == null || price == ''){
     		price = "0";
     	}    
     	//var total = parseFloat(quantity) *  parseFloat(price);
     	var total = parseFloat(quantity) *  parseFloat(price) / parseFloat(PEINH); //modify by zxj 20090508  	
     	row.set("TOTAL",formatnumber(total,2));	
     	updateMaterielValue(row,exportMateId,"TOTAL",formatnumber(total,2));
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
    //shipDetailInfogrid.addListener('cellclick', productgridcellclick);
    //shipDetailInfogrid.addListener('celldblclick', productgridcelldbclick);
    
    shipDetailInfods.load({params:{start:0, limit:10}});     

    
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			height:237,
			xtype:'tabpanel',
			id:'infotype',
			name:'infotype',
			plain:true,
            autoScroll:'true',
            activeTab: 0,			
            items:[{
            	title:'出口货物通知单',
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
			items:[{
				region:'center',
				xtype:'tabpanel',
				id:'infotype',
				name:'infotype',
				plain:true,
	            autoScroll:'true',
	            activeTab: 0,
	            height:220,
			    minSize: 210,
                maxSize: 400,
	            items:[{
			            	title:'物料信息',
			            	contentEl: '',
			            	id:'shipDetailInfo',
							name:'shipDetailInfo',
							autoScroll:'true',
			            	layout:'fit',
			            	items:[shipDetailInfogrid]
	                  }
	                  ]
	             }],
				buttons:[{
	            	text:'保存',
	            	hidden:${save},
	            	handler:function(){
	            		var param1 = Form.serialize('mainForm');
						var param = param1 + "&action=updateExportApply";
						new AjaxEngine('shipNotifyController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
					}

	            },{
            		text:'提交',
	            	hidden:${submit},            	
            		handler:function(){
           			if (shipDetailInfods.getCount() > 0){
            		var param1 = Form.serialize('mainForm');
					var param = param1  + "&action=submitAndSaveExportApply";
					new AjaxEngine('shipNotifyController.spr', 
						   {method:"post", parameters: param, onComplete: callBackHandle});
						   
					strOperType = '3';
					
					} else{
					top.Ext.Msg.show({
						title:'提示',
	  					msg: '没有增加物料信息,请确认是否真的要提交审批?',
	  					buttons: {yes:'是', no:'否'},
	  					fn: function(buttonId,text){
	  						if(buttonId=='yes'){
		             			var param = Form.serialize('mainForm');
								var param = param1  + "&action=submitAndSaveExportApply";
								new AjaxEngine('shipNotifyController.spr', 
						   			{method:"post", parameters: param, onComplete: callBackHandle});
						   				
						   		strOperType = '3';
	  						}
	  					},
	  					icon: Ext.MessageBox.QUESTION
					});
					}          	    	
            	    
            	}
            },
	            {
	            	text:'关闭',
	            	handler:closeForm
	            },
	            {
	            	text:'保存核销单号',
	            	hidden:${!showWriteNoButton=='true'},
	            	handler:function(){
						var param = "&action=updateWriteNo&exportApplyId="+$('exportApplyId').value+'&writeNo='+$('writeTo').value;
						param +='&getSheetTime='+$('getSheetTime').value;
						new AjaxEngine('shipNotifyController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
					}

	            }]
		       }]
	});

    //装运日期
   	var shipmentDate = new Ext.form.DateField({
   		format:'Ymd',
		id:'shipmentDate',
	    name:'shipmentDate',
		width: 160,
	    readOnly:true,
		applyTo:'shipmentDate'
   	});
   	
   	//领单时间
   	var getSheetTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'getSheetTime',
	    name:'getSheetTime',
		width: 160,
	    readOnly:true,
		applyTo:'getSheetTime'
   	});

  //交单时间
   	var referSheetTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'referSheetTime',
	    name:'referSheetTime',
		width: 160,
	    readOnly:true,
		applyTo:'referSheetTime'
   	});
   	

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

</script>
</head>
<body>
<div id='div_progressBar'></div>
<!-- <div id="div_basrForm">  -->
<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
	<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
      <tr>
      	<input type="hidden" id = "exportApplyId" name = "exportApplyId" value="${tExportApply.exportApplyId }"/>
      	<input type="hidden" id = "contractSalesId" name = "contractSalesId" value="${tExportApply.contractSalesId }"/>
      	<input type="hidden" id = "tradeType" name = "tradeType" value="${tExportApply.tradeType }"/>
      	<input type="hidden" id = "billState" name = "billState" value="${tExportApply.billState }"/>    
      	<input type="hidden" id = "applyTime" name = "applyTime" value="${tExportApply.applyTime }"/>
      	<input type="hidden" id = "approvedTime" name = "approvedTime" value="${tExportApply.approvedTime }"/>
      	<input type="hidden" id = "isAvaiLable" name = "isAvaiLable" value="${tExportApply.isAvaiLable }"/>
      	<input type="hidden" id = "creatorTime" name = "creatorTime" value="${tExportApply.creatorTime }"/>
      	<input type="hidden" id = "creator" name = "creator" value="${tExportApply.creator }"/> 
      	<input type="hidden" id = "projectName" name = "projectName" value="${tExportApply.projectName }"/> 
      	<input type="hidden" id="sapOrderNo" name="sapOrderNo"  value="${tExportApply.sapOrderNo}"  />   
        <td width="11%" align="right">销售合同号：</td>
        <td width="22%" align="left">
        	<input type="text" id="salesNo" name="salesNo" value="${tExportApply.salesNo}" readonly="readonly" size="12"/>
        	 <input type="button" value="..." onclick="selectSalesWin()" <c:if test="${save=='true'}">disabled="disabled"</c:if>></input>
        	 <a href="#" onclick="viewContractForm()">查看</a>
        </td>
        <script>
			function viewContractForm(){
			        var salesID = $('contractSalesId').value;
			        if(salesID==''||salesID==null){
			           alert('请先选择销售合同');
			           return false;
			        }
					top.ExtModalWindowUtil.show('查看合同信息','contractController.spr?action=archSalesInfoView&businessRecordId='+salesID,'','',{width:800,height:500});
		    }
        </script>
        <td width="12%" align="right">通知单号：</td>
        <td width="21%" align="left">
            <input id="noticeNo" name="noticeNo"  value="${tExportApply.noticeNo }" readonly="readonly" />
        </td>
        <td width="11%" align="right">外部合同号：</td>
        <td width="22%" align="left">
        	<input id="contractPaperNo" name="contractPaperNo" type="text" value="${tExportApply.contractPaperNo}" readonly="readonly" />
        </td>
      </tr>
      <tr>
      	<td align="right">立项号：</td>
        <td  align="left">
        	<input id="projectNo" name="projectNo" type="text" value="${tExportApply.projectNo}" readonly="readonly" />
        </td> 
        <td align="right">合同组编码：</td>
        <td align="left">
        	<input id="contractGroupNo" name="contractGroupNo" type="text" value="${tExportApply.contractGroupNo}" readonly="readonly" />
        </td>                
        <td align="right">部门：</td>
        <td align="left">
        	<input id="deptId" name="deptId" type="hidden" value="${tExportApply.deptId}"  />
        	<input id="deptName" name="deptname" type="text" value="${sysDept.deptname}"  readonly="readonly" />
        </td>
      </tr>
      <tr> 
        <td align="right">L/C NO.:</td>
        <td align="left">
            <input id="lcno" name="lcno" type="text"  value="${tExportApply.lcno}" readonly="readonly" />
            <input type="button" value="..." onclick="selectLcNoWin()"></input>
        </td>        
        <td align="right">委托方:</td>
        <td align="left">
            <input id="commitment" name="commitment" type="text"  value="${tExportApply.commitment}"/>
        </td>
         <td align="right">供应商:</td>
        <td align="left">
            <input id="supplier" name="supplier" type="text"  value="${tExportApply.supplier}"/>
        </td>          
      </tr>      
      <tr>
        <td align="right">收汇方式：</td>
        <td align="left">
       	    <input id="exchangeType" name="exchangeType" type="text"  value="${tExportApply.exchangeType}"/>
        </td>
        <td align="right">产地：</td>
        <td  align="left">
			<input id="origin" name="origin" type="text"  value="${tExportApply.origin}"/>
        </td>
        <td align="right">装运日期：</td>
        <td align="left">
			<input id="shipmentDate" name="shipmentDate" type="text"  value="${tExportApply.shipmentDate}"/>        
        </td>
      </tr>
     <tr>
        <td align="right">核销单号：</td>
        <td align="left">
        	<input id="writeTo" name="writeTo" type="text" value="${tExportApply.writeTo}"  />
        </td>       
        <td align="right">国际贸易条款:</td>
        <td align="left">
        	<div id="div_tradeTerms"></div>
        </td>              
        <td align="right">国际贸易条款2:</td>
        <td align="left">
            <input id="tradeTerms2" name="tradeTerms2" type="text"  value="${tExportApply.tradeTerms2}"/>
        </td>        
      </tr>
    <tr>

        <td align="right"><!--出口口岸:  --></td>
        <td align="left">
            <!-- input id="exportPort" name="exportPort" type="text"  value="${tExportApply.exportPort}"/-->
        </td>  
         <td align="right">目的地:</td>
        <td align="left">
            <input id="destinations" name="destinations" type="text" value="${tExportApply.destinations}"/>
        </td>       
        <td align="right">
           客户：
        </td>
        <td align="left">
          <input id="customer" name="customer" type="text" value="${tExportApply.customer}" readonly="readonly"/>
          <input type="button" id="cusBtn" value="..." onclick="selectCusWin()">
        </td>         
     </tr>
     <tr>
     	<td align="right">
     		领单时间:
     	</td>
     	<td  align="left">
     		<input type="text" id="getSheetTime" name="getSheetTime"  value="${tExportApply.getSheetTime}"/>
     	</td>
         <td align="right">是否有装前预收款</td>
        <td align="left">
        <input type="hidden" id="isPay" name="isPay"  value="${tExportApply.isPay}"/>
        <c:choose>
        	<c:when test="${tExportApply.isPay=='1'}">
				<input type="radio" name="is_Pay" id="is_Pay" value="1" onclick="javascript:Ext.getDom('isPay').value='1';" checked>是
				<input type="radio" name="is_Pay" id="is_Pay" value="0" onclick="javascript:Ext.getDom('isPay').value='0';">否
			</c:when>
			<c:otherwise>
				<input type="radio" name="is_Pay" id="is_Pay" value="1" onclick="javascript:Ext.getDom('isPay').value='1';">是
				<input type="radio" name="is_Pay" id="is_Pay" value="0" onclick="javascript:Ext.getDom('isPay').value='0';" checked>否
			</c:otherwise>
        </c:choose>
        </td>  
        <td width="11%" align="right">单据状态：</td>
        <td width="22%" align="left">
        	<div id="div_examineState"></div>
        </td>            
    </tr>
    
    <tr>
    	<td align="right">
    	最迟交单日期:
    	</td>
    	<td align="left">
    		<input type="text" id="referSheetTime" name="referSheetTime"  value="${tExportApply.referSheetTime}"/>
    	</td>
        <td align="right">备注:</td> 
         <td align="left" colSpan="3">
            <input id="cmd" name="cmd" type="text" value="${tExportApply.cmd}" size=65 />
        </td> 
     </tr> 

	</table>
</form>
</div>
<!-- </div> -->
<div id="div_center"></div>
<div id="div_history" class="x-hide-display" ></div>


</body>

</html>
<fiscxd:workflow-taskHistory divId="div_history" businessRecordId="${tExportApply.exportApplyId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:dictionary divId="div_examineState" fieldName="examineState" dictionaryName="BM_EXAMINE_STATE" width="150" selectedValue="${tExportApply.examineState}" disable="true" ></fiscxd:dictionary>
<fiscxd:dictionary divId="div_tradeTerms" fieldName="tradeTerms" dictionaryName="BM_INCOTERM_TYPE" needDisplayCode="true" width="150" selectedValue="${tExportApply.tradeTerms}"></fiscxd:dictionary>
