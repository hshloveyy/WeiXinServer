/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月07日 14点27分38秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象开票数据抬头(Vbrk)查看页面JS文件
 */
          
   

        

function _editVbrppCallBack()
{
}


function _editVbrpmCallBack()
{
}

/**
  * 开票数据抬头行项目编辑操作
  */
function _editVbrp(id,url)
{
	if(_preeditVbrp()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = Vbrp_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.vbrp_Edit,	url,'',_editVbrpCallBack,{width:660,height:300});
      
      }
      _posteditVbrp();
}
function _editVbrpCallBack()
{
}

    
/**
  * 开票数据抬头行项目查看操作
  */
function _viewVbrp(id,url)
{
	if(_previewVbrp()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = Vbrp_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.vbrp_View,	url,'','',{width:660,height:300});
	}
	_postviewVbrp();
}
/**
  * 开票数据抬头行项目查看，打开到页签 ,回调函数
  */
function _viewVbrppCallBack()
{
}

/**
  * 开票数据抬头行项目查看，弹出窗口 ,回调函数
  */
function _viewVbrpmCallBack()
{
}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

  

          

/**
 * 取消
 */
function _cancelVbrk()
{
  if(_precancelVbrk()){
	new AjaxEngine(contextPath + '/xdss3/tradeMonitoring/vbrkController.spr?action=_cancel&vbeln='+vbeln, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelVbrkCallBack});
   }
   _postcancelVbrk();
}

function _cancelVbrkCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
