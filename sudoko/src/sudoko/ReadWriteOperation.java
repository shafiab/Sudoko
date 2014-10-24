package sudoko;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class ReadWriteOperation {
	
	@SuppressWarnings("resource")
	void getInputMatrix(int [][]sudokoTable, final String ADDRESS_FILE) throws IOException
	{
		// open csv reader
		CSVReader myReader = new CSVReader(new FileReader(ADDRESS_FILE));
		
		// read input file as list of strings
		List<String[]> myList = myReader.readAll();

		if (myList.size()<Constant.NUM_ROW)
			throw new IOException("number of columns are less than NUM_COL");
		
		myReader.close();
		
		// convert to 2D integer array				
		for (int row=0;row<Constant.NUM_ROW;row++)
		{
			String[] tempStr = myList.get(row);
			for (int col=0;col<Constant.NUM_COL;col++)
			{
				sudokoTable[row][col] = Integer.parseInt(tempStr[col].trim());				
			}
		}	
		
	}
	
	void writeOutputMatrix(int [][]sudokoTable, final String OUTPUT_FILE) throws IOException
	{
		// open csv write
	    CSVWriter myWriter = new CSVWriter(new FileWriter(OUTPUT_FILE), ',', CSVWriter.NO_QUOTE_CHARACTER);
	    
	    // feed in your array (or convert your data to an array)
	    for (int row=0;row<Constant.NUM_ROW;row++)
	    {
	    	String[] entries = new String[Constant.NUM_COL];
	    	for (int col=0;col<Constant.NUM_COL;col++)
	    	{
	    		entries[col] = String.valueOf(sudokoTable[row][col]);
	    	}
	    	myWriter.writeNext(entries);
	    }	    
	    
	    myWriter.close();
	               
	}

}
