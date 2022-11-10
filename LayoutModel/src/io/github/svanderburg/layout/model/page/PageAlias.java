package io.github.svanderburg.layout.model.page;
import io.github.svanderburg.layout.model.*;
import io.github.svanderburg.layout.model.page.subpages.*;
import java.util.*;

/**
 * Defines a page alias that is an alias of an existing page that
 * can be reached from the entry page.
 */
public class PageAlias extends Page
{
	/** Path components to the actual page relative from the entry page */
	private String[] ids;
	
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
			ids = new String[0];
		else
			ids = path.split("/");
		
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
	 * @see Page#hasSubPage(String)
	 */
	@Override
	public boolean hasSubPage(String id)
	{
		return subPageExtender.hasSubPage(id);
	}
	
	/**
	 * @see Page#getSubPage(String)
	 */
	@Override
	public Page getSubPage(String id)
	{
		return subPageExtender.getSubPage(id);
	}
	
	/**
	 * @see Page#subPageKeyIterator()
	 */
	@Override
	public Iterator<String> subPageKeyIterator()
	{
		return subPageExtender.subPageKeyIterator();
	}
	
	/**
	 * @see Page#examineRoute(Application, Route, int, HashMap)
	 */
	@Override
	public void examineRoute(Application application, Route route, int index, HashMap<String, Object> params) throws PageException
	{
		if(route.indexIsAtRequestedPage(index))
		{
			route.reset(ids);
			application.examineRoute(route, params);
		}
		else
		{
			String currentId = route.getId(index);
			
			if(hasSubPage(currentId))
			{
				route.visitPage(this);
				Page currentPage = getSubPage(currentId);
				currentPage.examineRoute(application, route, index + 1, params);
			}
			else
				throw new PageNotFoundException();
		}
	}
}
