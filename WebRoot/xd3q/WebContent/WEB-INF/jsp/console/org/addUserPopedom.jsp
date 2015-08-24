<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/ext-2.0/TreeCheckNodeUI.js"></script>
</head>
<body>
<div id="div_west_tree" style="overflow: auto; width: 100%; height: 100%">
</div>
</body>
</html>
<script type="text/javascript">
var deptuserid = '${deptuserid}';

var tree;
var checkresult="";

function addUserManagerDept(){
	var param = param + "?action=addUserManagerDept&deptuserid="+deptuserid+'&deptList='+checkresult;
	new AjaxEngine('orgController.spr', 
		   {method:"post", parameters: param, onComplete: customCallBackHandle});
}

function customCallBackHandle(transport)
{
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var systemTreeLoader = new Ext.tree.TreeLoader({
   		baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
    	url:'orgController.spr?action=queryDeptForTree&deptuserid='+deptuserid
    });
      
    systemTreeLoader.on("beforeload", function(treeLoader, node) {   
                systemTreeLoader.baseParams.id = node.attributes.id;   
            }, systemTreeLoader);
    
    tree = new Ext.tree.TreePanel({
        el:'div_west_tree',
        autoScroll:true,
        animate:true,
        enableDD:true,
        checkModel: 'cascade',
		onlyLeafCheckable: false,
		animate: false,
        rootVisible:true,
        containerScroll: true,
        loader: systemTreeLoader
    }); 
    
    var root = new Ext.tree.AsyncTreeNode({
        text: '业务部门信息',
        draggable:false,
        id:'usercompany'
    });

    tree.setRootNode(root);
    tree.render();
    root.expand();
    tree.expandAll();
    
    function tradeList(prant,children)
	{            
		if(prant.childNodes && prant.childNodes.length>0)
		{
			var list;
			for (var i=0;i<prant.childNodes.length;i++)
			{
	           	list = prant.childNodes[i];
	           	if (list.getUI().checkbox.checked == true)
	           	{
	           		checkresult += list.id +"|";
	           	}
	           	
	           	if (list!=null && list.text.length>0 )
	           	{
	              	tradeList(list,list.text);                        
	           	}
			}
		}
	}
    
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'border',
			items:[{
				region:'center',
				xtype:'panel',
				border:false,
				buttonAlign:'center', 
            	defaults:{bodyStyle:'padding:0px'},
            	items:[tree],
            	buttons:[{
            		text:'提交',
            		handler:function(){
            			var node = tree.getRootNode();
						tradeList(node,node.text);
						addUserManagerDept();
            		}
            	},{
            		text:'关闭',
            		handler:function(){
            			top.ExtModalWindowUtil.close();
            		}
            	}]
			}]
		}]
	});
});
</script>