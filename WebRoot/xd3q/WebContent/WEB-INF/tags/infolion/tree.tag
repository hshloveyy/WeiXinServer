<%-- 
  - Author(s):黄登辉
  - Date: 2008-12-25
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:树状菜单控件
  -
--%><%@ tag pageEncoding="UTF-8"%><%@
 tag import="org.apache.commons.lang.StringUtils"%><%@
 tag import="java.net.URLEncoder"%><%@ 
 attribute name="divId" required="true" description="需要渲染的div的id"%><%@ 
 attribute name="boName" required="false" description="业务对象名"%><%@ 
 attribute name="sourceClass" required="false" description="取树的数据的Class"%><%@ 
 attribute name="tableName" required="false" description="要生成树的表名"%><%@ 
 attribute name="rootValue" required="false" description="根结点值"%><%@ 
 attribute name="rootVisible" required="false" description="是否显示根结点，默认true"%><%@ 
 attribute name="idColumnName" required="false" description="结点的字段"%><%@ 
 attribute name="titleColumnName" required="false" description="结点要显示的文字的来源字段"%><%@ 
 attribute name="parentColumnName" required="false" description="父节点ID字段名"%><%@ 
 attribute name="linkUrl" required="false" description="节点的链接"%><%@ 
 attribute name="height" required="false" description="树高度"%><%@ 
 attribute name="width" required="false" description="树宽度"%><%@ 
 attribute name="style" required="false" description="树的CSS风格,css路径必需正确"%><%@ 
 attribute name="treeName" required="false" description="需要从treeComponent中获取树时需要传入treeName"%><%@ 
 attribute name="treeTitle" required="true" description="树的名称"%><%@ 
 attribute name="onclick" required="false" description="单击事件"%><%@ 
 attribute name="ondbclick" required="false" description="双击事件"%><%@ 
 attribute name="target" required="false" description="要刷新的框架的target"%><%@ 
 attribute name="leafConditions" required="false" description="子节点的条件"%><%@ 
 attribute name="needCheckChilden" required="false" description="是否需要检查有无子节点，默认为true"%><%@
 attribute name="needAutoLoad" required="false" description="是否需要自动加载树及其下所有子节点，默认为false"%><%@ 
 attribute name="objectName" required="false" description="对象名"%><%@
 attribute name="entityName" required="false" description="实体类名，区别于业务对象，在维护tree时使用，格式为类的全名（例:com.infolion.platform.basicApp.authManage.domain.User）"%><%@
 attribute name="checkClientTableName" required="false" description="有哪些表需要做客户端过滤，有多张表需要用逗号隔开，表名与tableName属性要对应，如果有别名必需要用别名"%><%@ 
 attribute name="whereSql" required="false" description="查询条件"%><%@
 attribute name="autoScroll" required="false" description="是否自动出现滚动条，默认为false"%><%@
 attribute name="orderBySql" required="false" description="排序条件，如果有treeName则无需输入此条件，系统将按TreeComponent的nodeIndex来排序"%><%
	//构造URL用于提交给后台服务类处理
	String url = request.getContextPath()
			+ "/treeController.spr?action=getTreeData";
	if (StringUtils.isNotBlank(boName))
		url += "&boName=" + boName;
	if (StringUtils.isNotBlank(sourceClass))
		url += "&sourceClass=" + sourceClass;
	if (StringUtils.isNotBlank(tableName))
		url += "&tableName=" + tableName;
	if (StringUtils.isNotBlank(idColumnName))
		url += "&idColumnName=" + idColumnName;
	if (StringUtils.isNotBlank(titleColumnName)){
		url += "&titleColumnName=" + java.net.URLEncoder.encode(titleColumnName,"UTF-8");
		//System.out.println(url);
	}
	if (StringUtils.isNotBlank(parentColumnName))
		url += "&parentColumnName=" + parentColumnName;
	if (StringUtils.isNotBlank(linkUrl))
		url += "&linkUrl=" + linkUrl;
	if (StringUtils.isNotBlank(target))
		url += "&target=" + target;
	if (StringUtils.isNotBlank(style))
		url += "&style=" + style;
	if (StringUtils.isNotBlank(treeName))
		url += "&treeName=" + treeName;
	if (StringUtils.isNotBlank(onclick))
		url += "&onclick=" + onclick;
	if (StringUtils.isNotBlank(ondbclick))
		url += "&ondbclick=" + ondbclick;
	if (StringUtils.isNotBlank(rootValue))
		url += "&rootValue=" + rootValue;
	if (StringUtils.isNotBlank(rootVisible))
		url += "&rootVisible=" + rootVisible;
	if (StringUtils.isNotBlank(needCheckChilden))
		url += "&needCheckChilden="+needCheckChilden;
	if (StringUtils.isNotBlank(needAutoLoad))
		url += "&needAutoLoad="+needAutoLoad;
	if (StringUtils.isNotBlank(entityName))
		url += "&entityName="+entityName;
	if (StringUtils.isNotBlank(checkClientTableName))
		url += "&checkClientTableName="+checkClientTableName;
	if (StringUtils.isNotBlank(orderBySql))
		url += "&orderBySql="+orderBySql;
	if (StringUtils.isNotBlank(whereSql))
		url += "&whereSql="+whereSql;
	jspContext.setAttribute("url", url);
	jspContext.setAttribute("contextPath", request.getContextPath());
	
	if(!StringUtils.equalsIgnoreCase("true",autoScroll)){
		autoScroll="false";
	}
	
%><%@tag import="java.net.URLEncoder"
%>
<%@tag import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgHelper"%>
<%@tag import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants"%>
<script type="text/javascript" defer="defer">
var ${divId}_currentUrl = new String("<%=url%>");
var ${divId}_currentNodeId = "";
var ${divId}_tree;
var ${divId}_currentNode;
Ext.onReady(function(){
	var Tree = Ext.tree;
	var treeloader = new Tree.TreeLoader({dataUrl:${divId}_currentUrl});
	var tree = new Tree.ColumnTree({
		el:'${divId}',
		animate:false, 
		<%=StringUtils.isBlank(height)?"height:'auto',":"height:"+height+","%>
		<%=StringUtils.isBlank(width)?"width:'auto',":"width:"+width+","%>
		<%=StringUtils.isBlank(style)?"":"baseCls:'"+style+"',"%>
		autoScroll:'${autoScroll}',
		ddScroll:true,
		rootVisible:<%=StringUtils.isBlank(rootVisible)?"true":rootVisible%>,
		loader:treeloader,//加载函数为loader
		enableDD:false,//设置拖动为false
		border:false,
		<%=StringUtils.isBlank(leafConditions)?"":"leafConditions:'"+leafConditions+"',"%>
		//singleClickExpand:true,
		containerScroll:true,
		selModel: new Ext.tree.DefaultSelectionModel({ 
			listeners: { 
				selectionchange:function(selectionModel, node){
					if(typeof node == 'object' && node != null){
						${divId}_currentNode = node;
						${divId}_currentNodeId = node.id;
					}
				} 
			}
		})
	});
	new Tree.TreeSorter(tree, {folderSort:true,property:'displayOrder'});
	var root = new Tree.AsyncTreeNode({
		text: '${treeTitle}',//树及树的首节点名字
		draggable:false, //首节点不可拖动
		hidden:true,
		//首节点ID值
		id:'<%=StringUtils.isBlank(rootValue) ? "root" : rootValue%>' 
	});<%
	if(!"true".equals(needAutoLoad)){%>
	//加载时传入的参数为当前节点ID
	treeloader.on("beforeload", function(treeloader, node) {
		treeloader.baseParams = {
			'id': node.attributes.id,
			'text':node.attributes.text,
			'level':node.attributes.level
		};
	}, treeloader);<%
	}%>
	tree.setRootNode(root);
	tree.render();
	
	<%if(leafConditions!=null){%>
		tree.on('beforeappend',function(tree, pnode, node){
			try{
				if(tree.leafConditions&&eval(tree.leafConditions))node.leaf = true;
            }catch(e){}
		});
	<%}%>
	
	<%=onclick == null ? "" : "tree.on('click'," + onclick + ");"%>
	<%=ondbclick == null ? "" : "tree.on('dblclick'," + ondbclick + ");"%>
	root.expand(false,false);
	${divId}_tree = tree;<%
	if("true".equals(needAutoLoad)){%>
	${divId}_tree.loader = new Tree.TreeLoader({dataUrl:"${contextPath}/treeController.spr?action=getEmptyTreeData"});
	<%}%>
});

//重新加载整个树
function reload_${divId}_tree(whereSql){
	var url =new String("<%=url%>");
	if (whereSql != null && whereSql != '') {
		url = url.substr(0, url.indexOf('whereSql'));
		url += "whereSql=" + whereSql;
	}
	//alert(url);
	var ${divId}_loader=new Ext.tree.TreeLoader({dataUrl:url});<%
	if(!"true".equals(needAutoLoad)){%>
	${divId}_loader.on("beforeload", function(${divId}_loader, node) {
		${divId}_loader.baseParams = {
			'id': node.attributes.id,
			'text':node.attributes.text,
			'level':node.attributes.level
		};
	}, ${divId}_loader);<%
	}%>
	${divId}_tree.loader = ${divId}_loader;
	${divId}_tree.root.reload();
	${divId}_currentUrl = url;
}

//重新加载某一树节点，未传入节点对象则为当前节点
function reload_${divId}_treeNode(node){
	if(!node)
		node=${divId}_currentNode;
	if(node){
		${divId}_tree.loader.load(node);
		node.expand();
	}
}

//删除树的某一节点
function delete_${divId}_TreeNode(callBackFunction){
	if(${divId}_currentNodeId==null||${divId}_currentNodeId==""){
		showInfo('<%=SysMsgHelper.getDPMessage(SysMsgConstants.SelectNodeToDelete)%>');
		return false;
	}
	if(!callBackFunction||typeof(callBackFunction)!== 'function')
		var callBackFunction=${divId}CallBackHandle;
	var param =new String(${divId}_currentUrl);
	param = param.replace("<%=request.getContextPath()%>/treeController.spr?action=getTreeData","?action=deleteTreeNode");
	param += "&nodeId="+${divId}_currentNodeId;
	//alert(param);
	new AjaxEngine('<%=request.getContextPath()%>/treeController.spr', 
				{method:"post", parameters: param, onComplete: callBackHandle,callback:callBackFunction});	
}

//删除树的某一节点及其子节点
function delete_${divId}_TreeNodeAndSubNode(callBackFunction){
	if(${divId}_currentNodeId==null||${divId}_currentNodeId==""){
		showInfo('<%=SysMsgHelper.getDPMessage(SysMsgConstants.SelectNodeToDelete)%>');
		return false;
	}
	if(!callBackFunction||typeof(callBackFunction)!== 'function')
		var callBackFunction=${divId}CallBackHandle;
	var param =new String(${divId}_currentUrl);
	param = param.replace("<%=request.getContextPath()%>/treeController.spr?action=getTreeData","?action=deleteTreeNodeAndSubNode");
	param += "&nodeId="+${divId}_currentNodeId;
	//alert(param);
	new AjaxEngine('<%=request.getContextPath()%>/treeController.spr', 
				{method:"post", parameters: param, onComplete: callBackHandle,callback:callBackFunction});	
}

function ${divId}CallBackHandle(transport){
	//alert(transport);
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	//top.ExtModalWindowUtil.alert('[提示]',customField.returnMessage);
	showInfo(customField.returnMessage);
	reload_${divId}_treeNode();
}

//取得tree的变动数据
function get${divId}_treeData()
{
	//修改后的对象集合json串
	var ${divId}_modify_ids="&subObject={\"objectName\":\"${objectName}\",\"operType\":\"modify\",\"values\":[{}";
	//删除的对象集合json串
	var ${divId}_remvoe_ids="&subObject={\"objectName\":\"${objectName}\",\"operType\":\"delete\",\"values\":[{}";
	
	var modifies = ${divId}_tree.getModifiedRecords();
	
	for(var i=0;i<modifies.length;i++)
    {
     	${divId}_modify_ids += ","+ Ext.util.JSON.encode(modifies[i].attributes.entityAttributes);
    }
    var removes = ${divId}_tree.getRemovedRecords();
    for(var i=0;i<removes.length;i++)
    {
     	${divId}_remvoe_ids += ","+ Ext.util.JSON.encode(removes[i].attributes.entityAttributes);
    }
    
    ${divId}_modify_ids += "]}";
    ${divId}_remvoe_ids += "]}";
    
    return ${divId}_modify_ids+${divId}_remvoe_ids;
}
</script>