<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<html>

<head>
  <title>index</title>
</head>
<body>

<div id="div"></div>
<input type="button" value="test" onclick="loadEdit()">
  </body>
</html>
<script>
    function deleteAtt(id,url)
    {
        alert(url);
    }
    function _download(id,url){alert(id);}
    function _delete(id,url){alert(id);}
    function _create(){}
    function _copyCreate(){}
    function _upload()
    {
		_getMainFrame().maintabs.addPanel('标题',Attachement_grid,'component/grid/index.jsp'); 
    }
    
    function _deletes(){}
    function loadEdit()
    {
    	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.activeTab);
        alert("删除值:"+attachement_delete_ids);
        alert("修改值:"+attachement_modify_ids);
    }
</script>

<fisc:grid boName="Competitor" divId="div" needCheckBox="true" editable="true" title="test" width="800" needAutoLoad="true"></fisc:grid>