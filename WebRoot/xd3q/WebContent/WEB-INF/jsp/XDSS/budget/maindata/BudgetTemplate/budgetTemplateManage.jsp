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
<script language="javascript" type="text/javascript"  src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetTemplate/budgetTemplateManage.js"></script>
<script language="javascript" type="text/javascript"  src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetTemplate/budgetTemplateManageGen.js"></script>
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
<!-- column.property.numberObjectId column.visibility=="X" 11(调试用)序号:3;formRowNo:1 ;rowNo: 01;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>预算分类名称：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="BudgetClass.budclassname" name="BudgetClass.budclassname" value="${budgetClass.budclassname}" <fisc:authentication sourceName="BudgetClass.budclassname" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 11(调试用)序号:4;formRowNo:1 ;rowNo: 01;columnNo: 2;1X -->
		<td align="right"  width="15%" >预算分类描述：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="BudgetClass.budclassdesc" name="BudgetClass.budclassdesc" value="${budgetClass.budclassdesc}" <fisc:authentication sourceName="BudgetClass.budclassdesc" taskId=""/> >
		</td>
	</tr>
	
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:8;formRowNo:3 ;rowNo: 03;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>资源类型：</td>
		<td  width="30%" >
			<div id="div_sourcetype_dict"></div>
			<fisc:dictionary boName="BudgetClass" boProperty="sourcetype" dictionaryName="YDBUDGETSOURCE" divId="div_sourcetype_dict" isNeedAuth="false"  value="${budgetClass.sourcetype}"></fisc:dictionary>
		</td>
		
		<td align="right"  width="15%" >版本号：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="BudgetClass.version" name="BudgetClass.version" value="${budgetClass.version}" readonly="readonly" <fisc:authentication sourceName="BudgetClass.version" taskId=""/> >
		</td>
	</tr>
	
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 11(调试用)序号:6;formRowNo:2 ;rowNo: 02;columnNo: 2;1X -->
		<td align="right"  width="15%" >业务对象：</td>
		<td  width="30%" >
			<div id="div_boid_sh"></div>
			<fisc:searchHelp divId="div_boid_sh" boName="BudgetClass" boProperty="boid" value="${budgetClass.boid}"></fisc:searchHelp>
		</td>
		
		<td align="right"  width="15%" >汇总业务对象：</td>
		<td  width="30%" >
			<div id="div_sumboid_sh"></div>
			<fisc:searchHelp divId="div_sumboid_sh" boName="BudgetClass" boProperty="sumboid" value="${budgetClass.sumboid}"></fisc:searchHelp>
		</td>
	</tr>
	
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 11(调试用)序号:7;formRowNo:3 ;rowNo: 03;columnNo: 1;1X -->
		<td align="right"  width="15%" >创建人：</td>
		<td  width="30%" >
			<fisc:user boProperty="creator" boName="BudgetClass" userId="${budgetClass.creator}"></fisc:user>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 11(调试用)序号:8;formRowNo:3 ;rowNo: 03;columnNo: 2;1X -->
		<td align="right"  width="15%" >创建日期：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="BudgetClass.createtime" name="BudgetClass.createtime" value="${budgetClass.createtime}"  readonly="readonly" <fisc:authentication sourceName="BudgetClass.createtime" taskId=""/>>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 11(调试用)序号:9;formRowNo:4 ;rowNo: 04;columnNo: 1;1X -->
		<td align="right"  width="15%" >最后修改者：</td>
		<td  width="30%" >
			<fisc:user boProperty="lastmodifyer" boName="BudgetClass" userId="${budgetClass.lastmodifyer}"></fisc:user>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 11(调试用)序号:10;formRowNo:4 ;rowNo: 04;columnNo: 2;1X -->
		<td align="right"  width="15%" >最后修改日期：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="BudgetClass.lastmodifytime" name="BudgetClass.lastmodifytime" value="${budgetClass.lastmodifytime}"  readonly="readonly" <fisc:authentication sourceName="BudgetClass.lastmodifytime" taskId=""/>>
		</td>
	</tr>

	<input type="hidden" id="BudgetClass.client" name="BudgetClass.client" value="${budgetClass.client}">
	<input type="hidden" id="BudgetClass.budclassid" name="BudgetClass.budclassid" value="${budgetClass.budclassid}">
	<input type="hidden" id="BudgetClass.budupclassid" name="BudgetClass.budupclassid" value="${budgetClass.budupclassid}">
	<input type="hidden" id="BudgetClass.active" name="BudgetClass.active" value="${budgetClass.active}">
</table>
</form>
</div>
</div>

<div id="div_center_south"></div>
</body>
</html>

<!--<fisc:tree tableName="YBUDCLASS"
	entityName="com.infolion.XDSS.budget.maindata.BudgetClass.domain.BudgetClass" height="500"
	rootValue="-1" idColumnName="budclassid"
	parentColumnName="budupclassid" titleColumnName="budclassname"
	treeTitle="预算分类目录" divId="div_center_weast" needCheckChilden="true"
	needAutoLoad="true" rootVisible="true"></fisc:tree>-->
	
<fisc:tree tableName=" "
	sourceClass="butgetClassTree" height="500"
	rootValue="-1" idColumnName="budclassid" style=""
	parentColumnName="budupclassid" titleColumnName="budclassname"
	treeTitle="预算分类目录" divId="div_center_weast" 
	rootVisible="true"></fisc:tree>
	


	
<!--<fisc:grid divId="div_center_south" 
boName="BudgetTemplate"  
editable="false" 
defaultCondition=" YBUDTEM.BUDCLASSID=''"
needCheckBox="true" 
needToolbar="true" 
needAutoLoad="true"></fisc:grid>-->

<script type="text/javascript" defer="defer">
	var tab ;
	//页面文本
	var Txt={
		// 采购订单
		budgetTemplate:'${vt.boText}',
		budgetTemplate_Create:'${vt.boTextCreate}',
		// 复制创建
		budgetTemplate_CopyCreate:'${vt.boTextCopyCreate}',
		// 复制创建
		mCopyCreate:'${vt.mCopyCreate}',
		// 请选择需要进行【预算模版复制创建】操作的记录！
		budgetTemplate_CopyCreate_SelectToOperation:'${vt.copyCreate_SelectToOperate}',
		// 进行【预算模版复制创建】操作时，只允许选择一条记录！
		budgetTemplate_CopyCreate_AllowOnlyOneItemForOperation:'${vt.copyCreate_AllowOnlyOneItemForOperation}',
		// 您选择了【预算模版批量删除】操作，是否确定继续该操作？
		budgetTemplate_Deletes_ConfirmOperation:'${vt.deletes_ConfirmOperation}',
		// 请选择需要进行【预算模版批量删除】操作的记录！
		budgetTemplate_Deletes_SelectToOperation:'${vt.deletes_SelectToOperate}',
		// 您选择了【预算模版删除】操作，是否确定继续该操作？
		budgetTemplate_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
		// 提示
		cue:'${vt.sCue}',
		// 确定
		ok:'${vt.sOk}',
		// 取消
		cancel:'${vt.sCancel}',
		// 创建
		mCreate:'${vt.mCreate}'
	};

	//存放当前选 中树的节点ID
	var treeNodeId = '';
	//全局路径
	var context = '<%= request.getContextPath() %>';
	//判断是否叶结点
	var isLeaf = true;

	/**
	 * 操作成功后的回调动作
	 */
	/*function customCallBackHandle()
	{
		reload_BudgetTemplate_grid("defaultCondition=YBUDTEM.BUDCLASSID='" + $('BudgetClass.budclassid').value + "'");
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

	/**
	 * grid 上的 创建按钮调用方法 
	 * 新增预算模版
	 */
	/*function _create()
	{
		if(_precreate()){
			if (treeNodeId == '' || treeNodeId == '-1'){
				_getMainFrame().showInfo('请选择节点后再进行此功能的操作！');
			}else{
				if (dict_div_sourcetype_dict.getValue()!='2'){
					_getMainFrame().showInfo('资源类型不是预算模版不能进行此功能操作！');
				}else{
					if ($('BudgetClass.budclassid').value == ''){
						_getMainFrame().showInfo('请选择保存后再进行此功能的操作！');
					}else{
						if ($('BudgetClass.version').value != '0'){
							_getMainFrame().showInfo('只能在临时版上进行此功能的操作！');
						}else{
			   				var para = "&budclassid=" + $('BudgetClass.budclassid').value;
							//增加页签
							_getMainFrame().maintabs.addPanel(Txt.budgetTemplate_Create,BudgetTemplate_grid,contextPath + '/XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_create'+ para);
						}
					}
				}
			}
	    }
	    _postcreate();
	}*/

	//清空form组件的信息
	function resetMainForm()
	{
		$('BudgetClass.client').value = '';
		$('BudgetClass.budclassid').value = '';
		$('BudgetClass.budupclassid').value = '';
		$('BudgetClass.budclassname').value = '';
		$('BudgetClass.budclassdesc').value = '';
		div_boid_sh_sh.setValue("");
		div_sumboid_sh_sh.setValue("");
		$('BudgetClass.version').value = '';
		$('BudgetClass.active').value = '';
		dict_div_sourcetype_dict.setValue("");

		$('BudgetClass.creator').value = '';
		$('BudgetClass.lastmodifyer').value = '';
		$('BudgetClass.createtime').value = '';
		$('BudgetClass.lastmodifytime').value = '';
		$('BudgetClass.creator_text').value = '';
		$('BudgetClass.lastmodifyer_text').value = '';
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

			$('BudgetClass.budclassid').value = "";
			$('BudgetClass.budupclassid').value = parentTreeNode.id;

			createNewNode(parentTreeNode.id);

			//刷新数据集
			tab.setSrc('<%=request.getContextPath()%>/XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_versionGrid&budclassid=111');
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
		
			$('BudgetClass.budclassid').value = "";
			$('BudgetClass.budupclassid').value = treeNode.id;

			createNewNode(treeNode.id);

			//刷新数据集
			tab.setSrc('<%=request.getContextPath()%>/XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_versionGrid&budclassid=111');
		}
	}

	//删除回
	function deleteCallBackHandle(transport){
		var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
		treeNode.remove();
		treeNodeId = '';
		resetMainForm();

		Ext.getCmp('_createNode').disable();
        Ext.getCmp('_createSubNode').disable();
		Ext.getCmp('_deleteNode').disable();
        Ext.getCmp('submit').disable();
        Ext.getCmp('reset').disable();

		//刷新数据集
        tab.setSrc('<%=request.getContextPath()%>/XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_versionGrid&budclassid=111');
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
							_getMainFrame().Ext.MessageBox.show({
								title:'系统提示',
								msg: '选择的节点为非叶节点，是否继续执行此操作？',
								buttons: {yes:'确定', no:'取消'},
								icon: Ext.MessageBox.QUESTION,
								fn:function(buttonid){
									if (buttonid == 'yes'){
										var param = "&budclassid=" + treeNodeId;
										param += "&action=_delete";
										new AjaxEngine(context + '/XDSS/budget/maindata/BudgetClass/budgetClassController.spr', 
											   {method:"post", parameters: param, onComplete: callBackHandle, callback: deleteCallBackHandle});
									}
								}
							});
						}else{
							var param = "&budclassid=" + treeNodeId;
							param += "&action=_delete";
							new AjaxEngine(context + '/XDSS/budget/maindata/BudgetClass/budgetClassController.spr', 
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
		var nodeid = $('BudgetClass.budclassid').value;

		if(nodeid==""){
			if (njson["BudgetClass.sourcetype"] == "2"){
				var ppnode = div_center_weast_tree.getNodeById(treeNodeId);

				var mnode=new Ext.tree.TreeNode({
					id:customField.budupclassid,
					text:customField.budclassname,
					leaf : true
				});

				ppnode.leaf = false;
				ppnode.appendChild(mnode);
				ppnode.expand();
				mnode.select();

			    var node=new Ext.tree.TreeNode({
					id:customField.budclassid,
					text:'临时版',
					leaf : true
				});
	
			    mnode.leaf = false;
			    mnode.appendChild(node);
			    mnode.expand();
			    node.select();
				$('BudgetClass.budclassid').value = customField.budclassid;
			}else{
				var pnodeid = njson["BudgetClass.budupclassid"];
				var pnode = div_center_weast_tree.getNodeById(pnodeid);
	
			    var node=new Ext.tree.TreeNode({
					id:customField.budclassid,
					text:customField.budclassname,
					leaf : true
				});

			    pnode.leaf = false;
			    pnode.appendChild(node);
			    pnode.expand();
			    node.select();
				$('BudgetClass.budclassid').value = customField.budclassid;
			}
			treeClick(node);
		}else{
			var node = div_center_weast_tree.getNodeById(nodeid);
			Ext.apply(node.attributes, njson);
			node.setText(njson["BudgetClass.budclassname"]);
		}
	}

	//保存
	function tree_submit(){
		var param = Form.serialize('mainForm');
		param += "&action=_saveOrUpdate";

		new AjaxEngine(context + '/XDSS/budget/maindata/BudgetClass/budgetClassController.spr', 
		{method:"post", parameters: param, onComplete: callBackHandle, callback: saveCallBackHandle});
	}

	//取消
	function tree_cancel(){
		$('BudgetClass.budclassname').value = '';
		$('BudgetClass.budclassdesc').value = '';
		div_boid_sh_sh.setValue("");
		div_sumboid_sh_sh.setValue("");
		dict_div_sourcetype_dict.setValue("");
	}

	//用于改变下面的grid页面
	function changeGrid(typeid){
		var tab = Ext.getCmp("tab");
		if (typeid == 'version')
			tab.setSrc('<%=request.getContextPath()%>/XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_versionGrid&budclassid='+treeNodeId);
		if (typeid =='temp')
			tab.setSrc('<%=request.getContextPath()%>/XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_tempGrid&budclassid='+treeNodeId);
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

	        Ext.getCmp('_promulgate').disable();
	        Ext.getCmp('_active').disable();
		}else{
			Ext.getCmp('_createNode').enable();
	        Ext.getCmp('_createSubNode').enable();
			Ext.getCmp('_deleteNode').enable();
	        Ext.getCmp('submit').enable();
	        Ext.getCmp('reset').enable();

	        isLeaf = node.leaf;

	        Ext.Ajax.request({
		        url: context + '/XDSS/budget/maindata/BudgetClass/budgetClassController.spr?action=getBudClass',
		        params:{'budclassid': node.id},
		        success: function(response, options){
		            var responseArray = Ext.util.JSON.decode(response.responseText);

		            $('BudgetClass.client').value = responseArray.client.trim();
		    		$('BudgetClass.budclassid').value = responseArray.budclassid.trim();
		    		$('BudgetClass.budupclassid').value = responseArray.budupclassid.trim();
		    		$('BudgetClass.budclassname').value = responseArray.budclassname.trim();
		    		$('BudgetClass.budclassdesc').value = responseArray.budclassdesc.trim();
		    		$('BudgetClass.createtime').value = responseArray.createtime.trim();
		    		div_boid_sh_sh.setValue(responseArray.boid.trim(),function(){});
		    		div_sumboid_sh_sh.setValue(responseArray.sumboid.trim(),function(){});
		    		$('BudgetClass.version').value = responseArray.version;
		    		$('BudgetClass.active').value = responseArray.active.trim();
		    		dict_div_sourcetype_dict.setValue(responseArray.sourcetype.trim());
		    		$('BudgetClass.lastmodifytime').value = responseArray.lastmodifytime.trim();
		    		$('BudgetClass.creator').value = responseArray.creator.trim();
		    		$('BudgetClass.lastmodifyer').value = responseArray.lastmodifyer.trim();

		    		$('BudgetClass.creator_text').value = responseArray.creator_text.trim();
		    		$('BudgetClass.lastmodifyer_text').value = responseArray.lastmodifyer_text.trim();

		    		//刷新数据集
					//reload_BudgetTemplate_grid("defaultCondition=YBUDTEM.BUDCLASSID='"+node.id+"'");
					
					//做预算对像和文件夹的判断
					if (dict_div_sourcetype_dict.getValue() =='2' ){
						//做预算对像文件夹
						if ($('BudgetClass.version').value == '0'){
							changeGrid('version');
							Ext.getCmp('_promulgate').disable();
							Ext.getCmp('_active').disable();

							Ext.getCmp('_createNode').disable();
					        Ext.getCmp('_createSubNode').disable();
							Ext.getCmp('_deleteNode').enable();
							
						}else{
							if ($('BudgetClass.version').value == '-1'){
								changeGrid('temp');
								Ext.getCmp('_promulgate').enable();
								Ext.getCmp('_active').disable();

								Ext.getCmp('_createNode').disable();
						        Ext.getCmp('_createSubNode').disable();
								Ext.getCmp('_deleteNode').disable();
								
							}else{
								if ($('BudgetClass.active').value == 'Y')
									Ext.getCmp('_active').disable();
								else
									Ext.getCmp('_active').enable();
								
								changeGrid('version');
								Ext.getCmp('_promulgate').disable();

								Ext.getCmp('_createNode').disable();
						        Ext.getCmp('_createSubNode').disable();
								Ext.getCmp('_deleteNode').disable();
							}
						}
						
					}else{
						changeGrid('version');
						Ext.getCmp('_createNode').enable();
				        Ext.getCmp('_createSubNode').enable();
						Ext.getCmp('_deleteNode').enable();
				        Ext.getCmp('submit').enable();
				        Ext.getCmp('reset').enable();
					}
		        },
		        failure:function(response, options){
		        	_getMainFrame().showInfo('获取该预算分类资源失败！');
		        }
	    	});
		}
	}

	//发布回调函数
	function promulgateCallBackHandle(transport){
		var responseUtil = new AjaxResponseUtils(transport.responseText);
		var customField = responseUtil.getCustomField("coustom");
		var njson = tranFormToJSON(mainForm);
		var nodeid = $('BudgetClass.budclassid').value;
		if(nodeid==""){
			var pnodeid = njson["BudgetClass.budupclassid"];
			var pnode = div_center_weast_tree.getNodeById(pnodeid);

		    var node=new Ext.tree.TreeNode({
				id:customField.budclassid,
				text:'第' + customField.version + '版',
				leaf : true
			});

		    pnode.leaf = false;
		    pnode.appendChild(node);
		    pnode.expand();
		    node.select();
			$('BudgetClass.budclassid').value = customField.budclassid;

			treeClick(node);
		}else{
			var node = div_center_weast_tree.getNodeById(nodeid);
			Ext.apply(node.attributes, njson);
			node.setText(njson["BudgetClass.budclassname"]);
		}
	}

	//发布
	function promulgate(){
		if (treeNodeId == ''){
			_getMainFrame().showInfo('请选择节点后再进行此功能的操作！');
		}else{
			var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
			var parentTreeNode = treeNode.parentNode;

			$('BudgetClass.budclassid').value = "";
			$('BudgetClass.budupclassid').value = parentTreeNode.id;
			
			var param = '?action=_promulgate';
			param += "&budclassid="+treeNodeId;
			param += "&version="+parentTreeNode.childNodes.length ;

			new AjaxEngine(context + '/XDSS/budget/maindata/BudgetClass/budgetClassController.spr', 
			{method:"post", parameters: param, onComplete: callBackHandle, callback: promulgateCallBackHandle});
		}
	}

	//激活回调
	function activeCallBackHandle(transport){
		var node = div_center_weast_tree.getNodeById(treeNodeId);
		var pnode = node.parentNode;
		pnode.reload();

		treeClick(pnode);
	}

	//激活
	function active(){
		if (treeNodeId == ''){
			_getMainFrame().showInfo('请选择节点后再进行此功能的操作！');
		}else{
			var param = '?action=_active';
			param += "&budclassid="+treeNodeId;

			new AjaxEngine(context + '/XDSS/budget/maindata/BudgetClass/budgetClassController.spr', 
			{method:"post", parameters: param, onComplete: callBackHandle, callback: activeCallBackHandle});
		}
	}

Ext.onReady(function(){
	tab = new Ext.ux.ManagedIframePanel({
	    id:'tab',
	    title:'预算模版信息',
	    name:'tab',
		defaultSrc:"<%=request.getContextPath()%>/XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_versionGrid&budclassid=11111",
        height:310
	});
	
	//选择资源类型的变换动作
	dict_div_sourcetype_dict.on("change",function(){
		 if(dict_div_sourcetype_dict.getValue()=='2'){
			 div_boid_sh_sh.setDisabled(false);
			 div_sumboid_sh_sh.setDisabled(false);
		 }else{
			 div_boid_sh_sh.setDisabled(true);
			 div_sumboid_sh_sh.setDisabled(true);
		 }
	});
	
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
				{id:'_promulgate',text:'发布',handler:promulgate,disabled:true},'-',
				{id:'_active',text:'激活',handler:active,disabled:true},'-',
				{id:'submit',text:'保存',handler:tree_submit,disabled:true,iconCls:'icon-table-save'},'-',
				{id:'reset',text:'取消',handler:tree_cancel,disabled:true,iconCls:'icon-undo'},'-'
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
            	region:'center',
	            layout:'fit',
	            contentEl:'div_center_north'
            },{
            	region:'south',
            	layout:'fit',
	    		autoScroll: false,
	    		autoWidth: false ,
	    		autoHeight: false ,
	            border:false,
	            items:[tab]
            }]
		}]
   	});
});
</script>