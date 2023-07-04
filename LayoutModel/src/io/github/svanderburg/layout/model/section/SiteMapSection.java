package io.github.svanderburg.layout.model.section;

/**
 * Represents a section displaying a site map with menu links for every sub page and transitive sub page to which a specific page refers.
 */
public class SiteMapSection extends MenuSection
{
	/**
	 * Creates a new SiteMapSection instance.
	 *
	 * @param level The level in the page hierarchy from which menu links must be displayed
	 */
	public SiteMapSection(int level)
	{
		super(level);
	}
}
