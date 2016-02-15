package org.usfirst.frc.team1635.robot.commands;

import org.usfirst.frc.team1635.robot.Robot;
import org.usfirst.frc.team1635.robot.subsystems.DoubleCamera;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DualCameras extends Command {

    public DualCameras() {
    	requires(Robot.doublecamera);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.doublecamera.init();
    	//Robot.doublecamera.runCamZero();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.doublecamera.run();
    	//Robot.doublecamera.runWithOneButton();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.doublecamera.end();
    	//System.out.println("camera0 is ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
