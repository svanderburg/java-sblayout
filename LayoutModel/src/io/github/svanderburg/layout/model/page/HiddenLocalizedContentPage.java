package io.github.svanderburg.layout.model.page;

/**
 * Defines a localized content page which link is not visible in any menu section.
 */
public class HiddenLocalizedContentPage extends LocalizedContentPage
{
	/**
	 * @see LocalizedContentPage#LocalizedContentPage()
	 */
	public HiddenLocalizedContentPage()
	{
		super();
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
