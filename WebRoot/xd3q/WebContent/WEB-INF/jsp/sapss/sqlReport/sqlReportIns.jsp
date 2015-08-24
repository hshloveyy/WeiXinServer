<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@page import="com.infolion.sapss.sqlReport.domain.TSqlElement"%>
<%@page import="com.infolion.sapss.sqlReport.domain.TSqlFieldDf"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${info.name}报表</title>
<style type="text/css">
.add{
	background-image:url(images/fam/add.gif) !important;
}
.delete{
	background-image:url(images/fam/delete.gif) !important;
}
.reset{
	background-image:url(images/fam/refresh.gif) !important;
}
.find{
	background-image:url(images/fam/find.png) !important;
}
</style>
</head>
<body>
<%
List<TSqlFieldDf> list = (List<TSqlFieldDf>)request.getAttribute("list");
String deptParam = "'&1=1'";
StringBuffer sqlInfoPlantStr = new StringBuffer("");
StringBuffer sqlInfocmStr = new StringBuffer("");
%>
<div id="div_northForm" class="x-hide-display">
<form action="" id="findsqlFrom" name="findsqlFrom" class="datatable">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<%
int i = 1;
int h = 0;//定义查询行条件行高
for(int k = 0 ; k<list.size() ;k++ ){ 
	TSqlFieldDf fdf = list.get(k);
	String fieldName = fdf.getFiledName();
	String filedShowName = fdf.getFiledShowName();
	String width = fdf.getWidth();
	if(width==null) width = "100";
	String isShow = fdf.getIsShow();
	if("1".equals(isShow)) isShow = "false";
	else isShow = "true";
	String isQuery = fdf.getIsQuery();
	if(k == list.size()-1){
	     sqlInfoPlantStr.append("{name:'"+fieldName.toUpperCase()+"'}");
	     sqlInfocmStr.append("{header: '"+filedShowName+"',width: "+width +",sortable: true,dataIndex: '"+fieldName.toUpperCase()+"',hidden:"+isShow+"}"); 
	}else {
		sqlInfoPlantStr.append("{name:'"+fieldName.toUpperCase()+"'},");
	    sqlInfocmStr.append("{header: '"+filedShowName+"',width: "+width +",sortable: true,dataIndex: '"+fieldName.toUpperCase()+"',hidden:"+isShow+"},"); 
	}
	
   if(!"1".equals(isQuery)) continue;
   ////字段类型 0字符、1数值、2日期、3部门、4数据字典、5搜索帮助
   String fieldType = fdf.getFiledType();
   String dicName = fdf.getDicName();
   String displayField = "";
   String valueField ="";
   if("5".endsWith(fieldType)){
	   String[] dicNames = dicName.split(",");
	   dicName = dicNames[0];
	   displayField = dicNames[1];
	   valueField = dicNames[2];
	   request.setAttribute("displayField",displayField);
	   request.setAttribute("valueField",valueField);
   }
   request.setAttribute("fieldName",fieldName);
   request.setAttribute("dicName",dicName);
   if(i%3==1) out.print("<tr>");
%>
	<td align="right"><%=fdf.getFiledShowName() %>：</td>
	<td>
<% if("0".equals(fieldType)){%>
		<input type="text" id="<%=fieldName %>" name="<%=fieldName %>" value=""></input>
<% } else if("1".equals(fieldType)){%>
		<input type="text" id="<%=fieldName %>" name="<%=fieldName %>" value="" size="10"></input>-
		<input type="text" id="<%=fieldName %>1" name="<%=fieldName %>1" value="" size="10"></input>
<% } else if("2".equals(fieldType)){%>
        <table>
          <tr>
          <td><div id="<%=fieldName %>DateDiv"></div></td>
          <td>&nbsp;&nbsp;&nbsp;&nbsp;—</td>
          <td><div id="<%=fieldName %>DateDiv1"></div></td>
          </tr>
         </table>
     <script type="text/javascript">
		var <%=fieldName %>Date = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:'<%=fieldName %>',
			name:'<%=fieldName %>',
			width: 80,
		    readOnly:true,
			renderTo:'<%=fieldName %>DateDiv'
	   	});
		var <%=fieldName %>Date1 = new Ext.form.DateField({
			format:'Y-m-d',
			id:'<%=fieldName %>1',
			name:'<%=fieldName %>1',
			width: 80,
		    readOnly:true,
			renderTo:'<%=fieldName %>DateDiv1'
	   	});
    </script>
<% } else if("3".equals(fieldType)){%>
		<div id="<%=fieldName %>Div"></div>
		<fiscxd:dept divId="${fieldName }Div" rootTitle="部门信息" width="155" isMutilSelect="true"></fiscxd:dept>
<% 
        deptParam="'&"+fieldName+"='+"+"selectId_"+fieldName+"Div";

   } else if("4".equals(fieldType)){%>
		<div id="<%=fieldName %>Div"></div>
		<fiscxd:dictionary divId="${fieldName }Div" fieldName="${fieldName }" dictionaryName="${dicName }" width="150"></fiscxd:dictionary>
		
<% } else if("5".equals(fieldType)){%>
		<div id="<%=fieldName %>Div"></div>
		<fisc:searchHelp boName=""  boProperty="" searchType="field" searchHelpName="${dicName }"  displayField="${displayField }" valueField="${valueField }" hiddenName="${fieldName }" divId="${fieldName }Div"></fisc:searchHelp>
<% } %>
	</td>
<%
   if(i%3==0) { 
	   out.print("</tr>");
       h++;
   }
   i++;
}
%>
<tr>
    <td align="right">&nbsp;</td>
	<td align="left" colspan="5">
	    <input type="hidden" name="sqlElementId" id="sqlElementId" value="${info.sqlElementId }">
		<input type="button" value="查找" onclick="findSqlInfo()"></input>
		<input type="reset" value="清空"></input>
		<input type="button" value="导出EXCEL" onclick="onbeforClickA()"></input>
	</td>
</tr>
</table>
</form>
</div>
<div id="div_center"></div>


</body>
</html>
<script type="text/javascript">
function onbeforClickA(){
	var param = Form.serialize('findsqlFrom');
	var requestUrl = 'sqlReportController.spr?action=dealOutToExcel&'+ param +<%=deptParam%> ;
	window.location.href = requestUrl;
}

document.onkeydown = function(){if (event.keyCode == 13){findSqlInfo();}}
var strOperType = '0';

var sqlRecord;

var sqlInfogrid;		//信息列表
var sqlInfods;

//sql 查找按钮的单击事件
function findSqlInfo(){
	var param = Form.serialize('findsqlFrom');
	var requestUrl = 'sqlReportController.spr?action=queryIns&'+ param +<%=deptParam%> ;
	sqlInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	sqlInfods.load({params:{start:0, limit:10},arg:[]});
}


Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
    
	var sqlInfoPlant = Ext.data.Record.create([
     <%= sqlInfoPlantStr.toString()%>	                                           
	]);

	sqlInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'sqlReportController.spr?action=queryIns'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},sqlInfoPlant)
    });
    
    var sqlInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var sqlInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sqlInfosm,
		<%= sqlInfocmStr.toString()%>	
    ]);
    sqlInfocm.defaultSortable = true;
    
    var sqlInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:sqlInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    sqlInfogrid = new Ext.grid.EditorGridPanel({
    	id:'sqlInfoGrid',
        ds: sqlInfods,
        cm: sqlInfocm,
        sm: sqlInfosm,
        bbar: sqlInfobbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        autowidth:true,
        layout:'fit'
    });
    
    //sqlInfods.load({params:{start:0, limit:10},arg:[]});
    
    sqlInfogrid.addListener('rowclick', sqlInfogridrowclick);
    
    function sqlInfogridrowclick(grid, rowIndex, e){
    	sqlRecord = grid.getStore().getAt(rowIndex);
    }    
 
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:<%=(h+2)*30 + 30%>
		},
		{
			region:"center",
			layout:'fit',
		    split:true,
			collapsible: true,
			height:220,
			minSize: 210,
            maxSize: 400,
			title: "列表",
			items:[sqlInfogrid]
		}]
	});
	
});


</script>
