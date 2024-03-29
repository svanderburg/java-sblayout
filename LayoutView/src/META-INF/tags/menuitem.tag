<%@ tag description="Displays a menu item"
	language="java"
	import="io.github.svanderburg.layout.model.page.*"
	trimDirectiveWhitespaces="true"
%>
<%@ attribute name="active" required="true" type="Boolean" description="Indicates whether the link is active" %>
<%@ attribute name="url" required="true" type="String" description="URL of the sub page" %>
<%@ attribute name="subPage" required="true" type="Page" description="Page where the menu item links to" %>

<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<%
if(subPage.getMenuItem() == null)
{
	%>
	<layout:standardmenuitem active="<%= active %>" url="<%= url %>" subPage="<%= subPage %>" />
	<%
}
else
{
	%>
	<layout:custommenuitem active="<%= active %>" url="<%= url %>" subPage="<%= subPage %>" />
	<%
}
%>
