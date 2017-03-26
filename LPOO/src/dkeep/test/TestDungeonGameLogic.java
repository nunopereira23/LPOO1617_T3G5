package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.DungeonKeep;
import dkeep.logic.DungeonKeep.State;
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
	
	public char[][] getMap2() {
		return map2;
	}
	
	
	int[][][] mapDoors = {
						  {
						   {0, 0, 0, 0, 0}, // Mapa que representa as portas
						   {0, 0, 0, 0, 0}, // Se a porta � aberta por uma chave
						   {1, 0, 0, 0, 0}, // o numero deve ser positivo
						   {1, 0, 0, 0, 0}, // Se a porta � aberta por uma alavanca
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
						  {0, 0, 0, 0, 0}, // Se � uma chave, o numero deve ser positivo 
						  {0, 1, 0, 0, 0}, // Se � uma alavanca, o numero deve ser negativo
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
	public void testMoveHeroIntoToFreeCell(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, map, mapDoors[0], mapKeys[0]);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		dk1.update("s");
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(2, dk1.getHeroPos()[1]);
	}
	
	@Test
	public void testMoveHeroIntoToWall(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, map, mapDoors[0], mapKeys[0]);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		dk1.update("a");
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]); // Aqui tinhas 2 em vez de 1	
	}
	
	@Test
	public void testMoveHeroToClosedDoor(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, map, mapDoors[0], mapKeys[0]);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		dk1.update("s");
		DungeonKeep.State state = dk1.update("a");
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(2, dk1.getHeroPos()[1]);
		assertEquals(DungeonKeep.State.LEVEL_PLAYING, state);
	}
	
	@Test
	public void testMoveHeroToAdjacentGuardPosition(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, map, mapDoors[0], mapKeys[0]);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		DungeonKeep.State state = dk1.update("d");
		assertEquals(DungeonKeep.State.GAME_OVER, state);
	}
	
	@Test
	public void testMoveHeroIntoKeyCell(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, map, mapDoors[0], mapKeys[0]);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		dk1.update("s");
		dk1.update("s");
		assertEquals(true, dk1.getHero().checkKey(0));     // Porqu� que o checkKey tem parametro? Talvez haja uma melhor solucao
														   // O checkKey � usado para ver se o heroi tem a chave que corresponde ao argumento dado.
														   // A fun��o � usada para verificar se o heroi pode abrir uma porta quando se move contra ela
														   // (se a porta corresponde a alguma das chaves que o heroi possa ter)
														   // Se o argumento dado for 0, a fun��o diz te s� se o heroi tem alguma chave ou n�o; 
														   // talvez prefiras dar 0 como argumento? S� para verificar se ele tem alguma chave?
	}
	
	
	
	
	@Test                 //Esta dar Game Completed quando devia dar Level Completed
	public void testLevelCompleted(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, map, mapDoors[0], mapKeys[0]);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		dk1.update("s");
		dk1.update("s");
		dk1.update("a");
		DungeonKeep.State state = dk1.update("a");
		assertEquals(DungeonKeep.State.GAME_COMPLETED, state); // Isso � pq � o ultimo nivel da lista de niveis, para todos os efeitos � o mesmo que LEVEL_COMPLETED,
	}															// mas � uma maneira de dizer � interface que n�o h� mais n�veis.
																// Isto acontece pq quando criamos o nivel pomos o no fim da lista de niveis
	
	// Testes Ogre
	
	@Test
	public void testHeroMovesToOgreAdjacentPosition(){
	DungeonKeep dk1 = new DungeonKeep(new int[]{1, 4}, map2, mapDoors[1], mapKeys[1]);
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
	DungeonKeep dk1 = new DungeonKeep(new int[]{1, 4}, map2, mapDoors[1], mapKeys[1]);
	assertEquals(1, dk1.getHeroPos()[0]);
	assertEquals(4, dk1.getHeroPos()[1]);
	DungeonKeep.State state = dk1.update("w");
	assertEquals(true, dk1.getHero().checkKey(0));
	}
	
	
	@Test
	public void testHeroMovesIntoClosedDoorsWithoutKey(){
	DungeonKeep dk1 = new DungeonKeep(new int[]{1, 4}, map2, mapDoors[1], mapKeys[1]);
	assertEquals(1, dk1.getHeroPos()[0]);
	assertEquals(4, dk1.getHeroPos()[1]);
	DungeonKeep.State state= dk1.update("d");
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
	DungeonKeep dk1 = new DungeonKeep(new int[]{1, 4}, map2, mapDoors[1], mapKeys[1]);
	assertEquals(1, dk1.getHeroPos()[0]);
	assertEquals(4, dk1.getHeroPos()[1]);
	dk1.update("w");
	dk1.update("w");
	dk1.update("w");
	assertEquals(1,dk1.getMap().checkDoor(dk1.getHeroPos()[0] - 1,dk1.getHeroPos()[1]));  
	assertEquals('I', this.getMap2()[1][0]);	//First y, then x but only with getMap2
	DungeonKeep.State state = dk1.update("a");
	assertEquals('S', this.getMap2()[1][0]);
	
	}
	
	
	@Test
	public void testHeroMovesIntoOpenDoorsWithKey(){
	DungeonKeep dk1 = new DungeonKeep(new int[]{1, 4}, map2, mapDoors[1], mapKeys[1]);
	assertEquals(1, dk1.getHeroPos()[0]);
	assertEquals(4, dk1.getHeroPos()[1]);
	dk1.update("w");
	dk1.update("w");
	dk1.update("w");
	dk1.update("a");
	DungeonKeep.State state = dk1.update("a");
	assertEquals(DungeonKeep.State.GAME_COMPLETED, state);
	}
}
