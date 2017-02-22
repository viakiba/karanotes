<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>测试页</title>
  </head>
  
  <body>
    <form action="user/register" method="post">
    	<input type="text" name="user">
    	<input type="submit" value="提交"  ></button>
    </form>
  </body>
</html>
