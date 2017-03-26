package dkeep.logic;

public class Hero
{
	private int level_id_;
	private static int[][] hero_pos_level_ = {{1, 1}, {1, 7}};
	private static boolean[] hero_armed_level_ = {false, true};
		
	private int hero_x_, hero_y_;
	private int new_hero_x_, new_hero_y_;
	private boolean hero_armed_;
	private int[] hero_keys_ = {};
	
	/**
	 * Basic hero constructor
	 * @param level_id Identifies what information should be loaded
	 */
	public Hero(int level_id)
	{
		level_id_ = level_id;
		new_hero_x_ = hero_x_ = hero_pos_level_[level_id_][0];
		new_hero_y_ = hero_y_ = hero_pos_level_[level_id_][1];
		hero_armed_ = hero_armed_level_[level_id_];
	}
	
	/**
	 *	Special constructor used in JUnit tests
	 */
	public Hero(int[] hero_pos, boolean hero_armed){
		level_id_ = hero_pos_level_.length;
		
		int[][] new_hero_pos_level_ = new int[hero_pos_level_.length + 1][];
		for (int i = 0; i < hero_pos_level_.length; ++i)
		{
			new_hero_pos_level_[i] = hero_pos_level_[i];
		}
		new_hero_pos_level_[hero_pos_level_.length] = hero_pos;
		hero_pos_level_ = new_hero_pos_level_;
		
		boolean[] new_hero_armed_level_ = new boolean[hero_armed_level_.length + 1];
		for (int i = 0; i < hero_armed_level_.length; ++i)
		{
			new_hero_armed_level_[i] = hero_armed_level_[i];
		}
		new_hero_armed_level_[hero_armed_level_.length] = hero_armed;
		hero_armed_level_ = new_hero_armed_level_;
		
		new_hero_x_ = hero_x_ = hero_pos_level_[level_id_][0];
		new_hero_y_ = hero_y_ = hero_pos_level_[level_id_][1];
		hero_armed_ = hero_armed_level_[level_id_];
	}
	
	void update(int hero_pos)
	{
		switch (hero_pos)
		{
		case 0:
			--new_hero_y_;
			break;
		case 1:
			--new_hero_x_;
			break;
		case 2:
			++new_hero_y_;
			break;
		case 3:
			++new_hero_x_;
			break;
		}
	}
	
	/**
	 * Getter that returns the hero's position in the x-axis of the map
	 */
	public int getX()
	{
		return hero_x_;
	}
	
	/**
	 * Getter that returns the hero's position in the y-axis of the map
	 */
	public int getY()
	{
		return hero_y_;
	}

	int getNewX()
	{
		return new_hero_x_;
	}
	
	int getNewY()
	{
		return new_hero_y_;
	}
	
	void setCoord()
	{
		hero_x_ = new_hero_x_;
		hero_y_ = new_hero_y_;
	}
	void resetCoord()
	{
		new_hero_x_ = hero_x_;
		new_hero_y_ = hero_y_; 
	}
	
	/**
	 * Getter that returns a boolean based on whether the hero holds a particular key, or any at all
	 * @param key If this argument is 0, it checks if the hero holds any key; if it is anything other than 0,
	 * 				it checks if the hero holds that particular key. Note that the keys can only take positive values
	 */
	public boolean checkKey(int key)
	{
		if (key != 0)
		{
			for (int i = 0; i < hero_keys_.length; ++i)
			{
				if (hero_keys_[i] == key)
				{
					return true;
				}
			}
		}
		else if (hero_keys_.length > 0)
		{
			return true;
		}
		return false;
	}
		
	void pickKey(int key)
	{
		int[] new_hero_keys_level_ = new int[hero_keys_.length + 1];
		for (int i = 0; i < hero_keys_.length; ++i)
		{
			new_hero_keys_level_[i] = hero_keys_[i]; 
		}
		new_hero_keys_level_[hero_keys_.length] = key;
		hero_keys_ = new_hero_keys_level_;
	}
	
	/**
	 * Getter that returns a boolean based on whether the hero is armed (and therefore, can stun ogres) 
	 */
	public boolean checkArmed()
	{
		return hero_armed_;
	}
}
