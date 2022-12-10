<%@ page language="java"
	import="io.github.svanderburg.layout.model.page.*"
	trimDirectiveWhitespaces="true"
%>

<span>
	<%
	boolean active = (Boolean)request.getAttribute("active");
	String url = (String)request.getAttribute("url");
	Page subPage = (Page)request.getAttribute("subPage");
	
	if(active)
	{
		%>
		<a class="active" href="<%= url %>">
			<img src="<%= getServletContext().getContextPath() %>/image/menu/go-home.png" alt="Home icon">
			<strong><%= subPage.getTitle() %></strong>
		</a>
		<%
	}
	else
	{
		%>
		<a href="<%= url %>">
			<img src="<%= getServletContext().getContextPath() %>/image/menu/go-home.png" alt="Home icon">
			<%= subPage.getTitle() %>
		</a>
		<%
	}
	%>
</span>
