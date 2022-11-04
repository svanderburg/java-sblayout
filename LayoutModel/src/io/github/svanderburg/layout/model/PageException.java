package io.github.svanderburg.layout.model;

/**
 * An exception that gets thrown when something went wrong in a page lookup.
 */
public class PageException extends Exception
{
	private static final long serialVersionUID = 6105916647605719394L;
	
	/** HTTP status code */
	private int statusCode;
	
	/**
	 * Creates a new PageException instance.
	 *
	 * @param statusCode HTTP status code
	 * @param message Exception error message
	 */
	public PageException(int statusCode, String message)
	{
		super(message);
		this.statusCode = statusCode;
	}
	
	/**
	 * Returns the HTTP status code.
	 *
	 * @return HTTP status code
	 */
	public int getStatusCode()
	{
		return statusCode;
	}
}
