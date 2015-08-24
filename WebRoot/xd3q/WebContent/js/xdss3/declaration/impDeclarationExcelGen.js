/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年06月23日 17点57分24秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象进口报关单(ImpDeclaration)增加页面JS文件
 */


          
          
          
          
/**
 * 保存 
 */
function _saveOrUpdateImpDeclaration()
{					 
    if(isCreateCopy){
		document.getElementById("ImpDeclaration.declarationsid").value = "";
	}
	if(_presaveOrUpdateImpDeclaration()){
		var param = Form.serialize('mainForm');	
	    new AjaxEngine(contextPath + '/xdss3/declaration/impDeclarationController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateImpDeclaration();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var declarationsid=result.declarationsid;
	document.getElementById("ImpDeclaration.declarationsid").value = declarationsid;
	
	isCreateCopy = false;	

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
          
