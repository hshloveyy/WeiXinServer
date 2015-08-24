/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月06日 10点45分08秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象未明户收款(UnnamerCollect)查看页面JS文件
 */

/**
 * 凭证预览
 * @return
 */
function _previewVoucherUnnamerCollect(){
	// 在点击凭证预览时验证
	if(!checkOnSubmit()){
		return;
	}else if(!Utils.isNumber(Ext.getDom('UnnamerCollect.applyamount').value)){
		showInfo('[申请收款金额]必须是数字！');
		return;
	}else if(!Utils.isNumber(Ext.getDom('UnnamerCollect.applyamount2').value)){
		showInfo('[申请收款金额（本位币）]必须是数字！');
		return;
	}else if(!Utils.isNumber(Ext.getDom('UnnamerCollect.collectrate').value)){
		showInfo('[收款汇率]必须是数字！');
		return;
	}
	
	var param = Form.serialize('mainForm');
	new AjaxEngine(
			contextPath + '/xdss3/unnameCollect/unnamerCollectController.spr?action=_previewVoucher',
			{
				method :"post",
				parameters :param,
				onComplete :vouchercallBackHandle
			});
}

function vouchercallBackHandle(transport){
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	if(transport.responseText){
		Ext.get('currentWorkflowTask').mask();
		Ext.get('centercontent').mask();
		_getMainFrame().maintabs.addPanel('模拟凭证','', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('UnnamerCollect.unnamercollectid').dom.value,closeVoucherCallBack,true);
	}
}

function closeVoucherCallBack(flag){
	Ext.get('currentWorkflowTask').unmask();
	Ext.get('centercontent').unmask();
	
	if(flag)_submitProcessUnnamerCollect(flag);
}
          
/**
 * 保存 
 */
function _presaveOrUpdateUnnamerCollect(){
	return true;
}

/**
 * 保存 
 */
function _postsaveOrUpdateUnnamerCollect(){}
          
/**
 * 取消
 */
function _precancelUnnamerCollect(){
	return true ;
}

/**
 * 取消
 */
function _postcancelUnnamerCollect(){}
          

/**
 * 提交
 */
function _presubmitProcessUnnamerCollect(id){
	if(Utils.isEmpty(Ext.getDom('workflowExamine').value)){
		_getMainFrame().showInfo('[审批意见]不能为空！');
		return false;
	}
	return true;
}

/**
 * 提交
 */
function _postsubmitProcessUnnamerCollect(){}

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-08-17
 * 所有日期控件的控制先注释掉，等日期控制的setEditable可用时再释放
 * --------------------------------- 修改记录 ----------------------------------
 * 邱杰烜 2010-09-07 去除前台现金流量项的设置，直接在后台设置
 */
/******************************************************************************/
var ps = Ext.getDom('UnnamerCollect.processstate').value;
Ext.onReady(function(){
	
	// 现金日记账按钮只在"出纳修改"出现
	if(ps!='出纳修改'){
		Ext.getCmp('_cashJournal').hide();
	}
	// "记账日期"只能在财务节点编辑
//	calendar_UnnamerCollect_accountdate.setEditable(false);
	Ext.getDom('_previewVoucher').hide();
	// 设置收款银行不可编辑 
	Ext.getDom('UnnamerCollect.collectbankname').readOnly = true;
	
	var applyAmount = Ext.getDom('UnnamerCollect.applyamount');			// 收款金额
	var collectRate = Ext.getDom('UnnamerCollect.collectrate');			// 收款汇率
	var applyAmount2= Ext.getDom('UnnamerCollect.applyamount2');	// 收款金额（本位币）
	/*
	 * 改变申请收款金额时, 变更申请收款金额(本位币)
	 */
	applyAmount.onblur = function(){
		if(Utils.isNumber(applyAmount.value) && Utils.isNumber(collectRate.value)){
			applyAmount2.value = round((parseFloat(applyAmount.value * collectRate.value)),2);
		}
	}
	/*
	 * 改变收款汇率时，自动计算"收款金额（本位币） = 收款金额 * 收款汇率"
	 */
	collectRate.onblur = function(){
		if(Utils.isNumber(applyAmount.value) && Utils.isNumber(collectRate.value)){
			applyAmount2.value = round((parseFloat(applyAmount.value * collectRate.value)),2);
		}
	}
	/*
	 * 根据币别设置汇率与本位币
	 */
//	div_currency_sh_sh.on('select',function(e,n,o){
//		if(div_currency_sh_sh.getValue()=='CNY'){
//			applyAmount2.value = applyAmount.value;
//			collectRate.value = 1;
//		}else{
//			applyAmount2.value = '';
//			collectRate.value = '';
//		}
//	});
});     
/******************************************************************************/

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-08-19
 * --------------------------------- 修改记录 ----------------------------------
 * 邱杰烜 2010-09-20 需要做凭证生成的节点不允许使用提交按钮
 */
function checkOnSubmit(){
	if(Ext.getDom('UnnamerCollect.applyamount').value==''){
		showInfo('[申请收款金额]不能为空！');
		return false;
	}else if(div_currency_sh_sh.getValue()==''){
		showInfo('[币别]不能为空！');
		return false;
	}else if(Ext.getDom('UnnamerCollect.collectbankname').value==''){
		showInfo('[收款银行]不能为空！');
		return false;
	}else if(div_collcetbankacc_sh_sh.getValue()==''){
		showInfo('[收款银行账号]不能为空！');
		return false;
	}else if(div_deptid_sh_sh.getValue()==''){
		showInfo('[申请部门]不能为空！');
		return false;
	}else if(Ext.getDom('UnnamerCollect.text').value==''){
		showInfo('[抬头文本]不能为空！');
		return false;
	}else if(Ext.getDom('UnnamerCollect.voucherdate').value==''){
		showInfo('[凭证日期]不能为空！');
		return false;
	}else if(Ext.getDom('UnnamerCollect.accountdate').value==''){
		showInfo('[记账日期]不能为空！');
		return false;
	}else if(Ext.getDom('UnnamerCollect.applyamount2').value==''){
		showInfo('[申请收款金额（本位币）]不能为空！');
		return false;
	}else if(Ext.getDom('UnnamerCollect.collectrate').value==''){
		showInfo('[收款汇率]不能为空！');
		return false;
	}else if(div_cashflowitem_sh_sh.getValue()==''){
		showInfo('[现金流量项]不能为空！');
		return false;
	}else{
		return true;
	}
}