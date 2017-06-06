package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.World;

public class CVCStructure {
	protected int health_ = 100;
    protected World world_;

	public enum StructureType {Fortification, Weapon}

    /** Creates one CVCStructure for the desired world
     *
     * @param world
     */
    public CVCStructure(World world) {
        world_ = world;
    }

    /** Returns the type of the structure
     *
     * @return StructureType the type of the structure
     */
    public StructureType getType() {
	    return null;
    }
}
