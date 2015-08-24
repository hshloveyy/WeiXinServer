<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月05日 13点59分05秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象预算组织(BudgetOrganization)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript"  src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetOrganization/budgetOrganizationAdd.js"></script>
<script language="javascript" type="text/javascript"  src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetOrganization/budgetOrganizationAddGen.js"></script>
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
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:3;formRowNo:1 ;rowNo: 01;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.budorgname}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="BudgetOrganization.budorgname" name="BudgetOrganization.budorgname" value="${budgetOrganization.budorgname}" <fisc:authentication sourceName="BudgetOrganization.budorgname" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:4;formRowNo:1 ;rowNo: 01;columnNo: 2;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.budorgtype}：</td>
		<td   width="40%" >
			<div id="div_budorgtype_dict"></div>
			<fisc:dictionary boName="BudgetOrganization" boProperty="budorgtype" dictionaryName="YDBUDORGTYPE" divId="div_budorgtype_dict" isNeedAuth="false"  value="${budgetOrganization.budorgtype}"></fisc:dictionary>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:5;formRowNo:2 ;rowNo: 02;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.budcontype}：</td>
		<td  width="30%" >
			<div id="div_budcontype_dict"></div>
			<fisc:dictionary boName="BudgetOrganization" boProperty="budcontype" dictionaryName="YDBUDCONTYPE" divId="div_budcontype_dict" isNeedAuth="false"  value="${budgetOrganization.budcontype}"></fisc:dictionary>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:6;formRowNo:2 ;rowNo: 02;columnNo: 2;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.deptid}：</td>
		<td   width="40%" >
			<div id="div_deptid_sh"></div>
			<fisc:searchHelp divId="div_deptid_sh" boName="BudgetOrganization" boProperty="deptid" value="${budgetOrganization.deptid}" callBackHandler="boSerachHelpCallBack"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:7;formRowNo:3 ;rowNo: 03;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.companycode}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="BudgetOrganization.companycode" name="BudgetOrganization.companycode" value="${budgetOrganization.companycode}" <fisc:authentication sourceName="BudgetOrganization.companycode" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:8;formRowNo:3 ;rowNo: 03;columnNo: 2;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.costcenter}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="BudgetOrganization.costcenter" name="BudgetOrganization.costcenter" value="${budgetOrganization.costcenter}" <fisc:authentication sourceName="BudgetOrganization.costcenter" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:9;formRowNo:4 ;rowNo: 04;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.status}：</td>
		<td  width="30%" >
			<div id="div_status_dict"></div>
			<fisc:dictionary boName="BudgetOrganization" boProperty="status" dictionaryName="YDBUDSTATUS" divId="div_status_dict" isNeedAuth="false"  value="${budgetOrganization.status}"></fisc:dictionary>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:11;formRowNo:5 ;rowNo: 05;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.creator}：</td>
		<td  width="30%" >
			<fisc:user boProperty="creator" boName="BudgetOrganization" userId="${budgetOrganization.creator}"></fisc:user>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:12;formRowNo:5 ;rowNo: 05;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.createtime}：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="BudgetOrganization.createtime" name="BudgetOrganization.createtime" value="${budgetOrganization.createtime}"  readonly="readonly" <fisc:authentication sourceName="BudgetOrganization.createtime" taskId=""/>>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:13;formRowNo:6 ;rowNo: 06;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.lastmodifyer}：</td>
		<td  width="30%" >
			<fisc:user boProperty="lastmodifyer" boName="BudgetOrganization" userId="${budgetOrganization.lastmodifyer}"></fisc:user>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 15(调试用)序号:14;formRowNo:6 ;rowNo: 06;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.lastmodifytime}：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="BudgetOrganization.lastmodifytime" name="BudgetOrganization.lastmodifytime" value="${budgetOrganization.lastmodifytime}"  readonly="readonly" <fisc:authentication sourceName="BudgetOrganization.lastmodifytime" taskId=""/>>
		</td>
	</tr>

	<input type="hidden" id="BudgetOrganization.client" name="BudgetOrganization.client" value="${budgetOrganization.client}">
	<input type="hidden" id="BudgetOrganization.budorgid" name="BudgetOrganization.budorgid" value="${budgetOrganization.budorgid}">
	<input type="hidden" id="BudgetOrganization.buduporgid" name="BudgetOrganization.buduporgid" value="${budgetOrganization.buduporgid}">
</table>
</form>
</div>
</div>

<div id="div_center_south"></div>
                     
</body>
</html>

<fisc:tree tableName="YBUDORG"
	entityName="com.infolion.XDSS.budget.maindata.BudgetOrganization.domain.BudgetOrganization" 
	height="500"
	rootValue="-1" idColumnName="budorgid"
	parentColumnName="buduporgid" titleColumnName="budorgname"
	treeTitle="预算组织目录" divId="div_center_weast" needCheckChilden="true"
	needAutoLoad="true" rootVisible="true"></fisc:tree>

<fisc:grid divId="div_center_south" 
           boName="BudgetOrgTemp" 
           needCheckBox="true" 
           editable="true" 
           defaultCondition=" YBUDORGTEM.BUDORGID=''" 
           needAutoLoad="true" height="285" 
           nameSapceSupport="true"></fisc:grid>	

<script type="text/javascript" defer="defer">
	//应用的上下文路径，作为全局变量供js使用
	var context = '<%= request.getContextPath() %>';
	//如果是复制创建，则清空主对象ID，使之执行保存动作
	var isCreateCopy = '${isCreateCopy}';
	
	//页面文本
	var Txt={
		//预算组织
		budgetOrganization:'${vt.boText}',
		          
		//预算组织模版
		budgetOrgTemp:'${budgetOrgTempVt.boText}',
		//boText创建
		budgetOrgTemp_Create:'${budgetOrgTempVt.boTextCreate}',
		//boText复制创建
		budgetOrgTemp_CopyCreate:'${budgetOrgTempVt.boTextCopyCreate}',
		// 进行【预算组织模版复制创建】操作时，只允许选择一条记录！
		budgetOrgTemp_CopyCreate_AllowOnlyOneItemForOperation:'${budgetOrgTempVt.copyCreate_AllowOnlyOneItemForOperation}',
		// 请选择需要进行【预算组织模版复制创建】操作的记录！
		budgetOrgTemp_CopyCreate_SelectToOperation:'${budgetOrgTempVt.copyCreate_SelectToOperate}',
		//boText复制创建
		budgetOrganization_CopyCreate:'${vt.boTextCopyCreate}',
		//boText创建
		budgetOrganization_Create:'${vt.boTextCreate}',
		// 复制创建
		mCopyCreate:'${vt.mCopyCreate}',
		// 创建
		mCreate:'${vt.mCreate}',
		// 编辑
		mEdit:'${vt.mEdit}',
		// 查看
		mView:'${vt.mView}',
		// '您选择了【采购订单删除】操作，是否确定继续该操作？'
		budgetOrganization_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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

	//部门搜索帮助回调函数
	function boSerachHelpCallBack(sjson){
		$('BudgetOrganization.companycode').value = '800';
		$('BudgetOrganization.costcenter').value = sjson.COSTCENTER;
	}

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

	//清空form
	function resetMainForm(){
		$('BudgetOrganization.client').value = '';
		$('BudgetOrganization.budorgid').value = '';
		$('BudgetOrganization.buduporgid').value = '';
		$('BudgetOrganization.budorgname').value = '';
		div_deptid_sh_sh.setValue("");
		$('BudgetOrganization.companycode').value = '';
		$('BudgetOrganization.costcenter').value = '';
		dict_div_budorgtype_dict.setValue("");
		dict_div_budcontype_dict.setValue("");
		dict_div_status_dict.setValue("");

		$('BudgetOrganization.creator').value = '';
		$('BudgetOrganization.lastmodifyer').value = '';
		$('BudgetOrganization.createtime').value = '';
		$('BudgetOrganization.lastmodifytime').value = '';
		$('BudgetOrganization.creator_text').value = '';
		$('BudgetOrganization.lastmodifyer_text').value = '';
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

			$('BudgetOrganization.budorgid').value = "";
			$('BudgetOrganization.buduporgid').value = parentTreeNode.id;

			createNewNode(parentTreeNode.id);

			//刷新数据集
			reload_BudgetOrgTemp_grid("defaultCondition=YBUDORGTEM.BUDORGID='1'");
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
		
			$('BudgetOrganization.budorgid').value = "";
			$('BudgetOrganization.buduporgid').value = treeNode.id;

			createNewNode(treeNode.id);

			//刷新数据集
			reload_BudgetOrgTemp_grid("defaultCondition=YBUDORGTEM.BUDORGID='1'");
		}
	}

	//删除回
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

		reload_BudgetOrgTemp_grid("defaultCondition=YBUDORGTEM.BUDORGID='1'");
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
							var param = "&budorgid=" + treeNodeId;
							param += "&action=_delete";
							new AjaxEngine(context + '/XDSS/budget/maindata/BudgetOrganization/budgetOrganizationController.spr', 
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
		var nodeid = $('BudgetOrganization.budorgid').value;
		if(nodeid==""){
			var pnodeid = njson["BudgetOrganization.buduporgid"];
			var pnode = div_center_weast_tree.getNodeById(pnodeid);
		    //var node = new Ext.tree.AsyncTreeNode({id:customField.budorgid,text:njson["BudgetOrganization.budorgname"],entityAttributes:njson});
		    var node=new Ext.tree.TreeNode({
				id:customField.budorgid,
				text:customField.budorgname,
				leaf : true
			});
		    pnode.leaf = false;
			pnode.appendChild(node);
			pnode.expand();
			node.select();
			$('BudgetOrganization.budorgid').value = customField.budsortid;

			treeClick(node);
		}else{
			var node = div_center_weast_tree.getNodeById(nodeid);
			Ext.apply(node.attributes, njson);
			node.setText(njson["BudgetOrganization.budorgname"]);
		}
	}

	//保存
	function tree_submit(){
		var param = Form.serialize('mainForm');

		param = param + "" + getBudgetOrgTempGridData();
		
		param += "&action=_saveOrUpdate";

		new AjaxEngine(context + '/XDSS/budget/maindata/BudgetOrganization/budgetOrganizationController.spr', 
		{method:"post", parameters: param, onComplete: callBackHandle, callback: saveCallBackHandle});
	}

	//取消
	function tree_cancel(){
		$('BudgetOrganization.budorgname').value = '';
		div_deptid_sh_sh.setValue("");
		$('BudgetOrganization.companycode').value = '';
		$('BudgetOrganization.costcenter').value = '';
		dict_div_budorgtype_dict.setValue("");
		dict_div_budcontype_dict.setValue("");
		dict_div_status_dict.setValue("");
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
		        url: context + '/XDSS/budget/maindata/BudgetOrganization/budgetOrganizationController.spr?action=getBudOrg',
		        params:{'budorgid': node.id},
		        success: function(response, options){
		            var responseArray = Ext.util.JSON.decode(response.responseText);
		            $('BudgetOrganization.client').value = responseArray.client.trim();
		    		$('BudgetOrganization.budorgid').value = responseArray.budorgid.trim();
		    		$('BudgetOrganization.buduporgid').value = responseArray.buduporgid.trim();
		    		$('BudgetOrganization.budorgname').value = responseArray.budorgname.trim();
		    		div_deptid_sh_sh.setValue(responseArray.deptid.trim(),function(){
		    			$('BudgetOrganization.companycode').value = responseArray.companycode.trim();
			    		$('BudgetOrganization.costcenter').value = responseArray.costcenter.trim();
			    	});
		    		dict_div_budorgtype_dict.setValue(responseArray.budorgtype.trim());
		    		dict_div_budcontype_dict.setValue(responseArray.budcontype.trim());
		    		dict_div_status_dict.setValue(responseArray.status.trim());

		    		$('BudgetOrganization.creator').value = responseArray.creator.trim();
		    		$('BudgetOrganization.lastmodifyer').value = responseArray.lastmodifyer.trim();
		    		$('BudgetOrganization.createtime').value = responseArray.createtime.trim();
		    		$('BudgetOrganization.lastmodifytime').value = responseArray.lastmodifytime.trim();
		    		$('BudgetOrganization.creator_text').value = responseArray.creator_text.trim();
		    		$('BudgetOrganization.lastmodifyer_text').value = responseArray.lastmodifyer_text.trim();		    		

		        },
		        failure:function(response, options){
		        	_getMainFrame().showInfo('获取该预算分类资源失败！');
		        }
	    	});
		}

		//刷新数据集
		reload_BudgetOrgTemp_grid("defaultCondition=YBUDORGTEM.BUDORGID='"+node.id+"'");
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
