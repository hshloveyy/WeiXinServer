<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>付款信息管理</title>
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
      <td width="12%" align="right">合同号:</td>
      <td width="20%"><input name="contractNo" type="text" size="20"/></td>
      <td width="10%" align="right">付款时间:</td>
      <td width="20%"><input name="payDate" type="text" size="20" /></td>
    </tr>
    <tr align="center">
      <td align="center" colspan="4"><input type="button" value="查找" onclick="find()"/> <input type="reset" value="清空"/></td>
	</tr>
</table>
</form>
</div>
</body>
</html>
<script type="text/javascript">
document.onkeydown = function(){if (event.keyCode == 13){find();}}
var ds;
var tempGrid;
var callbackFlag;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
    
    var add = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('增加','paymentMoneyController.spr?action=toInfo&tradeType=${tradeType}','',find,{width:600,height:500});
		}
   	});
   	
   	var del = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
   				if (itemGrid.selModel.hasSelection()){
					var records = itemGrid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
					}else{
							top.Ext.Msg.show({
   								title:'提示',
   								msg: '是否确定删除记录',
   								buttons: {yes:'是', no:'否'},
   								fn: function(buttonId,text){
   									callbackFlag = 'del';
 									if(buttonId=='yes'){
 	 									var parm ='?action=delete&infoId='+records[0].json.INFO_ID; 
 										new AjaxEngine('paymentMoneyController.spr',
 												 {method:"post", parameters: parm,onComplete: callBackHandle});
									}
   								},
   							    animEl: 'elId',
   								icon: Ext.MessageBox.QUESTION
							});
					}
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
				//selectWindow.hide();
		}
   	});

    var itemTbar = new Ext.Toolbar({
		items:[add,'-',del]
	});
	//project_name,apply_time,project_no,trade_type,nuder_taker,org_name
	//extComponentServlet?type=grid&grid_columns=PROJECT_ID,PROJECT_NAME,APPLY_TIME,PROJECT_NO&grid_sql=select * from t_project_info&grid_size=10'}),
	var itemStore = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'paymentMoneyController.spr?action=find&tradeType=${tradeType}'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'INFO_ID'},
					{name:'CONTRACT_NO'},
					{name:'PAY_DATE'},
					{name:'REAL_PAY_DATE'},
					{name:'REAL_PAY_TOTAL'},
					{name:'PAY_TOTAL'},
					{name:'PAY_BALANCE'},
					{name:'PURCHASE_CONTRACT_STAUS'}
          		])
    });
    itemStore.load();
    ds = itemStore;
    var itemSm = new Ext.grid.CheckboxSelectionModel();
    var itemCm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		itemSm,
		{
			header: 'ID',
            sortable: false,
            hidden:true,
            dataIndex: 'INFO_ID'
        },{
       		header: '操作',
       		width: 100,
       		sortable: false,
       		dataIndex: 'oper',
       		renderer:operaRD
        },{
			header: '合同ID',
            sortable: false,
            hidden:true,
            dataIndex: 'CONTRACT_ID'
        },{
			header: '合同号',
           	width: 100,
           	dataIndex: 'CONTRACT_NO',
           	sortable: true
           },{
           header: '付款时间',
           sortable: false,
           dataIndex: 'PAY_DATE'
           },
           {header: '实际付款时间',
           width: 100,
           sortable: true,
           dataIndex: 'REAL_PAY_DATE'
           },{
           		header: '应付金额',
          		width: 100,
           		sortable: false,
           		dataIndex: 'PAY_TOTAL'
           },
           {header: '已付款金额',
           width: 100,
           sortable: false,
           dataIndex: 'REAL_PAY_TOTAL'
           },
           {header: '应付余额',
           width: 100,
           sortable: false,
           dataIndex: 'PAY_BALANCE'
           },{
           		header: '采购合同状态',
           		width: 100,
           		sortable: false,
           		dataIndex: 'PURCHASE_CONTRACT_STAUS'
           }
    ]);
    itemCm.defaultSortable = true;
    function renderHallName(value, meta, rec, rowIdx, colIdx, ds){
        return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
    }    
    //审批通过时间渲染
    function approvedTimeRD(value,metadata,record){
    	var apptime = record.data.APPROVED_TIME;
    	if(apptime!=null)
    		return apptime.substring(0,4)+'-'+apptime.substring(4,6)+'-'+apptime.substring(6,8)+' '+apptime.substring(8,10)+':'+apptime.substring(10,12)+':'+apptime.substring(12,14);
    	else
    		return '';	
    }
    function operaRD(value,metadata,record){
        return "<a href='#' onclick='modify()'>修改</a>";
     }
    var paging = new Ext.PagingToolbar({
        pageSize: 10,
        store:itemStore,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: '没有记录'
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
			height:105,
			items:[{contentEl:'main'}]
		},{
			region:"center",
			layout:'fit',
			title:"列表",
			items:[itemGrid]
		}]
	});

		var d1 = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:"payDate",
			width: 150,
			applyTo:'payDate'
	   	});
});
var customCallBackHandle=function(trans){
	var responseUtil1 = new AjaxResponseUtils(trans.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	top.ExtModalWindowUtil.alert('提示',customField.ok);
	if(callbackFlag=='del')
		find();
}

function modify(){
	var record = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('修改','paymentMoneyController.spr?action=forModify&infoId='+record[0].json.INFO_ID,'',find,{width:600,height:500});
}
function modifyCallback(){
	var url = 'paymentMoneyController.spr?action=find&tradeType=${tradeType}';
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
function find(){
	var url = 'paymentMoneyController.spr?action=find&tradeType=${tradeType}&'+Form.serialize('findCondictionFR');;
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
</script>
