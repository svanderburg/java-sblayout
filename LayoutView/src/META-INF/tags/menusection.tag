<%@ tag description="Displays a menu section containing links to sub pages"
	language="java"
	import="io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.section.*, io.github.svanderburg.layout.model.page.*, io.github.svanderburg.layout.model.page.subpages.*"
%>
<%@ attribute name="app" required="true" type="Application" description="Encoding of the web application layout and pages" %>
<%@ attribute name="menuSection" required="true" type="MenuSection" description="Menu section to display" %>
<%@ attribute name="route" required="true" type="Route" description="Route from the entry page to current page to be displayed" %>
<%
if(menuSection.getLevel() <= route.size())
{
	String subPath = route.composeBaseURL(menuSection.getLevel());
	Page rootPage = route.getPage(menuSection.getLevel());
	
	// Display links to the sub pages
	
	if(rootPage instanceof ExtendablePage)
	{
		ExtendablePage extendablePage = (ExtendablePage)rootPage;
		
		for(String subId : extendablePage.subPageKeys())
		{
			Page subPage = extendablePage.getSubPage(subId);
			
			if(subPage.checkVisibleInMenu())
			{
				if(subPage instanceof ExternalPage)
				{
					ExternalPage externalPage = (ExternalPage)subPage;
					%>
					<a href="<%= externalPage.getUrl() %>"><%= externalPage.getTitle() %></a>
					<%
				}
				else
				{
					%>
					<a<%if(route.hasVisitedPageOnLevel(subId, menuSection.getLevel())) { out.print(" class=\"active\""); }%> href="<%= request.getContextPath()+request.getServletPath()+"/"+subPath+subId %>"><%= subPage.getTitle() %></a>
					<%
				}
			}
		}
	}
}
%>
