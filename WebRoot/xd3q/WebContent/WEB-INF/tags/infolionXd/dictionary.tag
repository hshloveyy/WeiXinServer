<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="com.infolion.platform.component.dictionary.DictionaryTagHandler"%>
<%@ tag	import="com.infolion.platform.util.StringUtils"%>
<link rel="stylesheet" type="text/css"	href='<%= request.getContextPath() %>/component/dhtmlxCombo/dhtmlxcombo.css'>
<script language="javascript" src="<%= request.getContextPath() %>/component/dhtmlxCombo/dhtmlxcommon.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/component/dhtmlxCombo/dhtmlxcombo.js"></script>
<%@ attribute name="dictionaryName" required="true" rtexprvalue="true"	description="数据字典名称"%>
<%@ attribute name="divId" required="true" rtexprvalue="true"	description="显示DIV"%>
<%@ attribute name="fieldName" required="true" rtexprvalue="true"	description="表单域名称"%>
<%@ attribute name="width" required="false" rtexprvalue="true"	description="宽度（默认值：100）"%>
<%@ attribute name="needDisplayCode" required="false" rtexprvalue="true"	description="是否需要显示code"%>
<%@ attribute name="selectedValue" required="false" rtexprvalue="true"	description="选中值"%>
<%@ attribute name="readonly" required="false" rtexprvalue="true" description="只读（默认值：true）" %>
<%@ attribute name="disable" required="false" rtexprvalue="true" description="失效（默认值：false）" %>
<%
	String jsonOptions = null;
	//是否需要显示编码
	if("true".equals(needDisplayCode))
	{
		jsonOptions = DictionaryTagHandler.getDictionaryArrayStringWithCode(dictionaryName);
	}else{
		jsonOptions = DictionaryTagHandler.getDictionaryArrayString(dictionaryName);
	}	
	jspContext.setAttribute("contextPath", request.getContextPath());
	jspContext.setAttribute("jsonOptions", jsonOptions);
	jspContext.setAttribute("width", StringUtils.isNullBlank(width) ?  "100":width);
    jspContext.setAttribute("readonly", "false".equalsIgnoreCase(readonly) ? "false" : "true");
    jspContext.setAttribute("disable", "true".equalsIgnoreCase(disable) ? "true" : "false");
	jspContext.setAttribute("selectedValue", StringUtils.isNullBlank(selectedValue) ?  "":selectedValue);
	//默认的组件名字
	jspContext.setAttribute("objName", "dict_"+divId);
	//默认图片路径
	jspContext.setAttribute("defaultImgPath",request.getContextPath()+"/component/dhtmlxCombo/imgs/");
%>

<script type="text/javascript">
	window.dhx_globalImgPath="${defaultImgPath}";
	var ${objName}=new dhtmlXCombo('${divId}','${fieldName}','${width}');
	${objName}.addOption(${jsonOptions});
    ${objName}.readonly(${readonly});
    ${objName}.disable(${disable});
	if('${selectedValue}'=='')
        ${objName}.selectOption(0);
    else
        ${objName}.setComboValue('${selectedValue}');
</script>