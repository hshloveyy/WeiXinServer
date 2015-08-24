/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月02日 08点03分16秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象凭证预览(Voucher)编辑页面用户可编程扩展JS文件
 */
 
Ext.onReady(function(){
	var voucherFlag = Ext.getDom('Voucher.flag').value;
	var VoucherItem_grid_cm = VoucherItem_grid.getColumnModel();
	if(voucherFlag.trim()=='W'){
		VoucherItem_grid_cm.setHidden(VoucherItem_grid_cm.findColumnIndex('depid'),true);
	}else{
		VoucherItem_grid_cm.setHidden(VoucherItem_grid_cm.findColumnIndex('gsber'),true);
	}
});          
   
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
    

function _genVoucherVoucher()
{
	var param = Form.serialize('mainForm');	
    param = param + ""+ getAllVoucherItemGridData();

    new AjaxEngine(contextPath+'/xdss3/voucher/voucherController.spr?action=genVoucher', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
		   
}  
  

function customCallBackHandle()
{
	
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

function round(v,e) 
{ 
	var t=1; 
	for(;e>0;t*=10,e--); 
	for(;e<0;t/=10,e++); 
	return Math.round(v*t)/t; 
} 

function _confirmVoucher()
{
	var amountH=0;
	var amountS=0;
	var amountH2=0;
	var amountS2=0;
	for(var i=0;i<VoucherItem_grid.getStore().getCount();i++){
		var rec = VoucherItem_grid.getStore().getAt(i);
		
		if(rec.get('debitcredit')=='H'){
			amountH = amountH + parseFloat(rec.get('amount'));
			amountH2 = amountH2 + parseFloat(rec.get('amount2'));
		}else if(rec.get('debitcredit')=='S'){
			amountS = amountS + parseFloat(rec.get('amount'));
			amountS2 = amountS2 + parseFloat(rec.get('amount2'));
		}
	}
	if(round(amountS,2)!=round(amountH,2)){
		_getMainFrame().showInfo("凭证行项的借贷金额不相等,请修改!");
		return;
	}
	if(round(amountS2,2)!=round(amountH2,2)){
		_getMainFrame().showInfo("凭证行项的借贷本位币金额不相等,请修改!");
		return;
	}
	
	if(Ext.get('Voucher.bstat').dom.value == 'A'){
		var voucherItems = VoucherItem_grid.getStore();
		for(var i=0;i<voucherItems.getCount();i++){
			var subject = voucherItems.getAt(i).get('subject');
			if(subject=='6603050301'||subject=='6603050302'||subject=='6603050101'||subject=='6603050201'){
				if(div_kostl_sh_sh.getValue()==''){
					_getMainFrame().showInfo("成本中心不能为空!");
					return;
				}
			}
		}
	}else{
		// 若不为清帐凭证（即不为A），要对本位币金额为0的进行检查
		for(var i=0;i<VoucherItem_store.getCount();i++){
			var amount2 = VoucherItem_store.getAt(i).get('amount2');
			if(amount2=='' || amount2==' ' || amount2=='0'){
				_getMainFrame().showInfo("凭证预览明细中存在[本位币金额]为空或为零的行项,请修改!");
				return;
			}
		}
	}
	
	var param = Form.serialize('mainForm');	
    param = param + ""+ getAllVoucherItemGridData();

    new AjaxEngine(contextPath+'/xdss3/voucher/voucherController.spr?action=confirmVoucher', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
		   
}  
  

function customCallBackHandle()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
    