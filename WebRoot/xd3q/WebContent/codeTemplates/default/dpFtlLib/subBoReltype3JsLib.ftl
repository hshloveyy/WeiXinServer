<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  业务对象关联类型为3,1:N:1,子业务对象Grid ToolBar上按钮事件句柄及JS扩展
  - <功能>
--%>
<#-- 一、生成子业务对象(关系类型为3),Grid Toolbar上按钮事件句柄#########################-->
<#macro generatorSubBoGridToolbarActionJs toolbar subBo>
<#if bo.properties?exists && (bo.properties?size>0)> 
<#list bo.properties?if_exists as boProperty>
<#if boProperty.subBoRelType=="3" && boProperty.subBoName==subBo.boName && boProperty.searchHelp!="" && boProperty.searchHelp!=" ">  <#--    CC -->
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
</#if>
<#if subBo.gridColumns?exists && (subBo.gridColumns?size>0)> 
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
<#-- 生成空方法体 ，已经不生成在此文件，改为生成在可扩展编程JS文件中！-->
</#if>                                     <#-- END AA -->
</#if>                                     <#-- END CC -->
</#list>
</#if>
</#macro>


<#-- 二、生成子业务对象(关系类型为3),Grid Toolbar上按钮事件句柄所用到的2次开发扩展句柄。#########################-->
<#macro generatorSubBoGridToolbarActionExtendJs toolbar subBo>
<#if bo.properties?exists && (bo.properties?size>0)> 
<#list bo.properties?if_exists as boProperty>
<#if boProperty.subBoRelType=="3" && boProperty.subBoName==subBo.boName && boProperty.searchHelp!="" && boProperty.searchHelp!=" ">  <#--    CC -->
<#if toolbar.method.methodName=="_assign">  <#--    AA -->

/**
 *  
 *${subBo.description},grid上的增加和删除操作,弹出搜索帮助
 */  
function _preassign${subBo.boName}(){
  return true ;
}

/**
 *  
 *${subBo.description},grid上的增加和删除操作,弹出搜索帮助
 */  
function _postassign${subBo.boName}(){

}
<#elseif toolbar.method.methodName=="_deletes">

/**
 *${subBo.description}
 * grid上的和删除操作
 */
function _predeletes${subBo.boName}(){
	return true ;
}

/**
 *${subBo.description}
 * grid上的和删除操作
 */
function _postdeletes${subBo.boName}(){

}
<#elseif toolbar.method.isDefault!="X">
<#-- 生成空方法体-->
/**
 *  ${toolbar.method.methodDesc}
 */
function ${toolbar.method.methodName}${subBo.boName}()
{
   if(pre${toolbar.method.methodName}${subBo.boName}()){
   //方法执行体
   }
   post${toolbar.method.methodName}${subBo.boName}();
}

/**
 *  ${toolbar.method.methodDesc}
 */
function pre${toolbar.method.methodName}${subBo.boName}()
{
	return true ;
}

/**
 *  ${toolbar.method.methodDesc}
 */
function post${toolbar.method.methodName}${subBo.boName}()
{

}
</#if>                                     <#-- END AA -->
</#if>                                     <#-- END CC -->
</#list>
</#if>
</#macro>