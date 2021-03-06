package com.cvc.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class CVCFortification extends CVCStructure {
	public enum FortificationType {Tower, Wall}

    public static final float STONE_WIDTH = 2.0f;
    public static final float STONE_HEIGHT = 1.0f;
    public static final float STONE_EDGE_WIDTH = 1.0f;
    public static final float STONE_EDGE_HEIGHT = 2.0f;

	protected boolean[] edges_;
    protected int high_edges_;

	/** Creates fortification
	 *
	 * @param world
	 */
	public CVCFortification(World world) {
		super(world);
	}

	/** Get the edges
	 *
	 * @return the edges of the fortification
	 */
	public boolean[] getEdges() {
		return edges_;
	}

	/** Get the highest edges
	 *
	 * @return int the high edges
	 */
    public int getHighEdges() {
        return high_edges_;
    }

	/** Returns the type of the structure
	 *
	 * @return StructureType the type of the structure
	 */
	public StructureType getType() {
		return StructureType.Fortification;
	}

	public abstract FortificationType getSubType();
}
