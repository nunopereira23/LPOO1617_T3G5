package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.World;

public class CVCBallista extends CVCWeapon {

    public CVCBallista(World world){
        super(world);
    }

    public void fireWeapon(float x, float y, boolean enemy)
    {
        // to do
    }

    public void loadWeapon(boolean enemy)
    {
        // to do
    }

    /** Get the subtype of the weapon
     *
     * @return WeaponType the subtype of the weapon
     */
    public WeaponType getSubType()
    {
        return WeaponType.Ballista;
    }
}
