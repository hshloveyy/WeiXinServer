/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月04日 13点22分32秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象预算分类(BudgetSort)增加页面JS文件
 */

             
  
    


function _editBudgetTemplatepCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_modifyBudgetTemplateRow(json);
}


function _editBudgetTemplatemCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyBudgetTemplateRow(json);
}

/**
  * 预算分类行项目编辑操作
  */
function _editBudgetTemplate(id,url)
{
	if(_preeditBudgetTemplate()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = BudgetTemplate_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.budgetTemplate_Edit,	url,'',_editBudgetTemplateCallBack,{width:660,height:300});
      
      }
      _posteditBudgetTemplate();
}
function _editBudgetTemplateCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyBudgetTemplateRow(json);
}

/**
 * 行项目编辑后的grid更新
 */
function _modifyBudgetTemplateRow(json)
{
	if(json){
		var records = BudgetTemplate_grid.selModel.getSelections();
		var record = records[0];
		record.set('budtemname',json["BudgetTemplate.budtemname"]);
		record.set('budtemtype',json["BudgetTemplate.budtemtype"]);
		record.set('boid',json["BudgetTemplate.boid"]);
		record.set('budconcycle',json["BudgetTemplate.budconcycle"]);
		record.set('budsortid',json["BudgetTemplate.budsortid"]);
		record.set('creator',json["BudgetTemplate.creator"]);
		record.set('createtime',json["BudgetTemplate.createtime"]);
		record.set('lastmodifyer',json["BudgetTemplate.lastmodifyer"]);
		record.set('lastmodifytime',json["BudgetTemplate.lastmodifytime"]);
	}
}
/**
  * 预算分类行项目查看操作
  */
function _viewBudgetTemplate(id,url)
{
	if(_previewBudgetTemplate()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = BudgetTemplate_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.budgetTemplate_View,	url,'','',{width:660,height:300});
	}
	_postviewBudgetTemplate();
}
/**
  * 预算分类行项目查看，打开到页签 ,回调函数
  */
function _viewBudgetTemplatepCallBack()
{
}

/**
  * 预算分类行项目查看，弹出窗口 ,回调函数
  */
function _viewBudgetTemplatemCallBack()
{
}

    
  
          
          
          
          
/**
 * 保存 
 */
function _save()
{
	//if(isCreateCopy){
	//	document.getElementById("BudgetSort.budsortid").value = "";
	//}
	if(_presave()){
		var param = Form.serialize('mainForm');	
		           
			        	         
		if(isCreateCopy)
		{
			param = param + ""+ getAllBudgetTemplateGridData();
		}
		else
		{
			param = param + ""+ getBudgetTemplateGridData(); 
		}
			        		    new AjaxEngine(contextPath + '/XDSS/budget/maindata/BudgetSort/budgetSortController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}
	_postsave();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var budsortid=result.budsortid;
	document.getElementById("BudgetSort.budsortid").value = budsortid;
	document.getElementById("BudgetSort.creator_text").value = result.creator_text;
	document.getElementById("BudgetSort.creator").value = result.creator;
	document.getElementById("BudgetSort.createtime").value = result.createtime;
	document.getElementById("BudgetSort.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("BudgetSort.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("BudgetSort.lastmodifytime").value = result.lastmodifytime;
	isCreateCopy = false;	
	reload_BudgetTemplate_grid("defaultCondition=YBUDTEM.BUDSORTID='"+ budsortid +"'");

}
          

/**
 * 取消
 */
function _cancel()
{
	if(_precancel()){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	 }
	 _postcancel();
}
          

