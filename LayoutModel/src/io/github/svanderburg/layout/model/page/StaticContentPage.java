package io.github.svanderburg.layout.model.page;
import io.github.svanderburg.layout.model.*;
import io.github.svanderburg.layout.model.page.content.*;

import java.util.*;

/**
 * Defines a page referring to a collection of sub pages whose links can be picked
 * from a menu section. 
 */
public class StaticContentPage extends ContentPage
{
	/** An associative array mapping URL path components to sub pages */
	protected LinkedHashMap<String, Page> subPages;
	
	/**
	 * Creates a new ContentPage instance.
	 * 
	 * @param title Title of the page that is used as a label in a menu section
	 * @param contents A content object storing properties of the content sections of a page
	 */
	public StaticContentPage(String title, Contents contents)
	{
		super(title, contents);
		subPages = new LinkedHashMap<String, Page>();
	}
	
	/**
	 * Adds a sub page to which the menu section displaying the current page refers
	 * 
	 * @param id URL path component
	 * @param subPage Sub page reference to add
	 * @return A reference to itself
	 */
	public StaticContentPage addSubPage(String id, Page subPage)
	{
		subPages.put(id, subPage);
		return this;
	}
	
	/**
	 * Checks whether a subpage is directly reachable with the given id from the current page
	 *  
	 * @param id URL path component
	 * @return true if and only if the sub page is directly reachable 
	 */
	public boolean hasSubPage(String id)
	{
		return subPages.containsKey(id);
	}
	
	/**
	 * Gets the sub page that is reachable with the given path id
	 * 
	 * @param id URL path component
	 * @return The requested sub page
	 */
	public Page getSubPage(String id)
	{
		return subPages.get(id);
	}
	
	/**
	 * Returns the keys of all subpages.
	 * 
	 * @return A set containing the keys of all the subpages
	 */
	public Set<String> subPageKeys()
	{
		return subPages.keySet();
	}
	
	/**
	 * @see Page#lookupSubPage(Page, String[], int, HashMap)
	 */
	@Override
	public Page lookupSubPage(Page entryPage, String[] ids, int index, HashMap<String, Object> params) throws PageNotFoundException, PageForbiddenException
	{
		if(ids.length == index)
			return super.lookupSubPage(entryPage, ids, index, params);
		else
		{
			String currentId = ids[index];
			
			if(hasSubPage(currentId))
			{
				Page currentPage = getSubPage(currentId);
				return currentPage.lookupSubPage(entryPage, ids, index + 1, params);			
			}
			else
				throw new PageNotFoundException();
		}
	}
}
