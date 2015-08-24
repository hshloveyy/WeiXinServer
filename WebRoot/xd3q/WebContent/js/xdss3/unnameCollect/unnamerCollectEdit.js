/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月06日 10点45分07秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象未明户收款(UnnamerCollect)编辑页面用户可编程扩展JS文件
 */
 
/**
 * 选则银行名称搜索帮助后的回调函数
 */
function bankNameShCallBack(jsonData){
	div_collcetbankacc_sh_sh.defaultCondition = "BANK_CODE='" +jsonData.BANK_CODE + "'";
} 

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

//现金日记账
function _cashJournalUnnamerCollect(){
	var param = Form.serialize('mainForm');
	new AjaxEngine(
			contextPath + '/xdss3/unnameCollect/unnamerCollectController.spr?action=cashJournal',
			{
				method :"post",
				parameters :param,
				onComplete :cashJournalcallBackHandle
			});
}

function cashJournalcallBackHandle(transport){
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	
	if(transport.responseText){
		//人民币 
		var journalType = '2';
		//外币
		if(div_currency_sh_sh.getValue()!='CNY'){
			journalType = '1';
		}
		_getMainFrame().maintabs.addPanel('现金日记账','', xjrj+'/xjrj/journal.do?method=preAdd&journalType='+journalType+'&bus_id='+Ext.get('UnnamerCollect.unnamercollectid').dom.value+'&bus_type=6&userName='+username+'&isFromXdss=1');
	}
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
function _presubmitProcessUnnamerCollect(){
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
 * 邱杰烜 2010-09-11 更改收款金额在change的计算功能（保留两位小数）
 * 邱杰烜 2010-09-11 添加收款汇率变更时自动计算收款金额（本位币）功能
 */
/******************************************************************************/
var ps = Ext.getDom('UnnamerCollect.processstate').value;
Ext.onReady(function(){
	
	// 现金日记账按钮只在"出纳修改"出现
	if(ps!='出纳修改'){
		Ext.getCmp('_cashJournal').hide();
	}
	// "记账日期"只能在财务节点编辑
	if(ps!='财务会计审核'){
//		calendar_UnnamerCollect_accountdate.Editable(false);
		Ext.getDom('_previewVoucher').hide();
	}else{
		Ext.getCmp('_submitProcess').setDisabled(true);				// 禁用（按钮）提交
		Ext.getDom('workflowLeaveTransitionName').onchange = function(){
			var ns = this.value;
			if(ns=='提交'){
				Ext.getCmp('_submitProcess').setDisabled(true);		// 禁用（按钮）提交
			}else{
				Ext.getCmp('_submitProcess').setDisabled(false);	// 启用（按钮）提交
			}
		}
	}
	// 设置收款银行不可编辑 
	Ext.getDom('UnnamerCollect.collectbankname').readOnly = true;
	
	var applyAmount = Ext.getDom('UnnamerCollect.applyamount');			// 收款金额
	var collectRate = Ext.getDom('UnnamerCollect.collectrate');			// 收款汇率
	var applyAmount2= Ext.getDom('UnnamerCollect.applyamount2');	// 收款金额（本位币）
	/*
	 * 改变申请收款金额时, 变更申请收款金额(本位币)
	 */
	applyAmount.onblur = function(){
		if(div_currency_sh_sh.getValue()=='CNY'){  
			collectRate.value = 1;
			applyAmount.value = round(parseFloat(applyAmount.value),2);
			applyAmount2.value = applyAmount.value;
		}else{
			applyAmount2.value = '';
			collectRate.value = '';
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
	var index = 0;
	div_currency_sh_sh.on('change',function(){
		if(index == 0){
			index ++;
		}else{
			collectRate.value = '';
			var fCurr = n;
			if(fCurr!== undefined){
				if(fCurr == 'CNY'){
					applyAmount2.value = applyAmount.value;
					collectRate.value = '1';
				}else{
					applyAmount2.value = '';
					Ext.Ajax.request({
						url : contextPath + '/xdss3/unnameCollect/unnamerCollectController.spr?action=calculateExchangeRate&fCurr=' + fCurr  ,
					    success : function(xhr){
					    	if(xhr.responseText){
						        var jsonData = Ext.util.JSON.decode(xhr.responseText);
						        collectRate.value = jsonData.collectRate;
					        }
					    },
					    scope : this
				    });
				}
			}
		}
	});
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