package dkeep.logic;

class Guard
{
	private int guard_id_;
	private char[] guard_1_map_1_ = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
	private int[] guard_1_map_1_pos_ = {1, 8};
	
	private int guard_x_, guard_y_;
	private int guard_type_;
	private int guard_index_ = 0;
	public void init(int level_id)
	{
		guard_id_ = level_id;
		switch (guard_id_)
		{
		case 1:
			guard_x_ = guard_1_map_1_pos_[1];
			guard_y_ = guard_1_map_1_pos_[0];
			break;
		case 2:
			break;
		}
	}
	public static int get_amount(int level_id)
	{
		switch (level_id)
		{
		case 1:
			return 1;
		case 2:
			return 0;
		}
		return -1;
	}
	public int getX()
	{	
		return guard_x_;
	}
	
}