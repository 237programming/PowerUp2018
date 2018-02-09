package org.usfirst.frc.team237.robot;

import org.usfirst.frc.team237.robot.commands.ElevatorDown;
import org.usfirst.frc.team237.robot.commands.ElevatorUp;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI
{
	public static Joystick driveJoystick = new Joystick(0);
	
	public static Button releaseRamp = new JoystickButton(driveJoystick, 0);
	public static Button intake = new JoystickButton(driveJoystick, 1);
	public static Button outtake = new JoystickButton(driveJoystick, 4);
	public static Button elevatorUp = new JoystickButton(driveJoystick, 3);
	public static Button elevatorDown = new JoystickButton(driveJoystick, 2);
	public static Button climbOn = new JoystickButton(driveJoystick, 5);
	public static Button climbOff = new JoystickButton(driveJoystick, 8);
	public static Button grabberOpen = new JoystickButton(driveJoystick, 6);
	public static Button grabberClose = new JoystickButton(driveJoystick, 7);
	
//	public OI()
//	{
//		elevatorUp.whenPressed(new ElevatorUp());
//		elevatorDown.whenPressed(new ElevatorDown());
//	}
}
