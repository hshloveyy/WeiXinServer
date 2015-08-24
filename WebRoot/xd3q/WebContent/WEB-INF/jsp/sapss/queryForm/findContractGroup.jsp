<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同组查询页面</title>
</head>
<body>
<div id="grid"></div>
<script type="text/javascript">
</script>
</body>
</html>
<fiscxd:grid width="590" headers="合同组Id,合同组名,合同组编号,项目名,注释" columns="CONTRACT_GROUP_ID,CONTRACT_GROUP_NAME,CONTRACT_GROUP_NO,PROJECT_NAME,CMD" pageSize="10" divId="grid" height="280" 
	sql="select * from t_contract_group tcg where tcg.project_id='${projectId}' and tcg.is_available=1"></fiscxd:grid>
