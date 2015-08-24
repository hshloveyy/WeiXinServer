<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已开票退货单</title>
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
var taskType = '${taskType}';
var Enabled = '${Enabled}';
var grid;		
var ds;
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
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
	return;
	
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

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	if (strOperType == '3'){
		top.ExtModalWindowUtil.alert('提示',customField.returnMessage);	
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
	    top.ExtModalWindowUtil.close();
	} else {
		ds.reload();		
	}	
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';

	if(taskType=="1") //填写实际金额
    {
        Ext.getDom("sapDeliveryNo").readOnly = false;
    } else if (taskType == "2"){
        Ext.getDom("sapBillingNo").readOnly = false;
        Ext.getDom("invoiceNo").readOnly = false;            
    } else if (taskType == "3"){
        Ext.getDom("sapOrderNo").readOnly = false;
    }
   	var fm = Ext.form;

    //增加出仓单明细资料(物料)
   	   var add = new Ext.Toolbar.Button({
	   		text:'增加',
		    iconCls:'add',
			handler:function(){
				var contractSalesId = Ext.getDom('contractSalesId').value;
				if (contractSalesId == '' ){
					top.ExtModalWindowUtil.alert('提示','请先选择合同才能增加物料信息！');
					return;				
				}
				
				top.ExtModalWindowUtil.show('合同物料表',
				'salesMaterialQueryController.spr?action=findSalesMaterial&contractSalesId='+contractSalesId ,
				'',
				funAddCallBack,
				{width:800,height:500,closable:false});					
			}
	   	});
	   	
    //增加出仓单明细资料(物料)的回调函数
    function funAddCallBack(){
	 	var returnValue = top.ExtModalWindowUtil.getReturnValue(); 
		var param = "?action=addMaterial&returnId=" + "${tSalesReturn.returnId}" + "&salesRowsId=" + returnValue.SALES_ROWS_ID;
		new AjaxEngine('salesReturnController.spr', 
   			{method:"post", parameters: param, onComplete: callBackHandle}); 
   			strOperType = "1";
    }
    	   	
   	
   	//删除
   	var del = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (grid.selModel.hasSelection()){
				var records = grid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一条物料信息！');
				}else{
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=delMaterial";
								param = param + "&returnMaterialId=" + records[0].data.RETURN_MATERIAL_ID;

								new AjaxEngine('salesReturnController.spr', 
						   			{method:"post", parameters: param, onComplete: callBackHandle});
						   			
						   		strOperType = '1';
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的物料信息！');
			}
		}
   	});
   	   	          
   	//工具栏---添加、删除按钮。
   	if (Enabled!='false')
    var tbar = new Ext.Toolbar({
		items:[add,'-',del]
	  });
	  
	var plant = Ext.data.Record.create([
		{name:'RETURN_MATERIAL_ID'},            //行ID
		{name:'RETURN_ID'},                     //主表ID
		{name:'MATERIAL_CODE'},                 //物料编码
	    {name:'MATERIAL_NAME'},                 //物料名称
		{name:'MATERIAL_UNIT'},                 //物料单位
		{name:'QUANTITY'},                      //数量
		{name:'CALC_UNIT'},                      //计量单位	
		{name:'PRICE_UNIT'},                      //条件定价单位
		{name:'FACTORY'},                      //工厂
		{name:'DELIVERY_TIME'},                //项目交货日期										
		{name:'BATCH_NO'},                      //批次号
		{name:'PRICE'},                         //单价
		{name:'RATE'},                          //税率	
		{name:'MONEY'},                          //货款
		{name:'TAXES'},                          //税金					
		{name:'TOTAL_MONEY'}                    //总计
	]);

	ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'salesReturnController.spr?action=queryMaterial&returnId='+'${tSalesReturn.returnId}'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},plant)
    });
    
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		   {header: '物料行ID',
           width: 100,
           sortable: true,
           dataIndex: 'RETURN_MATERIAL_ID',
            hidden:true
           },
		   {header: '主表ID',
           width: 100,
           sortable: true,
           dataIndex: 'RETURN_ID',
            hidden:true
           },           
           {header: '物料编码',
           width: 60,
           sortable: true,
           dataIndex: 'MATERIAL_CODE'
           },
		　 {header: '物料名称',
           width: 80,
           sortable: true,
           dataIndex: 'MATERIAL_NAME'
           },
		　 {header: '物料单位',
           width: 60,
           sortable: false,
           dataIndex: 'MATERIAL_UNIT'
           },            
           {header: '<font color="red">▲</font>退货数量',
           width: 100,
           sortable: true,
           dataIndex: 'QUANTITY',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:3,
               maxValue: 10000000000
           })            
           },{
        	   header: '<font color="red">▲</font>批次', 
        	   width: 60,
        	   dataIndex: 'BATCH_NO',
               editor: new fm.TextField({
            	   allowBlank: false
               })
           },
           {header: '计量单位',
           width: 60,
           sortable: true,
           dataIndex: 'CALC_UNIT'           
           },
		   {header: '条件定价单位',
           width: 60,
           hidden: true,
           dataIndex: 'PRICE_UNIT',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 10000000000
           })                      
           }, 
 			{header: '工厂',
           width: 60,
           sortable: true,
           dataIndex: 'FACTORY'           
           },
 			{header: '项目交货日期',
           width: 60,
           sortable: true,
           dataIndex: 'DELIVERY_TIME'           
           },                        
           {
           header: '单价',
           width: 60,
           sortable: true,
           dataIndex: 'PRICE'
           },
		   {
           header: '税率%',
           width: 60,
           sortable: true,
           dataIndex: 'RATE',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 10000000000
           })            
           },           
           {
           header: '货款',
           width: 60,
           sortable: true,
           dataIndex: 'MONEY'
           },
           {
           header: '税金',
           width: 60,
           sortable: true,
           dataIndex: 'TAXES'
           },           
           {
           header: '<font color="red">▲</font>税价合计',
           width: 100,
           sortable: true,
           dataIndex: 'TOTAL_MONEY',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 10000000000
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
    	//物料列表
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
	        el: 'div_center',
	        autowidth:true,
	        clicksToEdit:1,
	        layout:'fit'
	    });
 
    grid.render();
    
    grid.on("afteredit", afterEdit, grid);

    function afterEdit(obj){
        if (Enabled=='false') return ;
    	var row = obj.record;//获取被修改的行
        var colName = obj.field;//获取被修改的列
        var returnMaterialId = row.get("RETURN_MATERIAL_ID");
        var colValue = row.get(colName);     
        updateMaterielValue(row,returnMaterialId,colName,colValue);
    }     
         
    //更新物料信息
    function updateMaterielValue(row,returnMaterialId,colName,colValue){
    	var requestUrl = 'salesReturnController.spr?action=updateMaterial';
		requestUrl = requestUrl + '&returnMaterialId=' + returnMaterialId;
		requestUrl = requestUrl + '&colName=' + colName;
		requestUrl = requestUrl + '&colValue=' + colValue;
		requestUrl = requestUrl + '&returnId=' + '${tSalesReturn.returnId}';
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseUtil1 = new AjaxResponseUtils(response.responseText);
				var customField = responseUtil1.getCustomField("coustom");  
					//row.set("TOTAL_MONEY",customField.total_money);
				if (colName == "QUANTITY" ||colName == "PRICE" ||colName == "TOTAL_MONEY" || colName == "RATE" ){
					row.set("PRICE",formatnumber(parseFloat(customField.price),6));
					row.set("QUANTITY",customField.quantity);
					row.set("TAXES",customField.taxes);	
					row.set("MONEY",customField.money);
					row.set("TOTAL_MONEY",customField.total);		
					ds.commitChanges();
					// alert(customField.netMoney);
					Ext.getDom('netMoney').value = formatnumber(parseFloat(customField.netMoney),2);
					Ext.getDom('taxMoney').value = formatnumber(parseFloat(customField.taxMoney),2);
					Ext.getDom('totalMoney').value = formatnumber(parseFloat(customField.totalMoney),2);																										
				}					   
			},
			failure:function(response, options){					
			}
		});    
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
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			height:240,
			xtype:'tabpanel',
			id:'infotype',
			name:'infotype',
			plain:true,
            autoScroll:'true',
            activeTab: 0,			
            items:[{
            	title:'已开票退货信息',
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
	            height:300,
	            autoScroll:'true',
	            activeTab: 0,
	            items:[{
			            	title:'物料信息',
			            	contentEl: '',
			            	id:'materialInfo',
							name:'materialInfo',
							autoScroll:'true',
			            	layout:'fit',
			            	items:[grid]
	                  }]
	             }],
	                  
				buttons:[{
	            	text:'保存',
    	            hidden:${Enabled==false},   
	            	handler:function(){
	            	    var param1 = Form.serialize('mainForm');	
	            	    grid.getSelectionModel().selectAll();
						var recoder = grid.selModel.getSelections();
						var param = param1 + "&action=save";
						new AjaxEngine('salesReturnController.spr', 
						   {method:"post", parameters: param, onComplete: callBackHandle});
						   strOperType = "3";
					}

	            },{
            	text:'提交',
    	        hidden:${Enabled==false},             	
            	handler:function(){            		
            	    grid.getSelectionModel().selectAll();
					var recoder = grid.selModel.getSelections();
            		var param1 = Form.serialize('mainForm');					

           			if (ds.getCount() > 0){
						var param = param1  + "&action=saveAndsubmit";
						new AjaxEngine('salesReturnController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});		   
						strOperType = '3';
					} else{
						top.Ext.Msg.show({
							title:'提示',
		  					msg: '没有增加物料信息,请确认是否真的要提交审批?',
		  					buttons: {yes:'是', no:'否'},
		  					fn: function(buttonId,text){
		  						if(buttonId=='yes'){
									var param = param1  + "&action=saveAndsubmit";
									new AjaxEngine('salesReturnController.spr', 
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
    	        hidden:${Enabled==false},   
            	handler:function(){
            		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
            	    top.ExtModalWindowUtil.markUpdated();
	                top.ExtModalWindowUtil.close();
	            }
            }]
		}]
	});//end fo viewPort

});


</script>
</head>
<body>
<div id='div_progressBar'></div>
<!-- <div id="div_basrForm">  -->
<div id="div_main" >
<form id="mainForm" action="" name="mainForm">
	 <table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable"> 
      <tr>
      	<input type="hidden" id = "returnId" name = "returnId" value="${tSalesReturn.returnId }"/>      
      	<input type="hidden" id = "contractSalesId" name = "contractSalesId" value="${tSalesReturn.contractSalesId }"/>
      	<input type="hidden" id = "shipId" name = "shipId" value="${tSalesReturn.shipId }"/> 
      	<input type="hidden" id = "returnType" name = "returnType" value="${tSalesReturn.returnType}"/>        	      	     	 	
      	<input type="hidden" id = "deptId" name = "deptId" value="${tSalesReturn.deptId}"/>   
      	<input type="hidden" id = "isAvailable" name = "isAvailable" value="${tSalesReturn.isAvailable}"/>  
      	<input type="hidden" id = "creatorTime" name = "creatorTime" value="${tSalesReturn.creatorTime}"/>  
      	<input type="hidden" id = "creator" name = "creator" value="${tSalesReturn.creator }"/>  
      	<input type="hidden" id = "examineState" name = "examineState" value="${tSalesReturn.examineState }"/> 
      	<input type="hidden" id = "applyTime" name = "applyTime" value="${tSalesReturn.applyTime }"/> 
      	<input type="hidden" id = "approvedTime" name = "approvedTime" value="${tSalesReturn.approvedTime }"/>         	       	       	      	      	      	        	      	      	      	
        <td width="11%" align="right">参考合同号<font color="red">▲</font></td>
        <td width="24%" align="left">
            <input id="contractNo" name="contractNo"  value="${tContractSalesInfo.contractNo }" size=16 readonly="readonly" />
        	<input type="button" value="..." onclick="selectContractSalesInfo()" />   
        	<a href="#" onclick="viewSaleContract()">查看</a>         
        </td>       	
        <td width="11%" align="right">合同号</td>
        <td width="22%" align="left">
            <input type="text" id = "returnNo" name = "returnNo" value="${tSalesReturn.returnNo}" readonly="readonly" />        
        </td>
        <td width="8%" align="right">合同名称</td>
        <td width="22%" align="left">
        	<input id="contractName" name="contractName"  value="${tContractSalesInfo.contractName}" size=16 readonly="readonly" />     
        </td>
      </tr>
		<tr>
			<td align="right">
				参考SAP订单号
			</td>
			<td>
			<input type="text" id="salesSapOrderNo" name="salesSapOrderNo" value="${tContractSalesInfo.sapOrderNo}" readonly="readonly"></input>
			</td>
			<td align="right">
				项目名称
			</td>
			<td>
			<input type="text" id="projectName" name="projectName" value="${tProjectInfo.projectName}" readonly="readonly"></input>
			</td>
			<td align="right">
				项目号
			</td>
			<td>
				<input type="text" id="projectNo" name="projectNo" value="${tProjectInfo.projectNo}" readonly="readonly"></input>
			</td>		
		</tr>       
		
		<tr>
			<td align="right">会计汇率<font color="red">▲</font></td>
			<td>
			<input type="text" id="rate" name="rate" value="${tSalesReturn.rate}"></input>
			</td>		
			<td align="right">SAP订单号</td>	
			<td >
		      	<input type="text" id = "sapOrderNo" name = "sapOrderNo" value="${tSalesReturn.sapOrderNo}" readonly="readonly" /> 				
			</td>	
			<td align="right">净金额</td>	
			<td>
		      	<input type="text" id = "netMoney" name = "netMoney" value="${tSalesReturn.netMoney}" readonly="readonly" /> 			
			</td>					
		</tr>
		<tr>
			<td align="right">SAP交货单号</td>
			<td>
		      	<input type="text" id = "sapDeliveryNo" name = "sapDeliveryNo" value="${tSalesReturn.sapDeliveryNo}" readonly="readonly" /> 
			</td>
			<td align="right">仓库</td>
			<td>
        	<div id="div_warehouse"></div>			
			</td>
			<td align="right">销项税金</td>
			<td>
	      	<input type="text" id = "taxMoney" name = "taxMoney" value="${tSalesReturn.taxMoney }"readonly="readonly" /> 
			</td>	
		</tr>
		<tr>
			<td align="right">SAP开票号</td>
			<td>
		      	<input type="text" id = "sapBillingNo" name = "sapBillingNo" value="${tSalesReturn.sapBillingNo}" readonly="readonly" /> 
			</td>
			<td align="right">红字发票号码</td>
			<td>
 	     	<input type="text" id = "invoiceNo" name = "invoiceNo" value="${tSalesReturn.invoiceNo}" readonly="readonly" />   
			</td>
			<td align="right">总金额</td>
			<td>
	      	<input type="text" id = "totalMoney" name = "totalMoney" value="${tSalesReturn.totalMoney }" readonly="readonly" /> 
			</td>	
		</tr>
		<tr>
			<td align="right">纸质合同号</td>
			<td><input type="text" name="paperContractNo" value="${tSalesReturn.paperContractNo}"/></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">备注</td>
			<td colspan=5>
		      	<textarea rows="3" cols="80" name="cmd">${tSalesReturn.cmd }</textarea>
			</td>			
		</tr>										     
	</table>
</form>
</div>
<!-- </div> -->
<div id="div_center"></div>
<div id="div_history" class="x-hide-display"></div>
<script type="text/javascript">
	function viewSaleContract(){
        var contractSalesId = Ext.getDom("contractSalesId").value;
        if(contractSalesId=='') {
           showMsg('请选择销售合同信息！');
           return ;
        }
        top.ExtModalWindowUtil.show('销售合同信息','contractController.spr?action=addSaleContractView&contractid='+contractSalesId,'',	'',{width:900,height:500});
	}
	function selectContractSalesInfo(){
        if (Enabled=='false') return ;	
		top.ExtModalWindowUtil.show('查询销售合同信息',
		'exportIncomeController.spr?action=selectSalesInfo',
		'',
		selectSalesInfoCallback,
		{width:900,height:450,closable:false});
	}
	var selectSalesInfoCallback=function(){
		var returnvalue = top.ExtModalWindowUtil.getReturnValue();
		$('contractSalesId').value = returnvalue.CONTRACT_SALES_ID;
		$('projectNo').value = returnvalue.PROJECT_NO;	
		$('projectName').value = returnvalue.PROJECT_NAME;				
		$('contractNo').value = returnvalue.CONTRACT_NO;
		$('contractName').value = returnvalue.CONTRACT_NAME;		
		$('salesSapOrderNo').value = returnvalue.SAP_ORDER_NO;	
		$('paperContractNo').value = returnvalue.OLD_CONTRACT_NO;	
		var requesturl = 'salesReturnController.spr?action=initSalesReturnMt&contractSalesId=' + returnvalue.CONTRACT_SALES_ID + '&returnId='+'${tSalesReturn.returnId}';
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

</script>
</body>
</html>
<fiscxd:dictionary divId="div_warehouse" fieldName="warehouse" dictionaryName="BM_WAREHOUSE" needDisplayCode="true" width="150" selectedValue="${tSalesReturn.warehouse}"></fiscxd:dictionary>
<fiscxd:workflow-taskHistory divId="div_history" businessRecordId="${tSalesReturn.returnId}" width="750"></fiscxd:workflow-taskHistory>

    
