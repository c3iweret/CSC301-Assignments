package edu.toronto.csc301.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.robot.GridRobot;
import edu.toronto.csc301.robot.IGridRobot;
import edu.toronto.csc301.robot.IGridRobot.Direction;

public class Warehouse implements IWarehouse{
	private Map<IGridRobot, Direction> movingrobots = new HashMap<IGridRobot, Direction>();
	private Set<IGridRobot> robots = new HashSet<IGridRobot>();
	private IGrid<Rack> grid;
	private ArrayList<Consumer<IWarehouse>> observers;
	private WarehouseListener listener; 

	public Warehouse(IGrid<Rack> g){
		if(g == null){
			throw new NullPointerException();
		}
		
		this.grid = g;	
		this.listener = new WarehouseListener();
		this.observers = new ArrayList<Consumer<IWarehouse>>();
	}

	@Override
	public IGrid<Rack> getFloorPlan() {
		return this.grid;
	}
	
	@Override
	public IGridRobot addRobot(GridCell initialLocation) {
		if(sameLocation(initialLocation)){
			throw new IllegalArgumentException();
		}
		if(!this.grid.hasCell(initialLocation)){
			throw new IllegalArgumentException();
		}
		IGridRobot robot = new GridRobot(initialLocation);
		this.robots.add(robot);
		
		//trigger listeners
		for (Consumer<IWarehouse> observer: observers) { 
			  observer.accept(this); // notify observers warehouse object has changed
		}
		
		robot.startListening(this.listener);

		return robot;
	}
	
	public boolean sameLocation(GridCell location){
		IGridRobot robot = new GridRobot(location);
		Iterator<IGridRobot> robots = this.getRobots();
		IGridRobot g;
		boolean result = false;
		while(robots.hasNext()){
			g = robots.next();
			if (g.getLocation() == robot.getLocation()){
				return true;
			}
		}
		return result;
	}
	
	@Override
	public Iterator<IGridRobot> getRobots() {
		return this.robots.iterator();
	}

	@Override
	public Map<IGridRobot, Direction> getRobotsInMotion() {
		Map<IGridRobot, Direction> movingrobotscopy = new HashMap<IGridRobot, Direction>();
		for(IGridRobot g: this.movingrobots.keySet() ){
			movingrobotscopy.put(g, this.movingrobots.get(g));
			
		}
		return movingrobotscopy;
	}

	@Override
	public void subscribe(Consumer<IWarehouse> observer) {
		observers.add(observer);

		
	}

	@Override
	public void unsubscribe(Consumer<IWarehouse> observer) {
		observers.remove(observer);

	}
	

	private class WarehouseListener implements IGridRobot.StepListener {
		
		public WarehouseListener(){
		}
		
		@Override
		public void onStepStart(IGridRobot robot, Direction direction) {
			Warehouse.this.movingrobots.put(robot, direction);
			for (Consumer<IWarehouse> observer: observers) {
				observer.accept(Warehouse.this);
			}
		}

		@Override
		public void onStepEnd(IGridRobot robot, Direction direction) {
			Warehouse.this.movingrobots.remove(robot, direction);
			for (Consumer<IWarehouse> observer: observers) {
				observer.accept(Warehouse.this);
			}
		}

}


}
