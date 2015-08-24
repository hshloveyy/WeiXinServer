<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象${beanAttribute.boDescription}(${beanAttribute.boName})增加页面
--%>
<#import "/dpFtlLib/commonObjectTypeManagePageLib.ftl" as commonObjectTypeManagePageLib>
<#import "/dpFtlLib/treeObjectTypeManagePageLib.ftl" as treeObjectTypeManagePageLib>
<#import "/dpFtlLib/commonFormLib.ftl" as commonFormLib>

<#-- 一、管理页面Page, boby区域代码 。############################################-->
<#--通用对象类型管理页面，boby区域代码 -->
<#macro generatorManagePageBody>
<!-- dpPageFtlLib.ftl -->
<#if bo.boType=="1">
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<@commonObjectTypeManagePageLib.generatorCommonObjectTypeManageForm/>
</form>
</div>
<div id="div_southForm"></div>
<#--树对象类型管理页面，boby区域代码 -->
<#else>
<div id="tree-div">
<div id="div_center_weast"></div>
</div>
<div id="div_center_north">
<div id="div_centerToolbar"></div>
	<div id="div_centerForm" class="search">
		<form id="mainForm" name="mainForm" class="search">
		<@treeObjectTypeManagePageLib.generatorTreeObjectTypeManageForm page="managerPage"/>
		</form>
	</div>
</div>
<@commonFormLib.generatorBoTabPanelForm/>
<@commonFormLib.generatorSubBoDivAndForm/>
<div id="div_center_south"></div>
</#if>
</#macro>

<#-- 二、管理页面Ext布局和系统文本。############################################-->
<#-- 生成管理页面Ext布局和系统文本-->
<#macro generatorManagePageExtLayOutAndSysTxt>
<#if bo.boType=="1">
<@commonObjectTypeManagePageLib.generatorManagePageExtLayOutAndSysTxt/>
<#else>
<@treeObjectTypeManagePageLib.generatorManagePageExtLayOutAndSysTxt/>
</#if>
</#macro>

<#-- 三、管理页面tag生成。############################################-->
<#-- 管理页面tag-->
<#macro generatorManagePageTag>
<#if bo.boType=="1">
<@commonObjectTypeManagePageLib.generatorManagePageTag/>
<#else>
<@treeObjectTypeManagePageLib.generatorManagePageTag/>
</#if></#macro>









































