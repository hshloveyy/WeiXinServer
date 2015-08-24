<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>特殊处理</title>
<link href="<%=request.getContextPath()%>/css/public.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div id="div_center"></div>

<div id="div_south"></div>
<div id="gridTagDiv"></div>

</body>
</body>
</html>
<script type="text/javascript">
var itemStore;
var tempGrid;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
    

	//project_name,apply_time,project_no,trade_type,nuder_taker,org_name
	//extComponentServlet?type=grid&grid_columns=PROJECT_ID,PROJECT_NAME,APPLY_TIME,PROJECT_NO&grid_sql=select * from t_project_info&grid_size=10'}),
	 itemStore = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'masterQueryController.spr?action=findProjectExam&person=${person}'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'PROJECT_NO'},
            		{name:'CONTRACT_SALES_ID'},
            		
          		])
    });
    itemStore.load();
    var itemSm = new Ext.grid.CheckboxSelectionModel();
    var itemCm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		itemSm,
		   {
			header: '立项号',
            sortable: false,
            dataIndex: 'PROJECT_NO'
           },{
			header: '立项Id',
            sortable: true,
            hidden:true,
            dataIndex: 'PROJECT_ID'
           },{
           		header: '操作',
           		width: 100,
           		sortable: false,
           		dataIndex: 'oper',
           		renderer:operaRD
           }
    ]);
    itemCm.defaultSortable = true;
    function operaRD(value,metadata,record){
        if('${person}'=='1')
        	return "<a href='#' onclick='modify()'>总经理审批</a>";
        else
        	return "<a href='#' onclick='modify()'>董事长审批</a>";
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
//        tbar: itemTbar,
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
			region:"center",
			layout:'fit',
			title:"列表",
			items:[itemGrid]
		}]
	});

});


function modify(){
	var record = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批',
			'projectController.spr?action=seniorExamine&businessRecordId='+record[0].json.PROJECT_ID+"&person=${person}",
			'',callback,{width:850,height:600});
}
function callback(){
	itemStore.reload();
};
</script>
