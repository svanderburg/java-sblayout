package io.github.svanderburg.layout.view;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import io.github.svanderburg.layout.model.*;
import io.github.svanderburg.layout.model.page.*;

/**
 * A servlet that processes all requests, lookups the requested page components and displays it.
 */
public class IndexServlet extends HttpServlet
{
	private static final long serialVersionUID = -9166368655953754971L;

	/**
	 * Creates an object with default parameters used by the layout framework.
	 *
	 * @param req HTTP request object that contains request parameters
	 * @return A hash map with default parameters
	 */
	protected static HashMap<String, Object> createParams(HttpServletRequest req)
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		HashMap<String, String> query = new HashMap<String, String>();
		String acceptLanguage = req.getHeader("Accept-Language");
		params.put("query", query);
		params.put("acceptLanguage", acceptLanguage);
		req.setAttribute("query", query);
		return params;
	}

	/**
	 * Looks up the route to the currently requested page.
	 *
	 * @param application Encoding of the web application layout and pages
	 * @param req HTTP request object that contains request parameters
	 * @param resp HTTP response object to which the output is sent
	 * @return Route to the page to be currently displayed
	 * @throws PageException when the lookup fails
	 */
	protected static Route determineRoute(Application application, HttpServletRequest req, HttpServletResponse resp) throws PageException
	{
		HashMap<String, Object> params = createParams(req);
		return application.determineRoute(req.getRequestURI(), req.getContextPath(), req.getServletPath(), params);
	}
	
	/**
	 * Directs the user to an error page.
	 *
	 * @param application Encoding of the web application layout and pages
	 * @param ex Exception that causes the redirect
	 * @param req HTTP request object that contains request parameters
	 * @param resp HTTP response object to which the output is sent
	 * @return Route to the error page
	 */
	protected static Route redirectToErrorPage(Application application, PageException ex, HttpServletRequest req, HttpServletResponse resp)
	{
		HashMap<String, Object> params = createParams(req);
		resp.setStatus(ex.getStatusCode());
		req.setAttribute("error", ex.getDisplayMessage());
		return application.determineErrorRoute(ex, params);
	}
	
	/**
	 * Displays the controller page that handles GET and POST parameters
	 *
	 * @param currentPage Page to be currently displayed
	 * @param req HTTP request object that contains request parameters
	 * @param resp HTTP response object to which the output is sent
	 * @throws ServletException
	 * @throws IOException
	 * @throws PageException if an error occured while executing the controller
	 */
	protected static void displayController(Page currentPage, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, PageException
	{
		// Include controller page if one is defined
		if(currentPage instanceof ContentPage)
		{
			ContentPage contentPage = (ContentPage)currentPage;
			String controller = contentPage.getContents().getController();
			
			if(controller != null)
			{
				req.getRequestDispatcher(controller).include(req, resp);
				if(req.getAttribute("ex") != null)
					throw ((PageException)req.getAttribute("ex"));
			}
		}
	}
	
	/**
	 * Looks up the requested page and dispatches the request to the page that displays its sections
	 * and contents.
	 * 
	 * @param application Encoding of the web application layout and pages
	 * @param req HTTP request object that contains request parameters
	 * @param resp HTTP response object to which the output is sent
	 * @throws ServletException
	 * @throws IOException
	 */
	protected static void dispatchLayoutView(Application application, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Route route;
		Page currentPage;
		
		try
		{
			route = determineRoute(application, req, resp);
			currentPage = route.determineCurrentPage();
			displayController(currentPage, req, resp);
		}
		catch(PageException ex)
		{
			route = redirectToErrorPage(application, ex, req, resp);
			currentPage = route.determineCurrentPage();
		}
		
		// Return HTML mime type
		resp.setContentType("text/html");
		resp.setCharacterEncoding(application.getCharset());
		
		// Include the view page
		req.setAttribute("app", application);
		req.setAttribute("route", route);
		req.setAttribute("currentPage", currentPage);
		req.getRequestDispatcher("/WEB-INF/index.jsp").include(req, resp);
	}
}
