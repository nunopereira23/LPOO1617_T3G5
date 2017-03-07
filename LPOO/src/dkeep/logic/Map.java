package dkeep.logic;

class Map
{
	private int level_id_;
	private char[][][] map_level_ = {
								     {// Level 1
									  {'X','X','X','X','X','X','X','X','X','X'},
									  {'X','H',' ',' ','I',' ','X',' ','G','X'},
									  {'X','X','X',' ','X','X','X',' ',' ','X'},
									  {'X',' ','I',' ','I',' ','X',' ',' ','X'},
									  {'X','X','X',' ','X','X','X',' ',' ','X'},
									  {'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
									  {'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
									  {'X','X','X',' ','X','X','X','X',' ','X'},
									  {'X',' ','I',' ','I',' ','X','k',' ','X'},
									  {'X','X','X','X','X','X','X','X','X','X'}
								     },
								     {// Level 2
								      {'X','X','X','X','X','X','X','X','X'},
								      {'I',' ',' ',' ','O',' ',' ','k','X'},
								      {'X',' ',' ',' ','*',' ',' ',' ','X'},
								      {'X',' ',' ',' ',' ',' ',' ',' ','X'},
								      {'X',' ',' ',' ',' ',' ',' ',' ','X'},
								      {'X',' ',' ',' ',' ',' ',' ',' ','X'},
								      {'X',' ',' ',' ',' ',' ',' ',' ','X'},
								      {'X','H',' ',' ',' ',' ',' ',' ','X'},
								      {'X','X','X','X','X','X','X','X','X'}
								     }
								  	};
	private int[][][] map_doors_level_ = {
									  	  {// Level 1
									  	   {0,0,0,0,0,0,0,0,0,0},
									  	   {0,0,0,0,0,0,0,0,0,0},
									  	   {0,0,0,0,0,0,0,0,0,0},
									  	   {0,0,0,0,0,0,0,0,0,0},
									  	   {0,0,0,0,0,0,0,0,0,0},
									  	   {-1,0,0,0,0,0,0,0,0,0},
									  	   {-1,0,0,0,0,0,0,0,0,0},
									  	   {0,0,0,0,0,0,0,0,0,0},
									  	   {0,0,0,0,0,0,0,0,0,0},
									  	   {0,0,0,0,0,0,0,0,0,0}
									  	  },
									  	  {// Level 2
									  	   {0,0,0,0,0,0,0,0,0},
									  	   {1,0,0,0,0,0,0,0,0},
									  	   {0,0,0,0,0,0,0,0,0},
									  	   {0,0,0,0,0,0,0,0,0},
									  	   {0,0,0,0,0,0,0,0,0},
									  	   {0,0,0,0,0,0,0,0,0},
									  	   {0,0,0,0,0,0,0,0,0},
									  	   {0,0,0,0,0,0,0,0,0},
									  	   {0,0,0,0,0,0,0,0,0}
									 	  }
										 };
	private int[][][] map_keys_level_ = {
								   		 {// Level 1
								   		  {0,0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,-1,0,0},
								   		  {0,0,0,0,0,0,0,0,0,0}
								   		 },
								   		 {// Level 2
								   		  {0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,1,0},
								   		  {0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,0,0},
								   		  {0,0,0,0,0,0,0,0,0}
								   		 }
								  		};
	private char[][] map_backup_; // To use with refresh
	
	public Map(int level_id)
	{
		level_id_ = level_id - 1;
		map_backup_ = new char[map_level_[level_id_].length][];
		for (int i = 0; i < map_level_[level_id_].length; ++i)
		{
			map_backup_[i] = new char[map_level_[level_id_][i].length];
			for (int j = 0; j < map_level_[level_id_][i].length; ++j)
			{
				map_backup_[i][j] = map_level_[level_id_][i][j];
			}
		}
	}
	
	public void update(int map_x, int map_y, char map_char)
	{
		map_level_[level_id_][map_y][map_x] = map_char;
	}
	public char check(int map_x, int map_y)
	{
		return map_level_[level_id_][map_y][map_x];
	}
	public void refresh(char map_char)
	{
		for (int i = 0; i < map_level_[level_id_].length; ++i)
		{
			for (int j = 0; j < map_level_[level_id_][i].length; ++j)
			{
				if (map_backup_[i][j] == map_char)
				{
					map_level_[level_id_][i][j] = map_char;
				}
			}
		}
	}
	public char[][] display()
	{
		return map_level_[level_id_];
	}
	
	public int checkKey(int map_x, int map_y)
	{
		return map_keys_level_[level_id_][map_y][map_x];
	}
	public int pickKey(int map_x, int map_y)
	{
		map_backup_[map_y][map_x] = ' ';
		return map_keys_level_[level_id_][map_y][map_x];
	}
	public int checkDoor(int map_x, int map_y)
	{
		return map_doors_level_[level_id_][map_y][map_x];
	}
	public void unlockDoor(int key)
	{
		for (int i = 0; i < map_doors_level_[level_id_].length; ++i)
		{
			for (int j = 0; j < map_doors_level_[level_id_][i].length; ++j)
			{
				if (map_doors_level_[level_id_][i][j] == key)
				{
					map_level_[level_id_][i][j] = 'S';
					map_backup_[i][j] = 'S';
				}
			}
		}
	}
}
