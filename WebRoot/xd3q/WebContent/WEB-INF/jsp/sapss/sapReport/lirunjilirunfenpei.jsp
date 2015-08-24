<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>利润及利润分配信息</title>
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
var tempDS;
var grid;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	//{LARGEITEM=01, NAME=一、 营业收入, MIDDLEITEM=00, HEJI=9653315.80, LINEROW=01, LITTLEITEM=00, BENYUE=17094.02}, 
 	var ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'sapReportController.spr?action=rtnLirunjilirunfenpei&noPage=1&kuaijiniandu=${kuaijiniandu}&kuaijiqijian=${kuaijiqijian}&gongsidaima=${gongsidaima}'}),
        		reader: new Ext.data.JsonReader({
            				root: 'root',
            				totalProperty: 'totalProperty'
            			},[
            		{name:'LARGEITEM'},
            		{name:'NAME'},
            		{name:'MIDDLEITEM'},
            		{name:'HEJI'},
            		{name:'LINEROW'},
            		{name:'LITTLEITEM'},
            		{name:'BENYUE'}
          		])
    });
    tempDS = ds;
    ds.load({params:{start:0, limit:10}});
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		   {
			  header: '行次',
			  width: 50,
			  sortable: true,
			  dataIndex: 'LINEROW',
              renderer:handleValue
		   },{                        
           	  header: '项目',
              width:300,
              sortable: true,
              dataIndex: 'NAME',
              renderer:handleValue
           },{
           	  header: '本月数',
              width: 100,
              sortable: false,
              dataIndex: 'BENYUE',
              renderer:handleValue
           },{
           	  header: '本年累计数',
              width: 100,
              sortable: false,
              dataIndex: 'HEJI',
              renderer:handleValue
           }
       ]);
    cm.defaultSortable = true;
    
    var paging = new Ext.PagingToolbar({
        pageSize:100,
        store:ds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
 
  grid = new Ext.grid.GridPanel({
    	id:'grid',
        ds: ds,
        cm: cm,
//        sm: sm,
       // bbar: paging,
        border:false,
        loadMask:true,
        autoScroll:true,
        applyTo:'gridDiv',
        height:350,
		viewConfig : {  
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
			title:"利润表",
			contentEl:'gridDiv'
		}]
	});
    	
});//end of Ext.onReady    
function findMaterial(){
	if($('gongsidaimamingchen').value==''){
		top.Ext.Msg.alert('提示','请选择公司');
		return;
	}
	var url = 'sapReportController.spr?action=rtnLirunjilirunfenpei&noPage=1&'+Form.serialize('materialForm');
	Ext.Ajax.request({
		url: url,
		params:'',
		success: function(response, options){
			var txt = Ext.util.JSON.decode(response.responseText);
			if(txt!=null && txt[0]!=null)
				alert(txt[0].MESSAGE);
			else{
				tempDS.proxy= new Ext.data.HttpProxy({url:url});
				tempDS.load({params:{start:0, limit:10}});
			}	
		}
	});	
}
function findGongsidaima(){
	top.ExtModalWindowUtil.show('查找公司',
			'sapReportController.spr?action=toGongsidaima',
			'',callback,{width:800,height:500});
}
function callback(){
	var rtn = top.ExtModalWindowUtil.getReturnValue();
	$('gongsidaima').value=rtn.BUKRS;
	$('gongsidaimamingchen').value=rtn.BUTXT;
}
function handleValue(value){
   if((value+0)==0) return '';
   return '<span style="color:black">'+Utils.commafy(value)+'</span>';
} 
</script>
</head>
<body>

<div id="customer" class="x-hide-display"></div>
<div id="gridDiv" class="x-hide-display"></div>
</body>
</html>
