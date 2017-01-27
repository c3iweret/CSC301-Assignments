package edu.toronto.csc301.grid.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;
import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.grid.IGridSerializerDeserializer;

public class RectangularGridSerializerDeserializer implements IGridSerializerDeserializer {
	public RectangularGridSerializerDeserializer(){
		
	}
	
	
	@Override
	public <T> void serialize(IGrid<T> grid, OutputStream output, Function<T, byte[]> item2byteArray)
			throws IOException {
		
		
		OutputStreamWriter serializer = new OutputStreamWriter(output);
		Iterator<GridCell> gridCells = grid.getGridCells();
		int width = 0;
		int height = 0;
		GridCell gc;
		int count = 0;
		int minX = 0, minY = 0, maxX = 0, maxY = 0;
		GridCell origin;
		
		
		//get width and height of grid
		while(gridCells.hasNext()){
			gc = gridCells.next();
			if( count == 0) {
				  minX = gc.x;
				  maxX = gc.x;
				  minY = gc.y;
				  maxY = gc.y;
				}
				if (gc.x < minX) {
				  minX = gc.x;
				}
				if (gc.x > maxX) {
				  maxX = gc.x;
				}
				if (gc.y < minY) {
				  minY = gc.y;
				}
				if (gc.y > maxY) {
				  maxY = gc.y;
				}
				count++;


		}
		
		//initialize width, height and origin of grid
		width = (maxX - minX) + 1;
		height = (maxY - minY) + 1;
		origin = GridCell.at(minX, minY);
		RectangularGrid<T> rect = new RectangularGrid<T>(width, height, origin);
		Iterator<GridCell> iterate = rect.getGridCells();
		GridCell g;
		gridCells = grid.getGridCells();
		
		HashMap<GridCell, T> flexOrRectGrid = new HashMap<GridCell, T>();
		
		//add cells to set
		while (gridCells.hasNext()) {
		  flexOrRectGrid.put(gridCells.next(), null);
		}
		
		//throw exception if cells are incomplete
		while(iterate.hasNext()) {
		 if (!flexOrRectGrid.containsKey(iterate.next())) {
		  throw new IllegalArgumentException();
		 }
		}

		gridCells = grid.getGridCells();
		
		//build sting and write to stream
		String s = "width: "     + width    + "\n" +
			   "height: "     + height   + "\n" +
			   "south-west: " + origin.x + ":"  + origin.y + "\n";
		serializer.write(s);
		
	
		while(gridCells.hasNext()) {
		  g = gridCells.next();
		  if(grid.getItem(g) != null) {
			  String t = "" + g.x + ":" + g.y + " ";
			  serializer.write(t);
			  s = new String(item2byteArray.apply((grid.getItem(g))));
			  s = s + "\n";
			  serializer.write(s);
		  }
		}

		
		serializer.close();
	}


	
	@Override
	public <T> IGrid<T> deserialize(InputStream input, Function<byte[], T> byteArray2item) throws IOException {
		
		String str;
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		int width = 0, height = 0;
		GridCell origin;
		IGrid<T> grid = null;
	
		try {
			  while ((str = reader.readLine()) != null) {
				  String[] parts = str.split(":");
				  if(parts.length == 2){
					  if (parts[0].equals("width")) { // width
					      width = Integer.parseInt(parts[1].trim());
					  }
					  else if(parts[0].equals("height")){
						  height = Integer.parseInt(parts[1].trim());
					  }
					  
					  else{
						  
					  }
				  }
				  else if(parts.length == 3){
					  if(parts[0].equals("south-west")){
						  origin = GridCell.at(Integer.parseInt(parts[1].trim()), Integer.parseInt(parts[2].trim()));
						  grid = new RectangularGrid<T>(width, height, origin);
						  for(int x1 = 0; x1 < width;x1++){
							  for(int y1 = 0; y1 < height; y1++){
								((RectangularGrid<T>) grid).addItem(GridCell.at(x1, y1), null);
							  }
						  }
						  
					  }
					  
					  else{
						  String r[] = str.split(" ");
						  String r1[] = r[0].split(":");
						  int x2 = Integer.parseInt(r1[0].trim());
						  int y2 = Integer.parseInt(r1[1].trim());
						  byte[] temp = r[1].getBytes();
						 
						  ((RectangularGrid<T>) grid).addItem(GridCell.at(x2, y2), byteArray2item.apply(temp));
					  }  
				  } 
				  
				
			  }
			 
			} catch(IOException e) {
			  e.printStackTrace();
			}
		reader.close();
		
		return (IGrid<T>)grid;
	}
}
