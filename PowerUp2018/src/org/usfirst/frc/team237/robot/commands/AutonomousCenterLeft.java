package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;
import org.usfirst.frc.team237.robot.commands.AutonomousRightRight.State;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousCenterLeft extends Command 
{
	public Timer backwardsIntakeTimer = new Timer();
	public Timer elevatorTimer = new Timer();
	public double time;
	private enum State
	{
		start,
		firstMove,
		firstTurn,
		secondMove,
		secondTurn,
		thirdMove,
		outtake,
		fourthMove,
		thirdTurn,
		elevator,
		fifthMove,
		intake,
		elevator2,
		backwardsFifthMove,
		backwardsThirdTurn,
		backwardsFourthMove,
		outtake2,
		finished
	};
	
	private State currentState;
	
    public AutonomousCenterLeft() 
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
    		if(Robot.driveTrain.getEncPos() > 1500)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(-50);
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
		    	Robot.driveTrain.rotateTo(-50);
    			currentState = State.secondMove;
    		}
    		break;
    	case secondMove:
    		Robot.driveTrain.pidDrive(-.8);
    		if(Robot.driveTrain.getEncPos() > 3500)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(-5);
		    	time = Timer.getFPGATimestamp();
    			currentState =  State.secondTurn;
    		}
    		break;
    	case secondTurn:
    		Robot.driveTrain.pidDrive(0);
    		if(Timer.getFPGATimestamp() > time + .5)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(-5);
    			currentState = State.thirdMove;
    		}
    		break;
    	case thirdMove:
    		Robot.driveTrain.pidDrive(-.8);
    		if(Robot.driveTrain.getEncPos() > 500)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(-5);
    			time = backwardsIntakeTimer.getFPGATimestamp();
    			currentState =  State.outtake;
    		}
    		break;
    	case outtake:
    		Robot.cubeHandler.backwardIntake();
    		if(backwardsIntakeTimer.getFPGATimestamp() > 2 + time)
    		{
    			Robot.cubeHandler.offIntake();
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
    			Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
    			Robot.driveTrain.rotateTo(0);
    			currentState = State.fourthMove;
    		}
    		break;
    	case fourthMove:
    		Robot.driveTrain.pidDrive(.8);
    		if(Robot.driveTrain.getEncPos() > 250)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(90);
    			time = Timer.getFPGATimestamp();
    			currentState =  State.thirdTurn;
    		}
    		break;
    	case thirdTurn:
    		Robot.driveTrain.pidDrive(0);
    		if(Timer.getFPGATimestamp() > time + .5)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
//		    	Robot.driveTrain.rotateTo(90);
		    	time = elevatorTimer.getFPGATimestamp();
    			currentState = State.elevator;
    		}
    		break;
    	case elevator:
    		Robot.cubeHandler.elevator.set(1);
    		Robot.cubeHandler.downElevator();
    		if(elevatorTimer.getFPGATimestamp() > time + 2.5)
    		{
    			Robot.cubeHandler.offElevator();
 //   			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(90);
		    	time = Timer.getFPGATimestamp();
		    	currentState = State.fifthMove;
    		}
    		break;
    	case fifthMove:
    		Robot.driveTrain.pidDrive(-.8);
    		if(Robot.driveTrain.getEncPos() > 200)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(90);
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
    		if(elevatorTimer.getFPGATimestamp() > time + 2.5)
    		{
    			Robot.cubeHandler.offElevator();
 //   			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(90);
		    	time = Timer.getFPGATimestamp();
		    	currentState = State.backwardsFifthMove;
    		}
    		break;
    	case backwardsFifthMove:
    		Robot.driveTrain.pidDrive(.8);
    		if(Robot.driveTrain.getEncPos() > 200)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
		    	Robot.driveTrain.rotateTo(0);
		    	time = Timer.getFPGATimestamp();
    			currentState = State.backwardsThirdTurn;
    		}
    		break;
    	case backwardsThirdTurn:
    		Robot.driveTrain.pidDrive(0);
    		if(Timer.getFPGATimestamp() > time + .5)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
		    	Robot.driveTrain.setPIDValues(RobotMap.driveP, RobotMap.driveI, RobotMap.driveD);
		    	Robot.driveTrain.rotateTo(0);
    			currentState = State.backwardsFourthMove;
    		}
    		break;
    	case backwardsFourthMove:
    		Robot.driveTrain.pidDrive(-.8);
    		if(Robot.driveTrain.getEncPos() > 250)
    		{
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
				Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
//		    	Robot.driveTrain.rotateTo(90);
    			time = backwardsIntakeTimer.getFPGATimestamp();
    			currentState =  State.outtake2;
    		}
    		break;
    	case outtake2:
    		Robot.cubeHandler.backwardIntake();
    		if(backwardsIntakeTimer.getFPGATimestamp() > 2 + time)
    		{
    			Robot.cubeHandler.offIntake();
    			Robot.driveTrain.disableRotateTo();
				Robot.driveTrain.zeroEnc();
				Robot.driveTrain.setDrives(0, 0);
    			Robot.driveTrain.setPIDValues(RobotMap.turnP, RobotMap.turnI, RobotMap.turnD);
    			Robot.driveTrain.rotateTo(0);
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
