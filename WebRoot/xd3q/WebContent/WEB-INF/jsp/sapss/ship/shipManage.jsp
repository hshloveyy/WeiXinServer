<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发货管理</title>
<style type="text/css">
.add{
	background-image:url(images/fam/add.gif) !important;
}
.delete{
	background-image:url(images/fam/delete.gif) !important;
}
.reset{
	background-image:url(images/fam/refresh.gif) !important;
}
.find{
	background-image:url(images/fam/find.png) !important;
}
</style>
</head>
<body>
<div id="div_northForm">
<form action="" id="findshipFrom" name="findshipFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">部门名称：</td>
	<td>
		<div id="dept"></div>
	</td>
	<td align="right">购（领）货单位：</td>
	<td><input type="text" id="unitName" name="unitName" ></input></td>
	<td align="right">纸质合同号：</td>
	<td>
		<input type="text" id="paperNo" name="paperNo" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">货物出仓单编码：</td>
	<td>
	    <input type="text" id="shipNo" name="shipNo" value=""></input>
	</td>
	<td align="right">合同编码：</td>
	<td>
		<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>
	<td align="right">SAP订单号：</td>
	<td>
		<input type="text" id="sapOrderNo" name="sapOrderNo" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">申报日期从：</td>
	<td>
		<div id="sDateDiv"></div>
	</td>
	<td align="right">到：</td>
	<td>
		<div id="eDateDiv"></div>
	</td>
	<td align="center">
	     SAP交货单号：</td>
	 
	 <td><input type="text" id="sapReturnNo" name="sapReturnNo" value=""></input>
		<input type="button" value="查找" onclick="findShipInfo()"></input>
		<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_center"></div>
<div id="div_ShipNotifycenter"></div>
<div id="div_south"></div>
</body>
</html>
<script type="text/javascript">
document.onkeydown = function(){if (event.keyCode == 13){findShipInfo();}}
//贸易类型
var tradeType = '${tradeType}';

var strshipTitle ='';
var strOperType = '0';

var shipRecord;
var purchaserfieldName;
strshipTitle = Utils.getTradeTypeTitle(tradeType);


var shipInfogrid;		//出仓单信息列表
var shipInfods;

//ship 查找按钮的单击事件
function findShipInfo(){
	var param = Form.serialize('findshipFrom');
	var requestUrl = 'shipController.spr?action=query&' + param;;
	requestUrl = requestUrl + "&tradeType=" + tradeType;	
	requestUrl = requestUrl + '&deptId=' + selectId_dept;	
	shipInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	shipInfods.load({params:{start:0, limit:10},arg:[]});
}

//ship 删除出仓单数据的回调函数
function customCallBackHandle(transport){
	var responseCustomUtil = new AjaxResponseUtils(transport.responseText);
	var customField = responseCustomUtil.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	
	if (strOperType == '1'){
		shipInfods.reload();
	}
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	
	//增加出仓单的回调函数
    function funAddshipCallBack(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	
    	var shipinfopt = new shipInfoPlant({
             ship_GROUP_ID: returnvalue.shipGroupId,
             ship_GROUP_NAME: returnvalue.shipGroupName,
             ship_GROUP_NO: returnvalue.shipGroupNo,
             CMD: returnvalue.cmd,
             PROJECT_ID: returnvalue.projectId,
             PROJECT_NAME: returnvalue.projectName,
             CREATE_TIME: returnvalue.createTime,
             USER_NAME: returnvalue.creator
         });
        shipInfogrid.stopEditing();
        shipInfods.insert(0, shipinfopt);
        shipInfogrid.startEditing(0, 0);
    }
    
    //增加
    var addshipInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			var requestUrl = 'shipController.spr?action=addShipInfo&isProduct=1&tradeType='+tradeType;
         
			Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				
				var shipinfopt = new shipInfoPlant({
	                    SHIP_ID: responseArray.shipId,
	                    EXAMINE_STATE: responseArray.examineState,
	                    RSEXAM_STATE_D_RSEXAM_STATE: '新增',
	                    CREATOR_TIME: responseArray.creatorTime,
	                    CREATOR: responseArray.creator
	                });
                shipInfogrid.stopEditing();
                shipInfods.insert(0, shipinfopt);
                shipInfogrid.startEditing(0, 0);
                shipRecord = shipInfogrid.getStore().getAt(0);
                operaForm(responseArray.shipId);
			}
		});
		}	    
   	});
   	
    //增加原材料出仓单
    var addshipInfo1 = new Ext.Toolbar.Button({
   		text:'增加原材料调拨出仓单',
	    iconCls:'add',
		handler:function(){
			var requestUrl = 'shipController.spr?action=addShipInfo&isProduct=0&tradeType='+tradeType;
			Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				
				var shipinfopt = new shipInfoPlant({
	                    SHIP_ID: responseArray.shipId,
	                    EXAMINE_STATE: responseArray.examineState,
	                    RSEXAM_STATE_D_RSEXAM_STATE: '新增',
	                    CREATOR_TIME: responseArray.creatorTime,
	                    CREATOR: responseArray.creator
	                });
                shipInfogrid.stopEditing();
                shipInfods.insert(0, shipinfopt);
                shipInfogrid.startEditing(0, 0);
                shipRecord = shipInfogrid.getStore().getAt(0);
                operaForm(responseArray.shipId);
			}
		});
		}	    
   	});
   	
    //增加成品出仓单
    var addshipInfo2 = new Ext.Toolbar.Button({
   		text:'增加成品出仓单/出口',
	    iconCls:'add',
		handler:function(){
			var requestUrl = 'shipController.spr?action=addShipInfo&isProduct=1&isHome=0&tradeType='+tradeType;
			Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				
				var shipinfopt = new shipInfoPlant({
	                    SHIP_ID: responseArray.shipId,
	                    EXAMINE_STATE: responseArray.examineState,
	                    RSEXAM_STATE_D_RSEXAM_STATE: '新增',
	                    CREATOR_TIME: responseArray.creatorTime,
	                    CREATOR: responseArray.creator
	                });
                shipInfogrid.stopEditing();
                shipInfods.insert(0, shipinfopt);
                shipInfogrid.startEditing(0, 0);
                shipRecord = shipInfogrid.getStore().getAt(0);
                operaForm(responseArray.shipId);
			}
		});
		}	    
   	});
   	
   	//增加成品出仓单
    var addshipInfo3 = new Ext.Toolbar.Button({
   		text:'增加成品出仓单/内贸',
	    iconCls:'add',
		handler:function(){
			var requestUrl = 'shipController.spr?action=addShipInfo&isProduct=1&isHome=1&tradeType='+tradeType;
			Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				
				var shipinfopt = new shipInfoPlant({
	                    SHIP_ID: responseArray.shipId,
	                    EXAMINE_STATE: responseArray.examineState,
	                    RSEXAM_STATE_D_RSEXAM_STATE: '新增',
	                    CREATOR_TIME: responseArray.creatorTime,
	                    CREATOR: responseArray.creator
	                });
                shipInfogrid.stopEditing();
                shipInfods.insert(0, shipinfopt);
                shipInfogrid.startEditing(0, 0);
                shipRecord = shipInfogrid.getStore().getAt(0);
                operaForm(responseArray.shipId);
			}
		});
		}	    
   	});
   	   	   	
   	
   	//删除
   	var deleteshipInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (shipInfogrid.selModel.hasSelection()){
				var records = shipInfogrid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个出仓单信息！');
				}else{
					if (records[0].data.EXAMINE_STATE != "1" )
						top.ExtModalWindowUtil.alert('提示','不能删除已送审的单据！');
					else
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=delete";
								param = param + "&shipId=" + records[0].data.SHIP_ID;

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
				top.ExtModalWindowUtil.alert('提示','请选择要删除的出仓单信息！');
			}
		}
   	});
    
    var shipInfotbar ;
   
	if (tradeType == 8 ){
	     shipInfotbar = new Ext.Toolbar({
			items:[addshipInfo1,'-',addshipInfo3,'-',deleteshipInfo]
		});
	}else if (tradeType == 12 ){
	     shipInfotbar = new Ext.Toolbar({
			items:[addshipInfo1,'-',addshipInfo2,'-',deleteshipInfo]
		});
	} else {
	     shipInfotbar = new Ext.Toolbar({
			items:[addshipInfo,'-',deleteshipInfo]
		});
	}
	var shipInfoPlant = Ext.data.Record.create([
		{name:'SHIP_ID'},                 
		{name:'EXPORT_APPLY_ID'},                 
		{name:'CONTRACT_SALES_ID'},                 
	    {name:'TRADE_TYPE'},                 
		{name:'PROJECT_NO'},                 
		{name:'PROJECT_NAME'},                 
		{name:'CONTRACT_PAPER_NO'},                 
		{name:'SALES_NO'},                 
		{name:'SAP_ORDER_NO'},         
		{name:'SAP_RETURN_NO'},     
		{name:'SERIALNO'},          
		{name:'SHIP_NO'},                 
		{name:'DECLARATIONS_NO'}, 
		{name:'WAREHOUSE'},                 
		{name:'WAREHOUSE_ADD'},                 
		{name:'BILL_STATE'},                 
	    {name:'SHIP_OPERATOR'},                 
		{name:'SHIP_NOTE'},                 
		{name:'SHIP_TIME'},                 
		{name:'DEPT_ID'},                 
		{name:'EXAMINE_STATE'},                 
		{name:'RSEXAM_STATE_D_RSEXAM_STATE'},                 
		{name:'APPLY_TIME'},                 
		{name:'APPROVED_TIME'}, 
		{name:'IS_AVAILABLE'},                 
		{name:'CREATOR_TIME'},                 
		{name:'CREATOR'}				                 
	]);

	shipInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'shipController.spr?action=query&tradeType='+tradeType}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},shipInfoPlant)
    });
    
    var shipInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var shipInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		shipInfosm,
		   {header: '出仓单ID',
           width: 100,
           sortable: true,
           dataIndex: 'SHIP_ID',
            hidden:true
           },                       
           {
               header: '操作',
               width: 100,
               sortable: true,
               dataIndex: 'EXPORTINFO',
               renderer: operaRD
           },        
           {
               header: '状态',
               width: 100,
               sortable: true,
               dataIndex: 'RSEXAM_STATE_D_RSEXAM_STATE'
               },
		　 {header: '立项号',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_NO'
           },
		　 {header: '立项名称',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_NAME'
           },           
		　 {header: '纸质合同号',
           width: 100,
           sortable: false,
           dataIndex: 'CONTRACT_PAPER_NO'
           },
           {header: '合同编码',
           width: 100,
           sortable: true,
           dataIndex: 'SALES_NO'
           },
           {
           header: 'SAP订单编号',
           width: 100,
           sortable: true,
           dataIndex: 'SAP_ORDER_NO'
           },
           {
           header: 'SAP交货单号',
           width: 100,
           sortable: true,
           dataIndex: 'SAP_RETURN_NO'
           },{
           header: '序号',
           width: 100,
           sortable: true,
           dataIndex: 'SERIALNO'
           },
           {
           header: '出仓单编码',
           width: 100,
           sortable: true,
           dataIndex: 'SHIP_NO'
           },
           {
           header: '申报日期',
           width: 100,
           sortable: true,
           dataIndex: 'CREATOR_TIME'
           },
           {
           header: '审批时间',
           width: 100,
           sortable: true,
           dataIndex: 'APPROVED_TIME'
           },                                
           {
           header: '状态',
           width: 100,
           sortable: true,
           dataIndex: 'EXAMINE_STATE',
           hidden: true
           }
    ]);
    shipInfocm.defaultSortable = true;
    
    var shipInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:shipInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    shipInfogrid = new Ext.grid.EditorGridPanel({
    	id:'shipInfoGrid',
        ds: shipInfods,
        cm: shipInfocm,
        sm: shipInfosm,
        bbar: shipInfobbar,
        tbar: shipInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    shipInfods.load({params:{start:0, limit:10},arg:[]});
    
    shipInfogrid.addListener('rowclick', shipInfogridrowclick);
    
    function shipInfogridrowclick(grid, rowIndex, e){
    	shipRecord = grid.getStore().getAt(rowIndex);
    }    
 
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: strshipTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:110
		},
		{
			region:"center",
			layout:'fit',
		    split:true,
			collapsible: true,
			height:220,
			minSize: 210,
            maxSize: 400,
			title: strshipTitle + "出仓单列表",
			items:[shipInfogrid]
		}]
	});
	
	var sDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'sDate',
		name:'sDate',
		width: 160,
	    readOnly:true,
		renderTo:'sDateDiv'
   	});
   	
   	var sDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'eDate',
	    name:'eDate',
		width: 160,
	    readOnly:true,
		renderTo:'eDateDiv'
   	});

});

//shipManage 修改出仓单链接
function operaRD(value,metadata,record){
	var state = record.data.EXAMINE_STATE;
	if('${loginer}'== record.data.CREATOR){
		if (state=='1'){    		
	   		return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" style="color:red" onclick="subimWorkflow()">提交</a>';
	  	 } if(state=='3'){
	  		return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a> <a href="#" onclick="writeOff()">冲销</a>';
		 } if(state=='7' || state=='8' ||state=='9'){
			return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="businessAllProcessRecords()">流程跟踪</a>';
		 }else{
	   		return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	   	}		
	}else{
		return '<a href="#" onclick="viewForm()">查看</a>';
	}
}
function openSubmitParticularWorkflow(){
	var records = shipInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('特批',//
			'shipController.spr?action=openSubmitParticularWorkflow&shipId='+ records[0].data.SHIP_ID + "&tradeType=" + tradeType + "&operType=001",
			'',
			ShipcallbackFunction,
			{width:900,height:550});
}
function businessAllProcessRecords(){
	var records = shipInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息',
	'workflowController.spr?action=businessAllProcessRecords&businessRecordId='+records[0].data.SHIP_ID,
	'',
	'',
	{width:800,height:500});	
}
function writeOff(){
    top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定冲销选择的记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var records = shipInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show(strshipTitle + '出仓冲销详情信息','shipController.spr?action=preWriteOff&shipId='+records[0].data.SHIP_ID+'&tradeType='+tradeType,
		'',	'',	{width:880,height:600});
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});

	
}
function operaForm(shipId){
	if (shipId == null || shipId == ''){
		var records = shipInfogrid.selModel.getSelections();		
		shipId = records[0].data.SHIP_ID;
	}

	top.ExtModalWindowUtil.show(strshipTitle + '出仓单信息',
		'shipController.spr?action=addShipInfoView&shipId='+ shipId + "&tradeType=" + tradeType + "&operType=111",
		'',
		ShipcallbackFunction,
		{width:900,height:550});
}

function subimWorkflow(shipId){
	if (shipId == null || shipId == ''){
		var records = shipInfogrid.selModel.getSelections();		
		shipId = records[0].data.SHIP_ID;
	}

	top.ExtModalWindowUtil.show(strshipTitle + '出仓单信息',
		'shipController.spr?action=addShipInfoView&shipId='+ shipId + "&tradeType=" + tradeType + "&operType=011",
		'',
		
		ShipcallbackFunction,
		{width:900,height:550});
}

function viewForm(shipId){
	if (shipId == null || shipId == ''){
		var records = shipInfogrid.selModel.getSelections();		
		shipId = records[0].data.SHIP_ID;
	}

	top.ExtModalWindowUtil.show(strshipTitle + '出仓单信息',
		'shipController.spr?action=addShipInfoView&shipId='+ shipId + "&tradeType=" + tradeType + "&operType=001",
		'',
		ShipcallbackFunction,
		{width:900,height:550});
}

function ShipcallbackFunction(){
		var shipId = shipRecord.get("SHIP_ID");
 		var requestUrl = 'shipController.spr?action=getShipInfo&shipId='+shipId;   	
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				shipRecord.set('PROJECT_NO',responseArray.projectNo);
				shipRecord.set('PROJECT_NAME',responseArray.projectName);
				shipRecord.set('CONTRACT_GROUP_NO',responseArray.contractGroupNo);
				shipRecord.set('SALES_NO',responseArray.salesNo);
				shipRecord.set('SAP_ORDER_NO',responseArray.sapOrderNo);
				shipRecord.set('EXAMINE_STATE',responseArray.examineState);	
				
				shipRecord.set('APPLY_TIME',responseArray.applyTime);	
				shipRecord.set('APPROVED_TIME',responseArray.approvedTime);	
				shipInfods.commitChanges();
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});
		var records = shipInfogrid.selModel.getSelections();
		if (records.length < 1){
			var model = shipInfogrid.getSelectionModel();
	        model.selectFirstRow();		
		}

}

//shipManage 查看出仓单信息 明细资料
function viewDetail(value,metadata,record){
	var state = record.data.PROJECT_STATE_D_PROJECT_STATE;
	if('${loginer}'== record.data.CREATOR){
		if (state=='立项'){    		
	   		return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" style="color:red" onclick="operaForm()">提交</a>';
	  	 	}else{
	   		return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	   	}		
	}else{
		return '<a href="#" onclick="viewForm()">查看立项</a>';
	}
}
function viewHistory(){
	var records = shipInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息',
	'workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.SHIP_ID+'&isRSHis=1',
	'',
	'',
	{width:900,height:365});	
}

</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"></fiscxd:dept>
