package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimberSubsystem extends Subsystem 
{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private WPI_TalonSRX climbMotor = new WPI_TalonSRX(RobotMap.climber);
	private Compressor compressor = new Compressor();
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }	
    public void ClimberSubsystem()
   	{
  		climbMotor.set(ControlMode.PercentOutput, 0);
   	}
    public void climberOn()
   	{
   		climbMotor.set(1);
   	}
   	public void climberOff()
   	{
   		climbMotor.set(0);
   	}
    		
}

// This is officially the dankest line of code

