/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年05月19日 16点48分19秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象打包贷款(PackingLoan)增加页面用户可编程扩展JS文件
 */

          
   
/**
 * grid 上的 创建按钮调用方法 
 * 新增打包贷款,打包贷款银行
 */
function _precreatePackingBankItem()
{
   return true;
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增打包贷款,打包贷款银行
 */
function _postcreatePackingBankItem()
{

}
	 
    

    
/**
 *打包贷款行项目
 *复制创建
 */
function _precopyCreatePackingBankItem()
{
    return true;
}

/**
 *打包贷款行项目
 *复制创建
 */
function _postcopyCreatePackingBankItem()
{

}
    

    

/**
 *打包贷款行项目
 *批量删除
 */
function _predeletesPackingBankItem()
{
	return true;
}

/**
 *打包贷款行项目
 *批量删除
 */
function _postdeletesPackingBankItem()
{

}
    

    
  
    
/**
  * 打包贷款行项目编辑操作
  */
function _preeditPackingBankItem(id,url)
{
   return true ;
}

/**
  * 打包贷款行项目编辑操作
  */
function _posteditPackingBankItem(id,url)
{

}


/**
  * 打包贷款行项目查看操作
  */
function _previewPackingBankItem(id,url)
{
   return true ;
}

/**
  * 打包贷款行项目查看操作
  */
function _postviewPackingBankItem(id,url)
{

}
              
   
/**
 * grid 上的 创建按钮调用方法 
 * 新增打包贷款,还打包贷款银行
 */
function _precreatePackingReBankItem()
{
   return true;
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增打包贷款,还打包贷款银行
 */
function _postcreatePackingReBankItem()
{

}
	 
    

    
/**
 *打包贷款行项目
 *复制创建
 */
function _precopyCreatePackingReBankItem()
{
    return true;
}

/**
 *打包贷款行项目
 *复制创建
 */
function _postcopyCreatePackingReBankItem()
{

}
    

    

/**
 *打包贷款行项目
 *批量删除
 */
function _predeletesPackingReBankItem()
{
	return true;
}

/**
 *打包贷款行项目
 *批量删除
 */
function _postdeletesPackingReBankItem()
{

}
    

    
  
    
/**
  * 打包贷款行项目查看操作
  */
function _previewPackingReBankItem(id,url)
{
   return true ;
}

/**
  * 打包贷款行项目查看操作
  */
function _postviewPackingReBankItem(id,url)
{

}

/**
  * 打包贷款行项目编辑操作
  */
function _preeditPackingReBankItem(id,url)
{
   return true ;
}

/**
  * 打包贷款行项目编辑操作
  */
function _posteditPackingReBankItem(id,url)
{

}

              
   
  
                  
   
    
    
    
  
          

          
function _precopyCreatePackingLoan()
{
	return true ;
}

function _postcopyCreatePackingLoan()
{

}

          

/**
 * 删除打包贷款
 */
function _predeletePackingLoan()
{
	return true ;
}

/**
 * 删除打包贷款
 */
function _postdeletePackingLoan()
{

}
          

/**
 * 创建按钮调用方法 
 * 新增打包贷款
 */
function _precreatePackingLoan()
{
	return true ;
}

/**
 * 创建按钮调用方法 
 * 新增打包贷款
 */
function _postcreatePackingLoan()
{

}
          
/**
 * 保存 
 */
function _presaveOrUpdatePackingLoan()
{
	return true ;
}

/**
 * 保存 
 */
function _postsaveOrUpdatePackingLoan()
{

}

          

/**
 * 取消
 */
function _precancelPackingLoan()
{
	return true ;
}

/**
 * 取消
 */
function _postcancelPackingLoan()
{

}
          

/**
 * 提交
 */
function _presubmitProcessPackingLoan(id)
{
	return true ;
}

/**
 * 提交
 */
function _postsubmitProcessPackingLoan(id)
{

}

/**
 *  增加打包银行
 */
function _addBankPackingBankItem()
{
	
}

/**
 *  增加还打包银行
 */
function _addReBankPackingReBankItem()
{
	
}

/**
 *  现金日记账记帐
 */
function _cashJournalPackingLoan(){
	var param = Form.serialize('mainForm');
	param = param + "&"+ Form.serialize('settleSubjectForm');
	param = param + "&"+ Form.serialize('fundFlowForm');
	param = param + "" + getPackingBankItemGridData();
	param = param + "" + getPackingReBankItemGridData();
	new AjaxEngine(
			contextPath + '/xdss3/packingloan/packingLoanController.spr?action=_cashJournal',
			{
				method : "post",
				parameters : param,
				onComplete : _cashJournalCallBackHandle
			});

}

 /**
  * 现金日记账回调函数
  */
function _cashJournalCallBackHandle(transport){
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	
	PackingBankItem_grid.getStore().reload();
	PackingBankItem_grid.getStore().commitChanges();
	PackingReBankItem_grid.getStore().reload();
	PackingReBankItem_grid.getStore().commitChanges();
	
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	_getMainFrame().maintabs.addPanel('现金日记账','', result.cashJournalURl);
}


/**
 * 信用证号回调函数
*/
function _creditnoCallBack(jsonArrayData)
{
	div_currency_sh_sh.setValue(jsonArrayData.CURRENCY);
	Ext.getDom("PackingLoan.contractno").value = jsonArrayData.CONTRACT_NO;
	Ext.getDom("PackingLoan.createbank").value = jsonArrayData.CREATE_BANK;
	Ext.getDom("PackingLoan.applyamount").value = jsonArrayData.AMOUNT;
	calendar_PackingLoan_creditrecdate.setValue(jsonArrayData.CREDIT_REC_DATE);
}