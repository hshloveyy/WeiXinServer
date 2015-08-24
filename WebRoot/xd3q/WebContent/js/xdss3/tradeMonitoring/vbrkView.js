/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月07日 14点27分38秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象开票数据抬头(Vbrk)查看页面JS文件
 */

          
   
    
/**
  * 开票数据抬头行项目编辑操作
  */
function _preeditVbrp(id,url)
{
	return true ;
}

/**
  * 开票数据抬头行项目编辑操作
  */
function _posteditVbrp(id,url)
{

}
    

/**
  * 开票数据抬头行项目查看操作
  */
function _previewVbrp(id,url)
{
	return false ;
}

/**
  * 开票数据抬头行项目查看操作
  */
function _postviewVbrp(id,url)
{
    
           url = contextPath+"/"+url + "&";
        //取得行数据，转发到查看页面
        var records = Vbrp_grid.getSelectionModel().getSelections();     
        url = url + commonUrlEncode(records[0].data);
        _getMainFrame().ExtModalWindowUtil.show(Txt.vbrp_View,  url,'','',{width:660,height:300});

}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  

          

/**
 * 取消
 */
function _precancelVbrk()
{
	return false ;
}

/**
 * 取消
 */
function _postcancelVbrk()
{
         if (_getMainFrame().ExtModalWindowUtil.getActiveExtWin())
        {
            _getMainFrame().ExtModalWindowUtil.close();
        }
        else
        {
            _getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
        }
}
