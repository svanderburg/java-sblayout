package io.github.svanderburg.layout.model;

/**
 * An exception that gets thrown if the path to page does not exists.
 */
public class PageNotFoundException extends Exception
{
	private static final long serialVersionUID = -4386085810974705686L;
	
	/**
	 * Creates a new PageNotFoundException instance
	 */
	public PageNotFoundException()
	{
		super("Page not found!");
	}
}
