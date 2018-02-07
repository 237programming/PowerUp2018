package org.usfirst.frc.team237.robot;

public class RobotMap 
{
	//drive shit
	public static int driveTalon1 = 1;
	public static int driveTalon2 = 2;
	public static int driveTalon3 = 3;
	public static int driveTalon4 = 4;

	//cubeHandler shit
	public static int intake1 = 9;
	public static int intake2 = 10;
	public static int elevator1 = 11;
	public static double deadband = 0.3;
	public static double elevatorMax = 10000;
	public static double elevatorMin = 0;

	//climber shit
	public static int climber = 12;
	
	//solenoid shit
	public static int solenoidCAN = 51;
	public static int grabber = 1;
	
	//PID Values
	public static double turnP = 0.1;
	public static double turnI = 0.0;
	public static double turnD = 0.1;
	public static double driveP = 0.05;
	public static double driveI = 0.001;
	public static double driveD = 0.0;
	
	
}
