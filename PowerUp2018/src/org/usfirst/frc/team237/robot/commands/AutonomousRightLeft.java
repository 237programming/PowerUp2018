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
	private double time;
	private enum State
	{
		start,
		bringElevatorUp,
		pause,
		movePastSwitch,
		turn90ForLongMove,
		moveAlongSwitch,
		turnToSwitch,
		smallMoveToSwitch,
		outtakeCube1,
		backAwayFromSwitch,
		elevatorDown,
		moveBackToSwitch,
		intake,
		elevatorUp,
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
    	Robot.cubeHandler.zeroEnc();
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
			currentState = State.bringElevatorUp;
			break;
	    case bringElevatorUp:
	    	Robot.cubeHandler.autoElevatorUp(.3);
	    	if(Robot.cubeHandler.getEncPos() > 700)
	    	{
	    		Robot.cubeHandler.offElevator();
	    		Robot.driveTrain.disableRotateTo();
	    		Robot.driveTrain.zeroEnc();
	    		Robot.driveTrain.setDrives(0, 0);
	    		time = Timer.getFPGATimestamp();
	    		currentState = State.pause;
	    	}
	    	break;
	    case pause:
	    	Robot.driveTrain.setDrives(0, 0);
	    	if(Timer.getFPGATimestamp() > time + .25)
	    	{
	    		Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.cubeHandler.autoElevatorUp(.6);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(0);
		    	currentState = State.movePastSwitch;
	    	}
	    	break;
		case movePastSwitch:
			Robot.driveTrain.pidDrive(-.8);
			if(Robot.driveTrain.getEncPos() > 14000)
			{
				Robot.cubeHandler.offElevator();
				System.out.println(Robot.driveTrain.getEncPos());
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(-90);
		    	time = Timer.getFPGATimestamp();
				currentState = State.turn90ForLongMove;
			}
			break;
		case turn90ForLongMove:
			Robot.driveTrain.pidDrive(0);
			if(Timer.getFPGATimestamp() > time + 1)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(-90);
				currentState = State.moveAlongSwitch;
			}
			break;
		case moveAlongSwitch:
			Robot.driveTrain.pidDrive(-.8);
			if(Robot.driveTrain.getEncPos() > 13000)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(150);
		    	time = Timer.getFPGATimestamp();
				currentState = State.turnToSwitch;
			}
			break;
		case turnToSwitch:
			Robot.driveTrain.pidDrive(0);
    		if(Timer.getFPGATimestamp() > time + 1)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(150);
    			currentState = State.smallMoveToSwitch;
    		}
			break;
		case smallMoveToSwitch:
			Robot.driveTrain.pidDrive(-.8);
			if(Robot.driveTrain.getEncPos() > 800)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(150);
    			time = Timer.getFPGATimestamp();
				currentState = State.outtakeCube1;
			}
			break;
		case outtakeCube1:
    		Robot.cubeHandler.backwardIntake();
    		if(Timer.getFPGATimestamp() > time + 1)
    		{
    			Robot.cubeHandler.offIntake();
    			Robot.cubeHandler.actuate(true);
    			Robot.driveTrain.disableRotateTo();
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
				Robot.driveTrain.rotateTo(150);
    			time = Timer.getFPGATimestamp();
    			currentState = State.backAwayFromSwitch;
    		}
    		break;
		case backAwayFromSwitch:
			Robot.driveTrain.pidDrive(.8);
			if(Robot.driveTrain.getEncPos() < -200)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(150);
		    	time = Timer.getFPGATimestamp();
				currentState = State.elevatorDown;
			}
			break;
		case elevatorDown:
    		Robot.cubeHandler.downElevator();
    		if(Timer.getFPGATimestamp() > time + 1.75)
    		{
    			Robot.cubeHandler.offElevator();
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
				Robot.driveTrain.rotateTo(150);
		    	time = Timer.getFPGATimestamp();
		    	currentState = State.moveBackToSwitch;
    		}
    		break;
    	case moveBackToSwitch:
    		Robot.driveTrain.pidDrive(-.8);
   			if(Robot.driveTrain.getEncPos() > 400)
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
    			time = Timer.getFPGATimestamp();
    			currentState = State.elevatorUp;
       		}
       		break;
        case elevatorUp:
       		Robot.cubeHandler.upElevator();
       		if(Timer.getFPGATimestamp() > time + 2)
       		{
        		Robot.cubeHandler.offElevator();
       			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.setDrives(0, 0);
    	    	time = Timer.getFPGATimestamp();
   		    	currentState = State.outtake2;
        	}
    		break;
        case outtake2:
        	Robot.cubeHandler.backwardIntake();
       		if(Timer.getFPGATimestamp() > time + 1)
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
    	Robot.driveTrain.disableRotateTo();
    	Robot.driveTrain.setDrives(0, 0);
    	Robot.cubeHandler.offElevator();
    	Robot.cubeHandler.offIntake();
    	Robot.driveTrain.zeroEnc();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	Robot.driveTrain.disableRotateTo();
    	Robot.driveTrain.setDrives(0, 0);
    	Robot.cubeHandler.offElevator();
    	Robot.cubeHandler.offIntake();
    	Robot.driveTrain.zeroEnc();
    }
}
