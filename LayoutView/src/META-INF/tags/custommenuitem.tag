<%@ tag description="Displays a menu item in a custom way"
	language="java"
	import="io.github.svanderburg.layout.model.page.*"
	trimDirectiveWhitespaces="true"
%>

<%@ attribute name="active" required="true" type="Boolean" description="Indicates whether the link is active" %>
<%@ attribute name="url" required="true" type="String" description="URL of the sub page" %>
<%@ attribute name="subPage" required="true" type="Page" description="Page where the menu item links to" %>

<%
request.setAttribute("active", active);
request.setAttribute("url", url);
request.setAttribute("subPage", subPage);
%>
<jsp:include page="<%= \"/WEB-INF/menuitems/\" + subPage.getMenuItem() %>" />
