<%@ page language="java"
	import="io.github.svanderburg.layout.model.*"
	trimDirectiveWhitespaces="true"
%>
<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<%
Application app = (Application)request.getAttribute("app");
String contextPath = request.getContextPath() + request.getServletPath();
%>

<layout:sitemap app="<%= app %>" contextPath="<%= contextPath %>" />
