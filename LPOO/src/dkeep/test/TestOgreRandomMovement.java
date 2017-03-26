package dkeep.test;

import static org.junit.Assert.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import dkeep.logic.DungeonKeep;
import dkeep.logic.Map;
import dkeep.logic.Ogre;


public class TestOgreRandomMovement {

	@Test(timeout=1000)
	public void testRandomBehaviour(){
		DungeonKeep dk1 = new DungeonKeep(1, 0, 0);    
		boolean noHeroMovEqualsNoOgreMov = false, ogreClubIsInvalidPos = false, neverOnWall = true;
		while(!noHeroMovEqualsNoOgreMov || !ogreClubIsInvalidPos || !neverOnWall){
			int randNum = ThreadLocalRandom.current().nextInt(0, 4);
			String input = dk1.possibleKeys[randNum];
			int hero_x = dk1.getHero().getX();
			int hero_y = dk1.getHero().getY();
			int ogre_x = dk1.getOgres()[0].getOgreX();
			int ogre_y = dk1.getOgres()[0].getOgreY();
			dk1.update(input);
			
			if(dk1.getHero().getX()== hero_x && dk1.getHero().getY()== hero_y){
				if(dk1.getOgres()[0].getOgreX()==ogre_x && dk1.getOgres()[0].getOgreY()==ogre_y)
					noHeroMovEqualsNoOgreMov = true;
			}
			
			
			else if(dk1.getOgres()[0].getOgreX() > 8 
					|| dk1.getOgres()[0].getOgreX() < 1
					|| dk1.getOgres()[0].getOgreY() > 8
					|| dk1.getOgres()[0].getOgreY() < 1)
				neverOnWall = false;
					
					
			else if(dk1.getOgres()[0].getClubX() > 8
					|| dk1.getOgres()[0].getClubX() < 1
					|| dk1.getOgres()[0].getClubY() > 8
					|| dk1.getOgres()[0].getClubY() < 1)
				neverOnWall = false;
			
		
			else if(dk1.getOgres()[0].getClubX()== dk1.getOgres()[0].getOgreX() 
						&& dk1.getOgres()[0].getClubY() == dk1.getOgres()[0].getOgreY()-1
					||	
					dk1.getOgres()[0].getClubX()== dk1.getOgres()[0].getOgreX() 
						&& dk1.getOgres()[0].getClubY() == dk1.getOgres()[0].getOgreY()+1
					||			
					dk1.getOgres()[0].getClubX()== dk1.getOgres()[0].getOgreX()-1
						&& dk1.getOgres()[0].getClubY() == dk1.getOgres()[0].getOgreY()
					||					
					dk1.getOgres()[0].getClubX()== dk1.getOgres()[0].getOgreX()+1
						&& dk1.getOgres()[0].getClubY() == dk1.getOgres()[0].getOgreY())
				ogreClubIsInvalidPos=true;
			
			else if(dk1.getOgres()[0].getClubX()== dk1.getOgres()[0].getOgreX() 
						&& dk1.getOgres()[0].getClubY() == dk1.getOgres()[0].getOgreY())
				ogreClubIsInvalidPos=true;
			
			else
				fail("Some error occured.\n");	
		
		}
	}
}
