package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class CVCWall extends CVCStructure {
    private PolygonShape shape_;
    private FixtureDef fixture_;
    private BodyDef bodydef_;

    public CVCWall(World world) {
        super(world);

        bodydef_ = new BodyDef();
        bodydef_.position.set(5f, 10f);
        bodydef_.type = BodyDef.BodyType.DynamicBody;

        shape_ = new PolygonShape();
        shape_.setAsBox(2, 1);

        fixture_ = new FixtureDef();
        fixture_.shape = shape_;
        fixture_.density = 2560.0f; // Limestone kg/m^3
        fixture_.friction = 0.75f; // Limestone on limestone
        fixture_.restitution = 0.2f; // Limestone? Not sure

        body_ = world.createBody(bodydef_);
        body_.createFixture(fixture_);
    }
}
