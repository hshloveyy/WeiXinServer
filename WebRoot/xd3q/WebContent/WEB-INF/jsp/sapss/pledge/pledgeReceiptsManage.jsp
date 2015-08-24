<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>进仓</title>
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
<form action="" id="findreceiptsFrom" name="findreceiptsFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">部门名称：</td>
	<td>
		<div id="dept"></div>
	</td>
	<td align="right">立项号：</td>
	<td>
		<input type="text" id="projectName" name="projectName" value=""></input>
		<input type="hidden" id="projectNo" name="projectNo" value=""></input>
	</td>
	<td align="right">销货单位：</td>
	<td>
		<input type="text" id="unitName" name="unitName" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">物料名称：</td>
	<td>
		<input type="text" id="materialName" name="materialName" value=""></input>
	</td>
	<td align="right">物料号：</td>
	<td>
		<input type="text" id="materialCode" name="materialCode" value=""></input>
	</td>
	<td align="right">进仓单编号/序号：</td>
	<td>
		<input type="text" id="pledgeReceiptsInfoNo" name="pledgeReceiptsInfoNo" value=""></input>
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
	     批次号：</td>
	 
	 <td><input type="text" id="batchNo" name="batchNo" value=""></input>
		<input type="button" value="查找" onclick="findreceiptsInfo()"></input>
		<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_center"></div>

<div id="div_south"></div>
</body>
</html>
<script type="text/javascript"><!--
document.onkeydown = function(){if (event.keyCode == 13){findreceiptsInfo();}}
var strOperType = '0';

var receiptsRecord;
var purchaserrecord;
var purchasefrieldName;

var receiptsInfogrid;		//进仓单信息列表
var receiptsInfods;

//receipts 查找按钮的单击事件
function findreceiptsInfo(){
	var sDate= Ext.getDom("sDate").value;
    var eDate= Ext.getDom("eDate").value;
    
	var param = Form.serialize('findreceiptsFrom');
	var requestUrl = 'pledgeReceiptsController.spr?action=query&' + param;
	requestUrl = requestUrl + '&deptId=' + selectId_dept;		
	receiptsInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	receiptsInfods.load({params:{start:0, limit:10},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

   /*
    //TEST
   top.Ext.Msg.show({
			title:'提示',
			closable:false,
			msg:tradeType,
			buttons:{yes:'关闭'},
			fn:Ext.emptyFn,
			icon:Ext.MessageBox.INFO
	});
	*/
	
	//增加进仓单的回调函数
    function funAddreceiptsCallBack(){
    	receiptsInfods.reload();
    }
    
    //增加
    var addreceiptsInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('新增入库申请单',
			'pledgeReceiptsController.spr?action=create',
			'',
			funAddreceiptsCallBack,
			{width:900,height:600});
		}
   	});
     
   	//删除
   	var deletereceiptsInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (receiptsInfogrid.selModel.hasSelection()){
				var records = receiptsInfogrid.selModel.getSelections();
				
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个进仓单信息！');
				}else{
					if (records[0].data.EXAMINE_STATE != "1" )
						top.ExtModalWindowUtil.alert('提示','不能删除已送审的单据！');
					else					
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除选择的记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=delete";
								param = param + "&pledgeReceiptsInfoId=" + records[0].data.PLEDGERECEIPTS_INFO_ID;

								new AjaxEngine('pledgeReceiptsController.spr', 
						   			{method:"post", parameters: param, onComplete: DeleteReceiptCallbackFunction});
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的进仓单信息！');
			}
		}
   	});
    
    var receiptsInfotbar = new Ext.Toolbar({
		items:[addreceiptsInfo,'-',deletereceiptsInfo]
	});
	
	var receiptsInfoPlant = Ext.data.Record.create([
		{name:'PLEDGERECEIPTS_INFO_ID'},  	//进仓ID 
		{name:'PLEDGERECEIPTS_INFO_NO'},    //进仓单号
		{name:'DEPT_ID'},                 	//部门代码
		{name:'DEPT_NAME'},                 //部门名称
		{name:'PROJECT_NO'},            	//立项号
		{name:'UNIT_NAME'},                  //销货单位
		{name:'PROJECT_NAME'},            	//立项名称
		{name:'APPLY_TIME'},             	//申报日期
	    {name:'CREATOR'},             		//创建者
	    {name:'REAL_NAME'},             	//创建者姓名
	    {name:'WAREHOUSE'},             	//仓库
	    {name:'WAREHOUSE_ADD'},             //仓库地址
		{name:'CREATOR_TIME'},              //创建时间
	    {name:'IS_AVAILABLE'},
		{name:'APPROVED_TIME'},             //审批时间
		{name:'EXAMINE_STATE'},
		{name:'RSEXAM_STATE_D_RSEXAM_STATE'}, //状态             
		{name:'EXPORTINFO'}                //操作
	]);

	receiptsInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'pledgeReceiptsController.spr?action=query'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},receiptsInfoPlant)
    });
    
    var receiptsInfosm = new Ext.grid.CheckboxSelectionModel();    
    var receiptsInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		receiptsInfosm,
		　 {header: '进仓ID',
           width: 100,
           sortable: true,
           dataIndex: 'PLEDGERECEIPTS_INFO_ID',
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
               dataIndex: 'RSEXAM_STATE_D_RSEXAM_STATE'
           },{
               header: '创建人',
               width: 100,
               sortable: true,
               dataIndex: 'REAL_NAME'
           },                                          
  		　 {header: '进仓单编号',
           width: 100,
           sortable: true,
           dataIndex: 'PLEDGERECEIPTS_INFO_NO'
           },
           {header: '部门代码',
           width: 100,
           sortable: true,
           dataIndex: 'DEPT_ID',
           hidden: true
           },
           {header: '部门名称',
           width: 100,
           sortable: true,
           dataIndex: 'DEPT_NAME'
           },
           {header: '立项号',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_NO'
           },
           {header: '销货单位',
           width: 100,
           sortable: true,
           dataIndex: 'UNIT_NAME'
           },
           {header: '立项名称',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_NAME'
           },
           {
           header: '仓库',
           width: 100,
           sortable: true,
           dataIndex: 'WAREHOUSE'
           },
           {
           header: '仓库地址',
           width: 100,
           sortable: true,
           dataIndex: 'WAREHOUSE_ADD',
           hidden:true
           },
           {
           header: '申报时间',
           width: 100,
           sortable: true,
           dataIndex: 'APPLY_TIME'
           }, 
           {
           header: '审批时间',
           width: 100,
           sortable: true,
           dataIndex: 'APPROVED_TIME'
           },  
           {
           header: '创建时间',
           width: 100,
           sortable: true,
           dataIndex: 'CREATOR_TIME'
           },                                
           {
           header: '创建人ID',
           width: 100,
           sortable: true,
           dataIndex: 'CREATOR',
           hidden:true           
           }
    ]);
    receiptsInfocm.defaultSortable = true;
    
    var receiptsInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:receiptsInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    receiptsInfogrid = new Ext.grid.EditorGridPanel({
    	id:'receiptsInfoGrid',
        ds: receiptsInfods,
        cm: receiptsInfocm,
        sm: receiptsInfosm,
        bbar: receiptsInfobbar,
        tbar: receiptsInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    receiptsInfods.load({params:{start:0, limit:10},arg:[]});
    
 //receiptsInfogrid.addListener('rowclick', receiptsInfogridrowclick);

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: "质押物进仓条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:110
		},{
			region:"center",
			layout:'fit',
			title: "质押物进仓单列表",
			items:[receiptsInfogrid]
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

function operaRD(value,metadata,record){
	var state = record.data.EXAMINE_STATE;
	if('${loginer}'== record.data.CREATOR){
		if (state=='1'){    		
	   		return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" style="color:red" onclick="submitForm()">提交</a>';
	  	 } if(state=='3'){
	  		return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
		 } if(state=='7' || state=='8' ||state=='9'){
			return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="businessAllProcessRecords()">流程跟踪</a>';
		 }else{
	   		return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	   	}		
	}else{
		return '<a href="#" onclick="viewForm()">查看</a>';
	}
}

function businessAllProcessRecords(){
	var records = receiptsInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息',
	'workflowController.spr?action=businessAllProcessRecords&businessRecordId='+records[0].data.PLEDGERECEIPTS_INFO_ID,
	'',
	'',
	{width:800,height:500});	
}

//流程跟踪
function viewHistory()
{
	var records = receiptsInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.PLEDGERECEIPTS_INFO_ID+'&isRSHis=1',
		'',	'',	{width:880,height:400});
}

function viewForm(pledgeReceiptsInfoId){
	if (pledgeReceiptsInfoId == null || pledgeReceiptsInfoId == ''){
		var records = receiptsInfogrid.selModel.getSelections();		
		pledgeReceiptsInfoId = records[0].data.PLEDGERECEIPTS_INFO_ID;
	}

	top.ExtModalWindowUtil.show('入库申请单信息',
		'pledgeReceiptsController.spr?action=view&pledgeReceiptsInfoId='+ pledgeReceiptsInfoId,
		'',
		funReceiptsCallbackFunction,
		{width:900,height:550});
}



//receipts 进仓单数据的回调函数
function DeleteReceiptCallbackFunction(transport){
	callBackHandle(transport);
	receiptsInfods.reload();
}

//增加进仓单的回调函数
function funReceiptsCallbackFunction(){
	receiptsInfods.reload();
}
   
function operaForm(pledgeReceiptsInfoId){
	if (pledgeReceiptsInfoId == null || pledgeReceiptsInfoId == ''){
		var records = receiptsInfogrid.selModel.getSelections();		
		pledgeReceiptsInfoId = records[0].data.PLEDGERECEIPTS_INFO_ID;
	}

	top.ExtModalWindowUtil.show('入库申请单信息',
		'pledgeReceiptsController.spr?action=modify&pledgeReceiptsInfoId='+ pledgeReceiptsInfoId,
		'',
		funReceiptsCallbackFunction,
		{width:900,height:550});
}
   
function submitForm(pledgeReceiptsInfoId){
	if (pledgeReceiptsInfoId == null || pledgeReceiptsInfoId == ''){
		var records = receiptsInfogrid.selModel.getSelections();		
		pledgeReceiptsInfoId = records[0].data.PLEDGERECEIPTS_INFO_ID;
	}

	top.ExtModalWindowUtil.show('入库申请单信息',
		'pledgeReceiptsController.spr?action=modify&pledgeReceiptsInfoId='+ pledgeReceiptsInfoId,
		'',
		funReceiptsCallbackFunction,
		{width:900,height:550});
}
--></script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"></fiscxd:dept>
