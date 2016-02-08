package org.usfirst.frc.team1635.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//PWM ports
	public static int kDriveTrain_FrontLeftMotorController = 6;//0
	public static int kDriveTrain_BackLeftMotorController = 2;
	public static int kDriveTrain_FrontRightMotorController = 1;//1
	public static int kDriveTrain_BackRightMotorController = 3;//3
	public static int kIntakerTalonPort = 0;//
	public static int kLeftWinchPort = 4;
	public static int kRightWinchPort = 5;
	
	//constants
    public static int kPressureLimit = 15;
    
    //AnalogInputs
    public static int kPressureAnalogPort = 3;//0
    
   //solenoid ports on the PCM 
    public static int kIntakerLifterPort = 1;//solenoid
    public static int kHookExtenderPort = 0;//TODO
    public static int kHookRaiserPort = 2;
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
