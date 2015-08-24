<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>
--%>

<#import "/dpFtlLib/subBoReltype2JsLib.ftl" as subBoReltype2JsLib>
<#import "/dpFtlLib/subBoReltype3JsLib.ftl" as subBoReltype3JsLib>
<#import "/dpFtlLib/subBoReltype4JsLib.ftl" as subBoReltype4JsLib>
<#import "/dpFtlLib/boFormToolBarJsLib.ftl" as boFormToolBarJsLib>

<#-- 一、生成子业务对象Grid Toolbar上按钮事件句柄############################################-->
<#macro generatorSubBoGridToolbarJs subBusinessObject>
<#if subBusinessObject.boName!="Attachement">   <#-- 业务对象名字判断，业务附件Attachement不处理 -->
<#list subBusinessObject.gridToolbars as gridToolbar>
<#if subBusinessObject.subBoAttribute.subBoRelType=="2">
<#-- 生成子业务对象(关系类型为2,1对多)Grid Toolbar上按钮事件句柄 -->
<@subBoReltype2JsLib.generatorSubBoGridToolbarActionJs toolbar=gridToolbar subBo=subBusinessObject/>
<#elseif subBusinessObject.subBoAttribute.subBoRelType=="3">
<#-- 生成子业务对象(关系类型为3,1:N:1)Grid Toolbar上按钮事件句柄 -->
<@subBoReltype3JsLib.generatorSubBoGridToolbarActionJs toolbar=gridToolbar subBo=subBusinessObject/>
<#-- 对象关系为：1对多(特殊) -->
<#elseif subBusinessObject.subBoAttribute.subBoRelType=="4">
<#-- 生成子业务对象(关系类型为4,1对多(特殊))Grid Toolbar上按钮事件句柄 -->
<@subBoReltype4JsLib.generatorSubBoGridToolbarActionJs toolbar=gridToolbar subBo=subBusinessObject/>
</#if>    <#-- End <#if subBusinessObject.subBoAttribute.subBoRelType=="2">  -->
</#list>  <#-- End <#list subBusinessObject.gridToolbars as toolbar>  -->
</#if>    <#-- End <#if (toolbar.size>0)>  -->
</#macro>

<#-- 二、生成子业务对象Grid Toolbar上按钮事件2次开发句柄############################################-->
<#macro generatorSubBoGridToolbarExtendJs subBusinessObject>
<#if subBusinessObject.boName!="Attachement">   <#-- 业务对象名字判断，业务附件Attachement不处理 -->
<#list subBusinessObject.gridToolbars as gridToolbar>
<#if subBusinessObject.subBoAttribute.subBoRelType=="2">
<#-- 生成子业务对象(关系类型为2,1对多)Grid Toolbar上按钮事件2次开发扩展句柄 -->
<@subBoReltype2JsLib.generatorSubBoGridToolbarActionExtendJs toolbar=gridToolbar subBo=subBusinessObject/>
<#elseif subBusinessObject.subBoAttribute.subBoRelType=="3">
<#-- 生成子业务对象(关系类型为3,1:N:1)Grid Toolbar上按钮事件2次开发扩展句柄 -->
<@subBoReltype3JsLib.generatorSubBoGridToolbarActionExtendJs toolbar=gridToolbar subBo=subBusinessObject/>
<#-- 对象关系为：1对多(特殊) -->
<#elseif subBusinessObject.subBoAttribute.subBoRelType=="4">
<#-- 生成子业务对象(关系类型为4,1对多(特殊))Grid Toolbar上按钮2次开发扩展句柄 -->
<@subBoReltype4JsLib.generatorSubBoGridToolbarActionExtendJs toolbar=gridToolbar subBo=subBusinessObject/>
</#if>    <#-- End <#if subBusinessObject.subBoAttribute.subBoRelType=="2">  -->
</#list>  <#-- End <#list subBusinessObject.gridToolbars as toolbar>  -->
</#if>    <#-- End <#if (toolbar.size>0)>  -->
</#macro>

<#--三、生成子业务对象Grid上超级链接所用到的事件句柄######################################## -->
<#macro generatorSubBoGridUrlLinksJs subBo>
<#if subBo.gridColumns?exists &&(subBo.gridColumns?size>0)>
<#list subBo.gridColumns as gridColumn>
<#if gridColumn.method?exists &&(gridColumn.method?size>0) && subBo.boName!="Attachement">
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
<#elseif gridColumn.method.methodName=="_edit" && subBo.subBoAttribute.subBoRelType!="4">
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
</#if>
</#list>
</#if>    <#-- End <#if gridColumn.method?exists> -->
</#macro>

<#--四、生成子业务对象Grid上超级链接所用到2此开发扩展句柄######################################## -->
<#macro generatorSubBoGridUrlLinksExtendJs subBo>
<#if subBo.gridColumns?exists &&(subBo.gridColumns?size>0)>
<#list subBo.gridColumns as gridColumn>
<#if gridColumn.method?exists &&(gridColumn.method?size>0) && subBo.boName!="Attachement">
<#-- 开始生成grid上超级链接所用到的方法 -->
<#if gridColumn.method.methodName=="_delete" && gridColumn.method.methodType=="4" && subBo.subBoAttribute.subBoRelType=="4">

/**
 * grid 上的超级链接, 删除按钮调用方法
 * 删除${subBo.beanAttribute.boDescription}
 */
function _predelete${subBo.boName}(id,url)
{
	return true ;
}


/**
 * grid 上的超级链接, 删除按钮调用方法
 * 删除${subBo.beanAttribute.boDescription}
 */
function _postdelete${subBo.boName}(id,url)
{

}
<#elseif gridColumn.method.methodName=="_edit" && subBo.subBoAttribute.subBoRelType!="4">

/**
  * ${beanAttribute.boDescription}行项目编辑操作
  */
function _preedit${subBo.boName}(id,url)
{
   return true ;
}

/**
  * ${beanAttribute.boDescription}行项目编辑操作
  */
function _postedit${subBo.boName}(id,url)
{

}

<#elseif gridColumn.method.methodName=="_view" && subBo.subBoAttribute.subBoRelType!="4">

/**
  * ${beanAttribute.boDescription}行项目查看操作
  */
function _preview${subBo.boName}(id,url)
{
   return true ;
}

/**
  * ${beanAttribute.boDescription}行项目查看操作
  */
function _postview${subBo.boName}(id,url)
{

}
<#elseif subBo.subBoAttribute.subBoRelType!="4" && gridColumn.method.isDefault!="X">
<#-- 生成空方法体-->
/**
 *  ${gridColumn.method.methodDesc}
 */
function pre${gridColumn.method.methodName}()
{
	return true;
}

/**
 *  ${gridColumn.method.methodDesc}
 */
function post${gridColumn.method.methodName}()
{

} 
</#if>
</#if>
</#list>
</#if>    <#-- End <#if gridColumn.method?exists> -->
</#macro>


<#--五、生成主业务对象(新增、编辑、查看、管理(树类型对象)页面)FormToolbar上按钮事件句柄######################################## -->
<#macro generatorBoFormToolbarJs page>
<@boFormToolBarJsLib.generatorBoFormToolbarJs page=page />
</#macro>

<#--六、生成主业务对象(新增、编辑、查看页面)FormToolbar上按钮事件2次开发句柄######################################## -->
<#macro generatorBoFormToolbarExtendJs page>
<@boFormToolBarJsLib.generatorBoFormToolbarExtendJs page=page />
</#macro>
