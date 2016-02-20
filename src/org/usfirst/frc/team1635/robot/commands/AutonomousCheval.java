package org.usfirst.frc.team1635.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author: Jing Wei Li (SKRUB_HUNTER) , Miguel Cruz (@Acelogic_ )
 */
public class AutonomousCheval extends CommandGroup {
    
    public  AutonomousCheval() {
    	
    	addSequential(new DriveTimeout(0.75, 3));// drive to the ball
    	addSequential(new RollIn_OutTheBall(true));//pick up the ball
    	addSequential(new Raise_LowerIntaker(true));//raise intaker
    	addSequential(new DriveTimeout(0.75, 3));//drive to the cheval
    	addSequential(new Raise_LowerIntaker(false));//bring down the cheval
    	addSequential(new DriveTimeout(0.75, 3));//drive over the cheval
    	addSequential(new RotateToSetPoint(45, false));// rotate to aim at the target
    	addSequential(new Raise_LowerIntaker(false));//lower the intaker
    	addSequential(new RollIn_OutTheBall(false));// shoot out the ball
    	        // Add Commands here:
        //addSequential(new Command1());
        //addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
