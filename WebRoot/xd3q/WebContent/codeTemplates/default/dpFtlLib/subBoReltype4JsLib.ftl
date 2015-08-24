<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  业务对象关联类型为4,1对多(特殊),子业务对象Grid ToolBar上按钮事件句柄及JS扩展
  - <功能>
--%>
<#--一、生成子业务对象(关系类型为4),Grid Toolbar上按钮事件句柄##########################-->
<#macro generatorSubBoGridToolbarActionJs toolbar subBo>
<#--开始处理对象关系为：1对多(特殊)的子业务对象的，gridToolbars上的功能：-->
<#if toolbar.method.methodName=="_create">
  <#-- 方法 为1：打开到页签-->
/**
 * grid 上的 创建按钮调用方法 
 * 新增${subBo.beanAttribute.boDescription}
 */
function _create${subBo.boName}()
{
	if(_precreate${subBo.boName}()){
		   var id= document.getElementById("${bo.boName}.${bo.primaryKeyProperty.propertyName}").value;
		   if(!id)
		   {
		   	 _getMainFrame().showInfo('请先保存${bo.beanAttribute.boDescription}！');
		   	 return ;
		   }
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
		   _getMainFrame().maintabs.addPanel(Txt.${subBo.beanAttribute.boInstanceName}_Create,${subBo.beanAttribute.boName}_grid,contextPath + '/${subBo.beanAttribute.webPath}/${subBo.beanAttribute.boInstanceName}Controller.spr?action=_create'+ para,${subBo.boName?uncap_first}CallBack,true);
       }
       _postcreate${subBo.boName}();
}

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
	
		}else{
			_getMainFrame().showInfo(Txt.${subBo.beanAttribute.boInstanceName}_Deletes_SelectToOperation);
		}
	}
	_postdeletes${subBo.boName}();
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

<#elseif toolbar.method.isDefault!="X">
<#-- 生成空方法体，已经不生成在此文件，改为生成在可扩展编程JS文件中！-->
</#if>    <#-- End <#if toolbar.method.methodName=="_create">  -->
<#--结束处理对象关系为：1对多(特殊)的子业务对象的，gridToolbars上的功能：-->
</#macro>

<#-- 二、生成子业务对象(关系类型为4),Grid Toolbar上按钮事件句柄所用到的2次开发扩展句柄。#########################-->
<#macro generatorSubBoGridToolbarActionExtendJs toolbar subBo>
<#--开始处理对象关系为：1对多(特殊)的子业务对象的，gridToolbars上的功能：-->
<#if toolbar.method.methodName=="_create">
  <#-- 方法 为1：打开到页签-->
  <#if toolbar.method.methodType=="1">
/**
 * grid 上的 创建按钮调用方法 
 * 新增${subBo.beanAttribute.boDescription}
 */
function _precreate${subBo.boName}()
{
   return true ;
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增${subBo.beanAttribute.boDescription}
 */
function _postcreate${subBo.boName}()
{

}
  </#if> <#-- End <#if toolbar.method.methodType=="1"> -->
<#elseif toolbar.method.methodName=="_deletes">
/**
 * ${subBo.beanAttribute.boDescription}grid 上的 删除按钮调用方法，批量删除
 * 批量删除${subBo.beanAttribute.boDescription}
 */
function _predeletes${subBo.boName}()
{
	return true ;
}

/**
 * ${subBo.beanAttribute.boDescription}grid 上的 删除按钮调用方法，批量删除
 * 批量删除${subBo.beanAttribute.boDescription}
 */
function _postdeletes${subBo.boName}()
{

}

<#elseif toolbar.method.methodName=="_copyCreate">
/**
 * ${subBo.beanAttribute.boDescription}行项目上的 复制创建按钮调用方法
 */
function _precopyCreate${subBo.boName}()
{
	return true ;
}

/**
 * ${subBo.beanAttribute.boDescription}行项目上的 复制创建按钮调用方法
 */
function _postcopyCreate${subBo.boName}()
{

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
</#if>    <#-- End <#if toolbar.method.methodName=="_create">  -->
<#--结束处理对象关系为：1对多(特殊)的子业务对象的，gridToolbars上的功能：-->
</#macro>