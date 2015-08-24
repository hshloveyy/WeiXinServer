<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>贴现管理</title>
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


<div id="div_south"></div>
<div id="main" class="x-hide-display">
<form id="query" method="post">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
    <tr >
	<td align="right" width="10%" >部门名称：</td>
	<td width="26%" >
		<div id="dept"></div>
	</td>    
      <td width="10%" height="20" align="right">项目号:</td>
     <td width="22%"><input name="projectNo" type="text" size="20"/></td>
      <td width="10%" height="20" align="right">合同组编号:</td>
     <td width="22%"><input name="contractGroupNo" type="contractGroupNo" size="20"/></td>     
    </tr>
    <tr >
      <td  align="right">合同号:</td>
      <td><input name="contractNo" type="text" size="20"/></td>
      <td align="right">合同名称:</td>
      <td ><input name="contractName" type="text" size="20"/></td>    
      <td></td>  <td></td>
    </tr>
    <tr align="center">
      <td align="center" colspan="6"><input type="button" value="查找" onclick="find()"/> <input type="reset" value="清空"/></td>
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
			top.ExtModalWindowUtil.show('贴现信息','discountController.spr?action=toBaleLoanInfo','',find,{width:600,height:500});
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
						if (records[0].data.EXAMINE_STATE != "1" )
							top.ExtModalWindowUtil.alert('提示','不能删除已送审的单据！');
						else
							top.Ext.Msg.show({
   								title:'提示',
   								msg: '是否确定删除记录',
   								buttons: {yes:'是', no:'否'},
   								fn: function(buttonId,text){
   									callbackFlag='del';
 									if(buttonId=='yes'){
 	 									var parm ='?action=delete&id='+records[0].json.DISCOUNT_ID; 
 										new AjaxEngine('discountController.spr',
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
        		proxy : new Ext.data.HttpProxy({url:'discountController.spr?action=find'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'DISCOUNT_ID'},
            		{name:'DISCOUNT_NO'},            		
            		{name:'DEPT_NAME'},
					{name:'PROJECT_NO'},
					{name:'CONTRACT_NO'},
					{name:'CONTRACT_NAME'},					
					{name:'APPLY_TIME'},
					{name:'CURRENCY'},
					{name:'APPLY_MONEY'},
					{name:'REAL_MONEY'},
					{name:'BANK'},																											
					{name:'CREATOR'},
					{name:'CREATOR_TIME'},
					{name:'APPLY_TIME'},
					{name:'APPROVED_TIME'},
            		{name: 'EXAMINE_STATE'}, 					
            		{name: 'EXAMINE_STATE_D_EXAMINE_STATE'}										
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
            dataIndex: 'DISCOUNT_ID'
        },{
       		header: '操作',
       		width: 130,
       		sortable: false,
       		dataIndex: 'oper',
       		renderer:operaRD
       },{
            header: '审批状态',
            width: 100,
            sortable: true,
            dataIndex: 'EXAMINE_STATE_D_EXAMINE_STATE'
        },{
			header: '贴现编号',
            sortable: false,
            dataIndex: 'DISCOUNT_NO'
        },{
			header: '项目号',
            sortable: false,
            dataIndex: 'PROJECT_NO'
        },
           {header: '合同号',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_NO'
           },
           {
       		header: '合同名称',
      		width: 100,
       		sortable: false,
       		dataIndex: 'CONTRACT_NAME'
           },
           {
           		header: '币别',
           		width: 100,
           		sortable: false,
           		dataIndex: 'CURRENCY'
           },{
           		header: '申请金额',
           		width: 100,
           		sortable: true,
           		dataIndex: 'APPLY_MONEY'
           },{
           		header: '实际金额',
           		width: 100,
           		sortable: false,
           		dataIndex:'REAL_MONEY'
           },{
           		header: '银行',
           		width: 100,
           		sortable: false,
           		dataIndex: 'BANK'
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
		var state = record.data.EXAMINE_STATE;
		return '<a href="#" onclick="view()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
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
    //    tbar: itemTbar,
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
//
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
  //流程跟踪
function viewHistory()
{
	var records = tempGrid.selModel.getSelections();
//	alert(records[0].data.RECEIPT_ID);
	top.ExtModalWindowUtil.show('贴现审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.DISCOUNT_ID,
		'',	'',	{width:880,height:400});
}
function modify(){
	var record = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('贴现信息','discountController.spr?action=forModify&id='+record[0].json.DISCOUNT_ID,'',find,{width:600,height:500});
}

function view(){
	var record = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('贴现信息','discountController.spr?action=forView&id='+record[0].json.DISCOUNT_ID,'',find,{width:600,height:500});
}

function modifyCallback(){
	var url = 'discountController.spr?action=find&tradeTypeSingle=${tradeType}';
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
function find(){
	var url = 'discountController.spr?action=find&'+Form.serialize('query');
	url = url + '&deptId=' + selectId_dept;		
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"></fiscxd:dept>
