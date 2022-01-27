<%@ tag description="Displays a simple HTML page containing the sections defined in the application layout"
	language="java"
	import="io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.section.*, io.github.svanderburg.layout.model.page.*"
%>
<%@ attribute name="app" required="true" type="Application" description="Encoding of the web application layout and pages" %>
<%@ attribute name="route" required="true" type="Route" description="Route from the entry page to the current page to be displayed" %>
<%@ attribute name="contextPath" required="false" type="String" description="Context path of the servlet" %>

<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<%
Page currentPage = route.determineCurrentPage();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title><%= currentPage.getTitle() %> - <%= app.getTitle() %></title>
		<meta http-equiv="Content-Type" content="text/html; charset=<%= app.getCharset() %>">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%
		if(app.getIcon() != null)
		{
			%>
			<link rel="shortcut icon" href="<%= contextPath + "/" + app.getIcon() %>">
			<%
		}
		%>
		<layout:styles app="<%= app %>" currentPage="<%= currentPage %>" />
		<layout:scripts app="<%= app %>" currentPage="<%= currentPage %>" />
	</head>
	<body>
		<%
		for(String sectionId : app.sectionKeys())
		{
			Section section = app.getSection(sectionId);
			%>
			<layout:section id="<%= sectionId %>" app="<%= app %>" section="<%= section %>" route="<%= route %>" currentPage="<%= currentPage %>" />
			<%
		}
		%>
	</body>
</html>
