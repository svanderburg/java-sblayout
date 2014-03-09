package io.github.svanderburg.layout.model;

import io.github.svanderburg.layout.model.page.*;
import io.github.svanderburg.layout.model.section.*;

import java.util.*;

/**
 * Encodes the structure of a web application system which pages have common sections, common style settings
 * and a collection of pages displaying content.
 */
public class Application
{
	/** Title of the entire application */
	private String title;
	
	/** An array of CSS stylesheets used for all pages */
	private String[] styles;
	
	/** A hash map of sections of which every page is composed */
	private LinkedHashMap<String, Section> section;
	
	/** The entry page of the application (which itself may refer to other sub pages) */
	private Page entryPage;

	/** Contains the path id components derived from the URL */
	private String[] menuPathIds;
	
	/** An array of JavaScript files included by all pages */
	private String[] scripts;
	
	/**
	 * Creates a new application instance.
	 * 
	 * @param title Title of the entire application
	 * @param styles An array of CSS stylesheets used for all pages
	 * @param entryPage The entry page of the application
	 */
	public Application(String title, String[] styles, Page entryPage)
	{
		this.title = title;
		this.styles = styles;
		this.entryPage = entryPage;
		section = new LinkedHashMap<String, Section>();
	}
	
	/**
	 * Creates a new layout instance.
	 * 
	 * @param title Title of the entire application
	 * @param styles An array of CSS stylesheets used for all pages
	 * @param entryPage The entry page of the application
	 * @param scripts An array of JavaScript files included by all pages
	 */
	public Application(String title, String[] styles, Page entryPage, String[] scripts)
	{
		this(title, styles, entryPage);
		this.scripts = scripts;
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
	 * Lookups the 403 error page and changes the internal id components to refer to it.
	 * 
	 * @return The 403 error page
	 */
	public Page lookup403Page(HashMap<String, Object> params)
	{
		menuPathIds = new String[] { "403" };
		
		try
		{
			return entryPage.lookupSubPage(menuPathIds, params);
		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}
		return null;
	}
	
	/**
	 * Lookups the 404 error page and changes the internal id components to refer to it.
	 *
	 * @return The 404 error page
	 */
	public Page lookup404Page(HashMap<String, Object> params)
	{
		menuPathIds = new String[] { "404" };
		
		try
		{
			return entryPage.lookupSubPage(menuPathIds, params);
		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}
		return null;
	}
	
	/**
	 * Looks up the currently requested page to be displayed, by looking at the structure of the current URL
	 * 
	 * @param requestURL Contains the URL that has been requested
	 * @param contextPath Contains the path to web application
	 * @param servletPath Contains the path to the servlet
	 * @param params A hash map containing additional parameters
	 * @return The page which is currently requested
	 * @throws PageNotFoundException If the page cannot be found
	 * @throws PageForbiddenException If access to the page is restricted
	 */
	
	public Page lookupCurrentPage(String requestURL, String contextPath, String servletPath, HashMap<String, Object> params) throws PageNotFoundException, PageForbiddenException
	{
		String sitePath = contextPath+"/"+servletPath;
		
		if(requestURL.length() > sitePath.length()) /* We are not at root level */
		{
			String menuPath = requestURL.substring(sitePath.length());
			menuPathIds = menuPath.split("/");
		}
		else
			menuPathIds = new String[0];
		
		return entryPage.lookupSubPage(menuPathIds, params);
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
	 * Returns the number of menu path id components
	 * 
	 * @return The number of menu path id components
	 */
	public int menuPathIdsLength()
	{
		if(menuPathIds == null)
			return 0;
		else
			return menuPathIds.length;
	}
	
	/**
	 * Returns a menu path id component
	 * 
	 * @param id Index of the menu path components
	 * @return Menu path component
	 */
	public String getMenuPathId(int id)
	{
		return menuPathIds[id];
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
		this.section.put(id, section);
		return this;
	}
	
	/**
	 * Returns the keys of the section hash map
	 * 
	 * @return A set of keys
	 */
	public Set<String> sectionKeys()
	{
		return section.keySet();
	}
	
	/**
	 * Returns the section with a given id
	 * 
	 * @param id Id of a division
	 * @return The section with the given id
	 */
	public Section getSection(String id)
	{
		return section.get(id);
	}
}
