/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月19日 10点44分13秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象内贸付款(HomePayment)查看页面JS文件
 */
/**
 * 增加采购合同
 */
function _addPurchaseHomePaymentItem() {
	// 方法执行体

}

/**
 * 增加外部纸合同
 */
function _addPageContractHomePaymentItem() {
	// 方法执行体

}

/**
 * 增加立项
 */
function _addProjectHomePaymentItem() {
	// 方法执行体

}

/**
 * 增加
 */
function _addbillHomePaymentCbill() {
	// 方法执行体

}

/**
 * 增加
 */
function _addBankHomePayBankItem() {
	// 方法执行体

}

/**
 * 增加
 */
function _addDocuBankHomeDocuBankItem() {
}
/**
 * grid 上的 创建按钮调用方法 新增内贸付款,付款相关单据
 */
function _precreateHomePaymentRelat() {
	return true;
}

/**
 * grid 上的 创建按钮调用方法 新增内贸付款,付款相关单据
 */
function _postcreateHomePaymentRelat() {

}

/**
 * 内贸付款行项目 复制创建
 */
function _precopyCreateHomePaymentRelat() {
	return true;
}

/**
 * 内贸付款行项目 复制创建
 */
function _postcopyCreateHomePaymentRelat() {

}

function _precopyCreateHomePayment() {
	return true;
}

function _postcopyCreateHomePayment() {

}

/**
 * 删除内贸付款
 */
function _predeleteHomePayment() {
	return true;
}

/**
 * 删除内贸付款
 */
function _postdeleteHomePayment() {

}

/**
 * 创建按钮调用方法 新增内贸付款
 */
function _precreateHomePayment() {
	return true;
}

/**
 * 创建按钮调用方法 新增内贸付款
 */
function _postcreateHomePayment() {

}

/**
 * 保存
 */
function _presaveOrUpdateHomePayment() {
	return true;
}

/**
 * 保存
 */
function _postsaveOrUpdateHomePayment() {

}

/**
 * 取消
 */
function _precancelHomePayment() {
	return false;
}

/**
 * 取消
 */
function _postcancelHomePayment()
{
    new AjaxEngine(contextPath + '/xdss3/payment/homePaymentController.spr?action=_cancel&paymentid='+paymentid, 
               {method:"post", parameters: '', onComplete: callBackHandle,callback:cancelHomePaymentCallBack});
  
}

function cancelHomePaymentCallBack()
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

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-09-09 内贸付款流程中【出纳审核】节点和【出纳确认】节点点击确认后将当前人保存到付款人字段
 */
/***********************************************************************/  
function _setPayer(){
	var cs = Ext.getDom('HomePayment.processstate').value;		// 当前节点状态
	var ns = Ext.getDom('workflowLeaveTransitionName').value;		// 下一步操作
	var ca = Ext.getDom('taskInstanceContext.currentActor').value;	// 当前办理人
	if((cs=='出纳审核'||cs=='出纳确认') && ns=='确认'){
		Ext.getDom('HomePayment.payer').value = ca;
	}
}
/***********************************************************************/

/**
 * 提交 ------------------------------ 修改记录 ------------------------------ 邱杰烜
 * 2010-09-09 添加"根据当前结点状态设置付款人"功能
 */
function _presubmitProcessHomePayment(id) {
	// 根据当前结点状态设置付款人
	_setPayer();
	return true;
}

/**
 * 提交
 */
function _postsubmitProcessHomePayment(id) {

}

/**
 * 自动分配
 */
function _autoassignHomePayment() {
	// 方法执行体

}

/**
 * 清除分配
 */
function _clearassignHomePayment() {
	// 方法执行体

}

/**
 * 现金日记帐
 */
function _bookofaccountHomePayment() {
	// 方法执行体

}

/**
 * 查看信用额度
 * 
 * @return
 */
function _viewCreditHomePayment() {

}

/**
 * 模拟凭证
 * 
 * @return
 */
function _simulateHomePayment() {

}

/**
 * 重分配提交
 * 
 * @return
 */
function _submitForReassignHomePayment() {

}

/**
 * 打印
 */
function  _printHomePayment(){
    window.open(contextPath +'/xdss3/payment/homePaymentController.spr?action=_print&paymentId='+document.getElementById('HomePayment.paymentid').value,'_blank','location=no,resizable=yes');
}
function  _printHomePayment1(){
    window.open(contextPath +'/xdss3/payment/homePaymentController.spr?action=_print&printType=1&paymentId='+document.getElementById('HomePayment.paymentid').value,'_blank','location=no,resizable=yes');
}
/*
 * 合同详细信息查看
 */ 
function viewContractInfo(contNo){
	var contUrl = contextPath + '/contractController.spr?action=viewPurchaseContract&contractno=' + contNo;
	if(contNo.trim()!=''){
		_getMainFrame().ExtModalWindowUtil.show('合同信息', contUrl, '', '', {width:700,height:600});
	}
}
/*
 * 立项详细信息查看
 */
function viewProjectInfo(projNo){
	var projUrl = contextPath + '/projectController.spr?action=modify&from=view&projectNo=' + projNo;
	if(projNo.trim()!=''){
		_getMainFrame().ExtModalWindowUtil.show('立项信息', projUrl, '', '', {width:700,height:600});
	}
}
/*
 * 相关单据信息查看 
 */
function viewRelatedInfo(relaNo){
	var relaUrl = contextPath + '/xdss3/payment/homePaymentController.spr?action=viewRelatedInfo&relatedNo=' + relaNo;
	if(relaNo.trim()!=''){
		_getMainFrame().maintabs.addPanel('原内贸付款单信息','',relaUrl);
		   
	}
}

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-09-13
 * 若为业务人员，要把打印按钮隐藏
 * ------------------------------ 修改记录 ------------------------------
 * 邱杰烜 2010-09-28 将付款金额分配的合同号与立项号渲染成链接形式，并在点击时弹出出详情查看窗口
 */
Ext.onReady(function(){
	// 若为业务人员，要把打印按钮隐藏
//	if(strRoleType=="1"){
	//Ext.getCmp('_print').hide();	// 隐藏（按钮）打印
//	}
//	var tb = Ext.getCmp('tabs');	
//	tb.hideTabStripItem('attachementTab');	
	/*
	 * 将付款金额分配的合同号与立项号渲染成链接形式，并在点击时弹出出详情查看窗口
	 */
	
	
});
