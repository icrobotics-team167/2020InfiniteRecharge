/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.controllers.Controller;
import frc.robot.controllers.SingleXboxController;
import frc.robot.routines.teleop.SwerveTeleop;
import frc.robot.routines.teleop.TankTeleop;
import frc.robot.routines.teleop.Teleop;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private static final String DEFAULT_AUTO_NAME = "Default";
    private static final String CUSTOM_AUTO_NAME = "My Auto";
    private String selectedAutoName;
    private final SendableChooser<String> AUTO_CHOOSER = new SendableChooser<>();

    private DriverStation driverStation;
    private Controller controller;
    private Teleop teleop;

//    private XboxController controller;
//
//    private ColorSensorV3 colorSensor;
//    private final I2C.Port i2cPort = I2C.Port.kOnboard;
//
//    private boolean rotationControl;
//    private boolean positionControl;
//
//    private TalonSRX colorMotor;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        AUTO_CHOOSER.setDefaultOption("Default Auto", DEFAULT_AUTO_NAME);
        AUTO_CHOOSER.addOption("My Auto", CUSTOM_AUTO_NAME);
        SmartDashboard.putData("Auto choices", AUTO_CHOOSER);

        driverStation = DriverStation.getInstance();
        controller = new SingleXboxController();
        teleop = Config.SWERVE_ENABLED ? new SwerveTeleop(controller) : new TankTeleop(controller);

//        controller = new XboxController(Config.Ports.PRIMARY_CONTROLLER);
//
//        colorSensor = new ColorSensorV3(i2cPort);
//        rotationControl = false;
//        positionControl = false;
//
//        colorMotor = new TalonSRX(Config.Ports.COLOR_SENSOR); // port
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString line to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional comparisons to
     * the switch structure below with additional strings. If using the
     * SendableChooser make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() {
        selectedAutoName = AUTO_CHOOSER.getSelected();
        // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
        System.out.println("Auto selected: " + selectedAutoName);
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        switch (selectedAutoName) {
            case CUSTOM_AUTO_NAME:
                // Put custom auto code here
                break;
            case DEFAULT_AUTO_NAME:
            default:
                // Put default auto code here
                break;
        }
    }

//    private Colors current;
//    private boolean changedColor;
//    private int semiCycleCount;

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        teleop.exec();

//        Color color = new Color(colorSensor.getColor().red, colorSensor.getColor().green, colorSensor.getColor().blue);
//
//        if (controller.getXButton() && !positionControl) {
//            rotationControl = true;
//            current = color.getBestColorMatch();
//            semiCycleCount = 0;
//            changedColor = false;
//            colorMotor.set(ControlMode.PercentOutput, 1);
//        }
//
//        if (controller.getYButton() && !rotationControl) {
//            positionControl = true;
//            colorMotor.set(ControlMode.PercentOutput, Color.getClosest(color.getBestColorMatch(), Colors.getColor(driverStation.getGameSpecificMessage())));
//        }
//
//        if (rotationControl && !changedColor && color.getBestColorMatch() != current) {
//            changedColor = true;
//        } else if (rotationControl && changedColor && color.getBestColorMatch() == current) {
//            semiCycleCount++;
//            changedColor = false;
//        }
//
//        if (rotationControl && semiCycleCount == 8) {
//            rotationControl = false;
//            colorMotor.set(ControlMode.PercentOutput, 0);
//        }
//
//        if (positionControl && color.getBestColorMatch().label == driverStation.getGameSpecificMessage()) {
//            colorMotor.set(ControlMode.PercentOutput, 0);
//        }
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}
