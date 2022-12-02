<%@ tag description="Displays an embedded representation of a menu section"
	language="java"
	import="io.github.svanderburg.layout.model.*"
	trimDirectiveWhitespaces="true"
%>
<%@ attribute name="route" required="true" type="Route" description="Route from the entry page to current page to be displayed" %>
<%@ attribute name="level" required="true" type="Integer" description="The level in the navigation structure where to display sub page links from" %>

<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>

<div class="menusection"><layout:inlinemenusection route="<%= route %>" level="<%= level %>" /></div>
