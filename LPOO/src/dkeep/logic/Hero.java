package dkeep.logic;

public class Hero
{
	private int level_id_;
	private int[][] hero_pos_level_ = {{1, 1}, {1, 7}};
	private boolean[] hero_armed_level_ = {false, true};
	private int[][] hero_keys_level_ = {{}, {}};
	
	private int hero_x_, hero_y_;
	private int new_hero_x_, new_hero_y_;
	private boolean hero_armed_; 
	
	public Hero(int level_id)
	{
		level_id_ = level_id;
		new_hero_x_ = hero_x_ = hero_pos_level_[level_id_][0];
		new_hero_y_ = hero_y_ = hero_pos_level_[level_id_][1];
		hero_armed_ = hero_armed_level_[level_id_];
	}
	
	public Hero(int[] hero_pos){
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
		new_hero_armed_level_[hero_armed_level_.length] = false;
		hero_armed_level_ = new_hero_armed_level_;
		int[][] new_hero_keys_level_ = new int[hero_keys_level_.length + 1][];
		for (int i = 0; i < hero_keys_level_.length; ++i)
		{
			new_hero_keys_level_[i] = hero_keys_level_[i];
		}
		new_hero_keys_level_[hero_keys_level_.length] = new int[]{0};
		hero_keys_level_ = new_hero_keys_level_;
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
	
	public int getX()
	{
		return hero_x_;
	}
	public int getY()
	{
		return hero_y_;
	}
	public int getNewX()
	{
		return new_hero_x_;
	}
	public int getNewY()
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
	
	public boolean checkKey(int key)
	{
		if (key != 0)
		{
			for (int i = 0; i < hero_keys_level_[level_id_].length; ++i)
			{
				if (hero_keys_level_[level_id_][i] == key)
				{
					return true;
				}
			}
		}
		else if (hero_keys_level_[level_id_].length > 0)
		{
			return true;
		}
		return false;
	}
	
	void pickKey(int key)
	{
		int[] new_hero_keys_level_ = new int[hero_keys_level_[level_id_].length + 1];
		for (int i = 0; i < hero_keys_level_[level_id_].length; ++i)
		{
			new_hero_keys_level_[i] = hero_keys_level_[level_id_][i]; 
		}
		new_hero_keys_level_[hero_keys_level_[level_id_].length] = key;
		hero_keys_level_[level_id_] = new_hero_keys_level_;
	}
	
	public boolean checkArmed()
	{
		return hero_armed_;
	}
}
