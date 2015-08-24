<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>翻译语言选择</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_mainFrom" name="div_mainFrom" class="search" >
<form id="mainFrom" action="" name="mainFrom">
<table width="99%" height="220" align="center" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="30%" ></td>
		<td width="70%" >
			<input type="hidden" name="menuId" value="${menuId}" /> 
			<input type="hidden" name="sourceLanguage" value="" />
			<input type="hidden" name="targerLanguage" value="" />
       </td>
	</tr>
	<tr>
		<td align="right" width="30%" >源语言:</td>
		<td width="70%" >
			<div id="div_sourceLanguage"></div>
	    </td>
	</tr>
	<tr>
		<td align="right" width="30%" >目标语言:</td>
		<td width="70%" >
		   <div id="div_targerLanguage"></div>
	    </td>
	</tr>
	<tr>
		<td align="center" colspan="2" height="170">
		</td>
	</tr>	
</table>
</form>
</div>
</body>
</html>
<fisc:searchHelp boName="" searchType="field"  searchHelpName="YHLANGUAGECODE"  boProperty="" hiddenName="sourceLanguage"  divId="div_sourceLanguage" displayField="LAISO" valueField="SPRAS"  callBackHandler="sLanguageCodeCallBack"></fisc:searchHelp>
<fisc:searchHelp boName="" searchType="field"  searchHelpName="YHLANGUAGECODE"  boProperty="" hiddenName="targerLanguage"  divId="div_targerLanguage" displayField="LAISO" valueField="SPRAS"  callBackHandler="tLanguageCodeCallBack"></fisc:searchHelp>

<script type="text/javascript" defer="defer">
/**
 * 源语言搜索帮助回调函数
 */
function sLanguageCodeCallBack(sjson){
	$('sourceLanguage').value = sjson.SPRAS;
}

/**
* 目标语言搜索帮助回调函数
*/
function tLanguageCodeCallBack(sjson){
	$('targerLanguage').value = sjson.SPRAS;
}

Ext.onReady(function(){
   /**
    * 确认翻译
    */
	function _translate(){
		
		var sourceLanguage = $('sourceLanguage').value;
		if (!sourceLanguage){
			_getMainFrame().showInfo('[源语言]为必填项！');
			return;				
		}
		var targerLanguage = $('targerLanguage').value;
		if (!targerLanguage){
			_getMainFrame().showInfo('[目标语言]为必填项！');
			return;				
		}
		if(sourceLanguage && targerLanguage){
			//alert("sourceLanguage:" + sourceLanguage);
			//alert("targerLanguage:" + targerLanguage);
			//alert(sourceLanguage==targerLanguage);
            if(sourceLanguage==targerLanguage)
            {
				_getMainFrame().showInfo('[源语言]、[目标语言]不能一样！');
			    return;
            }
		}
		var param = "&"+ Form.serialize('mainFrom') ;
		_getMainFrame().ExtModalWindowUtil.close();
		_getMainFrame().maintabs.addPanel("翻译菜单文本",'',contextPath + '/basicApp/authManage/menuController.spr?action=_translate'+ param,'');
   
 	 }
	  

	//整体布局
  	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:'center',
			layout:'border',
			buttonAlign:'center',
			border:false,
			items:[{
					region:'center',
		            layout:'fit',
		            border:false,
		            height:160,
		            contentEl:'div_mainFrom'
				}],
		    buttons:[{
		        	text:'确定翻译',
		        	id:'btn_Save',
		        	name:'btn_Save',
		        	minWidth: 20,
		        	handler:_translate
		        },
		        {
		        	text:'取消',
		        	id:'btn_cancel',
		        	minWidth: 20,
		        	name:'btn_cancel',
		        	handler:_cancel
		        }]
		}]
	});
});

//关闭窗体
function _cancel(){
	_getMainFrame().ExtModalWindowUtil.markUpdated();
	_getMainFrame().ExtModalWindowUtil.close();
}

/**
 * 确认转移权限回调函数
 */
function customCallBackHandle(transport)
{
  //alert(transport.responseText);
  var responseUtil = new AjaxResponseUtils(transport.responseText);
  var msg = responseUtil.getMessage();
  var msgType = responseUtil.getMessageType();
  if(msgType!="error")
  {   
	  _getMainFrame().showInfo(msg);	
	  _getMainFrame().ExtModalWindowUtil.markUpdated();
	  _getMainFrame().ExtModalWindowUtil.close();
   }   
}
</script>

