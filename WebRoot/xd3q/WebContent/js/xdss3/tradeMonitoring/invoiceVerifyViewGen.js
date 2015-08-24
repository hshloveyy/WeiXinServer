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
function _viewInvoiceVerifyItem(id,url)
{
	if(_previewInvoiceVerifyItem()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = InvoiceVerifyItem_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.invoiceVerifyItem_View,	url,'','',{width:660,height:300});
	}
	_postviewInvoiceVerifyItem();
}
/**
  * 发票校验行项目查看，打开到页签 ,回调函数
  */
function _viewInvoiceVerifyItempCallBack()
{
}

/**
  * 发票校验行项目查看，弹出窗口 ,回调函数
  */
function _viewInvoiceVerifyItemmCallBack()
{
}

    
    
    

  

          

/**
 * 取消
 */
function _cancelInvoiceVerify()
{
  if(_precancelInvoiceVerify()){
	new AjaxEngine(contextPath + '/xdss3/tradeMonitoring/invoiceVerifyController.spr?action=_cancel&maincode='+maincode, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelInvoiceVerifyCallBack});
   }
   _postcancelInvoiceVerify();
}

function _cancelInvoiceVerifyCallBack()
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
