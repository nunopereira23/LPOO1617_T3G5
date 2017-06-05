package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.World;

public class CVCStructure {
	protected int health_ = 100;
    protected World world_;

	public enum StructureType {Fortification, Weapon}

    public CVCStructure(World world) {
        world_ = world;
    }

    public StructureType getType() {
	    return null;
    }
}
