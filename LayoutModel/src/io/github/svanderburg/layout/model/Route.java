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
	 * Composes the URL for the current page or any of its parent pages at a certain level.
	 * 
	 * @param baseURL Base URL to prepend to the resulting URL
	 * @param level Page level
	 * @param argSeparator The symbol that separates arguments
	 * @return The URL of the current page or any of its parent pages
	 */
	public String composeURLAtLevel(String baseURL, int level, String argSeparator)
	{
		String url = baseURL;
		
		for(int i = 0; i < level; i++)
		{
			String currentId = ids[i];
			Page currentPage = pages.elementAt(i + 1);
			
			url = currentPage.deriveURL(url, currentId, argSeparator);
		}
		
		return url;
	}
	
	/**
	 * Composes the URL for the current page or any of its parent pages at a certain level.
	 * 
	 * @param baseURL Base URL to prepend to the resulting URL
	 * @param level Page level
	 * @return The URL of the current page or any of its parent pages
	 */
	public String composeURLAtLevel(String baseURL, int level)
	{
		return composeURLAtLevel(baseURL, level, "&amp;");
	}
	
	/**
	 * Composes the URL to the parent page of the currently opened URL.
	 * 
	 * @param baseURL Base URL to prepend to the resulting URL
	 * @param argSeparator The symbol that separates arguments
	 * @return The URL to the parent page
	 */
	public String composeParentPageURL(String baseURL, String argSeparator)
	{
		return composeURLAtLevel(baseURL, ids.length - 1, argSeparator);
	}
	
	/**
	 * Composes the URL to the parent page of the currently opened URL.
	 * 
	 * @param baseURL Base URL to prepend to the resulting URL
	 * @return The URL to the parent page
	 */
	public String composeParentPageURL(String baseURL)
	{
		return composeParentPageURL(baseURL, "&amp;");
	}
}
