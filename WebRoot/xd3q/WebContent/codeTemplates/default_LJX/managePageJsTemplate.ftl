/**
  * Author(s):java业务平台代码生成工具
  * Date: ${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象${beanAttribute.boDescription}(${beanAttribute.boName})管理页面JS用户可编程扩展文件
 */


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