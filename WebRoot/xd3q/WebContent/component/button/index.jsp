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
<title>Insert title here</title>
<script type="text/javascript">
function btn_clk()
{
	Ext.Msg.show({
   		title:'提示',
   		msg: 'I服了You!!',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			var btn2 = Ext.getCmp("btn2");
 			if(buttonId=='yes'){
				//ExtModalWindowUtil.markUpdated();
				//btn2.disabled = false;
				btn2.setDisabled(false);
			}
 			else
 			{
 				btn2.setDisabled(true);
 			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
	
}

function btn_cls()
{
	var btn2 = Ext.getCmp("btn2");
	btn2.disabled = false;
	btn2.setDisabled(false);
	
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否退出并关闭窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
 			if(buttonId=='yes'){

			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
	//alert("YOU ARE CLECK btnClose!");
}
</script>
</head>
<body>

<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">部门名称：</td>
	<td>
	  	<input type="hidden" id="creditName.fieldName" name="creditName.fieldName" value="creditName"></input>
		<input type="text" id="creditName.fieldValue" name="creditName.fieldValue" ></input>
		<select id="creditName.option" name="creditName.option">
			<option value="greater">大于</option>
			<option value="equal">等于</option>
			<option value="notequal">不等于</option>
			<option value="less">小于</option>
			<option value="lessequal">小于等于</option>
			<option value="greaterequal">大于等于</option>
			<option value="like">LIKE查询</option>
		</select>
	</td>
	<td align="right">信用证号：</td>
	<td>
		<input type="hidden" id="creditNo.fieldName" name="creditNo.fieldName" value="creditNo"></input>
		<input type="text" id="creditNo.fieldValue" name="creditNo.fieldValue" ></input>
		<select id="creditNo.option" name="creditNo.option">
			<option value="greater">大于</option>
			<option value="equal">等于</option>
			<option value="notequal">不等于</option>
			<option value="less">小于</option>
			<option value="lessequal">小于等于</option>
			<option value="greaterequal">大于等于</option>
			<option value="like">LIKE查询</option>
		</select>
	</td>
	<td align="right">开证行：</td>
	<td>
		<input type="hidden" id="createBank.fieldName" name="createBank.fieldName" value="createBank"></input>
		<input type="text" id="createBank.fieldValue"  name="createBank.fieldValue" ></input>
		<select id="createBank.option" name="createBank.option">
			<option value="greater">大于</option>
			<option value="equal">等于</option>
			<option value="notequal">不等于</option>
			<option value="less">小于</option>
			<option value="lessequal">小于等于</option>
			<option value="greaterequal">大于等于</option>
			<option value="like">LIKE查询</option>
		</select>
	</td>
</tr>


<tr>
	<td align="right">数字查询1：</td>
	<td>
	  	<input type="hidden" id="versionNo.fieldName" name="versionNo.fieldName" value="versionNo"></input>
	  	<input type="hidden" id="versionNo.dataType" name="versionNo.dataType" value="N"></input>
		<input type="text" id="versionNo.fieldValue" name="versionNo.fieldValue" ></input>
		<select id="versionNo.option" name="versionNo.option">
			<option value="greater">大于</option>
			<option value="equal">等于</option>
			<option value="notequal">不等于</option>
			<option value="less">小于</option>
			<option value="lessequal">小于等于</option>
			<option value="greaterequal">大于等于</option>
			<option value="like">LIKE查询</option>
		</select>
	</td>
	<td align="right">数字查询2：</td>
	<td>
		<input type="hidden" id="abcNO.dataType" name="abcNO.dataType" value="N"></input>
		<input type="hidden" id="abcNO.fieldName" name="abcNO.fieldName" value="abcNO"></input>
		<input type="text" id="abcNO.fieldValue" name="abcNO.fieldValue" ></input>
		<select id="abcNO.option" name="abcNO.option">
			<option value="greater">大于</option>
			<option value="equal">等于</option>
			<option value="notequal">不等于</option>
			<option value="less">小于</option>
			<option value="lessequal">小于等于</option>
			<option value="greaterequal">大于等于</option>
			<option value="like">LIKE查询</option>
		</select>
	</td>
	<td align="right">数字查询3：</td>
	<td>
		<input type="hidden" id="ppsNO.dataType" name="ppsNO.dataType" value="N"></input>
		<input type="hidden" id="ppsNO.fieldName" name="ppsNO.fieldName" value="ppsNO"></input>
		<input type="text" id="ppsNO.fieldValue"  name="ppsNO.fieldValue" ></input>
		<select id="ppsNO.option" name="ppsNO.option">
			<option value="greater">大于</option>
			<option value="equal">等于</option>
			<option value="notequal">不等于</option>
			<option value="less">小于</option>
			<option value="lessequal">小于等于</option>
			<option value="greaterequal">大于等于</option>
			<option value="like">LIKE查询</option>
		</select>
	</td>
</tr>


<tr>
	<td align="right">日期查询1：</td>
	<td>
	  	<input type="hidden" id="sDate.fieldName" name="sDate.fieldName" value="sDate"></input>
	  	<input type="hidden" id="sDate.dataType" name="sDate.dataType" value="D"></input>
		<div id="sDateDiv" name = "sDateDiv"></div>
		<select id="sDate.option" name="sDate.option">
			<option value="greater">大于</option>
			<option value="equal">等于</option>
			<option value="notequal">不等于</option>
			<option value="less">小于</option>
			<option value="lessequal">小于等于</option>
			<option value="greaterequal">大于等于</option>
			<option value="like">LIKE查询</option>
		</select>
	</td>
	<td align="right">日期查询2：</td>
	<td>
		<input type="hidden" id="eDate.dataType" name="eDate.dataType" value="D"></input>
		<input type="hidden" id="eDate.fieldName" name="eDate.fieldName" value="eDate"></input>
		<div id="eDateDiv" name = "eDateDiv"></div>
		<select id="eDate.option" name="eDate.option">
			<option value="greater">大于</option>
			<option value="equal">等于</option>
			<option value="notequal">不等于</option>
			<option value="less">小于</option>
			<option value="lessequal">小于等于</option>
			<option value="greaterequal">大于等于</option>
			<option value="like">LIKE查询</option>
		</select>
	</td>
	<td align="right">日期查询3：</td>
	<td>
		<input type="hidden" id="ssDate.dataType" name="ssDate.dataType" value="D"></input>
		<input type="hidden" id="ssDate.fieldName" name="ssDate.fieldName" value="ssDate"></input>
		<div id="ssDateDiv" name = "ssDateDiv"></div>
		<select id="ssDate.option" name="ssDate.option">
			<option value="greater">大于</option>
			<option value="equal">等于</option>
			<option value="notequal">不等于</option>
			<option value="less">小于</option>
			<option value="lessequal">小于等于</option>
			<option value="greaterequal">大于等于</option>
			<option value="like">LIKE查询</option>
		</select>
	</td>
</tr>

<tr>
	<td align="right">从：</td>
	<td>
	  	<input type="hidden" id="ab.fieldName" name="ab.fieldName" value="ab"></input>
		<input type="text" id="ab.minValue" name="ab.minValue" ></input>
	    <input type="hidden" id="ab.dataType" name="ab.dataType" value="N"></input>
	   	<input type="hidden" id="ab.isRangeValue" name="ab.isRangeValue" value="true"></input>
	   	
	</td>
	<td align="right">到：</td>
	<td>
	<input type="text" id="ab.maxValue" name="ab.maxValue" ></input>
	</td>
	<td align="right"></td>
	<td>

	</td>
</tr>


</table>

<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
	<tr>
		<td align="right" width="45%">
			<div id="btnSave" name="btnSave"></div>
		</td>
		<td width="5%"></td>
		<td align="left" width="45%">
			<div id="btnClose" name="btnClose"></div>
		</td>
	</tr>
	<tr>
		<td align="center" width="45%" colspan="3">
			<div id="div-submit" name="div-submit"></div>
		</td>
	</tr>
</table>
</body>


</html>
<script type="text/javascript">

function submitMainForm()
{
	Ext.Msg.show({
   		title:'提示',
   		msg: '确认提交流程审批!',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			var btn2 = Ext.getCmp("btn2");
   			var btn1 = Ext.getCmp("btn1");
   			
 			if(buttonId=='yes'){
				btn2.setDisabled(false);
				btn1.setDisabled(false);
	 			btn_btnSave.setText("更改Text成功A！！");
	 			btn_btnClose.setText("更改Text成功B！！");
			}
 			else
 			{
 				btn2.setDisabled(true);
 				btn1.setDisabled(true);

 	 			btn_btnSave.setText("查找");
 	 			btn_btnClose.setText("关闭");
 			}

 			var parentTab = _getMainFrame().maintabs.getActiveTab();
 			alert(parentTab);
 			_getMainFrame().addTab('fdsfdsfsd',parentTab,'');

   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}

Ext.onReady(function(){

	//var btn1 = Ext.getCmp("btn1");
	//btn1.disabled = false;
	//btn1.setDisabled(false);

	 var btnSubmit = new Ext.Button(
			    {
			     	text:'提交审批',
			     	id:'btnSubmit',
			     	name:'btnSubmit',
			     	handler:submitMainForm,
			     	renderTo:'div-submit'
			    }
		    )


		var eDatefieldValue = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:'eDate.fieldValue',
			name:'eDate.fieldValue',
			width: 160,
		    readOnly:true,
		    renderTo:'eDateDiv'
	   	});
	   	
	   	var sDatedataType = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:'sDate.fieldValue',
		    name:'sDate.fieldValue',
			width: 160,
		    readOnly:true,
		    renderTo:'sDateDiv'
	   	});


});

	
	var ssDatefieldValue = new Ext.form.DateField({
		format:'Y-m-d',
	id:'ssDate.fieldValue',
	name:'ssDate.fieldValue',
	width: 160,
    readOnly:true,
    renderTo:'ssDateDiv'
	});
</script> 
<fisc:button text="查找" divId="btnSave" onclick="btn_clk" name="btn1" resourceName="buyOrder.order"></fisc:button>
<fisc:button text="关闭" divId="btnClose" onclick="btn_cls" name="btn2" resourceName="1.2"></fisc:button>

