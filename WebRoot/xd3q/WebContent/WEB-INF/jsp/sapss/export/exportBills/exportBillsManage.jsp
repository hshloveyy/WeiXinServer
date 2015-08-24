<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出口押汇管理</title>
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
<form action="" id="findExportBillsFrom" name="findExportBillsFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">部门编码：</td>
	<td>
		<div id="deptCode"></div>
	</td>
	<td align="right">出口押汇单号：</td>
	<td>
		<input type="text" id="exportBillNo" name="exportBillNo" value=""></input>
	</td>
	<td align="right">申请人：</td>
	<td>
		<input type="text" id="realName" name="realName" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">备注：</td>
	<td>
		<input type="text" id="cmd" name="cmd" value=""></input>
	</td>
	<td align="right">实际押汇金额：</td>
	<td>
		<input type="text" id="realMoney" name="realMoney" value="" size="12"></input>-
		<input type="text" id="realMoney1" name="realMoney1" value="" size="12"></input>
	</td>
	<td align="right">
	　</td>
	<td>
		
	  </td>
</tr>

<tr>
	<td align="right">出口日期从：</td>
	<td>
		<div id="sDateDiv"></div>
	</td>
	<td align="right">到：</td>
	<td>
		<div id="eDateDiv"></div>
	</td>
	<td align="center" colspan="2">
		<input type="button" value="查找" onclick="findExportBillsInfo()"></input>
		<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_center"></div>
</body>
</html>
<script type="text/javascript"><!--
document.onkeydown = function(){if (event.keyCode == 13){findExportBillsInfo();}}
//贸易类型
var tradeType = '${tradeType}';

var ExportBillsTitle ='出口押汇单';
var strOperType = '0';
ExportBillsTitle = Utils.getTradeTypeTitle(tradeType);


var exportBillsInfogrid;		//信息列表

//exportBills 查找按钮的单击事件
function findExportBillsInfo(){
	//var sDate= Ext.getDom("sDate").value;
    //var eDate= Ext.getDom("eDate").value;
    
	var param = Form.serialize('findExportBillsFrom');
	var requestUrl = 'exportController.spr?action=queryExportBills&' + param;
	requestUrl = requestUrl + "&tradeType=" + tradeType;	
	//alert(requestUrl);
	ExportBillsInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	ExportBillsInfods.load({params:{start:0, limit:10},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	
	//增加出货押汇单的回调函数
    function funAddExportBillsCallBack(){
    	ExportBillsInfods.reload();
    }
    
    //增加
    var addExportBillsInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('新增出货押汇单',
			'exportController.spr?action=createExportBills&tradeType='+tradeType,
			'',
			funAddExportBillsCallBack,
			{width:800,height:480});
		}
   	});
    	
   	//删除
   	var deleteExportBillsInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (exportBillsInfogrid.selModel.hasSelection()){
				var records = exportBillsInfogrid.selModel.getSelections();
				
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个出货押汇单信息！');
				}else{				
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除选择的记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=deleteExportBills";
								param = param + "&exportBillId=" + records[0].data.EXPORT_BILL_ID;
//alert(param);
								new AjaxEngine('exportController.spr', 
						   			{method:"post", parameters: param, onComplete: DeleteExportBillsCallbackFunction});
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的出货押汇单信息！');
			}
		}
   	});
    
    var ExportBillsInfotbar = new Ext.Toolbar({
		items:[addExportBillsInfo,'-',deleteExportBillsInfo]
	});
	
	var ExportBillsInfoPlant = Ext.data.Record.create([
		{name:'EXPORT_BILL_ID'},  			//出货押汇ID
		{name:'EXPORT_BILL_NO'},  			//出货押汇NO
		{name:'EXPORT_BILL_EXAM_ID'},  		//出口出单审单记录ID
		{name:'CREATOR'},             		//创建者
		{name:'DEPT_ID'},                 	//部门代码
		{name:'DEPT_NAME'},                 	//部门代码
		{name:'REAL_NAME'},                 	//部门代码
		{name:'CURRENCY'},                 
		{name:'BANK'},                 	
		{name:'APPLY_MONEY'},            	//申请押汇金额
		{name:'REAL_MONEY'},            	//实际押汇金额
		{name:'CMD'},                       //备注
		{name:'APPLY_TIME'},				//申请日期
		{name:'APPROVED_TIME'},				//审批通过时间
		{name:'IS_AVAILABLE'},				//0 无效	1 有效
		{name:'CREATOR_TIME'},				//创建时间
		{name:'EXAMINE_STATE'},
		{name:'EXAMINE_STATE_D_EXAMINE_STATE'}
	]);

	ExportBillsInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'exportController.spr?action=queryExportBills&tradeType='+tradeType}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},ExportBillsInfoPlant)
    });
    
    var ExportBillsInfosm = new Ext.grid.CheckboxSelectionModel();    
    var ExportBillsInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		ExportBillsInfosm,
		　 {header: '出货押汇ID',
           width: 100,
           sortable: true,
           dataIndex: 'EXPORT_BILL_ID',
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
               dataIndex: 'EXAMINE_STATE_D_EXAMINE_STATE'
               },                    
           {header: '出货押汇单号',
           width: 100,
           sortable: true,
           dataIndex: 'EXPORT_BILL_NO'
           },
  		　 {header: '出口出单审单记录ID',
           width: 100,
           sortable: true,
           hidden:true,
           dataIndex: 'EXPORT_BILL_EXAM_ID'
           },
           {header: '创建者',
           width: 100,
           sortable: true,
           dataIndex: 'REAL_NAME'
           },
           {header: '部门',
           width: 100,
           sortable: true,
           dataIndex: 'DEPT_NAME'
           },
           {header: '创建者',
           width: 100,
           sortable: true,
           hidden:true,
           dataIndex: 'CREATOR'
           },
           {header: '部门代码',
           width: 100,
           sortable: true,
           hidden:true,
           dataIndex: 'DEPT_ID'
           },
           {
           header: '申请押汇金额',
           width: 100,
           sortable: true,
           dataIndex: 'APPLY_MONEY'
           },
           {
           header: '实际押汇金额',
           width: 100,
           sortable: true,
           dataIndex: 'REAL_MONEY'
           },                                
           {header: '银行',
           width: 100,
           sortable: true,
           dataIndex: 'BANK'
           },
           {header: '币种',
           width: 50,
           sortable: true,
           dataIndex: 'CURRENCY'
           },
           {
           header: '备注',
           width: 100,
           sortable: true,
           dataIndex: 'CMD'
           },
           {
           header: '创建时间',
           width: 100,
           sortable: true,
           dataIndex: 'CREATOR_TIME'
           },             
           {
           header: '申请日期',
           width: 100,
           sortable: true,
           dataIndex: 'APPLY_TIME'
           },                    
           {
           header: '审批通过时间',
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
    ExportBillsInfocm.defaultSortable = true;
    
    var ExportBillsInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:ExportBillsInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    exportBillsInfogrid = new Ext.grid.EditorGridPanel({
    	id:'exportBillsInfogrid',
        ds: ExportBillsInfods,
        cm: ExportBillsInfocm,
        sm: ExportBillsInfosm,
        bbar: ExportBillsInfobbar,
        tbar: ExportBillsInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    ExportBillsInfods.load({params:{start:0, limit:10},arg:[]});
    
 //exportBillsInfogrid.addListener('rowclick', exportBillsInfogridrowclick);

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: ExportBillsTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:107
		},{
			region:"center",
			layout:'fit',
			title: ExportBillsTitle + "出货押汇单列表",
			items:[exportBillsInfogrid]
		}]
	});
	
	var sDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'sDate',
		name:'sDate',
		width: 100,
	    readOnly:true,
		renderTo:'sDateDiv'
   	});
   	
   	var eDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'eDate',
	    name:'eDate',
		width: 100,
	    readOnly:true,
		renderTo:'eDateDiv'
   	});

});

function operaRD(value,metadata,record){
	var state = record.data.EXAMINE_STATE;
	if('${loginer}'== record.data.CREATOR){
		if (state=='1' || state=='' || state==null){    		
	   		return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" style="color:red" onclick="submitForm();">提交</a>';
	  	}
///*
	  	else{
	   		return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	   	}		
	}else{
		return '<a href="#" onclick="viewForm()">查看</a>';
//*/
	}
	return '<a href="#" onclick="viewForm()">查看</a>';
}

//流程跟踪
function viewHistory()
{
	var records = exportBillsInfogrid.selModel.getSelections();
//	alert(records[0].data.EXPORT_BILL_ID);
	top.ExtModalWindowUtil.show(ExportBillsTitle + '审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.EXPORT_BILL_ID,
		'',	'',	{width:880,height:400});
}

function viewForm(exportShipMentStatId){
	var records = exportBillsInfogrid.selModel.getSelections();		
	var exportBillId = records[0].data.EXPORT_BILL_ID;
    if(exportBillId==''||exportBillId==null){
        return ;   
    }
	top.ExtModalWindowUtil.show(ExportBillsTitle + '查看出口押汇单信息',
		'exportController.spr?action=updateExportBillsView&ExportBillId='+ exportBillId +"&tradeType="+ tradeType +"&isShow=1&operType=001",
		'',
		funexportBillsCallbackFunction,
		{width:800,height:480});
}

//exportBills 进仓单数据的回调函数
function DeleteExportBillsCallbackFunction(transport){
	callBackHandle(transport);
	ExportBillsInfods.reload();
}

//增加进仓单的回调函数
function funexportBillsCallbackFunction(){
	ExportBillsInfods.reload();
}
   
function operaForm(exportBillId){
	    if(exportBillId==''||exportBillId==null)
		{		
			var records = exportBillsInfogrid.selModel.getSelections();		
			var exportBillId = records[0].data.EXPORT_BILL_ID;
		}
	    if(exportBillId==''||exportBillId==null) return; 

	    top.ExtModalWindowUtil.show(ExportBillsTitle + '出货押汇单信息',
		'exportController.spr?action=updateExportBillsView&ExportBillId='+ exportBillId +　"&tradeType="+ tradeType + "&operType=101",
		'',
		funexportBillsCallbackFunction,
		{width:900,height:480});
}
   
	function submitForm(exportBillId){

		if (exportBillId == null || exportBillId == '')
		{
			var records = exportBillsInfogrid.selModel.getSelections();		
			exportBillId = records[0].data.EXPORT_BILL_ID;
		}
	    if(exportBillId==''||exportBillId==null) return;   
		
    	top.ExtModalWindowUtil.show(ExportBillsTitle + '提交出口押汇单',
		'exportController.spr?action=updateExportBillsView&ExportBillId='+ exportBillId +　"&tradeType="+ tradeType+  "&operType=011",
		'',
		funexportBillsCallbackFunction,
		{width:800,height:480});
}
--></script>
<fiscxd:dept divId="deptCode" rootTitle="部门信息" width="155"></fiscxd:dept>