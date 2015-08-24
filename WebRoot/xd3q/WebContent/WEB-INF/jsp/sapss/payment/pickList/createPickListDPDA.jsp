<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%String pickListType = request.getParameter("pickListType"); 
String operate = request.getParameter("operate")==null?"":request.getParameter("operate");%>
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
	'pickListInfoController.spr?action=selectPurchaseInfo&orderState=3,5,7,8,9',
	'',
	selectPurchaseInfoCallBack,
	{width:900,height:450});
}
function selectPurchaseInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('contractPurchaseId').value=returnvalue.CONTRACT_PURCHASE_ID;
	Ext.getDom('contractNo').value=returnvalue.CONTRACT_NO;
	Ext.getDom('provider').value = returnvalue.EKKO_LIFNR_NAME;
	
	dict_div_currencyId.setComboValue(returnvalue.EKKO_WAERS);	
	dict_div_ymatGroup.setComboValue(returnvalue.YMAT_GROUP);
	var requesturl = 'pickListInfoController.spr?action=initPickListInfoMt&contractPurchaseId=' + returnvalue.CONTRACT_PURCHASE_ID + '&pickListId='+'${tPickListInfo.pickListId}';
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
var operate = '<%=operate%>';
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

   	//----------------------------------------------------------------------
     //增加
     var addButton,delButton;
     addButton = new Ext.Toolbar.Button({
    		text:'增加',
 	    iconCls:'add',
 		handler:function(){
 			var contractPurchaseId = Ext.getDom('contractPurchaseId').value;
			if (contractPurchaseId == ''){
				top.ExtModalWindowUtil.alert('提示','请先选择合同才能增加物料信息！');
				return;				
			}
 			top.ExtModalWindowUtil.show('合同选择',
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
		{name:'TAX'}			  //金额
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
	           dataIndex: 'EKPO_MENGE',
	           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:4,
               maxValue: 99999999999
             })  
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
	           dataIndex: 'TOTAL_VALUE',
	           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 99999999999
             })  
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
        tbar: tbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_grid',
        autowidth:true,
        clicksToEdit:1,        
        layout:'fit'
    });
    
    grid.render();
    
    grid.on("afteredit", afterEdit, grid);

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
		if(colName=='TOTAL_VALUE')
          updateTotal();  
    }     
    // 更新总计
function updateTotal()
{
	ds.commitChanges();
    var count = ds.getCount();
    var records = ds.getRange(0,count-1);
    var valueTotal = 0;
    var i;
    
    for (i=0;i<count;i++)
    {	
    	var value=parseFloat(records[i].data.TOTAL_VALUE)
    	if (!isNaN(value))valueTotal += value;
    }
	Ext.getDom('totalValue').value = formatnumber(valueTotal,2);
}
function formatnumber(value,num) {
	var value1 = value+0;
	var a = Math.round (value1*Math.pow(10,num))/Math.pow(10,num); 
	return a;
} 
//--------------------------------END--------------------------------------

    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			collapsible: true,
			height:270,
			contentEl: 'div_main'
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
	            height:200,
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
	                }]
	    		 
		       }],
				buttons:[{
	            	text:'保存',
	            	handler:function(){
	            		var materialOrg=dict_div_ymatGroup.getActualValue();			
						if(materialOrg==""){
							top.ExtModalWindowUtil.alert('提示','请选择物料组！');
							return;
						}
						if($('pickListRecDate').value==""){
							top.ExtModalWindowUtil.alert('提示','到单日必填！');
							return;
						}
	            	    var rtn = checkInput();
	            		if (rtn != ''){
	            			alert(rtn);
	            			return;
	            		}
	            		/**
	            		if(document.getElementsByName("isCustomPayTax")[0].checked){
	            		    grid.getSelectionModel().selectAll();
							var recoder = grid.selModel.getSelections();
							for (var i=0; i< recoder.length; i++) {							
								if(recoder[i].data.TAX==null){
									 top.ExtModalWindowUtil.alert('提示','请填写关税率！');
								     return ;
								}
								
							};
	            		}*/
	            		var param1 = Form.serialize('mainForm');
	            		param1 = param1 + "&deptId=" + selectId_dept;
						var param = param1 + "&action=updatePickListInfo";
						new AjaxEngine('pickListInfoController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
					}

	            },{
            	text:'提交',
            	hidden:${from!=null},
            	handler:function(){
            		var materialOrg=dict_div_ymatGroup.getActualValue();			
						if(materialOrg==""){
							top.ExtModalWindowUtil.alert('提示','请选择物料组！');
							return;
						}
            		var rtn = checkInput();
            		if (rtn != ''){
            			alert(rtn);
            			return;
            		}
           			if (ds.getCount() > 0){
            		var param1 = Form.serialize('mainForm');
            		 param1 = param1 + "&deptId=" + selectId_dept;
					var param = param1  + "&action=saveAndSubmitPickListInfo";
					new AjaxEngine('pickListInfoController.spr', 
						   {method:"post", parameters: param, onComplete: callBackHandle});
						   
					strOperType = '3';
					
					} else{
					top.Ext.Msg.show({
						title:'提示',
	  					msg: '没有增加物料信息,请确认是否真的要提交审批?',
	  					buttons: {yes:'是', no:'否'},
	  					fn: function(buttonId,text){
	  						if(buttonId=='yes'){
		             			var param1 = Form.serialize('mainForm');
		             			param1 = param1 + "&deptId=" + selectId_dept;
								var param = param1  + "&action=saveAndSubmitPickListInfo";
								new AjaxEngine('pickListInfoController.spr', 
						   			{method:"post", parameters: param, onComplete: callBackHandle});
						   				
						   		strOperType = '3';
	  						}
	  					},
	  					icon: Ext.MessageBox.QUESTION
					});
					}          	    	
            	    
            	}
            },{
            	text:'关闭',
            	hidden:${from!=null},
            	handler:function(){
            		//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
            	    top.ExtModalWindowUtil.markUpdated();
	                top.ExtModalWindowUtil.close();
	            }
            }]
		}]
	});//end fo viewPort
	
   	var arrivalDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'arrivalDate',
	    name:'arrivalDate',
		width: 160,
	    readOnly:true,
		applyTo:'arrivalDate'
   	});
   	var pickListRecDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'pickListRecDate',
	    name:'pickListRecDate',
		width: 160,
	    readOnly:true,
		applyTo:'pickListRecDate'
   	});
   	//付款日
   	var payDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'payDate',
	    name:'payDate',
		width: 160,
	    readOnly:true,
		applyTo:'payDate'
   	});
   	//担保提货日
   	var securityPickDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'securityPickDate',
	    name:'securityPickDate',
		width: 160,
	    readOnly:true,
		applyTo:'securityPickDate'
   	});
   	//承兑日 
   	var acceptanceDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'acceptanceDate',
	    name:'acceptanceDate',
		width: 160,
	    readOnly:true,
		applyTo:'acceptanceDate'
   	});   	  	
});//end of Ext.onReady   

//提示窗口
function showMsg(msg){
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

//回调函数
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if (strOperType == '1'){
		ds.reload();
	}	
	if (strOperType == '3'){
		//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
	    top.ExtModalWindowUtil.close();
	}	
}

//提交工作流审批
function submitWorkflow(){

}	

//合同选择窗体
function selectContractInfo()
{

}

//合同选择窗体 回调函数。
function selectContractInfoCallBack(){
	
}

//提交审批流程回调函数
function submitWorkflowCallBackHandle(transport){
}
</script>
</head>
<body>
<div id='div_progressBar'></div>
<div id="div_basrForm"></div>
<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
	<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
      <tr>
        <td width="11%" align="right">单号：<font color="red">▲</font></td>
        <td width="22%" align="left">
        	<input name="pickListNo" type="text" size="20" tabindex="1" value="${tPickListInfo.pickListNo}"/>
        </td>
        <td width="11%" align="right">到单类型：<font color="red">▲</font></td>

        <td width="22%" align="left">
           <div id="div_pickListType"></div>
        </td>
		<td align="right">部门：<font color="red">▲</font></td>
		<td>
			<div id="dept"></div>
		</td>
      </tr>
      <tr>
        <td width="11%" align="right">合同号：<font color="red">▲</font></td>
        <td width="22%" align="left">
        	<input name="contractNo" type="text" size="20" tabindex="1" value="${tPickListInfo.contractNo}" readonly="readonly"/>
        	<input type="button" value="..." onclick="selectPurchaseWin()"></input>
        	<input type="hidden" name="provider" value="${tPickListInfo.provider}"/>
        </td>
        <td align="right">币种：</td>
        <td>
		<div id="div_currencyId">
        </td>        
        <td width="11%" align="right">创建者：</td>
        <td width="22%" align="left">
			${creatorName}
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">D/P D/A No.：<font color="red">▲</font></td>
        <td width="22%" align="left">
        	<input name="dpDaNo" type="text" size="20" tabindex="1" value="${tPickListInfo.dpDaNo}"/>
        </td>      
        <td width="11%" align="right">装运港：</td>
        <td width="22%" align="left">
        	<input name="shipmentPort" type="text" size="20" tabindex="1" value="${tPickListInfo.shipmentPort}"/>
        </td>
        <td  align="right">目的港：</td>
        <td  align="left">
        	<input name="destinationPort" type="text" size="20" tabindex="1" value="${tPickListInfo.destinationPort}"/>
        </td>
      </tr>
      <tr>
        <td  align="right">预计到货日：</td>
        <td  align="left">
            <input id="arrivalDate" name="arrivalDate" type="text" value="${tPickListInfo.arrivalDate}" />      	
        </td>      
        <td width="11%" align="right">单到日：<font color="red">▲</font></td>
        <td width="22%" align="left">
            <input id="pickListRecDate" name="pickListRecDate" type="text" value="${tPickListInfo.pickListRecDate}" />   
        </td>
        <td width="11%" align="right">担保提货日：</td>
        <td width="22%" align="left">
            <input id="securityPickDate" name="securityPickDate" type="text" value="${tPickListInfo.securityPickDate}" />                
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">承兑日：</td>
        <td width="22%" align="left">
            <input id="acceptanceDate" name="acceptanceDate" type="text" value="${tPickListInfo.acceptanceDate}" /> 
        </td>      
        <td width="11%" align="right">付款日：</td>
        <td width="22%" align="left">
             <input id="payDate" name="payDate" type="text" value="${tPickListInfo.payDate}" />         
        </td>
        <td width="11%" align="right">货物品名：</td>
        <td width="22%" align="left">
            <input id="goods" name="goods" type="text" value="${tPickListInfo.goods}" /> 
        </td>
        
      </tr>
      <tr>
        <td width="11%" align="right">代收行：<font color="red">▲</font></td>
        <td width="22%" align="left">
			<input name="collectionBank" type="text" size="20" value="${tPickListInfo.collectionBank}"/>
        </td>
        <td align="right"><!-- <nobr>是否客户自缴关税增值税<font color="red">▲</font></nobr> --></td>
        <td align="left">
            <!--<input type="radio" name="isCustomPayTax" value="1" <c:if test="${tPickListInfo.isCustomPayTax=='1'}">checked</c:if> />是 -->
            <!--<input type="radio" name="isCustomPayTax" value="0" <c:if test="${tPickListInfo.isCustomPayTax=='0'}">checked</c:if> />否 -->      
        </td> 
        <td width="11%" align="right">代收期限：</td>
        <td width="22%" align="left">
			<input name="timeLimit" type="text" size="20" value="${tPickListInfo.timeLimit}"/>
        </td>
        
      </tr>
      <tr>
       <td align="right">	
		物料组<font color="red">▲</font>
		</td>
		<td  >	
			<div id="div_ymatGroup"></div>
		</td>
		<td align="right">总金额：</td>
        <td colspan="5" align="left" height="20">
                <input id="totalValue" name="totalValue" type="text" value="${tPickListInfo.totalValue}" />
        </td>
		<td colspan="2"></td>
      </tr>
     <tr>
        <td width ="11%" align="right">贸管意见：<font color="red">▲</font></td>
        <td colspan="5" align="left">
             <textarea rows="3" id="maoguanAdvice" name="maoguanAdvice" style="width:90%;overflow:auto" >${tPickListInfo.maoguanAdvice}</textarea>
        </td>
      </tr>
      <tr>
        <td width ="11%" align="right">银行/风险管理部审单意见：</td>
        <td colspan="5" align="left">
             <textarea rows="3" id="examineAdvice" name="examineAdvice" style="width:90%;overflow:auto" >${tPickListInfo.examineAdvice}</textarea>
        </td>
      </tr>
      <tr>
        <td width="11%" align="right" valign="top">备注：</td>
        <td colspan="5" align="left" height="20">
                <textarea rows="3" id="cmd" name="cmd" style="width:90%;overflow:auto" >${tPickListInfo.cmd}</textarea>
        </td>
      </tr>
	</table>
	<input name="pickListId" type="hidden" size="40" value="${tPickListInfo.pickListId}"/>
  	<input name="contractPurchaseId" type="hidden" value="${tPickListInfo.contractPurchaseId }"/>
   	<input name="tradeType" type="hidden" value="${tPickListInfo.tradeType}"/>    
	<input name="isAvailable" type="hidden" size="40" value="${tPickListInfo.isAvailable}"/>
	<input name="creatorTime" type="hidden" size="40" value="${tPickListInfo.creatorTime}"/>
	<input name="creator" type="hidden" size="40" value="${tPickListInfo.creator}"/>
	<input name="applyTime" type="hidden" size="40" value="${tPickListInfo.applyTime}"/>
	<input name="approvedTime" type="hidden" size="40" value="${tPickListInfo.approvedTime}"/>
	<input name="sapOrderNo" type="hidden" size="40" value="${tPickListInfo.sapOrderNo}"/>
	<input name="examineState" type="hidden" size="40" value="${tPickListInfo.examineState}"/>
	<input name="issuingBank" type="hidden" size="40" value="${tPickListInfo.issuingBank}"/>	
</form>
</div>

<div id="div_center"></div>
<div id="div_grid"></div>
</body>
</html>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155" isUseDiv="true"></fiscxd:dept>
<fiscxd:dictionary divId="div_pickListType" fieldName="pickListType" dictionaryName="BM_PICK_LIST_TYPE_DPDA" width="150" selectedValue="${tPickListInfo.pickListType}" ></fiscxd:dictionary>
<fiscxd:dictionary divId="div_currencyId" fieldName="currencyId" dictionaryName="BM_CURRENCY" needDisplayCode = "true" width="150" selectedValue="${tPickListInfo.currencyId}" disable="true" ></fiscxd:dictionary>
<script type="text/javascript">
selectId_dept = '${selectId_dept}'
dict_dept.setValue('${selectText_dept}');
function checkInput(){
	var pickListNo = Ext.getDom('pickListNo').value;
	var pickListType = dict_div_pickListType.getSelectedValue();
	var contractNo = Ext.getDom('contractNo').value;	
	var collectionBank = Ext.getDom('collectionBank').value;	
	var dpDaNo =  Ext.getDom('dpDaNo').value;
	var maoguanAdvice = Ext.getDom('maoguanAdvice').value;
	var rtn = "";
	/**
	var isCustomPayTax = document.getElementsByName("isCustomPayTax");
	var checkTax = false;
     for(var i=0;i<isCustomPayTax.length;i++){
         if(isCustomPayTax[i].checked){
         	checkTax = true;
         }
         
     }
     if (checkTax == false){
		rtn = "请选择是否客户自缴关税增值税";
		return rtn;
	}
	*/
	if (pickListNo == ''){
		rtn = "单号没有输入";
		return rtn;
	}
	if (pickListType == ''){
		rtn = "没有选择到单类型";
		return rtn;
	}
	if (selectId_dept == ''){
		rtn = "没有选择部门";
		return rtn;
	}
	if (contractNo == ''){
		rtn = "没有选择合同号";
		return rtn;
	}	
	if (collectionBank == ''){
		rtn = "代收行没有输入";
		return rtn;
	}
	if (dpDaNo == ''){
		rtn = "dpDaNo没有输入"
		return rtn;
	}
	if (maoguanAdvice == ''){
		rtn = "贸管意见没有输入";
		return rtn;
	}	
	return rtn;
}
</script>



<fiscxd:dictionary divId="div_ymatGroup" fieldName="ymatGroup" dictionaryName="YMATGROUP" width="150" selectedValue="${tPickListInfo.ymatGroup}" disable="false" ></fiscxd:dictionary>

