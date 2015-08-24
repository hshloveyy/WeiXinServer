<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ taglib prefix="ffcs" tagdir="/WEB-INF/tags/infolion"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>开票申请单登记表(增值税)</title>
		
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
.copy{
	background-image:url(<%= request.getContextPath()%>/images/fam/application_go.png) !important;
}
</style>

<script type="text/javascript">
//var from = '<%=request.getAttribute("popfrom")%>';
//贸易类型
var tradeType ='${tradeType}';
var isChanged=false;
//贸易名称
var strbillApplyTitle ='';
var strOperType='';
var Enabled='false';
if ('${submit}'!='true')Enabled='submit';
if ('${save}'!='true')Enabled='save';

//取贸易名称

strbillApplyTitle = Utils.getTradeTypeTitle(tradeType);

function selectCustomerWin(){
	if (Enabled=='false')return;
	top.ExtModalWindowUtil.show('查找客户',
	'masterQueryController.spr?action=toFindCustomer',
	'',
	customerCallback,
	{width:755,height:410});
}
function customerCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('billToParty').value=cb.KUNNR;
	Ext.getDom('billToPartyName').value=cb.NAME1;
}

function selectShipInfoWin(){
	if (Enabled=='false')return;
	top.ExtModalWindowUtil.show('查询出仓单信息',
	'shipQueryController.spr?action=findShipInfo' +
	'&tradeType=' + tradeType +'&examineState=3&deptid=' +
	'&customer=' + Ext.getDom('billToParty').value,
	'',
	selectShipInfoCallBack,
	{width:900,height:450});
}
function selectShipInfoCallBack(){
	var records = top.ExtModalWindowUtil.getReturnValue();
	var returnvalue = records[0].data;
	var shipNo = '';
	var contractNo = '';
	var sapOrderNo = '';
	var sapReturnNo = '';
	var paperContractNo = '';//VBKD_IHREZ
	var i, num=records.length;

	for (i=0;i<num;i++)
	{
		shipNo += records[i].data.SHIP_NO;
		if(i < num - 1 ) shipNo += ',';
		
		var shipsNo = ","+shipNo+","; 
		var shipId = ","+records[i].data.SHIP_NO+","
		if (shipsNo.indexOf(shipId)<0)
		{
			shipNo += records[i].data.SHIP_NO;
			if(i < num - 1 ) shipNo += ',';
		}
		
		var sapOrdersNo = ","+sapOrderNo+","; 
		var sapNo = ","+records[i].data.SAP_ORDER_NO+","
		if (sapOrdersNo.indexOf(sapNo)<0)
		{
			sapOrderNo += records[i].data.SAP_ORDER_NO;
			if(i < num - 1 ) sapOrderNo += ',';
		}

		var salesNo = ","+contractNo+","; 
		var saleNo = ","+records[i].data.SALES_NO+","
		if (salesNo.indexOf(saleNo)<0)
		{
			contractNo += records[i].data.SALES_NO;
			if(i < num - 1 ) contractNo += ',';
		}

		sapReturnsNo = ","+sapReturnNo+","; 
		var sapNo = ","+records[i].data.SAP_RETURN_NO+","
		if (sapReturnsNo.indexOf(sapNo)<0)
		{
			sapReturnNo += records[i].data.SAP_RETURN_NO;
			if(i < num - 1 ) sapReturnNo += ',';
		}
		//纸质合同号
		var pNo =records[i].data.VBKD_IHREZ;
		if (paperContractNo.indexOf(pNo)<0)
		{
			paperContractNo =paperContractNo + records[i].data.VBKD_IHREZ;
			if(i < num - 1 ) paperContractNo += ',';
		}
	}
	Ext.getDom('exportApplyNo').value = shipNo;
	Ext.getDom('contractSalesNo').value = contractNo;
	Ext.getDom('sapOrderNo').value = sapOrderNo;
	Ext.getDom('sapReturnNo').value = sapReturnNo;
	Ext.getDom('paperContractNo').value = paperContractNo;

	Ext.getDom('contractSalesId').value = returnvalue.CONTRACT_SALES_ID;
	Ext.getDom('billToParty').value = returnvalue.BILL_TO_PARTY;
	Ext.getDom('billToPartyName').value = returnvalue.BILL_TO_PARTY_NAME;
	
	var requesturl = 'billApplyController.spr?action=initBillApplyMt&billApplyId='+'${main.billApplyId}';
	requesturl = requesturl + "&shipNo=" + shipNo;
	requesturl = requesturl + "&sapOrderNo=" + sapOrderNo;
	requesturl = requesturl + "&receiptTime=" + Ext.getDom('receiptTime').value;
	
	Ext.Ajax.request({
		url: encodeURI(requesturl),
		success: function(response, options){
			var responseUtil1 = new AjaxResponseUtils(response.responseText);
			var customField = responseUtil1.getCustomField("coustom");
			Ext.getDom('taxTotal').value = customField.taxTotal;
			Ext.getDom('priceTotal').value = customField.priceTotal;
			Ext.getDom('quantityTotal').value = customField.quantityTotal;
			Ext.getDom('loanTotal').value = customField.loanTotal;
			Ext.getDom('sapOrderNo').value = customField.sapOrderNo;
			Ext.getDom('sapReturnNo').value = customField.sapReturnNo;
			Ext.getDom('exportApplyNo').value = customField.exportApplyNo;
			
  			billApplyDetailInfods.load({params:{start:0, limit:10},arg:[]});
		},
		failure:function(response, options){
			top.ExtModalWindowUtil.alert('提示','与服务器交付失败请重新再试！');
		}
	});
}

//新增保存删除提示
function customCallBackHandle(transport)
{
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var sReturnMessage=customField.returnMessage;
	
	top.ExtModalWindowUtil.alert('提示',sReturnMessage);

	billApplyDetailInfods.reload();
	//billApplyDetailInfods.commitChanges();
	//updateTotal();
	
	if ('${close}'=='false' && (sReturnMessage.indexOf("提交成功")>-1))
	{
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	}
}

var billApplyDetailInfogrid;		//开票申请单明细列表
var billApplyDetailInfods;

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';
	
	var taskType = '${taskType}';
	if (taskType=='1')
		Ext.getDom("sapBillNo").readOnly = false;  

    var fm = Ext.form;
    
    //增加开票申请单明细资料(物料)的回调函数
    function funAddBillApplyDetailCallBack(){
	 	var returnValue = top.ExtModalWindowUtil.getReturnValue();   
    	var requestUrl = "billApplyController.spr?action=addDetail";

    	requestUrl += "&salesRowsId=" + returnValue.SALES_ROWS_ID;
    	requestUrl += "&billApplyId=" + "${main.billApplyId}";
    	   
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				billApplyDetailInfods.reload();
				billApplyDetailInfods.commitChanges();
				// updateTotal();				
			},
			failure:function(response, options){
				Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});
    }  

    //增加开票申请单明细资料(物料)
    var addBillApplyDetailInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
	    disabled:${save},
		handler:function(){
			var contractSalesNo = Ext.getDom('contractSalesNo').value;
			if (contractSalesNo == ''){
				top.ExtModalWindowUtil.alert('提示','请先选择销售合同再增加物料信息！');
				return;				
			}
			top.ExtModalWindowUtil.show(strbillApplyTitle+'开票申请单登记表',
			'salesMaterialQueryController.spr?action=findSalesMaterialByContractNo&contractSalesNo='+contractSalesNo,
			'',
			funAddBillApplyDetailCallBack,
			{width:800,height:500});
			isChanged=true;
		}
   	});
   	
   	//删除
   	var deleteBillApplyDetailInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
	    disabled:${save},
		handler:function(){
			if (billApplyDetailInfogrid.selModel.hasSelection()){
				var records = billApplyDetailInfogrid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个开票申请单明细信息！');
				}else{
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=deleteDetail";
								param = param + "&exportMateId=" + records[0].data.EXPORT_MATE_ID;

								new AjaxEngine('billApplyController.spr', 
						   			{method:"post", parameters: param, onComplete: callBackHandle});
						   			
								isChanged=true;
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的开票申请单明细信息！');
			}
		}
   	});
   	
   	//复制
   	var copyBillApplyDetailInfo = new Ext.Toolbar.Button({
   		text:'复制',
	    iconCls:'copy',
	    disabled:${save},
		handler:function(){
			if (billApplyDetailInfogrid.selModel.hasSelection()){
				var records = billApplyDetailInfogrid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能复制一个开票申请单明细信息！');
				}else{
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定复制记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=copyDetail";
								param = param + "&exportMateId=" + records[0].data.EXPORT_MATE_ID;

								new AjaxEngine('billApplyController.spr', 
						   			{method:"post", parameters: param, onComplete: callBackHandle});
						   			
								isChanged=true;
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要复制的开票申请单明细信息！');
			}
		}
   	});
   	
   	//工具栏---添加、删除按钮。
    var billApplyDetailInfotbar = new Ext.Toolbar({
		items:[addBillApplyDetailInfo,'-',copyBillApplyDetailInfo,'-',deleteBillApplyDetailInfo]
	  });

	var billApplyDetailInfoPlant = Ext.data.Record.create([
		{name:'EXPORT_MATE_ID'},  				//明细ID
		{name:'BILL_APPLY_ID'},  				//开票申请ID
		{name:'MATERIAL_CODE'},  				//物料编码
		{name:'MATERIAL_NAME'},  				//物料名称
		{name:'MATERIAL_UNIT'},      			//物料单位
		{name:'QUANTITY'},
		{name:'LOAN_MONEY'},
		{name:'TAX'},
		{name:'TAX_RATE'},
		{name:'CURRENCY'},                 
		{name:'TOTAL_MONEY'},     
		{name:'CONTRACT_NO'},   
		{name:'CONTRACT_SALES_ID'},              
		{name:'PRICE'},
		{name:'CMD'},
		{name:'RECEIPTTIME'}
	]);

	billApplyDetailInfods = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({url:'billApplyController.spr?action=queryMaterial&billApplyId=' + '${main.billApplyId}'}),
        reader: new Ext.data.JsonReader({
        root: 'root',
        totalProperty: 'totalProperty'
        },billApplyDetailInfoPlant)
    });
        
    var billApplyDetailInfosm = new Ext.grid.CheckboxSelectionModel();

    var billApplyDetailInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		billApplyDetailInfosm,
           {header: '物料编码',
           width: 60,
           sortable: true,
           dataIndex: 'MATERIAL_CODE'
           },
			{header: '物料名称',
			width: 150,
			sortable: true,
			dataIndex: 'MATERIAL_NAME'
           },
			{header: '合同编码',
			width: 80,
			sortable: true,
			dataIndex: 'CONTRACT_NO'
           },
			{header: '单位',
			width: 40,
			sortable: false,
			dataIndex: 'MATERIAL_UNIT',
			editor: new fm.TextField({
               allowBlank: true
           	}) 
           }, 
           {header: '数量<font color="red">▲</font>',
           width: 90,
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
           header: '货款金额',
           width: 100,
           sortable: true,
           dataIndex: 'LOAN_MONEY'          
           },
           {
           header: '税金',
           width: 80,
           sortable: true,
           dataIndex: 'TAX'        
           },
           {
           header: '税率%<font color="red">▲</font>',
           width: 80,
           sortable: true,
           dataIndex: 'TAX_RATE',  
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 200
           })         
           },
		   {header: '币别',
           width: 120,
           sortable: true,
           dataIndex: 'CURRENCY',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'currency',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })           
           },
           {
           header: '价税总计<font color="red">▲</font>',
           width: 100,
           sortable: true,
           dataIndex: 'TOTAL_MONEY',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 1000000000
           })
           },
           {
           header: '含税单价',
           width: 90,
           sortable: true,
           dataIndex: 'PRICE'     
           },
           {
           header: '备注',
           width: 300,
           sortable: true,
           hidden:true,
           dataIndex: 'CMD',
           editor: new fm.TextField({
               allowBlank: true
           })           
    	},{
    		header: '应收款日',
            width: 300,
            sortable: true,
            dataIndex: 'RECEIPTTIME',
            editor:new Ext.form.DateField({
           		format:'Y-m-d'
            })
    	}
    ]);

    billApplyDetailInfocm.defaultSortable = true;
    
    var billApplyDetailInfobbar = new Ext.PagingToolbar({
        pageSize: 50,
        store:billApplyDetailInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });

	billApplyDetailInfogrid = new Ext.grid.EditorGridPanel({
    	id:'billApplyDetailInfoGrid',
        ds: billApplyDetailInfods,
        cm: billApplyDetailInfocm,
        sm: billApplyDetailInfosm,
        bbar: billApplyDetailInfobbar,
        tbar: billApplyDetailInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit',
        clicksToEdit:1
    });
	
    billApplyDetailInfogrid.render();
    
    billApplyDetailInfogrid.on("afteredit", afterEdit, billApplyDetailInfogrid);

    function afterEdit(obj){
    	isChanged=true;
        if( Enabled=='false') return;
    	var row = obj.record;//获取被修改的行
        var colName = obj.field;//获取被修改的列
        var colValue = row.get(colName);
        var exportMateId = row.get("EXPORT_MATE_ID");
        
        //数量或单价
        if (colName == 'TAX_RATE' || colName == 'QUANTITY' || colName == 'TOTAL_MONEY'){
        	setMaterielPrice(row);
        }

        updateMaterielValue(row,exportMateId,colName,colValue);
        billApplyDetailInfods.commitChanges();
        updateTotal();
    }
	
    //更新物料信息
    function updateMaterielValue(row,exportMateId,colName,colValue){
    	var requestUrl = 'billApplyController.spr?action=updateBillApplyMateriel';
		requestUrl = requestUrl + '&exportMateId=' + exportMateId;
		requestUrl = requestUrl + '&colName=' + colName;
		requestUrl = requestUrl + '&colValue=' + colValue;

		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseUtil1 = new AjaxResponseUtils(response.responseText);
				var customField = responseUtil1.getCustomField("coustom");   
				var stateCode = customField.stateCode;
				billApplyDetailInfods.commitChanges();
			},
			failure:function(response, options){
				Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});
	}    
     
     //设置单价
     function setMaterielPrice(row){   
        var exportMateId = row.get("EXPORT_MATE_ID");
     	var total = row.get("TOTAL_MONEY");
     	var quantity = row.get("QUANTITY");
     	var rate = row.get("TAX_RATE");  
     	if (quantity == null || quantity == ''){
     		quantity = "1";
     	}
     	if (total == null || total == ''){
     		total = "0";
     	}
     	if (rate == null || rate == ''){
     		rate = "0";
     	}
		var tax = parseFloat(total) * rate / (100+parseFloat(rate));

     	var price = 0;
     	if (parseFloat(quantity)!=0){
     		price = parseFloat(total) / parseFloat(quantity);
     	}
     	//var loanMoney =parseFloat(total)-tax;
     	var loanMoney=0;
     	row.set("TAX",formatnumber(tax,2));
     	updateMaterielValue(row,exportMateId,"TAX",formatnumber(tax,2));
     	
     	row.set("PRICE",formatnumber(price,6));
     	updateMaterielValue(row,exportMateId,"PRICE",formatnumber(price,6));
     	
     	//row.set("LOAN_MONEY",formatnumber(loanMoney,2));
     	loanMoney=parseFloat(total)-parseFloat(formatnumber(tax,2));
     	row.set("LOAN_MONEY",loanMoney);
     	updateMaterielValue(row,exportMateId,"LOAN_MONEY",loanMoney);
     }
	    
　   billApplyDetailInfods.load({params:{start:0, limit:10},arg:[]});

    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			height:215,
			xtype:'tabpanel',
			id:'infotype',
			name:'infotype',
			plain:true,
            autoScroll:'true',
            activeTab: 0,			
            items:[{
            	title:'开票申请信息(增值税)',
            	contentEl: 'div_main',
            	id:'maininfo',
				name:'maininfo',
				autoScroll:'true',
            	layout:'fit'
            },{contentEl:'rule',
               id:'fileEl', 
               title: '附件信息',
               autoScroll:'true',
               listeners:{activate:handlerActivate}
            }
            ,{
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
			            	id:'billApplyDetailInfo',
							name:'billApplyDetailInfo',
							autoScroll:'true',
			            	layout:'fit',
			            	items:[billApplyDetailInfogrid]
	                  }]
/*
	                  {
	                        title:'备注',
	                        id:'Memo',
			            	contentEl: 'div_Memo',
							autoScroll:'true',
			            	layout:'fit',
			            	disabled:true
			           }
*/			           
	             }],
	                  
				buttons:[{
	            	text:'保存',
	            	hidden:${save},
	            	handler:function(){
						billApplyDetailInfogrid. getSelectionModel().selectAll();
						var recoder = billApplyDetailInfogrid.selModel.getSelections();
						for (var i=0; i< recoder.length; i++) {
							/*
							var batch_no = recoder[i].data.BATCH_NO;
							if(batch_no==null||batch_no==''){
							   top.ExtModalWindowUtil.alert('提示','请填写批次号！');
	            		       return;
	            			}
	            			*/
						};

	            		var param1 = Form.serialize('mainForm');
	            		
	            		if (checkValues()==false)return;
	            		
	            		var param = param1 + "&action=updateBillApply";
						new AjaxEngine('billApplyController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
						isChanged=false;
						updateTotal();
					}

	            },{
					text:'保存SAP开票凭证和金税票号',
					hidden:${!modifiable},
					handler:function(){
						var param='?action=saveSapInfo&billApplyId=${main.billApplyId}&sapBillNo='+$('sapBillNo').value+'&taxNo='+$('taxNo').value;
						new AjaxEngine('billApplyController.spr', 
								   {method:"post", parameters: param, onComplete: callBackHandle});
						}
		            },
		            {
					text:'打印',
					hidden:${!modifiable},
					handler:function(){
						window.open('billApplyController.spr?action=dealPrint&billApplyId=${main.billApplyId}','_blank','');
						}
		            },
	            {
            		text:'提交',
	            	hidden:${submit},            	
            		handler:function(){
		            	var param1 = Form.serialize('mainForm');

            			if (checkValues()==false)return;
	            			            		
	           			if (billApplyDetailInfods.getCount() > 0){
							var param = param1  + "&action=submitAndSaveBillApply";
							new AjaxEngine('billApplyController.spr', 
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
	            }]
		       }]
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
   	//开票日期
   	var applyTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'billTime',
	    name:'billTime',
		width: 160,
		applyTo:'billTime'
   	});  	
   	var receiptTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'receiptTime',
	    name:'receiptTime',
		width: 160,
		applyTo:'receiptTime'
   	}); 
   	   	
   	var billType_combo = new Ext.form.ComboBox({
        typeAhead: true,
        triggerAction: 'all',
        transform:'billType',
        width:150,
        disabled:false, 
        allowBlank:false,
        blankText:'请选择',
        forceSelection:true
     });
     billType_combo.setValue('${main.billType}'); 

 	if(${modifiable}){
 	     $('sapBillNo').readOnly=false;
 	     $('taxNo').readOnly=false;
 	};
 	//---------------------------------------
	new Ext.ToolTip({
        target:'sapOrderNo',
        width:200,
        html: $('sapOrderNo').value==''?'':$('sapOrderNo').value,
        trackMouse:true
    });
	new Ext.ToolTip({
        target:'sapReturnNo',
        width:200,
        html: $('sapReturnNo').value==''?'':$('sapReturnNo').value,
        trackMouse:true
    });
 	 
});//end of Ext.onReady   

// 格式化数字
function formatnumber(value,num) {
/*	var a,b,c,i
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
	*/
	var value1 = value+0;
	var a = Math.round (value1*Math.pow(10,num))/Math.pow(10,num); 
	return a;
	
}

// 更新总计
function updateTotal()
{
	billApplyDetailInfods.commitChanges();
    var count = billApplyDetailInfods.getCount();
    var records = billApplyDetailInfods.getRange(0,count-1);
    var taxTotal = 0;
    var quantityTotal = 0;
    var priceTotal = 0;
    var loanTotal = 0;
    var i;
    
    for (i=0;i<count;i++)
    {	
    	var tax=parseFloat(records[i].data.TAX)
    	if (!isNaN(tax))taxTotal += tax;
    	var quantity=parseFloat(records[i].data.QUANTITY)
    	if (!isNaN(quantity))quantityTotal += parseFloat(records[i].data.QUANTITY);
    	var price=parseFloat(records[i].data.TOTAL_MONEY)
    	if (!isNaN(price))priceTotal += parseFloat(records[i].data.TOTAL_MONEY);
    	var loan=parseFloat(records[i].data.LOAN_MONEY)
    	if (!isNaN(loan))loanTotal += parseFloat(records[i].data.LOAN_MONEY);
    }
	Ext.getDom('taxTotal').value = formatnumber(taxTotal,2);
	Ext.getDom('priceTotal').value = formatnumber(priceTotal,2);
	Ext.getDom('quantityTotal').value = formatnumber(quantityTotal,3);
	Ext.getDom('loanTotal').value = formatnumber(loanTotal,2);  
}

function checkValues()
{
     if (Ext.getDom('exportApplyNo').value=="")
     {
     	top.ExtModalWindowUtil.alert('提示','请先选择出仓单！');
     	return false;
     }
     if (Ext.getDom('billType').value=="")
     {
     	top.ExtModalWindowUtil.alert('提示','请先选择票据类型！');
     	return false;
     }
	 if (Ext.getDom('billToParty').value=="")
     {
     	top.ExtModalWindowUtil.alert('提示','客户编码不能为空！');
     	return false;
     }
     if (Ext.getDom('billTime').value=="")
     {
     	top.ExtModalWindowUtil.alert('提示','开票日期不能为空！');
     	return false;
     }    
     if (Ext.getDom('receiptTime').value=="")
     {
     	top.ExtModalWindowUtil.alert('提示','应收款日不能为空！');
     	return false;
     }     
     return true;
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
   			
				new AjaxEngine('billApplyController.spr?action=submitWorkflow&doWorkflow=mainForm',
					 {method:"post", parameters: Form.serialize('mainForm'), onComplete: submitWorkflowCallBackHandle});
			
   			}
   		},
   		icon: Ext.MessageBox.QUESTION
	});
}

//关闭窗体
function closeForm(){
  		if(isChanged){
	   	   	top.Ext.Msg.alert('提示','请先保存!');
			return;
  		}
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
	updateTotal();
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
					<table width="100%" border="1" cellpadding="4" cellspacing="1"
						bordercolor="#6699FF" class="datatable">
						<tr>
							<td width="11%" align="right">
								客户编码：
							</td>
							<td width="22%" align="left">
								<input id="billToParty" name="billToParty" readonly="readonly"
									type="text" size="20" tabindex="1" value="${main.billToParty}">
								<input id="selectCustomer" type="button" name="selectCustomer"
									value="..." onclick="selectCustomerWin()">
								</input>
								<input id="tradeType" name="tradeType" type="hidden" size="20"
									value="${main.tradeType}" />
							</td>
							<td width="11%" align="right">
								开票申请单号：
							</td>
							<td width="22%" align="left">
								<div id="div_billType">
									<input id="billApplyNo" name="billApplyNo" type="text"
										size="20" tabindex="1" value="${main.billApplyNo}" readonly />
								</div>
								<input id="deptId" name="deptId" type="hidden" size="20"
									value="${main.deptId}" />
								<input id="isAvailable" name="isAvailable" type="hidden"
									size="40" value="${main.isAvailable}" />
								<input id="creatorTime" name="creatorTime" type="hidden"
									size="40" value="${main.creatorTime}" />
								<input name="creator" type="hidden" size="40"
									value="${main.creator}" />
							</td>
							<td width="11%" align="right">
								部门：
							</td>
							<td width="22%" align="left">
								<input id="deptname" type="text" id="deptname" name="deptname"
									readonly="readonly" size="20" value="${sysDept.deptname}">
							</td>
						</tr>
						<tr>
							<td width="11%" align="right">
								出仓单号码<font color="red">▲</font>
							</td>
							<td width="22%" align="left">
								<input id="billApplyId" name="billApplyId" type="hidden"
									size="40" value="${main.billApplyId}" />
								<input id="exportApplyNo" name="exportApplyNo"
									readonly="readonly" type="text" size="20" tabindex="1"
									value="${main.exportApplyNo}" />
								<input type="button" id="selectShipInfo" name="selectShipInfo"
									value="..." onclick="selectShipInfoWin()">
							</td>
							<td width="11%" align="right">
								客户名称：
							</td>
							<td width="22%" align="left">
								<input id="billToPartyName" name="billToPartyName"
									readonly="readonly" type="text" size="20" tabindex="1"
									value="${main.billToPartyName}">
							</td>
							<td width="11%" align="right">
								客户税务登记号：
							</td>
							<td width="22%" align="left">
								<input id="billToPartyNo" type="text" id="billToPartyNo" name="billToPartyNo"
									size="20" value="${main.billToPartyNo}">
							    <input type="hidden" name="examineState" value="${main.examineState}"/>
							</td>
						</tr>
						<tr>
							<td width="11%" align="right">
								销售合同号：
							</td>
							<td width="22%" align="left">
								<input id="contractSalesId" name="contractSalesId" type="hidden"
									value="${main.contractSalesId }" />
								<input id="contractSalesNo" name="contractSalesNo"
									readonly="readonly" type="text" size="20" tabindex="1"
									value="${main.contractSalesNo}" />
							</td>
							<td width="11%" align="right">
								票据类型<font color="red">▲</font>
							</td>
							<td width="22%" align="left">
								<select id="billType" name="billType">
									<option value="">
										请选择
									</option>
									<option value="1">
										增值税发票
									</option>
									<option value="2">
										服务性发票
									</option>
									<option value="3">
										普通发票
									</option>
								</select>
							</td>
							<td width="11%" align="right">
								申报日期：
							</td>
							<td width="22%" align="left">
								<input id="applyTime" name="applyTime" type="text"
									value="${main.applyTime}">
							</td>
						</tr>
						<tr>
							<td width="11%" align="right">
								SAP开票凭证：
							</td>
							<td width="22%" align="left">
								<input id="sapBillNo" name="sapBillNo" type="text"
									readonly="readonly" size="20" tabindex="1"
									value="${main.sapBillNo}">
							</td>
							<td align="right">
								金税票号：
							</td>
							<td align="left">
								<input id="taxNo" name="taxNo" type="text" size="20"
									tabindex="1" value="${main.taxNo}">
							</td>
							<td align="right">
								开票日期<font color="red">▲</font>
							</td>
							<td align="left">
								<input id="billTime" name="billTime" type="text" size="20"
									tabindex="1" value="${main.billTime}">
							</td>
						</tr>
						<tr>
							<td width="11%" align="right">
								SAP销售订单号：
							</td>
							<td width="22%" align="left">
								<input id="sapOrderNo" name="sapOrderNo" readonly="readonly"
									type="text" value="${main.sapOrderNo}" />
							</td>
							<td width="11%" align="right">
								数量合计：
							</td>
							<td width="22%" align="left">
								<input id="quantityTotal" readonly="readonly"
									name="quantityTotal" type="text" value="${main.quantityTotal}">
							</td>
							<td width="11%" align="right">
								货款合计：
							</td>
							<td width="22%" align="left">
								<input id="loanTotal" readonly="readonly" name="loanTotal"
									type="text" value="${loanTotal}">
							</td>
						</tr>
						<tr>
							<td align="right">
								SAP交货单号：
							</td>
							<td>
								<input id="sapReturnNo" name="sapReturnNo" readonly="readonly"
									type="text" value="${main.sapReturnNo}" />
							</td>
							<td align="right">
								税金合计：
							</td>
							<td>
								<input id="taxTotal" name="taxTotal" readonly="readonly"
									type="text" value="${taxTotal}">
							</td>
							<td align="right">
								价税合计：
							</td>
							<td>
								<input id="priceTotal" name="priceTotal" type="text" size="20"
									readonly="readonly" value="${priceTotal}">
							</td>
						</tr>
						<tr>
							<td align="right">纸质合同号:</td>
							<td> <input id="paperContractNo" name="paperContractNo" type="text" value="${main.paperContractNo}"/></td>
							<td align="right">
								应收款日：<font color="red">▲</font>
							</td>
							<td align="left">
								<input id="receiptTime" name="receiptTime" type="text" size="20"
									tabindex="1" value="${main.receiptTime}">
							</td>
							<td align="right">
							备注：
						    </td>
						    <td>
							   <textarea cols="30" rows="1" id="cmd" name="cmd" style="width:95%;overflow-y:visible;word-break:break-all;">${main.cmd}</textarea>
						    </td>
						</tr>
						
					</table>

				</form>
			</div>

			<div id="div_center"></div>

			<div id="div_Memo">
				<table width="100%">
					<tr>
						
					</tr>
				</table>
			</div>
<select name="currency" id="currency" style="display: none;">
		<option value="">请选择</option>
		<option value="CNY">CNY</option>
		<option value="USD">USD</option>
		<option value="EUR">EUR</option>
		<option value="GBP">GBP</option>
</select>
		</div>

		<div id="div_history" class="x-hide-display"></div>
        <div id="rule" class="x-hide-display" ></div>

	</body>
</html>
<fiscxd:workflow-taskHistory divId="div_history"
	businessRecordId="${main.billApplyId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:fileUpload divId="rule" recordId="${main.billApplyId}" erasable="${!save}"  increasable="${!save}" 
 resourceId="2222" resourceName="33333" recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>