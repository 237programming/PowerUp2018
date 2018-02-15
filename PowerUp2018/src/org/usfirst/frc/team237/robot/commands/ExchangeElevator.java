package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExchangeElevator extends Command 
{
	private boolean isGoingUp;

    public ExchangeElevator() 
    {
    	requires(Robot.cubeHandler);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	if(Robot.cubeHandler.getEncPos() > RobotMap.midElevator)
    	{
    		Robot.cubeHandler.downElevator();
    		isGoingUp = false;
    	}
    	if(Robot.cubeHandler.getEncPos() < RobotMap.midElevator)
    	{
    		Robot.cubeHandler.upElevator();
    		isGoingUp = true;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
    	if(isGoingUp == false)
    	{
    		if(Robot.cubeHandler.getEncPos() > RobotMap.exElevator)
    			return true;
    	}
    	if(isGoingUp == true)
    	{
    		if(Robot.cubeHandler.getEncPos() < RobotMap.exElevator)
    			return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    }
}
