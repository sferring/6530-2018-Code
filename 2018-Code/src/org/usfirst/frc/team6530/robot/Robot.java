package org.usfirst.frc.team6530.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	// Setup the Driver Joystick/gamepad to port 0
	Joystick driverStick = new Joystick(0);
	// Setup the drive speed controllers. Since we split the wires to the speed controllers, the roborio should think we just have one
	Victor leftFrontDrive = new Victor(0);
	Victor rightFrontDrive = new Victor(1);
	// Setup a button function on a Victor so we first define the victor
	Victor gearSpit = new Victor(4);
	// Setup the compressor
	Compressor c = new Compressor(0);
	
	// Setup a button function for a solenoid so we first define the solenoids on ports 1 and 2
	Solenoid pistonExtend = new Solenoid(1);
	Solenoid pistonRetract = new Solenoid(2);
	/*
	There is also a DoubleSolenoid class I don't know how to use those yet, but here is the structure
	DoubleSolenoid exampleDouble = new DoubleSolenoid(1, 2);

	exampleDouble.set(DoubleSolenoid.Value.kOff);
	exampleDouble.set(DoubleSolenoid.Value.kForward);
	exampleDouble.set(DoubleSolenoid.Value.kReverse);
	*/
	
	//Setup strings for picking Auto code from the driver station... I think
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();

	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//Main code for driving with gamepad that runs approximately every 20 miliseconds
		//Get joystick Values assuming left = axis 1 and right = axis 3. These values can be verified on the drivers station.
		/* Definition of the X box controller
		 * left stick side to side = axis 0
		 * left stick up and down = axis 1
		 * left trigger = axis 2 
		 * right trigger = axis 3
		 * right stick side to side = axis 4
		 * right stick up and down = axis 5
		 * Xbox Button A = button 0
		 * Xbox Button B = button 1
		 * Xbox Button X = button 2
		 * Xbox Button Y = button 3
		 * Xbox Button Left Bumper = button 4
		 * Xbox Button Right Bumper = button 5
		 */
		double leftStickValue = driverStick.getRawAxis(1);
		double rightStickValue = driverStick.getRawAxis(5);
		
		//Send Joystick values to the driver motors
		leftFrontDrive.set(leftStickValue);
		rightFrontDrive.set(rightStickValue);
		
		// I think we still need to turn on the compressor, so to do that we do this:
		c.setClosedLoopControl(true);
		
		//If statement to run a victor from port 4 (defined above) from button 1, defined here
		if (driverStick.getRawButton(1))
		{
			gearSpit.set(0.5);
		}
		else
		{
			gearSpit.set(0);
		}
		
		// If statement to fire a selonoid
		if (driverStick.getRawButton(3))
		{
			pistonExtend.set(true);
			pistonRetract.set(false);
		}
		else
		{
			pistonExtend.set(false);
			pistonRetract.set(true);	
		}
		// They recommend you turn both to false with a final else, but I did not do that to see what happens here.
	
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

