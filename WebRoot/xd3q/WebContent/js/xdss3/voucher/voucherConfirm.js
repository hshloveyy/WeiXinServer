/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月02日 08点03分16秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象凭证预览(Voucher)编辑页面用户可编程扩展JS文件
 */
 
          
   
/**
 *凭证预览行项目
 *批量删除
 */
function _predeletesVoucherItem()
{
	return true ;
}

/**
 *凭证预览行项目
 *批量删除
 */
function _postdeletesVoucherItem()
{

}
    

function _confirmVoucher()
{
	var param = Form.serialize('mainForm');	
	alert(param);
    param = param + ""+ getAllVoucherItemGridData();

    new AjaxEngine(contextPath+'/xdss3/voucher/voucherController.spr?action=confirmVoucher', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
		   
}  
  

function customCallBackHandle()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  

          

function _precopyCreateVoucher()
{
	return true ;
}

function _postcopyCreateVoucher()
{

}
          

/**
 * 删除凭证预览
 */
function _predeleteVoucher()
{
	return true ;
}

/**
 * 删除凭证预览
 */
function _postdeleteVoucher()
{

}
          

/**
 * 创建按钮调用方法 
 * 新增凭证预览
 */
function _precreateVoucher()
{
	return true ;
}

/**
 * 创建按钮调用方法 
 * 新增凭证预览
 */
function _postcreateVoucher()
{

}
          
/**
 * 保存 
 */
function _presaveOrUpdateVoucher()
{
	return true ;
}

/**
 * 保存 
 */
function _postsaveOrUpdateVoucher()
{

}
          
/**
 * 取消
 */
function _precancelVoucher()
{
	return true ;
}

/**
 * 取消
 */
function _postcancelVoucher()
{

}
