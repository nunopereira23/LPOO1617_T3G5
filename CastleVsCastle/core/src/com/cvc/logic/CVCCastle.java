package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.World;
import com.cvc.game.CVCGame;
import com.cvc.game.CVCMenu;

import java.util.Arrays;

public class CVCCastle {
    private final World world_;

    private float health_ = 100;
	private int[][] touch_index_ = new int [20][50];

	private boolean isTargeting_ = false;
	private CVCWeapon weaponFiring_ = null;

    private CVCStructure[] structures_;
	private CVCDefender[] defenders_;
	private CVCWood resources_wood_ = new CVCWood();
	private CVCStone resources_stone_ = new CVCStone();
	private CVCIron resources_iron_ = new CVCIron();
	private CVCRope resources_rope_ = new CVCRope();

	private CVCFortification.FortificationType plannedFortification = null;

    /**
     * Constructs one Castle in the world given
     *
     * @param world the world where the castle will be
     */
    public CVCCastle(World world, boolean enemy) { // automatically generate necessary structures
        world_ = world;

	    for (int[] i: touch_index_)
	        Arrays.fill(i, -1);

	    structures_ = new CVCStructure[]{new CVCTower(world_, 15 + (!enemy ? 0 : 100), 8),
									     new CVCWall(world_, 21 + (!enemy ? 0 : 100), 8, 4),
									     new CVCTower(world_, 29 + (!enemy ? 0 : 100), 8)};

	    defenders_ = new CVCDefender[]{new CVCLumberjack(1),
			                           new CVCQuarryman(2),
			                           new CVCMiner(3),
			                           new CVCScavenger(4)};

	    // index 0
	    for (int j = 1; j < 9; ++j)
	    {
		    for (int i = 15; i < 21; ++i)
		    {
			    touch_index_[j][i] = 0;
		    }
	    }
	    for (int j = 9; j < 11; ++j)
	    {
		    for (int i = 14; i < 22; ++i)
		    {
			    touch_index_[j][i] = 0;
		    }
	    }
	    touch_index_[11][14] = 0;
	    touch_index_[11][21] = 0;

	    // index 1
	    for (int j = 1; j < 5; ++j)
	    {
		    for (int i = 21; i < 29; ++i)
		    {
			    touch_index_[j][i] = 1;
		    }
	    }

	    // index 2
	    for (int j = 1; j < 9; ++j)
	    {
		    for (int i = 29; i < 35; ++i)
		    {
			    touch_index_[j][i] = 2;
		    }
	    }
	    for (int j = 9; j < 11; ++j)
	    {
		    for (int i = 28; i < 36; ++i)
		    {
			    touch_index_[j][i] = 2;
		    }
	    }
	    touch_index_[11][28] = 2;
	    touch_index_[11][35] = 2;

	    for (CVCStructure struct : structures_)
	    	struct.buildFortification();

	 /*   for (int j = 19; j >= 0; --j) { // for testing
		    String s = "";
		    for (int i = 0; i < 50; ++i) {
			    s = s.concat((touch_index_[j][i] < 0 ? "" : " ")+touch_index_[j][i]);
		    }
		    CVCUtils.debugOut(s);
	    } */
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
        for (CVCDefender defender : defenders_) {
	        defender.update(delta);
        }
        health_ = health / structures_.length;
    }

	public void planFortification(CVCFortification.FortificationType fortificationType, float posX, int width, int height) {
		CVCStructure[] new_structures = new CVCStructure[structures_.length + 1];
		int n;
		for (n = 0; n < structures_.length; ++n)
			new_structures[n] = structures_[n];
		switch (fortificationType)
		{
			case Tower:
				plannedFortification = CVCFortification.FortificationType.Tower;
				new_structures[n] = new CVCTower(world_, posX, height);
				break;
			case Wall:
				plannedFortification = CVCFortification.FortificationType.Wall;
				new_structures[n] = new CVCWall(world_, posX, width, height);
				break;
		}
		structures_ = new_structures;
    }

	public void changePlannedFortification(float posX, int width, int height) {
		int n = structures_.length - 1;
		structures_[n].destroyFortification();
		switch (plannedFortification)
		{
			case Tower:
				structures_[n] = new CVCTower(world_, posX, height);
				break;
			case Wall:
				structures_[n] = new CVCWall(world_, posX, width, height);
				break;
		}
	}

    public void buildPlannedFortification(float posX, int width, int height) {
	    int n = structures_.length - 1;
	    switch (plannedFortification)
	    {
		    case Tower:
			    for (int j = 1; j < height + 1; ++j)
			    {
				    for (int i = (int) posX; i < (int) posX + 6; ++i)
				    {
					    touch_index_[j][i] = n;
				    }
			    }
			    for (int j = height + 1; j < height + 3; ++j)
			    {
				    for (int i = (int) posX - 1; i < (int) posX + 7; ++i)
				    {
					    touch_index_[j][i] = n;
				    }
			    }
			    touch_index_[height + 3][(int) posX - 1] = n;
			    touch_index_[height + 3][(int) posX + 6] = n;
			    break;
		    case Wall:
			    for (int j = 1; j < height + 1; ++j)
			    {
				    for (int i = (int) posX; i < (int) posX + width; ++i)
				    {
					    touch_index_[j][i] = n;
				    }
			    }
			    break;
	    }
	    structures_[structures_.length - 1].buildFortification();
		plannedFortification = null;

	/*	for (int j = 19; j >= 0; --j) { // for testing
		    String s = "";
		    for (int i = 0; i < 50; ++i) {
			    s = s.concat((touch_index_[j][i] < 0 ? "" : " ")+touch_index_[j][i]);
		    }
		    CVCUtils.debugOut(s);
	    } */
    }

	public void cancelPlannedFortification() {
		if (plannedFortification != null) {
			CVCStructure[] new_structures = new CVCStructure[structures_.length - 1];
			int i;
			for (i = 0; i < new_structures.length; ++i)
				new_structures[i] = structures_[i];
			structures_[i].destroyFortification();
			structures_ = new_structures;
			plannedFortification = null;
		}
	}

	public void buildWeapon(float posX, float posY, boolean enemy) {
		CVCStructure[] new_structures = new CVCStructure[structures_.length + 1];
		int n;
		for (n = 0; n < structures_.length; ++n)
			new_structures[n] = structures_[n];
		// For now only catapult
		new_structures[n] = new CVCCatapult(world_, posX, posY, enemy);
		structures_ = new_structures;
		for (int j = (int) posY; j < (int) posY + 3; ++j)
		{
			for (int i = (int) posX - 1; i < (int) posX + 2; ++i)
			{
				touch_index_[j][i] = n;
			}
		}
	}

	public void addDefender(CVCDefender defender) {
		CVCDefender[] new_defenders = new CVCDefender[defenders_.length + 1];
		int n;
		for (n = 0; n < defenders_.length; ++n)
			new_defenders[n] = defenders_[n];
		new_defenders[n] = defender;
		defenders_ = new_defenders;
	}

    /** Returns all the structures of the castle
     *
     * @return CVCStructure[] all the structures of the castle
     */
	public CVCStructure[] getStructures() {
		return structures_;
	}

	public CVCDefender[] getDefenders() {
		return defenders_;
	}

	public CVCWood getWood() {
		return resources_wood_;
	}

	public CVCStone getStone() {
		return resources_stone_;
	}

	public CVCIron getIron() {
		return resources_iron_;
	}

	public CVCRope getRope() {
		return resources_rope_;
	}

	public void getContextMenu(int x, int y) {
		if (y < 20 && x < 50) {
			if (touch_index_[y][x] != -1) {
				switch (structures_[touch_index_[y][x]].getType()) {
					case Fortification:
						switch (((CVCFortification) structures_[touch_index_[y][x]]).getSubType()) {
							case Tower:
								CVCGame.openMenu(CVCMenu.MenuType.Tower, structures_[touch_index_[y][x]]);
								break;
							case Wall:
								CVCGame.openMenu(CVCMenu.MenuType.Wall, structures_[touch_index_[y][x]]);
								break;
						}
						break;
					case Weapon:
						switch (((CVCWeapon) structures_[touch_index_[y][x]]).getSubType()) {
							case Catapult:
								CVCGame.openMenu(CVCMenu.MenuType.Catapult, structures_[touch_index_[y][x]]);
								break;
							case Trebuchet:
								CVCGame.openMenu(CVCMenu.MenuType.Trebuchet, structures_[touch_index_[y][x]]);
								break;
							case Ballista:
								CVCGame.openMenu(CVCMenu.MenuType.Ballista, structures_[touch_index_[y][x]]);
								break;
						}
						break;
				}
			}
			else if (touch_index_[1][x] == -1 && x > 3 && x < 47) {
				int found = 0, i = x, j = x, fi = x, fj = x;
				for (; i < 44 || j > 5;) {
					if (i < 44) {
						if (touch_index_[1][i] != -1) {
							++found;
							fi = i;
							i = 44;
						} else ++i;
					}
					if (j > 5) {
						if (touch_index_[1][j] != -1) {
							++found;
							fj = j;
							j = 5;
						} else --j;
					}
				}
				if (found == 2)
					if (fi - x > 4 && x - fj > 5) // need to double check these values
						CVCGame.openMenu(CVCMenu.MenuType.bAll, new int[]{x - 3, fj + 1, fi - fj - 1, Math.min(((CVCTower)structures_[touch_index_[1][fj]]).getHeight(),
																											   ((CVCTower)structures_[touch_index_[1][fi]]).getHeight()) - 2});
					else
						CVCGame.openMenu(CVCMenu.MenuType.bWall, new int[]{fj + 1, fi - fj - 1, Math.min(((CVCTower)structures_[touch_index_[1][fj]]).getHeight(),
																										 ((CVCTower)structures_[touch_index_[1][fi]]).getHeight()) - 2});
				else
					if (fi - x > 4 || x - fj > 5) // need to double check these values
						CVCGame.openMenu(CVCMenu.MenuType.bTower, new int[]{x - 3});
			}
		}
	}

	public void setTargeting() {
		isTargeting_ = !isTargeting_;
	}

	public boolean isTargeting() {
		return isTargeting_;
	}

	public void setFiring(CVCWeapon weapon) {
		weaponFiring_ = weapon;
	}

	public void isFiring(float x, float y) {
		weaponFiring_.fireWeapon(x, y, false);
		weaponFiring_.fireWeapon(x, y, false);
	}

	public void investWood() {
		resources_wood_.invest();
	}

	public void investStone() {
		resources_stone_.invest();
	}

	public void investIron() {
		resources_iron_.invest();
	}

	public void investRope() {
		resources_rope_.invest();
	}

	public void divestWood() {
		resources_wood_.divest();
	}

	public void divestStone() {
		resources_stone_.divest();
	}

	public void divestIron() {
		resources_iron_.divest();
	}

	public void divestRope() {
		resources_rope_.divest();
	}
}
