<%@ tag description="Displays a section that includes CSS files both on application and page level"
	language="java"
	pageEncoding="UTF-8"
	import="io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.page.*"
%>
<%@ attribute name="app" required="true" type="Application" description="Encoding of the web application layout and pages" %>
<%@ attribute name="currentPage" required="true" type="Page" description="Page to be currently displayed" %>

<%!
public String determineStylePath(String style, String contextPath)
{
	if(style.length() > 0 && style.charAt(0) == '/')
		return style;
	else
		return contextPath + "/styles/" + style;
}
%>
<%
for(int i = 0; i < app.stylesLength(); i++)
{
	String stylePath = determineStylePath(request.getContextPath()+"/styles/"+app.getStyle(i), request.getContextPath());
	%>
	<link rel="stylesheet" type="text/css" href="<%= stylePath %>">
	<%
}

if(currentPage instanceof ContentPage)
{
	ContentPage contentPage = (ContentPage)currentPage;
	
	for(int i = 0; i < contentPage.getContents().stylesLength(); i++)
	{
		String stylePath = determineStylePath(request.getContextPath()+"/styles/"+contentPage.getContents().getStyle(i), request.getContextPath());
		%>
		<link rel="stylesheet" type="text/css" href="<%= stylePath %>">
		<%
	}
}
%>
