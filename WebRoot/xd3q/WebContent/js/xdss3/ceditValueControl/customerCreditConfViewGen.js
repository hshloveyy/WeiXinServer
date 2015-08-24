/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年05月28日 11点05分03秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象客户代垫额度和发货额度配置(CustomerCreditConf)查看页面JS文件
 */
          
   
/**
 * grid 上的 创建按钮调用方法 
 * 新增客户代垫额度和发货额度配置,客户信用额度下挂立项配置表
 */
function _createCustomerCreditProj()
{
  if(_precreateCustomerCreditProj()){
     _commonMethodOperation('1',Txt.customerCreditProj_Create,CustomerCreditProj_grid,'xdss3/ceditValueControl/customerCreditProjController.spr?action=_create','_createCustomerCreditProjCallBack',true);
   }
   _postcreateCustomerCreditProj();
}

/**
 * 打开页签，回调
 */
function _createCustomerCreditProjCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_insertCustomerCreditProjRow(json);
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增客户代垫额度和发货额度配置,客户信用额度下挂立项配置表
 */
function _createCustomerCreditProjm()
{
  if(_precreateCustomerCreditProj()){
	_getMainFrame().ExtModalWindowUtil.show(Txt.customerCreditProj_Create,
			contextPath + '/xdss3/ceditValueControl/customerCreditProjController.spr?action=_create','',_createCustomerCreditProjmCallBack,{width:660,height:300});
   }
   _postcreateCustomerCreditProj();
}

/**
 * 弹出窗口，回调
 */
function _createCustomerCreditProjmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_insertCustomerCreditProjRow(json);
}

function _insertCustomerCreditProjRow(json)
{
	if(json){
	var customerCreditProjFields = new CustomerCreditProj_fields({
		configprojectid:''
       ,configid:''
	   ,projectid:''
	   ,projectid_text:''
       ,otherprepayvalue:''
       ,creator:''
       ,createtime:''
       ,lastmodifyer:''
       ,lastmodifytime:''
	});		
	
		CustomerCreditProj_grid.stopEditing();
		CustomerCreditProj_grid.getStore().insert(0, customerCreditProjFields);
		CustomerCreditProj_grid.startEditing(0, 0);
		var record = CustomerCreditProj_grid.getStore().getAt(0);
			record.set('configprojectid',json["CustomerCreditProj.configprojectid"]);
			record.set('configid',json["CustomerCreditProj.configid"]);
			record.set('projectid',json["CustomerCreditProj.projectid"]);
			record.set('projectid_text',json["CustomerCreditProj.projectid_htext"]);
            record.set('othersendvalue',0);
			record.set('otherprepayvalue',json["CustomerCreditProj.otherprepayvalue"]);
			record.set('creator',json["CustomerCreditProj.creator"]);
			record.set('createtime',json["CustomerCreditProj.createtime"]);
			record.set('lastmodifyer',json["CustomerCreditProj.lastmodifyer"]);
			record.set('lastmodifytime',json["CustomerCreditProj.lastmodifytime"]);
	}
}

    

    
/**
 *客户代垫额度和发货额度配置行项目
 *复制创建
 */
function _copyCreateCustomerCreditProj()
{
  if(_precopyCreateCustomerCreditProj()){
		if (CustomerCreditProj_grid.selModel.hasSelection() > 0){
			var records = CustomerCreditProj_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.customerCreditProj_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
		    _commonMethodOperation('1',Txt.customerCreditProj_CopyCreate,CustomerCreditProj_grid,'xdss3/ceditValueControl/customerCreditProjController.spr?action=_copyCreate'+pars,'_createCustomerCreditProjCallBack',true);
		
		}else{
			_getMainFrame().showInfo(Txt.customerCreditProj_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateCustomerCreditProj();
}


/**
 *客户代垫额度和发货额度配置行项目
 *复制创建
 */
function _copyCreateCustomerCreditProjm()
{
  if(_precopyCreateCustomerCreditProj()){
		if (CustomerCreditProj_grid.selModel.hasSelection() > 0){
			var records = CustomerCreditProj_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.customerCreditProj_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
			_getMainFrame().ExtModalWindowUtil.show(Txt.customerCreditProj_CopyCreate,contextPath + '/xdss3/ceditValueControl/customerCreditProjController.spr?action=_copyCreate'+pars,'',_createCustomerCreditProjmCallBack,{width:610,height:200});
		}else{
			_getMainFrame().showInfo(Txt.customerCreditProj_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateCustomerCreditProj();
}
    

    
  

/**
  * 客户代垫额度和发货额度配置行项目查看操作
  */
function _viewCustomerCreditProj(id,url)
{
	if(_previewCustomerCreditProj()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = CustomerCreditProj_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.customerCreditProj_View,	url,'','',{width:660,height:300});
	}
	_postviewCustomerCreditProj();
}
/**
  * 客户代垫额度和发货额度配置行项目查看，打开到页签 ,回调函数
  */
function _viewCustomerCreditProjpCallBack()
{
}

/**
  * 客户代垫额度和发货额度配置行项目查看，弹出窗口 ,回调函数
  */
function _viewCustomerCreditProjmCallBack()
{
}

    

function _editCustomerCreditProjpCallBack()
{
}


function _editCustomerCreditProjmCallBack()
{
}

/**
  * 客户代垫额度和发货额度配置行项目编辑操作
  */
function _editCustomerCreditProj(id,url)
{
	if(_preeditCustomerCreditProj()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = CustomerCreditProj_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.customerCreditProj_Edit,	url,'',_editCustomerCreditProjCallBack,{width:660,height:300});
      
      }
      _posteditCustomerCreditProj();
}
function _editCustomerCreditProjCallBack()
{
}

    
    
    
    
    
    
    
    
    
    
    
  

          
/**
 * 保存 
 */
function _saveOrUpdateCustomerCreditConf()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("CustomerCreditConf.configid").value = id;	
}
          

/**
 * 取消
 */
function _cancelCustomerCreditConf()
{
  if(_precancelCustomerCreditConf()){
	new AjaxEngine(contextPath + '/xdss3/ceditValueControl/customerCreditConfController.spr?action=_cancel&configid='+configid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelCustomerCreditConfCallBack});
   }
   _postcancelCustomerCreditConf();
}

function _cancelCustomerCreditConfCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
