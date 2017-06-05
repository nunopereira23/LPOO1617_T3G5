package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.World;

public class CVCCastle {
    private final World world_;

    private int health_ = 100;
//    private long courtyard_size_;

    private CVCStructure[] structures_;
    private CVCDefender[] defenders_;
    private CVCResource[] resources_;

    public CVCCastle(World world) { // automatically generate necessary structures
        world_ = world;

        structures_ = new CVCStructure[]{/*new CVCWall(world_, 8, 8, 4), new CVCTower(world_, 16, 8),*/ new CVCCatapult(world_, 18, 1)};
    }

    public void update() {

    }

	public CVCStructure[] getStructures() {
		return structures_;
	}
}
