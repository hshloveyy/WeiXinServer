<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ taglib prefix="ffcs" tagdir="/WEB-INF/tags/infolion"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>货物进仓单登记表</title>
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
//var from = '<%=request.getAttribute("popfrom")%>';
//贸易类型
var tradeType ='${tradeType}'; 
//'<%=request.getAttribute("tradeType")%>'
//贸易名称
var strreceiptsTitle ='';
var strOperType='';
var Enabled='false';
if ('${submit}'!='true')Enabled='submit';
if ('${save}'!='true')Enabled='save';
<c:if test="${not empty main.oldReceiptId}">
var Enabled='false';
</c:if>
var iNum=true;
if (tradeType=='5'||tradeType=='6')iNum=false;		//代理进出口业务

//取贸易名称
strreceiptsTitle = Utils.getTradeTypeTitle(tradeType);

function selectPurchaseWin(){
	if (Enabled=='false')return;
	top.ExtModalWindowUtil.show('查询所属登陆员工的合同信息',
	'receiptsController.spr?action=selectPurchaseInfo&tradeType=' + '${main.tradeType}' +'&orderState=3,5,7,8,9,11&deptid=',
	'',
	selectPurchaseInfoCallBack,
	{width:900,height:450});
}
function selectPurchaseInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('contractPurchaseId').value=returnvalue.CONTRACT_PURCHASE_ID;
	Ext.getDom('contractNo').value=returnvalue.CONTRACT_NO;
	Ext.getDom('projectNo').value = returnvalue.PROJECT_NO;
	Ext.getDom('projectName').value = returnvalue.PROJECT_NAME;
	Ext.getDom('contractGroupNo').value = returnvalue.CONTRACT_GROUP_NO;
	Ext.getDom('sapOrderNo').value = returnvalue.SAP_ORDER_NO;	
	Ext.getDom('contractPaperNo').value = returnvalue.EKKO_UNSEZ!=null?returnvalue.EKKO_UNSEZ:'';	
	Ext.getDom('unitName').value = returnvalue.EKKO_LIFNR_NAME;	
	
	var requesturl = 'receiptsController.spr?action=initReceiptMt&contractPurchaseId=' + returnvalue.CONTRACT_PURCHASE_ID + '&receiptId='+'${main.receiptId}';
	requesturl = requesturl + "&projectNo=" + returnvalue.PROJECT_NO + '&projectName=' + returnvalue.PROJECT_NAME;
	requesturl = requesturl + "&contractGroupNo=" + returnvalue.CONTRACT_GROUP_NO + '&sapOrderNo=' + returnvalue.SAP_ORDER_NO;
	requesturl = requesturl + "&receiptTime=" + Ext.getDom('receiptTime').value;
	requesturl = requesturl + "&payAbleDate=" + Ext.getDom('payAbleDate').value;
	requesturl = requesturl + "&receiveBillDate=" + Ext.getDom('receiveBillDate').value;
	requesturl = requesturl + "&sendGoodsDate=" + Ext.getDom('sendGoodsDate').value;

	
	Ext.Ajax.request({
		url: encodeURI(requesturl),
		success: function(response, options){
  			 receiptsDetailInfods.load({params:{start:0, limit:10},arg:[]});
		},
		failure:function(response, options){
			top.ExtModalWindowUtil.alert('提示','与服务器交付失败请重新再试！');
		}
	});
}

//新增保存删除提示
function customCallBackHandle(transport)
{
	if('save'==strOperType){
		//top.ExtModalWindowUtil.markUpdated();
		//top.ExtModalWindowUtil.close();
		//return;
	}
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var sReturnMessage=customField.returnMessage;
	
	top.ExtModalWindowUtil.alert('提示',sReturnMessage);

//	if (strOperType == '1'){
		receiptsDetailInfods.reload();
//	}
	if ('${close}'=='false' && sReturnMessage.indexOf("提交成功")>-1)
	{	
		//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	}
	if (sReturnMessage.indexOf("过帐成功")>-1){
	    $('sapReturnNo').value=sReturnMessage.substring(sReturnMessage.indexOf("过帐成功")+5,sReturnMessage.length);
	}
}
function customCallBackHandle1(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var msg=customField.returnMessage;
	var returnNo= customField.returnMessage;
	$('sapReturnNo').value=returnNo;
	if(returnNo!=''){
	   top.ExtModalWindowUtil.alert('提示','过帐成功：'+returnNo);
	}
	else {
       top.ExtModalWindowUtil.alert('提示','过帐失败：'+msg);
	}
}
var receiptsDetailInfogrid;		//进仓单明细列表
var receiptsDetailInfods;

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

    var fm = Ext.form;
    //增加进仓单明细资料(物料)的回调函数
    function funAddReceiptDetailCallBack(){
	 	var returnValue = top.ExtModalWindowUtil.getReturnValue();   
    	var requestUrl = 'receiptsController.spr?action=addDetail&purchaseRowsId=' + returnValue.PURCHASE_ROWS_ID;
		
    	requestUrl = requestUrl + "&receiptId=" + "${main.receiptId}";   
    	requestUrl = requestUrl + "&Lines=" + returnValue.LINES;   
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				receiptsDetailInfods.reload();
				
				//var records = receiptsDetailInfogrid.getStore().getAt(0);
				//records.set("VBAP_WERKS_D_FACTORY",getTitle('vbapWerks11',responseArray.vbapWerks));
				//records.set("VBKD_ZTERM_D_PAYMENT_TYPE",getTitle('vbkdZterm11',responseArray.vbkdZterm));
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});
    }  
        
    //增加进仓单明细资料(物料)
    var addReceiptDetailInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			var contractPurchaseId = Ext.getDom('contractPurchaseId').value;
			if (contractPurchaseId == ''){
				top.ExtModalWindowUtil.alert('提示','请先选择采购合同才能增加物料信息！');
				return;				
			}
			top.ExtModalWindowUtil.show(strreceiptsTitle+'货物进仓单登记表',
			'receiptsController.spr?action=toFindPurchaseMaterial&contractPurchaseId='+contractPurchaseId,
			'',
			funAddReceiptDetailCallBack,
			{width:800,height:500});
		}
   	});
   	
   	//删除
   	var deleteReceiptDetailInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (receiptsDetailInfogrid.selModel.hasSelection()){
				var records = receiptsDetailInfogrid.selModel.getSelections();
				var receiptDetailIds='' ;
				for(i=0;i<records.length ;i++){
				   receiptDetailIds+=records[i].data.RECEIPT_DETAIL_ID+',';
				}
				/*if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个进仓单明细信息！');
				}else{*/
				top.Ext.Msg.show({
					title:'提示',
  						msg: '是否确定删除记录',
  						buttons: {yes:'是', no:'否'},
  						fn: function(buttonId,text){
  							if(buttonId=='yes'){
  								var param = "?action=deleteDetail";
							param = param + "&receiptDetailIds=" + receiptDetailIds;

							new AjaxEngine('receiptsController.spr', 
					   			{method:"post", parameters: param, onComplete: callBackHandle});
					   			
					   		strOperType = '1';
  							}
  						},
  						icon: Ext.MessageBox.QUESTION
				});
				//}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的进仓单明细信息！');
			}
		}
   	});
   	
   	
   	//工具栏---添加、删除按钮。
   	if (Enabled!='false')
    var receiptsDetailInfotbar = new Ext.Toolbar({
		items:[addReceiptDetailInfo,'-',deleteReceiptDetailInfo]
	  });

   	var checkColumn = new Ext.grid.CheckColumn({
        header: "是否已清",
        dataIndex: 'IS_CLEAR',
        width: 55
     });
	  
	var receiptsDetailInfoPlant = Ext.data.Record.create([
		{name:'RECEIPT_DETAIL_ID'},  			//明细ID
		{name:'RECEIPT_ID'},  					//进仓ID
		{name:'MATERIAL_CODE'},  				//物料编码
		{name:'MATERIAL_NAME'},  				//物料名称
		{name:'SAP_ROW_NO'},  				//行项目编号
		{name:'MATERIAL_UNIT'},      			//物料单位
		{name:'INVENTORY_NUM'},            		//库存数量
		
		{name:'QUANTITY'},            			//数量
		{name:'SEQUANTITY'},            		//二次结算调整数量
		{name:'GROSS_WEIGHT'},               
		{name:'NET_WEIGHT'},                 
		{name:'RATE'},                
		{name:'BATCH_NO'},         				//批次号
		{name:'PRICE'},           				//单价
		{name:'EKPO_PEINH'},                    //每价格单位
		{name:'CURRENCY'},             			//币别
		{name:'TOTAL'},             			//总计
		{name:'CMD'},             				//备注
		{name:'IS_AVAILABLE'},             		//是否有效
		{name:'CREATOR_TIME'},             		//创建时间
		{name:'CREATOR'},             			//创建人
		{name:'RECEIPTTIME'},					//实际入库时间
		{name:'PAYABLEDATE'},					//预计付款日
		{name:'RECEIVEBILLDATE'},				//预计收票日
		{name:'SENDGOODSDATE'},					//预计销售发货日
		{name:'CURRENT_CLEAR_CHARGE'},			//本次清帐金额
		<c:if test="${tradeType=='8'||tradeType=='12'}">
		{name:'COST_PRICE'},			//成本单价
		{name:'COST_TOTAL'},			//成本总计
		</c:if>
		{name:'IS_CLEAR'}						//是否已清
	]);

	receiptsDetailInfods = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({url:'receiptsController.spr?action=queryMaterial&receiptId=' + '${main.receiptId}'}),
        reader: new Ext.data.JsonReader({
        root: 'root',
        totalProperty: 'totalProperty'
        },receiptsDetailInfoPlant)
    });
        
    var receiptsDetailInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var receiptsDetailInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		receiptsDetailInfosm,
		   {header: '行项目编号',
           width: 70,
           sortable: true,
           dataIndex: 'SAP_ROW_NO'
           },
           {header: '物料编码',
           width: 80,
           sortable: true,
           dataIndex: 'MATERIAL_CODE'
           },
			{header: '物料名称',
			width: 100,
			sortable: true,
			dataIndex: 'MATERIAL_NAME'
           },
			{header: '物料单位',
			width: 40,
			sortable: false,
			dataIndex: 'MATERIAL_UNIT'
           },
		　 {header: '库存数量',
           width: 70,
           sortable: false,
           dataIndex: 'INVENTORY_NUM',
           hidden: ${inventoryNum}
           }, 
           {header: '数量',
           width: 80,
           sortable: true,
           dataIndex: 'QUANTITY',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:3,
               maxValue: 10000000000,
               allowNegative:true,
               disabled:${main.examineState=='5'}
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
           hidden:${main.examineState=='5'?"false":"true"}
           },
/*           {
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
               maxValue: 100000
           })             
           },
           {
           header: '税率',
           width: 100,
           sortable: true,
           dataIndex: 'RATE'
           },
*/       
           {
           header: '批次号<font color="red">▲</font>',
           width: 80,
           sortable: true,
           dataIndex: 'BATCH_NO',
           editor: new fm.TextField({
               allowBlank: true,
               maxLength:10
           })
           },
           {
           header: '单价',
           width: 80,
           sortable: true,
           dataIndex: 'PRICE',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 1000000000
           })            
           },
           {
           header: '每价格单位',
           width: 80,
           sortable: true,
           dataIndex: 'EKPO_PEINH',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 10000
           })            
           },
           {
           header: '币别',
           width: 50,
           sortable: true,
           dataIndex: 'CURRENCY'
           },
           {
           header: '总计',
           width: 80,
           sortable: true,
           dataIndex: 'TOTAL'
           },
           <c:if test="${tradeType=='8'||tradeType=='12'}">
           {
           header: '成品单价',
           width: 100,
           sortable: true,
           dataIndex: 'COST_PRICE',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 1000000000
           })            
           },
           {
           header: '成品总计',
           width: 100,
           sortable: true,
           dataIndex: 'COST_TOTAL'
           },
		  </c:if>
           {
           header: '备注',
           width: 300,
           sortable: true,
           dataIndex: 'CMD',
           editor: new fm.TextField({
               allowBlank: true
           })           
    	},
    	{
    		header: '实际入库时间',
            width: 100,
            sortable: true,
            dataIndex: 'RECEIPTTIME',
            editor:new Ext.form.DateField({
           		format:'Y-m-d',
           		renderer: Ext.util.Format.dateRenderer('Y-m-d')
            })
        },
    	{
    		header: '预计付款日',
            width: 100,
            sortable: true,
            dataIndex: 'PAYABLEDATE', 
            editor:new Ext.form.DateField({
           		format:'Y-m-d'
            })
        },
    	{
    		header: '预计收票日',
            width: 100,
            sortable: true,
            dataIndex: 'RECEIVEBILLDATE',
            editor:new Ext.form.DateField({
           		format:'Y-m-d'
            })
        },
    	{
    		header: '预计销售发货日',
            width: 100,
            sortable: true,
            dataIndex: 'SENDGOODSDATE',
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
    
    receiptsDetailInfocm.defaultSortable = true;
    
    var receiptsDetailInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:receiptsDetailInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
	 /*
   	if (Enabled=='false')
		//进仓单明细列表 
		receiptsDetailInfogrid = new Ext.grid.GridPanel({
	    	id:'receiptsDetailInfoGrid',
	        ds: receiptsDetailInfods,
	        cm: receiptsDetailInfocm,
	        sm: receiptsDetailInfosm,
	        bbar: receiptsDetailInfobbar,
	        tbar: receiptsDetailInfotbar,
	        border:false,
	        loadMask:true,
	        autoScroll:true,
	        region:'center',
	        el: 'div_center',
	        autowidth:true,
	        layout:'fit',
	        clicksToEdit:1
	    });
	else
	*/
		receiptsDetailInfogrid = new Ext.grid.EditorGridPanel({
	    	id:'receiptsDetailInfoGrid',
	        ds: receiptsDetailInfods,
	        cm: receiptsDetailInfocm,
	        sm: receiptsDetailInfosm,
	        bbar: receiptsDetailInfobbar,
	        tbar: receiptsDetailInfotbar,
	        border:false,
	        loadMask:true,
	        autoScroll:true,
	        region:'center',
	        el: 'div_center',
	        autowidth:true,
	        layout:'fit',
	        plugins:checkColumn,
	        clicksToEdit:1
	    });
	
    receiptsDetailInfogrid.render();

    /*清帐上线前先关掉
    receiptsDetailInfogrid.addListener('rowdblclick',onRowDoubleClick);

    function onRowDoubleClick(grid, rowIndex, e){
    	//alert(grid.getStore().getAt(rowIndex).data.CURRENT_CLEAR_CHARGE);
    	
    	var receiptDetailId=grid.getStore().getAt(rowIndex).data.RECEIPT_DETAIL_ID;

    	top.ExtModalWindowUtil.show('未清付款',
    			'receiptsController.spr?action=receiptClearChargeView&receiptDetailId='+receiptDetailId,
    			'',
    			'',
    			{width:900,height:250});
    }*/
    
    receiptsDetailInfogrid.on("afteredit", afterEdit, receiptsDetailInfogrid);
    
    function afterEdit(obj){
        if( '${saveMaterial}'==''&&Enabled=='false') return ;
    	var row = obj.record;//获取被修改的行
        var colName = obj.field;//获取被修改的列
        var colValue = row.get(colName);
      //财务部只允许改采购单价和定价单位
        if ('${saveMaterial}'=='true'){
            if(colName!='PRICE'&&colName!='EKPO_PEINH') return ;
        }
        var receiptDetailId = row.get("RECEIPT_DETAIL_ID");
        var isRightBatchNo ='true';
        //部分冲销，数量填负值，取消校验批次
        var quantity = row.get("QUANTITY");
		//批号
        if (quantity>=0&&colName == 'BATCH_NO'){
	        if($('warehouse').value==''){
			   Ext.MessageBox.alert('提示', '请先选择仓库');
		       return;
			}
			
			var requestUrl ="receiptsController.spr?action=isRightBatchNo&" + Form.serialize('mainForm');
	        var materialCode = row.get("MATERIAL_CODE");
	        var batchNo = row.get("BATCH_NO");
			requestUrl += "&materialCode=" +　materialCode;
			requestUrl += "&batchNo=" +　batchNo;
			requestUrl += "&tradeType=" +　tradeType;
	
			Ext.Ajax.request({
				url: requestUrl,
				sync:true,
				success: function(response, options){
					var responseUtil1 = new AjaxResponseUtils(response.responseText);
					var customField = responseUtil1.getCustomField("coustom");   
					isRightBatchNo = customField.isRightBatchNo;
	  				if(isRightBatchNo=='false'){
	  				    Ext.MessageBox.alert('提示', '该仓库、物料下已存在相同批次号，请更改！');
	  				    row.set("BATCH_NO",'');
	  				    return ;
	  				}		
				},
				failure:function(response, options){
					//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
				}
			});	
        }
        //数量或单价
        if (colName == 'QUANTITY' || colName == 'PRICE'||colName=='EKPO_PEINH'){
        	setMaterielTotal(row);
//        	setContractPurchaseMoney();
        }
        
        //数量或单价
        if (colName == 'QUANTITY' || colName == 'COST_PRICE'||colName=='EKPO_PEINH'){
        	setMaterielCostTotal(row);
        }

        if (colName == 'RECEIPTTIME' || colName == 'PAYABLEDATE' || colName == 'RECEIVEBILLDATE' || colName == 'SENDGOODSDATE'){
        	colValue = Ext.util.Format.date(colValue, "Y-m-d");
            row.set(colName,colValue);
        }
        
        if(isRightBatchNo=='true'){
           updateMaterielValue(row,receiptDetailId,colName,colValue);
        }
    }

    //更新物料信息
    function updateMaterielValue(row,receiptDetailId,colName,colValue){
    	var requestUrl = 'receiptsController.spr?action=updateReceiptMateriel';
		requestUrl = requestUrl + '&receiptDetailId=' + receiptDetailId;
		requestUrl = requestUrl + '&colName=' + colName;
		requestUrl = requestUrl + '&colValue=' + colValue;

		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseUtil1 = new AjaxResponseUtils(response.responseText);
				var customField = responseUtil1.getCustomField("coustom");   
				var stateCode = customField.stateCode;
				var hasQuantity = customField.hasQuantity;				
				
				/*
				if (stateCode == '0'){
					if (colName == "QUANTITY"){
						top.ExtModalWindowUtil.alert('提示','输入的申请数量已超过合同物料数量！');
						row.set("QUANTITY",row.get("OLD_QUANTITY"));
					}
				}
				else {
				*/
					if (hasQuantity<0)
						top.ExtModalWindowUtil.alert('提示','该行物料的申请数量已超过合同物料数量！'+hasQuantity);
					if (colName == "QUANTITY" || colName == "PRICE" || colName == "PEINH" ){
						row.set("TOTAL",customField.total);
					}
					receiptsDetailInfods.commitChanges();
				//}
			},
			failure:function(response, options){
				/*
				if (colName == "QUANTITY"){
					top.ExtModalWindowUtil.alert('提示','输入的申请数量超过合同物料数量！');					
					row.set("QUANTITY",row.get("OLD_QUANTITY"));
				}
				*/
			}
		});
	}    
     
     //刷新库存数量
     function　queryInventoryNum(row){
		if ('${inventoryNum}'=='true')return;

		var requestUrl ="receiptsController.spr?action=queryInventoryNum&" + Form.serialize('mainForm');

        var materialCode = row.get("MATERIAL_CODE");
        var batchNo = row.get("BATCH_NO");

		requestUrl += "&materialCode=" +　materialCode;
		requestUrl += "&batchNo=" +　batchNo;
		requestUrl += "&tradeType=" +　tradeType;

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

     //设置金额
     function setMaterielCostTotal(row){   
        var receiptDetailId = row.get("RECEIPT_DETAIL_ID");
     	var price = row.get("COST_PRICE");
     	var quantity = row.get("QUANTITY"); 
     	var ekpoPeinh = row.get("EKPO_PEINH");   	
     	if (quantity == null || quantity == ''){
     		quantity = "0";
     	}
     	if (price == null || price == ''){
     		price = "0";
     	}
     	if(ekpoPeinh==null||ekpoPeinh==''){
     	    ekpoPeinh="1";
     	}
     	var total = parseFloat(quantity) *  parseFloat(price)/parseFloat(ekpoPeinh);

     	row.set("COST_TOTAL",Utils.roundoff(total,2));
     	updateMaterielValue(row,receiptDetailId,"COST_TOTAL",Utils.roundoff(total,2));
     }
     //设置金额
     function setMaterielTotal(row){   
        var receiptDetailId = row.get("RECEIPT_DETAIL_ID");
     	var price = row.get("PRICE");
     	var quantity = row.get("QUANTITY"); 
     	var ekpoPeinh = row.get("EKPO_PEINH");   	
     	if (quantity == null || quantity == ''){
     		quantity = "0";
     	}
     	if (price == null || price == ''){
     		price = "0";
     	}
     	if(ekpoPeinh==null||ekpoPeinh==''){
     	    ekpoPeinh="1";
     	}
     	var total = parseFloat(quantity) *  parseFloat(price)/parseFloat(ekpoPeinh);

     	row.set("TOTAL",Utils.roundoff(total,2));
     	updateMaterielValue(row,receiptDetailId,"TOTAL",Utils.roundoff(total,2));
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
	    
　   receiptsDetailInfods.load({params:{start:0, limit:10},arg:[]});
   
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			height:213,
			xtype:'tabpanel',
			id:'infotype',
			name:'infotype',
			plain:true,
            autoScroll:'true',
            activeTab: 0,			
            items:[{
            	title:'入库申请信息',
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
	            height:270,
	            autoScroll:'true',
	            activeTab: 0,
	            items:[{
			            	title:'物料信息',
			            	contentEl: '',
			            	id:'receiptsDetailInfo',
							name:'receiptsDetailInfo',
							autoScroll:'true',
			            	layout:'fit',
			            	items:[receiptsDetailInfogrid]
	                  },{contentEl:'rule',
			               id:'fileEl', 
			               title: '附件信息',
			               autoScroll:'true',
			               listeners:{activate:handlerActivate}
			            }
/*
	                  {
	                        title:'备注',
	                        id:'Memo',
			            	contentEl: 'div_Memo',
							autoScroll:'true',
			            	layout:'fit',
			            	disabled:true
			           },
*/			           
	                  ]
	             }],
	                  
				buttons:[{
	            	text:'保存',
	            	hidden:${save},
	            	handler:function(){
	            		strOperType='save';
						receiptsDetailInfogrid. getSelectionModel().selectAll();
						var recoder = receiptsDetailInfogrid.selModel.getSelections();
						var batchStr = '';
						for (var i=0; i< recoder.length; i++) {
							var batch_no = recoder[i].data.BATCH_NO;
							var code = recoder[i].data.MATERIAL_CODE;
							var str = batch_no+code;
							if(batch_no==null||batch_no==''){
							   top.ExtModalWindowUtil.alert('提示','请填写批次号！');
	            		       return;
	            			}
	            			if(batchStr.indexOf(str)>=0){
	            			   top.ExtModalWindowUtil.alert('提示','相同物料批次号不能重复');
	            		       return;
	            			}
	            			batchStr = batchStr +','+ str;
						};

	            		var param1 = Form.serialize('mainForm');
	            		
	            		if (Ext.getDom('contractNo').value=="")
	            		{
	            			top.ExtModalWindowUtil.alert('提示','请选择采购合同！');
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
            				Ext.MessageBox.alert('提示', '销货单位必填！');
	            			return;
	            		}
	            		/*if (Ext.getDom('sendGoodsDate').value=="")
	            		{
	            			top.ExtModalWindowUtil.alert('提示','预计销售发货日必填！');
	            			return;
	            		}*/	
	            		var param = param1 + "&action=updateReceipt";
						new AjaxEngine('receiptsController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});

					}
 
	            },
	            {
            		text:'提交',
	            	hidden:${submit},            	
            		handler:function(){
		            	var param1 = Form.serialize('mainForm');

            			if (Ext.getDom('contractNo').value=="")
            			{
            				top.ExtModalWindowUtil.alert('提示','无效的采购合同！');
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
            				Ext.MessageBox.alert('提示', '销货单位必填！');
	            			return;
	            		}
	            			            		
	           			if (receiptsDetailInfods.getCount() > 0){
							var param = param1  + "&action=submitAndSaveReceiptInfo";
							new AjaxEngine('receiptsController.spr', 
								   {method:"post", parameters: param, onComplete: callBackHandle});
								   
							strOperType = '3';
						
						} else{
							top.Ext.Msg.show({
								title:'提示',
			  					msg: '没有增加物料信息,不能提交审批！',
			  					buttons: {yes:'关闭'},
			  					fn: Ext.emptyFn,
			  					icon: Ext.MessageBox.INFO
								});
						}
            		}
	            },
	            {
	            	text:'关闭',
	            	hidden:${close},
	            	handler:closeForm
	            },
	            {
	            	text:'过帐',
	            	hidden:${!postDateEdit},            	
            		handler:function(){
            		   var postDate = $('postDate').value;
            		   top.Ext.Msg.show({
   								title:'提示',
   								msg: '是否确定过帐该进仓单（过帐时间：'+postDate+'）',
   								buttons: {yes:'是', no:'否'},
   								fn: function(buttonId,text){
   									callbackFlag = 'del';
 									if(buttonId=='yes'){
 	 									var parm ='?action=dealPost&receiptId=${main.receiptId}&postDate='+postDate; 
 										new AjaxEngine('receiptsController.spr',
 												 {method:"post", parameters: parm,onComplete: callBackHandle});
									}
   								},
   							    animEl: 'elId',
   								icon: Ext.MessageBox.QUESTION
							});
            		}
	            }
	            ,{
					text:'保存SAP物料凭证号',
					hidden:'${modifiable}'=='true',
					handler:function(){
						var param='?action=saveSAPReturnNO&receiptId=${main.receiptId}&sapReturnNO='+$('sapReturnNO').value+'&memo='+$('memo').value;
						new AjaxEngine('receiptsController.spr', 
								   {method:"post", parameters: param, onComplete: callBackHandle});

						}
		            },{
					text:'保存日期',
					hidden:${!saveDate},
					handler:function(){
						var param='?action=saveDate&receiptId=${main.receiptId}&receiptTime='+$('receiptTime').value+'&payAbleDate='+$('payAbleDate').value+'&receiveBillDate='+$('receiveBillDate').value+'&sendGoodsDate='+$('sendGoodsDate').value;
						new AjaxEngine('receiptsController.spr', 
								   {method:"post", parameters: param, onComplete: callBackHandle});

						}
		            },{
					text:'预确认',
					hidden:${fn:indexOf(taskName,"财务会计审核*")>=0?"false":fn:indexOf(taskName,"财务审核*")>=0?"false":"true"},
					handler:function(){
						var param='?action=preSeConfig&receiptId=${main.receiptId}';
						new AjaxEngine('receiptsController.spr', 
								   {method:"post", parameters: param, onComplete: callBackHandle});

						}
		            }]
		       }]
	});

	//应发货日
	var sendGoodsDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'sendGoodsDate',
	    name:'sendGoodsDate',
		width: 160,
	    readOnly:true,
		applyTo:'sendGoodsDate'
   	});


  //预计付款日
   	var payAbleTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'payAbleTime',
	    name:'payAbleTime',
		width: 160,
	    readOnly:true,
		applyTo:'payAbleDate'
   	});

  //预计收票日
   	var receiveBillTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'receiveBillTime',
	    name:'receiveBillTime',
		width: 160,
	    readOnly:true,
		applyTo:'receiveBillDate'
   	});
	
   	//申报日期
   	var applyTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'applyTime',
	    name:'applyTime',
		width: 160,
	    readOnly:true,
		applyTo:'applyTime'
   	});
   	//进仓日期
   	var receiptTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'receiptTime',
	    name:'receiptTime',
		width: 160,
	    readOnly:true,
		applyTo:'receiptTime'
   	});
   	//过帐日期
   	var postDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'postDate',
	    name:'postDate',
		width: 160,
	    readOnly:true,
		applyTo:'postDate'
   	});
   	postDate.disable();
   	<c:if test="${postDateEdit==true}">
    postDate.enable();
    </c:if>
   	$('warehouse').value='${main.warehouse}';
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

            var receiptDetailId = record.data["RECEIPT_DETAIL_ID"];



            var requestUrl = 'receiptsController.spr?action=updateReceiptMateriel';
    		requestUrl = requestUrl + '&receiptDetailId=' + receiptDetailId;
    		requestUrl = requestUrl + '&colName=IS_CLEAR';
    		requestUrl = requestUrl + '&colValue=' + read_text;

    		Ext.Ajax.request({
    			url: encodeURI(requestUrl),
    			success: function(response, options){					
    				receiptsDetailInfods.commitChanges();				     
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


//提交工作流审批
function submitWorkflow(){

	Ext.Msg.show({
   		title:'提示',
   		msg: '是否确定提交流程审批 ',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
   			
				new AjaxEngine('receiptsController.spr?action=submitWorkflow&doWorkflow=mainForm',
					 {method:"post", parameters: Form.serialize('mainForm'), onComplete: submitWorkflowCallBackHandle});
			
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

//提交审批流程回调函数
function submitWorkflowCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
//	showMsg(customField.returnMessage);
	setTimeout(function(){
		//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	},1000);	
}
function handlerActivate(tab){
	rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
}
</script>
</head>
<body>
<div id='div_progressBar'></div>
<div id="main" class="x-hide-display">
<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
	<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
      <tr>
        <td width="11%" align="right">采购合同号<font color="red">▲</font>：</td>
        <td width="22%" align="left">
			<input name="receiptId" type="hidden" size="40" value="${main.receiptId}"/>
		  	<input name="contractPurchaseId" type="hidden" value="${main.contractPurchaseId }"/>
		   	<input name="billState" type="hidden" value="${main.billState}"/>    
			<input name="tradeType" type="hidden" size="20" value="${main.tradeType}"/>
			<input name="deptId" type="hidden" size="20" value="${main.deptId}"/>
			<input name="isAvailable" type="hidden" size="40" value="${main.isAvailable}"/>
			<input name="creatorTime" type="hidden" size="40" value="${main.creatorTime}"/>
			<input name="creator" type="hidden" size="40" value="${main.creator}"/>
			<input name="seConfigTime" type="hidden" size="40" value="${main.seConfigTime}"/>
			<input name="isReturn" type="hidden" size="40" value="${main.isReturn}"/>
       	<input name="contractNo" readonly="readonly" type="text" size="12" tabindex="1" value="${main.contractNo}"/>
       	<input type="button" name="selectPurchase" value="..." onclick="selectPurchaseWin()"></input>
        <a href="#" onclick="Utils.showPurcharseContract('${main.contractPurchaseId}')">查看</a>
        </td>
        <td width="11%" align="right">序号：</td>
        <td width="22%" align="left">
        	<input name="serialNo" readonly="readonly" type="text" size="20" tabindex="1" value="${main.serialNo}" <c:if test="${not empty main.oldReceiptId}">style='background-color: red;'</c:if>/>
            <input name="receiptNo" type="hidden" value="${main.receiptNo}"/> 
        </td>
        <td width="11%" align="right">单据状态：</td>
        <td width="22%" align="left">
        	<div id="div_examineState"></div>
        </td>
      </tr>
      <c:choose>
      <c:when test="${not empty main.oldReceiptId}">
      <tr>
        <td width="11%" align="right"></td>
        <td width="22%" align="left"><input name="oldReceiptId" type="hidden" size="40" value="${main.oldReceiptId}"/>
        </td>
        <td width="11%" align="right">原进仓单号：</td>
        <td width="22%" align="left">
			<input type="text" name="oldReceiptNo" id="oldReceiptNo" size="20" value="${main.oldReceiptNo}" readonly="readonly"/>
        </td>
        <td width="11%" align="right">原物料凭证号</td>
        <td width="22%" align="left">
        	<input type="text" name="oldSapReturnNo" id="oldSapReturnNo" size="20" value="${main.oldSapReturnNo}" readonly="readonly"/>
        </td>
      </tr>
      </c:when>
      <c:otherwise>
       <input name="oldReceiptId" type="hidden" value="${main.oldReceiptId}"/>
	   <input name="oldReceiptNo" type="hidden" value="${main.oldReceiptNo}"/>
	   <input name="oldSapReturnNo" type="hidden" value="${main.oldSapReturnNo}"/>
      </c:otherwise>
      </c:choose>
      <tr>
        <td width="11%" align="right">纸质合同号<font color="red">▲</font>：</td>
        <td width="22%" align="left">
        	<input type="text" name="contractPaperNo" id="contractPaperNo" value="${main.contractPaperNo}"/>
        </td>
        <td width="11%" align="right">销货单位<font color="red">▲</font>：</td>
        <td width="22%" align="left">
			<input type="text" name="unitName" id="unitName" size="20" value="${main.unitName}"/>
        </td>
        <td width="11%" align="right">报关单号</td>
        <td width="22%" align="left">
        	<input type="text" name="declarationsNo" id="declarationsNo" size="20" value="${main.declarationsNo}"/>
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">立项号：</td>
        <td width="22%" align="left">
        	<input name="projectNo" readonly="readonly" type="text" size="20" tabindex="1" value="${main.projectNo}"/>
        </td>
        <td width="11%" align="right">立项名称：</td>
        <td width="22%" align="left">
			<input name="projectName" readonly="readonly" size="20" value="${main.projectName}"/>
        </td>
        <td width="11%" align="right">SAP订单号：</td>
        <td width="22%" align="left">
        	<input name="sapOrderNo" readonly="readonly" type="text" size="20" tabindex="1" value="${main.sapOrderNo}"/>
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">合同组编号：</td>
        <td width="22%" align="left">
        	<input name="contractGroupNo" readonly="readonly" type="text" size="20" tabindex="1" value="${main.contractGroupNo}"/>
        </td>
        <td  align="right">仓库<font color="red">▲</font>：</td>
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
        	<input id="warehouseAdd" name="warehouseAdd" type="text" value="${main.warehouseAdd}"  />        	
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">申报日期：</td>
        <td width="22%" align="left">
			<input id="applyTime" name="applyTime" type="text"  value="${main.applyTime}"/>        
        </td>
        <td width="11%" align="right">过帐日期：</td>
        <td width="22%" align="left">
            <c:if test="${postDateEdit==true}">
            <input id="postDate" name="postDate" readonly="readonly" type="text"  value="${paseDate}"/>
            </c:if>
            <c:if test="${postDateEdit!=true}">
            <input id="postDate" name="postDate" readonly="readonly" type="text"  value="${main.postDate}"/>
            </c:if>
			<input id="approvedTime" name="approvedTime" readonly="readonly" type="hidden"  value="${main.approvedTime}"/>        
        </td>
        <td width="11%" align="right">SAP物料凭证号</td>
        <td width="22%" align="left"><input name="sapReturnNo" type="text" size="20" value="${main.sapReturnNo}" <c:if test="${save!=true}">readonly="readonly"</c:if>/>
        </td>
        
      </tr>
       <tr>
      	<td align="right">
      		业务类型：
      	</td>
      	<td align="left">
      		<input type="text" value="${tradeTypeName}" readonly="readonly"/> 
      	</td>
      	<td width="11%" align="right">实际入库时间<font color="red">▲</font>：</td>
        <td width="22%" align="left">
			<input id="receiptTime" name="receiptTime" readonly="readonly" type="text"  value="${main.receiptTime}"/>        
        </td>
        <td colspan="2"><font color="red">如进仓物料为石材，请在备注栏填写石材块数</font></td>
      </tr>
      
      <tr>
      	<td align="right">
      		预计付款日<font color="red">▲</font>：
      	</td>
      	<td align="left">
      		<input type="text" id="payAbleDate" name="payAbleDate" value="${main.payAbleDate}" /> 
      	</td>
      	
      	<td align="right">
      		预计收票日<font color="red">▲</font>：
      	</td>
      	<td align="left">
      		<input type="text" id="receiveBillDate" name="receiveBillDate" value="${main.receiveBillDate}" /> 
      	</td>
      	
      	<td align="right">
      		预计销售发货日<font color="red">▲</font>：
      	</td>
      	<td align="left">
      		<input type="text" id="sendGoodsDate" name="sendGoodsDate" value="${main.sendGoodsDate}" />
      	</td>
      </tr>
      
      <tr>
        <td align="right">备注：</td>
        <td colspan="5">
        	 <textarea cols="80" rows="1" id="memo" name="memo" style="width:95%;overflow-y:visible;word-break:break-all;">${main.memo}</textarea>
        </td>
    </tr>
	</table>
	
</form>
</div>

<div id="div_center"></div>

<div id="div_Memo">
<table width="100%">
   
</table>
</div>
</div>

<div id="div_history" class="x-hide-display" ></div>
<div id="rule" class="x-hide-display" ></div>
</body>
</html>
<fiscxd:fileUpload divId="rule" recordId="${main.receiptId}" erasable="${!save}"  increasable="${!save}" 
 resourceId="2222" resourceName="33333" recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>
<fiscxd:workflow-taskHistory divId="div_history" businessRecordId="${main.receiptId}" width="750" url="shipController.spr?action=queryTaskHis&businessid=${main.receiptId}"></fiscxd:workflow-taskHistory>
<fiscxd:dictionary divId="div_examineState" fieldName="examineState" dictionaryName="BM_EXAMINE_STATE" width="150" selectedValue="${main.examineState}" disable="true" ></fiscxd:dictionary>    
