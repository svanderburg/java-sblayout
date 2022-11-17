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
	private static final long serialVersionUID = 6641153504105482668L;
	
	private static final Application application = new Application(
		/* Title */
		"Test website",
		
		/* CSS stylesheets */
		new String[] { "default.css" },
		
		/* Pages */
		new StaticContentPage("Home", new Contents("home.jsp"))
			.addSubPage("400", new HiddenStaticContentPage("Bad request", new Contents("error/400.jsp")))
			.addSubPage("403", new HiddenStaticContentPage("Forbidden", new Contents("error/403.jsp")))
			.addSubPage("404", new HiddenStaticContentPage("Page not found", new Contents("error/404.jsp")))
			
			.addSubPage("home", new PageAlias("Home", ""))
			
			.addSubPage("aliaspage1", new PageAlias("Alias page 1", "page1"))
			
			.addSubPage("inaccessible", new InaccessibleContentPage("Inaccessible", new Contents("page1.jsp")))
			
			.addSubPage("page1", new StaticContentPage("Page 1", new Contents("page1.jsp"))
				.addSubPage("subpage11", new StaticContentPage("Subpage 1.1", new Contents("page1/subpage11.jsp")))
				.addSubPage("subpage12", new StaticContentPage("Subpage 1.2", new Contents("page1/subpage12.jsp")))
				.addSubPage("subpage13", new StaticContentPage("Subpage 1.3", new Contents("page1/subpage13.jsp"))))
			
			.addSubPage("page2", new StaticContentPage("Page 2", new Contents("page2.jsp"))
				.addSubPage("subpage21", new StaticContentPage("Subpage 2.1", new Contents("page2/subpage21.jsp")))
				.addSubPage("subpage22", new StaticContentPage("Subpage 2.2", new Contents("page2/subpage22.jsp")))
				.addSubPage("subpage23", new StaticContentPage("Subpage 2.3", new Contents("page2/subpage23.jsp"))))
			
			.addSubPage("firstname", new DynamicContentPage("First name", "firstname", new Contents("firstname.jsp"), new StaticContentPage("First name", new Contents("firstname/firstname.jsp"))
				.addSubPage("lastname", new DynamicContentPage("Last name", "lastname", new Contents("firstname/lastname.jsp"), new StaticContentPage("Last name", new Contents("firstname/lastname/lastname.jsp"))))))
			
			.addSubPage("external", new ExternalPage("External", "http://www.google.com"))
			
			.addSubPage("tests", new StaticContentPage("Tests", new Contents("tests.jsp"))
				.addSubPage("form", new StaticContentPage("Form", new Contents("tests/form.jsp", "/tests/form.wss", null, null)))
				.addSubPage("breadcrumbs", new StaticContentPage("Bread crumbs", new Contents("tests/breadcrumbs.jsp")))
				.addSubPage("sitemap", new StaticContentPage("Site map", new Contents("tests/sitemap.jsp")))
				.addSubPage("parent", new StaticContentPage("Parent", new Contents("tests/parent.jsp")))),
			
		/* Favorite icon */
		"favicon.ico"
	)
	/* Sections */
	.addSection("header", new StaticSection("header.jsp"))
	.addSection("menu", new MenuSection(0))
	.addSection("container", new CompoundSection()
		.addSection("submenu", new MenuSection(1))
		.addSection("contents", new ContentsSection(true)));

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		dispatchLayoutView(application, req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		dispatchLayoutView(application, req, resp);
	}
}
