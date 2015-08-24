/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月11日 15点19分14秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象管理费用预算(DeptCharge)增加页面JS文件
 */

             
/**
 *管理费用预算行项目
 *复制创建
 */
function _copyCreateDeptCharDetail()
{
  if(_precopyCreateDeptCharDetail()){
		if (DeptCharDetail_grid.selModel.hasSelection() > 0){
			var records = DeptCharDetail_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.deptCharDetail_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
		    _commonMethodOperation('1',Txt.deptCharDetail_CopyCreate,DeptCharDetail_grid,'XDSS/sample/deptcharge/deptCharDetailController.spr?action=_copyCreate'+pars,'_createDeptCharDetailCallBack',true);
		
		}else{
			_getMainFrame().showInfo(Txt.deptCharDetail_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateDeptCharDetail();
}


/**
 *管理费用预算行项目
 *复制创建
 */
function _copyCreateDeptCharDetailm()
{
  if(_precopyCreateDeptCharDetail()){
		if (DeptCharDetail_grid.selModel.hasSelection() > 0){
			var records = DeptCharDetail_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.deptCharDetail_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
			_getMainFrame().ExtModalWindowUtil.show(Txt.deptCharDetail_CopyCreate,contextPath + '/XDSS/sample/deptcharge/deptCharDetailController.spr?action=_copyCreate'+pars,'',_createDeptCharDetailmCallBack,{width:610,height:200});
		}else{
			_getMainFrame().showInfo(Txt.deptCharDetail_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateDeptCharDetail();
}

    

    
/**
 * grid 上的 创建按钮调用方法 
 * 新增管理费用预算,费用明细
 */
function _createDeptCharDetail()
{
  if(_precreateDeptCharDetail()){
     _commonMethodOperation('1',Txt.deptCharDetail_Create,DeptCharDetail_grid,'XDSS/sample/deptcharge/deptCharDetailController.spr?action=_create','_createDeptCharDetailCallBack',true);
   }
   _postcreateDeptCharDetail();
}

/**
 * 打开页签，回调
 */
function _createDeptCharDetailCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_insertDeptCharDetailRow(json);
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增管理费用预算,费用明细
 */
function _createDeptCharDetailm()
{
  if(_precreateDeptCharDetail()){
	_getMainFrame().ExtModalWindowUtil.show(Txt.deptCharDetail_Create,
			contextPath + '/XDSS/sample/deptcharge/deptCharDetailController.spr?action=_create','',_createDeptCharDetailmCallBack,{width:660,height:300});
   }
   _postcreateDeptCharDetail();
}

/**
 * 弹出窗口，回调
 */
function _createDeptCharDetailmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_insertDeptCharDetailRow(json);
}

function _insertDeptCharDetailRow(json)
{
	if(json){
	var deptCharDetailFields = new DeptCharDetail_fields({
		deptchardetailid:''
		,deptchargeid:''
		,budtemid:''
		,budtemid_text:''
		,budtemitemid:''
		,budtemitemid_text:''
		,buditemname:''
		,last9month:''
		,last3month:''
		,lastpredict:''
		,crntbudget:''
		,budmonth1:''
		,budmonth2:''
		,budmonth3:''
		,budmonth4:''
		,budmonth5:''
		,budmonth6:''
		,budmonth7:''
		,budmonth8:''
		,budmonth9:''
		,budmonth10:''
		,budmonth11:''
		,budmonth12:''
		,budorgacc:''
		,prelimit:''
		,actamount:''
	});		

		DeptCharDetail_grid.stopEditing();
		DeptCharDetail_grid.getStore().insert(0, deptCharDetailFields);
		DeptCharDetail_grid.startEditing(0, 0);
		var record = DeptCharDetail_grid.getStore().getAt(0);
		record.set('deptchardetailid',json["DeptCharDetail.deptchardetailid"]);
		record.set('deptchargeid',json["DeptCharDetail.deptchargeid"]);
		record.set('budtemid',json["DeptCharDetail.budtemid"]);
		record.set('budtemid_text',json["DeptCharDetail.budtemid_htext"]);
		record.set('budtemitemid',json["DeptCharDetail.budtemitemid"]);
		record.set('budtemitemid_text',json["DeptCharDetail.budtemitemid_htext"]);
		record.set('buditemname',json["DeptCharDetail.buditemname"]);
		record.set('last9month',json["DeptCharDetail.last9month"]);
		record.set('last3month',json["DeptCharDetail.last3month"]);
		record.set('lastpredict',json["DeptCharDetail.lastpredict"]);
		record.set('crntbudget',json["DeptCharDetail.crntbudget"]);
		record.set('budmonth1',json["DeptCharDetail.budmonth1"]);
		record.set('budmonth2',json["DeptCharDetail.budmonth2"]);
		record.set('budmonth3',json["DeptCharDetail.budmonth3"]);
		record.set('budmonth4',json["DeptCharDetail.budmonth4"]);
		record.set('budmonth5',json["DeptCharDetail.budmonth5"]);
		record.set('budmonth6',json["DeptCharDetail.budmonth6"]);
		record.set('budmonth7',json["DeptCharDetail.budmonth7"]);
		record.set('budmonth8',json["DeptCharDetail.budmonth8"]);
		record.set('budmonth9',json["DeptCharDetail.budmonth9"]);
		record.set('budmonth10',json["DeptCharDetail.budmonth10"]);
		record.set('budmonth11',json["DeptCharDetail.budmonth11"]);
		record.set('budmonth12',json["DeptCharDetail.budmonth12"]);
		record.set('budorgacc',json["DeptCharDetail.budorgacc"]);
		record.set('prelimit',json["DeptCharDetail.prelimit"]);
		record.set('actamount',json["DeptCharDetail.actamount"]);
	}
}	
    

    
  
    

/**
  * 管理费用预算行项目查看操作
  */
function _viewDeptCharDetail(id,url)
{
	if(_previewDeptCharDetail()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = DeptCharDetail_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.deptCharDetail_View,	url,'','',{width:660,height:300});
	}
	_postviewDeptCharDetail();
}
/**
  * 管理费用预算行项目查看，打开到页签 ,回调函数
  */
function _viewDeptCharDetailpCallBack()
{
}

/**
  * 管理费用预算行项目查看，弹出窗口 ,回调函数
  */
function _viewDeptCharDetailmCallBack()
{
}


function _editDeptCharDetailpCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_modifyDeptCharDetailRow(json);
}


function _editDeptCharDetailmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyDeptCharDetailRow(json);
}

/**
  * 管理费用预算行项目编辑操作
  */
function _editDeptCharDetail(id,url)
{
	if(_preeditDeptCharDetail()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = DeptCharDetail_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.deptCharDetail_Edit,	url,'',_editDeptCharDetailCallBack,{width:660,height:300});
      
      }
      _posteditDeptCharDetail();
}
function _editDeptCharDetailCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyDeptCharDetailRow(json);
}

/**
 * 行项目编辑后的grid更新
 */
function _modifyDeptCharDetailRow(json)
{
	if(json){
		var records = DeptCharDetail_grid.selModel.getSelections();
		var record = records[0];
		record.set('deptchargeid',json["DeptCharDetail.deptchargeid"]);
		record.set('budtemid',json["DeptCharDetail.budtemid"]);
		record.set('budtemitemid',json["DeptCharDetail.budtemitemid"]);
		record.set('buditemname',json["DeptCharDetail.buditemname"]);
		record.set('last9month',json["DeptCharDetail.last9month"]);
		record.set('last3month',json["DeptCharDetail.last3month"]);
		record.set('lastpredict',json["DeptCharDetail.lastpredict"]);
		record.set('crntbudget',json["DeptCharDetail.crntbudget"]);
		record.set('budmonth1',json["DeptCharDetail.budmonth1"]);
		record.set('budmonth2',json["DeptCharDetail.budmonth2"]);
		record.set('budmonth3',json["DeptCharDetail.budmonth3"]);
		record.set('budmonth4',json["DeptCharDetail.budmonth4"]);
		record.set('budmonth5',json["DeptCharDetail.budmonth5"]);
		record.set('budmonth6',json["DeptCharDetail.budmonth6"]);
		record.set('budmonth7',json["DeptCharDetail.budmonth7"]);
		record.set('budmonth8',json["DeptCharDetail.budmonth8"]);
		record.set('budmonth9',json["DeptCharDetail.budmonth9"]);
		record.set('budmonth10',json["DeptCharDetail.budmonth10"]);
		record.set('budmonth11',json["DeptCharDetail.budmonth11"]);
		record.set('budmonth12',json["DeptCharDetail.budmonth12"]);
		record.set('budorgacc',json["DeptCharDetail.budorgacc"]);
		record.set('prelimit',json["DeptCharDetail.prelimit"]);
		record.set('actamount',json["DeptCharDetail.actamount"]);
	}
}
    
  
          
          
          
/**
 * 保存 
 */
function _save()
{
	//if(isCreateCopy){
	//	document.getElementById("DeptCharge.deptchargeid").value = "";
	//}
	if(_presave()){
		var param = Form.serialize('mainForm');	
		           
			        	         
		if(isCreateCopy)
		{
			param = param + ""+ getAllDeptCharDetailGridData();
		}
		else
		{
			param = param + ""+ getDeptCharDetailGridData(); 
		}
			        		    new AjaxEngine(contextPath + '/XDSS/sample/deptcharge/deptChargeController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}
	_postsave();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var deptchargeid=result.deptchargeid;
	document.getElementById("DeptCharge.deptchargeid").value = deptchargeid;
	isCreateCopy = false;	
	reload_DeptCharDetail_grid("defaultCondition=YDEPTCHARDETAIL.DEPTCHARGEID='"+ deptchargeid +"'");

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
          
          

