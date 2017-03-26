package dkeep.logic;

import java.util.Random;

public class Ogre
{
	private int level_id_;
	private static int[][][] ogre_pos_level_ = {{}, {{4, 1}}};
	private static int[][][] club_pos_level_ = {{}, {{4, 2}}};

	private int ogre_x_, ogre_y_;
	private int new_ogre_x_, new_ogre_y_;
	private int club_x_, club_y_;
	private int new_club_x_, new_club_y_;
	
	private int ogre_stunned_ = 0;
	
	private static Random rng_ = new Random();

	public Ogre(int level_id, int ogre_index)
	{
		level_id_ = level_id;
		new_ogre_x_ = ogre_x_ = ogre_pos_level_[level_id_][ogre_index][0];
		new_ogre_y_ = ogre_y_ = ogre_pos_level_[level_id_][ogre_index][1];
		new_club_x_ = club_x_ = club_pos_level_[level_id_][ogre_index][0];
		new_club_y_ = club_y_ = club_pos_level_[level_id_][ogre_index][1];
	}
	
	public static int getN(int level_id)
	{
		return ogre_pos_level_[level_id].length;
	}	
	
	void updateOgre()
	{
		if (ogre_stunned_ != 0)
		{
			--ogre_stunned_;
		}
		else
		{
			switch (rng_.nextInt(4))
			{
				case 0:
					--new_ogre_y_;
					break;
				case 1:
					--new_ogre_x_;
					break;
				case 2:
					++new_ogre_y_;
					break;
				case 3:
					++new_ogre_x_;
					break;
			}
		}
	}
	void updateClub()
	{
		new_club_x_ = new_ogre_x_;
		new_club_y_ = new_ogre_y_;
		switch (rng_.nextInt(4))
		{
			case 0:
				--new_club_y_;
				break;
			case 1:
				--new_club_x_;
				break;
			case 2:
				++new_club_y_;
				break;
			case 3:
				++new_club_x_;
				break;
		}
	}
	
	public int getOgreX()
	{
		return ogre_x_;
	}
	public int getOgreY()
	{
		return ogre_y_;
	}
	public int getNewOgreX()
	{
		return new_ogre_x_;
	}
	public int getNewOgreY()
	{
		return new_ogre_y_;
	}
	public int getClubX()
	{
		return club_x_;
	}
	public int getClubY()
	{
		return club_y_;
	}
	public int getNewClubX()
	{
		return new_club_x_;
	}
	public int getNewClubY()
	{
		return new_club_y_;
	}
	
	void setOgreCoord()
	{
		ogre_x_ = new_ogre_x_;
		ogre_y_ = new_ogre_y_;
	}
	void setClubCoord()
	{
		club_x_ = new_club_x_;
		club_y_ = new_club_y_;
	}
	void resetOgreCoord()
	{
		new_ogre_x_ = ogre_x_;
		new_ogre_y_ = ogre_y_;
	}
	void resetClubCoord()
	{
		new_club_x_ = club_x_;
		new_club_y_ = club_y_;
	}
	
	void putStun()
	{
		ogre_stunned_ = 2;
	}

	public boolean checkStun()
	{
		return ogre_stunned_ != 0;
	}
}
