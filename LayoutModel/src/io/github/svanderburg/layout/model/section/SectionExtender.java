package io.github.svanderburg.layout.model.section;
import java.util.*;

/**
 * An object providing facilities to attach one or more sections to an object.
 */
public class SectionExtender implements SectionManager
{
	/** A hash map of sub sections */
	private LinkedHashMap<String, Section> section;

	/**
	 * Creates a new SectionExtender instance.
	 */
	public SectionExtender()
	{
		section = new LinkedHashMap<String, Section>();
	}
	
	/**
	 * Adds a sub section to the compound section.
	 * 
	 * @param id ID of a division
	 * @param section Section to add
	 */
	public void addSection(String id, Section section)
	{
		this.section.put(id, section);
	}

	/**
	 * Returns the keys of the section hash map
	 * 
	 * @return A set of keys
	 */
	public Set<String> sectionKeys()
	{
		return section.keySet();
	}

	/**
	 * Returns the sub section with a given id
	 * 
	 * @param id Id of a division
	 * @return The sub section with the given id
	 */
	public Section getSection(String id)
	{
		return section.get(id);
	}
}
