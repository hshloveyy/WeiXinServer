<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内部划拨申请页面</title>
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
<script type="text/javascript">
var re_transferID = '${transferID}'
var from = '<%=request.getAttribute("popfrom")%>';
var tempTab={};
Ext.onReady(function(){
	var fm = Ext.form;
	//Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

//收款付款信息
var detailPlant = Ext.data.Record.create([
   		{name:'DETAIL_ID'},
		{name:'TRANSFER_ID'},
		{name:'PAY_BANK'}, 
		{name:'PAY_ACCOUNT'},    		   		   		
		{name:'RECEIVE_BANK'},
		{name:'RECEIVE_ACCOUNT'},
		{name:'SUM'}
	]);

detailds = new Ext.data.Store({
   		proxy : new Ext.data.HttpProxy({url:'innerTransferController.spr?action=findDetail&transferID='+re_transferID}),
   		reader: new Ext.data.JsonReader({
       	root: 'root',
       	totalProperty: 'totalProperty'
       	},detailPlant)
    });
 var detailsm = new Ext.grid.CheckboxSelectionModel();
 var detailcm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		detailsm,
			{header: '明细ID',
           	width: 10,
           	sortable: true,
           	dataIndex: 'DETAIL_ID',
           	hidden:true
			},
			{header: '内部划拨ID',
           	width: 10,
           	sortable: true,
           	dataIndex: 'TRANSFER_ID',
           	hidden:true
			},
			{header: '付款开户行',
           	width: 80,
           	sortable: true,
           	dataIndex: 'PAY_BANK',
			editor: new fm.TextField({
               allowBlank: false
            })
			},			
            {header: '付款帐户',
            width: 100,
            sortable: true,
            dataIndex: 'PAY_ACCOUNT',
			editor: new fm.TextField({
               allowBlank: false
           })
           },
           {header: '收款开户行',
           width: 80,
           sortable: true,
           dataIndex: 'RECEIVE_BANK',
		   editor: new fm.TextField({
               allowBlank: false
           })       
           },
           {header: '收款帐户',
           width:100,
           sortable: true,
           dataIndex: 'RECEIVE_ACCOUNT',
		   editor: new fm.TextField({
               allowBlank: false
           })
           },
		   {header: '金额',
           width:30,
           sortable: true,
           dataIndex: 'SUM',
		   editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 999999999999
           })
           }
    ]);
    detailcm.defaultSortable = true;
 var addDetail = new Ext.Toolbar.Button({
   		text:'增加',
   		iconCls:'add',
		handler:function(){
			var requestUrl = 'innerTransferController.spr?action=createDetail&transferID='+re_transferID;
			Ext.Ajax.request({
				url: requestUrl,
				success: function(response, options){
					var responseArray = Ext.util.JSON.decode(response.responseText);

					var detailtp = new detailPlant({
			    			DETAIL_ID: responseArray.detailID,
							TRANSFER_ID: responseArray.transferID,
							PAY_BANK: responseArray.payBank,
							PAY_ACCOUNT: responseArray.payAccount,
							RECEIVE_BANK: responseArray.receiveBank,
							RECEIVE_ACCOUNT: responseArray.receiveAccount,
							SUM: responseArray.sum
			             });
					detailgrid.stopEditing();
					detailds.insert(0, detailtp);
					detailgrid.startEditing(0, 0);
				},
				failure:function(response, options){
					//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
				}
			});
		}
 });
var deleteDetail = new Ext.Toolbar.Button({
   		text:'删除',
   		iconCls:'delete',
		handler:function(){
			if (detailgrid.selModel.hasSelection()){
				var records = detailgrid.selModel.getSelections();
				var idList = '';
				for (var i=0;i<records.length;i++){
					idList = idList + records[i].data.DETAIL_ID + '|';
				}
				
				top.Ext.Msg.show({
					title:'提示',
  						msg: '是否确定删除记录',
  						buttons: {yes:'是', no:'否'},
  						fn: function(buttonId,text){
  							if(buttonId=='yes'){
  								var param = "?action=deleteDetail";
								param = param + "&transferID="+ re_transferID + "&idList=" + idList;

								new AjaxEngine('innerTransferController.spr', 
					   				{method:"post", parameters: param, onComplete: callBackHandle});
  							}
							strOperType = '2';
  						},
  						icon: Ext.MessageBox.QUESTION
				});
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的明细信息！');
			}
		}
   	});
var detailtbar = new Ext.Toolbar({
		items:[addDetail,'-',deleteDetail]
	});
var detailbbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:detailds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
detailgrid = new Ext.grid.EditorGridPanel({
    	id:'detailGrid',
    	//title:'收付款明细信息',
        ds: detailds,
        cm: detailcm,
        sm: detailsm,
        tbar: detailtbar,
        bbar: detailbbar,
        border:false,
        loadMask:true,
        autoScroll:true,
		region:'south',
        el: 'div_detail',
        autowidth:true,
		layout:'fit',
		viewConfig : {  
                forceFit : true,  
                enableRowBody:true,
                getRowClass : 
                	function(record,rowIndex,rowParams,store){ 
                		if(rowIndex%2==0)
                			return 'x-grid-row-bgcolor'; 
                		else
                			return '';	
                	}  
        }//,       
       // height:200,
       // width:850
    });
    
    //detailgrid.render();
detailds.load({params:{start:0, limit:10}});  
detailgrid.on("afteredit", afterEdit, detailgrid);
 function afterEdit(obj){
    	var row = obj.record;//获取被修改的行
		var detailID = row.get("DETAIL_ID");
        var colName = obj.field;//获取被修改的列
		var colValue = row.get(colName);
        var requestUrl = 'innerTransferController.spr?action=saveDetail';
			requestUrl = requestUrl + '&detailID=' + detailID;
			requestUrl = requestUrl + '&colName=' + colName;
			requestUrl = requestUrl + '&colValue=' + colValue;
			
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				detailds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    }

var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			collapsible: true,
			height:150,
			items:[{contentEl:'div_main'}]
		},{
			region:"center",
			layout:'fit',
			title:'收付款明细信息',
			items:[detailgrid]
		},{
			region:"south",
			layout:'fit',
			buttonAlign:'center',
			buttons:[{
            	text:'保存',
				hidden: ${!save},
            	handler:function(){
            		var param1 = Form.serialize('mainForm');
					var param = param1 + "&action=save";
					new AjaxEngine('innerTransferController.spr', 
						   {method:"post", parameters: param, onComplete: callBackHandle});
            	}
            },{
            	text:'提交',
            	hidden: ${!submit},
            	handler:function(){
            		strOperType = '3';
             			if (detailds.getCount() > 0){
		            		var param1 = Form.serialize('mainForm');
							var param = param1 + "&action=submitTransferInfo";
							new AjaxEngine('innerTransferController.spr', 
								   {method:"post", parameters: param, onComplete: callBackHandle});
						} else{
							top.Ext.Msg.show({
								title:'提示',
			  					msg: '没有增加收付款明细信息,请确认是否真的要提交审批?',
			  					buttons: {yes:'是', no:'否'},
			  					fn: function(buttonId,text){
			  						if(buttonId=='yes'){
			  							var param1 = Form.serialize('mainForm');
										var param = param1 + "&" + param2 + "&action=submitTransferInfo";
										new AjaxEngine('innerTransferController.spr', 
								   			{method:"post", parameters: param, onComplete: callBackHandle});
			  						}
			  					},
			  					icon: Ext.MessageBox.QUESTION
							});
						}  
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
	});
});//end of Ext.onReady    


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

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
//	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	showMsg(customField.returnMessage);
	if(strOperType == '2')
		detailds.reload();
	else
		setTimeout(function(){
			_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
			top.ExtModalWindowUtil.markUpdated();
			top.ExtModalWindowUtil.close();
		},1000);	
}

</script>
</head>
<body>
<div id='progressBar'></div>
<div id="div_main">
<form id="mainForm">
<table width="550" align="center" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td width="35%" colspan="2" align="right">申请单位</td>
        <td width="65%" align="left"><input name="orgName" type="text" size="40" tabindex="1" value="${main.orgName}" readonly="readonly"/><input type="hidden" name="orgID" value="${main.orgID}"/></td>
      </tr>
      <tr>
        <td colspan="2" align="right">申请人</td>
        <td>
        	<input name="creatorName" type="text" size="40" tabindex="2" value="${main.creatorName}" readonly="readonly"/>
        	<input type="hidden" name="creatorID" value="${main.creatorID}"/>
        </td>
      </tr>
	  <tr>
        <td colspan="2" align="right">付款人<font color="red">▲</font></td>
        <td>
        	<input name="payer" type="text" size="40" tabindex="2" value="${main.payer}" maxlength="25"/>
        </td>
      </tr>
	  <tr>
        <td colspan="2" align="right">收款人<font color="red">▲</font></td>
        <td>
        	<input name="receiver" type="text" size="40" value="${main.receiver}"/>
        </td>
      </tr>
	  <tr>
        <td colspan="2" align="right">付款方式<font color="red">▲</font></td>
        <td>
        	<input name="payType" type="text" size="40" value="${main.payType}"/>
				<input type="hidden" name="transferID" value="${main.transferID}" />
				<input type="hidden" name="applyTime" value="${main.applyTime}" />
				<input type="hidden" name="approveState" value="${main.approveState}" />
				<input type="hidden" name="createTime" value="${main.createTime}" />
				<input type="hidden" name="isAvailable" value="${main.isAvailable}" />
        </td>
      </tr>
  		<!--tr>
  			<td width="34%" align="right">
  			    <input type="button" value="保 存" onClick="saveMainForm(this)" class="btn" id="savefr" />
  			</td>
  			<td width="1%" >
  				<input type="hidden" name="transferID" value="${main.transferID}" />
				<input type="hidden" name="applyTime" value="${main.applyTime}" />
				<input type="hidden" name="approveState" value="${main.approveState}" />
				<input type="hidden" name="createTime" value="${main.createTime}" />
				<input type="hidden" name="isAvailable" value="${main.isAvailable}" />
  			</td>
  			 <td  width="65%" align="left">
  			    <input type="button" value="提 交" onClick="submitWorkflow()" class="btn" id="submitfr"/>
    			<input type="button" value="关闭窗口" onClick="closeForm()" class="btn" id="closefr"/>
  			</td>
  		</tr-->
</table>
<input type="hidden" name="taskId" value="${taskId}">
</form>
</div>
<div id="div_detail"></div>
<div id="history" class="x-hide-display" ></div>
</body>
</html>
<fiscxd:workflow-taskHistory divId="history" businessRecordId="${transferID}" width="750"></fiscxd:workflow-taskHistory>
