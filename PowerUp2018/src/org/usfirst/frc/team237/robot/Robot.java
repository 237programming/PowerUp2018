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
import org.usfirst.frc.team237.robot.commands.AutonomousRightRight2;
import org.usfirst.frc.team237.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team237.robot.subsystems.CubeHandlerSubsystem;
import org.usfirst.frc.team237.robot.subsystems.DriveSubsystem;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
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
	private DigitalInput leftStart = new DigitalInput(1);
	private DigitalInput rightStart = new DigitalInput(2);
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
		
		Robot.climber.hangerRelease(false);
		
//		UsbCamera cam = CameraServer.getInstance().startAutomaticCapture();
//		cam.setResolution(1280, 720);
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
//		climber.enableCompressor(false);
		Robot.driveTrain.setRampRate(0);
		
		String gameData;
		if(rightStart.get() && !leftStart.get())
		{
			System.out.println("Running RightAutos");
			gameData = DriverStation.getInstance().getGameSpecificMessage();
                if(gameData.length() > 0)
                {
                	if(gameData.charAt(0) == 'L')
                		autonomousCommand = new AutonomousRightLeft();
                	else 
                		autonomousCommand = new AutonomousRightRight();
                }
		}
		else if(leftStart.get() && !rightStart.get())
		{
        	System.out.println("Running LeftAuto");
			gameData = DriverStation.getInstance().getGameSpecificMessage();
                if(gameData.length() > 0)
                {
                	if(gameData.charAt(0) == 'L')
                	{
                		autonomousCommand = new AutonomousLeftLeft();
                	} 
                	else 
                	{
                		autonomousCommand = new AutonomousLeftRight();
                	}
                }
		}
		else
		{
		System.out.println("Running CenterAuto");
		gameData = DriverStation.getInstance().getGameSpecificMessage();
            if(gameData.length() > 0)
            {
            	if(gameData.charAt(0) == 'L')
            	{
            		autonomousCommand = new AutonomousCenterLeft();
            	} 
            	else 
            	{
            		autonomousCommand = new AutonomousCenterRight();
            	}
            }
		}
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
		Robot.driveTrain.post();
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
	
	@Override
	public void teleopInit()
	{
//		climber.enableCompressor(true);
		Robot.driveTrain.disableRotateTo();
    	Robot.driveTrain.setDrives(0, 0);
    	Robot.cubeHandler.offElevator();
    	Robot.cubeHandler.offIntake();
    	Robot.driveTrain.setRampRate(0);
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
		
		if(OI.flipUp.get() == true)
			climber.startHang(true);
		if(OI.flipDown.get() == true)
			climber.startHang(false);
		
		if(OI.hang.get() == true)
			climber.hangerRelease(true);
		else
			climber.hangerRelease(false);
		
		cubeHandler.cubeSensor();
		
		driveTrain.post();
		cubeHandler.post();
	}
	
	@Override
	public void disabledPeriodic()
	{
		Robot.driveTrain.post();
		Robot.cubeHandler.post();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() 
	{
	
	}
}
