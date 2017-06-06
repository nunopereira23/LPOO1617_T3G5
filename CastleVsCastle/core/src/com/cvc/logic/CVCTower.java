package com.cvc.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.World;

public class CVCTower extends CVCFortification {
    private int height_;


    /** Creates the tower
     *
     * @param world the world where the tower is going to be constroyed
     * @param posX Position of the tower in the x axis
     * @param height Height of the tower
     */
    public CVCTower(World world, float posX, int height) { // Minimum 8 height
        super(world);
        height_ = height;

        float originX = posX;
        float posY = height_;

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(STONE_WIDTH / 2, STONE_HEIGHT / 2);
        PolygonShape shape_edge = new PolygonShape();
        shape_edge.setAsBox(STONE_EDGE_WIDTH / 2, STONE_HEIGHT / 2);
        PolygonShape shape_high_edge = new PolygonShape();
        shape_high_edge.setAsBox(STONE_EDGE_WIDTH / 2, STONE_EDGE_HEIGHT / 2);

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
        FixtureDef fixture_high_edge = new FixtureDef();
        fixture_high_edge.shape = shape_high_edge;
        fixture_high_edge.density = 1280.0f; // Limestone kg/m^3
        fixture_high_edge.friction = 0.75f; // Limestone on limestone
        fixture_high_edge.restitution = 0.20f; // Limestone

        bodies_ = new Body[3 * height_ + (height_ / 2) + 9];
        edges_ = new boolean[]
                {true, false, false, false, true, false, false, false, false, false, false, false, true, false, false, true};
        high_edges_ = 2;

        for (int n = 9, n1 = 9; n < bodies_.length; ++n) {
            bodydef.position.set(posX, posY);
            bodies_[n] = world_.createBody(bodydef);
            bodies_[n].createFixture(edges_[n1] ? fixture_edge : fixture);

            if (!edges_[n1]) { ++posX; } ++posX;
            ++n1; if (n1 == edges_.length) { n1 = 9; }
            if (posX - originX == 6) { posX = originX; --posY; }
        }

        --originX;
        posX = originX;
        posY = (height_ += 2);

        for (int n = 0; n < 9; ++n) {
            bodydef.position.set(posX, posY);
            bodies_[n] = world_.createBody(bodydef);
            if (n == 5 || n == 8) fixture.density = 1280.0f;
            bodies_[n].createFixture(edges_[n] ? fixture_high_edge : fixture);
            if (n == 5 || n == 8) fixture.density = 2560.0f;

            if (!edges_[n]) { ++posX; } ++posX;
            if (posX - originX == 8) { posX = originX; --posY; }
        }

        WeldJointDef weldjointdef_1 = new WeldJointDef();
        weldjointdef_1.initialize(bodies_[0], bodies_[5], new Vector2(0.5f, 0f));
        world_.createJoint(weldjointdef_1);

        WeldJointDef weldjointdef_2 = new WeldJointDef();
        weldjointdef_2.initialize(bodies_[4], bodies_[8], new Vector2(0.5f, 0f));
        world_.createJoint(weldjointdef_2);
    }

    /** Get height of the tower
     *
     * @return int tower height
     */
    public int getHeight_(){
        return height_;
    }
}
