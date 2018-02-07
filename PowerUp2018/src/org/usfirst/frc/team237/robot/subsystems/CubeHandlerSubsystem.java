package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CubeHandlerSubsystem extends Subsystem 
{
	private WPI_TalonSRX leftIntake = new WPI_TalonSRX(RobotMap.intake1);
	private WPI_TalonSRX rightIntake = new WPI_TalonSRX(RobotMap.intake2);
	private WPI_TalonSRX elevator = new WPI_TalonSRX(RobotMap.elevator1);
//	private DoubleSolenoid grabber = new DoubleSolenoid(0, RobotMap.grabber);
	
	public CubeHandlerSubsystem()
	{
		leftIntake.set(ControlMode.PercentOutput,0);
		rightIntake.set(ControlMode.PercentOutput, 0);
		elevator.set(ControlMode.PercentOutput, 0);
		elevator.configNominalOutputReverse(-12, 0);
		
		elevator.setSensorPhase(false);
	}
	
//	public void actuate(boolean open)
//	{
//		if(open == true)
//			grabber.set(DoubleSolenoid.Value.kForward);
//		else
//			grabber.set(DoubleSolenoid.Value.kReverse);
//	}
	
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
	
	public void upElevator()
	{
		elevator.set(.3);
	}
	
	public void downElevator()
	{
		elevator.set(-.3);
	}
	
	public void offElevator()
	{
		elevator.set(0);
	}
	
	public double getEncPos()
	{
		double elevatorEnc = elevator.getSelectedSensorPosition(0);
		return elevatorEnc;
	}
	
	public void post()
	{
		SmartDashboard.putNumber("Elevator", getEncPos());
	}
	
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

