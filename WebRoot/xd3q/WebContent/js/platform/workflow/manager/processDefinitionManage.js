/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年11月04日 11点00分35秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>流程定义(ProcessDefinition)管理页面JS文件
 */


/**
 * 查询
 */
function _search()
{
	var para = Form.serialize('mainForm');
	reload_ProcessDefinition_grid(para);
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_ProcessDefinition_grid("");
}

/**
 * 清空操作
 */
function _resetForm()
{
	document.all.mainForm.reset();
}


/**
 * grid 上的 创建按钮调用方法 
 * 新增流程定义
 */
function _create()
{
   var para = "";
	//增加页签
	_getMainFrame().maintabs.addPanel('流程定义新增',ProcessDefinition_grid,contextPath + '/platform/workflow/manager/processDefinitionController.spr?action=_create'+ para);
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除流程定义
 */
function _deletes()
{
	if (ProcessDefinition_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '您选择了【流程定义批量删除】操作，是否确定继续该操作？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
                   if(result == 'yes'){
		                var records = ProcessDefinition_grid.selModel.getSelections();
		                var ids = '';           
		                for(var i=0;i<records.length;i++){
		                    ids = ids + records[i].json.processdefid + '|';
		                }
		
		                var param = '&processDefinitionIds='+ids;
		                new AjaxEngine(contextPath + '/platform/workflow/manager/processDefinitionController.spr?action=_deletes', 
		                {method:"post", parameters: param, onComplete: callBackHandle});
		            }
                }
		});
	}else{
		_getMainFrame().showInfo('请选择需要进行【流程定义批量删除】操作的记录！');
	}
}
