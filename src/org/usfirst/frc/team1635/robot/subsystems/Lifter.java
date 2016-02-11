package org.usfirst.frc.team1635.robot.subsystems;

import org.usfirst.frc.team1635.robot.RobotMap;
import org.usfirst.frc.team1635.robot.commands.ClimbWithJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author : Jing Wei Li (SKRUB_HUNTER) , Miguel Cruz (@Acelogic_ )
 */
public class Lifter extends Subsystem {
	SpeedController leftWinch,rightWinch;
	Solenoid hookExtender, hookRaiser;
	
	public Lifter(){
		leftWinch = new TalonSRX(RobotMap.kLeftWinchPort);
		rightWinch = new TalonSRX(RobotMap.kRightWinchPort);

		hookExtender= new Solenoid(RobotMap.kHookExtenderPort);
		hookRaiser = new Solenoid(RobotMap.kHookRaiserPort);
	}
	
	public void operateWinch(Joystick joy3){
		double output = 0;
		//double leftInput = joy3.getRawAxis(2);
//		double rightInput = joy3.getRawAxis(3);
//		double output = 0.0;
//		
//		if ((rightInput > 0.0) && (leftInput > 0.0)) {
//			// both triggers are pressed so we shouldn't move the lift
//			output = 0.0;
//		} else {
//			if (leftInput > 0) {
//				// the right trigger should move the lift up.
//				output = leftInput * -1;
//			} else if(rightInput > 0){
//				// the left trigger should move the lift down
//				output = rightInput;
//			}
//		}
		if(joy3.getRawButton(8)){
			output = 1;
		}
		operateWinchFinal(output);
	}
	
	public void operateWinchFinal(double input2){
		leftWinch.set(input2);	
		rightWinch.set(input2);
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
		leftWinch.set(0);
		rightWinch.set(0);
	}
	
	public void extendHookOneButton(Joystick MLG_exe){
		if (MLG_exe.getRawButton(7)){
			if(hookExtender.get()){
				hookExtender.set(false);
			}else if(!hookExtender.get()){
				hookExtender.set(true);
			}
		}	
	}
	
	public void raiseHookOneButton(Joystick joy){
		if(joy.getRawButton(2)){
			if(hookRaiser.get()){
				hookRaiser.set(false);
			}else if (!hookRaiser.get()){
				hookRaiser.set(true);
			}
		}
	}
		
	public void log() { 
		SmartDashboard.putBoolean("IsExtended", hookExtender.get());		
	}
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ClimbWithJoystick());
    }
}

