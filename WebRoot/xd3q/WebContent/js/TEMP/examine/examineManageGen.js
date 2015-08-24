/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年12月25日 08点58分59秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象检验信息(Examine)管理页面JS文件
 */


/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
 
    var certificatedateMax = $('certificatedate_maxValue').value;
    var certificatedateMin = $('certificatedate_minValue').value;
    if(certificatedateMax && certificatedateMin)
    {
	    if(certificatedateMax<certificatedateMin)
	    {
           _getMainFrame().showInfo(Txt.certificatedate_EndDateShouldLargerStartDate);
	       return;
	    }
    }
    
	reload_Examine_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_Examine_grid("");
}

/**
 * 清空操作
 */
function _resetForm()
{
  if(_preresetForm()){
		document.all.mainForm.reset();
	}
	_postresetForm();
}

/**
 * grid 上的超级链接, 删除按钮调用方法
 * 删除检验信息
 */
function _deleteExamine(id,url)
{
  if(_predeleteExamine()){
	    url = url + '&vi=' + id;
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.examine_Delete_ConfirmOperation,
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
	_postdeleteExamine();
}


/**
 * grid 上的 创建按钮调用方法 
 * 新增检验信息
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.examine_Create,Examine_grid,contextPath + '/TEMP/examine/examineController.spr?action=_create'+ para);
    }
    _postcreate();
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
if(_precopyCreate()){
	if (Examine_grid.selModel.hasSelection() > 0){
		var records = Examine_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo(Txt.examine_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}
		var vi = records[0].json.vi;
		_getMainFrame().maintabs.addPanel(Txt.examine_CopyCreate,Examine_grid,contextPath + '/TEMP/examine/examineController.spr?action=_copyCreate&vi='+vi);
	}else{
		_getMainFrame().showInfo(Txt.examine_CopyCreate_SelectToOperation);
	}	
  }
  _postcopyCreate();
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除检验信息
 */
function _deletes()
{
if(_precreate()){
	if (Examine_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.examine_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = Examine_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.vi + '|';
						}
		
						var param = '&examineIds='+ids;
						new AjaxEngine(contextPath + '/TEMP/examine/examineController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo(Txt.examine_Deletes_SelectToOperation);
	}
   }
  _postcreate();
}
