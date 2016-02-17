package org.usfirst.frc.team1635.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * 
 * 
 * : @author: Jing Wei Li (SKRUB_HUNTER) , Miguel Cruz (@Acelogic_ )
 */
public class RobotMap {
	//PWM ports
	public static int kDriveTrain_FrontLeftMotorController = 1;//0
	public static int kDriveTrain_BackLeftMotorController = 0;//0 official
	public static int kDriveTrain_FrontRightMotorController = 3;//3
	public static int kDriveTrain_BackRightMotorController = 2;//2
	public static int kIntakerTalonPort = 6;//used to be 3
	public static int kLeftWinchPort = 4;//official
	public static int kRightWinchPort = 5;//official
	
	//constants
    public static int kPressureLimit = 15;
    
    //AnalogInputs
    public static int kPressureAnalogPort = 3;//0
    
   //solenoid ports on the PCM 
    public static int kIntakerLifterPort = 1;//solenoid
    public static int kHookExtenderPort = 3;//TODO used to be port 0
    public static int kHookRaiserPort = 2;
    public static int kGearShifterPort = 4;
    
    //DIO ports
    public static int kFirstSwitchPort = 0;
    public static int kSecondSwitchPort =1;
}
