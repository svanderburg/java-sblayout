package io.github.svanderburg.layout.view;

import io.github.svanderburg.layout.model.*;
import io.github.svanderburg.layout.model.page.*;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * A servlet that processes all requests, lookups the requested page components and displays it.
 */
public class IndexServlet extends HttpServlet
{
	private static final long serialVersionUID = -9166368655953754971L;

	/**
	 * Looks up the currently requested page.
	 * If the page cannot be found it provides the 404 error page.
	 * If the page is not accessible it provides the 403 error page.
	 * 
	 * @param application Encoding of the web application layout and pages
	 * @param req HTTP request object that contains request parameters
	 * @param resp HTTP response object to which the output is sent
	 * @return Page to be currently displayed
	 */
	protected static Page lookupCurrentPage(Application application, HttpServletRequest req, HttpServletResponse resp)
	{
		Page currentPage;
		HashMap<String, Object> params = new HashMap<String, Object>();
		HashMap<String, String> query = new HashMap<String, String>();
		String acceptLanguage = req.getHeader("Accept-Language");
		params.put("query", query);
		params.put("acceptLanguage", acceptLanguage);
		
		try
		{
			currentPage = application.lookupCurrentPage(req.getRequestURI(), req.getContextPath(), req.getServletPath(), params);
			req.setAttribute("query", query);
		}
		catch(PageForbiddenException ex)
		{
			resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
			currentPage = application.lookup403Page(params);
		}
		catch(PageNotFoundException ex)
		{
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			currentPage = application.lookup404Page(params);
		}
		
		return currentPage;
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
		Page currentPage = lookupCurrentPage(application, req, resp);
		
		// Include controller page if one is defined
		if(currentPage instanceof ContentPage)
		{
			ContentPage contentPage = (ContentPage)currentPage;
			String controller = contentPage.getContents().getController();
			
			if(controller != null)
				req.getRequestDispatcher(controller).include(req, resp);
		}
		
		// Include the view page
		req.setAttribute("app", application);
		req.setAttribute("currentPage", currentPage);
		req.getRequestDispatcher("/WEB-INF/index.jsp").include(req, resp);
	}
}
