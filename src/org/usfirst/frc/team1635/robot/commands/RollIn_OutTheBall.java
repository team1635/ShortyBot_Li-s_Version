package org.usfirst.frc.team1635.robot.commands;

import org.usfirst.frc.team1635.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RollIn_OutTheBall extends Command {
	boolean isIn;
/**
 * 
 * @param isInOut true - roll in the ball; false - roll out the ball
 */
	public RollIn_OutTheBall(boolean isInOut) {
		this.isIn = isInOut;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.intaker);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (isIn) {
			Robot.intaker.rollIn();
		} else if (!isIn) {
			Robot.intaker.rollOut();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.intaker.isOnTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.intaker.stopIntaker();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
