package io.github.svanderburg.layout.model.section;
import java.util.*;

/**
 * An interface with methods that an object that manages sections must implement.
 */
public interface SectionManager
{
	/**
	 * Returns the keys of the section hash map
	 * 
	 * @return A set of keys
	 */
	public Set<String> sectionKeys();

	/**
	 * Returns the sub section with a given id
	 * 
	 * @param id Id of a division
	 * @return The sub section with the given id
	 */
	public Section getSection(String id);
}
