package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ClimberSubsystem extends Subsystem 
{
	private WPI_TalonSRX climbMotor = new WPI_TalonSRX(RobotMap.climber);
	private Compressor compressor = new Compressor(51);
	
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
   	
   	public double getEncPos()
   	{
   		int hangEnc = climbMotor.getSelectedSensorPosition(0);
   		return hangEnc;
   	}
   	
   	public void zeroEnc()
   	{
   		climbMotor.setSelectedSensorPosition(0, 0, 0);
   	}
   	
   	public void post()
   	{
   		SmartDashboard.putNumber("Elevator", getEncPos());
   	}
}



