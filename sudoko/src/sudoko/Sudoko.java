package sudoko;
import java.io.IOException;
import sudoko.SolveSudoko;
import sudoko.Constant;

public class Sudoko {
	private static final String INPUT_FILE = "src/Input/inputMatrix.csv"; // input file name and location
	//private static final String INPUT_FILE = "src/Input/inputHard.csv"; // input file name and location
	private static final String OUTPUT_FILE = "src/Output/outputMatrix.csv"; // output file name and location

	public static void main(String[] args) throws IOException
	{
		int[][] sudokoTable = new int[Constant.NUM_COL][Constant.NUM_ROW]; // input table
		
		// read csv file into input table
		ReadWriteOperation myReadWriteOp = new ReadWriteOperation(); 		
		myReadWriteOp.getInputMatrix(sudokoTable, INPUT_FILE);
		
		// solve sudoko
		SolveSudoko mySolver = new SolveSudoko();		
		Utility.printGrid("Input Grid : ", sudokoTable);
				
		mySolver.initialize(sudokoTable);
		mySolver.solveSudoko(sudokoTable);
				
		System.out.println("number of combination attempted : "+mySolver.numTry);
		Utility.printGrid("Solved Grid : ",sudokoTable);		
		
		// write output table into csv file
		myReadWriteOp.writeOutputMatrix(sudokoTable, OUTPUT_FILE);
	}
	

}
