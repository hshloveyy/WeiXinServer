<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="com.infolion.platform.dpframework.util.StringUtils"%>

<%@tag import="com.infolion.platform.dpframework.language.LanguageService"%>
<%@tag import="com.infolion.platform.dpframework.language.LangConstants"%>
<script type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/ext/TreeCheckNodeUI.js"></script>
<%@ attribute name="dictionaryName" required="true" rtexprvalue="true"	description="数据字典名称"%>
<%@ attribute name="divId" required="true" rtexprvalue="true"	description="显示DIV"%>
<%@ attribute name="fieldName" required="true" rtexprvalue="true"	description="表单域名称"%>
<%@ attribute name="width" required="false" rtexprvalue="true"	description="宽度（默认值：100）"%>
<%@ attribute name="selectedValue" required="false" rtexprvalue="true"	description="选中值"%>
<%@ attribute name="rootTitle" required="true" description="根结点标题"%>
<%@ attribute name="isUseDiv" required="false" rtexprvalue="true" description="是否需要捆绑到div，如果为空或为true为需，false为不需要"%>
<%
	jspContext.setAttribute("contextPath", request.getContextPath());
	jspContext.setAttribute("width", StringUtils.isNullBlank(width) ?  "100":width);
	//判断如果需要与div捆绑就如果没有传就默认要
	jspContext.setAttribute("isUseDiv", StringUtils.isNullBlank(isUseDiv) ?  "true":isUseDiv);
	jspContext.setAttribute("selectedValue", StringUtils.isNullBlank(selectedValue) ?  "":selectedValue);
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

<script type="text/javascript" defer="defer">
    var ${selectId}="";
    var ${selectText}="";
    
    var ${objName} = new Ext.form.ComboBox({
		store:new Ext.data.SimpleStore({fields:[],data:[[]]}), 
		editable:false, //禁止手写及联想功能
		mode: 'local', 
		triggerAction:'all', 
		maxHeight: 200, 
		width:${width},
		tpl: '<div id="tree" style="overflow: auto; width: 100%; height: 200px"></div>', //html代码
		selectedClass:'', 
		onSelect:Ext.emptyFn,
		emptyText:'<%=LanguageService.getText(LangConstants.SYS_PLEASE_SELECT)%>...'
	});
	
	${objName}.on('expand',function(){ 
            ${treename}.render('tree'); 
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
    	url:'treeDictionaryController.spr?dictName=${dictionaryName}&selectvalue=${selectedValue}'
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
        checkModel: 'cascade',
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
    
    ${treename}.setRootNode(${root});
    
    ${objName}.on('expand',function(){ 
		${treename}.render('tree'); 
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
	           		${selectId} += list.id +"|";
	           		${selectText} +=list.text+"|";
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