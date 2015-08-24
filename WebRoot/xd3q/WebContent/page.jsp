<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
     <% 
  String start = request.getParameter("start"); 
  String limit = request.getParameter("limit");

int index = Integer.parseInt(start); 
  int pageSize = Integer.parseInt(limit); 
  String json = "{totalProperty:100,root:["; 
  String sex="";
  for (int i = index; i < pageSize + index; i++) { 
  if(i%2==0)
  {
   sex="male";
  }
  else{
   sex="female";
  }
    json += "{DICT_ID:" + i + ",DICT_NAME:'" + i + "',MEMO:'"+sex+"',DICT_TYPE:" + i 
    + "}"; 
    if (i != pageSize + index - 1) { 
    json += ","; 
    } 
  } 
  json += "]}"; 
  response.getWriter().write(json); 
  System.out.print(json); 
  %>