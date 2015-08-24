/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月27日 07点47分02秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象凭证预览(Voucher)查看页面JS文件
 */
          
   

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

  

          
          

/**
 * 取消
 */
function _cancelVoucher()
{
  if(_precancelVoucher()){
	new AjaxEngine(contextPath + '/xdss3/voucher/voucherController.spr?action=_cancel&voucherid='+voucherid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelVoucherCallBack});
   }
   _postcancelVoucher();
}

function _cancelVoucherCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
