import java.util.Scanner;

public class SlidePuzzle 
{
	int n = 3;
	
	String[][] grid;
	String[][] soln;
	
	int[] iarray;
	
	public SlidePuzzle(int r)
	{
		n = r;
		
		grid = new String[n][n];
		soln = new String[n][n];
		
		iarray = new int[n * n - 1];
		
		int counter = 1;
		
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{	
				grid[i][j] = counter + "";
				soln[i][j] = counter + "";
				counter++;
			}
		}
		
		grid[n - 1][n - 1] = " ";
		soln[n - 1][n - 1] = " ";
	}
	
	private void createIArray()
	{
		int index = 0;
		
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				if (!grid[i][j].equals(" "))
				{
					iarray[index] = Integer.parseInt(grid[i][j]);
					index++;
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void printIArray()
	{
		System.out.println();
		
		for (int i = 0; i < iarray.length; i++)
		{
			System.out.print(iarray[i]);
		}
		
		System.out.println();
	}
	
	private int findInversions()
	{
		int inv = 0;
		
		for (int i = 0; i < iarray.length; i++)
		{
			for (int j = i + 1; j < iarray.length; j++)
			{
				if (iarray[i] > iarray[j])
				{
					inv++;
				}
			}
		}
		
		return inv;
	}
	
	private int rowBlank()
	{
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				if (grid[i][j].equals(" "))
				{
					return n - i;
				}
			}
		}
		
		return -1;
	}
	
	private boolean checkIArray()
	{
		if (n % 2 == 1 && findInversions() % 2 == 0)
		{
			return true;
		}
		else if (n % 2 == 0)
		{
			if (rowBlank() % 2 == 0 && findInversions() % 2 == 1)
			{
				return true;
			}
			else if (rowBlank() % 2 == 1 && findInversions() % 2 == 0)
			{
				return true;
			}
		}
		
		return false;
	}
	
	private boolean slide(String num)
	{
		if (n == 1)
		{
			return false;
		}
		
		int x = -1, y = -1;
		
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				if (grid[i][j].equals(num))
				{
					x = i;
					y = j;
				}
			}
		}
		
		if (x == -1 || y == -1)
		{
			return false;
		}
		
		if ((x - 1 >= 0) && grid[x - 1][y].equals(" "))
		{
			grid[x - 1][y] = grid[x][y];
			grid[x][y] = " ";
			return true;
		}
		else if ((y - 1 >= 0) && grid[x][y - 1].equals(" "))
		{
			grid[x][y - 1] = grid[x][y];
			grid[x][y] = " ";
			return true;
		}
		else if ((x + 1 < n) && grid[x + 1][y].equals(" "))
		{
			grid[x + 1][y] = grid[x][y];
			grid[x][y] = " ";
			return true;
		}
		else if ((y + 1 < n) && grid[x][y + 1].equals(" "))
		{
			grid[x][y + 1] = grid[x][y];
			grid[x][y] = " ";
			return true;
		}
		
		return false;
	}
	
	private void swap(int x, int y, int r, int c)
	{
		String temp = grid[x][y];
		grid[x][y] = grid[r][c];
		grid[r][c] = temp;
	}
	
	private void shuffleBoard() // Fisher-Yates
	{
		int r = (n * n) - 1;
		
		while (r > 0)
		{
			int c = (int) Math.floor(Math.random() * r);
			
			int xr = r % n;
			int yr = (int) Math.floor(r / n);
			
			int xc = c % n;
			int yc = (int) Math.floor(c / n);
			
			swap(xr, yr, xc, yc);
			
			r--;
		}
		
		createIArray();
		
		if (!checkIArray())
		{
			shuffleBoard();
		}
		
		return;
	}
	
	private boolean checkBoard()
	{
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{	
				if (!grid[i][j].equals(soln[i][j]))
				{
					return false;
				}
			}
		}
		
		System.out.println();
		System.out.println("Congrats!");
		return true;
	}
	
	@SuppressWarnings("resource")
	private void play()
	{
		Scanner s = new Scanner(System.in);
		
		int movecounter = 1;
		
		while (!checkBoard())
		{
			String input;
			System.out.println();
			System.out.print("(Move #" + movecounter + ") Enter the number on the tile: ");
			input = s.next();
			
			while(!slide(input))
			{
				System.out.println();
				System.out.print("Enter a valid number, or enter Q to Quit: ");
				input = s.next();
				
				
				if (input.equalsIgnoreCase("q"))
				{
					return;
				}
			}
			
			System.out.println("---------------------------------------------------------------------------------"
					+ "-----------------------------------------------------------------------------------------------");
			
			printBoard();
			
			movecounter++;
		}
		
	}
	
	public void printBoard()
	{
		/*for (int i = 0; i < n; i++)
		{
			System.out.print("________");
		}*/
		
		for (int i = 0; i < n; i++)
		{
			System.out.print("\t");
			for (int j = 0; j < n; j++)
			{
				System.out.print("[" + grid[i][j] + "]\t");
			}
			
			System.out.println();
			
			if (i != n - 1)
			{
				System.out.println();
			}
		}
		
		System.out.print("---------------------------------------------------------------------------------"
				+ "-----------------------------------------------------------------------------------------------");
	}
	
	@SuppressWarnings("resource")
	public static void main (String[] args)
	{
		Scanner s = new Scanner(System.in);
		
		System.out.println("Welcome to SlidePuzzle!");
		
		String playagain = "y";
		
		while (playagain.equalsIgnoreCase("y"))
		{
		
			int num = 1;
			
			while (num <= 1)
			{
				System.out.print("Enter the number of rows/columns you would like to play with (Default is 3): ");
				try
				{
					num = s.nextInt();
				}
				catch (java.util.InputMismatchException e)
				{
					num = 3;
				}
				
				if (num <= 1)
				{
					System.out.println("Please enter a number greater than 1!");
				}
			}
			
			System.out.println();
			
			System.out.println("---------------------------------------------------------------------------------"
					+ "-----------------------------------------------------------------------------------------------");
			System.out.println("\t\t\t\t\t\t\t\t\t\t" + num + "x" + num + " Puzzle:");
			System.out.println("---------------------------------------------------------------------------------"
					+ "-----------------------------------------------------------------------------------------------");
			
			SlidePuzzle g = new SlidePuzzle(num);
			
			g.shuffleBoard();
			
			g.printBoard();
			
			//g.printIArray();
			//System.out.println(g.findInversions());
			
			g.play();
			
			System.out.println("---------------------------------------------------------------------------------"
					+ "-----------------------------------------------------------------------------------------------");
			
			System.out.println("Thank you for playing SlidePuzzle!");
			
			System.out.println("Would you like to play again?");
			playagain = s.nextLine();
		}
		
		/*g.printBoard();
		
		g.slide("12");
		
		g.printBoard();
		
		g.slide("11");
		
		g.printBoard();*/
	}
}
