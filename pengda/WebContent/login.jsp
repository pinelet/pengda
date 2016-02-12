<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Enumeration"%>
<%@page import="org.springframework.security.core.*"%>
<%@page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
  	String errorMsg = null;
  	if (session != null && (request.getParameter("error") != null)) {
  		AuthenticationException ex = (AuthenticationException) session
  				.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);
  		errorMsg = ex != null ? ex.getMessage() : "none";
  	}
  %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<title>登录页面</title>
<LINK REL="shortcut icon" HREF="http://10.10.38.92:8080/pengda/images/favicon.ico" />
<LINK REL="Bookmark" HREF="<s:url value='/images/favicon.ico'/>" />
<script type="text/javascript" src="<s:url value='/js/jquery-1.8.0.min.js'/>"></script>  
<script type="text/javascript">
	$(document).ready(function () {   
        var url = window.parent.document.URL;  
       if (url.indexOf("layout") != -1) {//layout页面才reload  
        window.location.reload("${pageContext.request.contextPath}/main");  
       }  
    });
</script>
</head>
<body>
<br></br>登录：<br>
  <span style="color:red"><%if(errorMsg != null) out.print(errorMsg);%></span>
<form action="<s:url value='/j_spring_security_check'/>" method="post">
<table width="73%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="21%" height="37" align="center"><font size="2"
						style="">用&nbsp;户</font></td>
					<td width="75%"><input type="text" name="j_username" id="j_username" size="20"></td>
				</tr>
				<tr>
					<td height="33" align="center"><font size="2" style="">密&nbsp;码</font></td>
					<td width="75%"><input type="password" id="j_password"
						name="j_password" size="22"></td>
				</tr>
				<tr>
					<td align="center"></td>
					<td>
					<input name="button" type="submit" class="button" value="登录" />
					</td>
				</tr>
			</table>
</form>
</body>
</html>