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
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
 	    
    tabPanel = new Ext.TabPanel({
		    region:'center',
					id:'infotype',
					name:'infotype',
					plain:true
	});
	
    
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
function find(){
    var reportType = $('reportType').value;
    var kuaijiniandu = $('kuaijiniandu').value;
    var kuaijiqijian = $('kuaijiqijian').value;
    var gongsidaima = $('gongsidaima').value;
    var labelid = reportType+kuaijiniandu+kuaijiqijian;
    var findTab = tabPanel.getItem(labelid);
    var labelurl;
    var labeltitle;
    var context = '<%= request.getContextPath() %>';
    
    if(reportType=='1'){
        labelurl= 'sapReportController.spr?action=toZichanfuzhai&kuaijiniandu='+kuaijiniandu+'&kuaijiqijian='+kuaijiqijian+'&gongsidaima='+gongsidaima;
        labeltitle = '资产负债表'+kuaijiniandu+'/'+kuaijiqijian;
    }
    if(reportType=='2'){
        labelurl= 'sapReportController.spr?action=toLirunjilirunfenpei&kuaijiniandu='+kuaijiniandu+'&kuaijiqijian='+kuaijiqijian+'&gongsidaima='+gongsidaima;
        labeltitle = '利润表'+kuaijiniandu+'/'+kuaijiqijian;
    }
    if(reportType=='3'){
        labelurl= 'sapReportController.spr?action=toXianjinliuliang&kuaijiniandu='+kuaijiniandu+'&kuaijiqijian='+kuaijiqijian+'&gongsidaima='+gongsidaima;
        labeltitle = '现金流量表'+kuaijiniandu+'/'+kuaijiqijian;
    }
    
	if (findTab != null){
		tabPanel.setActiveTab(findTab);
		var currentTab = tabPanel.activeTab;
		currentTab.src = labelurl;
	}else {
	 if (tabPanel.items.length + 1 > 6){
			Ext.MessageBox.alert('提示','当前打开的tab页不能超过6个，请先关闭一个tab页，谢谢！');
	 }else {
	   var newTab = new Ext.Panel({
				title: labeltitle,
				border:false,
				id:labelid,
				layout:'fit',
				closable:true,
				html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+ context + '/' + labelurl +'"></iframe>'
		});
		newTab.on("beforeclose",closeTable);
		tabPanel.add(newTab).show();
	  }
	}
}
function closeTable(tabPanel){
	tabPanel.src = "about:blank";
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
				<select name="kuaijiniandu">
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
				<select name="kuaijiqijian">
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
