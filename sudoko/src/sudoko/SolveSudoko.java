package sudoko;
import sudoko.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolveSudoko {
	int numTry = 0;
	int [] pos = new int[3];
	int [] rowUnit = new int[Constant.NUM_UNIT];
	int [] colUnit = new int[Constant.NUM_UNIT];

	List<Integer> indexAscendingEmptySquare = new ArrayList<Integer>();
	List<Integer> numPosDigitsEmptySquare = new ArrayList<Integer>();
	List<Integer> indxRowEmptySquare = new ArrayList<Integer>();
	List<Integer> indxColEmptySquare = new ArrayList<Integer>();
	List<List<Integer>> possibleDigitsEmptySquare = new ArrayList<List<Integer>>();
	
	// solve sudoko using modified backtracking method
	boolean solveSudoko(int [][] input) 
	{
		if (!findEmptySquare(input, pos))
			return true;
		
		int row = pos[0];
		int col = pos[1];
		int index = pos[2];

		for (int num : possibleDigitsEmptySquare.get(index))
		{
			if (isDigitPossible(input, row, col, num))
			{
				input[row][col] = num;
				numTry++;
				if (solveSudoko(input))
					return true;
				input[row][col] = 0; // if digit is not possible, then backtrack				
			}			
		}
		return false;
	}
	
	// find an empty square
	boolean findEmptySquare(int [][] input, int[] pos)
	{
		int row, col;
		// just go through the entries in numPosDigitsEmptySquare instead of the entire grid
		for (int idx : indexAscendingEmptySquare)
		{
			row = indxRowEmptySquare.get(idx);
			col = indxColEmptySquare.get(idx);
			if (input[row][col]==0)
			{
				pos[0] = row;
				pos[1] = col;
				pos[2] = idx;
				return true;
			}
			
		}
		return false;		
	}
	
	// check if a digit is possible in a given square
	boolean isDigitPossible(int [][] input, int row, int col, int num)
	{
		Utility.getUnitPos(row, col, rowUnit, colUnit); // get all square within the units		
		boolean isPossible = true;			
		for (int counter=0;counter<Constant.NUM_UNIT;counter++)
		{
			if (input[rowUnit[counter]][colUnit[counter]] == num)
			{
				isPossible = false;
				break;
			}
		}
		return isPossible;
	}


	// initialize function, this function optimize the backtracking operation
	// by finding the squares with the minimum number of possible digits
	void initialize(int[][]input)
	{
		for (int r=0;r<Constant.NUM_ROW;r++)		
		{
			for (int c=0;c<Constant.NUM_COL;c++)
			{
				if (input[r][c]==0)
				{
					List<Integer> possibleDigits = new ArrayList<Integer>();
					
					// initially all digits are possible
					boolean[] isDigitPossible = new boolean[Constant.NUM_DIGIT];
					Arrays.fill(isDigitPossible, true);
					
					// now go through all the squares within 
					// the unit to filter out impossible digits for that square
					Utility.getUnitPos(r, c, rowUnit, colUnit);					
					for (int counter=0;counter<Constant.NUM_UNIT;counter++)
					{
						int tempDigit = input[rowUnit[counter]][colUnit[counter]];
						if (tempDigit!=0)
							isDigitPossible[tempDigit-1] = false;
					}

					// count the number of possible digits
					int count = 0;
					for (int counter=0;counter<Constant.NUM_DIGIT;counter++)
					{
						if (isDigitPossible[counter])
						{
							count++;
							possibleDigits.add(counter+1);
						}
					}
					
					
					if (count==1) // if only one digit is possible, then this is final
						input[r][c] = possibleDigits.get(0);
					else // otherwise keep track of number of possible digits and empty squares
					{
						numPosDigitsEmptySquare.add(count);
						indxRowEmptySquare.add(r);
						indxColEmptySquare.add(c);
						possibleDigitsEmptySquare.add(possibleDigits);
					}					
				}
			}
		}
		// once we determined all the empty squares and number of possible digits,
		// we sort them from smallest to largest
		Utility.bubbleSort(numPosDigitsEmptySquare, indexAscendingEmptySquare);
		System.out.println("maximum number of combination to try : "+String.format("%.0f", Utility.numPossibleCombination(numPosDigitsEmptySquare)));
	}
}
