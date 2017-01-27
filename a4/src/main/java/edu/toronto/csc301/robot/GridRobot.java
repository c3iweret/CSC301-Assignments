package edu.toronto.csc301.robot;

import java.util.ArrayList;

import edu.toronto.csc301.grid.GridCell;

public class GridRobot implements IGridRobot{
	
	private GridCell gridcell;
	private ArrayList<StepListener> listeners;

	public GridRobot(GridCell grid){
		if (grid == null){
			throw new NullPointerException();
		}
		this.gridcell = grid;
		listeners = new ArrayList<StepListener>();
	}


	@Override
	public GridCell getLocation() {
		return this.gridcell;
	}

	@Override
	public void step(Direction direction) {
		for (StepListener listener: listeners){ 
			listener.onStepStart(this, direction);
		}

		if(direction == Direction.NORTH){
			this.gridcell = GridCell.at(this.gridcell.x, this.gridcell.y + 1);
		}
		else if(direction == Direction.SOUTH){
			this.gridcell = GridCell.at(this.gridcell.x, this.gridcell.y - 1);
		}
		else if(direction == Direction.EAST){
			this.gridcell = GridCell.at(this.gridcell.x + 1, this.gridcell.y);
		}
		else{
			this.gridcell = GridCell.at(this.gridcell.x - 1, this.gridcell.y);
		}
	
		for (StepListener listener: listeners){ 
			listener.onStepEnd(this, direction);
		}
		
	}

	@Override
	public void startListening(StepListener listener) {
		listeners.add(listener);

		
	}

	@Override
	public void stopListening(StepListener listener) {
		listeners.remove(listener);
	}

}
