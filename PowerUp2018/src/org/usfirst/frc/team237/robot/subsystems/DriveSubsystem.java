package org.usfirst.frc.team237.robot.subsystems;


import org.usfirst.frc.team237.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class DriveSubsystem extends Subsystem 
{
	// Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private TalonSRX leftDrive = new TalonSRX(RobotMap.driveTalon1);
	private TalonSRX leftDriveSlave = new TalonSRX(RobotMap.driveTalon2);
	private TalonSRX rightDrive = new TalonSRX(RobotMap.driveTalon3);
	private TalonSRX rightDriveSlave = new TalonSRX(RobotMap.driveTalon4);
	private AHRS gyro = new AHRS(SerialPort.Port.kUSB, AHRS.SerialDataType.kProcessedData, (byte) 200);
    
	public DriveSubsystem()
	{
		leftDrive.set(ControlMode.PercentOutput, 0);
		leftDriveSlave.set(ControlMode.Follower, 0);
		rightDrive.set(ControlMode.PercentOutput, 0);
		rightDriveSlave.set(ControlMode.Follower, 0);
		
		gyro.reset();
		
	}
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

