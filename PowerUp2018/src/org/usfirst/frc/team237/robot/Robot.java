/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.     
//Hello this be took                   */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team237.robot;

import org.usfirst.frc.team237.robot.commands.AutonomousCenterLeft;
import org.usfirst.frc.team237.robot.commands.AutonomousCenterRight;
import org.usfirst.frc.team237.robot.commands.AutonomousLeftLeft;
import org.usfirst.frc.team237.robot.commands.AutonomousLeftRight;
import org.usfirst.frc.team237.robot.commands.AutonomousRightLeft;
import org.usfirst.frc.team237.robot.commands.AutonomousRightRight;
import org.usfirst.frc.team237.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team237.robot.subsystems.CubeHandlerSubsystem;
import org.usfirst.frc.team237.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot 
{
	public static OI oi;
	public static DriveSubsystem driveTrain = new DriveSubsystem();
	public static ClimberSubsystem climber = new ClimberSubsystem();
	public static CubeHandlerSubsystem cubeHandler = new CubeHandlerSubsystem();
	public static PowerDistributionPanel PDP = new PowerDistributionPanel(50);
	private boolean previousButtonState = false;
	private boolean driveState = true;
//	private String m_autoSelected;
	Command autonomousCommand;
//	SendableChooser<Command> configChooser;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() 
	{
		oi = new OI();
//		m_chooser.addDefault("Default Auto", kDefaultAuto);
//		m_chooser.addObject("My Auto", kCustomAuto);
//		m_chooser = new SendableChooser<Command>();
//		m_chooser.addDefault("Center Left", new AutonomousCenterLeft());
//		SmartDashboard.putData("Auto choices", m_chooser);
//		configChooser = new SendableChooser<Command>();
//		configChooser.addDefault("Right Left", new AutonomousRightLeft());
//		configChooser.addObject("Other", new AutonomousRightRight());
//		SmartDashboard.putData("Starting Position", configChooser);
		
		cubeHandler.zeroEnc();
		driveTrain.zeroEnc();
		driveTrain.zeroYaw();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() 
	{
//		m_autoSelected = m_chooser.getSelected();
//		m_autoSelected = SmartDashboard.getString("Auto Selector",
//		 		kDefaultAuto);
		cubeHandler.zeroEnc();
		driveTrain.zeroYaw();
		String gameData;
//		if(configChooser.getSelected().equals(right))
//		{
			gameData = DriverStation.getInstance().getGameSpecificMessage();
                if(gameData.length() > 0)
                {
                	if(gameData.charAt(0) == 'L')
                		autonomousCommand = new AutonomousRightLeft();
                	else 
                		autonomousCommand = new AutonomousRightRight();
                }
//		}
		
//		if(configChooser.getSelected().equals(center))
//		{
//			gameData = DriverStation.getInstance().getGameSpecificMessage();
//                if(gameData.length() > 0)
//                {
//                	if(gameData.charAt(0) == 'L')
//                	{
//                		autonomousCommand = new AutonomousCenterLeft();
//                	} 
//                	else 
//                	{
//                		autonomousCommand = new AutonomousCenterRight();
//                	}
//                }
//		}
		
//		if(configChooser.getSelected().equals(left))
//		{
//			gameData = DriverStation.getInstance().getGameSpecificMessage();
//                if(gameData.length() > 0)
//                {
//                	if(gameData.charAt(0) == 'L')
//                	{
//                		autonomousCommand = new AutonomousLeftLeft();
//                	} 
//                	else 
//                	{
//                		autonomousCommand = new AutonomousLeftRight();
//                	}
//                }
//		}
//		autonomousCommand = (Command) configChooser.getSelected();
		if(autonomousCommand != null) 
			autonomousCommand.start();
		
//		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() 
	{
		Scheduler.getInstance().run();
//		switch (m_autoSelected) 
//		{
//			case kCustomAuto:
//				// Put custom auto code here
//				break;
//			case kDefaultAuto:
//			default:
				// Put default auto code here
//				break;
//		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() 
	{
		Scheduler.getInstance().run();
		
		SmartDashboard.putBoolean("Reverse Drive", driveState);
		
		driveTrain.setDrives(OI.driveJoystick.getY(), OI.driveJoystick.getX());
		
		if(OI.controller.getRawAxis(1) < -.5)
			cubeHandler.upElevator();
		else if(OI.controller.getRawAxis(1) > .5)
			cubeHandler.downElevator();
		else
			cubeHandler.offElevator();
		
		//Check for press of reverse drive button
		if(OI.reverseDrive.get() == true)
		{
			//Was it already pressed?
			if(previousButtonState == false)
			{
				//if not, change state
				if(driveState == true)
					driveState = false;
				else
					driveState = true;
			}
			//set current state of button
			previousButtonState = true;
		}	
		else
			previousButtonState = false;
			
		// if drive state reversed, reverse controls
		if(driveState == false)	
			driveTrain.reverseDrive(OI.driveJoystick.getY(), OI.driveJoystick.getX());

		else
			driveTrain.setDrives(OI.driveJoystick.getY(), OI.driveJoystick.getX());

		if(OI.controller.getRawAxis(4) > .5)
			cubeHandler.fowardIntake();
		else if(OI.controller.getRawAxis(3) > .5)
			cubeHandler.backwardIntake();
		else
			cubeHandler.offIntake();
		
		if(OI.grabberOpen.get() == true)
			cubeHandler.actuate(true);
		if(OI.grabberClose.get() == true)
			cubeHandler.actuate(false);	
		
		if(OI.startHang.get() == true)
			climber.startHang(true);
		else
			climber.startHang(false);
		
		cubeHandler.cubeSensor();
		
		driveTrain.post();
		cubeHandler.post();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() 
	{
	
	}
}
