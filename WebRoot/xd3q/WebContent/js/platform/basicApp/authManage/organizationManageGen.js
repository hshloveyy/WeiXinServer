/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年04月16日 09点50分38秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象组织(Organization)管理页面JS文件
 */

          

/**
 * 增加【组织】树同级节点
 */
function _addNodeOrganization()
{
	if (treeNodeId == ''){
			_getMainFrame().showInfo(Txt.organization_PlaseSelectTreeNode);
		}else{
Ext.getCmp('_saveOrUpdateOrganization').enable();
Ext.getCmp('_cancel').enable();
			resetMainForm();
			var node = div_center_weast_tree.getNodeById(treeNodeId);
			var parentTreeNode = node.parentNode;

			$('Organization.ORGANIZATIONID').value = "";
			$('Organization.PARENTORGID').value = parentTreeNode.id;
			createNewNode(parentTreeNode.id);
			//刷新数据集
		}
}
          

/**
 * 增加【组织】树下级节点
 */
function _addSubNodeOrganization()
{
	if (treeNodeId == ''){
			_getMainFrame().showInfo(Txt.organization_PlaseSelectTreeNode);
		}else{
Ext.getCmp('_saveOrUpdateOrganization').enable();
Ext.getCmp('_cancel').enable();
			resetMainForm();
			var node = div_center_weast_tree.getNodeById(treeNodeId);
			$('Organization.organizationId').value = "";
			$('Organization.parentOrganizationId').value = node.id;
			createNewNode(node.id);
			//刷新数据集
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
	
Ext.getCmp('_addNodeOrganization').disable();
Ext.getCmp('_addSubNodeOrganization').disable();
Ext.getCmp('_deleteNodeOrganization').disable();
Ext.getCmp('_saveOrUpdateOrganization').disable();
Ext.getCmp('_cancel').disable();			//刷新数据集
}
	
/**
 * 删除【组织】树节点
 */
function _deleteNodeOrganization()
{
	if (treeNodeId == ''){
		_getMainFrame().showInfo(Txt.organization_PlaseSelectTreeNode);
	}else{
		var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
		var parentTreeNode = treeNode.parentNode;
	
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
			msg: Txt.organization_Delete_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					if (treeNode.leaf == false){
						_getMainFrame().showInfo(Txt.organization_NotDeleteTreeNode);
					}else{
						var param = "&organizationId=" + treeNodeId;
						param += "&action=_deleteNode";
						new AjaxEngine(contextPath + '/platform/basicApp/authManage/organizationController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle, callback: deleteCallBackHandle});
					}
				}
			}
		});
	}
}

          
/**
 * 保存 
 */
function _saveOrUpdateOrganization()
{					 
	deleteNewNode();
	if(_presaveOrUpdateOrganization()){
		var param = Form.serialize('mainForm');	
		           
			        	         
		param = param + ""+ getOrgPersonnelGridData(); 
        //alert(param);
			        		    new AjaxEngine(contextPath + '/platform/basicApp/authManage/organizationController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateOrganization();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var organizationId=result.organizationId;
	var njson = tranFormToJSON(mainForm);                     
	var nodeid = $('Organization.organizationId').value;
	if(nodeid==""){
		var pnodeid = njson["Organization.parentOrganizationId"];
		var pnode = div_center_weast_tree.getNodeById(pnodeid);
	    var node=new Ext.tree.TreeNode({
			id:result.organizationId,
			text:result.organizationName,
			leaf : true
		});
	    pnode.leaf = false;
		pnode.appendChild(node);
		pnode.expand();
		node.select();
		$('Organization.organizationId').value = result.organizationId;
		organizationTreeClick(node);
	}else{
		var node = div_center_weast_tree.getNodeById(nodeid);
		Ext.apply(node.attributes, njson);
		node.setText(njson["Organization.organizationName"]);
	}
	
	document.getElementById("Organization.creator_text").value = result.creator_text;
	document.getElementById("Organization.creator").value = result.creator;
	document.getElementById("Organization.createTime").value = result.createTime;
	document.getElementById("Organization.lastModifyor_text").value = result.lastModifyor_text;
	document.getElementById("Organization.lastModifyor").value = result.lastModifyor;
	document.getElementById("Organization.lastmodifytime").value = result.lastmodifytime;
	isCreateCopy = false;	
	reload_OrgPersonnel_grid("defaultCondition=YORGPERSONNEL.ORGANIZATIONID='"+ organizationId +"'");

}

          
/**
 * 取消
 */
function _cancel()
{	
 	resetMainForm();
}
             
  
  
/**
 *  
 *组织员工,grid上的增加和删除操作(搜索帮助)回调函数
 */  
function winOrgPersonnelCallBack(jsonArrayData)
{
 	for(var i=0;i<jsonArrayData.length;i++){
 		var isExists = false;
 		for (var j = 0;j<OrgPersonnel_store.getCount();j++){
		   if (OrgPersonnel_store.getAt(j).get('personnelid') == jsonArrayData[i].PERSONNELID)
 				isExists = true;
 				break;
 		}

   if (isExists == false){
 	    var OrgPersonnelFields = new OrgPersonnel_fields({
	        orgpersonnelid:''
		   ,organizationId:''
		   ,organizationId_text:''
		   ,personnelid:''
		   ,personnelid_text:''
	   });		
	   
	   	OrgPersonnel_grid.stopEditing();
		OrgPersonnel_grid.getStore().insert(0, OrgPersonnelFields);
		OrgPersonnel_grid.startEditing(0, 0);
		var record = OrgPersonnel_grid.getStore().getAt(0);
		record.set('client',jsonArrayData[i].MANDT);
		record.set('orgpersonnelid',jsonArrayData[i].ORGpersonnelid);
		record.set('organizationId',jsonArrayData[i].ORGANIZATIONID);
		record.set('organizationId_text',jsonArrayData[i].ORGANIZATIONNAME);
		record.set('personnelid',jsonArrayData[i].PERSONNELID);
		record.set('personnelid_text',jsonArrayData[i].PERSONNELNAME);
 		}
 	}
}

/**
 *   
 *组织员工,grid上的增加和删除操作(搜索帮助)
 */  
var searchOrgPersonnelHelpWin = new Ext.SearchHelpWindow({
	    shlpName : 'YHPERSONNEL',
		callBack : winOrgPersonnelCallBack
});

/**
 *  
 *组织员工,grid上的增加和删除操作,弹出搜索帮助
 */  
function _assignOrgPersonnel(){
	if(_preassignOrgPersonnel()){
		searchOrgPersonnelHelpWin.show();
	}
	_postassignOrgPersonnel();
}
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
    
  

/**
 *组织员工
 * grid上的和删除操作
 */
function _deletesOrgPersonnel(){
	if(_predeletesOrgPersonnel()){
		if (OrgPersonnel_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.orgPersonnel_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (buttonid == 'yes'){
					   var records = OrgPersonnel_grid.selModel.getSelections();
					   for (var i=0;i<records.length;i++){
						 OrgPersonnel_grid.getStore().remove(records[i]);
					   }
				     }
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.orgPersonnel_Deletes_SelectToOperation);
		}
	}
	_postdeletesOrgPersonnel();
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
Ext.getCmp('_addNodeOrganization').disable();
Ext.getCmp('_addSubNodeOrganization').disable();
Ext.getCmp('_deleteNodeOrganization').disable();}

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
     $('Organization.organizationId').value='';
     $('Organization.client').value='';
     $('Organization.parentOrganizationId').value='';
     $('Organization.organizationName').value='';
     $('Organization.organizationDesc').value='';
     $('Organization.organizationCode').value='';
     $('Organization.orderNo').value='';
     $('Organization.busineseArea').value='';
     $('Organization.costCenter').value='';
     $('Organization.companyCode').value='';
     $('Organization.controllingArea').value='';
     $('Organization.profitCenter').value='';
     $('Organization.purchasingOrganization').value='';
     $('Organization.saleOrganization').value='';
     $('Organization.budgetOrganization').value='';
	 $('Organization.creator_text').value='';
	 $('Organization.creator').value='';
	 $('Organization.createTime').value='';
	 $('Organization.lastModifyor_text').value='';
	 $('Organization.lastModifyor').value='';
	 $('Organization.lastmodifytime').value='';
}
	
/**
 * Organization树单击事件处理
 */
function organizationTreeClick(node)
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
Ext.getCmp('_addNodeOrganization').disable();
Ext.getCmp('_addSubNodeOrganization').enable();
Ext.getCmp('_deleteNodeOrganization').disable();
Ext.getCmp('_saveOrUpdateOrganization').disable();
Ext.getCmp('_cancel').disable();
}else{
Ext.getCmp('_addNodeOrganization').enable();
Ext.getCmp('_addSubNodeOrganization').enable();
Ext.getCmp('_deleteNodeOrganization').enable();
Ext.getCmp('_saveOrUpdateOrganization').enable();
    Ext.Ajax.request({
	        url: context + '/platform/basicApp/authManage/organizationController.spr?action=_getTreeNode',
	        params:{'organizationId': node.id},
	        success: function(response, options){
	            var responseArray = Ext.util.JSON.decode(response.responseText);
$('Organization.organizationId').value=responseArray.organizationId;
$('Organization.client').value=responseArray.client;
$('Organization.parentOrganizationId').value=responseArray.parentOrganizationId;
$('Organization.organizationName').value=responseArray.organizationName;
$('Organization.organizationDesc').value=responseArray.organizationDesc;
$('Organization.organizationCode').value=responseArray.organizationCode;
$('Organization.orderNo').value=responseArray.orderNo;
$('Organization.busineseArea').value=responseArray.busineseArea;
$('Organization.costCenter').value=responseArray.costCenter;
$('Organization.companyCode').value=responseArray.companyCode;
$('Organization.controllingArea').value=responseArray.controllingArea;
$('Organization.profitCenter').value=responseArray.profitCenter;
$('Organization.purchasingOrganization').value=responseArray.purchasingOrganization;
$('Organization.saleOrganization').value=responseArray.saleOrganization;
$('Organization.budgetOrganization').value=responseArray.budgetOrganization;
$('Organization.creator_text').value=responseArray.creator_text;
$('Organization.creator').value=responseArray.creator;
$('Organization.createTime').value=responseArray.createTime;
$('Organization.lastModifyor_text').value=responseArray.lastModifyor_text;
$('Organization.lastModifyor').value=responseArray.lastModifyor;
$('Organization.lastmodifytime').value=responseArray.lastmodifytime;	        },
	        failure:function(response, options){
	        	_getMainFrame().showInfo('获取该组织资源失败！');
	        }
    	});
	}
			//刷新数据集
	       reload_OrgPersonnel_grid("defaultCondition=YORGPERSONNEL.ORGANIZATIONID='"+  node.id +"'");
}
