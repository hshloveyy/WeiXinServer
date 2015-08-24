/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月01日 06点24分18秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象收款信息表(Collect)增加页面用户可编程扩展JS文件
 */

/**
 * 提交
 * --------------------------------- 修改记录 ----------------------------------
 * 邱杰烜 2010-09-06 在提交的时候添加对[是否包含保证金]字段的检查
 * 邱杰烜 2010-09-14 加入自动分配功能
 */
function _presubmitProcessCollect(){
	/*
	 * 清除金额分配、清票、银行页签下金额为0的数据
	 */
	_clearAllZeroAmount();
	/*
	 * 检查银行行项是否为空
	 */
	if(!_checkBankItem()){
		return false;
	}
	/*
	 *检查出仓的关联
	 */
	if(!_checkEcportApplyNo()){
		return false;
	}
	/*
	 * 在提交的时候添加对[是否包含保证金]字段的检查
	 */
	if(!_checkDeposit()){
		return false;
	}
	/*
	 * 在提交的时候检查申请金额是否与收款金额分配总和或银行行项金额总和一致
	 */
	if(!_checkApplyAmount()){
		return false;
	}
	/*
	 * 在业务申请时预计发货日、应开票日必填
	 */
	if(calendar_Collect_sendgoodsdate.getValue()==''){
		_getMainFrame().showInfo('业务申请时[预计发货日]不能为空！');
		return false;
	}
	if(calendar_Collect_billcheckdate.getValue()==''){
		_getMainFrame().showInfo('业务申请时[应开票日]不能为空！');
		return false;
	}
	/*
	 * 加入计算预收票款功能
	 */
//	_calcPrebiilAmount();
	//检查清账金额与分配金额是否相等
	var msg = checkAmount();
	if(msg!=""){
		 _getMainFrame().showInfo(msg);
		return false;
	}
	return true;
}

function _postsubmitProcessCollect(){
	return true;
}

/**
 *收款信息表行项目
 *批量删除
 */
function _predeletesCollectItem(){
	var records = CollectItem_grid.getSelectionModel().getSelections();        	
	for(var i=0;i<records.size();i++)
    {
    	var c = records[i].get('contract_no');
    	var p = records[i].get('project_no');
    	var recs;
    	if(c.trim() !=''){
    		recs = CollectCbill_grid.getStore().query('contract_no',c);
    	}else{
	    	if(p.trim() !=''){
	    		recs = CollectCbill_grid.getStore().query('project_no',p);
	    	}
    	}

    	for(var j=0;j<recs.getCount();j++)
    	{
    		CollectCbill_grid.getStore().remove(recs.get(j));
    	}
    	
		CollectItem_grid.getStore().remove(records[i]);
    }
    return false ;
}

/**
 *收款信息表行项目
 *批量删除
 */
function _postdeletesCollectItem(){}
    
function _predeletesCollectCbill(){
	var records = CollectCbill_grid.getSelectionModel().getSelections();        			
	for(var i=0;i<records.size();i++){
		CollectCbill_grid.getStore().remove(records[i]);
    }
    return false ;
}
  
function _postdeletesCollectCbill(){}

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-09-06
 * 检查是否包含保证金（如果是否包含保证金选否，下面如果有保证金，在保存时必须提示，反之亦然）
 * --------------------------------- 修改记录 ----------------------------------
 * 邱杰烜 2010-09-14 添加对银行承兑汇票号的检查
 */
function _checkDeposit(){
	var isIncSuretyBond = dict_div_incsuretybond_dict.getValue();	// "是否包含保证金"
	var collectItems = CollectItem_grid.getStore();					// "收款金额分配"页签
	var flag = false;												// "收款金额分配"页签是否包含保证金
	for(var i=0;i<collectItems.getCount();i++){
		if(collectItems.getAt(i).get('suretybond')!=0){
			flag = true;
			break;
		}
	}
	if(isIncSuretyBond=='Y' && !flag){
		_getMainFrame().showInfo("收款金额分配不包含保证金，请将[是否包含保证金]设为'否'！");
		return false;
	}else if(isIncSuretyBond=='N' && flag){
		_getMainFrame().showInfo("收款金额分配有包含保证金，请将[是否包含保证金]设为'是'！");
		return false;
	}else if(isIncSuretyBond.trim()==''){
		_getMainFrame().showInfo('请选择[是否包含保证金]！');
		return false;
	}
//	if(dict_div_collecttype_dict.getValue()=='12' &&		// 若收款方式为"银行/商业承兑汇票"
//	   (Ext.getDom('Collect.draft').value).trim()==''){		// 且银行承兑汇票号为空
//		_getMainFrame().showInfo('[银行承兑汇票号]不能为空！');
//		return false;
//	}
	if(!_checkClearAmount()){
		return false;
	}
	//start yanghancai 2010-09-26   增加控制是否选择增加合同或立项
	if (CollectItem_grid.getStore().getCount() <= 0){
		_getMainFrame().showInfo('请增加合同或立项并分配金额!');
		return false;
	}
	//end yanghancai 2010-09-26   增加控制是否选择增加合同或立项	
	return true;
}

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-09-15
 * 检查未收款-在批金额是否小于清帐金额
 */
function _checkClearAmount(){
	var collectCbills = CollectCbill_grid.getStore();
	for(var j=0;j<collectCbills.getCount();j++){
		var unreceivedAmount = parseFloat(collectCbills.getAt(j).get('unreceivedamount'));	// 未收款
		var onroadAmount = parseFloat(collectCbills.getAt(j).get('onroadamount'));			// 在批金额
		var clearAmount  = parseFloat(collectCbills.getAt(j).get('clearamount'));			// 清账金额
		var difference = round((unreceivedAmount - onroadAmount - clearAmount),2);			// 未收款 - 在批金额 - 清账金额（差值）
		if(difference < 0){
			_getMainFrame().showInfo('未收款 - 在批金额不能小于清帐金额！');
			return false;
		}
	}
	return true;
}

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-10-11
 * 检查银行行项是否为空
 */
function _checkBankItem(){
	if(Ext.getDom('Collect.oldcollectitemid').value.trim()==''){
		var ct = dict_div_collecttype_dict.getValue();
		// 若为银行汇票(10)、国内信用证(11)、银行/商业承兑汇票(12)，则不检查
		if(ct!='10' && ct!='11' && ct!='12' && CollectBankItem_store.getCount()<=0){
			_getMainFrame().showInfo('收款银行行项不能为空！');
			return false;
		}
	}
	return true;
}

/**
 * 保存
 * --------------------------------- 修改记录 ----------------------------------
 * 邱杰烜 2010-09-06 在保存的时候添加对[是否包含保证金]字段的检查
 * 邱杰烜 2010-09-14 加入计算预收票款功能
 */
function _presaveOrUpdateCollect(){ 
	/*
	 * 清除金额分配、清票、银行页签下金额为0的数据
	 */
	_clearAllZeroAmount();
	/*
	 * 检查出仓的关联
	 */
	if(!_checkEcportApplyNo()){
		return false;
	}
	/*
	 * 在提交的时候添加对[是否包含保证金]字段的检查
	 */
	if(!_checkDeposit()){
		return false;
	}
	/*
	 * 在提交的时候检查申请金额是否与收款金额分配总和或银行行项金额总和一致
	 */
	if(!_checkApplyAmount()){
		return false;
	}
	/*
	 * 加入计算预收票款功能
	 */
//	_calcPrebiilAmount();
	
	//检查清账金额与分配金额是否相等
	var msg = checkAmount();
	if(msg!=""){
		 _getMainFrame().showInfo(msg);
		return false;
	}
	
	var oldcollectitemid = document.getElementById('Collect.oldcollectitemid').value;
    //if(oldcollectitemid=='') return true;
    var applyamount = document.getElementById('Collect.applyamount').value;
    var convertamount = document.getElementById('Collect.convertamount').value;
    if(applyamount!=convertamount){
       _getMainFrame().showInfo("申请收款金额和保证金转货款金额不相等！");
       return false;
    }
    var assignamount = 0;
    for(var i=0;i<CollectItem_grid.getStore().getCount();i++){
		assignamount = assignamount + CollectItem_grid.getStore().getAt(i).get('assignamount');
	}
	assignamount = round(assignamount,2);
	if(applyamount!=assignamount){
	   _getMainFrame().showInfo("保证金转货款金额和分配金额合计不相等！");
       return false;
	}
	if(assignamount==0){
	   _getMainFrame().showInfo("未选择分配行项目或未分配金额！");
       return false;
	}
	
	var size = CollectCbill_grid.getStore().getCount();
	if(size !=0){
		var param = Form.serialize('mainForm');	  		
		param = param + ""+ getAllCollectItemGridData();         	          
	 	param = param + ""+ getAllCollectCbillGridData(); 
	//	param = param +  "&"+ Form.serialize('settleSubjectForm'); 
 	//	param = param +  "&"+ Form.serialize('fundFlowForm');		
		
		//ajax同步调用
		var url = contextPath + '/xdss3/collect/collectController.spr?action=checkClearData&';	
		var conn = Ext.lib.Ajax.getConnectionObject().conn; 
		conn.open("post", url,false); 
		conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");  
		conn.send(param);  
		var responseUtil = new AjaxResponseUtils(conn.responseText);
		var result = responseUtil.getCustomField("coustom");		
		if(result.isRight){
			return true;	
		}else{
			showInfo(result.info);	
			return false;	
		}
	}
	return true ;
}


function _checkEcportApplyNo(){
	var collecttype = dict_div_collecttype_dict.getValue();
	var export_apply_no = div_export_apply_no_sh_sh.getValue().trim();
	
	if(export_apply_no == "" && (collecttype == '02' || collecttype == '03' || collecttype == '04' || collecttype == '05' )){
	   _getMainFrame().showInfo("收款方式为DP,DA,即期信用证,远期信用证,必须填入出单发票号！");
       return false;
	}
	/**
	if(export_apply_no != "" && collecttype == '01'){
		 _getMainFrame().showInfo("收款方式为TT,不能填写出单发票号！");
       return false;
	}
	**/
	return true ;
}

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-10-07 
 * 检查申请金额是否与收款金额分配总和或银行行项金额总和一致
 */
function _checkApplyAmount(){
	var convertAmount = round(parseFloat(Ext.getDom('Collect.convertamount').value),2);
	// 检查收款分配行项的分配金额总和是否与申请金额一致
	if(CollectItem_store.getCount() > 0){
		var assignAmout = 0;
		for(var i=0;i<CollectItem_store.getCount();i++){
			assignAmout += parseFloat(CollectItem_store.getAt(i).get('assignamount'));
		}
		assignAmout = round(assignAmout,2);
		if(convertAmount != assignAmout){
			var text = '折算金额为：' + convertAmount + '\n收款分配金额为：' + assignAmout + '\n两者不一致，是否继续？';
			if(confirm(text)){
			}else{
				return false;
			}
		}
	}
	// 检查收款银行行项的收款金额总和是否与申请金额一致
	if(CollectBankItem_store.getCount() > 0){
		var collectAmount = 0;
		for(var i=0;i<CollectBankItem_store.getCount();i++){
			collectAmount += parseFloat(CollectBankItem_store.getAt(i).get('collectamount'));
		}
		collectAmount = round(collectAmount,2);
		if(convertAmount != collectAmount){
			var text = '折算金额为：' + convertAmount + '\n银行收款金额为：' + collectAmount + '\n两者不一致，是否继续？' ;
			if(confirm(text)){
				return true;
			}else{
				return false;
			}
		}
	}
	return true;
}


/**
 * 保存 
 */
function _postsaveOrUpdateCollect(){}

          

/**
 * 取消
 */
function _precancel()
{
	return true;
}

/**
 * 取消
 */
function _postcancel()
{

}

var searchContractSalesWin = new Ext.SearchHelpWindow({
	    shlpName : 'YHCONTRASALES',
	    callBack : winContractSalesCallBack
});

var searchProjectInfoWin = new Ext.SearchHelpWindow({
	    shlpName : 'YHXDPROJECTINFO',
	    callBack : winProjectInfoCallBack
});

var searchCustomerTitleWin = new Ext.SearchHelpWindow({
	    shlpName : 'YHCUSTOMERTITLE2',
	    callBack : winCustomerTitleCallBack
});

var searchBankInfoWin = new Ext.SearchHelpWindow({
	    shlpName : 'YHBANKACCOUNT',
	    callBack : winBankInfoCallBack
});

/**
 * @修改作者：邱杰烜
 * @修改日期：2010-09-17 
 * 根据即将往收款金额分配的行项目插入的数据来判断这些项目是否已经授信，并将结果写入授信类型字段
 */
function winContractSalesCallBack(jsonArrayData){
	var contractnos = '';				// 合同号
	var projectnos = '';				// 立项号
	var projectnames = '';
	var contAmountArray = new Array();	// 合同金额数组
	var matGroupArray = new Array();    //物料组
	for(var i=0;i<jsonArrayData.length;i++){
		var num = CollectItem_grid.getStore().find('contract_no',jsonArrayData[i].CONTRACT_NO);
		if(num==-1){
			contractnos += jsonArrayData[i].CONTRACT_NO + ",";
			projectnos += jsonArrayData[i].VBAK_BNAME + ",";
			projectnames += jsonArrayData[i].PROJECT_NAME + ",";
			contAmountArray.push(jsonArrayData[i].ORDER_TOTAL);
			matGroupArray.push(jsonArrayData[i].WGBEZ);
		}
	}
	if(projectnos != ''){
		var boId = div_customer_sh_sh.getValue();		
		projectnos = projectnos.substring(0, projectnos.length-1);
		projectnames = projectnames.substring(0, projectnames.length-1);
		contractnos = contractnos.substring(0,contractnos.length-1);
		var param = 'boid=' + boId + '&projectnos=' + projectnos;
		new AjaxEngine(contextPath + '/xdss3/collect/collectController.spr?action=checkProjCreditType', {
			method:"post", 
			parameters:param, 
			onComplete:function(transport){
				var promptMessagebox = new MessageBoxUtil();
				promptMessagebox.Close();
				var responseUtil = new AjaxResponseUtils(transport.responseText);
				var returnParam = responseUtil.getCustomField("coustom");
				var creditTypes = returnParam.credittypes.split(',');	// 授信类型
				var contractArray = contractnos.split(',');				// 合同编号
				var projectArray= projectnos.split(',');				// 项目编号
				var projNameArr = projectnames.split(',');
//				alert(creditTypes.length + '\n' + creditTypes.toString());
				/*
				 * creditType所代表的含义
				 * 0 - 无授信
				 * 1 - 客户放货
				 * 2 - 客户代垫
				 * 3 - 客户放货+代垫
				 * 4 - 供应商授信
				 */
				for(var i=0;i<creditTypes.length;i++){
					var creditType = creditTypes[i];
					/**
					if(creditTypes[i]=='0'){
						creditType = '无';
					}else if(creditTypes[i]=='1'){
						creditType = '客户放货';
					}else if(creditTypes[i]=='2'){
						creditType = '客户代垫';
					}else if(creditTypes[i]=='3'){
						creditType = '客户放货+代垫';
					}else if(creditTypes[i]=='4'){
						creditType = '供应商授信';
					}**/
					var p = new Ext.data.Record({
						contract_no:contractArray[i],
						project_no:projectArray[i],
						project_no_text:projNameArr[i],
						contractamount:contAmountArray[i],
						creditlinetype:creditType,
						assignamount:0,
						assignamount2:0,
						suretybond:0,
						actsuretybond:0,
						prebillamount:0,
						prebillisclear:0,
						clearedamount:0,
						suretybondclear:0,
						goodsamount:0,
						ymat_group:matGroupArray[i]
					});
					CollectItem_grid.stopEditing();
					CollectItem_grid.getStore().insert(0, p);
				    CollectItem_grid.startEditing(0, 0);
				}
			}, 
			callback:''});
	}
	return;
}

/**
 * @修改作者：邱杰烜
 * @修改日期：2010-09-17 
 * 根据即将往收款金额分配的行项目插入的数据来判断这些项目是否已经授信，并将结果写入授信类型字段
 */
function winProjectInfoCallBack(jsonArrayData){
	var projectnos = '';
	var projectnames = '';
	var matGroupArray = new Array();    //物料组
	for(var i=0;i<jsonArrayData.length;i++){
		var canAdd = false;		
		var num = CollectItem_grid.getStore().find('project_no',jsonArrayData[i].PROJECT_NO);
		if(num==-1){
			canAdd = true;
		}else{
			if(CollectItem_grid.getStore().getAt(num).get('contract_no').trim()!=''){
				canAdd = true;
			}
		}
		if(canAdd){
			projectnos += jsonArrayData[i].PROJECT_NO + ",";
			projectnames += jsonArrayData[i].PROJECT_NAME + ",";
			matGroupArray.push(jsonArrayData[i].WGBEZ);
		}
	}
	// 根据项目号自动带出这些项目的授信类型
	if(projectnos != ''){
		var boId = div_customer_sh_sh.getValue();		// 客户编号
		projectnos = projectnos.substring(0, projectnos.length-1);
		projectnames = projectnames.substring(0, projectnames.length-1);
		var param = 'boid=' + boId + '&projectnos=' + projectnos;
		new AjaxEngine(contextPath + '/xdss3/collect/collectController.spr?action=checkProjCreditType', {
			method:"post", 
			parameters:param, 
			onComplete:function(transport){
				var promptMessagebox = new MessageBoxUtil();
				promptMessagebox.Close();
				var responseUtil = new AjaxResponseUtils(transport.responseText);
				var returnParam = responseUtil.getCustomField("coustom");
				var creditTypes = returnParam.credittypes.split(',');	// 授信类型
				var projectArray= projectnos.split(',');				// 项目编号
				var projNamesArr= projectnames.split(',');
				for(var i=0;i<creditTypes.length;i++){
					var creditType = creditTypes[i];
					/**
					if(creditTypes[i]=='0'){
						creditType = '无';
					}else if(creditTypes[i]=='1'){
						creditType = '客户放货';
					}else if(creditTypes[i]=='2'){
						creditType = '客户代垫';
					}else if(creditTypes[i]=='3'){
						creditType = '客户放货+代垫';
					}else if(creditTypes[i]=='4'){
						creditType = '供应商授信';
					}**/
					var p = new Ext.data.Record({
						contract_no:'',
						project_no:projectArray[i],
						project_no_text:projNamesArr[i],
						contractamount:0,
						creditlinetype:creditType,
						assignamount:0,
						assignamount2:0,
						clearedamount:0,
						suretybond:0,
						actsuretybond:0,
						prebillamount:0,
						prebillisclear:0,
						suretybondclear:0,
						goodsamount:0,
						ymat_group:matGroupArray[i]
					});
					CollectItem_grid.stopEditing();
					CollectItem_grid.getStore().insert(0, p);
				    CollectItem_grid.startEditing(0, 0);
				}
			}, 
			callback:''});
	}
	return;
}

function winCustomerTitleCallBack(jsonArrayData)
{
	var customertitleids = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var num = CollectCbill_grid.getStore().find('titleid',jsonArrayData[i].CUSTOMERTITLEID);
		if(num==-1){
			customertitleids += jsonArrayData[i].CUSTOMERTITLEID + ",";
		}
	}
	if(customertitleids=="")return;
	customertitleids = customertitleids.substring(0,customertitleids.length-1);
	
	Ext.Ajax.request({
    	url : contextPath+'/xdss3/collect/collectController.spr?action=getCollectCbillData',
        params : {customertitleids:customertitleids},
        success : function(xhr){
			if(xhr.responseText){
	        	var jsonData = Ext.util.JSON.decode(xhr.responseText);
	        	CollectCbill_grid.getStore().clearFilter();
	        	for(var j=0;j<jsonData.data.length;j++){
	        		var p = new Ext.data.Record(jsonData.data[j]);
					CollectCbill_grid.stopEditing();
					CollectCbill_grid.getStore().insert(0, p);
				    CollectCbill_grid.startEditing(0, 0);
	        	}
	        	//CollectCbill_grid.getStore().sort([{field:'contract_no',direction: 'ASC'}, {field:'project_no',direction:'ASC'}], 'ASC');
			}
        },
        scope : this
    });
}

function winBankInfoCallBack(jsonArrayData)
{
	var bank_account_Array = new Array();
	var bank_name_Array = new Array();
	var bank_hkont_Array = new Array();
	var txt20_Array = new Array();
	var k = jsonArrayData.length;
	for(var i=0;i<jsonArrayData.length;i++){
		var num = CollectBankItem_grid.getStore().find('collectbankid',jsonArrayData[i].BANK_ID);
			
		if(num==-1){		    
			bank_account_Array.push(jsonArrayData[i].BANK_ACCOUNT);
			bank_name_Array.push(jsonArrayData[i].BANK_NAME);
			bank_hkont_Array.push(jsonArrayData[i].BANK_HKONT);
			txt20_Array.push(jsonArrayData[i].TXT20);
		}
	}
		 var projectno ='';
		 for(var i=0;i<CollectItem_grid.getStore().getCount();i++){
		 	projectno = CollectItem_grid.getStore().getAt(i).get('project_no').trim();
		 	break;
		 }
		if(projectno !=''){
			Ext.Ajax.request({
		    	url : contextPath+'/xdss3/collect/collectController.spr?action=getTradeTypeByProjectno',
		        params : {projectno:projectno},
		        success : function(xhr){
					if(xhr.responseText){
			        	
			        	var responseUtil = new AjaxResponseUtils(xhr.responseText);							
			        	var returnParam = responseUtil.getCustomField("coustom");
						var tradeType = returnParam.tradeType;
						var cashflowitem = '101';
						var cashflowitem_text = '销售商品、提供劳务收到的现金';
						//纯代理现金流修改zzh 20140618
						//var t = dict_div_collecttype_dict.getValue();
						//if(t != '11' && t!= '12'){
				      //  	if(tradeType == '5' || tradeType ==  '6'){
				       // 		cashflowitem ='104';
				      // 		cashflowitem_text='收到的其他与经营活动有关的现金';
				      //  	}
			        //	}
			        	for(var i=0;i<k;i++){
				        	var p = new Ext.data.Record({
								collectbankacc:bank_account_Array[i],
								collectbankacc_text:bank_name_Array[i],
								collectamount:0,
								collectamount2:0,
								cashflowitem:cashflowitem,
								cashflowitem_text:cashflowitem_text,
								colbanksubject:bank_hkont_Array[i],
								colbanksubject_text:txt20_Array[i]
							});
							CollectBankItem_grid.stopEditing();
							CollectBankItem_grid.getStore().insert(0, p);
						    CollectBankItem_grid.startEditing(0, 0);
	    			    }    	
					}
		        },
		        scope : this
		    });
		}
}

function _addContractCollectItem()
{
	if(div_customer_sh_sh.getValue()==''){
		_getMainFrame().showInfo("必须先选择客户(付款单位)");
		return;
	}
	searchContractSalesWin.defaultCondition = "KUAGV_KUNNR='"+div_customer_sh_sh.getValue()+"'" + " and DEPT_ID='" + div_dept_id_sh_sh.getValue() + "' and IS_AVAILABLE='1'  and PROJECT_STATE in('3','8') AND order_state IN ('3', '5', '7', '9') ";
	searchContractSalesWin.show();
}

function _addProjectCollectItem(){
	searchProjectInfoWin.defaultCondition = "DEPT_ID='" + div_dept_id_sh_sh.getValue() + "' " + "and IS_AVAILABLE='1' and PROJECT_STATE in('3','8')";
	searchProjectInfoWin.show();
}

function _addCollectCbill(){
	if(div_customer_sh_sh.getValue()==''){
		_getMainFrame().showInfo("必须先选择客户(付款单位)");
		return;
	}
	
	var contractnos = "";
	var projectnos = "";
	for(var i=0;i<CollectItem_grid.getStore().getCount();i++){
		var rec = CollectItem_grid.getStore().getAt(i);
		
		if(rec.get('contract_no').trim() !=''){
			contractnos += "'"+rec.get('contract_no') + "',";
		}else{
			if(rec.get('project_no').trim() !=''){
				projectnos += "'"+rec.get('project_no') + "',";
			}
		}
	}
	if(contractnos==""){
		contractnos = "''";
	}else{
		contractnos = contractnos.substring(0,contractnos.length-1);
	}
	if(projectnos==""){
		projectnos = "''";
	}else{
		projectnos = projectnos.substring(0,projectnos.length-1);
	}
	
	var dc = "KUNNR="+div_customer_sh_sh.getValue() + " and ((IHREZ in ("+contractnos+")) or (BNAME in ("+projectnos+")))"  + " and ISCLEARED='0'";
	
	searchCustomerTitleWin.defaultCondition = dc;
	searchCustomerTitleWin.show();
}

/**
 * @修改作者：邱杰烜
 * @修改日期：2010-08-23
 * 设置增加收款银行时过滤掉空银行号
 */
function _addCollectBankItem(){
	searchBankInfoWin.defaultCondition = "BANK_ACCOUNT<>' ' and Isusing='1' and bukrs=(select company_code from t_sys_dept where dept_id='"+div_dept_id_sh_sh.getValue()+"')";
	//searchBankInfoWin.defaultCondition = "BANK_ACCOUNT<>' '  and Isusing='1' ";
	searchBankInfoWin.show();
}

function _voucherPreviewCollect(){}

//现金日记账
function _cashJournalCollect(){}

/**
 * @修改作者：邱杰烜
 * @修改日期：2010-08-24
 * 自动分配
 */
function _autoAssignCollect(){
	// 若收款清票页签下有数据，则不到数据库里带出该合同下的所有清票，直接给那些数据填写金额
	if(CollectCbill_grid.getStore().getCount()>0){
		_autoAssignAmount();
		return;
	}
	var param = Form.serialize('mainForm');	
	param = param + ""+ getAllCollectItemGridData();
	
	new AjaxEngine(contextPath+'/xdss3/collect/collectController.spr?action=assignCollectCbillData', 
		   {method:"post", parameters: param, onComplete: callBackHandle, callback: callBackHandle1});
}

function callBackHandle1(transport)
{
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	if(transport.responseText){
    	var jsonData = Ext.util.JSON.decode(transport.responseText);
    	
    	CollectItem_grid.getSelectionModel().clearSelections();
    	CollectCbill_grid.getStore().clearFilter();
    	CollectCbill_grid.getStore().removeAll();
    	for(var j=0;j<jsonData.data.length;j++){
    		var p = new Ext.data.Record(jsonData.data[j]);
			CollectCbill_grid.stopEditing();
			CollectCbill_grid.getStore().insert(0, p);
		    CollectCbill_grid.startEditing(0, 0);
    	}
	}
	_autoAssignAmount();
}

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-08-24
 * 自动分配金额
 * --------------------------------- 修改记录 ----------------------------------
 * 邱杰烜 2010-09-02 调整自动分配功能
 */
/******************************************************************************/
function _autoAssignAmount(){
	var collectItems = CollectItem_grid.getStore();
	var collectCbills = CollectCbill_grid.getStore();
	var assignAmount = 0;
	// 设置收款金额分配中的数据按"合同号"降序排序
	collectItems.sort('contract_no','DESC');
	// 设置收款清票中的数据按"记账日期"升序排序
	collectCbills.sort('accountdate','ASC');//改在服务器上升序排序
	// 将"清账金额"清零
	for(var k=0;k<collectCbills.getCount();k++){
		collectCbills.getAt(k).set('clearamount', 0);
	}
	// 分别取到收款金额分配页签中每条数据（排序后），然后再根据每条的合同号/立项号去处理对应的清票
	for(var i=0;i<collectItems.getCount();i++){
		// 先将"预付票款"清零
		collectItems.getAt(i).set('prebillamount',0);
		var cNo = collectItems.getAt(i).get('contract_no');
		var pNo = collectItems.getAt(i).get('project_no');
		// 计算当前记录的值（分配金额 - 保证金）
		assignAmount =  parseFloat(collectItems.getAt(i).get('assignamount')) - parseFloat(collectItems.getAt(i).get('suretybond'));
		if(!Utils.isEmpty(cNo)){	// 若为合同号
			for(var j=0;j<collectCbills.getCount();j++){
				if(collectCbills.getAt(j).get('contract_no')==cNo){		// 且该合同号与收款清票的合同号相同
					var unreceivedAmount = parseFloat(collectCbills.getAt(j).get('unreceivedamount'));	// 未收款
					var onroadAmount = parseFloat(collectCbills.getAt(j).get('onroadamount'));			// 在批金额
					var clearAmount  = parseFloat(collectCbills.getAt(j).get('clearamount'));			// 清账金额
					var difference = unreceivedAmount - onroadAmount - clearAmount;			// 未收款 - 在批金额 - 清账金额（差值）
					// 若差值大于零且当前分配金额大于零
					if(difference>0 && assignAmount>0){
						if(assignAmount > difference){
							collectCbills.getAt(j).set('clearamount', round((difference + clearAmount),2));	// 设置清账金额
							assignAmount -= difference;
						}else{
							collectCbills.getAt(j).set('clearamount', round((assignAmount + clearAmount),2));// 设置清账金额
							assignAmount = 0; 
							break;
						}
					}
				}
			}
		}else{						// 若为立项号
			for(var j=0;j<collectCbills.getCount();j++){
				if(collectCbills.getAt(j).get('project_no')==pNo){		// 且该合同号与收款清票的合同号相同
					var unreceivedAmount = parseFloat(collectCbills.getAt(j).get('unreceivedamount'));	// 未收款
					var onroadAmount = parseFloat(collectCbills.getAt(j).get('onroadamount'));			// 在批金额
					var clearAmount  = parseFloat(collectCbills.getAt(j).get('clearamount'));			// 清账金额
					var difference = unreceivedAmount - onroadAmount - clearAmount;			// 未收款 - 在批金额 - 清账金额（差值）
					// 若差值大于零且当前分配金额大于零
					if(difference>0 && assignAmount>0){
						if(assignAmount > difference){
							collectCbills.getAt(j).set('clearamount', round((difference + clearAmount),2));	// 设置清账金额
							assignAmount -= difference;
						}else{
							collectCbills.getAt(j).set('clearamount', round((assignAmount + clearAmount),2));// 设置清账金额
							assignAmount = 0;
							break;
						}
					}
				}
			}
		}
		collectItems.getAt(i).set('prebillamount', round(assignAmount,2));
	}
}
/******************************************************************************/

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-09-14
 * 计算每个收款金额分配页签下每条数据的预收票款（阉割版的自动分配）
 */
/******************************************************************************/
function _calcPrebiilAmount(){
	var collectItems = CollectItem_grid.getStore();
	var collectCbills = CollectCbill_grid.getStore();
	// 设置收款金额分配中的数据按"合同号"降序排序
	collectItems.sort('contract_no','DESC');
	// 设置收款清票中的数据按"记账日期"升序排序
	collectCbills.sort('accountdate','ASC');
	// 若清帐金额为0时，直接把该条纪录删除
	for(var i=collectCbills.getCount()-1;i>=0;i--){
		if(collectCbills.getAt(i).get('clearamount')==0){	
			collectCbills.removeAt(i);
		}
	}
	var assignAmount = 0;
	// 分别取到收款金额分配页签中每条数据，然后再根据每条的合同号/立项号去处理对应的清票
	for(var i=0;i<collectItems.getCount();i++){
		// 先将"预付票款"清零
		collectItems.getAt(i).set('prebillamount',0);
		var cNo = collectItems.getAt(i).get('contract_no');
		var pNo = collectItems.getAt(i).get('project_no');
		// 计算当前记录的值（分配金额 - 保证金）
		assignAmount =  parseFloat(collectItems.getAt(i).get('assignamount')) - parseFloat(collectItems.getAt(i).get('suretybond'));
		if(!Utils.isEmpty(cNo)){						// 若为合同号
			for(var j=0;j<collectCbills.getCount();j++){
				if(collectCbills.getAt(j).get('contract_no')==cNo){		// 且该合同号与收款清票的合同号相同
					var clearAmount = parseFloat(collectCbills.getAt(j).get('clearamount'));			// 清账金额
					// （分配金额 - 保证金）- 清帐金额
					assignAmount -= clearAmount;
				}
			}
		}else{											// 若为立项号
			var flag = true;
			for(var k=0; k<i; k++){
				var pNo2 = collectItems.getAt(k).get('project_no');
				var pba = collectItems.getAt(k).get('prebillamount');
				if(pNo2==pNo && pba!='0'){
					flag = false;
				}else if(pNo2==pNo && (pba=='0' || pba=='')){
					assignAmount += parseFloat(collectItems.getAt(k).get('assignamount'));
				}
			}
			if(flag){
				for(var j=0;j<collectCbills.getCount();j++){
					if(collectCbills.getAt(j).get('project_no')==pNo){		// 且该合同号与收款清票的合同号相同
						var clearAmount = parseFloat(collectCbills.getAt(j).get('clearamount'));			// 清账金额
						// （分配金额 - 保证金）- 清帐金额
						assignAmount -= clearAmount;
					}
				}
			}
		}
		if(assignAmount < 0){
			assignAmount = 0;
		}
		collectItems.getAt(i).set('prebillamount', round(assignAmount,2));
	}
}
/******************************************************************************/

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-09-17
 * 根据收款分配金额所有行项的授信类型，计算本次收款的"清放货、清代垫、清供应商额度"总本位币金额
 */
function _calcCreditValue(){
	var settleRate = Ext.getDom('Collect.settlerate').value;	// 结算汇率
	var goodsAmount = 0;		// 清放货额度金额（本位币）
	var replcAmount = 0;		// 清代垫额度（本位币）
	var suplrAmount = 0;		// 清供应商额度（本位币）
	// 额度  =（分配金额 - 保证金） * 结算汇率
	for(var i=0;i<CollectItem_store.getCount();i++){
		var assignAmount = parseFloat(CollectItem_store.getAt(i).get('assignamount'));	// 分配金额
		var suretyBond = parseFloat(CollectItem_store.getAt(i).get('suretybond'));		// 保证金
		var creditType = CollectItem_store.getAt(i).get('creditlinetype');	// 授信类型
		
		if(creditType.indexOf('放货',0)!= -1){
		    goodsAmount += (assignAmount - suretyBond) * settleRate;
		}
		if(creditType.indexOf('代垫',0)!= -1){
		    replcAmount += (assignAmount - suretyBond) * settleRate;
		}
		if(creditType.indexOf('供应商授信',0)!= -1){
		    suplrAmount += (assignAmount - suretyBond) * settleRate;
		}
		/**
		if(creditType=='客户放货'){
			goodsAmount += (assignAmount - suretyBond) * settleRate;
		}else if(creditType=='客户代垫'){
			replcAmount += (assignAmount - suretyBond) * settleRate;
		}else if(creditType=='客户放货+代垫'){
			goodsAmount += (assignAmount - suretyBond) * settleRate;
			replcAmount += (assignAmount - suretyBond) * settleRate;
		}else if(creditType=='供应商授信'){
			suplrAmount += (assignAmount - suretyBond) * settleRate;
		}
		**/
	}
	Ext.getDom('Collect.goodsamount').value   = round(goodsAmount,2);	// 设置清放货额度金额（本位币）
	Ext.getDom('Collect.replaceamount').value = round(replcAmount,2);	// 设置清代垫额度（本位币）
	Ext.getDom('Collect.supplieramount').value= round(suplrAmount,2);	// 设置清供应商额度（本位币）
}

/**
 * @修改作者：邱杰烜
 * @修改日期：2010-09-24
 * 清除分配
 */
function _clearAssignCollect(){
	_getMainFrame().Ext.MessageBox.show({
		title:'系统提示',
	    msg: '您选择了【清除分配】操作，是否确定继续该操作？',
		buttons: {yes:Txt.ok, no:Txt.cancel},
		icon: Ext.MessageBox.QUESTION,
		fn:function(buttonid){
		    if (buttonid == 'yes'){
		    	_clearBillValue();
		    	_getMainFrame().showInfo("清除分配成功！");
		    }
		}
	});
}

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-09-24
 * 清除票的金额
 */
function _clearBillValue(){
	for(var k = 0;k<CollectCbill_store.getCount();k++){
		var record = CollectCbill_store.getAt(k);
		record.set('clearamount','');
	}
	for(var i=0; i<CollectItem_store.getCount(); i++){
		var record = CollectItem_store.getAt(i);
		record.set('prebillamount','');
	}
}
/**
*检查清账金额是否跟收款行项目的金额一致
**/
function checkAmount(){
	var sumclearamount = 0;
	var sumitemamount = 0;
	for(var k = 0;k<CollectCbill_store.getCount();k++){
		var record = CollectCbill_store.getAt(k);		
		sumclearamount += parseFloat(record.get('clearamount'));
	}
	for(var i=0; i<CollectItem_store.getCount(); i++){
		var record = CollectItem_store.getAt(i);
		sumitemamount += (parseFloat(record.get('assignamount'))-parseFloat(record.get('suretybond'))-parseFloat(record.get('prebillamount')));
		if(parseFloat(record.get('assignamount'))<0){
			if(parseFloat(record.get('suretybond'))!=0){
				return "分配金额为负数时，保证金只能为零";
			}
			if(parseFloat(record.get('prebillamount'))!=0){
				return "分配金额为负数时，预收款只能为零";
			}
		}
		if(parseFloat(record.get('suretybond'))<0){
				return "保证金不能为负数";
		}
		if(parseFloat(record.get('prebillamount'))<0){
			return "预收款不能为负数";
		}
	}
	
	
	if(round(sumclearamount,2) !=round(sumitemamount,2) ){
		return "清账金额合计"+round(sumclearamount,2)+"必须等于分配金额合计-保证金金额合计-预收款金额合计" + round(sumitemamount,2);
	}
	return "";
}


/**
 * @创建作者：邱杰烜
 * @创建日期：2010-10-08
 * 清除金额分配、清票、银行页签下金额为0的数据
 */
function _clearAllZeroAmount(){
	/*
	 * 清空收款金额分配中"分配金额"为0的数据
	 */
	for(var i=CollectItem_store.getCount()-1;i>=0;i--){
		var amount = CollectItem_store.getAt(i).get('assignamount');
		if(amount==0 || amount==''){
			CollectItem_store.removeAt(i);
		}
	}
	/*
	 * 清空收款清票中"清账金额"为0的数据
	 */
	for(var i=CollectCbill_store.getCount()-1;i>=0;i--){
		var amount = CollectCbill_store.getAt(i).get('clearamount');
		if(amount==0 || amount==''){
			CollectCbill_store.removeAt(i);
		}
	}
	/*
	 * 清空收款银行中"收款金额"为0的数据
	 */
//	for(var i=CollectBankItem_store.getCount()-1;i>=0;i--){
//		var amount = CollectBankItem_store.getAt(i).get('collectamount');
//		if(amount==0 || amount==''){
//			CollectBankItem_store.removeAt(i);
//		}
//	}
}

function _submitForReassignCollect(){
	 if(isCreateCopy){
			document.getElementById("Collect.collectid").value = "";
		}
	 if(_presubmitProcessCollect()){
			var param = Form.serialize('mainForm');	
		          
		         
			param = param + "&" + getAllCollectItemGridData();
			        	          
		         
			param = param + "&" + getAllCollectCbillGridData();
			        	          
		         
			param = param + "&" + getAllCollectRelatedGridData();
			        	          
			//param = param + "&" + Form.serialize('settleSubjectForm');		
			//param = param + "&" + Form.serialize('fundFlowForm');			         
			param = param + "&" + getAllCollectBankItemGridData();
			        		param = param + "&"+ Form.serialize('workflowForm');
			
			new AjaxEngine(contextPath +'/xdss3/collect/collectController.spr?action=_submitForReassign', 
				   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
		  }
	 _postsubmitProcessCollect();
}

/**
 * 初始化界面信息
 */
function mainInit(){
	if (roletype == "1"){
		var maintab = Ext.getCmp("tabs");
		maintab.hideTabStripItem('collectRelatedTab');
		//maintab.hideTabStripItem('settleSubjectTab');
		//maintab.hideTabStripItem('fundFlowTab');

		Ext.getCmp("_voucherPreview").hide();
		Ext.getCmp("_cashJournal").hide();
		Ext.getCmp("_submitForReassign").hide();
		
		var CollectItem_grid_cm =CollectItem_grid.getColumnModel();
		CollectItem_grid_cm.setHidden(CollectItem_grid_cm.findColumnIndex('assignamount2'),true);
		
		var CollectBankItem_grid_cm = CollectBankItem_grid.getColumnModel();
		CollectBankItem_grid_cm.setHidden(CollectBankItem_grid_cm.findColumnIndex('cashflowitem'),true);
		CollectBankItem_grid_cm.setHidden(CollectBankItem_grid_cm.findColumnIndex('collectamount2'),true);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-16
	 * 隐藏所有页签的"增加行"、"删除行"按钮
	 */
	/******************************************************************************/
	var cbiToolbar = CollectBankItem_grid.getTopToolbar();
	cbiToolbar.items.get('CollectBankItemaddRow').hide();		// 隐藏"增加行"按钮
	cbiToolbar.items.get('CollectBankItemdeleteRow').hide();	// 隐藏"删除行"按钮
	var crToolbar = CollectRelated_grid.getTopToolbar();
	crToolbar.items.get('CollectRelatedaddRow').hide();			// 隐藏"增加行"按钮
	crToolbar.items.get('CollectRelateddeleteRow').hide();		// 隐藏"删除行"按钮
	var ccToolbar = CollectCbill_grid.getTopToolbar();
	ccToolbar.items.get('CollectCbilladdRow').hide();			// 隐藏"增加行"按钮
	ccToolbar.items.get('CollectCbilldeleteRow').hide();		// 隐藏"删除行"按钮
	var ciToolbar = CollectItem_grid.getTopToolbar();
	ciToolbar.items.get('CollectItemaddRow').hide();			// 隐藏"增加行"按钮
	ciToolbar.items.get('CollectItemdeleteRow').hide();			// 隐藏"删除行"按钮
	var crToolbar = CollectRelated_grid.getTopToolbar();
	crToolbar.items.get('CollectRelatedaddRow').hide();			// 隐藏"增加行"按钮
	crToolbar.items.get('CollectRelateddeleteRow').hide();		// 隐藏"删除行"按钮
	/******************************************************************************/
	
	/**
	 * @修改作者：邱杰烜
	 * @修改日期：2010-09-03
	 * 收款业务申请界面上收款汇率、凭证日期与记账日期不允许输入
	 */
	/*********************************************************************/
	Ext.getDom('Collect.collectrate').readOnly = true;		// 设置收款汇率只读
	// 等虎哥修改日期控件后再恢复以下两条注释
//	calendar_Collect_voucherdate.setEditable(false);		// 设置凭证日期只读
//	calendar_Collect_accountdate.setEditable(false);		// 设置记账日期只读
	/*********************************************************************/
	
	
	var ps = document.getElementById('Collect.processstate').value;
	if (ps == '资金部出纳审核' ||ps == '出纳审核' ||
		ps == '资金部审核票据并登记相关信息' || ps == '审核票据并登记相关信息'||
		ps == '资金确认票据' ||
		ps == '资金部经理审核贴现或议付' ||
		ps == '资金部人员办理贴现或议付'||ps == '办理贴现或议付'){
		
		var CollectItem_grid_cm =CollectItem_grid.getColumnModel();
		CollectItem_grid_cm.setHidden(CollectItem_grid_cm.findColumnIndex('assignamount2'),true);
		
		var CollectBankItem_grid_cm = CollectBankItem_grid.getColumnModel();
		CollectBankItem_grid_cm.setHidden(CollectBankItem_grid_cm.findColumnIndex('cashflowitem'),true);
		CollectBankItem_grid_cm.setHidden(CollectBankItem_grid_cm.findColumnIndex('collectamount2'),true);
	}
}

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-09-10
 * 自动计算"收款金额分配"（汇率触发）
 */
/***********************************************************************/
function _autoCalcAssignAmount(){
	var settleRate = Ext.getDom('Collect.settlerate').value;	// 结算汇率
	if(Utils.isNumber(settleRate)){
		var collectItems = CollectItem_grid.getStore();				// "收款金额分配"页签
		for(var i=0;i<collectItems.getCount();i++){
			var assignAmount = parseFloat(collectItems.getAt(i).get('assignamount'));		// 分配金额
			collectItems.getAt(i).set('assignamount2', round((assignAmount * settleRate),2));
		}
	}
}
/***********************************************************************/

/**
 * @修改作者：邱杰烜
 * @修改日期：2010-09-15
 * 更改币别时同时更改开票币别与折算金额、实际收款金额
 */
/***********************************************************************/
function setOtherCurrency(){
	var currencyValue = div_currency_sh_sh.getValue();
	div_billcurrency_sh_sh.setValue(currencyValue);
	div_actcurrency_sh_sh.setValue(currencyValue);
	Ext.getDom('Collect.convertamount').value = Ext.getDom('Collect.applyamount').value;
	Ext.getDom('Collect.actamount').value = Ext.getDom('Collect.applyamount').value;
	if(currencyValue == 'CNY'){
		Ext.getDom('Collect.collectrate').value = 1;		// 收款汇率设1
		Ext.getDom('Collect.settlerate').value = 1;			// 结算汇率设1
	}else{
		Ext.getDom('Collect.collectrate').value = '';		// 收款汇率清空
	}
}
/***********************************************************************/

/*
 * 合同详细信息查看
 */ 
function viewContractInfo(contNo){
	var contUrl = contextPath + '/contractController.spr?action=viewSaleContract&contractno=' + contNo;
	if(contNo.trim()!=''){
		_getMainFrame().ExtModalWindowUtil.show('合同信息', contUrl, '', '', {width:700,height:600});
	}
}
/*
 * 立项详细信息查看
 */
function viewProjectInfo(projNo){
	var projUrl = contextPath + '/projectController.spr?action=modify&from=view&projectNo=' + projNo;
	if(projNo.trim()!=''){
		_getMainFrame().ExtModalWindowUtil.show('立项信息', projUrl, '', '', {width:700,height:600});
	}
}
/*
 * 相关单据信息查看 
 */
function viewRelatedInfo(relaNo){
	var relaUrl = contextPath + '/xdss3/collect/collectController.spr?action=viewRelatedInfo&relatedNo=' + relaNo;
	if(relaNo.trim()!=''){
		_getMainFrame().maintabs.addPanel('原收款单信息','',relaUrl);
		   
	}
}

Ext.onReady(function(){
	mainInit();
	
	div_export_apply_no_sh_sh.defaultCondition = "ISAVAILABLE ='1' and DEPTID='" + div_dept_id_sh_sh.getValue() +"'";
	
	/**
	 * @修改作者：邱杰烜
	 * @修改日期：2010-08-19
	 * 根据收款方式设置相应的币别与金额
	 */
	/***********************************************************************/
	dict_div_collecttype_dict.on('select',function(e,n,o){
		var t = dict_div_collecttype_dict.getValue();
		// 若为国内付款，则设置默认币别为CNY、汇率为1
		if(!(t=='' || t=='01' || t=='02' || t=='03' || t=='04' || t=='05')){
			div_currency_sh_sh.setValue('CNY');					// 设置币别
			div_billcurrency_sh_sh.setValue('CNY');				// 设置开票币别
			div_actcurrency_sh_sh.setValue('CNY');				// 设置实际收款币别
			Ext.getDom('Collect.collectrate').value = 1;		// 设置收款汇率
			Ext.getDom('Collect.settlerate').value = 1;			// 设置结算汇率
			var applyamount = Ext.getDom('Collect.applyamount').value;	// 获取申请收款金额
			Ext.getDom('Collect.convertamount').value = applyamount;	// 设置折算金额/保证金转货款金额
			Ext.getDom('Collect.actamount').value = applyamount;		// 设置实际收款金额
		}else{
			Ext.getDom('Collect.collectrate').value = '';	// 清除收款汇率
			Ext.getDom('Collect.settlerate').value  = '';	// 清除结算汇率
		}
//		if(t=='06'){		// 若为现金，则把收款银行页签隐藏，并删除数据
//			Ext.getCmp("tabs").hideTabStripItem('collectBankItemTab');
//			CollectBankItem_store.removeAll();
//		}else{
//			Ext.getCmp("tabs").unhideTabStripItem('collectBankItemTab');
//		}
	});

	/**
	 * @创建作者：yanghancai
	 * @创建日期：2010-10-20
	 * 根据当前流程步骤隐藏相关按钮
	 */
	var taskName=Ext.getDom("workflowCurrentTaskName").value;
	if (taskName.substring(0,6) == '业务部重分配'){
		Ext.getCmp("_voucherPreview").hide();
		Ext.getCmp("_cashJournal").hide();
		Ext.getCmp("_submitProcess").hide();
	}
//	function  _prepConfirmCollect(){
//	 var param = '';
//	    new AjaxEngine(contextPath + '/xdss3/collect/collectController.spr?action=_prepConfirmCollect&collectid='+collectid, 
//	     {method:"post", parameters: param, onComplete: callBackHandle,callback:_prepConfirmCollectCallBackHandle});
//	}
//	function _prepConfirmCollectCallBackHandle(){
//	 var promptMessagebox = new MessageBoxUtil();
//	 promptMessagebox.Close();
//	}

	/**
	 * @修改作者：钟志华
	 * @修改日期：2010-09-26
	 * 收款单搜索应筛选
	 */
	/***********************************************************************/
	div_export_apply_no_sh_sh.on('beforeclick',function(o){
		var t2 = dict_div_collecttype_dict.getValue();
		var t='';
		if(t2!=''){
		  t = Ext.getDom('Collect.collecttype_text').value;
		  if(t2 == '03' || t2 == '04')t='LC';
		  //如果收款方式选“现金”、“电汇”、“网银”，出单搜索一直转，不能显示空白,先屏蔽掉
		  if(t2 == '06' || t2 == '08' || t2 == '09')t='';
		}		
		//if(t != ''){
			div_export_apply_no_sh_sh.defaultCondition = "ISAVAILABLE ='1' and ISCLEAR!='Y'  and DEPTID='" + div_dept_id_sh_sh.getValue() +"'" + " and BILLTYPE ='" + t + "'";
		//}
				
	});
	
	/***********************************************************************/
	
	/**
	 * @修改作者：邱杰烜
	 * @修改日期：2010-08-19
	 * 为申请收款金额添加触发事件
	 * ------------------------------ 修改记录 ------------------------------
	 * 邱杰烜 2010-09-10 添加"结算汇率"失去焦点时触发收款金额分配自动计算功能
	 * 邱杰烜 2010-09-17 添加结算汇率失去焦点时计算本次收款的清放贷、清代垫功能
	 */
	/***********************************************************************/
	Ext.getDom('Collect.applyamount').onchange = function(){
		var applyamount = Ext.getDom('Collect.applyamount').value;
		Ext.getDom('Collect.convertamount').value = applyamount;		// 设置折算金额/保证金转货款金额
		Ext.getDom('Collect.actamount').value = applyamount;			// 设置实际收款金额
	}
	Ext.getDom('Collect.settlerate').onblur = function(){				// 设置结算汇率失去焦点时触发
		_autoCalcAssignAmount();
		_calcCreditValue();
	}
	/***********************************************************************/
	/**
	div_customer_sh_sh.callBack = function(data){
		Ext.Ajax.request({
	    	url : contextPath+'/xdss3/collect/collectController.spr?action=syncUnclearCustomer',
	        params : {customer:data.KUNNR},
	        success : function(xhr){
	        },
	        scope : this
	    });
	}
	**/
	Ext.getDom('Collect.oldcollectno').readOnly = true;
	
	/**
	 * @修改作者：邱杰烜
	 * @修改日期：2010-08-20
	 * 在财务界面时，自动计算"收款金额分配"与"收款银行行项"页签下的分配金额与收款金额
	  ------------------------------ 修改记录 ------------------------------
	 * 邱杰烜 2010-09-14 添加计算收款金额分配的货款金额功能
	 * 邱杰烜 2010-09-17 添加计算该客户与立项下的授信额度功能
	 * 邱杰烜 2010-09-25 双击时去查看当前被双击的记录的立项号或合同号的详细信息
	 * 邱杰烜 2010-09-28 将收款金额分配的合同号与立项号的颜色设成蓝色，以示提醒
	 */
	/***********************************************************************/
	/*
	 * 收款金额分配页签
	 */
	CollectItem_grid.on('afteredit', calcAssignamount, CollectItem_grid);
	function calcAssignamount(e){
		var cRecord = e.record;
		var cField  = e.field;
		var settleRate = Ext.getDom('Collect.settlerate').value;				// 结算汇率
		if(cField == 'assignamount' && Utils.isNumber(settleRate)){									
			var applyAmount = parseFloat(cRecord.get(cField));					// 分配金额
			cRecord.set('assignamount2', round((applyAmount * settleRate),2));	// 分配金额（本位币）
			cRecord.set('prebillamount',cRecord.get('assignamount'));			
		}
		// 计算收款金额分配的保证金的和(也会计算收款金额分配的货款金额)
		var collectItems = CollectItem_grid.getStore();
		var suretybond = 0;
		for(var i=0;i<collectItems.getCount();i++){
			suretybond = suretybond + parseFloat(collectItems.getAt(i).get('suretybond'));
			var goodAmount = parseFloat(collectItems.getAt(i).get('assignamount')) - parseFloat(collectItems.getAt(i).get('suretybond'));
			collectItems.getAt(i).set('goodsamount', round(goodAmount,2));
		}
		Ext.getDom('Collect.remainsuretybond').value = round(suretybond,2);
		// 计算该客户与立项下的授信额度
		_calcCreditValue();
	}
	
	/*
	 * 收款银行行项页签
	 */
	CollectBankItem_grid.on('afteredit', calcCollectAmount, CollectBankItem_grid);
	function calcCollectAmount(e){
		var cRecord = e.record;
		var cField  = e.field;
		var collectRate = Ext.getDom('Collect.collectrate').value;				// 收款汇率
		if(cField == 'collectamount' && Utils.isNumber(collectRate)){
			var collectAmount = parseFloat(cRecord.get(cField));
			cRecord.set('collectamount2', round((collectAmount * collectRate),2));
		}
	}
	
	/*
	 * 将付款金额分配的合同号与立项号渲染成链接形式，并在点击时弹出出详情查看窗口
	 */
	var contIndex = CollectItem_grid.getColumnModel().findColumnIndex('contract_no');
	var projIndex = CollectItem_grid.getColumnModel().findColumnIndex('project_no');
	var relaIndex = CollectRelated_grid.getColumnModel().findColumnIndex('relatedno');
	CollectItem_grid.getColumnModel().setRenderer(contIndex,function(contNo){
		return '<a href="#" onclick="viewContractInfo(\''+contNo+'\');"><u style="border-bottom:1px;">'+contNo+'</u></a>';
	});
	CollectItem_grid.getColumnModel().setRenderer(projIndex,function(projNo){
		return '<a href="#" onclick="viewProjectInfo(\''+projNo+'\');"><u style="border-bottom:1px;">'+projNo+'</u></a>';
	});
	CollectRelated_grid.getColumnModel().setRenderer(relaIndex,function(relaNo){
		return '<a href="#" onclick="viewRelatedInfo(\''+relaNo+'\');"><u style="border-bottom:1px;">'+relaNo+'</u></a>';
	});
	/***********************************************************************/
	
	/**
	 * @修改作者：邱杰烜
	 * @修改日期：2010-09-02
	 * 若为未明户认领创建操作，将收款汇率与结算汇率都清空
	 */
	/*********************************************************************/
	if(!Utils.isEmpty(Ext.getDom('Collect.unnamercollectid').value)){
		Ext.getCmp("_cashJournal").hide();			// 隐藏现金日记账
		Ext.getCmp("_submitForReassign").hide();	// 隐藏重分配提交
		Ext.getDom('Collect.applyamount').readOnly = true;	// 设置申请收款金额不可更改
		dict_div_incsuretybond_dict.setValue('N'); 		// 设置是否包含保证金默认为否
	}
	/*********************************************************************/
});
