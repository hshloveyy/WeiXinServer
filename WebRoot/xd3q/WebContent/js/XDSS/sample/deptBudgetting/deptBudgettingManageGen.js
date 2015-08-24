/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年04月12日 11点34分36秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象部门预算编制(DeptBudgetting)管理页面JS文件
 */

/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_DeptBudgetting_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_DeptBudgetting_grid("");
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
 * grid 上的 创建按钮调用方法 
 * 新增部门预算编制
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.deptBudgetting_Create,DeptBudgetting_grid,contextPath + '/XDSS/sample/deptBudgetting/deptBudgettingController.spr?action=_create'+ para);
    }
    _postcreate();
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
if(_precopyCreate()){
	if (DeptBudgetting_grid.selModel.hasSelection() > 0){
		var records = DeptBudgetting_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo(Txt.deptBudgetting_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}
		var deptbudgettingid = records[0].json.deptbudgettingid;
		_getMainFrame().maintabs.addPanel(Txt.deptBudgetting_CopyCreate,DeptBudgetting_grid,contextPath + '/XDSS/sample/deptBudgetting/deptBudgettingController.spr?action=_copyCreate&deptbudgettingid='+deptbudgettingid);
	}else{
		_getMainFrame().showInfo(Txt.deptBudgetting_CopyCreate_SelectToOperation);
	}	
  }
  _postcopyCreate();
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除部门预算编制
 */
function _deletes()
{
if(_precreate()){
	if (DeptBudgetting_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.deptBudgetting_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = DeptBudgetting_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.deptbudgettingid + '|';
						}
		
						var param = '&deptBudgettingIds='+ids;
						new AjaxEngine(contextPath + '/XDSS/sample/deptBudgetting/deptBudgettingController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo(Txt.deptBudgetting_Deletes_SelectToOperation);
	}
   }
  _postcreate();
}
