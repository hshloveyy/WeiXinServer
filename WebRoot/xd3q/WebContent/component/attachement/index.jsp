<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib tagdir="/WEB-INF/tags/infolion" prefix="fisc"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试业务附件控件</title>
<script type="text/javascript">
Ext.onReady(function(){
	var tabs = new Ext.TabPanel({
		id:'mainTabPanel',
        renderTo: document.body,
        autoWidth:true,
        activeTab: 0,
        loadMask:true,
        frame:true,
        //defaults:{autoHeight: true},
        items:[
            	{contentEl:'mainDiv',id:'fileEl', title: '立项1212'},
            	{contentEl:'rule',id:'fileEl111', title: '立项附件'}
              ]
    });
});


function handlerActivate(tab){
	//rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
}

</script>
</head>
<body>
<div id="mainDiv">
<form id="mainForm" name="mainForm">
	<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
		<tr>
			<td align="right">测试业务附件控件:</td>
			<td colspan="4"> 
            	 <input name="boIdField" value="2" type="text" class="text" size="25" />
			</td>
		</tr>
	</table>
</form>
</div>

<div id="rule" class="x-hide-display"></div>

</body>

</html>
<script type="text/javascript">

function clk()
{
	var date1 = Ext.getDom("date1");
	var date11 = Ext.getDom("date11");
	var date12 = Ext.getCmp("date12");

	var date13 = calendar_divdate1.value;
	var date14 = calendar_date11.value;

	var date4 = Ext.getDom("date4"); 
	var date5 = Ext.getDom("date5");
	var date6 = Ext.getDom("date6");

	
	var msg = date1.value + " : " +date11.value + " : " + date12.value + " : " + date13 + " : " +date14 + ":date4:" + date4.value +"date5:" + date5.value + " date6:" + date6.value;
	
	Ext.Msg.show({
		title:'提示',
		closable:false,
		msg:msg,
		buttons:{yes:'关闭'},
		fn:Ext.emptyFn,
		icon:Ext.MessageBox.INFO
     });
}
	
</script>

<fisc:attachement allowDelete="true" divId="rule" businessId="" boName="NEWTESTA" gridPageSize="10"  gridWidth="500"></fisc:attachement>
