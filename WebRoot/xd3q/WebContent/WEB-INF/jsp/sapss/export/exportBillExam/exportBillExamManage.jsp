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
	<td align="right">单据类型：</td>
	<td>
	    <select name="billType">
	       <option value="">请选择</option>
	       <option value="LC">LC</option>
	       <option value="DP">DP</option>
	       <option value="DA">DA</option>
	       <option value="TT">TT</option>
	    </select>
	</td>
</tr>
<tr>
    <td align="right">核销单号：</td>
	<td>
		<input type="text" id="wirteNo" name="writeNo" value=""></input>
	</td>
	<td align="right">L/C NO.,D/P,DA ：</td>
	<td>
		<input type="text" id="lcdpdaNo" name="lcdpdaNo" value=""></input>
	</td>
	<td align="right"></td>
	<td>
	</td>
	
</tr>
<tr>
	<td align="right">出单日期从：</td>
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
	</td>
</tr>
</table>
</form>
</div>

<div id="div_center"></div>
</body>
</html>
<script type="text/javascript"><!--
document.onkeydown = function(){if (event.keyCode == 13){findExportStatInfo();}}
//贸易类型
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
	var requestUrl = 'exportController.spr?action=queryExportBillExam&' + param;
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
			top.ExtModalWindowUtil.show('新增出单审单',
			'exportController.spr?action=createExportBillExam&tradeType='+tradeType,
			'',
			funAddExportStatCallBack,
			{width:900,height:600});
		}
   	});
    	
   	//删除
   	var deleteExportStatInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (exportBillInfogrid.selModel.hasSelection()){
				var records = exportBillInfogrid.selModel.getSelections();
				
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个出货统计单信息！');
				}else{				
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除选择的记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=deleteExportBillExam";
								param = param + "&exportBillExamID=" + records[0].data.EXPORT_BILL_EXAM_ID;

								new AjaxEngine('exportController.spr', 
						   			{method:"post", parameters: param, onComplete: DeleteExportStatCallbackFunction});
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的出单审单信息！');
			}
		}
   	});
   	
   	//删除
   	var copyCreate = new Ext.Toolbar.Button({
   		text:'参考创建',
	    iconCls:'add',
		handler:function(){
			if (exportBillInfogrid.selModel.hasSelection()){
				var records = exportBillInfogrid.selModel.getSelections();
				
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','只允许选择一条记录参考创建！');
				}else{				
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定参考创建选择的记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								top.ExtModalWindowUtil.show('参考创建出单审单',
   										'exportController.spr?action=copyCreate&exportBillExamId='+records[0].data.EXPORT_BILL_EXAM_ID,
   										'',
   										funAddExportStatCallBack,
   										{width:900,height:600});
   								
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要参考创建的出单审单信息！');
			}
		}
   	});
    
    var exportStatInfotbar = new Ext.Toolbar({
		items:[addExportStatInfo,'-',deleteExportStatInfo,'-',copyCreate]
	});

	
	var exportStatInfoPlant = Ext.data.Record.create([
		{name:'EXPORT_BILL_EXAM_ID'},  	//ID
		{name:'EXPORT_APPLY_NO'},  			//出口货物通知单ID
		{name:'CREATOR'},             		//创建者
		{name:'CREATOR_DEPT'},                 	//部门代码
		{name:'PROJECT_NO'},            	//立项号
		{name:'PROJECT_NAME'},            	//立项名称
		{name:'CONTRACT_NO'},           	//合同编码
		{name:'SAP_ORDER_NO'},             	//SAP订单号
		{name:'BILL_TYPE'},                  //单据类型
		{name:'INV_NO'},             	    //发票号
		{name:'EXAM_DATE'},             	//出单日期
	    {name:'LCDPDA_NO'},                   //LCDPDA_NO
		{name:'TOTAL'},                     //金额
		{name:'CURRENCY'},                  //币别 
		{name:'WRITE_NO'},                  //核销单号
		{name:'BANK'},                  //预付银行
		{name:'IS_NEGOTIAT'},                      //是否押汇
		{name:'BILL_SHIP_DATE'},              //提单装船日
		{name:'SHOULD_INCOME_DATE'},                      //应收汇日
		{name:'MARK'},                       //备注
		{name:'DEPT_NAME'}, 
		{name:'EXPORTINFO'}
	]);

	exportStatInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'exportController.spr?action=queryExportBillExam&tradeType='+tradeType}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},exportStatInfoPlant)
    });
    
    var exportStatInfosm = new Ext.grid.CheckboxSelectionModel();    
    var exportStatInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		exportStatInfosm,
		　 {header: 'ID',
           width: 100,
           sortable: true,
           dataIndex: 'EXPORT_BILL_EXAM_ID',
           hidden:true
           },
           {
               header: '操作',
               width: 100,
               sortable: true,
               dataIndex: 'EXPORTINFO',
               renderer: operaRD
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
           {header: '部门名称',
           width: 100,
           sortable: true,
           dataIndex: 'DEPT_NAME'
           },
           {
           header: '单据类型',
           width: 100,
           sortable: true,
           dataIndex: 'BILL_TYPE'
           },
           {
           header: '发票号',
           width: 100,
           sortable: true,
           dataIndex: 'INV_NO'
           },
           {
           header: '出单日期',
           width: 100,
           sortable: true,
           dataIndex: 'EXAM_DATE'
           }, 
           {
           header: '金额',
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
           header: '议付银行',
           width: 100,
           sortable: true,
           dataIndex: 'BANK'
           },                       
           {
           header: '是否押汇',
           width: 100,
           sortable: true,
           dataIndex: 'IS_NEGOTIAT'
           },                       
           {
           header: '提单装船日',
           width: 100,
           sortable: true,
           dataIndex: 'BILL_SHIP_DATE'
           },                       
           {
           header: '应收汇日',
           width: 100,
           sortable: true,
           dataIndex: 'SHOULD_INCOME_DATE'
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
    
    exportBillInfogrid = new Ext.grid.EditorGridPanel({
    	id:'exportBillInfogrid',
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
    
 //exportBillInfogrid.addListener('rowclick', exportBillInfogridrowclick);

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
			title: exportStatTitle + "出单审单列表",
			items:[exportBillInfogrid]
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
	   		return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="printForm()">打印</a>';		
	}else{
		return '<a href="#" onclick="viewForm()">查看</a>';
	}
}

function viewForm(exportShipMentStatId){
	var records = exportBillInfogrid.selModel.getSelections();		
	var exportBillExamId = records[0].data.EXPORT_BILL_EXAM_ID;
    if(exportBillExamId==''||exportBillExamId==null){
        return ;   
    }
    _getMainFrame().maintabs.addPanel(exportStatTitle + '出单审单信息','', 'exportController.spr?action=updateExportBillExamView&exportBillExamId='+ exportBillExamId +"&tradeType="+ tradeType +"&isShow=1");
}

function printForm(){
	var records = exportBillInfogrid.selModel.getSelections();		
	var exportBillExamId = records[0].data.EXPORT_BILL_EXAM_ID;
    if(exportBillExamId==''||exportBillExamId==null){
        return ;   
    }
    window.open('exportController.spr?action=dealPrintExportBillExam&exportBillExamId='+exportBillExamId,'_blank','location=no,resizable=yes');

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
		var records = exportBillInfogrid.selModel.getSelections();		
		var exportBillExamId = records[0].data.EXPORT_BILL_EXAM_ID;
	    if(exportBillExamId==''||exportBillExamId==null){
	        return ;   
        }
        _getMainFrame().maintabs.addPanel(exportStatTitle + '出单审单信息','', 'exportController.spr?action=updateExportBillExamView&exportBillExamId='+ exportBillExamId +"&tradeType="+ tradeType);
}
--></script>
