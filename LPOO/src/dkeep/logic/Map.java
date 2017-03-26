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
	
	
	/**
	 *  Basic map constructor, it also creates a backup to use in conjunction with other methods
	 * @param level_id Identifies the map to load
	 */
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
	
	/**
	 *	Special constructor used in JUnit tests
	 */
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
	
	/**
	 *	Getter that returns the character in the map with coordinates equal to the arguments 
	 */
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
	
	/**
	 * Getter that returns the size of the level map in the x-axis
	 */
	public int getMapXSize()
	{
		return map_[0].length;
	}
	
	/**
	 * Getter that returns the size of the level map in the y-axis
	 */
	public int getMapYSize()
	{
		return map_.length;
	}
	
	/**
	 * Getter that returns the value of the key (or lever) in the given position, if there is one (returns 0 otherwise)
	 */
	public int checkKey(int map_x, int map_y)
	{
		return map_keys_level_[level_id_][map_y][map_x];
	}
	int pickKey(int map_x, int map_y)
	{
		map_backup_[map_y][map_x] = ' ';
		return map_keys_level_[level_id_][map_y][map_x];
	}
	
	/**
	 * Getter that returns the value of the door (the key/lever with the corresponding value opens that door) in the given position,
	 *  if there is one (returns 0 otherwise, or if the door can't be open)
	 */
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
