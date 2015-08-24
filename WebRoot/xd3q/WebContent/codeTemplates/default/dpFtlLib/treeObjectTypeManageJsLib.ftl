<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>
--%>
<#import "/dpFtlLib/commonJsLib.ftl" as commonJsLib>
<#-- 一、生成Manage页面Gen JS脚本。############################################-->
<#-- 生成Manage页面Gen JS脚本-->
<#macro generatorCommonObjectTypeManageJsGen>
<#-- 生成主业务对象(新增、编辑、查看、管理(树类型对象)页面)FormToolbar上按钮事件句柄-->
<@commonJsLib.generatorBoFormToolbarJs page="managePage"/>
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

<#-- 树对象管理页面，公用函数。-->
/**
 * 创建并新增一个空结点
 */
function createNewNode(parentNodeId){
	var parentNode = div_center_weast_tree.getNodeById(parentNodeId);
	var chlidNode=new Ext.tree.TreeNode({
		id:'new',
		text:'',
		leaf : true
	});
	
	parentNode.leaf = false;
	parentNode.appendChild(chlidNode);
	parentNode.expand();
<#compress>
<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
<#list bo.formToolbars as formToolbar>          <#-- 遍历业务对象下所有方法 -->
<#if formToolbar.method?exists && (formToolbar?size>0)>
<#if formToolbar.method.methodName=="_addNode"> 	
	Ext.getCmp('_addNode${bo.boName}').disable();
<#elseif formToolbar.method.methodName=="_addSubNode"> 
    Ext.getCmp('_addSubNode${bo.boName}').disable();
<#elseif formToolbar.method.methodName=="_deleteNode"> 
	Ext.getCmp('_deleteNode${bo.boName}').disable();
</#if>
</#if>
</#list>
</#if>
</#compress>
}

/**
 * 删除新增的结点
 */
function deleteNewNode(){
	var treeNode = div_center_weast_tree.getNodeById("new");
	if (treeNode != null)
	{
		var parentNode = treeNode.parentNode;
		treeNode.remove();
		if (parentNode.firstChild == null)
		{
			parentNode.leaf = true;
		}
	}
}

/**
 * 清空form组件的信息
 */
function resetMainForm()
{
<#if bo.formColumns?exists && (bo.formColumns?size>0)>
<#list bo.formColumns as formColumnList>
<#if formColumnList.property.columnName=="CREATOR" ||  formColumnList.property.columnName=="LASTMODIFYER" ||  formColumnList.property.columnName=="LASTMODIFYOR"> 
	 ${"$"}${"("}'${bo.boName}.${formColumnList.property.propertyName}_text'${")"}.value='';
	 ${"$"}${"("}'${bo.boName}.${formColumnList.property.propertyName}'${")"}.value='';
<#elseif formColumnList.property.columnName=="CREATETIME" || formColumnList.property.columnName=="LASTMODIFYTIME">
	 ${"$"}${"("}'${bo.boName}.${formColumnList.property.propertyName}'${")"}.value='';
<#else>	
     ${"$"}${"("}'${bo.boName}.${formColumnList.property.propertyName}'${")"}.value='';
</#if>
</#list>
</#if>
<#-- 1对1，子业务对象-->
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
<#list bo.subBusinessObject as subBo>  
<#if subBo.subBoAttribute.subBoRelType=="1">
<#if subBo.formColumns?exists && (subBo.formColumns?size>0)>
<#list subBo.formColumns as formColumns>
<#if formColumns.property.propertyName!=bo.primaryKeyProperty.propertyName>
 	${"$"}${"("}'${subBo.boName}.${formColumns.property.propertyName}'${")"}.value='';
 </#if>
</#list>
</#if>
</#if>
</#list>
</#if>
}
	
<#--生成主业务对象树onClick事件句柄 -->
/**
 * ${bo.boName}树单击事件处理
 */
function ${bo.beanAttribute.boInstanceName}TreeClick(node)
{
	treeNodeId = node.id;
	if (treeNodeId == 'new'){
		return;
	}else{
		deleteNewNode();
	}
	resetMainForm();
<#compress>	
	//控制工具条的按钮显示状态
	if (node.id == '-1'){
<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
<#list bo.formToolbars as formToolbar>          <#-- 遍历业务对象下所有方法 -->
<#if formToolbar.method?exists && (formToolbar?size>0)>
<#if formToolbar.method.methodName=="_addNode"> 	
	Ext.getCmp('_addNode${bo.boName}').disable();
<#elseif formToolbar.method.methodName=="_addSubNode"> 
    Ext.getCmp('_addSubNode${bo.boName}').enable();
<#elseif formToolbar.method.methodName=="_deleteNode"> 
	Ext.getCmp('_deleteNode${bo.boName}').disable();
<#elseif formToolbar.method.methodName=="_saveOrUpdate"> 
	Ext.getCmp('_saveOrUpdate${bo.boName}').disable();
<#elseif formToolbar.method.methodName=="_cancel"> 
	Ext.getCmp('_cancel').disable();
</#if>
</#if>
</#list>
</#if>
	}else{
<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
<#list bo.formToolbars as formToolbar>          <#-- 遍历业务对象下所有方法 -->
<#if formToolbar.method?exists && (formToolbar?size>0)>
<#if formToolbar.method.methodName=="_addNode"> 	
	Ext.getCmp('_addNode${bo.boName}').enable();
<#elseif formToolbar.method.methodName=="_addSubNode"> 
    Ext.getCmp('_addSubNode${bo.boName}').enable();
<#elseif formToolbar.method.methodName=="_deleteNode"> 
	Ext.getCmp('_deleteNode${bo.boName}').enable();
<#elseif formToolbar.method.methodName=="_saveOrUpdate"> 
	Ext.getCmp('_saveOrUpdate${bo.boName}').enable();
<#elseif formToolbar.method.methodName=="_deleteNode"> 
	Ext.getCmp('_cancel').enable();
</#if>
</#if>
</#list>
</#if>
</#compress>

    Ext.Ajax.request({
	        url: context + '/${bo.beanAttribute.webPath}/${bo.beanAttribute.boInstanceName}Controller.spr?action=_getTreeNode',
	        params:{'${bo.boTreeConfig.idProtertyName}': node.id},
	        success: function(response, options){
	            var responseArray = Ext.util.JSON.decode(response.responseText);
<#compress>   
<#if bo.formColumns?exists && (bo.formColumns?size>0)>
<#list bo.formColumns as formColumnList>
<#if formColumnList.property.columnName=="CREATOR" ||  formColumnList.property.columnName=="LASTMODIFYER" ||  formColumnList.property.columnName=="LASTMODIFYOR"> 
				${"$"}${"("}'${bo.boName}.${formColumnList.property.propertyName}_text'${")"}.value=responseArray.${formColumnList.property.propertyName}_text;
				${"$"}${"("}'${bo.boName}.${formColumnList.property.propertyName}'${")"}.value=responseArray.${formColumnList.property.propertyName};
<#elseif formColumnList.property.columnName=="CREATETIME" || formColumnList.property.columnName=="LASTMODIFYTIME">
				${"$"}${"("}'${bo.boName}.${formColumnList.property.propertyName}'${")"}.value=responseArray.${formColumnList.property.propertyName};
<#else>	
                ${"$"}${"("}'${bo.boName}.${formColumnList.property.propertyName}'${")"}.value=responseArray.${formColumnList.property.propertyName};
</#if>
</#list>
</#if>

<#-- 1对1，子业务对象-->
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
<#list bo.subBusinessObject as subBo>  
<#if subBo.subBoAttribute.subBoRelType=="1">
<#if subBo.formColumns?exists && (subBo.formColumns?size>0)>
<#list subBo.formColumns as formColumns>
<#if formColumns.property.propertyName!=bo.primaryKeyProperty.propertyName>
 			 ${"$"}${"("}'${subBo.boName}.${formColumns.property.propertyName}'${")"}.value=responseArray.${subBo.boName}${formColumns.property.propertyName};
 </#if>
</#list>
</#if>
</#if>
</#list>
</#if>
</#compress>
	        },
	        failure:function(response, options){
	        	_getMainFrame().showInfo('获取该${bo.description}资源失败！');
	        }
    	});
	}
<#--刷新子业务对象Grid数据集 -->
<@treeObjectSubBoGridRefresh/>
}
</#macro>


<#-- 二、生成Manage页面 JS脚本(2次开发扩展)。############################################-->
<#-- 生成Manage页面 JS脚本-->
<#macro generatorCommonObjectTypeManageJs>
<#-- 生成主业务对象(新增、编辑、查看、管理(树类型对象)页面)FormToolbar上按钮事件句柄-->
<@commonJsLib.generatorBoFormToolbarExtendJs page="managePage"/>
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)> 
<#-- 开始生成gridToolbars上所用到的方法 boName  boInstanceName-->
<#list bo.subBusinessObject as subBo>          <#-- 遍历业务对象下所有子对象 -->
<#--生成子业务对象Grid Toolbar上按钮事件句柄 -->
<@commonJsLib.generatorSubBoGridToolbarExtendJs subBusinessObject=subBo/>
<#--生成子业务对象Grid上超级链接所用到的事件句柄 -->
<@commonJsLib.generatorSubBoGridUrlLinksExtendJs subBo=subBo/>
</#list>  <#-- End <#list subBoList as subBo> -->
</#if>
</#macro>

<#-- 三、刷新树类型业务对象，子业务对象Grid数据集############################################-->
<#macro treeObjectSubBoGridRefresh>
<#--刷新子业务对象Grid数据集 -->
			//刷新数据集
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
<#list bo.subBusinessObject as subBo>
<#if subBo.subBoAttribute.subBoRelType=="2"  || subBo.subBoAttribute.subBoRelType=="3" || subBo.subBoAttribute.subBoRelType=="4">
	       reload_${subBo.boName}_grid("defaultCondition=${subBo.tableName}.${subBo.subBoAttribute.parentBoPrimaryKeyColumnName}='"+  node.id +"'");
</#if>
</#list>
</#if>
</#macro>