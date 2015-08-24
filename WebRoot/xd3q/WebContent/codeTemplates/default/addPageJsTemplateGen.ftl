/**
  * Author(s):java业务平台代码生成工具
  * Date: ${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象${beanAttribute.boDescription}(${beanAttribute.boName})增加页面JS文件
 */
<#import "/dpFtlLib/commonJsLib.ftl" as commonJsLib>

<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)> 
<#-- 开始生成gridToolbars上所用到的方法 boName  boInstanceName-->
<#list bo.subBusinessObject as subBo>          <#-- 遍历业务对象下所有子对象 -->
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
<#--生成子业务对象Grid Toolbar上按钮事件句柄 -->
<@commonJsLib.generatorSubBoGridToolbarJs subBusinessObject=subBo/>
<#--生成子业务对象Grid上超级链接所用到的事件句柄 -->
<@commonJsLib.generatorSubBoGridUrlLinksJs subBo=subBo/>
</#list>  <#-- End <#list subBoList as subBo> -->
</#if>

<#--开始处理表单工具栏上的按钮 -->
<@commonJsLib.generatorBoFormToolbarJs page="addPage"/>
<#--结束处理表单工具栏上的按钮 -->