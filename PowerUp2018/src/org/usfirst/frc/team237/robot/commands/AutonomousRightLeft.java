package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;
import org.usfirst.frc.team237.robot.commands.AutonomousRightLeft.State;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
/**
 *
 */
public class AutonomousRightLeft extends Command 
{
	private double time;
	public enum State
	{
		start,
		bringElevatorUp,
		pause,
		movePastSwitch,
		turn90ForLongMove,
		moveAlongSwitch,
		turnToSwitch,
		smallMoveToSwitch,
		turnToFaceSwitch,
		smallMoveToSwitchSide,
		outtakeCube1,
		backAwayFromSwitch,
		elevatorDown,
		moveBackToSwitch,
		intake,
		elevatorUp,
//		outtake2,
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
//			if(Robot.driveTrain.getEncPos() > 11000)
//				Robot.driveTrain.pidDrive(-.5);
//			else
				Robot.driveTrain.pidDrive(-.8);
			if(Robot.driveTrain.getEncPos() > 3000)
			{
				Robot.cubeHandler.offElevator();
				System.out.println(Robot.driveTrain.getEncPos());
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(-90);
		    	time = Timer.getFPGATimestamp();
				currentState = State.finished;
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
			if(Robot.driveTrain.getEncPos() > 11000)
				Robot.driveTrain.pidDrive(-.5);
			else
				Robot.driveTrain.pidDrive(-.8);
			if(Robot.driveTrain.getEncPos() > 17000)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(160);
		    	time = Timer.getFPGATimestamp();
				currentState = State.turnToSwitch;
			}
			break;
		case turnToSwitch:
			Robot.driveTrain.pidDrive(0);
    		if(Timer.getFPGATimestamp() > time + 2)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(-180);
    			currentState = State.smallMoveToSwitch;
    		}
			break;
		case smallMoveToSwitch:
			Robot.driveTrain.pidDrive(-.8);
			if(Robot.driveTrain.getEncPos() > 4500)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(90);
    			time = Timer.getFPGATimestamp();
				currentState = State.turnToFaceSwitch;
			}
			break;
		case turnToFaceSwitch:
			Robot.driveTrain.pidDrive(0);
    		if(Timer.getFPGATimestamp() > time + 2)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(90);
    			currentState = State.smallMoveToSwitchSide;
    		}
			break;
		case smallMoveToSwitchSide:
			Robot.driveTrain.pidDrive(-.8);
			if(Robot.driveTrain.getEncPos() > 800)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(-90);
    			time = Timer.getFPGATimestamp();
				currentState = State.outtakeCube1;
			}
			break;
		case outtakeCube1:
    		Robot.cubeHandler.setOuttake(-.25);
//			Robot.cubeHandler.actuate(true);
    		if(Timer.getFPGATimestamp() > time + 1)
    		{
    			Robot.cubeHandler.offIntake();
    			Robot.cubeHandler.actuate(true);
    			Robot.driveTrain.disableRotateTo();
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
				Robot.driveTrain.rotateTo(160);
    			time = Timer.getFPGATimestamp();
    			currentState = State.finished;
    		}
    		break;
		case backAwayFromSwitch:
			Robot.driveTrain.pidDrive(.8);
			if(Robot.driveTrain.getEncPos() < -500)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(160);
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
				Robot.driveTrain.rotateTo(160);
		    	time = Timer.getFPGATimestamp();
		    	currentState = State.moveBackToSwitch;
    		}
    		break;
    	case moveBackToSwitch:
    		Robot.driveTrain.pidDrive(-.8);
   			if(Robot.driveTrain.getEncPos() > 1000)
   			{
   				Robot.driveTrain.disableRotateTo();
   				Robot.driveTrain.zeroEnc();
   				Robot.driveTrain.setDrives(0, 0);
    			Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
    		   	Robot.driveTrain.rotateTo(160);
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
   		    	currentState = State.finished;
        	}
    		break;
//        case outtake2:
//        	Robot.cubeHandler.setOuttake(-.5);
//       		if(Timer.getFPGATimestamp() > time + 1)
//       		{
//        		Robot.cubeHandler.offIntake();
//       			Robot.cubeHandler.actuate(true);
//       			Robot.driveTrain.disableRotateTo();
//       			Robot.driveTrain.zeroEnc();
//       			Robot.driveTrain.setDrives(0, 0);
//   				Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
//   				Robot.driveTrain.rotateTo(160);
//       			time = Timer.getFPGATimestamp();
//       			currentState = State.finished;
//       		}
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
