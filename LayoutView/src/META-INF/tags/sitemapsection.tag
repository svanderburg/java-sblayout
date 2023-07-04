<%@ tag description="Displays a sitemap section containing links to sub pages and transitive sub pages"
	language="java"
	import="java.util.*, io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.section.*"
	trimDirectiveWhitespaces="true"
%>
<%@ attribute name="siteMapSection" required="true" type="SiteMapSection" description="Site map section to display" %>
<%@ attribute name="route" required="true" type="Route" description="Route from the entry page to current page to be displayed" %>

<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<%
if(siteMapSection.getLevel() <= route.size())
{
	String contextPath = request.getContextPath() + request.getServletPath();
	String baseURL = route.composeURLAtLevel(contextPath, siteMapSection.getLevel());
	%>
	<layout:sitemap route="<%= route %>" displayMenuItems="<%= true %>" baseURL="<%= baseURL %>" level="<%= siteMapSection.getLevel() %>" contextPath="<%= contextPath %>" />
	<%
}
%>
