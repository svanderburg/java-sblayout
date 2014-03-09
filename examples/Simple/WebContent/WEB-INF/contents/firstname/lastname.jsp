<%@ page import="java.util.*" %>
<%
HashMap<String, String> query = (HashMap<String, String>)request.getAttribute("query");
%>
<p>
Hey <%= query.get("firstname") %>, you can also pass your last name as parameter!
</p>
