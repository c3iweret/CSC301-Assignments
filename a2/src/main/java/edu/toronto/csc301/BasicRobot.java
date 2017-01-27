package edu.toronto.csc301;


public class BasicRobot implements IBasicRobot {
	
	private double X_coordinate;
	private double Y_coordinate;
	private int rotation;

	public BasicRobot(double X, double Y, int rot) throws IllegalArgumentException{
		X_coordinate = X;
		Y_coordinate = Y;
		if (rot < 0 || rot >= 360){
			throw new IllegalArgumentException();
		}
		else{
			rotation = rot;
		}
	}
	
	public BasicRobot(){
		
	}
	
	@Override
	public double getXCoordinate() {
		return this.X_coordinate;
	}

	@Override
	public double getYCoordinate() {
		return this.Y_coordinate;
	}

	@Override
	public int getRotation() {
		return this.rotation;
	}

	@Override
	public void rotateRight(int degrees) {
		if (degrees < 0) {
			while(degrees <= -360) {
				degrees += 360;
			}
			degrees = this.rotation + degrees;
			if(degrees >= 0){
				this.rotation = degrees;
			}
			else{
				this.rotation = degrees + 360;
			}
		} else {
			while (degrees >= 360) {
				degrees -= 360;
			}
			this.rotation = this.rotation + degrees;
			if(this.rotation > 360){
				this.rotation = this.rotation - 360;
			}
		}
		
	}

	@Override
	public void rotateLeft(int degrees) {
		if (degrees < 0) {
			while(degrees <= -360) {
				degrees += 360;
			}
			degrees = -degrees;
			degrees += this.rotation;
			if(degrees <= 360){
				this.rotation = degrees;
			}
			else{
				this.rotation = degrees - 360;
			}
		} else {
			while (degrees >= 360) {
				degrees -= 360;
			}
			degrees = this.rotation - degrees;
			if(degrees >= 0){ 
				this.rotation = degrees;
			}
			else{
				 this.rotation = degrees + 360;
			}
			
		}
		
	}

	@Override
	public void moveForward(int centimeters) {
		this.X_coordinate = this.X_coordinate + Math.sin(Math.toRadians(this.rotation)) * centimeters;
		this.Y_coordinate = this.Y_coordinate + Math.cos(Math.toRadians(this.rotation)) * centimeters;
	}
	

}
