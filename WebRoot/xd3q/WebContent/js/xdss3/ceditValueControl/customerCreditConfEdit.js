/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年05月20日 12点33分43秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象客户代垫额度和发货额度配置(CustomerCreditConf)编辑页面用户可编程扩展JS文件
 */
 
          
   
/**
 * grid 上的 创建按钮调用方法 
 * 新增客户代垫额度和发货额度配置,客户信用额度下挂立项配置表
 */
function _precreateCustomerCreditProj()
{
	return true;
}


function _check_save(transport){
	var param = Form.serialize('mainForm');
	param = param + ""+ getCustomerCreditProjGridData();
	new AjaxEngine(contextPath + '/xdss3/ceditValueControl/customerCreditConfController.spr?action=checkExists', 
	   {method:"post", parameters: param, onComplete: callBackHandle,callback:_check_save_callback});
}

function _check_save_callback(transport){
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var msgType = responseUtil.getMessageType();
	if (msgType == 'info' && responseUtil.getMessage() == "操作成功！" ){
		_saveOrUpdateCustomerCreditConf();
	}
	var result = responseUtil.getCustomField("coustom");
	if (result!=null && result!='') {
		_getMainFrame().showInfo(result);
		return;
	}
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增客户代垫额度和发货额度配置,客户信用额度下挂立项配置表
 */
function _postcreateCustomerCreditProj()
{

}
   
    

    
/**
*客户代垫额度和发货额度配置行项目
*复制创建
*/
function _precopyCreateCustomerCreditProj()
{
	return true ;
}

/**
*客户代垫额度和发货额度配置行项目
*复制创建
*/
function _postcopyCreateCustomerCreditProj()
{

}
    

    
  


/**
  * 客户代垫额度和发货额度配置行项目查看操作
  */
function _previewCustomerCreditProj(id,url)
{
	return  true ;
}

/**
  * 客户代垫额度和发货额度配置行项目查看操作
  */
function _postviewCustomerCreditProj(id,url)
{

}
    
/**
  * 客户代垫额度和发货额度配置行项目编辑操作
  */
function _preeditCustomerCreditProj(id,url)
{
	return true ;
}

/**
  * 客户代垫额度和发货额度配置行项目编辑操作
  */
function _posteditCustomerCreditProj(id,url)
{

}

    
    
    
    
    
    
    
    
    
    
  

          
/**
 * 保存 
 */
function _presaveOrUpdateCustomerCreditConf()
{
	return true ;
}

/**
 * 保存 
 */
function _postsaveOrUpdateCustomerCreditConf()
{

}
          
/**
 * 取消
 */
function _precancelCustomerCreditConf()
{
	return true ;
}

/**
 * 取消
 */
function _postcancelCustomerCreditConf()
{

}

Ext.onReady(function(){
	// 放货额度
	var sendValue = Ext.getDom('CustomerCreditConf.sendvalue');
	// 代垫额度
	var prepayValue = Ext.getDom('CustomerCreditConf.prepayvalue');
	dict_div_credittype_dict.on('change',function(e,n,o){
		if(dict_div_credittype_dict.getValue()=='1'){ // 若为放货额度
			sendValue.readOnly = false;
			prepayValue.readOnly = true;
			prepayValue.value = 0;
		}else if(dict_div_credittype_dict.getValue()=='2'){	// 若为代垫额度
			sendValue.readOnly = true;
			sendValue.value = 0;
			prepayValue.readOnly = false;
		}else if(dict_div_credittype_dict.getValue()=='3'){	// 若为放货额度+代垫额度
			sendValue.readOnly = false;
			prepayValue.readOnly = false;
			sendValue.value = 0;
			prepayValue.value = 0;
		}
	});
	
	var cbiToolbar = CustomerCreditProj_grid.getTopToolbar();
	cbiToolbar.items.get('CustomerCreditProjaddRow').hide();		// 隐藏"增加行"按钮
//	cbiToolbar.items.get('CustomerCreditProjdeleteRow').hide();	// 隐藏"删除行"按钮
	
});
