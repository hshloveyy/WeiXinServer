/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月06日 11点02分32秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象发票校验(InvoiceVerify)查看页面JS文件
 */

          
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    

/**
  * 发票校验行项目查看操作
  */
function _previewInvoiceVerifyItem(id,url)
{
	return true ;
}

/**
  * 发票校验行项目查看操作
  */
function _postviewInvoiceVerifyItem(id,url)
{

}
    
    
    
    
  

          

/**
 * 取消
 */
function _precancelInvoiceVerify()
{
	return false ;
}

/**
 * 取消
 */
function _postcancelInvoiceVerify()
{
    new AjaxEngine(contextPath + '/xdss3/tradeMonitoring/invoiceVerifyController.spr?action=_cancel&maincode='+maincode, 
               {method:"post", parameters: '', onComplete: callBackHandle,callback:cancelInvoiceVerifyCallBack});
}

function cancelInvoiceVerifyCallBack()
{
    if (_getMainFrame().ExtModalWindowUtil.getActiveExtWin())
    {
        _getMainFrame().ExtModalWindowUtil.close();
    }
    else
    {
        _getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
    }
}