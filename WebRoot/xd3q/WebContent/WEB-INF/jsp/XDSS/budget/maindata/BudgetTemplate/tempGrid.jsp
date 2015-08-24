<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月02日 17点04分34秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象预算分类(BudgetSort)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript"  src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetTemplate/budgetTemplateManage.js"></script>
<script language="javascript" type="text/javascript"  src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetTemplate/budgetTemplateManageGen.js"></script>
</head>
<body>
<div id="div_center_south"></div>
</body>
</html>	
<fisc:grid divId="div_center_south" 
boName="BudgetTemplate"  
editable="false" 
defaultCondition=" YBUDTEM.BUDCLASSID='${budclassid}'"
needCheckBox="true" 
needToolbar="true" height="290"
needAutoLoad="true"></fisc:grid>

<script type="text/javascript">
var budclassid = '${budclassid}';

//页面文本
var Txt={
	// 采购订单
	budgetTemplate:'${vt.boText}',
	budgetTemplate_Create:'${vt.boTextCreate}',
	// 复制创建
	budgetTemplate_CopyCreate:'${vt.boTextCopyCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 请选择需要进行【预算模版复制创建】操作的记录！
	budgetTemplate_CopyCreate_SelectToOperation:'${vt.copyCreate_SelectToOperate}',
	// 进行【预算模版复制创建】操作时，只允许选择一条记录！
	budgetTemplate_CopyCreate_AllowOnlyOneItemForOperation:'${vt.copyCreate_AllowOnlyOneItemForOperation}',
	// 您选择了【预算模版批量删除】操作，是否确定继续该操作？
	budgetTemplate_Deletes_ConfirmOperation:'${vt.deletes_ConfirmOperation}',
	// 请选择需要进行【预算模版批量删除】操作的记录！
	budgetTemplate_Deletes_SelectToOperation:'${vt.deletes_SelectToOperate}',
	// 您选择了【预算模版删除】操作，是否确定继续该操作？
	budgetTemplate_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
	// 提示
	cue:'${vt.sCue}',
	// 确定
	ok:'${vt.sOk}',
	// 取消
	cancel:'${vt.sCancel}',
	// 创建
	mCreate:'${vt.mCreate}'
};

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_BudgetTemplate_grid("defaultCondition=YBUDTEM.BUDCLASSID='" + budclassid + "'");
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增预算模版
 */
function _create()
{
	if(_precreate()){
		var para = "&budclassid=" + budclassid;
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.budgetTemplate_Create,BudgetTemplate_grid,contextPath + '/XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_create'+ para);
    }
    _postcreate();
}

Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'fit',
			contentEl:'div_center_south'
	}]});
});
</script>