<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="com.infolion.platform.dpframework.language.LangConstants"%>
<%@page import="com.infolion.platform.dpframework.language.LanguageService"%>
<%@page import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants"%>
<%@page import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgHelper"%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<!-- meta http-equiv="X-UA-Compatible" content="IE=8"> -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib tagdir="/WEB-INF/tags/infolion" prefix="fisc"%>
<%@taglib tagdir="/WEB-INF/tags/infolionXd" prefix="fiscxd"%>
<%
//系统错误
String sysError=LanguageService.getText(LangConstants.SYS_ERROR);
//系统提示
String sysInfo=LanguageService.getText(LangConstants.SYS_INFO);
//系统警告
String sysWarn=LanguageService.getText(LangConstants.SYS_WARN);
//请稍侯...
String waitMsg=LanguageService.getText(LangConstants.SYS_WAITMSG);
//正在处理您的请求，请稍侯...'
String progressText=LanguageService.getText(LangConstants.SYS_PROGRESSTEXT);
//确定
String okText=LanguageService.getText(LangConstants.SYS_OK);
//取消
String cancelText=LanguageService.getText(LangConstants.SYS_CANCEL);
//操作信息提示
String operMsg= "";
%>
<link rel="stylesheet" type="text/css"	href='<%= request.getContextPath() %>/css/public.css'>
<link rel="stylesheet" type="text/css"	href='<%= request.getContextPath() %>/css/layout.css'>
<link rel="stylesheet" type="text/css"	href='<%= request.getContextPath() %>/css/runInfoStyle.css'>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/js/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/js/extex/extex.css"/>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/js/extex/ColumnTree/Ext.tree.ColumnTree.css"/>
<link rel="stylesheet" type="text/css"	href='<%= request.getContextPath() %>/css/year.css'>
<!-- common js -->
<script language="javascript" src="<%= request.getContextPath() %>/js/lib/prototype.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/lib/Utils.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/lib/AjaxEngine.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/lib/AjaxResponseUtils.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/lib/RunInfoUtil.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/lib/MessageBoxUtil.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/lib/MessageDialogue.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/lib/DateUtils.js"></script>


<script language="javascript" src="<%= request.getContextPath() %>/js/ext/adapter/ext/ext-base.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/ext/ext-all.js"></script>

<script language="javascript" src="<%= request.getContextPath() %>/js/extex/DateTime/dateTimeField2.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/extex/ux/Ext.ux.SearchHelpField.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/extex/ux/Ext.ux.SearchHelpWindow.js"></script> 
<script language="javascript" src="<%= request.getContextPath() %>/js/extex/ux/Ext.ux.ManagedIFramePanel-min.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/extex/ux/Ext.ux.DpTabPanel.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/extex/ux/Ext.ux.DpComboBox.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/extex/ux/Ext.ux.PrintButton.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/extex/ux/Ext.ux.GridToExcel.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/extex/ux/Ext.ux.GridToExcelWithActiveX.js"></script>
<script language="javascript" src='<%= request.getContextPath() %>/js/extex/ux/Ext.ux.GridPanelEx.js'></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/extex/ColumnTree/Ext.tree.ColumnTree.js"></script>

<script language="javascript" src="<%= request.getContextPath() %>/js/extex/portal/Portal.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/extex/portal/PortalColumn.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/extex/MultiSelectField/MultiSelectField.js"></script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/js/extex/portal/portal.css"/>
<script language="javascript" src="<%= request.getContextPath() %>/js/lib/year.js"></script>

<!-- ext bug fix -->
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/js/ext/ext-bug-fix.css"/>
<script src="<%= request.getContextPath() %>/js/ext/ext-bug-fix.js" type="text/javascript"></script>
<%//动态加载EXT语言包 %>
<%=LanguageService.getExtLanguagePackage(request)%>

<!--  
<script src="<%= request.getContextPath() %>/js/ext/ext-lang-zh_CN.js" type="text/javascript"></script>
-->
<script language="javascript">
	if(Ext.isGecko||Ext.isChrome){ //firefox chrome innerText define
		HTMLElement.prototype.__defineGetter__( "innerText", 
		 	function(){ 
		 		return this.textContent; 
		 	} 
	 	); 
	 	HTMLElement.prototype.__defineSetter__( "innerText", 
		 	function(sText){ 
		 	this.textContent=sText; 
		 	} 
	 	); 
	}
	//应用的上下文路径，作为全局变量供js使用
	var contextPath = '<%= request.getContextPath() %>';
	var runInfoImgSrc = '<%= request.getContextPath() %>/images/info.gif';	

	//JS下公用的变量。
	var sys={
			  error:'<%=sysError%>',
			  warn:'<%=sysWarn%>',
			  info:'<%=sysInfo%>',
			  waitMsg:'<%=waitMsg%>',
			  progressText:'<%=progressText%>',
			  btnOk :{yes:'<%=okText%>'},
			  ok:'<%=okText%>'
			};

	//必须初始化,才能显示设置的tooltip
	Ext.QuickTips.init();
	// initialize state manager, we will use cookies
	//暂时屏蔽(打开会保持界面状态) 表格的列名互相重复导致问题
	//Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	
	Ext.form.Field.prototype.msgTarget = 'side';
	//指定一个 1x1 大小的 gif 透明图片的地址，让 CSS 用来做为创建的内联图标的背景。（默认为“http://extjs.com/s.gif”）
	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/js/ext/resources/images/default/s.gif';
	
	//页面的运行信息处理对象
	var runInfoHandle = new RunInfoUtil("runInfoDiv", runInfoImgSrc);
	//ajax提交后的回调动作
	//根据不同的提示类型展示不同的样式
	function callBackHandle(transport,callBackHandle){
		if(transport.responseText.indexOf("{")==0){
			var responseUtil = new AjaxResponseUtils(transport.responseText);
			if(responseUtil.getRoot()!=null){
				var msgType = responseUtil.getMessageType();
				var msg = responseUtil.getMessage();
				var msgDiv = document.all("msgDiv");
				var promptMessagebox = new MessageBoxUtil();
				promptMessagebox.Close();
				//提示
				if(msgType=="info"){
					showInfo(msg);
				}
				//提示并移动焦点
				else if(msgType=="info_focus"){
					var fieldName = responseUtil.getMessageField();
					//runInfoHandle.show(msg);
					promptMessagebox.show('info',msg);
					document.all(fieldName).focus();
				}
				//提示DIV
				else if(msgType=="info_div_msg"){
					var fieldName = responseUtil.getMessageField();
					runInfoHandle.show(msg);
				}
				//警告
				else if(msgType=="warn"){		
					showWarn(msg);
					return;
				}
				//错误
				else if(msgType=="error"){
					showError(msg);
					return;
				}
				//不提示信息
				else if(msgType=="info_hidden"){
				}
				// 关闭当前页
				else if(msgType=="close"){
					_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
				}
			}
		}
		try{
			//用户自定义回调
			if (callBackHandle) callBackHandle(transport);
			else customCallBackHandle(transport);
		}catch(ex){}
		
	}	

	/**
	* 通用方法操作函数，根据方法ui类型，决定方法调用的展现形式
	*/
	function _commonMethodOperation(uiType,title,grid,url,callBack,methodName,isSubBo)
	{
		var fun ;
		try
		{
			fun = eval(callBack)	
		}
		catch(e){}
		url = '<%= request.getContextPath() %>/'+url;
		//页签
		if(uiType=='1'){
			if(!fun)
			{
				_getMainFrame().maintabs.addPanel(title,grid,url);
			}
			else
			{
				if(methodName && (methodName=='_edit' || methodName=='_view'))
				{
					if(isSubBo=='true')
					{
						var records = grid.getSelectionModel().getSelections(); 
						if(records.length>=1){    
							url = url +'&'+ commonUrlEncode(records[0].data);
						}
					}
				}
				//alert(url);
				_getMainFrame().maintabs.addPanel(title,grid,url,fun,true);
			}
		}
		//弹出窗口
		else if(uiType=='2'){
			if(!fun)
			{
				_getMainFrame().ExtModalWindowUtil.show(title,
						url,'','',{width:660,height:300});
			}
			else
			{
				if(methodName && (methodName=='_edit' || methodName=='_view'))
				{
					if(isSubBo=='true')
					{
						var records = grid.getSelectionModel().getSelections(); 
						if(records.length>=1){    
							url = url +'&'+  commonUrlEncode(records[0].data);
						}
					}
				}
				_getMainFrame().ExtModalWindowUtil.show(title,
						url,'',fun,{width:680,height:360});
			}

		}
		//服务
		else if(uiType=='3'){
			_getMainFrame().Ext.MessageBox.show({
				title:sys.info,
				msg: title,
				buttons: {yes:'<%=okText%>', no:'<%=cancelText%>'},
				icon: Ext.MessageBox.QUESTION,
				fn:function callService(result){
					if (result == 'yes'){
						new AjaxEngine(url,{method:"post", parameters: [], onComplete: callBackHandle});
					}
				}
			});
	
			
		}
	}

	function _getMainFrame(obj, index){
		if(!obj)obj=window;
		if(!index)index=0;
		if(index == 10)return window;
		
		if (obj.frames.ExtModalWindowUtil){
			return obj.frames;
		}else {
			if (typeof(obj.parent.frames[0])=='object') {
				index++;
				return _getMainFrame(obj.parent,index);
			}else{
				return window
			}
		}
	}
	
	function _getChildFrame(frameId){
		return Ext.getCmp(frameId).getFrameWindow().frames;
	}
	
	//{11:[],22:[]}
	function _pushCmps(obj){
		if(!_getMainFrame()._winArray)return;
		var tabId = _getMainFrame().maintabs.getActiveTab().getId();
		var els = _getMainFrame()._winArray;
		
		if (!els[tabId]) els[tabId] = [obj];
		else els[tabId].push(obj);
		_getMainFrame()._winArray = els;
	}
	
	function _clearCmps(tabId){
		var els = _getMainFrame()._winArray;
		if(!els[tabId])return;
		for(var i=els[tabId].length-1;i>=0;i--){
			var obj = els[tabId][i];
            try{
				if(obj && typeof obj == 'object'){
					Ext.destroy(obj);
				}else if(typeof((_getMainFrame().Ext).getCmp(obj)) == 'object'){
					Ext.destroy((_getMainFrame().Ext).getCmp(obj));
				}
            }
            catch(e){
            }
		}
		els[tabId] = [];
		_getMainFrame()._winArray = els;
	}
	
	//自己创建的iframe需要使用iframe内存释放函数，在使用到ifame的页面引入<body onunload="cleanIframe(iframeName)">
	function _cleanIframe(iframeName){
	   	var contentIframe = window.frames[iframeName];
		if(typeof(contentIframe) == 'object'){
	    	contentIframe.document.write(""); 
	    	contentIframe.document.clear(); 
	    	document.body.removeChild(contentIframe); 
	    	CollectGarbage();
	    	contentIframe.src = 'javascript:false';
		}
	}

	//查看流程状态公共方法
	function _viewProcessState(businessRecordId){
		var url = "<%=request.getContextPath()%>/platform/workflow/manager/processDefinitionController.spr?action=_showProcessTaskUseBusinessRecordId&businessRecordId="+businessRecordId;
		_getMainFrame().maintabs.addPanel('查看流程状态',null,url);	
	}

	function round(v,e) { 
		var t=1; 
		for(;e>0;t*=10,e--); 
		for(;e<0;t/=10,e++); 
		return Math.round(v*t)/t; 
	}
	
	/*
	//屏蔽退格删除键是 8 屏蔽 F4 键 屏蔽 F5 键  屏蔽 F1 键 屏蔽 Ctrl + R
	Ext.onReady(function(){
		document.body.oncontextmenu = new Function("return false;");
	    document.body.ondragstart=new Function("return false;");
	    document.body.onselectstart =new Function("return false;");
	    document.body.onselect="document.selection.empty()";
	    document.body.oncopy="document.selection.empty()";
	    document.body.onbeforecopy=new Function("return false;");
	    document.body.onmouseup="document.selection.empty()";
	    
		document.body.onkeydown = new Function("if((event.keyCode==115)||(event.keyCode==116)||(event.keyCode==112)||(event.ctrlKey && event.keyCode==82)){event.keyCode=0;event.returnValue=false;}");
	});
	*/
</script>