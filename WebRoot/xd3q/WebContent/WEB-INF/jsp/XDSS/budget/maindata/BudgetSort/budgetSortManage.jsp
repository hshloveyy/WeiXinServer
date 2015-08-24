<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月02日 17点04分34秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象预算分类(BudgetSort)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetSort/budgetSortManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetSort/budgetSortManageGen.js"></script>
</head>
<body>
<div id="tree-div">
<div id="div_center_weast"></div>
</div>


<div id="div_center_north">
<div id="div_centerToolbar"></div>

<div id="div_centerForm" class="search">
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:3;formRowNo:10 ;rowNo: 10;columnNo: 1;2X -->
		<td align="right"  width="15%" ><font color="red">★</font>预算分类名称：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="BudgetSort.budsortname" name="BudgetSort.budsortname" value="${budgetSort.budsortname}" <fisc:authentication sourceName="BudgetSort.budsortname" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:4;formRowNo:10 ;rowNo: 10;columnNo: 2;2X -->
		<td align="right"  width="15%" >预算分类描述：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="BudgetSort.budsortdesc" name="BudgetSort.budsortdesc" value="${budgetSort.budsortdesc}" <fisc:authentication sourceName="BudgetSort.budsortdesc" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 12(调试用)序号:6;formRowNo:2 ;rowNo: 02;columnNo: 1;1X -->
		<td align="right"  width="15%" >业务对象：</td>
		<td  width="30%" >
			<div id="div_boid_sh"></div>
			<fisc:searchHelp divId="div_boid_sh" boName="BudgetSort" boProperty="boid" value="${budgetSort.boid}"></fisc:searchHelp>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:5;formRowNo:12 ;rowNo: 12;columnNo: 1;2X -->
		<td align="right"  width="15%" >创建人 ：</td>
		<td  width="30%" >
			<fisc:user boProperty="creator" boName="BudgetSort" userId="${budgetSort.creator}"></fisc:user>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:6;formRowNo:12 ;rowNo: 12;columnNo: 2;2X -->
		<td align="right"  width="15%" >创建日期：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="BudgetSort.createtime" name="BudgetSort.createtime" value="${budgetSort.createtime}"  readonly="readonly" <fisc:authentication sourceName="BudgetSort.createtime" taskId=""/>>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:7;formRowNo:14 ;rowNo: 14;columnNo: 1;2X -->
		<td align="right"  width="15%" >最后修改者 ：</td>
		<td  width="30%" >
			<fisc:user boProperty="lastmodifyer" boName="BudgetSort" userId="${budgetSort.lastmodifyer}"></fisc:user>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:8;formRowNo:14 ;rowNo: 14;columnNo: 2;2X -->
		<td align="right"  width="15%" >最后修改日期 ：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="BudgetSort.lastmodifytime" name="BudgetSort.lastmodifytime" value="${budgetSort.lastmodifytime}"  readonly="readonly" <fisc:authentication sourceName="BudgetSort.lastmodifytime" taskId=""/>>
		</td>
	</tr>

	<input type="hidden" id="BudgetSort.client" name="BudgetSort.client" value="${budgetSort.client}">
	<input type="hidden" id="BudgetSort.budsortid" name="BudgetSort.budsortid" value="${budgetSort.budsortid}">
	<input type="hidden" id="BudgetSort.budupsortid" name="BudgetSort.budupsortid" value="${budgetSort.budupsortid}">
	<input type="hidden" id="BudgetSort.ischange" name="BudgetSort.ischange" value="${budgetSort.ischange}">
</table>
</form>
</div>
</div>
</body>
</html>

<fisc:tree tableName="YBUDSORT"
	entityName="com.infolion.XDSS.budget.maindata.BudgetSort.domain.BudgetSort" height="500"
	rootValue="-1" idColumnName="budsortid"
	parentColumnName="budupsortid" titleColumnName="budsortname"
	treeTitle="预算分类目录" divId="div_center_weast" needCheckChilden="true"
	needAutoLoad="true" rootVisible="true"></fisc:tree>

<script type="text/javascript" defer="defer">
	//存放当前选 中树的节点ID
	var treeNodeId = '';
	//全局路径
	var context = '<%= request.getContextPath() %>';

	//清空form组件的信息
	function resetMainForm()
	{
		$('BudgetSort.client').value = '';
		$('BudgetSort.budsortid').value = '';
		$('BudgetSort.budupsortid').value = '';
		$('BudgetSort.budsortname').value = '';
		$('BudgetSort.budsortdesc').value = '';
		//$('BudgetSort.boid').value = '';
		div_boid_sh_sh.setValue("");
		$('BudgetSort.ischange').value = '';

		$('BudgetSort.createtime').value = '';
		$('BudgetSort.lastmodifytime').value = '';
		$('BudgetSort.creator_text').value = '';
		$('BudgetSort.lastmodifyer_text').value = '';
	}

	//创建同级节点
	function tree_createNode(){
		if (treeNodeId == ''){
			_getMainFrame().showInfo('请选择节点后再进行此功能的操作！');
		}else{
			Ext.getCmp('submit').enable();
	        Ext.getCmp('reset').enable();
			resetMainForm();
			var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
			var parentTreeNode = treeNode.parentNode;

			$('BudgetSort.budsortid').value = "";
			$('BudgetSort.budupsortid').value = parentTreeNode.id;
		}
	}
	
	//创建子节点
	function tree_createSubNode(){
		if (treeNodeId == ''){
			_getMainFrame().showInfo('请选择节点后再进行此功能的操作！');
		}else{
			Ext.getCmp('submit').enable();
	        Ext.getCmp('reset').enable();
			resetMainForm();
			var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
		
			$('BudgetSort.budsortid').value = "";
			$('BudgetSort.budupsortid').value = treeNode.id;
		}
	}

	//删除回
	function deleteCallBackHandle(transport){
		var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
		treeNode.remove();
		treeNodeId = '';
		resetMainForm();
	}
	
	//删除节点
	function tree_deleteNode(){
		if (treeNodeId == ''){
			_getMainFrame().showInfo('请选择节点后再进行此功能的操作！');
		}else{
			var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
			var parentTreeNode = treeNode.parentNode;

			_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '您选择了【删除节点】操作，是否确定继续该操作？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (buttonid == 'yes'){
						if (treeNode.leaf == false){
							_getMainFrame().showInfo('非叶结点，不能删除！');
						}else{
							var param = "&budsortid=" + treeNodeId;
							param += "&action=_delete";
							new AjaxEngine(context + '/XDSS/budget/maindata/BudgetSort/budgetSortController.spr', 
								   {method:"post", parameters: param, onComplete: callBackHandle, callback: deleteCallBackHandle});
						}
					}
				}
			});
		}
	}

	//保存的回调函数
	function saveCallBackHandle(transport){
		var responseUtil = new AjaxResponseUtils(transport.responseText);
		var customField = responseUtil.getCustomField("coustom");
		
		var njson = tranFormToJSON(mainForm);
		var nodeid = $('BudgetSort.budsortid').value;
		if(nodeid==""){
			var pnodeid = njson["BudgetSort.budupsortid"];
			var pnode = div_center_weast_tree.getNodeById(pnodeid);
		    var node = new Ext.tree.AsyncTreeNode({id:customField.budsortid,text:njson["BudgetSort.budsortname"],entityAttributes:njson});
			pnode.appendChild(node);
			node.select();
			node.expand();
			$('BudgetSort.budsortid').value = customField.budsortid;

			treeClick(node);
		}else{
			var node = div_center_weast_tree.getNodeById(nodeid);
			Ext.apply(node.attributes, njson);
			node.setText(njson["BudgetSort.budsortname"]);
		}
	}

	//保存
	function tree_submit(){
		$('BudgetSort.ischange').value = 'Y';
		var param = Form.serialize('mainForm');
		param += "&action=_saveOrUpdate";

		new AjaxEngine(context + '/XDSS/budget/maindata/BudgetSort/budgetSortController.spr', 
		{method:"post", parameters: param, onComplete: callBackHandle, callback: saveCallBackHandle});
	}

	//取消
	function tree_cancel(){
		resetMainForm();
	}
	
	//树的单击事件
	function treeClick(node){
		treeNodeId = node.id;
		resetMainForm();
		//控制工具条的按钮显示状态
		if (node.id == '-1'){
			Ext.getCmp('_createNode').disable();
	        Ext.getCmp('_createSubNode').enable();
			Ext.getCmp('_deleteNode').disable();
	        Ext.getCmp('submit').disable();
	        Ext.getCmp('reset').disable();
		}else{
			Ext.getCmp('_createNode').enable();
	        Ext.getCmp('_createSubNode').enable();
			Ext.getCmp('_deleteNode').enable();
	        Ext.getCmp('submit').enable();
	        Ext.getCmp('reset').enable();

	        Ext.Ajax.request({
		        url: context + '/XDSS/budget/maindata/BudgetSort/budgetSortController.spr?action=getBudSort',
		        params:{'budsortid': node.id},
		        success: function(response, options){
		            var responseArray = Ext.util.JSON.decode(response.responseText);

		            $('BudgetSort.client').value = responseArray.client.trim();
		    		$('BudgetSort.budsortid').value = responseArray.budsortid.trim();
		    		$('BudgetSort.budupsortid').value = responseArray.budupsortid.trim();
		    		$('BudgetSort.budsortname').value = responseArray.budsortname.trim();
		    		$('BudgetSort.budsortdesc').value = responseArray.budsortdesc.trim();
		    		$('BudgetSort.createtime').value = responseArray.createtime.trim();
		    		//$('BudgetSort.boid').value = responseArray.boid.trim();
		    		div_boid_sh_sh.setValue(responseArray.boid.trim(),function(){});
		    		$('BudgetSort.ischange').value = responseArray.ischange.trim();
		    		$('BudgetSort.lastmodifytime').value = responseArray.lastmodifytime.trim();
		    		$('BudgetSort.creator').value = responseArray.creator.trim();
		    		$('BudgetSort.lastmodifyer').value = responseArray.lastmodifyer.trim();

		    		$('BudgetSort.creator_text').value = responseArray.creator_text.trim();
		    		$('BudgetSort.lastmodifyer_text').value = responseArray.lastmodifyer_text.trim();
		        },
		        failure:function(response, options){
		        	_getMainFrame().showInfo('获取该预算分类资源失败！');
		        }
	    	});
		}
	}

Ext.onReady(function(){
	//增加树结点的点击事件
	div_center_weast_tree.on('click',function(node, e){
		treeClick(node);
    });

	var centerToolbars = new Ext.Toolbar({
		items:[
				{id:'_createNode',text:'创建同级节点',handler:tree_createNode,disabled:true,iconCls:'icon-item-add'},'-',
				{id:'_createSubNode',text:'创建子节点',handler:tree_createSubNode,disabled:true,iconCls:'icon-item-add'},'-',
				{id:'_deleteNode',text:'删除节点',handler:tree_deleteNode,disabled:true,iconCls:'icon-item-delete'},'-',
				'->',
				{id:'submit',text:'保存',disabled:false,handler:tree_submit,disabled:true,iconCls:'icon-table-save'},'-',
				{id:'reset',text:'取消',disabled:false,handler:tree_cancel,disabled:true,iconCls:'icon-undo'},'-'
				],
		renderTo:"div_centerToolbar"
	});

   	var viewport = new Ext.Viewport({
   		layout:"border",
		items:[{
			region:"west",
            split:true,
            collapsible: true,
            width: 200,
            minSize: 175,
            maxSize: 400,
            margins:'0 0 0 5',
            layout:'fit',
            layoutConfig:{
               animate:true
            },
            items:[{
                title:'预算分类目录树',
                border:false,
                layout:'fit',
                contentEl: 'tree-div',
                tools:[{
                    id:'refresh',   
                    qtip: '刷新预算分类目录',
                     handler: function(event, toolEl, panel) {
	                	div_center_weast_tree.body.mask('刷新中...');
	                    reload_div_center_weast_tree();
	                    div_center_weast_tree.root.collapse(true, false);
	                    setTimeout(function(){
	                        div_center_weast_tree.body.unmask();
	                    }, 1000);
                     }
                },{
                	id:'maximize',
                	qtip:'展开树',
                	handler:function(event, toolEl, panel) {
                		div_center_weast_tree.expandAll();
                	}
                },{
                	id:'minimize',
                	qtip:'收起树',
                	handler:function(event, toolEl, panel) {
                		div_center_weast_tree.collapseAll();
                	}
                }],
                items:[{contentEl:'div_center_weast'}]
            }]
		},{
			region:'center',
			autoScroll: true,
			border:false,
            items:[{
            	region:'north',
	            layout:'fit',
	            contentEl:'div_center_north'
            }]
		}]
   	});
});
</script>

