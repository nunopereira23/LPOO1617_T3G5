package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class CVCFortification extends CVCStructure {
	protected Body[] bodies_;
	protected boolean[] edges_;

	public CVCFortification(World world) {
		super(world);
	}

	public Body[] getBodies(){
		return bodies_;
	}

	public StructureType getType() {
		return StructureType.Fortification;
	};

	public boolean[] getEdges() {
		return edges_;
	} // This may change yet
}
