/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月07日 14点27分39秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象开票数据抬头(Vbrk)管理页面JS文件
 */

/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_Vbrk_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_Vbrk_grid("");
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


