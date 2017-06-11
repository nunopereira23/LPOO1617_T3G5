package com.cvc.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class CVCWorld {
    public final World world_ = new World(new Vector2(0, -9.8f), true); // don't simulate inactive bodies;

	private boolean update = true;

	public static final float GROUND_WIDTH = 150.0f;
	public static final float GROUND_HEIGHT = 1.0f;

//    private static long timer_epoch_;
//    private static long timer_;

    private CVCCastle player_castle_;
    private CVCCastle enemy_castle_;

	private Body body_;


    /** Create the world where the game is played
     *
     */
    public CVCWorld() {
        player_castle_ = new CVCCastle(world_, false);
        enemy_castle_ = new CVCCastle(world_, true);

        BodyDef bodydef = new BodyDef();
        bodydef.position.set(0f, 0f);
        bodydef.type = BodyDef.BodyType.StaticBody;

        PolygonShape shape = new PolygonShape();
        shape.set(new float[] {0.0f, 0.0f,
                               GROUND_WIDTH, 0.0f,
                               GROUND_WIDTH, GROUND_HEIGHT,
                               0.0f, GROUND_HEIGHT});

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.density = 1220; // Dirt kg/m^3
        fixture.friction = 0.325f; // Dirt
        fixture.restitution = 0.393f; // Dirt

	    body_ = world_.createBody(bodydef);
	    body_.createFixture(fixture);
    }

    /**  Connect and synchronize with other phone
     *
     */
    public void synchronize() {

    }

    public void setUpdate(){
	    update = !update;
    }

	public boolean isUpdating() {
		return world_.isLocked();
	}

    /** Updates the world
     *
     * @param delta The time in seconds since the last update
     */
    public void update(float delta) {
	    if (update) {
		    world_.step(delta, 50, 0);
		    player_castle_.update(delta);
		    enemy_castle_.update(delta);
	    }
    }

    /** Dispose the world
     *
     */
    public void dispose() {
        world_.dispose();
    }

    // Getters

	/** Get the ground
	 *
	 * @return Body ground
	 */
	public Body getGround(){
		return body_;
	}

    /** Get the player's castle
     *
     * @return the player's castle
     */
	public CVCCastle getPlayerCastle() {
		return player_castle_;
	}

	/** Get the enemy's castle
	 *
	 * @return the enemy's castle
	 */
    public CVCCastle getEnemyCastle() {
        return enemy_castle_;
    }

    public void getContextMenu(int x, int y) {
	    player_castle_.getContextMenu(x, y);
    }
}
