package dkeep.logic;

import java.util.Random;

class Guard
{
	public enum Type {ROOKIE, DRUNKEN, SUSPICIOUS};
	
	private int level_id_;
	private static int[][][] guard_pos_level_ = {{{8, 1}}, {}};
	private static int[][][] guard_move_level_ = {{{1,2,2,2,2,1,1,1,1,1,1,2,3,3,3,3,3,3,3,0,0,0,0,0}}, {}};
	private int[] guard_move_index_level_;
	
	private Type guard_type_ = Type.ROOKIE;
	private boolean guard_asleep_ = false;
	private boolean guard_reversed_ = false;
	
	private int guard_x_, guard_y_;
	
	private static Random rng_ = new Random();
	
	public Guard(int level_id, int guard_index, Type guard_type)
	{
		level_id_ = level_id - 1;
		guard_x_ = guard_pos_level_[level_id_][guard_index][0];
		guard_y_ = guard_pos_level_[level_id_][guard_index][1];
		guard_move_index_level_ = new int[guard_move_level_[level_id_].length];
		guard_type_ = guard_type;
	}
	
	public static int getN(int level_id)
	{
		return guard_pos_level_[level_id - 1].length;
	}
	
	public void update(int guard_index)
	{	
		if (guard_type_ == Type.DRUNKEN && rng_.nextInt(5) == 0)
		{
			guard_asleep_ = !guard_asleep_;
			if (!guard_asleep_ && rng_.nextInt(5) == 0)
			{
				guard_reversed_ = !guard_reversed_;
				if (!guard_reversed_)
				{
					if (++guard_move_index_level_[guard_index] == guard_move_level_[level_id_][guard_index].length)
					{
						guard_move_index_level_[guard_index] = 0;
					}
				}
				else
				{
					if (--guard_move_index_level_[guard_index] == -1)
					{
						guard_move_index_level_[guard_index] = guard_move_level_[level_id_][guard_index].length - 1;
					}
				}
			}
		}
		if (guard_type_ == Type.SUSPICIOUS && rng_.nextInt(5) == 0)
		{
			guard_reversed_ = !guard_reversed_;
			if (!guard_reversed_)
			{
				if (++guard_move_index_level_[guard_index] == guard_move_level_[level_id_][guard_index].length)
				{
					guard_move_index_level_[guard_index] = 0;
				}
			}
			else
			{
				if (--guard_move_index_level_[guard_index] == -1)
				{
					guard_move_index_level_[guard_index] = guard_move_level_[level_id_][guard_index].length - 1;
				}
			}
		}
		
		if (!guard_asleep_)
		{
			if (!guard_reversed_)
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
			else
			{
				switch (guard_move_level_[level_id_][guard_index][guard_move_index_level_[guard_index]])
				{
					case 0:
						++guard_y_;
						break;
					case 1:
						++guard_x_;
						break;
					case 2:
						--guard_y_;
						break;
					case 3:
						--guard_x_;
						break;
				}
				if (--guard_move_index_level_[guard_index] == -1)
				{
					guard_move_index_level_[guard_index] = guard_move_level_[level_id_][guard_index].length - 1;
				}
			}
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
	
	public boolean checkSleep()
	{
		return guard_asleep_;
	}
}