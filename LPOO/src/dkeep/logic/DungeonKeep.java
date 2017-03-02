package dkeep.logic;

import java.util.Random;
import java.util.Scanner; //tmp

class DungeonKeep
{	
	// Represent coordinates, formatted {y, x}
	private Hero hero_;
	private Guard[] guards_;
	private Ogre[] ogres_;
	private Map map_;
	
	private int state_ = -1;
	private boolean unlocked_ = false;
	private Random rng_ = new Random();
	
	DungeonKeep(int level_id)
	{	
		hero_ = new Hero(level_id);
		guards_ = new Guard[Guard.get_amount(level_id)];
		for (Guard guard_ : guards_)
		{
			guard_.init(level_id);
		}
		ogres_ = new Ogre[Ogre.get_amount(level_id)];
		for (Ogre ogre_ : ogres_)
		{
			ogre_.init(level_id);
		}
		map_ = new Map(level_id);
		
	}
	
	public void update(String input)
	{
		// Read input
		switch(input)
		{
			case "w":
				hero_.update(0, -1);
				break;
			case "s":
				hero_.update(0, 1);
				break;
			case "a":
				hero_.update(-1, 0);
				break;
			case "d":
				hero_.update(1, 0);
				break;
			case "restart":
				return; // Maybe change game_state
			case "exit":
				return;
			default:
				return;
		}
		
		// Hero position
		map_.update(hero_.getX(), hero_.getY(), ' ');
		switch (map_.check(hero_.getNewX(), hero_.getNewY()))
		{
		case 'K':
			if (!unlocked_)
				unlocked_ = true;
		case 'S':
		case ' ':
			hero_.setX(hero_.getNewX());
			hero_.setY(hero_.getNewY());
		}
		hero_.setNewX(hero_.getX());
		hero_.setNewY(hero_.getY());
		
		// Guard position
		int guard_index_ = 0;
		for (Guard guard_ : guards_)
		{
			map_.update(guard_.getX(), guard_.getY(), ' ');
			guard_.update(guard_index_++);
		}
		
		// Missing ogre and club position
		
		// Represent characters (hierarchy)
		map_.refresh('K');
		if (unlocked_)
		{
			map_.refresh('S');
		}
		map_.update(hero_.getX(), hero_.getY(), 'H');
		for (Guard guard_ : guards_)
		{
			map_.update(guard_.getX(), guard_.getY(), 'G');
		}
		
		// Game state
		if (dungeon[5][0] == 'H' || dungeon[6][0] == 'H')
		{
			game_state = 1;
		}
		else if (dungeon[hero_y][hero_x] == 'G' ||
			dungeon[hero_y - 1][hero_x] == 'G' ||
			dungeon[hero_y + 1][hero_x] == 'G' ||
			dungeon[hero_y][hero_x - 1] == 'G' ||
			dungeon[hero_y][hero_x + 1] == 'G')
		{
			game_state = 0;
		}
	}
	public void display()
	{
		for (int i = 0; i < 50; ++i)
		{
			System.out.println("");
		}
		for (int i = 0; i < dungeon.length; ++i)
		{
			for (int j = 0; j < dungeon[i].length; ++j)
				System.out.print(dungeon[i][j] + " ");
			System.out.print('\n');
		}
		if (game_state == 0)
		{
			System.out.println("Game over!");
		}
		else if (game_state == 1)
		{
			System.out.println("Level cleared!");
		}
		// Some other game state:	System.out.println("Invalid input!");
	}
	}
	class Level_2
	{
		// Represent coordinates, formatted {y, x}
		private int game_state = -1;
		private int hero_x = 1, hero_y = 7;
		private int new_hero_x = 1, new_hero_y = 7;
		private boolean unlocked = false;

		private Random random = new Random();
		private void update()
		{			
			// Hero position
			dungeon[hero_y][hero_x] = ' ';
			switch (dungeon[new_hero_y][new_hero_x])
			{
			case 'K':
				if (!unlocked)
					unlocked = true;
			case 'S':
			case ' ':
				hero_x = new_hero_x;
				hero_y = new_hero_y;
			}
			new_hero_x = hero_x;
			new_hero_y = hero_y;
			
			// Ogre position
			dungeon[ogre_y][ogre_x] = ' ';
			while (true)
			{
				switch(random.nextInt(4))
				{
				case 0:
					switch (dungeon[ogre_y - 1][ogre_x])
					{
					case 'H':
					case 'K':
					case ' ':
						--ogre_y;
						break;
					default:
						continue;
					}
					break;
				case 1:
					switch (dungeon[ogre_y + 1][ogre_x])
					{
					case 'H':
					case 'K':
					case ' ':
						++ogre_y;
						break;
					default:
						continue;
					}
					break;
				case 2:
					switch (dungeon[ogre_y][ogre_x - 1])
					{
					case 'H':
					case 'K':
					case ' ':
						--ogre_x;
						break;
					default:
						continue;
					}
					break;
				case 3:
					switch (dungeon[ogre_y][ogre_x + 1])
					{
					case 'H':
					case 'K':
					case ' ':
						++ogre_x;
						break;
					default:
						continue;
					}
					break;
				}
				break;
			}

			// Club position
			dungeon[club_y][club_x] = ' ';
			club_x = ogre_x;
			club_y = ogre_y;
			while (true)
			{
				switch(random.nextInt(4))
				{
				case 0:
					switch (dungeon[club_y - 1][club_x])
					{
					case 'H':
					case 'K':
					case ' ':
						--club_y;
						break;
					default:
						continue;
					}
					break;
				case 1:
					switch (dungeon[club_y + 1][club_x])
					{
					case 'H':
					case 'K':
					case ' ':
						++club_y;
						break;
					default:
						continue;
					}
					break;
				case 2:
					switch (dungeon[club_y][club_x - 1])
					{
					case 'H':
					case 'K':
					case ' ':
						--club_x;
						break;
					default:
						continue;
					}
					break;
				case 3:
					switch (dungeon[club_y][club_x + 1])
					{
					case 'H':
					case 'K':
					case ' ':
						++club_x;
						break;
					default:
						continue;
					}
					break;
				}
				break;
			}
			
			// Represent characters (hierarchy)
			if (!unlocked)
			{
				dungeon[1][7] = 'K';
			}
			else
			{
				dungeon[1][0] = 'S';
			}
			dungeon[hero_y][hero_x] = 'H';
			dungeon[ogre_y][ogre_x] = (dungeon[ogre_y][ogre_x] == 'K' ? '$' : 'O');
			dungeon[club_y][club_x] = (dungeon[club_y][club_x] == 'K' ? '$' : '*');
			
			// Game state
			if (dungeon[1][0] == 'H')
			{
				game_state = 1;
			}
			else if (dungeon[hero_y][hero_x] == 'O' ||
				dungeon[hero_y - 1][hero_x] == 'O' ||
				dungeon[hero_y + 1][hero_x] == 'O' ||
				dungeon[hero_y][hero_x - 1] == 'O' ||
				dungeon[hero_y][hero_x + 1] == 'O' ||
				dungeon[hero_y][hero_x] == '*' ||
				dungeon[hero_y - 1][hero_x] == '*' ||
				dungeon[hero_y + 1][hero_x] == '*' ||
				dungeon[hero_y][hero_x - 1] == '*' ||
				dungeon[hero_y][hero_x + 1] == '*')
			{
				game_state = 0;
			}
		}
		private void display() // Same as below
		{
			for (int i = 0; i < 50; ++i)
				System.out.println("");
			for (int i = 0; i < dungeon.length; ++i)
			{
				for (int j = 0; j < dungeon[i].length; ++j)
					System.out.print(dungeon[i][j] + " ");
				System.out.print('\n');
			}
			if (game_state == 0)
			{
				System.out.println("Game over!");
			}
			else if (game_state == 1)
			{
				System.out.println("Level cleared!");
			}
		}
		public boolean run() // Even better, will make this into a Dungeon_Keep method
		{
			Scanner input = new Scanner(System.in);
			display();
			System.out.println("Introduza um carater (w,s,a,d) para mover o heroi");
			
			while (game_state == -1)
			{
				switch(input.next())
				{
					case "w":
						--new_hero_y;
						break;
					case "s":
						++new_hero_y;
						break;
					case "a":
						--new_hero_x;
						break;
					case "d":
						++new_hero_x;
						break;
					case "restart":
						return true;
					case "exit":
						return false;
					default:
						System.out.println("Invalid input!");
				}
				update();
				display();
			}
			String s;
			do s = input.next();
			while (!s.equals("restart") && !s.equals("exit"));
			if (s.equals("restart"))
				return true;
			return false;
		}
	}
}
