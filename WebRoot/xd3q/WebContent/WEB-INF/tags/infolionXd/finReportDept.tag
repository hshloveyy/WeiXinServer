<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="com.infolion.platform.util.StringUtils"%>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/lib/TreeCheckNodeUI.js"></script>
<%@ attribute name="divId" required="true" rtexprvalue="true"	description="显示DIV"%>
<%@ attribute name="width" required="false" rtexprvalue="true"	description="宽度（默认值：100）"%>
<%@ attribute name="rootTitle" required="true" description="根结点标题"%>
<%@ attribute name="isMutilSelect" required="false" description="是否多选"%>
<%@ attribute name="isUseDiv" required="false" rtexprvalue="true" description="是否需要捆绑到div，如果为空或为true为需，false为不需要"%>
<%
	jspContext.setAttribute("contextPath", request.getContextPath());
	jspContext.setAttribute("width", StringUtils.isNullBlank(width) ?  "100":width);
	//判断如果需要与div捆绑就如果没有传就默认要
	jspContext.setAttribute("isUseDiv", StringUtils.isNullBlank(isUseDiv) ?  "true":isUseDiv);
	//默认的组件名字
	jspContext.setAttribute("objName", "dict_"+divId);
	//树load组件名
	jspContext.setAttribute("treeload","treeload_"+divId);
	//树对像名
	jspContext.setAttribute("treename","tree_" + divId);
	//树根对像名
	jspContext.setAttribute("root","root_" + divId);
	//选择内容编号
	jspContext.setAttribute("selectId","selectId_" + divId);
	//选择内容编号
	jspContext.setAttribute("selectText","selectText_" + divId);
	//函数名称
	jspContext.setAttribute("functionName","function" + divId);
%>

<script type="text/javascript">
    Ext.BLANK_IMAGE_URL = '${contextPath}/images/fam/s.gif';
    var ${selectId}="";
    var ${selectText}="";
    
    var ${objName} = new Ext.form.ComboBox({
		store:new Ext.data.SimpleStore({fields:[],data:[[]]}), 
		editable:true, //禁止手写及联想功能
		mode: 'local', 
		triggerAction:'all', 
		maxHeight: 200, 
		id:'${objName}',
		width:${width},
		tpl: '<div id="tree" style="overflow: auto; width: 100%; height: 200px"></div>', //html代码
		selectedClass:'', 
		onSelect:Ext.emptyFn,
		emptyText:'请选择...'
	});
    
    ${objName}.on('collapse',function(){
    	${selectId}="";
    	${selectText}="";
    	var rolenode = ${treename}.getRootNode();
		${functionName}(rolenode,rolenode.text);
    	${objName}.setValue(${selectText});
    });
    
    var ${treeload} = new Ext.tree.TreeLoader({
    	baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
    	url:'finReportController.spr?action=queryDeptInfo'
    });

    ${treeload}.on("beforeload", function(treeLoader, node) {   
                ${treeload}.baseParams.id = node.attributes.id;   
            }, ${treeload});
    
    var ${treename} = new Ext.tree.TreePanel({
    	border:false,
        autoScroll:true,
        animate:true,
        autoWidth:true,
        autoHeight:true,
        enableDD:true,
        //checkModel: 'cascade',
        onlyLeafCheckable: false,
        rootVisible:true,
        containerScroll: true,
        layout:'fit',
        loader: ${treeload}
    }); 
    
    
    
    var ${root} = new Ext.tree.AsyncTreeNode({
        text: '${rootTitle}',
        draggable:false,
        id:'-1'
    });
    /**
    ${treename}.on("check",function(node,checked){
    	var cNode ;
    	if(checked){
    		cNode = node.parentNode;
    		

    	}else{
    		cNode = node.parentNode;
    		while(cNode){
    		  cNode.getUI().checkbox.checked = false;  
    		  cNode.attributes.checked=false;
    		  cNode = cNode.parentNode;
    		}
    	}
    	//alert(node.text+" = "+checked)
    }); //注册"check"事件
    **/
    ${treename}.setRootNode(${root});

    ${objName}.on('expand',function(){ 
		${treename}.render('tree'); 
		${root}.expand();
	    ${treename}.expandAll();
    });
    
	function ${functionName}(prant,children)
	{            
		if(prant.childNodes && prant.childNodes.length>0)
		{
			var list;
			for (var i=0;i<prant.childNodes.length;i++)
			{
	           	list = prant.childNodes[i];
	           	if (list.getUI().checkbox.checked == true)
	           	{
		           	if(${selectId}!=''){
	           			${selectId} += ','+list.id;
	           			${selectText} +=','+list.text;
		           	}else{
		           		${selectId} += list.id;
		           		${selectText} +=list.text;
		           	}
	           	}
	           	
	           	if (list!=null && list.text.length>0 )
	           	{
	              	${functionName}(list,list.text);                        
	           	}
			}
		}
	};
	
	if (${isUseDiv} == true)
		${objName}.render('${divId}');

</script>