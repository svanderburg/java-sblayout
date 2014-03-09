package io.github.svanderburg.layout.model.section;

/**
 * Represents a section having dynamic content determined by the selected menu link.
 */
public class ContentsSection extends Section
{
	/** Indicates whether this is the main contents and the title should be displayed */
	private boolean titleDisplayed;
	
	/**
	 * Creates a new contents section instance.
	 */
	public ContentsSection()
	{
		titleDisplayed = false;
	}
	
	/**
	 * Creates a new contents section instance.
	 * 
	 * @param titleDisplayed Indicates whether this is the main contents and the title should be displayed
	 */
	public ContentsSection(boolean titleDisplayed)
	{
		this.titleDisplayed = titleDisplayed;
	}
	
	/**
	 * Indicates whether this is the main contents and the title should be displayed
	 * 
	 * @return true if and only if the title should be displayed
	 */
	public boolean isTitleDisplayed()
	{
		return titleDisplayed;
	}
}
