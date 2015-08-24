/**
  * Author(s):java业务平台代码生成工具
  * Date: ${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象${beanAttribute.boDescription}(${beanAttribute.boName})编辑页面JS文件
 */
 
<#if subBoList?exists && (subBoList?size>0)> 
<#-- 开始生成gridToolbars上所用到的方法 boName  boInstanceName-->
<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象 -->
<#-- 开始生成共用的回调函数-->
<#if subBo.subBoAttribute.subBoRelType=="4">

/**
 * 1对多(特殊)子对象操作成功后的回调动作
 */
function ${subBo.boName?uncap_first}CallBack()
{
    var ${bo.primaryKeyProperty.propertyName}=document.getElementById("${bo.boName}.${bo.primaryKeyProperty.propertyName}").value ;
	reload_${subBo.boName}_grid("defaultCondition=${subBo.tableName}.${bo.primaryKeyProperty.columnName}='"+ ${bo.primaryKeyProperty.propertyName} +"'");
}
</#if>
<#if subBo.boName!="Attachement">   <#-- 业务对象名字判断，业务附件Attachement不处理 -->
<#if (subBo.gridToolbars?exists) && (subBo.gridToolbars?size>0)>
<#list subBo.gridToolbars as toolbar>
<#if subBo.subBoAttribute.subBoRelType=="2">
<#if toolbar.method.methodName=="_create">
  <#-- 方法 为1：打开到页签-->
/**
 * grid 上的 创建按钮调用方法 
 * 新增${beanAttribute.boDescription},${subBo.description}
 */
function _create${subBo.boName}()
{
  if(_precreate${subBo.boName}()){
     _commonMethodOperation('1',Txt.${subBo.beanAttribute.boInstanceName}_Create,${subBo.boName}_grid,'${subBo.beanAttribute.webPath}/${subBo.beanAttribute.boInstanceName}Controller.spr?action=_create','_create${subBo.boName}CallBack',true);
   }
   _postcreate${subBo.boName}();
}

/**
 * 打开页签，回调
 */
function _create${subBo.boName}CallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_insert${subBo.boName}Row(json);
}

 <#-- 方法 为2：弹出窗口-->	
/**
 * grid 上的 创建按钮调用方法 
 * 新增${beanAttribute.boDescription},${subBo.description}
 */
function _create${subBo.boName}m()
{
  if(_precreate${subBo.boName}()){
	_getMainFrame().ExtModalWindowUtil.show(Txt.${subBo.beanAttribute.boInstanceName}_Create,
			contextPath + '/${subBo.beanAttribute.webPath}/${subBo.beanAttribute.boInstanceName}Controller.spr?action=_create','',_create${subBo.boName}mCallBack,{width:660,height:300});
   }
   _postcreate${subBo.boName}();
}

/**
 * 弹出窗口，回调
 */
function _create${subBo.boName}mCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_insert${subBo.boName}Row(json);
}

/**
 * 向Grid插入数据。
 */
function _insert${subBo.boName}Row(json)
{
	if(json){
	var ${subBo.boName?uncap_first}Fields = new ${subBo.boName}_fields({
	<#if subBo.gridColumns?exists && (subBo.gridColumns?size>0)>
	<#list subBo.gridColumns?if_exists as column>  
		<#if column.property.keyFlag=="X" && (column.property.columnName?upper_case!="MANDT"||column.propertyName?upper_case!="CLIENT")>
		${column.propertyName}:''
		<#break>
		</#if>
	</#list>
	<#list subBo.gridColumns?if_exists as column>
		<#if column.uiType!="10" && (column.property.columnName?upper_case!="MANDT"||column.propertyName?upper_case!="CLIENT") && column.property.keyFlag!="X" >
		<#if column.property.searchHelp=="" || column.property.searchHelp==" ">
		,${column.property.propertyName}:''
		<#else>
		,${column.property.propertyName}:''
		,${column.property.propertyName}_text:''
		</#if>
		</#if>
	</#list>
	</#if>
	});		
	
		${subBo.boName}_grid.stopEditing();
		${subBo.boName}_grid.getStore().insert(0, ${subBo.boName?uncap_first}Fields);
		${subBo.boName}_grid.startEditing(0, 0);
		var record = ${subBo.boName}_grid.getStore().getAt(0);
		<#if subBo.gridColumns?exists && (subBo.gridColumns?size>0)>
		<#list subBo.gridColumns?if_exists as column>  
			<#if column.property.keyFlag=="X" && (column.property.columnName?upper_case!="MANDT"||column.propertyName?upper_case!="CLIENT")><#-- ${subBo.boName} -->
			record.set('${column.propertyName}',json["${subBo.boName}.${column.propertyName}"]);
			<#break>
			</#if>
		</#list>
		<#list subBo.gridColumns?if_exists as column>
		<#if column.uiType!="10" && (column.property.columnName?upper_case!="MANDT"||column.propertyName?upper_case!="CLIENT") && column.property.keyFlag!="X" >		
			<#if column.property.searchHelp=="" || column.property.searchHelp==" ">
			record.set('${column.propertyName}',json["${subBo.boName}.${column.propertyName}"]);
			<#else>
			record.set('${column.propertyName}',json["${subBo.boName}.${column.propertyName}"]);
			record.set('${column.propertyName}_text',json["${subBo.boName}.${column.propertyName}_htext"]);
			</#if>
		</#if>
		</#list>
		</#if>
	}
}

<#elseif toolbar.method.methodName=="_deletes">
/**
 *${beanAttribute.boDescription}行项目
 *批量删除
 */
function _deletes${subBo.boName}()
{
	if(_predeletes${subBo.boName}()){
		if (${subBo.boName}_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.${subBo.beanAttribute.boInstanceName}_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = ${subBo.boName}_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						${subBo.boName}_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.${subBo.beanAttribute.boInstanceName}_Deletes_SelectToOperation);
		}
	}
	_postdeletes${subBo.boName}();
}
<#elseif toolbar.method.methodName=="_copyCreate">
<#-- 方法 为1：打开到页签-->
/**
 *${beanAttribute.boDescription}行项目
 *复制创建
 */
function _copyCreate${subBo.boName}()
{
  if(_precopyCreate${subBo.boName}()){
		if (${subBo.boName}_grid.selModel.hasSelection() > 0){
			var records = ${subBo.boName}_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.${subBo.beanAttribute.boInstanceName}_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
		    _commonMethodOperation('1',Txt.${subBo.beanAttribute.boInstanceName}_CopyCreate,${subBo.boName}_grid,'${subBo.beanAttribute.webPath}/${subBo.beanAttribute.boInstanceName}Controller.spr?action=_copyCreate'+pars,'_create${subBo.boName}CallBack',true);
		
		}else{
			_getMainFrame().showInfo(Txt.${subBo.beanAttribute.boInstanceName}_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreate${subBo.boName}();
}


 <#-- 方法 为2：弹出窗口-->	
/**
 *${beanAttribute.boDescription}行项目
 *复制创建
 */
function _copyCreate${subBo.boName}m()
{
  if(_precopyCreate${subBo.boName}()){
		if (${subBo.boName}_grid.selModel.hasSelection() > 0){
			var records = ${subBo.boName}_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.${subBo.beanAttribute.boInstanceName}_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
			_getMainFrame().ExtModalWindowUtil.show(Txt.${subBo.beanAttribute.boInstanceName}_CopyCreate,contextPath + '/${subBo.beanAttribute.webPath}/${subBo.beanAttribute.boInstanceName}Controller.spr?action=_copyCreate'+pars,'',_create${subBo.boName}mCallBack,{width:610,height:200});
		}else{
			_getMainFrame().showInfo(Txt.${subBo.beanAttribute.boInstanceName}_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreate${subBo.boName}();
}

<#elseif toolbar.method.isDefault!="不生成在这个文件，改生成在扩展文件中X">
<#-- 生成空方法体，已经不生成在此文件，改为生成在可扩展编程JS文件中！-->

</#if>    <#-- End <#if toolbar.method.methodName=="_create">  -->

<#elseif subBo.subBoAttribute.subBoRelType=="3">
<#list bo.properties?if_exists as boProperty>
<#if boProperty.subBoRelType=="3" && boProperty.propertyName?cap_first==subBo.boName && boProperty.searchHelp!="" && boProperty.searchHelp!=" ">  <#--    CC -->
<#if toolbar.method.methodName=="_assign">  <#--    AA -->
/**
*  
*${subBo.description},grid上的增加和删除操作(搜索帮助)回调函数
*/  
function win${subBo.boName}CallBack(jsonArrayData)
{
 	for(var i=0;i<jsonArrayData.length;i++){
 		var isExists = false;
 		for (var j = 0;j<${subBo.boName}_store.getCount();j++){
<#list subBo.gridColumns?if_exists as column>
	<#if column.uiType!="10" && (column.property.columnName?upper_case!="MANDT"||column.propertyName?upper_case!="CLIENT") && column.property.keyFlag!="X">
	    <#if column.property.searchHelp!="" && column.property.searchHelp!=" ">
		   if (${subBo.boName}_store.getAt(j).get('${column.property.propertyName}') == jsonArrayData[i].${column.property.columnName})
		</#if>
	</#if>
</#list>
 				isExists = true;
 				break;
 		}

   if (isExists == false){
 	    var ${subBo.boName?cap_first}Fields = new ${subBo.boName}_fields({
<#if subBo.gridColumns?exists && (subBo.gridColumns?size>0)>
<#list subBo.gridColumns?if_exists as column>  
	<#if column.property.keyFlag=="X" && (column.property.columnName?upper_case!="MANDT"||column.propertyName?upper_case!="CLIENT") && column.uiType!="10"><#-- ${subBo.boName} -->
	        ${column.property.propertyName}:''
	        <#break>
	</#if>
</#list>
<#list subBo.gridColumns?if_exists as column>
	<#if column.uiType!="10" && (column.property.columnName?upper_case!="MANDT"||column.propertyName?upper_case!="CLIENT") && column.property.keyFlag!="X">
	    <#if column.property.searchHelp=="" || column.property.searchHelp==" ">
	       ,${column.property.propertyName}:''
		<#else>
		   ,${column.property.propertyName}:''
		   ,${column.property.propertyName}_text:''
		</#if>
	</#if>
</#list>
</#if>
	   });		
	   
	   	${subBo.boName}_grid.stopEditing();
		${subBo.boName}_grid.getStore().insert(0, ${subBo.boName?cap_first}Fields);
		${subBo.boName}_grid.startEditing(0, 0);
		var record = ${subBo.boName}_grid.getStore().getAt(0);
		<#if subBo.gridColumns?exists && (subBo.gridColumns?size>0)>
		<#list subBo.gridColumns as column>
		<#if column.property.searchHelp=="" || column.property.searchHelp==" ">
		record.set('${column.property.propertyName}',jsonArrayData[i].${column.property.columnName});
		<#else>
		record.set('${column.property.propertyName}',jsonArrayData[i].${column.property.columnName});
		record.set('${column.property.propertyName}_text',jsonArrayData[i].${column.property.shTextColumn});
		</#if>
		</#list>
		</#if>
 		}
 	}
}

/**
*  
*${subBo.description},grid上的增加和删除操作(搜索帮助)
*/  
var search${subBo.boName}HelpWin = new Ext.SearchHelpWindow({
	   	shlpName : '${boProperty.searchHelp}',
		callBack : win${subBo.boName}CallBack
});

/**
*  
*${subBo.description},grid上的增加和删除操作,弹出搜索帮助
*/  
function _assign${subBo.boName}(){
	if(_preassign${subBo.boName}()){
		search${subBo.boName}HelpWin.show();
	}
	_postassign${subBo.boName}();
}
<#elseif toolbar.method.methodName=="_deletes">

/**
*${subBo.description}
* grid上的和删除操作
*/
function _deletes${subBo.boName}(){
	if(_predeletes${subBo.boName}()){
		if (${subBo.boName}_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.${subBo.beanAttribute.boInstanceName}_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = ${subBo.boName}_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						${subBo.boName}_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.${subBo.beanAttribute.boInstanceName}_Deletes_SelectToOperation);
		}
	}
	_postdeletes${subBo.boName}();
}
<#elseif toolbar.method.isDefault!="X">
<#-- 生成空方法体，已经不生成在此文件，改为生成在可扩展编程JS文件中！-->
</#if>                                     <#-- END AA -->
</#if>                                     <#-- END CC -->
</#list>
<#-- 对象关系为：1对多(特殊) -->
<#elseif subBo.subBoAttribute.subBoRelType=="4">
<#--开始处理对象关系为：1对多(特殊)的子业务对象的，gridToolbars上的功能：-->
<#if toolbar.method.methodName=="_create">
  <#-- 方法 为1：打开到页签-->
  <#if toolbar.method.methodType=="1">
/**
 * grid 上的 创建按钮调用方法 
 * 新增${subBo.beanAttribute.boDescription}
 */
function _create${subBo.boName}()
{
if(_precreate${subBo.boName}()){
   var para = "";
<#if subBo.methods?exists && (subBo.methods?size>0)>
<#list subBo.methods as modelPro>
<#if modelPro.methodName=="_create">
<#if modelPro.methodParameters?exists && (modelPro.methodParameters?size>0)>
<#list modelPro.methodParameters as methodParameter>
<#if methodParameter.constantValue?exists && methodParameter.constantValue!="" && methodParameter.constantValue!=" ">
   var ${methodParameter.parameterName}="${methodParameter.constantValue}";
   para = para + "&${methodParameter.parameterName}=" + ${methodParameter.parameterName};
<#elseif methodParameter.parameterRef==bo.primaryKeyProperty.propertyName>
   var ${methodParameter.parameterName}=document.getElementById("${bo.boName}.${bo.primaryKeyProperty.propertyName}").value ;
   para = para + "&${methodParameter.parameterName}=" + ${methodParameter.parameterName};
</#if>
</#list>
</#if>
</#if>
</#list>
</#if>
   //增加页签
   _getMainFrame().maintabs.addPanel(Txt.${subBo.beanAttribute.boInstanceName}_Create,${subBo.beanAttribute.boName}_grid,contextPath + '/${subBo.beanAttribute.webPath}/${subBo.beanAttribute.boInstanceName}Controller.spr?action=_create'+ para,${subBo.boName?uncap_first}CallBack);
   }
   
  _postcreate${subBo.boName}();
}
  </#if> <#-- End <#if toolbar.method.methodType=="1"> -->
<#elseif toolbar.method.methodName=="_deletes">
/**
 * ${subBo.beanAttribute.boDescription}grid 上的 删除按钮调用方法，批量删除
 * 批量删除${subBo.beanAttribute.boDescription}
 */
function _deletes${subBo.boName}()
{
if(_predeletes${subBo.boName}()){
	if (${subBo.beanAttribute.boName}_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
					title:Txt.cue,
				    msg: Txt.${subBo.beanAttribute.boInstanceName}_Deletes_ConfirmOperation,
					buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = ${subBo.beanAttribute.boName}_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.${subBo.primaryKeyProperty.propertyName} + '|';
						}
						
						var param = '&${subBo.beanAttribute.boInstanceName}Ids='+ids;
						new AjaxEngine(contextPath + '/${subBo.beanAttribute.webPath}/${subBo.beanAttribute.boInstanceName}Controller.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle,callback:${subBo.boName?uncap_first}CallBack});
					}
				}
		});

		function deletesSelectedRow(result){

		}
	}else{
		_getMainFrame().showInfo(Txt.${subBo.beanAttribute.boInstanceName}_Deletes_SelectToOperation);
	}
  }
  _predeletes${subBo.boName}();
}

<#elseif toolbar.method.methodName=="_copyCreate">
<#-- 方法 为1：打开到页签-->

/**
 * ${subBo.beanAttribute.boDescription}行项目上的 复制创建按钮调用方法
 */
function _copyCreate${subBo.boName}()
{
if(_precopyCreate${subBo.boName}()){
	if (${subBo.beanAttribute.boName}_grid.selModel.hasSelection() > 0){
		var records = ${subBo.beanAttribute.boName}_grid.selModel.getSelections();
		if(records.length>1){
				_getMainFrame().showInfo(Txt.${subBo.beanAttribute.boInstanceName}_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}
		var ${subBo.primaryKeyProperty.propertyName} = records[0].json.${subBo.primaryKeyProperty.propertyName};
		_getMainFrame().maintabs.addPanel(Txt.${subBo.beanAttribute.boInstanceName}_CopyCreate,${subBo.beanAttribute.boName}_grid,contextPath + '/${subBo.beanAttribute.webPath}/${subBo.beanAttribute.boInstanceName}Controller.spr?action=_copyCreate&${subBo.primaryKeyProperty.propertyName}='+${subBo.primaryKeyProperty.propertyName});
	}else{
		_getMainFrame().showInfo(Txt.${subBo.beanAttribute.boInstanceName}_CopyCreate_SelectToOperation);
	}
 }
 _postcopyCreate${subBo.boName}();
}
<#elseif gridColumn.method.isDefault!="X">
<#-- 生成空方法体，已经不生成在此文件，改为生成在可扩展编程JS文件中！-->
</#if>    <#-- End <#if toolbar.method.methodName=="_create">  -->
<#--结束处理对象关系为：1对多(特殊)的子业务对象的，gridToolbars上的功能：-->
</#if>    <#-- End <#if subo.subBoAttribute.subBoRelType=="2">  -->
</#list>  <#-- End <#list subBo.gridToolbars as toolbar>  -->
</#if>

<#-- 开始生成grid上超级链接所用到的方法 -->
<#if subBo.gridColumns?exists && (subBo.gridColumns?size>0)>
<#list subBo.gridColumns as gridColumn>
<#if gridColumn.method?exists >
<#-- 开始生成grid上超级链接所用到的方法 -->
<#if gridColumn.method.methodName=="_delete" && gridColumn.method.methodType=="4" && subBo.subBoAttribute.subBoRelType=="4">

/**
 * grid 上的超级链接, 删除按钮调用方法
 * 删除${subBo.beanAttribute.boDescription}
 */
function _delete${subBo.boName}(id,url)
{
	if(_predelete${subBo.boName}()){
		url = url + '&${subBo.primaryKeyProperty.propertyName}=' + id;
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.${beanAttribute.boInstanceName}_Delete_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
				    if (result == 'yes'){
						new AjaxEngine(contextPath + "/"+ url,{method:"post", parameters: [], onComplete: callBackHandle,callback:${subBo.boName?uncap_first}CallBack});
					}
				}
		});
	}
	_postdelete${subBo.boName}();
}
<#elseif gridColumn.method.methodName=="_edit"  && subBo.subBoAttribute.subBoRelType!="4">
<#-- 方法 为1：打开到页签 ,回调函数-->

function _edit${subBo.boName}pCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_modify${subBo.boName}Row(json);
}

<#-- 方法 为2：弹出窗口 ,回调函数-->

function _edit${subBo.boName}mCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modify${subBo.boName}Row(json);
}

<#-- 方法 为4：JS函数-->
/**
  * ${beanAttribute.boDescription}行项目编辑操作
  */
function _edit${subBo.boName}(id,url)
{
	if(_preedit${subBo.boName}()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = ${subBo.boName}_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.${subBo.beanAttribute.boInstanceName}_Edit,	url,'',_edit${subBo.boName}CallBack,{width:660,height:300});
      
      }
      _postedit${subBo.boName}();
}
<#-- 方法 为4：JS函数，回调函数-->
function _edit${subBo.boName}CallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modify${subBo.boName}Row(json);
}

/**
 * 行项目编辑后的grid更新
 */
function _modify${subBo.boName}Row(json)
{
	if(json){
		var records = ${subBo.boName}_grid.selModel.getSelections();
		var record = records[0];
		<#if subBo.gridColumns?exists && (subBo.gridColumns?size>0)>
		<#list subBo.gridColumns as column>
		<#if column.uiType!="10" && column.uiType!="13">
		record.set('${column.property.propertyName}',json["${subBo.boName}.${column.property.propertyName}"]);
		<#else>
		</#if>
		</#list>
		</#if>
	}
}
<#elseif gridColumn.method.methodName=="_view" && subBo.subBoAttribute.subBoRelType!="4">
<#-- 方法 为4：JS函数-->
/**
  * ${beanAttribute.boDescription}行项目查看操作
  */
function _view${subBo.boName}(id,url)
{
	if(_preview${subBo.boName}()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = ${subBo.boName}_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.${subBo.beanAttribute.boInstanceName}_View,	url,'','',{width:660,height:300});
	}
	_postview${subBo.boName}();
}
<#-- 方法 为1：打开到页签 ,回调函数-->
/**
  * ${beanAttribute.boDescription}行项目查看，打开到页签 ,回调函数
  */
function _view${subBo.boName}pCallBack()
{
}

<#-- 方法 为2：弹出窗口 ,回调函数-->
/**
  * ${beanAttribute.boDescription}行项目查看，弹出窗口 ,回调函数
  */
function _view${subBo.boName}mCallBack()
{
}
<#elseif subBo.subBoAttribute.subBoRelType!="4" && gridColumn.method.isDefault!="X">
<#-- 生成空方法体，已经不生成在此文件，改为生成在可扩展编程JS文件中！-->

</#if>
</#if>    <#-- End <#if gridColumn.method?exists> -->
</#list>
</#if>
</#if>    <#-- End <#if subBo.boName!="Attachement">  -->
</#list>  <#-- End <#list subBoList as subBo> -->
</#if>

<#--开始处理表单工具栏上的按钮 -->
<#if  bo.formToolbars?exists && ( bo.formToolbars?size>0)>
<#list bo.formToolbars as formToolbar>          <#-- 遍历业务对象下所有方法 -->
<#if formToolbar.method?exists && (formToolbar?size>0)>
<#if formToolbar.method.methodName=="_saveOrUpdate"> 					 
/**
 * 保存 
 */
function _saveOrUpdate${beanAttribute.boName}()
{
if(_presaveOrUpdate${beanAttribute.boName}()){
	var param = Form.serialize('mainForm');	
<#if bo.formColumnListTab?exists && (bo.formColumnListTab?size>0)>	
param = param + "&"+ Form.serialize('${beanAttribute.boInstanceName}Form');;
</#if>	   
<#if subBoList?exists && (subBoList?size>0)>
	<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象 --> 
		   <#list bo.properties as property>
		        <#if property.subBoName==subBo.boName> 
		            <#if property.subBoName="Attachement">
						param = param + ""+ get${property.propertyName}${subBo.boName}GridData();     
		        	<#elseif property.subBoRelType=="2" || property.subBoRelType=="3">         <#-- 业务对象子对象关系为 1对多 -->
		        		param = param + ""+ get${subBo.boName}GridData();
		        	<#elseif property.subBoRelType=="1"> <#-- 业务对象子对象关系为 1对1 -->
		        		param = param +  "&"+ Form.serialize('${subBo.beanAttribute.boInstanceName}Form');		
		            </#if>
		        </#if>
		   </#list>
	</#list>
</#if>
    //alert(param);
	new AjaxEngine(contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
   }
   _postsaveOrUpdate${beanAttribute.boName}();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	//var id = responseUtil.getCustomField('coustom');
	var ${bo.primaryKeyProperty.propertyName}=result.${bo.primaryKeyProperty.propertyName};
	//document.getElementById("${beanAttribute.boName}.${bo.primaryKeyProperty.propertyName}").value = id;
<#-- 回填最后修改人、最后修改时间 -->	
<#list bo.formColumns?if_exists as formColumn><#-- 遍历业务对象下所有属性 -->
<#if formColumn.property.columnName=="CREATOR" ||  formColumn.property.columnName=="LASTMODIFYER" ||  formColumn.property.columnName=="LASTMODIFYOR"> 
	<#if formColumn.visibility=="X">
	document.getElementById("${beanAttribute.boName}.${formColumn.property.propertyName}_text").value = result.${formColumn.property.propertyName}_text;
	</#if>
	document.getElementById("${beanAttribute.boName}.${formColumn.property.propertyName}").value = result.${formColumn.property.propertyName};
<#elseif formColumn.property.columnName=="CREATETIME" || formColumn.property.columnName=="LASTMODIFYTIME">
	document.getElementById("${beanAttribute.boName}.${formColumn.property.propertyName}").value = result.${formColumn.property.propertyName};
</#if>
</#list>
<#-- 新增保存回调，回填所有业务对象子对象的主键ID值到页面上-->
<#if subBoList?exists && (subBoList?size>0)> 
<#list subBoList as subBo>
<#if subBo.subBoAttribute.subBoRelType=="1">
<#elseif subBo.subBoAttribute.subBoRelType=="2" || subBo.subBoAttribute.subBoRelType=="3" || subBo.subBoAttribute.subBoRelType=="4">
	<#if subBo.boName!="Attachement">
	reload_${subBo.boName}_grid("defaultCondition=${subBo.tableName}.${bo.primaryKeyProperty.columnName}='"+ ${bo.primaryKeyProperty.propertyName} +"'");
	<#else>
<#list bo.properties as property>
 <#if property.subBoName==subBo.boName> 
	reload_${property.propertyName}${subBo.boName}_grid("defaultCondition=${subBo.tableName}.BUSINESSID='"+ ${bo.primaryKeyProperty.propertyName} +"' <#if property.propertyName?exists && property.propertyName!="" && property.propertyName!=" " >and YATTACHEMENT.BOPROPERTY='${property.propertyName}'</#if>");
 </#if>
</#list>
	</#if>
</#if>  
</#list>  
</#if>  	
}
<#elseif formToolbar.method.methodName=="_create"> 

/**
 * 创建按钮调用方法 
 * 新增${beanAttribute.boDescription}
 */
function _create${beanAttribute.boName}()
{
	if(_precreate${beanAttribute.boName}()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.${beanAttribute.boInstanceName}_Create,'',contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_create');
     }
     _postcreate${beanAttribute.boName}();
}
<#elseif formToolbar.method.methodName=="_copyCreate"> 

function _copyCreate${beanAttribute.boName}()
{
   if(_precopyCreate${beanAttribute.boName}()){
		_getMainFrame().maintabs.addPanel(Txt.${beanAttribute.boInstanceName}_CopyCreate,'',contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_copyCreate&${bo.primaryKeyProperty.propertyName}='+${bo.primaryKeyProperty.propertyName});
   }
   _postcopyCreate${beanAttribute.boName}();
}
<#elseif formToolbar.method.methodName=="_cancel"> 
/**
 * 取消
 */
function _cancel${beanAttribute.boName}()
{
  if(_precancel${beanAttribute.boName}()){
	new AjaxEngine(contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_cancel&${bo.primaryKeyProperty.propertyName}='+${bo.primaryKeyProperty.propertyName}, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancel${beanAttribute.boName}CallBack});
   }
   _postcancel${beanAttribute.boName}();
}
function _cancel${beanAttribute.boName}CallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
<#elseif formToolbar.method.methodName=="_delete"> 

/**
 * 删除${beanAttribute.boDescription}
 */
function _delete${beanAttribute.boName}()
{
if(_predelete${beanAttribute.boName}()){
	_getMainFrame().Ext.MessageBox.show({
		title:Txt.cue,
		msg: Txt.${beanAttribute.boInstanceName}_Delete_ConfirmOperation,
		buttons: {yes:Txt.ok, no:Txt.cancel},
		icon: Ext.MessageBox.QUESTION,
		fn:function(result){
			if (result == 'yes'){
				var param = '&${bo.primaryKeyProperty.propertyName}='+${bo.primaryKeyProperty.propertyName};
				new AjaxEngine(contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_delete', 
				{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
			}
		 }
	});
  }
  _postdelete${beanAttribute.boName}();
}

/**
 * 删除成功后的回调动作
 */
function _deleteCallBackHandle()
{
	_getMainFrame().showInfo(Txt.operateSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

<#elseif formToolbar.method.methodName=="_create"> 

/**
 * 创建按钮调用方法 
 * 新增${beanAttribute.boDescription}
 */
function _create${beanAttribute.boName}()
{
	if(_precreate${beanAttribute.boName}()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.${beanAttribute.boInstanceName}_Create,${beanAttribute.boName}_grid,contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_create');
	 }
	 _postcreate${beanAttribute.boName}();
}
<#elseif formToolbar.method.methodName=="_copyCreate"> 
function _copyCreate${beanAttribute.boName}()
{
  if(_precopyCreate${beanAttribute.boName}()){
	var ${bo.primaryKeyProperty.propertyName}= records[0].json.${bo.primaryKeyProperty.propertyName};
	_getMainFrame().maintabs.addPanel(Txt.${beanAttribute.boInstanceName}_CopyCreate,${beanAttribute.boName}_grid,contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_copyCreate&${bo.primaryKeyProperty.propertyName}='+${bo.primaryKeyProperty.propertyName});
  }
  _postcopyCreate${beanAttribute.boName}();
}

<#elseif formToolbar.method.methodName=="_submitProcess"  && bo.haveProcess> 

/**
 * 提交
 */
function _submitProcess${beanAttribute.boName}()
{
if(_presubmitProcess${beanAttribute.boName}()){
	var param = Form.serialize('mainForm');	
<#if bo.formColumnListTab?exists && (bo.formColumnListTab?size>0)>	
	param = param + "&"+ Form.serialize('${beanAttribute.boInstanceName}Form');
</#if>	
<#if subBoList?exists && (subBoList?size>0)>
<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象 -->
	   <#list bo.properties as property>
	        <#if property.subBoName==subBo.boName> 
                <#if property.subBoName="Attachement">
	param = param + ""+ getAll${property.propertyName}${subBo.boName}GridData();   
	        	<#elseif property.subBoRelType=="2" || property.subBoRelType=="3">         <#-- 业务对象子对象关系为 1对多 -->
	param = param + "&" + getAll${subBo.boName}GridData();
	        	<#elseif property.subBoRelType=="1"> <#-- 业务对象子对象关系为 1对1 -->
	param = param + "&" + Form.serialize('${subBo.beanAttribute.boInstanceName}Form');		
	            </#if>
	        </#if>
	   </#list>
</#list>
</#if>
	param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
  }
  _postsubmitProcess${beanAttribute.boName}();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
<#elseif formToolbar.method.isDefault!="X">
<#-- 生成空方法体，已经不生成在此文件，改为生成在可扩展编程JS文件中！-->
</#if>
</#if>
</#list>
</#if>
<#--结束处理表单工具栏上的按钮 -->