package dkeep.logic;

class Hero
{
	private int hero_id_;
	private int hero_x_, hero_y_;
	private int new_hero_x_, new_hero_y_;
	Hero(int level_id)
	{
		hero_id_ = level_id;
	}
	public void update(int new_hero_delta_x, int new_hero_delta_y)
	{
		new_hero_x_ += new_hero_delta_x;
		new_hero_y_ += new_hero_delta_y;
	}
	public int getX()
	{
		return hero_x_;
	}
	public int getY()
	{
		return hero_y_;
	}
	public int getNewX()
	{
		return new_hero_x_;
	}
	public int getNewY()
	{
		return new_hero_y_;
	}
	public void setX(int new_x)
	{
		hero_x_ = new_x;
	}
	public void setY(int new_y)
	{
		hero_y_ = new_y;
	}
	public void setNewX(int new_x)
	{
		new_hero_x_ = new_x;
	}
	public void setNewY(int new_y)
	{
		new_hero_y_ = new_y;
	}
}
