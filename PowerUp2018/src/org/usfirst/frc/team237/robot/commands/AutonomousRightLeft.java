package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
/**
 *
 */
public class AutonomousRightLeft extends Command 
{
	public Timer backwardsIntakeTimer = new Timer();
	public Timer elevatorTimer = new Timer();
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
		elevator,
		fourthMove,
		fifthMove,
		intake,
		elevator2,
		outtake2,
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
    	Robot.driveTrain.disableRotateTo();
    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
    	Robot.driveTrain.rotateTo(0);
    	time = Timer.getFPGATimestamp();
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
			Robot.driveTrain.pidDrive(-.8);
			if(Robot.driveTrain.getEncPos() > 12500)
			{
				System.out.println(Robot.driveTrain.getEncPos());
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(-85);
		    	time = Timer.getFPGATimestamp();
				currentState = State.firstTurn;
			}
			break;
		case firstTurn:
			Robot.driveTrain.pidDrive(0);
			if(Timer.getFPGATimestamp() > time + 1)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(-85);
				currentState = State.secondMove;
			}
			break;
		case secondMove:
			Robot.driveTrain.pidDrive(-.8);
			if(Robot.driveTrain.getEncPos() > 12000)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(150);
		    	time = Timer.getFPGATimestamp();
				currentState = State.secondTurn;
			}
			break;
		case secondTurn:
			Robot.driveTrain.pidDrive(0);
    		if(Timer.getFPGATimestamp() > time + 1)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(150);
		    	time = backwardsIntakeTimer.getFPGATimestamp();
    			currentState = State.thirdMove;
    		}
			break;
		case thirdMove:
			Robot.driveTrain.pidDrive(-.8);
			if(Robot.driveTrain.getEncPos() > 400)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(150);
    			time = backwardsIntakeTimer.getFPGATimestamp();
				currentState = State.outtake;
			}
			break;
		case outtake:
    		Robot.cubeHandler.backwardIntake();
    		if(backwardsIntakeTimer.getFPGATimestamp() > 2 + time)
    		{
    			Robot.cubeHandler.offIntake();
    			Robot.cubeHandler.actuate(true);
    			Robot.driveTrain.disableRotateTo();
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
				Robot.driveTrain.rotateTo(150);
    			time = Timer.getFPGATimestamp();
    			currentState = State.fourthMove;
    		}
    		break;
		case fourthMove:
			Robot.driveTrain.pidDrive(.8);
			if(Robot.driveTrain.getEncPos() < -200)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(150);
		    	time = elevatorTimer.getFPGATimestamp();
				currentState = State.elevator;
			}
			break;
		case elevator:
    		Robot.cubeHandler.elevator.set(1);
    		Robot.cubeHandler.downElevator();
    		if(elevatorTimer.getFPGATimestamp() > time + 2.25)
    		{
    			Robot.cubeHandler.offElevator();
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
				Robot.driveTrain.rotateTo(150);
		    	time = Timer.getFPGATimestamp();
		    	currentState = State.fifthMove;
    		}
    		break;
    		case fifthMove:
    			Robot.driveTrain.pidDrive(-.8);
    			if(Robot.driveTrain.getEncPos() > 250)
    			{
    				Robot.driveTrain.disableRotateTo();
    				Robot.driveTrain.zeroEnc();
    				Robot.driveTrain.setDrives(0, 0);
    				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
    		    	Robot.driveTrain.rotateTo(150);
        			time = Timer.getFPGATimestamp();
    				currentState = State.intake;
    			}
    			break;
        	case intake:
        		Robot.cubeHandler.fowardIntake();
        		Robot.cubeHandler.cubeSensor();
        		if(Timer.getFPGATimestamp() > time + 1)
        		{
        			Robot.cubeHandler.offIntake();
        			Robot.driveTrain.zeroEnc();
    				Robot.driveTrain.setDrives(0, 0);
    				time = elevatorTimer.getFPGATimestamp();
    				currentState = State.elevator2;
        		}
        		break;
        	case elevator2:
        		Robot.cubeHandler.elevator.set(1);
        		Robot.cubeHandler.upElevator();
        		if(elevatorTimer.getFPGATimestamp() > time + 2.25)
        		{
        			Robot.cubeHandler.offElevator();
     //   			Robot.driveTrain.disableRotateTo();
    				Robot.driveTrain.zeroEnc();
    				Robot.driveTrain.setDrives(0, 0);
    		    	time = backwardsIntakeTimer.getFPGATimestamp();
    		    	currentState = State.outtake2;
        		}
    		break;
        	case outtake2:
        		Robot.cubeHandler.backwardIntake();
        		if(backwardsIntakeTimer.getFPGATimestamp() > 2 + time)
        		{
        			Robot.cubeHandler.offIntake();
        			Robot.cubeHandler.actuate(true);
        			Robot.driveTrain.disableRotateTo();
        			Robot.driveTrain.zeroEnc();
        			Robot.driveTrain.setDrives(0, 0);
    				Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
    				Robot.driveTrain.rotateTo(150);
        			time = Timer.getFPGATimestamp();
        			currentState = State.finished;
        		}
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
