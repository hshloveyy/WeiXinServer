<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<head>
</head>
<body>
<form action="mainTreeController.spr" method="post">
	<table >
		<tr >
			<td align="left">节点名称</td><td><input type="text" value="${node.text}" name="text"/> </td>
		</tr>
		<tr>
			<td>节点样式类</td>
							<td><select name="cls" value="cls" >
										<option label="package" value="package">package</option>
										<option label="cls" value="cls">cls</option>
								</select>
						   </td>
		</tr>
		<tr>
			<td>节点是否有效</td><td>有效：
									<input type="radio" checked="${node.disabled}" name="disabled" value="true"/> 
									无效：
									<input type="radio" checked="${node.disabled}" name="disabled" value="false"/> 
								</td>
		</tr>
		<tr>
			<td>节点链接URL</td><td><input type="text" value="${node.href}" name="href"/> </td>
		</tr>
		<tr>
			<td>节点Id</td><td><input type="text" value="${node.id}" name="id"/> </td>
		</tr>
		<tr>
			<td>节点图标样式</td>
							<td><select name="iconCls" >
										<option label="icon-docs" value="icon-docs" >docs</option>
										<option label="icon-pkg" value="icon-pkg">pkg</option>
										<option label="icon-static" value="icon-static">static</option>
								</select>
						   </td>
		</tr>
		<tr>
			<td>单击展开</td><td>
								<input type="radio" checked="checked" name="singleClickExpand"/>
							</td>
		</tr>
		<tr>
			<td>是否叶子</td><td>是：
								<input type="radio" checked="${node.leaf}" name="leaf" value="true"/>
								不是：
								<input type="radio" checked="${node.leaf}" name="leaf" value="false"/>
							</td>
		</tr>
		<tr>
			<td><input type="submit" value="保存"> </td>
			<td><input type="button" value="按键" onclick="clk()"> </td>
		</tr>
	</table>
</form>
</body>
</html>