<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信用证开证管理</title>
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
<form id="mainForm" name="mainForm">
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

<tr>
	<td align="right"></td>
	<td>
	</td>
	<td align="right"></td>
	<td>
	</td>
	<td align="center">
		<input type="button" value="查找" onclick="findCreditEntryInfo()"></input>
		<input type="reset" value="清空"></input>
	</td>
    <td>
	</td>
</tr>

</table>
</form>
</div>


</body>
</html>

<script type="text/javascript">

//creditEntry 查找按钮的单击事件
function findCreditEntryInfo()
{
	alert(Form.serialize('mainForm'));
	var para = Form.serialize('mainForm');
	new AjaxEngine('<%= request.getContextPath() %>/queryConditionController.spr?action=getQueryExpression&' ,
			 {method:"post", parameters: Form.serialize('mainForm'), onComplete: sCallBackHandle});
	

}
function sCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	showMsg("最后得到的SQL WHERE语句为：" + customField.strSql);
	setTimeout(function(){
	},1000);	
}

//提示窗口
function showMsg(msg){
	_getMainFrame().Ext.Msg.show({
			title:'提示',
			closable:false,
			msg:msg,
			buttons:{yes:'关闭'},
			fn:Ext.emptyFn,
			icon:Ext.MessageBox.INFO
	});
}

Ext.onReady(function(){

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
   	
   	var ssDatefieldValue = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'ssDate.fieldValue',
		name:'ssDate.fieldValue',
		width: 160,
	    readOnly:true,
	    renderTo:'ssDateDiv'
   	});
   	
   });
	
</script>
