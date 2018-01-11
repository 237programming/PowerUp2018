package org.usfirst.frc.team237.robot.subsystems;

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

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

