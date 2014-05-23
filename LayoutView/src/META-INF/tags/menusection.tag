<%@ tag description="Displays a menu section containing links to sub pages"
	language="java"
	import="io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.section.*, io.github.svanderburg.layout.model.page.*"
%>
<%@ attribute name="app" required="true" type="Application" description="Encoding of the web application layout and pages" %>
<%@ attribute name="menuSection" required="true" type="MenuSection" description="Menu section to display" %>

<%
if(menuSection.getLevel() <= app.menuPathIdsLength())
{
	// Seek the page which links to their sub pages must be displayed in the menusection
	Page page = app.getEntryPage();
	String subPath = "";
	
	for(int j = 0; j < menuSection.getLevel(); j++)
	{
		String currentId = app.getMenuPathId(j);
		subPath = subPath+currentId+"/";
		
		if(page instanceof StaticContentPage)
		{
			StaticContentPage staticContentPage = (StaticContentPage)page;
			page = staticContentPage.getSubPage(currentId);
		}
	}
	
	// Display links to the sub pages
	
	if(page instanceof StaticContentPage)
	{
		StaticContentPage staticContentPage = (StaticContentPage)page;
		
		for(String subId : staticContentPage.subPageKeys())
		{
			Page subPage = staticContentPage.getSubPage(subId);							
			
			if(subPage.checkVisibility() && subPage.checkAccessibility())
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
					<a<%if(app.menuPathIdsLength() > menuSection.getLevel() && app.getMenuPathId(menuSection.getLevel()).equals(subId)) { out.print(" class=\"active\""); }%> href="<%= request.getContextPath()+request.getServletPath()+"/"+subPath+subId %>"><%= subPage.getTitle() %></a>
					<%
				}
			}
		}
	}
}
%>
