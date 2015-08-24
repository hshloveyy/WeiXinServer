/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年04月16日 11点33分53秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象人员(Personnel)增加页面JS文件
 */

             
    
    
    
  
    
/**
  * 人员行项目查看操作
  */
function _viewAddress(id,url)
{
	if(_previewAddress()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = Address_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.address_View,	url,'','',{width:660,height:300});
	}
	_postviewAddress();
}
/**
  * 人员行项目查看，打开到页签 ,回调函数
  */
function _viewAddresspCallBack()
{
}

/**
  * 人员行项目查看，弹出窗口 ,回调函数
  */
function _viewAddressmCallBack()
{
}


function _editAddresspCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_modifyAddressRow(json);
}


function _editAddressmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyAddressRow(json);
}

/**
  * 人员行项目编辑操作
  */
function _editAddress(id,url)
{
	if(_preeditAddress()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = Address_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.address_Edit,	url,'',_editAddressCallBack,{width:660,height:300});
      
      }
      _posteditAddress();
}
function _editAddressCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyAddressRow(json);
}

/**
 * 行项目编辑后的grid更新
 */
function _modifyAddressRow(json)
{
	if(json){
		var records = Address_grid.selModel.getSelections();
		var record = records[0];
		record.set('phone',json["Address.phone"]);
		record.set('extnumber',json["Address.extnumber"]);
		record.set('mobilephone',json["Address.mobilephone"]);
		record.set('fax',json["Address.fax"]);
		record.set('website',json["Address.website"]);
		record.set('qq_msn',json["Address.qq_msn"]);
		record.set('homephone',json["Address.homephone"]);
		record.set('addresstype',json["Address.addresstype"]);
		record.set('country',json["Address.country"]);
		record.set('area',json["Address.area"]);
		record.set('city',json["Address.city"]);
		record.set('zipcode',json["Address.zipcode"]);
		record.set('address',json["Address.address"]);
		record.set('email',json["Address.email"]);
	}
}
    
             
                                     
  
  
/**
 *  
 *组织员工,grid上的增加和删除操作(搜索帮助)回调函数
 */  
function winOrgPersonnelCallBack(jsonArrayData)
{
 	for(var i=0;i<jsonArrayData.length;i++){
 		var isExists = false;
 		for (var j = 0;j<OrgPersonnel_store.getCount();j++){
		   if (OrgPersonnel_store.getAt(j).get('organizationId') == jsonArrayData[i].ORGANIZATIONID)
		   if (OrgPersonnel_store.getAt(j).get('personnelId') == jsonArrayData[i].PERSONNELID)
 				isExists = true;
 				break;
 		}

   if (isExists == false){
 	    var OrgPersonnelFields = new OrgPersonnel_fields({
	        orgPersonnelId:''
		   ,organizationId:''
		   ,organizationId_text:''
		   ,personnelId:''
		   ,personnelId_text:''
	   });		
	   
	   	OrgPersonnel_grid.stopEditing();
		OrgPersonnel_grid.getStore().insert(0, OrgPersonnelFields);
		OrgPersonnel_grid.startEditing(0, 0);
		var record = OrgPersonnel_grid.getStore().getAt(0);
		record.set('client',jsonArrayData[i].MANDT);
		record.set('orgPersonnelId',jsonArrayData[i].ORGPERSONNELID);
		record.set('organizationId',jsonArrayData[i].ORGANIZATIONID);
		record.set('organizationId_text',jsonArrayData[i].ORGANIZATIONNAME);
		record.set('personnelId',jsonArrayData[i].PERSONNELID);
		record.set('personnelId_text',jsonArrayData[i].PERSONNELNAME);
 		}
 	}
}

/**
 *   
 *组织员工,grid上的增加和删除操作(搜索帮助)
 */  
var searchOrgPersonnelHelpWin = new Ext.SearchHelpWindow({
	    shlpName : 'YHORGANIZATION',
		callBack : winOrgPersonnelCallBack
});

/**
 *  
 *组织员工,grid上的增加和删除操作,弹出搜索帮助
 */  
function _assignOrgPersonnel(){
	if(_preassignOrgPersonnel()){
		searchOrgPersonnelHelpWin.show();
	}
	_postassignOrgPersonnel();
}
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
    
                                     
  

/**
 *组织员工
 * grid上的和删除操作
 */
function _deletesOrgPersonnel(){
	if(_predeletesOrgPersonnel()){
		if (OrgPersonnel_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.orgPersonnel_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (buttonid == 'yes'){
					   var records = OrgPersonnel_grid.selModel.getSelections();
					   for (var i=0;i<records.length;i++){
						 OrgPersonnel_grid.getStore().remove(records[i]);
					   }
				     }
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.orgPersonnel_Deletes_SelectToOperation);
		}
	}
	_postdeletesOrgPersonnel();
}
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
    
  
    
    
  

          
          
          
          
/**
 * 保存 
 */
function _saveOrUpdatePersonnel()
{					 
//if(isCreateCopy){
	//	document.getElementById("Personnel.personnelid").value = "";
	//}
	if(_presaveOrUpdatePersonnel()){
		var param = Form.serialize('mainForm');	
		           
			        	 
		param = param + "&" + Form.serialize('addressForm');		
           
			        	         
		if(isCreateCopy)
		{
			param = param + ""+ getAllOrgPersonnelGridData();
		}
		else
		{
			param = param + ""+ getOrgPersonnelGridData(); 
		}
			        		    new AjaxEngine(contextPath + '/platform/basicApp/authManage/personnelController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdatePersonnel();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var personnelid=result.personnelid;
	document.getElementById("Personnel.personnelid").value = personnelid;
	
	document.getElementById("Personnel.personnelno").value = result.personnelno;
	document.getElementById("Personnel.creator_text").value = result.creator_text;
	document.getElementById("Personnel.creator").value = result.creator;
	document.getElementById("Personnel.createtime").value = result.createtime;
	document.getElementById("Personnel.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("Personnel.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("Personnel.lastmodifytime").value = result.lastmodifytime;
	isCreateCopy = false;	
	document.getElementById("Address.addressid").value=result.addressid;
	reload_OrgPersonnel_grid("defaultCondition=YORGPERSONNEL.PERSONNELID='"+ personnelid +"'");

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
