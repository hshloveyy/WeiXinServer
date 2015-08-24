<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="fisc" tagdir="/WEB-INF/tags/infolion"%>
<%@ include file="/common/commons.jsp"%>
<html>

<head>
  <title>index</title>
</head>
<body>

<div id="div"></div>

<div id="div2"></div>
<div id="ext-modal-dialog-win" class="x-hidden">
    <div class="x-window-header" id="ext-modal-dialog-win-header"></div>
    <div id="ext-modal-dialog-win-content"></div>
</div>
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
    function _upload(){}
    function _deletes(){}
    function loadEdit()
    {
        var records = div_grid.getStore().getModifiedRecords();
        alert(records.size());
        for(i=0;i<records.size();i++)
        {
        	alert(Ext.util.JSON.encode(records[i].data));
        }
    }
</script>

<fisc:grid boName="Attachement" divId="div" needCheckBox="true" editable="true" title="test" width="800"></fisc:grid>