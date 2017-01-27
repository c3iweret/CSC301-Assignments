package edu.toronto.csc301;

public class GridRobot2 extends BasicRobot implements IGridRobot {
	private GridCell gridcell;
	private int cellsize;
	private Direction d;

	public GridRobot2(GridCell grid, Direction direction, int size){
		super(grid.x * size, grid.y * size, 0);
		if(direction == Direction.EAST) {
			super.rotateRight(90);
		}
		if(direction == Direction.SOUTH) {
			super.rotateRight(180);
		}
		if(direction == Direction.WEST) {
			super.rotateLeft(90);
		}
		d = direction;
		cellsize = size;
		gridcell = grid;
		
		
		
		if(grid == null || direction == null){
			throw new NullPointerException();
		}
		
		if (size <= 0){
			throw new IllegalArgumentException();
		}
		
	}
	
	
	@Override
	public GridCell getLocation() {
		return this.gridcell;
	}

	@Override
	public Direction getFacingDirection() {
		return this.d;
	}

	@Override
	public void step(Direction direction) {
		this.face(direction);
		this.moveForward(this.cellsize);
	}

	@Override
	public void face(Direction direction) {
		if(super.getRotation() ==  0 || super.getRotation() == 360){
			if(direction == Direction.EAST){
				super.rotateRight(90);	
			}
			if(direction == Direction.SOUTH){
				super.rotateRight(180);
			}
			if(direction == Direction.WEST){
				super.rotateLeft(90);
			}
			
			
		}
		
		else if(super.getRotation() ==  90){
			if(direction == Direction.NORTH){
				super.rotateLeft(90);	
			}
			if(direction == Direction.SOUTH){
				super.rotateRight(90);
			}
			if(direction == Direction.WEST){
				super.rotateLeft(180);
			}
		}
		
		else if(super.getRotation() ==  180){
			if(direction == Direction.NORTH){
				super.rotateLeft(180);	
			}
			if(direction == Direction.EAST){
				super.rotateLeft(90);
			}
			if(direction == Direction.WEST){
				super.rotateRight(90);
			}
		}
		
		else{
			if(direction == Direction.NORTH){
				super.rotateLeft(270);	
			}
			if(direction == Direction.EAST){
				super.rotateRight(180);
			}
			if(direction == Direction.SOUTH){
				super.rotateLeft(90);
			}
		}
		
		this.updateDirection();
	}
	@Override
	public void rotateLeft(int degrees) {
		if(degrees % 90 != 0){
			throw new IllegalArgumentException();
		}
		super.rotateLeft(degrees);
		this.updateDirection();
	}
	
	@Override
	public void rotateRight(int degrees) {
		if(degrees % 90 != 0){
			throw new IllegalArgumentException();
		}
		super.rotateRight(degrees);
		this.updateDirection();
	}
	
	//updates direction of gridrobot according to rotation
	private void updateDirection(){
		int rotation = super.getRotation();
		if (rotation == 0 || rotation == 360) {
			this.d = Direction.NORTH;
		}
		if (rotation == 90) {
			this.d = Direction.EAST;
		}
		if (rotation == 180) {
			this.d = Direction.SOUTH;
		}
		if (rotation == 270) {
			this.d = Direction.WEST;
		}
	}
	
	@Override
	public void moveForward(int centimeters) {
		super.moveForward(centimeters);
		if(Math.abs((int)Math.round(super.getXCoordinate() % this.cellsize)) != 0 || Math.abs((int)Math.round(super.getYCoordinate()) % this.cellsize) != 0){
			super.moveForward(-centimeters);
			throw new IllegalArgumentException();
		}
		this.gridcell = GridCell.at((int)Math.round(super.getXCoordinate()/cellsize), (int)Math.round(super.getYCoordinate()/cellsize));
	}

}
