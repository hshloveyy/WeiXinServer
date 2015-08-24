<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/infolion" prefix="fisc"%>
<html>
<head>
  <title>index</title>
  <%@ include file="../../js/ext/ext.jsp"%>
  <%@ include file="../../js/extex/extex.jsp"%>
</head>
<body>
    <div id="tabs" style="margin:15px 0;"></div>
</body>
</html>
<script type="text/javascript">
var tabspanel;
Ext.onReady(function(){

    tabspanel = new Ext.DpTabPanel({
        renderTo:'tabs',
        resizeTabs:true, // turn on tab resizing
        minTabWidth: 115,
        tabWidth:135,
        tabHeight:50,
        enableTabScroll:true,
        width:600,
        height:250,
        defaults: {autoScroll:true},
        plugins: new Ext.ux.TabCloseMenu()
    });
	tabspanel.on('dblclick', function(){alert("adc");});
    // tab generation code
	addTab('0');
    function addTab(id){
        var p = tabspanel.add(new Ext.DpIframePanel({
    		id:id,
    		title: 'TEST',
    		iconCls: 'icon-cls',
            defaultSrc : '/xmdp/component/grid/index.jsp'
        }));
        tabspanel.setActiveTab(p);    
    }
    new Ext.Button({
        text: 'Add Tab',
        handler: gridreload
    }).render(document.body, 'tabs');
    
    function gridreload(){
        var tab = tabspanel.add(new Ext.DpIframePanel({
    		title: 'ADD',
    		iconCls: 'icon-cls',
    		parentTab: tabspanel.getActiveTab(),
            defaultSrc : '/xmdp/component/grid/index.jsp'
        }));
        tabspanel.setActiveTab(tab);      
    	//tabspanel.getActiveTab().findByType('editorgrid')[0].reload();
    	//tabspanel.getItem('0') 
    }
});
</script>