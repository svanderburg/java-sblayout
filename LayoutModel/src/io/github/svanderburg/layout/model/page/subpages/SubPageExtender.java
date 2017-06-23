package io.github.svanderburg.layout.model.page.subpages;
import io.github.svanderburg.layout.model.page.*;
import java.util.*;

/**
 * An object providing facilities to attach one or more sub pages to an existing
 * page.
 */
public class SubPageExtender implements ExtendablePage
{
	/** An associative array mapping URL path components to sub pages */
	private LinkedHashMap<String, Page> subPages;
	
	/**
	 * Creates a new sub page extender object.
	 */
	public SubPageExtender()
	{
		subPages = new LinkedHashMap<String, Page>();
	}
	
	/**
	 * Adds a sub page to which the menu section displaying the current page refers
	 *
	 * @param id URL path component
	 * @param subPage Sub page reference to add
	 */
	public void addSubPage(String id, Page subPage)
	{
		subPages.put(id, subPage);
	}
	
	/**
	 * @see ExtendablePage#hasSubPage(String)
	 */
	public boolean hasSubPage(String id)
	{
		return subPages.containsKey(id);
	}
	
	/**
	 * @see ExtendablePage#getSubPage(String)
	 */
	public Page getSubPage(String id)
	{
		return subPages.get(id);
	}
	
	/**
	 * @see ExtendablePage#subPageKeys()
	 */
	public Set<String> subPageKeys()
	{
		return subPages.keySet();
	}

	/**
	 * Returns the linked hash map providing all sub pages.
	 *
	 * @return Linked hash map off all sub pages
	 */
	public LinkedHashMap<String, Page> getSubPages()
	{
		return subPages;
	}
}
