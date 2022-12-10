package io.github.svanderburg.layout.model.page;
import io.github.svanderburg.layout.model.*;
import io.github.svanderburg.layout.model.page.content.*;

import java.util.*;

/**
 * Defines a page displaying arbitrary contents in one or more content sections.
 */
public class ContentPage extends Page
{
	/** A content object storing properties of the content sections of a page */
	protected Contents contents;
	
	/**
	 * Creates a new ContentPage instance
	 * 
	 * @param title Title of the page that is used as a label in a menu section
	 * @param contents A content object storing properties of the content sections of a page
	 * @param menuItem JSP file that renders the menu item. Leaving it null just renders a hyperlink
	 */
	public ContentPage(String title, Contents contents, String menuItem)
	{
		super(title, menuItem);
		setContents(contents);
	}
	
	/**
	 * Creates a new ContentPage instance
	 * 
	 * @param title Title of the page that is used as a label in a menu section
	 * @param contents A content object storing properties of the content sections of a page
	 */
	public ContentPage(String title, Contents contents)
	{
		this(title, contents, null);
	}

	/**
	 * Sets the content of the page.
	 * 
	 * @param contents A content object storing properties of the content sections of a page
	 */
	public void setContents(Contents contents)
	{
		this.contents = contents;
	}
	
	/**
	 * Returns the content of the page.
	 * 
	 * @return A content object storing properties of the content sections of a page
	 */
	public Contents getContents()
	{
		return contents;
	}
	
	/**
	 * @see Page#checkActive(Route, String, int)
	 */
	@Override
	public boolean checkActive(Route route, String id, int level)
	{
		return route.hasVisitedPageOnLevel(id, level);
	}
	
	/**
	 * @see Page#examineRoute(Application, Route, int, HashMap)
	 */
	@Override
	public void examineRoute(Application application, Route route, int index, HashMap<String, Object> params) throws PageException
	{
		if(route.indexIsAtRequestedPage(index))
		{
			if(checkAccessibility())
				route.visitPage(this);
			else
				throw new PageForbiddenException();
		}
		else
			throw new PageNotFoundException(); // A ContentPage has no subitems
	}
}
