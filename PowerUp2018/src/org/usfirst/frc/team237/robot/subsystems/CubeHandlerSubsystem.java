package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CubeHandlerSubsystem extends Subsystem 
{
	private WPI_TalonSRX leftIntake = new WPI_TalonSRX(RobotMap.intake1);
	private WPI_TalonSRX rightIntake = new WPI_TalonSRX(RobotMap.intake2);
	private WPI_TalonSRX elevator = new WPI_TalonSRX(RobotMap.elevator1);
	
	public void CubeHandlerSubsystem()
	{
		leftIntake.set(ControlMode.PercentOutput,0);
		rightIntake.set(ControlMode.PercentOutput, 0);
		elevator.set(ControlMode.PercentOutput, 0);
	}
	
	public void fowardIntake()
	{
		leftIntake.set(1);
		rightIntake.set(1);
	}
	
	public void backwardIntake()
	{
		leftIntake.set(-1);
		rightIntake.set(-1);
	}
	
	public void offIntake()
	{
		rightIntake.set(0);
		leftIntake.set(0);
	}
	
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

