package io.github.svanderburg.layout.model.section;
import java.util.*;

/**
 * Represents a section that encapsulates other sections. This can be useful for layout purposes.
 */
public class CompoundSection extends Section implements SectionManager
{
	private SectionExtender sectionExtender;

	/**
	 * Creates a new CompoundSection instance.
	 */
	public CompoundSection()
	{
		sectionExtender = new SectionExtender();
	}

	/**
	 * Adds a sub section to the compound section.
	 * 
	 * @param id ID of a division
	 * @param section Section to add
	 * @return A self reference to the compound section
	 */
	public CompoundSection addSection(String id, Section section)
	{
		sectionExtender.addSection(id, section);
		return this;
	}

	/**
	 * Returns the keys of the sub section hash map
	 * 
	 * @return A set of keys
	 */
	public Set<String> sectionKeys()
	{
		return sectionExtender.sectionKeys();
	}

	/**
	 * Returns the sub section with a given id
	 * 
	 * @param id Id of a division
	 * @return The sub section with the given id
	 */
	public Section getSection(String id)
	{
		return sectionExtender.getSection(id);
	}
}
