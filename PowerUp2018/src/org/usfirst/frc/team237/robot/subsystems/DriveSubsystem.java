package org.usfirst.frc.team237.robot.subsystems;


import org.usfirst.frc.team237.robot.RobotMap;
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
	private TalonSRX leftDriveSlave1 = new TalonSRX(RobotMap.driveTalon2);
	private TalonSRX leftDriveSlave2 = new TalonSRX(RobotMap.driveTalon3);
	private TalonSRX leftDriveSlave3 = new TalonSRX(RobotMap.driveTalon4);
	private TalonSRX rightDrive = new TalonSRX(RobotMap.driveTalon5);
	private TalonSRX rightDriveSlave1 = new TalonSRX(RobotMap.driveTalon6);
	private TalonSRX rightDriveSlave2 = new TalonSRX(RobotMap.driveTalon7);
	private TalonSRX rightDriveSlave3 = new TalonSRX(RobotMap.driveTalon8);
	private AHRS gyro = new AHRS(SerialPort.Port.kUSB, AHRS.SerialDataType.kProcessedData, (byte) 200);
    

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

