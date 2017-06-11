package com.cvc.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Arrays;

public class CVCTower extends CVCFortification {
	private float posX_;
    private int height_;

    private boolean weapon_ = false;
    /** Creates the tower
     *
     * @param world the world where the tower is going to be created
     * @param posX Position of the tower in the x axis
     * @param height Height of the tower
     */
    public CVCTower(World world, float posX, int height) { // Minimum 8 height
        super(world);
	    posX_ = posX;
        height_ = height;

        float originX = posX;
        float posY = height_;

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.active = false;

        PolygonShape shape = new PolygonShape();
        shape.set(new float[] {0.0f, 0.0f,
                               STONE_WIDTH, 0.0f,
                               STONE_WIDTH, STONE_HEIGHT,
                               0.0f, STONE_HEIGHT});
        PolygonShape shape_edge = new PolygonShape();
        shape_edge.set(new float[] {0.0f, 0.0f,
                                    STONE_EDGE_WIDTH, 0.0f,
                                    STONE_EDGE_WIDTH, STONE_HEIGHT,
                                    0.0f, STONE_HEIGHT});
        PolygonShape shape_high_edge = new PolygonShape();
        shape_high_edge.set(new float[] {0.0f, 0.0f,
                                         STONE_EDGE_WIDTH, 0.0f,
                                         STONE_EDGE_WIDTH, STONE_EDGE_HEIGHT,
                                         0.0f, STONE_EDGE_HEIGHT});

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
        posY = (height_ + 2);

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
        weldjointdef_1.bodyA = bodies_[0];
        weldjointdef_1.bodyB = bodies_[5];
        weldjointdef_1.localAnchorA.set(bodies_[0].getLocalCenter());
        weldjointdef_1.localAnchorB.set(bodies_[5].getLocalCenter());
        world_.createJoint(weldjointdef_1);

        WeldJointDef weldjointdef_2 = new WeldJointDef();
        weldjointdef_2.bodyA = bodies_[4];
        weldjointdef_2.bodyB = bodies_[8];
        weldjointdef_2.localAnchorA.set(bodies_[4].getLocalCenter());
        weldjointdef_2.localAnchorB.set(bodies_[8].getLocalCenter());
        world_.createJoint(weldjointdef_2);

	    bodies_centers_ = new Vector2[bodies_.length];
	    for (int n = 0; n < bodies_.length; ++n)
		    bodies_centers_[n] = new Vector2(bodies_[n].getWorldCenter());

	    dying_bodies_ = new float[bodies_.length];
	    Arrays.fill(dying_bodies_, 0);
    }

	public float getX(){
		return posX_;
	}

    /** Get height of the tower
     *
     * @return int tower height
     */
    public int getHeight(){
        return height_;
    }

    /** Get the subtype of the fortification
     *
     * @return the subtype of the fortification
     */
    public FortificationType getSubType() { return FortificationType.Tower; }

    public boolean hasWeapon() {
        return weapon_;
    }

	public void getWeapon() {
		weapon_ = true;
	}
}
