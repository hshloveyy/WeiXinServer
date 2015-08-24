<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2011年06月23日 17点57分23秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象进口报关单(ImpDeclaration)增加页面
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
<form id="mainForm" name="mainForm"  enctype="multipart/form-data"  action="impDeclarationController.spr?action=saveExcel"  method="post" >
<!-- -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<div Id = "message"  align="center"><font size="12">${message}</font></div>
	<tr>
		<td align="right"  width="15%"  ></td>
		<td  width="30%" >
			<input type="file"  name="excelfile"  id="excelfile"  />
		</td>
		<td align="right"  width="15%"  ></td>
		<td   width="40%" ></td>
	</tr>
	&nbsp;<br>&nbsp;<br>
	<tr>
		<td align="right"  width="15%"  ></td>
		<td  width="30%" >
			<input type="submit"  name="btnSubmit"  id="btnSubmit" value="提交保存"  onclick="this.disable = true" />
			 
<%--			<input type="button"  name="btn"  id="btn" value="提交保存2"  onclick="_saveExcel()"/> --%>
		</td>
		<td align="right"  width="15%"  ></td>
		<td   width="40%" ></td>
	</tr>
	&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>
	<input type="hidden" id="calActivityId" name="calActivityId" value="${calActivityId}">
	<div id="uploadForm" name="uploadForm"> </div>
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_south" class="x-hide-display"></div>
<script type="text/javascript">

<%--var form = new Ext.form.FormPanel({--%>
<%--    renderTo: 'uploadForm',--%>
<%--    title: '文件上传',--%>
<%--    labelWidth: 60,--%>
<%--    frame: true,--%>
<%--    url: 'impDeclarationController.spr?action=saveExcel',--%>
<%--    height: 400,--%>
<%--    autoHeight: true,--%>
<%--    fileUpload: true,--%>
<%----%>
<%--    items: [{--%>
<%--        xtype: 'textfield',--%>
<%--        fieldLabel: '文件名',--%>
<%--        name: 'excelfile',--%>
<%--        inputType: 'file' //文件类型--%>
<%--    }],--%>
<%----%>
<%--    buttons: [{--%>
<%--        text: '上传',--%>
<%--        handler: function () {--%>
<%--            form.getForm().submit({--%>
<%--                success: function (form, action) {--%>
<%--                    Ext.Msg.alert('信息', '文件上传成功！');--%>
<%--                },--%>
<%--                failure: function (form, action) {--%>
<%--                    Ext.Msg.alert('错误', '文件上传失败');--%>
<%--                }--%>
<%--            });--%>
<%--        }--%>
<%--    }]--%>
<%----%>
<%--});--%>

</script>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/declaration/impDeclarationExcel.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/declaration/impDeclarationExcelGen.js"></script>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

//是否已经提交流程
var isSubmited = '';

//页面文本
var Txt={
	//进口报关单
	impDeclaration:'${vt.boText}',
	//boText复制创建
	impDeclaration_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	impDeclaration_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	impDeclaration_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
					          
          
          
          
					{id:'_saveOrUpdateImpDeclaration',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateImpDeclaration,iconCls:'icon-table-save'},'-',
          
					{id:'_cancel',text:'${vt.mCancel}',handler:_cancel,iconCls:'icon-undo'},'-',
          
					          
          
          
          
          
          
					' '],  
			renderTo:"div_toolbar"
	});
	Ext.getCmp("_saveOrUpdateImpDeclaration").disable();
	Ext.getCmp("_cancel").disable();
	
Ext.onReady(function(){

});
</script>