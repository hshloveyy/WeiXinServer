<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>
--%>
<#-- 开始生成Tag-->
<#macro tagLib subBoList>
<#if subBoList?exists && (subBoList?size>0)>
	<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象 "${"$"}${"{"}${beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}"-->
		<#if subBo.boName!="Attachement">   
		    <#if subBo.subBoAttribute.subBoRelType=="2">         <#-- 业务对象子对象关系为 1对多 -->
<fisc:grid divId="div_${subBo.beanAttribute.boInstanceName}" boName="${subBo.beanAttribute.boName}" needCheckBox="true" editable="true" defaultCondition=" ${subBo.tableName}.${bo.primaryKeyProperty.columnName}='${"$"}${"{"}${beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>      
		     <#elseif subBo.subBoAttribute.subBoRelType=="3" || subBo.subBoAttribute.subBoRelType=="4">
<fisc:grid divId="div_${subBo.beanAttribute.boInstanceName}" boName="${subBo.beanAttribute.boName}" needCheckBox="true" editable="false" defaultCondition=" ${subBo.tableName}.${bo.primaryKeyProperty.columnName}='${"$"}${"{"}${beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>        
		     </#if>
		<#else>
<fisc:attachement businessId="${"$"}${"{"}${"businessId"}${"}"}" allowDelete="true" divId="div_attachement" boName="${beanAttribute.boName}" gridPageSize="10" gridHeight="285"></fisc:attachement>
		</#if>
	</#list>
<#else>
</#if>
</#macro>

