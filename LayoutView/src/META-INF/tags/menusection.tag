<%@ tag description="Displays a menu section containing links to sub pages"
	language="java"
	import="java.util.*, io.github.svanderburg.layout.model.*, io.github.svanderburg.layout.model.section.*"
	trimDirectiveWhitespaces="true"
%>
<%@ attribute name="app" required="true" type="Application" description="Encoding of the web application layout and pages" %>
<%@ attribute name="menuSection" required="true" type="MenuSection" description="Menu section to display" %>
<%@ attribute name="route" required="true" type="Route" description="Route from the entry page to current page to be displayed" %>

<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<layout:inlinemenusection route="<%= route %>" level="<%= menuSection.getLevel() %>" />
