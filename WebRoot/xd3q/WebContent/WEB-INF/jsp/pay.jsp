<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工资查询页面</title>
<link href="<%=request.getContextPath()%>/css/public.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var tempDS;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	var customerForm=new Ext.form.FormPanel({
		frame:true,
		renderTo:document.body,
		baseCls:'x-plain',
		autoHeight:true,
		width:800,
		autoWidth:true,
		bodyStyle:'padding: 10px 10px 0 10px;',
		items:[{
			contentEl:'customer'
			},{
			contentEl:'gridDiv'
		}]
	});	
 	var ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'showProcessImgController.spr?action=rtnFindPay'}),
        		reader: new Ext.data.JsonReader({
            				root: 'root',
            				totalProperty: 'totalProperty'
            			},[
            		{name:'ORGTX'},
					{name:'SNAME'},
					{name:'BETRG1'},
					{name:'BETRG2'},
					{name:'BETRG3'},
					{name:'BETRG4'},
					{name:'BETRG5'},
					{name:'BETRG6'},
					{name:'BETRG7'},
					{name:'BETRG8'},
					{name:'BETRG9'},
					{name:'BETRG10'},
					{name:'BETRG11'},
					{name:'BETRG12'},
					{name:'BETRG13'},
					{name:'BETRG14'},
					{name:'BETRG15'},
					{name:'BETRG16'},
					{name:'BETRG17'},
					{name:'BETRG18'},
					{name:'BETRG19'},
					{name:'BETRG20'},
					{name:'BETRG21'},
					{name:'BETRG22'},
					{name:'BETRG23'},
					{name:'BETRG24'},
					{name:'BETRG25'},
					{name:'BETRG26'},
					{name:'BETRG27'}
          		])
    });
    tempDS = ds;
    var sm = new Ext.grid.CheckboxSelectionModel(); 
    var cm = new Ext.grid.ColumnModel([
		  {
           	  header: '部门',
              width: 100,
              sortable: true,
              dataIndex: 'ORGTX'
           },{
           	  header: '姓名',
              width: 60,
              sortable: false,
              dataIndex: 'SNAME'
           },{
           	   header: '基本工资',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG1'
           },{
           	   header: '岗位工资',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG2'
           },{
           	   header: '技能工资',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG3'
           },{
           	   header: '年调',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG4'
           },{
           	   header: '考核奖',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG5'
           },{
           	   header: '司龄工资',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG6'
           },{
           	   header: '外派工资',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG7'
           },{
           	   header: '值班工资',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG8'
           },{
           	   header: '公积金',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG9'
           },{
           	   header: '福利',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG10'
           },{
           	   header: '奖励',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG11'
           },{
           	   header: '补扣',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG12'
           },{
           	   header: '补发',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG13'
           },{
           	   header: '考勤',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG14'
           },{
           	   header: '小计',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG15'
           },{
           	   header: '专车使用',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG16'
           },{
           	   header: '房租水电',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG17'
           },{
           	   header: '住房公积金',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG18'
           },{
           	   header: '企业年金',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG19'
           },{
           	   header: '养老保险',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG20'
           },{
           	   header: '失业保险',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG21'
           },{
           	   header: '医疗保险',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG22'
           },{
           	   header: '工会费',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG23'
           },{
           	   header: '补扣税',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG24'
           },{
           	   header: '所得税',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG25'
           },{
           	   header: '小计',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG26'
           },{
           	   header: '实发工资',
           	   width: 80,
           	   sortable: false,
           	   dataIndex: 'BETRG27'
           }
    ]);
    cm.defaultSortable = true;

 
 var grid = new Ext.grid.GridPanel({
    	id:'grid',
        ds: ds,
        cm: cm,
        sm: sm,
        height:350,
        width:1000,
        border:false,
        loadMask:true,
        autoScroll:true,
        applyTo:'gridDiv',
        autowidth:true,
        layout:'fit'
    });
    
});//end of Ext.onReady    
function findPay(){
	var url = 'showProcessImgController.spr?action=rtnFindPay&'+Form.serialize('customerForm');
	tempDS.proxy= new Ext.data.HttpProxy({url:url});
	tempDS.load({params:{start:0, limit:10}});
}
</script>
</head>
<body>

<div id="customer" class="x-hide-display">
<form id="customerForm">
	<table width="800">
		<tr align="left">
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;核算年度:	
			<select name="year">
			    <option value="2009">2009</option>
			    <option value="2010">2010</option>
			    <option value="2011">2011</option>
			    <option value="2012">2012</option>
			    <option value="2013">2013</option>
			    <option value="2014">2014</option>
			    <option value="2015">2015</option>
			    <option value="2016">2016</option>
			    <option value="2017">2017</option>
			</select>&nbsp;&nbsp;&nbsp;
			核算月份区间:
			<select name="month">
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
			-
			<select name="month1">
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
			<input type="button" value="查找" onclick="findPay();"/>	
			</td>
		</tr>
		<tr></tr>
	</table>
</form>	
</div>
<div id="gridDiv" class="x-hide-display"></div>
<script type="text/javascript">
var d = new Date();
$('month').value=d.getMonth()+1;
$('month1').value=d.getMonth()+1;
$('year').value=d.getYear();
</script>
</body>
</html>
