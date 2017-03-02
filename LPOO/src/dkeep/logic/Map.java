package dkeep.logic;

class Map
{
	private int map_id_;
	private char[][] map_1_ = {{'X','X','X','X','X','X','X','X','X','X'},
								{'X','H',' ',' ','I',' ','X',' ','G','X'},
								{'X','X','X',' ','X','X','X',' ',' ','X'},
								{'X',' ','I',' ','I',' ','X',' ',' ','X'},
								{'X','X','X',' ','X','X','X',' ',' ','X'},
								{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
								{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
								{'X','X','X',' ','X','X','X','X',' ','X'},
								{'X',' ','I',' ','I',' ','X','K',' ','X'},
								{'X','X','X','X','X','X','X','X','X','X'}};
	private char[][] map_2_ = {{'X','X','X','X','X','X','X','X','X'},
								{'I',' ',' ',' ','O',' ',' ','k','X'},
								{'X',' ',' ',' ','*',' ',' ',' ','X'},
								{'X',' ',' ',' ',' ',' ',' ',' ','X'},
								{'X',' ',' ',' ',' ',' ',' ',' ','X'},
								{'X',' ',' ',' ',' ',' ',' ',' ','X'},
								{'X',' ',' ',' ',' ',' ',' ',' ','X'},
								{'X','H',' ',' ',' ',' ',' ',' ','X'},
								{'X','X','X','X','X','X','X','X','X'}};
	Map(int level_id)
	{
		map_id_ = level_id;
	}
	public void update(int map_x, int map_y, char map_cell)
	{
		switch(map_id_)
		{
		case 1:
			map_1_[map_y][map_x] = map_cell;
			break;
		case 2:
			map_2_[map_y][map_x] = map_cell;
			break;
		}
	}
	public char check(int map_x, int map_y)
	{
		switch(map_id_)
		{
		case 1:
			return map_1_[map_y][map_x];
		case 2:
			return map_2_[map_y][map_x];
		}
		return ' ';
	}
}
