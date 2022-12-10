package io.github.svanderburg.layout.model.page;
import io.github.svanderburg.layout.model.page.content.*;

/**
 * Defines a static content page which link is not visible in any menu section.
 */
public class HiddenStaticContentPage extends StaticContentPage
{
	/**
	 * @see StaticContentPage#StaticContentPage(String, Contents, String)
	 */
	public HiddenStaticContentPage(String title, Contents contents, String menuItem)
	{
		super(title, contents, menuItem);
	}
	
	/**
	 * @see StaticContentPage#StaticContentPage(String, Contents)
	 */
	public HiddenStaticContentPage(String title, Contents contents)
	{
		super(title, contents);
	}
	
	/**
	 * @see Page#checkVisibility()
	 */
	@Override
	public boolean checkVisibility()
	{
		return false;
	}
}
