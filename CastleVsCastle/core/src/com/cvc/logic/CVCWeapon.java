package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class CVCWeapon extends CVCStructure {
    protected int health_ = 100;

    protected Body[] bodies_; // Wood

    public CVCWeapon(World world) {
        super(world);
    }

    public Body[] getBodies(){
        return bodies_;
    }

    public StructureType getType() {
        return StructureType.Weapon;
    };
}
