<%@ tag description="Displays a simple HTML page containing the sections defined in the application layout"
	language="java"
	import="io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.section.*, io.github.svanderburg.layout.model.page.*"
%>
<%@ attribute name="app" required="true" type="Application" description="Encoding of the web application layout and pages" %>
<%@ attribute name="currentPage" required="true" type="Page" description="Page to be displayed" %>

<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title><%= currentPage.getTitle() %> - <%= app.getTitle() %></title>
		<meta http-equiv="Content-Type" content="text/html; charset=<%= app.getCharset() %>">
		<layout:styles app="<%= app %>" currentPage="<%= currentPage %>" />
		<layout:scripts app="<%= app %>" currentPage="<%= currentPage %>" />
	</head>
	<body>
		<%
		for(String sectionId : app.sectionKeys())
		{
			Section section = app.getSection(sectionId);
			%>
			<layout:section id="<%= sectionId %>" app="<%= app %>" section="<%= section %>" currentPage="<%= currentPage %>" />
			<%
		}
		%>
	</body>
</html>
