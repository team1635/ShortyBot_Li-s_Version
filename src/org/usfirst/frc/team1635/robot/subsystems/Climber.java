package org.usfirst.frc.team1635.robot.subsystems;

import org.usfirst.frc.team1635.robot.RobotMap;
import org.usfirst.frc.team1635.robot.commands.ClimbWithJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {
	SpeedController winch;
	Solenoid hookExtender, hookRaiser;
	
	public Climber(){
		winch = new TalonSRX(RobotMap.kWinchPort);

		hookExtender= new Solenoid(RobotMap.kHookExtenderPort);
		hookRaiser = new Solenoid(RobotMap.kHookRaiserPort);
		
	}
	
	public void operateWinch(Joystick joy3){
		double leftInput = joy3.getRawAxis(2);
		double rightInput = joy3.getRawAxis(3);
		double output = 0.0;
		
		if ((rightInput > 0.0) && (leftInput > 0.0)) {
			// both triggers are pressed so we shouldn't move the lift
			output = 0.0;
		} else {
			if (leftInput > 0) {
				// the right trigger should move the lift up.
				output = leftInput * -1;
			} else {
				// the left trigger should move the lift down
				output = rightInput;
			}
		}
		operateWinchFinal(output);
	}
	
	public void operateWinchFinal(double input2){
		winch.set(input2);		
	}
	
	public void liftHookExtender(Joystick joy_y){
		if (joy_y.getRawButton(7)){
			hookExtender.set(true);
		}else if(joy_y.getRawButton(8)){
			hookExtender.set(false);
		}	
		
	}
	
	public void liftHookRaiser(Joystick joy_z){
		if (joy_z.getRawButton(3)){
			hookRaiser.set(true);
		}else if(joy_z.getRawButton(2)){
			hookRaiser.set(false);
		}	
		
	}
	
	public void StopClimber(){
		winch.set(0);
	}
		
	
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ClimbWithJoystick());
    }
}

