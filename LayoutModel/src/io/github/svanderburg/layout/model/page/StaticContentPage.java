package io.github.svanderburg.layout.model.page;
import io.github.svanderburg.layout.model.*;
import io.github.svanderburg.layout.model.page.content.*;
import io.github.svanderburg.layout.model.page.subpages.*;
import java.util.*;

/**
 * Defines a page referring to a collection of sub pages whose links can be picked
 * from a menu section.
 */
public class StaticContentPage extends ContentPage
{
	/** Object used to add subpages to this page */
	protected SubPageExtender subPageExtender;
	
	/**
	 * Creates a new StaticContentPage instance.
	 * 
	 * @param title Title of the page that is used as a label in a menu section
	 * @param contents A content object storing properties of the content sections of a page
	 * @param menuItem JSP file that renders the menu item. Leaving it null just renders a hyperlink
	 */
	public StaticContentPage(String title, Contents contents, String menuItem)
	{
		super(title, contents, menuItem);
		subPageExtender = new SubPageExtender();
	}
	
	/**
	 * Creates a new StaticContentPage instance.
	 * 
	 * @param title Title of the page that is used as a label in a menu section
	 * @param contents A content object storing properties of the content sections of a page
	 */
	public StaticContentPage(String title, Contents contents)
	{
		this(title, contents, null);
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
			super.examineRoute(application, route, index, params);
		else
		{
			String currentId = route.getId(index);
			
			if(hasSubPage(currentId))
			{
				Page currentPage = getSubPage(currentId);
				route.visitPage(this);
				currentPage.examineRoute(application, route, index + 1, params);
			}
			else
				throw new PageNotFoundException();
		}
	}
}
