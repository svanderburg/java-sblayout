<%@ tag description="Displays a section"
	language="java"
	import="io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.page.*, io.github.svanderburg.layout.model.section.*"
	trimDirectiveWhitespaces="true"
%>
<%@ attribute name="app" required="true" type="Application" description="Encoding of the web application layout and pages" %>
<%@ attribute name="id" required="true" type="String" description="Id of the section to be displayed" %>
<%@ attribute name="section" required="true" type="Section" description="Section to be displayed" %>
<%@ attribute name="route" required="true" type="Route" description="Route from the entry page to the current page to be displayed" %>
<%@ attribute name="currentPage" required="true" type="Page" description="Page to be currently displayed" %>

<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<div id="<%= id %>"><%
	if(section instanceof StaticSection)
	{
		%>
		<jsp:include page="<%= \"sections/\"+((StaticSection)section).getContents() %>" />
		<%
	}
	else if(section instanceof SiteMapSection)
	{
		%>
		<layout:sitemapsection siteMapSection="<%= (SiteMapSection)section %>" route="<%= route %>" />
		<%
	}
	else if(section instanceof MenuSection)
	{
		%>
		<layout:menusection app="<%= app %>" menuSection="<%= (MenuSection)section %>" route="<%= route %>" />
		<%
	}
	else if(section instanceof ContentsSection)
	{
		if(((ContentsSection)section).isTitleDisplayed())
		{
			%>
			<h1><%= currentPage.getTitle() %></h1>
			<%
		}
		
		if(currentPage instanceof ContentPage)
		{
			ContentPage contentPage = (ContentPage)currentPage;
			%>
			<jsp:include page="<%= id+\"/\"+contentPage.getContents().getContentsFrom(id) %>" />
			<%
		}
	}
	else if(section instanceof CompoundSection)
	{
		%>
		<layout:sections app="<%= app %>" route="<%= route %>" currentPage="<%= currentPage %>" compoundSection="<%= (CompoundSection)section %>" />
		<%
	}
%></div>
