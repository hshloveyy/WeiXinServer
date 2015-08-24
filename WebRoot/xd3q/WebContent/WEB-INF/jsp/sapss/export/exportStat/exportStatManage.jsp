<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出口出货统计管理</title>
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
<form action="" id="findExportStatFrom" name="findExportStatFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">部门编码：</td>
	<td>
		<input type="text" id="deptCode" name="deptCode" value=""></input>
	</td>
	<td align="right">通知单号：</td>
	<td>
		<input type="text" id="exportApplyNo" name="exportApplyNo" value=""></input>
	</td>
	<td align="right">发票号：</td>
	<td>
		<input type="text" id="INVNO" name="INVNO" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">合同编码：</td>
	<td>
		<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>
	<td align="right">SAP订单号：</td>
	<td>
		<input type="text" id="sapOrderNo" name="sapOrderNo" value=""></input>
	</td>
	<td align="right">增值税发票号：</td>
	<td>
		<input type="text" id="addedTaxINVNO" name="addedTaxINVNO" value=""></input>
	</td>
</tr>
<tr>
    <td align="right">核销单号：</td>
	<td>
		<input type="text" id="wirteNo" name="writeNo" value=""></input>
	</td>
	<td align="right">报关单号：</td>
	<td>
		<input type="text" id="declarationNO" name="declarationNO" value=""></input>
	</td>
	<td align="right">提单号：</td>
	<td>
		<input type="text" id="catchNo" name="catchNo" value=""></input>
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
		<input type="button" value="查找" onclick="findExportStatInfo()"></input>
		<input type="reset" value="清空"></input>
		<a onbeforeactivate="onbeforClickA()" href="exportController.spr?action=dealOutToExcel" id="aaa" target="_self">导出</a>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_center"></div>
</body>
</html>
<script type="text/javascript"><!--
//贸易类型

 function onbeforClickA(){
	var param = Form.serialize('findExportStatFrom');
	var requestUrl = 'exportController.spr?action=dealOutToExcel&' + param;
	$('aaa').href=requestUrl;
}

var tradeType = '${tradeType}';

var exportStatTitle ='';
var strOperType = '0';
exportStatTitle = Utils.getTradeTypeTitle(tradeType);


var exportStatInfogrid;		//信息列表

//receipts 查找按钮的单击事件
function findExportStatInfo(){
	var sDate= Ext.getDom("sDate").value;
    var eDate= Ext.getDom("eDate").value;
    
	var param = Form.serialize('findExportStatFrom');
	var requestUrl = 'exportController.spr?action=queryExportStat&' + param;
	requestUrl = requestUrl + "&tradeType=" + tradeType;	
	exportStatInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	exportStatInfods.load({params:{start:0, limit:10},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	
	//增加出货统计单的回调函数
    function funAddExportStatCallBack(){
    	exportStatInfods.reload();
    }
    
    //增加
    var addExportStatInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('新增出货统计单',
			'exportController.spr?action=createExportStat&tradeType='+tradeType,
			'',
			funAddExportStatCallBack,
			{width:900,height:450});
		}
   	});
    	
   	//删除
   	var deleteExportStatInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (exportStatInfogrid.selModel.hasSelection()){
				var records = exportStatInfogrid.selModel.getSelections();
				
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个出货统计单信息！');
				}else{				
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除选择的记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=deleteExportStat";
								param = param + "&exportShipMentStatID=" + records[0].data.EXPORT_SHIPMENT_STAT_ID;

								new AjaxEngine('exportController.spr', 
						   			{method:"post", parameters: param, onComplete: DeleteExportStatCallbackFunction});
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的出货统计单信息！');
			}
		}
   	});
    
    var exportStatInfotbar = new Ext.Toolbar({
		items:[addExportStatInfo,'-',deleteExportStatInfo]
	});
	
	var exportStatInfoPlant = Ext.data.Record.create([
		{name:'EXPORT_SHIPMENT_STAT_ID'},  	//出货统计ID
		{name:'EXPORT_APPLY_NO'},  			//出口货物通知单ID
		{name:'CREATOR'},             		//创建者
		{name:'CREATOR_DEPT'},                 	//部门代码
		{name:'PROJECT_NO'},            	//立项号
		{name:'PROJECT_NAME'},            	//立项名称
		{name:'CONTRACT_NO'},           	//合同编码
		{name:'SAP_ORDER_NO'},             	//SAP订单号
		{name:'BATCH_NO'},                  //批次号
		{name:'INV_NO'},             	    //发票号
		{name:'EXPORT_DATE'},             	//出口日期
	    {name:'PRS_NUM'},                   //数量
		{name:'UNIT'},                      //单位
		{name:'TOTAL'},                     //出口金额
		{name:'CURRENCY'},                  //币别 
		{name:'ADDED_TAX_INV_NO'},          //增值税发票号
		{name:'ADDED_TAX_INV_VALUE'},       //发票金额（进项）
		{name:'ADDED_TAX_VALUE'},           //税面额 
		{name:'WRITE_NO'},                  //核销单号
		{name:'CATCH_NO'},                  //提单号
		{name:'PORT'},                      //口岸
		{name:'CUSTOME_NAME'},              //客户
		{name:'RATE'},                      //换汇比
		{name:'RECEIPT_DATE'},              //进仓日   
		{name:'DRAWBACK_DATE'},             //申报退税日
		{name:'DRAWBACK_END_DATE'},         //最后申报日
		{name:'MARK'},                       //备注
		{name:'EXPORTINFO'}
	]);

	exportStatInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'exportController.spr?action=queryExportStat&tradeType='+tradeType}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},exportStatInfoPlant)
    });
    
    var exportStatInfosm = new Ext.grid.CheckboxSelectionModel();    
    var exportStatInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		exportStatInfosm,
		　 {header: '出货统计ID',
           width: 100,
           sortable: true,
           dataIndex: 'EXPORT_SHIPMENT_STAT_ID',
           hidden:true
           },
           {
           header: '操作',
           width: 100,
           sortable: true,
           dataIndex: 'EXPORTINFO',
           renderer: operaRD
           },
  		　 {header: '出口货物通知单ID',
           width: 100,
           sortable: true,
           dataIndex: 'EXPORT_APPLY_NO',
           hidden:true
           },
           {header: '创建者',
           width: 100,
           sortable: true,
           dataIndex: 'CREATOR',
           hidden: true
           },
           {header: '部门代码',
           width: 100,
           sortable: true,
           dataIndex: 'CREATOR_DEPT',
           hidden:true
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
           {
           header: '合同编码',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_NO'
           },
           {
           header: 'SAP订单号',
           width: 100,
           sortable: true,
           dataIndex: 'SAP_ORDER_NO'
           },
           {
           header: '批次号',
           width: 100,
           sortable: true,
           dataIndex: 'BATCH_NO'
           },
           {
           header: '发票号',
           width: 100,
           sortable: true,
           dataIndex: 'INV_NO'
           },
           {
           header: '出口日期',
           width: 100,
           sortable: true,
           dataIndex: 'EXPORT_DATE'
           },
           {
           header: '数量',
           width: 100,
           sortable: true,
           dataIndex: 'PRS_NUM'
           }, 
           {
           header: '单位',
           width: 100,
           sortable: true,
           dataIndex: 'UNIT'
           },  
           {
           header: '出口金额',
           width: 100,
           sortable: true,
           dataIndex: 'TOTAL'
           },                                
           {
           header: '币别',
           width: 100,
           sortable: true,
           dataIndex: 'CURRENCY',
           hidden:true           
           },
           {
           header: '增值税发票号',
           width: 100,
           sortable: true,
           dataIndex: 'ADDED_TAX_INV_NO'
           },
      		{
           header: '发票金额（进项）',
           width: 100,
           sortable: true,
           dataIndex: 'ADDED_TAX_INV_VALUE',
           hidden: true
           },           
           {
           header: '税面额',
           width: 100,
           sortable: true,
           dataIndex: 'ADDED_TAX_VALUE'
           },                       
           {
           header: '核销单号',
           width: 100,
           sortable: true,
           dataIndex: 'WRITE_NO'
           },                       
           {
           header: '提单号',
           width: 100,
           sortable: true,
           dataIndex: 'CATCH_NO'
           },                       
           {
           header: '口岸',
           width: 100,
           sortable: true,
           dataIndex: 'PORT'
           },                       
           {
           header: '客户',
           width: 100,
           sortable: true,
           dataIndex: 'CUSTOME_NAME'
           },                       
           {
           header: '换汇比',
           width: 100,
           sortable: true,
           dataIndex: 'RATE'
           },                       
           {
           header: '进仓日',
           width: 100,
           sortable: true,
           dataIndex: 'RECEIPT_DATE'
           },                       
           {
           header: '申报退税日',
           width: 100,
           sortable: true,
           dataIndex: 'DRAWBACK_DATE'
           },                       
           {
           header: '最后申报日',
           width: 100,
           sortable: true,
           dataIndex: 'DRAWBACK_END_DATE'
           },                       
           {
           header: '备注',
           width: 100,
           sortable: true,
           dataIndex: 'MARK'
           }
    ]);
    exportStatInfocm.defaultSortable = true;
    
    var exportStatInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:exportStatInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    exportStatInfogrid = new Ext.grid.EditorGridPanel({
    	id:'exportStatInfogrid',
        ds: exportStatInfods,
        cm: exportStatInfocm,
        sm: exportStatInfosm,
        bbar: exportStatInfobbar,
        tbar: exportStatInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    exportStatInfods.load({params:{start:0, limit:10},arg:[]});
    
 //exportStatInfogrid.addListener('rowclick', exportStatInfogridrowclick);

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: exportStatTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:150
		},{
			region:"center",
			layout:'fit',
			title: exportStatTitle + "出货统计单列表",
			items:[exportStatInfogrid]
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
	if('${loginer}'== record.data.CREATOR){ 		
	   		return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" onclick="viewForm()">查看</a>';		
	}else{
		return '<a href="#" onclick="viewForm()">查看</a>';
	}
}

function viewForm(exportShipMentStatId){
	var records = exportStatInfogrid.selModel.getSelections();		
	var exportShipMentStatId = records[0].data.EXPORT_SHIPMENT_STAT_ID;
    if(exportShipMentStatId==''||exportShipMentStatId==null){
        return ;   
    }
	top.ExtModalWindowUtil.show(exportStatTitle + '出货统计单信息',
		'exportController.spr?action=updateExportStatView&exportStatId='+ exportShipMentStatId +"&tradeType="+ tradeType +"&isShow=1",
		'',
		funReceiptsCallbackFunction,
		{width:900,height:450});
}

//receipts 进仓单数据的回调函数
function DeleteExportStatCallbackFunction(transport){
	callBackHandle(transport);
	exportStatInfods.reload();
}

//增加进仓单的回调函数
function funReceiptsCallbackFunction(){
	exportStatInfods.reload();
}
   
function operaForm(receiptId){
		var records = exportStatInfogrid.selModel.getSelections();		
		var exportShipMentStatId = records[0].data.EXPORT_SHIPMENT_STAT_ID;
	    if(exportShipMentStatId==''||exportShipMentStatId==null){
	        return ;   
        }
	    top.ExtModalWindowUtil.show(exportStatTitle + '出货统计单信息',
		'exportController.spr?action=updateExportStatView&exportStatId='+ exportShipMentStatId +"&tradeType="+ tradeType,
		'',
		funReceiptsCallbackFunction,
		{width:900,height:450});
}
   
function submitForm(receiptId){
	if (exportShipMentStatId == null || exportShipMentStatId == ''){
		var records = exportStatInfogrid.selModel.getSelections();		
		exportShipMentStatId = records[0].data.EXPORT_SHIPMENT_STAT_ID;
	}

	top.ExtModalWindowUtil.show(exportStatTitle + '出货统计单信息',
		'exportController.spr?action=addExportStatView&exportStatId='+ exportShipMentStatId +"&tradeType="+ tradeType+  "&operType=011",
		'',
		funReceiptsCallbackFunction,
		{width:900,height:550});
}
--></script>
