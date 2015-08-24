/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年11月04日 14点50分05秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>字典表(SapDict)增加页面JS文件
 */

             
/**
 * grid 上的 创建按钮调用方法 
 * 新增字典表,字典表明细
 */
function _createSapDictDetail()
{
	_getMainFrame().ExtModalWindowUtil.show('字典表明细行项目新增',
			contextPath + '/platform/basicApp/sysConsole/dictionary/sapDictDetailController.spr?action=_create','',_insertSapDictDetailRow,{width:660,height:300});
}

function _insertSapDictDetailRow()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	if(json){
	var sapDictDetailFields = new SapDictDetail_fields({
		domName:''
		,domValue_l:''
		,ddText:''
        ,ddLanguage:''
        ,valPos:''
        ,as4Local:''
        ,as4Vers:''
	});		

    alert(json["SapDictDetail.domName"]);
		SapDictDetail_grid.stopEditing();
		SapDictDetail_grid.getStore().insert(0, sapDictDetailFields);
		SapDictDetail_grid.startEditing(0, 0);
		var record = SapDictDetail_grid.getStore().getAt(0);
		record.set('domName',json["SapDictDetail.domName"]);
		record.set('domValue_l',json["SapDictDetail.domValue_l"]);
		record.set('ddText',json["SapDictDetail.ddText"]);
        record.set('ddLanguage',json["SapDictDetail.ddLanguage"]);
        record.set('valPos',json["SapDictDetail.valPos"]);
        record.set('as4Local',json["SapDictDetail.as4Local"]);
        record.set('as4Vers',json["SapDictDetail.as4Vers"]);
	}
}
	 
    

    
/**
 *字典表行项目
 *复制创建
 */
function _copyCreateSapDictDetail()
{
	if (SapDictDetail_grid.selModel.hasSelection() > 0){
		var records = SapDictDetail_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo('进行【字典表行项目复制创建】操作时，只允许选择一条记录！');
			return;
		}   
		var recordData = commonUrlEncode(records[0].data);
		var pars ="&"+ recordData;
		_getMainFrame().ExtModalWindowUtil.show('复制创建字典表行项目',contextPath + '/platform/basicApp/sysConsole/dictionary/sapDictDetailController.spr?action=_copyCreate'+pars,'',_insertSapDictDetailRow,{width:610,height:200});
	}else{
		_getMainFrame().showInfo('请选择需要进行【字典表行项目复制创建】操作的记录！');
	}	
}
    

    
/**
 *字典表行项目
 *批量删除
 */
function _deletesSapDictDetail()
{
}

    

    
  
    

    
  
          
          
 /**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
    var responseUtil = new AjaxResponseUtils(transport.responseText);
    var result = responseUtil.getCustomField("coustom");
    var domName=result.domName;
    document.getElementById("SapDict.domName").value = domName;
    isCreateCopy = false;   
    reload_SapDictDetail_grid("defaultCondition=YDICTDETAIL.DOMNAME='"+ domName +"'");

}
       
          
/**
 * 保存 
 */
function _save()
{
	//if(isCreateCopy){
	//	document.getElementById("SapDict.domName").value = "";
	//}
	var param = Form.serialize('mainForm');	
	           
		        	         
	if(isCreateCopy)
	{
		param = param + ""+ getAllSapDictDetailGridData();
	}
	else
	{
		param = param + ""+ getSapDictDetailGridData(); 
	}
		        	    new AjaxEngine(contextPath + '/platform/basicApp/sysConsole/dictionary/sapDictController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
}

     

/**
 * 取消
 */
function _cancel()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
