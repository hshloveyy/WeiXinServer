<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.infolion.FCKeditor.*" %>
<%@ taglib uri="/FCKeditor" prefix="FCK" %>
<script type="text/javascript" src="<%= request.getContextPath() %>/FCKeditor/fckeditor.js"></script>

<%--
<form action="show.jsp" method="post" target="_blank">
        <FCK:editor id= "content"   basePath= "/FCKeditor/" 
        height= "100%" 
        skinPath= "/FCKeditor/editor/skins/silver/" 
        toolbarSet= "Default " 
        imageBrowserURL= "/FCKeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector " 
	linkBrowserURL= "/FCKeditor/editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector " 
	flashBrowserURL= "/FCKeditor/editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector " 
        imageUploadURL= "/FCKeditor/editor/filemanager/upload/simpleuploader?Type=Image " 
	linkUploadURL= "/FCKeditor/editor/filemanager/upload/simpleuploader?Type=File " 
	flashUploadURL= "/FCKeditor/editor/filemanager/upload/simpleuploader?Type=Flash "> 
        </FCK:editor> 
<input type="submit" value="Submit">
</form>
--%>

<form action="show.jsp" method="post" target="_blank">
<table border="0" width="700"><tr><td>
<textarea id="content" name="content" style="WIDTH: 100%; HEIGHT: 400px">input</textarea>
<script type="text/javascript">
 var oFCKeditor = new FCKeditor('content') ;
 oFCKeditor.BasePath = "<%= request.getContextPath() %>/FCKeditor/" ;
 oFCKeditor.Height = 400;
 oFCKeditor.ToolbarSet = "Default" ; 
 oFCKeditor.ReplaceTextarea();
</script>
<input type="submit" value="Submit">
</td></tr></table>
</form>

<%--
<form action="show.jsp" method="post" target="_blank">
<%
FCKeditor oFCKeditor ;
oFCKeditor = new FCKeditor( request, "content" ) ;
oFCKeditor.setBasePath( "/FCKeditor/" ) ;
oFCKeditor.setValue( "input" );
out.println( oFCKeditor.create() ) ;
%>
<br>
<input type="submit" value="Submit">
</form>
--%>