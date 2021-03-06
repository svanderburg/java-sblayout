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
	 * Lookup a sub page by using the given ids orginating from the last path components
	 * of an URL.
	 *
	 * @param application Application layout where the page belongs to
	 * @param ids The entry page of the application
	 * @param params A hash map containing arbitrary parameters 
	 * @return The requested sub page
	 * @throws PageNotFoundException If the sub page cannot be found
	 * @throws PageForbiddenException If the sub page is not accessible
	 */
	public Page lookupSubPage(Application application, String[] ids, HashMap<String, Object> params) throws PageNotFoundException, PageForbiddenException
	{
		return lookupSubPage(application, ids, 0, params);
	}
	
	/**
	 * Lookup a sub page by using the given ids orginating from the last path components
	 * of an URL.
	 *
	 * @param application Application layout where the page belongs to
	 * @param ids An array of strings containing URL path components
	 * @param index The current index of the element in the ids array
	 * @param params A hash map containing arbitrary parameters 
	 * @return The requested sub page
	 * @throws PageNotFoundException If the sub page cannot be found
	 * @throws PageForbiddenException If the sub page is not accessible
	 */
	public abstract Page lookupSubPage(Application application, String[] ids, int index, HashMap<String, Object> params) throws PageNotFoundException, PageForbiddenException;
}
