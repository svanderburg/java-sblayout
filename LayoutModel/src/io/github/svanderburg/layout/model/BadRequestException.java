package io.github.svanderburg.layout.model;

/**
 * An exception that gets thrown if invalid parameters were provided.
 */
public class BadRequestException extends PageException
{
	private static final long serialVersionUID = -2053130624161467722L;
	
	/** Stores the status code value */
	public final static int STATUS_CODE = 400;

	/**
	 * Creates a new BadRequestException instance
	 *
	 * @param displayMessage Error message to be displayed (optional)
	 */
	public BadRequestException(String displayMessage)
	{
		super(STATUS_CODE, "Bad Request", displayMessage);
	}
	
	/**
	 * Creates a new BadRequestException instance
	 */
	public BadRequestException()
	{
		this(null);
	}
}
