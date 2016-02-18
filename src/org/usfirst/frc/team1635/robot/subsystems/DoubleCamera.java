package org.usfirst.frc.team1635.robot.subsystems;


import org.usfirst.frc.team1635.robot.Robot;
import org.usfirst.frc.team1635.robot.RobotMap;
import org.usfirst.frc.team1635.robot.commands.DualCameras;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DoubleCamera extends Subsystem {
	private int camCenter;
	private int camRight;
	private int curCam;
	private Image frame;
	private CameraServer server;
	private Joystick stick;
	boolean cameraStatus;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public DoubleCamera(){
		
		stick = Robot.oi.getJoystick();
		// Get camera ids by supplying camera name ex 'cam0', found on roborio web interface
        camCenter = NIVision.IMAQdxOpenCamera(RobotMap.camNameCenter, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        camRight = NIVision.IMAQdxOpenCamera(RobotMap.camNameRight, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        curCam = camCenter;
        // Img that will contain camera img
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        // Server that we'll give the img to
        server = CameraServer.getInstance();
        server.setQuality(RobotMap.imgQuality);
        server.setSize(0);// limit the resolution to 160*120
        
        
	}
	
	public void StartCam0(){
		server = CameraServer.getInstance();
		server.setQuality(15);		
		server.startAutomaticCapture("cam0");;
		
	}
	public void StartCam1(){
		server = CameraServer.getInstance();
		server.setQuality(15);		
		server.startAutomaticCapture("cam1");
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DualCameras());
    }
    public void init()
	{
    	
		changeCam(camCenter);
	}
	
	public void run()
	{
		if(stick.getRawButton(2))
			changeCam(camCenter);
		
		if(stick.getRawButton(1))
			changeCam(camRight);
		
		updateCam();
	}
	
	
	
	public void runWithOneButton(){
		boolean camSwitch = false;
		boolean toggle = true;		
		cameraStatus = stick.getRawButton(2);        
		 
		if (toggle && cameraStatus) {  // Only execute once per Button push
		  toggle = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
		  if (camSwitch) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
		    camSwitch= false;
		    changeCam(camCenter);
		  } else {
		    camSwitch= true;
		    changeCam(camRight);
		  }
		} else if(!cameraStatus) { 
		    toggle = true; // Button has been released, so this allows a re-press to activate the code above.
		}
		updateCam();
		
	}
	
	public void runCamZero(){
		changeCam(camCenter);
		updateCam();
	}
	
	public void runCamOne(){
		changeCam(camRight);
		updateCam();
	}
	
	/**
	 * Stop aka close camera stream
	 */
	public void end()
	{
		NIVision.IMAQdxStopAcquisition(curCam);
		
	}
	
	/**
	 * Change the camera to get imgs from to a different one
	 * @param newId for camera
	 */
	public void changeCam(int newId)
    {
		NIVision.IMAQdxStopAcquisition(curCam);
    	NIVision.IMAQdxConfigureGrab(newId);
    	NIVision.IMAQdxStartAcquisition(newId);
    	curCam = newId;
    }
    
	/**
	 * Get the img from current camera and give it to the server
	 */
    public void updateCam()
    {
    	NIVision.IMAQdxGrab(curCam, frame, 1);
        server.setImage(frame);
    }
}

