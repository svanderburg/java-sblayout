package io.github.svanderburg.layout.model;

import io.github.svanderburg.layout.model.page.*;
import io.github.svanderburg.layout.model.section.*;

import java.util.*;

/**
 * Encodes the structure of a web application system which pages have common sections, common style settings
 * and a collection of pages displaying content.
 */
public class Application implements SectionManager
{
	/** Title of the entire application */
	private String title;
	
	/** An array of CSS stylesheets used for all pages */
	private String[] styles;
	
	/** The entry page of the application (which itself may refer to other sub pages) */
	private Page entryPage;

	/** The favorite icon the page should use */
	private String icon;

	/** An array of JavaScript files included by all pages */
	private String[] scripts;
	
	/** The character encoding standard that the page should use (defaults to UTF-8) */
	private String charset;
	
	/** An object used to add sections to this object */
	private SectionExtender sectionExtender;
	
	/**
	 * Creates a new application instance.
	 * 
	 * @param title Title of the entire application
	 * @param styles An array of CSS stylesheets used for all pages
	 * @param entryPage The entry page of the application
	 */
	public Application(String title, String[] styles, Page entryPage)
	{
		this(title, styles, entryPage, null, null, null);
	}
	
	/**
	 * Creates a new application instance.
	 *
	 * @param title Title of the entire application
	 * @param styles An array of CSS stylesheets used for all pages
	 * @param entryPage The entry page of the application
	 * @param icon The favorite icon the page should use
	 */
	public Application(String title, String[] styles, Page entryPage, String icon)
	{
		this(title, styles, entryPage, icon, null, null);
	}
	
	/**
	 * Creates a new application instance.
	 *
	 * @param title Title of the entire application
	 * @param styles An array of CSS stylesheets used for all pages
	 * @param entryPage The entry page of the application
	 * @param icon The favorite icon the page should use
	 * @param scripts An array of JavaScript files included by all pages
	 */
	public Application(String title, String[] styles, Page entryPage, String icon, String[] scripts)
	{
		this(title, styles, entryPage, icon, scripts, null);
	}
	
	/**
	 * Creates a new application instance.
	 * 
	 * @param title Title of the entire application
	 * @param styles An array of CSS stylesheets used for all pages
	 * @param entryPage The entry page of the application
	 * @param icon The favorite icon the page should use
	 * @param scripts An array of JavaScript files included by all pages
	 * @param charset The character encoding standard that the page should use
	 */
	public Application(String title, String[] styles, Page entryPage, String icon, String[] scripts, String charset)
	{
		this.title = title;
		this.styles = styles;
		this.entryPage = entryPage;
		this.icon = icon;
		this.scripts = scripts;
		if(charset == null)
			this.charset = "UTF-8";
		else
			this.charset = charset;
		sectionExtender = new SectionExtender();
	}
	
	/**
	 * Returns the title of the entire application
	 *
	 * @return Title of the entire application
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * Returns the entry page
	 * 
	 * @return The entry page of the application
	 */
	public Page getEntryPage()
	{
		return entryPage;
	}
	

	/**
	 * Derives the route to an error page.
	 *
	 * @param cause The page exception that triggered the error page
	 * @param params A hash map containing additional parameters
	 * @return The error page route
	 */
	public Route determineErrorRoute(PageException cause, HashMap<String, Object> params)
	{
		String[] errorPath = new String[1];
		errorPath[0] = "" + cause.getStatusCode();
		Route route = new Route(errorPath);
		
		try
		{
			entryPage.examineRoute(this, route, params);
			return route;
		}
		catch(PageException ex)
		{
			System.err.println(ex);
		}
		
		return null;
	}
	
	/**
	 * Examines a route derived from the path components of the requested URL and records all pages visited.
	 *
	 * @param route Route to investigate
	 * @param params A hash map containing additional parameters
	 * @throws PageException In case an error occured, such as the page cannot be found or access is restricted
	 */
	
	public void examineRoute(Route route, HashMap<String, Object> params) throws PageException
	{
		entryPage.examineRoute(this, route, params);
	}
	
	/**
	 * Determines the route from the entry page to the requested page derived from the path components of the requested URL.
	 *
	 * @param requestURL Contains the URL that has been requested
	 * @param contextPath Contains the path to web application
	 * @param servletPath Contains the path to the servlet
	 * @param params A hash map containing additional parameters
	 * @return A route that records all visited pages
	 * @throws PageException In case an error occured, such as the page cannot be found or access is restricted
	 */
	
	public Route determineRoute(String requestURL, String contextPath, String servletPath, HashMap<String, Object> params) throws PageException
	{
		String sitePath = contextPath+"/"+servletPath;
		String[] ids;
		
		if(requestURL.length() > sitePath.length()) /* We are not at root level */
		{
			String menuPath = requestURL.substring(sitePath.length());
			ids = menuPath.split("/");
		}
		else
			ids = new String[0];
		
		Route route = new Route(ids);
		examineRoute(route, params);
		return route;
	}
	
	/**
	 * Returns the number of CSS stylesheets used by all pages
	 * 
	 * @return Number of CSS stylesheets
	 */
	public int stylesLength()
	{
		if(styles == null)
			return 0;
		else
			return styles.length;
	}
	
	/**
	 * Retrieves a CSS stylesheet that is used by all pages
	 * 
	 * @param index Index of the stylesheet
	 * @return A string containing a path to a stylesheet
	 */
	public String getStyle(int index)
	{
		return styles[index];
	}
	
	/**
	 * Returns the icon.
	 *
	 * @return Icon path
	 */
	public String getIcon()
	{
		return icon;
	}
	
	/**
	 * Returns the number of script files included by all pages
	 * 
	 * @return The number of script files included by all pagaes
	 */
	public int scriptsLength()
	{
		if(scripts == null)
			return 0;
		else
			return scripts.length;
	}
	
	/**
	 * Returns a script file included by all pages
	 * 
	 * @param index Index of the script
	 * @return A string containing a path to a script file
	 */
	public String getScript(int index)
	{
		return scripts[index];
	}
	
	/**
	 * Adds a common section to the application layout
	 * 
	 * @param id Id of the division
	 * @param section A section to add
	 * @return A self reference to the application layout
	 */
	public Application addSection(String id, Section section)
	{
		sectionExtender.addSection(id, section);
		return this;
	}
	
	/**
	 * Returns the keys of the section hash map
	 * 
	 * @return A set of keys
	 */
	public Set<String> sectionKeys()
	{
		return sectionExtender.sectionKeys();
	}
	
	/**
	 * Returns the section with a given id
	 *
	 * @param id Id of a division
	 * @return The section with the given id
	 */
	public Section getSection(String id)
	{
		return sectionExtender.getSection(id);
	}
	
	/**
	 * Returns the charset
	 *
	 * @return Charset value
	 */
	public String getCharset()
	{
		return charset;
	}
}
