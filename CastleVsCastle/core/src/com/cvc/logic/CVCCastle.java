package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.World;



public class CVCCastle {
    private final World world_;

    private int health_ = 100;
//    private long courtyard_size_;

    private CVCStructure[] structures_;
    private CVCDefender[] defenders_;
    private CVCResource[] resources_;

    /**
     * Constructs one Castle in the world given
     *
     * @param world the world where the castle will be
     */
    public CVCCastle(World world) { // automatically generate necessary structures
        world_ = world;

        structures_ = new CVCStructure[]{new CVCWall(world_, 8, 8, 4), new CVCTower(world_, 16, 8), new CVCCatapult(world_, 18, 11, false)};
    }

    public void update(float delta) {
        for (CVCStructure struct : structures_)
            struct.update(delta);
    }

    /** Returns all the structures of the castle
     *
     * @return CVCStructure[] all the structures of the castle
     */
	public CVCStructure[] getStructures() {
		return structures_;
	}
}
