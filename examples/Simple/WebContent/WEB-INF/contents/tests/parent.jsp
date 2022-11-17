<%@ page language="java"
	import="io.github.svanderburg.layout.model.*"
	trimDirectiveWhitespaces="true"
%>

<%
Route route = (Route)request.getAttribute("route");
String contextPath = request.getContextPath() + request.getServletPath();
String parentURL = route.composeParentPageURL(contextPath);
%>

<p><a href="<%= parentURL %>">Go to the parent URL</a></p>
