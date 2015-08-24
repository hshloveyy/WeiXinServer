<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>
--%>
<#-- 一、标准对象管理页面Form。############################################-->
<#-- 生成Manage页面Gen JS脚本-->
<#macro generatorCommonObjectTypeManageJsGen>
/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
<#if bo.formSearchColumnRange?exists && (bo.formSearchColumnRange?size>0)>	
<#list bo.formSearchColumnRange as gridColumn>
<#if gridColumn.uiType=='04' || gridColumn.uiType=='05'> <#-- 日期类型 -->
    var ${gridColumn.property.propertyName}Max = ${'$'}('${gridColumn.property.propertyName}_maxValue').value;
    var ${gridColumn.property.propertyName}Min = ${'$'}('${gridColumn.property.propertyName}_minValue').value;
    if(${gridColumn.property.propertyName}Max && ${gridColumn.property.propertyName}Min)
    {
	    if(${gridColumn.property.propertyName}Max<${gridColumn.property.propertyName}Min)
	    {
           _getMainFrame().showInfo(Txt.${gridColumn.property.propertyName}_EndDateShouldLargerStartDate);
	       return;
	    }
    }
    
<#elseif gridColumn.dataType=='N'><#-- 数字类型 -->
    var ${gridColumn.property.propertyName}Max = ${'$'}('${gridColumn.property.propertyName}_maxValue').value;
    var ${gridColumn.property.propertyName}Min = ${'$'}('${gridColumn.property.propertyName}_minValue').value;
    
    if(!Utils.isEmpty(${gridColumn.property.propertyName}Max) & !Utils.isNumber(${gridColumn.property.propertyName}Max))
    {
       _getMainFrame().showInfo(Txt.${gridColumn.property.propertyName}_shouldBeNumber);
       return;
    }
    if(!Utils.isEmpty(${gridColumn.property.propertyName}Min) & !Utils.isNumber(${gridColumn.property.propertyName}Min))
    {
       _getMainFrame().showInfo(Txt.${gridColumn.property.propertyName}_shouldBeNumber);
       return;
    }
       
	if(!Utils.isEmpty(${gridColumn.property.propertyName}Max) && !Utils.isEmpty(${gridColumn.property.propertyName}Min))
	{
       _getMainFrame().showInfo(Txt.${gridColumn.property.propertyName}_fromShouldLessThanTo);
	   return;
	}
    
</#if>
</#list>
</#if>
	reload_${beanAttribute.boName}_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_${beanAttribute.boName}_grid("");
}

/**
 * 清空操作
 */
function _resetForm()
{
  if(_preresetForm()){
		document.all.mainForm.reset();
	}
	_postresetForm();
}
<#-- 开始生成grid上超级链接所用到的方法 -->
<#if bo.methods?exists && (bo.methods?size>0)> 
<#list bo.methods as modelPro>
<#if modelPro.methodName=="_delete" && modelPro.methodType=="4">

/**
 * grid 上的超级链接, 删除按钮调用方法
 * 删除${beanAttribute.boDescription}
 */
function _delete${bo.boName}(id,url)
{
  if(_predelete${bo.boName}()){
	    url = url + '&${bo.primaryKeyProperty.propertyName}=' + id;
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.${beanAttribute.boInstanceName}_Delete_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
				   if (result == 'yes'){
						var param = '';
						new AjaxEngine(contextPath + "/" + url,{method:"post", parameters: [], onComplete: callBackHandle});
					}
				}
		});
	}
	_postdelete${bo.boName}();
}
</#if>
</#list>
</#if>

<#-- 开始生成gridToolbars上所用到的方法 -->

<#if bo.gridToolbars?exists && (bo.gridToolbars?size>0)>
<#list bo.gridToolbars as toolbar>
<#if toolbar.methodName=="_create" >

/**
 * grid 上的 创建按钮调用方法 
 * 新增${beanAttribute.boDescription}
 */
function _create()
{
	if(_precreate()){
	   var para = "";
	<#if bo.methods?exists && (bo.methods?size>0)>
	<#list bo.methods as modelPro>
	<#if modelPro.methodName=="_create">
	<#if modelPro.methodParameters?exists && (modelPro.methodParameters?size>0)>
	<#list modelPro.methodParameters as methodParameter>
	<#if methodParameter.constantValue?exists && methodParameter.constantValue!="" && methodParameter.constantValue!=" ">
	   var ${methodParameter.parameterName}="${methodParameter.constantValue}";
	   para = para + "&${methodParameter.parameterName}=" + ${methodParameter.parameterName};
	</#if>
	</#list>
	</#if>
	<#elseif modelPro.methodName=="_manage">
	<#if modelPro.methodParameters?exists && (modelPro.methodParameters?size>0)>
	   //开始取得从_manage网址上带过来的参数，并传递给控制器_create方法。
	<#list modelPro.methodParameters as methodParameter>
	   para = para + "&${methodParameter.parameterName}=" + ${methodParameter.parameterName};
	</#list>
	</#if>
	</#if>
	</#list>
	</#if>
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.${beanAttribute.boInstanceName}_Create,${beanAttribute.boName}_grid,contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_create'+ para);
    }
    _postcreate();
}
<#elseif toolbar.methodName=="_deletes">

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除${beanAttribute.boDescription}
 */
function _deletes()
{
if(_precreate()){
	if (${beanAttribute.boName}_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.${beanAttribute.boInstanceName}_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = ${beanAttribute.boName}_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.${bo.primaryKeyProperty.propertyName} + '|';
						}
		
						var param = '&${beanAttribute.boInstanceName}Ids='+ids;
						new AjaxEngine(contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo(Txt.${beanAttribute.boInstanceName}_Deletes_SelectToOperation);
	}
   }
  _postcreate();
}
<#elseif toolbar.methodName=="_copyCreate">

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
if(_precopyCreate()){
	if (${beanAttribute.boName}_grid.selModel.hasSelection() > 0){
		var records = ${beanAttribute.boName}_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo(Txt.${beanAttribute.boInstanceName}_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}
		var ${bo.primaryKeyProperty.propertyName} = records[0].json.${bo.primaryKeyProperty.propertyName};
		_getMainFrame().maintabs.addPanel(Txt.${beanAttribute.boInstanceName}_CopyCreate,${beanAttribute.boName}_grid,contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_copyCreate&${bo.primaryKeyProperty.propertyName}='+${bo.primaryKeyProperty.propertyName});
	}else{
		_getMainFrame().showInfo(Txt.${beanAttribute.boInstanceName}_CopyCreate_SelectToOperation);
	}	
  }
  _postcopyCreate();
}
</#if>
</#list>
</#if>
</#macro>

<#-- 二、标准对象管理页面Form。############################################-->
<#-- 生成Manage页面JS脚本-->
<#macro generatorCommonObjectTypeManageJs>
/**
 * 查询动作执行前
 */
function _presearch()
{
	return true;
}

/**
 * 查询动作执行后
 * 当 _presearch() 返回 false 时候则执行本函数。
 */
function _postsearch()
{

}


/**
 * 清空操作
 */
function _preresetForm()
{
	return true;
}
/**
 * 清空操作
 */
function _postresetForm()
{

}
<#-- 开始生成grid上超级链接所用到的方法 -->
<#if bo.methods?exists && (bo.methods?size>0)> 
<#list bo.methods as modelPro>
<#if modelPro.methodName=="_delete" && modelPro.methodType=="4">

/**
 * grid 上的超级链接, 删除按钮调用方法
 * 删除${beanAttribute.boDescription}
 */
function _predelete${bo.boName}(id,url)
{
  return true;
}

/**
 * grid 上的超级链接, 删除按钮调用方法
 * 删除${beanAttribute.boDescription}
 */
function _postdelete${bo.boName}(id,url)
{

}
</#if>
</#list>
</#if>

<#-- 开始生成gridToolbars上所用到的方法 -->
<#list gridToolbars as toolbar>
<#if toolbar?exists && (toolbar?size>0)>
<#if toolbar.methodName=="_create" >

/**
 * grid 上的 创建按钮调用方法 
 * 新增${beanAttribute.boDescription}
 */
function _precreate()
{
	 return true ;
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增${beanAttribute.boDescription}
 */
function _postcreate()
{

}
<#elseif toolbar.methodName=="_deletes">

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除${beanAttribute.boDescription}
 */
function _predeletes()
{
   return true ;
}
/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除${beanAttribute.boDescription}
 */
function _postdeletes()
{

}
<#elseif toolbar.methodName=="_copyCreate">

/**
 * grid上的 复制创建按钮调用方法
 */
function _precopyCreate()
{
	return true ;
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _postcopyCreate()
{

}
</#if>
</#if>
</#list>
</#macro>
