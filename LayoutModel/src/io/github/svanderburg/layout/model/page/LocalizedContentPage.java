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
	 */
	public LocalizedContentPage()
	{
		super(null, null);
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
	
	/**
	 * @see Page#lookupSubPage(Application, String[], int, HashMap)
	 */
	@Override
	public Page lookupSubPage(Application application, String[] ids, int index, HashMap<String, Object> params) throws PageNotFoundException, PageForbiddenException
	{
		if(ids.length == index)
		{
			TreeMap<Float, String> options = new TreeMap<Float, String>(Collections.reverseOrder());
			String acceptLanguage = (String)params.get("acceptLanguage");
			String[] locales = acceptLanguage.split(",");
			
			/* Parse the locales to separate identifiers and weights */
			for(String locale : locales)
			{
				String[] localeComponents = locale.split(";");
				String identifier = localeComponents[0];
				float weight;
				
				if(localeComponents.length > 1)
					weight = Float.parseFloat(localeComponents[1].substring(2));
				else
					weight = 1.0f; // if a q value is not given, assume 1.0
				
				options.put(weight, identifier);
			}
			
			/* Try to lookup the locale with the highest priority that is defined as sub item */
			for(Entry<Float, String> option : options.entrySet())
			{
				String identifier = option.getValue();
				
				if(hasSubPage(identifier))
				{
					Page result = getSubPage(identifier);
					return result.lookupSubPage(application, ids, index, params);
				}
				else
				{
					String[] identifierComponents = identifier.split("-");
					
					if(identifierComponents.length > 1)
					{
						// Try the locale's language without country as a fallback
						String language = identifierComponents[0];
						
						if(hasSubPage(language))
						{
							Page result = getSubPage(language);
							return result.lookupSubPage(application, ids, index, params);
						}
					}
				}
			}
			
			/* If all locales have been tried and still none has been found, return the first sub item that is considered the default */
			Page result = subPageExtender.getSubPages().entrySet().iterator().next().getValue();
			return result.lookupSubPage(application, ids, index, params);
		}
		else
			return super.lookupSubPage(application, ids, index, params);
	}	
}
