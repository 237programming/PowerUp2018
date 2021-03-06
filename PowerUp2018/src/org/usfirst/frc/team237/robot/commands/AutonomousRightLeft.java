package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
/**
 *
 */
public class AutonomousRightLeft extends Command 
{
	public Timer backwardsIntakeTimer = new Timer();
	private double time;
	private enum State
	{
		start,
		firstMove,
		firstTurn,
		secondMove,
		secondTurn,
		thirdMove,
		outtake,
		finished
	};
	
	private State currentState;
	
    public AutonomousRightLeft() 
    {
    	requires(Robot.driveTrain);
    	requires(Robot.cubeHandler);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	Robot.driveTrain.zeroEnc();
    	currentState = State.start;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	switch(currentState)
    	{
	    case start: 
			currentState = State.firstMove;
			break;
		case firstMove:
			Robot.driveTrain.setDrives(-.8, 0);
			Robot.driveTrain.getEncPos();
			if(Robot.driveTrain.getEncPos() > 13000)
			{
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				currentState = State.firstTurn;
			}
			break;
		case firstTurn:
			Robot.driveTrain.setDrives(0, -.5);
			Robot.driveTrain.getYaw();
			if(Robot.driveTrain.getYaw() < -70.0)
			{
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.zeroYaw();
				currentState = State.secondMove;
			}
			break;
		case secondMove:
			Robot.driveTrain.setDrives(-.8, 0);
			Robot.driveTrain.getEncPos();
			if(Robot.driveTrain.getEncPos() > 9000)
			{
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				currentState = State.secondTurn;
			}
			break;
		case secondTurn:
			Robot.driveTrain.setDrives(0, -.5);
    		Robot.driveTrain.getYaw();
    		if(Robot.driveTrain.getYaw() < -70.0)
    		{
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.setDrives(0, 0);
    			Robot.driveTrain.zeroYaw();
    			currentState = State.thirdMove;
    		}
			break;
		case thirdMove:
			Robot.driveTrain.setDrives(-.3, 0);
			Robot.driveTrain.getEncPos();
			if(Robot.driveTrain.getEncPos() >500)
			{
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				currentState = State.outtake;
			}
			break;
		case outtake:
    		Robot.cubeHandler.backwardIntake();
    		if(backwardsIntakeTimer.getFPGATimestamp() > 2 + time)
    		{
    			Robot.cubeHandler.offIntake();
    			currentState = State.finished;
    		}
    		break;
    	default:
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
    	if(currentState == State.finished)
    		return true;
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
