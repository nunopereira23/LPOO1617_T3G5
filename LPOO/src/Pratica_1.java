import java.util.*;

public class Pratica_1 {
	// Represent coordinates, formatted {y, x}
	public int hero_x = 1, hero_y = 1;
	public int new_hero_x = 1, new_hero_y = 1;
	public boolean unlocked = false;
	//public int[] lever = {8, 7};
	//public int[][] doors = {{1, 4}, {3, 2}, {3, 4}, {5, 0}, {6, 0}, {8, 2}, {8, 4}};
	public char[][] dungeon = {{'X','X','X','X','X','X','X','X','X','X'},
								{'X','H',' ',' ','I',' ','X',' ','G','X'},
								{'X','X','X',' ','X','X','X',' ',' ','X'},
								{'X',' ','I',' ','I',' ','X',' ',' ','X'},
								{'X','X','X',' ','X','X','X',' ',' ','X'},
								{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
								{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
								{'X','X','X',' ','X','X','X','X',' ','X'},
								{'X',' ','I',' ','I',' ','X','K',' ','X'},
								{'X','X','X','X','X','X','X','X','X','X'}};
	public void update()
	{
		dungeon[hero_y][hero_x] = ' ';
		switch (dungeon[new_hero_y][new_hero_x])
		{
		case 'X':
			break;
		case 'I':
			break;
		case 'K':
			if (!unlocked)
				unlocked = true;
		case 'S':
		case ' ':
			hero_x = new_hero_x;
			hero_y = new_hero_y;
		}
		if (unlocked)
		{
			dungeon[1][4] = 'S';
			dungeon[3][2] = 'S';
			dungeon[3][4] = 'S';
			dungeon[5][0] = 'S';
			dungeon[6][0] = 'S';
			dungeon[8][2] = 'S';
			dungeon[8][4] = 'S';
		}
		dungeon[8][7] = 'K';
		dungeon[hero_y][hero_x] = 'H';
		// falta verificar alcance do guarda
		// falta detetar game states
		new_hero_x = hero_x;
		new_hero_y = hero_y;
	}
	public void display()
	{
		for (int i = 0; i < 50; ++i)
			System.out.println("");
		for (int i = 0; i < 10; ++i)
		{
			for (int j = 0; j < 10; ++j)
				System.out.print(dungeon[i][j] + " ");
			System.out.print('\n');
		}
	}
	public boolean run()
	{
		Scanner input = new Scanner(System.in);
		display();
		System.out.println("Introduza um carater (w,s,a,d) para mover o heroi");
		
		while (true) {
			switch(input.next())
			{
				case "w":
					--new_hero_y;
					break;
				case "s":
					++new_hero_y;
					break;
				case "a":
					--new_hero_x;
					break;
				case "d":
					++new_hero_x;
					break;
				case "restart":
					return true;
				case "exit":
					return false;
				default:
					break;
			}
			update();
			display();
		}
	}
	public static void main(String[] args)
	{
		Pratica_1 instance = new Pratica_1();
		while (instance.run())
			instance = new Pratica_1();
	}
}


