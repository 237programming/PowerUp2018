package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HighElevator extends Command 
{

    public HighElevator() 
    {
    	requires(Robot.cubeHandler);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	Robot.cubeHandler.upElevator();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
    	if(Robot.cubeHandler.getEncPos() > RobotMap.topElevator)
    	{
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	System.out.println("Done going up!");
    	Robot.cubeHandler.offElevator();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	System.out.println("up interupted");
    	Robot.cubeHandler.offElevator();
    }
}
