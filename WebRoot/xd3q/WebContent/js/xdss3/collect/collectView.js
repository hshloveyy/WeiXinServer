/**
 * Author(s):java业务平台代码生成工具 Date: 2010年06月01日 06点24分19秒 Copyright Notice:版权声明
 * 福建讯盟软件有限公司, 版权所有 违者必究
 * 
 * @(#) Description: <功能>主对象收款信息表(Collect)查看页面JS文件
 */

/**
 * 收款信息表行项目编辑操作
 */
function _preeditOverCollect(id, url)
{
	return true;
}

/**
 * 收款信息表行项目编辑操作
 */
function _posteditOverCollect(id, url)
{
	
}

/**
 * 收款信息表行项目查看操作
 */
function _previewOverCollect(id, url)
{
	return true;
}

/**
 * 收款信息表行项目查看操作
 */
function _postviewOverCollect(id, url)
{
	
}

/**
 * 创建按钮调用方法 新增收款信息表
 */
function _precreateCollect()
{
	return true;
}

/**
 * 创建按钮调用方法 新增收款信息表
 */
function _postcreateCollect()
{
	
}

function _precopyCreateCollect()
{
	return true;
}

function _postcopyCreateCollect()
{
	
}

/**
 * 删除收款信息表
 */
function _predeleteCollect()
{
	return true;
}

/**
 * 删除收款信息表
 */
function _postdeleteCollect()
{
	
}

/**
 * 保存
 */
function _presaveOrUpdateCollect()
{
	return true;
}

/**
 * 保存
 */
function _postsaveOrUpdateCollect()
{
	
}

function _presubmitProcessCollect()
{
	return true;
}

function _postsubmitProcessCollect()
{
	return true;
}

/**
 * 取消
 */
function _precancelCollect()
{
	return false;
}

/**
 * 取消
 */
function _postcancelCollect()
{
	new AjaxEngine(contextPath + '/xdss3/collect/collectController.spr?action=_cancel&collectid=' + collectid,
			{
				method		: "post",
				parameters	: '',
				onComplete	: callBackHandle,
				callback	: cancelCollectCallBack
			});
}

function cancelCollectCallBack()
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

//现金日记账
function _cashJournalCollect()
{
	//xjrj/journal.do?method=preAdd&journalType=2&isFromXdss=1&cxID=&companyID=&deptID=&isPay=&accountCode=&occurTime=&amount=&bankNoteNO=&journalName=&description=
}

function _submitForReassignCollect()
{
	
}

function _voucherPreviewCollect()
{
	var param = Form.serialize('mainForm');	
	
    param = param +  "&"+ Form.serialize('settleSubjectForm');		
	param = param +  "&"+ Form.serialize('fundFlowForm');		
	param = param + ""+ getCollectItemGridData();
	param = param + ""+ getCollectCbillGridData();
    param = param + ""+ getCollectBankItemGridData();
		   
    new AjaxEngine(contextPath+'/xdss3/collect/collectController.spr?action=voucherPreview', 
		   {method:"post", parameters: param, onComplete: callBackHandle1});
}

function callBackHandle1(transport)
{
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	if(transport.responseText){
		var voucherid = transport.responseText;
		_getMainFrame().maintabs.addPanel('模拟凭证','', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('Collect.collectid').dom.value);
	}
}

function _autoAssignCollect()
{
	
}

function _clearAssignCollect()
{
	
}
function _copyForReassignCollect()
{
	
}
function _viewForReassignCollect()
{
	
}

/*
 * 合同详细信息查看
 */ 
function viewContractInfo(contNo){
	var contUrl = contextPath + '/contractController.spr?action=viewSaleContract&contractno=' + contNo;
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
	var relaUrl = contextPath + '/xdss3/collect/collectController.spr?action=viewRelatedInfo&relatedNo=' + relaNo;
	if(relaNo.trim()!=''){
		_getMainFrame().maintabs.addPanel('原收款单信息','',relaUrl);
		   
	}
}

Ext.onReady(function(){
//	Ext.getCmp('_autoAssign').hide();				// 隐藏（按钮）自动分配
//	Ext.getCmp('_clearAssign').hide();				// 隐藏（按钮）清除分配
//	Ext.getCmp('_voucherPreview').hide();			// 隐藏（按钮）模拟凭证
//	Ext.getCmp('_cashJournal').hide();				// 隐藏（按钮）现金日记账
//	Ext.getCmp('_submitForReassign').hide();		// 隐藏（按钮）重分配提交
//	Ext.getCmp('_saveOrUpdate').hide();				// 隐藏（按钮）保存
	
});