import java.util.*;

public class Pratica_1 {
	public static void main(String[] args)
	{
		char[][] matrix = {{'X','X','X','X','X','X','X','X','X','X'},
							{'X','H',' ',' ','I',' ','X',' ','G','X'},
							{'X','X','X',' ','X','X','X',' ',' ','X'},
							{'X',' ','I',' ','I',' ','X',' ',' ','X'},
							{'X','X','X',' ','X','X','X',' ',' ','X'},
							{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
							{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
							{'X','X','X',' ','X','X','X','X',' ','X'},
							{'X',' ','I',' ','I',' ','X','K',' ','X'},
							{'X','X','X','X','X','X','X','X','X','X'}};
		
	
		for(int i=0; i<10;i++){
			for(int j=0; j<10;j++){
				System.out.print(matrix[i][j]);
				System.out.print(' ');
			}
			System.out.print('\n');
		}
		
		System.out.println("Introduza um carater (w,s,a,d) para mover o heroi"); 
		Scanner input = new Scanner(System.in);
		switch(input.next()){
		case "w":
				if()
			
			
		}
	}	
}

