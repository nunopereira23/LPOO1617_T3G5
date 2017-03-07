package dkeep.cli;

import java.util.Scanner;
import dkeep.logic.*;

public class DKInterface
{
	private DungeonKeep.GameState state;
	private static Scanner buffer = new Scanner(System.in);
	public void run()
	{
		int level_number = 1;
		DungeonKeep level = new DungeonKeep(level_number);
		String input;
		do
		{
			// Do a pretty introductory message?
			level.display();
			do
			{
				input = buffer.next();
				state = level.update(input);
				level.display();
			}
			while (state == DungeonKeep.GameState.LEVEL_PLAYING);
			
			switch (state)
			{
				case LEVEL_COMPLETED:
					if (++level_number > level.count())
					{
						state = DungeonKeep.GameState.GAME_COMPLETED;
						break;
					}
				case LEVEL_RESTART:
					level = new DungeonKeep(level_number);
					break;
				default:
					break;
			}
			switch (state)
			{
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
						level = new DungeonKeep(level_number);
					}
					else
					{
						state = DungeonKeep.GameState.GAME_EXITING;
					}
					break;
				case GAME_RESTART:
					level_number = 1;
					level = new DungeonKeep(level_number);
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
						level_number = 1;
						level = new DungeonKeep(level_number);
					}
					else
					{
						state = DungeonKeep.GameState.GAME_EXITING;
					}
					break;
				default:
					break;	
			}
		}
		while (state != DungeonKeep.GameState.GAME_EXITING);
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

