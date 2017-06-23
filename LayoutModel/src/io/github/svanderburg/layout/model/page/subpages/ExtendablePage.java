package io.github.svanderburg.layout.model.page.subpages;
import io.github.svanderburg.layout.model.page.*;
import java.util.*;

/**
 * An interface defining methods a page that can be extended with sub pages
 * must implement.
 */
public interface ExtendablePage
{
	/**
	 * Checks whether a subpage is directly reachable with the given id from the current page
	 *
	 * @param id URL path component
	 * @return true if and only if the sub page is directly reachable
	 */
	public boolean hasSubPage(String id);
	
	/**
	 * Gets the sub page that is reachable with the given path id
	 *
	 * @param id URL path component
	 * @return The requested sub page
	 */
	public Page getSubPage(String id);
	
	/**
	 * Returns the keys of all subpages.
	 *
	 * @return A set containing the keys of all the subpages
	 */
	public Set<String> subPageKeys();
}
