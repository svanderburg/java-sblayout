<%@ tag description="Displays an inline representation of a menu section"
	language="java"
	import="java.util.*, io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.page.*"
	trimDirectiveWhitespaces="true"
%>
<%@ attribute name="route" required="true" type="Route" description="Route from the entry page to current page to be displayed" %>
<%@ attribute name="level" required="true" type="Integer" description="The level in the navigation structure where to display sub page links from" %>

<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<%
if(level <= route.size())
{
	String contextPath = request.getContextPath() + request.getServletPath();
	String baseURL = route.composeURLAtLevel(contextPath, level);
	Page rootPage = route.getPage(level);
	
	// Display links to the sub pages
	
	Iterator<String> iterator = rootPage.subPageKeyIterator();
	
	while(iterator.hasNext())
	{
		String subId = iterator.next();
		Page subPage = rootPage.getSubPage(subId);
		
		if(subPage.checkVisibleInMenu())
		{
			String url = subPage.deriveURL(baseURL, subId, "&amp;");
			boolean active = subPage.checkActive(route, subId, level);
			
			if(subPage.getMenuItem() == null)
			{
				%>
				<layout:menuitem active="<%= active %>" url="<%= url %>" subPage="<%= subPage %>" />
				<%
			}
			else
			{
				%>
				<layout:custommenuitem active="<%= active %>" url="<%= url %>" subPage="<%= subPage %>" />
				<%
			}
		}
	}
}
%>
