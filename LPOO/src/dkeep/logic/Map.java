package dkeep.logic;

class Map
{
	private int level_id_;
	private char[][][] map_ = {
								{{'X','X','X','X','X','X','X','X','X','X'},
								 {'X','H',' ',' ','I',' ','X',' ','G','X'},
								 {'X','X','X',' ','X','X','X',' ',' ','X'},
								 {'X',' ','I',' ','I',' ','X',' ',' ','X'},
								 {'X','X','X',' ','X','X','X',' ',' ','X'},
								 {'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
								 {'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
								 {'X','X','X',' ','X','X','X','X',' ','X'},
								 {'X',' ','I',' ','I',' ','X','K',' ','X'},
								 {'X','X','X','X','X','X','X','X','X','X'}},
			
								{{'X','X','X','X','X','X','X','X','X'},
								 {'I',' ',' ',' ','O',' ',' ','k','X'},
								 {'X',' ',' ',' ','*',' ',' ',' ','X'},
								 {'X',' ',' ',' ',' ',' ',' ',' ','X'},
								 {'X',' ',' ',' ',' ',' ',' ',' ','X'},
								 {'X',' ',' ',' ',' ',' ',' ',' ','X'},
								 {'X',' ',' ',' ',' ',' ',' ',' ','X'},
								 {'X','H',' ',' ',' ',' ',' ',' ','X'},
								 {'X','X','X','X','X','X','X','X','X'}}
																		};
	private char[][] map_backup_;
	Map(int level_id)
	{
		level_id_ = level_id;
		map_backup_ = map_[level_id_];
	}
	public void update(int map_x, int map_y, char map_char)
	{
		map_[level_id_][map_y][map_x] = map_char;
	}
	public char check(int map_x, int map_y)
	{
		return map_[level_id_][map_y][map_x];
	}
	public void refresh(char map_char)
	{
		for (int i = 0; i < map_[level_id_].length; ++i)
		{
			for (int j = 0; j < map_[level_id_][i].length; ++j)
			{
				if (map_backup_[i][j] == map_char)
				{
					map_[level_id_][i][j] = map_char;
				}
			}
		}
	}
}
