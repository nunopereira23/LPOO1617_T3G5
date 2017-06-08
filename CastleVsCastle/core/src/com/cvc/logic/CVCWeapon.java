package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class CVCWeapon extends CVCStructure {
	public enum WeaponType {Trebuchet, Ballista, Catapult}

    /** Creates the weapon
     *
     * @param world
     */
    public CVCWeapon(World world) {
        super(world);
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
