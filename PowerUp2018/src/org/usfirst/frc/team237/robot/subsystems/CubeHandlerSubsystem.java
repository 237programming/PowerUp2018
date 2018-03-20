package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.RobotMap;
import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CubeHandlerSubsystem extends Subsystem 
{
	private WPI_TalonSRX leftIntake = new WPI_TalonSRX(RobotMap.intake1);
	private WPI_TalonSRX rightIntake = new WPI_TalonSRX(RobotMap.intake2);
	public WPI_TalonSRX elevator = new WPI_TalonSRX(RobotMap.elevator1);
//	private static Solenoid grabber = new Solenoid(RobotMap.solenoidCAN, RobotMap.grabber);
	private static DoubleSolenoid grabber = new DoubleSolenoid(RobotMap.solenoidCAN, 0,  RobotMap.grabber);
	private boolean autoStatus = false;
	private boolean pastStatus = false;
	private boolean manualStatus = false;
	private DigitalInput cubeSensor = new DigitalInput(0);
	private double time;
	private boolean grabberOpen;

	public CubeHandlerSubsystem()
	{
		leftIntake.set(ControlMode.PercentOutput,0);
		rightIntake.set(ControlMode.PercentOutput, 0);
		elevator.set(ControlMode.PercentOutput, 0);
//		elevator.configNominalOutputReverse(0, 0);
		
		elevator.setSensorPhase(false);
	}
	
//	public void actuate(boolean open)
//	{
//		grabber.set(open);
//	}
	
	public void actuate(boolean open)
	{
		if(open)
		{
			grabber.set(DoubleSolenoid.Value.kForward);
			grabberOpen = true;
		}
			
		else
		{
			grabber.set(DoubleSolenoid.Value.kReverse);
			grabberOpen = false;
		}
			
	}
	
	public void fowardIntake()
	{
		leftIntake.set(1);
		rightIntake.set(1);
		manualStatus = true;
	}
	
	public void backwardIntake()
	{
		leftIntake.set(-1);
		rightIntake.set(-1);
		manualStatus = true;
	}
	
	public void offIntake()
	{
		rightIntake.set(0);
		leftIntake.set(0);
		manualStatus = false;		
	}
	
	public void setOuttake(double speed)
	{
		leftIntake.set(speed);
		rightIntake.set(speed);
	}
	
	public void upElevator()
	{
		elevator.set(1);
	}
	
	public void downElevator()
	{
		elevator.set(-1);
	}
	
	public void offElevator()
	{
		elevator.set(0);
	}
	
	public void autoElevatorUp(double speed)
	{
		elevator.set(speed);
	}
	
	public double getEncPos()
	{
		double elevatorEnc = elevator.getSelectedSensorPosition(0);
		return elevatorEnc;
	}
	
	public void zeroEnc()
	{
		elevator.setSelectedSensorPosition(0, 0, 0);
	}
	
	public void post()
	{
		SmartDashboard.putNumber("Elevator", getEncPos());
		SmartDashboard.putBoolean("Cube Sensor", cubeSensor.get());
		SmartDashboard.putBoolean("Grabber Open?", grabberOpen);
	}
	
	public void cubeSensor()
	{
		if(manualStatus == true)
		{
			autoStatus = false;
			pastStatus = false;
			//stop timer here?
		}
		if(cubeSensor.get() == true)
			autoStatus = true;
		if(autoStatus == true && pastStatus == false)
		{
			actuate(false);
			time = Timer.getFPGATimestamp();
			
//			fowardIntake();
		}
		pastStatus = autoStatus;
		if(autoStatus == true)
		{
			if(Timer.getFPGATimestamp() > time + 2)
			{
				offIntake();
				autoStatus = false;
				//stop timer?
			}
		}
	}
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

