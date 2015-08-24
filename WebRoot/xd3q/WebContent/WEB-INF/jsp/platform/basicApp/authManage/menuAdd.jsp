<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加菜单</title>
</head>
<body>
<div id="div_north">
<div id="div_toolbar"></div>
<div id="div_menuForm" class="search">
<form id="menuForm" name="menuForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
	<td width="15%" align="right">菜单名称:</td>
	<td width="30%">
	<input type="hidden" id="Menu.menuId" name="Menu.menuId" value="${menu.menuId}">
	<input type="hidden" id="menuTextId" name="menuTextId" value="${menuText.menuTextId}">
  	<input class="inputText" type="text" id="Menu.menuName" name="Menu.menuName" value="${menu.menuName}" onblur="nameBlur();">
	</td>
	<td width="15%" align="right">功能模块:</td>
	<td width="40%" >
		<div id="div_appModel"></div>
	</td>
</tr>
<tr>
	<td width="15%" align="right">是否标准菜单:</td>
	<td width="30%" >
	<div id="div_isStandard"></div>
	</td>
	<td width="15%" align="right">排列顺序:</td>
	<td width="40%" >
  	<input class="inputText" type="text" id="Menu.orderNo" name="Menu.orderNo" value="${menu.orderNo}" >
	</td>

</tr>
<tr>
	<td align="right" width="15%" >菜单描述:</td>
	<td colspan="3" width="85%" >
	<input type="text" id="Menu.menuDesc" name="Menu.menuDesc" value="${menu.menuDesc}" size="79">
	</td>
</tr>
<tr>
	<td align="right" width="15%" >创建人:</td>
	<td width="30%" >
	<fisc:user boProperty="creator" boName="Menu" userId="${menu.creator}"></fisc:user>
	</td>
	<td align="right" width="15%" >创建时间:</td>
	<td width="40%" >
	<input class="inputText" type="text" id="Menu.createTime" name="Menu.createTime" value="${menu.createTime}" readOnly="true">
	</td>
</tr>
<tr>
	<td align="right" width="15%" >最后修改人:</td>
	<td width="30%" >
	<fisc:user boProperty="lastModifyor" boName="Menu" userId="${menu.lastModifyor}"></fisc:user>
	</td>
	<td align="right" width="15%" >最后修改时间:</td>
	<td width="40%">
	<input class="inputText" type="text" id="Menu.lastModifyTime" name="Menu.lastModifyTime" value="${menu.lastModifyTime}" readOnly="true">
	</td>
</tr>
</table>
</form>
</div>
</div>

<div id="div_center">
<div id="div_centerToolbar"></div>
<div id="div_menuTreeForm" class="search">
<form id="menuTreeForm" name="menuTreeForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
	<td align="right">节点类型:</td>
	<td>
	<div id="div_nodeType"></div>
	</td>
	<td align="right">节点描述:</td>
	<td>
	<input type="hidden" id="MenuTree.nodeId" name="MenuTree.nodeId" value="${menutree.nodeId}">
	<input type="hidden" id="MenuTree.parentNodeId" name="MenuTree.parentNodeId" value="">
	<input class="inputText" type="text" id="MenuTree.nodeDesc" name="MenuTree.nodeDesc" value="${menutree.nodeDesc}">
	</td>
</tr>

<tr>
	<td align="right">权限资源:</td>
	<td>
	<div id="div_authReourceId"></div>
	</td>
	<td align="right">事务:</td>
	<td>
	<input class="inputText" type="text" id="MenuTree.tCode" name="MenuTree.tCode" readonly="true">
	</td>
</tr>

<tr>
	<td align="right">URL:</td>
	<td colspan="3">
	<input type="text" id="MenuTree.url" name="MenuTree.url" size="101" <%=("view".equals(request.getAttribute("operType"))?"readonly=\"readonly\"":"")%>>
	</td>
</tr>

<tr>
	<td align="right">参考菜单:</td>
	<td>
	<div id="div_refMenuId"></div>
	</td>
	<td align="right">排列顺序:</td>
	<td>
	<input class="inputText" type="text" id="MenuTree.orderNo" name="MenuTree.orderNo">
	
	</td>
</tr>
<tr>
	<td align="right">菜单图标:</td>
	<td colspan="3">
	<input class="inputText" type="text" id="MenuTree.icon" name="MenuTree.icon">
	</td>
</tr>
<tr>
	<td colspan="4">
		<table width="100%" id="buttonTable" border="0" cellpadding="0" cellspacing="0">
		<tr><td>&nbsp;</td></tr>
		<tr align="center">
			<td width="37%"></td>
			<td align="right" width="5%">
				<div id="div_submit"></div>
			</td>
			<td width="5%"></td>
			<td align="left" width="5%">
				<div id="clean_div"></div>
			</td>
			<td width="5%"></td>
			<td align="left" width="5%">
				<div id="div_reset"></div>
			</td>
			<td width="37%"></td>
		</tr>
		</table>
	</td>
</tr>
</table>
</form>
</div>
</div>

<div id="div_east"></div>
<div id="div_west"></div>

<div id="div_center_weast"></div>
</body>
</html>
<fisc:tree tableName="ymenutree" objectName="MenuTree" entityName="com.infolion.platform.basicApp.authManage.domain.MenuTree"
 rootValue="-1" idColumnName="nodeid" parentColumnName="parentNodeId" titleColumnName="nodeDesc" whereSql=" AND MENUID='${menu.menuId}'"
 treeTitle="${menu.menuName}" divId="div_center_weast" needCheckChilden="false" needAutoLoad="true" orderBySql=" ORDERNO" 
 leafConditions="node.attributes.entityAttributes[\"MenuTree.nodeType\"] == 2"></fisc:tree>

<fisc:searchHelp boName="MenuTree" boProperty="authResourceId" divId="div_authReourceId" editable="${operType != 'view'}"></fisc:searchHelp>
<fisc:searchHelp boName="MenuTree" boProperty="refMenuId" divId="div_refMenuId" editable="${operType != 'view'}"></fisc:searchHelp>
<fisc:dictionary boName="Menu" boProperty="isStandard" dictionaryName="YDYESORNO" divId="div_isStandard" editable="${operType != 'view'}" value="${menu.isStandard}" isNeedAuth="false" ></fisc:dictionary>
<fisc:dictionary boName="MenuTree" boProperty="nodeType" dictionaryName="YDMENUNODETYPE" divId="div_nodeType" editable="${operType != 'view'}" isNeedAuth="false" ></fisc:dictionary>
<fisc:button text="确定" divId="div_submit" onclick="btn_submit" name="submit" hidden="${operType == 'view'}"></fisc:button>
<fisc:button text="清空" divId="div_reset" onclick="btn_reset" name="reset" hidden="${operType == 'view'}"></fisc:button>
<fisc:dictionary boName="Menu" boProperty="appModel" dictionaryName="YDAPPMODEL" divId="div_appModel" editable="${operType != 'view'}" value="${menu.appModel}" isNeedAuth="false" ></fisc:dictionary>

<script type="text/javascript" defer="defer">
	//存放当前选 中树的节点ID
	var treeNodeId = '';
	
	//用于判断是菜单的操作还是菜单树的操作动作
	var callBackType = '';
	
	//存储操作类型的变量
	var strOperType = '${operType}';
	//用于显示上面工具条的变量
	var showFlag;
	
	//用于显示下面工具条的变量
	var showDownFlag;
	
	
	//用于保存节点的下挂信息
	var nodeIdList = '';
	
	//根据操作类型判断工具条上按钮的可操作性
	if (strOperType == 'create'){
		showFlag = false;
		showDownFlag = true;
	}
	if (strOperType == 'view'){
		showFlag = true;
		showDownFlag = true;
	}
	if (strOperType == 'edit'){
		showFlag = false;
		showDownFlag = false;
	}
	if (strOperType == 'copycreate'){
		showFlag = false;
		showDownFlag = false;
	}
	
	//创建同级节点的动作
	function _createNode(){
		if (treeNodeId == ''){
			_getMainFrame().showInfo('请选择节点后再进行此功能的操作！');
		}else{
			resetForm(menuTreeForm);
			var pnode = div_center_weast_tree.getNodeById(treeNodeId).parentNode;
		
			$('MenuTree.parentNodeId').value = pnode.id;
			
			Ext.Ajax.request({
			    url: contextPath + '/basicApp/authManage/menuController.spr?action=getUUID',
			    success: function(xhr) {
			    	var responseUtil = Ext.util.JSON.decode(xhr.responseText);
					var uuId = responseUtil.uuId;

					var njson = tranFormToJSON(menuTreeForm);
					Ext.apply(njson, {'MenuTree.nodeId':uuId,'MenuTree.parentNodeId':pnode.id});
		    
		    		var node = new Ext.tree.AsyncTreeNode(Ext.apply({id:uuId,text:njson["MenuTree.nodeDesc"]}, {entityAttributes:njson}));
					pnode.appendChild(node);
					node.select();
					//node.expand();
					node.fireEvent('click', node, e);
					
					div_center_weast_tree.fireEvent('addnode', node, true);
			    },
			    failure: function(xhr) {
			    	_getMainFrame().showError(xhr.responseText);
			    },
			    scope : this
			});
		}
	}
	
	//创建子节点的动作
	function _createSubNode(){
		if (treeNodeId == ''){
			_getMainFrame().showInfo('请选择节点后再进行此功能的操作！');
		}else{
			resetForm(menuTreeForm);
			var pnode = div_center_weast_tree.getNodeById(treeNodeId);
			$('MenuTree.parentNodeId').value = pnode.id;
			
			Ext.Ajax.request({
			    url: contextPath + '/basicApp/authManage/menuController.spr?action=getUUID',
			    success: function(xhr) {
			    	var responseUtil = Ext.util.JSON.decode(xhr.responseText);
					var uuId = responseUtil.uuId;
					
					var njson = tranFormToJSON(menuTreeForm);
					Ext.apply(njson, {'MenuTree.nodeId':uuId,'MenuTree.parentNodeId':pnode.id});
		    
		    		var node = new Ext.tree.AsyncTreeNode(Ext.apply({id:uuId,text:njson["MenuTree.nodeDesc"]}, {entityAttributes:njson}));
					pnode.appendChild(node);
					node.select();
					//node.expand();
					node.fireEvent('click', node, e);
					
					div_center_weast_tree.fireEvent('addnode', node, true);
			    },
			    failure: function(xhr) {
			    	_getMainFrame().showError(xhr.responseText);
			    },
			    scope : this
			});
		}
	}
	
	//删除节点的动作
	function _deleteNode(){
		if (treeNodeId == ''){
			_getMainFrame().showInfo('请选择节点后再进行此功能的操作！');
		}else{
			var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
			var parentTreeNode = treeNode.parentNode;
	
			_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: treeNode.hasChildNodes() ? '选择的节点为非叶节点，是否继续执行此操作？':'数据删除后将不可恢复，是否继续执行此操作？',
				buttons: Ext.MessageBox.YESNO,
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (buttonid == 'yes'){
						treeNode.remove();
						div_center_weast_tree.fireEvent('removenode', treeNode, true);
						$('reset').click();
					}
				}
			});
		}
	}
	
	//确认按钮的操作动作 
	function btn_submit(){
		var njson = tranFormToJSON(menuTreeForm);
		var nodeid = njson["MenuTree.nodeId"];
		var pnodeid = njson["MenuTree.parentNodeId"];
		if(!pnodeid||(pnodeid==""&&nodeid==""))return;
		
		if(dict_div_nodeType.getValue()==''){
			_getMainFrame().showInfo('节点类型不能为空！');
			return;
		}
		if(njson["MenuTree.nodeDesc"]==''){
			_getMainFrame().showInfo('节点描述不能为空！');
			return;
		}

		var node = div_center_weast_tree.getNodeById(nodeid);
		var eab = node.attributes.entityAttributes;
		for (m in eab){
			eab[m] = njson[m];
		}
		//Ext.apply(node.attributes.entityAttributes, njson);
		
		node.setText(njson["MenuTree.nodeDesc"]);
		div_center_weast_tree.fireEvent('updatenode', node, true);
	}
	
	//清空按钮的操作动作
	function btn_reset(){
		if(treeNodeId!=''){
			var node = div_center_weast_tree.getNodeById(treeNodeId);
			if(node&&node.isSelected())node.unselect();
			treeNodeId=='';
		}
		Ext.getCmp('_createNode').enable();
		Ext.getCmp('_deleteNode').enable();
		Ext.getCmp('_createSubNode').enable();
		resetForm(menuTreeForm);
	}
		
	//保存的动作
	function _saveMenu(){
		if (strOperType == 'copycreate'){
			$('Menu.menuId').value = '';
		}
		
		var param = Form.serialize('menuForm');
		param += "&strOperType="+strOperType;
		param += "&Menu.isStandard=" +dict_div_isStandard.getValue();
		/*复制创建处理  暂时屏蔽掉
		if (strOperType == 'copycreate'){
			div_center_weast_tree.expandAll();
			var rootNode = div_center_weast_tree.getRootNode();
			div_center_weast_tree.updateAllNodes(rootNode, true);
		}
		*/
		param += getdiv_center_weast_treeData();
		new AjaxEngine(contextPath + '/basicApp/authManage/menuController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle, callback: saveCallBackHandle});
	}
	
	function _cancelMenu(){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	}

	/**
	 * 翻译
	 */
	function _translate()
	{
		/*
		var formTranslate=new Ext.form.FormPanel({
			frame:true,
			baseCls:'x-plain',
			autoHeight:true,
			labelWidth:60,
			name:'formTranslate',
			id:'formTranslate',
			width:200,
			fileUpload:true,
			bodyStyle:'padding: 10px 10px 0 10px;',
			defaults:{
				anchor:'95%',
				msgTarget:'side'
			},
			items:[{
				id:'sourceLanguage',
				width:200,
				allowBlank:false,
				xtype:'textfield',
				fieldLabel:'源语言',
				blankText:'[源语言]不能为空！',
				anchor:'95%'
			},{
				id:'targerLanguage',
				width:200,
				allowBlank:false,
				xtype:'textfield',
				fieldLabel:'目标语言',
				blankText:'[目标语言]不能为空！',
				anchor:'95%'
			}
			]
		});	

		var winTranslate = new Ext.Window({
		    title: '翻译',
		    width: 250,
		    height:160,
		    layout: 'fit',
		    iconCls:'icon-window',
		    plain:true,
		    modal:true,
		    bodyStyle:'padding:5px;',
		    buttonAlign:'center',
		    maximizable:false,
			closeAction:'close',
			closable:false,
			collapsible:false,
		    items: formTranslate,
		    buttons: [
		    {
		        text: '确认翻译',
		        handler:function()
		        {
					if(formTranslate.form.isValid()){
						var param = "&menuId="+ $('Menu.menuId').value+"&"+ Form.serialize('formTranslate') ;
				    	 formTranslate.form.reset();
				    	 winTranslate.close(); 
						 _getMainFrame().maintabs.addPanel("翻译菜单文本",'',contextPath + '/basicApp/authManage/menuController.spr?action=_translate'+ param,'');

					}
				}	
		    },
		    {
		        text: '取消',
		        handler:function()
		        {
		    	    formTranslate.form.reset();
		    	    winTranslate.close();
		        }
		    }]
		});
			
		winTranslate.show();
		*/
	_getMainFrame().ExtModalWindowUtil.show('翻译',
			    contextPath + '/basicApp/authManage/menuController.spr?action=_translateSelectLanguage&menuId='+ $('Menu.menuId').value,
					'',
					'',
					{width:350,height:140});
	}
	
	function saveCallBackHandle(transport){
		div_center_weast_tree.fireEvent('commitchanges');
		div_center_weast_tree.getRootNode().setText($('Menu.menuName').value);
		
		var responseUtil = new AjaxResponseUtils(transport.responseText);
		var result = responseUtil.getCustomField("coustom");
		var menuId = result.menuId;
		
		$('Menu.menuId').value = menuId ;
		$('menuTextId').value = result.menuTextId ;
		//alert($('Menu.menuId').value);
		
		//用判断是不是新增的,如果是新的保存完成后要让下面工具的可用
		if (showDownFlag == true){
			Ext.getCmp('_createNode').enable();
			Ext.getCmp('_deleteNode').enable();
			Ext.getCmp('_createSubNode').enable();

			showDownFlag = false;
		}

		document.getElementById("Menu.creator_text").value = result.creator_text;
		document.getElementById("Menu.creator").value = result.creator;
		document.getElementById("Menu.createTime").value = result.createtime;
		document.getElementById("Menu.lastModifyor_text").value = result.lastmodifyer_text;
		document.getElementById("Menu.lastModifyor").value = result.lastmodifyer;
		document.getElementById("Menu.lastModifyTime").value = result.lastmodifytime;
		
	}
	
	function nameBlur(){
		var rootNode = div_center_weast_tree.getRootNode();
		rootNode.setText($('Menu.menuName').value);
	}
	
	function setAllControlEnable(isEnable)
	{
		div_authReourceId_sh.setDisabled(isEnable);
		div_refMenuId_sh.setDisabled(isEnable);
		dict_div_nodeType.setDisabled(isEnable);
		
		$('MenuTree.nodeDesc').readOnly = isEnable;
	    $('MenuTree.url').readOnly = isEnable;
	    $('MenuTree.orderNo').readOnly = isEnable;
	    $('MenuTree.icon').readOnly = isEnable;
	}
	//fuyy 2010_01-06
	function setPartControlEnable(nodeTypValue){
		//如果节点类型为文件夹，则权限类型，URL，参考菜单不可编辑
		if (nodeTypValue == '1'){
			div_authReourceId_sh.setDisabled(true);
			div_refMenuId_sh.setDisabled(true);
			$('MenuTree.url').readOnly = true;
		}
		//如果节点类型为事务方法，则参考菜单不可编辑
		if (nodeTypValue == '2'){
			div_authReourceId_sh.setDisabled(false);
			div_refMenuId_sh.setDisabled(true);
			$('MenuTree.url').readOnly = false;
		}
		//如果节点类型为菜单参考,则权限类型，URL不可编辑
		if (nodeTypValue == '3'){
			div_authReourceId_sh.setDisabled(true);
			div_refMenuId_sh.setDisabled(false);
			$('MenuTree.url').readOnly = true;
		}
	}
	//fuyy 2010_01-06
	Ext.onReady(function(){
		if (strOperType == 'view'){
			$('Menu.menuName').readOnly = true;
			$('Menu.menuDesc').readOnly = true;
			$('MenuTree.nodeDesc').readOnly = true;
			$('MenuTree.orderNo').readOnly = true;
			$('MenuTree.icon').readOnly = true;
		}else{
			setAllControlEnable(true);
		}

		dict_div_nodeType.on("select",function(nodeCbo){
			setPartControlEnable(nodeCbo.value);
		});
		
		
		//树的单击事件
	    div_center_weast_tree.on('click',function(node, e){
			resetForm(menuTreeForm);
			var eab = node.attributes.entityAttributes;
	        setJSONToForm('menuTreeForm',eab);

			node.expand();
			if (strOperType != 'view'){
				if (node.id == '-1'){
					Ext.getCmp('_createNode').disable();
					Ext.getCmp('_deleteNode').disable();
					Ext.getCmp('_createSubNode').enable();
					setAllControlEnable(true);
					dict_div_nodeType.setValue('1');
					$('MenuTree.nodeDesc').value = node.text;
					treeNodeId = node.id;
				}else{
					Ext.getCmp('_createNode').enable();
					Ext.getCmp('_deleteNode').enable();
					if(node.isLeaf())Ext.getCmp('_createSubNode').disable();
					else Ext.getCmp('_createSubNode').enable();
					setAllControlEnable(false);
					//fuyy 2010-01-06
					setPartControlEnable(dict_div_nodeType.getValue());
					treeNodeId = node.id;
				}
			}
	        Ext.getCmp('submit').setDisabled(false);
	        Ext.getCmp('reset').setDisabled(false);	
	    });
    
		var toolbars = new Ext.Toolbar({
			items:[
					//{id:'_edit',text:'修改',disabled:true,iconCls:'icon-edit',disabled:showFlag},'-',
					//{id:'_delete',text:'删除',disabled:true,iconCls:'icon-delete',disabled:showFlag},'-',
					//{id:'_print',text:'打印',disabled:true,iconCls:'icon-print',disabled:showFlag},'-',
					'->',
					{id:'_translate',text:'翻译',handler:_translate,iconCls:'icon-table-save',disabled:showFlag,hidden:(strOperType == 'view')},'-'
					,{id:'_save',text:'保存',handler:_saveMenu,iconCls:'icon-table-save',disabled:showFlag,hidden:(strOperType == 'view')},'-'
					,{id:'_cancel',text:'取消',handler:_cancelMenu,iconCls:'icon-undo'},'-'
					],
			renderTo:"div_toolbar"
		});
	
		var centerToolbars = new Ext.Toolbar({
			items:[
					{id:'_createNode',text:'创建同级节点',handler:_createNode,iconCls:'icon-item-add',disabled:showDownFlag,hidden:(strOperType == 'view')},'-',
					{id:'_createSubNode',text:'创建子节点',handler:_createSubNode,iconCls:'icon-item-add',disabled:showDownFlag,hidden:(strOperType == 'view')},'-',
					{id:'_deleteNode',text:'删除节点',handler:_deleteNode,iconCls:'icon-item-delete',disabled:showDownFlag,hidden:(strOperType == 'view')},'-'
					],
			renderTo:"div_centerToolbar"
		});
	
	   	var viewport = new Ext.Viewport({
	   		layout:"border",
	   		border:false,
	   		items:[{
	   			region:'center',
				layout:'border',
				border:false,
				items:[{
					region:'north',
					height:175,
					contentEl:'div_north'
				},{
					region:'west',
					width:180,
					layout:'fit',
					autoScroll:true,
					contentEl:'div_center_weast'
				},{
					region:'center',
		            layout:'fit',
		            contentEl:'div_center'
				}]
	   	   	}]
	   	});
	});
</script>