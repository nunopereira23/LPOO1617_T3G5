package com.cvc.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class CVCStructure {
    protected static World world_;
	protected Body body_; // this will probably be an array

    public CVCStructure(World world) {
        world_ = world;
    }
}
