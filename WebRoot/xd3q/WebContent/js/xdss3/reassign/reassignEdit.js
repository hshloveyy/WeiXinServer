/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月13日 09点39分03秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象重分配(Reassign)编辑页面用户可编程扩展JS文件
 */
 

          
/**
 * 保存 
 */
function _presaveOrUpdateReassign()
{
	var rt = dict_div_reassigntype_dict.getValue();
	var n = dict_div_reassigntmethod_dict.getValue();
	if(n=='5' && (rt=='3' || rt=='4')){
		_getMainFrame().showInfo('重分配类型为[票清收款]或[票清付款]时，\n不能选择"重置并冲销（到业务部门重新分配，过资金部）"方式！');
		dict_div_reassigntmethod_dict.setValue('');
		return false ;
	}
	if(n=='3' && !(rt=='1' || rt=='2')){
		_getMainFrame().showInfo('选择"重置（财务部直接解除分配关系）"方式！，\n只能选择重分配类型为[收款]或[付款]时');
		dict_div_reassigntmethod_dict.setValue('');
		return false ;
	}
	return true ;
}

/**
 * 保存 
 */
function _postsaveOrUpdateReassign()
{

}
          
/**
 * 取消
 */
function _precancelReassign()
{
	return true ;
}

/**
 * 取消
 */
function _postcancelReassign()
{

}
          

/**
 * 提交
 */
function _presubmitProcessReassign()
{
	var rt = dict_div_reassigntype_dict.getValue();
	var n = dict_div_reassigntmethod_dict.getValue();
	if(n=='5' && (rt=='3' || rt=='4')){
		_getMainFrame().showInfo('重分配类型为[票清收款]或[票清付款]时，\n不能选择"重置并冲销（到业务部门重新分配，过资金部）"方式！');
		dict_div_reassigntmethod_dict.setValue('');
		return false ;
	}
	if(n=='3' && !(rt=='1' || rt=='2')){
		_getMainFrame().showInfo('选择"重置（财务部直接解除分配关系）"方式！，\n只能选择重分配类型为[收款]或[付款]时');
		dict_div_reassigntmethod_dict.setValue('');
		return false ;
	}
	return true ;
}

/**
 * 提交
 */
function _postsubmitProcessReassign()
{

}


Ext.onReady(function(){
	deptcode =  deptcode.replace(/\,/g,"','");
	deptcode = "'" + deptcode + "'";
	
	dict_div_reassigntype_dict.on('select',function(e,n,o){
		var n = dict_div_reassigntype_dict.getValue();
		var shName = '';
		var shlpField = '';
		var shlpDisplayField = '';
		if (companyCode == '1001'){
			companyCode = '2100';
		}
		if( n == '1' )		//收款 
		{
			shName = 'YHCOLLECTNO';			
			shlpField = 'COLLECTID';
			shlpDisplayField = 'COLLECTNO';
			div_reassignboid_sh_sh.defaultCondition = "BUSINESSSTATE = '3' and BUKRS='"+companyCode+
			"'and collectno not in (select REASSIGNBONO from yreassign where reassigntype='1') and DEPT_CODE in (" + deptcode + ")";
			dict_div_reassigntmethod_dict.editable = true;
		}
		else if( n == '2' )	//付款
		{
			shName = 'YHPAYMENTNO';
			shlpField = 'PAYMENTID';
			shlpDisplayField = 'PAYMENTNO';
			div_reassignboid_sh_sh.defaultCondition = "BUSINESSSTATE in ('4','7') and BUKRS='"+companyCode+
			"'and PAYMENTNO not in (select REASSIGNBONO from yreassign where reassigntype='2') and DEPT_CODE in (" + deptcode + ")";
			dict_div_reassigntmethod_dict.editable = true;
		}
		else if( n == '3' )	//票清付款
		{
			shName = 'YHBILLCLEARPAYMENTNO';
			shlpField = 'BILLCLEARID';
			shlpDisplayField = 'BILLCLEARNO';
			div_reassignboid_sh_sh.defaultCondition = "BUSINESSSTATE = '4' and BUKRS='"+companyCode+
			"'and BILLCLEARNO not in (select REASSIGNBONO from yreassign where reassigntype='3') and DEPT_CODE in (" + deptcode + ")";
			dict_div_reassigntmethod_dict.editable = true;
		}
		else if( n == '4' )	//票清收款
		{
			shName = 'YHBILLCLEARCOLLECTNO';
			shlpField = 'BILLCLEARID';
			shlpDisplayField = 'BILLCLEARNO';
			div_reassignboid_sh_sh.defaultCondition = "BUSINESSSTATE = '3' and BUKRS='"+companyCode+
			"'and BILLCLEARNO not in (select REASSIGNBONO from yreassign where reassigntype='4') and DEPT_CODE in (" + deptcode + ")";
			dict_div_reassigntmethod_dict.editable = true;
		}
		else if( n == '5' ) //客户退款
		{
			shName = 'YHCUREFUNDMENTNO';
			shlpField = 'REFUNDMENTID';
			shlpDisplayField = 'REFUNDMENTNO';
			div_reassignboid_sh_sh.defaultCondition = "BUSINESSSTATE = '3' and BUKRS='"+companyCode+
			"'and REFUNDMENTNO not in (select REASSIGNBONO from yreassign where reassigntype='5') and DEPT_CODE in (" + deptcode + ")";
			dict_div_reassigntmethod_dict.editable = true;
		}
		else if( n == '6' ) //供应商退款
		{
			shName = 'YHSUREFUNDMENTNO';
			shlpField = 'REFUNDMENTID';
			shlpDisplayField = 'REFUNDMENTNO';
			div_reassignboid_sh_sh.defaultCondition = "BUSINESSSTATE = '3' and BUKRS='"+companyCode+
			"'and REFUNDMENTNO not in (select REASSIGNBONO from yreassign where reassigntype='6') and DEPT_CODE in (" + deptcode + ")";
			dict_div_reassigntmethod_dict.editable = true;
		}
		else if( n == '7' ) //客户单清帐
		{
			shName = 'YHCUSINGLECLEAR';
			shlpField = 'CUSTOMSCLEARID';
			shlpDisplayField = 'CUSTOMCLEARNO';
			div_reassignboid_sh_sh.defaultCondition = "BUSINESSSTATE = '3' and COMPANYNO='"+companyCode+
			"'and CUSTOMCLEARNO not in (select REASSIGNBONO from yreassign where reassigntype='7') and DEPID in (" + deptcode + ")";
			dict_div_reassigntmethod_dict.setValue('4');	// 只能选择"冲销（财务部冲销并作废）"
			dict_div_reassigntmethod_dict.editable = false;
		}
		else if( n == '8' ) //供应商单清帐
		{
			shName = 'YHSUSINGLECLEAR';
			shlpField = 'SUPPLIERSCLEARID';
			shlpDisplayField = 'SUPPLIERCLEARNO';
			div_reassignboid_sh_sh.defaultCondition = "BUSINESSSTATE = '4' and COMPANYNO='"+companyCode+
			"'and SUPPLIERCLEARNO not in (select REASSIGNBONO from yreassign where reassigntype='8') and DEPID in (" + deptcode + ")";
			dict_div_reassigntmethod_dict.setValue('4');	// 只能选择"冲销（财务部冲销并作废）"
			dict_div_reassigntmethod_dict.editable = false;
		}
		div_reassignboid_sh_sh.changeShlp(shName, shlpField, shlpDisplayField, '');
	});
	
	dict_div_reassigntmethod_dict.on('select',function(e,n,o){
		var rt = dict_div_reassigntype_dict.getValue();
		if(this.value=='5' && (rt=='3' || rt=='4')){
			_getMainFrame().showInfo('重分配类型为[票清收款]或[票清付款]时，\n不能选择"重置并冲销（到业务部门重新分配，过资金部）"方式！');
			dict_div_reassigntmethod_dict.setValue('');
		}
		if(this.value=='3' && !(rt=='1' || rt=='2')){
			_getMainFrame().showInfo('选择"重置（财务部直接解除分配关系）"方式！，\n只能选择重分配类型为[收款]或[付款]时');
			dict_div_reassigntmethod_dict.setValue('');
		}
	});
	
	var reassignType = dict_div_reassigntype_dict.getValue();
	if(reassignType=='7' || reassignType=='8'){
		dict_div_reassigntmethod_dict.editable = false;
	}
});
