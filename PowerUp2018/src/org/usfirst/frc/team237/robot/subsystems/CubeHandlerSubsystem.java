package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.RobotMap;
import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
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
	private static DoubleSolenoid grabber = new DoubleSolenoid(RobotMap.solenoidCAN, 0, RobotMap.grabber);
//	private boolean currentState = true;
//	private boolean pastState = false;
//	private DigitalInput cubeSensor = new DigitalInput(0);
//	public Timer cubeSensorTimer = new Timer();
//	private int time;

	public CubeHandlerSubsystem()
	{
		leftIntake.set(ControlMode.PercentOutput,0);
		rightIntake.set(ControlMode.PercentOutput, 0);
		elevator.set(ControlMode.PercentOutput, 0);
		elevator.configNominalOutputReverse(0, 0);
		
		elevator.setSensorPhase(false);
	}
	
	public void actuate(boolean open)
	{
		if(open == true)
			grabber.set(DoubleSolenoid.Value.kForward);
		else
			grabber.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void fowardIntake()
	{
		leftIntake.set(.7);
		rightIntake.set(.7);
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
		elevator.set(.8);
	}
	
	public void downElevator()
	{
		elevator.set(-.8);
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
	
//	public void cubeSensor()
//	{
//		if(cubeSensor.get() == true)
//		{
//			if(pastState == false)
//			{
//				if(currentState == true)
//					currentState = false;
//				else
//					currentState = true;
//			}
//			pastState = true;
//		}
//		else
//			pastState = false;
//		if(currentState == false)
//		{
//			fowardIntake();
//			if(cubeSensorTimer.getFPGATimestamp() > time + .1);
//			{
//				offIntake();
//			}
//			actuate(false);
//			fowardIntake();
//			if(cubeSensorTimer.getFPGATimestamp() > time + .25);
//			{
//				offIntake();
//			}
//		}
//	}
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

