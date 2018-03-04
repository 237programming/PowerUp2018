package org.usfirst.frc.team237.robot;

import org.usfirst.frc.team237.robot.commands.ElevatorDown;
import org.usfirst.frc.team237.robot.commands.ElevatorUp;
import org.usfirst.frc.team237.robot.commands.ExchangeElevator;
import org.usfirst.frc.team237.robot.commands.HighElevator;
import org.usfirst.frc.team237.robot.commands.LowElevator;
import org.usfirst.frc.team237.robot.commands.SwitchElevator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI
{
	public static Joystick controller 		= new Joystick(0);
	public static Joystick driveJoystick 	= new Joystick(1);
	public static Joystick panel			= new Joystick(2);
	
//	PlayStation controller buttons	
	public static Button grabberOpen 		= new JoystickButton(controller, 5);
	public static Button grabberClose 		= new JoystickButton(controller, 6);
//	public static Button highElevator 		= new JoystickButton(controller, 4);
//	public static Button switchElevator 	= new JoystickButton(controller, 1);
//	public static Button exchangeElevator   = new JoystickButton(controller, 3);
//	public static Button lowElevator 		= new JoystickButton(controller, 2);
//	public static Button elevatorUp 		= new JoystickButton(controller, 9); 
//	public static Button elevatorDown 		= new JoystickButton(controller, 10); 
	
//  Panel buttons
	public static Button hang				= new JoystickButton(panel, 1);
	public static Button flipUp				= new JoystickButton(panel, 3);
	public static Button flipDown			= new JoystickButton(panel, 2);
	
//	Joystick buttons
	public static Button reverseDrive 		= new JoystickButton(driveJoystick, 3);
	
	public OI()
	{
//		highElevator.whenPressed(new HighElevator());
//		switchElevator.whenPressed(new SwitchElevator());
//		exchangeElevator.whenPressed(new ExchangeElevator());
//		lowElevator.whenPressed(new LowElevator());
//		elevatorUp.whenPressed(new ElevatorUp());
//		elevatorDown.whenPressed(new ElevatorDown());
	}
}
