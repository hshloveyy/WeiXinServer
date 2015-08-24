<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@page import="com.infolion.platform.dpframework.language.LangConstants"%>
<%@page import="com.infolion.platform.dpframework.language.LanguageService"%>
<%@page import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgHelper"%>
<%@page import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants"%>
<%

  //TCODE编码不能为空！
  String tcodeNotBlank =SysMsgHelper.getDPMessage(SysMsgConstants.TcodeNotBlank);
  //您打开的tab页不能超过10个，请先关闭一个tab页，谢谢！  
  String opentabs =SysMsgHelper.getDPMessage(SysMsgConstants.OPENTABS);
  //帮助
  String help = LanguageService.getText(LangConstants.INDEX_HELP);
  //首页
  String index = LanguageService.getText(LangConstants.INDEX);
  //关 于
  String about = LanguageService.getText(LangConstants.INDEX_ABOUT);
  //添加文件夹
  String addFile = LanguageService.getText(LangConstants.INDEX_ADDFILE);
  //添加事务
  String addTcode = LanguageService.getText(LangConstants.INDEX_ADDTCODE);
  //收藏
  String collection = LanguageService.getText(LangConstants.INDEX_COLLECTION);
  //新建文件夹
  String createFile = LanguageService.getText(LangConstants.INDEX_CREATEFILE);
  //新建事务
  String createTcode = LanguageService.getText(LangConstants.INDEX_CREATETCODE);
  //执行事务
  String execTcode = LanguageService.getText(LangConstants.INDEX_EXECTCODE);
  //收藏夹
  String favorite = LanguageService.getText(LangConstants.INDEX_FAVORITE);
  //加入收藏夹
  String addFavorite = LanguageService.getText(LangConstants.INDEX_ADDFAVORITE);
  //邮件系统
  String mail = LanguageService.getText(LangConstants.INDEX_MAIL);
  //修改标题
  String modiytitle = LanguageService.getText(LangConstants.INDEX_MODIYTITLE);
  //我的授权
  String myauth = LanguageService.getText(LangConstants.INDEX_MYAUTH);
  //个人
  String personal = LanguageService.getText(LangConstants.INDEX_PERSONAL);
  //个人设置
  String personalinf = LanguageService.getText(LangConstants.INDEX_PERSONALINF);
  //标准
  String standard = LanguageService.getText(LangConstants.INDEX_STANDARD);
  //事务搜索
  String tcode = LanguageService.getText(LangConstants.INDEX_TCODE);
  //工作台
  String workbench = LanguageService.getText(LangConstants.INDEX_WORKBENCH);
  //删除
  String delete = LanguageService.getText(LangConstants.INDEX_DELETE);
  //讯盟业务平台
  String xmdpTitle = LanguageService.getText(LangConstants.INDEX_XMDPTITLE);
  //退出
  String exit = LanguageService.getText(LangConstants.INDEX_EXIT);
  //日历
  String calendar = LanguageService.getText(LangConstants.INDEX_CALENDAR);
  //与服务器交互失败，请查看具体提示原因
  String mutualFail = SysMsgHelper.getDPMessage(SysMsgConstants.MUTUALFAIL);
  //操作失败
  String OperateFailure = SysMsgHelper.getDPMessage(SysMsgConstants.OperateFailure);
  //操作成功
  String OperateSuccess = SysMsgHelper.getDPMessage(SysMsgConstants.OperateSuccess);
  //不能删除,还有子节点！
  String canNotDeleteNode = SysMsgHelper.getDPMessage(SysMsgConstants.CanNotDeleteNode);;
  //确定要删除所选节点吗？
  String confirmDeleteNode = SysMsgHelper.getDPMessage(SysMsgConstants.ConfirmDeleteNode);;
  //不能删除树的根节点！
  String canNotDeleteRootNode = SysMsgHelper.getDPMessage(SysMsgConstants.CanNotDeleteRootNode);;
  //根目录节点不能修改！
  String canNotModifyRootNode = SysMsgHelper.getDPMessage(SysMsgConstants.CanNotModifyRootNode);;
  
  //个人菜单
  String personalMenu = LanguageService.getText(LangConstants.INDEX_PERSONALMENU);
  //系统菜单
  String systemMenu = LanguageService.getText(LangConstants.INDEX_SYSTEMMENU);
  //确认删除
  String confirmDelete = LanguageService.getText(LangConstants.INDEX_CONFIRMDELETE);
  //菜单目录
  String menuDirectory = LanguageService.getText(LangConstants.INDEX_MENUDIRECTORY);
  //刷新
  String refresh = LanguageService.getText(LangConstants.INDEX_REFRESH);
  //展开树
  String expandTree = LanguageService.getText(LangConstants.INDEX_EXPANDTREE);
  //收起树
  String collapseTree = LanguageService.getText(LangConstants.INDEX_COLLAPSETREE);
  //办理待办任务
  String waitTransact = LanguageService.getText(LangConstants.INDEX_WAITTRANSACT);

%>
<script language="javascript" src="<%= request.getContextPath() %>/js/lib/ExtModalWindowUtil.js"></script>
<script>
	var _winArray = {};
	var re_login_url = '${returnUrl}';
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=xmdpTitle%></title>

<style type="text/css">
html,body {
	font: normal 12px verdana;
	margin: 0;
	padding: 0;
	border: 0 none;
	overflow: hidden;
	height: 100%;
}

p {
	margin: 5px;
}

.tabs { 
	background-image: url(images/fam/tabs.gif);
}

.t_icon {background-image: url(images/ico1.gif);}   
.p_icon {background-image: url(images/ico2.gif);} 
.c_icon {background-image: url(images/ico3.gif);} 
	
</style>
</head>
<body>
<div id="tree_div" style="overflow: auto; width: 100%; height: 100%" class="x-hide-display"></div>
<div id="collectiontree_div" style="overflow: auto; width: 100%; height: 100%" class="x-hide-display"></div>
<div id="personaltree_div" style="overflow: auto; width: 100%; height: 100%" class="x-hide-display"></div>
<div id="ext-modal-dialog-win" class="x-hidden">
    <div class="x-window-header" id="ext-modal-dialog-win-header"></div>
    <div id="ext-modal-dialog-win-content"></div>
</div>

<div id="div_north">
	<ul>
		<li class="banner">           
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="logo">&nbsp;</td>
                <td class="topbg1">&nbsp;</td>
                <td>&nbsp;</td>
                <td class="topbg3">
                	<div class="help"><a href="#" style=" color:blue;font-size: 12px">&nbsp;&nbsp;&nbsp;&nbsp;<%=help%></a>       <a href="<%=request.getContextPath()%>/loginController.spr?action=loginOut" style=" color:blue;;font-size: 12px"><%=exit%></a></div>
				</td>
              </tr>
            </table>
        </li>
    	<li class="nav">
        	<span class="navbg">
             <div class="search1">
                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  	<td width="60" style="color:#fff; padding-left:15px;font-size: 12px"><%=tcode%>：</td>
                    <td>
                        <div class="border150">
                            <ul>
                                <li>
                                  <label>
                                    <input type="text" name="Tcode" id="Tcode"  class="searchinput" onblur="tcodeOnblur()" onkeypress="tcodeonkeypress();">
                                </label>
                                </li>
                                <li style="float:right;">
									<img src="<%= request.getContextPath() %>/images/search_but.gif" width="29" height="17"  
                                	onMouseOver="this.src='<%= request.getContextPath() %>/images/search_buthover.gif'" 
                					onMouseOut="this.src='<%= request.getContextPath() %>/images/search_but.gif'" onclick="btnexectcode();">
                				</li>
                            </ul>
                        </div>
                    </td>
                  </tr>
                </table>
             </div>
             <div class="menu">
             	<ul>
                	<!--  <li class="menu_lihover" style="margin-top:1px;"><a href="#" onclick="mainpage();" style="color:#000;"><%=calendar%></a></li>
                	<li><a href="#" onclick="mail();"><%=mail%></a></li>
                	<li><a href="#" onclick="workbench();"><%=workbench%></a></li>
                    <li><a href="#" onclick="myAuthResource();"><%=myauth%></a></li>
                    <li><a href="#" onclick="setPersonalInf();"><%=personalinf%></a></li>
                    <li><a href="#" onclick="about();"><%=about%></a></li>
                    <li></li>-->
                    
                    <li style="width: 220px;">
                    ${xdssUser.deptName} ${xdssUser.realName}${xdssUser.positionDes}，欢迎您！</li>
                    <!--li style="width: 90px;"><a href="#" onclick="modiUserInfo();">修改用户信息</a></li>  -->
                    <li style="width: 90px;"><a href="#" onclick="modiPass();">修改用户密码</a></li>
                    <li></li>
                </ul>
             </div>
           </span>
        </li>
  </ul>
</div>

<form action="" name="userform">
<input type="hidden" name="userId" name="userId" value="${xdssUser.userId}"></input>
<input type="hidden" name="deptUserId" name="deptUserId" value="${xdssUser.deptUserId}"></input>
</form>
<div id="windiv" name="windiv"></div>
</body>
</html>

<script type="text/javascript" defer="defer">
//全局路径
var context = '<%= request.getContextPath() %>';
//处理直接按按钮的动作
var operaction='close'; 
var treeTabs ;
var menus;
var personaltree; //个人菜单
var tree; //标准菜单
var collectiontree; //收藏菜单
//个人参数-启动菜单
var startMenu = '${startMenu}'
//个人参数-启动开始事务
var startTcode = '${startTcode}'
var tcodeWin ;


function tcodeOnblur()
{
	//document.all.btn.focus();
}

/**
 * 输入TCODE触发事件
 */
function tcodeonkeypress()
{
	if(event.keyCode==13){
	    exectcode($('Tcode').value);
	   }
}

/**
 * 执行TCODE
 */
function btnexectcode()
{
	exectcode($('Tcode').value);
}
/**
 * 执行TCODE
 */
function exectcode(tcode)
{
	if(!tcode)
	{
		Ext.Msg.show({
			title : sys.info,
			msg : '<%=tcodeNotBlank%>',
			buttons : {yes:sys.ok},
			icon : Ext.MessageBox.INFO
		});

		return ;
	}
	//执行TCODE所对应的资源菜单URL
	else
	{
		  Ext.Ajax.request({
				waitTitle:sys.info,
		  		method: 'POST',
				url: context + '/index/indexMainController.spr?action=executeTcode',
				params:'&tcode='+ tcode ,
				success: function(response, options){
				     var node = Ext.util.JSON.decode(response.responseText);
					 if(node.success==true){
						 if(node.isExist==true){
	                    	 addTab(node.text,node.id,node.hrefTarget,node.iconCls);
	                    	 $('Tcode').value = "";
						 }
						 else if(node.isLikeExist==true)
						 {
							var strTcode = encodeURI(encodeURI(tcode));
							//var url = '<iframe id="tcodeLst" name="tcodeLst" src="<%= request.getContextPath()%>/index/indexMainController.spr?action=showTcodes&strTcode='+ strTcode + '"   frameBorder="0" scrolling="no" width="100%" height="100%"></iframe>';
                            var url= "/index/indexMainController.spr?action=showTcodes&strTcode=" + strTcode ;
	                    	addTab('<%=execTcode%>','EXEC-TCODE-QUERY-LIST',url,'');
                            /*
							//_getMainFrame().ExtModalWindowUtil.show('执行事务',url2,'','',{width:550,height:380});
							var newTab = new Ext.ux.ManagedIframePanel({
								border:false,
								enableTabScroll:false,
								layout:'fit',
								closable:false,
							    autoScroll: false,
								defaultSrc : url
							});
							
							 tcodeWin = new Ext.Window({
								    title:'执行事务',
								    width: 565,
								    height:369,
								    layout: 'fit',
								    iconCls:'icon-window',
								    //plain:true,
								    modal:true,
								    bodyStyle:'padding:0 0 0 0;',
								    buttonAlign:'center',
								    maximizable:false,
									closable:true,
									autoScroll:false,
									collapsible:false,
									items:[newTab],
								    buttons: [
								    {
								        text: '取消',
								        iconCls:'but_two',
								        handler:function(){
								    		tcodeWin.close();
								    		tcodeWin=null ;
								    		$('Tcode').value = "";
								         }
								    }]

								});	

							//tcodeWin.html=url;	
							tcodeWin.show();
							tcodeWin.center();
							*/	
							$('Tcode').value = "";
						 }
						 else{
						      $('Tcode').value = "";
						      showInfo(node.msg);
						 }
					 }
					 else
				     {
							Ext.Msg.show({
								title : "<%=OperateFailure%>",
								msg : node.msg,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.ERROR
							});
					 }
				},
				faliue: function(response, options){
					showError('<%=mutualFail%>');
				}

			});
	}
	
}

/**
 * 首页
 */
function mainpage()
{
	_commonMethodOperation('1','<%=calendar%>','','/BDP/calendar/calendarController.spr?action=_view');
}

function workbench(){
	_commonMethodOperation('1','<%=workbench%>','','/basicApp/workbench/workbenchController.spr?action=_manage');
}

function mail(){
	_getMainFrame().maintabs.addPanel('<%=mail%>','','http://mail.ffcs.cn/');
}

/**
 * 关于
 */
function about()
{
	var aboutWin = new Ext.Window({
        title: '<%=about%>',
        width: 350,
        height:230,
        layout: 'fit',
        iconCls:'icon-window',
        closeAction:'close', 
        modal:true,
        maximizable:false,  //允许最大化
        resizable: false,   //不可调整大小
        html:'<iframe id="about" name="about" src="' + context + '/indexMainController.spr?action=about" frameBorder="0" scrolling="auto" width="100%" height="100%"></iframe>'
    });
    
	aboutWin.show();
	aboutWin.center();	
}

/**
* 个人参数设置
*/
function setPersonalInf()
{
   _commonMethodOperation('1','<%=personalinf%>','','/indexMainController.spr?action=setPersonalInf');
}

/**
 * 我的授权
 */
function myAuthResource()
{
   _commonMethodOperation('1','<%=myauth%>','','/indexMainController.spr?action=myAuthResource');
}

//修改员工信息
function modiUserInfo(){
	ExtModalWindowUtil.show('修改员工信息',
	'orgController.spr?action=modiUserInfoView',
	'',
	'',
	{width:450,height:180});
}

//修改员工密码
function modiPass(){
	ExtModalWindowUtil.show('修改登录员工密码',
	'orgController.spr?action=modiPasswordView',
	'',
	'',
	{width:300,height:160});
}



/**
 * 关闭标签页事件
 */
function closeTable(tabPanel){
	tabPanel.src = "about:blank";
}

/**
 * 设置树的点击事件
 */
function treeClick(node,e) 
{
	if (node.leaf == true)
		addTab(node.text,node.id,node.attributes.hrefTarget,node.attributes.iconCls);
}

/**
 * 生成标签页
 */

	        
var maintabs = new Ext.DpTabPanel({
    region:'center',
    activeItem:0,
    enableTabScroll:true,
    deferredRender:true,
    style:'padding:0 1px 5px 0px',   
    items:{
    	 title:'<%=index%>',
    	 id:'index',
    	 xtype:'dpiframepanel',
    	 closable:false,
    	 defaultSrc: context + '/workflowController.spr?action=workItemManageView'
        	 },
    //defaults: {autoScroll:true,height:400},
    plugins: new Ext.ux.TabCloseMenu()
});

/**
 * 添加新页标签
 */
function addTab(labeltitle,labelid,labelurl,iconCls)
{   
    var completeUrl="";
    var url = "";
    var icon ;
    if(iconCls=='' ||iconCls==' ' || iconCls=='undefined'){
        icon = "tabs";
    }else{
        icon = iconCls
    }
    var findTab = maintabs.getItem(labelid);
    var isTcodeTab = false;
    var tCodeTaab = maintabs.getItem('EXEC-TCODE-QUERY-LIST');
    if(tCodeTaab != null){
        isTcodeTab =true ;
    }
    if (findTab != null && isTcodeTab==false){
        maintabs.setActiveTab(findTab);
        var currentTab = maintabs.activeTab;
        currentTab.src = labelurl;
    }else{
        if (maintabs.items.length + 1 > 20){
            showInfo('<%=opentabs%>');
        }else{
            if(labelurl.indexOf('?')==-1){
                completeUrl = labelurl + '?menuid=' + labelid;
            }else{
                completeUrl = labelurl + '&menuid=' + labelid;
            }
            if(completeUrl.indexOf('http:')==-1){
                url = context + '/' + completeUrl;
            }else{
                url = completeUrl;
            }
            // 若为报表资源管理菜单，则作特殊处理
            if(completeUrl.indexOf('xindeco')!=-1){
            	Ext.Ajax.request({
	    			url : contextPath+'/orgController.spr?action=getAllDeptCodeByUserName',
	        		params : {},
	        		success : function(xhr){
	        			if(Utils.isEmpty(xhr.responseText)){
	        				_getMainFrame().showInfo('对不起，您没有权限访问报表数据！');
	        				return;
	        			}
	        			url += '&lsMdept=' + xhr.responseText;
	        			window.open(encodeURI(url), "newwindow", "height=" + (screen.availHeight-40) + ", width=" +screen.availWidth + ", top=0,left=0 toolbar =no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
	        			//win.moveTo(0,0);   
        				//win.resizeTo(screen.availWidth,screen.availHeight);
	        		},
	        		scope : this
	    		});
            }else{
	            var newTab = new Ext.DpIframePanel({
	                title: labeltitle,
	                border:false,
	                id:labelid,
	                enableTabScroll:true,
	                iconCls: icon,
	                layout:'fit',
	                closable:true,
	                height:400,
	                autoScroll: true,
	                defaultSrc : url
	            });
	            newTab.on("beforeclose",closeTable);
	            maintabs.add(newTab).show();
            }
        }
    }
}

/**
 * 注销
 */
function loginOut(){
	window.location.href='loginController.spr?action=loginOut';
	//window.location.href=context+'/authManage/userLoginController.spr?action=loginOut';
};

Ext.onReady(function(){	
//	Ext.Ajax.on('beforerequest', showSpinner, this);
//	Ext.Ajax.on('requestcomplete', hiddenSpinner, this);
//  Ext.Ajax.on('requestexception', hiddenSpinner, this);

	function showSpinner()
	{
		Ext.MessageBox.show({
	           msg: '正在执行中,请稍候...',
	           progressText: '数据载入中...',
	           width:300,
	           wait:true,
	           waitConfig: {interval:200}
	       });
	}
	
	function hiddenSpinner()
	{
		Ext.MessageBox.hide();
	}
	
    treeTabs = new Ext.TabPanel({
   	    border:false,
   	    activeItem:0,
   	 	autoScroll: true,
   	    style:'padding:0 0 0 0',
   	    enableTabScroll:true,
   	    animScroll:true,
   	    autoTabs:false,
   	    deferredRender:true,
   	    tabPosition:'top',
   	    items:[
  	          {
		            title:'<%=personal%>',
		            contentEl: 'personaltree_div',
		            id:'personaltree',
		            border:false,
		            layout:'fit',
		            iconCls:'p_icon'
		            	
	          }]        
   	   });
    
    // 个人菜单树
    var personalsystemTreeLoader = new Ext.tree.TreeLoader({
    	url:context+'/loginController.spr?action=getSystemPersonalMenu'
    });   
      
    personalsystemTreeLoader.on("beforeload", function(treeLoader, node) {   
                //systemTreeLoader.baseParams.id = node.attributes.id; 
                //alert("iconCls" + node.attributes.iconCls + "iconCls" + node.attributes.iconCls);
                personalsystemTreeLoader.baseParams = {'id': node.attributes.id,'menuId': node.attributes.menuId,'iconCls': node.attributes.iconCls};
            }, personalsystemTreeLoader);
    
    personaltree = new Ext.tree.TreePanel({
    	id:'personal_tree',
        el:'personaltree_div',
        useArrows:true,
        //lines: false,
        autoScroll:false,
        collapsed :false,
        rootVisible:false,
        enableDD:false,
        ddScroll:true,
        containerScroll: true,
        border:false,
        autoHeight:true,
        animate:true, 
        loader: personalsystemTreeLoader
    }); 
    
    var personalroot = new Ext.tree.AsyncTreeNode({
    	id:'-1',
        text: '<%=personalMenu%>',
        expandable:true,
        leaf:false ,
        draggable:false,
        iconCls:'x-tree-node.tree_iconCls',
        singleClickExpand:true 
    });

    personaltree.setRootNode(personalroot); 
    
    personaltree.on('click',treeClick);
    personaltree.expand();
    personaltree.render(); 
    personaltree.doLayout(); 
    
    var tab ;
    var treeTemp ;
    //页面整体布局
 	var vp = new Ext.Viewport({
		layout:"border",
		items:[
		{
			region:"north",
			height:100,
			id:'north-panel',
			contentEl:'div_north'
		},
		{
			region:"west",
			title:"<%=menuDirectory%>",
			id:'west-panel',
			split:true,
            width: 200,
            minSize: 175,
            maxSize: 400,
            collapsible: true,
            collapseMode:'mini',
            margins:'0 0 5 0',
            layout:'fit',
   		   	tools:[{
				id:'refresh',   
	            qtip: '<%=refresh%>',
	            handler: function(event, toolEl, panel) {
	                tab = treeTabs.getActiveTab();
	                if(tab.id=='tabtree')
	                {
	                  treeTemp = Ext.getCmp('main_tree');
	                  treeTemp.doLayout(); 
	                }
	                else if(tab.id=='collectiontree')
	                {
	                  treeTemp = Ext.getCmp('collection_tree'); 
	                }
	                else if(tab.id=='personaltree')
	                {
	                  treeTemp = Ext.getCmp('personal_tree'); 
	                }
	                
                	treeTemp.body.mask('Loading', 'x-mask-loading');
                	treeTemp.root.reload();
                	treeTemp.root.collapse(true, false);
		             setTimeout(function(){
		            	 treeTemp.body.unmask();
		             }, 1000); 
		          	
	          }
			},{
				id:'maximize',
				qtip:'<%=expandTree%>',
				handler:function(event, toolEl, panel) {
	                tab = treeTabs.getActiveTab();
	                //alert(tab.id);
	                if(tab.id=='tabtree')
	                {
	                	treeTemp = Ext.getCmp('main_tree');
	                }
	                else if(tab.id=='collectiontree')
	                {
	                	treeTemp = Ext.getCmp('collection_tree'); 
	                }
	                else if(tab.id=='personaltree')
	                {
	                	treeTemp = Ext.getCmp('personal_tree'); 
	                }
	                
	                treeTemp.expandAll();
				}
			},{
				id:'minimize',
				qtip:'<%=collapseTree%>',
				handler:function(event, toolEl, panel) {
	                tab = treeTabs.getActiveTab();
	                //alert(tab.id);
	                if(tab.id=='tabtree')
	                {
	                	treeTemp = Ext.getCmp('main_tree');
	                }
	                else if(tab.id=='collectiontree')
	                {
	                	treeTemp = Ext.getCmp('collection_tree'); 
	                }
	                else if(tab.id=='personaltree')
	                {
	                	treeTemp = Ext.getCmp('personal_tree'); 
	                }
	                treeTemp.collapseAll();
				}
			}], 
            layoutConfig:{animate:true},
            items:treeTabs
		},
			maintabs
		]
    });
	   
    vp.doLayout();
    //vp.show();
    
    maintabs.on('dblclick',function(){
		var w = Ext.getCmp('west-panel');
	    w.collapsed ? w.expand() : w.collapse();
	    var w1 = Ext.getCmp('north-panel');
	    w1.collapsed ? w1.expand() : w1.collapse();
    })

    //设置启动菜单
    //1 、收藏   2、个人  3、标准
    if(startMenu!="" && startMenu!=null)
    {
        if(startMenu=="1") //
        {
        	//treeTabs.setActiveTab(0);
        }
        else if(startMenu=="2")
        {
        	treeTabs.setActiveTab(0);
        }
        else if(startMenu=="3")
        {
        	//treeTabs.setActiveTab(2);
        }
    }
    
    //启动开始事务
    if(startTcode!="" && startTcode!= null)
    {
    	exectcode(startTcode);
    }
	if(re_login_url!="" && re_login_url!= null)
    {
    	maintabs.addPanel('<%=waitTransact%>',null,re_login_url);
    }
});

//top.ExtModalWindowUtil.show('','notice1.jsp','','',{modal:false,height:535,width:800});
//top.ExtModalWindowUtil.show('','notice.jsp','','',{modal:false,height:500,width:700});
//window.open('notice.jsp','','height=500,width=700');
</script>