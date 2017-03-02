package dkeep.logic;

class Guard
{
	private int level_id_;
	private int[][][] guard_map_ = {{{3,2,2,2,2,3,3,3,3,3,3,2,1,1,1,1,1,1,1,0,0,0,0,0}}, {{}}};
	private int[][][] guard_map_pos_ = {{{1, 8}}, {{}}};
	
	private int guard_type_;
	private int guard_x_, guard_y_;
	private int guard_pos_index_ = 0;
	
	public void init(int level_id)
	{
		level_id_ = level_id;
		guard_x_ = guard_map_pos_[level_id_][0][1];
		guard_y_ = guard_map_pos_[level_id_][0][0];
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
	public int getY()
	{	
		return guard_y_;
	}
	public void update(int guard_index)
	{
		switch (guard_map_[level_id_][guard_index][guard_pos_index_])
		{
		case 0:
			--guard_y_;
			break;
		case 1:
			++guard_x_;
			break;
		case 2:
			++guard_y_;
			break;
		case 3:
			--guard_x_;
			break;
		}
	}
}