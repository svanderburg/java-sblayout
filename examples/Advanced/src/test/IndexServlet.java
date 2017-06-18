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
	private static final long serialVersionUID = 7517017100281935224L;
	
	private static final Application application = new Application(
		/* Title */
		"Fancy test website",
		
		/* CSS stylesheets */
		new String[] { "default.css" },
		
		/* Site items */
		new StaticContentPage("Home", new Contents()
			.addSection("contents", "home.jsp")
			.addSection("header", "home.jsp"))
			.addSubPage("403", new HiddenStaticContentPage("Forbidden", new Contents()
				.addSection("contents", "error/403.jsp")
				.addSection("header", "home.jsp")))
			.addSubPage("404", new HiddenStaticContentPage("Page not found", new Contents()
				.addSection("contents", "error/404.jsp")
				.addSection("header", "home.jsp")))
				
			.addSubPage("home", new PageAlias("Home", ""))
			
			.addSubPage("styles", new StaticContentPage("Styles", new Contents()
				.addSection("contents", "styles.jsp")
				.addSection("header", "home.jsp"))
				.addSubPage("red", new StaticContentPage("Red", new Contents(new String[] { "styles/red.css" }, null)
					.addSection("contents", "styles/red.jsp")
					.addSection("header", "home.jsp")))
				.addSubPage("blue", new StaticContentPage("Blue", new Contents(new String[] { "styles/blue.css" }, null)
					.addSection("contents", "styles/blue.jsp")
					.addSection("header", "home.jsp")))
				.addSubPage("green", new StaticContentPage("Green", new Contents(new String[] { "styles/green.css" }, null)
					.addSection("contents", "styles/green.jsp")
					.addSection("header", "home.jsp"))))
			
			.addSubPage("scripts", new StaticContentPage("Scripts", new Contents()
				.addSection("contents", "scripts.jsp")
				.addSection("header", "home.jsp"))
				.addSubPage("one", new StaticContentPage("One", new Contents(null, new String[] { "scripts/one.js" })
					.addSection("contents", "scripts/one.jsp")
					.addSection("header", "home.jsp")))
				.addSubPage("two", new StaticContentPage("Two", new Contents(null, new String[] { "scripts/two.js" })
					.addSection("contents", "scripts/two.jsp")
					.addSection("header", "home.jsp"))))
			
			.addSubPage("header", new StaticContentPage("Header", new Contents()
				.addSection("contents", "header.jsp")
				.addSection("header", "home.jsp"))
				.addSubPage("first", new StaticContentPage("First", new Contents()
					.addSection("contents", "header/first.jsp")
					.addSection("header", "header/first.jsp")))
				.addSubPage("second", new StaticContentPage("Second", new Contents()
					.addSection("contents", "header/second.jsp")
					.addSection("header", "header/second.jsp")))),
		/* Favorite icon */
		"favicon.ico",
		
		/* JavaScript includes */
		new String[] { "hello.js" }
	)
	/* Sections */
	.addSection("header", new ContentsSection(false))
	.addSection("menu", new MenuSection(0))
	.addSection("submenu", new MenuSection(1))
	.addSection("contents", new ContentsSection(true))
	.addSection("footer", new StaticSection("footer.jsp"));
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		dispatchLayoutView(application, req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		dispatchLayoutView(application, req, resp);
	}
}
