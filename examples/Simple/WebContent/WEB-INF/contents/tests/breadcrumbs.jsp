<%@ page language="java"
	import="io.github.svanderburg.layout.model.*"
	trimDirectiveWhitespaces="true"
%>
<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<%
Route route = (Route)request.getAttribute("route");
String contextPath = request.getContextPath() + request.getServletPath();
%>
<p>From level 0 with root page:</p>
<layout:breadcrumbs route="<%= route %>" startIndex="0" displayRoot="true" contextPath="<%= contextPath %>" />

<p>From level 0 without root page:</p>
<layout:breadcrumbs route="<%= route %>" startIndex="0" displayRoot="false" contextPath="<%= contextPath %>" />

<p>From level 1:</p>
<layout:breadcrumbs route="<%= route %>" startIndex="1" displayRoot="false" contextPath="<%= contextPath %>" />
