<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年05月28日 09点10分41秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象供应商信用额度配置(ProviderCreditConf)增加页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/ceditValueControl/providerCreditConfAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/ceditValueControl/providerCreditConfAddGen.js"></script>
</head>
<body>
<fisc:grid divId="div_providerCreditProj" boName="ProviderCreditProj" needCheckBox="true" editable="true" defaultCondition=" YPROVCREDPROJ.CONFIGID='${providerCreditConf.configid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="ProviderCreditConf.providerid" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.providerid}：</td>
		<td  width="30%" >
			<div id="div_providerid_sh"></div>
			<fisc:searchHelp divId="div_providerid_sh" boName="ProviderCreditConf" boProperty="providerid" value="${providerCreditConf.providerid}"></fisc:searchHelp>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="ProviderCreditConf.credittype" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.credittype}：</td>
		<td   width="40%" >
			<div id="div_credittype_dict"></div>
			<fisc:dictionary boName="ProviderCreditConf" boProperty="credittype" dictionaryName="YDPROVIDERCREDITTYPE" divId="div_credittype_dict" isNeedAuth="false"  value="${providerCreditConf.credittype}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="ProviderCreditConf.totalvalue" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.totalvalue}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="ProviderCreditConf.totalvalue" name="ProviderCreditConf.totalvalue" value="${providerCreditConf.totalvalue}" <fisc:authentication sourceName="ProviderCreditConf.totalvalue" nodeId="${workflowNodeDefId}"/> >
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="ProviderCreditConf.startingtime" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.startingtime}：</td>
		<td  width="30%" >
			<input type="text" id="ProviderCreditConf.startingtime" name="ProviderCreditConf.startingtime" value="">
				<fisc:calendar applyTo="ProviderCreditConf.startingtime"  divId="" fieldName="" defaultValue="${providerCreditConf.startingtime}"></fisc:calendar>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="ProviderCreditConf.endtime" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.endtime}：</td>
		<td   width="40%" >
			<input type="text" id="ProviderCreditConf.endtime" name="ProviderCreditConf.endtime" value="">
				<fisc:calendar applyTo="ProviderCreditConf.endtime"  divId="" fieldName="" defaultValue="${providerCreditConf.endtime}"></fisc:calendar>
		</td>
	</tr>

	<input type="hidden" id="ProviderCreditConf.client" name="ProviderCreditConf.client" value="${providerCreditConf.client}">
	<input type="hidden" id="ProviderCreditConf.configid" name="ProviderCreditConf.configid" value="${providerCreditConf.configid}">
	<input type="hidden" id="ProviderCreditConf.lastmodifytime" name="ProviderCreditConf.lastmodifytime" value="${providerCreditConf.lastmodifytime}">
	<input type="hidden" id="ProviderCreditConf.createtime" name="ProviderCreditConf.createtime" value="${providerCreditConf.createtime}">
	<input type="hidden" id="ProviderCreditConf.creator" name="ProviderCreditConf.creator" value="${providerCreditConf.creator}">
	<input type="hidden" id="ProviderCreditConf.lastmodifyer" name="ProviderCreditConf.lastmodifyer" value="${providerCreditConf.lastmodifyer}">
	<input type="hidden" id="calActivityId" name="calActivityId" value="${calActivityId}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_providerCreditProj" ></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

//是否已经提交流程
var isSubmited = '';

//页面文本
var Txt={
	//供应商信用额度配置
	providerCreditConf:'${vt.boText}',
	          
	//供应商授限立项配置
	providerCreditProj:'${providerCreditProjVt.boText}',
	//boText创建
	providerCreditProj_Create:'${providerCreditProjVt.boTextCreate}',
	//boText复制创建
	providerCreditProj_CopyCreate:'${providerCreditProjVt.boTextCopyCreate}',
	// 进行【供应商授限立项配置复制创建】操作时，只允许选择一条记录！
	providerCreditProj_CopyCreate_AllowOnlyOneItemForOperation:'${providerCreditProjVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【供应商授限立项配置复制创建】操作的记录！
	providerCreditProj_CopyCreate_SelectToOperation:'${providerCreditProjVt.copyCreate_SelectToOperate}',
	//boText复制创建
	providerCreditConf_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	providerCreditConf_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	providerCreditConf_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
							layout:'anchor',
				            height:600,
				            border:false,
				            autoScroll:true,
				            items:[
				              {
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		autoScroll: true,
				            		border:false,
				            		//height:0,
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
						            		title:'${providerCreditProjVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'providerCreditProjTab',
						            		contentEl:'div_providerCreditProj'
						            	}
						    ]}
						   ]}
]
				}
                 ]
	});
	
	
	var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					          
					{id:'_saveOrUpdateProviderCreditConf',text:'${vt.mSaveOrUpdate}',handler:_check_save,iconCls:'icon-table-save'},'-',
          
					{id:'_cancel',text:'${vt.mCancel}',handler:_cancel,iconCls:'icon-undo'},
					' '],  
			renderTo:"div_toolbar"
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
	var cbiToolbar = ProviderCreditProj_grid.getTopToolbar();
	cbiToolbar.items.get('ProviderCreditProjaddRow').hide();		// 隐藏"增加行"按钮
 });
//});
</script>
