package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CubeHandlerSubsystem extends Subsystem 
{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private TalonSRX leftIntake = new TalonSRX(RobotMap.intake1);
	private TalonSRX rightIntake = new TalonSRX(RobotMap.intake2);
	private TalonSRX elevator = new TalonSRX(RobotMap.elevator1);
	
	public void CubeHandlerSubsystem()
	{
		leftIntake.set(ControlMode.PercentOutput,0);
		rightIntake.set(ControlMode.PercentOutput, 0);
		elevator.set(ControlMode.PercentOutput, 0);
		
	}

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

