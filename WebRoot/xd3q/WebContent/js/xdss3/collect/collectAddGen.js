/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年08月20日 01点22分46秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象收款(Collect)增加页面JS文件
 */

             
    
    
    
    
/**
 *收款行项目
 *批量删除
 */
function _deletesCollectItem()
{
	if(_predeletesCollectItem()){
		if (CollectItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.collectItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = CollectItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						CollectItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.collectItem_Deletes_SelectToOperation);
		}
	}
	_postdeletesCollectItem();
}
    


    
    
  
    
    
             
    
    
/**
 *收款行项目
 *批量删除
 */
function _deletesCollectCbill()
{
	if(_predeletesCollectCbill()){
		if (CollectCbill_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.collectCbill_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = CollectCbill_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						CollectCbill_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.collectCbill_Deletes_SelectToOperation);
		}
	}
	_postdeletesCollectCbill();
}
    

    
    
  
    
    
             
  
    
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

    
             
  
    
    
             
    
    
    
  
    
    
function _predeletesCollectBankItem(){
	return true;
}     

function _postdeletesCollectBankItem(){
	
}
    
    
/**
 *收款行项目
 *批量删除
 */
function _deletesCollectBankItem()
{
	if(_predeletesCollectBankItem()){
		if (CollectBankItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.collectBankItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = CollectBankItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						CollectBankItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.collectBankItem_Deletes_SelectToOperation);
		}
	}
	_postdeletesCollectBankItem();
}

    
    
  
    
    
  

          

          

          
          
          
          
/**
 * 保存 
 */
function _saveOrUpdateCollect()
{					 
    if(isCreateCopy){
		document.getElementById("Collect.collectid").value = "";
	}
	if(_presaveOrUpdateCollect()){
		var param = Form.serialize('mainForm');	
		           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllCollectItemGridData();
		}
		else
		{
			param = param + ""+ getCollectItemGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllCollectCbillGridData();
		}
		else
		{
			param = param + ""+ getCollectCbillGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllCollectRelatedGridData();
		}
		else
		{
			param = param + ""+ getCollectRelatedGridData(); 
		}
			        	           
 
		//param = param + "&" + Form.serialize('settleSubjectForm');		
           
 
		//param = param + "&" + Form.serialize('fundFlowForm');		
           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllCollectBankItemGridData();
		}
		else
		{
			param = param + ""+ getCollectBankItemGridData(); 
		}
			        		    new AjaxEngine(contextPath + '/xdss3/collect/collectController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateCollect();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var collectid=result.collectid;
	document.getElementById("Collect.collectid").value = collectid;
	
	document.getElementById("Collect.collectno").value = result.collectno;
	document.getElementById("Collect.creator_text").value = result.creator_text;
	document.getElementById("Collect.creator").value = result.creator;
	document.getElementById("Collect.createtime").value = result.createtime;
	document.getElementById("Collect.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("Collect.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("Collect.lastmodifytime").value = result.lastmodifytime;
	isCreateCopy = false;	
	reload_CollectItem_grid("defaultCondition=YCOLLECTITEM.COLLECTID='"+ collectid +"'");
	reload_CollectCbill_grid("defaultCondition=YCOLLECTCBILL.COLLECTID='"+ collectid +"'");
	reload_CollectRelated_grid("defaultCondition=YCOLLECTRELATED.COLLECTID='"+ collectid +"'");
	//document.getElementById("SettleSubject.settlesubjectid").value=result.settlesubjectid;
	//document.getElementById("FundFlow.fundflowid").value=result.fundflowid;
	reload_CollectBankItem_grid("defaultCondition=YCOLLECTBANKITEM.COLLECTID='"+ collectid +"'");

    if(Ext.getCmp("_submitProcess").disabled){
    	Ext.getCmp("_submitProcess").setDisabled(false);}
    if(Ext.getCmp("_submitForReassign").disabled){
    	Ext.getCmp("_submitForReassign").setDisabled(false);}    	
    if(Ext.getCmp("tabs").getItem('attachementTab').disabled){
    	Ext.getCmp("tabs").getItem('attachementTab').setDisabled(false);
    }
}

          
/**
 * 取消
 */
function _cancel()
{
	if(_precancel()){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	 }
	 _postcancel();
}
          

          

          

          

/**
 * 提交
 */
function _submitProcessCollect()
{
if(_presubmitProcessCollect()){
	var param = Form.serialize('mainForm');	
          
         
	param = param + "&" + getAllCollectItemGridData();
	        	          
         
	param = param + "&" + getAllCollectCbillGridData();
	        	          
         
	param = param + "&" + getAllCollectRelatedGridData();
	        	          
 
	//param = param + "&" + Form.serialize('settleSubjectForm');		
          
 
	//param = param + "&" + Form.serialize('fundFlowForm');		
          
         
	param = param + "&" + getAllCollectBankItemGridData();
	        		param = param + "&"+ Form.serialize('workflowForm');
	
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

