<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.infolion.platform.dpframework.language.LangConstants"%>
<%@ page import="com.infolion.platform.dpframework.language.LanguageService"%>
<%@ page import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgHelper"%>
<%@ page import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>收藏夹菜单标题修改</title>
</head>

<body>
</body>

</html>
<%
// 系统文本
String strSave = LanguageService.getText(LangConstants.SYS_SAVE);
String strCancel = LanguageService.getText(LangConstants.SYS_CANCEL);
String oldTitleName = LanguageService.getText(LangConstants.INDEX_OLDTITLENAME);
String titleName = LanguageService.getText(LangConstants.INDEX_TITLENAME);
//文件夹名称不能为空
String titleNameNotNull = SysMsgHelper.getDPMessage(SysMsgConstants.TITLENAMENOTNULL);
%>
<script type="text/javascript" defer="defer">
Ext.onReady(function(){

   	var nodeType = '${favorites.nodeType}';
   	
   	var addFrom = new Ext.form.FormPanel({
    	baseCls: 'x-plain',
    	frame:true,  
	    labelWidth: 70,
	    id: 'addForm', 
	    name: 'addForm',
	    bodyStyle:'padding:5px 5px 0',
	    autoTabs:true,
	    activeTab:0,                    
	    deferredRender:false,
	    border:false,
	    defaultType: 'textfield',
	    items: [{
    	        fieldLabel: '<%=oldTitleName%>',
    	        name: 'nodeDescOld',
    	        id:'nodeDescOld',
    	        readOnly:true,
    	        allowBlank:false,
                value:'${nodeDescOld}',
    	        width:150,
    	        blankText:'<%=titleNameNotNull%>'
    	        //anchor:'85%' 
	    },
	    {
	        fieldLabel: '<%=titleName%>',
	        name: 'nodeDescNew',
	        id:'nodeDescNew',
	        allowBlank:false,
	        width:150,
	        blankText:'<%=titleNameNotNull%>'
	       // anchor:'75%' 
    	},
        {   
            xtype:'hidden',   
            name:'nodeId',   
            id:'nodeId',
            value:'${nodeId}' 
        }
	    ],
	    buttons:[{
        	text:'<%=strSave%>',
        	id:'btn_Save',
        	name:'btn_Save',
        	minWidth: 20,
        	handler:save
        },
        {
        	text:'<%=strCancel%>',
        	id:'btn_cancel',
        	minWidth: 20,
        	name:'btn_cancel',
        	handler:function(){window.parent.FormEditWin.close();}
        }]
	});	

  //保存回调函数
   	function customCallBackHandle(transport)
   	{
   	  var responseUtil = new AjaxResponseUtils(transport.responseText);
   	  var msg = responseUtil.getMessage();
   	  var msgType = responseUtil.getMessageType();
   	  
   	  if(msgType!="error"){ _getMainFrame().ExtModalWindowUtil.close(); _getMainFrame().showInfo(msg);}
   	 
   	}
   	
	function save(){
   	 if (addFrom.form.isValid()){  
   	   	 /**
   		var param ="";//=addFrom.form.getValues() ;
   		param += encodeURI("nodeDescNew=" + Ext.getDom('nodeDescNew').value + "&nodeId=" + Ext.getDom('nodeId').value + "&action=modifyNodeText");
   		new AjaxEngine('<%=request.getContextPath()%>/index/favoritesController.spr', 
   			   {method:"post", parameters: param, onComplete: callBackHandle});
	    }
        **/
	  addFrom.form.submit({   
          url : 'favoritesController.spr?action=modifyNodeText',   
          success : function(form, action) { 
				     window.parent.FormEditWin.reloadNavNode();
		         	 window.parent.FormEditWin.close();
	                }   
         ,   
         failure : function(form, action) {
        	 _getMainFrame().showError(action.result.msg); 
         }  
    	 });   
		}
 	  }

   	addFrom.render(document.body);

});
</script>

