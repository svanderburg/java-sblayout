package io.github.svanderburg.layout.model.page.content;
import java.util.*;

/**
 * Represents the contents that ends up in the dynamic content sections of a page.
 * 
 * The content is in most cases a single division containing HTML code, but it can also
 * be multiple divisions each having their own HTML fragments.
 */
public class Contents
{
	/** A hash map mapping division ids onto JSP files representing HTML content */
	private HashMap<String, String> sections;
	
	/** A string containing the path to the controller page that handles GET or POST parameters */
	private String controller;
	
	/** An array containing stylesheet files to include */
	private String[] styles;
	
	/** An array containing script files to include */
	private String[] scripts;
	
	/**
	 * Creates a new contents instance.
	 */
	public Contents()
	{
		this(null, null, null);
	}
	
	/**
	 * Creates a new contents instance.
	 * 
	 * @param styles An array containing stylesheet files to include
	 * @param scripts An array containing script files to include
	 */
	public Contents(String[] styles, String[] scripts)
	{
		this(null, styles, scripts);
	}
	
	/**
	 * Creates a new contents instance.
	 * 
	 * @param controller A string containing the path to the controller page that handles GET or POST parameters
	 * @param styles An array containing stylesheet files to include
	 * @param scripts An array containing script files to include
	 */
	public Contents(String controller, String[] styles, String[] scripts)
	{
		this.sections = new HashMap<String, String>();
		this.controller = controller;
		this.styles = styles;
		this.scripts = scripts;
	}
	
	/**
	 * Creates a new contents instance.
	 * 
	 * @param contents The JSP file that is displayed in the contents section with id contents
	 * @param controller A string containing the path to the controller page that handles GET or POST parameters
	 * @param styles An array containing stylesheet files to include
	 * @param scripts An array containing script files to include
	 */
	public Contents(String contents, String controller, String[] styles, String[] scripts)
	{
		this(controller, styles, scripts);
		this.sections.put("contents", contents);
	}
	
	/**
	 * Creates a new contents instance.
	 * 
	 * @param contents The JSP file that is displayed in the contents section with id contents
	 */
	public Contents(String contents)
	{
		this(contents, null, null, null);
	}
	
	/**
	 * Adds the content of a specific section
	 * 
	 * @param id Id of a content section
	 * @param contents A JSP file displaying the contents
	 * @return A self reference to a contents object
	 */
	public Contents addSection(String id, String contents)
	{
		this.sections.put(id, contents);
		return this;
	}
	
	/**
	 * Gets the content from a section
	 * 
	 * @param id Id of a content section
	 * @return A JSP file displaying the contents
	 */
	public String getContentsFrom(String id)
	{
		return sections.get(id);
	}
	
	/**
	 * Gets the controller page
	 * 
	 * @return Path to the controller page
	 */
	public String getController()
	{
		return controller;
	}
	
	/**
	 * Returns the number of CSS stylesheets specific to this page
	 * 
	 * @return The number of CSS stylesheets specific to this page
	 */
	public int stylesLength()
	{
		if(styles == null)
			return 0;
		else
			return styles.length;
	}
	
	/**
	 * Returns a page specific CSS stylesheet
	 * 
	 * @param index Index of the stylesheet
	 * @return Path to a CSS stylesheet
	 */
	public String getStyle(int index)
	{
		return styles[index];
	}
	
	/**
	 * Returns the number of JavaScript files included by this page
	 * 
	 * @return The number of JavaScript files included by this page
	 */
	public int scriptsLength()
	{
		if(scripts == null)
			return 0;
		else
			return scripts.length;
	}
	
	/**
	 * Returns a page specific JavaScript file
	 * 
	 * @param index Index of the JavaScript file
	 * @return Path to a JavaScript file
	 */
	public String getScript(int index)
	{
		return scripts[index];
	}
}
