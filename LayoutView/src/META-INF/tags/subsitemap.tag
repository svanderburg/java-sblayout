<%@ tag description="Displays a site map of all sub pages and transitive sub pages reachable from a given page"
	language="java"
	import="java.util.*, io.github.svanderburg.layout.model.page.*"
	trimDirectiveWhitespaces="true"
%>
<%@ attribute name="page" required="true" type="Page" description="Page to display the sub pages from" %>
<%@ attribute name="baseURL" required="true" type="String" description="URL of the given page" %>

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
			<a href="<%= url %>"><%= subPage.getTitle() %></a>
			<layout:subsitemap page="<%= subPage %>" baseURL="<%= url %>" />
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
