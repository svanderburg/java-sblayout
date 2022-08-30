<%@ page import="java.util.*"
	trimDirectiveWhitespaces="true" %>
<%
HashMap<String, String> query = (HashMap<String, String>)request.getAttribute("query");
%>
<p>
Hi <%= query.get("firstname") %> <%= query.get("lastname") %>!
</p>
