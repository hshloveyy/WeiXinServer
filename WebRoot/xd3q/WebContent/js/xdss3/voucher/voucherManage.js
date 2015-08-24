/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月04日 05点41分18秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象凭证预览(Voucher)管理页面JS用户可编程扩展文件
 */


/**
 * 查询动作执行前
 */
function _presearch()
{
	return true;
}

/**
 * 查询动作执行后
 * 当 _presearch() 返回 false 时候则执行本函数。
 */
function _postsearch()
{

}


/**
 * 清空操作
 */
function _preresetForm()
{
	return true;
}
/**
 * 清空操作
 */
function _postresetForm()
{

}


/**
 * grid 上的 创建按钮调用方法 
 * 新增凭证预览
 */
function _precreate()
{
	 return true ;
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增凭证预览
 */
function _postcreate()
{

}

/**
 * grid上的 复制创建按钮调用方法
 */
function _precopyCreate()
{
	return true ;
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _postcopyCreate()
{

}

function _genVoucher()
{
	var voucherids = "";
	var isconfirm = true;
	for(var i=0;i<Voucher_grid.getStore().getCount();i++){
		var rec = Voucher_grid.getStore().getAt(i);
		voucherids += rec.get('voucherid') + ",";
		if(rec.get('isconfirm')=='0'||rec.get('isconfirm').trim()==''){
			isconfirm = false;
			break;
		}
	}
	if(isconfirm){
		voucherids = voucherids.substring(0,voucherids.length-1);
		var param = "voucherids="+voucherids;	
	    new AjaxEngine(contextPath+'/xdss3/voucher/voucherController.spr?action=genVoucherList', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}else{
		_getMainFrame().showInfo("存在未确认的凭证，不可生成!");
	}

}  

function callBackHandle(transport){
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	var rt = Ext.util.JSON.decode(transport.responseText);
	if(rt.isVoucherGen==true){
		_getMainFrame().maintabs.getActiveTab().flag = true;
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	}else if(rt.isVoucherGen==false){
		showError("凭证生成失败");
		Voucher_grid.reload();
	}else{
		var responseUtil = new AjaxResponseUtils(transport.responseText);
		var msgType = responseUtil.getMessageType();
		var msg = responseUtil.getMessage();
		//警告
		if(msgType=="warn"){		
			showWarn(msg);
		}
		//错误
		else if(msgType=="error"){
			showError(msg);
		}
		_getMainFrame().maintabs.getActiveTab().flag = false;
		Voucher_grid.reload();
	}
}

