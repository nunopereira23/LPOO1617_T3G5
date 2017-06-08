package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class CVCProjectile {
	public enum ProjectileType {Rock, Dart}

	private Body body_;
	private ProjectileType type_;

	public CVCProjectile(World world, ProjectileType type, boolean enemy){
		type_ = type;

		CircleShape rock_shape = new CircleShape();
		PolygonShape dart_shape_head = new PolygonShape();
		PolygonShape dart_shape_body = new PolygonShape();

		FixtureDef fixture = new FixtureDef();
		switch (type_)
		{
			case Rock:
				rock_shape.setRadius(1.0f);

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

				fixture.shape = dart_shape_body;
				fixture.density = 2560.0f; // Limestone kg/m^3
				fixture.friction = 0.75f; // Limestone on limestone
				fixture.restitution = 0.20f; // Limestone
				break;
		}
		fixture.density = 2560.0f; // Limestone kg/m^3
		fixture.friction = 0.75f; // Limestone on limestone
		fixture.restitution = 0.20f; // Limestone
	}
}
