<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.Enumeration"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无权限访问提示页面</title>
</head>
<body>
  <span style="color:red"><% 
  Enumeration en = null;
  if (request != null) {
	  en = request.getAttributeNames();
	  while (en.hasMoreElements()) {
		 out.println( en.nextElement());
	  	 out.print("<br>");
	  }
  }
  %><b>您无权访问此页面</b></span>
</body>
</html>