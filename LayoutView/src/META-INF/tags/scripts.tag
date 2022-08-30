<%@ tag description="Displays a section that includes JavaScript files both on application and page level"
	language="java"
	import="io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.page.*"
	trimDirectiveWhitespaces="true"
%>
<%@ attribute name="app" required="true" type="Application" description="Encoding of the web application layout and pages" %>
<%@ attribute name="currentPage" required="true" type="Page" description="Page to be currently displayed" %>

<%!
public String determineScriptPath(String script, String contextPath)
{
	if(script.length() > 0 && script.charAt(0) == '/')
		return script;
	else
		return contextPath + "/scripts/" + script;
}
%>
<%
for(int i = 0; i < app.scriptsLength(); i++)
{
	String scriptPath = determineScriptPath(app.getScript(i), request.getContextPath());
	%>
	<script type="text/javascript" src="<%= scriptPath %>"></script>
	<%
}

if(currentPage instanceof ContentPage)
{
	ContentPage contentPage = (ContentPage)currentPage;
	
	for(int i = 0; i < contentPage.getContents().scriptsLength(); i++)
	{
		String scriptPath = determineScriptPath(contentPage.getContents().getScript(i), request.getContextPath());
		%>
		<script type="text/javascript" src="<%= scriptPath %>"></script>
		<%
	}
}
%>
