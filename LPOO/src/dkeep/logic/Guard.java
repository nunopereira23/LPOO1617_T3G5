package dkeep.logic;

class Guard
{
	private int level_id_;
	private int[][][] guard_pos_level_ = {{{8, 1}}, {{}}};
	private int[][][] guard_move_level_ = {{{1,2,2,2,2,1,1,1,1,1,1,2,3,3,3,3,3,3,3,0,0,0,0,0}}, {{}}};
	private int[] guard_move_index_level_;
	
	private int guard_type_; // Might make enum
	private int guard_x_, guard_y_;
	
	public Guard(int level_id, int guard_index)
	{
		level_id_ = level_id - 1;
		guard_x_ = guard_pos_level_[level_id_][guard_index][0];
		guard_y_ = guard_pos_level_[level_id_][guard_index][1];
		guard_move_index_level_ = new int[guard_move_level_[level_id_].length];
	}
	
	public static int getN(int level_id)
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
	
	public void update(int guard_index)
	{
		switch (guard_move_level_[level_id_][guard_index][guard_move_index_level_[guard_index]])
		{
			case 0:
				--guard_y_;
				break;
			case 1:
				--guard_x_;
				break;
			case 2:
				++guard_y_;
				break;
			case 3:
				++guard_x_;
				break;
		}
		if (++guard_move_index_level_[guard_index] == guard_move_level_[level_id_][guard_index].length)
		{
			guard_move_index_level_[guard_index] = 0;
		}
	}
	public int getX()
	{	
		return guard_x_;
	}
	public int getY()
	{	
		return guard_y_;
	}
}