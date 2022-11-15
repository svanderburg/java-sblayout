package test;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import io.github.svanderburg.layout.model.*;

/**
 * Servlet implementation class FormServlet
 */
public class FormServlet extends HttpServlet
{
	private static final long serialVersionUID = -3795042001117148078L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FormServlet()
	{
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		
		if(firstname == null || firstname.equals("") ||
			lastname == null || lastname.equals(""))
			request.setAttribute("ex", new BadRequestException("This page requires a firstname and lastname parameter!"));
		else
		{
			String fullname = firstname + " " + lastname;
			request.setAttribute("fullname", fullname);
		}
	}
}
