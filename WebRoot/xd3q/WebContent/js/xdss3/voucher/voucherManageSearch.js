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


/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_Voucher_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_Voucher_grid("");
}

/**
 * 清空操作
 */
function _resetForm()
{
  if(_preresetForm()){
		document.all.mainForm.reset();
	}
	_postresetForm();
}

function genvoucher2(){
	var param = Form.serialize('mainForm');
	_getMainFrame().Ext.MessageBox.show({
				title:'重新生成凭证',
			    msg: '该操作功能用于SAP系统数据丢失从外围重新生成，覆盖原来生成的凭证，是否继续！',
				buttons: {yes:'是', no:'否'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
				    	 new AjaxEngine(contextPath+'/xdss3/voucher/voucherController.spr?action=genVoucher2', 
			   			{method:"post", parameters: param, onComplete: callBackHandle2,callback:customCallBackHandle});
					}else{
						_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
					}
				}
			});


}

function callBackHandle2(transport){
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
		}else{
			showError(msg);
		}
		_getMainFrame().maintabs.getActiveTab().flag = false;
		Voucher_grid.reload();
	}
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



function _commonMethodOperation(arg1,arg2,arg3,arg4,arg5,arg6,arg7){
	
	if("_confirm" == arg6){		
		_getMainFrame().showInfo("只能查看，不能确定凭证！ ");
		return false;
	}else{
		var records = Voucher_grid.getSelectionModel().getSelections();   
		var voucherid = "";     			
		for(i=0;i<records.size();i++)
	    {
	    	 voucherid = records[i].get('voucherid');  
			
	    }
		var url =  '/xdss3/voucher/voucherController.spr?action=_view&voucherid='+voucherid;
		_getMainFrame().maintabs.addPanel('凭证预览查看', '', contextPath + "/" + url);
	}
	
}