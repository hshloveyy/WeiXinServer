<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年06月03日 16点09分39秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象业务预警信息(AdvanceWarnInfo)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/advanceWarn/advanceWarnInfoManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/advanceWarn/advanceWarnInfoManageGen.js"></script>
</head>
<body>
<fisc:grid divId="div_southForm" boName="AdvanceWarnInfo" defaultCondition ="receiveuserid = '${userId}'" editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="false" ></fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.boid}：</td>
		<td  width="30%" >
			<div id="div_boid_sh"></div>
			<input type="hidden" id="boid.fieldName" name="boid.fieldName" value="YADVAWARNINFO.BOID"> 
			<input type="hidden" id="boid.dataType" name="boid.dataType" value="S">  
			<input type="hidden" id="boid.option" name="boid.option" value="like">
			<fisc:searchHelp divId="div_boid_sh" boName="AdvanceWarnInfo" boProperty="boid" searchType="field" hiddenName="boid.fieldValue" valueField="OBJECTNAME" displayField="OBJECTDESC"></fisc:searchHelp>
		</td>
		<td width="15%" align="right">${vt.property.state}：</td>
		<td  width="40%" >
			<div id="div_state_dict"></div>
			<input type="hidden" id="state.fieldName" name="state.fieldName" value="YADVAWARNINFO.STATE"> 
			<input type="hidden" id="state.dataType" name="state.dataType" value="S">  
			<input type="hidden" id="state.option" name="state.option" value="like">
			<fisc:dictionary hiddenName="state.fieldValue" dictionaryName="YDWARNSTATE" divId="div_state_dict" isNeedAuth="false"  defaultValue="1"></fisc:dictionary>
		</td>
</tr>
	<tr>
		<td align="center" colspan="4" height="10">
		</td>
	</tr>
</table>
</form>
</div>
<div id="div_southForm"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

//页面文本
var Txt={
	// 采购订单
	advanceWarnInfo:'${vt.boText}',
	// 您选择了【业务预警信息删除】操作，是否确定继续该操作？
	advanceWarnInfo_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
 * EXT 布局
 */
Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			items:[{
					region:"north",
					contentEl:'div_toolbar',
					border:false,
					height:26
				},{
					region:'center',
					border:false,
					contentEl:'div_center'
				},		
				{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            title:'${vt.boTextDetail}',
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_southForm'
				}]
			}]
	});

	var para = Form.serialize('mainForm');
	reload_AdvanceWarnInfo_grid(para);
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'${vt.mQuery}',handler:_search,iconCls:'icon-search'},'-',
				{id:'_cls',text:'${vt.sClean}',handler:_resetForm,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});

	AdvanceWarnInfo_grid.addListener('rowdblclick', rowDbleclick);
	
	function rowDbleclick(grid, rowIndex, columnIndex, e) {
	    var record = grid.getStore().getAt(rowIndex);   //Get the Record
	    //var fieldName = grid.getColumnModel().getDataIndex(columnIndex);  //Get field name
	    var primarykey = record.get("objectinstanceid");
	    var url = record.get("url");
		_getMainFrame().maintabs.addPanel('预警信息查看','',url + primarykey);
	}
});

//处理业务预警
function _dealwith()
{
	var mygrid = AdvanceWarnInfo_grid;
	var idList = '';
	var warnidList = '';
	var primarykeyList = '';
	if (mygrid.selModel.hasSelection() > 0)
	{
		var records = mygrid.selModel.getSelections();
		var tag = '';		
		for(var i=0;i<records.length;i++)
		{
			idList += tag + records[i].json.warninfoid;
			warnidList += tag + records[i].json.warnid;
			primarykeyList += tag + records[i].json.objectinstanceid;
			tag = ',';
		}
	}
	else
	{
		_getMainFrame().showInfo('请选择需要进行办理的预警信息！');
		return false;
	}

	var rederict = '<%= request.getContextPath() %>/xdss3/advanceWarn/advanceWarnInfoController.spr?action=_dealwith&warnInfoIds=' + idList + '&warnids=' + warnidList + '&primarykeys=' + primarykeyList;
	_getMainFrame().ExtModalWindowUtil.show('预警信息办理',
			rederict,
		    '',
		    reloadGridData,
		    {width:350,height:140});
};

// 处理预警信息后，回调函数：重载grid
function reloadGridData()
{
	AdvanceWarnInfo_store.reload();
}
</script>
