package edu.toronto.csc301.warehouse;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.robot.IGridRobot;
import edu.toronto.csc301.robot.IGridRobot.Direction;

public class PathPlanner implements IPathPlanner {

	/**
	 * TODO: Complete the implementation of this class.
	*/
	
	
	@Override
	public Entry<IGridRobot, Direction> nextStep(IWarehouse warehouse, Map<IGridRobot, GridCell> robot2dest) {
		// TODO Auto-generated method stub
		
		//returns null if robot is already at destination
		for(Entry<IGridRobot, GridCell> g: robot2dest.entrySet()){
			if(g.getKey().getLocation() == robot2dest.get(g.getKey())){
				return null;
			}
		}

		// Return valid next step
		int xRobot, yRobot, xDest, yDest;
		for(Entry<IGridRobot, GridCell> g1: robot2dest.entrySet()){
			xRobot = g1.getKey().getLocation().x;
			yRobot = g1.getKey().getLocation().y;
			xDest = robot2dest.get(g1.getKey()).x;
			yDest = robot2dest.get(g1.getKey()).y;


			// if destination is to the right of robot current location, move EAST (if possible)
			if((xRobot < xDest) && warehouse.getFloorPlan().hasCell(GridCell.at(xRobot+1, yRobot)) && !robotExistsOnGrid(warehouse, GridCell.at(xRobot+1, yRobot))){
				return new AbstractMap.SimpleEntry<IGridRobot, Direction>(g1.getKey(), Direction.EAST);
			}

			// if destination is to the left of robot current location, move WEST (if possible)
			if((xRobot > xDest) && warehouse.getFloorPlan().hasCell(GridCell.at(xRobot-1, yRobot)) && !robotExistsOnGrid(warehouse, GridCell.at(xRobot-1, yRobot))){
				return new AbstractMap.SimpleEntry<IGridRobot, Direction>(g1.getKey(), Direction.WEST);
			}

			// if destination is at the top of robot current location, move NORTH (if possible)
			if((yRobot < yDest) && warehouse.getFloorPlan().hasCell(GridCell.at(xRobot, yRobot+1)) && !robotExistsOnGrid(warehouse, GridCell.at(xRobot, yRobot+1))){
				return new AbstractMap.SimpleEntry<IGridRobot, Direction>(g1.getKey(), Direction.NORTH);
			}

			// if destination is to the bottom of robot current location, move SOUTH (if possible)
			if((yRobot > yDest) && warehouse.getFloorPlan().hasCell(GridCell.at(xRobot, yRobot-1)) && !robotExistsOnGrid(warehouse, GridCell.at(xRobot, yRobot-1))){
				return new AbstractMap.SimpleEntry<IGridRobot, Direction>(g1.getKey(), Direction.SOUTH);
			}

			// Destination exists but,
			if (warehouse.getFloorPlan().hasCell(GridCell.at(xDest, yDest))) {
				// There's no way to move left or right
				if (xRobot < xDest || xRobot > xDest) {
					// move up or down instead
					if (warehouse.getFloorPlan().hasCell(GridCell.at(xRobot, yRobot+1)) && !robotExistsOnGrid(warehouse, GridCell.at(xRobot, yRobot+1))) {//move up
						return new AbstractMap.SimpleEntry<IGridRobot, Direction>(g1.getKey(), Direction.NORTH);
					}
					if (warehouse.getFloorPlan().hasCell(GridCell.at(xRobot, yRobot-1)) && !robotExistsOnGrid(warehouse, GridCell.at(xRobot, yRobot-1))) {// move down
						return new AbstractMap.SimpleEntry<IGridRobot, Direction>(g1.getKey(), Direction.SOUTH);
					}
				}

				// There's no way to move up or down
				if (yRobot < yDest || yRobot > yDest) {
					// Move right or left instead
					if (warehouse.getFloorPlan().hasCell(GridCell.at(xRobot+1, yRobot)) && !robotExistsOnGrid(warehouse, GridCell.at(xRobot+1, yRobot))) {// move right
						return new AbstractMap.SimpleEntry<IGridRobot, Direction>(g1.getKey(), Direction.EAST);
					}
					if (warehouse.getFloorPlan().hasCell(GridCell.at(xRobot-1, yRobot)) && !robotExistsOnGrid(warehouse, GridCell.at(xRobot-1, yRobot))) {// move left
						return new AbstractMap.SimpleEntry<IGridRobot, Direction>(g1.getKey(), Direction.WEST);
					}
				}
			}
		}

		return null;
	}


	private boolean robotExistsOnGrid(IWarehouse warehouse, GridCell g) {
		Iterator<IGridRobot> robots = warehouse.getRobots();

		while(robots.hasNext()) {
			IGridRobot robot = robots.next();
			if(robot.getLocation().x == g.x && robot.getLocation().y == g.y) {
				return true;
			}
		}

		return false;
	}
}
