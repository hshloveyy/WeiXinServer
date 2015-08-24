/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年08月20日 01点22分47秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象收款(Collect)编辑页面JS文件
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
    
    
    
    
    
             

    
             
    
    
    
  

    
             

    

    
/**
 *收款行项目
 *批量删除
 */
function _deletesCollectBankItem()
{
	if(_predeletesCollectBankItem()){
		if (CollectBankItem_grid.selModel.hasSelection() > 0){
			/* 在确认删除前，先判断所选数据是否包含不可删除的数据
			var errMsg = '';
			var records = CollectBankItem_grid.selModel.getSelections();
			for(var k=0; k<records.length; k++){
				if(records[k].data.flag != '1'){
					errMsg += '账号为“' + records[k].data.collectbankacc + '”的“' + records[k].data.collectbankacc_text + '”数据不能被删除！\n';
				}
			}
			if(errMsg != ''){
				_getMainFrame().showInfo(errMsg);
				return;
			}*/
			
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.collectBankItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = CollectBankItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						// 只有状态不为1的银行项才可以删除
						if(records[i].data.flag != '1'){
							CollectBankItem_grid.getStore().remove(records[i]);
						}
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
if(_presaveOrUpdateCollect()){
	var param = Form.serialize('mainForm');	
	           
         
		        		param = param + ""+ getCollectItemGridData();
		        	           
         
		        		param = param + ""+ getCollectCbillGridData();
		        	           
         
		        		param = param + ""+ getCollectRelatedGridData();
		        	           
 
		        		param = param +  "&"+ Form.serialize('settleSubjectForm');		
           
 
		        		param = param +  "&"+ Form.serialize('fundFlowForm');		
           
         
		        		param = param + ""+ getCollectBankItemGridData();
		        	    //alert(param);
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
	//var id = responseUtil.getCustomField('coustom');
	var collectid=result.collectid;
	//document.getElementById("Collect.collectid").value = id;
	document.getElementById("SettleSubject.settlesubjectid").value = result.settlesubjectid;
	document.getElementById("FundFlow.fundflowid").value = result.fundflowid;
	document.getElementById("Collect.creator_text").value = result.creator_text;
	document.getElementById("Collect.creator").value = result.creator;
	document.getElementById("Collect.createtime").value = result.createtime;
	document.getElementById("Collect.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("Collect.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("Collect.lastmodifytime").value = result.lastmodifytime;
	reload_CollectItem_grid("defaultCondition=YCOLLECTITEM.COLLECTID='"+ collectid +"'");
	reload_CollectCbill_grid("defaultCondition=YCOLLECTCBILL.COLLECTID='"+ collectid +"'");
	reload_CollectRelated_grid("defaultCondition=YCOLLECTRELATED.COLLECTID='"+ collectid +"'");
	reload_CollectBankItem_grid("defaultCondition=YCOLLECTBANKITEM.COLLECTID='"+ collectid +"'");
	
	if(Ext.getCmp("_submitProcess").disabled){
    	Ext.getCmp("_submitProcess").setDisabled(false);}
    
	if(div_customer_sh_sh.editable){
		div_customer_sh_sh.setEditable(false);
	}
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
          
          
          
var voucherFlag = false;     

/**
 * 提交
 * ------------------------------ 修改记录 ------------------------------
 * 邱杰烜 2010-09-20 在资金部节点提交时，给予现金日记账是否已经登记的提示
 */
function _submitProcessCollect(flag){
if(_presubmitProcessCollect()){
	voucherFlag = flag;
	var param = Form.serialize('mainForm');	
	param = param + "&" + getAllCollectItemGridData();
	param = param + "&" + getAllCollectCbillGridData();
	param = param + "&" + getAllCollectRelatedGridData();
	param = param + "&" + Form.serialize('settleSubjectForm');		
	param = param + "&" + Form.serialize('fundFlowForm');		
	param = param + "&" + getAllCollectBankItemGridData();
	param = param + "&"+ Form.serialize('workflowForm');
	/*
	 * 在资金部节点提交时，给予现金日记账提示（stateFlag为2代表资金部，在collectEdit.js里赋值）
	 */
	var ns = Ext.getDom('workflowLeaveTransitionName').value;		// 下一步操作
	if(stateFlag==2 && (ns!='退回业务修改' && ns!='退回')){
		Ext.MessageBox.confirm(Txt.cue, '请确认是否已经登记现金日记账？', function(result){
			if(result=='yes'){
				new AjaxEngine(contextPath +'/xdss3/collect/collectController.spr?action=_submitProcess', 
						{method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
			}
		});
	}else{	// 不为资金部节点则不给提示
		new AjaxEngine(contextPath +'/xdss3/collect/collectController.spr?action=_submitProcess', 
				{method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
  }
  _postsubmitProcessCollect();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var msgType = responseUtil.getMessageType();
	if (msgType == 'info')
	{
		if(voucherFlag==true){
			var custom =div_customer_sh_sh.getValue();	
			var collectid=Ext.get('Collect.collectid').dom.value;	
			Ext.Ajax.request({
	    				url : contextPath+'/xdss3/collect/collectController.spr?action=updateCustomerTitle',
	        			params : {customer:custom,collectid:collectid},
	        			success : function(xhr){
							if(xhr.responseText){				        	
							}
			        	},
			        	scope : this
			    	});
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.submitSuccess+'是否需要查看凭证？',
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
				    	_getMainFrame().maintabs.addPanel2('凭证查看', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('Collect.collectid').dom.value);
					}else{
						_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
					}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.submitSuccess);
			_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		}
	}
}
