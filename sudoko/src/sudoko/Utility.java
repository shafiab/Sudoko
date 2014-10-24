package sudoko;

import java.util.List;

public class Utility {
	
	// print the grid on console
	static void printGrid(String message, int [][]input)
	{
		System.out.println();
		System.out.println(message);
		for (int r=0;r<Constant.NUM_ROW;r++)
		{
			for (int c=0;c<Constant.NUM_COL;c++)
				System.out.print(input[r][c]+" ");
			System.out.println();
		}
		System.out.println();
	}
	
	// find all 20 squares which affect a given square
	static void getUnitPos(int row, int col, int rowUnit[], int colUnit[])
	{
		int counter = 0;
		
		// check for row
		for (int r=0;r<Constant.NUM_ROW;r++)
		{
			if (r!=row)
			{
				rowUnit[counter] = r;
				colUnit[counter] = col;
				counter++;
			}
		}

		// check for column		
		for (int c=0;c<Constant.NUM_COL;c++)
		{
			if (c!=col)
			{
				rowUnit[counter] = row;
				colUnit[counter] = c;
				counter++;
			}
		}

		
		// check for square
		int rB = (row/3)*3;
		int rE = rB+3;

		int cB = (col/3)*3;
		int cE = cB+3;

		for (int r=rB;r<rE;r++)
			for (int c=cB;c<cE;c++)
				if ((r!=row)&(c!=col))
				{
					rowUnit[counter] = r;
					colUnit[counter] = c;
					counter++;
							
				}
	}
	
	// get bubble sort for multiple lists together
	static void bubbleSort(List<Integer> a, List<Integer> index)
	{
		for (int i=0;i<a.size();i++)
			index.add(i);

			
		for (int i=0;i<a.size();i++)
			for (int j=0;j<a.size()-1;j++)
				if (a.get(j)>a.get(j+1))
				{
					swap(j,j+1,a);
					//swap(j,j+1,b);
					//swap(j,j+1,c);
					swap(j,j+1,index);
				}
	}
	
	static void swap(int i, int j, List<Integer> b)
	{
		int temp = b.get(i);
		b.set(i, b.get(j));
		b.set(j, temp);
	}
	
	
	static double numPossibleCombination(List<Integer> numPosDigitsEmptySquare)
	{
		double numCombination = 1.0;
		for (int i=0;i<numPosDigitsEmptySquare.size();i++)
			numCombination*=numPosDigitsEmptySquare.get(i);
		return numCombination;
	}

}
