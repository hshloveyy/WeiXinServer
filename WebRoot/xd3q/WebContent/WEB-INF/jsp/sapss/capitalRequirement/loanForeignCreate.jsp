<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>外币短期借款添加页面</title>
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
var re_loanID = '${loanID}';
var from = '<%=request.getAttribute("popfrom")%>';
var tempTab={};
Ext.onReady(function(){
	var fm = Ext.form;
	//Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	var beginDate = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 100,
		applyTo:'beginDate'
   	});
	var endDate = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 100,
		applyTo:'endDate'
   	});
//收款付款信息
var repayPlant = Ext.data.Record.create([
   		{name:'REPAY_ID'},
		{name:'LOAN_ID'},
		{name:'AMOUNT_FOREIGN'}, 
		{name:'REPAY_DATE'}, 
		{name:'EX_RATE'},   
		{name:'AMOUNT_CNY'},		   		   		
		{name:'REMARK'}
	]);

repayds = new Ext.data.Store({
   		proxy : new Ext.data.HttpProxy({url:'loanForeignController.spr?action=findRepay&loanID='+re_loanID}),
   		reader: new Ext.data.JsonReader({
       	root: 'root',
       	totalProperty: 'totalProperty'
       	},repayPlant)
    });
 var repaysm = new Ext.grid.CheckboxSelectionModel();
 var repaycm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		repaysm,
			{header: '还款ID',
           	width: 10,
           	sortable: true,
           	dataIndex: 'REPAY_ID',
           	hidden:true
			},
			{header: '短期借款ID',
           	width: 10,
           	sortable: true,
           	dataIndex: 'LOAN_ID',
           	hidden:true
			},
			{header: '还款外币金额',
			   width:30,
			   sortable: true,
			   dataIndex: 'AMOUNT_FOREIGN',
			   editor: new fm.NumberField({
				   allowBlank: false,
				   allowNegative: false,
				   decimalPrecision:3,
				   maxValue: 999999999999
			   })
			},
			{header: '还款日期',
           	width: 30,
           	sortable: true,
           	dataIndex: 'REPAY_DATE',
			editor: new fm.DateField({
				format:'Y-m-d',
            	allowBlank: false
            })
			},
			{header: '还款日汇率',
			   width:30,
			   sortable: true,
			   dataIndex: 'EX_RATE',
			   editor: new fm.NumberField({
				   allowBlank: false,
				   allowNegative: false,
				   decimalPrecision:3,
				   maxValue: 999999999999
			   })
			},
			{header: '还款人民币金额',
			   width:30,
			   sortable: true,
			   dataIndex: 'AMOUNT_CNY',
			   editor: new fm.NumberField({
				   allowBlank: false,
				   allowNegative: false,
				   decimalPrecision:3,
				   maxValue: 999999999999
			   })
			},			
            {header: '备注',
            width: 100,
            sortable: true,
            dataIndex: 'REMARK',
			editor: new fm.TextField({
               allowBlank: true
           })
           }
    ]);
    repaycm.defaultSortable = true;
 var addRepay = new Ext.Toolbar.Button({
   		text:'增加',
   		iconCls:'add',
		handler:function(){
			var requestUrl = 'loanForeignController.spr?action=createRepay&loanID='+re_loanID;
			Ext.Ajax.request({
				url: requestUrl,
				success: function(response, options){
					var responseArray = Ext.util.JSON.decode(response.responseText);

					var repaytp = new repayPlant({
			    			REPAY_ID: responseArray.repayID,
							LOAN_ID: responseArray.loanID,
							AMOUNT_FOREIGN: responseArray.amountForeign,
							REPAY_DATE: responseArray.repayDate,
							EX_RATE: responseArray.exRate,
							AMOUNT_CNY: responseArray.amountCNY,
							REMARK: responseArray.remark
			             });
					repaygrid.stopEditing();
					repayds.insert(0, repaytp);
					repaygrid.startEditing(0, 0);
				},
				failure:function(response, options){
					//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
				}
			});
		}
 });
var deleteRepay = new Ext.Toolbar.Button({
   		text:'删除',
   		iconCls:'delete',
		handler:function(){
			if (repaygrid.selModel.hasSelection()){
				var records = repaygrid.selModel.getSelections();
				var idList = '';
				for (var i=0;i<records.length;i++){
					idList = idList + records[i].data.REPAY_ID + '|';
				}
				
				top.Ext.Msg.show({
					title:'提示',
  						msg: '是否确定删除记录',
  						buttons: {yes:'是', no:'否'},
  						fn: function(buttonId,text){
  							if(buttonId=='yes'){
  								var param = "?action=deleteRepay";
								param = param + "&loanID="+ re_loanID + "&idList=" + idList;

								new AjaxEngine('loanForeignController.spr', 
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
if(from!='view')
{
var repaytbar = new Ext.Toolbar({
		items:[addRepay,'-',deleteRepay]
	});
}
else
{
var repaytbar = new Ext.Toolbar({
		items:[]
	});
}
var repaybbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:repayds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
repaygrid = new Ext.grid.EditorGridPanel({
    	id:'repayGrid',
    	//title:'收付款明细信息',
        ds: repayds,
        cm: repaycm,
        sm: repaysm,
        tbar: repaytbar,
        bbar: repaybbar,
        border:false,
        loadMask:true,
        autoScroll:true,
		region:'south',
        el: 'div_repay',
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
    
    //repaygrid.render();
repayds.load({params:{start:0, limit:10}});  
repaygrid.on("afteredit", afterEdit, repaygrid);
 function afterEdit(obj){
    	var row = obj.record;//获取被修改的行
		var repayID = row.get("REPAY_ID");
        var colName = obj.field;//获取被修改的列
		var colValue = row.get(colName);
		if (colName == 'REPAY_DATE'){
        	colValue = Ext.util.Format.date(colValue, "Y-m-d");
            row.set(colName,colValue);
        }  
        var requestUrl = 'loanForeignController.spr?action=saveRepay';
			requestUrl = requestUrl + '&repayID=' + repayID;
			requestUrl = requestUrl + '&colName=' + colName;
			requestUrl = requestUrl + '&colValue=' + colValue;
			
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				repayds.commitChanges();
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
			height:210,
			items:[{contentEl:'div_main'}]
		},{
			region:"center",
			layout:'fit',
			title:'还款信息',
			items:[repaygrid]
		},{
			region:"south",
			layout:'fit',
			buttonAlign:'center',
			buttons:[{
            	text:'保存',
				hidden: ${!save_button_enable},
            	handler:function(){
            		var param1 = Form.serialize('mainForm');
					var param = param1 + "&action=save";
					new AjaxEngine('loanForeignController.spr', 
						   {method:"post", parameters: param, onComplete: callBackHandle});
            	}
            },{
            	text:'关闭',
            	handler:function(){
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
		repayds.reload();
	else
		setTimeout(function(){
			top.ExtModalWindowUtil.markUpdated();
			top.ExtModalWindowUtil.close();
		},1000);	
}

</script>
</head>
<body>
<div id="div_main">
<form id="mainForm">
<table width="775" align="center" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td width="20%" align="right">添加单位</td>
        <td width="30%" align="left"><input name="orgName" type="text" size="25" tabindex="1" value="${main.orgName}" readonly="readonly"/><input type="hidden" name="orgID" value="${main.orgID}"/></td>
        <td colspan="2" width="20%" align="right">添加人</td>
        <td width="30%">
        	<input name="creatorName" type="text" size="25" tabindex="2" value="${main.creatorName}" readonly="readonly"/>
        	<input type="hidden" name="creatorID" value="${main.creatorID}"/>
        </td>
      </tr>
	  <tr>
        <td align="right">公司代码<font color="red">▲</font></td>
        <td>
        	<input name="companyCode" type="text" size="25" tabindex="2" value="${main.companyCode}"/>
        </td>
        <td colspan="2" align="right">单据号</td>
        <td>
        	<input name="billNO" type="text" size="25" tabindex="2" value="${main.loanID}" readonly="readonly"/>
        </td>
      </tr>
	  <tr>
        <td align="right">贷款日<font color="red">▲</font></td>
        <td>
        	<input name="beginDate" type="text" size="10" tabindex="2" value="${main.beginDate}" readonly="readonly"/>
        </td>
        <td colspan="2" align="right">到期日<font color="red">▲</font></td>
        <td>
        	<input name="endDate" type="text" size="10" tabindex="2" value="${main.endDate}" maxlength="20" readonly="readonly"/>
        </td>
      </tr>
	  <tr>
		<td align="right">外币金额<font color="red">▲</font></td>
        <td>
        	<input name="amountForeign" type="text" size="25" tabindex="2" value="${main.amountForeign}"/>
        </td>
        <td colspan="2" align="right">币别<font color="red">▲</font></td>
        <td>
        	<div id="div_currency" name="div_currency"></div>
        </td>
	  </tr>
	  <tr>
	  	<td align="right">贷款日汇率<font color="red">▲</font></td>
        <td>
        	<input name="exRate" type="text" size="25" tabindex="2" value="${main.exRate}"/>
        </td>
		<td colspan="2" align="right">人民币金额</td>
        <td>
        	<input name="amountCNY" type="text" size="25" tabindex="2" value="${main.amountCNY}" readonly="readonly"/>
        </td>
	  </tr>
	  <tr>
        <td align="right">金融机构</td>
        <td>
        	<input name="institution" type="text" size="25" tabindex="5" value="${main.institution}">
        </td>
        <td colspan="2" align="right">利率<font color="red">▲</font></td>
        <td>
        	<input name="rate" type="text" size="25" tabindex="2" value="${main.rate}"/>
        </td>
      </tr>
	  <tr>
	  	 <td align="right">外币余额</td>
        <td>
        	<input name="balanceForeign" type="text" size="25" tabindex="5" value="${main.balanceForeign}" readonly="readonly"/>
        <td colspan="2" align="right">人民币余额</td>
        <td>
        	<input name="balanceCNY" type="text" size="25" tabindex="5" value="${main.balanceCNY}" readonly="readonly"/>
        </td>
      </tr>
	   <tr>
		<td align="right">承诺等其他费用</td>
        <td>
        	<input name="otherFee" type="text" size="25" tabindex="5" value="${main.otherFee}"/>
        </td>
		<td colspan="3"><font color="#FF0000">注：保存后，人民币金额、外币余额、人民币余额系统自动计算</font></td>
      </tr>
</table>
<input type="hidden" name="loanID" value="${main.loanID}">
<input type="hidden" name="createTime" value="${main.createTime}" />
<input type="hidden" name="isAvailable" value="${main.isAvailable}" />
<input type="hidden" name="from" value="${popfrom}">
</form>
</div>
<div id="div_repay"></div>
</body>
</html>
<fiscxd:dictionary divId="div_currency" fieldName="currency" dictionaryName="bm_currency" width="200" selectedValue="${main.currency}" disable="false" needDisplayCode="true"></fiscxd:dictionary>