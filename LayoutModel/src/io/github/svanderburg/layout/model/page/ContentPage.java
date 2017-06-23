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
	 */
	public ContentPage(String title, Contents contents)
	{
		super(title);
		setContents(contents);
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
		{
			if(checkAccessibility())
				return this;
			else
				throw new PageForbiddenException();
		}
		else
			throw new PageNotFoundException(); // A ContentSiteItem has no subitems
	}
}
