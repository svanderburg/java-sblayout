<%@ tag description="Displays a menu section containing links to sub pages"
	language="java"
	import="java.util.*, io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.section.*, io.github.svanderburg.layout.model.page.*, io.github.svanderburg.layout.model.page.subpages.*"
	trimDirectiveWhitespaces="true"
%>
<%@ attribute name="app" required="true" type="Application" description="Encoding of the web application layout and pages" %>
<%@ attribute name="menuSection" required="true" type="MenuSection" description="Menu section to display" %>
<%@ attribute name="route" required="true" type="Route" description="Route from the entry page to current page to be displayed" %>
<%
if(menuSection.getLevel() <= route.size())
{
	String baseURL = request.getContextPath() + request.getServletPath() + route.composeBaseURL(menuSection.getLevel());
	Page rootPage = route.getPage(menuSection.getLevel());
	
	// Display links to the sub pages
	
	Iterator<String> iterator = rootPage.subPageKeyIterator();
	
	while(iterator.hasNext())
	{
		String subId = iterator.next();
		Page subPage = rootPage.getSubPage(subId);
		
		if(subPage.checkVisibleInMenu())
		{
			String url = subPage.deriveURL(baseURL, subId);
			%>
			<a<%if(route.hasVisitedPageOnLevel(subId, menuSection.getLevel())) { out.print(" class=\"active\""); }%> href="<%= url %>"><%= subPage.getTitle() %></a>
			<%
		}
	}
}
%>
