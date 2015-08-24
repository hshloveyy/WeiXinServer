<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2011年11月25日 11点42分18秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象利率(Rate)增加页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Rate.rate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.rate}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Rate.rate" name="Rate.rate" value="${rate.rate}" <fisc:authentication sourceName="Rate.rate" nodeId="${workflowNodeDefId}"/> >
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Rate.startdate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.startdate}：</td>
		<td   width="40%" >
			<input type="text" id="Rate.startdate" name="Rate.startdate" value="">
				<fisc:calendar applyTo="Rate.startdate"  divId="" fieldName="" defaultValue="${rate.startdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Rate.enddate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.enddate}：</td>
		<td  width="30%" >
			<input type="text" id="Rate.enddate" name="Rate.enddate" value="">
				<fisc:calendar applyTo="Rate.enddate"  divId="" fieldName="" defaultValue="${rate.enddate}"></fisc:calendar>
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="Rate.client" name="Rate.client" value="${rate.client}">
	<input type="hidden" id="Rate.rateid" name="Rate.rateid" value="${rate.rateid}">
	<input type="hidden" id="Rate.creator" name="Rate.creator" value="${rate.creator}">
	<input type="hidden" id="Rate.createtime" name="Rate.createtime" value="${rate.createtime}">
	<input type="hidden" id="Rate.lastmodifyer" name="Rate.lastmodifyer" value="${rate.lastmodifyer}">
	<input type="hidden" id="Rate.lastmodifytime" name="Rate.lastmodifytime" value="${rate.lastmodifytime}">
	<input type="hidden" id="Rate.dept_id" name="Rate.dept_id" value="${rate.dept_id}">
	<input type="hidden" id="calActivityId" name="calActivityId" value="${calActivityId}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_south" class="x-hide-display"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/rate/rateAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/rate/rateAddGen.js"></script>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

//是否已经提交流程
var isSubmited = '';

//页面文本
var Txt={
	//利率
	rate:'${vt.boText}',
	//boText复制创建
	rate_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	rate_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	rate_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
							   ]
				   }]
				}
                 ]
	});
	
	
	var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					          
					{id:'_saveOrUpdateRate',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateRate,iconCls:'icon-table-save'},'-',
          
					{id:'_cancel',text:'${vt.mCancel}',handler:_cancel,iconCls:'icon-undo'},'-',
          
					          
          
          
					' '],  
			renderTo:"div_toolbar"
	});
	
	

//});
</script>


