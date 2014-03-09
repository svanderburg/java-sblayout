package io.github.svanderburg.layout.model;

/**
 * An exception that gets thrown if access to a page is restricted.
 */
public class PageForbiddenException extends Exception
{
	private static final long serialVersionUID = -3478080095167628755L;

	/**
	 * Creates a new PageForbiddenException instance
	 */
	public PageForbiddenException()
	{
		super("Access denied to this page!");
	}
}
