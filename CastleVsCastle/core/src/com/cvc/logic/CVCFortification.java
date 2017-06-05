package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class CVCFortification extends CVCStructure {
    public static final float STONE_WIDTH = 2.0f;
    public static final float STONE_HEIGHT = 1.0f;
    public static final float STONE_EDGE_WIDTH = 1.0f;
    public static final float STONE_EDGE_HEIGHT = 2.0f;

	protected Body[] bodies_; // Stone
	protected boolean[] edges_;
    protected int high_edges_;

	public CVCFortification(World world) {
		super(world);
	}

	public Body[] getBodies(){
		return bodies_;
	}

	public StructureType getType() {
		return StructureType.Fortification;
	}

	public boolean[] getEdges() {
		return edges_;
	}

    public int getHighEdges() {
        return high_edges_;
    }
}
