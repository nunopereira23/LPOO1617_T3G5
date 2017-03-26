package dkeep.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DKGraphics extends JPanel {
	private DKInterfaceV2 window = null;
	
	private BufferedImage img_title_screen;
	private BufferedImage img_level_complete_screen;
	private BufferedImage img_game_over_screen;
	private BufferedImage img_game_complete_screen;
	private BufferedImage img_delete_icon;
	
	private BufferedImage img_hero_north;
	private BufferedImage img_hero_west;
	private BufferedImage img_hero_east;
	private BufferedImage img_hero_south;
	
	private BufferedImage img_hero_key_north;
	private BufferedImage img_hero_key_west;
	private BufferedImage img_hero_key_east;
	private BufferedImage img_hero_key_south;
	
	private BufferedImage img_hero_armed_north;
	private BufferedImage img_hero_armed_west;
	private BufferedImage img_hero_armed_east;
	private BufferedImage img_hero_armed_south;
	
	private BufferedImage img_hero_key_armed_north;
	private BufferedImage img_hero_key_armed_west;
	private BufferedImage img_hero_key_armed_east;
	private BufferedImage img_hero_key_armed_south;
	
	private BufferedImage img_guard_north;
	private BufferedImage img_guard_west;
	private BufferedImage img_guard_east;
	private BufferedImage img_guard_south;
	
	private BufferedImage img_guard_asleep_north;
	private BufferedImage img_guard_asleep_west;
	private BufferedImage img_guard_asleep_east;
	private BufferedImage img_guard_asleep_south;
	
	private BufferedImage img_ogre_north;
	private BufferedImage img_ogre_west;
	private BufferedImage img_ogre_east;
	private BufferedImage img_ogre_south;

	private BufferedImage img_ogre_stunned_north;
	private BufferedImage img_ogre_stunned_west;
	private BufferedImage img_ogre_stunned_east;
	private BufferedImage img_ogre_stunned_south;
	
	private BufferedImage img_club_north;
	private BufferedImage img_club_west;
	private BufferedImage img_club_east;
	private BufferedImage img_club_south;
	
	private BufferedImage img_wall;
	private BufferedImage img_door_closed;
	private BufferedImage img_door_open;
	private BufferedImage img_key;
	private BufferedImage img_lever;
	
	private int[] hero_last_pos = {};
	private int hero_current_pos = 3;
	private int[][] guards_last_pos = {};
	private int[] guards_current_pos = {};
	
	private char draw_id = ' ';
	private char[][] draw_id_array = {};
	
	/**
	 * Create the panel.
	 */
	public DKGraphics() {
		initialize();
	}
	public DKGraphics(DKInterfaceV2 window) {
		this.window = window;
		
		initialize();
	}
	
	void initialize() {
		try {
			img_title_screen = ImageIO.read(new File("src/resources/DungeonKeep.png"));
			img_level_complete_screen = ImageIO.read(new File("src/resources/LevelComplete.png"));
			img_game_over_screen = ImageIO.read(new File("src/resources/GameOver.png"));
			img_game_complete_screen = ImageIO.read(new File("src/resources/GameCleared.png"));
			img_delete_icon = ImageIO.read(new File("src/resources/Cross.png"));
			
			img_hero_north = ImageIO.read(new File("src/resources/HeroNorth.png"));
			img_hero_west = ImageIO.read(new File("src/resources/HeroWest.png"));
			img_hero_east = ImageIO.read(new File("src/resources/HeroEast.png"));
			img_hero_south = ImageIO.read(new File("src/resources/HeroSouth.png"));
			
			img_hero_key_north = ImageIO.read(new File("src/resources/HeroKeyNorth.png"));
			img_hero_key_west = ImageIO.read(new File("src/resources/HeroKeyWest.png"));
			img_hero_key_east = ImageIO.read(new File("src/resources/HeroKeyEast.png"));
			img_hero_key_south = ImageIO.read(new File("src/resources/HeroKeySouth.png"));
			
			img_hero_armed_north = ImageIO.read(new File("src/resources/HeroArmedNorth.png"));
			img_hero_armed_west = ImageIO.read(new File("src/resources/HeroArmedWest.png"));
			img_hero_armed_east = ImageIO.read(new File("src/resources/HeroArmedEast.png"));
			img_hero_armed_south = ImageIO.read(new File("src/resources/HeroArmedSouth.png"));
			
			img_hero_key_armed_north = ImageIO.read(new File("src/resources/HeroKeyArmedNorth.png"));
			img_hero_key_armed_west = ImageIO.read(new File("src/resources/HeroKeyArmedWest.png"));
			img_hero_key_armed_east = ImageIO.read(new File("src/resources/HeroKeyArmedEast.png"));
			img_hero_key_armed_south = ImageIO.read(new File("src/resources/HeroKeyArmedSouth.png"));
			
			img_guard_north = ImageIO.read(new File("src/resources/GuardNorth.png"));
			img_guard_west = ImageIO.read(new File("src/resources/GuardWest.png"));
			img_guard_east = ImageIO.read(new File("src/resources/GuardEast.png"));
			img_guard_south = ImageIO.read(new File("src/resources/GuardSouth.png"));
			
			img_guard_asleep_north = ImageIO.read(new File("src/resources/GuardAsleepNorth.png"));
			img_guard_asleep_west = ImageIO.read(new File("src/resources/GuardAsleepWest.png"));
			img_guard_asleep_east = ImageIO.read(new File("src/resources/GuardAsleepEast.png"));
			img_guard_asleep_south = ImageIO.read(new File("src/resources/GuardAsleepSouth.png"));
			
			img_ogre_north = ImageIO.read(new File("src/resources/OgreNorth.png"));
			img_ogre_west = ImageIO.read(new File("src/resources/OgreWest.png"));
			img_ogre_east = ImageIO.read(new File("src/resources/OgreEast.png"));
			img_ogre_south = ImageIO.read(new File("src/resources/OgreSouth.png"));

			img_ogre_stunned_north = ImageIO.read(new File("src/resources/OgreStunnedNorth.png"));
			img_ogre_stunned_west = ImageIO.read(new File("src/resources/OgreStunnedWest.png"));
			img_ogre_stunned_east = ImageIO.read(new File("src/resources/OgreStunnedEast.png"));
			img_ogre_stunned_south = ImageIO.read(new File("src/resources/OgreStunnedSouth.png"));
			
			img_club_north = ImageIO.read(new File("src/resources/ClubNorth.png"));
			img_club_west = ImageIO.read(new File("src/resources/ClubWest.png"));
			img_club_east = ImageIO.read(new File("src/resources/ClubEast.png"));
			img_club_south = ImageIO.read(new File("src/resources/ClubSouth.png"));
			
			img_wall = ImageIO.read(new File("src/resources/Wall.png"));
			img_door_closed = ImageIO.read(new File("src/resources/DoorClosed.png"));
			img_door_open = ImageIO.read(new File("src/resources/DoorOpen.png"));
			img_key = ImageIO.read(new File("src/resources/Key.png"));
			img_lever = ImageIO.read(new File("src/resources/Lever.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if (window != null)
		{
			switch (window.getState())
			{
				case GAME_START:
					g.drawImage(img_title_screen, 0, 0, window.getWidth(), window.getHeight(), null);
					break;
				case GAME_OVER:
					g.drawImage(img_game_over_screen, 0, 0, window.getWidth(), window.getHeight(), null);
					break;
				case GAME_COMPLETED:
					g.drawImage(img_game_complete_screen, 0, 0, window.getWidth(), window.getHeight(), null);
					break;
				case LEVEL_COMPLETED:
					g.drawImage(img_level_complete_screen, 0, 0, window.getWidth(), window.getHeight(), null);
					break;
				case LEVEL_PLAYING:
					if (hero_last_pos.length == 0)
					{
						hero_last_pos = new int[]{window.getLevel().getHero().getX(), window.getLevel().getHero().getY()};
					}
					if (guards_last_pos.length == 0)
					{
						guards_last_pos = new int[window.getLevel().getGuards().length][];
						guards_current_pos = new int[window.getLevel().getGuards().length];
						for (int i = 0; i < window.getLevel().getGuards().length; ++i)
						{
							guards_last_pos[i] = new int[]{window.getLevel().getGuards()[i].getX(), window.getLevel().getGuards()[i].getY()};
							guards_current_pos[i] = 3;
						}
					}
					
					int x_count = window.getLevel().getMap().getMapXSize(), y_count = window.getLevel().getMap().getMapYSize();
					int x_size = window.getWidth() / x_count, y_size = (window.getHeight() - 30) / y_count;
					
					for (int y = 0; y < y_count; ++y)
					{
						for (int x = 0; x < x_count; ++x)
						{						
							switch (window.getLevel().getMap().check(x, y))
							{
								case ' ':
									break;
								case 'X':
									g.drawImage(img_wall, x * x_size, y * y_size, x_size, y_size, null);
									break;
								case 'I':
									g.drawImage(img_door_closed, x * x_size, y * y_size, x_size, y_size, null);
									break;
								case 'S':
									g.drawImage(img_door_open, x * x_size, y * y_size, x_size, y_size, null);
									break;
								case 'k':
									if (window.getLevel().getMap().checkKey(x, y) > 0)
										g.drawImage(img_key, x * x_size, y * y_size, x_size, y_size, null);
									else
										g.drawImage(img_lever, x * x_size, y * y_size, x_size, y_size, null);
									break;								
							}		
						}
					}
					
					if (window.getLevel().getHero().checkArmed())
					{
						if (window.getLevel().getHero().checkKey(0))
						{
							switch (window.getLevel().getHero().getY() - hero_last_pos[1])
							{
								case -1:
									g.drawImage(img_hero_key_armed_north, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
									hero_current_pos = 0;
									break;
								case 0:
									switch (window.getLevel().getHero().getX() - hero_last_pos[0])
									{
										case -1:
											g.drawImage(img_hero_key_armed_west, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
											hero_current_pos = 1;
											break;
										case 0:
											switch (hero_current_pos)
											{
												case 0:
													g.drawImage(img_hero_key_armed_north, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
												case 1:
													g.drawImage(img_hero_key_armed_west, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
												case 2:
													g.drawImage(img_hero_key_armed_east, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
												case 3:
													g.drawImage(img_hero_key_armed_south, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
											}
											break;
										case 1:
											g.drawImage(img_hero_key_armed_east, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
											hero_current_pos = 2;
											break;
									}
									break;
								case 1:
									g.drawImage(img_hero_key_armed_south, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
									hero_current_pos = 3;
									break;
							}
						}
						else
						{
							switch (window.getLevel().getHero().getY() - hero_last_pos[1])
							{
								case -1:
									g.drawImage(img_hero_armed_north, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
									hero_current_pos = 0;
									break;
								case 0:
									switch (window.getLevel().getHero().getX() - hero_last_pos[0])
									{
										case -1:
											g.drawImage(img_hero_armed_west, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
											hero_current_pos = 1;
											break;
										case 0:
											switch (hero_current_pos)
											{
												case 0:
													g.drawImage(img_hero_armed_north, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
												case 1:
													g.drawImage(img_hero_armed_west, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
												case 2:
													g.drawImage(img_hero_armed_east, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
												case 3:
													g.drawImage(img_hero_armed_south, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
											}
											break;
										case 1:
											g.drawImage(img_hero_armed_east, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
											hero_current_pos = 2;
											break;
									}
									break;
								case 1:
									g.drawImage(img_hero_armed_south, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
									hero_current_pos = 3;
									break;
							}
						}
					}
					else
					{
						if (window.getLevel().getHero().checkKey(0))
						{
							switch (window.getLevel().getHero().getY() - hero_last_pos[1])
							{							
								case -1:
									g.drawImage(img_hero_key_north, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
									hero_current_pos = 0;
									break;
								case 0:
									switch (window.getLevel().getHero().getX() - hero_last_pos[0])
									{
										case -1:
											g.drawImage(img_hero_key_west, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
											hero_current_pos = 1;
											break;
										case 0:
											switch (hero_current_pos)
											{
												case 0:
													g.drawImage(img_hero_key_north, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
												case 1:
													g.drawImage(img_hero_key_west, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
												case 2:
													g.drawImage(img_hero_key_east, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
												case 3:
													g.drawImage(img_hero_key_south, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
											}
											break;
										case 1:
											g.drawImage(img_hero_key_east, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
											hero_current_pos = 2;
											break;
									}
									break;
								case 1:
									g.drawImage(img_hero_key_south, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
									hero_current_pos = 3;
									break;
							}
						}
						else
						{
							switch (window.getLevel().getHero().getY() - hero_last_pos[1])
							{							
								case -1:
									g.drawImage(img_hero_north, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
									hero_current_pos = 0;
									break;
								case 0:
									switch (window.getLevel().getHero().getX() - hero_last_pos[0])
									{
										case -1:
											g.drawImage(img_hero_west, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
											hero_current_pos = 1;
											break;
										case 0:
											switch (hero_current_pos)
											{
												case 0:
													g.drawImage(img_hero_north, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
												case 1:
													g.drawImage(img_hero_west, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
												case 2:
													g.drawImage(img_hero_east, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
												case 3:
													g.drawImage(img_hero_south, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
													break;
											}
											break;
										case 1:
											g.drawImage(img_hero_east, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
											hero_current_pos = 2;
											break;
									}
									break;
								case 1:
									g.drawImage(img_hero_south, window.getLevel().getHero().getX() * x_size, window.getLevel().getHero().getY() * y_size, x_size, y_size, null);
									hero_current_pos = 3;
									break;
							}
						}
					}
					hero_last_pos[0] = window.getLevel().getHero().getX();
					hero_last_pos[1] = window.getLevel().getHero().getY();
					
					for (int i = 0; i < window.getLevel().getGuards().length; ++i)
					{
						if (window.getLevel().getGuards()[i].checkSleep())
						{
							switch (window.getLevel().getGuards()[i].getY() - guards_last_pos[i][1])
							{							
								case -1:
									g.drawImage(img_guard_asleep_north, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
									guards_current_pos[i] = 0;
									break;
								case 0:
									switch (window.getLevel().getGuards()[i].getX() - guards_last_pos[i][0])
									{
										case -1:
											g.drawImage(img_guard_asleep_west, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
											guards_current_pos[i] = 1;
											break;
										case 0:
											switch (guards_current_pos[i])
											{
												case 0:
													g.drawImage(img_guard_asleep_north, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
													break;
												case 1:
													g.drawImage(img_guard_asleep_west, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
													break;
												case 2:
													g.drawImage(img_guard_asleep_east, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
													break;
												case 3:
													g.drawImage(img_guard_asleep_south, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
													break;
											}
											break;
										case 1:
											g.drawImage(img_guard_asleep_east, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
											guards_current_pos[i] = 2;
											break;
									}
									break;
								case 1:
									g.drawImage(img_guard_asleep_south, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
									guards_current_pos[i] = 3;
									break;
							}
						}
						else
						{
							switch (window.getLevel().getGuards()[i].getY() - guards_last_pos[i][1])
							{							
								case -1:
									g.drawImage(img_guard_north, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
									guards_current_pos[i] = 0;
									break;
								case 0:
									switch (window.getLevel().getGuards()[i].getX() - guards_last_pos[i][0])
									{
										case -1:
											g.drawImage(img_guard_west, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
											guards_current_pos[i] = 1;
											break;
										case 0:
											switch (guards_current_pos[i])
											{
												case 0:
													g.drawImage(img_guard_north, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
													break;
												case 1:
													g.drawImage(img_guard_west, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
													break;
												case 2:
													g.drawImage(img_guard_east, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
													break;
												case 3:
													g.drawImage(img_guard_south, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
													break;
											}
											break;
										case 1:
											g.drawImage(img_guard_east, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
											guards_current_pos[i] = 2;
											break;
									}
									break;
								case 1:
									g.drawImage(img_guard_south, window.getLevel().getGuards()[i].getX() * x_size, window.getLevel().getGuards()[i].getY() * y_size, x_size, y_size, null);
									guards_current_pos[i] = 3;
									break;
							}
						}
						guards_last_pos[i][0] = window.getLevel().getGuards()[i].getX();
						guards_last_pos[i][1] = window.getLevel().getGuards()[i].getY();
					}
					
					
					for (int i = 0; i < window.getLevel().getOgres().length; ++i)
					{
						if (window.getLevel().getOgres()[i].checkStun())
						{
							switch (window.getLevel().getOgres()[i].getClubY() - window.getLevel().getOgres()[i].getOgreY())
							{
								case -1:
									g.drawImage(img_club_north, window.getLevel().getOgres()[i].getClubX() * x_size, window.getLevel().getOgres()[i].getClubY() * y_size, x_size, y_size, null);
									g.drawImage(img_ogre_stunned_north, window.getLevel().getOgres()[i].getOgreX() * x_size, window.getLevel().getOgres()[i].getOgreY() * y_size, x_size, y_size, null);
									break;
								case 0:
									switch (window.getLevel().getOgres()[i].getClubX() - window.getLevel().getOgres()[i].getOgreX())
									{
										case -1:
											g.drawImage(img_club_west, window.getLevel().getOgres()[i].getClubX() * x_size, window.getLevel().getOgres()[i].getClubY() * y_size, x_size, y_size, null);
											g.drawImage(img_ogre_stunned_west, window.getLevel().getOgres()[i].getOgreX() * x_size, window.getLevel().getOgres()[i].getOgreY() * y_size, x_size, y_size, null);
											break;
										case 1:
											g.drawImage(img_club_east, window.getLevel().getOgres()[i].getClubX() * x_size, window.getLevel().getOgres()[i].getClubY() * y_size, x_size, y_size, null);
											g.drawImage(img_ogre_stunned_east, window.getLevel().getOgres()[i].getOgreX() * x_size, window.getLevel().getOgres()[i].getOgreY() * y_size, x_size, y_size, null);
											break;
									}
									break;
								case 1:
									g.drawImage(img_club_south, window.getLevel().getOgres()[i].getClubX() * x_size, window.getLevel().getOgres()[i].getClubY() * y_size, x_size, y_size, null);
									g.drawImage(img_ogre_stunned_south, window.getLevel().getOgres()[i].getOgreX() * x_size, window.getLevel().getOgres()[i].getOgreY() * y_size, x_size, y_size, null);
									break;
							}
						}
						else
						{
							switch (window.getLevel().getOgres()[i].getClubY() - window.getLevel().getOgres()[i].getOgreY())
							{
								case -1:
									g.drawImage(img_club_north, window.getLevel().getOgres()[i].getClubX() * x_size, window.getLevel().getOgres()[i].getClubY() * y_size, x_size, y_size, null);
									g.drawImage(img_ogre_north, window.getLevel().getOgres()[i].getOgreX() * x_size, window.getLevel().getOgres()[i].getOgreY() * y_size, x_size, y_size, null);
									break;
								case 0:
									switch (window.getLevel().getOgres()[i].getClubX() - window.getLevel().getOgres()[i].getOgreX())
									{
										case -1:
											g.drawImage(img_club_west, window.getLevel().getOgres()[i].getClubX() * x_size, window.getLevel().getOgres()[i].getClubY() * y_size, x_size, y_size, null);
											g.drawImage(img_ogre_west, window.getLevel().getOgres()[i].getOgreX() * x_size, window.getLevel().getOgres()[i].getOgreY() * y_size, x_size, y_size, null);
											break;
										case 1:
											g.drawImage(img_club_east, window.getLevel().getOgres()[i].getClubX() * x_size, window.getLevel().getOgres()[i].getClubY() * y_size, x_size, y_size, null);
											g.drawImage(img_ogre_east, window.getLevel().getOgres()[i].getOgreX() * x_size, window.getLevel().getOgres()[i].getOgreY() * y_size, x_size, y_size, null);
											break;
									}
									break;
								case 1:
									g.drawImage(img_club_south, window.getLevel().getOgres()[i].getClubX() * x_size, window.getLevel().getOgres()[i].getClubY() * y_size, x_size, y_size, null);
									g.drawImage(img_ogre_south, window.getLevel().getOgres()[i].getOgreX() * x_size, window.getLevel().getOgres()[i].getOgreY() * y_size, x_size, y_size, null);
									break;
							}
						}
					}
					
					break;
			}
		}
		else if (draw_id != ' ')
		{
			switch (draw_id)
			{
				case 'H':
					g.drawImage(img_hero_south, 0, 0, this.getWidth(), this.getHeight(), null);
					break;
				case 'A':
					g.drawImage(img_hero_armed_south, 0, 0, this.getWidth(), this.getHeight(), null);
					break;
				case 'G':
					g.drawImage(img_guard_south, 0, 0, this.getWidth(), this.getHeight(), null);
					break;
				case 'O':
					g.drawImage(img_ogre_south, 0, 0, this.getWidth(), this.getHeight(), null);
					break;
				case '*':
					g.drawImage(img_club_south, 0, 0, this.getWidth(), this.getHeight(), null);
					break;
				case 'X':
					g.drawImage(img_wall, 0, 0, this.getWidth(), this.getHeight(), null);
					break;
				case 'I':
					g.drawImage(img_door_closed, 0, 0, this.getWidth(), this.getHeight(), null);
					break;
				case 'k':
					g.drawImage(img_key, 0, 0, this.getWidth(), this.getHeight(), null);
					break;
				case 'l':
					g.drawImage(img_lever, 0, 0, this.getWidth(), this.getHeight(), null);
					break;
				case '?':
					g.drawImage(img_delete_icon, 0, 0, this.getWidth(), this.getHeight(), null);
					break;					
			}
		}
		else if (draw_id_array.length > 0 && draw_id_array[0].length > 0)
		{
			int x_size = this.getWidth() / draw_id_array[0].length;
			int y_size = this.getHeight() / draw_id_array.length;
			
			for (int y = 0; y < draw_id_array.length; ++y)
			{
				for (int x = 0; x < draw_id_array[0].length; ++x)
				{
					switch (draw_id_array[y][x])
					{
						case 'H':
							g.drawImage(img_hero_south, x * x_size, y * y_size, x_size, y_size, null);
							break;
						case 'A':
							g.drawImage(img_hero_armed_south, x * x_size, y * y_size, x_size, y_size, null);
							break;
						case 'G':
							g.drawImage(img_guard_south, x * x_size, y * y_size, x_size, y_size, null);
							break;
						case 'O':
							g.drawImage(img_ogre_south, x * x_size, y * y_size, x_size, y_size, null);
							break;
						case '*':
							g.drawImage(img_club_south, x * x_size, y * y_size, x_size, y_size, null);
							break;
						case 'X':
							g.drawImage(img_wall, x * x_size, y * y_size, x_size, y_size, null);
							break;
						case 'I':
							g.drawImage(img_door_closed, x * x_size, y * y_size, x_size, y_size, null);
							break;
						case 'k':
							g.drawImage(img_key, x * x_size, y * y_size, x_size, y_size, null);
							break;
						case 'l': // Will temporarily replace lever
							g.drawImage(img_lever, x * x_size, y * y_size, x_size, y_size, null);
							break;
					}
				}
			}
		}
	}
	
	char getDraw() {
		return draw_id;
	}
	
	void setDraw(char draw_id) {
		this.draw_id = draw_id;
	}
	
	void setDraw(char[][] draw_id_array) {
		this.draw_id_array = draw_id_array;
	}
	
	void reset()
	{
		hero_last_pos = new int[]{};
		hero_current_pos = 3;
		guards_last_pos = new int[][]{};
		guards_current_pos = new int[]{};
	}
}
