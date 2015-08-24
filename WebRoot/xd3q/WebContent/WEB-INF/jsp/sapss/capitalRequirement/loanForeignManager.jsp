<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>外币短期借款管理页面</title>
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
.x-grid-record-red{
	background-color: #FF66FF;
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
	  <td width="12%" height="20" align="right">单据号:</td>
      <td width="20%"><input name="billNO" type="text" size="20"/></td>
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
			top.ExtModalWindowUtil.show('增加外币短期借款','loanForeignController.spr?action=create','',find,{width:800,height:530});
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
												url: 'loanForeignController.spr?action=delete&loanID='+records[0].json.LOAN_ID,
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
        		proxy : new Ext.data.HttpProxy({url:'loanForeignController.spr?action=find'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'LOAN_ID'},
            		{name:'COMPANY_CODE'},
					{name:'AMOUNT_FOREIGN'},
					{name:'CURRENCY'},
					{name:'AMOUNT_CNY'},
					{name:'INSTITUTION'},
					{name:'BALANCE_FOREIGN'},
					{name:'EX_RATE'},
					{name:'BEGIN_DATE'},
					{name:'END_DATE'},
					{name:'IS_PAY'},
					{name:'IS_OVERDUE'},
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
           dataIndex: 'LOAN_ID'
           },{
			header: '公司代码',
           	width: 100,
           	dataIndex: 'COMPANY_CODE',
           	sortable: true
           },
           {header: '外币金额',
           width: 100,
           sortable: true,
           dataIndex: 'AMOUNT_FOREIGN'
           },
           {header: '币别',
           width: 100,
           sortable: true,
           dataIndex: 'CURRENCY'
           },
           {header: '人民币金额',
           width: 100,
           sortable: true,
           dataIndex: 'AMOUNT_CNY'
           },
           {header: '金融机构',
           width: 100,
           sortable: false,
           dataIndex: 'INSTITUTION'
           },
           {header: '外币余额',
           width: 100,
           sortable: false,
           dataIndex: 'BALANCE_FOREIGN'
           },
           {header: '贷款日汇率',
           width: 100,
           sortable: false,
           dataIndex: 'EX_RATE'
           },{
           		header: '贷款日',
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
           		header: '是否逾期未还',
           		width: 100,
           		sortable: true,
           		dataIndex: 'IS_OVERDUE',
				renderer:isOverdueRD
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
	function isOverdueRD(value,metadata,record){
    	var isOverdue = record.data.IS_OVERDUE;
    	if(isOverdue==null)
    		return '';
    	else if(isOverdue=='1')
    		return '已逾期';	
		else if(isOverdue=='0')
    		return '未逾期';	
    } 
    function operaRD(value,metadata,record){
    	if('${loginer}'== record.data.CREATOR_ID&&'0'==record.data.IS_PAY){
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
						if(record.data.IS_OVERDUE=='1'){  
							return 'x-grid-record-red';   
						}
                		else if(rowIndex%2==0)
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
			title:"外币短期借款列表",
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
			top.ExtModalWindowUtil.show('修改外币短期借款','loanForeignController.spr?action=modify&from=modify&loanID='+record[0].json.LOAN_ID,'',modifyCallback,{width:800,height:530});
		}
}
function viewProjectForm(){
		if(tempGrid.selModel.hasSelection()){
			var record = tempGrid.selModel.getSelections();
			top.ExtModalWindowUtil.show('查看外币短期借款','loanForeignController.spr?action=modify&from=view&loanID='+record[0].json.LOAN_ID,'',modifyCallback,{width:800,height:530});
		}
}
function modifyCallback(){
	var url = 'loanForeignController.spr?action=find';
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
function find(){
	var url = 'loanForeignController.spr?action=find&'+Form.serialize('findCreditFR');
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
</script>