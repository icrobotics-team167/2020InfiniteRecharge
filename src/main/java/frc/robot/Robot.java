/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.controls.controllers.Controller;
import frc.robot.controls.controllers.PSController;
import frc.robot.controls.controllers.XBController;
import frc.robot.controls.inputs.ControlScheme;
import frc.robot.controls.inputs.DoubleController;
import frc.robot.controls.inputs.NullController;
import frc.robot.controls.inputs.SingleController;
import frc.robot.routines.Teleop;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.drive.TalonTankDriveBase;

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
    private ControlScheme controls;
    private Teleop teleop;

    private TalonSRX collectorTalon;

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

        Controller primaryController = null;
        switch (Config.Settings.PRIMARY_CONTROLLER_TYPE) {
            case XB:
                primaryController = new XBController(Config.Ports.PRIMARY_CONTROLLER);
                break;
            case PS:
                primaryController = new PSController(Config.Ports.PRIMARY_CONTROLLER);
                break;
            case NONE:
                primaryController = null;
                break;
        }
        Controller secondaryController = null;
        switch (Config.Settings.SECONDARY_CONTROLLER_TYPE) {
            case XB:
                secondaryController = new XBController(Config.Ports.SECONDARY_CONTROLLER);
                break;
            case PS:
                secondaryController = new PSController(Config.Ports.SECONDARY_CONTROLLER);
                break;
            case NONE:
                secondaryController = null;
                break;
        }
        if (primaryController == null && secondaryController == null) {
            controls = new NullController();
        } else if (primaryController != null && secondaryController == null) {
            controls = new SingleController(primaryController);
        } else if (primaryController != null && secondaryController != null) {
            controls = new DoubleController(primaryController, secondaryController);
        } else {
            // Fallback
            // This should be unreachable in normal conditions
            // This could only occur if the secondary controller is configured but the primary controller isn't
            controls = new NullController();
        }

        teleop = new Teleop(controls);

        collectorTalon = new TalonSRX(14);

        TalonTankDriveBase.getInstance();
        Shooter.getInstance();
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

    @Override
    public void teleopInit() {
        teleop.init();
    }

    //    private Colors current;
//    private boolean changedColor;
//    private int semiCycleCount;

    boolean doneDriving = false;
    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        teleop.periodic();
//        collectorTalon.set(ControlMode.PercentOutput, 0.65 * controls.getIntakeSpeed());
//        if (controls.getClockwiseTurretSpeed() >= 0.03) {
//            Turret.getInstance().turnClockwise(controls.getClockwiseTurretSpeed());
//        } else if (controls.getCounterClockwiseTurretSpeed() > 0.03) {
//            Turret.getInstance().turnCounterclockwise(controls.getCounterClockwiseTurretSpeed());
//        } else if (controls.trackTarget()) {
//            Turret.getInstance().trackTarget();
//        } else {
//            Turret.getInstance().stopTracking();
//        }
//        Shooter.getInstance().drive();

        // teleop.exec();
        // frontRightMove.set(1);
        // if (!doneDriving) {
        //     doneDriving = SwerveDriveBase.getInstance().driveMeter();
        // }

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
