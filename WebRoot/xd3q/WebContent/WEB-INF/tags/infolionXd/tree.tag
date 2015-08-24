<%@ tag pageEncoding="utf-8"%>
<%@ attribute name="divId" required="true" description="div id"%>
<%@ attribute name="url" required="true" description="数据源路径"%>
<%@ attribute name="height" required="true"  description="树高度"%>
<%@ attribute name="width" required="true"  description="树宽度"%>
<%@ attribute name="rootName" required="true"  description="根结点名称"%>
<%@ attribute name="click" required="false"  description="单击事件"%>
<%@ attribute name="rootId" required="true"  description="根结点ID"%>
<%@ attribute name="sql" required="false"  description="sql语句,必须设置parentId"%>
<%@ attribute name="parentId" required="false"  description="表中充当父结点的字段"%>
<%@ attribute name="id" required="false"  description="表中充当父结点的字段"%>
<%@ attribute name="text" required="false"  description="表中充当父结点的字段"%>

<%
	if(sql!=null && parentId!=null){
		sql = "select "+id+" as id,"+text+" as text,"+parentId+" from ("+sql+")";
		request.getSession().setAttribute("tree_sql",sql);
		request.getSession().setAttribute("tree_parentId",parentId);
		jspContext.setAttribute("url",request.getContextPath()+"/extComponentServlet?type=tree");
	}
%>
<script type="text/javascript">
 Ext.namespace('${divId}_ns');
 var ${divId}_var;
 ${divId}_ns.fuc=function(){
	var Tree = Ext.tree;
 	return {
 		tree:function(){
 		var loader = new Tree.TreeLoader({dataUrl:'${url}'});
        var tree = new Tree.TreePanel({
            el:'${divId}',
            animate:false, 
            height:${height},
            width:${width},
            autoScroll:true,
            loader:loader,
            enableDD:false,
            containerScroll:true
        });
        new Tree.TreeSorter(tree, {folderSort:true});
        var root = new Tree.AsyncTreeNode({
            text: '${rootName}', 
            draggable:false, // disable root node dragging
            id:'${rootId}'
        });
        loader.on("beforeload", function(loader, node) {  
     	   loader.baseParams.id = node.attributes.ID;   
     	   loader.baseParams.text = node.attributes.TEXT;   
        }, loader);
        tree.setRootNode(root);
        tree.render();
        <%=click==null?"":"tree.on('click',"+click+");"%>
        root.expand(false,false);
        ${divId}_var = tree;
 		}//end
 	};  
 }();
 Ext.onReady(${divId}_ns.fuc.tree,${divId}_ns.fuc);
</script>