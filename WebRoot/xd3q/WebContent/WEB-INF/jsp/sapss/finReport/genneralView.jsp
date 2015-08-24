<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>财务分析报告——经营情况分析</title>
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
<div id="div_northForm">
<form action="" id="findsqlFrom" name="findsqlFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
    <td align="right">板块（公司/部门）：</td>
	<td align="left" colspan="3">
		<div id="dept"></div>
	</td>
</tr>
<tr>
	<td align="right" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;会计年度：</td>
	<td align="left">
		<select name="year" id="year">
		   <option value="2013">2013</option>
		   <option value="2014">2014</option>
		   <option value="2015">2015</option>
		   <option value="2016">2016</option>
		   <option value="2017">2017</option>
		   <option value="2018">2018</option>
		</select>
	</td>
	<td align="right" >
		会计月度：
    </td>
    <td align="left">
		<select name="month" id="month">
		   <option value="1">01</option>
		   <option value="2">02</option>
		   <option value="3">03</option>
		   <option value="4">04</option>
		   <option value="5">05</option>
		   <option value="6">06</option>
		   <option value="7">07</option>
		   <option value="8">08</option>
		   <option value="9">09</option>
		   <option value="10">10</option>
		   <option value="11">11</option>
		   <option value="12">12</option>
		</select>
		<input type="hidden" name="comCode" id="comCode" value="2100"/>
		<input type="hidden" name="busCode" id="busCode" value="2101"/>
		<input type="button" value="查找" onclick="findSqlInfo()"></input>
		<input type="reset" value="清空"></input>
		<input type="button" value="导出EXCEL" onclick="export_()"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_center"></div>

</body>
</html>
<script type="text/javascript">
var myDate = new Date();
document.getElementById("year").value=myDate.getFullYear();
document.getElementById("month").value=(myDate.getMonth()==0?12:myDate.getMonth());

document.onkeydown = function(){if (event.keyCode == 13){findSqlInfo();}}
var strOperType = '0';

var sqlRecord;

var sqlInfogrid;		//信息列表
var sqlInfods;

function export_(){
        var vExportContent = sqlInfogrid.getExcelXml();  
        vExportContent = vExportContent.split('Export GridData').join('经营情况分析');
        alert(vExportContent);
        if (Ext.isIE8||Ext.isIE6 || Ext.isIE7 || Ext.isSafari || Ext.isSafari2 || Ext.isSafari3) {  
            var fd=Ext.get('frmDummy');  
            if (!fd) {  
                fd=Ext.DomHelper.append(Ext.getBody(),{tag:'form',method:'post',id:'frmDummy',action:'exportexcel.jsp', target:'_blank',name:'frmDummy',cls:'x-hidden',cn:[  
                    {tag:'input',name:'fileName',id:'fileName',type:'hidden'},   
                    {tag:'input',name:'exportContent',id:'exportContent',type:'hidden'}  
                ]},true);  
            }  
             fd.child('#fileName').set({value:'exl'});   
            fd.child('#exportContent').set({value:vExportContent});  
            fd.dom.submit();  
        } else {  
            document.location = 'data:application/vnd.ms-excel;base64,'+Base64.encode(vExportContent);  
        }  
              

}

//sql 查找按钮的单击事件
function findSqlInfo(){
	var param='?action=dealDeptBs&deptId='+selectId_dept;
	new AjaxEngine('finReportController.spr', {method:"post", parameters: param, onComplete: findSqlInfocallBackHandle});
}
function findSqlInfocallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var isSuccess = customField.isSuccess;
	var sReturnMessage=customField.returnMessage;
	if(isSuccess=='false'){
		top.ExtModalWindowUtil.alert('提示',sReturnMessage);
	}else if(isSuccess=='true'){
		var busCode = customField.busCode;
		var comCode = customField.comCode;
		$('comCode').value = comCode;
		$('busCode').value = busCode;
		var param = Form.serialize('findsqlFrom');
		var requestUrl = 'finReportController.spr?action=queryGenneral&' + param;
		requestUrl = requestUrl + '&deptId=' + selectId_dept;
		sqlInfods.proxy= new Ext.data.HttpProxy({url:requestUrl,timeout: 900000});
		sqlInfods.load({params:{start:0, limit:30},arg:[]});
	}
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
}



Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
    
    
	var sqlInfoPlant = Ext.data.Record.create([
		{name:'TITLE'},       
		{name:'YBUDGET'},                 
		{name:'MBUDGET'},                 
		{name:'FACT'},     
		{name:'DIFFERENCE'},                 
		{name:'DIFRANGE'},                 
		{name:'DIFREASON'},     
		{name:'YEARTOTAL'},                 
		{name:'YEARDIFF'},                 
		{name:'YEARDIFRANGE'},     
		{name:'YEARDIFFREASON'},                 
		{name:'LASTYEARTOTAL'},                 
		{name:'LASTYEARDIFF'},     
		{name:'LASTYEARRANGE'},                 
		{name:'LASTYEARREASON'}   
	]);

	sqlInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'#'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},sqlInfoPlant)
    });
    
    
    var sqlInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '项目',width: 100,sortable: true,dataIndex: 'TITLE'},
		{header: '当年年预算数',width: 100,sortable: true,dataIndex: 'YBUDGET'},
		{header: '本月预算数',width: 100,sortable: true,dataIndex: 'MBUDGET'},
		{header: '本月实际数',width: 100,sortable: true,dataIndex: 'FACT'},
		{header: '本月预算偏差额',width: 100,sortable: true,dataIndex: 'DIFFERENCE'},
		{header: '本月预算偏差幅度',width: 100,sortable: true,dataIndex: 'DIFRANGE'},
		{header: '本月差异原因分析',width: 100,sortable: true,dataIndex: 'DIFREASON'},
		{header: '本年累计实际数',width: 100,sortable: true,dataIndex: 'YEARTOTAL'},
		{header: '本年累计预算偏差额',width: 100,sortable: true,dataIndex: 'YEARDIFF'},
		{header: '本年累计预算偏差幅度',width: 100,sortable: true,dataIndex: 'YEARDIFRANGE'},
		{header: '本年差异原因分析',width: 100,sortable: true,dataIndex: 'YEARDIFFREASON'},
		{header: '上年同期累计数',width: 100,sortable: true,dataIndex: 'LASTYEARTOTAL'},
		{header: '累计同比增减额',width: 100,sortable: true,dataIndex: 'LASTYEARDIFF'},
		{header: '累计同比增减%',width: 100,sortable: true,dataIndex: 'LASTYEARRANGE'},
		{header: '差异原因分析',width: 100,sortable: true,dataIndex: 'LASTYEARREASON'}
		
    ]);
    sqlInfocm.defaultSortable = true;
    
    sqlInfogrid = new Ext.grid.EditorGridPanel({
    	id:'sqlInfoGrid',
        ds: sqlInfods,
        cm: sqlInfocm,
        //sm: sqlInfosm,
        //bbar: sqlInfobbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
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
			height:80
		},
		{
			region:"center",
			layout:'fit',
		    split:true,
			collapsible: true,
			height:220,
			minSize: 210,
            maxSize: 400,
			title: "经营情况分析表",
			items:[sqlInfogrid]
		}]
	});
	
});


</script>
<fiscxd:finReportDept divId="dept" rootTitle="板块（公司/部门）选择" width="600"></fiscxd:finReportDept>
