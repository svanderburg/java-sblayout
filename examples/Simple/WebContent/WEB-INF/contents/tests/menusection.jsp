<%@ page language="java"
	import="io.github.svanderburg.layout.model.*"
	trimDirectiveWhitespaces="true"
%>
<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>
<%
Route route = (Route)request.getAttribute("route");
%>
<layout:embeddedmenusection route="<%= route %>" level="2" />
<p>
This page contains an embedded menu section displaying sub pages on the second level allowing you to navigate to sub pages stored under this sub page.
</p>
