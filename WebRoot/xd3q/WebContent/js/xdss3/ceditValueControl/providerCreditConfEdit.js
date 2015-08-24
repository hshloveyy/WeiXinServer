/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年05月20日 12点34分50秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象供应商信用额度配置(ProviderCreditConf)编辑页面用户可编程扩展JS文件
 */
 
          
   
/**
 * grid 上的 创建按钮调用方法 
 * 新增供应商信用额度配置,供应商授限立项配置
 */
function _precreateProviderCreditProj()
{
	return true ;
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增供应商信用额度配置,供应商授限立项配置
 */
function _postcreateProviderCreditProj()
{

}
   
    

    
/**
*供应商信用额度配置行项目
*复制创建
*/
function _precopyCreateProviderCreditProj()
{
	return true ;
}

/**
*供应商信用额度配置行项目
*复制创建
*/
function _postcopyCreateProviderCreditProj()
{

}
    

    
  


/**
  * 供应商信用额度配置行项目查看操作
  */
function _previewProviderCreditProj(id,url)
{
	return  true ;
}

/**
  * 供应商信用额度配置行项目查看操作
  */
function _postviewProviderCreditProj(id,url)
{

}
    
/**
  * 供应商信用额度配置行项目编辑操作
  */
function _preeditProviderCreditProj(id,url)
{
	return true ;
}

/**
  * 供应商信用额度配置行项目编辑操作
  */
function _posteditProviderCreditProj(id,url)
{

}

    
    
    
    
    
    
    
    
    
    
  

          
/**
 * 保存 
 */
function _presaveOrUpdateProviderCreditConf()
{
	return true ;
}



function _check_save(transport){
	var param = Form.serialize('mainForm');
	param = param + ""+ getProviderCreditProjGridData();
	new AjaxEngine(contextPath + '/xdss3/ceditValueControl/providerCreditConfController.spr?action=checkExists', 
	   {method:"post", parameters: param, onComplete: callBackHandle,callback:_check_save_callback});
}

function _check_save_callback(transport){
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var msgType = responseUtil.getMessageType();
	if (msgType == 'info' && responseUtil.getMessage() == "操作成功！" ){
		_saveOrUpdateProviderCreditConf();
	}
	var result = responseUtil.getCustomField("coustom");
	if (result!=null && result!='') {
		_getMainFrame().showInfo(result);
		return;
	}
}


/**
 * 保存 
 */
function _postsaveOrUpdateProviderCreditConf()
{

}
          
/**
 * 取消
 */
function _precancelProviderCreditConf()
{
	return true ;
}

/**
 * 取消
 */
function _postcancelProviderCreditConf()
{

}
