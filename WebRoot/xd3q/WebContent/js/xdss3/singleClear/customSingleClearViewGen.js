/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月19日 11点44分18秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象客户单清帐(CustomSingleClear)查看页面JS文件
 */
          
   

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   

    
          
   
    
    
    
  

    
  

          
          
/**
 * 保存 
 */
function _saveOrUpdateCustomSingleClear()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("CustomSingleClear.customsclearid").value = id;	
}
          

/**
 * 取消
 */
function _cancelCustomSingleClear()
{
  if(_precancelCustomSingleClear()){
	new AjaxEngine(contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=_cancel&customsclearid='+customsclearid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelCustomSingleClearCallBack});
   }
   _postcancelCustomSingleClear();
}

function _cancelCustomSingleClearCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
          
          
          
          
