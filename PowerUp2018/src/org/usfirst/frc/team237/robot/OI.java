package org.usfirst.frc.team237.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI
{
	public static Joystick drivejoystick= new Joystick(0);
	public static Button releaseRamp= new JoystickButton(drivejoystick, 0);
	public static Button intake= new JoystickButton(drivejoystick, 1);
	public static Button outtake= new JoystickButton(drivejoystick, 2);
	public static Button elevatorUp= new JoystickButton(drivejoystick, 3);
	public static Button elevatorDown= new JoystickButton(drivejoystick, 4);
	public static Button climbOn= new JoystickButton(drivejoystick, 5);
	public static Button climbOff= new JoystickButton(drivejoystick, 6);
	
	
}
