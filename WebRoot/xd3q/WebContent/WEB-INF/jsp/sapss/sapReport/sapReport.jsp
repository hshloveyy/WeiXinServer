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
var ziCanziCanTempDS;
var ziCanGrid;
var lirunTempDS;
var lirunGrid;
var xianjinTempDS;
var xianjinGrid;
var tabPanel;
var panel1;
var panel2;
var panel3;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
 	var ziCanDs = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
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
    ziCanTempDS = ziCanDs;
    var ziCanSm = new Ext.grid.CheckboxSelectionModel();
    
    var ziCnCm = new Ext.grid.ColumnModel([
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
    ziCnCm.defaultSortable = true;
    
    var paging = new Ext.PagingToolbar({
        pageSize:10,
        store:ziCanDs,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
 
  ziCanGrid = new Ext.grid.GridPanel({
    	id:'grid',
        ds: ziCanDs,
        cm: ziCnCm,
        border:true,
        loadMask:true,
        autoScroll:true,
        applyTo:'ziCanGridDiv',
        height:350,
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
    /****************************/
    var lirunDs = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
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
    lirunTempDS = lirunDs;
    
    var lirunCm = new Ext.grid.ColumnModel([
		   {
			  header: '行次',
			  width: 50,
			  sortable: true,
			  dataIndex: 'LINEROW',
              renderer:handleValue
		   },{                        
           	  header: '项目',
              width:150,
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
     lirunCm.defaultSortable = true;
 
    lirunGrid = new Ext.grid.GridPanel({
    	id:'lirungrid',
        ds: lirunDs,
        cm: lirunCm,
//        sm: sm,
       // bbar: paging,
        border:false,
        loadMask:true,
        autoScroll:true,
        applyTo:'lirunGridDiv',
        height:350,
        hidden:'true',
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
    /******end lirun and start xianjin**********************/
    var xianjinDs = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            				root: 'root',
            				totalProperty: 'totalProperty'
            			},[
            		{name:'ZSDTXT'},
            		{name:'ZSDDF'},
            		{name:'ZSDLF'}
          		])
    });
    xianjinTempDS = xianjinDs;
    
    var xianjinCm = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),                                       
		   {
           	  header: '项目',
              width:150,
              sortable: true,
              dataIndex: 'ZSDTXT',
              renderer:handleValue
           },{
           	  header: '当期发生数',
              width: 100,
              sortable: true,
              dataIndex: 'ZSDDF',
              renderer:handleValue
           },{
           	  header: '累计发生数',
              width: 100,
              sortable: false,
              dataIndex: 'ZSDLF',
              renderer:handleValue
           }
       ]);
    xianjinCm.defaultSortable = true;
 
    xianjinGrid = new Ext.grid.GridPanel({
    	id:'xianjingrid',
        ds: xianjinDs,
        cm: xianjinCm,
//        sm: sm,
        //bbar: paging,
        border:false,
        loadMask:true,
        autoScroll:true,
        applyTo:'xianjinGridDiv',
        height:350,
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
    
    /******end xianjin*********************/
    
    tabPanel = new Ext.TabPanel({
			        region:'center',
					id:'infotype',
					name:'infotype',
					plain:true
	});
	
    tabPanel1 = tabPanel.add({
                            title:'资产负债表',
							layout:'fit',
							contentEl:'ziCanGridDiv'
	});
	
	tabPanel2 = tabPanel.add({
                            title:'利润表',
							layout:'fit',
							contentEl:'lirunGridDiv'
	});
	
	tabPanel3 = tabPanel.add({
                            title:'现金流量表',
							layout:'fit',
							contentEl:'xianjinGridDiv'
	});
	
	tabPanel.activate(tabPanel1);
    
 	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title:"查询",
			collapsible: true,
			contentEl:'customer'
		},{
		    region:"center",
			layout:'fit',
			border:false,
			items:[tabPanel]
		}]
	});
    	
});//end of Ext.onReady  
function handleValue(value){
   if((value+0)==0) return '';
   return '<span style="color:black">'+Utils.commafy(value)+'</span>';
}

function displayHandle(tagIndex){
	if(tagIndex=='1'){
	  tabPanel.activate(tabPanel1);
	}
	else if(tagIndex=='2'){
	  tabPanel.activate(tabPanel2);
	}
	else if(tagIndex=='3'){
	  tabPanel.activate(tabPanel3);
	}
}  
function find(){
    var reportType = $('reportType').value;
    displayHandle(reportType);
	var url = 'sapReportController.spr?action=findSapReport&noPage=1&'+Form.serialize('materialForm');
	Ext.Ajax.request({
		url: url,
		params:'',
		success: function(response, options){
			var txt = Ext.util.JSON.decode(response.responseText);
			if(txt!=null && txt[0]!=null)
				alert(txt[0].MESSAGE);
			else{
			    if(reportType=='1'){
				   ziCanTempDS.proxy= new Ext.data.HttpProxy({url:url});
				   ziCanTempDS.load({params:{start:0, limit:10}});
				}
				else if(reportType=='2'){
				   lirunTempDS.proxy= new Ext.data.HttpProxy({url:url});
			   	   lirunTempDS.load({params:{start:0, limit:10}});
				}
				else if(reportType=='3'){
				   xianjinTempDS.proxy= new Ext.data.HttpProxy({url:url});
			   	   xianjinTempDS.load({params:{start:0, limit:10}});
				}
			}	
		}
	});	
}
function removeAllStore(){
ziCanTempDS.removeAll();
lirunTempDS.removeAll();
xianjinTempDS.removeAll();
}
</script>
</head>
<body>

<div id="customer" class="x-hide-display">
<form id="materialForm">
<input type="hidden" name="gongsidaima" value="${comCode}"/>
	<table width="70%"  border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
		<tr>
			<td align="right">报表类型</td>
			<td>
			   <select name ="reportType">
			      <option value="1">资产负债表</option>
			      <option value="2">利润表</option>
			      <option value="3">现金流量</option>
			   </select>
			</td>
			<td align="right">会计年度</td>
			<td>
				<select name="kuaijiniandu" onchange="removeAllStore();">
					<option value="2009">2009</option>
					<option value="2010">2010</option>
					<option value="2011">2011</option>
					<option value="2012">2012</option>
					<option value="2013">2013</option>
					<option value="2014">2014</option>
					<option value="2015">2015</option>
				</select>
			
			</td>
			<td align="right">会计期间</td>
			<td>
				<select name="kuaijiqijian" onchange="removeAllStore();">
					<option value="01">01</option>
					<option value="02">02</option>
					<option value="03">03</option>
					<option value="04">04</option>
					<option value="05">05</option>
					<option value="06">06</option>
					<option value="07">07</option>
					<option value="08">08</option>
					<option value="09">09</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
				</select>
			
			</td>
			<td width="100">
				<input type="button" value="查找" onclick="find()"/>   <input type="reset" value="清空"/>
			</td>
		</tr>
	</table>
</form>	
</div>
<br>
<div id="ziCanGridDiv" class="x-hide-display" style=""></div>
<div id="lirunGridDiv" class="x-hide-display" style=""></div>
<div id="xianjinGridDiv" class="x-hide-display" style=""></div>
</body>
</html>
