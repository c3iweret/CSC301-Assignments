package edu.toronto.csc301.grid.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;

public class FlexGrid<T> implements IGrid<T>{
	Set<GridCell> gridCells = new HashSet<GridCell>();
	HashMap<GridCell, T> items = new HashMap<GridCell, T>();
	public FlexGrid(){
	
	}

	@Override
	public T getItem(GridCell cell) {
		
		if (!(items.containsKey(cell))){
			throw new IllegalArgumentException();
		}
		return items.get(cell);
	}
	
	public void addItem(GridCell cell, T rack){
		items.put(cell, rack);
		gridCells.add(cell);
	}

	@Override
	public Iterator<GridCell> getGridCells() {
		
		
		return gridCells.iterator();
	}

	@Override
	public boolean hasCell(GridCell cell) {
		
		return gridCells.contains(cell);
	}
}
