<%-- 
  - Author(s):黄虎
  - Date: 2009-05-13
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:webeditor控件
  -
--%>

<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="com.infolion.platform.dpframework.util.StringUtils"%>
<%@ attribute name="divId" required="true" rtexprvalue="true" description="div id,用于webeditor定位"%>
<%@ attribute name="boName" required="false"  description="业务对象名"%>
<%@ attribute name="boProperty" required="false"  description="业务对象属性"%>
<%@ attribute name="name" required="false" rtexprvalue="true" description="webeditor名称"%>
<%@ attribute name="value" required="false" rtexprvalue="true" description="webeditor值"%>
<%@ attribute name="disable" required="false"  rtexprvalue="true" description="可编辑"%>

<% 
	jspContext.setAttribute("contextPath", request.getContextPath());
	//默认的组件名字
	jspContext.setAttribute("editorObj", "editor_" + divId);
	jspContext.setAttribute("name", StringUtils.isNullBlank(name) ?  boName+"."+boProperty:name);
%>

<script type="text/javascript" defer="defer">
	function FCKeditor_OnComplete( editorInstance )
	{
		<%if(null!=disable&&"true".equals(disable)){%>
			editorInstance.EditorDocument.body.contentEditable='false'; 
			editorInstance.EditorDocument.designMode='off';
		<%}%>
	}
	
	Ext.onReady(function(){
		var div = '${divId}';
		var ${editorObj} = new FCKeditor('${name}');
		${editorObj}.BasePath = "<%= request.getContextPath() %>/component/FCKeditor/" ;
		${editorObj}.Value = '${value}';
	    
	    <%if(null!=disable&&"true".equals(disable)){%>
	    	//${editorObj}.ToolbarSet = "None" ;
	 	<%}else{%>
	   		//${editorObj}.ToolbarSet = "Basic" ;
	    <%}%>
	    
		$(div).innerHTML = ${editorObj}.CreateHtml();  //使用innerHTML方法，在myFCKeditor div元素里创建编辑器
		
	});
</script>