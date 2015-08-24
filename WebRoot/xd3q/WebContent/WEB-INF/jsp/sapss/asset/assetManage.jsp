<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>固定资产管理</title>
<style type="text/css">
.add{
	background-image:url(images/fam/add.gif) !important;
}
.delete{
	background-image:url(images/fam/delete.gif) !important;
}
.reset{
	background-image:url(images/fam/refresh.gif) !important;
}
.find{
	background-image:url(images/fam/find.png) !important;
}
</style>
<script type="text/javascript">
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
</script>
</head>
<body>
<div id="div_northForm">
<form action="" id="findassetFrom" name="findassetFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">资产分类：</td>
	<td>
		<div id="div_assetType"></div>
	</td>
	<td align="right">业务范围：</td>
	<td>
	    <div id="div_businessScope"></div>
	</td>
	<td align="right">成本中心：</td>
	<td>
	    <input type="hidden" id="comCode" name="comCode" value="${comCode}"/>
		<input type="hidden" id="costCenter" name="costCenter" value=""/>
		<input type="text" id="costCenterName" name="costCenterName" value=""/>
		<input type="button" value="..." onclick="selectCostCenter('1');">
	</td>
	<td align="right">使用人：</td>
	<td>
		<input type="text" id="userMan" name="userMan" value=""></input>
	</td>
</tr>
<tr>
    <td align="right">资产名称：</td>
	<td>
		<input type="text" id="assetName" name="assetName" value=""></input>
	</td>
	<td align="right">型号配置：</td>
	<td>
	    <input type="text" id="assetConfig" name="assetConfig" value=""></input>
	</td>
	<td align="right">设备序列号：</td>
	<td>
		<input type="text" id="assetSerialNo" name="assetSerialNo" value=""></input>
	</td>
	<td align="right">外部编号：</td>
	<td>
		<input type="text" id="outsideNo" name="outsideNo" value=""></input>
	</td>
</tr>
<tr>
    <td align="right">存放位置：</td>
	<td>
		<input type="text" id="location" name="location" value=""></input>
	</td>
    <td align="right">购置时间从：</td>
    <td>
	   <table>
	   <tr>
		<td>
			<div id="spurchaseDateDiv"></div>
		</td>
		<td align="right">到</td>
		<td>
			<div id="epurchaseDateDiv"></div>
		</td>
	   </tr>
	  </table>
	</td>
	<td align="center">采购合同号：</td>
	 
	<td><input type="text" id="contractPuchaseNo" name="contractPuchaseNo" value=""></input></td>
	 
	<td align="right">供应商名称：</td>
	<td>
		<input type="text" id="supplierName" name="supplierName" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">SAP资产号：</td>
	<td>
		<input type="text" id="sapAssetNo" name="sapAssetNo" value=""></input>
	</td>
    <td align="right">预计报废时间从：</td>
    <td>
	   <table>
	   <tr>
		<td>
			<div id="sscrapDateDiv"></div>
		</td>
		<td align="right">到</td>
		<td>
			<div id="escrapDateDiv"></div>
		</td>
	   </tr>
	  </table>
	</td>
	<td align="center">资产使用状态：</td>
	 
	<td><div id="div_state"></div></td>
	<td align="right">资产类别：</td>
	<td>
		<div id="div_category"></div>
	</td>
<tr>
	<td colspan="8">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="查找" onclick="findAssetInfo()"></input>
		<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_center"></div>
<div id="div_AssetNotifycenter"></div>
<div id="div_south"></div>
</body>
</html>
<script type="text/javascript">
document.onkeydown = function(){if (event.keyCode == 13){findAssetInfo();}}
var category = ${category};
var assetRecord;
var assetInfogrid;		//固定资产信息列表
var assetInfods;

//asset 查找按钮的单击事件
function findAssetInfo(){
	var param = Form.serialize('findassetFrom');
	var requestUrl = 'assetController.spr?action=query&' + param;;
	assetInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	assetInfods.load({params:{start:0, limit:10},arg:[]});
}

//asset 删除固定资产数据的回调函数
function customCallBackHandle(transport){
	var responseCustomUtil = new AjaxResponseUtils(transport.responseText);
	var customField = responseCustomUtil.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	
	if (strOperType == '1'){
		assetInfods.reload();
	}
}
function funEditAssetInfoCallBack(){
	assetInfods.reload();
}
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   
    //增加
    var addassetInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			var requestUrl = '';
        top.ExtModalWindowUtil.show('增加固定资产',
			'assetController.spr?action=preAdd&category=${category}',
			'',
			funEditAssetInfoCallBack,
			{width:900,height:600});
		}	    
   	});
  	
   	//删除(无删)
   	var deleteassetInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (assetInfogrid.selModel.hasSelection()){
				var records = assetInfogrid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个固定资产信息！');
				}else{
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var url = "assetController.spr?action=delete";
								url = url + "&assetId=" + records[0].data.ASSETINFOID;

								Ext.Ajax.request({
									url: encodeURI(url),
									success: function(response, options){
										assetInfods.load();
									},
									failure:function(response, options){
									}
								});
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的固定资产信息！');
			}
		}
   	});
    
    var assetInfotbar  = new Ext.Toolbar({
			items:[addassetInfo,'-',deleteassetInfo]
    });
	
	var assetInfoPlant = Ext.data.Record.create([
			{name:'ASSETINFOID'},//外围资产号（主键）
			{name:'COMCODE'},//公司代码
			{name:'ASSETTYPE'},//资产分类
			{name:'ASSETTYPE_D_ASSET_TYPE'},//资产分类
			{name:'BUSINESSSCOPE'},//业务范围
			{name:'BUSINESSSCOPE_D_BUSINESS_SCOPE'},//业务范围
			{name:'COSTCENTERNAME'},//成本中心
			{name:'ASSETNAME'},//资产名称
			{name:'ASSETCONFIG'},//型号配置
			{name:'ASSETSERIALNO'},//设备序列号
			{name:'OUTSIDENO'},//外部编号
            {name:'SAPASSETNO'},//SAP资产号	
			{name:'CATEGORY'},//资产类别（即四大分类）	固定资产、低值易耗品和低值耐用品，在用无值资产
		    {name:'CATEGORY_D_ASSET_CATEGORY'},//资产类别（即四大分类）	固定资产、低值易耗品和低值耐用品，在用无值资产
			
			{name:'LOCATION'},//存放位置
			{name:'PURCHASEDATE'},//购置时间
			{name:'CONTRACTPUCHASENO'},//采购合同号
			{name:'COST'},//采购金额
			{name:'SUPPLIERNAME'},//供应商名称
			{name:'DEPRECIATIONYEAR'},//折旧年限
			{name:'SCRAPDATE'},//预计报废时间
			{name:'STATE_D_ASSET_STATE'}//资产使用状态 在用，拟报废，报废			                 
	]);

	assetInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'assetController.spr?action=query&category=${category}'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},assetInfoPlant)
    });
    
    var assetInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var assetInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		assetInfosm,
		   {header: '固定资产ID',
           width: 100,
           sortable: true,
           dataIndex: 'ASSETINFOID',
           hidden:true
           },                       
           {   
           header: '操作',
           width: 100,
           sortable: true,
           dataIndex: 'EXPORTINFO',
           renderer: operaRD
           },        
           {
           header: '公司代码',
           width: 70,
           sortable: true,
           dataIndex: 'COMCODE'
           },                                
           {
           header: '使用状态',
           width: 100,
           sortable: true,
           dataIndex: 'STATE_D_ASSET_STATE'
           },
           {
           header: '资产类别',
           width: 100,
           sortable: true,
           dataIndex: 'CATEGORY_D_ASSET_CATEGORY'
           },
		　 {header: '资产分类',
           width: 100,
           sortable: true,
           dataIndex: 'ASSETTYPE_D_ASSET_TYPE'
           },
		　 {header: '业务范围',
           width: 100,
           sortable: true,
           dataIndex: 'BUSINESSSCOPE_D_BUSINESS_SCOPE'
           },           
		　 {header: '成本中心',
           width: 100,
           sortable: false,
           dataIndex: 'COSTCENTERNAME'
           },
           {header: '资产名称',
           width: 100,
           sortable: true,
           dataIndex: 'ASSETNAME'
           },
           {
           header: '型号配置',
           width: 100,
           sortable: true,
           dataIndex: 'ASSETCONFIG'
           },
           {
           header: '设备序列号',
           width: 100,
           sortable: true,
           dataIndex: 'ASSETSERIALNO'
           },
           {
           header: '外部编号',
           width: 100,
           sortable: true,
           dataIndex: 'OUTSIDENO'
           },
           {
           header: 'SAP资产号',
           width: 100,
           sortable: true,
           dataIndex: 'SAPASSETNO'
           },                                
           {
           header: '存放位置',
           width: 100,
           sortable: true,
           dataIndex: 'LOCATION'
           },                                
           {
           header: '购置时间',
           width: 100,
           sortable: true,
           dataIndex: 'PURCHASEDATE'
           },                                
           {
           header: '采购合同号',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACTPUCHASENO'
           },                                
           {
           header: '采购金额',
           width: 100,
           sortable: true,
           dataIndex: 'COST'
           },                                
           {
           header: '供应商名称',
           width: 100,
           sortable: true,
           dataIndex: 'SUPPLIERNAME'
           },                                
           {
           header: '折旧年限',
           width: 100,
           sortable: true,
           dataIndex: 'DEPRECIATIONYEAR'
           },                                
           {
           header: '预计报废时间',
           width: 100,
           sortable: true,
           dataIndex: 'SCRAPDATE'
           }
    ]);
    assetInfocm.defaultSortable = true;
    
    var assetInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:assetInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    assetInfogrid = new Ext.grid.EditorGridPanel({
    	id:'assetInfoGrid',
        ds: assetInfods,
        cm: assetInfocm,
        sm: assetInfosm,
        bbar: assetInfobbar,
        tbar: assetInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    assetInfods.load({params:{start:0, limit:10},arg:[]});
    
    assetInfogrid.addListener('rowclick', assetInfogridrowclick);
    
    function assetInfogridrowclick(grid, rowIndex, e){
    	assetRecord = grid.getStore().getAt(rowIndex);
    }    
 
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: "固定资产条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:164
		},
		{
			region:"center",
			layout:'fit',
		    split:true,
			collapsible: true,
			height:220,
			minSize: 210,
            maxSize: 400,
			title: "固定资产列表",
			items:[assetInfogrid]
		}]
	});
	
	var spurchaseDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'spurchaseDate',
		name:'spurchaseDate',
		width: 90,
	    readOnly:true,
		renderTo:'spurchaseDateDiv'
   	});
   	
   	var epurchaseDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'epurchaseDate',
	    name:'epurchaseDate',
		width: 90,
	    readOnly:true,
		renderTo:'epurchaseDateDiv'
   	});
   	var sscrapDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'sscrapDate',
		name:'sscrapDate',
		width: 90,
	    readOnly:true,
		renderTo:'sscrapDateDiv'
   	});
   	
   	var escrapDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'escrapDate',
	    name:'escrapDate',
		width: 90,
	    readOnly:true,
		renderTo:'escrapDateDiv'
   	});

});

//assetManage 修改固定资产链接
function operaRD(value,metadata,record){
    return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" onclick="viewForm()">查看</a>';
}


function operaForm(assetId){
	if (assetId == null || assetId == ''){
		var records = assetInfogrid.selModel.getSelections();		
		assetId = records[0].data.ASSETINFOID;
	}

	top.ExtModalWindowUtil.show('固定资产信息',
		'assetController.spr?action=preEdit&assetInfoId='+ assetId,
		'',
		funEditAssetInfoCallBack,
		{width:900,height:550});
}

function viewForm(assetId){
	if (assetId == null || assetId == ''){
		var records = assetInfogrid.selModel.getSelections();		
		assetId = records[0].data.ASSETINFOID;
	}

	top.ExtModalWindowUtil.show('固定资产信息',
		'assetController.spr?action=addAssetInfoView&assetInfoId='+ assetId,
		'',
		funEditAssetInfoCallBack,
		{width:900,height:550});
}
</script>
<fiscxd:dictionary divId="div_assetType" fieldName="assetType" dictionaryName="BM_ASSET_TYPE" width="153"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_businessScope" fieldName="businessScope" dictionaryName="BM_BUSINESS_SCOPE" width="153"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_state" fieldName="state" dictionaryName="BM_ASSET_STATE" width="153"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_category" fieldName="category" dictionaryName="BM_ASSET_CATEGORY" width="153" selectedValue="${category }" disable="true"></fiscxd:dictionary>