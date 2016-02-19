
package org.usfirst.frc.team1635.robot.subsystems;

import org.usfirst.frc.team1635.robot.RobotMap;
import org.usfirst.frc.team1635.robot.commands.DriveWithJoystick;

import NavxMXP.AHRS;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @Authors : Jing Wei Li (SKRUB_HUNTER) , Miguel Cruz (@Acelogic_ )
 */
public class DriveTrain extends Subsystem {
	private SpeedController frontLeft, backLeft, frontRight, backRight;
	private RobotDrive drive;
	private Solenoid gearShifter;
	SerialPort serial_port;
	double degrees, DistanceToStop;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	AHRS imu; // This class can only be used w/the navX MXP.
	boolean first_iteration, onTarget, direction;

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

		gearShifter = new Solenoid(RobotMap.kGearShifterPort);

		LiveWindow.addActuator("Drive Train", "Front_Left Motor", (Victor) frontLeft);
		LiveWindow.addActuator("Drive Train", "Back Left Motor", (Victor) backLeft);
		LiveWindow.addActuator("Drive Train", "Front Right Motor", (Victor) frontRight);
		LiveWindow.addActuator("Drive Train", "Back Right Motor", (Victor) backRight);

		try {
			serial_port = new SerialPort(57600, SerialPort.Port.kMXP);

			byte update_rate_hz = 50;
			// imu = new IMU(serial_port,update_rate_hz);
			// imu = new IMUAdvanced(serial_port,update_rate_hz);
			imu = new AHRS(serial_port, update_rate_hz);
		} catch (Exception ex) {
			ex.printStackTrace();

		}

		if (imu != null) {
			LiveWindow.addSensor("IMU", "Gyro", imu);
		}
		first_iteration = true;

		// When calibration has completed, zero the yaw
		// Calibration is complete approaximately 20 seconds
		// after the robot is powered on. During calibration,
		// the robot should be still

		boolean is_calibrating = imu.isCalibrating();
		if (first_iteration && !is_calibrating) {
			Timer.delay(0.3);
			imu.zeroYaw();
			first_iteration = false;
		}

	}

	/**
	 * f you if you don't understand
	 * 
	 * @return gyroscope angles
	 */
	public double obtainYaw() {
		return imu.getYaw();
	}

	public void log() {
		SmartDashboard.putBoolean("gearStatus", gearShifter.get());
	}

	public void drive(Joystick joy) {
		// driveSquared( -1 * joy.getY() , -1 * joy.getRawAxis(5) * 0.9);
		drive.tankDrive(-joy.getY(), -joy.getRawAxis(5));
	}

	/*
	 * unlimited drive for timeouts
	 */
	public void unlimitedDrive(double leftspd, double rightspd) {
		drive.tankDrive(-leftspd, -rightspd);
	}

	/**
	 * 
	 * @param REKTstick
	 *            Most Horrible Driving Method Yet IGN-1000/10
	 *            "Horrible Never Again" - Forbes
	 */
	public void Chrys_s_DeeznutsIllegalDriveMethod(Joystick REKTstick) {
		drive.tankDrive(REKTstick.getY(), REKTstick.getRawAxis(5));

	}

	public void stopDrive() {
		drive.tankDrive(0, 0);
	}

	public void driveWithParameters(double left, double right) {
		drive.tankDrive(left, right);
	}

	public void shiftGearsTwoButton(Joystick joy) {
		if (joy.getRawButton(9)) {
			gearShifter.set(false);
		} else if (joy.getRawButton(10)) {
			gearShifter.set(true);
		}
	}

	public void getGearStatus() {
		if (!gearShifter.get()) {
			System.out.println("high gear");
			// SmartDashboard.put
		} else {
			System.out.println("low gear");
		}
	}

	public void shiftGears(Joystick stick) {// button x
		if (stick.getRawButton(3)) {
			if (gearShifter.get()) {
				gearShifter.set(false);
			} else if (!gearShifter.get()) {
				gearShifter.set(true);
			}
		}
	}

	public boolean isOnTarget() {
		return onTarget;
	}

	/**
	 * set the direction and degrees for rotation
	 * 
	 * @param deg
	 *            angles for rotation
	 * @param dir
	 *            direction for rotation: true - clockwise, false -
	 *            counterClockwise
	 */
	public void setRotation(double deg, boolean dir) {
		imu.zeroYaw();
		this.degrees = deg;
		this.direction = dir;
	}

	/**
	 * rotate to a certain angle
	 */
	public void AngularRotation() {
		onTarget = false;
		if (direction) {// turn to the right
			if (obtainYaw() < degrees + 1.5 && obtainYaw() > degrees - 1.5) {
				drive.tankDrive(0, 0);
				onTarget = true;
			} else {
				drive.tankDrive(-0.23, 0.23);
			}
		} else if (!direction) {// turn to the left
			double inverted = - degrees;
			if (obtainYaw() < inverted + 1.5 && obtainYaw() > inverted - 1.5) {
				drive.tankDrive(0, 0);
				onTarget = true;
			} else {
				drive.tankDrive(0.23, -0.23);
			}
		}
	}

	/**
	 * Drive while maintaining the correct direction with the gyro on the NavX
	 */
	public void correctWhileDriving() {
		if (obtainYaw() > 0) {
			if (obtainYaw() < 1.5 && obtainYaw() > 0) {
				drive.tankDrive(-0.23, -0.23);
			} else if (obtainYaw() > 1.5 && obtainYaw() < 4) {
				drive.tankDrive(0.15, -0.15);
			} else if (obtainYaw() > 4) {
				drive.tankDrive(0.23, -0.23);
			}
		} else if (obtainYaw() < 0) {
			if (obtainYaw() > -1.5 && obtainYaw() < 0) {
				drive.tankDrive(-0.23, -0.23);
			} else if (obtainYaw() < -1.5 && obtainYaw() > -4) {
				drive.tankDrive(-0.15, 0.15);
			} else if (obtainYaw() < -4) {
				drive.tankDrive(-0.23, 0.23);
			}
		}
	}
	
	public double convertNavXtoInches(){
		double inches = imu.getDisplacementX() * 1.116;
		return inches;
	}

	public void setDistToStop(double dist_) {
		this.DistanceToStop = dist_;
	}

	public void NavxDriveToSetPoint() {
		//double dist = imu.getDisplacementX();
		double dist = convertNavXtoInches();
		SmartDashboard.putNumber("autonomous distance", dist);

		if (dist >= DistanceToStop) {
			drive.tankDrive(0, 0);
			onTarget = true;
		} else {
			drive.tankDrive(-0.3, -0.3);
		}
	}
	public void brake() {
		drive.tankDrive(0.4, 0.4);
		Timer.delay(0.2);
		drive.tankDrive(0, 0);
	}
 
	public void unlimitedDrive(double spd) {
		drive.tankDrive(spd, spd);
		SmartDashboard.putNumber("vertical displacement", imu.getDisplacementY());
	}

}
