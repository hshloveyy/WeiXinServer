<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年04月21日 15点06分41秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>贸易监控管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ page import="com.infolion.xdss3.tradeMonitoring.domain.XdssBusinessType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>贸易监控</title>
</head>
<body>
<fisc:dictionary hiddenName="queryField" width="130" dictionaryName="YDTRADEQUERYFIELD" divId="div_QueryFieldDict" isNeedAuth="false"></fisc:dictionary>
<fisc:searchHelp divId="div_dept_sh" boName="" searchType="field"  searchHelpName="YHORGANIZATION" boProperty="" hiddenName="dept_id" displayField="ORGANIZATIONNAME" valueField="ORGANIZATIONID"></fisc:searchHelp>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
   <tr>
		<td align="left"><nobr>查询字段：</nobr></td>
		<td>
			<div id="div_QueryFieldDict"></div>
		</td>
		<td align="left">
		<input type="text" id="queryValue" size="12" name="queryValue" onkeypress="queryOnkeypress();" value="${queryValue}"></input>
		</td>
		<td id="td1" align="left" style="visibility: hidden;" >
        <lable id="lbl1" >申报部门:<lable/>
		</td>
        <td id="td2" align="left" style="visibility: hidden;" >
            <div id="div_dept_sh"></div>
        </td>
        <td id="td3" align="left" style="visibility: hidden;" >
        <lable id="lbl2" >申报时间:<lable/>
        </td>
        <td width="40%" id="td4" align="left" style="visibility: hidden;" >
         <table><tr><td>
        <div id="div_apply_time"></div>
        <fisc:calendar applyTo="" width="90" showTime="false" format="Y-m-d" divId="div_apply_time" fieldName="apply_time"></fisc:calendar>
         </td>
         <td>-</td>
         <td>
        <div id="div_apply_time1"></div>
        <fisc:calendar applyTo="" width="90" showTime="false" format="Y-m-d" divId="div_apply_time1" fieldName="apply_time1"></fisc:calendar>
        </td></tr></table>
        </td>
	</tr>
	<tr>
		<td colspan="8"><table><tr><td>合同组创建时间：</td><td>
		<div id="div_cgroup_time"></div>
        <fisc:calendar applyTo="" width="90" showTime="false" format="Y-m-d" divId="div_cgroup_time" fieldName="cGroupTime"></fisc:calendar>
        </td><td>-</td><td>
        <div id="div_cgroup_time1"></div>
        <fisc:calendar applyTo="" width="90" showTime="false" format="Y-m-d" divId="div_cgroup_time1" fieldName="cGroupTime1"></fisc:calendar>
        </td></tr></table>
        </td>
	</tr>	
</table>
</form>
</div>
<div id="div_southForm"></div>
</body>
</html>
<script type="text/javascript" defer="defer"> 
var isCreateCopy=false;
// 全局路径
var context = '<%= request.getContextPath() %>';
var tradeMonitoringColumnTree ; // 贸易监控树
var tradeMonitoringTreeLoader ;
var queryField ;
var isdefault = '${isdefault}';

function queryOnkeypress()
{
  if(event.keyCode==13){
        _search();
     }
}
function dictSelect()
{
   var queryField = dict_div_QueryFieldDict.value;
   var td1 = $('td1');
   var td2 = $('td2');
   var td3 = $('td3');
   var td4 = $('td4');
   if(queryField=="01" || queryField=="02"|| queryField=="28")
   {
      td1.style.visibility = "";
      td2.style.visibility = "";
      td3.style.visibility = "";
      td4.style.visibility = "";
   }
   else
   {
      td1.style.visibility = "hidden";
      td2.style.visibility = "hidden";
      td3.style.visibility = "hidden";
      td4.style.visibility = "hidden";
   }
}


function _search()
{
	var para = Form.serialize('mainForm')
	queryField = dict_div_QueryFieldDict.value;
    var dept_Id = div_dept_sh_sh.getValue();
    var apply_time = calendar_div_apply_time.getValue();

    if(!queryField)
    {
        _getMainFrame().showInfo('请选择选择条件！');
        return ;
    }
    else
    {
        if(queryField=='01' || queryField=='02'|| queryField=='28')
        {
            if(!$('queryValue').value && !dept_Id && !apply_time)
            {
               _getMainFrame().showInfo('请输入查询值！');
               return ;
            }
        }
        else
        {
	        if(!$('queryValue').value)
	        {
	           _getMainFrame().showInfo('请输入查询值！');
	           return ;
	        }
        }
    }

	tradeMonitoringTreeLoader.url= context+'/tradeMonitoringController.spr?action=getTradeMonitoringColumnTree&' + para;
	tradeMonitoringColumnTree.render(); 
	tradeMonitoringColumnTree.expand();
	tradeMonitoringColumnTree.body.mask('Loading', 'x-mask-loading');
	tradeMonitoringColumnTree.root.reload();
	tradeMonitoringColumnTree.root.collapse(true, false);
     setTimeout(function(){
    	 tradeMonitoringColumnTree.body.unmask();
     }, 1000); 

     //alert("queryField:" + queryField + ",para:" +  para);
     if(queryField)
     {  
        if(queryField>='09'&&queryField!='28'&&queryField!='29'&&queryField!='30')
        {  //alert(queryField);
		   tradeMonitoringColumnTree.expandAll();
        }
        else if(queryField=='01')
        {
        	var node = tradeMonitoringColumnTree.getRootNode();
            // alert(node.id);
            var bbcnode = tradeMonitoringColumnTree.getNodeById(node.id);
            // alert("node.childNodes.length:" + bbcnode.childNodes.length);
            
	       if (node.childNodes && node.childNodes.length>0)
	       {
	           var child; //node.childNodes.length
	           for (var i=0;i < 2 ; i++)
                {                 
	               child = node.childNodes[i];
                   // alert(child);
	               if (child!=null && child.text.length>0 )
	               {
                        child.expand(true,false);
	               }                    
	           }
	       }      

         }
     }
}


function _resetForm()
{
    dict_div_QueryFieldDict.clearValue();
    $('queryValue').value = "";
    div_dept_sh_sh.setValue("");
    calendar_div_apply_time.setValue("");
}

/**
 * EXT 布局
 */
Ext.onReady(function(){
  dict_div_QueryFieldDict.on("select",dictSelect);
	
  tradeMonitoringTreeLoader = new Ext.tree.TreeLoader({
	   	url:context+'/tradeMonitoringController.spr?action=getTradeMonitoringColumnTree',
	       baseAttrs:{uiProvider: Ext.tree.ColumnTreeNodeUI}// 如果不需要checkbox
															// ,则需要使用
															// Ext.tree.ColumnTreeNodeUI
		 });   
     
  tradeMonitoringTreeLoader.on("beforeload", function(treeLoader, node){   
  tradeMonitoringTreeLoader.baseParams = {'nodeId': node.attributes.nodeId,
                                        'nodeName': node.attributes.nodeName,
                                    'businessType': node.attributes.businessType,
                                       'tradeType': node.attributes.tradeType,
                                'businessTypeName': node.attributes.businessTypeName,
                                      'businessNo': node.attributes.businessNo,
                                    'businessName': node.attributes.businessName,
                                           'sdate': node.attributes.sdate,
                                          'remark': node.attributes.remark,
                                          'remark1': node.attributes.remark1,
                                    'processState': node.attributes.processState,
                                   			 'url': node.attributes.url
                                                    };
           }, tradeMonitoringTreeLoader);
	 
	 
   tradeMonitoringColumnTree = new Ext.tree.ColumnTree({   
				id:'tradeMonitoringColumnTree',
		        region:'center',
		        split:true,
		        el:'div_southForm',
		        // tbar:[toolbars],
		        cmargins:'0 5 0 5',
		        animCollapse:false,
		        animate: true,
		        collapseMode:'mini',
			    border: false,   
			    lines: true,   
                height:400,
			    rootVisible: false,   
			    autoScroll:true,   
			    // checkModel:'cascade',//级联多选，如果不需要checkbox,该属性去掉
			    // onlyLeafCheckable: false,//所有结点可选，如果不需要checkbox,该属性去掉
			    loader:tradeMonitoringTreeLoader,   
			    root: new Ext.tree.AsyncTreeNode({ nodeId:'-1'}),   
			    columns:[   
			        { header:'节点名称', width:155, dataIndex:'nodeName',align :'left'},
			        { header:'类型', width:80, dataIndex:'businessTypeName',align :'center'},
			        { header:'编号', width:120, dataIndex:'businessNo',align :'left',headeralign :'center'},
			        { header:'名称', width:150, dataIndex:'businessName',align :'left',headeralign :'center'},
			        { header:'日期', width:90, dataIndex:'sdate',align :'left',headeralign :'center'},
			        { header:'审批状态', width:90, dataIndex:'processState',align :'left',headeralign :'center'},
				    { header:'备注', width:90, dataIndex:'remark',align :'left',headeralign :'center'},
				    { header:'备注1', width:90, dataIndex:'remark1',align :'left',headeralign :'center'}
			    ],  
  	            listeners: {
	            click : function(node){
                              // alert(node.attributes.url);
   			              },
   			     dblclick : function(node){
                              // alert(node.attributes.url);
                              var xdssBusinessType = node.attributes.businessType;
                              var tradeType = node.attributes.tradeType;
                              var tradeTypeTitle = Utils.getTradeTypeTitle(tradeType);
                              var title = Utils.getTradeTypeTitle(tradeType);
                              var xdssBusinessTypeTitle = Utils.getXdssBusinessTypeTitle(xdssBusinessType);
                              var url = node.attributes.url;
                              //alert(url);
                              if(xdssBusinessType && xdssBusinessType!='<%=XdssBusinessType.CONTRACTGROUP%>')
                              	_getMainFrame().maintabs.addPanel('查看'+ tradeTypeTitle +',' + xdssBusinessTypeTitle,'', url);
                              	//_getMainFrame().ExtModalWindowUtil.show('查看'+ tradeTypeTitle +',' + xdssBusinessTypeTitle,url,'','',{width:800,height:500});
                              
   			              },
                 load:function(node) {
                     if(queryField)
                     {
                        if(queryField>='09'&&queryField!='28'&&queryField!='29'&&queryField!='30')
                        {
                           tradeMonitoringColumnTree.expandAll();
                        }
                        else if(queryField=='01')
                        {
                            // alert("node.childNodes.length:" + bbcnode.childNodes.length);
                            var xdssBusinessType =  node.attributes.businessType ;
                               //alert(xdssBusinessType + typeof(xdssBusinessType));
                               if(typeof(xdssBusinessType) == "undefined" || xdssBusinessType=='PRO' )
                               {
                                  node.select();
                                  node.expand(false,true);
                               }
//                           if (node.childNodes && node.childNodes.length>0)
//                           {
//                               alert("node.childNodes.length:" + node.childNodes.length);
//                               var child;//node.childNodes.length
//                               for (var i=0;i < node.childNodes.length ; i++)
//                                {    
//                                   child = node.childNodes[i];
//                                   alert(i);
//                                   if (child)
//                                   {
//                                      child.select();
//                                      child.expand(false,true);
//                                   }                    
//                               }
//                           }      
                
                         }
                     }
                 },
	            afterrenderer : function(n) {
	            	var checkbox = n.getUI().checkbox;
	            	// if((typeof checkbox != 'undefined') && (n.parentNode !=
					// n.getOwnerTree().getRootNode()))
		                	// checkbox.disabled = false ;
	            }
	        }
		}); 
   tradeMonitoringColumnTree.render(); 
   tradeMonitoringColumnTree.expand(); 
   // tradeMonitoringColumnTree.expandAll();
	 
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			items:[{
					region:"north",
					contentEl:'div_toolbar',
					border:false,
					height:26
				},{
					region:'center',
					border:false,
					contentEl:'div_center'
				},		
				{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            title:'信息列表详情',
		            height:400,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});

	if (isdefault !=''){
		dict_div_QueryFieldDict.setValue(isdefault);
	}
});
</script>
