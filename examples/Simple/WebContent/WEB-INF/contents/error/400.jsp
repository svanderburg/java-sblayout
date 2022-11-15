<%
String error = (String)request.getAttribute("error");

if(error == null)
{
	%>
	<p>The request could not be processed due to an invalid parameter!</p>
	<%
}
else
{
	%>
	<p><%= error %></p>
	<%
}
%>
