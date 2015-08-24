/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月01日 06点24分19秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象收款信息表(Collect)管理页面JS用户可编程扩展文件
 */


/**
 * 查询动作执行前
 */
function _presearch()
{
	return true;
}

/**
 * 查询动作执行后
 * 当 _presearch() 返回 false 时候则执行本函数。
 */
function _postsearch()
{

}


/**
 * 清空操作
 */
function _preresetForm()
{
	return true;
}
/**
 * 清空操作
 */
function _postresetForm()
{

}

function winPickCallBack(jsonArrayData){
	if(jsonArrayData.length ==0){
		return ;
	}
    if(jsonArrayData.length >1){
       showInfo("只能选择一条记录！");
       return ;
    }
    var pare = '&unnamercollectid='+ jsonArrayData[0].UNNAMERCOLLECTID;

	_getMainFrame().maintabs.addPanel("未名户认领创建",Collect_grid,contextPath + '/xdss3/collect/collectController.spr?action=_createUnnamerCollect'+ pare);
}

var searchDepositWin = new Ext.SearchHelpWindow({
	shlpName : 'YHUNNAMERCOLLECT',
	callBack : winPickCallBack
});

/**
 * grid 上的 创建按钮调用方法 
 * 新增收款信息表
 */
function _precreate()
{
    searchDepositWin.defaultCondition = "ISCLAIM='0' AND BUSINESSSTATE='2'";
	searchDepositWin.show();
	return false ;
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增收款信息表
 */
function _postcreate()
{

}

/**
 * grid上的 复制创建按钮调用方法
 */
function _precopyCreate()
{
	return true ;
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _postcopyCreate()
{

}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除收款信息表
 */
function _predeletes()
{
   return true ;
}
/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除收款信息表
 */
function _postdeletes()
{

}

function _viewVoucher()
{
	if (Collect_grid.selModel.hasSelection() == 1){
		var records = Collect_grid.selModel.getSelections();
		var id = records[0].json.collectid;
		_getMainFrame().maintabs.addPanel("查看凭证",null,contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid='+ id);
	}
}
