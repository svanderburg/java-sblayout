<%@ tag description="Displays a site map with all visitable links of the application"
	language="java"
	import="io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.page.*"
	trimDirectiveWhitespaces="true"
%>
<%@ attribute name="app" required="true" type="Application" description="Encoding of the web application layout and pages" %>
<%@ attribute name="contextPath" required="true" type="String" description="Context path of the servlet" %>

<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<%
Page entryPage = app.getEntryPage();
String url = contextPath;
%>
<a href="<%= url %>"><%= entryPage.getTitle() %></a>
<layout:subsitemap page="<%= entryPage %>" baseURL="<%= url %>" />
