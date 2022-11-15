package io.github.svanderburg.layout.model;

/**
 * An exception that gets thrown when something went wrong in a page lookup.
 */
public class PageException extends Exception
{
	private static final long serialVersionUID = 6105916647605719394L;
	
	/** HTTP status code */
	private int statusCode;
	
	/** Error message to be displayed (optional) */
	private String displayMessage;
	
	/**
	 * Creates a new PageException instance.
	 *
	 * @param statusCode HTTP status code
	 * @param message Exception error message
	 * @param displayMessage Error message to be displayed (optional)
	 */
	public PageException(int statusCode, String message, String displayMessage)
	{
		super(message);
		this.statusCode = statusCode;
		this.displayMessage = displayMessage;
	}
	
	/**
	 * Creates a new PageException instance.
	 *
	 * @param statusCode HTTP status code
	 * @param message Exception error message
	 */
	public PageException(int statusCode, String message)
	{
		this(statusCode, message, null);
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
	
	/**
	 * Returns the error message to be display or null if there none.
	 *
	 * @return Display message
	 */
	public String getDisplayMessage()
	{
		return displayMessage;
	}
}
