<%@ tag description="Displays a site map consisting of all sub pages and transitive sub pages of a given page"
	language="java"
	import="java.util.*, io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.page.*"
	trimDirectiveWhitespaces="true"
%>
<%@ attribute name="route" required="true" type="Route" description="Route from the entry page to current page to be displayed" %>
<%@ attribute name="page" required="true" type="Page" description="Page to display the sub pages from" %>
<%@ attribute name="displayMenuItems" required="true" type="Boolean" description="Indicates whether to display each page link as a menu item or an ordinary link" %>
<%@ attribute name="baseURL" required="true" type="String" description="URL of the given page" %>
<%@ attribute name="level" required="true" type="Integer" description="Level of a menu section" %>

<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<%
Iterator<String> iterator = page.subPageKeyIterator();
boolean hasSubPages = false;

while(iterator.hasNext())
{
	String id = iterator.next();
	Page subPage = page.getSubPage(id);
	
	if(!hasSubPages)
	{
		hasSubPages = true;
		%>
		<ul>
		<%
	}
	
	if(subPage.checkVisibleInMenu())
	{
		String url = subPage.deriveURL(baseURL, id, "&amp;");
		%>
		<li>
			<%
			if(displayMenuItems)
			{
				boolean active = subPage.checkActive(route, id, level);
				%>
				<layout:menuitem active="<%= active %>" url="<%= url %>" subPage="<%= subPage %>" />
				<%
			}
			else
			{
				%>
				<layout:standardmenuitem active="<%= false %>" url="<%= url %>" subPage="<%= subPage %>" />
				<%
			}
			%>
			<layout:subsitemap route="<%= route %>" page="<%= subPage %>" displayMenuItems="<%= displayMenuItems %>" baseURL="<%= url %>" level="<%= level + 1 %>" />
		</li>
		<%
	}
}

if(hasSubPages)
{
	%>
	</ul>
	<%
}
%>
