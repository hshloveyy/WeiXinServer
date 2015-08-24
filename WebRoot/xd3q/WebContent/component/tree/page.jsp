<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<% 
String dept_id=request.getParameter("dept_id");
response.getWriter().print("dept_id = "+dept_id);
%>