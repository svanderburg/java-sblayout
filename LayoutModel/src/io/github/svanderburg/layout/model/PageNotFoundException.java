package io.github.svanderburg.layout.model;

/**
 * An exception that gets thrown if the path to page does not exists.
 */
public class PageNotFoundException extends PageException
{
	/** Stores the status code value */
	public final static int STATUS_CODE = 404;

	private static final long serialVersionUID = -4386085810974705686L;
	
	/**
	 * Creates a new PageNotFoundException instance
	 *
	 * @param displayMessage Error message to be displayed (optional)
	 */
	public PageNotFoundException(String displayMessage)
	{
		super(STATUS_CODE, "Not Found", displayMessage);
	}
	
	/**
	 * Creates a new PageNotFoundException instance
	 */
	public PageNotFoundException()
	{
		this(null);
	}
}
