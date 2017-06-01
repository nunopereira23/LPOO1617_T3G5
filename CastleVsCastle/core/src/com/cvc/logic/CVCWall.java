package com.cvc.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class CVCWall extends CVCFortification {
	public static final float BRICK_WIDTH = 2.0f;
	public static final float BRICK_EDGE_WIDTH = 1.0f;
	public static final float BRICK_HEIGHT = 1.0f;

	private int width_;
    private int height_;

    private PolygonShape shape_;
    private PolygonShape shape_edge_;
    private FixtureDef fixture_;
    private FixtureDef fixture_edge_;
    private BodyDef bodydef_;

    public CVCWall(World world, int posX, int posY, int width, int height) {
        super(world);
        width_ = width;
        height_ = height;

	    int originX = posX;
	    posY = posY + height_ - 1;
	    int halfwidth_ = width_ / 2;

        bodydef_ = new BodyDef();
        bodydef_.type = BodyDef.BodyType.DynamicBody;

        shape_ = new PolygonShape();
        shape_.setAsBox(BRICK_WIDTH / 2, BRICK_HEIGHT / 2);
        shape_edge_ = new PolygonShape();
        shape_edge_.setAsBox(BRICK_EDGE_WIDTH / 2, BRICK_HEIGHT / 2);

        fixture_ = new FixtureDef();
        fixture_.shape = shape_;
        fixture_.density = 2560.0f; // Limestone kg/m^3
        fixture_.friction = 0.75f; // Limestone on limestone
        fixture_.restitution = 0.48f; // Limestone
        fixture_edge_ = new FixtureDef();
        fixture_edge_.shape = shape_edge_;
        fixture_edge_.density = 2560.0f; // Limestone kg/m^3
        fixture_edge_.friction = 0.75f; // Limestone on limestone
        fixture_edge_.restitution = 0.48f; // Limestone

	    edges_ = new boolean[width_ + 1];
        if (width_ % 2 == 0) {
	        bodies_ = new Body[halfwidth_ * height_ + (height_ / 2)];
	        int n = 0;
	        for (; n < halfwidth_; ++n)
	        	edges_[n] = false;
	        edges_[n] = true;
	        for (++n; n < width_; ++n)
	        	edges_[n] = false;
	        edges_[n] = true;
        }
        else {
	        bodies_ = new Body[halfwidth_ * height_ + height_];
	        int n = 0;
	        for (; n < halfwidth_; ++n)
		        edges_[n] = false;
	        edges_[n] = true; ++n;
	        edges_[n] = true;
	        for (++n; n < width_; ++n)
		        edges_[n] = false;
        }

        for (int n = 0, n1 = 0; n < bodies_.length; ++n)
        {
	        bodydef_.position.set(posX, posY);
		    bodies_[n] = world_.createBody(bodydef_);
	        bodies_[n].createFixture(edges_[n1] ? fixture_edge_ : fixture_);

	        if (!edges_[n1]) { ++posX; } ++posX;
	        ++n1; if (n1 == edges_.length) { n1 = 0; }
	        if (posX - originX == width_) { posX = originX; --posY; }
        }

        // Testing
        bodydef_.position.set(originX + halfwidth_, 128f);
	    Body body = world_.createBody(bodydef_);
	    fixture_edge_.density *= 1000f;
	    body.createFixture(fixture_edge_);
    }
}
