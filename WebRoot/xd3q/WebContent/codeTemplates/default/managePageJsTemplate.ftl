﻿/**
  * Author(s):java业务平台代码生成工具
  * Date: ${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象${beanAttribute.boDescription}(${beanAttribute.boName})管理页面JS用户可编程扩展文件
 */

<#import "/dpFtlLib/commonObjectTypeManageJsLib.ftl" as commonObjectTypeManageJsLib>
<#import "/dpFtlLib/treeObjectTypeManageJsLib.ftl" as treeObjectTypeManageJsLib>

<#if bo.boType=="1">
	<@commonObjectTypeManageJsLib.generatorCommonObjectTypeManageJs/>
<#else>
	<@treeObjectTypeManageJsLib.generatorCommonObjectTypeManageJs/>
</#if>
