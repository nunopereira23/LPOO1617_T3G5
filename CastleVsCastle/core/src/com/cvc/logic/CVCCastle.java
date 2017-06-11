package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.World;
import com.cvc.game.CVCGame;

import java.util.Arrays;

public class CVCCastle {
    private final World world_;

    private float health_ = 100;
	private int[][] touch_index_ = new int [20][50];

    private CVCStructure[] structures_;
    private CVCDefender[] defenders_;
    private CVCResource[] resources_;

    /**
     * Constructs one Castle in the world given
     *
     * @param world the world where the castle will be
     */
    public CVCCastle(World world, boolean enemy) { // automatically generate necessary structures
        world_ = world;

	    for (int[] i: touch_index_)
	        Arrays.fill(i, -1);

	    structures_ = new CVCStructure[]{new CVCTower(world_, 13 + (!enemy ? 0 : 100), 6),
									     new CVCWall(world_, 19 + (!enemy ? 0 : 100), 12, 4),
									     new CVCTower(world_, 31 + (!enemy ? 0 : 100), 6)};

	    // index 0
	    for (int j = 1; j < 7; ++j)
	    {
		    for (int i = 13; i < 19; ++i)
		    {
			    touch_index_[j][i] = 0;
		    }
	    }
	    for (int j = 7; j < 9; ++j)
	    {
		    for (int i = 12; i < 20; ++i)
		    {
			    touch_index_[j][i] = 0;
		    }
	    }
	    touch_index_[9][12] = 0;
	    touch_index_[9][19] = 0;

	    // index 1
	    for (int j = 1; j < 5; ++j)
	    {
		    for (int i = 19; i < 31; ++i)
		    {
			    touch_index_[j][i] = 1;
		    }
	    }

	    // index 2
	    for (int j = 1; j < 7; ++j)
	    {
		    for (int i = 31; i < 37; ++i)
		    {
			    touch_index_[j][i] = 2;
		    }
	    }
	    for (int j = 7; j < 9; ++j)
	    {
		    for (int i = 30; i < 38; ++i)
		    {
			    touch_index_[j][i] = 2;
		    }
	    }
	    touch_index_[9][30] = 2;
	    touch_index_[9][37] = 2;

	    for (int j = 19; j >= 0; --j) {
		    String s = "";
		    for (int i = 0; i < 50; ++i) {
			    s = s.concat((touch_index_[j][i] < 0 ? "" : " ")+touch_index_[j][i]);
		    }
		    CVCUtils.debugOut(s);
	    }

    }

    /** Updates the castle's structures
     *
     * @param delta The time in seconds since the last update
     */
    public void update(float delta) {
	    float health = 0;
        for (CVCStructure struct : structures_) {
	        struct.update(delta);
	        health += struct.getHealth();
        }
        health_ = health / structures_.length;
    }

    /** Returns all the structures of the castle
     *
     * @return CVCStructure[] all the structures of the castle
     */
	public CVCStructure[] getStructures() {
		return structures_;
	}

	public void getContextMenu(int x, int y) {
		if (y < 20 && x < 50 && touch_index_[y][x] != -1) {
			switch(structures_[touch_index_[y][x]].getType())
			{
				case Fortification:
					switch (((CVCFortification) structures_[touch_index_[y][x]]).getSubType())
					{
						case Tower:
							CVCGame.game_screen.openMenu(); // Will take parameters
							break;
						case Wall:
							CVCGame.game_screen.openMenu();
							break;
					}
					break;
				case Weapon:
					switch (((CVCWeapon) structures_[touch_index_[y][x]]).getSubType())
					{
						case Catapult:

							break;
						case Trebuchet:

							break;
						case Ballista:

							break;
					}
					break;
			}
		}
		else {
			CVCGame.game_screen.closeMenu();
		}
	}
}
