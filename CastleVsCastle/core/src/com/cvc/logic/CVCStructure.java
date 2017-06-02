package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.World;

public class CVCStructure {
    protected World world_; // This may be removed

	public enum StructureType {Fortification, Weapon};

    public CVCStructure(World world) {
        world_ = world;
    }

    public StructureType getType() {
	    return null;
    };
}
