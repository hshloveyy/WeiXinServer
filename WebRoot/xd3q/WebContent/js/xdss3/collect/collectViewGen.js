/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年08月20日 01点22分47秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象收款(Collect)查看页面JS文件
 */
          
   
    

    
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   

        
    
    
    
    
    
    
/**
  * 收款行项目查看操作
  */
function _viewCollectRelated(id,url)
{
	if(_previewCollectRelated()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = CollectRelated_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.collectRelated_View,	url,'','',{width:660,height:300});
	}
	_postviewCollectRelated();
}
/**
  * 收款行项目查看，打开到页签 ,回调函数
  */
function _viewCollectRelatedpCallBack()
{
}

/**
  * 收款行项目查看，弹出窗口 ,回调函数
  */
function _viewCollectRelatedmCallBack()
{
}

    
    
    
    

          
   

    
          
   
    
    
    
  

    
          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    

  

          
          
          

/**
 * 创建按钮调用方法 
 * 新增收款
 */
function _createCollect()
{
	if(_precreateCollect()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.collect_Create,'',contextPath + '/xdss3/collect/collectController.spr?action=_create');
	}
	_postcreateCollect();
}
          
function _copyCreateCollect()
{
	if(_precopyCreateCollect()){
		_getMainFrame().maintabs.addPanel(Txt.collect_CopyCreate,'',contextPath + '/xdss3/collect/collectController.spr?action=_copyCreate&collectid='+collectid);
	}
	_postcopyCreateCollect();
}

          

/**
 * 删除收款
 */
function _deleteCollect()
{
if(_predeleteCollect()){
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
		    msg: Txt.collect_Delete_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(result){
			   if (result == 'yes'){
					var param = '&collectid='+collectid;
					new AjaxEngine(contextPath + '/xdss3/collect/collectController.spr?action=_delete', 
					{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
				}
			}
		});
	 }
 	 _postdeleteCollect();
}

/**
 * 删除成功后的回调动作
 */
function _deleteCallBackHandle()
{
	_getMainFrame().showInfo(Txt.operateSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

          
/**
 * 保存 
 */
function _saveOrUpdateCollect()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("Collect.collectid").value = id;	
}
          

/**
 * 取消
 */
function _cancelCollect()
{
  if(_precancelCollect()){
	new AjaxEngine(contextPath + '/xdss3/collect/collectController.spr?action=_cancel&collectid='+collectid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelCollectCallBack});
   }
   _postcancelCollect();
}

function _cancelCollectCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
          
          
          

/**
 * 提交
 */
function _submitProcessCollect(id)
{
	if(_presubmitProcessCollect()){
		var param = Form.serialize('mainForm');	
	            
         
		param = param + "&" + getAllCollectItemGridData();
		        	            
         
		param = param + "&" + getAllCollectCbillGridData();
		        	            
         
		param = param + "&" + getAllCollectRelatedGridData();
		        	            
 
		param = param  + "&" +  Form.serialize('settleSubjectForm');		
            
 
		param = param  + "&" +  Form.serialize('fundFlowForm');		
            
         
		param = param + "&" + getAllCollectBankItemGridData();
		        			param = param + "&"+ Form.serialize('workflowForm')  + "&type=view";
	
		new AjaxEngine(contextPath +'/xdss3/collect/collectController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessCollect();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
