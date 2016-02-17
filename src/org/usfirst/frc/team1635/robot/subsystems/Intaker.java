package org.usfirst.frc.team1635.robot.subsystems;

import org.usfirst.frc.team1635.robot.RobotMap;
import org.usfirst.frc.team1635.robot.commands.IntakeWithJoystick;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author : Jing Wei Li (SKRUB_HUNTER) , Miguel Cruz (@Acelogic_ )
 */
public class Intaker extends Subsystem {
	TalonSRX talon;
	AnalogInput pressuresensor;
	Solenoid IntakerLifter;
	boolean onTarget;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new IntakeWithJoystick());
	}

	public Intaker() {

		talon = new TalonSRX(RobotMap.kIntakerTalonPort);
		pressuresensor = new AnalogInput(RobotMap.kPressureAnalogPort);
		IntakerLifter = new Solenoid(RobotMap.kIntakerLifterPort);
		
		onTarget = false;

	}

	public double ObtainTalonLastSetValue() { // Made by miguel
		double getTalonValue = talon.get();
		return getTalonValue;

	}

	public void log() {
		SmartDashboard.putNumber("Pressurelevel", obtainPressureLevel());
		SmartDashboard.putNumber("TalonSRXlastSetValue", ObtainTalonLastSetValue());

	}

	public void Operate(Joystick joy1) {
		boolean leftInput = joy1.getRawButton(5);
		boolean rightInput = joy1.getRawButton(6);
		double output = 0.0;

		if (leftInput && rightInput) {
			// both triggers are pressed so we shouldn't move the roller
			output = 0.0;
		} else {
			if (leftInput) {
				// the left button LB should roll the ball in
				output = -1;

			} else if (rightInput) {
				// the right button RB should roll the ball out
				output = 1;
			}
		}
		PressureCheck(output, joy1);
	}

	public void PressureCheck(double input, Joystick joy_x) {

		double outputspd = 0.0;

		if (obtainPressureLevel() < RobotMap.kPressureLimit && joy_x.getRawButton(5)) {
			Timer.delay(0.05);// delay the locking mechanism to allow more grip
								// on the ball
			// for debugging System.out.println("Pressure Detected");
			outputspd = 0.0;
			raiseIntaker();
		} else {
			outputspd = input;
			// for debugging System.out.println("Pressure null");
		}

		operateIntaker(outputspd);
	}

	public void operateIntaker(double finalInput) {
		talon.set(finalInput);
	}

	/*
	 * obtain the pressure from the pressure sensor
	 */
	public double obtainPressureLevel() {
		double pressureLevel = pressuresensor.getValue();
		return pressureLevel;
	}

	public void liftIntaker(Joystick joy2) {
		if (joy2.getRawButton(4)) {
			IntakerLifter.set(true);
		} else if (joy2.getRawButton(1)) {
			IntakerLifter.set(false);
		}
	}

	public void intakerLiftOneButton(Joystick joyy) {
		if (joyy.getRawButton(4)) {
			if (IntakerLifter.get()) {
				IntakerLifter.set(false);
			} else if (!IntakerLifter.get()) {
				IntakerLifter.set(true);
			}
		}
	}
	
	public void rollIn(){
		if(obtainPressureLevel() < RobotMap.kPressureLimit){
			Timer.delay(0.05);
			talon.set(0);
			onTarget =true;
		} else {
			talon.set(-1);
		}
	}
	/**
	 * supposed to work with timeouts
	 */
	public void rollOut(){
		//double time = System.currentTimeMillis();
		talon.set(-1);
		Timer.delay(1);
		talon.set(0);
		onTarget = true;
	}

	public void raiseIntaker() {
		IntakerLifter.set(true);
		if(IntakerLifter.get()){
			onTarget = true;
		}
	}

	public void lowerIntaker() {
		IntakerLifter.set(false);
		if(!IntakerLifter.get()){
			onTarget = true;
		}
	}
	public boolean isOnTarget(){
		return onTarget;		
	}

	public void stopIntaker() {
		talon.set(0);
	}
}
