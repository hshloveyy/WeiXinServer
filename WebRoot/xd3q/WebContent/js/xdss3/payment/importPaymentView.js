/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年05月27日 19点57分45秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象进口付款(ImportPayment)查看页面JS文件
 */

          
   
    
          
   
    

    
  


/**
  * 进口付款行项目查看操作
  */
function _previewPaymentCgoods(id,url)
{
	return true ;
}

/**
 * 进口付款行项目查看操作
 */
function _postviewPaymentCgoods(id, url) {

}

/**
 * 进口付款行项目编辑操作
 */
function _preeditPaymentCgoods(id, url) {
	return true;
}

/**
 * 进口付款行项目编辑操作
 */
function _posteditPaymentCgoods(id, url) {

}

function _simulateImportPayment() {
}

function _submitForReassignImportPayment() {
		var param = Form.serialize('mainForm');	
		param = param + "&" + getAllImportPaymentItemGridData();
		param = param + "&" + getAllImportPaymentCbillGridData();
		param = param + "&" + getAllImportPayBankItemGridData();
		param = param + "&" + getAllImportDocuBankItemGridData();
		param = param + "&" + Form.serialize('importSettSubjForm');		
		param = param + "&" + Form.serialize('importFundFlowForm');		
		param = param + "&" + getAllImportRelatPaymentGridData();
		param = param + "&"+ Form.serialize('workflowForm');
		
		new AjaxEngine(contextPath +'/xdss3/payment/importPaymentController.spr?action=_submitForReassign', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
}

/**
 * grid 上的 创建按钮调用方法 新增进口付款,付款银行
 */
function _precreatePayBankItem() {
	return true;
}

/**
 * grid 上的 创建按钮调用方法 新增进口付款,付款银行
 */
function _postcreatePayBankItem() {

}

/**
 * 进口付款行项目 复制创建
 */
function _precopyCreatePayBankItem() {
	return true;
}

/**
 * 进口付款行项目 复制创建
 */
function _postcopyCreatePayBankItem() {

}

/**
 * 进口付款行项目查看操作
 */
function _previewPayBankItem(id, url) {
	return true;
}

/**
 * 进口付款行项目查看操作
 */
function _postviewPayBankItem(id, url) {

}

/**
 * 进口付款行项目编辑操作
 */
function _preeditPayBankItem(id, url) {
	return true;
}

/**
 * 进口付款行项目编辑操作
 */
function _posteditPayBankItem(id, url) {

}

/**
 * 删除进口付款
 */
function _predeleteImportPayment() {
	return true;
}

/**
 * 删除进口付款
 */
function _postdeleteImportPayment() {

}

/**
 * 创建按钮调用方法 新增进口付款
 */
function _precreateImportPayment() {
	return true;
}

/**
 * 创建按钮调用方法 新增进口付款
 */
function _postcreateImportPayment() {

}

/**
 * 保存
 */
function _presaveOrUpdateImportPayment() {
	return true;
}

/**
 * 保存
 */
function _postsaveOrUpdateImportPayment() {

}

/**
 * 取消
 */
function _precancelImportPayment() {
	return false;
}

/**
 * 取消
 */
function _postcancelImportPayment() {
	new AjaxEngine(
			contextPath
					+ '/xdss3/payment/importPaymentController.spr?action=_cancel&paymentid='
					+ paymentid, {
				method : "post",
				parameters : '',
				onComplete : callBackHandle,
				callback : cancelImportPaymentCallBack
			});
}

function cancelImportPaymentCallBack() {
	if (_getMainFrame().ExtModalWindowUtil.getActiveExtWin()) {
		_getMainFrame().ExtModalWindowUtil.close();
	} else {
		_getMainFrame().maintabs
				.remove(_getMainFrame().maintabs.getActiveTab());
	}
}

/**
 * 提交 ------------------------------ 修改记录 ------------------------------ 邱杰烜
 * 2010-09-09 进口付款流程中【资金部出纳付款】节点点击"确认"， 或者【资金部出纳付押汇到期款】节点点击"提交机要室"后，
 * 将当前人保存到付款人字段
 */
function _presubmitProcessImportPayment(id) {
	/**
	 * 进口付款流程中【资金部出纳付款】节点点击"确认"， 或者【资金部出纳付押汇到期款】节点点击"提交机要室"后， 将当前人保存到付款人字段
	 */
	/** ******************************************************************** */
	var cs = Ext.getDom('ImportPayment.processstate').value; // 当前节点状态
	var ns = Ext.getDom('workflowLeaveTransitionName').value; // 下一步操作
	var ca = Ext.getDom('taskInstanceContext.currentActor').value; // 当前办理人
	if ((cs == '资金部出纳付款' && ns == '确认')
			|| (cs == '资金部出纳付押汇到期款' && ns == '提交机要室')||
			((cs == '出纳付款'||cs == '出纳付押汇到期款') && ns == '提交财务经理确认')) {
		Ext.getDom('ImportPayment.payer').value = ca;
	}
	/** ******************************************************************** */
	return true;
}

/**
 * 提交
 */
function _postsubmitProcessImportPayment(id) {
}

/**
 * 查看信用额度
 */
function _viewCreditImportPayment() {
	if (pre_viewCreditImportPayment()) {
		// 方法执行体
	}
	post_viewCreditImportPayment();
}

/**
 * 查看信用额度
 */
function pre_viewCreditImportPayment() {
	return true;
}

/**
 * 查看信用额度
 */
function post_viewCreditImportPayment() {

}

/**
 * 增加采购合同
 * 
 * @return
 */
function _addPurchaseImportPaymentItem() {
}

/**
 * 增加到单
 * 
 * @return
 */
function _addPlickImportPaymentItem() {

}

/**
 * 增加立项
 * 
 * @return
 */
function _addProjectImportPaymentItem() {

}

/**
 * 增加外部纸质合同号
 * 
 * @return
 */
function _addPageContractImportPaymentItem() {

}

/**
 * 增加发票
 * 
 * @return
 */
function _addbillImportPaymentCbill() {

}

/**
 * 增加银行
 * 
 * @return
 */
function _addBankImportPayBankItem() {

}

/**
 * 自动分配
 * 
 * @return
 */
function _autoassignImportPayment() {

}

/**
 * 清除分配
 * 
 * @return
 */
function _clearassignImportPayment() {

}

/**
 * 现金日记帐
 * 
 * @return
 */
function _bookofaccountImportPayment() {

}

/**
 * 打印
 */
function  _printImportPayment(){
    window.open(contextPath +'/xdss3/payment/importPaymentController.spr?action=_print&paymentId='+document.getElementById('ImportPayment.paymentid').value,'_blank','location=no,resizable=yes');
}
function  _printImportPayment1(){
    window.open(contextPath +'/xdss3/payment/importPaymentController.spr?action=_print&printType=1&paymentId='+document.getElementById('ImportPayment.paymentid').value,'_blank','location=no,resizable=yes');
}
function  _printImportPayment2(){
    window.open(contextPath +'/xdss3/payment/importPaymentController.spr?action=_print&printType=2&paymentId='+document.getElementById('ImportPayment.paymentid').value,'_blank','location=no,resizable=yes');
}
function  _printImportPayment3(){
    window.open(contextPath +'/xdss3/payment/importPaymentController.spr?action=_print&printType=3&paymentId='+document.getElementById('ImportPayment.paymentid').value,'_blank','location=no,resizable=yes');
}

Ext.onReady(function(){
//	var tb = Ext.getCmp('tabs');	
//	tb.hideTabStripItem('attachementTab');	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-08-31
	 * 在查看页面时直接隐藏以下按钮
	 * ------------------------------ 修改记录 ------------------------------
	 * 邱杰烜 2010-09-13 若为业务人员，要把打印按钮隐藏
	 */
	/***********************************************************************/    
	Ext.getCmp('_autoassign').hide();				// 隐藏（按钮）自动分配
	Ext.getCmp('_clearassign').hide();				// 隐藏（按钮）清除分配
	Ext.getCmp('_viewCredit').hide();				// 隐藏（按钮）查看信用额度
	Ext.getCmp('_bookofaccount').hide();			// 隐藏（按钮）现金日记帐
	Ext.getCmp('_simulate').hide();					// 隐藏（按钮）模拟凭证
	//
	if(isReassign!='Y'){
	   Ext.getCmp('_submitForReassign').hide();		// 隐藏（按钮）重分配提交
	}
	else {
	   Ext.getCmp('_submitProcess').setDisabled(true);	// 禁用（按钮）提交
	}
	// 若为业务人员，要把打印按钮隐藏
	if(strRoleType=="1"){
		//Ext.getCmp('_print').hide();				// 隐藏（按钮）打印
	}
	/***********************************************************************/   
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-08
	 * 申请界面时，直接将押汇币别、押汇日、押汇利率、押汇汇率、押汇到期付款日等设为不可填
	 * （备注：等虎哥修改完日期控制后恢复以下三条注释）
	 */
	/***********************************************************************/
//	calendar_ImportPayment_documentarydate.setEditable(false);		// 押汇日（日期型）
	Ext.getDom('ImportPayment.doctaryinterest').readOnly = true;	// 押汇利率
	Ext.getDom('ImportPayment.documentaryrate').readOnly = true;	// 押汇汇率
//	calendar_ImportPayment_documentarypaydt.setEditable(false);		// 押汇到期付款日（日期型）
//	calendar_ImportPayment_payrealdate.setEditable(false);			// 实际付款日期
//	Ext.getDom('ImportPayment.redocaryamount').readOnly = true;		// 还押汇金额
	Ext.getDom('ImportPayment.redocaryrate').readOnly = true;		// 还押汇汇率
	/***********************************************************************/
	

	
	
});    

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
 * 到单详细信息查看xx
 */
function viewPickListInfo(pickNo){
	var pickUrl = contextPath + '/pickListInfoController.spr?action=viewPickListInfo&pickListId=' + pickNo;
	if(pickNo.trim()!=''){
		_getMainFrame().ExtModalWindowUtil.show('到单信息', pickUrl, '', '', {width:700,height:600});
	}
}
/*
 * 相关单据信息查看 
 */
function viewRelatedInfo(relaNo){
	var relaUrl = contextPath + '/xdss3/payment/importPaymentController.spr?action=viewRelatedInfo&relatedNo=' + relaNo;
	if(relaNo.trim()!=''){
		_getMainFrame().maintabs.addPanel('原内贸付款单信息','',relaUrl);
		   
	}
}

/**
 *  增加还押汇银行
 */
function _addReBankBillPayReBankItem()
{


}

function _deletesBillPayReBankItem()
{
	
}