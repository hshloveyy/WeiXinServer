<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月15日 19点35分40秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象AuthTest(AuthTest)增加页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/auth/authTestAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/auth/authTestAddGen.js"></script>
</head>
<body>
<fisc:grid divId="div_authItem" boName="AuthItem" needCheckBox="true" editable="true" defaultCondition=" YAUTHITEM.AUTHRESOURCEID='${authTest.authresourceid}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
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
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="AuthTest.authresourceid" name="AuthTest.authresourceid" value="${authTest.authresourceid}">
	<input type="hidden" id="AuthTest.degree" name="AuthTest.degree" value="${authTest.degree}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_authItem" ></div>
<div id="div_authInfo" class="x-hide-display">
<form id="authInfoForm" name="authInfoForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td align="right" width="15%">${authInfoVt.property.memo}：</td>
<td width="30%" >
<input type="text" class="inputText" id="AuthInfo.memo" name="AuthInfo.memo" value="${authTest.authInfo.memo}" <fisc:authentication sourceName="AuthInfo.memo" taskId=""/>>
</td>
<td></td><td></td>
</tr>
<input type="hidden" id="AuthInfo.authinfoid" name="AuthInfo.authinfoid" value="${authTest.authInfo.authinfoid}">
</table>
</form>
</div>
<div id="div_authTest" class="x-hide-display"> 
<form id="authTestForm" name="authTestForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
			<tr>
				<td align="right"  width="15%" >${vt.property.methodtype}：</td>
				<td  width="30%" >
					<input type="text" class="inputText" id="AuthTest.methodtype" name="AuthTest.methodtype" value="${authTest.methodtype}"   <fisc:authentication sourceName="AuthTest.methodtype" taskId=""/>>
				</td>
				<td align="right"  width="15%" >${vt.property.methoddesc}：</td>
				<td   width="40%" >
					<input type="text" class="inputText" id="AuthTest.methoddesc" name="AuthTest.methoddesc" value="${authTest.methoddesc}"   <fisc:authentication sourceName="AuthTest.methoddesc" taskId=""/>>
				</td>
				</tr>
			<tr>
				<td align="right"  width="15%" >${vt.property.url}：</td>
				<td  width="30%" >
					<input type="text" class="inputText" id="AuthTest.url" name="AuthTest.url" value="${authTest.url}"   <fisc:authentication sourceName="AuthTest.url" taskId=""/>>
				</td>
				<td align="right"  width="15%" >${vt.property.tcode}：</td>
				<td   width="40%" >
					<input type="text" class="inputText" id="AuthTest.tcode" name="AuthTest.tcode" value="${authTest.tcode}"   <fisc:authentication sourceName="AuthTest.tcode" taskId=""/>>
				</td>
				</tr>
	</table>
	</form>
	</div> 
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

//页面文本
var Txt={
	//AuthTest
	authTest:'${vt.boText}',
	          
	//AuthInfo
	authItem:'${authItemVt.boText}',
	//boText复制创建
	authItem_CopyCreate:'${authItemVt.boTextCopyCreate}',
	// 进行【AuthInfo复制创建】操作时，只允许选择一条记录！
	authItem_CopyCreate_AllowOnlyOneItemForOperation:'${authItemVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【AuthInfo复制创建】操作的记录！
	authItem_CopyCreate_SelectToOperation:'${authItemVt.copyCreate_SelectToOperate}',
          
	//AuthInfo
	authInfo:'${authInfoVt.boText}',
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
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
					region:'center',
					layout:'border',
					border:false,
					items:[{
							region:'north',
							layout:'fit',
							height:26,
							border:false,
							contentEl:'div_toolbar'
						},{
							region:'center',
							layout: 'anchor',
				            height:600,
				            border:false,
				            autoScroll: true,
				            items:[{
				            		title: '${vt.boTextInfo}',
				            		layout:'fit',
				            		autoScroll: true,
				            		contentEl:'div_center'
						}
						,{
									xtype:'tabpanel',
					            	height:310,
					            	id:'tabs',
					            	name:'tabs',
					            	defaults:{bodyStyle:"background-color:#DFE8F6"},
					            	autoScroll: true,
									activeTab:0,
						            items:[
									    {
									    		id:'authTestTab',
							            		title: '${vt.boText}',
							            		layout:'fit',
							            		autoWidth:true,
							            		bodyStyle:"background-color:#DFE8F6",
							            		contentEl:'div_authTest'
							             },
				          
						                {
						            		title: '${authItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'authItemTab',
						            		contentEl:'div_authItem'
						            	},
          
						                {
						            		title: '${authInfoVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'authInfoTab',
						            		contentEl:'div_authInfo'
						            	}
						]
						}]
						}]
				}
				 ]
	});
	var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					          
          
          
          
					{id:'_cancel',text:'${vt.mCancel}',handler:_cancel,iconCls:'icon-undo'},
					' '],  
			renderTo:"div_toolbar"
	});
Ext.onReady(function(){
    var tabsSize = 3;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 3 ; i++){
		   tabs.setActiveTab(3-1-i);
		}
	}
 });
//});
</script>
