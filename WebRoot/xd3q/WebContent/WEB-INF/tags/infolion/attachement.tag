<%-- 
  - Author(s):林进旭
  - Date: 2009-05-14
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:业务附件控件
  -
--%>

<%@ tag pageEncoding="utf-8"%>
<%@ tag import="com.infolion.platform.dpframework.uicomponent.attachement.web.AttachementTagHandler"%>
<%@ tag import="com.infolion.platform.component.fileProcess.services.AttachementBusinessJdbcService"%>
<%@ tag import="com.infolion.platform.util.EasyApplicationContextUtils"%>
<%@ tag import="org.apache.commons.lang.StringUtils"%>
<%@ tag import="com.infolion.sapss.Constants"%>
<%@ attribute name="divId" required="true" rtexprvalue="true"
	description="div id,用于业务附件控件定位"%>
<%@ attribute name="businessId" required="true" rtexprvalue="true" description="业务ID"%>
<%@ attribute name="boIdField" required="false" description="业务字段"%>
<%@ attribute name="boName" required="true" rtexprvalue="true" description="业务对象名称"%>
<%@ attribute name="boProperty" required="false" rtexprvalue="true" description="业务对象属性名"%>
<%@ attribute name="allowUpload" required="false" rtexprvalue="true" description="是否允许上传附件"%>
<%@ attribute name="allowDelete" required="true" rtexprvalue="true" description="是否允许删除附件"%>
<%@ attribute name="gridPageSize" required="false"  description="grid每页显示条数"%>
<%@ attribute name="gridHeight" required="false"  description="grid高度"%>
<%@ attribute name="gridTitle" required="false"  description="grid标题"%>
<%@ attribute name="needToolbar" required="false"  description="是否需要显示gridtoolbar"%>
<%@taglib tagdir="/WEB-INF/tags/infolion" prefix="fisc"%>
<%@tag import="com.infolion.platform.dpframework.language.LanguageService"%>
<%@tag import="com.infolion.platform.dpframework.language.LangConstants"%>
<%@tag import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgHelper"%>
<%@tag import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants"%>
<script language="javascript" src="<%= request.getContextPath() %>/js/ext/CrossDomainAjax.js"></script>

<script type="text/javascript" defer="defer">
var filepath = '';
   	function fileUploadCheck(){
	  	Ext.Ajax.request({
			url: encodeURI('/attachementController.spr?action=fileUploadCheck&userId=<%=Constants.ins().getCurrentUserId()%>'+'&filepath='+filepath),
			params:'',
			success: function(response, options){
				var resp = Ext.util.JSON.decode(response.responseText);
				//_getMainFrame().showInfo(resp.msg);
				top.ExtModalWindowUtil.alert('提示',resp.msg);
			}	
	    });
   	}
   	
    function valiDateTimes(str){                 
     //var reg =  /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
       var reg =  /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/;        
     var r = str.match(reg); 
     if(r==null) return false; 
     r[2]=r[2]-1; 
     var d= new Date(r[1], r[2],r[3], r[4],r[5], r[6]); 
          
     if(d.getFullYear()!=r[1]) return false; 
     if(d.getMonth()!=r[2]) return false; 
     if(d.getDate()!=r[3]) return false; 
     if(d.getHours()!=r[4]) return false; 
     if(d.getMinutes()!=r[5]) return false; 
     if(d.getSeconds()!=r[6]) return false; 
     return true;
    }
<%
	boolean isAdd = false;
	boolean isDelete = false;
	
	String allowUpload = (String)jspContext.getAttribute("allowUpload");
	String allowDelete = (String)jspContext.getAttribute("allowDelete");

	jspContext.setAttribute("contextPath", request.getContextPath());
	//默认的组件实例名称
	jspContext.setAttribute("attachementObj", "atc_" + divId);
	//组件ID,Name属性值。
	jspContext.setAttribute("attachementName", "atc" + divId);
	
	AttachementTagHandler attachementTagHandler = new AttachementTagHandler();
	isAdd = attachementTagHandler.addPermissions("");
	isDelete = attachementTagHandler.deletePermissions("");
	
	if(StringUtils.isBlank(businessId.trim()))
	{
		jspContext.setAttribute("businessId", "");
	}
	else
	{
		jspContext.setAttribute("businessId", businessId);
	}
        
	// 页面文本
	String txtConfirmDelete=SysMsgHelper.getDPMessage(SysMsgConstants.ConfirmDelete);
	String txtOk=LanguageService.getText(LangConstants.SYS_OK);
	String txtCancel=LanguageService.getText(LangConstants.SYS_CANCEL);
%>

<%/*******20140904modify by cat 新增对附件的处理***********/
	AttachementBusinessJdbcService abs = (AttachementBusinessJdbcService)EasyApplicationContextUtils.getBeanByName("attachementBusinessJdbcService");
	int i = abs.fileUploadModifyCheck(businessId);
%>
/*
* 下载业务附件
*/
function _download${boProperty}Attachement(id,url)
{
	var strurl =  url + "&attachementId=" + id ;
	//alert(strurl);
	var win=window.open(strurl,'','top=0, left=0,width=0,height=0,toolbar=no,scrollbars=no,menubar=no,screenX=0,screenY=0,resizable=no,location=no, status=no');
}

/*
* 单一删除业务附件
*/
function _delete${boProperty}Attachement(id,url)
{  
	if('true'!='<%=allowDelete%>'){
	  alert('无权限删除附件');
	  return ;
	}
	
	var attachementIdList =  "'"+id+"',";
	
	Ext.Msg.show({
		title:'<%=LanguageService.getText(LangConstants.SYS_CUE)%>',
		msg: '<%=txtConfirmDelete%>',
		buttons: {yes:'<%=txtOk%>', no:'<%=txtCancel%>'},
		fn: function(buttonId,text){
			if(buttonId=='yes'){
			SetCrossDomain();
			Ext.Ajax.request({
				url: '<%=Constants.FILE_LOCATION_URL%>/attachementController.spr?action=delete&userId=<%=Constants.ins().getCurrentUserId()%>&attachementIdList='+ attachementIdList,
				params:'',
				scriptTag: true,
				success: function(response, options){
					_getMainFrame().showInfo(response.responseText);
					${boProperty}Attachement_grid.getStore().reload();
				}	
			});
		}
		},
		icon: Ext.MessageBox.QUESTION
    });
}

function _download${boProperty}Attachement(){
if (${boProperty}Attachement_grid.selModel.hasSelection()){
		var records = ${boProperty}Attachement_grid.selModel.getSelections();
        var fileName = records[0].data.fileName;
        var url = encodeURI('<%=Constants.FILE_LOCATION_URL%>/extComponentServlet?type=fileDownload&fileName='+fileName);
        window.open(url,'_self');
		
	}else{
		_getMainFrame().showInfo('<%=SysMsgHelper.getDPMessage(SysMsgConstants.SelectOneRecord)%>');
	}
}

/*
 * 批量删除业务附件
 */
function _deletes${boProperty}Attachement()
{
    
    if('true'!='<%=allowDelete%>'){
	  alert('无权限删除附件');
	  return ;
	}
	
	if (${boProperty}Attachement_grid.selModel.hasSelection()){
		var records = ${boProperty}Attachement_grid.selModel.getSelections();
        var attachementIdList = "";
        var n = records.length ;
        var i = 0;
        
		if (n > 0)
		{
			for(i=0;i<n;i++)
			{   
				attachementIdList = attachementIdList+ "'" + records[i].data.attachementId + "',";
			}
		}

		Ext.Msg.show({
			title:'<%=LanguageService.getText(LangConstants.SYS_CUE)%>',
			msg: '<%=txtConfirmDelete%>',
			buttons: {yes:'<%=txtOk%>', no:'<%=txtCancel%>'},
			fn: function(buttonId,text){
				if(buttonId=='yes'){
				SetCrossDomain();
				Ext.Ajax.request({
					url: '<%=Constants.FILE_LOCATION_URL%>/attachementController.spr?action=delete&userId=<%=Constants.ins().getCurrentUserId()%>&attachementIdList='+ attachementIdList,
					params:'',
					scriptTag: true,
					success: function(response, options){
						_getMainFrame().showInfo(response.responseText);
						${boProperty}Attachement_grid.getStore().reload();
					}	
				});
			}
			},
			icon: Ext.MessageBox.QUESTION
	    });
		
	}else{
		_getMainFrame().showInfo('<%=SysMsgHelper.getDPMessage(SysMsgConstants.SelectOneRecord)%>');
	}
}
/*
 * 上传业务附件
 */
function _upload${boProperty}Attachement()
{
    if('true'!='<%=allowUpload%>'){
	  alert('无权限上传附件');
	  return ;
	}
	
	var uploadwin ;
	var formMain ;
	
	formMain=new Ext.form.FormPanel({
		frame:true,
		baseCls:'x-plain',
		enctype:'multipart/form-data',
		autoHeight:true,
		labelWidth:40,
		width:500,
		fileUpload:true,
		bodyStyle:'padding: 10px 10px 0 10px;',
		defaults:{
			anchor:'95%',
			msgTarget:'side'
		},
		items:[{
			id:'fileSelected',
			xtype:'textfield',
			height:20,
			fieldLabel:'<%=LanguageService.getText(LangConstants.SYS_FILE)%>',
			name:'upload',
			inputType:'file',
			allowBlank:false,
			blankText:'<%=SysMsgHelper.getDPMessage(SysMsgConstants.SelectFile)%>',
			width:500,
			anchor:'95%'
		},{
			id:'description',
			width:500,
			allowBlank:true,
			xtype:'textarea',
			fieldLabel:'<%=LanguageService.getText(LangConstants.SYS_DESCRIPTION)%>'
		}]
	});	

		uploadwin = new Ext.Window({
	    title: '<%=SysMsgHelper.getDPMessage(SysMsgConstants.UploadAttachement)%>',
	    width: 500,
	    height:180,
	    layout: 'fit',
	    iconCls:'window',
	    plain:true,
	    modal:true,
	    bodyStyle:'padding:5px;',
	    buttonAlign:'center',
	    maximizable:false,
		closeAction:'close',
		closable:false,
		collapsible:false,
	    items: formMain,
	    buttons: [
	    {
	        text: '<%=LanguageService.getText(LangConstants.SYS_UPLOAD)%>',
	        handler:function()
	        {
		    	var boId = '${businessId}' ;//Ext.getDom('${boIdField}').value;
		    	if(boId==""&&Ext.get('${boIdField}'))boId=Ext.get('${boIdField}').dom.value;
				var boName = '${boName}';
				var boProperty = '${boProperty}';
				var	result = '&boId='+boId +'&boName='+ boName +'&boProperty='+ boProperty;
				if(formMain.form.isValid()){
					formMain.getForm().submit({
						method:'POST', 
						url:encodeURI('<%=Constants.FILE_LOCATION_URL%>/attachementController.spr?action=upload2&userId=<%=Constants.ins().getCurrentUserId()%>'+result +'&description='+Ext.getCmp('description').getValue()+'&filepath='+Ext.getCmp('fileSelected').getValue()),
						success:function(form,action){
						    filepath = Ext.getCmp('fileSelected').getValue();
						    setTimeout("fileUploadCheck()",1000);
						    formMain.form.reset();
				        	uploadwin.close();
				        	${boProperty}Attachement_grid.getStore().reload();
				        	//_getMainFrame().showInfo(action.result.msg);							
						},
						waitMsg: '<%=SysMsgHelper.getDPMessage(SysMsgConstants.FileUploading)%>'
					}
				)}
	        }	
	    },
	    {
	        text: '<%=txtCancel%>',
	        handler:function()
	        {
	        	formMain.form.reset();
	        	 uploadwin.close();
	        }
	    }]
	});
		
   uploadwin.show();
}

<%if(i==1){%>
/*
 * 批量删除业务附件_附件编辑
 */
function _deletes${boProperty}Attachement_modify()
{
	
	if (${boProperty}Attachement_grid.selModel.hasSelection()){
		var records = ${boProperty}Attachement_grid.selModel.getSelections();
        var attachementIdList = "";
        var n = records.length ;
        var i = 0;
        
		if (n > 0)
		{
			for(i=0;i<n;i++)
			{   
				attachementIdList = attachementIdList+ records[i].data.attachementId + ",";
			}
		}

		Ext.Msg.show({
			title:'<%=LanguageService.getText(LangConstants.SYS_CUE)%>',
			msg: '<%=txtConfirmDelete%>',
			buttons: {yes:'<%=txtOk%>', no:'<%=txtCancel%>'},
			fn: function(buttonId,text){
				if(buttonId=='yes'){
				SetCrossDomain();
				Ext.Ajax.request({
					url: '<%=Constants.FILE_LOCATION_URL%>/attachementController.spr?action=deleteModify&userId=<%=Constants.ins().getCurrentUserId()%>&attachementIdList='+ attachementIdList,
					params:'',
					scriptTag: true,
					success: function(response, options){
						_getMainFrame().showInfo(response.responseText);
						${boProperty}Attachement_grid.getStore().reload();
					}	
				});
			}
			},
			icon: Ext.MessageBox.QUESTION
	    });
		
	}else{
		_getMainFrame().showInfo('<%=SysMsgHelper.getDPMessage(SysMsgConstants.SelectOneRecord)%>');
	}
}


/*
 * 上传业务附件
 */
function _upload${boProperty}Attachement_modify()
{
  var uploadwinM ;
  var formMainM ;
  
  formMainM=new Ext.form.FormPanel({
    frame:true,
    baseCls:'x-plain',
    enctype:'multipart/form-data',
    autoHeight:true,
    labelWidth:40,
    width:500,
    fileUpload:true,
    bodyStyle:'padding: 10px 10px 0 10px;',
    defaults:{
      anchor:'95%',
      msgTarget:'side'
    },
    items:[{
      id:'fileSelectedM',
      xtype:'textfield',
      height:20,
      fieldLabel:'<%=LanguageService.getText(LangConstants.SYS_FILE)%>',
      name:'uploadM',
      inputType:'file',
      allowBlank:false,
      blankText:'<%=SysMsgHelper.getDPMessage(SysMsgConstants.SelectFile)%>',
      width:500,
      anchor:'95%'
    },{
      id:'descriptionM',
      width:500,
      allowBlank:true,
      xtype:'textarea',
      fieldLabel:'<%=LanguageService.getText(LangConstants.SYS_DESCRIPTION)%>'
    },{id:'modifyAddTime',
			width:200,
			allowBlank:false,
			xtype:'textfield',
			inputType:'text',
			emptyText:'yyyy-MM-dd HH:mm:ss',
			fieldLabel:'上传时间'
		}]
  });  

    uploadwinM = new Ext.Window({
      title: '<%=SysMsgHelper.getDPMessage(SysMsgConstants.UploadAttachement)%>',
      width: 500,
      height:220,
      layout: 'fit',
      iconCls:'window',
      plain:true,
      modal:true,
      bodyStyle:'padding:5px;',
      buttonAlign:'center',
      maximizable:false,
    closeAction:'close',
    closable:false,
    collapsible:false,
      items: formMainM,
      buttons: [
      {
          text: '<%=LanguageService.getText(LangConstants.SYS_UPLOAD)%>',
          handler:function()
          {
          var boId = '${businessId}' ;//Ext.getDom('${boIdField}').value;
          if(boId==""&&Ext.get('${boIdField}'))boId=Ext.get('${boIdField}').dom.value;
        var boName = '${boName}';
        var boProperty = '${boProperty}';
        var  result = '&boId='+boId +'&boName='+ boName +'&boProperty='+ boProperty;
        if(formMainM.form.isValid()){
        var modifyAddTime = Ext.getCmp('modifyAddTime').getValue();					    
	    if(!valiDateTimes(modifyAddTime)) {
	       Ext.Msg.alert('Error','上传时间格式错误');
	       return;
	    }
        
          formMainM.getForm().submit({
            method:'POST', 
            url:encodeURI('<%=Constants.FILE_LOCATION_URL%>/attachementController.spr?action=upload2Modify&userId=<%=Constants.ins().getCurrentUserId()%>'+result +'&description='+Ext.getCmp('descriptionM').getValue()+'&filepath='+Ext.getCmp('fileSelectedM').getValue()+'&modifyAddTime='+Ext.getCmp('modifyAddTime').getValue()),
            success:function(form,action){
                filepath = Ext.getCmp('fileSelectedM').getValue();
                setTimeout("fileUploadCheck()",1000);
                formMainM.form.reset();
                  uploadwinM.close();
                  ${boProperty}Attachement_grid.getStore().reload();
                  //_getMainFrame().showInfo(action.result.msg);              
            },
            waitMsg: '<%=SysMsgHelper.getDPMessage(SysMsgConstants.FileUploading)%>'
          }
        )}
          }  
      },
      {
          text: '<%=txtCancel%>',
          handler:function()
          {
            formMainM.form.reset();
             uploadwinM.close();
          }
      }]
  });
    
   uploadwinM.show();
}



/*
 * 上传业务替换
 */
function _upload${boProperty}Attachement_modifyReplace()
{
       if (${boProperty}Attachement_grid.selModel.hasSelection()){
		 var records = ${boProperty}Attachement_grid.selModel.getSelections();
		 if(records.length>1){
		   _getMainFrame().showInfo('只允许选择一条记录');
		   return;
		  }
		
        var attachementId = records[0].data.attachementId;
        
		  var uploadwinMR ;
		  var formMainMR ;
		  
		  formMainMR=new Ext.form.FormPanel({
		    frame:true,
		    baseCls:'x-plain',
		    enctype:'multipart/form-data',
		    autoHeight:true,
		    labelWidth:40,
		    width:500,
		    fileUpload:true,
		    bodyStyle:'padding: 10px 10px 0 10px;',
		    defaults:{
		      anchor:'95%',
		      msgTarget:'side'
		    },
		    items:[{
		      id:'fileSelectedMR',
		      xtype:'textfield',
		      height:20,
		      fieldLabel:'<%=LanguageService.getText(LangConstants.SYS_FILE)%>',
		      name:'uploadMR',
		      inputType:'file',
		      allowBlank:false,
		      blankText:'<%=SysMsgHelper.getDPMessage(SysMsgConstants.SelectFile)%>',
		      width:500,
		      anchor:'95%'
		    },{
		      id:'descriptionMR',
		      width:500,
		      allowBlank:true,
		      xtype:'textarea',
		      fieldLabel:'<%=LanguageService.getText(LangConstants.SYS_DESCRIPTION)%>'
		    }]
		  });  
		
		    uploadwinMR = new Ext.Window({
		      title: '<%=SysMsgHelper.getDPMessage(SysMsgConstants.UploadAttachement)%>',
		      width: 500,
		      height:180,
		      layout: 'fit',
		      iconCls:'window',
		      plain:true,
		      modal:true,
		      bodyStyle:'padding:5px;',
		      buttonAlign:'center',
		      maximizable:false,
		    closeAction:'close',
		    closable:false,
		    collapsible:false,
		      items: formMainMR,
		      buttons: [
		      {
		          text: '<%=LanguageService.getText(LangConstants.SYS_UPLOAD)%>',
		          handler:function()
		          {
		          var boId = '${businessId}' ;//Ext.getDom('${boIdField}').value;
		          if(boId==""&&Ext.get('${boIdField}'))boId=Ext.get('${boIdField}').dom.value;
		        var boName = '${boName}';
		        var boProperty = '${boProperty}';
		        var  result = '&boId='+boId +'&boName='+ boName +'&boProperty='+ boProperty;
		        if(formMainMR.form.isValid()){
		          formMainMR.getForm().submit({
		            method:'POST', 
		            url:encodeURI('<%=Constants.FILE_LOCATION_URL%>/attachementController.spr?action=upload2ModifyReplace&userId=<%=Constants.ins().getCurrentUserId()%>'+result +'&description='+Ext.getCmp('descriptionMR').getValue()+'&filepath='+Ext.getCmp('fileSelectedMR').getValue()+'&attachementId='+attachementId),
		            success:function(form,action){
		                filepath = Ext.getCmp('fileSelectedMR').getValue();
		                setTimeout("fileUploadCheck()",1000);
		                formMainMR.form.reset();
		                  uploadwinMR.close();
		                  ${boProperty}Attachement_grid.getStore().reload();
		                  //_getMainFrame().showInfo(action.result.msg);              
		            },
		            waitMsg: '<%=SysMsgHelper.getDPMessage(SysMsgConstants.FileUploading)%>'
		          }
		        )}
		          }  
		      },
		      {
		          text: '<%=txtCancel%>',
		          handler:function()
		          {
		            formMainMR.form.reset();
		             uploadwinMR.close();
		          }
		      }]
		  });
		    
		   uploadwinMR.show();

	}else{
		_getMainFrame().showInfo('<%=SysMsgHelper.getDPMessage(SysMsgConstants.SelectOneRecord)%>');
	}




}

<%}%>

</script>

<fisc:grid boName="Attachement" gridId="${boProperty}Attachement" divId="${divId}" height="${gridHeight}"  title="${gridTitle}" pageSize="${gridPageSize}"  needCheckBox="true" needAutoLoad="true" needToolbar="${needToolbar}" nameSapceSupport="true" defaultCondition=" YATTACHEMENT.BUSINESSID='${businessId}' AND YATTACHEMENT.BOPROPERTY='${boProperty}'" orderBySql="createtime desc"></fisc:grid>
<script type="text/javascript" defer="defer">
Ext.onReady(function(){
    if('true'!='<%=allowUpload%>'){
	  Ext.getDom('Attachement._upload').disabled=true;
	}
	if('true'!='<%=allowDelete%>'){
	  Ext.getDom('Attachement._deletes').disabled=true;
	}
	
	//Ext.getDom('Attachement._upload').disabled=true;
	//Ext.getDom('Attachement._deletes').disabled=true;
<%if(i==1){%>	
	try{
        var addBtn = document.createElement("input"); 
        addBtn.type = 'button';
        addBtn.onclick =  _upload${boProperty}Attachement_modify; 
        addBtn.value='补录上传';
        
        var editBtn = document.createElement("input"); 
        editBtn.type = 'button';
        editBtn.onclick =  _upload${boProperty}Attachement_modifyReplace; 
        editBtn.value='替 换';
                
        var delBtn = document.createElement("input"); 
        delBtn.type = 'button';
        delBtn.onclick =  _deletes${boProperty}Attachement_modify; 
        delBtn.value='删 除';
    }  
    catch(e){  
    }  
    
    ${boProperty}Attachement_grid.tbar.child('div').appendChild(addBtn);
    ${boProperty}Attachement_grid.tbar.child('div').appendChild(editBtn);
    ${boProperty}Attachement_grid.tbar.child('div').appendChild(delBtn);
<%}%>	
	attachementAttachement_grid.getColumnModel().setRenderer(5,function(value, p, record){
	    url =  encodeURI('<%=Constants.FILE_LOCATION_URL%>/extComponentServlet?type=fileDownload&fileName='+value);
	    return '<b><a href="'+url+'" target="_self">'+value+'</a></b>';
	});
	
	attachementAttachement_grid.getColumnModel().setRenderer(7,function(value, p, record){
	    //屏蔽创建人
	    return '';
	});

 });
 
</script>