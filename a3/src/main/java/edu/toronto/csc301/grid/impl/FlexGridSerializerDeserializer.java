package edu.toronto.csc301.grid.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.function.Function;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.grid.IGridSerializerDeserializer;

public class FlexGridSerializerDeserializer implements IGridSerializerDeserializer {
	public FlexGridSerializerDeserializer(){
		
	}

	@Override
	public <T> void serialize(IGrid<T> grid, OutputStream output, Function<T, byte[]> item2byteArray)
			throws IOException {
		
		OutputStreamWriter serializer = new OutputStreamWriter(output);
		Iterator<GridCell> gridCells = grid.getGridCells();
		GridCell g;
		
		while(gridCells.hasNext()) {
			
		  g = gridCells.next();
		  if(grid.getItem(g) != null) {
			  String t = "" + g.x +":" + g.y + " ";
			  serializer.write(t);
			  String s = new String(item2byteArray.apply((grid.getItem(g))));
			  s = s + "\n";
			  serializer.write(s);  
		  }
		  else{
			  String t = "" + g.x  + ":" + g.y + "\n";
			  serializer.write(t);
		  }
		}
		
		

		
		serializer.close();
		
	}

	
	@Override
	public <T> IGrid<T> deserialize(InputStream input, Function<byte[], T> byteArray2item) throws IOException {
		
		String str;
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		@SuppressWarnings("unchecked")
		IGrid<T> grid = (IGrid<T>) new FlexGrid<Object>();
		
		try {
			  while ((str = reader.readLine()) != null) {
			    String[] line = str.split(":");
			    if(line.length == 2){
			    	if(line[0].equals("weight") || line[0].equals("height")){
			    		
			    	}
			    	else{
			    		GridCell flexgrid = GridCell.at(Integer.parseInt(line[0].trim()), Integer.parseInt(line[1].trim()));
			    		((FlexGrid<T>) grid).addItem(flexgrid, null);
			    	}
			    }
			    else if(line.length == 3){
			    	if (line[0].equals("south-west")){
			    		GridCell flexgrid = GridCell.at(Integer.parseInt(line[1].trim()), Integer.parseInt(line[2].trim()));
			    		((FlexGrid<T>) grid).addItem(flexgrid, null);	
			    	}
			    	else{
				    	String s[] = str.split(" ");
				    	String t[] = s[0].split(":");
				    	GridCell flexgrid = GridCell.at(Integer.parseInt(t[0].trim()), Integer.parseInt(t[1].trim()));
				    	byte[] temp = s[1].trim().getBytes();
				    	((FlexGrid<T>) grid).addItem(flexgrid, byteArray2item.apply(temp));	
			    	}
			    }
			    else{
			    	
			    }
			  }
			 
			} catch(IOException e) {
			  e.printStackTrace();
			}
		
		reader.close();
		
		return grid;
	
	}
}
