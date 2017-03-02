package dkeep.cli;

import java.util.Scanner;
import dkeep.logic.*;

class DKInterface
{
	private int game_state; // Will be used
	private static Scanner istream = new Scanner(System.in);
	public boolean run()
	{
		DungeonKeep level = new DungeonKeep();
		String input;
		do
		{
			// Create level or continue
			do
			{
				input = istream.next();
				// Update
				// Display
				
				break;
			}
			while (game_state == 0);
			// Check state
			break;
		}
		while (true);
		
		return false;
	}
	public static void main(String[] args)
	{
		DKInterface instance = new DKInterface();
		while (instance.run());
		for (int i = 0; i < 50; ++i)
			System.out.println("");
		System.out.println("Goodbye!");
	}
} 

