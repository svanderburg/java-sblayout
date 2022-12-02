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
Sub page 1
</p>
