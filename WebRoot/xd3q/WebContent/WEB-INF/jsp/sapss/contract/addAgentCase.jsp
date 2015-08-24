<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.add{
	background-image:url(images/fam/add.gif) !important;
}
.delete{
	background-image:url(images/fam/delete.gif) !important;
}
.close{
	background-image:url(images/fam/forward.gif) !important;
}
</style>
</head>
<body>
<div id="div_otherInfo"></div><form action="" id="testform" name="testform">
<select name="konvKschl" id="konvKschl" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Condition}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="konvKschl11" id="konvKschl11" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Condition}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>

<select name="konvWears" id="konvWears" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Currency}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="konvWears11" id="konvWears11" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Currency}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
</form>
</body>
</html>
<script type="text/javascript">
var strSalesRowsId = '${salesrowsid}';
var strSapRowNo = '${saprowno}';
var otherds;

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	
	otherds.reload();
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var fm = Ext.form;
   	
   	var otherPlant = Ext.data.Record.create([
   		{name:'MATERIEL_CASE_ID'},
   		{name:'SALES_ROWS_ID'},
   		{name:'KONV_KSCHL_D_CONDITION_TYPE_SE'},
   		{name:'KONV_WEARS_D_CURRENCY'},
   		{name:'RATE'},
   		{name:'KONV_KBETR'},
   		{name:'CMD'}
	]);

	otherds = new Ext.data.Store({
   		proxy : new Ext.data.HttpProxy({url:'contractController.spr?action=queryContractAgentMtCase&salesrowsid='+strSalesRowsId}),
   		reader: new Ext.data.JsonReader({
       	root: 'root',
       	totalProperty: 'totalProperty'
       	},otherPlant)
    });
    
    var othersm = new Ext.grid.CheckboxSelectionModel();
    
    var othercm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		othersm,
			{header: '其他费用ID',
           	width: 100,
           	sortable: true,
           	dataIndex: 'MATERIEL_CASE_ID',
           	hidden:true
			},
           {header: '销售合同行项ID',
           width: 100,
           sortable: true,
           dataIndex: 'SALES_ROWS_ID',
           hidden:true
           },
           {header: '*条件类型',
           width: 100,
           sortable: true,
           dataIndex: 'KONV_KSCHL_D_CONDITION_TYPE_SE',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'konvKschl',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })
           },
           {header: '*金额',
           width: 100,
           sortable: true,
           dataIndex: 'KONV_KBETR',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue:999999999999
           })             
           },
           {header: '*币别',
           width: 100,
           sortable: true,
           dataIndex: 'KONV_WEARS_D_CURRENCY',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'konvWears',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })
           },
           {
               header: '*汇率',
               width: 100,
               sortable: true,
               dataIndex: 'RATE',
               editor: new fm.NumberField({
                   allowBlank: false,
                   allowNegative: false,
                   decimalPrecision:2,
                   maxValue: 999999999999
               })
            },
          {header: '*价格( 条件金额或百分数 )',
           width: 200,
           sortable: true,
           dataIndex: 'KONV_KBETR',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 999999999999
           })
           },
           {header: '*备注',
           width: 100,
           sortable: true,
           dataIndex: 'CMD',
           editor: new fm.TextField({
               allowBlank: false
           })
           }
    ]);
    othercm.defaultSortable = true;
    
    var addOther = new Ext.Toolbar.Button({
   		text:'增加',
   		iconCls:'add',
		handler:function(){
			var requestUrl = 'contractController.spr?action=addContractAgentMtCase&salesrowsid='+strSalesRowsId;
			Ext.Ajax.request({
				url: requestUrl,
				success: function(response, options){
					var responseArray = Ext.util.JSON.decode(response.responseText);

					var othertp = new otherPlant({
			    			MATERIEL_CASE_ID: responseArray.materielCaseId,
							KONV_KSCHL_D_CONDITION_TYPE_SE: responseArray.konvKschl,
							KONV_KBETR: responseArray.konvKbetr,
							KONV_WEARS_D_CURRENCY: responseArray.konvWears,
							CMD: responseArray.cmd
			             });
					othergrid.stopEditing();
					otherds.insert(0, othertp);
					othergrid.startEditing(0, 0);
				},
				failure:function(response, options){
					//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
				}
			});
		}
   	});
   	
   	var deleteOther = new Ext.Toolbar.Button({
   		text:'删除',
   		iconCls:'delete',
		handler:function(){
			if (othergrid.selModel.hasSelection()){
				var records = othergrid.selModel.getSelections();
				var idList = '';
				for (var i=0;i<records.length;i++){
					idList = idList + records[i].data.MATERIEL_CASE_ID + '|';
				}
				
				top.Ext.Msg.show({
					title:'提示',
  						msg: '是否确定删除记录',
  						buttons: {yes:'是', no:'否'},
  						fn: function(buttonId,text){
  							if(buttonId=='yes'){
  								var param = "?action=deleteAgentMtCase";
							param = param + "&idList=" + idList;

							new AjaxEngine('contractController.spr', 
					   			{method:"post", parameters: param, onComplete: callBackHandle});
  							}
  						},
  						icon: Ext.MessageBox.QUESTION
				});
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的物料信息！');
			}
		}
   	});
   	
   	var closeOther = new Ext.Toolbar.Button({
   		text:'关闭',
   		iconCls:'close',
		handler:function(){
			top.ExtModalWindowUtil.close();
		}
   	});
   	
   	var othertbar = new Ext.Toolbar({
		items:[addOther,'-',deleteOther,'-',closeOther]
	});
    
    var otherbbar = new Ext.PagingToolbar({
        pageSize: 50,
        store:otherds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    var othergrid = new Ext.grid.EditorGridPanel({
    	id:'otherGrid',
        ds: otherds,
        cm: othercm,
        sm: othersm,
        tbar: othertbar,
        bbar: otherbbar,
        border:false,
        clicksToEdit:1,
        loadMask:true,
        autoScroll:true,
        el: 'div_otherInfo',
        layout:"fit"
    });
    
    otherds.load({params:{start:0, limit:10}});
    
     othergrid.on("afteredit", afterEdit, othergrid);
     
     function afterEdit(obj){
    	var row = obj.record;//获取被修改的行
        var colname = obj.field;//获取被修改的列
        var matreielcaseid = row.get("MATERIEL_CASE_ID");
        var colvalue = row.get(colname);
        
        if (colname == 'KONV_KSCHL_D_CONDITION_TYPE_SE'){   
        	row.set(colname,getTitle('konvKschl11',colvalue));
        	colname = 'KONV_KSCHL';
        }
        if (colname == 'KONV_WEARS_D_CURRENCY'){
        	row.set(colname,getTitle('konvWears11',colvalue));
        	colname = 'KONV_WEARS';
        }
        
        var requestUrl = 'contractController.spr?action=updateAgentMtCase';
			requestUrl = requestUrl + '&materielcaseid=' + matreielcaseid;
			requestUrl = requestUrl + '&colname=' + colname;
			requestUrl = requestUrl + '&colvalue=' + colvalue;
			
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				otherds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    }
   othergrid.addListener('celldblclick', othergridcelldbclick);
    
    function callkonvLifnr(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	
    	record.set('KONV_LIFNR',returnvalue.LIFNR);
    	record.set('KONV_LIFNR_NAME',returnvalue.NAME1);
    	
    	var requestUrl = 'contractController.spr?action=updateAgentMtCase';
		requestUrl = requestUrl + '&materielcaseid=' + matreielcaseid;
		requestUrl = requestUrl + '&colname=KONV_LIFNR';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.LIFNR;
		
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				otherds.commitChanges();
			},
			failure:function(response, options){
			}
		});
		
		requestUrl = 'contractController.spr?action=updateAgentMtCase';
		requestUrl = requestUrl + '&materielcaseid=' + matreielcaseid;
		requestUrl = requestUrl + '&colname=KONV_LIFNR_NAME';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.NAME1;
		
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				otherds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    }
    
    function othergridcelldbclick(grid, rowIndex, columnIndex, e){
    	record = grid.getStore().getAt(rowIndex);
    	fieldName = grid.getColumnModel().getDataIndex(columnIndex);
    	matreielcaseid = record.get("MATERIEL_CASE_ID");

    	if (fieldName == 'KONV_LIFNR'){
    		top.ExtModalWindowUtil.show('供应商查询',
			'masterQueryController.spr?action=toFindSupplier',
			'',
			callkonvLifnr,
			{width:755,height:410});
    	}
    }   	
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'fit',
			items:[othergrid]
		}]
	});
});

function getConditionTitle(code)
{
	var sourceObj = document.getElementById('konvKschl11');
	var dropDownList = sourceObj.options;
	for(i=0;i<dropDownList.length;i++)
	{
		if(dropDownList[i].value==code)
			return dropDownList[i].text;
	}
}

function getCurrencyTitle(code)
{
	var sourceObj = document.getElementById('konvWears11');
	var dropDownList = sourceObj.options;
	for(i=0;i<dropDownList.length;i++)
	{
		if(dropDownList[i].value==code)
			return dropDownList[i].text;
	}
}

function getTitle(reourceId,code)
{
	var sourceObj = document.getElementById(reourceId);
	var dropDownList = sourceObj.options;
	for(i=0;i<dropDownList.length;i++)
	{
		if(dropDownList[i].value==code)
			return dropDownList[i].text;
	}
}
</script>