package io.github.svanderburg.layout.model.section;

/**
 * Represents a static section that has contents that is the same across all pages.
 */
public class StaticSection extends Section
{
	/** JSP file containing the actual contents of this section */
	private String contents;
	
	/**
	 * Creates a new StaticSection instance.
	 * 
	 * @param contents JSP file containing the actual contents of this section
	 */
	public StaticSection(String contents)
	{
		this.contents = contents;
	}
	
	/**
	 * Returns the JSP file containing the actual contents of this section
	 * 
	 * @return A JSP file
	 */
	public String getContents()
	{
		return contents;
	}
}
