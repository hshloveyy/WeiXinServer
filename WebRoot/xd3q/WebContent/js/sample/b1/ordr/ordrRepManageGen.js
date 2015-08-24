/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月23日 15点52分48秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象OrdrRep(OrdrRep)管理页面JS文件
 */

/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
 
    var docdateMax = $('docdate_maxValue').value;
    var docdateMin = $('docdate_minValue').value;
    if(docdateMax && docdateMin)
    {
	    if(docdateMax<docdateMin)
	    {
           _getMainFrame().showInfo(Txt.docdate_EndDateShouldLargerStartDate);
	       return;
	    }
    }
    
	reload_OrdrRep_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_OrdrRep_grid("");
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


