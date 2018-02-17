package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
/**
 *
 */
public class AutonomousRightRight extends Command 
{
	public double time;
	private enum State
	{
		start,
		bringElevatorUp,
		pause,
		moveToSwitch,
		turnToSwitch,
		outtakeCube1,
		turnBackToZero,
		elevatorDown,
		movePastSwitch,
		turnBackToSwitch,
		moveToSwitchAgain,
		intake,
		elevatorUp,
		outtakeCube2,
		finished
	};
	
	private State currentState;
	
    public AutonomousRightRight() 
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
		    	currentState = State.moveToSwitch;
	    	}
	    	break;
    	case moveToSwitch:
    		Robot.driveTrain.pidDrive(-.8);
    		if(Robot.driveTrain.getEncPos() > 9000)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(-90);
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
				time = Timer.getFPGATimestamp();
    			currentState = State.outtakeCube1;
    		}
    		break;
    	case outtakeCube1:
    		Robot.cubeHandler.backwardIntake();
    		if(Timer.getFPGATimestamp() > 1 + time)
    		{
    			Robot.cubeHandler.offIntake();
    			Robot.cubeHandler.actuate(true);
		    	Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
				Robot.driveTrain.rotateTo(0);
				time = Timer.getFPGATimestamp();
		    	currentState = State.turnBackToZero;
    		}
    		break;
    	case turnBackToZero:
    		Robot.driveTrain.pidDrive(0);
    		if(Timer.getFPGATimestamp() > time + 1)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				time = Timer.getFPGATimestamp();
		    	currentState = State.elevatorDown;
    		}
    		break;
    	case elevatorDown:
    		Robot.cubeHandler.downElevator();
    		if(Timer.getFPGATimestamp() > time + 1)
    		{
 				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(0);
		    	time = Timer.getFPGATimestamp();
		    	currentState = State.movePastSwitch;
    		}
    		break;
    	case movePastSwitch:
    		Robot.driveTrain.pidDrive(-.8);
    		if(Robot.driveTrain.getEncPos() > 5000)
    		{
    			Robot.cubeHandler.offElevator();
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(-140);
		    	time = Timer.getFPGATimestamp();
		    	currentState = State.turnBackToSwitch;
    		}
    		break;
    	case turnBackToSwitch:
    		Robot.driveTrain.pidDrive(0);
    		if(Timer.getFPGATimestamp() > time + 1)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(-140);
		    	currentState = State.moveToSwitchAgain;
    		}
    		break;
    	case moveToSwitchAgain:
    		Robot.driveTrain.pidDrive(-.8);
    		if(Robot.driveTrain.getEncPos() > 1200)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
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
		    	currentState = State.outtakeCube2;
    		}
    		break;
    	case outtakeCube2:
    		Robot.cubeHandler.backwardIntake();
    		if(Timer.getFPGATimestamp() > time + 1)
    		{
    			Robot.cubeHandler.offIntake();
    			Robot.cubeHandler.actuate(true);
		    	Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				time = Timer.getFPGATimestamp();
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