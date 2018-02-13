package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousCenterRight extends Command 
{
	public Timer backwardsIntakeTimer = new Timer();
	public Timer elevatorTimer = new Timer();
	public double time;
	private enum State
	{
		start,
		moveOffWall,
		turnToDiagonalAngle,
		moveAtAngle,
		turnToZero,
		moveFowardToSwitch,
		outtakeCube1,
		backAwayFromSwitch,
		turnTowardsCubePile,
		elevatorDown,
		intakeInit,
		moveToCubePile,
		intakeAfterGrab,
		backAwayFromPile,
		elevatorUp,
		turnBackToZero,
		moveFowardToSwitch2,
		outtakeCube2,
		finished
	};
	
	private State currentState;
	
    public AutonomousCenterRight() 
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
    		currentState = State.moveOffWall;
    		break;
    	case moveOffWall:
    		Robot.driveTrain.pidDrive(-.8);
    		if(Robot.driveTrain.getEncPos() > 1500)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(50);
		    	time = Timer.getFPGATimestamp();
    			currentState = State.turnToDiagonalAngle;
    		}
    		break;
    	case turnToDiagonalAngle:
    		Robot.driveTrain.pidDrive(0);
    		if(Timer.getFPGATimestamp() > time + 1)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(50);
    			currentState = State.moveAtAngle;
    		}
    		break;
    	case moveAtAngle:
    		Robot.driveTrain.pidDrive(-.8);
    		if(Robot.driveTrain.getEncPos() > 3500)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(0);
		    	time = Timer.getFPGATimestamp();
    			currentState =  State.turnToZero;
    		}
    		break;
    	case turnToZero:
    		Robot.driveTrain.pidDrive(0);
    		if(Timer.getFPGATimestamp() > time + .5)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(0);
    			currentState = State.moveFowardToSwitch;
    		}
    		break;
    	case moveFowardToSwitch:
    		Robot.driveTrain.pidDrive(-.5);
    		if(Robot.driveTrain.getEncPos() > 300)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.rotateTo(0);
    			time = backwardsIntakeTimer.getFPGATimestamp();
    			currentState =  State.outtakeCube1;
    		}
    		break;
    	case outtakeCube1:
    		Robot.cubeHandler.backwardIntake();
    		if(backwardsIntakeTimer.getFPGATimestamp() > 1 + time)
    		{
    			Robot.cubeHandler.offIntake();
    			Robot.cubeHandler.actuate(true);
    			Robot.driveTrain.disableRotateTo();
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
				Robot.driveTrain.rotateTo(0);
    			currentState = State.backAwayFromSwitch;
    		}
    		break;
    	case backAwayFromSwitch:
    		Robot.driveTrain.pidDrive(.8);
			if(Robot.driveTrain.getEncPos() < -2000)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(-60);
		    	time = Timer.getFPGATimestamp();
				currentState = State.turnTowardsCubePile;
			}
			break;
    	case turnTowardsCubePile:
    		Robot.driveTrain.pidDrive(0);
    		if(Timer.getFPGATimestamp() > time + 1)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.rotateTo(-60);
		    	time = elevatorTimer.getFPGATimestamp();
		    	currentState = State.elevatorDown;
    		}
    		break;
    	case elevatorDown:
    		Robot.cubeHandler.downElevator();
    		if(elevatorTimer.getFPGATimestamp() > time + 2.25)
    		{
    			Robot.cubeHandler.offElevator();
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
//				Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
//				Robot.driveTrain.rotateTo(-90);
		    	time = Timer.getFPGATimestamp();
		    	currentState = State.intakeInit;
    		}
    		break;
    	case intakeInit:
    		Robot.cubeHandler.fowardIntake();
    		Robot.cubeHandler.cubeSensor();
    		if(Timer.getFPGATimestamp() > time + 1)
    		{
    			Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
				Robot.driveTrain.rotateTo(-60);
				time = elevatorTimer.getFPGATimestamp();
				currentState = State.moveToCubePile;
    		}
    		break;
    	case moveToCubePile:
    		Robot.driveTrain.pidDrive(-.5);
			if(Robot.driveTrain.getEncPos() > 3800)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				time = Timer.getFPGATimestamp();
				currentState = State.intakeAfterGrab;
			}
			break;
    	case intakeAfterGrab:
    		Robot.cubeHandler.fowardIntake();
    		Robot.cubeHandler.cubeSensor();
    		if(Timer.getFPGATimestamp() > time + 1)
    		{
    			Robot.cubeHandler.offIntake();
    			Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
				Robot.driveTrain.rotateTo(-60);
				time = elevatorTimer.getFPGATimestamp();
				currentState = State.backAwayFromPile;
    		}
    		break;
    	case backAwayFromPile:
    		Robot.driveTrain.pidDrive(.8);
			if(Robot.driveTrain.getEncPos() < -700)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(0);
		    	time = Timer.getFPGATimestamp();
				currentState = State.elevatorUp;
			}
			break;
    	case elevatorUp:
    		Robot.cubeHandler.upElevator();
    		if(elevatorTimer.getFPGATimestamp() > time + 2.25)
    		{
    			Robot.cubeHandler.offElevator();
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
				Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
				Robot.driveTrain.rotateTo(0);
		    	currentState = State.moveFowardToSwitch2;
    		}
    		break;
    	case moveFowardToSwitch2:
    		Robot.driveTrain.pidDrive(-.8);
			if(Robot.driveTrain.getEncPos() > 1000)
			{
				Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	time = backwardsIntakeTimer.getFPGATimestamp();
				currentState = State.outtakeCube2;
			}
			break;
    	case outtakeCube2:
    		Robot.cubeHandler.backwardIntake();
    		if(backwardsIntakeTimer.getFPGATimestamp() > 1 + time)
    		{
    			Robot.cubeHandler.offIntake();
    			Robot.cubeHandler.actuate(true);
    			Robot.driveTrain.disableRotateTo();
    			Robot.driveTrain.zeroEnc();
    			Robot.driveTrain.setDrives(0, 0);
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
