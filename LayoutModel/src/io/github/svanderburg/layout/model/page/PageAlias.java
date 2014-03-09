package io.github.svanderburg.layout.model.page;
import io.github.svanderburg.layout.model.*;

import java.util.*;

/**
 * Defines a page alias that is an alias of an existing page that
 * can be reached from the entry page.
 */
public class PageAlias extends Page
{
	/** Path components to the actual page relative from the entry page */
	private String[] menuPathIds;
	
	/**
	 * Creates a new PageAlias instance.
	 * 
	 * @param title Title of the page that is used as a label in a menu section
	 * @param path Path to the actual page relative from the entry page
	 */
	public PageAlias(String title, String path)
	{
		super(title);
		
		if(path.equals(""))
			menuPathIds = new String[0];
		else
			menuPathIds = path.split("/");
	}
	
	/**
	 * @see Page#checkVisibility()
	 */
	@Override
	public boolean checkVisibility()
	{
		return true;
	}
	
	/**
	 * @see Page#checkAccessibility()
	 */
	@Override
	public boolean checkAccessibility()
	{
		return true;
	}
	
	/**
	 * @see Page#lookupSubPage(Page, String[], int, HashMap)
	 */
	@Override
	public Page lookupSubPage(Page entryPage, String[] ids, int index, HashMap<String, Object> params) throws PageNotFoundException, PageForbiddenException
	{
		return entryPage.lookupSubPage(entryPage, menuPathIds, params);
	}
}
