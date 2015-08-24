<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>fiscxdxd title here</title>
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
<div id="div_bomInfo"></div>
<select name="entryUom" id="entryUom" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Unit}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="entryUom1" id="entryUom1" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Unit}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="plant" id="plant" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Factory}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="plant1" id="plant1" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Factory}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>

</body>
</html>
<script type="text/javascript">
var PurchaseRowsId ='${purchaserowsid}';
var sapRowNo = '${saprowno}';
var bomds;

var record;
var fieldName;
var bomid;

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	
	bomds.reload();
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var fm = Ext.form;
   	
   	var bomPlant = Ext.data.Record.create([
   		{name:'BOM_ID'},
   		{name:'PURCHASE_ROWS_ID'},
   		{name:'SAP_ROW_NO'},   		
   		{name:'MATERIEL'},
   		{name:'MATERIEL_NAME'},
   		{name:'BOM_MATERIEL_CMD'},
   		{name:'ENTRY_QUANTITY'},
   		{name:'ENTRY_UOM'},
   		{name:'PLANT_D_FACTORY'},
   		{name:'CMD'} 		   		   		
	]);
	
	bomds = new Ext.data.Store({
   		proxy : new Ext.data.HttpProxy({url:'contractController.spr?action=queryBomInfo&purchaserowsid='+PurchaseRowsId}),
   		reader: new Ext.data.JsonReader({
       	root: 'root',
       	totalProperty: 'totalProperty'
       	},bomPlant)
    });
    
    var bomsm = new Ext.grid.CheckboxSelectionModel();
    
    var bomcm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		bomsm,
			{header: 'BOMID',
           	width: 100,
           	sortable: true,
           	dataIndex: 'BOM_ID',
           	hidden:true
			},
			{header: '采购合同行项ID',
           	width: 100,
           	sortable: true,
           	dataIndex: 'PURCHASE_ROWS_ID',
           	hidden:true
			},
           {header: '物料号',
           width: 100,
           sortable: true,
           dataIndex: 'MATERIEL'
           },
           {header: '物料描述',
           width: 100,
           sortable: true,
           dataIndex: 'MATERIEL_NAME'
           },
           {header: '条目单位',
           width: 150,
           sortable: true,
           dataIndex: 'ENTRY_UOM'
/*              
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'entryUom',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })*/
           },
           {header: '物料数量',
           width: 100,
           sortable: true,
           dataIndex: 'ENTRY_QUANTITY',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:3,
               maxValue: 999999999999
           })
           },
           {header: '工厂',
           width: 100,
           sortable: true,
           dataIndex: 'PLANT_D_FACTORY',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'plant',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })
           },           
           {header: '备注',
           width: 200,
           sortable: true,
           dataIndex: 'CMD',
           editor: new fm.TextField({
               allowBlank: true
           })
           }
    ]);
    bomcm.defaultSortable = true;
    
    function MaterielcallbackFunction(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	var requestUrl = 'contractController.spr?action=addBomMaterielInfo';
		requestUrl = requestUrl + '&purchaserowsid=' + PurchaseRowsId;
		requestUrl = requestUrl + '&bommateriel=' + returnvalue.MATNR;
		requestUrl = requestUrl + '&bommaterielcmd=' + returnvalue.MAKTX;
		requestUrl = requestUrl + '&bomplant=' + returnvalue.WERKS;
		requestUrl = requestUrl + '&saprowno=${saprowno}';
		//requestUrl = requestUrl + '&ekpomeinsdunit=${ekpomeinsdunit}';
		requestUrl = requestUrl + '&ekpomeinsdunit='+returnvalue.MEINS;
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);

				var bomtp = new bomPlant({
	    			BOM_ID: responseArray.bomId,
					PURCHASE_ROWS_ID: responseArray.purchaseRowsId,
					SAP_ROW_NO: responseArray.sapRowNo,
					MATERIEL: responseArray.materiel,
			        MATERIEL_NAME: responseArray.materielName,
			        VBAP_WERKS_D_FACTORY: responseArray.vbapWerks,
					ENTRY_UOM: responseArray.entryUom,
					ENTRY_QUANTITY: responseArray.entryQuantity,
					PLANT_D_FACTORY: responseArray.plant,					
					CMD: responseArray.cmd
	             });
				bomgrid.stopEditing();
				bomds.insert(0, bomtp);
				bomgrid.startEditing(0, 0);
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});
    }
    
    var addBom = new Ext.Toolbar.Button({
   		text:'增加',
   		iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('物料信息',
			'masterQueryController.spr?action=toFindMaterial',
			'',
			MaterielcallbackFunction,
			{width:755,height:478});
		}
   	});

   	var deleteBom = new Ext.Toolbar.Button({
   		text:'删除',
   		iconCls:'delete',
		handler:function(){
			if (bomgrid.selModel.hasSelection()){
				var records = bomgrid.selModel.getSelections();
				var idList = '';
				for (var i=0;i<records.length;i++){
					idList = idList + records[i].data.BOM_ID + '|';
				}
				
				top.Ext.Msg.show({
					title:'提示',
  						msg: '是否确定删除记录',
  						buttons: {yes:'是', no:'否'},
  						fn: function(buttonId,text){
  							if(buttonId=='yes'){
  								var param = "?action=deletePurchaseBom";
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
   	
   	var closeBom = new Ext.Toolbar.Button({
   		text:'关闭',
   		iconCls:'close',
		handler:function(){
			top.ExtModalWindowUtil.close();
		}
   	});
   	
   	var bomtbar = new Ext.Toolbar({
		items:[addBom,'-',deleteBom,'-',closeBom]
	});
    
    var bombbar = new Ext.PagingToolbar({
        pageSize: 50,
        store:bomds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    var bomgrid = new Ext.grid.EditorGridPanel({
    	id:'bomGrid',
        ds: bomds,
        cm: bomcm,
        sm: bomsm,
        tbar: bomtbar,
        bbar: bombbar,
        border:false,
        loadMask:true,
        clicksToEdit:1,
        autoScroll:true,
        el: 'div_bomInfo',
        layout:"fit"
    });

   	bomds.load({params:{start:0, limit:10}});
   	
   	bomgrid.on("afteredit", afterEdit, bomgrid);
    
    function afterEdit(obj){
    	var row = obj.record;//获取被修改的行
        var colname = obj.field;//获取被修改的列
        var bomid = row.get("BOM_ID");
        var colvalue = row.get(colname);

        if (colname == 'PLANT_D_FACTORY'){
        	row.set(colname,getTitle('plant1',colvalue));
        	colname = 'PLANT';
        }
                
        var requestUrl = 'contractController.spr?action=updateBomInfo';
			requestUrl = requestUrl + '&bomid=' + bomid;
			requestUrl = requestUrl + '&colname=' + colname;
			requestUrl = requestUrl + '&colvalue=' + colvalue;
			
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				bomds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    }
    
    bomgrid.addListener('celldblclick', bomgridcelldbclick);
    
    function updateMaterielcallbackFunction(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	
    	record.set('BOM_MATERIEL',returnvalue.MATNR);
    	record.set('BOM_MATERIEL_CMD',returnvalue.MAKTX);
    	
    	var requestUrl = 'contractController.spr?action=updateBomInfo';
		requestUrl = requestUrl + '&bomid=' + bomid;
		requestUrl = requestUrl + '&colname=BOM_MATERIEL ';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.MATNR;
		
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				bomds.commitChanges();
			},
			failure:function(response, options){
			}
		});
		
		requestUrl = 'contractController.spr?action=updateBomInfo';
		requestUrl = requestUrl + '&bomid=' + bomid;
		requestUrl = requestUrl + '&colname=BOM_MATERIEL_CMD';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.MAKTX;
		
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				bomds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    }
    
    function bomgridcelldbclick(grid, rowIndex, columnIndex, e){
    	record = grid.getStore().getAt(rowIndex);
    	fieldName = grid.getColumnModel().getDataIndex(columnIndex);
    	bomid = record.get("BOM_ID");
    	
    	if (fieldName == 'MATERIEL'){
    		top.ExtModalWindowUtil.show('物料信息',
			'masterQueryController.spr?action=toFindMaterial',
			'',
			updateMaterielcallbackFunction,
			{width:755,height:478});
    	}
    }
   	
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'fit',
			items:[bomgrid]
		}]
	});
});
function getTitle(sourceid,code)
{
	var sourceObj = document.getElementById(sourceid);
	var dropDownList = sourceObj.options;
	for(i=0;i<dropDownList.length;i++)
	{
		if(dropDownList[i].value==code)
			return dropDownList[i].text;
	}
}
</script>