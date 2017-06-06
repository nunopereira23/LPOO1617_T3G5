package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class CVCWeapon extends CVCStructure {
    protected Body[] bodies_; // Wood

	public enum WeaponType {Trebuchet, Catapult}

    /** Creates the weapon
     *
     * @param world
     */
    public CVCWeapon(World world) {
        super(world);
    }

    /** Get the bodies of the weapon
     *
     * @return Body[] weapon bodies
     */
    public Body[] getBodies(){
        return bodies_;
    }

    /** Get type of the structure
     *
     * @return StructureType structure type
     */
    public StructureType getType() {
        return StructureType.Weapon;
    }

    /** Get the subtype of the weapon
     *
     * @return WeaponType subtype of weapon
     */
    public WeaponType getSubType() { return null; }
}
