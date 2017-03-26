package dkeep.logic;

import java.io.PrintStream;

import dkeep.logic.DungeonKeep.State;

public class DungeonKeep
{	
	/**
	 *	Enum type that describes the game's current state, with respect to the player's last movement
	 */
	public enum State {LEVEL_PLAYING, LEVEL_RESTART, LEVEL_COMPLETED, GAME_START, GAME_OVER, GAME_RESTART, GAME_COMPLETED, GAME_EXITING}
	
	/**
	 *  A unique identifier that describes the current level; every component of the logic uses it to understand which information they should acquire
	 */
	private int level_id_ = 0;
	private static int level_count_ = 2; // This is const
	
	private Hero hero_;
	private Guard[] guards_;
	private Ogre[] ogres_;
	private Map map_;
	
	/**
	 * Basic level constructor 
	 * @param level_id Identifies the level that should be loaded
	 * @param guard_type A number between 1 and 3, that represent a enum type Guard.Type that defines the guards' behaviour 
	 * @param ogre_number A number between 0 and 5, where 0 is ignored as a parameter, 
	 * 						and the rest define the amount of ogres that spawn in the level
	 * 						(if there is a ogre in the first place) 
	 */
	public DungeonKeep(int level_id, int guard_type, int ogre_number)
	{	
		level_id_ = level_id;
		hero_ = new Hero(level_id);
		guards_ = new Guard[Guard.getN(level_id)];
		for (int i = 0; i < guards_.length; ++i)
		{
			guards_[i] = new Guard(level_id, i, guard_type);
		}
		ogres_ = new Ogre[(ogre_number * Ogre.getN(level_id) == 0 ? Ogre.getN(level_id) : ogre_number)];
		for (int i = 0; i < ogres_.length; ++i)
		{
			ogres_[i] = new Ogre(level_id, (ogre_number == 0 ? i : 0));
		}
		map_ = new Map(level_id);
	}
	
	/**
	 *	Special constructor used in JUnit tests
	 */
	public DungeonKeep(int[] hero_pos, boolean hero_armed, char[][] map, int[][] map_doors, int[][] map_keys) {
		level_id_ = level_count_++;
		map_ = new Map(map, map_doors, map_keys);
		hero_ = new Hero(hero_pos, hero_armed);
		guards_ = new Guard[0];
		ogres_ = new Ogre[0];
	}
	
	/**
	 *	Special constructor used in JUnit tests
	 */
	public DungeonKeep(int[] hero_pos, boolean hero_armed, char[][] map, int[][] map_doors, int[][] map_keys, int[][] guard_pos, int[][] guard_move, int guard_type, int[][] ogre_pos, int[][] club_pos) {
		level_id_ = level_count_++;
		map_ = new Map(map, map_doors, map_keys);
		hero_ = new Hero(hero_pos, hero_armed);
		new Guard(guard_pos, guard_move);
		new Ogre(ogre_pos, club_pos);
		guards_ = new Guard[Guard.getN(level_id_)];
		for (int i = 0; i < guards_.length; ++i)
		{
			guards_[i] = new Guard(level_id_, i, guard_type);
		}
		ogres_ = new Ogre[Ogre.getN(level_id_)];
		for (int i = 0; i < ogres_.length; ++i)
		{
			ogres_[i] = new Ogre(level_id_, i);
		}
	}
	
	/**
	 *	Getter method used in JUnit tests
	 */
	public Hero getHero(){
		return this.hero_;
	}
	
	/**
	 *	Getter method used in JUnit tests
	 */
	public Guard[] getGuards(){
		return this.guards_;
	}
	
	/**
	 *	Getter method used in JUnit tests
	 */
	public Ogre[] getOgres(){
		return this.ogres_;
	}
	
	/**
	 *	Getter method used in JUnit tests
	 */
	public Map getMap(){
		return this.map_;
	}
	
	/**
	 *	Getter method used in JUnit tests
	 */
	public int[] getHeroPos(){
		return new int[] {this.hero_.getX(), this.hero_.getY()};
	}
	
	/**
	 * Method that updates the state of the game based on the input received through the String
	 * @param input String type that holds a basic form of user input
	 * @return Enum type State that after updating every element in the game, describes the state of the player,
	 * 			determining whether he may still play or not. Be aware that this function doesn't enforce the state that it returns,
	 * 			being dependent on the interface whether or not to continue the game after receiving the state.
	 */
	public State update(String input)
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
				return State.LEVEL_RESTART;
			case "reset":
				return State.GAME_RESTART;
			case "exit":
				return State.GAME_EXITING;
			default:
				return State.LEVEL_PLAYING;
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
			case '8': // Death play
			case 'g': // Death play
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
		map_.update(hero_.getX(), hero_.getY(), (hero_.checkKey(0) ? 'K' : (hero_.checkArmed() ? 'A' : 'H')));
		for (Guard guard_ : guards_)
		{
			map_.update(guard_.getX(), guard_.getY(), (guard_.checkSleep() ? 'g' : 'G'));
		}
		for (Ogre ogre_ : ogres_)
		{
			if (hero_.checkArmed() &&
					((Math.abs(hero_.getX() - ogre_.getOgreX()) < 2 && Math.abs(hero_.getY() - ogre_.getOgreY()) == 0) ||
					 (Math.abs(hero_.getX() - ogre_.getOgreX()) == 0 && Math.abs(hero_.getY() - ogre_.getOgreY()) < 2)))
			{
				ogre_.putStun();
			}
			
			if (map_.check(ogre_.getOgreX(), ogre_.getOgreY()) == 'k')
			{
				map_.update(ogre_.getOgreX(), ogre_.getOgreY(), (ogre_.checkStun() ? '8' : '$'));
				map_.update(ogre_.getClubX(), ogre_.getClubY(), '*');
			}
			else if (map_.check(ogre_.getClubX(), ogre_.getClubY()) == 'k')
			{
				map_.update(ogre_.getOgreX(), ogre_.getOgreY(), (ogre_.checkStun() ? '8' : 'O'));
				map_.update(ogre_.getClubX(), ogre_.getClubY(), '$');
			}
			else
			{
				map_.update(ogre_.getOgreX(), ogre_.getOgreY(), (ogre_.checkStun() ? '8' : 'O'));
				map_.update(ogre_.getClubX(), ogre_.getClubY(), '*');
			}
		}
		
		// Check game state
		if (hero_.getX() == 0 || hero_.getY() == 0)
		{
			if (level_id_ + 1 == level_count_)
			{
				return State.GAME_COMPLETED;
			}
			return State.LEVEL_COMPLETED;
		}
		else if (map_.check(hero_.getX(), hero_.getY()) == 'g' ||
				map_.check(hero_.getX(), hero_.getY()) == 'G' ||
				map_.check(hero_.getX() - 1, hero_.getY()) == 'G' ||
				map_.check(hero_.getX() + 1, hero_.getY()) == 'G' ||
				map_.check(hero_.getX(), hero_.getY() - 1) == 'G' ||
				map_.check(hero_.getX(), hero_.getY() + 1) == 'G' ||
				map_.check(hero_.getX(), hero_.getY()) == '8' ||
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
			return State.GAME_OVER;
		}
		
		return State.LEVEL_PLAYING;
	}
	
	/**
	 *	Method that outputs the game map to a stream. 
	 * @param stream Stream type that holds a string representation of the game map
	 */
	public void display(PrintStream stream)
	{
		for (int i = 0; i < 50; ++i)
		{
			stream.println();
		}
		for (int i = 0; i < map_.display().length; ++i)
		{
			for (int j = 0; j < map_.display()[i].length; ++j)
			{
				stream.print(map_.display()[i][j] + " ");
			}
			stream.println();
		}
	}
}