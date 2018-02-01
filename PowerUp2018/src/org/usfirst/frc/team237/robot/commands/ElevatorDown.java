package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorDown extends Command 
{

    public ElevatorDown() 
    {
    	requires(Robot.cubeHandler);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	Robot.cubeHandler.downElevator();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
    	if(Robot.cubeHandler.getEncPos() < RobotMap.elevatorMin)
    	{
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	Robot.cubeHandler.offElevator();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	Robot.cubeHandler.offElevator();
    }
}
