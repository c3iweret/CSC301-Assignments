package edu.toronto.csc301.grid.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import edu.toronto.csc301.warehouse.*;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;

public class RectangularGrid<T> implements IGrid<T>{
	
	private int width;
	private int height;
	private GridCell gridcell;
	private Set<GridCell> gridCells = new HashSet<GridCell>();
	private LinkedHashMap<GridCell, T> items = new LinkedHashMap<GridCell, T>();
	
	public RectangularGrid(int a, int b, GridCell grid){
		if(grid == null){
			throw new NullPointerException();
		}
		if(a <= 0){
			throw new IllegalArgumentException();
		}
		if(b <= 0){
			throw new IllegalArgumentException();
		}
		height = b;
		width = a;
		gridcell = GridCell.at(grid.x, grid.y);
		Iterator<GridCell> gridCells = getGridCells();
		while (gridCells.hasNext()) {
		  items.put(gridCells.next(), null);
		}
		
	}


	@Override
	public T getItem(GridCell cell) {
		
		if(!items.containsKey(cell)){
	
			throw new IllegalArgumentException();
		}

		else{
		
			return this.items.get(cell);
		}
	} 
	
	public void addItem(GridCell cell, T object){
		this.items.replace(cell, object);
	}

	@Override
	public Iterator<GridCell> getGridCells() {

		for(int x = this.gridcell.x; x < this.gridcell.x + width; x++) {
		  for (int y = this.gridcell.y; y < this.gridcell.y + height; y++) {
		    this.gridCells.add(GridCell.at(x,y));
		  }
		}

		return this.gridCells.iterator();
		
	}
	
	public int getRackCapacity(GridCell cell){
		if(this.getItem(cell) == null){
			return 0;
		}
		return ((Rack)this.getItem(cell)).getCapacity();
	}
	
	public GridCell getOrigin(){
		return this.gridcell;
	}

	@Override
	public boolean hasCell(GridCell cell) {
		
		return this.items.containsKey(cell);
	}
}
