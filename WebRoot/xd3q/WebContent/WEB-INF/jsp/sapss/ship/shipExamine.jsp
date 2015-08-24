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

//取贸易名称

strshipTitle = Utils.getTradeTypeTitle(tradeType);


//document.title = strshipTitle + "货物出仓单" ;

var shipDetailInfogrid;		//出仓单明细列表
var shipDetailInfods;

function selectExportApplyWin(){
	top.ExtModalWindowUtil.show('查询所属登陆员工部门的出仓通知单信息',
	'shipController.spr?action=selectExportApply&tradeType=' + '${tShipInfo.tradeType}' +'&examineState=3&&deptid=',
	'',
	selectExportApplyCallBack,
	{width:900,height:450});
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
	var requesturl = 'shipController.spr?action=initShipInfo&exportApplyId=' + returnvalue.EXPORT_APPLY_ID;
	requesturl = requesturl + '&contractSalesId=' + returnvalue.CONTRACT_SALES_ID;
	requesturl = requesturl + '&shipId=' + '${tShipInfo.shipId}';
	//alert(requesturl);
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

function selectSalesWin(){
	top.ExtModalWindowUtil.show('查询所属登陆员工总门的合同信息',
	'shipNotifyController.spr?action=selectSalesInfo&tradeType=' + '${tShipInfo.tradeType}' +'&orderState=3&&deptid=',
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
	var requesturl = 'shipController.spr?action=initShipInfo&contractSalesId=' + returnvalue.CONTRACT_SALES_ID + '&exportApplyId='+'${tExportApply.exportApplyId}';
	requesturl = requesturl + '&shipId=' + '${tShipInfo.shipId}';
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

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if (strOperType == '1'){
		shipDetailInfods.reload();
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
    	requestUrl = requestUrl + "&exportApplyId=" + "${tShipInfo.exportApplyId}";  
    	requestUrl = requestUrl + "&contractSalesId=" + Ext.getDom('contractSalesId').value;   
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
						CREATOR: responseArray.creator	                    
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
    	requestUrl = requestUrl + "&shipId=" + "${tShipInfo.shipId}";  
    	requestUrl = requestUrl + "&exportApplyId=" + "${tShipInfo.exportApplyId}";  
    	requestUrl = requestUrl + "&contractSalesId=" + Ext.getDom('contractSalesId').value; 
    	requestUrl = requestUrl + "&hasQuantity=" + returnValue.HAS_QUANTITY;     
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
						CREATOR: responseArray.creator	                    
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
    
    //增加出仓单明细资料(物料)
    var addshipDetailInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			var contractSalesId = Ext.getDom('contractSalesId').value;
			var exportApplyId = Ext.getDom('exportApplyId').value;
			if (contractSalesId == '' && exportApplyId == ''){
				top.ExtModalWindowUtil.alert('提示','请先选择合同或通知单才能增加物料信息！');
				return;				
			}
			if (exportApplyId != ''){
				top.ExtModalWindowUtil.show(strshipTitle+'货物出仓单登记表',
				'shipController.spr?action=toFindExportApplyMaterial&exportApplyId='+exportApplyId + "&tradeType=" + tradeType,
				'',
				funAddshipDetail2CallBack,
				{width:800,height:500});			
			
			} else {
				top.ExtModalWindowUtil.show(strshipTitle+'货物出仓单登记表',
				'shipNotifyController.spr?action=toFindSalesMaterial&shipType=1&contractSalesId='+contractSalesId + "&tradeType=" + tradeType,
				'',
				funAddshipDetailCallBack,
				{width:800,height:500});
			}
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
   								var param = "?action=deleteShipMateriel";
								param = param + "&shipDetailId=" + records[0].data.SHIP_DETAIL_ID;

								new AjaxEngine('shipController.spr', 
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
    var shipDetailInfotbar = new Ext.Toolbar({
		items:[addshipDetailInfo,'-',deleteshipDetailInfo]
	  });
	  
	var shipDetailInfoPlant = Ext.data.Record.create([
		{name:'SHIP_DETAIL_ID'},                 //行ID
		{name:'SAP_ROW_NO'},                 //行ID
		{name:'SHIP_ID'},                        //主表ID
		{name:'MATERIAL_CODE'},                 //物料编码
	    {name:'MATERIAL_NAME'},                 //物料名称
		{name:'MATERIAL_UNIT'},                 //物料单位
		{name:'OLD_QUANTITY'},	
		{name:'QUANTITY'},                      //数量
		{name:'BATCH_NO'},                      //批次号
		{name:'PRICE'},                         //单价
		{name:'CURRENCY'},                     //币别
		{name:'TOTAL'},                        //总计
		{name:'CMD'}                            //备注
	]);

	shipDetailInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'shipController.spr?action=queryMaterial&shipId='+'${tShipInfo.shipId}'}),
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
           dataIndex: 'ship_GROUP_ID',
            hidden:true
           },
		   {header: '主表ID',
           width: 100,
           sortable: true,
           dataIndex: 'SHIP_ID',
            hidden:true
           },           
           {header: '行项目ID',
           width: 100,
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
               maxValue: 100000
           })            
           },
           {
           header: '批次号',
           width: 100,
           sortable: true,
           dataIndex: 'BATCH_NO',
           editor: new fm.TextField({
               allowBlank: true
           })           
           },
           {
           header: '单价(采购)',
           width: 100,
           sortable: true,
           dataIndex: 'PRICE',
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
        var shipDetailId = row.get("SHIP_DETAIL_ID");
        var colValue = row.get(colName);
        
        if (colName == 'QUANTITY' || colName == 'PRICE' ){
        	//setMaterielTotal(row);
        }       
        updateMaterielValue(row,shipDetailId,colName,colValue);

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
					if (colName == "QUANTITY"){
						top.ExtModalWindowUtil.alert('提示','输入的申请数量超过合同物料数量！');					
						row.set("QUANTITY",row.get("OLD_QUANTITY"));
					}				
				}
				else {
				   if (colName == "QUANTITY" || colName == "PRICE" ){
						row.set("TOTAL",customField.total);						
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
        var shipDetailId = row.get("SHIP_DETAIL_ID");
     	var price = row.get("PRICE");
     	var quantity = row.get("QUANTITY");    	
     	if (quantity == null || quantity == ''){
     		quantity = "0";
     	}
     	if (price == null || price == ''){
     		price = "0";
     	}    
     	var total = parseFloat(quantity) *  parseFloat(price);
     	
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
	        
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			collapsible: true,
			height:200,
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
	            height:300,
	            autoScroll:'true',
	            activeTab: 0,
	            items:[{
			            	title:strshipTitle+'出仓单明细信息',
			            	contentEl: '',
			            	id:'shipDetailInfo',
							name:'shipDetailInfo',
							autoScroll:'true',
			            	layout:'fit',
			            	items:[shipDetailInfogrid]
	                  }]
	             }]
		       }]
	});
	//---------------------------------------
/*	new Ext.ToolTip({
        target:'sapReturnNo',
        width:200,
        html: $('sapReturnNo').value==''?'':$('sapReturnNo').value,
        trackMouse:true
    });*/
	new Ext.ToolTip({
        target:'sapOrderNo',
        width:200,
        html: $('sapOrderNo').value==''?'':$('sapOrderNo').value,
        trackMouse:true
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
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否退出并关闭窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
 			if(buttonId=='yes'){
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
      	<input type="hidden" id = "shipId" name = "shipId" value="${tShipInfo.shipId }"/>
      	<input type="hidden" id = "contractSalesId" name = "contractSalesId" value="${tShipInfo.contractSalesId }"/>
      	<input type="hidden" id = "tradeType" name = "tradeType" value="${tShipInfo.tradeType }"/>
      	<input type="hidden" id = "billState" name = "billState" value="${tShipInfo.billState }"/>    
      	<input type="hidden" id = "applyTime" name = "applyTime" value="${tShipInfo.applyTime }"/>
      	<input type="hidden" id = "approvedTime" name = "approvedTime" value="${tShipInfo.approvedTime }"/>
      	<input type="hidden" id = "isAvaiLable" name = "isAvaiLable" value="${tShipInfo.isAvaiLable }"/>
      	<input type="hidden" id = "creator" name = "creator" value="${tShipInfo.creator }"/>  
      	<input type="hidden" id = "exportApplyId" name = "exportApplyId" value="${tShipInfo.exportApplyId }"/> 
      	<input type="hidden" id = "isProduct" name = "isProduct" value="${tShipInfo.isProduct }"/>   
        <input type="hidden" id="shipOperator" name="shipOperator"  value="${tShipInfo.shipOperator}"  />   

         <c:choose>
         	<c:when test="${tShipInfo.tradeType=='2' || tShipInfo.tradeType == '4' || tShipInfo.tradeType == '6' || (tShipInfo.tradeType == '8' && tShipInfo.isProduct == '1')}">
		        <td width="11%" align="right">通知单号：</td>
		        <td width="22%" align="left">
		        	<input type="text" id="noticeNo" name="noticeNo" value="${tExportApply.noticeNo}" readonly="readonly" />
		        	 <input type="button" value="..." onclick="selectExportApplyWin()"></input>
		        	 <input type="hidden" id="salesNo" name="salesNo" value="${tShipInfo.salesNo}" readonly="readonly" />
		        </td>
      		</c:when>
       		<c:otherwise>
		        <td width="11%" align="right">销售合同号：</td>
		        <td width="22%" align="left">
		        	<input type="text" id="salesNo" name="salesNo" value="${tShipInfo.salesNo}" readonly="readonly" />
		        	 <input type="button" value="..." onclick="selectSalesWin()"></input>
		        </td>     			
       		</c:otherwise>
         </c:choose>	      	

        <td width="11%" align="right">出仓单号：</td>
        <td width="22%" align="left">
            <input id="shipNo" name="shipNo"  value="${tShipInfo.shipNo }"/>
        </td>
        <td width="11%" align="right">单据状态：</td>
        <td width="22%" align="left">
        	<div id="div_examineState"></div>
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
        <td  align="right">仓库：</td>
        <td  align="left">
        	<div id="div_warehouse"></div>
        </td>
        <td  align="right">仓库地址：</td>
        <td  align="left">
        	<input id="warehouseAdd" name="warehouseAdd" type="text" value="${tShipInfo.warehouseAdd}"  />        	
        </td>
        <td  align="right">申报日期：</td>
        <td  align="left">
            <input id="creatorTime" name="creatorTime" type="text" value="${tShipInfo.creatorTime}" readonly="readonly" />
        </td>         
      </tr>
      <tr>
      	<td align="right">
      		业务类型:
      	</td>
      	<td align="left">
      		<input type="text" value="${tradeTypeName}"/> 
      	</td>
        <td align="right">出仓时间</td>
      	<td align="left">
      		<input type="text" id="shipTime" name="shipTime"  value="${tShipInfo.shipTime}"  readonly="readonly"/>   
      	</td>
      
        <td  align="right">出仓执行意见：</td>
        <td align="left" >     
           <input type="text" id="shipNote" name="shipNote"  value="${tShipInfo.shipNote}"  />  
        </td> 
      </tr>
	</table>
</form>
</div>
<!-- </div> -->
<div id="div_center"></div>
</body>
</html>
<fiscxd:dictionary divId="div_examineState" fieldName="examineState" dictionaryName="BM_EXAMINE_STATE" width="150" selectedValue="${tShipInfo.examineState}" disable="true" ></fiscxd:dictionary>
<fiscxd:dictionary divId="div_warehouse" fieldName="warehouse" dictionaryName="BM_WAREHOUSE" needDisplayCode="true" width="150" selectedValue="${tShipInfo.warehouse}"></fiscxd:dictionary>
    
