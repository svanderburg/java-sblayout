package io.github.svanderburg.layout.model.section;

/**
 * Represents a section displaying menu links for every sub page to which a specific page refers.
 */
public class MenuSection extends Section
{
	/** The level in the page hierarchy from which menu links must be displayed */
	private int level;
	
	/**
	 * Creates a new MenuSection instance.
	 * 
	 * @param level The level in the page hierarchy from which menu links must be displayed
	 */
	public MenuSection(int level)
	{
		this.level = level;
	}
	
	/**
	 * Returns the level in the page hierarchy from which menu links must be displayed
	 * 
	 * @return The level in the page hierarchy from which menu links must be displayed
	 */
	public int getLevel()
	{
		return level;
	}
}
