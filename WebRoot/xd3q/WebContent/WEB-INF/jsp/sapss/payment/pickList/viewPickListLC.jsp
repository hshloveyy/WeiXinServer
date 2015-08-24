<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%String pickListType = request.getParameter("pickListType"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>到单登记表</title>
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
function selectPurchaseWin(){
	top.ExtModalWindowUtil.show('查询所属登陆员工总门的合同信息',
	'pickListInfoController.spr?action=selectLcNoInfo&creditState=3',
	'',
	selectPurchaseInfoCallBack,
	{width:900,height:450});
}
function selectPurchaseInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('contractPurchaseId').value=returnvalue.CONTRACT_PURCHASE_ID;
	Ext.getDom('contractNo').value=returnvalue.CONTRACT_NO;
	Ext.getDom('sapOrderNo').value = returnvalue.SAP_ORDER_NO;	
	Ext.getDom('lcNo').value = returnvalue.CREDIT_NO;		
	
	
	var requesturl = 'pickListInfoController.spr?action=initPickListInfoMt&lcNo=' + returnvalue.CREDIT_NO + '&contractPurchaseId=' + returnvalue.CONTRACT_PURCHASE_ID + '&pickListId='+'${tPickListInfo.pickListId}';
	Ext.Ajax.request({
		url: encodeURI(requesturl),
		success: function(response, options){
  			 ds.load({params:{start:0, limit:10},arg:[]});
		},
		failure:function(response, options){
			top.ExtModalWindowUtil.alert('提示','与服务器交付失败请重新再试！');
		}
	});
}


//操作类别  
var operate = '${operate}';
//贸易类型
var pickListType = '${pickListType}';
//是否可以提交
var submitHidden = '${submitHidden}'; 
//是否可以保存
var saveDisabled = '${saveDisabled}';
var grid;

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	var fm = Ext.form;
     //增加
     var addButton,delButton;
     addButton = new Ext.Toolbar.Button({
    		text:'增加',
 	    iconCls:'add',
 		handler:function(){
 			var contractPurchaseId = Ext.getDom('contractPurchaseId').value;
			if (contractPurchaseId == ''){
				top.ExtModalWindowUtil.alert('提示','请先选择信用证号才能增加物料信息！');
				return;				
			}
 			top.ExtModalWindowUtil.show('合同物料选择',
 			'pickListInfoController.spr?action=toFindPurchaseMaterial&contractPurchaseId='+contractPurchaseId,
 			'',
 			funAddMtCallBack,
 			{width:900,height:600});
 		}
    	});
    	
    //增加出仓单明细资料(物料)的回调函数
    function funAddMtCallBack(){
	 	var returnValue = top.ExtModalWindowUtil.getReturnValue(); 
    	var requestUrl = 'pickListInfoController.spr?action=addDetail&purchaseRowsId=' + returnValue.PURCHASE_ROWS_ID;	
    	requestUrl = requestUrl + "&pickListId=" + "${tPickListInfo.pickListId}";  
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);

				var p = new plant({
	                    PURCHASE_ROWS_ID: responseArray.purchaseRowsId,            
						PICK_LIST_ID: responseArray.pickListId,               
				    	EKPO_MATNR: responseArray.ekpoMatnr,                
						EKPO_TXZ01: responseArray.ekpoTxz01,               
						EKPO_MENGE: responseArray.ekpoMenge,    
					    EKPO_BPRME: responseArray.ekpoBprme,                              
						TOTAL_VALUE: responseArray.totalValue                                
	                });
				grid.stopEditing();
				ds.insert(0, p);
				grid.startEditing(0, 0);
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});    
    }
        	

    	//删除
    	delButton = new Ext.Toolbar.Button({
    		text:'删除',
 	    iconCls:'delete',
		handler:function(){
			if (grid.selModel.hasSelection()){
				var records = grid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个物料信息！');
				}else{
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=deleteMateriel";
								param = param + "&purchaseRowsId=" + records[0].data.PURCHASE_ROWS_ID;

								new AjaxEngine('pickListInfoController.spr', 
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
    	
	var plant = Ext.data.Record.create([
		{name:'PURCHASE_ROWS_ID'},           //物料ID
		{name:'PICK_LIST_ID'},           //提货单ID
		{name:'EKPO_MATNR'},         //物料号
		{name:'EKPO_TXZ01'},             //物料描述
		{name:'EKPO_MENGE'},             //数量
		{name:'EKPO_MEINS'},             //单位
		{name:'EKPO_BPRME'},              //币种
		{name:'TOTAL_VALUE'},		  //金额
		{name:'TAX'}			  //金额		  //金额
	]);

	ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'pickListInfoController.spr?action=queryMaterial&pickListId=${tPickListInfo.pickListId}'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},plant)
    });

  	 var tbar = new Ext.Toolbar({
 		items:[addButton,'-',delButton]
 	});
  	
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
           {
			   header: '物料ID',
	           width: 150,
	           sortable: true,
	           hidden: true,
	           dataIndex: 'PURCHASE_ROWS_ID'
           },		
           {
			   header: '物料号',
	           width: 150,
	           sortable: true,
	           dataIndex: 'EKPO_MATNR'
           },
		   {
			   header: '物料描述',
	           width: 150,
	           sortable: true,
	           dataIndex: 'EKPO_TXZ01'
           },          
		   {
			   header: '数量',
	           width: 150,
	           sortable: false,
	           dataIndex: 'EKPO_MENGE'
           },
           {
			   header: '单位',
	           width: 150,
	           sortable: true,
	           dataIndex: 'EKPO_MEINS'
           },
           {
	           header: '金额',
	           width: 150,
	           sortable: true,
	           dataIndex: 'TOTAL_VALUE'
           },
           {
	           header: '关税率%',
	           width: 80,
	           sortable: true,
	           dataIndex: 'TAX',
	           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 10000
             }) 
             }
           
    ]);
    cm.defaultSortable = true;
    
    var bbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:ds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
  
   ds.load({params:{start:0, limit:10},arg:[]});    
    
    grid = new Ext.grid.EditorGridPanel({
    	id:'grid',
        ds: ds,
        cm: cm,
        sm: sm,
        bbar: bbar,
        //tbar: tbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_grid',
        autowidth:true,
        layout:'fit'
    });
    
    grid.render();
    
    //grid.on("afteredit", afterEdit, grid);

    function afterEdit(obj){
    	var row = obj.record;//获取被修改的行
        var colName = obj.field;//获取被修改的列
        var purchaseRowsId = row.get("PURCHASE_ROWS_ID");
        var colValue = row.get(colName);     
        updateMaterielValue(row,purchaseRowsId,colName,colValue);
    } 
    //更新物料信息
    function updateMaterielValue(row,purchaseRowsId,colName,colValue){
    	var requestUrl = 'pickListInfoController.spr?action=updateMateriel';
		requestUrl = requestUrl + '&purchaseRowsId=' + purchaseRowsId;
		requestUrl = requestUrl + '&colName=' + colName;
		requestUrl = requestUrl + '&colValue=' + colValue;
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
   
			},
			failure:function(response, options){					
			}
		});    
    }  
//--------------------------------END--------------------------------------

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			height:250,
			xtype:'tabpanel',
			id:'infotype',
			name:'infotype',
			plain:true,
            autoScroll:'true',
            activeTab: 0,			
            items:[{
            	title:'到单信息',
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
	            height:330,
	            autoScroll:'true',
	            activeTab: 0,
	            items:[{
	            	title:'物料信息',
	            	contentEl: '',
	            	id:'mtInfo',
					name:'mtInfo',
					autoScroll:'true',
	            	layout:'fit',
	        	    items:[grid]	
	                },{
	            	title:'附件信息',
	            	contentEl: 'div_accessory',
	            	id:'accessoryinfo',
					name:'accessoryinfo',
					height:300,
					autoScroll:'true',
	            	layout:'fit',
	            	listeners:{activate:handlerActivate}
	             }]
	    		 
		       }]
			}]
	});//end fo viewPort
});//end of Ext.onReady   
function handlerActivate(tab){
	div_accessory_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('444').value}});
}
//回调函数
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if (strOperType == '1'){
		ds.reload();
	}	
	if (strOperType == '3'){
		top.ExtModalWindowUtil.markUpdated();
	    top.ExtModalWindowUtil.close();
	}	
}
</script>
</head>
<body>
<div id='div_progressBar'></div>
<div id="div_basrForm"></div>
<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
	<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td align="right">单号：</td>
        <td align="left">
        	<input name="pickListNo" type="text" size="20" tabindex="1" value="${tPickListInfo.pickListNo}" readOnly="readonly"/>
        </td>
        <td align="right">到单类型：</td>
        <td align="left">
           <div id="div_pickListType"></div>
        </td>
		<td align="center" colspan="2">部门：${selectText_dept}  创建者：${creatorName}
		</td>
      </tr>
      <tr>
        <td align="right">开证行：</td>
        <td align="left">
        	<input name="issuingBank" type="text" size="20" tabindex="1" value="${tPickListInfo.issuingBank}" readOnly="readonly"/>
        </td>
        <td align="right">L/C No.：</td>
        <td align="left">
			<input name="lcNo" type="text" size="20" tabindex="1" value="${tPickListInfo.lcNo}" readOnly="readonly"/>
        </td>
        <td width="11%" align="right">期限：</td>
        <td width="22%" align="left" colspan="3">
			<input name="timeLimit" type="text" size="20" value="${tPickListInfo.timeLimit}"/>
        </td>          
      </tr>
      <tr>
        <td align="right">开证日期：</td>
        <td align="left">
             <input id="issuingDate" name="issuingDate" type="text" value="${tPickListInfo.issuingDate}" readOnly="readonly"/>  
        </td>      
        <td align="right">装运港：</td>
        <td align="left">
        	<input name="shipmentPort" type="text" size="20" tabindex="1" value="${tPickListInfo.shipmentPort}" readOnly="readonly"/>
        </td>
        <td  align="right">目的港：</td>
        <td  align="left">
        	<input name="destinationPort" type="text" size="20" tabindex="1" value="${tPickListInfo.destinationPort}" readOnly="readonly"/>
        </td>
      </tr>
      <tr>
        <td  align="right">预计到货日：</td>
        <td  align="left">
            <input id="arrivalDate" name="arrivalDate" type="text" value="${tPickListInfo.arrivalDate}" readOnly="readonly"/>    	
        </td>      
        <td align="right">单到日：</td>
        <td align="left">
            <input id="pickListRecDate" name="pickListRecDate" type="text" value="${tPickListInfo.pickListRecDate}" readOnly="readonly"/>  
        </td>
        <td align="right">担保提货日：</td>
        <td align="left">
            <input id="securityPickDate" name="securityPickDate" type="text" value="${tPickListInfo.securityPickDate}" readOnly="readonly"/>      
        </td>    
      </tr>
      <tr>
        <td align="right">承兑日：</td>
        <td align="left">
            <input id="acceptanceDate" name="acceptanceDate" type="text" value="${tPickListInfo.acceptanceDate}" readOnly="readonly"/>
        </td>       
        <td align="right">付款日：</td>
        <td align="left">
             <input id="payDate" name="payDate" type="text" value="${tPickListInfo.payDate}" readOnly="readonly"/>      
        </td>
        <td align="right">币种：</td>
        <td>
		<div id="div_currencyId"></div>
        </td>          
      </tr>
      <tr>
        <td align="right"><!-- 是否客户自缴关税增值税： --></td>
        <td align="left" >
            <!--<input type="radio" name="isCustomPayTax" value="1" disabled="disabled" <c:if test="${tPickListInfo.isCustomPayTax=='1'}">checked</c:if> />是 -->
            <!--<input type="radio" name="isCustomPayTax" value="0" disabled="disabled" <c:if test="${tPickListInfo.isCustomPayTax=='0'}">checked</c:if> />否 -->      
        </td>      
        <td align="right">核销单号：</td>
        <td align="left">
        	<input name="writeListNo" type="text" size="20" tabindex="" value="${tPickListInfo.writeListNo }" readOnly="readonly"/>
        </td>
         <td align="right">货物品名：</td>
        <td align="left">
            <input id="goods" name="goods" type="text" value="${tPickListInfo.goods}" />       
        </td>   
      </tr>
       <tr>
       <td align="right">	
		物料组:
		</td>
		<td>	
			<div id="div_ymatGroup"></div>
		</td>
		<td align="right">总金额：</td>
        <td align="left">
        	<input name="totalValue" type="text" size="20" tabindex="" value="${tPickListInfo.totalValue }" readOnly="readonly"/>
        </td>
		<td colspan="2"></td>
      </tr>
      <tr>
        <td align="right">贸管意见：</td>
        <td colspan="5" align="left">
            <textarea rows="3" cols="85" readonly="readonly">${tPickListInfo.maoguanAdvice}</textarea>
        </td>
      </tr>
      <tr>
        <td align="right">银行/风险管理部审单意见：</td>
        <td colspan="5" align="left">
            <textarea rows="3" cols="85" readonly="readonly">${tPickListInfo.examineAdvice}</textarea>
        </td>
      </tr>
      <tr>
        <td align="right" valign="top">备注：</td>
        <td colspan="5" align="left" height="20">
               <textarea rows="3" cols="85" readonly="readonly">${tPickListInfo.cmd}</textarea>
        </td>
      </tr>
	</table>
	<input name="pickListId" type="hidden" size="40" value="${tPickListInfo.pickListId}"/>
  	<input name="contractPurchaseId" type="hidden" value="${tPickListInfo.contractPurchaseId }"/>
  	<input name="contractNo" type="hidden" value="${tPickListInfo.contractNo }"/>  	
   	<input name="tradeType" type="hidden" value="${tPickListInfo.tradeType}"/>    
	<input name="isAvailable" type="hidden" size="40" value="${tPickListInfo.isAvailable}"/>
	<input name="creatorTime" type="hidden" size="40" value="${tPickListInfo.creatorTime}"/>
	<input name="creator" type="hidden" size="40" value="${tPickListInfo.creator}"/>
	<input name="applyTime" type="hidden" size="40" value="${tPickListInfo.applyTime}"/>
	<input name="approvedTime" type="hidden" size="40" value="${tPickListInfo.approvedTime}"/>
	<input name="sapOrderNo" type="hidden" size="40" value="${tPickListInfo.sapOrderNo}"/>
	<input name="examineState" type="hidden" size="40" value="${tPickListInfo.examineState}"/>
</form>
</div>

<div id="div_center"></div>
<div id="div_grid"></div>
<div id="div_history" class="x-hide-display"></div>
<div id="div_accessory" class="x-hide-display"></div>
<fiscxd:fileUpload divId="div_accessory" resourceId="2222" resourceName="33333" erasable="false" increasable="false" recordIdHiddenInputName="444" recordId="${tPickListInfo.pickListId}"></fiscxd:fileUpload>
</body>
</html>
<fiscxd:dictionary divId="div_currencyId" fieldName="currencyId" dictionaryName="BM_CURRENCY" needDisplayCode = "true" width="150" selectedValue="${tPickListInfo.currencyId}" disable="true" ></fiscxd:dictionary>
<fiscxd:workflow-taskHistory divId="div_history" businessRecordId="${tPickListInfo.pickListId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:dictionary divId="div_pickListType" fieldName="pickListType" dictionaryName="BM_PICK_LIST_TYPE_LC" width="150" selectedValue="${tPickListInfo.pickListType}" disable="true"></fiscxd:dictionary>


<fiscxd:dictionary divId="div_ymatGroup" fieldName="ymatGroup" dictionaryName="YMATGROUP" width="150" selectedValue="${tPickListInfo.ymatGroup}" disable="true" ></fiscxd:dictionary>

