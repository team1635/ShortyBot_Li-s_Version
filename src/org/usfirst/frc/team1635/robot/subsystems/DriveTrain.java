
package org.usfirst.frc.team1635.robot.subsystems;

import org.usfirst.frc.team1635.robot.RobotMap;
import org.usfirst.frc.team1635.robot.commands.DriveWithJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class DriveTrain extends Subsystem {
	private SpeedController frontLeft, backLeft, frontRight, backRight;
	private RobotDrive drive;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveWithJoystick());
    }
    
    public DriveTrain() {
		super();
		 
		frontLeft = new Victor(RobotMap.kDriveTrain_FrontLeftMotorController);
		backLeft = new Victor(RobotMap.kDriveTrain_BackLeftMotorController);
		frontRight = new Victor(RobotMap.kDriveTrain_FrontRightMotorController);
		backRight = new Victor(RobotMap.kDriveTrain_BackRightMotorController);

		drive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
				
		LiveWindow.addActuator("Drive Train", "Front_Left Motor",
				(Victor) frontLeft);
		LiveWindow.addActuator("Drive Train", "Back Left Motor",
				(Victor) backLeft);
		LiveWindow.addActuator("Drive Train", "Front Right Motor",
				(Victor) frontRight);
		LiveWindow.addActuator("Drive Train", "Back Right Motor",
				(Victor) backRight);
		
		
    }
    
    public void drive(Joystick joy) {
		//driveSquared( -1 * joy.getY()  , -1 * joy.getRawAxis(5) * 0.9);
		drive.tankDrive(joy.getY(), joy.getRawAxis(5));
	}
    
    public void driveParameters(double left, double right){
    	
    	drive.tankDrive(left, right);
    }
}

