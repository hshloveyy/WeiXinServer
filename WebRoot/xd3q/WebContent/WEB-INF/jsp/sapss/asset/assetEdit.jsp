<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>固定资产编辑</title>
<style type="text/css">
.add{
	background-image:url(<%=request.getContextPath()%>/images/fam/add.gif) !important;
}
.delete{
	background-image:url(<%=request.getContextPath()%>/images/fam/delete.gif) !important;
}
.update{
	background-image:url(<%=request.getContextPath()%>/images/fam/refresh.gif) !important;
}
.find{
	background-image:url(<%=request.getContextPath()%>/images/fam/find.png) !important;
}
</style>
<script type="text/javascript">

var assetUserHisgrid;		//使用人明细列表
var assetUserHisds;
var assetMaintaingrid;		//维修记录明细列表
var assetMaintainds;
var assetInfoId = '${info.assetInfoId}';
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Date.prototype.toString=function(){ return this.format("Y-m-d");};
   	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';
   	var fm = Ext.form;  
   	function dateRender(value){ 
	  if(value==''||value==null||typeof value == "string"){ 
	     return value; 
	  } 
	  return value.format("Y-m-d"); 
	}
    //增加使用人
    var addassetUserHis = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			var requestUrl = 'assetController.spr?action=addUserHis&assetInfoId=' + assetInfoId;
			Ext.Ajax.request({
				url: requestUrl,
				success: function(response, options){
				    assetUserHisds.proxy = new Ext.data.HttpProxy({url:'assetController.spr?action=queryUserHis&assetInfoId='+assetInfoId});
  			        assetUserHisds.load({params:{start:0, limit:10},arg:[]});
				},
				failure:function(response, options){
					//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
				}
			});    
		}
   	});
   	
   	//删除使用人
   	var deleteassetUserHis = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
		  if (assetUserHisgrid.selModel.hasSelection()){
				var records = assetUserHisgrid.selModel.getSelections();
				if (records.length < 1){
					top.ExtModalWindowUtil.alert('提示','请选择要删除的记录！');
				}else{
					var idList = '';
					for (var i=0;i<records.length;i++){
						idList = idList + records[i].data.ASSETUSERHISID + '|';
					}
					top.Ext.Msg.show({
						title:'提示',
	  						msg: '是否确定删除记录',
	  						buttons: {yes:'是', no:'否'},
	  						fn: function(buttonId,text){
	  							if(buttonId=='yes'){
	  							    var param = "assetController.spr?action=deleteUserHis";
									    param = param + "&idList=" + idList;
 									Ext.Ajax.request({
									    url: param,
										success: function(response, options){
						  			        assetUserHisds.load({params:{start:0, limit:10},arg:[]});
										},
										failure:function(response, options){
											//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
										}
								    });    
	  							}
	  						},
	  						icon: Ext.MessageBox.QUESTION
					});
				}
			}			
		}
   	});

    var assetUserHistbar = new Ext.Toolbar({
		items:[addassetUserHis,'-',deleteassetUserHis]
	  });

	var assetUserHisPlant = Ext.data.Record.create([
		{name:'ASSETUSERHISID'}, 
		{name:'ASSETINFOID'}, 
		{name:'DEPTID'}, 
		{name:'DEPTNAME'},
		{name:'USERMAN'}, 
	    {name:'STARTDATE'}, 
		{name:'ENDDATE'}
	]);

	assetUserHisds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'assetController.spr?action=queryUserHis&assetInfoId='+assetInfoId}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},assetUserHisPlant)
    });
    
    var assetUserHissm = new Ext.grid.CheckboxSelectionModel();
    
    var assetUserHiscm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		assetUserHissm,
		   {header: 'ASSETUSERHISID',
           width: 100,
           sortable: true,
           dataIndex: 'ASSETUSERHISID',
           hidden:true
           },
		   {header: 'ASSETINFOID',
           width: 100,
           sortable: true,
           dataIndex: 'ASSETINFOID',
           hidden:true
           },
		   {header: 'DEPTID',
           width: 100,
           sortable: true,
           dataIndex: 'DEPTID',
           hidden:true
           },           
           {header: '部门',
           width: 150,
           sortable: true,
           dataIndex: 'DEPTNAME'
           /**
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               allowBlank: false,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'depts',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })*/
           },           
           {header: '使用人',
           width: 100,
           sortable: true,
           dataIndex: 'USERMAN',
           editor: new fm.TextField({
               allowBlank: false,
               maxLength:10
           })
           },
		　 {header: '开始时间',
           width: 100,
           sortable: true,
           dataIndex: 'STARTDATE',
           editor:new Ext.form.DateField({
               format:'Y-m-d'
           })
           },
		　 {header: '结束时间',
           width: 100,
           sortable: false,
           dataIndex: 'ENDDATE',
           editor:new Ext.form.DateField({
                format:'Y-m-d'
           })
           }
    ]);
    
    assetUserHiscm.defaultSortable = true;
        
    var assetUserHisbbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:assetUserHisds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
	 
   assetUserHisds.load({params:{start:0, limit:10},arg:[]});
   

	assetUserHisgrid = new Ext.grid.EditorGridPanel({
    	id:'assetUserHisGrid',
        ds: assetUserHisds,
        cm: assetUserHiscm,
        sm: assetUserHissm,
        bbar: assetUserHisbbar,
        tbar: assetUserHistbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_userinfo',
        autowidth:true,
        clicksToEdit:1,
        layout:'fit'
    });
 
    assetUserHisgrid.render();
    
    assetUserHisgrid.on("cellclick", userHiscellclick, assetUserHisgrid);
    
    function userHiscellclick(grid, rowIndex, columnIndex, e){
       if(columnIndex==5){
		      top.ExtModalWindowUtil.show('部门信息',
			'assetController.spr?action=toFindDept' ,
			'',
			selectDeptCallback,
			{width:755,height:478});
       }
    }
    
    function selectDeptCallback(){
		var returnvalue = top.ExtModalWindowUtil.getReturnValue();
		var records = assetUserHisgrid.selModel.getSelections();
		records[0].set('DEPTNAME',returnvalue.DEPT_NAME);
		records[0].set('DEPTID',returnvalue.DEPT_ID);
		var assetUserHisId = records[0].get('ASSETUSERHISID');
		var requestUrl = 'assetController.spr?action=updateUserHis';
			requestUrl = requestUrl + '&assetUserHisId=' + assetUserHisId;
			requestUrl = requestUrl + '&colname=DEPTNAME' ;
			requestUrl = requestUrl + '&colvalue=' + returnvalue.DEPT_NAME;
			
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				assetUserHisds.commitChanges();
			},
			failure:function(response, options){
			}
		});
		var requestUrl = 'assetController.spr?action=updateUserHis';
			requestUrl = requestUrl + '&assetUserHisId=' + assetUserHisId;
			requestUrl = requestUrl + '&colname=DEPTID' ;
			requestUrl = requestUrl + '&colvalue=' + returnvalue.DEPT_ID;
			
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				assetUserHisds.commitChanges();
			},
			failure:function(response, options){
			}
		});
   }
    
    assetUserHisgrid.on("afteredit", afterUserHisEdit, assetUserHisgrid);
    
    function afterUserHisEdit(obj){
    	var row = obj.record;//获取被修改的行
        var colname = obj.field;//获取被修改的列
        var assetUserHisId = row.get("ASSETUSERHISID");
        var colvalue = row.get(colname);
        
        var requestUrl = 'assetController.spr?action=updateUserHis';
			requestUrl = requestUrl + '&assetUserHisId=' + assetUserHisId;
			requestUrl = requestUrl + '&colname=' + colname;
			requestUrl = requestUrl + '&colvalue=' + colvalue;
			
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				assetUserHisds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    }
    
    
    //增加维修记录
    var addassetMaintain = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			var requestUrl = 'assetController.spr?action=addMaintain&assetInfoId=' + assetInfoId;
			Ext.Ajax.request({
				url: requestUrl,
				success: function(response, options){
				    assetMaintainds.proxy = new Ext.data.HttpProxy({url:'assetController.spr?action=queryMaintain&assetInfoId='+assetInfoId});
  			        assetMaintainds.load({params:{start:0, limit:10},arg:[]});
				},
				failure:function(response, options){
					//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
				}
			});    
		}
   	});
   	
   	//删除维修记录
   	var deleteassetMaintain = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){if(assetMaintaingrid.selModel.hasSelection()){
				var records = assetMaintaingrid.selModel.getSelections();
				if (records.length < 1){
					top.ExtModalWindowUtil.alert('提示','请选择要删除的记录！');
				}else{
					var idList = '';
					for (var i=0;i<records.length;i++){
						idList = idList + records[i].data.ASSETMAINTAINID + '|';
					}
					top.Ext.Msg.show({
						title:'提示',
	  						msg: '是否确定删除记录',
	  						buttons: {yes:'是', no:'否'},
	  						fn: function(buttonId,text){
	  							if(buttonId=='yes'){
	  							    var param = "assetController.spr?action=deleteMaintain";
									    param = param + "&idList=" + idList;
 									Ext.Ajax.request({
									    url: param,
										success: function(response, options){
						  			        assetMaintainds.load({params:{start:0, limit:10},arg:[]});
										},
										failure:function(response, options){
											//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
										}
								    });    
	  							}
	  						},
	  						icon: Ext.MessageBox.QUESTION
					});
				}
			}			
		}
   	});

    var assetMaintaintbar = new Ext.Toolbar({
		items:[addassetMaintain,'-',deleteassetMaintain]
	  });

	var assetMaintainPlant = Ext.data.Record.create([
		{name:'ASSETMAINTAINID'}, 
		{name:'ASSETINFOID'}, 
		{name:'MAINTAINDATE'}, 
		{name:'MAINTAINCOMNAME'}, 
	    {name:'MAINTAINMONEY'}, 
		{name:'MEMO'}
	]);

	assetMaintainds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'assetController.spr?action=queryMaintain&assetInfoId='+assetInfoId}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},assetMaintainPlant)
    });
    
    var assetMaintainsm = new Ext.grid.CheckboxSelectionModel();
    
    var assetMaintaincm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		assetMaintainsm,
		   {header: 'ASSETMAINTAINID',
           width: 100,
           sortable: true,
           dataIndex: 'ASSETMAINTAINID',
           hidden:true
           },
		   {header: 'ASSETINFOID',
           width: 100,
           sortable: true,
           dataIndex: 'ASSETINFOID',
           hidden:true
           },           
           {header: '维修日期',
           width: 100,
           sortable: true,
           dataIndex: 'MAINTAINDATE',
           editor:new Ext.form.DateField({
              		format:'Y-m-d'
           })
           },           
           {header: '维修公司名称',
           width: 150,
           sortable: true,
           dataIndex: 'MAINTAINCOMNAME',
           editor: new fm.TextField({
               allowBlank: false,
               maxLength:100
           })
           },
		　 {header: '维修费用(元)',
           width: 100,
           sortable: true,
           dataIndex: 'MAINTAINMONEY',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 100000000
           })
           },
		　 {header: '备注',
           width: 150,
           sortable: false,
           dataIndex: 'MEMO',
           editor: new fm.TextField({
               allowBlank: false,
               maxLength:200
           })
           }
    ]);
    
    assetMaintaincm.defaultSortable = true;

    var assetMaintainbbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:assetMaintainds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
	 
   assetMaintainds.load({params:{start:0, limit:10},arg:[]});
   

	assetMaintaingrid = new Ext.grid.EditorGridPanel({
    	id:'assetMaintainGrid',
        ds: assetMaintainds,
        cm: assetMaintaincm,
        sm: assetMaintainsm,
        bbar: assetMaintainbbar,
        tbar: assetMaintaintbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_maintain',
        autowidth:true,
        clicksToEdit:1,
        layout:'fit'
    });
 
    assetMaintaingrid.render();
    
    assetMaintaingrid.on("afteredit", afterMaintainEdit, assetMaintaingrid);
    
    function afterMaintainEdit(obj){
    	var row = obj.record;//获取被修改的行
        var colname = obj.field;//获取被修改的列
        var assetMaintainId = row.get("ASSETMAINTAINID");
        var colvalue = row.get(colname);
        
        var requestUrl = 'assetController.spr?action=updateMaintain';
			requestUrl = requestUrl + '&assetMaintainId=' + assetMaintainId;
			requestUrl = requestUrl + '&colname=' + colname;
			requestUrl = requestUrl + '&colvalue=' + colvalue;
			
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				assetMaintainds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    }
    
    
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			height:274,
			id:'infotype',
			name:'infotype',
            autoScroll:'true',
            activeTab: 0,			
            items:[{
            	contentEl: 'div_main',
            	id:'maininfo',
				name:'maininfo',
				autoScroll:'true',
            	layout:'fit'
            }]
		},{
			region:"center",
			layout:'fit',
			buttonAlign:'center',
			autoScroll:'true',
			items:[{
				region:'center',
				xtype:'tabpanel',
				id:'infotype',
				name:'infotype',
				plain:true,
	            height:300,
	            autoScroll:'true',
	            activeTab: 0,
	            items:[{
			            	title:'资产历史使用记录',
			            	contentEl: '',
			            	id:'assetUserHis',
							name:'assetUserHis',
							autoScroll:'true',
			            	layout:'fit',
			            	disabled:${empty info.assetInfoId},
			            	items:[assetUserHisgrid]
	                  },
	                  {
			            	title:'资产维修记录',
			            	contentEl: '',
			            	id:'assetMaintain',
							name:'assetMaintain',
							autoScroll:'true',
			            	layout:'fit',
			            	disabled:${empty info.assetInfoId},
			            	items:[assetMaintaingrid]
	                  }]
	             }],
	             buttons:[{
		            	text:'保存',
		            	id:'saveBtn',
		            	handler:function(){
		            	  Ext.getDom('saveBtn').disabled=true;
						  var ajax = Ext.Ajax.request({
							   url: 'assetController.spr?action=add',
							   params:Form.serialize('mainForm'),
							   success: function(response, options){
							        Ext.getDom('saveBtn').disabled=false;
									var txt = Ext.util.JSON.decode(response.responseText);
									var json =txt['infolion-json'];
									if(json!=null && json.message!=null){
										top.Ext.Msg.show({
					   						title:'警告',
					   						closable:false,
					   						msg:json.message,
					   						buttons:{yes:'关闭'},
					   						fn:Ext.emptyFn,
					   						icon:Ext.MessageBox.WARNING
										});
									}else{
									    Ext.getCmp('assetUserHis').enable();
	                                    Ext.getCmp('assetMaintain').enable();
	                                    if($('sapAssetNo').value==''){
										    Ext.getCmp('subToSap').enable();
										}									
										Ext.getDom('assetInfoId').value=txt.assetInfoId;
										assetInfoId = txt.assetInfoId;
										Ext.Msg.alert('提示','保存成功');
									}
								},
								waitMsg:'处理中...',
								failure:function(resp,o){
								   Ext.getDom('saveBtn').disabled=false;
								   Ext.Msg.alert('Error',o.result.msg);
								}
						  });
						}
		            },
		            {
		        	text:'写入SAP',
		        	id:'subToSap',
		        	disabled:(${empty info.assetInfoId||!empty info.sapAssetNo}&&$('category').value=='1'),
		        	handler:function(){
		            		var param = Form.serialize('mainForm') + "&action=submitToSap";
							new AjaxEngine('assetController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
		                   }
		            },
		            {
		        	text:'关闭',
		        	handler:function(){
		        	        top.ExtModalWindowUtil.markUpdated();
		                    top.ExtModalWindowUtil.close();
		                   }
		            }]
		}]
		
	});//end fo viewPort
	
	//购置时间
	var purchaseDate = new Ext.form.DateField({
	  		format:'Y-m-d',
			name:"purchaseDate",
			id:"purchaseDate",
			width: 155,
			readOnly:false,
			applyTo:'purchaseDate',
			listeners:{
               'select':countScrapDate
            } 
	});
	//预计报废时间
	var scrapDate = new Ext.form.DateField({
	  		format:'Y-m-d',
			name:"scrapDate",
			id:"scrapDate",
			width: 155,
			readOnly:false,
			applyTo:'scrapDate'
	});
});//end of Ext.onReady   
function selectCostCenter(type){
     top.ExtModalWindowUtil.show('成本中心信息',
			'assetController.spr?action=toFindCostCenter&type=' + type,
			'',
			selectCostCenterCallback,
			{width:755,height:478});
}
function selectCostCenterCallback(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	$('costCenter').value=returnvalue.KOSTL;
	$('costCenterName').value=returnvalue.KTEXT;
}
function  check() 
{ 
     var frozenMark=document.getElementById("frozenMark");
  if(frozenMark.checked==true)
  frozenMark.value="1";
if(frozenMark.checked==false)
  frozenMark.value="0";
}

function countScrapDate(){
var purchaseDate = $('purchaseDate').value;
var depreciationYear = $('depreciationYear').value;
if(purchaseDate==''||depreciationYear=='') return;
if(isNaN(depreciationYear)){
    Ext.Msg.alert('提示','折旧年限请填写整数！');
}
var d = purchaseDate.split('-');
var dd = new Date(d[0],d[1]-1,d[2]);
dd.setMonth(dd.getMonth() + parseFloat(depreciationYear));
$('scrapDate').value=dd;
}
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if(customField.sapAssetNo!=''){
		$('sapAssetNo').value=customField.sapAssetNo;
		Ext.getCmp('subToSap').disable();
    }	
}
</script>
</head>
<body>
<div id='div_progressBar'></div>
<!-- <div id="div_basrForm">  -->

<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
	 <table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
     <tr>
        <td width="11%" align="right">公司代码<font color="red">▲</font>：</td>
        <td width="22%" align="left">
        	<input type="text" name="comCode" value="${empty info.comCode ?comCode:info.comCode}" readonly="readonly"/>
        	<input type="hidden" name="assetInfoId" id="assetInfoId" value="${info.assetInfoId }"/>
        </td>
        <td width="11%" align="right">资产分类<font color="red">▲</font>：</td>
        <td width="22%" align="left">
			<div id="div_assetType"></div>
        </td>
        <td width="11%" align="right">业务范围：</td>
        <td width="22%" align="left"><div id="div_businessScope"></div>
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">成本中心<font color="red">▲</font>：</td>
        <td width="22%" align="left">
            <input type="hidden" id="costCenter" name="costCenter" value="${info.costCenter }"/>
			<input type="text" id="costCenterName" name="costCenterName" value="${info.costCenterName }" readonly="readonly"/>
			<input type="button" value="..." onclick="selectCostCenter('1');">
        </td>
        <td align="right">资产名称<font color="red">▲</font>：</td>
        <td  align="left">
        	<input name="assetName" type="text" value="${info.assetName }"/>
        </td>
        <td align="right">型号配置<font color="red">▲</font>：</td>
        <td align="left">
        	<input name="assetConfig" type="text" value="${info.assetConfig }"/>
        </td>
      </tr>
      <tr>
        <td align="right">设备序列号<font color="red">▲</font>：</td>
        <td align="left">
        	<input name="assetSerialNo" type="text" value="${info.assetSerialNo }"/>
        </td>
        <td align="right">外部编号<font color="red">▲</font>：</td>
        <td align="left">
        	<input name="outsideNo" type="text" value="${info.outsideNo }"/>
        </td>
        <td align="right">存放位置<font color="red">▲</font>：</td>
        <td align="left">
        	<input name="location" type="text" value="${info.location }"/>
        </td>
      </tr>
      <tr>
        <td align="right">数量：</td>
        <td align="left">
        	<input name="quality" type="text" value="1" readonly="readonly" value="${info.quality }"/>
        </td>
        <td align="right">计量单位：</td>
        <td align="left">
        	<input name="measure" type="text" value="${info.measure }"/>
        </td>
        <td align="right">购置时间<font color="red">▲</font>：</td>
        <td align="left">
        	<input name="purchaseDate" type="text" readonly="readonly" value="${info.purchaseDate }"/>
        </td>
      </tr>
      <tr>
        <td align="right">采购合同号：</td>
        <td align="left">
        	<input name="contractPuchaseNo" type="text" value="${info.contractPuchaseNo }"/>
        </td>
        <td align="right">采购金额<font color="red">▲</font>：</td>
        <td align="left">
        	<input name="cost" type="text" value="${info.cost }"/>
        </td>
        <td align="right">供应商名称：</td>
        <td align="left">
        	<input name="supplierName" type="text" value="${info.supplierName }"/>
        </td>
      </tr>
      <tr>
        <td align="right">采购联系人：</td>
        <td align="left">
        	<input name="purchaseLinkMan" type="text" value="${info.purchaseLinkMan }"/>
        </td>
        <td align="right">采购联系电话：</td>
        <td align="left">
        	<input name="purchaseLinkTel" type="text" value="${info.purchaseLinkTel }"/>
        </td>
        <td align="right">保修期：</td>
        <td align="left">
        	<input name="guaranteeDate" type="text" value="${info.guaranteeDate }"/>
        </td>
      </tr>
      <tr>
        <td align="right">维修电话：</td>
        <td align="left">
        	<input name="maintainTel" type="text" value="${info.maintainTel }"/>
        </td>
        <td align="right">折旧年限：</td>
        <td align="left">
        	<input name="depreciationYear" type="text" value="${info.depreciationYear }" onchange="countScrapDate();"/>月
        </td>
        <td align="right">预计报废时间：</td>
        <td align="left">
        	<input name="scrapDate" type="text" readonly="readonly" value="${info.scrapDate }"/>
        </td>
      </tr>
      <tr>
        <td align="right">资产使用状态<font color="red">▲</font>：</td>
        <td align="left">
        	<div id="div_state"></div>
        </td>
        <td align="right">SAP资产号：</td>
        <td align="left">
        	<input name="sapAssetNo" type="text" readonly="readonly" value="${info.sapAssetNo }"/>
        </td>
        <td align="right">资产类别<font color="red">▲</font>：</td>
        <td align="left">
            <input type="hidden" id="category" name="category" value="${empty category?info.category:category }"> 
        	<div id="div_category"></div>
        </td>
      </tr>
      <tr>
        <td align="right">冻结标记：</td>
        <td align="left">
        	<input id="frozenMark" name="frozenMark" type="checkbox" value="${info.frozenMark}" ${info.frozenMark==1?"checked":""} onclick="check()"/>
        </td>
        <td align="right">冻结原因：</td>
        <td align="left" colspan="3">
        	<input name="frozenReason" type="text" value="${info.frozenReason }" size="68"/>
        </td>
      </tr>
      <tr>
        <td align="right">备注：</td>
        <td colspan="5">
        	 <textarea cols="80" rows="1" id="memo" name="memo" style="width:95%;overflow-y:visible;word-break:break-all;">${info.memo }</textarea>
        </td>
    </tr>
	</table>
</form>
</div>
<div id="div_userinfo"></div>
<div id="div_maintain"></div>
<fiscxd:dictionary divId="div_assetType" fieldName="assetType" dictionaryName="BM_ASSET_TYPE" width="153" selectedValue="${info.assetType }"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_businessScope" fieldName="businessScope" dictionaryName="BM_BUSINESS_SCOPE" width="153" selectedValue="${info.businessScope }"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_state" fieldName="state" dictionaryName="BM_ASSET_STATE" width="153" selectedValue="${info.state }"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_category" fieldName="category1" dictionaryName="BM_ASSET_CATEGORY" width="153" selectedValue="${empty category?info.category:category }" disable="true"></fiscxd:dictionary>
</body>
</html>
    
