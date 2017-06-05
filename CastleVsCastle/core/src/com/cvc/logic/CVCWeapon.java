package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class CVCWeapon extends CVCStructure {
    protected Body[] bodies_; // Wood

	public enum WeaponType {Trebuchet, Catapult}

    public CVCWeapon(World world) {
        super(world);
    }

    public Body[] getBodies(){
        return bodies_;
    }

    public StructureType getType() {
        return StructureType.Weapon;
    }

    public WeaponType getSubType() { return null; }
}
