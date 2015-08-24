/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月14日 16点59分08秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象供应商单清帐(SupplierSinglClear)查看页面JS文件
 */
          
   

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   
    
    
    
  

    
          
   

    
  

          

/**
 * 取消
 */
function _cancelSupplierSinglClear()
{
  if(_precancelSupplierSinglClear()){
	new AjaxEngine(contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=_cancel&suppliersclearid='+suppliersclearid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelSupplierSinglClearCallBack});
   }
   _postcancelSupplierSinglClear();
}

function _cancelSupplierSinglClearCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
          
          
          
          
          
/**
 * 保存 
 */
function _saveOrUpdateSupplierSinglClear()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("SupplierSinglClear.suppliersclearid").value = id;	
}
          
