<%@ tag description="Displays a menu item in the standard way"
	language="java"
	import="io.github.svanderburg.layout.model.page.*"
	trimDirectiveWhitespaces="true"
%>
<%@ attribute name="active" required="true" type="Boolean" description="Indicates whether the link is active" %>
<%@ attribute name="url" required="true" type="String" description="URL of the sub page" %>
<%@ attribute name="subPage" required="true" type="Page" description="Page where the menu item links to" %>

<%
if(active)
{
	%>
	<a class="active" href="<%= url %>"><strong><%= subPage.getTitle() %></strong></a>
	<%
}
else
{
	%>
	<a href="<%= url %>"><%= subPage.getTitle() %></a>
	<%
}
%>
