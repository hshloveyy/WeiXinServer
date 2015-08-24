<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>国内信用证管理页面</title>
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
<form id="findCreditFR" method="post">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
    <tr >
      <td width="12%" height="20" align="right">公司代码:</td>
      <td width="20%"><input name="companyCode" type="text" size="20"/></td>
      <td width="12%" align="right">开证行:</td>
      <td width="20%" colspan="3"><input name="bank" type="text" size="20"/></td>
	  <td width="10%" align="right">收益人名称:</td>
      <td width="20%"><input name="receiver" type="text" size="20"/></td>
	</tr>
	<tr>
	  <td width="12%" height="20" align="right">单据号:</td>
      <td width="20%"><input name="billNO" type="text" size="20"/></td>
      <td width="12%" align="right">开证日:</td>
      <td width="20%" colspan="3"><div id="beginDateDiv"></div></td>
      <td width="10%" align="right">状态:</td>
      <td width="20%">
	  	<select name="isPay">
			<option value="">请选择</option>
			<option value="1">已还款</option>
			<option value="0">未还款</option>
		</select>
	  </td>
    </tr>
    <tr align="center">
      <td align="center" colspan="8"><input type="button" value="查找" onClick="find()"/> <input type="reset" value="清空"/></td>
	</tr>
</table>
</form>
</div>
</body>
</html>
<script type="text/javascript">
var ds;
var tempGrid;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
    
    var add = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('增加国内信用证','creditController.spr?action=create&type=2','',find,{width:800,height:300});
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
 									if(buttonId=='yes'){
 											Ext.Ajax.request({
												url: 'creditController.spr?action=delete&creditID='+records[0].json.CREDIT_ID,
												success: function(response, options){
												   var json = Ext.util.JSON.decode(response.responseText);
													if(json.success){
														Ext.Msg.alert('提示',json.msg);
														find();
													}else{
														Ext.Msg.alert('提示',json.msg);
													}
												}
											});
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
	var itemStore = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'creditController.spr?action=find&type=2'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'CREDIT_ID'},
            		{name:'COMPANY_CODE'},
					{name:'BANK'},
					{name:'AMOUNT'},
					{name:'RECEIVER'},
					{name:'BILLNO'},
					{name:'BEGIN_DATE'},
					{name:'END_DATE'},
					{name:'IS_PAY'},
					{name:'CREATOR_NAME'},
					{name:'CREATOR_ID'},
					{name:'opera'}
          		])
    });
    itemStore.load();
    ds = itemStore;
    var itemSm = new Ext.grid.CheckboxSelectionModel();
    ////project_name,apply_time,project_no,trade_type,nuder_taker,org_name
    var itemCm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		itemSm,{
			header: 'ID',
           width: 100,
           sortable: false,
           hidden:true,
           dataIndex: 'CREDIT_ID'
           },{
			header: '公司代码',
           	width: 100,
           	dataIndex: 'COMPANY_CODE',
           	sortable: true
           },{
           header: '开证行',
           width: 100,
           sortable: false,
           dataIndex: 'BANK',
           renderer:renderHallName
           },
           {header: '金额',
           width: 100,
           sortable: true,
           dataIndex: 'AMOUNT'
           },
		   {
			header: '收益人名称',
			width: 100,
			sortable: false,
			dataIndex: 'RECEIVER'
           },
           {header: '单据号',
           width: 100,
           sortable: false,
           dataIndex: 'CREDIT_ID'
           },{
           		header: '开证日',
           		width: 100,
           		sortable: false,
           		dataIndex: 'BEGIN_DATE'
           },{
           		header: '到期日',
           		width: 100,
           		sortable: true,
           		dataIndex: 'END_DATE'
           },{
           		header: '是否已还款',
           		width: 100,
           		sortable: true,
           		dataIndex: 'IS_PAY',
				renderer:isPayRD
           },{
           		header: '创建者',
           		width: 100,
           		sortable: false,
           		dataIndex: 'CREATOR_NAME'
           },{
           		header: '创建者ID',
           		width: 100,
           		sortable: false,
				hidden: true,
           		dataIndex: 'CREATOR_ID'
           },{
           		header: '操作',
           		width: 100,
           		sortable: false,
           		dataIndex:'opera',
           		renderer: operaRD
           }
    ]);
    itemCm.defaultSortable = true;
    function renderHallName(value, meta, rec, rowIdx, colIdx, ds){
        return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
    }    
	function isPayRD(value,metadata,record){
    	var isPay = record.data.IS_PAY;
    	if(isPay==null)
    		return '';
    	else if(isPay=='1')
    		return '已还款';	
		else if(isPay=='0')
    		return '未还款';	
    }
    function operaRD(value,metadata,record){
    	if('${loginer}'== record.data.CREATOR_ID){
        	return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" onclick="viewProjectForm()">查看</a>';
    	}else{
        	return '<a href="#" onclick="viewProjectForm()">查看</a>';
    	}
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
    	id:'creditGrid',
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
			title:"国内信用证列表",
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
	var beginDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'beginDate',
		name:'beginDate',
		width: 160,
	    readOnly:true,
		renderTo:'beginDateDiv'
   	});

});
function operaForm(){
		if(tempGrid.selModel.hasSelection()){
			var record = tempGrid.selModel.getSelections();
			top.ExtModalWindowUtil.show('修改国内信用证','creditController.spr?action=modify&from=modify&creditID='+record[0].json.CREDIT_ID,'',modifyCallback,{width:800,height:300});
		}
}
function viewProjectForm(){
		if(tempGrid.selModel.hasSelection()){
			var record = tempGrid.selModel.getSelections();
			top.ExtModalWindowUtil.show('查看国内信用证','creditController.spr?action=modify&from=view&creditID='+record[0].json.CREDIT_ID,'',modifyCallback,{width:800,height:300});
		}
}
function modifyCallback(){
	var url = 'creditController.spr?action=find&type=2';
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
function find(){
	var url = 'creditController.spr?action=find&type=2&'+Form.serialize('findCreditFR');
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
</script>