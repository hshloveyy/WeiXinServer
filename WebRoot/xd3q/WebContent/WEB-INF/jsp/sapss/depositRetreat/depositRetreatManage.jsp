<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>保证金回退管理</title>
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
<form action="" id="findDepositRetreatFrom" name="findDepositRetreatFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">申请人：</td>
	<td>
		<input type="text" id="applyer" name="applyer" value=""></input>
	</td>
	<td align="right">申请期货账户：</td>
	<td>
		<div id="div_applyAccount"></div>
	</td>
	<td align="right">收款账户：</td>
	<td>
		<div id="div_receiptAccount"></div>
	</td>
</tr>

<tr>
	<td align="right">申请时间从：</td>
	<td>
		<div id="sDateDiv"></div>
	</td>
	<td align="right">到：</td>
	<td>
		<div id="eDateDiv"></div>
	</td>
	<td align="center" colspan="2">
		<input type="button" value="查找" onclick="findDepositRetreatInfo()"></input>
		<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_center"></div>
</body>
</html>
<fiscxd:dictionary divId="div_receiptAccount" fieldName="receiptAccount" dictionaryName="BM_DEPOSIT_RECEIPT_ACCOUNT" width="200" selectedValue=""></fiscxd:dictionary>
<fiscxd:dictionary divId="div_applyAccount" fieldName="applyAccount" dictionaryName="BM_DEPOSIT_ACCOUNT" width="200" selectedValue=""></fiscxd:dictionary>

<script type="text/javascript"><!--
document.onkeydown = function(){if (event.keyCode == 13){findDepositRetreatInfo();}}
var DepositRetreatTitle ='保证金退回';
var strOperType = '0';

var DepositRetreatInfogrid;		//信息列表

//DepositRetreat 查找按钮的单击事件
function findDepositRetreatInfo(){
	//var sDate= Ext.getDom("sDate").value;
    //var eDate= Ext.getDom("eDate").value;
    
	var param = Form.serialize('findDepositRetreatFrom');
	var requestUrl = 'depositRetreatController.spr?action=queryDepositRetreat&'+ param;
	DepositRetreatInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	DepositRetreatInfods.load({params:{start:0, limit:10},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	
	//增加出货押汇单的回调函数
    function funAddDepositRetreatCallBack(){
    	DepositRetreatInfods.reload();
    }
    
    //增加
    var addDepositRetreatInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('新增保证金退回',
			'depositRetreatController.spr?action=createDepositRetreat',
			'',
			funAddDepositRetreatCallBack,
			{width:750,height:400});
		}
   	});
    	
   	//删除
   	var deleteDepositRetreatInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (DepositRetreatInfogrid.selModel.hasSelection()){
				var records = DepositRetreatInfogrid.selModel.getSelections();
				
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个出货押汇单信息！');
				}else{				
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除选择的记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=deleteDepositRetreat";
								param = param + "&depositRetreatId=" + records[0].data.DEPOSIT_RETREAT_ID;
//alert(param);
								new AjaxEngine('depositRetreatController.spr', 
						   			{method:"post", parameters: param, onComplete: DeleteDepositRetreatCallbackFunction});
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
    
    var DepositRetreatInfotbar = new Ext.Toolbar({
		items:[addDepositRetreatInfo,'-',deleteDepositRetreatInfo]
	});
	
	var DepositRetreatInfoPlant = Ext.data.Record.create([
		{name:'DEPOSIT_RETREAT_ID'}, 
		{name:'APPLYER'},  			
		{name:'APPLY_SUN'},  		
		{name:'APPLY_ACCOUNT_NAME'},
		{name:'RECEIPT_ACCOUNT_NAME'},         
		{name:'APPLY_DATE'},                 
		{name:'APPLY_TIME'},                 	
		{name:'APPLYED_TIME'},           
		{name:'CREATOR'},            
		{name:'DEPT_ID'},              
		{name:'CREATER_TIME'},		
		{name:'EXAM_STATE'},
		{name:'EXAM_STATE_D_EXAMINE_STATE'},
		{name:'EXPORTINFO'}
	]);

	DepositRetreatInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'depositRetreatController.spr?action=queryDepositRetreat'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},DepositRetreatInfoPlant)
    });
    
    var DepositRetreatInfosm = new Ext.grid.CheckboxSelectionModel();    
    var DepositRetreatInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		DepositRetreatInfosm,
		　 {header: '保证金退回ID',
           width: 100,
           sortable: true,
           dataIndex: 'DEPOSIT_RETREAT_ID',
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
               width: 80,
               sortable: true,
               dataIndex: 'EXAM_STATE_D_EXAMINE_STATE'
               },                    
           {header: '申请人',
           width: 60,
           sortable: true,
           dataIndex: 'APPLYER'
           },
  		　 {header: '申请金额',
           width: 100,
           sortable: true,
           dataIndex: 'APPLY_SUN'
           },
           {header: '申请期货账户',
           width: 200,
           sortable: true,
           dataIndex: 'APPLY_ACCOUNT_NAME'
           },
           {header: '收款账户',
           width: 200,
           sortable: true,
           dataIndex: 'RECEIPT_ACCOUNT_NAME'
           },
           {
           header: '申请时间',
           width: 100,
           sortable: true,
           dataIndex: 'APPLY_DATE'
           },
           {
           header: '提交时间',
           width: 100,
           sortable: true,
           dataIndex: 'APPLY_TIME'
           },                                
           {header: '审批通过时间',
           width: 100,
           sortable: true,
           dataIndex: 'APPLYED_TIME'
           },
           {header: '创建者',
           width: 50,
           sortable: true,
           dataIndex: 'CREATOR',
           hidden:true
           },
           {
           header: '创建部门',
           width: 100,
           sortable: true,
           dataIndex: 'DEPT_ID',
           hidden:true
           },
           {
           header: '创建时间',
           width: 100,
           sortable: true,
           dataIndex: 'CREATOR_TIME',
           hidden:true
           },
      		{
           header: '状态',
           width: 80,
           sortable: true,
           dataIndex: 'EXAM_STATE',
           hidden: true
           }
    ]);
    DepositRetreatInfocm.defaultSortable = true;
    
    var DepositRetreatInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:DepositRetreatInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    DepositRetreatInfogrid = new Ext.grid.EditorGridPanel({
    	id:'DepositRetreatInfogrid',
        ds: DepositRetreatInfods,
        cm: DepositRetreatInfocm,
        sm: DepositRetreatInfosm,
        bbar: DepositRetreatInfobbar,
        tbar: DepositRetreatInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    DepositRetreatInfods.load({params:{start:0, limit:10},arg:[]});
    
 //DepositRetreatInfogrid.addListener('rowclick', DepositRetreatInfogridrowclick);

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: DepositRetreatTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:107
		},{
			region:"center",
			layout:'fit',
			title: DepositRetreatTitle + "列表",
			items:[DepositRetreatInfogrid]
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
	var state = record.data.EXAM_STATE;
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
	var records = DepositRetreatInfogrid.selModel.getSelections();
//	alert(records[0].data.EXPORT_BILL_ID);
	top.ExtModalWindowUtil.show(DepositRetreatTitle + '审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.DEPOSIT_RETREAT_ID,
		'',	'',	{width:880,height:400});
}

function viewForm(exportShipMentStatId){
	var records = DepositRetreatInfogrid.selModel.getSelections();		
	var depositRetreatId = records[0].data.DEPOSIT_RETREAT_ID;
    if(depositRetreatId==''||depositRetreatId==null){
        return ;   
    }
   
	top.ExtModalWindowUtil.show(DepositRetreatTitle + '信息',
		'depositRetreatController.spr?action=updateDepositRetreatView&depositRetreatId='+ depositRetreatId +"&isShow=1&operType=001",
		'',
		funDepositRetreatCallbackFunction,
		{width:800,height:480});
}

//DepositRetreat 回调函数
function DeleteDepositRetreatCallbackFunction(transport){
	callBackHandle(transport);
	DepositRetreatInfods.reload();
}

//回调函数
function funDepositRetreatCallbackFunction(){
	DepositRetreatInfods.reload();
}
   
function operaForm(depositRetreatId){
	    if(depositRetreatId==''||depositRetreatId==null)
		{		
			var records = DepositRetreatInfogrid.selModel.getSelections();		
			var depositRetreatId = records[0].data.DEPOSIT_RETREAT_ID;
		}
	    if(depositRetreatId==''||depositRetreatId==null) return; 

	    top.ExtModalWindowUtil.show(DepositRetreatTitle + '信息',
		'depositRetreatController.spr?action=updateDepositRetreatView&depositRetreatId='+ depositRetreatId  + "&operType=101",
		'',
		funDepositRetreatCallbackFunction,
		{width:800,height:480});
}
   
	function submitForm(depositRetreatId){

		if (depositRetreatId == null || depositRetreatId == '')
		{
			var records = DepositRetreatInfogrid.selModel.getSelections();		
			depositRetreatId = records[0].data.DEPOSIT_RETREAT_ID;
		}
	    if(depositRetreatId==''||depositRetreatId==null) return;   
		
    	top.ExtModalWindowUtil.show(DepositRetreatTitle + '提交',
		'depositRetreatController.spr?action=updateDepositRetreatView&depositRetreatId='+ depositRetreatId +"&operType=011",
		'',
		funDepositRetreatCallbackFunction,
		{width:800,height:480});
}
--></script>