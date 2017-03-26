package dkeep.test;

import static org.junit.Assert.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import dkeep.logic.DungeonKeep;
import dkeep.logic.Map;


public class TestOgreRandomMovement {

	@Test(timeout=1000)
	public void testRandomBehaviour(){
		DungeonKeep dk1 = new DungeonKeep(1, 0, 0);    // Help me out here calling ogre's map at the parameters
														// Acho que isto funciona para o que pretendes
	}
}
