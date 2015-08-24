<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出口货物通知单管理</title>
<style type="text/css">
.add {
	background-image: url(images/fam/add.gif) !important;
}

.delete {
	background-image: url(images/fam/delete.gif) !important;
}

.reset {
	background-image: url(images/fam/refresh.gif) !important;
}

.find {
	background-image: url(images/fam/find.png) !important;
}
</style>
</head>
<body>

<div id="div_northForm">
<form action="" id="findshipNotifyFrom" name="findshipNotifyFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1"
	bordercolor="#6699FF" class="datatable">
	<tr>
		<td align="right">部门名称：</td>
		<td>
		<div id="dept"></div>
		</td>
		<td align="right">立项号：</td>
		<td><input type="text" id="projectNo" name="projectNo" value=""></input>
		</td>
		<td align="right">合同组编码：</td>
		<td><input type="text" id="contractGroupNo"
			name="contractGroupNo" value=""></input></td>
	</tr>
	<tr>
		<td align="right">通知单号：</td>
		<td><input type="text" id="noticeNo" name="noticeNo" value=""></input>
		</td>
		<td align="right">合同编码：</td>
		<td><input type="text" id="contractNo" name="contractNo" value=""></input>
		</td>
		<td align="right">SAP订单号：</td>
		<td><input type="text" id="sapOrderNo" name="sapOrderNo" value=""></input>
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
		<td align="right">是否出单</td>
		<td><select name="isBill"><option value="">请选择</option><option value="1">是</option><option value="0">否</option></select></td>
	</tr>
	<tr>
		<td align="right">领单时间：</td>
		<td>
		<div id="sGetSheetTimeDiv"></div>
		</td>
		<td align="right">到：</td>
		<td>
		<div id="eGetSheetTimeDiv"></div>
		</td>
		<td>合同名称</td>
		<td><input type="text" id="contractName" name="contractName" value=""></td>
	</tr>
	<tr>
		<td align="right">贸易方式：</td>
		<td>
		<div id="div_tradeType"></div>
		</td>
		<td align="right">核销单号：</td>
		<td><input type="text" id="writeNo" name="writeNo" value="">
		</td>
		<td colspan="2" align="center"><input type="button" value="查找"
			onclick="findShipNotifyInfo()"></input> <input type="reset"
			value="清空"></input><a onbeforeactivate="onbeforClickA()" href="shipNotifyController.spr?action=dealOutToExcel" id="aaa" target="_self">下载</a></td>
	</tr>
</table>
</form>
</div>

<div id="div_center"></div>

<div id="div_south"></div>
</body>
</html>
<script type="text/javascript">
function onbeforClickA(){
	var param = Form.serialize('findshipNotifyFrom');
	var requestUrl = 'shipNotifyController.spr?action=dealOutToExcelV1&' + param;
	requestUrl = requestUrl + '&deptId=' + selectId_dept;
	$('aaa').href=requestUrl;
}
document.onkeydown = function(){if (event.keyCode == 13){findShipNotifyInfo();}}
//贸易类型
var tradeType = '${tradeType}';

var strshipNotifyTitle ='';
var strOperType = '0';

var purchaserrecord;
var shipNotifyRecord;
var purchaserfieldName;
strshipNotifyTitle = Utils.getTradeTypeTitle(tradeType);


var shipNotifyInfogrid;		//出仓单信息列表
var shipNotifyInfods;

//shipNotify 查找按钮的单击事件
function findShipNotifyInfo(){
	var param = Form.serialize('findshipNotifyFrom');
	var requestUrl = 'shipNotifyController.spr?action=query&' + param;
	requestUrl = requestUrl + "&tradeType=" + tradeType;
	requestUrl = requestUrl + '&deptId=' + selectId_dept;
	//alert(requestUrl);
	
	shipNotifyInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	shipNotifyInfods.load({params:{start:0, limit:10},arg:[]});
}

//shipNotify 删除出口货物通知单的回调函数
function customCallBackHandle(transport){
	var responseCustomUtil = new AjaxResponseUtils(transport.responseText);
	var customField = responseCustomUtil.getCustomField("coustom");
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if (strOperType == '1'){
		shipNotifyInfods.reload();
	}
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	    
    //增加
    var addshipNotifyInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			var requestUrl = 'shipNotifyController.spr?action=addShipNotify&tradeType='+tradeType;
			Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				
				var shipNotifyinfopt = new shipNotifyInfoPlant({
	                    EXPORT_APPLY_ID: responseArray.exportApplyId,
	                    NOTICE_NO: responseArray.noticeNo,
	                    EXAMINE_STATE: responseArray.examineState,
	                    EXAMINE_STATE_D_EXAMINE_STATE: '新增',
	                    CREATOR_TIME: responseArray.creatorTime,
	                    CREATOR: responseArray.creator
	                });
                shipNotifyInfogrid.stopEditing();
                shipNotifyInfods.insert(0, shipNotifyinfopt);
                shipNotifyInfogrid.startEditing(0, 0);
                shipNotifyRecord = shipNotifyInfogrid.getStore().getAt(0);
                operaForm(responseArray.exportApplyId);
			}
		});
		}
   	});
   	
   	//删除
   	var deleteshipNotifyInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (shipNotifyInfogrid.selModel.hasSelection()){
				var records = shipNotifyInfogrid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个出口货物通知单信息！');
				}else{
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=delete";
								param = param + "&exportApplyId=" + records[0].data.EXPORT_APPLY_ID;

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
				top.ExtModalWindowUtil.alert('提示','请选择要删除的出口货物通知单信息！');
			}
		}
   	});
   	    
    var shipNotifyInfotbar = new Ext.Toolbar({
//		items:[addshipNotifyInfo,'-',deleteshipNotifyInfo]
		items:['-'<c:if test="${deptShortCode=='ZH2'}">,deleteshipNotifyInfo</c:if>]
	});
	
	var shipNotifyInfoPlant = Ext.data.Record.create([
		{name:'EXPORT_APPLY_ID'}, 
		{name:'CONTRACT_SALES_ID'},                 
		{name:'PROJECT_NO'},                    
		{name:'PROJECT_NAME'},                   
	    {name:'CONTRACT_GROUP_NO'},   
	    {name:'CONTRACT_NAME'},            
		{name:'SALES_NO'},                       
		{name:'SAP_ORDER_NO'},
		{name:'BILL_STATE'},                     
		{name:'NOTICE_NO'},   
		{name:'EXCHANGE_TYPE'}, 
		{name:'ORIGIN'},                 
		{name:'SHIPMENT_DATE'},                    
		{name:'LCNO'},                   
	    {name:'COMMITMENT'},               
		{name:'EXPORT_PORT'},                       
		{name:'WRITE_NO'},
		{name:'SUPPLIER'},                     
		{name:'DESTINATIONS'},  
		{name:'TRADE_TERMS'},   
		{name:'DEPT_ID'}, 
		{name:'EXAMINE_STATE'},    
		{name:'EXAMINE_STATE_D_EXAMINE_STATE'},              
		{name:'APPLY_TIME'},                    
		{name:'APPROVED_TIME'},                   
	    {name:'IS_AVAILABLE'},               
		{name:'CREATOR_TIME'},                       
		{name:'CREATOR'},
		{name:'GET_SHEET_TIME'},	
		{name:'EXPORTINFO'}  
	]);

	shipNotifyInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'shipNotifyController.spr?action=query&tradeType='+tradeType}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},shipNotifyInfoPlant)
    });
    
    var shipNotifyInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var shipNotifyInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		shipNotifyInfosm,
		   {header: '申请表ID',
           width: 100,
           sortable: true,
           dataIndex: 'EXPORT_MATE_ID',
            hidden:true
           },{           
               header: '操作',
               width: 100,
               sortable: true,
               dataIndex: 'EXPORTINFO',
               renderer: operaRD
           },{
               header: '状态',
               width: 100,
               sortable: true,
               dataIndex: 'EXAMINE_STATE_D_EXAMINE_STATE'
           },{                       
		　 		header: '立项号',
          		width: 100,
           		sortable: true,
           		dataIndex: 'PROJECT_NO'
           },
           {header: '立项名称',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_NAME'
           // hidden:true
           },
		　 {header: '合同组编号',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_GROUP_NO'
           },
		　 {header: '销售合同号',
           width: 100,
           sortable: false,
           dataIndex: 'SALES_NO'
           },
           {header: '合同名称',
           width: 100,
           sortable: false,
           dataIndex: 'CONTRACT_NAME'
           },
           {header: 'SAP订单号',
           width: 100,
           sortable: true,
           dataIndex: 'SAP_ORDER_NO'
           },
           {
           header: '通知单号',
           width: 100,
           sortable: true,
           dataIndex: 'NOTICE_NO'
           },           
           {
           header: '核销单号',
           width: 100,
           sortable: true,
           dataIndex: 'WRITE_NO'
           },
           {
           header: '领单时间',
           width: 90,
           sortable: true,
           dataIndex: 'GET_SHEET_TIME'
           }, 
           {
           header: '申报时间',
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
    shipNotifyInfocm.defaultSortable = true;
    
    var shipNotifyInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:shipNotifyInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    shipNotifyInfogrid = new Ext.grid.EditorGridPanel({
    	id:'shipNotifyInfoGrid',
        ds: shipNotifyInfods,
        cm: shipNotifyInfocm,
        sm: shipNotifyInfosm,
        bbar: shipNotifyInfobbar,
        tbar: shipNotifyInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    shipNotifyInfods.load({params:{start:0, limit:10},arg:[]});
    
   shipNotifyInfogrid.addListener('rowclick', shipNotifyInfogridrowclick);

    function shipNotifyInfogridrowclick(grid, rowIndex, e){
    	shipNotifyRecord = grid.getStore().getAt(rowIndex);
    }
    
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: strshipNotifyTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:151
		},{
			region:"center",
			layout:'fit',
			title: strshipNotifyTitle + "出口货物通知单列表",
			items:[shipNotifyInfogrid]
		}]
	});
	
	Ext.apply(Ext.form.VTypes, {
		daterange: function(val, field) {
			var date = field.parseDate(val);
			
			var dispUpd = function(picker) {
				var ad = picker.activeDate;
				picker.activeDate = null;
				picker.update(ad);
			};
			
			if (field.startDateField) {
				var sd = Ext.getCmp(field.startDateField);
				sd.maxValue = date;
				if (sd.menu && sd.menu.picker) {
					sd.menu.picker.maxDate = date;
					dispUpd(sd.menu.picker);
				}
			} else if (field.endDateField) {
				var ed = Ext.getCmp(field.endDateField);
				ed.minValue = date;
				if (ed.menu && ed.menu.picker) {
					ed.menu.picker.minDate = date;
					dispUpd(ed.menu.picker);
				}
			}
			return true;
		}
	});
	
	var sDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'sDate',
		name:'sDate',
		width: 160,
	    readOnly:true,
		vtype: 'daterange',
		renderTo:'sDateDiv',
		endDateField:'eDate'
   	});
   	
   	var eDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'eDate',
	    name:'eDate',
		width: 160,
	    readOnly:true,
		vtype: 'daterange',
		renderTo:'eDateDiv',
		startDateField:'sDate'
   	});

	var sGetSheetTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'sGetSheetTime',
		name:'sGetSheetTime',
		width: 160,
	    readOnly:true,
		vtype: 'daterange',
		renderTo:'sGetSheetTimeDiv',
		endDateField:'eGetSheetTime'
   	});
   	
   	var eGetSheetTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'eGetSheetTime',
	    name:'eGetSheetTime',
		width: 160,
	    readOnly:true,
		vtype: 'daterange',
		renderTo:'eGetSheetTimeDiv',
		startDateField:'sGetSheetTime'
   	});
});

function ShipNotifycallbackFunction(){
		var exportApplyId = shipNotifyRecord.get("EXPORT_APPLY_ID");
 		var requestUrl = 'shipNotifyController.spr?action=getExportApply&exportApplyId='+exportApplyId;   	
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				shipNotifyRecord.set('PROJECT_NO',responseArray.projectNo);
				shipNotifyRecord.set('PROJECT_NAME',responseArray.projectName);
				shipNotifyRecord.set('CONTRACT_GROUP_NO',responseArray.contractGroupNo);
				shipNotifyRecord.set('SALES_NO',responseArray.salesNo);
				shipNotifyRecord.set('SAP_ORDER_NO',responseArray.sapOrderNo);
				shipNotifyRecord.set('NOTICE_NO',responseArray.noticeNo);
				shipNotifyRecord.set('EXAMINE_STATE',responseArray.examineState);	
				shipNotifyRecord.set('APPLY_TIME',responseArray.applyTime);	
				shipNotifyRecord.set('APPROVED_TIME',responseArray.approvedTime);	
				shipNotifyInfods.commitChanges();
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});
	    var records = shipNotifyInfogrid.selModel.getSelections();
		if (records.length < 1){
			var model = shipNotifyInfogrid.getSelectionModel();
	        model.selectFirstRow();		
		}
}
//shipNotifyManage 修改出口货物通知单链接
function operaRD(value,metadata,record){
	var state = record.data.EXAMINE_STATE;
	if('${loginer}'== record.data.CREATOR){
		if (state=='1'){    		
	   		return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" style="color:red" onclick="operaForm()">提交</a>';
	  	 	}else{
	   		return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	   	}		
	}else{
		if (state=='1')
			return '<a href="#" onclick="viewForm()">查看</a>';
		else
			return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	}
}
function operaForm(exportApplyId){
	if (exportApplyId == null || exportApplyId == ''){
		var records = shipNotifyInfogrid.selModel.getSelections();		
		exportApplyId = records[0].data.EXPORT_APPLY_ID;
	}

	top.ExtModalWindowUtil.show(strshipNotifyTitle + '出口货物通知单信息',
		'shipNotifyController.spr?action=addShipNotifyView&exportApplyId='+ exportApplyId + "&operType=11",
		'',
		ShipNotifycallbackFunction,
		{width:900,height:550});
}
function viewForm(exportApplyId){
	if (exportApplyId == null || exportApplyId == ''){
		var records = shipNotifyInfogrid.selModel.getSelections();		
		exportApplyId = records[0].data.EXPORT_APPLY_ID;
	}

	top.ExtModalWindowUtil.show(strshipNotifyTitle + '出口货物通知单信息',
		'shipNotifyController.spr?action=addShipNotifyView&exportApplyId='+ exportApplyId + "&operType=00",
		'',
		ShipNotifycallbackFunction,
		{width:900,height:550});
}

function viewHistory(){
	var records = shipNotifyInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息',
	'workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.EXPORT_APPLY_ID,
	'',
	'',
	{width:900,height:365});	
}
</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"
	isMutilSelect="true"></fiscxd:dept>
<fiscxd:dictionary divId="div_tradeType" fieldName="tradeType"
	dictionaryName="BM_BUSINESS_TYPE" width="153"></fiscxd:dictionary>