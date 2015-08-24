/**
  * Author(s):java业务平台代码生成工具
  * Date: 2012年06月08日 11点48分56秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象其他公司客户单清帐(CustomSingleOther)管理页面JS用户可编程扩展文件
 */



/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');

	
	var businessstate = document.getElementById("businessstate").value;
	
	reload_CustomSingleOther_grid(para);
	}
	_postsearch();
}

/**
 * 查询动作执行前
 */
function _presearch()
{
	
	
	return true;
}

/**
 * 查询动作执行后
 * 当 _presearch() 返回 false 时候则执行本函数。
 */
function _postsearch()
{

}


/**
 * 清空操作
 */
function _preresetForm()
{
	return true;
}
/**
 * 清空操作
 */
function _postresetForm()
{

}

/**
 * grid 上的超级链接, 删除按钮调用方法
 * 删除客户单清帐
 */
function _predeleteCustomSingleOther(id,url)
{
	var records = CustomSingleOther_grid.getSelectionModel().getSelections();        			
	for(i=0;i<records.size();i++)
    {
    	var customsclearid = records[i].get('customsclearid');    	
		var businessstate = records[i].get('businessstate');
		if(customsclearid == id && (businessstate=='3' || businessstate=='-1')){
			_getMainFrame().showInfo("已经确定清账过，不能删除，只能作废！ ");
			return false;
		}
		
    }
  return true;
}

/**
 * grid 上的超级链接, 删除按钮调用方法
 * 删除客户单清帐
 */
function _postdeleteCustomSingleOther(id,url)
{

}
function _editCustomSingleOther(id,url){
	var records = CustomSingleOther_grid.getSelectionModel().getSelections();        			
	for(i=0;i<records.size();i++)
    {
    	var customsclearid = records[i].get('customsclearid');    	
		var businessstate = records[i].get('businessstate');
		if(customsclearid == id && (businessstate=='3' || businessstate=='-1')){
			_getMainFrame().showInfo("已经确定清账过，不能编辑，只能查看！ ");
			return false;
		}
		
    }
	url = url +"&customsclearid=" + id;
	_getMainFrame().maintabs.addPanel('客户单清账编辑', '', contextPath + "/" + url);
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增客户单清帐
 */
function _precreate()
{
	 return true ;
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增客户单清帐
 */
function _postcreate()
{

}


Ext.onReady(function(){
	 
	
});

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	var para = Form.serialize('mainForm');
	para +="&businessstate.fieldName=YCUSTOMCLEAROTHE.BUSINESSSTATE";
	para +="&businessstate.dataType=S";
	para +="&businessstate.option=notequal";
	para +="&businessstate.fieldValue=3";	
	reload_CustomSingleOther_grid(para);
}

/**
 * grid 上的超级链接, 删除按钮调用方法
 * 删除客户单清帐
 */
function _deleteCustomSingleOther(id,url)
{
  if(_predeleteCustomSingleOther(id,url)){
	    url = url + '&customsclearid=' + id;
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.CustomSingleOther_Delete_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
				   if (result == 'yes'){
						var param = '';
						new AjaxEngine(contextPath + "/" + url,{method:"post", parameters: [], onComplete: callBackHandle});
					}
				}
		});
	}
	_postdeleteCustomSingleOther();
}
