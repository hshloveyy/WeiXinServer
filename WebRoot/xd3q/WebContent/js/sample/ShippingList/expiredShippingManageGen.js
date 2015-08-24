/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年12月04日 14点50分32秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象过期出货清单(ExpiredShipping)管理页面JS文件
 */


/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_ExpiredShipping_grid(para);
	}
	_postsearch();
}

function _expExcel()
{
    var vExportContent = ExpiredShipping_grid.getExcelXml();
    //var vExportContent = '';
    if (Ext.isIE6 || Ext.isIE7 || Ext.isSafari || Ext.isSafari2 || Ext.isSafari3) {
        var fd=Ext.get('frmDummy');
        if (!fd) {
            fd=Ext.DomHelper.append(Ext.getBody(),{tag:'form',method:'post',id:'frmDummy',action:'expiredShippingController.spr?action=_expExcel', target:'_blank',name:'frmDummy',cls:'x-hidden',cn:[
                {tag:'input',name:'exportContent',id:'exportContent',type:'hidden'}
            ]},true);
        }
        fd.child('#exportContent').set({value:vExportContent});
        fd.dom.submit();
    } else {
        document.location = 'data:application/vnd.ms-excel;base64,'+Base64.encode(vExportContent);
    }
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_ExpiredShipping_grid("");
}

/**
 * 清空操作
 */
function _resetForm()
{
  if(_preresetForm()){
		document.all.mainForm.reset();
	}
	_postresetForm();
}

