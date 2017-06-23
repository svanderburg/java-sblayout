package io.github.svanderburg.layout.model.page;
import io.github.svanderburg.layout.model.*;
import io.github.svanderburg.layout.model.page.content.*;

import java.util.*;

/**
 * Defines a page referring to the same sub page that dynamically generates it contents
 * using the sub page URL path component as parameter.
 */
public class DynamicContentPage extends ContentPage
{
	/** The name of the query parameter that must be set when retrieving the sub page */
	protected String param;
	
	/** The dynamic sub page that interprets the URL parameter component */
	protected Page dynamicSubPage;
	
	/**
	 * Create a new DynamicContentPage instance.
	 * 
	 * @param title Title of the page that is used as a label in a menu section
	 * @param param The name of the query parameter that must be set when retrieving the sub page
	 * @param contents A content object storing properties of the content sections of a page
	 * @param dynamicSubPage The dynamic sub page that interprets the URL parameter component
	 */
	public DynamicContentPage(String title, String param, Contents contents, Page dynamicSubPage)
	{
		super(title, contents);
		this.param = param;
		this.dynamicSubPage = dynamicSubPage;
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
			
			// Memorize the given parameter value
			@SuppressWarnings("unchecked")
			HashMap<String, String> query = (HashMap<String, String>)params.get("query");
			query.put(param, currentId);
			
			return dynamicSubPage.lookupSubPage(application, ids, index + 1, params);
		}
	}
}
