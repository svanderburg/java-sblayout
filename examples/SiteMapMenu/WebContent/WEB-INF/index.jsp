<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="io.github.svanderburg.layout.model.*,io.github.svanderburg.layout.model.page.*, test.*"
	trimDirectiveWhitespaces="true"
%>
<%
Application app = (Application)request.getAttribute("app");
Route route = (Route)request.getAttribute("route");
%>
<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>
<layout:index app="<%= app %>" route="<%= route %>" contextPath="<%= getServletContext().getContextPath() %>" />
