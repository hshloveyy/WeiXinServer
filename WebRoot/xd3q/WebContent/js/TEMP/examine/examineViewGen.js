/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年12月25日 08点58分59秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象检验信息(Examine)查看页面JS文件
 */
          
   
/**
 * grid 上的 创建按钮调用方法 
 * 新增检验信息,检查信息明细
 */
function _createExamineItem()
{
	if(_precreateExamineItem()){
		_getMainFrame().ExtModalWindowUtil.show(Txt.examineItem_Create,
				contextPath + '/TEMP/examineItem/examineItemController.spr?action=_create','',_insertExamineItemRow,{width:660,height:300});
	}
	_postcreateExamineItem();
}

function _insertExamineItemRow()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	if(json){
	var examineItemFields = new ExamineItem_fields({
		viitem:''
       ,vi:''
       ,memo:''
       ,certificatedate:''
       ,outward4:''
	});		
	
		ExamineItem_grid.stopEditing();
		ExamineItem_grid.getStore().insert(0, examineItemFields);
		ExamineItem_grid.startEditing(0, 0);
		var record = ExamineItem_grid.getStore().getAt(0);
			record.set('viitem',json["ExamineItem.viitem"]);
			record.set('vi',json["ExamineItem.vi"]);
			record.set('memo',json["ExamineItem.memo"]);
			record.set('certificatedate',json["ExamineItem.certificatedate"]);
			record.set('outward4',json["ExamineItem.outward4"]);
	}
}
	 
    

    
/**
*检验信息行项目
*复制创建
*/
function _copyCreateExamineItem()
{
if(_precopyCreateExamineItem()){
	if (ExamineItem_grid.selModel.hasSelection() > 0){
		var records = ExamineItem_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo(Txt.examineItem_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}   
		var recordData = Ext.util.JSON.encode(records[0].data);
		var pars ="&examineItem="+ recordData;
		_commonMethodOperation('2',Txt.examineItem_CopyCreate,ExamineItem_grid,'/TEMP/examineItem/examineItemController.spr?action=_copyCreate'+pars);
	}else{
		_getMainFrame().showInfo(Txt.examineItem_CopyCreate_SelectToOperation);
	}
 }
_postcopyCreateExamineItem();	
}
    

    
  

/**
  * 检验信息行项目编辑操作
  */
function _editExamineItem(id,url)
{
 	if(_preeditExamineItem()){
		url = contextPath+"/"+url;
		//如果不存在id，则为之
		if(!id||id==''||id==' '||id=='undefined'){
			var records = ExamineItem_grid.getSelectionModel().getSelections();     
			var recordData = Ext.util.JSON.encode(records[0].data);
			url = url+"examineItem="+ recordData;//'&subObject={"objectName":"OrderItems","operType":"modify","values":[{}';
		}
		else{
			url = url+"viitem="+id;
		}
		_getMainFrame().ExtModalWindowUtil.show(Txt.examineItem_Edit,	url,'','',{width:660,height:300});
	}
	_posteditExamineItem();
}
    
    
    
    
    
    
          
   
                                     
  
  
/**
*  
*检查参与人员,grid上的增加和删除操作(搜索帮助)回调函数
*/  
function winExamineUserCallBack(jsonArrayData)
{
 	for(var i=0;i<jsonArrayData.length;i++){
 		var isExists = false;
 		for (var j = 0;j<ExamineUser_store.getCount();j++){
		   if (ExamineUser_store.getAt(j).get('userid') == jsonArrayData[i].USERID)
 				isExists = true;
 				break;
 		}

   if (isExists == false){
 	    var ExamineUserFields = new ExamineUser_fields({
	        examineuserid:''
		   ,userid:''
		   ,userid_text:''
	       ,vi:''
	   });		
	   
	   	ExamineUser_grid.stopEditing();
		ExamineUser_grid.getStore().insert(0, ExamineUserFields);
		ExamineUser_grid.startEditing(0, 0);
		var record = ExamineUser_grid.getStore().getAt(0);
		record.set('examineuserid',jsonArrayData[i].EXAMINEUSERID);
		record.set('userid',jsonArrayData[i].USERID);
		record.set('userid_text',jsonArrayData[i].USERNAME);
		record.set('vi',jsonArrayData[i].VI);
 		}
 	}
}

/**
*  
*检查参与人员,grid上的增加和删除操作(搜索帮助)
*/  
var searchExamineUserHelpWin = new Ext.SearchHelpWindow({
	   	  shlpName : 'YHUSER',
		callBack : winExamineUserCallBack
});

/**
*  
*检查参与人员,grid上的增加和删除操作,弹出搜索帮助
*/  
function _assignExamineUser(){
   if(_preassignExamineUser()){
		searchExamineUserHelpWin.show();
	}
	_postassignExamineUser();
}
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
    
                                     
  

/**
*检查参与人员
* grid上的和删除操作
*/
function _deletesExamineUser(){
 if(_predeletesExamineUser()){
		if (ExamineUser_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.examineUser_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				   if (buttonid == 'yes'){
						var records = ExamineUser_grid.selModel.getSelections();
						for (var i=0;i<records.length;i++){
							ExamineUser_grid.getStore().remove(records[i]);
					  }
				  }
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.examineUser_Deletes_SelectToOperation);
		}
	}
	_postdeletesExamineUser();
}
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
                                     
    
  

    
    
    
    
          
    
  

          
/**
 * 保存 
 */
function _saveOrUpdateExamine()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("Examine.vi").value = id;	
}
          

/**
 * 取消
 */
function _cancelExamine()
{
  if(_precancelExamine()){
	new AjaxEngine(contextPath + '/TEMP/examine/examineController.spr?action=_cancel&vi='+vi, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelExamineCallBack});
   }
   _postcancelExamine();
}

function _cancelExamineCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
