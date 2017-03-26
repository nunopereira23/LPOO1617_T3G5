package dkeep.logic;

import java.util.Random;

public class Guard
{
	/**
	 *	Enum type that defines the guards' behaviour on their patrols
	 */
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
	
	/**
	 * Basic guard constructor
	 * @param level_id Identifies what information should be loaded
	 * @param guard_index Allows the loading of information particular to a certain guard in a certain level 
	 * @param guard_type Defines the behaviour of the guard
	 */
	public Guard(int level_id, int guard_index, int guard_type)
	{
		level_id_ = level_id;
		guard_x_ = guard_pos_level_[level_id_][guard_index][0];
		guard_y_ = guard_pos_level_[level_id_][guard_index][1];
		guard_move_index_level_ = new int[guard_move_level_[level_id_].length];
		switch (guard_type)
		{
			case 1:
				guard_type_ = Type.ROOKIE;
				break;
			case 2:
				guard_type_ = Type.DRUNKEN;
				break;
			case 3:
				guard_type_ = Type.SUSPICIOUS;
				break;
			default:
				break;
		}
	}
	
	/**
	 *	Special constructor used in JUnit tests
	 */
	public Guard(int[][] guard_pos, int[][] guard_move)
	{
		level_id_ = guard_pos_level_.length;
		
		int[][][] new_guard_pos_level_ = new int[guard_pos_level_.length + 1][][];
		for (int i = 0; i < guard_pos_level_.length; ++i)
		{
			new_guard_pos_level_[i] = new int[guard_pos_level_[i].length][];
			for (int j = 0; j < guard_pos_level_[i].length; ++j)
			{
				new_guard_pos_level_[i][j] = new int[]{guard_pos_level_[i][j][0], guard_pos_level_[i][j][1]};				
			}
		}
		new_guard_pos_level_[guard_pos_level_.length] = guard_pos;
		guard_pos_level_ = new_guard_pos_level_;
		
		int[][][] new_guard_move_level_ = new int[guard_move_level_.length + 1][][];
		for (int i = 0; i < guard_move_level_.length; ++i)
		{
			new_guard_move_level_[i] = new int[guard_move_level_[i].length][];
			for (int j = 0; j < guard_move_level_[i].length; ++j)
			{
				new_guard_move_level_[i][j] = new int[]{guard_move_level_[i][j][0], guard_move_level_[i][j][1]};				
			}
		}
		new_guard_move_level_[guard_move_level_.length] = guard_move;
		guard_move_level_ = new_guard_move_level_;
	}
	
	/**
	 *	Static method used for the initialization of the guards array, based on level
	 */
	public static int getN(int level_id)
	{
		return guard_pos_level_[level_id].length;
	}
	
	void update(int guard_index)
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
	
	/**
	 * Getter that returns a guard's position in the x-axis of the map
	 */
	public int getX()
	{	
		return guard_x_;
	}
	
	/**
	 * Getter that returns a guard's position in the y-axis of the map
	 */
	public int getY()
	{	
		return guard_y_;
	}
	
	/**
	 * Getter that returns a boolean based on whether a guard is asleep
	 */
	public boolean checkSleep()
	{
		return guard_asleep_;
	}
}