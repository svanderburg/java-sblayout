package io.github.svanderburg.layout.model.page;
import io.github.svanderburg.layout.model.*;
import io.github.svanderburg.layout.model.page.subpages.*;
import java.util.*;

/**
 * Defines a page alias that is an alias of an existing page that
 * can be reached from the entry page.
 */
public class PageAlias extends Page implements ExtendablePage
{
	/** Path components to the actual page relative from the entry page */
	private String[] menuPathIds;
	
	/** Object used to add subpages to this page */
	protected SubPageExtender subPageExtender;
	
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
		
		subPageExtender = new SubPageExtender();
	}
	
	/**
	 * Adds a sub page to which the menu section displaying the current page refers
	 *
	 * @param id URL path component
	 * @param subPage Sub page reference to add
	 * @return A reference to itself
	 */
	public PageAlias addSubPage(String id, Page subPage)
	{
		subPageExtender.addSubPage(id, subPage);
		return this;
	}
	
	/**
	 * @see ExtendablePage#hasSubPage(String)
	 */
	public boolean hasSubPage(String id)
	{
		return subPageExtender.hasSubPage(id);
	}
	
	/**
	 * @see ExtendablePage#getSubPage(String)
	 */
	public Page getSubPage(String id)
	{
		return subPageExtender.getSubPage(id);
	}
	
	/**
	 * @see ExtendablePage#subPageKeys()
	 */
	public Set<String> subPageKeys()
	{
		return subPageExtender.subPageKeys();
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
	 * @see Page#lookupSubPage(Application, String[], int, HashMap)
	 */
	@Override
	public Page lookupSubPage(Application application, String[] ids, int index, HashMap<String, Object> params) throws PageNotFoundException, PageForbiddenException
	{
		if(ids.length == index)
			return application.lookupSubPage(menuPathIds, params);
		else
		{
			String currentId = ids[index];
			
			if(hasSubPage(currentId))
			{
				Page currentPage = getSubPage(currentId);
				return currentPage.lookupSubPage(application, ids, index + 1, params);
			}
			else
				throw new PageNotFoundException();
		}
	}
}
