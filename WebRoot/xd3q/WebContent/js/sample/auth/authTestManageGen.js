/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月16日 09点34分24秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象AuthTest(AuthTest)管理页面JS文件
 */

          

/**
 * 增加【AuthTest】树同级节点
 */
function _addNodeAuthTest()
{
	if (treeNodeId == ''){
			_getMainFrame().showInfo(Txt.authTest_PlaseSelectTreeNode);
		}else{
Ext.getCmp('_cancel').enable();
Ext.getCmp('_saveOrUpdateAuthTest').enable();
			resetMainForm();
			var node = div_center_weast_tree.getNodeById(treeNodeId);
			var parentTreeNode = node.parentNode;

			$('AuthTest.authresourceid').value = "";
			$('AuthTest.parentauthresid').value = parentTreeNode.id;
			createNewNode(parentTreeNode.id);
			//刷新数据集
	       reload_AuthItem_grid("defaultCondition=YAUTHITEM.AUTHRESOURCEID='"+  node.id +"'");
		}
}
          

/**
 * 增加【AuthTest】树下级节点
 */
function _addSubNodeAuthTest()
{
	if (treeNodeId == ''){
			_getMainFrame().showInfo(Txt.authTest_PlaseSelectTreeNode);
		}else{
Ext.getCmp('_cancel').enable();
Ext.getCmp('_saveOrUpdateAuthTest').enable();
			resetMainForm();
			var node = div_center_weast_tree.getNodeById(treeNodeId);
		
			$('AuthTest.authresourceid').value = "";
			$('AuthTest.parentauthresid').value = node.id;
			createNewNode(node.id);
			//刷新数据集
	       reload_AuthItem_grid("defaultCondition=YAUTHITEM.AUTHRESOURCEID='"+  node.id +"'");
		}
}

          

/**
 * 删除树节点回调函数
 */
function deleteCallBackHandle(transport){
	var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
	treeNode.remove();
	treeNodeId = '';
	resetMainForm();
	
Ext.getCmp('_addNodeAuthTest').disable();
Ext.getCmp('_addSubNodeAuthTest').disable();
Ext.getCmp('_deleteNodeAuthTest').disable();
Ext.getCmp('_cancel').disable();
Ext.getCmp('_saveOrUpdateAuthTest').disable();			//刷新数据集
	       reload_AuthItem_grid("defaultCondition=YAUTHITEM.AUTHRESOURCEID='"+  node.id +"'");
}
	
/**
 * 删除【AuthTest】树节点
 */
function _deleteNodeAuthTest()
{
	if (treeNodeId == ''){
		_getMainFrame().showInfo(Txt.authTest_PlaseSelectTreeNode);
	}else{
		var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
		var parentTreeNode = treeNode.parentNode;
	
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
			msg: Txt.authTest_Delete_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					if (treeNode.leaf == false){
						_getMainFrame().showInfo(Txt.authTest_NotDeleteTreeNode);
					}else{
						var param = "&authresourceid=" + treeNodeId;
						param += "&action=_deleteNode";
						new AjaxEngine(contextPath + '/sample/auth/authTestController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle, callback: deleteCallBackHandle});
					}
				}
			}
		});
	}
}

          
/**
 * 取消
 */
function _cancel()
{	
 	resetMainForm();
}
          
/**
 * 保存 
 */
function _saveOrUpdateAuthTest()
{
	deleteNewNode();
	if(_presaveOrUpdateAuthTest()){
		var param = Form.serialize('mainForm');	
		           
			        	         
		param = param + ""+ getAuthItemGridData(); 
			        		    new AjaxEngine(contextPath + '/sample/auth/authTestController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}
	_postsaveOrUpdateAuthTest();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var authresourceid=result.authresourceid;
	var njson = tranFormToJSON(mainForm);                     
	var nodeid = $('AuthTest.authresourceid').value;
	if(nodeid==""){
		var pnodeid = njson["AuthTest.parentauthresid"];
		var pnode = div_center_weast_tree.getNodeById(pnodeid);
	    var node=new Ext.tree.TreeNode({
			id:result.authresourceid,
			text:result.authresourcedesc,
			leaf : true
		});
	    pnode.leaf = false;
		pnode.appendChild(node);
		pnode.expand();
		node.select();
		$('AuthTest.authresourceid').value = result.authresourceid;
		authTestTreeClick(node);
	}else{
		var node = div_center_weast_tree.getNodeById(nodeid);
		Ext.apply(node.attributes, njson);
		node.setText(njson["AuthTest.authresourcedesc"]);
	}
	
	isCreateCopy = false;	
	reload_AuthItem_grid("defaultCondition=YAUTHITEM.AUTHRESOURCEID='"+ authresourceid +"'");
}

             
/**
 * grid 上的 创建按钮调用方法 
 * 新增AuthTest,AuthInfo
 */
function _createAuthItem()
{
  if(_precreateAuthItem()){
     _commonMethodOperation('1',Txt.authItem_Create,AuthItem_grid,'sample/auth/authItemController.spr?action=_create','_createAuthItemCallBack',true);
   }
   _postcreateAuthItem();
}

/**
 * 打开页签，回调
 */
function _createAuthItemCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_insertAuthItemRow(json);
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增AuthTest,AuthInfo
 */
function _createAuthItemm()
{
  if(_precreateAuthItem()){
	_getMainFrame().ExtModalWindowUtil.show(Txt.authItem_Create,
			contextPath + '/sample/auth/authItemController.spr?action=_create','',_createAuthItemmCallBack,{width:660,height:300});
   }
   _postcreateAuthItem();
}

/**
 * 弹出窗口，回调
 */
function _createAuthItemmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_insertAuthItemRow(json);
}

function _insertAuthItemRow(json)
{
	if(json){
	var authItemFields = new AuthItem_fields({
		authinfoid:''
		,authresourceid:''
		,memo:''
	});		

		AuthItem_grid.stopEditing();
		AuthItem_grid.getStore().insert(0, authItemFields);
		AuthItem_grid.startEditing(0, 0);
		var record = AuthItem_grid.getStore().getAt(0);
		record.set('authinfoid',json["AuthItem.authinfoid"]);
		record.set('authresourceid',json["AuthItem.authresourceid"]);
		record.set('memo',json["AuthItem.memo"]);
	}
}	
    
    
/**
 *AuthTest行项目
 *复制创建
 */
function _copyCreateAuthItem()
{
  if(_precopyCreateAuthItem()){
		if (AuthItem_grid.selModel.hasSelection() > 0){
			var records = AuthItem_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.authItem_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
		    _commonMethodOperation('1',Txt.authItem_CopyCreate,AuthItem_grid,'sample/auth/authItemController.spr?action=_copyCreate'+pars,'_createAuthItemCallBack',true);
		
		}else{
			_getMainFrame().showInfo(Txt.authItem_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateAuthItem();
}


/**
 *AuthTest行项目
 *复制创建
 */
function _copyCreateAuthItemm()
{
  if(_precopyCreateAuthItem()){
		if (AuthItem_grid.selModel.hasSelection() > 0){
			var records = AuthItem_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.authItem_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
			_getMainFrame().ExtModalWindowUtil.show(Txt.authItem_CopyCreate,contextPath + '/sample/auth/authItemController.spr?action=_copyCreate'+pars,'',_createAuthItemmCallBack,{width:610,height:200});
		}else{
			_getMainFrame().showInfo(Txt.authItem_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateAuthItem();
}

    
    
  
    
/**
  * AuthTest行项目查看操作
  */
function _viewAuthItem(id,url)
{
	if(_previewAuthItem()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = AuthItem_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.authItem_View,	url,'','',{width:660,height:300});
	}
	_postviewAuthItem();
}
/**
  * AuthTest行项目查看，打开到页签 ,回调函数
  */
function _viewAuthItempCallBack()
{
}

/**
  * AuthTest行项目查看，弹出窗口 ,回调函数
  */
function _viewAuthItemmCallBack()
{
}


function _editAuthItempCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_modifyAuthItemRow(json);
}


function _editAuthItemmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyAuthItemRow(json);
}

/**
  * AuthTest行项目编辑操作
  */
function _editAuthItem(id,url)
{
	if(_preeditAuthItem()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = AuthItem_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.authItem_Edit,	url,'',_editAuthItemCallBack,{width:660,height:300});
      
      }
      _posteditAuthItem();
}
function _editAuthItemCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyAuthItemRow(json);
}

/**
 * 行项目编辑后的grid更新
 */
function _modifyAuthItemRow(json)
{
	if(json){
		var records = AuthItem_grid.selModel.getSelections();
		var record = records[0];
		record.set('memo',json["AuthItem.memo"]);
	}
}
    
  

/**
 * 创建并新增一个空结点
 */
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
Ext.getCmp('_addNodeAuthTest').disable();
Ext.getCmp('_addSubNodeAuthTest').disable();
Ext.getCmp('_deleteNodeAuthTest').disable();}

/**
 * 删除新增的结点
 */
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
 * 清空form组件的信息
 */
function resetMainForm()
{
     $('AuthTest.authresourceid').value='';
     $('AuthTest.parentauthresid').value='';
     $('AuthTest.authresourcetype').value='';
     $('AuthTest.authresourcedesc').value='';
     $('AuthTest.objecttype').value='';
     $('AuthTest.object').value='';
     $('AuthTest.objectdesc').value='';
     $('AuthTest.methodname').value='';
     $('AuthTest.methodtype').value='';
     $('AuthTest.methoddesc').value='';
     $('AuthTest.url').value='';
     $('AuthTest.tcode').value='';
     $('AuthTest.degree').value='';
}
	
/**
 * AuthTest树单击事件处理
 */
function authTestTreeClick(node)
{
	treeNodeId = node.id;
	if (treeNodeId == 'new'){
		return;
	}else{
		deleteNewNode();
	}
	resetMainForm();
//控制工具条的按钮显示状态
if (node.id == '-1'){
Ext.getCmp('_addNodeAuthTest').disable();
Ext.getCmp('_addSubNodeAuthTest').enable();
Ext.getCmp('_deleteNodeAuthTest').disable();
Ext.getCmp('_cancel').disable();
Ext.getCmp('_saveOrUpdateAuthTest').disable();
}else{
Ext.getCmp('_addNodeAuthTest').enable();
Ext.getCmp('_addSubNodeAuthTest').enable();
Ext.getCmp('_deleteNodeAuthTest').enable();
Ext.getCmp('_saveOrUpdateAuthTest').enable();
    Ext.Ajax.request({
	        url: context + '/sample/auth/authTestController.spr?action=_getTreeNode',
	        params:{'authresourceid': node.id},
	        success: function(response, options){
	            var responseArray = Ext.util.JSON.decode(response.responseText);
$('AuthTest.authresourceid').value=responseArray.authresourceid;
$('AuthTest.parentauthresid').value=responseArray.parentauthresid;
$('AuthTest.authresourcetype').value=responseArray.authresourcetype;
$('AuthTest.authresourcedesc').value=responseArray.authresourcedesc;
$('AuthTest.objecttype').value=responseArray.objecttype;
$('AuthTest.object').value=responseArray.object;
$('AuthTest.objectdesc').value=responseArray.objectdesc;
$('AuthTest.methodname').value=responseArray.methodname;
$('AuthTest.methodtype').value=responseArray.methodtype;
$('AuthTest.methoddesc').value=responseArray.methoddesc;
$('AuthTest.url').value=responseArray.url;
$('AuthTest.tcode').value=responseArray.tcode;
$('AuthTest.degree').value=responseArray.degree;	        },
	        failure:function(response, options){
	        	_getMainFrame().showInfo('获取该AuthTest资源失败！');
	        }
    	});
	}
			//刷新数据集
	       reload_AuthItem_grid("defaultCondition=YAUTHITEM.AUTHRESOURCEID='"+  node.id +"'");
}
