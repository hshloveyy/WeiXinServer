<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月03日 09点49分14秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象预算项分组(BudgetItemGroup)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetItemGroup/budgetItemGroupAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetItemGroup/budgetItemGroupAddGen.js"></script>
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
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.budgroupname}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="BudgetItemGroup.budgroupname" name="BudgetItemGroup.budgroupname" value="${budgetItemGroup.budgroupname}" <fisc:authentication sourceName="BudgetItemGroup.budgroupname" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:4;formRowNo:10 ;rowNo: 10;columnNo: 2;2X -->
		<td align="right"  width="15%" >${vt.property.budgroupdesc}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="BudgetItemGroup.budgroupdesc" name="BudgetItemGroup.budgroupdesc" value="${budgetItemGroup.budgroupdesc}" <fisc:authentication sourceName="BudgetItemGroup.budgroupdesc" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:5;formRowNo:12 ;rowNo: 12;columnNo: 1;2X -->
		<td align="right"  width="15%" >${vt.property.creator}：</td>
		<td  width="30%" >
			<fisc:user boProperty="creator" boName="BudgetItemGroup" userId="${budgetItemGroup.creator}"></fisc:user>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:6;formRowNo:12 ;rowNo: 12;columnNo: 2;2X -->
		<td align="right"  width="15%" >${vt.property.createtime}：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="BudgetItemGroup.createtime" name="BudgetItemGroup.createtime" value="${budgetItemGroup.createtime}"  readonly="readonly" <fisc:authentication sourceName="BudgetItemGroup.createtime" taskId=""/>>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:7;formRowNo:14 ;rowNo: 14;columnNo: 1;2X -->
		<td align="right"  width="15%" >${vt.property.lastmodifyer}：</td>
		<td  width="30%" >
			<fisc:user boProperty="lastmodifyer" boName="BudgetItemGroup" userId="${budgetItemGroup.lastmodifyer}"></fisc:user>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:8;formRowNo:14 ;rowNo: 14;columnNo: 2;2X -->
		<td align="right"  width="15%" >${vt.property.lastmodifytime}：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="BudgetItemGroup.lastmodifytime" name="BudgetItemGroup.lastmodifytime" value="${budgetItemGroup.lastmodifytime}"  readonly="readonly" <fisc:authentication sourceName="BudgetItemGroup.lastmodifytime" taskId=""/>>
		</td>
	</tr>

	<input type="hidden" id="BudgetItemGroup.client" name="BudgetItemGroup.client" value="${budgetItemGroup.client}">
	<input type="hidden" id="BudgetItemGroup.budgroupid" name="BudgetItemGroup.budgroupid" value="${budgetItemGroup.budgroupid}">
	<input type="hidden" id="BudgetItemGroup.budupgroupid" name="BudgetItemGroup.budupgroupid" value="${budgetItemGroup.budupgroupid}">
</table>
</form>
</div>
</div>

<div id="div_center_south"></div>
</body>
</html>

<fisc:tree tableName="YBUDITEMGROUP"
	entityName="com.infolion.XDSS.budget.maindata.BudgetItemGroup.domain.BudgetItemGroup" height="500"
	rootValue="-1" idColumnName="budgroupid"
	parentColumnName="budupgroupid" titleColumnName="budgroupname"
	treeTitle="预算项分组目录" divId="div_center_weast" needCheckChilden="true"
	needAutoLoad="true" rootVisible="true"></fisc:tree>
	

	
<fisc:grid divId="div_center_south" 
	boName="BudgetItem" 
	needCheckBox="true" 
	title="预算项信息"
	editable="true" 
	defaultCondition=" YBUDITEM.BUDGROUPID='${budgetItemGroup.budgroupid}'" 
	needAutoLoad="true" 
	height="285" nameSapceSupport="true"></fisc:grid>


<script type="text/javascript" defer="defer">
//页面文本
var Txt={
	//预算项分组
	budgetItemGroup:'${vt.boText}',
	          
	//预算项
	budgetItem:'${budgetItemVt.boText}',
	//boText创建
	budgetItem_Create:'${budgetItemVt.boTextCreate}',
	//boText复制创建
	budgetItem_CopyCreate:'${budgetItemVt.boTextCopyCreate}',
	// 进行【预算项复制创建】操作时，只允许选择一条记录！
	budgetItem_CopyCreate_AllowOnlyOneItemForOperation:'${budgetItemVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【预算项复制创建】操作的记录！
	budgetItem_CopyCreate_SelectToOperation:'${budgetItemVt.copyCreate_SelectToOperate}',
	// 请选择需要进行【预算项批量删除】操作的记录！
	budgetItem_Deletes_SelectToOperation:'${budgetItemVt.deletes_SelectToOperate}',
	// 您选择了【预算项批量删除】操作，是否确定继续该操作？
	budgetItem_Deletes_ConfirmOperation:'${budgetItemVt.deletes_ConfirmOperation}',
	//boText复制创建
	budgetItemGroup_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	budgetItemGroup_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	budgetItemGroup_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
	// '提交成功！'
	submitSuccess:'${vt.submitSuccess}',
	// '操作成功！'
	operateSuccess:'${vt.operateSuccess}',
	// 提示
	cue:'${vt.sCue}',
	// 确定
	ok:'${vt.sOk}',
	// 取消
	cancel:'${vt.sCancel}'
};

	//存放当前选 中树的节点ID
	var treeNodeId = '';
	//全局路径
	var context = '<%= request.getContextPath() %>';


	/**
	 * grid 上的 创建按钮调用方法 
	 * 新增预算项
	 */
	/*function _create()
	{
		//alert(treeNodeId);
		
		if (treeNodeId == '-1' || treeNodeId == ''){
			_getMainFrame().showInfo('请选择节点后再进行此功能的操作！');
		}else{
			if ($('BudgetItemGroup.budgroupid').value == ''){
				_getMainFrame().showInfo('请保存节点后再进行此功能的操作！');
			}else{
				var para = "&budgroupid=" + $('BudgetItemGroup.budgroupid').value;
				alert(para);
				//增加页签
				_getMainFrame().maintabs.addPanel(Txt.budgetItem_Create,BudgetItem_grid,contextPath + '/XDSS/budget/maindata/BudgetItem/budgetItemController.spr?action=_create'+ para);
			}
		}
	}*/

	//创建并新增一个空结点
	function createNewNode(parentNodeId){
		var parentNode = div_center_weast_tree.getNodeById(parentNodeId);

		var chlidNode=new Ext.tree.TreeNode({
			id:'new',
			text:'',
			leaf : true
		});
		
		parentNode.leaf = false;
		parentNode.appendChild(chlidNode);
		parentNode.expand();

		Ext.getCmp('_createNode').disable();
        Ext.getCmp('_createSubNode').disable();
		Ext.getCmp('_deleteNode').disable();
	}

	//删除新增的结点
	function deleteNewNode(){
		var treeNode = div_center_weast_tree.getNodeById("new");
    	if (treeNode != null)
    	{
    		var parentNode = treeNode.parentNode;
    		treeNode.remove();
			if (parentNode.firstChild == null)
			{
				parentNode.leaf = true;
			}
    	}
	}

	//清空form组件的信息
	function resetMainForm()
	{
		$('BudgetItemGroup.client').value = '';
		$('BudgetItemGroup.budgroupid').value = '';
		$('BudgetItemGroup.budupgroupid').value = '';
		$('BudgetItemGroup.budgroupname').value = '';
		$('BudgetItemGroup.budgroupdesc').value = '';

		$('BudgetItemGroup.creator').value = '';
		$('BudgetItemGroup.lastmodifyer').value = '';
		$('BudgetItemGroup.createtime').value = '';
		$('BudgetItemGroup.lastmodifytime').value = '';
		$('BudgetItemGroup.creator_text').value = '';
		$('BudgetItemGroup.lastmodifyer_text').value = '';
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

			$('BudgetItemGroup.budgroupid').value = "";
			$('BudgetItemGroup.budupgroupid').value = parentTreeNode.id;

			createNewNode(parentTreeNode.id);

			//刷新数据集
			reload_BudgetItem_grid("defaultCondition=YBUDITEM.BUDGROUPID='1'");
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
		
			$('BudgetItemGroup.budgroupid').value = "";
			$('BudgetItemGroup.budupgroupid').value = treeNode.id;

			createNewNode(treeNode.id);

			//刷新数据集
			reload_BudgetItem_grid("defaultCondition=YBUDITEM.BUDGROUPID='1'");
		}
	}

	//删除回调函数
	function deleteCallBackHandle(transport){
		var treeNode = div_center_weast_tree.getNodeById(treeNodeId);

		var parentNode = treeNode.parentNode;
		treeNode.remove();
		if (parentNode.firstChild == null)
		{
			parentNode.leaf = true;
		}

		treeNodeId = '';
		resetMainForm();

		Ext.getCmp('_createNode').disable();
        Ext.getCmp('_createSubNode').disable();
		Ext.getCmp('_deleteNode').disable();
        Ext.getCmp('submit').disable();
        Ext.getCmp('reset').disable();
		
		//刷新数据集
		reload_BudgetItem_grid("defaultCondition=YBUDITEM.BUDGROUPID='1'");
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
							var param = "&budgroupid=" + treeNodeId;
							param += "&action=_delete";
							new AjaxEngine(context + '/XDSS/budget/maindata/BudgetItemGroup/budgetItemGroupController.spr', 
								   {method:"post", parameters: param, onComplete: callBackHandle, callback: deleteCallBackHandle});
						}
					}
				}
			});
		}
	}

	//保存的回调函数
	function saveCallBackHandle(transport){
		deleteNewNode();
		
		var responseUtil = new AjaxResponseUtils(transport.responseText);
		var customField = responseUtil.getCustomField("coustom");
		
		var njson = tranFormToJSON(mainForm);
		var nodeid = $('BudgetItemGroup.budgroupid').value;
		if(nodeid==""){
			var pnodeid = njson["BudgetItemGroup.budupgroupid"];
			var pnode = div_center_weast_tree.getNodeById(pnodeid);
		    //var node = new Ext.tree.AsyncTreeNode({id:customField.budgroupid,text:njson["BudgetItemGroup.budgroupname"],entityAttributes:njson});
		    var node=new Ext.tree.TreeNode({
				id:customField.budgroupid,
				text:customField.budgroupname,
				leaf : true
			});
		    pnode.leaf = false;
			pnode.appendChild(node);
			pnode.expand();
			node.select();
			$('BudgetItemGroup.budgroupid').value = customField.budgroupid;

			treeClick(node);
		}else{
			var node = div_center_weast_tree.getNodeById(nodeid);
			Ext.apply(node.attributes, njson);
			node.setText(njson["BudgetItemGroup.budgroupname"]);
		}
	}

	//保存
	function tree_submit(){
		var param = Form.serialize('mainForm');

		param = param + "" + getBudgetItemGridData();

		param += "&action=_saveOrUpdate";

		new AjaxEngine(context + '/XDSS/budget/maindata/BudgetItemGroup/budgetItemGroupController.spr', 
		{method:"post", parameters: param, onComplete: callBackHandle, callback: saveCallBackHandle});
	}

	//取消
	function tree_cancel(){
		$('BudgetItemGroup.budgroupname').value = '';
		$('BudgetItemGroup.budgroupdesc').value = '';
	}

	//树的单击事件
	function treeClick(node){
		treeNodeId = node.id;

		if (treeNodeId == 'new'){
			return;
		}else{
			deleteNewNode();
		}

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
		        url: context + '/XDSS/budget/maindata/BudgetItemGroup/budgetItemGroupController.spr?action=getBudGroup',
		        params:{'budgroupid': node.id},
		        success: function(response, options){
		            var responseArray = Ext.util.JSON.decode(response.responseText);

		            $('BudgetItemGroup.client').value = responseArray.client.trim();
		    		$('BudgetItemGroup.budgroupid').value = responseArray.budgroupid.trim();
		    		$('BudgetItemGroup.budupgroupid').value = responseArray.budupgroupid.trim();
		    		$('BudgetItemGroup.budgroupname').value = responseArray.budgroupname.trim();
		    		$('BudgetItemGroup.budgroupdesc').value = responseArray.budgroupdesc.trim();
		    		$('BudgetItemGroup.createtime').value = responseArray.createtime.trim();
		    		$('BudgetItemGroup.lastmodifytime').value = responseArray.lastmodifytime.trim();
		    		$('BudgetItemGroup.creator').value = responseArray.creator.trim();
		    		$('BudgetItemGroup.lastmodifyer').value = responseArray.lastmodifyer.trim();

		    		$('BudgetItemGroup.creator_text').value = responseArray.creator_text.trim();
		    		$('BudgetItemGroup.lastmodifyer_text').value = responseArray.lastmodifyer_text.trim();
		        },
		        failure:function(response, options){
		        	_getMainFrame().showInfo('获取该预算项分组资源失败！');
		        }
	    	});
		}

		//刷新数据集
		reload_BudgetItem_grid("defaultCondition=YBUDITEM.BUDGROUPID='"+node.id+"'");
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
				border:false,
	            items:[{
	            	region:'north',
		            layout:'fit',
		            contentEl:'div_center_north'
	            },{
	            	region:'south',
		            layout:'fit',
		            contentEl:'div_center_south'
	            }]
			}]
	   	});
	});

</script>

