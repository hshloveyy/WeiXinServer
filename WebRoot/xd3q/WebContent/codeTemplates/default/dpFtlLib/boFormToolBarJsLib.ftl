<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>
--%>
<#-- 一、生成主业务对象(新增、编辑、查看页面)FormToolbar上按钮事件句柄############################################-->
<#macro generatorBoFormToolbarJs page>
<#--开始处理表单工具栏上的按钮 -->
<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
<#list bo.formToolbars as formToolbar>          <#-- 遍历业务对象下所有方法 -->
<#if formToolbar.method?exists && (formToolbar?size>0)>
<#if formToolbar.method.methodName=="_saveOrUpdate"> 
/**
 * 保存 
 */
function _saveOrUpdate${bo.boName}()
{					 
<#if page!="addPage">
<#if bo.boType=="2">
	deleteNewNode();
</#if>
	if(_presaveOrUpdate${bo.boName}()){
		var param = Form.serialize('mainForm');	
	<#if bo.formColumnListTab?exists  && (bo.formColumnListTab?size>0)>	
		param = param + "&"+ Form.serialize('${beanAttribute.boInstanceName}Form');
	</#if>	   
	<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
		<#list bo.subBusinessObject as subBo>          <#-- 遍历业务对象下所有子对象 --> 
			   <#list bo.properties as property>
			        <#if property.subBoName==subBo.boName> 
			            <#if property.subBoName="Attachement">
		if(isCreateCopy)
		{
			param = param + ""+ getAll${property.propertyName}${subBo.boName}GridData();
		}
		else
		{
			param = param + ""+ get${property.propertyName}${subBo.boName}GridData(); 
		}	            
			        	<#elseif property.subBoRelType=="2" || property.subBoRelType=="3">         <#-- 业务对象子对象关系为 1对多 -->
		if(isCreateCopy)
		{
			param = param + ""+ getAll${subBo.boName}GridData();
		}
		else
		{
			param = param + ""+ get${subBo.boName}GridData(); 
		}
			        	<#elseif property.subBoRelType=="1"> <#-- 业务对象子对象关系为 1对1 -->
		param = param + "&" + Form.serialize('${subBo.beanAttribute.boInstanceName}Form');		
			            </#if>
			        </#if>
			   </#list>
		</#list>
	</#if>
	    new AjaxEngine(contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

<#else>
    if(isCreateCopy){
		document.getElementById("${beanAttribute.boName}.${bo.primaryKeyProperty.propertyName}").value = "";
	}
	if(_presaveOrUpdate${bo.boName}()){
		var param = Form.serialize('mainForm');	
	<#if bo.formColumnListTab?exists  && (bo.formColumnListTab?size>0)>	
		param = param + "&"+ Form.serialize('${beanAttribute.boInstanceName}Form');
	</#if>	   
	<#if subBoList?exists && (subBoList?size>0)>
		<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象 --> 
			   <#list bo.properties as property>
			        <#if property.subBoName==subBo.boName>
		              <#if property.subBoName="Attachement">
		if(isCreateCopy)
		{
			param = param + ""+ getAll${property.propertyName}${subBo.boName}GridData();
		}
		else
		{
			param = param + ""+ get${property.propertyName}${subBo.boName}GridData(); 
		}	           
			        	<#elseif property.subBoRelType=="2" || property.subBoRelType=="3">         <#-- 业务对象子对象关系为 1对多 -->
		if(isCreateCopy)
		{
			param = param + ""+ getAll${subBo.boName}GridData();
		}
		else
		{
			param = param + ""+ get${subBo.boName}GridData(); 
		}
			        	<#elseif property.subBoRelType=="1"> <#-- 业务对象子对象关系为 1对1 -->
		param = param + "&" + Form.serialize('${subBo.beanAttribute.boInstanceName}Form');		
			            </#if>
			        </#if>
			   </#list>
		</#list>
	</#if>
	    new AjaxEngine(contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

</#if>
	_postsaveOrUpdate${bo.boName}();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var ${bo.primaryKeyProperty.propertyName}=result.${bo.primaryKeyProperty.propertyName};
<#-- -->
<#if bo.boType=="2">	
	var njson = tranFormToJSON(mainForm);                     
	var nodeid = ${'$'}${'('}'${bo.boName}.${bo.boTreeConfig.idProtertyName}'${')'}.value;
	if(nodeid==""){
		var pnodeid = njson["${bo.boName}.${bo.boTreeConfig.parentProtertyName}"];
		var pnode = div_center_weast_tree.getNodeById(pnodeid);
	    var node=new Ext.tree.TreeNode({
			id:result.${bo.boTreeConfig.idProtertyName},
			text:result.${bo.boTreeConfig.titleProtertyName},
			leaf : true
		});
	    pnode.leaf = false;
		pnode.appendChild(node);
		pnode.expand();
		node.select();
		$('${bo.boName}.${bo.boTreeConfig.idProtertyName}').value = result.${bo.boTreeConfig.idProtertyName};
		${bo.beanAttribute.boInstanceName}TreeClick(node);
	}else{
		var node = div_center_weast_tree.getNodeById(nodeid);
		Ext.apply(node.attributes, njson);
		node.setText(njson["${bo.boName}.${bo.boTreeConfig.titleProtertyName}"]);
	}
<#else>
	document.getElementById("${beanAttribute.boName}.${bo.primaryKeyProperty.propertyName}").value = ${bo.primaryKeyProperty.propertyName};
</#if>
	
<#-- 回填最后修改人、最后修改时间 -->	
<#list bo.formColumns?if_exists as formColumn><#-- 遍历业务对象下所有属性 -->
<#if formColumn.property.numberObjectId?exists && formColumn.property.numberObjectId!="" && formColumn.property.numberObjectId!=" ">
	document.getElementById("${beanAttribute.boName}.${formColumn.property.propertyName}").value = result.${formColumn.property.propertyName};
</#if>
<#if formColumn.property.columnName=="CREATOR" ||  formColumn.property.columnName=="LASTMODIFYER" ||  formColumn.property.columnName=="LASTMODIFYOR"> 
	<#if formColumn.visibility=="X">
	document.getElementById("${beanAttribute.boName}.${formColumn.property.propertyName}_text").value = result.${formColumn.property.propertyName}_text;
	</#if>
	document.getElementById("${beanAttribute.boName}.${formColumn.property.propertyName}").value = result.${formColumn.property.propertyName};
<#elseif formColumn.property.columnName=="CREATETIME" || formColumn.property.columnName=="LASTMODIFYTIME">
	document.getElementById("${beanAttribute.boName}.${formColumn.property.propertyName}").value = result.${formColumn.property.propertyName};
</#if>
</#list>
	isCreateCopy = false;	
<#-- 新增保存回调，1比多特殊关系的页签设置为可用-->
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)> 
<#list bo.subBusinessObject as subBo>
<#if subBo.subBoAttribute.subBoRelType=="4">
    var ${subBo.beanAttribute.boInstanceName}Tab = Ext.getCmp("${subBo.beanAttribute.boInstanceName}Tab");
	${subBo.beanAttribute.boInstanceName}Tab.enable();
</#if>  
</#list>  
</#if>  
<#-- 新增保存回调，回填所有业务对象子对象的主键ID值到页面上-->
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)> 
<#list bo.subBusinessObject as subBo>
<#if subBo.subBoAttribute.subBoRelType=="1">
	document.getElementById("${subBo.beanAttribute.boName}.${subBo.primaryKeyProperty.propertyName}").value=result.${subBo.primaryKeyProperty.propertyName};
<#elseif subBo.subBoAttribute.subBoRelType=="2" || subBo.subBoAttribute.subBoRelType=="3" || subBo.subBoAttribute.subBoRelType=="4">
	<#if subBo.boName!="Attachement">
	reload_${subBo.boName}_grid("defaultCondition=${subBo.tableName}.${bo.primaryKeyProperty.columnName}='"+ ${bo.primaryKeyProperty.propertyName} +"'");
	<#else>
<#list bo.properties as property>
 <#if property.subBoName==subBo.boName && subBo.subBoAttribute.parentBoProName=property.propertyName> 
	reload_${property.propertyName}${subBo.boName}_grid("defaultCondition=${subBo.tableName}.BUSINESSID='"+ ${bo.primaryKeyProperty.propertyName} +"' <#if property.propertyName?exists && property.propertyName!="" && property.propertyName!=" " >and YATTACHEMENT.BOPROPERTY='${property.propertyName}'</#if>");
 </#if>
</#list>
	</#if>
</#if>  
</#list>  
</#if>  

<#if bo.haveProcess &&  page=="addPage">
    if(Ext.getCmp("_submitProcess").disabled){
    	Ext.getCmp("_submitProcess").setDisabled(false);}
</#if>
}

<#elseif formToolbar.method.methodName=="_create" && bo.boType=="1" && page!="addPage"> 

/**
 * 创建按钮调用方法 
 * 新增${beanAttribute.boDescription}
 */
function _create${bo.boName}()
{
	if(_precreate${bo.boName}()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.${beanAttribute.boInstanceName}_Create,'',contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_create');
     }
     _postcreate${beanAttribute.boName}();
}
<#elseif formToolbar.method.methodName=="_copyCreate" && bo.boType=="1" && page!="addPage"> 
/**
 * 复制创建按钮调用方法 
 * 复制创建${beanAttribute.boDescription}
 */
function _copyCreate${bo.boName}()
{
   if(_precopyCreate${bo.boName}()){
		_getMainFrame().maintabs.addPanel(Txt.${beanAttribute.boInstanceName}_CopyCreate,'',contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_copyCreate&${bo.primaryKeyProperty.propertyName}='+${bo.primaryKeyProperty.propertyName});
   }
   _postcopyCreate${bo.boName}();
}

<#elseif formToolbar.method.methodName=="_delete" && bo.boType=="1" && page=="editPage"> 

/**
 * 删除${beanAttribute.boDescription}
 */
function _delete${bo.boName}()
{
if(_predelete${bo.boName}()){
	_getMainFrame().Ext.MessageBox.show({
		title:Txt.cue,
		msg: Txt.${beanAttribute.boInstanceName}_Delete_ConfirmOperation,
		buttons: {yes:Txt.ok, no:Txt.cancel},
		icon: Ext.MessageBox.QUESTION,
		fn:function(result){
			if (result == 'yes'){
				var param = '&${bo.primaryKeyProperty.propertyName}='+${bo.primaryKeyProperty.propertyName};
				new AjaxEngine(contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_delete', 
				{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
			}
		 }
	});
  }
  _postdelete${bo.boName}();
}

/**
 * 删除成功后的回调动作
 */
function _deleteCallBackHandle()
{
	_getMainFrame().showInfo(Txt.operateSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
<#elseif formToolbar.method.methodName=="_submitProcess"  && bo.haveProcess > 

/**
 * 提交
 */
function _submitProcess${bo.boName}()
{
if(_presubmitProcess${bo.boName}()){
	var param = Form.serialize('mainForm');	
<#if bo.formColumnListTab?exists && (bo.formColumnListTab?size>0)>	
	param = param + "&"+ Form.serialize('${bo.beanAttribute.boInstanceName}Form');
</#if>	
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
<#list bo.subBusinessObject as subBo>          <#-- 遍历业务对象下所有子对象 -->
	   <#list bo.properties as property>
	        <#if property.subBoName==subBo.boName> 
                <#if property.subBoName="Attachement">
	param = param + ""+ getAll${property.propertyName}${subBo.boName}GridData();     
	        	<#elseif property.subBoRelType=="2" || property.subBoRelType=="3">         <#-- 业务对象子对象关系为 1对多 -->
	param = param + "&" + getAll${subBo.boName}GridData();
	        	<#elseif property.subBoRelType=="1"> <#-- 业务对象子对象关系为 1对1 -->
	param = param + "&" + Form.serialize('${subBo.beanAttribute.boInstanceName}Form');		
	            </#if>
	        </#if>
	   </#list>
</#list>
</#if>
	param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
  }
  _postsubmitProcess${bo.boName}();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

<#elseif formToolbar.method.methodName=="_cancel"> 
<#if bo.boType=="2">
/**
 * 取消
 */
function _cancel()
{	
 	resetMainForm();
}
 <#else>
	<#if  page=="editPage" ||  page=="viewPage">
/**
 * 取消
 */
function _cancel()
{
  if(_precancel()){
	new AjaxEngine(contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr?action=_cancel&${bo.primaryKeyProperty.propertyName}='+${bo.primaryKeyProperty.propertyName}, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelCallBack});
   }
   _postcancel();
}

function _cancelCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
	<#else>
/**
 * 取消
 */
function _cancel()
{
	if(_precancel()){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	 }
	 _postcancel();
}
	</#if>
  </#if>
<#elseif formToolbar.method.methodName=="_addNode" && bo.boType=="2" && page=="managePage"> 

/**
 * 增加【${beanAttribute.boDescription}】树同级节点
 */
function _addNode${beanAttribute.boName}()
{
	if (treeNodeId == ''){
			_getMainFrame().showInfo(Txt.${beanAttribute.boInstanceName}_PlaseSelectTreeNode);
		}else{
<#compress>
<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
<#list bo.formToolbars as formToolbar>          <#-- 遍历业务对象下所有方法 -->
<#if formToolbar.method?exists && (formToolbar?size>0)>	
<#if formToolbar.method.methodName=="_saveOrUpdate"> 
	Ext.getCmp('_saveOrUpdate${bo.boName}').enable();
<#elseif formToolbar.method.methodName=="_cancel"> 
	Ext.getCmp('_cancel').enable();
</#if>
</#if>
</#list>
</#if>
</#compress>

			resetMainForm();
			var node = div_center_weast_tree.getNodeById(treeNodeId);
			var parentTreeNode = node.parentNode;

			${'$'}${'('}'${bo.boName}.${bo.boTreeConfig.idProtertyName}'${')'}.value = "";
			${'$'}${'('}'${bo.boName}.${bo.boTreeConfig.parentProtertyName}'${')'}.value = parentTreeNode.id;
			createNewNode(parentTreeNode.id);
<#--刷新子业务对象Grid数据集 -->
<@treeObjectSubBoGridRefresh/>
		}
}
<#elseif formToolbar.method.methodName=="_addSubNode" && bo.boType=="2" && page=="managePage"> 

/**
 * 增加【${beanAttribute.boDescription}】树下级节点
 */
function _addSubNode${beanAttribute.boName}()
{
	if (treeNodeId == ''){
			_getMainFrame().showInfo(Txt.${beanAttribute.boInstanceName}_PlaseSelectTreeNode);
		}else{
<#compress>
<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
<#list bo.formToolbars as formToolbar>          <#-- 遍历业务对象下所有方法 -->
<#if formToolbar.method?exists && (formToolbar?size>0)>	
<#if formToolbar.method.methodName=="_saveOrUpdate"> 
	Ext.getCmp('_saveOrUpdate${bo.boName}').enable();
<#elseif formToolbar.method.methodName=="_cancel"> 
	Ext.getCmp('_cancel').enable();
</#if>
</#if>
</#list>
</#if>
</#compress>

			resetMainForm();
			var node = div_center_weast_tree.getNodeById(treeNodeId);
			${'$'}${'('}'${bo.boName}.${bo.boTreeConfig.idProtertyName}'${')'}.value = "";
			${'$'}${'('}'${bo.boName}.${bo.boTreeConfig.parentProtertyName}'${')'}.value = node.id;
			createNewNode(node.id);
<#--刷新子业务对象Grid数据集 -->
<@treeObjectSubBoGridRefresh/>
		}
}

<#elseif formToolbar.method.methodName=="_deleteNode" && bo.boType=="2" && page=="managePage"> 

/**
 * 删除树节点回调函数
 */
function deleteCallBackHandle(transport){
	var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
	treeNode.remove();
	treeNodeId = '';
	resetMainForm();
	
<#compress>
<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
<#list bo.formToolbars as formToolbar>          <#-- 遍历业务对象下所有方法 -->
<#if formToolbar.method?exists && (formToolbar?size>0)>	
<#if formToolbar.method.methodName=="_saveOrUpdate"> 
	Ext.getCmp('_saveOrUpdate${bo.boName}').disable();
<#elseif formToolbar.method.methodName=="_addNode"> 
	Ext.getCmp('_addNode${bo.boName}').disable();
<#elseif formToolbar.method.methodName=="_addSubNode"> 
	Ext.getCmp('_addSubNode${bo.boName}').disable();
<#elseif formToolbar.method.methodName=="_deleteNode"> 
	Ext.getCmp('_deleteNode${bo.boName}').disable();
<#elseif formToolbar.method.methodName=="_cancel"> 
	Ext.getCmp('_cancel').disable();
</#if>
</#if>
</#list>
</#if>
</#compress>
<#--刷新子业务对象Grid数据集 -->
<@treeObjectSubBoGridRefresh/>
}
	
/**
 * 删除【${beanAttribute.boDescription}】树节点
 */
function _deleteNode${beanAttribute.boName}()
{
	if (treeNodeId == ''){
		_getMainFrame().showInfo(Txt.${beanAttribute.boInstanceName}_PlaseSelectTreeNode);
	}else{
		var treeNode = div_center_weast_tree.getNodeById(treeNodeId);
		var parentTreeNode = treeNode.parentNode;
	
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
			msg: Txt.${beanAttribute.boInstanceName}_Delete_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					if (treeNode.leaf == false){
						_getMainFrame().showInfo(Txt.${beanAttribute.boInstanceName}_NotDeleteTreeNode);
					}else{
						var param = "&${bo.boTreeConfig.idProtertyName}=" + treeNodeId;
						param += "&action=_deleteNode";
						new AjaxEngine(contextPath + '/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Controller.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle, callback: deleteCallBackHandle});
					}
				}
			}
		});
	}
}

<#elseif formToolbar.method.isDefault!="X">
<#-- 生成空方法体，已经不生成在此文件，改为生成在可扩展编程JS文件中！-->

</#if>
</#if>
</#list>
</#if>
<#--结束处理表单工具栏上的按钮 -->
</#macro>


<#-- 二、生成主业务对象(新增、编辑、查看页面)FormToolbar上按钮事件2次开发扩展句柄############################################-->
<#macro generatorBoFormToolbarExtendJs page>
<#--开始处理表单工具栏上的按钮 -->
<#if  bo.formToolbars?exists && ( bo.formToolbars?size>0)>
<#list bo.formToolbars as formToolbar>          <#-- 遍历业务对象下所有方法 -->
<#if formToolbar.method?exists && (formToolbar?size>0)>
<#if formToolbar.method.methodName=="_saveOrUpdate"> 					 
/**
 * 保存 
 */
function _presaveOrUpdate${bo.boName}()
{
	return true ;
}

/**
 * 保存 
 */
function _postsaveOrUpdate${bo.boName}()
{

}
<#elseif formToolbar.method.methodName=="_create" && bo.boType=="1" && page!="addPage"> 

/**
 * 创建按钮调用方法 
 * 新增${beanAttribute.boDescription}
 */
function _precreate${bo.boName}()
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
<#elseif formToolbar.method.methodName=="_copyCreate" && bo.boType=="1" && page!="addPage"> 

function _precopyCreate${beanAttribute.boName}()
{
	return true ;
}

function _postcopyCreate${beanAttribute.boName}()
{

}
<#elseif formToolbar.method.methodName=="_cancel"> 
/**
 * 取消
 */
function _precancel()
{
	return true ;
}

/**
 * 取消
 */
function _postcancel()
{

}
<#elseif formToolbar.method.methodName=="_delete" && bo.boType=="1" && page=="editPage"> 

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

<#elseif formToolbar.method.methodName=="_submitProcess"  && bo.haveProcess && page!="addPage"> 

/**
 * 提交
 */
function _presubmitProcess${beanAttribute.boName}()
{
	return true ;
}

/**
 * 提交
 */
function _postsubmitProcess${beanAttribute.boName}()
{

}
<#elseif formToolbar.method.methodName=="_addNode" && bo.boType=="2" && page=="managePage"> 

/**
 * 增加【${beanAttribute.boDescription}】树同级节点
 */
function _postaddNode${beanAttribute.boName}()
{

}
/**
 * 增加【${beanAttribute.boDescription}】树同级节点
 */
function _preaddNode${beanAttribute.boName}()
{
	return true;
}
<#elseif formToolbar.method.methodName=="_addSubNode" && bo.boType=="2" && page=="managePage"> 

/**
 * 增加【${beanAttribute.boDescription}】树下级节点
 */
function _postaddSubNode${beanAttribute.boName}()
{
}

/**
 * 增加【${beanAttribute.boDescription}】树下级节点
 */
function _preaddSubNode${beanAttribute.boName}()
{
	return true ;
}

<#elseif formToolbar.method.methodName=="_deleteNode" && bo.boType=="2" && page=="managePage"> 

/**
 * 删除【${beanAttribute.boDescription}】树节点
 */
function _postdeleteNode${beanAttribute.boName}()
{
}

/**
 * 删除【${beanAttribute.boDescription}】树节点
 */
function _predeleteNode${beanAttribute.boName}()
{
	return true ;
}

<#elseif formToolbar.method.isDefault!="X">
<#-- 生成空方法体-->
/**
 *  ${formToolbar.method.methodDesc}
 */
function ${formToolbar.method.methodName}()
{
   if(pre${formToolbar.method.methodName}()){
   //方法执行体
   }
   post${formToolbar.method.methodName}();
}

/**
 *  ${formToolbar.method.methodDesc}
 */
function pre${formToolbar.method.methodName}()
{
	return true ;
}

/**
 *  ${formToolbar.method.methodDesc}
 */
function post${formToolbar.method.methodName}()
{

}
</#if>
</#if>
</#list>
</#if>
<#--结束处理表单工具栏上的按钮 -->
</#macro>



<#-- 三、刷新树类型业务对象，子业务对象Grid数据集############################################-->
<#macro treeObjectSubBoGridRefresh>
<#--刷新子业务对象Grid数据集 -->
			//刷新数据集
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
<#list bo.subBusinessObject as subBo>
<#if subBo.subBoAttribute.subBoRelType=="2" || subBo.subBoAttribute.subBoRelType=="4">
	       reload_${subBo.boName}_grid("defaultCondition=${subBo.tableName}.${subBo.subBoAttribute.parentBoPrimaryKeyColumnName}='"+  node.id +"'");
</#if>
</#list>
</#if>
</#macro>