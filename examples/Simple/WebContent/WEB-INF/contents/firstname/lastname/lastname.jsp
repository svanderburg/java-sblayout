<%@ page import="java.util.*" %>
<%
HashMap<String, String> query = (HashMap<String, String>)request.getAttribute("query");
%>
<p>
Hi <%= query.get("firstname") %> <%= query.get("lastname") %>!
</p>
