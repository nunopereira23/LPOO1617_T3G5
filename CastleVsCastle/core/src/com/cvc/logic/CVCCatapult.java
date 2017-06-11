package com.cvc.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Timer;
import com.cvc.game.CVCGame;

import java.util.Arrays;

public class CVCCatapult extends CVCWeapon {
	private boolean fire = true;

	/** Creates the catapult
	 *
	 * @param world the world where the catapult is created
	 * @param posX Position of the catapult in the x axis
	 * @param posY Position of the catapult in the y axis
	 */
    public CVCCatapult(World world, float posX, float posY, boolean enemy) {
        super(world);

	    posX_ = posX;
	    posY_ = posY;

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;

	    PolygonShape shape_center = new PolygonShape();
	    shape_center.set(new float[] {0f, 0f,
			                          3f, 0f,
			                          3f, 0.5f,
			                          0f, 0.5f});
        PolygonShape shape_left = new PolygonShape();
	    shape_left.set(new float[] {0f, 0.5f,
			                        0.5f, 0.5f,
			                        1f, 1.5f,
	                                1f, 2.5f});
	    PolygonShape shape_right = new PolygonShape();
	    shape_right.set(new float[] {0.5f, 0.5f,
								     1f, 0.5f,
								     0f, 2.5f,
								     0f, 1.5f});
	    PolygonShape shape_arm = new PolygonShape();
	    shape_arm.set(!enemy ? new float[] {0.25f, 1f,
			                                -1.75f, 2f,
			                                -1.5f, 2f,
			                                0.5f, 1.5f} :
							   new float[] {0.75f, 1f,
										    2.75f, 2f,
										    2.5f, 2f,
									        0.5f, 1.5f});

        FixtureDef fixture_center = new FixtureDef();
	    fixture_center.shape = shape_center;
        fixture_center.density = 710.0f; // Ashwood kg/m^3
        fixture_center.friction = 0.375f; // Wood on Wood
        fixture_center.restitution = 0.44f; // Ashwood
	    FixtureDef fixture_left = new FixtureDef();
	    fixture_left.shape = shape_left;
	    fixture_left.density = 710.0f; // Ashwood kg/m^3
	    fixture_left.friction = 0.375f; // Wood on Wood
	    fixture_left.restitution = 0.44f; // Ashwood
	    FixtureDef fixture_right = new FixtureDef();
	    fixture_right.shape = shape_right;
	    fixture_right.density = 710.0f; // Ashwood kg/m^3
	    fixture_right.friction = 0.375f; // Wood on Wood
	    fixture_right.restitution = 0.44f; // Ashwood
	    FixtureDef fixture_arm = new FixtureDef();
	    fixture_arm.shape = shape_arm;
	    fixture_arm.density = 710.0f; // Ashwood kg/m^3
	    fixture_arm.friction = 0.375f; // Wood on Wood
	    fixture_arm.restitution = 0.44f; // Ashwood

	    bodies_ = new Body[4];
	    bodydef.position.set(posX + (!enemy ? 0 : -1), posY);
	    bodies_[0] = world_.createBody(bodydef);
	    bodies_[0].createFixture(fixture_center);
	    bodydef.position.set(posX + (!enemy ? 1 : -1), posY);
	    bodies_[1] = world_.createBody(bodydef);
	    bodies_[1].createFixture(fixture_left);
	    bodydef.position.set(posX + (!enemy ? 2 : 0), posY);
	    bodies_[2] = world_.createBody(bodydef);
	    bodies_[2].createFixture(fixture_right);
	    bodydef.position.set(posX + (!enemy ? 1 : 0), posY);
	    bodies_[3] = world_.createBody(bodydef);
	    bodies_[3].createFixture(fixture_arm);

	    WeldJointDef weldjointdef_1 = new WeldJointDef();
	    weldjointdef_1.bodyA = bodies_[0];
	    weldjointdef_1.bodyB = bodies_[1];
	    weldjointdef_1.localAnchorA.set(bodies_[0].getLocalCenter());
	    weldjointdef_1.localAnchorB.set(bodies_[1].getLocalCenter());
	    world_.createJoint(weldjointdef_1);

	    WeldJointDef weldjointdef_2 = new WeldJointDef();
	    weldjointdef_2.bodyA = bodies_[0];
	    weldjointdef_2.bodyB = bodies_[2];
	    weldjointdef_2.localAnchorA.set(bodies_[0].getLocalCenter());
	    weldjointdef_2.localAnchorB.set(bodies_[2].getLocalCenter());
	    world_.createJoint(weldjointdef_2);

	    WeldJointDef weldjointdef_3 = new WeldJointDef();
	    weldjointdef_3.bodyA = bodies_[(!enemy ? 1 : 2)];
	    weldjointdef_3.bodyB = bodies_[3];
	    weldjointdef_3.localAnchorA.set(bodies_[(!enemy ? 1 : 2)].getLocalCenter());
	    weldjointdef_3.localAnchorB.set(bodies_[3].getLocalCenter());
	    world_.createJoint(weldjointdef_3);

	    bodies_centers_ = new Vector2[bodies_.length];
	    for (int n = 0; n < bodies_.length; ++n)
	        bodies_centers_[n] = new Vector2(bodies_[n].getWorldCenter());

	    dying_bodies_ = new float[bodies_.length];
	    Arrays.fill(dying_bodies_, 0);
    }

    public void fireWeapon(float x, float y, boolean enemy)
    {
	    if (fire = !fire) {
		    ammo_.getBody().applyLinearImpulse(
		    		new Vector2(CVCProjectile.projectileEquation(19.6f, x, posX_, y, posY_), 19.6f).scl(CVCProjectile.ROCK_DENSITY_BY_AREA),
				    ammo_.getBody().getWorldCenter(),
				    true);
		    Timer.schedule(new Timer.Task() {
			    @Override
			    public void run() {
				    CVCGame.world.setUpdate();
				    while (CVCGame.world.isUpdating());
				    world_.destroyBody(getAmmoBody());
				    CVCGame.world.setUpdate();
				    ammo_ = null;
			    }
		    }, 10);
	    }
	    else {
		    ammo_.setJoint(world_);
	    }
    }

    public void loadWeapon(boolean enemy)
    {
	    ammo_ = new CVCProjectile(world_, CVCProjectile.ProjectileType.Rock, posX_ + (!enemy ? -0.66f : 0.66f), posY_ + 2.5f, enemy);
	    WeldJointDef weldjointdef = new WeldJointDef();
	    weldjointdef.bodyA = ammo_.getBody();
	    weldjointdef.bodyB = bodies_[3];
	    weldjointdef.localAnchorA.set(ammo_.getBody().getPosition().x, ammo_.getBody().getPosition().y + 0.001f);
	    weldjointdef.localAnchorB.set(ammo_.getBody().getPosition().x, ammo_.getBody().getPosition().y - 0.001f);
	    ammo_.setJoint(world_.createJoint(weldjointdef));
    }

    /** Get the subtype of the weapon
     *
     * @return WeaponType the subtype of the weapon
     */
	public WeaponType getSubType() { return WeaponType.Catapult; }
}
