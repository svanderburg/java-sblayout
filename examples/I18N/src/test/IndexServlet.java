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
		"Internationalised test website",
		
		/* CSS stylesheets */
		new String[] { "default.css" },
		
		/* Pages */
		new LocalizedContentPage()
			.addSubPage("nl", new StaticContentPage("Nederlands", new Contents("nl.jsp")))
			.addSubPage("en-us", new StaticContentPage("American", new Contents("en-us.jsp")))
			.addSubPage("en-gb", new StaticContentPage("British", new Contents("en-gb.jsp")))
			.addSubPage("fr", new StaticContentPage("Français", new Contents("fr.jsp")))
			.addSubPage("de", new StaticContentPage("Deutsch", new Contents("de.jsp")))
			
			.addSubPage("inaccessible", new InaccessibleContentPage("Inaccessible", new Contents("nl.jsp")))
			
			.addSubPage("404", new HiddenLocalizedContentPage()
				.addSubPage("nl", new HiddenStaticContentPage("Pagina niet gevonden", new Contents("error/404/nl.jsp")))
				.addSubPage("en-us", new HiddenStaticContentPage("Page not found", new Contents("error/404/en-us.jsp")))
				.addSubPage("en-gb", new HiddenStaticContentPage("Page not found", new Contents("error/404/en-gb.jsp")))
				.addSubPage("fr", new HiddenStaticContentPage("Page non trouvée", new Contents("error/404/fr.jsp")))
				.addSubPage("de", new HiddenStaticContentPage("Seite nicht gefunden", new Contents("error/404/de.jsp"))))
			
			.addSubPage("403", new HiddenLocalizedContentPage()
				.addSubPage("nl", new HiddenStaticContentPage("Verboden", new Contents("error/403/nl.jsp")))
				.addSubPage("en-us", new HiddenStaticContentPage("Forbidden", new Contents("error/403/en-us.jsp")))
				.addSubPage("en-gb", new HiddenStaticContentPage("Forbidden", new Contents("error/403/en-gb.jsp")))
				.addSubPage("fr", new HiddenStaticContentPage("Interdit", new Contents("error/403/fr.jsp")))
				.addSubPage("de", new HiddenStaticContentPage("Verboten", new Contents("error/403/de.jsp")))),

		/* Favorite icon */
		"favicon.ico"
	)
	/* Sections */
	.addSection("header", new StaticSection("header.jsp"))
	.addSection("menu", new MenuSection(0))
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
