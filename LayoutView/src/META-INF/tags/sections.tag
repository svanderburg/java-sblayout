<%@ tag description="Displays a collection of sections"
	language="java"
	import="java.util.*, io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.page.*, io.github.svanderburg.layout.model.section.*"
%>
<%@ attribute name="app" required="true" type="Application" description="Encoding of the web application layout and pages" %>
<%@ attribute name="route" required="true" type="Route" description="Route from the entry page to the current page to be displayed" %>
<%@ attribute name="currentPage" required="true" type="Page" description="Page to be currently displayed" %>
<%@ attribute name="compoundSection" required="false" type="CompoundSection" description="Compound section in which the sections are embedded or null if they are on root level" %>

<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<%
SectionManager sectionManager;

if(compoundSection == null)
	sectionManager = app;
else
	sectionManager = compoundSection;

for(String sectionId : sectionManager.sectionKeys())
{
	Section section = sectionManager.getSection(sectionId);
	%>
	<layout:section id="<%= sectionId %>" app="<%= app %>" section="<%= section %>" route="<%= route %>" currentPage="<%= currentPage %>" />
	<%
}
%>
