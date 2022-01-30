package io.github.svanderburg.layout.model;
import java.util.*;
import io.github.svanderburg.layout.model.page.*;

/**
 * Records the route from the entry page to the page that is currently requested.
 */
public class Route
{
	/** Path components in the URL that correspond to the IDs of the sub pages */
	private String[] ids;
	
	/** Pages visited to reach the currently opened page */
	private Vector<Page> pages;
	
	/**
	 * Creates a new route instance
	 *
	 * @param ids Path components in the URL that correspond to the IDs of the sub pages
	 */
	public Route(String[] ids)
	{
		reset(ids);
	}
	
	/**
	 * Clears the route so that it can be re-investigated with the given path components
	 *
	 * @param ids Path components in the URL that correspond to the IDs of the sub pages
	 */
	public void reset(String[] ids)
	{
		this.ids = ids;
		pages = new Vector<Page>();
	}
	
	/**
	 * Visits the provided page by adding a record
	 *
	 * @param page Page to visit
	 */
	public void visitPage(Page page)
	{
		pages.add(page);
	}
	
	/**
	 * Checks whether the given index is the currently requested page.
	 *
	 * @param index Index of a page
	 * @return true if the corresponding page is currently requested, else false
	 */
	public boolean indexIsAtRequestedPage(int index)
	{
		return ids.length == index;
	}
	
	/**
	 * Returns the ID of a sub page.
	 * 
	 * @param index Index of a visited page
	 * @return The ID of the visited page
	 */
	public String getId(int index)
	{
		return ids[index];
	}
	
	/**
	 * Returns a visited page.
	 * 
	 * @param index Index of a visited page
	 * @return The visited page
	 */
	public Page getPage(int index)
	{
		return pages.elementAt(index);
	}
	
	/**
	 * Determines what the currently requested page is.
	 * 
	 * @return Currently requested page
	 */
	public Page determineCurrentPage()
	{
		return pages.lastElement();
	}

	/**
	 * Shows how many pages that need to be visited to reach the current page.
	 * 
	 * @return Number of pages
	 */
	public int size()
	{
		return ids.length;
	}

	/**
	 * Checks whether a page with provided ID was visited on the specified level of a menu section.
	 * 
	 * @param id ID of a page
	 * @param level Level of a menu section 
	 * @return true if the page was visited, else false
	 */
	public boolean hasVisitedPageOnLevel(String id, int level)
	{
		return ids.length > level && ids[level].equals(id);
	}
	
	/**
	 * Composes a base URL for a menu section on a certain level.
	 * 
	 * @param level Menu section level
	 * @return The base URL for all links in the menu section
	 */
	public String composeBaseURL(int level)
	{
		String basePath = "";
		
		for(int i = 0; i < level; i++)
			basePath += ids[i] + "/";
		
		return basePath;
	}
}
