
package org.usfirst.frc.team1635.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team1635.robot.commands.Autonomous;
import org.usfirst.frc.team1635.robot.commands.Autonomous2;
import org.usfirst.frc.team1635.robot.commands.Autonomous3;
import org.usfirst.frc.team1635.robot.commands.Autonomous4;
import org.usfirst.frc.team1635.robot.commands.DriveTimeout;
import org.usfirst.frc.team1635.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team1635.robot.subsystems.Lifter;
import org.usfirst.frc.team1635.robot.subsystems.DoubleCamera;
import org.usfirst.frc.team1635.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1635.robot.subsystems.Intaker;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author : Jing Wei Li (SKRUB_HUNTER) , Miguel Cruz (@Acelogic_ )
 * 
 *         The VM is configured to automatically run this class, and to call the
 *         functions corresponding to each mode, as described in the
 *         IterativeRobot documentation. If you change the name of this class or
 *         the package after creating this project, you must also update the
 *         manifest file in the resource directory.
 */
public class Robot extends IterativeRobot {

	public static DriveTrain drivetrain;
	public static Intaker intaker;
	public static Lifter climber;
	public static DoubleCamera doublecamera;
	public static OI oi;

	public DigitalInput swich, swich2;

	Command autonomousCommand, auto1, auto2, auto3, auto4, testAuto;
	SendableChooser chooser;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		drivetrain = new DriveTrain();
		intaker = new Intaker();
		climber = new Lifter();
		
		oi = new OI();
		doublecamera = new DoubleCamera();
		// chooser = new SendableChooser();
		// chooser.addDefault("Default Auto", new Autonomous());
		//// chooser.addObject("My Auto", new MyAutoCommand());
		// SmartDashboard.putData("Auto mode", chooser);
		swich = new DigitalInput(RobotMap.kFirstSwitchPort);
		swich2 = new DigitalInput(RobotMap.kSecondSwitchPort);

		auto1 = new Autonomous();
		auto2 = new Autonomous2();
		auto3 = new Autonomous3();
		auto4 = new Autonomous4();

		SmartDashboard.putData("timeoutAuto", new DriveTimeout(0.5, 0.5, 17));

		// testAuto = new DriveTimeout();

	}

	public Command selectAutonomous() {
		if (!swich.get() && !swich2.get()) {// both switches off
			autonomousCommand = auto1;
		} else if (swich.get() && !swich2.get()) {// switch 1 on
			autonomousCommand = auto2;
		} else if (!swich.get() && swich2.get()) {// switch 2 on
			autonomousCommand = auto3;
		} else if (swich.get() && swich2.get()) {// both switches on
			autonomousCommand = auto4;
		}
		return autonomousCommand;
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	public void autonomousInit() {

		if (auto1 != null)
			auto1.start();
	}
	// if (testAuto != null)
	// testAuto.start();
	// }

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (auto1 != null)
			auto1.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		log();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

	public void log() {
		intaker.log();
		drivetrain.log();

	}
}
