package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ClimberSubsystem extends Subsystem 
{
	private Compressor compressor = new Compressor(51);
//	private static DoubleSolenoid ramp = new DoubleSolenoid(RobotMap.solenoidCAN, 0, RobotMap.ramp);
	private static DoubleSolenoid hangerRelease = new DoubleSolenoid(RobotMap.solenoidCAN, 1, RobotMap.hanger);
	private static DoubleSolenoid hanger = new DoubleSolenoid(RobotMap.solenoidCAN, 3, 4);


	
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }	
    
    public ClimberSubsystem()
   	{
    	hanger.set(DoubleSolenoid.Value.kOff);
   	}
    
//    public void releaseRamp(boolean open)
//	{
//		if(open == true)
//			ramp.set(DoubleSolenoid.Value.kForward);
//		else
//			ramp.set(DoubleSolenoid.Value.kReverse);
//	}
    public boolean compressorEnabled()
    {
    	return compressor.enabled();
    }
    
    public void enableCompressor(boolean enable)
    {
    	if(enable)
    		compressor.start();
    	else
    		compressor.stop();
    }
    public void startHang(boolean open)
	{
		if(open == true)
			hanger.set(DoubleSolenoid.Value.kForward);
		else
			hanger.set(DoubleSolenoid.Value.kReverse);
	}
    
    public void hangerRelease(boolean open)
    {
    	if(open == true)
    		hangerRelease.set(DoubleSolenoid.Value.kForward);
    	else
    		hangerRelease.set(DoubleSolenoid.Value.kReverse);
    }
}



