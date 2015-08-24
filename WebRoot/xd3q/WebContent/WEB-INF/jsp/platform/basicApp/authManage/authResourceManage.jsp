<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限资源管理</title>

</head>
<body>
<div id="div_north">
<div id="div_toolbar"></div>
</div>

<div id="div_center">
<div id="div_centerToolbar"></div>
<div id="div_authResourceForm" class="search">
<form id="authResourceForm" name="authResourceForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
	<td align="right" width="15%">资源类型:</td>
	<td colspan="3" width="85%">
		<div id="div_authResourceType"></div>
	</td>
</tr>
<tr>
	<td width="15%" align="right">权限描述:</td>
	<td width="85%" colspan="3">
        <input type="hidden" id="AuthResource.authResourceId" name="AuthResource.authResourceId" value="">
		<input type="hidden" id="AuthResource.parentAuthResourceId" name="AuthResource.parentAuthResourceId" value="">
		<input  type="text" id="AuthResource.authResourceDesc" name="AuthResource.authResourceDesc" readonly ="readonly" size="89" >
    </td>
</tr>
<tr>
	<td width="15%" align="right">对象类型:</td>
	<td width="30%">
		<div id="div_objectType"></div>
		<input type="hidden" id="AuthResource.objectType" name="AuthResource.objectType" value="1">
        <input class="inputText" type="text" id="AuthResource.objectType_text" name="AuthResource.objectType_text" readonly ="readonly" value="业务对象">
	</td>
	<td width="15%" align="right">对象名:</td>
	<td width="40%">
	   <div id="div_object"></div>
	</td>
</tr>
<tr>
	<td width="15%" align="right">对象描述:</td>
	<td width="85%" colspan="3">
	<input type="text" id="AuthResource.objectDesc" name="AuthResource.objectDesc" readonly ="readonly" value="" size="89">
	</td>
</tr>
<tr>
	<td width="15%" align="right">方法名:</td>
	<td width="30%" >
		<input class="inputText" type="text" id="AuthResource.methodName" name="AuthResource.methodName"  readonly ="readonly" value="">
	</td>
	<td width="15%" align="right">方法类型:</td>
	<td width="40%" >
		<div id="div_methodType"></div>
	</td>
</tr>
<tr>
	<td width="15%" align="right">方法描述:</td>
	<td width="85%" colspan="3">
	<input type="text" id="AuthResource.methodDesc" name="AuthResource.methodDesc" readonly ="readonly" value="" size="89">
	</td>
</tr>
<tr>
	<td width="15%" align="right">URL:</td>
	<td width="85%" colspan="3">
	<input type="text" id="AuthResource.url" name="AuthResource.url" value="" readonly ="readonly" size="89">
	</td>
</tr>
<tr>
	<td width="15%" align="right">事务码:</td> 
	<td width="85%" colspan="3">
	<input class="inputText" type="text" id="AuthResource.tcode" name="AuthResource.tcode" value="" readonly ="readonly" size="69">
	</td>
</tr>
<tr>
	<td colspan="4">
		<table width="600" id="buttonTable" border="0" cellpadding="0" cellspacing="0">
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
<div id="tree-div">
    <div id="div_center_weast"></div>
</div>
</body>
</html>

<fisc:tree tableName="yauthresource" entityName="com.infolion.platform.basicApp.authManage.domain.AuthResource" rootValue="-1" idColumnName="authResourceId" parentColumnName="parentAuthresId" titleColumnName="authresourcedesc" whereSql=""
linkUrl="" target="" treeTitle="权限资源" divId="div_center_weast" needCheckChilden="false" needAutoLoad="true" 
leafConditions="node.attributes.entityAttributes[\"AuthResource.authResourceType\"] != 1" width="'auto'"></fisc:tree>

<fisc:searchHelp boName="AuthResource" boProperty="object" divId="div_object" disabled="true"></fisc:searchHelp>
<fisc:dictionary boName="AuthResource" boProperty="authResourceType" divId="div_authResourceType" dictionaryName="YDAUTHRESOURCE" disabled="true" isNeedAuth="false" ></fisc:dictionary>
<fisc:dictionary boName="AuthResource" boProperty="methodType" divId="div_methodType" dictionaryName="YDMETHODTYPE" disabled="true" isNeedAuth="false" ></fisc:dictionary>


<script type="text/javascript" defer="defer">
	//全局路径
	var context = '<%= request.getContextPath() %>';
	//存放当前选 中树的节点ID
	var treeNodeId = '';
	//用于判断是菜单的操作还是菜单树的操作动作
	var callBackType = '';
	//存储操作类型的变量
	var strOperType = '';
	//用于显示上面工具条的变量
	var showFlag;
	//用于显示下面工具条的变量
	var showDownFlag;
	//用于保存节点的下挂信息
	var nodeIdList = '';
	
	//确认按钮的操作动作 
	function btn_submit(){
		var type=dict_div_authResourceType.getValue();
		var mType=dict_div_methodType.getValue();
		if(type==''){
			_getMainFrame().showInfo('资源类型不能为空！');
			return;
		}
		if(type==2 && $('AuthResource.object').value==''){
			_getMainFrame().showInfo('资源类型为方法时,请获取对象名！');
			return;
		}
		if($('AuthResource.authResourceDesc').value==''){
			_getMainFrame().showInfo('权限描述不能为空');
			return;
		}
		//alert("权限资源类别:" + type + "方法类别:" + mType);
		var param = Form.serialize('authResourceForm');
		param += "&action=_saveOrUpdate";
		param += "&type="+ type + "&mType=" + mType;
		//alert(param);
		new AjaxEngine(context + '/basicApp/authManage/authResourceController.spr', 
		{method:"post", parameters: param, onComplete: callBackHandle, callback: saveCallBackHandle});
	}
	
	//清空按钮的操作动作
	function btn_reset(){
		resetForm(authResourceForm);
	}
	
	//树的单击事件
	function treeClick(node){
		dict_div_authResourceType.setDisabled(true);
		resetForm(authResourceForm);
        Ext.getCmp('submit').setDisabled(false);
        Ext.getCmp('reset').setDisabled(false);	
        node.expand();
		if (node.id == '-1'){
			Ext.getCmp('_createNode').disable();
			Ext.getCmp('_deleteNode').disable();
	        Ext.getCmp('_createSubNode').enable();
			treeNodeId = node.id;
	        setAllControlEnable(true);
	        dict_div_authResourceType.setValue('1');
	        $('AuthResource.authResourceDesc').value = node.text;
		}else{
			Ext.getCmp('_createNode').enable();
			Ext.getCmp('_deleteNode').enable();
			if(node.isLeaf())Ext.getCmp('_createSubNode').disable();
			else Ext.getCmp('_createSubNode').enable();
			treeNodeId = node.id;
	        setAllControlEnable(false);
	        
	        Ext.Ajax.request({
		        url: context + '/basicApp/authManage/authResourceController.spr?action=getAuthResourceTreeNode',
		        params:{'nodeId': node.id},
		        success: function(response, options){
		            var responseArray = Ext.util.JSON.decode(response.responseText);
	            	dict_div_authResourceType.setValue(responseArray.authResourceType);
	            	dict_div_authResourceType.fireEvent('change');
		            $('AuthResource.authResourceId').value = responseArray.authResourceId.trim();
		            $('AuthResource.parentAuthResourceId').value = responseArray.parentAuthResourceId.trim();
		            $('AuthResource.authResourceDesc').value = responseArray.authResourceDesc.trim();
		            $('AuthResource.tcode').value = responseArray.tcode.trim();
		            div_object_sh.setValue(responseArray.object.trim(),function(){
			            $('AuthResource.objectDesc').value = responseArray.objectDesc.trim();
			            $('AuthResource.methodName').value = responseArray.methodName.trim();
			            $('AuthResource.methodDesc').value = responseArray.methodDesc.trim();
			            $('AuthResource.url').value = responseArray.url.trim();
		            });
		        },
		        failure:function(response, options){
		        	_getMainFrame().showInfo('获取该权限资源失败！');
		        }
	    	});
		}
	}
	
	//AjaxEngine的回调函数
	function saveCallBackHandle(transport){
		var responseUtil = new AjaxResponseUtils(transport.responseText);
		var customField = responseUtil.getCustomField("coustom");
		
		var njson = tranFormToJSON(authResourceForm);
		var nodeid = $('AuthResource.authResourceId').value;
		if(nodeid==""){
			var pnodeid = njson["AuthResource.parentAuthResourceId"]
			var pnode = div_center_weast_tree.getNodeById(pnodeid);
		    var node = new Ext.tree.AsyncTreeNode({id:customField.authResourceId,text:njson["AuthResource.authResourceDesc"],entityAttributes:njson});
			pnode.appendChild(node);
			node.select();
			node.expand();
			$('AuthResource.authResourceId').value = customField.authResourceId;
		}else{
			//huanghu  需要测试
			var node = div_center_weast_tree.getNodeById(nodeid);
			Ext.apply(node.attributes, njson);
			node.setText(njson["AuthResource.authResourceDesc"]);
		}
		
		treeClick(node);
	}
	
	//删除
	function deleteCallBackHandle(transport){
		var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
		treeNode.remove();
		treeNodeId = '';
		resetForm(authResourceForm);
	}
	
	//创建同级节点的动作
	function _createNode(){
		if (treeNodeId == ''){
			_getMainFrame().showInfo('请选择节点后再进行此功能的操作！');
		}else{
	        dict_div_authResourceType.setDisabled(false);
			resetForm(authResourceForm);
			var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
			var parentTreeNode = treeNode.parentNode;

			$('AuthResource.authResourceId').value = "";
			$('AuthResource.parentAuthResourceId').value = parentTreeNode.id;
		}
	}
	
	//创建子节点的动作
	function _createSubNode(){
		if (treeNodeId == ''){
			_getMainFrame().showInfo('请选择节点后再进行此功能的操作！');
		}else{
	        $('AuthResource.authResourceDesc').readOnly = false;
	        dict_div_authResourceType.setDisabled(false);
			resetForm(authResourceForm);
			var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
		
			$('AuthResource.authResourceId').value = "";
			$('AuthResource.parentAuthResourceId').value = treeNode.id;
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
										var param = "&nodeId=" + treeNodeId;
										param += "&action=_deleteAuthResourceNodeByParent";
										new AjaxEngine(context + '/basicApp/authManage/authResourceController.spr', 
											   {method:"post", parameters: param, onComplete: callBackHandle, callback: deleteCallBackHandle});
									}
								}
							});
						}else{
							var param = "&nodeId=" + treeNodeId;
							param += "&action=_deleteAuthResourceNode";
							new AjaxEngine(context + '/basicApp/authManage/authResourceController.spr', 
								   {method:"post", parameters: param, onComplete: callBackHandle, callback: deleteCallBackHandle});
							
						}
					}
				}
			});
		}
	}
	
	function _saveMenu(){
	
	}
	
	function _resetMenu(){
	
	}
	
	function setAllControlEnable(isEnable)
	{
		div_object_sh.setDisabled(isEnable);
		$('AuthResource.authResourceDesc').readOnly = isEnable;
	    $('AuthResource.objectDesc').readOnly = isEnable;
	    $('AuthResource.methodName').readOnly = isEnable;
	    $('AuthResource.methodDesc').readOnly = isEnable;
	    $('AuthResource.url').readOnly = isEnable;
	    $('AuthResource.tcode').readOnly = isEnable;
	    //dict_div_authResourceType.setDisabled(isEnable);
	}
	
	Ext.onReady(function(){
	    div_center_weast_tree.on('click',function(node, e){
			treeClick(node);
	    });
	    
		dict_div_authResourceType.on("change",function(){
	        if(dict_div_authResourceType.getValue()=='2')
	        {
	        	div_object_sh.setDisabled(false);
	        	$('AuthResource.objectDesc').readOnly = false;
	            $('AuthResource.methodName').readOnly = false;
	            $('AuthResource.methodDesc').readOnly = false;
	            $('AuthResource.url').readOnly = false;
	            $('AuthResource.tcode').readOnly = false;
	        }
	        else
	        {
	        	div_object_sh.setDisabled(true);
	            $('AuthResource.objectDesc').readOnly = true;
	            $('AuthResource.methodName').readOnly = true;
	            $('AuthResource.methodDesc').readOnly = true;
	            $('AuthResource.url').readOnly = true;
	            $('AuthResource.tcode').readOnly = true;
	        }
	        if(dict_div_authResourceType.getValue()=='3'){
	        	$('AuthResource.url').readOnly = false;
	        }
	    });
	    
		var toolbars = new Ext.Toolbar({
			items:[
					{id:'_edit',text:'修改',disabled:true,disabled:showFlag},'-',
					{id:'_delete',text:'删除',disabled:true,disabled:showFlag},'-',
					{id:'_print',text:'打印',disabled:true,disabled:showFlag},'-',
					'->',
					{id:'_save',text:'保存',disabled:true,handler:_saveMenu,disabled:showFlag},'-',
					{id:'_cancel',text:'取消',disabled:true,handler:_resetMenu,disabled:showFlag},'-'
					]
			//renderTo:"div_toolbar"
		});
	
		var centerToolbars = new Ext.Toolbar({
			items:[
					{id:'_createNode',text:'创建同级节点',handler:_createNode,disabled:true,iconCls:'icon-item-add'},'-',
					{id:'_createSubNode',text:'创建子节点',handler:_createSubNode,disabled:true,iconCls:'icon-item-add'},'-',
					{id:'_deleteNode',text:'删除节点',handler:_deleteNode,disabled:true,iconCls:'icon-item-delete'},'-',
					'->',
					{id:'submit',text:'保存',disabled:true,handler:btn_submit,disabled:true,iconCls:'icon-table-save'},'-',
					{id:'reset',text:'取消',disabled:true,handler:btn_reset,disabled:true,iconCls:'icon-undo'},'-'
					],
			renderTo:"div_centerToolbar"
		});
	
	   	var viewport = new Ext.Viewport({
	   		layout:"border",
	   		items:[{
	   			region:'center',
	   			border:false,
				layout:'border', 
				items:[{
					region:'north',
					height:0,
	                border:false,
					contentEl:'div_north'
				},{
	                region:"west",
		            title:"",
		            split:true,          //可改变框体大小
		            collapsible: true,   //可收缩
		            width: 200,
		            minSize: 175,
		            maxSize: 400,
		            margins:'0 0 0 5',
		            //contentEl: 'tree-div'
		            layout:'fit',
		            layoutConfig:{
		               animate:true
		            },
		            items:[{
		                title:'权限资源树',
		                border:false,
		                layout:'fit',
		                contentEl: 'tree-div',
		                tools:[{
		                    id:'refresh',   
		                    qtip: '刷新资源树信息',
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
		                }}],
		                items:[{autoScroll: true,contentEl:'div_center_weast'}]
		            }]
				},{
					region:'center',
		            layout:'fit',
		            contentEl:'div_center'
				}]
	   	   	}]
	   	});
	});
</script>