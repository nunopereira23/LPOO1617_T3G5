package dkeep.cli;

import java.util.Scanner;
import dkeep.logic.*;
import dkeep.logic.DungeonKeep.State;

public class DKInterface
{
	private int level_number = 0;
	private DungeonKeep level;
	private DungeonKeep.State state = DungeonKeep.State.GAME_START;
	
	private String input;
	private static Scanner buffer = new Scanner(System.in);
	
	public void run()
	{
		level = new DungeonKeep(level_number, 0, 0);
		do
		{
			// Do a pretty introductory message?
			level.display(System.out);
			do
			{
				input = buffer.next();
				state = level.update(input);
				level.display(System.out);
			}
			while (state == DungeonKeep.State.LEVEL_PLAYING);
			
			switch (state)
			{
				case LEVEL_COMPLETED:
					++level_number;
				case LEVEL_RESTART:
					level = new DungeonKeep(level_number, 0, 0);
					break;
				case GAME_OVER:
					System.out.println("Game over!");
					System.out.println("Continue?");
					do
					{
						input = buffer.next();
					}
					while (!input.equals("yes") && !input.equals("no"));
					if (input.equals("yes"))
					{
						level = new DungeonKeep(level_number, 0, 0);
					}
					else
					{
						level_number = 0;
						level = new DungeonKeep(level_number, 0, 0);
					}
					break;
				case GAME_RESTART:
					level_number = 0;
					level = new DungeonKeep(level_number, 0, 0);
					break;
				case GAME_COMPLETED:
					System.out.println("Congratulations!");
					System.out.println("You beat the game!");
					System.out.println("Restart?");
					do
					{
						input = buffer.next();
					}
					while (!input.equals("yes") && !input.equals("no"));
					if (input.equals("yes"))
					{
						level_number = ;
						level = new DungeonKeep(level_number, 0, 0);
					}
					else
					{
						state = DungeonKeep.State.GAME_EXITING;
					}
					break;
				default:
					break;	
			}
		}
		while (state != DungeonKeep.State.GAME_EXITING);
	}
	 
	public State getGameState(){
		return this.state;
	}
	
	public static void main(String[] args)
	{
		DKInterface instance = new DKInterface();
		instance.run();
		for (int i = 0; i < 50; ++i)
		{
			System.out.println();
		}
		System.out.println("Goodbye!");
	}
}

