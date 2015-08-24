/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年06月23日 17点57分24秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象进口报关单(ImpDeclaration)增加页面用户可编程扩展JS文件
 */

/**
 * 提交Excel 
 */
function _saveExcel()
{
	if(_presaveOrUpdateImpDeclaration()){
		var param = Form.serialize('mainForm');	
	    new AjaxEngine(contextPath + '/xdss3/declaration/impDeclarationController.spr?action=saveExcel', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_saveExcelCallBackHandle, enctype:"multipart/form-data"});
	}
}
/**
 * 提交Excel 操作成功后的回调动作
 */
function _saveExcelCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
}
          
/**
 * 保存 
 */
function _presaveOrUpdateImpDeclaration()
{
	return true ;
}

/**
 * 保存 
 */
function _postsaveOrUpdateImpDeclaration()
{
}

          

/**
 * 取消
 */
function _precancel()
{
	return true;
}

/**
 * 取消
 */
function _postcancel()
{

}
          
