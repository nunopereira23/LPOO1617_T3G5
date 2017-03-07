package dkeep.logic;

public class DungeonKeep
{	
	public enum GameState {LEVEL_START, LEVEL_PLAYING, LEVEL_COMPLETED, LEVEL_RESTART, GAME_OVER, GAME_RESTART, GAME_COMPLETED, GAME_EXITING};
	
	// Must be changed accordingly
	private int count = 2;
	
	private Hero hero_;
	private Guard[] guards_;
	private Ogre[] ogres_;
	private Map map_;
	
	public DungeonKeep(int level_id)
	{	
		hero_ = new Hero(level_id);
		guards_ = new Guard[Guard.getN(level_id)];
		for (int i = 0; i < guards_.length; ++i)
		{
			guards_[i] = new Guard(level_id, i);
		}
		ogres_ = new Ogre[Ogre.getN(level_id)];
		for (int i = 0; i < ogres_.length; ++i)
		{
			ogres_[i] = new Ogre(level_id, i);
		}
		map_ = new Map(level_id);
	}
	
	public GameState update(String input)
	{
		// Read input
		switch(input)
		{
			case "w":
				hero_.update(0);
				break;
			case "a":
				hero_.update(1);
				break;
			case "s":
				hero_.update(2);
				break;
			case "d":
				hero_.update(3);
				break;
			case "restart":
				return GameState.LEVEL_RESTART;
			case "reset":
				return GameState.GAME_RESTART;
			case "exit":
				return GameState.GAME_EXITING;
			default:
				return GameState.LEVEL_PLAYING;
		}
		
		// Hero position
		map_.update(hero_.getX(), hero_.getY(), ' ');
		switch (map_.check(hero_.getNewX(), hero_.getNewY()))
		{
			case 'I':
				if (hero_.checkKey(map_.checkDoor(hero_.getNewX(), hero_.getNewY())))
				{
					map_.unlockDoor(map_.checkDoor(hero_.getNewX(), hero_.getNewY()));
				}
				break;
			case 'k':
				if (map_.checkKey(hero_.getNewX(), hero_.getNewY()) > 0)
				{
					hero_.pickKey(map_.pickKey(hero_.getNewX(), hero_.getNewY()));
				}
				else if (map_.checkKey(hero_.getNewX(), hero_.getNewY()) < 0)
				{
					map_.unlockDoor(map_.checkKey(hero_.getNewX(), hero_.getNewY()));
				}
			case 'S':
			case ' ':
				hero_.setCoord();
				break;
		}
		hero_.resetCoord();
		
		// Guard position
		for (int i = 0; i < guards_.length; ++i)
		{
			map_.update(guards_[i].getX(), guards_[i].getY(), ' ');
			guards_[i].update(i);
		}
		
		// Ogre position
		for (int i = 0; i < ogres_.length; ++i)
		{
			map_.update(ogres_[i].getOgreX(), ogres_[i].getOgreY(), ' ');
			map_.update(ogres_[i].getClubX(), ogres_[i].getClubY(), ' ');
			
			boolean not_set = true;
			do
			{
				ogres_[i].updateOgre();
				switch (map_.check(ogres_[i].getNewOgreX(), ogres_[i].getNewOgreY()))
				{
					case 'k':
					case ' ':
						ogres_[i].setOgreCoord();
						not_set = false;
						break;
				}
				ogres_[i].resetOgreCoord();
			}
			while (not_set);
			
			not_set = true;
			do
			{
				ogres_[i].updateClub();
				switch (map_.check(ogres_[i].getNewClubX(), ogres_[i].getNewClubY()))
				{
					case 'k':
					case ' ':
						ogres_[i].setClubCoord();
						not_set = false;
						break;
				}
				ogres_[i].resetClubCoord();
			}
			while (not_set);
		}
		
		// Represent characters (hierarchy)
		map_.refresh('k');
		map_.refresh('S');
		map_.update(hero_.getX(), hero_.getY(), hero_.checkKey(0) ? 'K' : 'H');
		for (Guard guard_ : guards_)
		{
			map_.update(guard_.getX(), guard_.getY(), 'G');
		}
		for (Ogre ogre_ : ogres_)
		{
			if (map_.check(ogre_.getOgreX(), ogre_.getOgreY()) == 'k')
			{
				map_.update(ogre_.getOgreX(), ogre_.getOgreY(), '$');
				map_.update(ogre_.getClubX(), ogre_.getClubY(), '*');
			}
			else if (map_.check(ogre_.getClubX(), ogre_.getClubY()) == 'k')
			{
				map_.update(ogre_.getOgreX(), ogre_.getOgreY(), 'O');
				map_.update(ogre_.getClubX(), ogre_.getClubY(), '$');
			}
			else
			{
				map_.update(ogre_.getOgreX(), ogre_.getOgreY(), 'O');
				map_.update(ogre_.getClubX(), ogre_.getClubY(), '*');
			}
		}
		
		// Check game state
		if (hero_.getX() == 0 || hero_.getY() == 0)
		{
			return GameState.LEVEL_COMPLETED;
		}
		else if (map_.check(hero_.getX(), hero_.getY()) == 'G' ||
				map_.check(hero_.getX() - 1, hero_.getY()) == 'G' ||
				map_.check(hero_.getX() + 1, hero_.getY()) == 'G' ||
				map_.check(hero_.getX(), hero_.getY() - 1) == 'G' ||
				map_.check(hero_.getX(), hero_.getY() + 1) == 'G' ||
				map_.check(hero_.getX(), hero_.getY()) == 'O' ||
				map_.check(hero_.getX() - 1, hero_.getY()) == 'O' ||
				map_.check(hero_.getX() + 1, hero_.getY()) == 'O' ||
				map_.check(hero_.getX(), hero_.getY() - 1) == 'O' ||
				map_.check(hero_.getX(), hero_.getY() + 1) == 'O' ||
				map_.check(hero_.getX(), hero_.getY()) == '*' ||
				map_.check(hero_.getX() - 1, hero_.getY()) == '*' ||
				map_.check(hero_.getX() + 1, hero_.getY()) == '*' ||
				map_.check(hero_.getX(), hero_.getY() - 1) == '*' ||
				map_.check(hero_.getX(), hero_.getY() + 1) == '*' ||
				map_.check(hero_.getX(), hero_.getY()) == '$' ||
				map_.check(hero_.getX() - 1, hero_.getY()) == '$' ||
				map_.check(hero_.getX() + 1, hero_.getY()) == '$' ||
				map_.check(hero_.getX(), hero_.getY() - 1) == '$' ||
				map_.check(hero_.getX(), hero_.getY() + 1) == '$')
		{
			return GameState.GAME_OVER;
		}
		
		return GameState.LEVEL_PLAYING;
	}
	public void display()
	{
		for (int i = 0; i < 50; ++i)
		{
			System.out.println();
		}
		for (int i = 0; i < map_.display().length; ++i)
		{
			for (int j = 0; j < map_.display()[i].length; ++j)
			{
				System.out.print(map_.display()[i][j] + " ");
			}
			System.out.println();
		}
	}
	public int count()
	{
		return count;
	}
}