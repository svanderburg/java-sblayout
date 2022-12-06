package io.github.svanderburg.layout.model.page;

import io.github.svanderburg.layout.model.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Defines a page that can be reached from a link in a menu section or through
 * the last path components of an URL.
 */
public abstract class Page
{
	/** Title of the page that is used as a label in a menu section */
	protected String title;

	/**
	 * Creates a new Page instance.
	 * 
	 * @param title Title of the page that is used as a label in a menu section
	 */
	public Page(String title)
	{
		setTitle(title);
	}

	/**
	 * Sets the title of the page.
	 * 
	 * @param title Title of the page that is used as a label in a menu section
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	/**
	 * Returns the title of the page.
	 * 
	 * @return Title of the page that is used as a label in a menu section
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * Checks whether the page link should be displayed in a menu section.
	 *
	 * @return true if the page is visible, else false
	 */
	public boolean checkVisibility()
	{
		return true;
	}
	
	/**
	 * Checks whether the page is currently accessible.
	 *
	 * @return true if the user has access, else false
	 */
	public boolean checkAccessibility()
	{
		return true;
	}
	
	/**
	 * Checks all conditions that must be met so that a page is displayed in a menu.
	 *
	 * @return true if the page should be visible, else false
	 */
	public boolean checkVisibleInMenu()
	{
		return checkVisibility() && checkAccessibility();
	}
	
	/**
	 * Gets the sub page that is reachable with the given path id
	 *
	 * @param id URL path component
	 * @return The requested sub page
	 */
	public Page getSubPage(String id)
	{
		return null;
	}
	
	/**
	 * Checks whether a subpage is directly reachable with the given id from the current page
	 *
	 * @param id URL path component
	 * @return true if and only if the sub page is directly reachable
	 */
	public boolean hasSubPage(String id)
	{
		return false;
	}
	
	/**
	 * Creates an iterator that can be used to traverse over all sub pages.
	 *
	 * @return An iterator
	*/
	public Iterator<String> subPageKeyIterator()
	{
		String[] emptyArray = new String[0];
		return Arrays.stream(emptyArray).iterator(); // This just returns an empty iterator
	}
	
	/**
	 * Decides how to compose a URL for the given page from its baseURL and the page identifier.
	 *
	 * @param baseURL Base URL of the page
	 * @param id Identifier of the page
	 * @return The URL to this page
	 */
	public String deriveURL(String baseURL, String id)
	{
		try
		{
			return baseURL + "/" + URLEncoder.encode(id, "UTF-8").replace("+", "%20");
		}
		catch(UnsupportedEncodingException ex)
		{
			return null; // Should never happen since UTF-8 is supported
		}
	}

	/**
	 * Checks whether the page is currently active
	 *
	 * @param route The route from the entry page to the current page
	 * @param id Identifier of the page
	 * @param level Level in the navigation structure
	 * @return true if the page is active, else false
	 */
	public abstract boolean checkActive(Route route, String id, int level);

	/**
	 * Examines a route derived from the path components of the requested URL and records all pages visited.
	 *
	 * @param application Application layout where the page belongs to
	 * @param route Route to investigate
	 * @param params A hash map containing arbitrary parameters
	 * @throws PageException In case an error occured, such as the page cannot be found or access is restricted
	 */
	public void examineRoute(Application application, Route route, HashMap<String, Object> params) throws PageException
	{
		examineRoute(application, route, 0, params);
	}
	
	/**
	 * Examines a route derived from the path components of the requested URL and records all pages visited.
	 *
	 * @param application Application layout where the page belongs to
	 * @param route Route to investigate
	 * @param index The index of the page to be visited
	 * @param params A hash map containing arbitrary parameters
	 * @throws PageException In case an error occured, such as the page cannot be found or access is restricted
	 */
	public abstract void examineRoute(Application application, Route route, int index, HashMap<String, Object> params) throws PageException;
}
