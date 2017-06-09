package com.cvc.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class CVCWorld {
    private final World world_;

	public static final float GROUND_WIDTH = 150.0f;
	public static final float GROUND_HEIGHT = 1.0f;

//    private static long timer_epoch_;
//    private static long timer_;

    private CVCCastle player_castle_;
//    private CVCCastle enemy_castle_;

    private PolygonShape shape_;
    private FixtureDef fixture_;
    private BodyDef bodydef_;
	private Body body_;


    /** Create the world where the game is played
     *
     */
    public CVCWorld() {
        world_ = new World(new Vector2(0, -9.8f), true); // don't simulate inactive bodies
	    world_.setVelocityThreshold(Float.MAX_VALUE);

        player_castle_ = new CVCCastle(world_);
     //   enemy_castle_ = new CVCCastle(world_);

        bodydef_ = new BodyDef();
        bodydef_.position.set(0f, 0f);
        bodydef_.type = BodyDef.BodyType.StaticBody;

        shape_ = new PolygonShape();
        shape_.setAsBox(GROUND_WIDTH / 2, GROUND_HEIGHT / 2); // halfwidth, halfheight

        fixture_ = new FixtureDef();
        fixture_.shape = shape_;
        fixture_.density = 1220; // Dirt kg/m^3
        fixture_.friction = 0.325f; // Dirt
        fixture_.restitution = 0.393f; // Dirt

	    body_ = world_.createBody(bodydef_);
	    body_.createFixture(fixture_);
    }

    /**  Connect and synchronize with other phone
     *
     */
    public void synchronize() {

    }

    /** Update the world
     *
     * @param delta
     */
    public void update(float delta) {
        world_.step(delta, 50, 0);
        player_castle_.update(delta);
//        enemy_castle_.update();
    }

    /** Get the ground
     *
     * @return Body ground
     */
    public Body getGround(){
	    return body_;
    }

    /** Dispose the world
     *
     */
    public void dispose() {
        world_.dispose();
    }

    // Getters

    /** Get the structures of the player
     *
     * @return CVCStructure[] the structures of the player
     */
	public CVCStructure[] getPlayerStructures() {
		return player_castle_.getStructures();
	}
}
