package io.github.svanderburg.layout.model.page;

import io.github.svanderburg.layout.model.*;

import java.util.*;
import java.util.Map.Entry;

/**
 * Defines a static content page referring to sub pages that each implement the same page,
 * but in a different language. The localized page tries to detect the preferred language
 * from the Accept-Language parameter sent by the user agent and displays the page in that
 * language accordingly.
 * 
 * If the preferred languages are not supported, it will fallback to the first sub page. 
 */
public class LocalizedContentPage extends StaticContentPage
{
	/**
	 * Creates a new LocalizedContentPage instance.
	 *
	 * @param menuItem JSP file that renders the menu item. Leaving it null just renders a hyperlink
	 */
	public LocalizedContentPage(String menuItem)
	{
		super(null, null, menuItem);
	}
	
	/**
	 * Creates a new LocalizedContentPage instance.
	 */
	public LocalizedContentPage()
	{
		this(null);
	}

	/**
	 * Adds a sub page to which the menu section displaying the current page refers
	 * 
	 * @param id URL path component
	 * @param subPage Sub page reference to add
	 * @return A reference to itself
	 */
	public LocalizedContentPage addSubPage(String id, ContentPage subPage)
	{
		/* Adopt title and contents of first item */
		if(subPageExtender.getSubPages().size() == 0)
		{
			setTitle(subPage.getTitle());
			setContents(subPage.getContents());
		}
		
		/* Add the site item */
		subPageExtender.addSubPage(id, subPage);
		
		return this;
	}
	
	private TreeMap<Float, String> parseLocaleOptions(String acceptLanguage)
	{
		TreeMap<Float, String> options = new TreeMap<Float, String>(Collections.reverseOrder());
		String[] locales = acceptLanguage.split(",");
		
		for(String locale : locales)
		{
			String[] localeComponents = locale.split(";");
			String identifier = localeComponents[0].toLowerCase();
			float weight;
			
			if(localeComponents.length > 1)
				weight = Float.parseFloat(localeComponents[1].substring(2));
			else
				weight = 1.0f; // if a q value is not given, assume 1.0
			
			options.put(weight, identifier);
		}
		
		return options;
	}
	
	private Page findLocalizedSubPage(TreeMap<Float, String> options)
	{
		for(Entry<Float, String> option : options.entrySet())
		{
			String identifier = option.getValue();
			
			// Check if there is a locale option that matches the requested locale
			if(hasSubPage(identifier))
				return getSubPage(identifier);
			else
			{
				String[] identifierComponents = identifier.split("-");
				
				if(identifierComponents.length > 1)
				{
					// Try the locale's language without country as a fallback
					String language = identifierComponents[0];
					
					if(hasSubPage(language))
						return getSubPage(language);
				}
			}
		}
		
		/* If all locales have been tried and still none has been found, return the first sub item (that is considered the default) */
		return subPageExtender.getSubPages().entrySet().iterator().next().getValue();
	}
	
	/**
	 * @see Page#examineRoute(Application, Route, int, HashMap)
	 */
	@Override
	public void examineRoute(Application application, Route route, int index, HashMap<String, Object> params) throws PageException
	{
		if(route.indexIsAtRequestedPage(index))
		{
			/* Visit itself */
			route.visitPage(this);

			/* Parse the locales to separate identifiers and weights */
			String acceptLanguage = (String)params.get("acceptLanguage");
			TreeMap<Float, String> options = parseLocaleOptions(acceptLanguage);
			
			/* Try to lookup the locale with the highest priority that is defined as sub item */
			Page subPage = findLocalizedSubPage(options);
			
			/* Examine the localized page */
			subPage.examineRoute(application, route, index, params);
		}
		else
			super.examineRoute(application, route, index, params);
	}	
}
