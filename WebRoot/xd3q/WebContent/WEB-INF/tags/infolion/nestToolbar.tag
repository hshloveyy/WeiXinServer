<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="com.infolion.platform.basic.component.processor.ToolBar"%>
<%@ attribute name="name" required="true" description="属性名称"%>
<%@ attribute name="modelName" required="true"  description="传递页面ID,用于比较资源中的parentid"%>
<%@ attribute name="pageResourcesName" required="true"  description="用于比较资源中的resourcename"%>
<%@ attribute name="isNeedAuth" required="true"  description="是否要授权"%>
<%@ attribute name="text" required="true"  description="button显示名称"%>
<%@ attribute name="iconCls" required="true"  description="图标cls"%>
<%@ attribute name="handler" required="true"  description="处理器"%>
 <%
	ToolBar bar = new ToolBar();
	boolean[] authbl= bar.justify(request,modelName,pageResourcesName,isNeedAuth);
	String[] txt = text.replaceAll("\t"," ").split(";");
 	String[] cls = iconCls.replaceAll("\t"," ").split(";");
 	String[] hd = handler.replaceAll("\t"," ").split(";");
%>   
 <script type="text/javascript" defer="defer">
 <%for(int i=0;i<txt.length;i++){%>   
 var <%=hd[i]%>${name}=new Ext.Toolbar.Button({text:'<%=txt[i]%>',iconCls:'<%=cls[i]%>',handler:<%=hd[i]%>,disabled:<%=authbl[i]%>});
 <%}%>
var ${name}=new Ext.Toolbar({items:[
<%for(int i=0;i<txt.length;i++){%> 
 	 <%=hd[i]%>${name},'-'<%if(i<txt.length-1){%>,
<%}}%>	
]});
</script>