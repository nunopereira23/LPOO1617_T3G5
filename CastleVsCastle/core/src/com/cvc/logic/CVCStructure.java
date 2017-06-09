package com.cvc.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class CVCStructure {
	protected float health_ = 100f;
    protected World world_;

	protected Body[] bodies_;
	protected float[] dying_bodies_;
	protected Vector2[] bodies_centers_;


	public enum StructureType {Fortification, Weapon}

    /** Creates one CVCStructure for the desired world
     *
     * @param world
     */
    public CVCStructure(World world) {
	    world_ = world;
    }

	/** Updates the world's structures' bodies
	 *
	 */
    public void update(float delta)
    {
	    for (int n = 0; n < bodies_.length; ++n)
	    {
		    if (dying_bodies_[n] == 0) {
			    if (bodies_[n].getWorldCenter().dst2(bodies_centers_[n]) > 16) {
				    dying_bodies_[n] += delta;
				    health_ -= (100.0f / bodies_.length);
			    }
		    }
		    else if (dying_bodies_[n] < 3) {
			    dying_bodies_[n] += delta;
		    }
		    else if (bodies_[n].isActive()){
			    bodies_[n].setActive(false);
		    }
	    }
    }

    /** Returns the type of the structure
     *
     * @return StructureType the type of the structure
     */
    public StructureType getType() {
	    return null;
    }

	/** Get the bodies of the structure
	 *
	 * @return Body[] the bodies of the structure
	 */
	public Body[] getBodies(){
		return bodies_;
	}

	/** Get how long have the bodies of the structure been "dead"
	 *
	 * @return float[] duration of bodies' death
	 */
	public float[] getDyingBodies(){
		return dying_bodies_;
	}
}
