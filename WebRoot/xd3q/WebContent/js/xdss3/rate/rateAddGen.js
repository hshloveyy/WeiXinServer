/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年11月25日 11点42分18秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象利率(Rate)增加页面JS文件
 */


          
/**
 * 保存 
 */
function _saveOrUpdateRate()
{					 
    if(isCreateCopy){
		document.getElementById("Rate.rateid").value = "";
	}
	if(_presaveOrUpdateRate()){
		var param = Form.serialize('mainForm');	
	    new AjaxEngine(contextPath + '/xdss3/rate/rateController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateRate();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var rateid=result.rateid;
	document.getElementById("Rate.rateid").value = rateid;
	
	document.getElementById("Rate.creator").value = result.creator;
	document.getElementById("Rate.createtime").value = result.createtime;
	document.getElementById("Rate.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("Rate.lastmodifytime").value = result.lastmodifytime;
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
          
