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
	private WPI_TalonSRX leftDrive = new WPI_TalonSRX(RobotMap.driveTalon1);
	private WPI_TalonSRX leftDriveSlave = new WPI_TalonSRX(RobotMap.driveTalon2);
	private WPI_TalonSRX rightDrive = new WPI_TalonSRX(RobotMap.driveTalon3);
	private WPI_TalonSRX rightDriveSlave = new WPI_TalonSRX(RobotMap.driveTalon4);
	private AHRS gyro = new AHRS(SerialPort.Port.kUSB, AHRS.SerialDataType.kProcessedData, (byte) 200);
    
	public DriveSubsystem()
	{
		leftDrive.set(ControlMode.PercentOutput, 0);
		leftDriveSlave.set(ControlMode.Follower, 1);
		rightDrive.set(ControlMode.PercentOutput, 0);
		rightDriveSlave.set(ControlMode.Follower, 3);
		
		gyro.reset();
	}
	
	public void setDrives(double x, double y)
	{
			x = Math.abs(x) > 0.1 ? x : 0;
			y = Math.abs(y) > 0.1 ? y : 0;
			
			if(x != 0)
			{
				x = sgn(x) * ((Math.abs(x) - RobotMap.deadband) / (1 - RobotMap.deadband));
			}
			
			if(y != 0)
			{
				y = sgn(y) * ((Math.abs(y) - RobotMap.deadband) / (1 - RobotMap.deadband));
			}
			
			double left = y + x;
			double right = (y - x) * -1;
			double absLeft = Math.abs(left);
			double absRight = Math.abs(right);
			double normalLeft;
			double normalRight;
			
			if(absLeft > absRight && absLeft > 1)
			{
				normalLeft = left / absLeft;
				normalRight = right / absLeft;
			}
			
			else if(absRight > absLeft && absRight > 1)
			{
				normalLeft = left / absRight;
				normalRight = right / absRight;
			}
			
			else
			{
				normalLeft = left;
				normalRight = right;
			}
			
			leftDrive.set(normalLeft);
			rightDrive.set(normalRight);
	}
	
	double sgn(double x)
	{
		return x/Math.abs(x);
	}
	
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

