package org.usfirst.frc.team1635.robot.subsystems;

import org.usfirst.frc.team1635.robot.Robot;
import org.usfirst.frc.team1635.robot.RobotMap;
import org.usfirst.frc.team1635.robot.commands.IntakeWithJoystick;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Intaker extends Subsystem {
	TalonSRX talon;
	AnalogInput pressuresensor;
	Solenoid IntakerLifter;

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

	}
	
	public void log(){
		SmartDashboard.putNumber("Pressurelevel", obtainPressureLevel());
		
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
				// the right trigger should move the lift up.
				output = -1;
				
			} else if (rightInput){
				// the left trigger should move the lift down
				output = 1;
			}
		}
		PressureCheck(output,joy1);
	}

	public void PressureCheck(double input,Joystick joy_x) {

		double outputspd = 0.0;

		if (obtainPressureLevel() > RobotMap.kPressureLimit && joy_x.getRawButton(5)) {
			outputspd = 0.0;
		} else {
			outputspd = input;
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
	
	
	public void liftIntaker(Joystick joy2){
		if (joy2.getRawButton(4)){
			IntakerLifter.set(true);
		}else if(joy2.getRawButton(1)){
			IntakerLifter.set(false);
		}	
	}
	
	public void stopIntaker() {
		talon.set(0);
	}
}
