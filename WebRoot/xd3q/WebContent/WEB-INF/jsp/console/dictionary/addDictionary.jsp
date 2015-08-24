<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="" name="dictform" method="post">
<table border="0" width="100%">
	<tr>
		<td>字 典 表 表 名:</td>
		<td><input id="dictid" name="dictid" type="hidden" value="${dict.dictid}"></input>
		<input id="dicttable" name="dicttable" type="text" value="${dict.dicttable}" size="15"></input>
		</td>
		<td>编 码 字 段 名:</td>
		<td><input id="dictcodecolumn" name="dictcodecolumn" type="text"
			value="${dict.dictcodecolumn}" size="15"></input></td>
	</tr>
	<tr>
		<td>名 称 字 段 名:</td>
		<td><input id="dictnamecolumn" name="dictnamecolumn" type="text"
			value="${dict.dictnamecolumn}" size="15"></input></td>

		<td>字 典 表 类 型:</td>
		<td>
			<!-- <select name="dicttype" id="dicttype" size="1"
			onchange="editfield();" width="125" style="width: 125px">
			<option value="" selected="selected">请选择</option>
			<option value="1">单表字典表</option>
			<option value="2">主从字典表</option>
			</select> -->
			<div id="dicttypeDiv"></div>
		</td>
	</tr>
	<tr>
		<td>字典关键字段名:</td>
		<td><input id="dictkeycolumn" name="dictkeycolumn" type="text"
			value="${dict.dictkeycolumn}" size="15"></input></td>
		<td>父 编 码 字 段:</td>
		<td><input id="dictparentcolumn" name="dictparentcolumn" type="text" 
			value="${dict.dictparentcolumn}" size="15"></input></td>
	</tr>
	<tr>
		<td>备&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp注:</td>
		<td colspan="3"><input type="text" name="cmd" id="cmd" value="${dict.cmd}"
			size="57"></input></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="button" value="保存"
			　name="dictsubmit" id="dictsubmit" onclick="addDictionary();"></input>
		</td>
		<td colspan="2" align="center"><input type="reset" value="继续增加"
			　name="dictreset" id="dictreset" disabled="true"></input></td>
	</tr>
	<tr>
		<td colspan="4"><div id="runInfoDiv" onclick="this.hide();"></div></td>
	</tr>
</table>
</form>
</body>
</html>
<script type="text/javascript">

function editfield()
{
	if (document.dictform.dicttype.value == '1'){
		document.dictform.dictkeycolumn.disabled = true;
		document.dictform.dictkeycolumn.value = '';
		document.dictform.dictparentcolumn.disabled = true;
		document.dictform.dictparentcolumn.value = '';
		
	}
	
	if (document.dictform.dicttype.value == '2'){
		document.dictform.dictkeycolumn.disabled = false;
		document.dictform.dictparentcolumn.disabled = false;
	}
}

function addDictionary(){
	var param = Form.serialize('dictform');
	param += "&action=addDictionary";
	new AjaxEngine('dictController.spr', 
		   {method:"post", parameters: param, onComplete: callBackHandle});
}

function customCallBackHandle(transport){
	if (document.all.dictid.value == ''){
		Ext.MessageBox.confirm("提示","是否需要继续进行此种操作？",function(btn){
			if (btn == "no"){
				top.ExtModalWindowUtil.close();
			}
			else{
				document.all.dictreset.disabled = false;
				document.all.runInfoDiv.hide();
			}
		});
	}else{
		transport.responseText;
		top.ExtModalWindowUtil.close();
	}
}
</script>

<fiscxd:dictionary divId="dicttypeDiv" fieldName="dicttype" dictionaryName="BM_SYS_DICT_TYPE" selectedValue="${dict.dicttype}"></fiscxd:dictionary>