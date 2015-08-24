<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="" name="deptform">
<table>
<tr>
<td>部 门 名 称:</td>
<td>
<input type="hidden" name="deptid" id="deptid" value="${dept.deptid }"></input>
<input type="text" name="deptname" id="deptname" size="15" value="${dept.deptname}"></input>
</td>
<td>部 门 编 码:</td>
<td>
<input type="text" name="deptcode" id="deptcode" size="15" value="${dept.deptcode}"></input>
</td>
</tr>

<tr>
<td>
上一级部门:
</td>
<td align="left">
<div id="comboxtree" align="left"></div>
</td>
<td></td>
<td>
<input type="hidden"></input>
</td>
</tr>

<tr>
<td>业务部门SAP代码:</td>
<td>
<input type="text" name="ywbmcode" id="ywbmcode" size="15" value="${dept.ywbmcode}"></input>
</td>
<td>利润中心SAP代码:</td>
<td>
<input type="text" name="lrzxcode" id="lrzxcode" size="15" value="${dept.lrzxcode}"></input>
</td>
</tr>

<tr>
<td>成本中心SAP代码:</td>
<td>
<input type="text" name="cbzxcode" id="cbzxcode" size="15" value="${dept.cbzxcode}"></input>
</td>
<td>销售组织SAP代码:</td>
<td>
<input type="text" name="xszzcode" id="xszzcode" size="15" value="${dept.xszzcode}"></input>
</td>
</tr>

<tr>
<td>采购组织SAP代码:</td>
<td>
<input type="text" name="cgzzcode" id="cgzzcode" size="15" value="${dept.cgzzcode}"></input>
</td>
<td>预算组织SAP代码:</td>
<td>
<input type="text" name="yszzcode" id="yszzcode" size="15" value="${dept.yszzcode}"></input>
</td>
</tr>

<tr>
<td colspan="2">
<input type="button" name="deptfind" id="deptfind" value="提交"></input>
</td>
<td></td>
</tr>
</table>
</form>
</body>
</html>
<script type="text/javascript">
var tree;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var comboWithTooltip = new Ext.form.ComboBox({
		store:new Ext.data.SimpleStore({fields:[],data:[[]]}), 
		editable:false, //禁止手写及联想功能
		mode: 'local', 
		triggerAction:'all', 
		maxHeight: 200, 
		tpl: '<div id="tree" style="height:200px"></div>', //html代码
		selectedClass:'', 
		onSelect:Ext.emptyFn,
		emptyText:'请选择...',
		renderTo: 'comboxtree' 
	});

	var systemTreeLoader = new Ext.tree.TreeLoader({
    	url:'orgController.spr?action=queryDeptByParentId'
    });
      
    systemTreeLoader.on("beforeload", function(treeLoader, node) {   
                systemTreeLoader.baseParams.id = node.attributes.id;   
            }, systemTreeLoader);
    
    tree = new Ext.tree.TreePanel({
        border:false, 
        autoScroll:true,
        animate:true,
        autoWidth:true,
        autoHeight:true,
        enableDD:true,
        containerScroll: true,
        loader: systemTreeLoader
    }); 
    
    var root = new Ext.tree.AsyncTreeNode({
        text: '部门信息',
        draggable:false,
        id:'-1'
    });
    
    tree.on("click",function(node,e)
	{       
        if(!node.isLeaf()){
               e.stopEvent();//非叶子节点则不触发
        } else {
	        comboWithTooltip.setValue(node.text);//设置option值
		    comboWithTooltip.collapse();//隐藏option列表
		}
	});
	
	tree.setRootNode(root);
	comboWithTooltip.on('expand',function(){ 
            tree.render('tree'); 
    }); 
});
</script>