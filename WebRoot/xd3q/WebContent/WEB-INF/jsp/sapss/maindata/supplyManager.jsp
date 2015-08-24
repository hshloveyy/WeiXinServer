<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商主数据</title>
<link href="<%=request.getContextPath()%>/css/public.css" rel="stylesheet" type="text/css" />
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
.x-grid-row-bgcolor{  
    background-color:#ffffcc;
}
.x-grid-row-bgcolor-effect{
	color:red;
}
</style>
</head>
<body>

<div id="div_center"></div>

<div id="div_south"></div>
<div id="gridTagDiv"></div>
<div id="main" class="x-hide-display">
<form id="findCondictionFR" method="post">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
    <tr >
      <td width="12%" height="20" align="right">供应商编号:</td>
      <td width="25%" colspan="3"><input name="suppply_ID" type="text" size="20"/></td>
      <td width="8%" align="right">供应商名称:</td>
      <td width="25%" colspan="3"><input name="suppply_Name" type="text" size="20"/></td>
      <td width="10%" align="right">供应商组:</td>
      <td width="24%"><input name="suppply_Group" type="text" size="20" /></td>
    </tr>
    <tr align="center">
      <td align="center" colspan="10"><input type="button" value="查找" onclick="find()"/> <input type="reset" value="清空"/>
      </td>
	</tr>
</table>
</form>
</div>
</body>
</body>
</html>
<script type="text/javascript">
document.onkeydown = function(){if (event.keyCode == 13){find();}}
//<button onclick="window.showModalDialog('projectController.spr?action=create&tradeType=7','','width:300;height:400');"> test</button>
var ds;
var tempGrid;
var buttonType;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	//project_name,apply_time,project_no,trade_type,nuder_taker,org_name
	//extComponentServlet?type=grid&grid_columns=PROJECT_ID,PROJECT_NAME,APPLY_TIME,PROJECT_NO&grid_sql=select * from t_project_info&grid_size=10'}),
	var itemStore = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'supplyController.spr?action=find&state=3'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'SUPPLIERS_ID'},
            		{name:'ACCOUNT_GROUP'},
            		{name:'COMPANY_CODE'},
            		{name:'PURCHASE_ORG'},
            		{name:'TITLE'},
            		{name:'SUPPLIERS_CODE'},
            		{name:'SUPPLIERS_NAME1'},
            		{name:'SUPPLIERS_NAME2'},
            		{name:'STREET'},
            		{name:'REGION'},
            		{name:'SEARCH_TERM'},
            		{name:'CITY'},
            		{name:'COUNTRY'},
            		{name:'AREA'},
            		{name:'ZIP_CODE'},
            		{name:'PHONE'},
            		{name:'PHONE_EXT'},
            		{name:'FAX'},
            		{name:'CONTACT_MAN'},
            		{name:'EMAIL'},
            		{name:'SAP_GUEST_CODE'},
            		{name:'SAP_GUEST_NAME'},
            		{name:'SUBJECTS'},
            		{name:'CASH_GROUP'},
            		{name:'PAY_WAY'},
            		{name:'CREATOR'},
					{name:'CREATOR_TIME'},
					{name:'APPLY_TIME'},
					{name:'APPROVED_TIME'},
            		{name: 'EXAMINE_STATE'}, 					
            		{name: 'EXAMINE_STATE_D_EXAMINE_STATE'}										
          		])
    });
    //增加
    var addmaterialInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('新增供应商主数据申请',
			'supplyController.spr?action=createSupply',
			'',
			funAddreceiptsCallBack,
			{width:900,height:600});
		}
   	});
   		//修证数据
    var updatematerialInfo = new Ext.Toolbar.Button({
   		text:'修改',
	    iconCls:'add',
		handler:function(){
			buttonType = "update";
			
			top.ExtModalWindowUtil.show('查找供应商','masterQueryController.spr?action=toFindSupplier','',supplierCallback,{width:755,height:450});
			
		}
   	});
   	
   		//更新年检年度数据
    var updatematerialInfo2 = new Ext.Toolbar.Button({
   		text:'更新年检年度',
	    iconCls:'add',
		handler:function(){
			buttonType = "update2";
			top.ExtModalWindowUtil.show('查找供应商','masterQueryController.spr?action=toFindSupplier','',supplierCallback,{width:755,height:450});
			
		}
   	});
   	    //删除
    var deletematerialInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
   				if (itemGrid.selModel.hasSelection()){
					var records = itemGrid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
					}else{
					if(records[0].json.EXAMINE_STATE == 1)
					{
					     top.Ext.Msg.show({
   								title:'提示',
   								msg: '是否确定删除记录',
   								buttons: {yes:'是', no:'否'},
   								fn: function(buttonId,text){
   									callbackFlag='del';
 									if(buttonId=='yes'){
 	 									var parm ='?action=delete&id='+records[0].json.SUPPLIERS_ID; 
 										new AjaxEngine('supplyController.spr',
 												 {method:"post", parameters: parm,onComplete: callBackHandle});
									}
   								},
   							    animEl: 'elId',
   								icon: Ext.MessageBox.QUESTION
							});
					}
					else
					{
					    top.ExtModalWindowUtil.alert('提示','只有新增状态可以删除！');
					}
				}		
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
				//selectWindow.hide();
		}
   	});
   	function operaRD(value,metadata,record){
		var state = record.data.EXAMINE_STATE;
		if('${loginer}'== record.data.CREATOR){
			if (state=='1'){    		
		   		return '<a href="#" onclick="modify();" >修改</a> <a href="#" style="color:red" onclick="modify()">提交</a>';
		  	 	}else{
		   		return '<a href="#" onclick="view()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
		   	}		
		}else{
			return '<a href="#" onclick="view()">查看</a>';
		}
     }
   	//增加物料主数据单的回调函数
    function funAddreceiptsCallBack(){
    	itemStore.reload();
    }
    itemStore.load();
    ds = itemStore;
    var itemSm = new Ext.grid.CheckboxSelectionModel();
    var itemCm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		itemSm,{
			header: '供应商ID',
           width: 50,
           sortable: false,
           hidden:true,
           dataIndex: 'SUPPLIERS_ID'
           },{
          		header: '操作',
          		width: 100,
          		sortable: false,
          		dataIndex: 'oper',
          		renderer:operaRD
          },{
               header: '审批状态',
               width: 100,
               sortable: true,
               dataIndex: 'EXAMINE_STATE_D_EXAMINE_STATE'
           },{
			header: '供应商编码',
           width: 50,
           sortable: true,
           dataIndex: 'COMPANY_CODE'
           },{
			header: '供应商名称',
           	width: 100,
           	dataIndex: 'SUPPLIERS_NAME1',
           	sortable: true
           },{
			header: '单位',
           	width: 50,
           	dataIndex: 'TITLE',
           	sortable: true
           },
           {header: '申请日期',
           width: 100,
           sortable: false,
           dataIndex: 'APPLY_TIME'
           },
           {header: '审批通过日期',
           width: 100,
           sortable: false,
           dataIndex: 'APPROVED_TIME'
           }
    ]);
    itemCm.defaultSortable = true;
    function approvedTimeRD(value,metadata,record){
    	var apptime = record.data.APPROVED_TIME;
    	if(apptime!=null)
    		return apptime.substring(0,4)+'-'+apptime.substring(4,6)+'-'+apptime.substring(6,8)+' '+apptime.substring(8,10)+':'+apptime.substring(10,12)+':'+apptime.substring(12,14);
    	else
    		return '';	
    }    
     var paging = new Ext.PagingToolbar({
        pageSize: 10,
        store:itemStore,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: '没有记录'
    });
    var itemTbar = new Ext.Toolbar({
		items:[addmaterialInfo,'-',updatematerialInfo,'-',updatematerialInfo2,'-',deletematerialInfo]
	});
    //itemStore.load();进入页面时不加载
    var itemGrid = new Ext.grid.GridPanel({
    	id:'contractGrid',
        ds: itemStore,
        cm: itemCm,
        sm: itemSm,
        bbar: paging,
        tbar: itemTbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'south',
        el: 'div_south',
        autowidth:true,
		viewConfig : {  
                forceFit : true,  
                enableRowBody:true,
                getRowClass : 
                	function(record,rowIndex,rowParams,store){ 
                		if(rowIndex%2==0)
                			return 'x-grid-row-bgcolor'; 
                		else
                			return '';	
                	}  
        },          
        layout:'fit'
    });
    
    tempGrid = itemGrid;
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title:"查询",
			collapsible: true,
			height:85,
			items:[{contentEl:'main'}]
		},{
			region:"center",
			layout:'fit',
			title:"立项列表",
			items:[itemGrid]
		}]
	});
  //data range
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
});
var customCallBackHandle=function(trans){
var responseUtil1 = new AjaxResponseUtils(trans.responseText);
var customField = responseUtil1.getCustomField("coustom");
top.ExtModalWindowUtil.alert('提示',customField.ok);
if(callbackFlag=='del')
	find();
  }
    function viewHistory()
{
	var records = tempGrid.selModel.getSelections();
//	alert(records[0].data.RECEIPT_ID);
	top.ExtModalWindowUtil.show('供应商主数据审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.SUPPLIERS_ID,
		'',	'',	{width:880,height:400});
}
function modify(){
	var record = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('修改','supplyController.spr?action=forModify&id='+record[0].json.SUPPLIERS_ID,'',find,{width:900,height:600});
}
function view(){
	var record = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('查看','supplyController.spr?action=forView&id='+record[0].json.SUPPLIERS_ID,'',find,{width:900,height:600});
}
function modifyCallback(){
	ds.load({params:{start:0, limit:10}});
}
function find(){
	var url = 'supplyController.spr?action=find&'+Form.serialize('findCondictionFR');
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
 function supplierCallback(){
		var cb = top.ExtModalWindowUtil.getReturnValue();
		var lifnr=cb.LIFNR;
    	
		new AjaxEngine('supplyController.spr?action=updateSupplerMaster&lifnr=' + lifnr,
 												 {method:"post", parameters: '',onComplete: updateMastercallBackHandle});
    }
    
    function updateMastercallBackHandle(trans){
    	var promptMessagebox = new MessageBoxUtil();
		promptMessagebox.Close();
		var responseUtil1 = new AjaxResponseUtils(trans.responseText);
		var customField = responseUtil1.getCustomField("coustom");
		
		if(customField.success == 'true'){
		top.ExtModalWindowUtil.show('修改',
			'supplyController.spr?action=forModify2&id=' + customField.id + '&buttonType=' + buttonType,
			'',
			find,
			{width:900,height:600});
		}else{
			top.ExtModalWindowUtil.alert('提示',customField.msg);
		}
	}
</script>