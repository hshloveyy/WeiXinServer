<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/common/commons.jsp"%>
<%@page import="com.infolion.sapss.Constants"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>上传文件</title>

 
</head>
<body>  

<form action="<%=Constants.FILE_LOCATION_URL%>/attachementController.spr?action=upload3&cburl=http://<%= request.getServerName() %>:<%= request.getServerPort() %><%= request.getContextPath() %>/attachementController.spr&callback=uploadManger" id="form1" name="form1" encType="multipart/form-data"  method="post" target="hidden_frame" >  
	<br> 
	文件名：
    <input type="file" id="uploadFile" name="uploadFile" style="width:250"> 
    <br> 
    <font color="red">支持所有文件的上传，大小不能超过4M</font>  
    <br>
    <br>
    <div align="center">
    <INPUT type="submit" value="上传文件"><span id="msg"></span> 
     <INPUT type="button" value="关闭" onclick="window.close();">
     </div>
    <input type="hidden" name="id" value="${id}"/>
    <input type="hidden" name="userId" value="${userId}"/>
    <input type="hidden" name="boname" value="${boname}"/>  
    <br>  
    
    
</form>  

   <iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe> 

</body>  


<script type="text/javascript">

	function callback(path){
		window.returnValue=path;
		window.close();
		
	}  
	var success = '${success}';
	if(success == 'true'){
		callback('${path}');
	}
    if(success == 'fasle'){
    	alert('上传失败');
    }
   
</script>
</html>

