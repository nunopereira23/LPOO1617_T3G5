package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.DungeonKeep;
import dkeep.logic.DungeonKeep.State;
import dkeep.logic.Guard.Type;
import dkeep.logic.Map;
import dkeep.logic.Hero;

public class TestDungeonGameLogic {

	char[][] map = {{'X','X','X','X','X'},
					{'X','H',' ','G','X'},
					{'I',' ',' ',' ','X'},
					{'I','k',' ',' ','X'},
					{'X','X','X','X','X'}};
	
	
	char[][] map2 = {{'X','X','X','X','X'},
					{'I',' ',' ',' ','O'},
					{'X',' ',' ',' ','*'},
					{'X','k',' ',' ',' '},
					{'X','A',' ',' ',' '},
					{'X','X','X','X','X'}};
	
	
	int[][][] mapDoors = {
						  {
						   {0, 0, 0, 0, 0}, // Mapa que representa as portas
						   {0, 0, 0, 0, 0}, // Se a porta é aberta por uma chave
						   {1, 0, 0, 0, 0}, // o numero deve ser positivo
						   {1, 0, 0, 0, 0}, // Se a porta é aberta por uma alavanca
						   {0, 0, 0, 0, 0}
						  },					// o numero deve ser negativo
						  {// Level 2
						   {0,0,0,0,0},
						   {1,0,0,0,0},
						   {0,0,0,0,0},
						   {0,0,0,0,0},
						   {0,0,0,0,0},
						   {0,0,0,0,0},
						  }
						 };
			
	int[][][] mapKeys = {
						 {
						  {0, 0, 0, 0, 0}, // Mapa que representa as chaves ou alavancas,
						  {0, 0, 0, 0, 0}, // que abrem as portas com o respectivo numero
						  {0, 0, 0, 0, 0}, // Se é uma chave, o numero deve ser positivo 
						  {0, 1, 0, 0, 0}, // Se é uma alavanca, o numero deve ser negativo
						  {0, 0, 0, 0, 0}
						 },
						 {// Level 2
					  	  {0,0,0,0,0},
					  	  {0,0,0,0,0},
					  	  {0,0,0,0,0},
					  	  {0,1,0,0,0},
					  	  {0,0,0,0,0},
					  	  {0,0,0,0,0}
					 	 }
						};
	
	@Test
	public void testGameMap1(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, false, map, mapDoors[0], mapKeys[0]);
		assertEquals(5, dk1.getMap().getMapXSize());
		assertEquals(5, dk1.getMap().getMapYSize());
	}
	
	@Test
	public void testExitGame(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, false, map, mapDoors[0], mapKeys[0]);
		DungeonKeep.State state = dk1.update("a");
		assertEquals(DungeonKeep.State.LEVEL_PLAYING, state);
		DungeonKeep.State state2 = dk1.update("exit");
		assertEquals(DungeonKeep.State.GAME_EXITING, state2);
	}
	
	@Test
	public void testRestartGame(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, false, map, mapDoors[0], mapKeys[0]);
		DungeonKeep.State state = dk1.update("a");
		assertEquals(DungeonKeep.State.LEVEL_PLAYING, state);
		DungeonKeep.State state2 = dk1.update("restart");
		assertEquals(DungeonKeep.State.LEVEL_RESTART, state2);
	}
	
	@Test
	public void testResetGame(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, false, map, mapDoors[0], mapKeys[0]);
		DungeonKeep.State state = dk1.update("a");
		assertEquals(DungeonKeep.State.LEVEL_PLAYING, state);
		DungeonKeep.State state2 = dk1.update("reset");
		assertEquals(DungeonKeep.State.GAME_RESTART, state2);
	}
	
	
	@Test
	public void testMoveHeroIntoToFreeCell(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, false, map, mapDoors[0], mapKeys[0]);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		dk1.update("s");
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(2, dk1.getHeroPos()[1]);
	}
	
	@Test
	public void testMoveHeroIntoToWall(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, false, map, mapDoors[0], mapKeys[0]);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		dk1.update("a");
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]); // Aqui tinhas 2 em vez de 1	
	}
	
	@Test
	public void testMoveHeroToClosedDoor(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, false, map, mapDoors[0], mapKeys[0]);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		dk1.update("s");
		DungeonKeep.State state = dk1.update("a");
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(2, dk1.getHeroPos()[1]);
		assertEquals(false, dk1.getHero().checkKey(0)); 
		assertEquals(DungeonKeep.State.LEVEL_PLAYING, state);
	}
	
	@Test
	public void testMoveHeroToAdjacentGuardPosition(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, false, map, mapDoors[0], mapKeys[0]);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		DungeonKeep.State state = dk1.update("d");
		assertEquals(DungeonKeep.State.GAME_OVER, state);
	}
	
	@Test
	public void testMoveHeroIntoKeyCell(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, false, map, mapDoors[0], mapKeys[0]);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		dk1.update("s");
		dk1.update("s");
		assertEquals(true, dk1.getHero().checkKey(0));     
														   
	}
	
	
	
	
	@Test                 
	public void testLevelCompleted(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, false, map, mapDoors[0], mapKeys[0]);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		dk1.update("s");
		dk1.update("s");
		dk1.update("a");
		DungeonKeep.State state = dk1.update("a");
		assertEquals(DungeonKeep.State.GAME_COMPLETED, state); 
	}															
	
	@Test
	public void testNumberOfGuards(){
		DungeonKeep dk1 = new DungeonKeep(0, 1, 0); 
		assertEquals(1,dk1.getGuards().length);
	}
	
	@Test
	public void testGuardsPersonality1(){
		DungeonKeep dk1 = new DungeonKeep(0, 1, 0); 
		assertEquals(Type.ROOKIE,dk1.getGuards()[0].getPersonality());
	}
	
	@Test
	public void testGuardsPersonality2(){
		DungeonKeep dk1 = new DungeonKeep(0, 2, 0); 
		assertEquals(Type.DRUNKEN,dk1.getGuards()[0].getPersonality());
	}
	
	@Test
	public void testGuardsPersonality3(){
		DungeonKeep dk1 = new DungeonKeep(0, 3, 0); 
		assertEquals(Type.SUSPICIOUS,dk1.getGuards()[0].getPersonality());
	}
	
	
	
	// Testes Ogre
	
	@Test
	public void testHeroMovesToOgreAdjacentPosition(){
	DungeonKeep dk1 = new DungeonKeep(new int[]{1, 4}, false, map2, mapDoors[1], mapKeys[1]);
	assertEquals(1, dk1.getHeroPos()[0]);
	assertEquals(4, dk1.getHeroPos()[1]);
	dk1.update("d");
	dk1.update("d");
	dk1.update("w");
	dk1.update("w");
	DungeonKeep.State state = dk1.update("w");
	assertEquals(DungeonKeep.State.GAME_OVER, state);
	
	}
	
	@Test
	public void testHeroMovesToKeyCell(){
	DungeonKeep dk1 = new DungeonKeep(new int[]{1, 4}, false, map2, mapDoors[1], mapKeys[1]);
	assertEquals(1, dk1.getHeroPos()[0]);
	assertEquals(4, dk1.getHeroPos()[1]);
	DungeonKeep.State state = dk1.update("w");
	assertEquals(true, dk1.getHero().checkKey(0));
	}
	
	
	@Test
	public void testHeroMovesIntoClosedDoorsWithoutKey(){
	DungeonKeep dk1 = new DungeonKeep(new int[]{1, 4}, false, map2, mapDoors[1], mapKeys[1]);
	assertEquals(1, dk1.getHeroPos()[0]);
	assertEquals(4, dk1.getHeroPos()[1]);
	DungeonKeep.State state = dk1.update("d");
	assertEquals(DungeonKeep.State.LEVEL_PLAYING, state);
	dk1.update("w");
	dk1.update("w");
	dk1.update("w");
	dk1.update("a");
	DungeonKeep.State state2 = dk1.update("a");
	assertEquals(DungeonKeep.State.LEVEL_PLAYING, state2);
	}
	
	
	@Test
	public void testHeroMovesIntoClosedDoorsWithKey(){
	DungeonKeep dk1 = new DungeonKeep(new int[]{1, 4}, false, map2, mapDoors[1], mapKeys[1]);
	assertEquals(1, dk1.getHeroPos()[0]);
	assertEquals(4, dk1.getHeroPos()[1]);
	dk1.update("w");
	dk1.update("w");
	dk1.update("w");
	assertEquals(1, dk1.getMap().checkDoor(dk1.getHeroPos()[0] - 1,dk1.getHeroPos()[1]));
	assertEquals('I', dk1.getMap().check(0, 1));	
	DungeonKeep.State state = dk1.update("a");
	assertEquals('S', dk1.getMap().check(0, 1));	
													
	
	}
	
	@Test
	public void testHeroMovesIntoOpenDoorsWithKey(){
	DungeonKeep dk1 = new DungeonKeep(new int[]{1, 4}, false, map2, mapDoors[1], mapKeys[1]);
	assertEquals(1, dk1.getHeroPos()[0]);
	assertEquals(4, dk1.getHeroPos()[1]);
	dk1.update("w");
	dk1.update("w");
	dk1.update("w");
	dk1.update("a");
	DungeonKeep.State state = dk1.update("a");
	assertEquals(DungeonKeep.State.GAME_COMPLETED, state);
	}
	
	/*
	@Test
	public void testOgreMovesIntoKey(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 7}, false, map2, mapDoors[1], mapKeys[1],1);
		dk1.update("d");
		dk1.update("d");
		dk1.update("d");
		dk1.getOgres()[0].changeX(1, 5);
		dk1.getOgres()[0].changeY(3, 5);
		assertEquals('$', dk1.getMap().check(1, 3));
	}
	*/
	
}
