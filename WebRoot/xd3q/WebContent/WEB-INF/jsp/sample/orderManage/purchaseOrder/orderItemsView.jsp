<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月31日 15点00分59秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象采购行项目(OrderItems)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购行项目查看页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/orderManage/purchaseOrder/orderItemsView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/orderManage/purchaseOrder/orderItemsViewGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" valign="top"><font color="red">★</font>${vt.property.quantity}：</td>
		<td width="30%"  colspan="3" >
			<textarea rows="4" cols="54" id="OrderItems.quantity" name="OrderItems.quantity"  <fisc:authentication sourceName="OrderItems.quantity" taskId="${workflowTaskId}"/>>${orderItems.quantity}</textarea>
		</td>
	</tr>
		<tr>
		 <td width="20%" align="right" ><font color="red">★</font>${vt.property.materielNo}：</td>
		 <td width="30%" >
				<div id="div_materielNo_sh"></div>
				<fisc:searchHelp divId="div_materielNo_sh" boName="OrderItems" boProperty="materielNo"  value="${orderItems.materielNo}"></fisc:searchHelp>
			</td>
		      <td></td><td></td>
			</tr>
		<tr>
		 <td width="20%" align="right" >${vt.property.deliveryDate}：</td>
		 <td width="30%" >
				<input type="text" id="OrderItems.deliveryDate" name="OrderItems.deliveryDate" value="">
					<fisc:calendar applyTo="OrderItems.deliveryDate" format="Ymd" divId="" fieldName=""  defaultValue="${orderItems.deliveryDate}" ></fisc:calendar>
			</td>
		 <td width="20%" align="right" >${vt.property.measureUnit}：</td>
		 <td width="30%" >
				<div id="div_measureUnit_dict"></div>
				<fisc:dictionary boName="OrderItems" boProperty="measureUnit" dictionaryName="YDMEASUREUNIT" divId="div_measureUnit_dict" isNeedAuth="false"  value="${orderItems.measureUnit}"  ></fisc:dictionary>
			</td>
			</tr>
		<tr>
		 <td width="20%" align="right" valign="top">${vt.property.address}：</td>
		 <td width="30%"  colspan="3" >
				<textarea rows="4" cols="54" id="OrderItems.address" name="OrderItems.address"  <fisc:authentication sourceName="OrderItems.address" taskId="${workflowTaskId}"/>>${orderItems.address}</textarea>
			</td>
			</tr>
<input type="hidden" id="OrderItems.orderItemsId" name="OrderItems.orderItemsId" value="">
<input type="hidden" id="OrderItems.purchaseOrderId" name="OrderItems.purchaseOrderId" value="">
<input type="hidden" id="OrderItems.itemNo" name="OrderItems.itemNo" value="">
</table>
</form>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">

//页面文本
var Txt={
	// '操作成功！'
	operateSuccess:'${vt.operateSuccess}',
	// 取消
	cancel:'${vt.sCancel}'
};

var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					{id:'_cancel',text:'${vt.sCancel}',handler:_cancel,iconCls:'icon-undo'},'-',
					' '
				   ],
			renderTo:'div_toolbar'
});
/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[{
					region:'north',
					layout:'fit',
					height:26,
					border:false,
					contentEl:'div_toolbar'
	   		   },{
					region:'center',
					border:false,
					buttonAlign:'center',
					items:[{
							layout:'fit',
							region:'center',
							height:435.0,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
