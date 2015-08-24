<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="com.infolion.platform.basic.component.processor.ToolBar"%>
<%@ attribute name="divId" required="true" description="div id"%>
<%@ attribute name="buttons" required="false" description="button 属性"%>
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
 Ext.namespace('${divId}_ns');
 var ${divId}_var;
 ${divId}_ns.fuc=function(){
 	return {
 		toolBar:function(){
    			var tbr = new Ext.Toolbar();   
    			tbr.render('${divId}');
 				<%for(int i=0;i<txt.length;i++){%>   
 					tbr.addButton(new Ext.Toolbar.Button({text:'<%=txt[i]%>',iconCls:'<%=cls[i]%>',handler:<%=hd[i]%>,disabled:<%=authbl[i]%>}));
 				<%if(i!=txt.length-1){%>
 					tbr.addSpacer();
					tbr.addSeparator();
 				<%}}%>
 				${divId}_var = tbr;
 		}
 	};  
 }();
 Ext.onReady(${divId}_ns.fuc.toolBar,${divId}_ns.fuc);
</script>