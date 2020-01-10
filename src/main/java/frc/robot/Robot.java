/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C;
import com.revrobotics.ColorSensorV3;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";
    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser<>();
    private DriverStation driverStation;

    private XboxController controller;
    private SwerveDriveKinematics swerveKinematics;
    private CANSparkMax frontLeftSpin;
    private CANSparkMax frontLeftMove;
    private CANSparkMax frontRightSpin;
    private CANSparkMax frontRightMove;
    private CANSparkMax backLeftSpin;
    private CANSparkMax backLeftMove;
    private CANSparkMax backRightSpin;
    private CANSparkMax backRightMove;

    private ColorSensorV3 colorSensor;
    private final I2C.Port i2cPort = I2C.Port.kOnboard;

    private boolean rotationControl;
    private boolean positionControl;

    private TalonSRX colorMotor;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        m_chooser.addOption("My Auto", kCustomAuto);
        SmartDashboard.putData("Auto choices", m_chooser);
        driverStation = DriverStation.getInstance();

        controller = new XboxController(Config.Ports.PRIMARY_CONTROLLER);

        swerveKinematics = new SwerveDriveKinematics(
            new Translation2d(Config.Measures.Swerve.FRONT_LEFT_X, Config.Measures.Swerve.FRONT_LEFT_Y),
            new Translation2d(Config.Measures.Swerve.FRONT_RIGHT_X, Config.Measures.Swerve.FRONT_RIGHT_Y),
            new Translation2d(Config.Measures.Swerve.BACK_LEFT_X, Config.Measures.Swerve.BACK_LEFT_Y),
            new Translation2d(Config.Measures.Swerve.BACK_RIGHT_X, Config.Measures.Swerve.BACK_RIGHT_Y)
        );

        frontLeftSpin = new CANSparkMax(Config.Ports.Swerve.FRONT_LEFT_SPIN, MotorType.kBrushless);
        frontLeftMove = new CANSparkMax(Config.Ports.Swerve.FRONT_LEFT_MOVE, MotorType.kBrushless);
        frontRightSpin = new CANSparkMax(Config.Ports.Swerve.FRONT_RIGHT_SPIN, MotorType.kBrushless);
        frontRightMove = new CANSparkMax(Config.Ports.Swerve.FRONT_RIGHT_MOVE, MotorType.kBrushless);
        backLeftSpin = new CANSparkMax(Config.Ports.Swerve.BACK_LEFT_SPIN, MotorType.kBrushless);
        backLeftMove = new CANSparkMax(Config.Ports.Swerve.BACK_LEFT_MOVE, MotorType.kBrushless);
        backRightSpin = new CANSparkMax(Config.Ports.Swerve.BACK_RIGHT_SPIN, MotorType.kBrushless);
        backRightMove = new CANSparkMax(Config.Ports.Swerve.BACK_RIGHT_MOVE, MotorType.kBrushless);

        frontLeftSpin.setIdleMode(CANSparkMax.IdleMode.kBrake);
        frontLeftMove.setIdleMode(CANSparkMax.IdleMode.kBrake);
        frontRightSpin.setIdleMode(CANSparkMax.IdleMode.kBrake);
        frontRightMove.setIdleMode(CANSparkMax.IdleMode.kBrake);
        backLeftSpin.setIdleMode(CANSparkMax.IdleMode.kBrake);
        backLeftMove.setIdleMode(CANSparkMax.IdleMode.kBrake);
        backRightSpin.setIdleMode(CANSparkMax.IdleMode.kBrake);
        backRightMove.setIdleMode(CANSparkMax.IdleMode.kBrake);

        colorSensor = new ColorSensorV3(i2cPort); 
        rotationControl = false;
        positionControl = false;

        colorMotor = new TalonSRX(Config.Ports.COLOR_SENSOR); // port
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
        m_autoSelected = m_chooser.getSelected();
        // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
        System.out.println("Auto selected: " + m_autoSelected);
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        switch (m_autoSelected) {
            case kCustomAuto:
                // Put custom auto code here
                break;
            case kDefaultAuto:
            default:
                // Put default auto code here
                break;
        }
    }

    private Colors current;
    private boolean changedColor;
    private int semiCycleCount;

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        double leftX = controller.getX(Hand.kLeft);
        double leftY = controller.getY(Hand.kLeft);
        double rightX = controller.getX(Hand.kRight);
        // double rightY = controller.getY(Hand.kRight);

        ChassisSpeeds speeds = new ChassisSpeeds(
            leftY * Config.Measures.SwerveSpeeds.MOVE,
            leftX * Config.Measures.SwerveSpeeds.MOVE,
            rightX * Config.Measures.SwerveSpeeds.SPIN
        );
        SwerveModuleState[] moduleStates = swerveKinematics.toSwerveModuleStates(speeds);
        SwerveModuleState frontLeftState = moduleStates[0];
        SwerveModuleState frontRightState = moduleStates[1];
        SwerveModuleState backLeftState = moduleStates[2];
        SwerveModuleState backRightState = moduleStates[3];

        // TODO drive motors with encoders

        Color color = new Color(colorSensor.getColor().red, colorSensor.getColor().green, colorSensor.getColor().blue);

        if (controller.getXButton() && !positionControl) {
            rotationControl = true;
            current = color.getBestColorMatch();
            semiCycleCount = 0;
            changedColor = false;
            colorMotor.set(ControlMode.PercentOutput, 1);
        }

        if (controller.getYButton() && !rotationControl) {
            positionControl = true;
            colorMotor.set(ControlMode.PercentOutput, Color.getClosest(color.getBestColorMatch(), Colors.getColor(driverStation.getGameSpecificMessage())));
        }

        if (rotationControl && !changedColor && color.getBestColorMatch() != current) {
            changedColor = true;
        } else if (rotationControl && changedColor && color.getBestColorMatch() == current) {
            semiCycleCount++;
            changedColor = false;
        }

        if (rotationControl && semiCycleCount == 8) {
            rotationControl = false;
            colorMotor.set(ControlMode.PercentOutput, 0);
        }

        if (positionControl && color.getBestColorMatch().label == driverStation.getGameSpecificMessage()) {
            colorMotor.set(ControlMode.PercentOutput, 0);
        }
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}
