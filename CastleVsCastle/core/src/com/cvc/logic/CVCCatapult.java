package com.cvc.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class CVCCatapult extends CVCWeapon {

    public CVCCatapult(World world, float posX, float posY) {
        super(world);

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;

	    PolygonShape shape = new PolygonShape();
	    shape.setAsBox(2f, 0.5f);
        PolygonShape shape_left = new PolygonShape();
	    float[] left = new float[] {0f, 0f, // Save offsets in class
			                        1f, 0f,
			                        2f, 2f,
	                                2f, 3f};
        shape_left.set(left);
	    PolygonShape shape_right = new PolygonShape();
	    float[] right = new float[] {1f, 0f, // Save offsets
								     2f, 0f,
								     0f, 3f,
								     0f, 2f};
	    shape_right.set(right);

        FixtureDef fixture = new FixtureDef();
	    fixture.shape = shape;
        fixture.density = 710.0f; // Ashwood kg/m^3
        fixture.friction = 0.375f; // Wood on Wood
        fixture.restitution = 0.44f; // Ashwood
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

	    bodies_ = new Body[3];
	    bodydef.position.set(posX + 2, posY);
	    bodies_[0] = world_.createBody(bodydef);
	    bodies_[0].createFixture(fixture);
	    bodydef.position.set(posX, posY+1);
	    bodies_[1] = world_.createBody(bodydef);
	    bodies_[1].createFixture(fixture_left);
	    bodydef.position.set(posX+2, posY+1);
	    bodies_[2] = world_.createBody(bodydef);
	    bodies_[2].createFixture(fixture_right);

    }

	public WeaponType getSubType() { return WeaponType.Catapult; };
}
