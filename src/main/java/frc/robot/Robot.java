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

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void autonomousInit() {
        selectedAutoName = AUTO_CHOOSER.getSelected();
        // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
        System.out.println("Auto selected: " + selectedAutoName);
    }

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
//    boolean doneDriving = false;

    @Override
    public void teleopPeriodic() {
        teleop.periodic();
//        collectorTalon.set(ControlMode.PercentOutput, 0.65 * controls.getIntakeSpeed());
//        Shooter.getInstance().drive();

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

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

}
