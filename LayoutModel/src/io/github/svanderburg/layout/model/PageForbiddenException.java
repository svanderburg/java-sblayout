package io.github.svanderburg.layout.model;

/**
 * An exception that gets thrown if access to a page is restricted.
 */
public class PageForbiddenException extends PageException
{
	/** Stores the status code value */
	public final static int STATUS_CODE = 403;

	private static final long serialVersionUID = -3478080095167628755L;

	/**
	 * Creates a new PageForbiddenException instance
	 *
	 * @param displayMessage Error message to be displayed (optional)
	 */
	public PageForbiddenException(String displayMessage)
	{
		super(STATUS_CODE, "Forbidden", displayMessage);
	}
	
	/**
	 * Creates a new PageForbiddenException instance
	 */
	public PageForbiddenException()
	{
		this(null);
	}
}
