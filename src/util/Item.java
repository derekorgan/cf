/**
 * This represents an item that the users can rate
 * 
 * Michael O'Mahony
 * 20/01/2011
 */

package util;

public class Item
{
	private Integer id; // the numeric ID of the item
	private String name; // the name of the item
	
	/**
	 * constructor - creates a new Item object
	 * @param id
	 * @param name
	 */
	public Item(final Integer id, final String name)
	{
		this.id = id;
		this.name = name;
	}

	/**
	 * @return the id of the item
	 */
	public Integer getId() 
	{
		return id;
	}
	
	/**
	 * @return the name of the item
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * @param set the ID of the item
	 */
	public void setId(final Integer id)
	{
		this.id = id;
	}
	
	/**
	 * @param set the name of the item
	 */
	public void setName(final String name)
	{
		this.name = name;
	}
}
