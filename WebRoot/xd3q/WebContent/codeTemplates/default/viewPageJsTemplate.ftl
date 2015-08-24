/**
  * Author(s):java业务平台代码生成工具
  * Date: ${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象${beanAttribute.boDescription}(${beanAttribute.boName})查看页面JS文件
 */

 <#if subBoList?exists && (subBoList?size>0)>
<#-- 开始生成gridToolbars上所用到的方法 -->
<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象 -->
<#if subBo.boName!="Attachement">   <#-- 业务对象名字判断，业务附件Attachement不处理 -->
<#if (subBo.gridToolbars?exists) && (subBo.gridToolbars?size>0)>
<#list subBo.gridToolbars as toolbar>
<#if subBo.subBoAttribute.subBoRelType=="2">
<#if toolbar.method.methodName=="_create">
  <#-- 方法 为1：打开到页签-->
  <#if toolbar.method.methodType=="1">
/**
 * grid 上的 创建按钮调用方法 
 * 新增${beanAttribute.boDescription},${subBo.description}
 */
function _precreate${subBo.boName}()
{
	return true ;
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
<#elseif toolbar.method.isDefault!="X">
<#-- 生成空方法体-->
/**
 *  ${toolbar.method.methodDesc}
 */
function ${toolbar.method.methodName}${subBo.boName}()
{
   //方法执行体

}

</#if>    <#-- End <#if toolbar.method.methodName=="_create">  -->

<#elseif subBo.subBoAttribute.subBoRelType=="3">
<#list bo.properties?if_exists as boProperty>
<#if boProperty.subBoRelType=="3" && boProperty.propertyName?cap_first==subBo.boName && boProperty.searchHelp!="" && boProperty.searchHelp!=" ">  <#--    CC -->
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
   //方法执行体

}

</#if>                                     <#-- END AA -->
</#if>                                     <#-- END CC -->
</#list>
</#if>    <#-- End <#if subo.subBoAttribute.subBoRelType=="2">  -->
</#list>  <#-- End <#list subBo.gridToolbars as toolbar>  -->

</#if>
<#-- 开始生成grid上超级链接所用到的方法 -->
<#if subBo.gridColumns?exists && (subBo.gridColumns?size>0)>
<#list subBo.gridColumns as gridColumn>
<#if gridColumn.method?exists>
<#-- 开始生成grid上超级链接所用到的方法 -->
<#if gridColumn.method.methodName=="_delete" && gridColumn.method.methodType=="4" && subBo.subBoAttribute.subBoRelType=="4">

/**
 * grid 上的超级链接, 删除按钮调用方法
 * 删除${subBo.beanAttribute.boDescription}
 */
function _predelete${subBo.boName}(id,url)
{
	return true;
}

/**
 * grid 上的超级链接, 删除按钮调用方法
 * 删除${subBo.beanAttribute.boDescription}
 */
function _postdelete${subBo.boName}(id,url)
{

}
<#elseif gridColumn.method.methodName=="_edit"  && subBo.subBoAttribute.subBoRelType!="4">
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
 *  ${toolbar.method.methodDesc}
 */
function ${toolbar.method.methodName}${subBo.boName}()
{
   //方法执行体
  
}

</#if>
</#if>    <#-- End <#if gridColumn.method?exists> -->
</#list>
</#if>
</#if>    <#-- End <#if subBo.boName!="Attachement">  -->
</#list>  <#-- End <#list subBoList as subBo> -->
</#if>

<#--开始处理表单工具栏上的按钮 -->
<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
<#list bo.formToolbars as formToolbar>          <#-- 遍历业务对象下所有方法 -->
<#if formToolbar.method?exists && (formToolbar?size>0)>
<#if formToolbar.method.methodName=="_saveOrUpdate"> 					 
/**
 * 保存 
 */
function _presaveOrUpdate${beanAttribute.boName}()
{
	return true ;
}

/**
 * 保存 
 */
function _postsaveOrUpdate${beanAttribute.boName}()
{

}

<#elseif formToolbar.method.methodName=="_cancel"> 

/**
 * 取消
 */
function _precancel${beanAttribute.boName}()
{
	return true ;
}

/**
 * 取消
 */
function _postcancel${beanAttribute.boName}()
{

}
<#elseif formToolbar.method.methodName=="_delete"> 

/**
 * 删除${beanAttribute.boDescription}
 */
function _predelete${beanAttribute.boName}()
{
	return true ;
}

/**
 * 删除${beanAttribute.boDescription}
 */
function _postdelete${beanAttribute.boName}()
{

}
<#elseif formToolbar.method.methodName=="_create"> 

/**
 * 创建按钮调用方法 
 * 新增${beanAttribute.boDescription}
 */
function _precreate${beanAttribute.boName}()
{
	return true ;
}

/**
 * 创建按钮调用方法 
 * 新增${beanAttribute.boDescription}
 */
function _postcreate${beanAttribute.boName}()
{

}
<#elseif formToolbar.method.methodName=="_copyCreate"> 

function _precopyCreate${beanAttribute.boName}()
{
	return true ;
}

function _postcopyCreate${beanAttribute.boName}()
{

}

<#elseif formToolbar.method.methodName=="_submitProcess"  && bo.haveProcess> 

/**
 * 提交
 */
function _presubmitProcess${beanAttribute.boName}(id)
{
	return true ;
}

/**
 * 提交
 */
function _postsubmitProcess${beanAttribute.boName}(id)
{

}

<#elseif formToolbar.method.isDefault!="X">
<#-- 生成空方法体-->
/**
 *  ${formToolbar.method.methodDesc}
 */
function ${formToolbar.method.methodName}${beanAttribute.boName}()
{
   //方法执行体
 
}

</#if>
</#if>
</#list>
</#if>
<#--结束处理表单工具栏上的按钮 -->