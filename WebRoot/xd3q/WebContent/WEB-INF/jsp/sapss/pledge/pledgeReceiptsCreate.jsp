<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ taglib prefix="ffcs" tagdir="/WEB-INF/tags/infolion"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增入库申请单</title>
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

var assetUserHisgrid;		//物料明细列表
var assetUserHisds;


function customCallBackHandle(transport)
{
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var sReturnMessage=customField.returnMessage;
	top.ExtModalWindowUtil.alert('提示',sReturnMessage);  
	assetUserHisds.reload();
	if ('${close}'=='false' && sReturnMessage.indexOf("提交成功")>-1)
	{	
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	}
}
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
	
	 //增加进仓单明细资料(物料)的回调函数
 function funAddReceiptDetailCallBack(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	var requestUrl = 'pledgeReceiptsController.spr?action=addMaterielInfo';
    	requestUrl = requestUrl + "&pledgeReceiptsInfoId=" + "${main.pledgeReceiptsInfoId}";
		requestUrl = requestUrl + '&EkpoMatnr=' + returnvalue.MATNR;//物料编码
		requestUrl = requestUrl + '&EkpoTxz01=' + returnvalue.MAKTX;//物料描述
		requestUrl = requestUrl + '&EkpoMeins=' + returnvalue.MEINS;//单位
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var p = new assetUserHisPlant({
				        PLEDGERECEIPTS_ITEM_ID: responseArray.pledgeReceiptsItemId,
				        PLEDGERECEIPTS_ID: responseArray.pledgeReceiptsId,
						EKPO_MATNR: responseArray.materialCode,
						EKPO_TXZ01: responseArray.materialName,
						EKPO_MEINS_D_UNIT: responseArray.materialUnit,
						BATCH_NO: responseArray.batchNo,
						QUANTITY: responseArray.quantity,
						PRICE: responseArray.price,
						TOTAL: responseArray.total,
						EKPO_PEINH:''
	                });
				assetUserHisgrid.stopEditing();
				assetUserHisds.insert(0, p);
				assetUserHisgrid.startEditing(0, 0);
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});
    }


    //增加进仓单明细资料(物料)
    var addassetUserHis = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('物料明细表',
			'pledgeReceiptsController.spr?action=toFindMaterial',
			'',
			funAddReceiptDetailCallBack,
			{width:800,height:500});
		}
   	});
   	
   	//删除物料
   	var deleteassetUserHis = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
		  if (assetUserHisgrid.selModel.hasSelection()){
				var records = assetUserHisgrid.selModel.getSelections();
				var idList = '';
				for (var i=0;i<records.length;i++){
						idList = idList + records[i].data.PLEDGERECEIPTS_ITEM_ID + ',';
					}
					top.Ext.Msg.show({
						title:'提示',
	  						msg: '是否确定删除记录',
	  						buttons: {yes:'是', no:'否'},
	  						fn: function(buttonId,text){
	  							if(buttonId=='yes')if(buttonId=='yes'){
  								var param = "?action=deleteDetail";
							   param = param + "&idList=" + idList;

							new AjaxEngine('pledgeReceiptsController.spr', 
					   			{method:"post", parameters: param, onComplete: callBackHandle});
  							}  							
	  					},
	  						icon: Ext.MessageBox.QUESTION
					});
				}
				else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的进仓单明细信息！');
			}			
		}
   	});

    var assetUserHistbar = new Ext.Toolbar({
		items:[addassetUserHis,'-',deleteassetUserHis]
	  });

	var assetUserHisPlant = Ext.data.Record.create([
		{name:'PLEDGERECEIPTS_ITEM_ID'},  			//明细ID
		{name:'PLEDGERECEIPTS_ID'},  				//进仓ID
		{name:'EKPO_MATNR'},  				//物料编码
		{name:'EKPO_TXZ01'},  				//物料名称
		{name:'MATERIAL_UNIT'},      	//物料单位
	    {name:'EKPO_PEINH'},                //每价格单位	
	    {name:'CURRENCY'},     //币别
	    {name:'QUANTITY'},                 //数量
	    {name:'PRICE'},                    //单价
	    {name:'TOTAL'},                    //总计
	    {name:'BATCH_NO'}                  //批次号
	]);

	assetUserHisds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'pledgeReceiptsController.spr?action=queryMaterial&pledgeReceiptsId=' + '${main.pledgeReceiptsInfoId}'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},assetUserHisPlant)
    });
    
    var assetUserHissm = new Ext.grid.CheckboxSelectionModel();
    
    var assetUserHiscm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		assetUserHissm,
		   {header: '物料明细ID',
           width: 80,
           sortable: true,
           dataIndex: 'PLEDGERECEIPTS_ITEM_ID',
           hidden: true
           },
           {header: '进仓ID',
           width: 80,
           sortable: true,
           dataIndex: 'PLEDGERECEIPTS_ID',
           hidden: true
           },  
		   {header: '物料编码',
           width: 80,
           sortable: true,
           dataIndex: 'EKPO_MATNR'
           },   
          {header: '物料名称',
			width: 100,
			sortable: true,
			dataIndex: 'EKPO_TXZ01'
           },
           {header: '物料单位1',
			width: 80,
			sortable: false,
			dataIndex: 'MATERIAL_UNIT'
           },		　
            {
           header: '批次号<font color="red">▲</font>',
           width: 80,
           sortable: true,
           dataIndex: 'BATCH_NO',
           editor: new fm.TextField({
               allowBlank: true,
               maxLength:10
           })
           },
           {header: '数量',
           width: 100,
           sortable: true,
           dataIndex: 'QUANTITY',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:3,
               maxValue: 10000000000,
               allowNegative:true
           })            
           },
           {
           header: '单价',
           width: 80,
           sortable: true,
           dataIndex: 'PRICE',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 1000000000
           })            
           },
            {
           header: '条件定价单位',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_PEINH',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 10000
           })            
           },
           {
           header: '总计',
           width: 80,
           sortable: true,
           dataIndex: 'TOTAL'
           },
           {header: '币别',
           width: 200,
           sortable: true,
           dataIndex: 'CURRENCY',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'ekpoBprme',
               lazyRender:true,
               listClass: 'x-combo-list-small'
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
    
    
    
  
    
    assetUserHisgrid.on("afteredit", afterUserHisEdit, assetUserHisgrid);
    
    function afterUserHisEdit(obj){
        if ('${save}'=='true') return;
    	var row = obj.record;//获取被修改的行
        var colName = obj.field;//获取被修改的列
        var pledgeReceiptsItemId = row.get("PLEDGERECEIPTS_ITEM_ID");
        var colValue = row.get(colName);
        if (colName == 'BATCH_NO'){
	        if($('warehouse').value==''){
			   Ext.MessageBox.alert('提示', '请先选择仓库');
			   row.set("BATCH_NO",'');
		       return;
			}
			updateMaterielValue(row,pledgeReceiptsItemId,colName,colValue);
        }
        //数量或单价
        else if (colName == 'QUANTITY' || colName == 'PRICE'||colName=='EKPO_PEINH'){
        	setMaterielTotal(row);
        	updateMaterielValue(row,pledgeReceiptsItemId,colName,colValue);
        }
        else {updateMaterielValue(row,pledgeReceiptsItemId,colName,colValue);}
    }
    
     //更新物料信息
    function updateMaterielValue(row,pledgeReceiptsItemId,colName,colValue){
    	var requestUrl = 'pledgeReceiptsController.spr?action=updateReceiptMateriel';
		requestUrl = requestUrl + '&pledgeReceiptsItemId=' + pledgeReceiptsItemId;
		requestUrl = requestUrl + '&colName=' + colName;
		requestUrl = requestUrl + '&colValue=' + colValue;

		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseUtil1 = new AjaxResponseUtils(response.responseText);
				var customField = responseUtil1.getCustomField("coustom");   
				var stateCode = customField.stateCode;			
					if (colName == "QUANTITY" || colName == "PRICE" || colName == "EKPO_PEINH" ){
						row.set("TOTAL",customField.total);
					}
					assetUserHisds.commitChanges();

			},
			failure:function(response, options){
			}
		});
	}    
   
       //设置金额
     function setMaterielTotal(row){   
        var pledgeReceiptsItemId = row.get("PLEDGERECEIPTS_ITEM_ID");
     	var price = row.get("PRICE");
     	var quantity = row.get("QUANTITY"); 
     	var ekpoPeinh = row.get("EKPO_PEINH");   	
     	if (quantity == null || quantity == ''){
     		quantity = "0";
     	}
     	if (price == null || price == ''){
     		price = "0";
     	}
     	if(ekpoPeinh==null||ekpoPeinh==''){
     	    ekpoPeinh="1";
     	}
     	var total = parseFloat(quantity) *  parseFloat(price)/parseFloat(ekpoPeinh);

     	row.set("TOTAL",Utils.roundoff(total,2));
     	updateMaterielValue(row,pledgeReceiptsItemId,"TOTAL",Utils.roundoff(total,2));
     }   
     
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			height:190,
			xtype:'tabpanel',
			id:'infotype',
			name:'infotype',
			plain:true,
            autoScroll:'true',
            activeTab: 0,			
           items:[{
            	title:'入库申请信息',
            	contentEl: 'div_main',
            	id:'maininfo',
				name:'maininfo',
				autoScroll:'true',
            	layout:'fit'
            },
            {
            	title:'审批历史记录',
            	contentEl: 'div_history',
            	id:'historyinfo',
				name:'historyinfo',
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
			            	title:'物料信息',
			            	contentEl: '',
			            	id:'assetUserHis',
							name:'assetUserHis',
							autoScroll:'true',
			            	layout:'fit',
			            	items:[assetUserHisgrid]
	                  },
	                 {contentEl:'rule',
			               id:'fileEl', 
			               title: '附件信息',
			               autoScroll:'true',
			               listeners:{activate:handlerActivate}
			            }]
	             }],
	             buttons:[{
		            text:'保存',
		            hidden:${save},
	            	handler:function(){
						assetUserHisgrid. getSelectionModel().selectAll();
						var recoder = assetUserHisgrid.selModel.getSelections();
						var batchStr = '';
						for (var i=0; i< recoder.length; i++) {
							var batch_no = recoder[i].data.BATCH_NO;
							var code = recoder[i].data.EKPO_MATNR;
                            var QUANTITY = recoder[i].data.QUANTITY;
                            var PRICE = recoder[i].data.PRICE;
                            var CURRENCY = recoder[i].data.CURRENCY;
                            var EKPO_PEINH = recoder[i].data.EKPO_PEINH;
							if(batch_no==null||batch_no==''){
							   top.ExtModalWindowUtil.alert('提示','请填写批次号！');
	            		       return;
	            			}
	            			if(QUANTITY==null||QUANTITY==''){
							   top.ExtModalWindowUtil.alert('提示','请填写数量！');
	            		       return;
	            			}
	            			if(EKPO_PEINH==null||EKPO_PEINH==''){
							   top.ExtModalWindowUtil.alert('提示','请填写条件定价单位！');
	            		       return;
	            			}
	            			if(PRICE=='0'){
							   top.ExtModalWindowUtil.alert('提示','请填写单价！');
	            		       return;
	            			}
	            			if(CURRENCY==null||CURRENCY==''){
							   top.ExtModalWindowUtil.alert('提示','请填写币别！');
	            		       return;
	            			}
	            			
						};

	            		var param1 = Form.serialize('mainForm');
	            		
	            		if (Ext.getDom('projectNo').value=="")
	            		{
	            			top.ExtModalWindowUtil.alert('提示','请选择立项号！');
	            			return;
	            		}	            		
	            		if (param1.indexOf("&warehouse=&")>-1)
            			{
            				Ext.MessageBox.alert('提示', '仓库必填！');
	            			return;
	            		}
	            		
	            		if (param1.indexOf("&unitName=&")>-1)
            			{
            				Ext.MessageBox.alert('提示', '销货单位必填！');
	            			return;
	            		}
	            		
	            		var param = param1 + "&action=updateReceipt";
						new AjaxEngine('pledgeReceiptsController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});

					}
 
	            },
	            {
		        	text:'提交', 
		        	hidden:${submit},         	
            		handler:function(){
		            	var param1 = Form.serialize('mainForm');
            			if (Ext.getDom('projectNo').value=="")
            			{
            				top.ExtModalWindowUtil.alert('提示','无效的立项！');
	            			return;
	            		}
	            		if (param1.indexOf("&warehouse=&")>-1)
            			{
            				Ext.MessageBox.alert('提示', '仓库必填！');
	            			return;
	            		}
	            		
	            		if (param1.indexOf("&unitName=&")>-1)
            			{
            				Ext.MessageBox.alert('提示', '销货单位必填！');
	            			return;
	            		}
	            			            		
	           			if (assetUserHisds.getCount() > 0){
							var param = param1  + "&action=submitAndSaveReceiptInfo";
							new AjaxEngine('pledgeReceiptsController.spr', 
								   {method:"post", parameters: param, onComplete: callBackHandle});													
						} else{
							top.Ext.Msg.show({
								title:'提示',
			  					msg: '没有增加物料信息,不能提交审批！',
			  					buttons: {yes:'关闭'},
			  					fn: Ext.emptyFn,
			  					icon: Ext.MessageBox.INFO
								});
						}
            		}
	            },
	            {
		        	text:'关闭',
		        	hidden:${close},
		        	handler:function(){
		        	        top.ExtModalWindowUtil.markUpdated();
		                    top.ExtModalWindowUtil.close();
		                   }
		            }]
		}]
		
	});//end fo viewPort
	//申报日期
   	var applyTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'applyTime',
	    name:'applyTime',
		width: 160,
	    readOnly:true,
		applyTo:'applyTime'
   	});
   	//进仓时间
   	var receiptTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'receiptTime',
	    name:'receiptTime',
		width: 160,
	    readOnly:true,
		applyTo:'receiptTime'
   	});
	$('warehouse').value='${main.warehouse}';
});//end of Ext.onReady   
function handlerActivate(tab){
	rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
}

function selectProjectWin(){
	top.ExtModalWindowUtil.show('查询立项信息',
	'pledgeReceiptsController.spr?action=selectProjrctInfo',
	'',
	selectProjectInfoCallBack,
	{width:560,height:300});
}
function selectProjectInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
 
	document.mainForm.projectName.value = returnvalue.PROJECT_NAME;
	document.mainForm.projectId.value = returnvalue.PROJECT_ID;
	document.mainForm.projectNo.value = returnvalue.PROJECT_NO;
	document.mainForm.unitName.value = returnvalue.PROVIDER_LINK_MAN;
}

function warehouse_chang(){
	var str =document.mainForm.warehouse.options[document.mainForm.warehouse.selectedIndex].text;
	var v = new Array();
	v = str.split('-');
	$('warehouseAdd').value=v[1];
}
</script>
</head>
<body>
<div id='div_progressBar'></div>
<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
	 <table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
     <tr>
        <td width="11%" align="right">立项号<font color="red">▲</font>：</td>
        <td width="22%" align="left">
            <input type="hidden" id="pledgeReceiptsInfoId" name="pledgeReceiptsInfoId" value="${main.pledgeReceiptsInfoId}"></input>
            <input type="hidden" id="projectId" name="projectId" value="${main.projectId}"></input>
            <input type="hidden" id="creatorTime" name="creatorTime" value="${main.creatorTime}"></input>
            <input type="hidden" id="creator" name="creator" value="${main.creator}"></input>
            <input type="hidden" id="examineState" name="creator" value="${main.examineState}"></input>
            <input type="hidden" id="isAvailable" name="isAvailable" value="${main.isAvailable}"></input>
            <input name="deptId" type="hidden" size="20" value="${main.deptId}"/>       
        	<input name="projectNo" readonly="readonly" size="20" value="${main.projectNo}"/>
        	<input type="button" value="..." onclick="selectProjectWin()"></input>      	
        </td>
        <td width="11%" align="right">进仓单编号：</td>
        <td width="22%" align="left">
			<input name="pledgeReceiptsInfoNo" readonly="readonly" type="text" size="20" tabindex="1" value="${main.pledgeReceiptsInfoNo}"/> 
        </td>
        <td width="11%" align="right">单据状态：</td>
        <td width="22%" align="left"><div id="div_examineState"></div>
        </td>
      </tr>
      
      <tr>
        <td width="11%" align="right">立项名称：</td>
        <td width="22%" align="left">
			<input name="projectName" readonly="readonly" size="20" value="${main.projectName}"/>
        </td>
        <td width="11%" align="right">销货单位<font color="red">▲</font>：</td>
        <td width="22%" align="left">
			<input type="text" name="unitName" id="unitName" size="20" value="${main.unitName}"/>
        </td>
        <td width="11%" align="right">申报日期：</td>
        <td width="22%" align="left">
			<input id="applyTime" name="applyTime" type="text"  value="${main.applyTime}"/>        
        </td>    
      </tr>
          
          <tr>
        <td align="right">仓库<font color="red">▲</font>：</td>
        <td align="left">
        	<select name="warehouse" id="warehouse" onchange="warehouse_chang()">
				<option value="">请选择</option>
				<c:forEach items="${warehouse}" var="row">
					<option value="${row.code}">${row.code}-${row.title}</option>
				</c:forEach>
			</select>
        </td>
        <td align="right">仓库地址：</td>
        <td align="left">
        	<input id="warehouseAdd" name="warehouseAdd" type="text" value="${main.warehouseAdd}"/>
        </td>
        <td width="11%" align="right">进仓时间<font color="red">▲</font>：</td>
        <td width="22%" align="left">
			<input id="receiptTime" name="receiptTime" type="text"  value="${main.receiptTime}"/>        
        </td>      
      </tr>     
      <tr>
        <td align="right">备注：</td>
        <td colspan="5">
        	 <textarea cols="80" rows="1" id="memo" name="memo" style="width:95%;overflow-y:visible;word-break:break-all;">${main.memo}</textarea>
        </td>
    </tr>
	</table>
</form>
</div>

<div id="div_history" class="x-hide-display" ></div>
<div id="rule" class="x-hide-display" >
<select name="ekpoBprme" id="ekpoBprme" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Currency}" var="row">
		<option value="${row.code}">${row.code}-${row.title}</option>
	</c:forEach>
</select>
</div>
<div id="div_userinfo"></div>


</body>
</html>
<fiscxd:dictionary divId="div_examineState" fieldName="examineState" dictionaryName="BM_EXAMINE_STATE" width="150" selectedValue="${main.examineState}" disable="true" ></fiscxd:dictionary>
<fiscxd:workflow-taskHistory divId="div_history" businessRecordId="${main.pledgeReceiptsInfoId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:fileUpload divId="rule" recordId="${main.pledgeReceiptsInfoId}" erasable="${!save}"  increasable="${!save}" 
 resourceId="2222" resourceName="33333" recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>

