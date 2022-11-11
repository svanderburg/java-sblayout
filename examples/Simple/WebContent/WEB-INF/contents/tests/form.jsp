<%@ page trimDirectiveWhitespaces="true" %>
<%
String fullname = (String)request.getAttribute("fullname");

if(fullname == null)
{
	%>
	<p>Please enter your names:</p>
	<form action="" method="post">
		<p>
			<label>First name:</label>
			<input type="text" name="firstname">
		</p>
		<p>
			<label>Last name:</label>
			<input type="text" name="lastname">
		</p>
		<p>
			<button type="submit">Submit</button>
		</p>
	</form>
	<%
}
else
{
	%>
	<p>Hi, I believe your full name is: <%= fullname %> </p>
	<%
}
%>
