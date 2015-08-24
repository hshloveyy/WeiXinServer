<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户供应商部门权限</title>
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
   
   <tr>
   	<td><div align="right">客户编码:</div></td>
   	<td  width="30%" >
			<div id="div_custom_sh"></div>
			<fisc:searchHelp divId="div_custom_sh" searchHelpName="YHGETKUNNR" boName="" boProperty="" searchType="field" hiddenName="guestcode" valueField="KUNNR" displayField="NAME1" value=""></fisc:searchHelp>
		</td>
   	<td><div align="right">供应商编码:</div></td>
   	<td  width="30%" >
			<div id="div_supplier_sh"></div>
			<fisc:searchHelp divId="div_supplier_sh" searchHelpName="YHGETLIFNR" boName="" boProperty="" searchType="field" hiddenName="suppliercode" valueField="LIFNR" displayField="NAME1" value=""></fisc:searchHelp>
	</td>
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
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	//project_name,apply_time,project_no,trade_type,nuder_taker,org_name
	//extComponentServlet?type=grid&grid_columns=PROJECT_ID,PROJECT_NAME,APPLY_TIME,PROJECT_NO&grid_sql=select * from t_project_info&grid_size=10'}),
	var itemStore = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'deptByCusuController.spr?action=find'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'DEPTBYCUSUID'},
            		{name:'GUESTCODE'},
            		{name:'SUPPLIERCODE'},
            		{name:'DEPTID'},
            		{name:'GSBER'},
            		{name:'COMPANY_CODE'}
            										
          		])
    });
    //增加
    var addmaterialInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('新增客户供应商部门权限',
			'deptByCusuController.spr?action=createDeptByCusu',
			'',
			find,
			{width:900,height:400}); 
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
					
					     top.Ext.Msg.show({
   								title:'提示',
   								msg: '是否确定删除记录',
   								buttons: {yes:'是', no:'否'},
   								fn: function(buttonId,text){
   									callbackFlag='del';
 									if(buttonId=='yes'){
 	 									var parm ='?action=delete&id='+records[0].json.DEPTBYCUSUID; 
 										new AjaxEngine('deptByCusuController.spr',
 												 {method:"post", parameters: parm,onComplete: delCallback});
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
   	function operaRD(value,metadata,record){
   	
   	if('${addupdate}' == 'true'){
   		 return '<a href="#" onclick="modify();" >修改</a> <a href="#" onclick="view()">查看</a>';
   	}else{
   		 return '<a href="#" onclick="view()">查看</a>';
   	}
   	
    }
     
    
  
    itemStore.load();
    ds = itemStore;
    var itemSm = new Ext.grid.CheckboxSelectionModel();
    var itemCm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		itemSm,{
			header: '部门权限ID',
           width: 50,
           sortable: false,
           hidden:true,
           dataIndex: 'DEPTBYCUSUID'
           },{
          		header: '操作',
          		width: 100,
          		sortable: false,
          		dataIndex: 'oper',
          		renderer:operaRD
          },{
               header: '客户编号',
               width: 100,
               sortable: true,
               dataIndex: 'GUESTCODE'
           },{
			header: '供应商编号',
           width: 50,
           sortable: true,
           dataIndex: 'SUPPLIERCODE'
           },{
			header: '部门编号',
           width: 100,
           sortable: true,
           dataIndex: 'DEPTID'
           },{
			header: '业务范围',
           width: 100,
           sortable: true,
           dataIndex: 'GSBER'
           },{
			header: '公司代码',
           	width: 100,
           	dataIndex: 'COMPANY_CODE',
           	sortable: true
           }
    ]);
    itemCm.defaultSortable = true;
    
     var paging = new Ext.PagingToolbar({
        pageSize: 10,
        store:itemStore,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: '没有记录'
    });
    var itemTbar = new Ext.Toolbar({
		items:[addmaterialInfo,'-'
		
		,deletematerialInfo
		]
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
			height:90,
			items:[{contentEl:'main'}]
		},{
			region:"center",
			layout:'fit',
			title:"客户供应商部门权限一览表",
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


function modify(){
	var record = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('修改','deptByCusuController.spr?action=forModify&id='+record[0].json.DEPTBYCUSUID,'',find,{width:900,height:400});
}
function view(){
	var record = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('查看','deptByCusuController.spr?action=forView&id='+record[0].json.DEPTBYCUSUID,'',find,{width:900,height:400});
}
function delCallback(){
	ds.load({params:{start:0, limit:10}});
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
}
function find(){
	var url = 'deptByCusuController.spr?action=find&'+Form.serialize('findCondictionFR');
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});

}
function funAddreceiptsCallBack(){
    	itemStore.reload();
    }


    
</script>