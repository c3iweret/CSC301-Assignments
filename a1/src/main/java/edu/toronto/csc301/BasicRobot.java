package edu.toronto.csc301;
import java.lang.IllegalArgumentException;
import java.lang.Math;
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
	@Override
	public double getXCoordinate() {
		// TODO Auto-generated method stub
		return this.X_coordinate;
	}

	@Override
	public double getYCoordinate() {
		// TODO Auto-generated method stub
		return this.Y_coordinate;
	}

	@Override
	public int getRotation() {
		// TODO Auto-generated method stub
		return this.rotation;
	}

	@Override
	public void rotateRight(int degrees) {
		// TODO Auto-generated method stub
		if (degrees < 0) {//Left degree rotation
			while(degrees <= -360) {//retrieve single rotation angle value
				degrees += 360;
			}
			this.rotation = degrees + 360; //equivalent positive angle for the negative rotation
		} else {// Right degree rotation
			while (degrees >= 360) {//retrieve single rotation angle value
				degrees -= 360;
			}
			this.rotation = this.rotation + degrees;
		}
	}

	@Override
	public void rotateLeft(int degrees) {
		// TODO Auto-generated method stub
		if (degrees < 0) {//right degree rotation
			while(degrees <= -360) {//retrieve single rotation angle value
				degrees += 360;
			}
			this.rotation = -degrees; //equivalent positive angle for the negative rotation
		} else {// Right degree rotation
			while (degrees >= 360) {//retrieve single rotation angle value
				degrees -= 360;
			}
			//this.rotation = (360 - degrees);//equivalent angle in regular coordinate rotation
			this.rotation = 360 + (this.rotation - degrees);
		}
		
	}

	@Override
	public void moveForward(int millimeters) {
		// TODO Auto-generated method stub
		this.X_coordinate = this.X_coordinate + (Math.sin(Math.toRadians(this.rotation)) * millimeters);
		this.Y_coordinate = this.Y_coordinate + (Math.cos(Math.toRadians(this.rotation)) * millimeters);
	}

}
