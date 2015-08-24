/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年11月02日 15点51分19秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象资金占用调整(FundAdjustment)编辑页面用户可编程扩展JS文件
 */
 
Ext.onReady(function(){
	
});


/**
 * 删除资金占用调整
 */
function _predeleteFundAdjustment()
{
	return true ;
}

/**
 * 删除资金占用调整
 */
function _postdeleteFundAdjustment()
{

}
          

/**
 * 创建按钮调用方法 
 * 新增资金占用调整
 */
function _precreateFundAdjustment()
{
	return true ;
}

/**
 * 创建按钮调用方法 
 * 新增资金占用调整
 */
function _postcreateFundAdjustment()
{

}
          
/**
 * 保存 
 */
function _presaveOrUpdateFundAdjustment()
{
	//	P 收款
//	S 付款
	var amount = Ext.getDom('FundAdjustment.amount').value;
	if (amount=='') {
			_getMainFrame().showInfo('请填写金额！');
		return false;
	}
	if ( amount >= 0 ) {
		Ext.getDom('FundAdjustment.fundflag').value = 'S';
		if (div_supplier_sh_sh.getValue() == '') {
			_getMainFrame().showInfo('请填写供应商！');
			return false;
		}
	} else {
		Ext.getDom('FundAdjustment.fundflag').value = 'P';
		if (div_customer_sh_sh.getValue() == '') {
			_getMainFrame().showInfo('请填写客户！');
			return false;
		}
	}
	
	return true ;
}

/**
 * 保存 
 */
function _postsaveOrUpdateFundAdjustment()
{

}
          
/**
 * 取消
 */
function _precancelFundAdjustment()
{
	return true ;
}

/**
 * 取消
 */
function _postcancelFundAdjustment()
{

}
          
