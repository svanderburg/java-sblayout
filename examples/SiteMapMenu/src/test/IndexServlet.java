package test;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import io.github.svanderburg.layout.model.*;
import io.github.svanderburg.layout.model.page.*;
import io.github.svanderburg.layout.model.page.content.*;
import io.github.svanderburg.layout.model.section.*;

public class IndexServlet extends io.github.svanderburg.layout.view.IndexServlet
{
	private static final Application application = new Application(
		/* Title */
		"Site map menu website",
		
		/* CSS stylesheets */
		new String[] { "default.css" },
		
		/* Pages */
		new StaticContentPage("Home", new Contents("home.jsp"))
			.addSubPage("403", new HiddenStaticContentPage("Forbidden", new Contents("error/403.jsp")))
			.addSubPage("404", new HiddenStaticContentPage("Page not found", new Contents("error/404.jsp")))
			
			.addSubPage("page1", new StaticContentPage("Page 1", new Contents("page1.jsp"))
				.addSubPage("subpage11", new StaticContentPage("Subpage 1.1", new Contents("page1/subpage11.jsp")))
				.addSubPage("subpage12", new StaticContentPage("Subpage 1.2", new Contents("page1/subpage12.jsp"))))
			
			.addSubPage("page2", new StaticContentPage("Page 2", new Contents("page2.jsp"))
				.addSubPage("subpage21", new StaticContentPage("Subpage 2.1", new Contents("page2/subpage21.jsp")))
				.addSubPage("subpage22", new StaticContentPage("Subpage 2.2", new Contents("page2/subpage22.jsp"))))
			
			.addSubPage("page3", new StaticContentPage("Page 3", new Contents("page3.jsp"))),

		/* Favorite icon */
		"favicon.ico",

		/* JavaScript includes */
		new String[] { "mobilenavmenu.js" }
	)
	/* Sections */
	.addSection("header", new StaticSection("header.jsp"))
	.addSection("menu", new SiteMapSection(0))
	.addSection("contents", new ContentsSection(true));

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		dispatchLayoutView(application, req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		dispatchLayoutView(application, req, resp);
	}
}
