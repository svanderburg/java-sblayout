<%@ page language="java"
	import="io.github.svanderburg.layout.model.*"
	trimDirectiveWhitespaces="true"
%>
<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<%
Route route = (Route)request.getAttribute("route");
String contextPath = request.getContextPath() + request.getServletPath();
%>

<layout:sitemap route="<%= route %>" displayMenuItems="<%= true %>" contextPath="<%= contextPath %>" />
