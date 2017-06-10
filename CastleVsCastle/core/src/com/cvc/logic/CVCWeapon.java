package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class CVCWeapon extends CVCStructure {
	public enum WeaponType {Trebuchet, Ballista, Catapult}

    protected float posX_;
    protected float posY_;
    protected CVCProjectile ammo_ = null;

    /** Creates the weapon
     *
     * @param world
     */
    public CVCWeapon(World world) {
        super(world);
    }

    /** Loads the weapon
     *
     * @param enemy whether the weapon being loaded belongs to the enemy castle or not
     */
    public abstract void loadWeapon(boolean enemy); // Will be called asynchronously

	/** Get the body of the ammunition
	 *
	 */
	public Body getAmmo()
	{
		return ammo_.getBody();
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
    public abstract WeaponType getSubType();
}
