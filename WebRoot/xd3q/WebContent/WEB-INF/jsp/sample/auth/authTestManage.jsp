<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月16日 09点34分24秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象AuthTest(AuthTest)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/auth/authTestManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/auth/authTestManageGen.js"></script>
</head>
<body>
<fisc:tree tableName="YAUTHRESOURCEA" 
	entityName="com.infolion.sample.auth.domain.AuthTest" height="500"
	rootValue="-1" idColumnName="authresourceid"
	parentColumnName="parentauthresid" titleColumnName="authresourcedesc"
	treeTitle="预算项分组目录" divId="div_center_weast" needCheckChilden="true"
	needAutoLoad="true" rootVisible="true"  onclick="authTestTreeClick"></fisc:tree>
	          
		             
<fisc:grid divId="div_authItem" boName="AuthItem" needCheckBox="true" editable="true" defaultCondition=" YAUTHITEM.AUTHRESOURCEID='${authTest.authresourceid}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>      
<!-- dpPageFtlLib.ftl -->
<div id="tree-div">
<div id="div_center_weast"></div>
</div>
<div id="div_center_north">
<div id="div_centerToolbar"></div>
	<div id="div_centerForm" class="search">
		<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right"  width="15%" >${vt.property.parentauthresid}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="AuthTest.parentauthresid" name="AuthTest.parentauthresid" value="${authTest.parentauthresid}" <fisc:authentication sourceName="AuthTest.parentauthresid" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.authresourcetype}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="AuthTest.authresourcetype" name="AuthTest.authresourcetype" value="${authTest.authresourcetype}" <fisc:authentication sourceName="AuthTest.authresourcetype" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.authresourcedesc}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="AuthTest.authresourcedesc" name="AuthTest.authresourcedesc" value="${authTest.authresourcedesc}" <fisc:authentication sourceName="AuthTest.authresourcedesc" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.objecttype}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="AuthTest.objecttype" name="AuthTest.objecttype" value="${authTest.objecttype}" <fisc:authentication sourceName="AuthTest.objecttype" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.object}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="AuthTest.object" name="AuthTest.object" value="${authTest.object}" <fisc:authentication sourceName="AuthTest.object" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.objectdesc}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="AuthTest.objectdesc" name="AuthTest.objectdesc" value="${authTest.objectdesc}" <fisc:authentication sourceName="AuthTest.objectdesc" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.methodname}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="AuthTest.methodname" name="AuthTest.methodname" value="${authTest.methodname}" <fisc:authentication sourceName="AuthTest.methodname" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.methodtype}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="AuthTest.methodtype" name="AuthTest.methodtype" value="${authTest.methodtype}" <fisc:authentication sourceName="AuthTest.methodtype" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.methoddesc}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="AuthTest.methoddesc" name="AuthTest.methoddesc" value="${authTest.methoddesc}" <fisc:authentication sourceName="AuthTest.methoddesc" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.url}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="AuthTest.url" name="AuthTest.url" value="${authTest.url}" <fisc:authentication sourceName="AuthTest.url" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.tcode}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="AuthTest.tcode" name="AuthTest.tcode" value="${authTest.tcode}" <fisc:authentication sourceName="AuthTest.tcode" taskId=""/> >
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="AuthTest.authresourceid" name="AuthTest.authresourceid" value="${authTest.authresourceid}">
	<input type="hidden" id="AuthTest.degree" name="AuthTest.degree" value="${authTest.degree}">
</table>
		</form>
	</div>
</div>
<div id="div_authItem" ></div><div id="div_center_south"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//存放当前选 中树的节点ID
var treeNodeId = '';
//全局路径
var context = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var authresourceid = '${authTest.authresourceid}';	
	
//页面文本
var Txt={
	//AuthTest
	authTest:'${vt.boText}',
	          
	//AuthInfo
	authItem:'${authItemVt.boText}',
	//boText创建
	authItem_Create:'${authItemVt.boTextCreate}',
	//boText复制创建
	authItem_CopyCreate:'${authItemVt.boTextCopyCreate}',
	// 进行【AuthInfo复制创建】操作时，只允许选择一条记录！
	authItem_CopyCreate_AllowOnlyOneItemForOperation:'${authItemVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【AuthInfo复制创建】操作的记录！
	authItem_CopyCreate_SelectToOperation:'${authItemVt.copyCreate_SelectToOperate}',
	//请选择节点后再进行此功能的操作！
	authTest_PlaseSelectTreeNode:'${vt.plaseSelectTreeNode}',
	//非叶结点，不能删除！
	authTest_NotDeleteTreeNode:'${vt.notDeleteTreeNode}',
	//boText复制创建
	authTest_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	authTest_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	authTest_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
	// '提交成功！'
	submitSuccess:'${vt.submitSuccess}',
	// '操作成功！'
	operateSuccess:'${vt.operateSuccess}',
	// 提示
	cue:'${vt.sCue}',
	// 确定
	ok:'${vt.sOk}',
	// 取消
	cancel:'${vt.sCancel}'
};


/**
 * EXT 布局
 */
		var centerToolbars = new Ext.Toolbar({
		items:[
{id:'_addNodeAuthTest',text:'增加同级节点',disabled:true,handler:_addNodeAuthTest,iconCls:' '},'-',
{id:'_addSubNodeAuthTest',text:'增加下级节点',disabled:true,handler:_addSubNodeAuthTest,iconCls:' '},'-',
{id:'_deleteNodeAuthTest',text:'删除节点',disabled:true,handler:_deleteNodeAuthTest,iconCls:' '},'-',
'->','-',
{id:'_saveOrUpdateAuthTest',text:'${vt.mSaveOrUpdate}',disabled:true,handler:_saveOrUpdateAuthTest,iconCls:'icon-table-save'},'-',
{id:'_cancel',text:'${vt.mCancel}',disabled:true,handler:_cancel,iconCls:'icon-undo'},				' '],  
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
	                title:'${vt.boTextDetail}',
	                border:false,
	                layout:'fit',
	                contentEl: 'tree-div',
	                tools:[{
	                    id:'refresh',   
	                    qtip: '刷新${vt.boTextDetail}',
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
			            		title: '${vt.boTextInfo}',
			            		layout:'fit',
			            		autoScroll: true,
			            		contentEl:'div_centerForm'
							 }
							 ,{
								xtype:'tabpanel',
				            	height:450,
				            	id:'tabs',
				            	name:'tabs',
				            	defaults:{bodyStyle:"background-color:#DFE8F6"},
				            	autoScroll: true,
								activeTab:0,
					            items:[
				          
						             {
						            		title: '${authItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'authItemTab',
						            		contentEl:'div_authItem'
						             }
						            ]
	           		     }]
			}]
		}]
});

Ext.onReady(function(){
    var tabsSize = 1;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 1 ; i++){
		   tabs.setActiveTab(1-1-i);
		}
	}
 });
</script>
