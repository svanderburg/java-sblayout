package io.github.svanderburg.layout.model.page;
import io.github.svanderburg.layout.model.*;
import io.github.svanderburg.layout.model.page.content.*;
import io.github.svanderburg.layout.model.page.subpages.*;
import java.util.*;

/**
 * Defines a page referring to a collection of sub pages whose links can be picked
 * from a menu section.
 */
public class StaticContentPage extends ContentPage implements ExtendablePage
{
	/** Object used to add subpages to this page */
	protected SubPageExtender subPageExtender;
	
	/**
	 * Creates a new ContentPage instance.
	 * 
	 * @param title Title of the page that is used as a label in a menu section
	 * @param contents A content object storing properties of the content sections of a page
	 */
	public StaticContentPage(String title, Contents contents)
	{
		super(title, contents);
		subPageExtender = new SubPageExtender();
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
	 * @see Page#lookupSubPage(Application, String[], int, HashMap)
	 */
	@Override
	public Page lookupSubPage(Application application, String[] ids, int index, HashMap<String, Object> params) throws PageNotFoundException, PageForbiddenException
	{
		if(ids.length == index)
			return super.lookupSubPage(application, ids, index, params);
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
