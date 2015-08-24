<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出单审单</title>
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


//贸易类型
var tradeType ='${tradeType}';

//贸易名称
var strshipTitle ='';

var Enabled='false';
if ('${submit}'!='true')Enabled='submit';
if ('${save}'!='true')Enabled='save';

//取贸易名称
strshipTitle = Utils.getTradeTypeTitle(tradeType);

//document.title = strshipTitle + "货物出仓单" ;

var exportApplyInfogrid;
var exportApplyInfods;


function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);

	
		top.ExtModalWindowUtil.markUpdated();
	    top.ExtModalWindowUtil.close();
	
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';

   	//承兑到期日
   	var maturity = new Ext.form.DateField({
   		format:'Ymd',
		name:"maturity",
		id:"maturity",
		width: 140,
		applyTo:'maturity'
   	});
   	//押汇到期日
   	var negotiatDate = new Ext.form.DateField({
   		format:'Ymd',
		name:"negotiatDate",
		id:"negotiatDate",
		width: 140,
		applyTo:'negotiatDate'
   	});

   	//期限
   	/*var timeLimit = new Ext.form.DateField({
   		format:'Ymd',
		name:"timeLimit",
		id:"timeLimit",
		width: 140,
		applyTo:'timeLimit'
   	});*/
   	
   	

    var deliveryDate = new Ext.form.DateField({
   		format:'Ymd',
		name:"deliveryDate",
		id:"deliveryDate",
		width: 140,
		applyTo:'deliveryDate'
   	});

    var billDate = new Ext.form.DateField({
   		format:'Ymd',
		name:"billDate",
		id:"billDate",
		width: 140,
		applyTo:'billDate'
   	});
  
   	
   	var billShipDate = new Ext.form.DateField({
   		format:'Ymd',
		name:"billShipDate",
		id:"billShipDate",
		width: 140,
		applyTo:'billShipDate'
   	});
   	
   	var shouldIncomeDate = new Ext.form.DateField({
   		format:'Ymd',
		name:"shouldIncomeDate",
		id:"shouldIncomeDate",
		width: 140,
		readOnly:true,
		applyTo:'shouldIncomeDate'
   	});
   	
   	var examDate = new Ext.form.DateField({
   		format:'Ymd',
		name:"examDate",
		id:"examDate",
		width: 140,
		applyTo:'examDate'
   	});
   	
   	var currency_combo = new Ext.form.ComboBox({
        typeAhead: true,
        triggerAction: 'all',
        transform:'currency',
        width:150,
        allowBlank:false,
        blankText:'请输入货币',
        forceSelection:true
     });
     currency_combo.setValue('${main.currency}'); 
    //增加收款单号
   //  document.getElementById('test_text').value='${main.collectNo}';
   //  document.getElementById('test').value='${main.collectId}';
     /**/
    
	
   	var exportApplyInfoPlant = Ext.data.Record.create([
		{name:'EXPORT_BILLD_ID'},			 
		{name:'EXPORT_BILL_EXAM_ID'},	
		{name:'EXPORT_APPLY_ID'},	 
		{name:'NOTICE_NO'},
		{name:'CONTRACT_NO'},
		{name:'WRITE_NO'},
		{name:'LCDPDA_NO'},
		{name:'EXPORT_APPLY_INFO'}				 
	]);

	exportApplyInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'exportController.spr?action=queryExportApply&exportExamId=${main.exportBillExamId}'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},exportApplyInfoPlant)
    });
    exportApplyInfods.load();

    var exportApplyInfosm = new Ext.grid.CheckboxSelectionModel();
	var exportApplyInfocm = new Ext.grid.ColumnModel([
	new Ext.grid.RowNumberer(),
	exportApplyInfosm,
		{
			header: '',
			width: 100,
			sortable: true,
			dataIndex: 'EXPORT_BILLD_ID',
			hidden:true
		},
		{
			header: '',
			width: 100,
			sortable: true,
			dataIndex: 'EXPORT_APPLY_ID',
			hidden:true
		},
		{
			header: '',
			width: 100,
			sortable: true,
			dataIndex: 'EXPORT_BILL_EXAM_ID',
			hidden:true
		},
		{
			header: '通知单号码',
			width: 100,
			sortable: true,
			dataIndex: 'NOTICE_NO'
		},
		{
			header: '合同号',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_NO'
		},
		{
			header: '核销单号',
			width: 100,
			sortable: true,
			dataIndex: 'WRITE_NO'
		},
		{
			header: 'L/C NO.,D/P,DA',
			width: 100,
			sortable: true,
			dataIndex: 'LCDPDA_NO'
		},
		{
			header: '通知单详情',
			width: 100,
			sortable: true,
			dataIndex: 'EXPORT_APPLY_INFO',
			renderer:contractOper
		}
	]);
	function selectExportApplyWin(){
		if (Enabled=='false')return;	
		top.ExtModalWindowUtil.show('查询所属登陆员工部门的出口货物通知单信息',
		'shipController.spr?action=selectExportApply&tradeType=' + '${tradeType}' +'&examineState=3&deptid=',
		'',
		selectExportApplyCallBack,
		{width:900,height:450});
	}

   function selectExportApplyCallBack(){
    var returnValues = top.ExtModalWindowUtil.getReturnValue();
	if (returnValues != null) {
				var param = "exportController.spr?action=saveBillExamD&billExamId=${main.exportBillExamId}";
				param+="&noticeNo="+returnValues.NOTICE_NO+"&contractNo="+returnValues.SALES_NO;
				param+="&lcdpdaNo="+returnValues.LCNO+"&writeNo="+returnValues.WRITE_NO;
				param+="&exportApplyId="+returnValues.EXPORT_APPLY_ID;
				Ext.Ajax.request({
					url: encodeURI(param),
					success: function(response, options){
					       exportApplyInfods.load();
					},
				    failure:function(response, options){
					     //Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
				    }
		        });
	 }
}
    //付款合同相关信息
  var addExportStatInfo1 = new Ext.Toolbar.Button({
   		text:'增加出口货物通知单',
	    iconCls:'add',
	    hidden:${isShow},
		handler:selectExportApplyWin
   	});
   	var delExportStatInfo1 = new Ext.Toolbar.Button({
   		text:'删除出口货物通知单',
	    iconCls:'delete',
	    hidden:${isShow},
	    handler:function(){
			if (exportApplyInfogrid.selModel.hasSelection()){
				var records = exportApplyInfogrid.selModel.getSelections();
				if(records.length>1){
					top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
				}else{
                var exportExamBillDId = records[0].data.EXPORT_BILLD_ID;
				alert(records[0].data);
				top.Ext.Msg.show({
					title:'提示',
  						msg: '是否确定删除记录',
  						buttons: {yes:'是', no:'否'},
  						fn: function(buttonId,text){
  							if(buttonId=='yes'){
  			            		var param = "exportController.spr?action=deleteBillExamD&billExamDId="+exportExamBillDId;
  			            		Ext.Ajax.request({
									url: encodeURI(param),
									success: function(response, options){
									       exportApplyInfods.load();
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
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的物料信息！');
			}
		}
   	});
    var exportApplyInfotbar = new Ext.Toolbar({
		items:[addExportStatInfo1,'-',delExportStatInfo1]
	});
	
	var exportApplyInfopbar = new Ext.PagingToolbar({
        pageSize: 5,
        store:exportApplyInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
	
	exportApplyInfogrid = new Ext.grid.EditorGridPanel({
    	id:'exportApplyInfogrid',
        ds: exportApplyInfods,
        cm: exportApplyInfocm,
		sm: exportApplyInfosm,
		tbar: exportApplyInfotbar,
		bbar: exportApplyInfopbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        autowidth:true,
		height:150,
        layout:'fit'
    });
     /**/
     
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			collapsible: true,
			height:150,
			items:[exportApplyInfogrid]
		},
		{
			region:"center",
			collapsible: true,
			height:450,
			autoScroll:'true',
			contentEl: 'div_main',
		/*清帐上线前先关掉
		}
		,{
			region:"south",
			layout:'fit',
			height:200,
			autoScroll:'true',
			items:[{
				region:"center",
				layout:'fit',
				contentEl:'div_center'
			}],*/
			buttonAlign:'center',
			buttons:[{
	            	text:'保存',
	            	hidden:${isShow},
	            	handler:function(){
	            	    var materialOrg=dict_div_ymatGroup.getActualValue();			
						if(materialOrg==""){
							top.ExtModalWindowUtil.alert('提示','请选择物料组！');
							return;
						}
						
	            		var collectno = div4_sh.getValue();	            		
    					document.getElementById('collectNo').value= collectno;
    					var collectid = div4_sh.getJsonValue().COLLECTID;
    					if( typeof collectid == 'undefined')collectid='';	    					
	            		document.getElementById('collectId').value=collectid;
    					//alert(collectid);
	            		var param1 = Form.serialize('mainForm');
	            		if (param1.indexOf("&exportApplyNo=&")>-1)
            			{
            				Ext.MessageBox.alert('提示', '通知单号必填！');
	            			return;
	            		}
	            		if (param1.indexOf("&shouldIncomeDate=&")>-1)
            			{
            				Ext.MessageBox.alert('提示', '应收汇日必填！');
	            			return;
	            		}
	            		if (param1.indexOf("&billShipDate=&")>-1)
            			{
            				Ext.MessageBox.alert('提示', '提单装船日必填！');
	            			return;
	            		}
	            		if (param1.indexOf("&examDate=&")>-1)
            			{
            				Ext.MessageBox.alert('提示', '出单日期必填！');
	            			return;
	            		}
	            		if (param1.indexOf("&billDate=&")>-1)
            			{
            				Ext.MessageBox.alert('提示', '接单日期必填！');
	            			return;
	            		}
	            		if (param1.indexOf("&invNo=&")>-1)
            			{
            				Ext.MessageBox.alert('提示', '发票号必填！');
	            			return;
	            		}
	            		
	            		if (document.getElementById('billType').value == "LC" && document.getElementById('deliveryDate').value == '') {
	            			Ext.MessageBox.alert('提示', '最迟交单日必填！');
	            			return;
	            		}
	            		
	            		if (document.getElementById('billType').value == "TT" && collectno != ''){
	            			Ext.MessageBox.alert('提示', '单据类型为TT,不能填写收款单号！');
	            			return;
	            		}
	            		if (param1.indexOf("&billType=&")>-1)
            			{
            				Ext.MessageBox.alert('提示', '单据类型必填！');
	            			return;
	            		}
	            		if (param1.indexOf("&currency=&")>-1)
            			{
            				Ext.MessageBox.alert('提示', '币别必填！');
	            			return;
	            		}
	            		if (param1.indexOf("&total=&")>-1)
            			{
            				Ext.MessageBox.alert('提示', '金额必填！');
	            			return;
	            		}
	            		
	            		var oldCollectId = document.getElementById('oldCollectId').value;
						var param = param1 + "&action=updateExportBillExam&deptId="+selectId_dept + "&oldCollectId=" + oldCollectId;
						new AjaxEngine('exportController.spr', 
						   {method:"post", parameters: param, onComplete: callBackHandle});
					}

	            },{
            	text:'关闭',
            	handler:function(){
            	    _getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
            	    top.ExtModalWindowUtil.markUpdated();
	                top.ExtModalWindowUtil.close();
	            }
            }]
		}]
	});//end fo viewPort
	
//	document.getElementById("test_text").style.width='120px';
	Ext.fly('test_text').setStyle("width","150px");
	
	div4_sh.defaultCondition = "BUSINESSSTATE='3' and DEPT_ID='" + selectId_dept + "'";
	
	div4_sh.on('beforeclick',function(o){
		var t2 = document.getElementById('billType').value;		
		var t="=''";
		if(t2!=''){		  
		  if(t2 == 'LC')t=" in('03','04')";
		  if(t2 == 'TT')t="='01'";
		  if(t2 == 'DP')t="='02'";
		  if(t2 == 'DA')t="='05'";
		}		
		//alert(selectId_dept);alert(selectText_dept);
		//alert("BUSINESSSTATE='3' and DEPT_ID='" + selectId_dept + "'" + " and COLLECTTYPE" + t );
		div4_sh.defaultCondition = "BUSINESSSTATE='3' and DEPT_ID='" + selectId_dept + "'" + " and COLLECTTYPE" + t;
		
	});
	
});//end of Ext.onReady   

//提示窗口
function showMsg(msg){
	top.Ext.Msg.show({
			title:'提示',
			closable:false,
			msg:msg,
			buttons:{yes:'关闭'},
			fn:Ext.emptyFn,
			icon:Ext.MessageBox.INFO
	});
}
 function contractOper(value,metadata,record){
 	return '<a href="#" onclick="viewExportApplyForm()">查看</a>';
 }
 
  function viewExportApplyForm(){
         var records =exportApplyInfogrid.selModel.getSelections();
      var exportApplyId = records[0].data.EXPORT_APPLY_ID;
      if(exportApplyId=='') {
         showMsg('请选择通知单信息！');
         return ;
      }
top.ExtModalWindowUtil.show('出口货物通知单信息','shipNotifyController.spr?action=addShipNotifyView&exportApplyId='+ exportApplyId + '&operType=00','','',{width:900,height:500});
  }  
//关闭窗体
function closeForm(){
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
	return;
	
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否退出并关闭窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
 			if(buttonId=='yes'){
				top.ExtModalWindowUtil.markUpdated();
				top.ExtModalWindowUtil.close();
			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}

//清空窗口
function resetForm(form){
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否清空窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
   				form.reset()
   			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}

//查看收款单
function showCollect(){
	var collectno = div4_sh.getValue();	
	var collectid = div4_sh.getJsonValue().COLLECTID;
		
	if(typeof collectid == 'undefined')collectid='';
	if(collectid==""){
		top.Ext.MessageBox.alert('提示', '请先选择收款单号！');
	}else{
		_getMainFrame().maintabs.addPanel('收款单信息','', 'xdss3/collect/collectController.spr?action=_view&collectid='+ collectid);
	}
}
</script>
</head>
<body>
<div id="div_exportApplyInfogrid"></div>
<div id='div_progressBar'></div>
<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
      	<input type="hidden" id = "tradeType" name = "tradeType" value="${tradeType}"/>
      	<input type="hidden" name="exportBillExamId" value="${main.exportBillExamId }"/> 
		<input type="hidden" name="creatorTime" value="${main.creatorTime }"/> 
		<input type="hidden" name="creator" value="${main.creator }"/> 
		<input type="hidden" name="creatorDept" value="${main.creatorDept }"/>
		<input type="hidden" id="collectId" name="collectId" value="${main.collectId }"/>
		<input type="hidden" id="collectNo" name="collectNo" value="${main.collectNo }"/>
		<input type="hidden" id="oldCollectId" name="oldCollectId" value="${main.collectId }"/>
	 <table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
      <tr>
        
        <td width="11%" align="right">发票号<font color="red">▲</font></td>
        <td width="22%" align="left">
            <input id="invNo" name="invNo"  value="${main.invNo }"/>
        </td>
        <td width="11%" align="right">单据类型<font color="red">▲</font></td>
        <td width="22%" align="left">
        	<select name="billType">
	       <option value="">请选择</option>
	       <option value="LC">LC</option>
	       <option value="DP">DP</option>
	       <option value="DA">DA</option>
	       <option value="TT">TT</option>
	    </select>
        </td>
        <td align="right">接单日期<font color="red">▲</font>：</td>
        <td align="left">
        	<input type="text" name="billDate" value="${main.billDate }" readonly="readonly"/>
        </td>
      </tr>
      <script>
         document.getElementById('billType').value='${main.billType}'
      </script>
      <tr>
        
        <td align="right">金额<font color="red">▲</font>：</td>
        <td align="left">
        	<input id="total" name="total" type="text" value="${main.total}"/>
        </td>
        <td align="right">币别<font color="red">▲</font>：</td>
        <td align="left">
        	<div id="div_currency">
			<select name="currency" id="currency">
				<option value="">请选择</option>
				<c:forEach items="${Currency}" var="row">
					<option value="${row.code}">${row.code}-${row.title}</option>
				</c:forEach>
			</select>
			</div>
        </td>
        <td align="right">承兑到期日：</td>
        <td align="left">
        	<input type="text" name="maturity" value="${main.maturity }" readonly="readonly"/>
        </td>
      </tr>
      <tr>
        <td align="right">出单日期<font color="red">▲</font>：</td>
        <td  align="left">
        	<input id="examDate" name="examDate" type="text" value="${main.examDate}" readonly="readonly"/>
        </td>
        <td align="right">议付银行：</td>
        <td align="left">
        	<input id="bank" name="bank" type="text" value="${main.bank}"/>
        </td>
        <td align="right">是否押汇：</td>
        <td align="left">
            <select name="isNegotiat">
               <option value="否">否</option>
               <option value="是">是</option>
            </select>
        </td>
      </tr>
      <script>
         document.getElementById('isNegotiat').value='${main.isNegotiat}'
      </script>
      <tr>
        <td align="right">押汇到期日：</td>
        <td align="left">
        	<input id="negotiatDate" name="negotiatDate" type="text" value="${main.negotiatDate}"/>
        </td>      
        <td align="right">提单装船日<font color="red">▲</font>：</td>
        <td align="left">
        	<input id="billShipDate" name="billShipDate" type="text" value="${main.billShipDate}" readonly="readonly"/>
        </td>
        <td align="right">应收汇日<font color="red">▲</font>：</td>
        <td align="left">
        	<input id="shouldIncomeDate" name="shouldIncomeDate" type="text" value="${main.shouldIncomeDate}" readonly="readonly"/>
        </td>
      </tr>
      <tr>
        <td align="right"><nobr>单据所属部门<font color="red">▲</font></nobr></td>
        <td align="left"><div id="dept"></div>
        </td>
        <td align="right">货物品名：</td>
        <td align="left">
        	<input id="goods" name="goods" type="text" value="${main.goods}"/>
        </td>
        <td align="right">期限<font color="red">▲</font>：</td>
        <td align="left">
        	<input id="timeLimit" name="timeLimit" type="text" value="${main.timeLimit}"/>
        </td>
      </tr>
      <tr>
      		<td align="right">最迟交单日期：</td>
        	<td align="left" >
        	<input id="deliveryDate" name="deliveryDate" type="text" value="${main.deliveryDate}" readonly="readonly"/>
        	</td>
        	<td align="right">	
				物料组<font color="red">▲</font>
			</td>
			<td  >	
				<div id="div_ymatGroup"></div>
			</td>
			<td align="right" >收款单号:			
			</td>
			<td >				
				<div id="div4" style=" float:left;"></div>					
				<input style="float:left;" id="show" type="button" value="..." onclick="showCollect();"/>
				
			</td>
			
      </tr>
      <tr>
        <td align="right">审单记录：</td>
        <td  align="left" colspan="5">
        	<textarea name="examRecord" rows="7" cols="85">${main.examRecord }</textarea>
        </td>
      </tr>
       <tr>
        <td align="right">备注：</td>
        <td  align="left" colspan="5">
        	<textarea name="mark" rows="5" cols="85">${main.mark }</textarea>
        </td>
      </tr>
      <!-- 清帐上线前先关掉 
      <tr>
        <td align="right">清帐标志：</td>
        <td  align="left" colspan="5">
        	<input id="isClear" name="isClear" type="checkbox" value="${main.isClear}"/>
        </td>
      </tr>
      <script>
      	 var isClear = '${main.billType}';
      	 if (isClear == "Y"){
      		document.getElementById('isClear').checked = true;
          	 }  else {
           		document.getElementById('isClear').checked = false;              	 
          	 }
      </script>-->
	</table>
</form>
</div>
<div id="div_center"></div>
<div id="history" class="x-hide-display" ></div>
<fiscxd:dept divId="dept" rootTitle="单据所属部门" width="155" isUseDiv="true"></fiscxd:dept>
<!--<fisc:grid divId="div_center" boName="ExportCcollect"  editable="true" needCheckBox="false" needToolbar="false" needAutoLoad="true"></fisc:grid>-->
<script>
selectId_dept = '${selectId_dept}'
dict_dept.setValue('${selectText_dept}');
</script>
</body>
</html>
    
<fisc:searchHelp boName=""  boProperty="" searchType="field" searchHelpName="YHCOLLECTNO2"  displayField="COLLECTNO" valueField="COLLECTNO" hiddenName="test" divId="div4" value="${main.collectNo}"></fisc:searchHelp>

<fiscxd:dictionary divId="div_ymatGroup" fieldName="ymatGroup" dictionaryName="YMATGROUP" width="150" selectedValue="${main.ymatGroup}" disable="false" ></fiscxd:dictionary>
    