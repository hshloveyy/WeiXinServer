<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报废管理</title>
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
</head>
<body>
<div id="div_main" class="x-hide-display">
<form id="mainForm" name="mainForm">
	 <table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
      <tr>
        <td align="right">报废部门:</td>
        <td align="left">
        	<input name="deptId" type="hidden" value="${deptId}"/>
        	<input  type="text" value="${deptName}" readonly="readonly"/>
        </td>
        <td align="right">单据提交日期：</td>
        <td align="left">
           <input type="text" value="${applyTime}" readonly="readonly"/>
        </td>
		<td align="right">生效日期：</td>
		<td>
			<input type="text" value="${approvedTime}" readonly="readonly"/>
		</td>
      </tr>
      <tr>
      <td>备注:</td>
      <td colspan="5">
      	<textarea rows="3" name="cmd" style="width:600;overflow-y:visible;word-break:break-all;" ${readable==true?"readonly='readonly'":''}>${cmd}</textarea>
      </td>
      </tr>
      </table>
      		<input type="hidden" name="scrappedId" value="${scrappedId}"/>
 </form>
</div>
<div id="div_grid"  style="position: relative" class="x-hide-display"></div>
<select name="factoryLocal" id="factoryLocal" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${wareHouse}" var="row">
		<option value="${row.code}-${row.title}">${row.title}</option>
	</c:forEach>
</select>
<select name="currencyLocal" id="currencyLocal" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${currency}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<div id="workflowHistory"  style="position: relative" class="x-hide-display"></div>
</body>
<script type="text/javascript">
var grid;
var ds;
var strOperType;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

   	//----------------------------------------------------------------------
    var funAddMtCallBack=function(){
	 	var returnValue = top.ExtModalWindowUtil.getReturnValue(); 
    	var requestUrl = 'scrappedController.spr?action=addMaterial&scrappedId=${scrappedId}';	
    	var rtnValue = '&MATNR='+returnValue.MATNR;
    	rtnValue = rtnValue+'&MAKTX='+returnValue.MAKTX;
    	rtnValue = rtnValue+'&MEINS='+returnValue.MEINS;
		Ext.Ajax.request({
			url: encodeURI(requestUrl+rtnValue),
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var p = new plant({
	                    SCRAPPED_MATERIAL_ID: responseArray.scrappedMaterialId,
	                    MATERIAL_CODE:responseArray.materialCode,
	                    MATERIAL_NAME:responseArray.materialName,
	                    MATERIAL_UNIT:responseArray.materialUnit,
	                    WAREHOUSE:'',
	                    REASON:'',
	                    CURRENCY:''
	                });
				grid.stopEditing();
				ds.insert(0, p);
				grid.startEditing(0, 0);
			},
			failure:function(response, options){
			}
		});    
    }
    var addButton = new Ext.Toolbar.Button({
    	text:'增加',
 	    iconCls:'add',
 	    hidden:${readable==true},
 		handler:function(){
 			top.ExtModalWindowUtil.show('选择物料',
 			'masterQueryController.spr?action=toFindMaterial',
 			'',//blank
 			funAddMtCallBack,
 			{width:800,height:500});
 		}
    });

   	//删除
    var delButton = new Ext.Toolbar.Button({
    	text:'删除',
 	    iconCls:'delete',
 	   	hidden:${readable==true},
		handler:function(){
			if (grid.selModel.hasSelection()){
				var records = grid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个物料信息！');
				}else{
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							strOperType='del';
   							if(buttonId=='yes'){
   								var param = "?action=delMaterial&scrappedMaterialId="+records[0].data.SCRAPPED_MATERIAL_ID;
								new AjaxEngine('scrappedController.spr',{method:"post", parameters: param, onComplete: callBackHandle});
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的明细信息！');
			}
		  }
    	});
	var plant = Ext.data.Record.create([
		{name:'SCRAPPED_MATERIAL_ID'},
		{name:'SCRAPPED_ID'},
		{name:'MATERIAL_CODE'},
		{name:'MATERIAL_NAME'},
		{name:'MATERIAL_UNIT'},
		{name:'BATCH_NO'},
		{name:'MONEY'},
		{name:'WAREHOUSE'},
		{name:'REASON'},
		{name:'CURRENCY'},
		{name:'QUANTITY'}
	]);

	ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'scrappedController.spr?action=getMaterial&scrappedId=${scrappedId}'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},plant)
    });

  	 var tbar = new Ext.Toolbar({
 		items:[addButton,'-',delButton]
 	});
  	
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
           {
			   header: '物料ID',
	           sortable: true,
	           hidden: true,
	           dataIndex: 'SCRAPPED_MATERIAL_ID'
           },		
           {
			   header: '报废物料编号',
	           width: 100,
	           sortable: true,
	           dataIndex: 'MATERIAL_CODE'
           },
		   {
			   header: '物料描述',
	           width: 150,
	           sortable: true,
	           dataIndex: 'MATERIAL_NAME'
           },        
		   {
			   header: '物料单位',
	           width: 80,
	           sortable: true,
	           dataIndex: 'MATERIAL_UNIT'
           },        
		   {
			   header: '批次',
	           width: 60,
	           sortable: true,
	           dataIndex: 'BATCH_NO',
	           editor:new Ext.form.TextField({
               		readOnly:${readable==true},
	        	   	allowBlank:true
		           })
           },        
		   {
			   header: '报废数量',
	           width: 80,
	           sortable: false,
	           dataIndex: 'QUANTITY',
	           editor: new  Ext.form.NumberField({
               		allowBlank: false,
              		allowNegative: false,
               		decimalPrecision:3,
               		readOnly:${readable==true},
               		maxValue: 10000000000
             })  
           },{
	           header: '币种',
	           width: 100,
	           sortable: true,
	           hidden:false,
	           dataIndex: 'CURRENCY',
	           editor: new Ext.form.ComboBox({
	               typeAhead: true,
	               maxHeight:150,
	               triggerAction: 'all',
	               transform:'currencyLocal',
	               lazyRender:true,
	               disabled:${readable==true},
	               listClass: 'x-combo-list-small',
	               allowBlank: true
	            })
           },{
	           header: '报废金额',
	           width: 80,
	           sortable: true,
	           dataIndex: 'MONEY',
	           editor: new  Ext.form.NumberField({
              		allowBlank: false,
               		allowNegative: false,
               		readOnly:${readable==true},
               		maxValue: 10000000000
              })  
           },
           {
	           header: '报废仓库',
	           width: 200,
	           sortable: true,
	           dataIndex: 'WAREHOUSE',
	           editor: new Ext.form.ComboBox({
	               typeAhead: true,
	               maxHeight:150,
	               triggerAction: 'all',
	               transform:'factoryLocal',
	               lazyRender:true,
	               disabled:${readable==true},
	               listClass: 'x-combo-list-small',
	               allowBlank: true
	            })
           },{
	           header: '报废原因',
	           width: 300,
	           sortable: true,
	           dataIndex: 'REASON',
	           editor: new Ext.form.TextField({
	        	   readOnly:${readable==true},
              		allowBlank: true
              })  
           }
           
    ]);
    cm.defaultSortable = true;
    
    var bbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:ds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
  
   ds.load({params:{start:0, limit:10},arg:[]});    
   
    grid = new Ext.grid.EditorGridPanel({
    	id:'grid',
        ds: ds,
        cm: cm,
        sm: sm,
        bbar: bbar,
        tbar: tbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        renderTo: 'div_grid',
        width:800,
        height:260,
        clicksToEdit:1,        
        layout:'fit'
    });
    
   // grid.render();
    
    var afterEdit=function(obj){
    	var row = obj.record;//获取被修改的行
        var colName = obj.field;//获取被修改的列
        var scrappedMaterialId = row.get("SCRAPPED_MATERIAL_ID");
        var colValue = row.get(colName);     
        var parm='&scrappedMaterialId='+scrappedMaterialId+'&colName='+colName+'&colValue='+colValue;
    	var requestUrl = 'scrappedController.spr?action=updateMateriel'+parm;
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
			},
			failure:function(response, options){					
			}
		});    
    } 
    grid.on("afteredit", afterEdit, grid);



    
	var tabs = new Ext.TabPanel({
		id:'mainTabPanel',
        autoWidth:true,
        activeTab: 0,
        autoScroll:true,
        loadMask:true,
        frame:true,
        defaults:{autoHeight: true},
        items:[{
            	title:'业务信息',
            	layout:'form',
            	buttonAlign:'center',
                items:[{
                		miniHeight:80,
                		autoHeight:true,
        				contentEl: 'div_main'
                    },{
                    	autoScroll:true,
        	            contentEl:'div_grid'
                    }],
            	buttons:[{
    	            text:'保存',
    	            hidden:${readable==true},
    	            handler:function(){
    	            	var param1 = Form.serialize('mainForm');
    					var param = param1 + "&action=updateScrapped&nosubmit=${nosubmit}";
    					new AjaxEngine('scrappedController.spr', 
    							   {method:"post", parameters: param, onComplete: callBackHandle});
    				}
    			},{
                	text:'提交',
                	<c:if test="${nosubmit!='yes'}">
                    hidden:${readable==true},
                    </c:if>
                    <c:if test="${nosubmit=='yes'}">
                    hidden:true,
                    </c:if>
                	handler:function(){
                		if (ds.getCount() > 0){
                			var param1 = Form.serialize('mainForm');
    						var param = param1  + "&action=firstSubmit&scrappedId=${scrappedId}";
    						new AjaxEngine('scrappedController.spr', 
    						   {method:"post", parameters: param, onComplete: callBackHandle});
    						   
    						strOperType = 'submit';
    					
    					} else{
    					top.Ext.Msg.show({
    						title:'提示',
    	  					msg: '没有增加物料信息,请确认是否真的要提交审批?',
    	  					buttons: {yes:'是', no:'否'},
    	  					fn: function(buttonId,text){
    	  						if(buttonId=='yes'){
    		             			var param1 = Form.serialize('mainForm');
    		             			param1 = param1 + "&deptId=" + selectId_dept;
    								var param = param1  + "&action=saveAndSubmitScrapped";
    								new AjaxEngine('scrappedController.spr', 
    						   			{method:"post", parameters: param, onComplete: callBackHandle});
    						   				
    						   		strOperType = 'submit';
    	  						}
    	  					},
    	  					icon: Ext.MessageBox.QUESTION
    					});
    					}          	    	
                	    
                		}
    			 },{
                	text:'关闭',
                	hidden:${readable==true},
                	handler:function(){
                		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
                			top.ExtModalWindowUtil.markUpdated();
    				 		top.ExtModalWindowUtil.close();
    	            }
                }]    
            },{
				title:'审批信息',
				contentEl:'workflowHistory'
            }]
	});
  	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'fit',
			items:[tabs]
		}]
	});

});//end of Ext.onReady   
//回调函数
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if (strOperType == 'del'){
		ds.reload();
	}	
	if (strOperType == 'submit'){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
	    top.ExtModalWindowUtil.close();
	}	
}
</script>
</html>

<fiscxd:workflow-taskHistory divId="workflowHistory" businessRecordId="${scrappedId}" width="800"></fiscxd:workflow-taskHistory>



