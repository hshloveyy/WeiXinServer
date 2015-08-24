/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年11月16日 01点50分24秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>社区管理(Community)编辑页面JS文件
 */
 
             
/**
 * grid 上的 创建按钮调用方法 
 * 新增社区管理,Portlet
 */
function _createPortlet()
{
	_getMainFrame().ExtModalWindowUtil.show('Portlet行项目新增',
			contextPath + '/platform/basicApp/sysConsole/portlet/portletController.spr?action=_create','',_insertPortletRow,{width:660,height:300});
}

function _insertPortletRow()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	if(json){
	var portletFields = new Portlet_fields({
		portletid:''
		,portletname:''
		,portletdesc:''
		,authresourceid:''
		,authresourceid_text:''
		,url:''
		,url_text:''
		,communityid:''
		,communitythemeid:''
		,communitythemeid_text:''
		,regionid:''
		,regionid_text:''
		,orderno:''
		,closable:''
		,maximizable:''
		,minimizable:''
		,creator:''
		,createtime:''
		,lastmodifyer:''
		,lastmodifytime:''
	});		
	
		Portlet_grid.stopEditing();
		Portlet_grid.getStore().insert(0, portletFields);
		Portlet_grid.startEditing(0, 0);
		var record = Portlet_grid.getStore().getAt(0);
			record.set('portletid',json["Portlet.portletid"]);
			record.set('portletname',json["Portlet.portletname"]);
			record.set('portletdesc',json["Portlet.portletdesc"]);
			record.set('authresourceid',json["Portlet.authresourceid"]);
			record.set('authresourceid_text',json["Portlet.authresourceid_htext"]);
			record.set('url',json["Portlet.url"]);
			record.set('url_text',json["Portlet.url_htext"]);
			record.set('communityid',json["Portlet.communityid"]);
			record.set('communitythemeid',json["Portlet.communitythemeid"]);
			record.set('communitythemeid_text',json["Portlet.communitythemeid_htext"]);
			record.set('regionid',json["Portlet.regionid"]);
			record.set('regionid_text',json["Portlet.regionid_htext"]);
			record.set('orderno',json["Portlet.orderno"]);
			record.set('closable',json["Portlet.closable"]);
			record.set('maximizable',json["Portlet.maximizable"]);
			record.set('minimizable',json["Portlet.minimizable"]);
			record.set('creator',json["Portlet.creator"]);
			record.set('createtime',json["Portlet.createtime"]);
			record.set('lastmodifyer',json["Portlet.lastmodifyer"]);
			record.set('lastmodifytime',json["Portlet.lastmodifytime"]);
	}
}
	 
    

    
/**
*社区管理行项目
*复制创建
*/
function _copyCreatePortlet()
{
	if (Portlet_grid.selModel.hasSelection() > 0){
		var records = Portlet_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo('进行【社区管理行项目复制创建】操作时，只允许选择一条记录！');
			return;
		}   
		var recordData = commonUrlEncode(records[0].data);
		var pars ="&"+ recordData;
		_getMainFrame().ExtModalWindowUtil.show('复制创建社区管理行项目',contextPath + '/platform/basicApp/sysConsole/portlet/portletController.spr?action=_copyCreate'+pars,'',_insertPortletRow,{width:610,height:200});
		//_commonMethodOperation('2','复制创建社区管理行项目',Portlet_grid,'/platform/basicApp/sysConsole/portlet/portletController.spr?action=_copyCreate'+pars);
	}else{
		_getMainFrame().showInfo('请选择需要进行【社区管理行项目复制创建】操作的记录！');
	}	
}
    

    
/**
 *社区管理行项目
 *批量删除
 */
function _deletesPortlet()
{
}
    

    
  


/**
  * 社区管理行项目查看操作
  */
function _viewPortlet(id,url)
{
	url = contextPath+"/"+url + "&";
	//取得行数据，转发到查看页面
	var records = Portlet_grid.getSelectionModel().getSelections();     
	url = url + commonUrlEncode(records[0].data);
	_getMainFrame().ExtModalWindowUtil.show("社区管理行项目查看",	url,'','',{width:660,height:300});
}
    
/**
  * 社区管理行项目编辑操作
  */
function _editPortlet(id,url)
{
	url = contextPath+"/"+url + "&";
	//取得行数据，转发到编辑页面
	var records = Portlet_grid.getSelectionModel().getSelections();     
	url = url + commonUrlEncode(records[0].data);
	_getMainFrame().ExtModalWindowUtil.show("社区管理行项目编辑",	url,'',_modifyPortletRow,{width:660,height:300});
}

/**
 * 行项目编辑后的grid更新
 */
function _modifyPortletRow()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	if(json){
		var records = Portlet_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo('进行【社区管理行项目编辑】操作时，只允许选择一条记录！');
			return;
		}  
		var record = records[0];
		record.set('portletname',json["Portlet.portletname"]);
		record.set('portletdesc',json["Portlet.portletdesc"]);
		record.set('url',json["Portlet.url"]);
		record.set('communityid',json["Portlet.communityid"]);
		record.set('communitythemeid',json["Portlet.communitythemeid"]);
		record.set('regionid',json["Portlet.regionid"]);
		record.set('orderno',json["Portlet.orderno"]);
		record.set('closable',json["Portlet.closable"]);
		record.set('maximizable',json["Portlet.maximizable"]);
		record.set('minimizable',json["Portlet.minimizable"]);
		record.set('creator',json["Portlet.creator"]);
		record.set('createtime',json["Portlet.createtime"]);
		record.set('lastmodifyer',json["Portlet.lastmodifyer"]);
		record.set('lastmodifytime',json["Portlet.lastmodifytime"]);
	}
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  

          

function _copyCreateCommunity()
{
	_getMainFrame().maintabs.addPanel('复制创建社区管理','',contextPath + '/platform/basicApp/sysConsole/portlet/communityController.spr?action=_copyCreate&communityid='+communityid);
}
          

/**
 * 删除社区管理
 */
function _deleteCommunity()
{
	_getMainFrame().Ext.MessageBox.show({
		title:'系统提示',
		msg: '您选择了【社区管理删除】操作，是否确定继续该操作？',
		buttons: {yes:'确定', no:'取消'},
		icon: Ext.MessageBox.QUESTION,
		fn:function(result){
			if (result == 'yes'){
				var param = '&communityid='+communityid;
				new AjaxEngine(contextPath + '/platform/basicApp/sysConsole/portlet/communityController.spr?action=_delete', 
				{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
			}
		 }
	});

}

/**
 * 删除成功后的回调动作
 */
function _deleteCallBackHandle()
{
	_getMainFrame().showInfo('操作成功！       ');
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

          
/**
 * 保存 
 */
function _saveOrUpdateCommunity()
{
	var param = Form.serialize('mainForm');	
	param = param + ""+ getPortletGridData();
	new AjaxEngine(contextPath + '/platform/basicApp/sysConsole/portlet/communityController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	//var id = responseUtil.getCustomField('coustom');
	var communityid=result.communityid;
	//document.getElementById("Community.communityid").value = id;
	document.getElementById("Community.creator_text").value = result.creator_text;
	document.getElementById("Community.creator").value = result.creator;
	document.getElementById("Community.createtime").value = result.createtime;
	document.getElementById("Community.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("Community.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("Community.lastmodifytime").value = result.lastmodifytime;
	reload_Portlet_grid("defaultCondition=YPORTLET.COMMUNITYID='"+ communityid +"'");
}
          
/**
 * 取消
 */
function _cancelCommunity()
{
	new AjaxEngine(contextPath + '/platform/basicApp/sysConsole/portlet/communityController.spr?action=_cancel&communityid='+communityid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelCommunityCallBack});
}
function _cancelCommunityCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 创建按钮调用方法 
 * 新增社区管理
 */
function _createCommunity()
{
	//增加页签
	_getMainFrame().maintabs.addPanel('社区管理新增','',contextPath + '/platform/basicApp/sysConsole/portlet/communityController.spr?action=_create');
}
