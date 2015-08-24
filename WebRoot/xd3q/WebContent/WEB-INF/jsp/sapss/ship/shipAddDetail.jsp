<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信用证开证审查登记表</title>
<style type="text/css">
.add{
	background-image:url(<%= request.getContextPath()%>/images/fam/add.gif) !important;
}
.delete{
	background-image:url(<%= request.getContextPath()%>/images/fam/delete.gif) !important;
}
.update{
	background-image:url(<%= request.getContextPath()%>/images/fam/refresh.gif) !important;
}
.find{
	background-image:url(<%= request.getContextPath()%>/images/fam/find.png) !important;
}
</style>
<script type="text/javascript">
//var from = '<%=request.getAttribute("popfrom")%>';
//贸易类型
var tradeType = '<%=request.getAttribute("tradeType")%>'

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			collapsible: true,
			height:0,
			contentEl: 'div_basrForm'
		},{
			region:"center",
			layout:'fit',
			buttonAlign:'center',
			items:[{
				region:'center',
				xtype:'tabpanel',
				id:'infotype',
				name:'infotype',
				plain:true,
	            height:300,
	            autoScroll:'true',
	            activeTab: 0,
	            items:[{
	            	title:'信用证开证审查登记表',
	            	contentEl: 'div_main',
	            	id:'creditEntryInfo',
					name:'creditEntryInfo',
					autoScroll:'true',
	            	layout:'fit'
	                  }]
		       }],
				buttons:[{
	            	text:'保存',
	            	handler:function(){
	            		var param1 = Form.serialize('mainForm');
						var param = param1 + "&action=save";
						new AjaxEngine('contractController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
					}

	            },
	            {
	            	text:'提交',
	            	hidden: ${!submit},
	            	handler:closeForm
	            },
	            {
	            	text:'关闭',
	            	handler:closeForm
	            }]
		       }]
	});

   	var sDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'eDate',
	    name:'eDate',
		width: 160,
	    readOnly:true,
		renderTo:'div-entryDate'
   	});
   	
   var payDate = new Ext.form.DateField({
	  	format:'Y-m-d',
		id:'payDate',
	    name:'payDate',
		width: 160,
	    readOnly:true,
		renderTo:'div_payDate'
   	});

});//end of Ext.onReady   

//提示窗口
function showMsg(msg){
	top.Ext.Msg.show({
			title:'提示',
			closable:false,
			msg:msg,
			buttons:{yes:'关闭'},
			fn:Ext.emptyFn,
			icon:Ext.MessageBox.INFO
	});
}

//关闭窗体
function closeForm(){
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否退出并关闭窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
 			if(buttonId=='yes'){
				top.ExtModalWindowUtil.markUpdated();
				top.ExtModalWindowUtil.close();
			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}

//清空窗口
function resetForm(form){
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否清空窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
   				form.reset()
   			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}
//合同选择窗体
function selectContractWin()
{

}
</script>
</head>
<body>
<div id='div_progressBar'></div>
<div id="div_basrForm">
<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
	<table width="100%">
      <tr>
        <td width="11%" align="right">信用证号：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">开证日期：</td>
        <td width="22%" align="left">
        	<div id="div-entryDate"></div>
        </td>
        <td width="11%" align="right">开证行：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="2" value=""/>
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">申请人：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">国别/地区：</td>
        <td width="22%" align="left">
        	<div id="createDateDiv"></div>
        </td>
        <td width="11%" align="right">受益人：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="2" value=""/>
        </td>
      </tr>
      
     <tr>
        <td width="11%" align="right">付款方式：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">金额：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">关联合同号：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="2" value=""/>
        	<input type="button" value="..." onclick="selectContractWin()">
        </td>
      </tr>
      
      <tr>
        <td width="11%" align="right">货物品名及规格：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">唛头：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right"></td>
        <td width="22%" align="left">
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">发票：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">提单：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">保险单：</td>
        <td width="22%" align="left">
            <input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">品质(分析证)：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">受益人证明：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">产地证:</td>
        <td width="22%" align="left">
            <input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">装箱单：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">起运电：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">其他单据:</td>
        <td width="22%" align="left">
            <input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">装期：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">效期及地点：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">可否分批：</td>
        <td width="22%" align="left">
            <SELECT NAME="isA" SIZE="1">
                <OPTION value="">请选择</option>
				<OPTION VALUE="Y">是</option>
				<OPTION VALUE="N">否</option>
			</SELECT>
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">装运港：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">目的港：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">可否转船：</td>
        <td width="22%" align="left">
            <SELECT NAME="isB" SIZE="1">
                <OPTION value="">请选择</option>
				<OPTION VALUE="Y">是</option>
				<OPTION VALUE="N">否</option>
			</SELECT>
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">核销单号：</td>
        <td width="22%" align="left">
        	<input name="orgName" type="text" size="20" tabindex="1" value=""/>
        </td>
        <td width="11%" align="right">付款日：</td>
        <td width="22%" align="left">
        	<div id="div_payDate"></div>
        </td>
        <td width="11%" align="right">是否预保：</td>
        <td width="22%" align="left">
            <SELECT NAME="isB" SIZE="1">
                <OPTION value="">请选择</option>
				<OPTION VALUE="Y">是</option>
				<OPTION VALUE="N">否</option>
			</SELECT>
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">特别条款：</td>
        <td width="88%" align="left" colspan="5">
        </td>
      </tr>
      <tr>
        <td width="11%" align="right"></td>
        <td width="88%" align="left" colspan="5">
           <textarea cols="50" rows="10" id="contactus" name="contactus"></textarea>
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">修改历史：</td>
        <td width="88%" align="left" colspan="5">
        </td>
      </tr>
      <tr>
        <td width="11%" align="right"></td>
        <td width="88%" align="left" colspan="5">
        </td>
      </tr>
	</table>
	<div id="div_CreditEntryInfo"></div>
</form>
</div>
</div>
</body>
</html>

    
