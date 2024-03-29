<%@ tag description="Displays breadcrumbs that lead the user from an earlier page to the current page"
	language="java"
	import="io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.page.*"
	trimDirectiveWhitespaces="true"
%>
<%@ attribute name="route" required="true" type="Route" description="Route from the entry page to the current page to be displayed" %>
<%@ attribute name="startIndex" required="false" type="Integer" description="Indicates whether to include the root page" %>
<%@ attribute name="displayRoot" required="false" type="Boolean" description="Indicates whether to include the root page" %>
<%@ attribute name="contextPath" required="true" type="String" description="Context path of the servlet" %>

<p class="breadcrumbs">
	<%
	// Assign default values
	if(startIndex == null)
		startIndex = new Integer(0);

	if(displayRoot == null)
		displayRoot = false;

	boolean first = true;
	String url = contextPath;

	int routeSize = route.size();

	if(displayRoot)
	{
		Page currentPage = route.getPage(0);
		%>
		<a href="<%= url %>"<%= (routeSize == 0) ? " class=\"active\"" : "" %>><%= currentPage.getTitle() %></a>
		<%
		first = false;
	}

	for(int i = 0; i < routeSize; i++)
	{
		String currentId = route.getId(i);
		Page currentPage = route.getPage(i + 1);
		
		url = currentPage.deriveURL(url, currentId, "&amp;");
		
		if(i >= startIndex)
		{
			if(first)
				first = false;
			else
				out.print(" &raquo; ");
			%><a href="<%= url %>"<%= (i == routeSize - 1) ? " class=\"active\"" : "" %>><%= currentPage.getTitle() %></a>
			<%
		}
	}
%>
</p>
