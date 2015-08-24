/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年11月04日 14点50分05秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>字典表(SapDict)编辑页面JS文件
 */
 
             
/**
 * grid 上的 创建按钮调用方法 
 * 新增字典表,字典表明细
 */
function _createSapDictDetail()
{
	_getMainFrame().ExtModalWindowUtil.show('字典表明细行项目新增',
			contextPath + '/platform/basicApp/sysConsole/dictionary/sapDictDetailController.spr?action=_create&domName='+domName ,'',_insertSapDictDetailRow,{width:350,height:200});
}

function _insertSapDictDetailRow()
{
    var errMsg = "";
    var isExists = false ;
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	if(json){
	    var count = SapDictDetail_store.getCount();
	    for(var i=0;i<count;i++){
	        if(json["SapDictDetail.domValue_l"]==SapDictDetail_store.getAt(i).get('domValue_l'))
	        {
	            isExists = true;
	             errMsg = '值为:' + json["SapDictDetail.domValue_l"] +"的数据已经存在，请重新输入！";
	             break;
	        }
	    }
    
        if(isExists)
        {
            _getMainFrame().showInfo(errMsg);
        }
        else
        {
			var sapDictDetailFields = new SapDictDetail_fields({
				domName:''
				,domValue_l:''
				,ddText:''
		        ,ddLanguage:''
		        ,valPos:''
		        ,as4Local:''
		        ,as4Vers:''
			});		
			
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
}
	 
    
/**
  * 字典表明细行项目编辑操作
  */
function _editSapDictDetail(id,url)
{
    url = contextPath+"/"+url + "&";
    //取得行数据，转发到编辑页面
    var records = SapDictDetail_grid.getSelectionModel().getSelections();     
    url = url + commonUrlEncode(records[0].data);
    _getMainFrame().ExtModalWindowUtil.show("字典表明细行项目编辑", url,'',_modifySapDictDetailRow,{width:350,height:200});
}

/**
 * 行项目编辑后的grid更新
 */
function _modifySapDictDetailRow()
{
    var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
    if(json){
        var records = SapDictDetail_grid.selModel.getSelections();
        if(records.length>1){
            _getMainFrame().showInfo('进行【字典表明细行项目编辑】操作时，只允许选择一条记录！');
            return;
        }  
        var record = records[0];
        record.set('domName:',json["SapDictDetail.domName"]);
        record.set('domValue_l',json["SapDictDetail.domValue_l"]);
        record.set('ddText',json["SapDictDetail.ddText"]);
        record.set('ddLanguage',json["SapDictDetail.ddLanguage"]);
        record.set('valPos',json["SapDictDetail.valPos"]);
        record.set('as4Local',json["SapDictDetail.as4Local"]);
        record.set('as4Vers',json["SapDictDetail.as4Vers"]);
    }
}

/**
  * 字典表明细行项目查看操作
  */
function _viewSapDictDetail(id,url)
{
    url = contextPath+"/"+url + "&";
    //取得行数据，转发到编辑页面
    var records = SapDictDetail_grid.getSelectionModel().getSelections();     
    url = url + commonUrlEncode(records[0].data);
    _getMainFrame().ExtModalWindowUtil.show("字典表明细行项目产看",url,'','',{width:350,height:200});
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
		_getMainFrame().ExtModalWindowUtil.show('复制创建字典表行项目',contextPath + '/platform/basicApp/sysConsole/dictionary/sapDictDetailController.spr?action=_copyCreate'+pars,'',_insertSapDictDetailRow,{width:350,height:200});
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
    if (SapDictDetail_grid.selModel.hasSelection() > 0){
        _getMainFrame().Ext.MessageBox.show({
            title:'系统提示',
            msg: '您选择了【字典表行项目批量删除】操作，是否确定继续该操作？',            
            buttons: {yes:'确定', no:'取消'},
            icon: Ext.MessageBox.QUESTION,
            fn:function(buttonid){
                 if (buttonid == 'yes'){
                     var records = SapDictDetail_grid.selModel.getSelections();
                    for (var i=0;i<records.length;i++){
                      SapDictDetail_grid.getStore().remove(records[i]);
                     }
            }
            }
        });

    }else{
        _getMainFrame().showInfo('请选择要删除的字典表行项目信息！');
    }
}
                
          
/**
 * 保存 
 */
function _saveOrUpdateSapDict()
{
	var param = Form.serialize('mainForm');	        	         
	param = param + ""+ getSapDictDetailGridData();
	new AjaxEngine(contextPath + '/platform/basicApp/sysConsole/dictionary/sapDictController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	reload_SapDictDetail_grid("defaultCondition=YDICTDETAIL.DOMNAME='"+ domName +"'");
}
          
/**
 * 取消
 */
function _cancelSapDict()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
