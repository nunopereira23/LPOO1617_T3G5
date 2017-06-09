package com.cvc.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import java.util.Arrays;

public class CVCWall extends CVCFortification {
	private int width_;
    private int height_;

    /** Creates wall
     *
     * @param world world where the wall is going to be created
     * @param posX Position of the wall in the x axis
     * @param width Width of the wall
     * @param height Height of the wall
     */
    public CVCWall(World world, float posX, int width, int height) { // Minimum 8 width
        super(world);
        width_ = width;
        height_ = height;

	    float originX = posX;
        float posY = height_;
	    int halfwidth = width_ / 2;

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(STONE_WIDTH / 2, STONE_HEIGHT / 2);
        PolygonShape shape_edge = new PolygonShape();
        shape_edge.setAsBox(STONE_EDGE_WIDTH / 2, STONE_HEIGHT / 2);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.density = 2560.0f; // Limestone kg/m^3
        fixture.friction = 0.75f; // Limestone on limestone
        fixture.restitution = 0.20f; // Limestone
        FixtureDef fixture_edge = new FixtureDef();
        fixture_edge.shape = shape_edge;
        fixture_edge.density = 2560.0f; // Limestone kg/m^3
        fixture_edge.friction = 0.75f; // Limestone on limestone
        fixture_edge.restitution = 0.20f; // Limestone

	    edges_ = new boolean[width_ + 1];
        if (width_ % 2 == 0) {
	        bodies_ = new Body[halfwidth * height_ + (height_ / 2)];
	        int n = 0;
	        for (; n < halfwidth; ++n)
	        	edges_[n] = false;
	        edges_[n] = true;
	        for (++n; n < width_; ++n)
	        	edges_[n] = false;
	        edges_[n] = true;
        }
        else {
	        bodies_ = new Body[halfwidth * height_ + height_];
	        int n = 0;
	        for (; n < halfwidth; ++n)
		        edges_[n] = false;
	        edges_[n] = true; ++n;
	        edges_[n] = true;
	        for (++n; n < width_; ++n)
		        edges_[n] = false;
        }
        high_edges_ = 0;

        for (int n = 0, n1 = 0; n < bodies_.length; ++n)
        {
	        bodydef.position.set(posX, posY);
		    bodies_[n] = world_.createBody(bodydef);
	        bodies_[n].createFixture(edges_[n1] ? fixture_edge : fixture);

	        if (!edges_[n1]) { ++posX; } ++posX;
	        ++n1; if (n1 == edges_.length) { n1 = 0; }
	        if (posX - originX == width_) { posX = originX; --posY; }
        }

	    bodies_centers_ = new Vector2[bodies_.length];
	    for (int n = 0; n < bodies_.length; ++n)
		    bodies_centers_[n] = new Vector2(bodies_[n].getWorldCenter());

	    dying_bodies_ = new float[bodies_.length];
	    Arrays.fill(dying_bodies_, 0);
    }
}
