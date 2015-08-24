<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ page import="com.infolion.platform.dpframework.language.LangConstants"%>
<%@ page import="com.infolion.platform.dpframework.language.LanguageService"%>
<%@ page import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgHelper"%>
<%@ page import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>收藏夹菜单新增</title>
</head>

<body>
</body>

</html>
<%
// 系统文本
String strSave = LanguageService.getText(LangConstants.SYS_SAVE);
String strCancel = LanguageService.getText(LangConstants.SYS_CANCEL);
String strTcode = LanguageService.getText(LangConstants.INDEX_TCODE1);
String fileName = LanguageService.getText(LangConstants.INDEX_FILENAME);
//文件夹名称不能为空
String fileNameNotNull =SysMsgHelper.getDPMessage(SysMsgConstants.FILENAMENOTNULL);
%>
<script type="text/javascript" defer="defer">
Ext.onReady(function(){

   	var nodeType = '${favorites.nodeType}';
   	
   	var addFrom = new Ext.form.FormPanel({
    	baseCls: 'x-plain',
    	frame:true,
    	method:'POST',   
	    labelWidth: 80,
	    id: 'addForm', 
	    name: 'addForm',
	    bodyStyle:'padding:5px 5px 0',
	    autoTabs:true,
	    activeTab:0,                    
	    deferredRender:false,
	    border:false,
	    defaultType: 'textfield',
	    items: [
        {   
            xtype:'hidden',   
            name:'nodeId',   
            id:'nodeId',
            value:'${favorites.nodeId}' 
        },
        {   
            xtype:'hidden',   
            name:'parentNodeId',   
            id:'parentNodeId',
            value:'${favorites.parentNodeId}'   
        },
        {   
            xtype:'hidden',   
            name:'nodeType',   
            id:'nodeType',
            value:'${favorites.nodeType}' 
        },
        {   
            xtype:'hidden',   
            name:'favoritesId',   
            id:'favoritesId',
            value:'${favorites.favoritesId}' 
        },
        {   
            xtype:'hidden',   
            name:'userId',   
            id:'userId',
            value:'${favorites.userId}' 
        },
        {   
            xtype:'hidden',   
            name:'orderNo',   
            id:'orderNo',
            value:'${favorites.orderNo}' 
        },
        {   
            xtype:'hidden',   
            name:'nodeType',   
            id:'nodeType',
            value:'${favorites.nodeType}' 
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

	function save(){
   	 if (addFrom.form.isValid()){  
	    	  addFrom.form.submit({   
                url : 'favoritesController.spr?action=save',   
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

    if(nodeType=='0')
    {
       	var rTitle = new Ext.form.TextField(
       		{
    	        fieldLabel: '<%=fileName%>',
    	        name: 'nodeDesc',
    	        id:'nodeDesc',
    	        allowBlank:false,
    	        width:160,
    	        blankText:'<%=fileNameNotNull%>',
    	        anchor:'90%'
    	    });
    }
    else
    {
       	var rTitle = new Ext.form.TextField({ 
    	   	  fieldLabel: '<%=strTcode%>',
    	      name: 'tcode',
    	      id: 'tcode',
    	      width:160,
    	      allowBlank:false,
    	      anchor:'90%'
            }); 
    }
    addFrom.add(rTitle); 
   	addFrom.render(document.body);

});
</script>

