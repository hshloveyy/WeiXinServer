<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资产负债信息</title>
<link href="<%=request.getContextPath()%>/css/public.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.x-grid-row-bgcolor{  
    background-color:#ffffcc;
}
/* Override standard grid styles (add colour to vertical grid lines) */   
.x-grid3-col {   
    border-left:  1px solid #EEEEEE;   
    border-right: 1px solid #D2D2D2;   
}   
    
/* Also remove padding from table data (to compensate for added grid lines) */   
.x-grid3-row td, .x-grid3-summary-row td {   
    padding-left: 0px;   
    padding-right: 0px;   
}  
</style>
<script type="text/javascript">
var grid;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	//{LARGEITEM=01, NAME=一、 营业收入, MIDDLEITEM=00, HEJI=9653315.80, LINEROW=01, LITTLEITEM=00, BENYUE=17094.02}, 
 	var ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'sapReportController.spr?action=rtnZichanfuzhai&noPage=1&kuaijiniandu=${kuaijiniandu}&kuaijiqijian=${kuaijiqijian}&gongsidaima=${gongsidaima}'}),
        		reader: new Ext.data.JsonReader({
            				root: 'root',
            				totalProperty: 'totalProperty'
            			},[
            		{name:'ROW_NO'},
            		{name:'PJ_NAME1'},
            		{name:'FNBL'},
            		{name:'EYBL'},
            		{name:'PJ_NAME2'},
            		{name:'ROW_NO2'},
            		{name:'FNBL2'},
            		{name:'EYBL2'}
          		])
    });
    ds.load({params:{start:0, limit:10}});
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		  {
           	  header: '行次',
              width:30,
              sortable: true,
              dataIndex: 'ROW_NO',
              renderer:handleValue
           },{
           	  header: '资产',
              width: 180,
              sortable: true,
              dataIndex: 'PJ_NAME1',
              renderer:handleValue
           },{
           	  header: '期末余额',
              width: 90,
              sortable: false,
              dataIndex: 'FNBL',
              renderer:handleValue
           },{
           	  header: '年初余额',
              width: 90,
              sortable: false,
              dataIndex: 'EYBL',
              renderer:handleValue
           },{
           	  header: '负债及所有者权益',
              width: 180,
              sortable: false,
              dataIndex: 'PJ_NAME2',
              renderer:handleValue
           },{
           	  header: '行次',
              width: 30,
              sortable: true,
              dataIndex: 'ROW_NO2',
              renderer:handleValue
           },{
           	  header: '期末余额',
              width: 90,
              sortable: false,
              dataIndex: 'FNBL2',
              renderer:handleValue
           },{
           	  header: '年初余额',
              width: 90,
              sortable: false,
              dataIndex: 'EYBL2',
              renderer:handleValue
           }
        ]);
    cm.defaultSortable = true;
    
    
 
  grid = new Ext.grid.GridPanel({
    	id:'grid',
        ds: ds,
        cm: cm,
//        sm: sm,
       // bbar: paging,
        border:true,
        loadMask:true,
        autoScroll:true,
        applyTo:'gridDiv',
        height:350,
		viewConfig : {  
			//注掉此行可以使列不会被拉开
            //forceFit : true, 
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
 	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'fit',
			contentEl:'gridDiv'
		}]
	});
    	
});//end of Ext.onReady   
function handleValue(value){
   if((value+0)==0) return '';
   return '<span style="color:black">'+Utils.commafy(value)+'</span>';
} 
</script>
</head>
<body>
<div id="gridDiv" class="x-hide-display"></div>
</body>
</html>
