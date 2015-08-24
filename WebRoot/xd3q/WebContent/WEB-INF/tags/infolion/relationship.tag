<%-- 
  - Author(s):黄登辉
  - Date: 2008-12-25
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:协作组件控件
  -
--%><%@ tag pageEncoding="utf-8"%><%@
 tag import="org.apache.commons.lang.StringUtils"%><%@
 attribute name="divId" required="true" description="需要渲染的div的id"%><%@
 attribute name="boName" required="true" description="业务对象名"%><%@ 
 attribute name="height" required="false"  description="高度"%><%@ 
 attribute name="width" required="false"  description="宽度"%><%@ 
 attribute name="title" required="false"  description="标题"%><% 
 jspContext.setAttribute("contextPath", request.getContextPath());%>

<%@tag import="com.infolion.platform.dpframework.language.LanguageService"%>
<%@tag import="com.infolion.platform.dpframework.language.LangConstants"%>
<%@tag import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgHelper"%>
<%@tag import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants"%>
<script type="text/javascript" defer="defer">
Ext.onReady(function(){
	var ${divId}_relationship = new Ext.Panel({
		<%=StringUtils.isBlank(title)?"":"title:'"+title+"',"%>//标题
		collapsible:false,//右上角上的那个收缩按钮，设为false则不显示
		renderTo: '${divId}',//这个panel显示在html中id为${divId}的层中
		id:'relationship',
		<%=StringUtils.isBlank(width)?"autoWidth:true":"width:"+width%>,
		<%=StringUtils.isBlank(height)?"autoHeight:true":"height:"+height%>,
		frame:true,
		autoScroll:true,
		//autoLoad:{url:'${contextPath}/relationshipController.spr?action=getRelationshipList&boName=${boName}', nocache: true, scripts:true},
		html: "<p></p>"//panel主体中的内容，可以执行html代码
	});
	${divId}_relationship.render();
	${divId}_relationship.load({
		url:"${contextPath}/relationshipController.spr?action=getRelationshipList&boName=${boName}",
	    discardUrl: false,
	    nocache: false,
	    text: "Loading...",
	    timeout: 30,
	    scripts: false
	});
})

/**
 * 根据传入的boUrl及grid组合提交到后台的链接
 * @param {Object} boUrl
 * @param {Object} gridName
 */
function getUrl(boUrl,boName,gridData){
	//匹配用正则表达式,匹配&m=n格式
	//其中m为关联bo对象的参数，n为当前bo对象与之对应的属性
	var r = new RegExp("&([\\-\\.\\w]+)=([\\-\\.\\w]+)", "g");
	var data = '';
	var tag = '&';
	//解析URL并根据匹配的属性取属性值，RegExp.$1得到的是m，RegExp.$2得到的是n
	if(gridData==null||gridData==''){
		for(var a = r.exec(boUrl);a!=null;a = r.exec(boUrl)){
			//取当前bo的属性值并转换成json串，格式为 m:value
			//alert('A:' + boName+'.'+RegExp.$2);
			//alert('B:' + RegExp.$1);
			//alert($(boName+'.'+RegExp.$1).value);
			//data += tag+RegExp.$1+'='+Ext.getCmp(boName+'.'+RegExp.$2).value;
			//LJX 20090905 Modify
			data += tag+RegExp.$2+'='+$(boName+'.'+RegExp.$1).value;
			//data += tag+RegExp.$1+'='+$(boName+'.'+RegExp.$2).value;
		}
	}else{
		//alert(Ext.encode(gridData));
		for(var a = r.exec(boUrl);a!=null;a = r.exec(boUrl)){
			//取当前grid的属性值并转换成json串，格式为 m:value
			data += tag+RegExp.$2+'='+gridData[RegExp.$1];
			//data += tag+RegExp.$1+'='+gridData[RegExp.$2];
		}		
	}
	var url = new String(boUrl);
	url = url.substr(0,url.indexOf('||||'));
	url += data;
	return url;
}
 
function getBoRelationShip(boUrl,boName,uiType,title){
	var url = getUrl(boUrl,boName,null);
	_commonMethodOperation(uiType,title,'',url);
	/*var param = "action=boRedirect&boUrl=" + boUrl;
	param += "&param=" + data;
	//alert(param);
	new AjaxEngine('${contextPath}/relationshipController.spr', {
		method: "post",
		parameters: param,
		onComplete: function(){
			;
		}
	});*/
}

/**
 * 根据传入的boUrl及grid组合提交到后台的链接
 * @param {Object} boUrl
 * @param {Object} gridName
 */
function getBoRelationShipFromGrid(boUrl,boName,uiType,title){
	var mygrid = Ext.getCmp(boName);
	var rec = mygrid.getSelectionModel().getSelected();
	if (rec != undefined) {
		url = getUrl(boUrl,boName,rec.data);
		_commonMethodOperation(uiType,title,'',url);
	}else{
		Ext.Msg.show({
			title:'<%=LanguageService.getText(LangConstants.SYS_CUE)%>',
			msg: '<%=SysMsgHelper.getDPMessage(SysMsgConstants.SelectOneRecord1,StringUtils.isBlank(title)?"":"["+title+"]")%>',
			buttons: Ext.Msg.OK,
			animEl: 'elId',
			icon: Ext.MessageBox.WARNING
		});
	}
}
</script>