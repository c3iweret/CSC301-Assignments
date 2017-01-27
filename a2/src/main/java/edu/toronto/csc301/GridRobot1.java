package edu.toronto.csc301;

public class GridRobot1 implements IGridRobot{
	
	private IBasicRobot robot;
	private int cellsize;
	 
	
	public GridRobot1(IBasicRobot r, int size){
		
		if (size <= 0){
			throw new IllegalArgumentException();
		}
		else if (r == null){
			throw new NullPointerException();
		}
		if(r.getXCoordinate() % size != 0 || r.getYCoordinate() % size != 0){
			throw new IllegalArgumentException();
		}
		else if(r.getRotation() == 0|| r.getRotation() == 90 || r.getRotation() == 180 || r.getRotation() == 270 ){
			robot = r;
			cellsize = size;
		}
		else{
			throw new IllegalArgumentException();
		}
	}


	@Override
	public GridCell getLocation() {
		return GridCell.at((int)Math.round(this.robot.getXCoordinate()/this.cellsize), (int)Math.round(this.robot.getYCoordinate()/this.cellsize));
	}


	@Override
	public Direction getFacingDirection() {
		if (this.robot.getRotation() == 0 || this.robot.getRotation() == 360){
			return Direction.NORTH;
		}
		else if(this.robot.getRotation() == 90){
			return Direction.EAST;
		}
		else if(this.robot.getRotation() == 180){
			return Direction.SOUTH;
		}
		else {
			return Direction.WEST;
		}
		
	}


	@Override
	public void step(Direction direction) {
		this.face(direction);
		this.robot.moveForward(this.cellsize);
	}


	@Override
	public void face(Direction direction) {
		if(this.robot.getRotation() ==  0 || this.robot.getRotation() == 360){
			if(direction == Direction.EAST){
				this.robot.rotateRight(90);	
			}
			if(direction == Direction.SOUTH){
				this.robot.rotateRight(180);
			}
			if(direction == Direction.WEST){
				this.robot.rotateLeft(90);
			}
		}
		
		else if(this.robot.getRotation() ==  90){
			if(direction == Direction.NORTH){
				this.robot.rotateLeft(90);	
			}
			if(direction == Direction.SOUTH){
				this.robot.rotateRight(90);
			}
			if(direction == Direction.WEST){
				this.robot.rotateLeft(180);
			}
		}
		
		else if(this.robot.getRotation() ==  180){
			if(direction == Direction.NORTH){
				this.robot.rotateLeft(180);	
			}
			if(direction == Direction.EAST){
				this.robot.rotateLeft(90);
			}
			if(direction == Direction.WEST){
				this.robot.rotateRight(90);
			}
		}
		
		else{
			if(direction == Direction.NORTH){
				this.robot.rotateLeft(270);	
			}
			if(direction == Direction.EAST){
				this.robot.rotateRight(180);
			}
			if(direction == Direction.SOUTH){
				this.robot.rotateLeft(90);
			}
		}
	}


	

}
