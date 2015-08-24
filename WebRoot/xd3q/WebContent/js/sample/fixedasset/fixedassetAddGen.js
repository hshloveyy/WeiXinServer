/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年12月14日 11点16分13秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象固定资产(Fixedasset)增加页面JS文件
 */

          
          
          
          
/**
 * 保存 
 */
function _save()
{
	//if(isCreateCopy){
	//	document.getElementById("Fixedasset.anln1").value = "";
	//}
	if(_presave()){
		var param = Form.serialize('mainForm');	
	    new AjaxEngine(contextPath + '/sample/fixedasset/fixedassetController.spr?action=_saveOrUpdate', 
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
	var anln1=result.anln1;
	document.getElementById("Fixedasset.anln1").value = anln1;
	document.getElementById("Fixedasset.creator_text").value = result.creator_text;
	document.getElementById("Fixedasset.creator").value = result.creator;
	document.getElementById("Fixedasset.createtime").value = result.createtime;
	document.getElementById("Fixedasset.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("Fixedasset.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("Fixedasset.lastmodifytime").value = result.lastmodifytime;
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
          

