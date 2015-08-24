<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="com.infolion.platform.component.processor.ReflectionHandler"%>
<%@ attribute name="divId" required="true" description="div外层的id"%>
<%@ attribute name="width" required="true"  description="tab宽度"%>
<%@ attribute name="tabs" required="true"  description="div内层id,每个tab对应一个div,多列用,分隔"%>
<%@ attribute name="titles" required="true"  description="tab的标题,多列用,分隔"%>
<%@ attribute name="activeTab" required="true"  description="第一个活动页"%>
<%@ attribute name="modelName" required="true"  description="模块,对应用户上下文中的parentId"%>
<%@ attribute name="process" required="true"  description="后台处理类"%>
<%@ attribute name="isNeedAuth" required="true"  description="tag页是否需要授权"%>
<%
String[] needAuth = isNeedAuth.toLowerCase().split(",");boolean[] auth=new boolean[needAuth.length]; for(int j=0;j<auth.length;j++){auth[j]=Boolean.valueOf(needAuth[j]); } 
String[] tab = tabs.split(",");String[] title = titles.split(",");String[] model=modelName.split(",");Boolean[] isDisabled =(Boolean[])ReflectionHandler.process(process,model,tab);%>   
<script type="text/javascript">
var ${divId}Items ;
${divId}Items=[<%for(int i=0;i<tab.length;i++){%>{contentEl:'<%=tab[i]%>',title:'<%=title[i]%>',disabled:<%=(auth[i]&&isDisabled[i])%>}<%if(i<tab.length-1){%>,<%}}%>];
Ext.namespace('${divId}_ns');
${divId}_ns.fuc=function(){
return {
tab:function(){
var tabs = new Ext.TabPanel({
renderTo: '${divId}',
width:${width},
activeTab: ${activeTab},
frame:true,
defaults:{autoHeight: true},
items:${divId}Items
});
}//end of function
};  
}();
Ext.onReady(${divId}_ns.fuc.tab,${divId}_ns.fuc);
</script>

