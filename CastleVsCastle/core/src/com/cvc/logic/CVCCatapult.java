package com.cvc.logic;

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
    //    shape.setAsBox(); // to do

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.density = 710.0f; // Ashwood kg/m^3
        fixture.friction = 0.375f; // Wood on Wood
        fixture.restitution = 0.44f; // Ashwood
    }

}
