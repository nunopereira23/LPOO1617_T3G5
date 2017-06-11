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

	public static final float ROCK_RADIUS = 0.5f;
	public static final float ROCK_DENSITY_BY_AREA = 20802.5f; // 26500 * 0.785

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
				rock_shape.setRadius(ROCK_RADIUS);

				fixture.shape = rock_shape;
				fixture.density = 26500f; // Limestone * 10 kg/m^3
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
				// to do
				break;
		}
	}

	public static float projectileEquation(float vy, float x, float x0, float y, float y0)
	{
		float a = 4.9f * (float) Math.pow(x - x0, 2);
		float b = vy * (x - x0);
		float c = y - y0;
		if (c == 0) return 1.0f / (b / a);
		return 1.0f / (float) ((b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a));
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
		if (joint_ != null) {
			world.destroyJoint(joint_);
			joint_ = null;
		}
	}
}
