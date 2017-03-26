package dkeep.logic;

public class Map
{
	private int level_id_;	
	private static char[][][] map_level_ = {
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
								      {'X','A',' ',' ',' ',' ',' ',' ','X'},
								      {'X','X','X','X','X','X','X','X','X'}
								     }
								  	};
	private static int[][][] map_doors_level_ = {
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
	private static int[][][] map_keys_level_ = {
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
	private char[][] map_;
	private char[][] map_backup_; // To use with refresh
	
	public Map(int level_id)
	{
		level_id_ = level_id;
		map_ = new char[map_level_[level_id_].length][];
		for (int i = 0; i < map_level_[level_id_].length; ++i)
		{
			map_[i] = new char[map_level_[level_id_][i].length];
			for (int j = 0; j < map_level_[level_id_][i].length; ++j)
			{
				map_[i][j] = map_level_[level_id_][i][j];
			}
		}
		
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
	
	public Map(char[][] map, int[][] map_doors, int[][] map_keys){
		level_id_ = map_doors_level_.length;
		
		char[][][] new_map_level_ = new char[map_level_.length + 1][][];
		for (int i = 0; i < map_level_.length; ++i)
		{
			new_map_level_[i] = map_level_[i];
		}
		new_map_level_[map_level_.length] = map;
		map_level_ = new_map_level_;
		
		int[][][] new_map_doors_level_ = new int[map_doors_level_.length + 1][][];
		for (int i = 0; i < map_doors_level_.length; ++i)
		{
			new_map_doors_level_[i] = map_doors_level_[i];
		}
		new_map_doors_level_[map_doors_level_.length] = map_doors; 
		map_doors_level_ = new_map_doors_level_;
		
		int[][][] new_map_keys_level_ = new int[map_keys_level_.length + 1][][];
		for (int i = 0; i < map_keys_level_.length; ++i)
		{
			new_map_keys_level_[i] = map_keys_level_[i];
		}
		new_map_keys_level_[map_keys_level_.length] = map_keys; 
		map_keys_level_ = new_map_keys_level_;
		
		map_ = new char[map_level_[level_id_].length][];
		for (int i = 0; i < map_level_[level_id_].length; ++i)
		{
			map_[i] = new char[map_level_[level_id_][i].length];
			for (int j = 0; j < map_level_[level_id_][i].length; ++j)
			{
				map_[i][j] = map_level_[level_id_][i][j];
			}
		}
		
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
	
	void update(int map_x, int map_y, char map_char)
	{
		map_[map_y][map_x] = map_char;
	}
	public char check(int map_x, int map_y)
	{
		return map_[map_y][map_x];
	}
	void refresh(char map_char)
	{
		for (int i = 0; i < map_.length; ++i)
		{
			for (int j = 0; j < map_[i].length; ++j)
			{
				if (map_backup_[i][j] == map_char)
				{
					map_[i][j] = map_char;
				}
			}
		}
	}
	
	char[][] display()
	{
		return map_;
	}
	
	public int getMapXSize()
	{
		return map_[0].length;
	}
	public int getMapYSize()
	{
		return map_.length;
	}
	
	public int checkKey(int map_x, int map_y)
	{
		return map_keys_level_[level_id_][map_y][map_x];
	}
	int pickKey(int map_x, int map_y)
	{
		map_backup_[map_y][map_x] = ' ';
		return map_keys_level_[level_id_][map_y][map_x];
	}
	
	public int checkDoor(int map_x, int map_y)
	{
		return map_doors_level_[level_id_][map_y][map_x];
	}
	void unlockDoor(int key)
	{
		for (int i = 0; i < map_doors_level_[level_id_].length; ++i)
		{
			for (int j = 0; j < map_doors_level_[level_id_][i].length; ++j)
			{
				if (map_doors_level_[level_id_][i][j] == key)
				{
					map_[i][j] = 'S';
					map_backup_[i][j] = 'S';
				}
			}
		}
	}
}
