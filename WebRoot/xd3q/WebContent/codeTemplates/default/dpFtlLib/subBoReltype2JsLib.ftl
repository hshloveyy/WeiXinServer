<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  业务对象关联类型为2,1:N,子业务对象Grid ToolBar上按钮事件句柄及JS扩展
  - <功能>
--%>
<#--  生成子业务对象(关系类型为2),Grid Toolbar上按钮事件句柄-->
<#macro generatorSubBoGridToolbarActionJs toolbar subBo>
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

function _insert${subBo.boName}Row(json)
{
	if(json){
	var ${subBo.boName?uncap_first}Fields = new ${subBo.boName}_fields({
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
	});		

		${subBo.boName}_grid.stopEditing();
		${subBo.boName}_grid.getStore().insert(0, ${subBo.boName?uncap_first}Fields);
		${subBo.boName}_grid.startEditing(0, 0);
		var record = ${subBo.boName}_grid.getStore().getAt(0);
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
	}
}	
<#elseif toolbar.method.methodName=="_deletes">
/**
 *${beanAttribute.boDescription}行项目
 *批量删除
 */
function _deletes${subBo.boName}()
{
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

<#else>
<#-- 生成空方法体，已经不生成在此文件，改为生成在可扩展编程JS文件中！-->
</#if>    <#-- End <#if toolbar.method.methodName=="_create">  -->
</#macro>


<#--  生成子业务对象(关系类型为2),Grid Toolbar上按钮事件句柄所用到的2次开发扩展句柄。-->
<#macro generatorSubBoGridToolbarActionExtendJs toolbar subBo>
<#if toolbar.method.methodName=="_create">
  <#-- 方法 为1：打开到页签-->
  <#if toolbar.method.methodType=="1">
/**
 * grid 上的 创建按钮调用方法 
 * 新增${beanAttribute.boDescription},${subBo.description}
 */
function _precreate${subBo.boName}()
{
   return true;
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增${beanAttribute.boDescription},${subBo.description}
 */
function _postcreate${subBo.boName}()
{

}
	</#if> <#-- End <#if toolbar.method.methodType=="1"> -->
<#elseif toolbar.method.methodName=="_deletes">

/**
 *${beanAttribute.boDescription}行项目
 *批量删除
 */
function _predeletes${subBo.boName}()
{
	return true;
}

/**
 *${beanAttribute.boDescription}行项目
 *批量删除
 */
function _postdeletes${subBo.boName}()
{

}
<#elseif toolbar.method.methodName=="_copyCreate">
/**
 *${beanAttribute.boDescription}行项目
 *复制创建
 */
function _precopyCreate${subBo.boName}()
{
    return true;
}

/**
 *${beanAttribute.boDescription}行项目
 *复制创建
 */
function _postcopyCreate${subBo.boName}()
{

}
<#else>
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
</#if>    <#-- End <#if toolbar.method.methodName=="_create">  -->
</#macro>