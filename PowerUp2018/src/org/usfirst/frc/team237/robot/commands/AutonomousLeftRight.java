package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousLeftRight extends Command 
{
	
	
	public Timer backwardsIntakeTimer = new Timer();
	public double time;
	private enum State
	{
		start,
		firstMove,
		firstTurn,
		secondMove,
		secondTurn,
		thirdMove,
		thirdTurn,
		fourthMove,
		lastTurn,
		lastMove,
		outtake,
		finished,
		
	};
	
	private State currentState;

    public AutonomousLeftRight() 
    {
    	requires(Robot.driveTrain);
    	requires(Robot.cubeHandler);
        //Use requires() here to declare subsystem dependencies
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
    	switch (currentState)
    	{
    	case start:
    		currentState = State.firstMove;
    		break;
    	case firstMove:
    		Robot.driveTrain.setDrives(-.3,0);
    		Robot.driveTrain.getEncPos();
    		if(Robot.driveTrain.getEncPos() > 5000)
    		{
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.setDrives(0, 0);
    			currentState = State.firstTurn;
    		}
    		break;
    	case firstTurn:
    		Robot.driveTrain.setDrives(0, -.3);
    		Robot.driveTrain.getYaw();
    		if(Robot.driveTrain.getYaw() <- 2.0)
    		{
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.zeroYaw();
    			Robot.driveTrain.setDrives(0, 0);
    			currentState = State.secondMove;
    		}
    		break;
    	case secondMove:
    		Robot.driveTrain.setDrives(-.3,0);
    		Robot.driveTrain.getEncPos();
    		if(Robot.driveTrain.getEncPos()>5000)
    		{
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.setDrives(0, 0);
    			currentState = State.secondTurn;
    		}
    		break;
    	case secondTurn:
    		Robot.driveTrain.setDrives(0, -.3);
    		Robot.driveTrain.getYaw();
    		if(Robot.driveTrain.getYaw() < 2.0)
    		{
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.zeroYaw();
    			Robot.driveTrain.setDrives(0, 0);
    			currentState = State.thirdMove;
    		}
    		break;
    	case thirdMove:
    		Robot.driveTrain.setDrives(-.3,0);
    		Robot.driveTrain.getEncPos();
    		if(Robot.driveTrain.getEncPos()>5000)
    		{
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.setDrives(0, 0);
    			currentState = State.thirdTurn;
    		}
    		break;
    	case thirdTurn:
    		Robot.driveTrain.setDrives(0, -.3);
    		Robot.driveTrain.getYaw();
    		if(Robot.driveTrain.getYaw() < 2.0)
    		{
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.zeroYaw();
    			Robot.driveTrain.setDrives(0, 0);
    			currentState = State.fourthMove;
    		}
    		break;
    	case fourthMove:
    		Robot.driveTrain.setDrives(-.3,0);
    		Robot.driveTrain.getEncPos();
    		if(Robot.driveTrain.getEncPos()>5000)
    		{
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.setDrives(0, 0);
    			currentState = State.lastTurn;
    		}
    		break;
    	case lastTurn:
    		Robot.driveTrain.setDrives(0, -.3);
    		Robot.driveTrain.getYaw();
    		if(Robot.driveTrain.getYaw() < 2.0)
    		{
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.zeroYaw();
    			Robot.driveTrain.setDrives(0, 0);
    			currentState = State.lastMove;
    		}
    		break;
    	case lastMove:
    		Robot.driveTrain.setDrives(-.3,0);
    		Robot.driveTrain.getEncPos();
    		if(Robot.driveTrain.getEncPos()>5000)
    		{
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.setDrives(0, 0);
    			time = backwardsIntakeTimer.getFPGATimestamp();
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
