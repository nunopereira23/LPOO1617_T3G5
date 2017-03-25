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
	
	int[][] mapDoors = {{0, 0, 0, 0, 0}, // Mapa que representa as portas
						{0, 0, 0, 0, 0}, // Se a porta é aberta por uma chave
						{1, 0, 0, 0, 0}, // o numero deve ser positivo
						{1, 0, 0, 0, 0}, // Se a porta é aberta por uma alavanca
						{0, 0, 0, 0, 0}};// o numero deve ser negativo
	
	int[][] mapKeys = {{0, 0, 0, 0, 0}, // Mapa que representa as chaves ou alavancas,
					   {0, 0, 0, 0, 0}, // que abrem as portas com o respectivo numero
					   {0, 0, 0, 0, 0}, // Se é uma chave, o numero deve ser positivo 
					   {0, 1, 0, 0, 0}, // Se é uma alavanca, o numero deve ser negativo
					   {0, 0, 0, 0, 0}};
	
	@Test
	public void testMoveHeroIntoToFreeCell(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, map, mapDoors, mapKeys);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		dk1.update("s");
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(2, dk1.getHeroPos()[1]);
	}
	
	@Test
	public void testMoveHeroIntoToWall(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, map, mapDoors, mapKeys);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		dk1.update("a");
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]); // Aqui tinhas 2 em vez de 1	
	}
	
	@Test
	public void testMoveHeroToClosedDoor(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, map, mapDoors, mapKeys);
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
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, map, mapDoors, mapKeys);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		DungeonKeep.State state = dk1.update("d");
		assertEquals(DungeonKeep.State.GAME_OVER, state);
	}
	
	@Test
	public void testMoveHeroIntoKeyCell(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, map, mapDoors, mapKeys);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		dk1.update("s");
		dk1.update("s");
		assertEquals(true, dk1.getHero().checkKey(1));     // Porquê que o checkKey tem parametro? Talvez haja uma melhor solucao
														   // O checkKey é usado para ver se o heroi tem a chave que corresponde ao argumento dado.
														   // A função é usada para verificar se o heroi pode abrir uma porta quando se move contra ela
														   // (se a porta corresponde a alguma das chaves que o heroi possa ter)
														   // Se o argumento dado for 0, a função diz te só se o heroi tem alguma chave ou não; 
														   // talvez prefiras dar 0 como argumento? Só para verificar se ele tem alguma chave?
	}
	
	
	
	
	@Test                 //Esta dar Game Completed quando devia dar Level Completed
	public void testLevelCompleted(){
		DungeonKeep dk1 = new DungeonKeep(new int[]{1, 1}, map, mapDoors, mapKeys);
		assertEquals(1, dk1.getHeroPos()[0]);
		assertEquals(1, dk1.getHeroPos()[1]);
		dk1.update("s");
		dk1.update("s");
		dk1.update("a");
		DungeonKeep.State state = dk1.update("a");
		assertEquals(DungeonKeep.State.GAME_COMPLETED, state); // Isso é pq é o ultimo nivel da lista de niveis, para todos os efeitos é o mesmo que LEVEL_COMPLETED,
	}															// mas é uma maneira de dizer à interface que não há mais níveis.
																// Isto acontece pq quando criamos o nivel pomos o no fim da lista de niveis
}
