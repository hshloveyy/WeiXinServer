<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ taglib prefix="ffcs" tagdir="/WEB-INF/tags/infolion"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>采购开票申请</title>
		
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


var isChanged=false;
//贸易名称
var strpurchaseBillTitle ='采购开票申请';
var strOperType='';
var Enabled='false';
if ('${submit}'!='true')Enabled='submit';
if ('${save}'!='true')Enabled='save';
function showFindSupplier(){
	top.ExtModalWindowUtil.show('查找供应商','masterQueryController.spr?action=toFindSupplier','',supplierCallback,{width:755,height:410});
}
function supplierCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	if(cb==''){
		Ext.getDom('billToPartyName').value='';
		Ext.getDom('billToParty').value='';
	}
	else {
		Ext.getDom('billToPartyName').value=cb.NAME1;
		Ext.getDom('billToParty').value=cb.LIFNR;
	}
}
//新增保存删除提示
function customCallBackHandle(transport)
{
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var sReturnMessage=customField.returnMessage;
	
	top.ExtModalWindowUtil.alert('提示',sReturnMessage);

	purchaseBillDetailInfods.reload();

	
	if ('${close}'=='false' && (sReturnMessage.indexOf("提交成功")>-1))
	{
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	}
}
var purchaseBillDetailInfogrid;		//开票申请单明细列表
var purchaseBillDetailInfods;

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';

    var fm = Ext.form;
    //增加开票申请单明细资料(物料)的回调函数
    function funAddpurchaseBillDetailCallBack(){
	 	var returnValue = top.ExtModalWindowUtil.getReturnValue();   
    	var requestUrl = "purchaseBillController.spr?action=addDetail";

    	requestUrl += "&purchaseRowsId=" + returnValue.PURCHASE_ROWS_ID;
    	requestUrl += "&purchaseBillId=" + "${main.purchaseBillId}";
    	   
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				purchaseBillDetailInfods.reload();
				purchaseBillDetailInfods.commitChanges();
				// updateTotal();				
			},
			failure:function(response, options){
				Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});
    }  
     //增加开票申请单明细资料(物料)
    var addpurchaseBillDetailInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
	    disabled:${save},
	    handler:function(){
			var contractPurchaseId = Ext.getDom('contractPurchaseId').value;
			if (contractPurchaseId == ''){
				top.ExtModalWindowUtil.alert('提示','请先选择采购合同再增加物料信息！');
				return;				
			}
			top.ExtModalWindowUtil.show(strpurchaseBillTitle+'开票申请单登记表',
			'receiptsController.spr?action=toFindPurchaseMaterial&contractPurchaseId='+contractPurchaseId,
			'',
			funAddpurchaseBillDetailCallBack,
			{width:800,height:500});
			isChanged=true;
		}
   	});
   	
   	//删除
   	var deletepurchaseBillDetailInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
	    disabled:${save},
	    handler:function(){
			if (purchaseBillDetailInfogrid.selModel.hasSelection()){
				var records = purchaseBillDetailInfogrid.selModel.getSelections();
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
								param = param + "&purchaseBillMateId=" + records[0].data.PURCHASE_BILL_MATE_ID;

								new AjaxEngine('purchaseBillController.spr', 
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
   	

   	
   	//工具栏---添加、删除按钮。
    var purchaseBillDetailInfotbar = new Ext.Toolbar({
		items:[addpurchaseBillDetailInfo,'-',deletepurchaseBillDetailInfo]
	  });

	var purchaseBillDetailInfoPlant = Ext.data.Record.create([
		{name:'PURCHASE_BILL_MATE_ID'},  			//明细ID
		{name:'PURCHASE_BILL_ID'},  				//开票申请ID
		{name:'MATERIAL_CODE'},  				//物料编码
		{name:'MATERIAL_NAME'},  				//物料名称
		{name:'MATERIAL_UNIT'},      			//物料单位
		{name:'QUANTITY'},
		{name:'TAX_RATE'},
		{name:'CURRENCY'},                 
		{name:'TOTAL_MONEY'},     
		{name:'CONTRACT_NO'},             
		{name:'PRICE'},
		{name:'CMD'}
	]);

	purchaseBillDetailInfods = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({url:'purchaseBillController.spr?action=queryMaterial&purchaseBillId=' + '${main.purchaseBillId}'}),
        reader: new Ext.data.JsonReader({
        root: 'root',
        totalProperty: 'totalProperty'
        },purchaseBillDetailInfoPlant)
    });
        
    var purchaseBillDetailInfosm = new Ext.grid.CheckboxSelectionModel();

    var purchaseBillDetailInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		purchaseBillDetailInfosm,
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
           header: '开票金额<font color="red">▲</font>',
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
           header: '单价',
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
    	}
    ]);

    purchaseBillDetailInfocm.defaultSortable = true;
    
    var purchaseBillDetailInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:purchaseBillDetailInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });

	purchaseBillDetailInfogrid = new Ext.grid.EditorGridPanel({
    	id:'purchaseBillDetailInfoGrid',
        ds: purchaseBillDetailInfods,
        cm: purchaseBillDetailInfocm,
        sm: purchaseBillDetailInfosm,
        bbar: purchaseBillDetailInfobbar,
        tbar: purchaseBillDetailInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit',
        clicksToEdit:1
    });
    purchaseBillDetailInfods.load({params:{start:0, limit:10},arg:[]});
    
    purchaseBillDetailInfogrid.render();
    
    purchaseBillDetailInfogrid.on("afteredit", afterEdit, purchaseBillDetailInfogrid);

    function afterEdit(obj){
    	isChanged=true;
        if( Enabled=='false') return;
    	var row = obj.record;//获取被修改的行
        var colName = obj.field;//获取被修改的列
        var colValue = row.get(colName);
        var purchaseBillMateId = row.get("PURCHASE_BILL_MATE_ID");
        
        //数量或单价
        if (colName == 'QUANTITY' || colName == 'TOTAL_MONEY'){
        	setMaterielPrice(row);
        }

        updateMaterielValue(row,purchaseBillMateId,colName,colValue);
        purchaseBillDetailInfods.commitChanges();
        updateTotal();
    }
    
    
     //设置单价
     function setMaterielPrice(row){   
        var purchaseBillMateId = row.get("PURCHASE_BILL_MATE_ID");
     	var total = row.get("TOTAL_MONEY");
     	var quantity = row.get("QUANTITY");
     	if (quantity == null || quantity == ''){
     		quantity = "1";
     	}
     	if (total == null || total == ''){
     		total = "0";
     	}
     	var price = 0;
     	if (parseFloat(quantity)!=0){
     		price = parseFloat(total) / parseFloat(quantity);
     	}
     	
     	row.set("PRICE",formatnumber(price,6));
     	updateMaterielValue(row,purchaseBillMateId,"PRICE",formatnumber(price,2));
     }
	
    //更新物料信息
    function updateMaterielValue(row,purchaseBillMateId,colName,colValue){
    	var requestUrl = 'purchaseBillController.spr?action=updatePurchaseBillMateriel';
		requestUrl = requestUrl + '&purchaseBillMateId=' + purchaseBillMateId;
		requestUrl = requestUrl + '&colName=' + colName;
		requestUrl = requestUrl + '&colValue=' + colValue;

		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseUtil1 = new AjaxResponseUtils(response.responseText);
				var customField = responseUtil1.getCustomField("coustom");   
				var stateCode = customField.stateCode;
				purchaseBillDetailInfods.commitChanges();
			},
			failure:function(response, options){
				Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});
	}    
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
            	title:'采购开票申请',
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
			            	id:'purchaseBillDetailInfo',
							name:'purchaseBillDetailInfo',
							autoScroll:'true',
			            	layout:'fit',
			            	items:[purchaseBillDetailInfogrid]
	                  }]		           
	             }],
	                  
				buttons:[{
	            	text:'保存',
	            	hidden:${save},
	            	handler:function(){
						purchaseBillDetailInfogrid. getSelectionModel().selectAll();
						var recoder = purchaseBillDetailInfogrid.selModel.getSelections();

	            		var param1 = Form.serialize('mainForm');
	            		
	            		if (checkValues()==false)return;
	            		
	            		var param = param1 + "&action=updatePurchaseBill";
						new AjaxEngine('purchaseBillController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
						isChanged=false;
						updateTotal();
					}
	            },{
					text:'保存票号',
					hidden:${!modifiable},
					handler:function(){
						var param='?action=saveSapInfo&purchaseBillId=${main.purchaseBillId}&taxNo='+$('taxNo').value;
						new AjaxEngine('purchaseBillController.spr', 
								   {method:"post", parameters: param, onComplete: callBackHandle});
					}
		        },
	            {
            		text:'提交',
	            	hidden:${submit},
	            	hidden:${submit},            	
            		handler:function(){
		            	var param1 = Form.serialize('mainForm');
            			if (checkValues()==false)return;
	            			            		
	           			if (purchaseBillDetailInfods.getCount() > 0){
							var param = param1  + "&action=submitAndSavePurchaseBill";
							new AjaxEngine('purchaseBillController.spr', 
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
   	var applyTime1 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'billTime',
	    name:'billTime',
		width: 160,
		applyTo:'billTime'
   	});  	
   	var payTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'payTime',
	    name:'payTime',
		width: 160,
		applyTo:'payTime'
   	}); 
   	   

 	if(${modifiable}){
 	    $('taxNo').readOnly=false;
 	};
 	 
});//end of Ext.onReady   
function checkValues()
{
     if (Ext.getDom('contractPurchaseNo').value=="")
     {
     	top.ExtModalWindowUtil.alert('提示','请先选采购订单号！');
     	return false;
     }
	 if (Ext.getDom('billToParty').value=="")
     {
     	top.ExtModalWindowUtil.alert('提示','供应商编码不能为空！');
     	return false;
     }    
     return true;
}
function handlerActivate(tab){
	rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
}
// 更新总计
function updateTotal()
{
	purchaseBillDetailInfods.commitChanges();
    var count = purchaseBillDetailInfods.getCount();
    var records = purchaseBillDetailInfods.getRange(0,count-1);
    var quantityTotal = 0;
    var priceTotal = 0;
    var i;
    
    for (i=0;i<count;i++)
    {	
    	var quantity=parseFloat(records[i].data.QUANTITY)
    	if (!isNaN(quantity))quantityTotal += parseFloat(records[i].data.QUANTITY);
    	var price=parseFloat(records[i].data.TOTAL_MONEY)
    	if (!isNaN(price))priceTotal += parseFloat(records[i].data.TOTAL_MONEY);
    }
	Ext.getDom('priceTotal').value = formatnumber(priceTotal,2);
	Ext.getDom('quantityTotal').value = formatnumber(quantityTotal,3);
}
// 格式化数字
function formatnumber(value,num) {
	var value1 = value+0;
	var a = Math.round (value1*Math.pow(10,num))/Math.pow(10,num); 
	return a;
	
}
function selectContractPurchase(){
	if (Enabled=='false')return;
	top.ExtModalWindowUtil.show('查询所属登陆员工总门的合同信息',
	'creditEntryController.spr?action=selectContractInfo&tradeType='+'&orderState=3,5,7,8,9,11&deptid=',
	'',
	selectPurchaseInfoCallBack,
	{width:900,height:450});
}
function selectPurchaseInfoCallBack(){
    var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    if(returnvalue.length =1){
	    $('contractPurchaseNo').value=returnvalue[0].data.CONTRACT_NO;
	    $('contractPurchaseId').value=returnvalue[0].data.CONTRACT_PURCHASE_ID
	    $('sapOrderNo').value=returnvalue[0].data.SAP_ORDER_NO
	    $('paperContractNo').value=returnvalue[0].data.EKKO_UNSEZ
		var requesturl = 'purchaseBillController.spr?action=initPurchaseBillMt&purchaseBillId='+'${main.purchaseBillId}';
		requesturl = requesturl + "&contractPurchaseId=" + returnvalue[0].data.CONTRACT_PURCHASE_ID;
		Ext.Ajax.request({
			url: encodeURI(requesturl),
			success: function(response, options){
				var responseUtil1 = new AjaxResponseUtils(response.responseText);
				var customField = responseUtil1.getCustomField("coustom");
				Ext.getDom('priceTotal').value = customField.priceTotal;
				Ext.getDom('quantityTotal').value = customField.quantityTotal;
				Ext.getDom('billToParty').value = customField.billToParty;
				Ext.getDom('billToPartyName').value = customField.billToPartyName;
	  			purchaseBillDetailInfods.load({params:{start:0, limit:10},arg:[]});
			},
			failure:function(response, options){
				top.ExtModalWindowUtil.alert('提示','与服务器交付失败请重新再试！');
			}
		});
    }
    
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
 				_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
				top.ExtModalWindowUtil.markUpdated();
				top.ExtModalWindowUtil.close();
			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}
</script>
	</head>
	<body>
		<div id="main" class="x-hide-display">
			<div id="div_main" class="x-hide-display">
				<form id="mainForm" action="" name="mainForm">
					<table width="100%" border="1" cellpadding="4" cellspacing="1"
						bordercolor="#6699FF" class="datatable">
						<tr>
							<td width="11%" align="right">
								采购合同号<font color="red">▲</font>：
							</td>
							<td width="22%" align="left">
								<input id="contractPurchaseNo" name="contractPurchaseNo" readonly="readonly"
									type="text" size="20" tabindex="1" value="${main.billToParty}">
								<input id="contractPurchaseId" name="contractPurchaseId" type="hidden"
									value="${main.contractPurchaseId }" />
								<input id="selectPurchase" type="button" name="selectContract"
									value="..." onclick="selectContractPurchase()"/>
							</td>
							<td width="11%" align="right">
								开票申请单号：
							</td>
							<td width="22%" align="left">
								<div id="div_billType">
									<input id="purchaseBillNo" name="purchaseBillNo" type="text"
										size="20" tabindex="1" value="${main.purchaseBillNo}" readonly />
								</div>
								<input id="deptId" name="deptId" type="hidden" size="20"
									value="${main.deptId}" />
								<input id="isAvailable" name="isAvailable" type="hidden"
									size="40" value="${main.isAvailable}" />
								<input id="creatorTime" name="creatorTime" type="hidden"
									size="40" value="${main.creatorTime}" />
								<input name="creator" type="hidden" size="40"
									value="${main.creator}" />
								<input id="purchaseBillId" name="purchaseBillId" type="hidden"
									size="40" value="${main.purchaseBillId}" />
								<input type="hidden" name="examineState" value="${main.examineState}"/>
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
								采购订单号
							</td>
							<td width="22%" align="left">
								<input id="sapOrderNo" name="sapOrderNo"
									readonly="readonly" type="text" size="20" tabindex="1"
									value="${main.sapOrderNo}" />
							</td>
							<td width="11%" align="right">
								纸质合同号：
							</td>
							<td width="22%" align="left">
								<input id="paperContractNo" name="paperContractNo"
									type="text" size="20" tabindex="1"
									value="${main.paperContractNo}">
							</td>
							<td width="11%" align="right">
								身份证号：
							</td>
							<td width="22%" align="left">
								<input type="text" id="cardNo" name="cardNo"
									size="20" value="${main.cardNo}">
							</td>
						</tr>
						<tr>
							<td width="11%" align="right">
								供应商编码：
							</td>
							<td width="22%" align="left">
								<input id="billToParty" name="billToParty" type="text"
									value="${main.billToParty }" readonly="readonly"/>
								<input id="selectBillToParty"  type="button" value="..." size="20" onclick="showFindSupplier()"/>
							</td>
							<td width="11%" align="right">
								供应商名称：
							</td>
							<td width="22%" align="left">
								 <input id="billToPartyName" name="billToPartyName" type="text"
									value="${main.billToPartyName}" readonly="readonly"/>
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
							<td align="right">
								应付款日
							</td>
							<td align="left">
								<input id="payTime" name="payTime" type="text" size="20"
									tabindex="1" value="${main.payTime}">
							</td>
							<td align="right">
								票号：
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
								数量合计：
							</td>
							<td width="22%" align="left">
								<input id="quantityTotal" readonly="readonly"
									name="quantityTotal" type="text" value="${main.quantityTotal}">
							</td>
							<td align="right">
								开票金额：
							</td>
							<td>
								<input id="priceTotal" name="priceTotal" type="text" size="20"
									readonly="readonly" value="${priceTotal}">
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
	businessRecordId="${main.purchaseBillId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:fileUpload divId="rule" recordId="${main.purchaseBillId}" erasable="${!save}"  increasable="${!save}" 
 resourceId="2222" resourceName="33333" recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>