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
<title>信用证开证管理</title>
<script type="text/javascript">
Ext.onReady(function(){

	 var aa = "";
	
	
	    var availableDataEnd = new Ext.form.DateField({
	   		format:'Y-m-d h:i',
	   		typeAhead: true,  
	   		minValue:'2009-3-1 12:11',
			width: 150,
			timePicker:true,
			allowBlank:false,
			renderTo:'divdate3'
	   	});
	    //renderTo  applyTo
	 
		
	    var datatimefield1 = new Ext.form.DateTimeField(
	    		{
	    		    fieldLabel: 'Time of Day',
	    		    id:'datatimefield1',
	    		    name: 'datatimefield1',
	    		    width:150,	
	        		id:'ddd',
	        		name:'bbbb',
	        		minValue:'2009-05-01',
	        	    minText:'日期选择不能小于 2009-05-01 23:59',
	        	    maxValue:'2009-05-20', 
		        	maxText:'日期选择不能大于 2009-05-20 23:59',
	        		renderTo:'divdate6'
	        	});
	
	
//   	var aab = new DatetimePicker({
//        applyTo:'date6' 
//   	});

	  
	   	new Ext.CycleButton({
	   	    renderTo:Ext.getBody(),
	   	    showText: true,
	   	    prependText: 'View as ',

	   	    items: [{
	   	        text:'text only',
	   	        iconCls:'view-text',
	   	        checked:true
	   	    },{
	   	        text:'HTML',
	   	        iconCls:'view-html'
	   	    },{
	   	        text:'XML',
	   	        iconCls:'view-html'
	   	    }
	   	    ],
	   	    changeHandler:function(btn, item){
	   	        Ext.MessageBox.alert('Change View', item.text);
	   	    }
	   	});


	   	var button=new Ext.SplitButton({
	   	    renderTo:Ext.getBody(),
	   	    arrowHandler : showMenu,
	   	    handler: onItemCheck,
	   	    arrowTooltip : "更多",
	   	    text:'按我',

	   	    menu:'mainMenu'
	   	});

	   	function onItemCheck(item){
	   		Ext.MessageBox.alert("点击事件",String.format("您选择了{0}",item.text));
	   		}
	   		function showMenu(obj){
	   		Ext.MessageBox.alert("点击下拉",obj.getXTypes() )
	   		}
});
	
</script>
</head>
<body>
<form id="mainForm" name="mainForm">
	<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
		<tr>
			<td align="right">测试日期TAG控件:</td>
			<td colspan="4"> 
             
			</td>
		</tr>
		<tr>
			<td align="right">日期查询1(TAG)：</td>
			<td>
		    	 <div id="divdate1" name="divdate1"></div>
			</td>
			<td align="right">日期查询2(TAG)：</td>
			<td>
				 <input id="date11" name="date11" type="text" size="14" tabindex="4"  readonly="readonly" />
			</td>
			<td align="right">日期查询3(原始EXT)：</td>
			<td>
				<div id="divdate3" name="divdate3"></div>
			</td>
		</tr>
		<tr>
			<td align="right">日期查询4(TAG)：</td>
			<td>
		    	 <div id="divdate4" name="divdate4"></div>
			</td>
			<td align="right">日期查询5(TAG)：</td>
			<td>
			   <input id="date5" name="date5" type="text" size="14" tabindex="4"  readonly="readonly" />

			</td>
			<td align="right">日期查询(原始EXT)6：</td>
			<td>
				<div id="divdate6" name="divdate6"></div>
			</td>
		</tr>
		<tr>
			<td align="right"></td>
			<td>
			   <input id="date6" name="date6" type="text" size="14" tabindex="4"  readonly="readonly" />
			</td>
			<td align="right"></td>
			<td>
			</td>
			<td align="center">
				<input type="button" value="查找" onclick="selectDeptInfo()"></input>
				<input type="reset" value="清空"></input>
			</td>
		    <td>
		      <div name="btnDiv" id="btnDiv"></div>
			</td>
		</tr>
	</table>
</form>
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


//部门(受益人)选择窗体
function selectDeptInfo()
{
	_getMainFrame().ExtModalWindowUtil.alert("ddd","dddd");
 	_getMainFrame().ExtModalWindowUtil.show('选择受益人',
	'creditArriveController.spr?action=selectDeptInfo',
	'',
	selectDeptInfoCallBack,
	{width:410,height:400});	
}

function selectDeptInfoCallBack()
{	
alert("fdsfds");
}
	
</script>
<fisc:calendar divId="divdate1" applyTo="" fieldName="PurchaseOrder.memo" maxValue="2009-05-20" maxText="日期不嫩太大了" ></fisc:calendar>
<fisc:calendar divId="" applyTo="date11" fieldName="" extendField="allowBlank:false,emptyText:'请选择日期!!!'," ></fisc:calendar>
<fisc:calendar divId="divdate4" applyTo="" fieldName="PurchaseOrder.memo" maxValue="2009-05-20" maxText="日期不不能小于 2009-05-20" format="Y-m-d h:i"></fisc:calendar>
<fisc:calendar divId="" applyTo="date6" fieldName="" minValue="2009-05-01 23:59:00" minText="日期选择不能小于 2009-05-01 23:59:00" maxValue="2009-05-20 23:59:00" maxText="日期选择不能大于 2009-05-20 23:59:00"  showTime="true"></fisc:calendar>
<fisc:calendar divId="" applyTo="date5" fieldName="" extendField="allowBlank:false,emptyText:'请选择日期!'," minValue="2009-05-05 23:59:00" minText="日期选择不能小于 2009-05-05 23:59:00" maxValue="2009-05-14 11:59:00" maxText="日期选择不能大于 2009-05-14 11:59:00"  showTime="true" ></fisc:calendar>

