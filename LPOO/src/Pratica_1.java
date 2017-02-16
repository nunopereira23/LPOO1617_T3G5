import java.util.*;

public class Pratica_1 {
	public static int hero_x = 1, hero_y = 1;
	public static char[][] matrix = {{'X','X','X','X','X','X','X','X','X','X'},
									{'X','H',' ',' ','I',' ','X',' ','G','X'},
									{'X','X','X',' ','X','X','X',' ',' ','X'},
									{'X',' ','I',' ','I',' ','X',' ',' ','X'},
									{'X','X','X',' ','X','X','X',' ',' ','X'},
									{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
									{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
									{'X','X','X',' ','X','X','X','X',' ','X'},
									{'X',' ','I',' ','I',' ','X','K',' ','X'},
									{'X','X','X','X','X','X','X','X','X','X'}};
	public static void display()
	{
		for (int i = 0; i < 50; ++i)
			System.out.println("");
		for (int i = 0; i < 10; ++i)
		{
			for (int j = 0; j < 10; ++j)
				System.out.print(matrix[i][j] + " ");
			System.out.print('\n');
		}
	}
	public static void update()
	{
		
	}
	public static void main(String[] args)
	{
		System.out.println("Introduza um carater (w,s,a,d) para mover o heroi"); 
		Scanner input = new Scanner(System.in);
		switch(input.next()){
		case "w":
//				if()
			
			
		}
	}	
}

