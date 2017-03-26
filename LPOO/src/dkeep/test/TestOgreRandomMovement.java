package dkeep.test;

import static org.junit.Assert.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import dkeep.logic.DungeonKeep;
import dkeep.logic.Map;


public class TestOgreRandomMovement {

	@Test(timeout=1000)
	public void testRandomBehaviour(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 7}, map_level_[1], mapDoors[1], mapKeys[1]);    // Help me out here calling ogre's map at the parameters 
	}
}
