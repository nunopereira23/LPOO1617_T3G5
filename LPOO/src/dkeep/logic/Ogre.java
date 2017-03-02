package dkeep.logic;

class Ogre
{
	private int ogre_id_;
	private int ogre_x_, ogre_y_;
	private int club_x_, club_y_;
/*	{
		ogre_x_ = ogre_x;
		ogre_y_ = ogre_y;
		club_x_ = ogre_x;
		club_y_ = ogre_y;
		switch (club_pos)
		{
		case 0:
			--club_y_;
			break;
		case 1:
			++club_x_;
			break;
		case 2:
			++club_y_;
			break;
		case 3:
			--club_x_;
			break;
		}
	}*/
	public void init(int level_id)
	{
		ogre_id_ = level_id;
	}
	public static int get_amount(int level_id)
	{
		switch (level_id)
		{
		case 1:
			return 0;
		case 2:
			return 1;
		}
		return -1;
	}
}
