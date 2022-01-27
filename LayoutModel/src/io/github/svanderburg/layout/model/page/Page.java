package io.github.svanderburg.layout.model.page;

import io.github.svanderburg.layout.model.*;
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
	public abstract boolean checkVisibility();
	
	/**
	 * Checks whether the page is currently accessible.
	 *
	 * @return true if the user has access, else false
	 */
	public abstract boolean checkAccessibility();
	
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
	 * Examines a route derived from the path components of the requested URL and records all pages visited.
	 *
	 * @param application Application layout where the page belongs to
	 * @param route Route to investigate
	 * @param params A hash map containing arbitrary parameters 
	 * @throws PageNotFoundException If the sub page cannot be found
	 * @throws PageForbiddenException If the sub page is not accessible
	 */
	public void examineRoute(Application application, Route route, HashMap<String, Object> params) throws PageNotFoundException, PageForbiddenException
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
	 * @throws PageNotFoundException If the sub page cannot be found
	 * @throws PageForbiddenException If the sub page is not accessible
	 */
	public abstract void examineRoute(Application application, Route route, int index, HashMap<String, Object> params) throws PageNotFoundException, PageForbiddenException;
}
