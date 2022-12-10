<%@ page language="java"
	import="io.github.svanderburg.layout.model.page.*"
	trimDirectiveWhitespaces="true"
%>

<%
boolean active = (Boolean)request.getAttribute("active");
String url = (String)request.getAttribute("url");
Page subPage = (Page)request.getAttribute("subPage");

if(active)
{
	%>
	<a class="alternative_link active" href="<%= url %>"><em><%= subPage.getTitle() %></em></a>
	<%
}
else
{
	%>
	<a class="alternative_link" href="<%= url %>"><%= subPage.getTitle() %></a>
	<%
}
%>
