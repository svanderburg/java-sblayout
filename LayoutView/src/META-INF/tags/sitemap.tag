<%@ tag description="Displays a site map with all visitable links of the application"
	language="java"
	import="io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.page.*"
	trimDirectiveWhitespaces="true"
%>
<%@ attribute name="route" required="true" type="Route" description="Route from the entry page to current page to be displayed" %>
<%@ attribute name="displayMenuItems" required="false" type="Boolean" description="Indicates whether to display each page link as a menu item or an ordinary link" %>
<%@ attribute name="baseURL" required="false" type="String" description="URL of the given page" %>
<%@ attribute name="level" required="false" type="Integer" description="Level of a menu section" %>
<%@ attribute name="contextPath" required="true" type="String" description="Context path of the servlet" %>

<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<%
/* Set default values */
if(displayMenuItems == null)
	displayMenuItems = false;

if(baseURL == null)
	baseURL = contextPath;

if(level == null)
	level = 0;

Page rootPage = route.getPage(level);

if(displayMenuItems)
{
	%>
	<layout:menuitem active="<%= true %>" url="<%= baseURL %>" subPage="<%= rootPage %>" />
	<%
}
else
{
	%>
	<layout:standardmenuitem active="<%= false %>" url="<%= baseURL %>" subPage="<%= rootPage %>" />
	<%
}
%>
<layout:subsitemap route="<%= route %>" page="<%= rootPage %>" displayMenuItems="<%= displayMenuItems %>" baseURL="<%= baseURL %>" level="<%= level %>" />
