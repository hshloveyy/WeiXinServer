<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>
--%>
<#import "/dpFtlLib/commonFormLib.ftl" as commonFormLib>

<#-- 一、树对象管理页面Form。############################################-->
<#-- 生成Manage页面Form表单区域-->
<#macro generatorTreeObjectTypeManageForm page>
<@commonFormLib.generatorMainBoForm page/>
</#macro>

<#-- 二、标准对象管理页面Ext布局和系统文本。############################################-->
<#-- 生成标准对象管理页面Ext布局和系统文本-->
<#macro generatorManagePageExtLayOutAndSysTxt>
//存放当前选 中树的节点ID
var treeNodeId = '';
//全局路径
var context = '<%= request.getContextPath() %>';
//是否已经提交流程
<#if bo.haveProcess>
<#if bo.properties?exists && (bo.properties?size>0)>
<#list bo.properties as property>
<#if property.columnName=='PROCESSSTATE'>
var isSubmited = '${'$'}${'{'}${beanAttribute.boInstanceName}.${property.propertyName}${'}'}';
<#break>
</#if>
</#list>
</#if>
<#else>
var isSubmited = '';
</#if>
//当前对象主键ID
var ${bo.primaryKeyProperty.propertyName} = '${"$"}${"{"}${bo.beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}';	
	
//页面文本
var Txt={
	//${bo.boText}
	${beanAttribute.boInstanceName}:'${"$"}${"{"}vt.boText${"}"}',
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
	<#list bo.subBusinessObject as subBo>          <#-- 遍历业务对象下所有子对象 -->
	<#if subBo.boName!="Attachement"> 
	//${subBo.boText}
	${subBo.beanAttribute.boInstanceName}:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boText${"}"}',
	<#if subBo.gridToolbars?exists && (subBo.gridToolbars?size>0)>  
	<#list subBo.gridToolbars as toolbar>
    <#if toolbar.methodName=="_create">
	//boText创建
	${subBo.beanAttribute.boInstanceName}_Create:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boTextCreate${"}"}',
	<#elseif toolbar.methodName=="_edit">
	${subBo.beanAttribute.boInstanceName}_Edit:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boTextEdit${"}"}',
	<#elseif toolbar.methodName=="_view">
	${subBo.beanAttribute.boInstanceName}_View:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boTextView${"}"}',
	<#elseif toolbar.methodName=="_copyCreate">
	//boText复制创建
	${subBo.beanAttribute.boInstanceName}_CopyCreate:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boTextCopyCreate${"}"}',
	// 进行【${subBo.boText}复制创建】操作时，只允许选择一条记录！
	${subBo.beanAttribute.boInstanceName}_CopyCreate_AllowOnlyOneItemForOperation:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.copyCreate_AllowOnlyOneItemForOperation${"}"}',
	// 请选择需要进行【${subBo.boText}复制创建】操作的记录！
	${subBo.beanAttribute.boInstanceName}_CopyCreate_SelectToOperation:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.copyCreate_SelectToOperate${"}"}',
	<#elseif toolbar.methodName=="_deletes">
	// 请选择需要进行【${subBo.boText}批量删除】操作的记录！
	${subBo.beanAttribute.boInstanceName}_Deletes_SelectToOperation:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.deletes_SelectToOperate${"}"}',
	// 您选择了【${subBo.boText}批量删除】操作，是否确定继续该操作？
	${subBo.beanAttribute.boInstanceName}_Deletes_ConfirmOperation:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.deletes_ConfirmOperation${"}"}',
	<#elseif toolbar.methodName=="_delete">
	// 您选择了【${beanAttribute.boDescription}删除】操作，是否确定继续该操作？
	${subBo.beanAttribute.boInstanceName}_Delete_ConfirmOperation:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.delete_ConfirmOperation${"}"}',
	</#if>
	</#list>
	</#if>
	</#if>
	</#list>
</#if>
<#if bo.boType=="2">
	//请选择节点后再进行此功能的操作！
	${beanAttribute.boInstanceName}_PlaseSelectTreeNode:'${"$"}${"{"}vt.plaseSelectTreeNode${"}"}',
	//非叶结点，不能删除！
	${beanAttribute.boInstanceName}_NotDeleteTreeNode:'${"$"}${"{"}vt.notDeleteTreeNode${"}"}',
</#if>
	//boText复制创建
	${beanAttribute.boInstanceName}_CopyCreate:'${"$"}${"{"}vt.boTextCopyCreate${"}"}',
	//boText创建
	${beanAttribute.boInstanceName}_Create:'${"$"}${"{"}vt.boTextCreate${"}"}',
	// 复制创建
	mCopyCreate:'${"$"}${"{"}vt.mCopyCreate${"}"}',
	// 创建
	mCreate:'${"$"}${"{"}vt.mCreate${"}"}',
	// 编辑
	mEdit:'${"$"}${"{"}vt.mEdit${"}"}',
	// 查看
	mView:'${"$"}${"{"}vt.mView${"}"}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	${beanAttribute.boInstanceName}_Delete_ConfirmOperation:'${"$"}${"{"}vt.delete_ConfirmOperation${"}"}',
	// '提交成功！'
	submitSuccess:'${"$"}${"{"}vt.submitSuccess${"}"}',
	// '操作成功！'
	operateSuccess:'${"$"}${"{"}vt.operateSuccess${"}"}',
	// 提示
	cue:'${"$"}${"{"}vt.sCue${"}"}',
	// 确定
	ok:'${"$"}${"{"}vt.sOk${"}"}',
	// 取消
	cancel:'${"$"}${"{"}vt.sCancel${"}"}'
};


/**
 * EXT 布局
 */
		var centerToolbars = new Ext.Toolbar({
		items:[
				<#compress>
				<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
				<#list bo.formToolbars?sort_by("orderNo") as formToolbar>          <#-- 遍历业务对象下所有方法 -->
				<#if formToolbar.method.methodName=="_addNode"> 	
				{id:'_addNode${bo.boName}',text:'${formToolbar.method.methodDesc}',disabled:true,handler:_addNode${bo.boName},iconCls:'${formToolbar.method.icon}'},<#if formToolbar_has_next>'-',</#if>
				<#elseif formToolbar.method.methodName=="_addSubNode"> 
				{id:'_addSubNode${bo.boName}',text:'${formToolbar.method.methodDesc}',disabled:true,handler:_addSubNode${bo.boName},iconCls:'${formToolbar.method.icon}'},<#if formToolbar_has_next>'-',</#if>
				<#elseif formToolbar.method.methodName=="_deleteNode"> 
				{id:'_deleteNode${bo.boName}',text:'${formToolbar.method.methodDesc}',disabled:true,handler:_deleteNode${bo.boName},iconCls:'${formToolbar.method.icon}'},<#if formToolbar_has_next>'-',</#if>
				</#if>
				</#list>
				</#if>
				'->','-',
				<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
				<#list bo.formToolbars?sort_by("orderNo") as formToolbar>          <#-- 遍历业务对象下所有方法 -->
				<#if formToolbar.method.methodName=="_saveOrUpdate"> 	
				{id:'_saveOrUpdate${bo.boName}',text:'${"$"}${"{"}vt.mSaveOrUpdate${"}"}',disabled:true,handler:_saveOrUpdate${bo.boName},iconCls:'${formToolbar.method.icon}'},<#if formToolbar_has_next>'-',</#if>
				<#elseif formToolbar.method.methodName=="_submitProcess不生成"> 
				{id:'_submitProcess${bo.boName}',text:'${formToolbar.method.methodDesc}',disabled:true,handler:_submitProcess${bo.boName},iconCls:'${formToolbar.method.icon}'},<#if formToolbar_has_next>'-',</#if>
				<#elseif formToolbar.method.methodName=="_cancel"> 
				{id:'_cancel',text:'${"$"}${"{"}vt.mCancel${"}"}',disabled:true,handler:_cancel,iconCls:'${formToolbar.method.icon}'},<#if formToolbar_has_next>'-',</#if>
				</#if>
				</#list>
				</#if>
				</#compress>
				' '],  
		       renderTo:"div_centerToolbar"
		});

	var viewport = new Ext.Viewport({
	   		layout:"border",
			items:[{
				region:"west",
	            split:true,
	            collapsible: true,
	            width: 200,
	            minSize: 175,
	            maxSize: 400,
	            margins:'0 0 0 5',
	            layout:'fit',
	            layoutConfig:{
	               animate:true
	            },
	            items:[{
	                title:'${"$"}${"{"}vt.boTextDetail${"}"}',
	                border:false,
	                layout:'fit',
	                contentEl: 'tree-div',
	                tools:[{
	                    id:'refresh',   
	                    qtip: '刷新${"$"}${"{"}vt.boTextDetail${"}"}',
	                     handler: function(event, toolEl, panel) {
		                	div_center_weast_tree.body.mask('刷新中...');
		                    reload_div_center_weast_tree();
		                    div_center_weast_tree.root.collapse(true, false);
		                    setTimeout(function(){
		                        div_center_weast_tree.body.unmask();
		                    }, 1000);
	                     }
	                },{
	                	id:'maximize',
	                	qtip:'展开树',
	                	handler:function(event, toolEl, panel) {
	                		div_center_weast_tree.expandAll();
	                	}
	                },{
	                	id:'minimize',
	                	qtip:'收起树',
	                	handler:function(event, toolEl, panel) {
	                		div_center_weast_tree.collapseAll();
	                	}
	                }],
	                items:[{contentEl:'div_center_weast'}]
	            }]
			},{
				region:'center',
				border:false,
	            items:[{
	            	region:'north',
		            layout:'fit',
		            contentEl:'div_centerToolbar'
	            }
 				,{
						region:'center',
						layout: 'anchor',
			            height:600,
			            border:false,
			            autoScroll: true,
			            items:[{
			            		title: '${"$"}${"{"}vt.boTextInfo${"}"}',
			            		layout:'fit',
			            		autoScroll: true,
			            		contentEl:'div_centerForm'
							 }
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>							 
							 ,{
								xtype:'tabpanel',
				            	height:310,
				            	id:'tabs',
				            	name:'tabs',
				            	defaults:{bodyStyle:"background-color:#DFE8F6"},
				            	autoScroll: true,
								activeTab:0,
					            items:[
<#if bo.formColumnListTab?exists && (bo.formColumnListTab?size>0)>
								    {
							    		id:'${bo.beanAttribute.boInstanceName}Tab',
					            		title: '${"$"}${"{"}vt.boText${"}"}',
					            		layout:'fit',
					            		autoWidth:true,
					            		bodyStyle:"background-color:#DFE8F6",
					            		contentEl:'div_${beanAttribute.boInstanceName}'
							        },
</#if>
				<#list bo.subBusinessObject as subBo>          <#-- 遍历业务对象下所有子对象 -->
						             {
						            		title: '${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boText${"}"}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'${subBo.beanAttribute.boInstanceName}Tab',
						            		<#if subBo.subBoAttribute.subBoRelType=="4">
						            		disabled: true,
						            		</#if>
						            		contentEl:'div_${subBo.beanAttribute.boInstanceName}'
						             }<#if subBo_has_next>,</#if>
				</#list>
						            ]
	           		     }]
<#else>
	<#if bo.formColumnListTab?exists  && (bo.formColumnListTab?size>0)>
	              ,{
								xtype:'tabpanel',
				            	height:310,
				            	id:'tabs',
				            	name:'tabs',
				            	defaults:{bodyStyle:"background-color:#DFE8F6"},
				            	autoScroll: true,
								activeTab:0,
					            items:[{
					            		title: '${"$"}${"{"}vt.boText${"}"}',
					            		layout:'fit',
					            		autoWidth:true,
					            		contentEl:'div_${beanAttribute.boInstanceName}'
			                          }]
			       }]   
    <#else>
     ]
	</#if>
</#if>	
			}]
		}]
});

<#if (bo.tabsSize>0) >
Ext.onReady(function(){
    var tabsSize = ${bo.tabsSize};
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < ${bo.tabsSize} ; i++){
		   tabs.setActiveTab(${bo.tabsSize}-1-i);
		}
	}
 });
</#if>	
</#macro>

<#-- 三、树对象管理页面tag生成。############################################-->
<#-- 生成树对象管理页面tag-->
<#macro generatorManagePageTag>
<fisc:tree tableName="${bo.tableName}" 
	entityName="${beanAttribute.packageName}.domain.${beanAttribute.boName}" height="500"
	rootValue="-1" idColumnName="${bo.boTreeConfig.idColumnName}"
	parentColumnName="${bo.boTreeConfig.parentColumnName}" titleColumnName="${bo.boTreeConfig.titleColumnName}"
	treeTitle="${"$"}${"{"}vt.boText${"}"}" divId="div_center_weast" needCheckChilden="true"
	needAutoLoad="true" rootVisible="true"  onclick="${bo.beanAttribute.boInstanceName}TreeClick"></fisc:tree>
<#-- 生成子业务对象Tag标签-->
<@commonFormLib.generatorSubBoTag/>    
</#macro>

