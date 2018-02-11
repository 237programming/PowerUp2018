package org.usfirst.frc.team237.robot.subsystems;


import org.usfirst.frc.team237.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;

/**
 *
 */
public class DriveSubsystem extends Subsystem implements edu.wpi.first.wpilibj.PIDOutput
{	
	private WPI_TalonSRX leftDrive = new WPI_TalonSRX(RobotMap.driveTalon1);
	private WPI_TalonSRX leftDriveSlave = new WPI_TalonSRX(RobotMap.driveTalon2);
	private WPI_TalonSRX rightDrive = new WPI_TalonSRX(RobotMap.driveTalon3);
	private WPI_TalonSRX rightDriveSlave = new WPI_TalonSRX(RobotMap.driveTalon4);
//	private AHRS gyro = new AHRS(SerialPort.Port.kUSB, AHRS.SerialDataType.kProcessedData, (byte) 200);
	private AHRS gyro = new AHRS(SPI.Port.kMXP);
	private PIDController angularPID = new PIDController(0.1, 0.0, 0.1, gyro, this);
	private double PIDOutput = 0;
    
	public DriveSubsystem()
	{
		leftDrive.set(ControlMode.PercentOutput, 0);
		leftDriveSlave.set(ControlMode.Follower, 1);
		rightDrive.set(ControlMode.PercentOutput, 0);
		rightDriveSlave.set(ControlMode.Follower, 3);
		angularPID.disable();
		angularPID.setInputRange(-180, 180);
		angularPID.setOutputRange(-0.8, 0.8);
		angularPID.setPercentTolerance(30);
		angularPID.setContinuous();
		
		
		gyro.reset();
		
		leftDrive.setSensorPhase(true);
	}
	
	public void setDrives(double x, double y)
	{
		x = Math.abs(x) > 0.2 ? x : 0;
		y = Math.abs(y) > 0.2 ? y : 0;
		
		if(x != 0)
		{
			x = sgn(x) * ((Math.abs(x) - RobotMap.deadband) / (1 - RobotMap.deadband));
		}
			
		if(y != 0)
		{
			y = sgn(y) * ((Math.abs(y) - RobotMap.deadband) / (1 - RobotMap.deadband));			}
			
		double right = y + x;
		double left = (y - x) * -1;	
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
	
	public void reverseDrive(double x, double y)
	{
		x = Math.abs(x) > 0.2 ? x : 0;
		y = Math.abs(y) > 0.2 ? y : 0;
		
		if(x != 0)
		{
			x = -1 * (sgn(x) * ((Math.abs(x) - RobotMap.deadband) / (1 - RobotMap.deadband)));
		}
		
		if(y != 0)
		{
			y =  (sgn(y) * ((Math.abs(y) - RobotMap.deadband) / (1 - RobotMap.deadband)));
		}
		
		double right = y + x;
		double left = (y - x) * -1;
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
		if (x < 0)
		{
			return -1.0; 
		}
		else if (x > 0)
		{
			return 1.0; 
		} 
		else
		{
			return 0.0; 
		}
	}
	
	public double getEncPos()
	{
		int leftEnc = leftDrive.getSelectedSensorPosition(0);
		int rightEnc = rightDrive.getSelectedSensorPosition(0);
		double position = (leftEnc + rightEnc)/2;
		return position;
	}
	
	public double getYaw()
	{
		double realYaw = gyro.getYaw();
		return realYaw;
	}
	
	public void zeroYaw()
	{
		gyro.zeroYaw();
	}
	
	public void zeroEnc()
	{
		leftDrive.setSelectedSensorPosition(0, 0, 0);
		rightDrive.setSelectedSensorPosition(0, 0, 0);
	}
	
	@Override
	public void pidWrite(double output)
	{
		PIDOutput = output;
	}
	
	public void rotateTo(double targetAngle)
	{
		PIDOutput = 0; 
		angularPID.disable();
		angularPID.setSetpoint(targetAngle);
		angularPID.enable();
	}
	
	public void disableRotateTo()
	{
		angularPID.disable();
		setDrives(0.0,0.0);
	}
	public void setPIDValues(double P, double I, double D)
	{
		angularPID.setP(P);
		angularPID.setI(I);
		angularPID.setD(D);
	}
	public void pidDrive(double speed)
	{
		setDrives(speed,PIDOutput);
	}
	
	public void post()
	{
		SmartDashboard.putNumber("Right Drive", rightDrive.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Left Drive", leftDrive.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Gyro Yaw", getYaw());
	}

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

