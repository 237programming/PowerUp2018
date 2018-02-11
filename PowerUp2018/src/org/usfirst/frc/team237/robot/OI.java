package org.usfirst.frc.team237.robot;

import org.usfirst.frc.team237.robot.commands.HighElevator;
import org.usfirst.frc.team237.robot.commands.LowElevator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI
{
//	public static Joystick driveJoystick = new Joystick(0);
	public static Joystick controller = new Joystick(0);
	
//	PlayStation controller buttons	
//	public static Button intake = new JoystickButton(controller, 1);
//	public static Button outtake = new JoystickButton(controller, 3);
//	public static Button elevatorUp = new JoystickButton(controller, 4);
//	public static Button elevatorDown = new JoystickButton(controller, 2);
	public static Button grabberOpen = new JoystickButton(controller, 5);
	public static Button grabberClose = new JoystickButton(controller, 6);
	public static Button reverseDrive = new JoystickButton(controller, 9);
	public static Button highElevator = new JoystickButton(controller, 4);
	public static Button lowElevator = new JoystickButton(controller, 2);
	
//	Joystick buttons
//	public static Button releaseRamp = new JoystickButton(driveJoystick, 0);
//	public static Button intake = new JoystickButton(driveJoystick, 1);
//	public static Button outtake = new JoystickButton(driveJoystick, 4);
//	public static Button elevatorUp = new JoystickButton(driveJoystick, 3);
//	public static Button elevatorDown = new JoystickButton(driveJoystick, 2);
//	public static Button climbOn = new JoystickButton(driveJoystick, 5);
//	public static Button climbOff = new JoystickButton(driveJoystick, 8);
//	public static Button grabberOpen = new JoystickButton(driveJoystick, 6);
//	public static Button grabberClose = new JoystickButton(driveJoystick, 7);
//	public static Button reverseDrive = new JoystickButton(driveJoystick, 9);
	
//	public OI()
//	{
//		elevatorUp.whenPressed(new ElevatorUp());
//		elevatorDown.whenPressed(new ElevatorDown());
//	}
}
