package com.cvc.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class CVCStructure {
    protected final World world_;

	protected Body[] bodies_;
	protected float[] dying_bodies_;
	protected Vector2[] bodies_centers_;

	protected float health_ = 100f;
	private boolean build_ = false;

	public enum StructureType {Fortification, Weapon}

    /** Creates one CVCStructure for the desired world
     *
     * @param world
     */
    public CVCStructure(World world) {
	    world_ = world;
    }

	/** Updates the structure's bodies
	 *
	 * @param delta The time in seconds since the last update
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

	public void buildFortification() {
		for (Body body : bodies_)
			body.setActive(true);
		build_ = true;
	}

	public boolean isBuilt() {
		return build_;
	}

	public void destroyFortification() {
		for (Body body : bodies_)
			world_.destroyBody(body);
	}

    /** Returns the type of the structure
     *
     * @return the type of the structure
     */
    public abstract StructureType getType();

	/** Returns the total health of the structure
	 *
	 * @return the current total health
	 */
    public float getHealth() {
	    return health_;
    }

	/** Get the bodies of the structure
	 *
	 * @return the bodies of the structure
	 */
	public Body[] getBodies(){
		return bodies_;
	}

	/** Get how long ago the bodies of the structure started "dying"
	 *
	 * @return time since bodies started "dying" in seconds
	 */
	public float[] getDyingBodies(){
		return dying_bodies_;
	}
}
