package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class CVCProjectile {
	public enum ProjectileType {Rock, Dart}

	private Body body_;
	private Joint joint_ = null;

	public CVCProjectile(World world, ProjectileType type, float posX, float posY, boolean enemy){
		BodyDef bodydef = new BodyDef();
		bodydef.type = BodyDef.BodyType.DynamicBody;
		bodydef.position.set(posX, posY);

		CircleShape rock_shape = new CircleShape();
		PolygonShape dart_shape_head = new PolygonShape();
		PolygonShape dart_shape_body = new PolygonShape();

		FixtureDef fixture = new FixtureDef();
		switch (type)
		{
			case Rock:
				rock_shape.setRadius(0.5f);

				fixture.shape = rock_shape;
				fixture.density = 2560.0f; // Limestone kg/m^3
				fixture.friction = 0.75f; // Limestone on limestone
				fixture.restitution = 0.20f; // Limestone
				break;
			case Dart:
				dart_shape_head.set(!enemy ? new float[]{0.0f, 0.0f,
														 0.5f, 0.25f,
														 0.0f, 0.5f} :
											 new float[]{0.5f, 0.0f,
													     0.5f, 0.5f,
														 0.0f, 0.25f});
				dart_shape_body.set(new float[]{0.0f, 0.0f,
												2.0f, 0.0f,
												2.0f, 0.25f,
												0.0f, 0.0f});

				fixture.shape = dart_shape_head;
				fixture.density = 2560.0f; // Limestone kg/m^3
				fixture.friction = 0.75f; // Limestone on limestone
				fixture.restitution = 0.20f; // Limestone
				break;
		}

		body_ = world.createBody(bodydef);
		switch (type)
		{
			case Rock:
				body_.createFixture(fixture);
				break;
			case Dart:
				break;
		}
	}

	public Body getBody()
	{
		return body_;
	}

	public void setJoint(Joint joint) // loadWeapon uses this
	{
		joint_ = joint;
	}

	public void setJoint(World world) // fireWeapon uses this
	{
		world.destroyJoint(joint_);
		joint_ = null;
	}
}
