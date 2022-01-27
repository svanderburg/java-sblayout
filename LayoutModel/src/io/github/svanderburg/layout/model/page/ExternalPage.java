package io.github.svanderburg.layout.model.page;

import io.github.svanderburg.layout.model.*;

import java.util.*;

/**
 * A page that refers to an external URL instead of a sub page belonging to the same application.
 */
public class ExternalPage extends Page
{
	/** External URL to which the page redirects */
	protected String url;
	
	/**
	 * Creates a new ExternalPage instance.
	 * 
	 * @param title Title of the page that is used as a label in a menu section
	 * @param url External URL to which the page redirects
	 */
	public ExternalPage(String title, String url)
	{
		super(title);
		this.url = url;
	}
	
	/**
	 * Returns the URL to which the page redirects
	 * @return External URL to which the page redirects
	 */
	public String getUrl()
	{
		return url;
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
	 * @see Page#examineRoute(Application, Route, int, HashMap)
	 */
	@Override
	public void examineRoute(Application application, Route route, int index, HashMap<String, Object> params) throws PageNotFoundException, PageForbiddenException
	{
		if(route.indexIsAtRequestedPage(index))
		{
			if(checkAccessibility())
				route.visitPage(this);
			else
				throw new PageForbiddenException();
		}
		else
			throw new PageNotFoundException(); // An external page does not refer to sub pages
	}

}
