package frc.robot.subsystems.drive;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Config;

public class SparkTankDriveBase implements TankDriveBase {

    private AHRS navx;
    private CANSparkMax leftMaster;
    private CANSparkMax leftSlave1;
    private CANSparkMax leftSlave2;
    private CANSparkMax rightMaster;
    private CANSparkMax rightSlave1;
    private CANSparkMax rightSlave2;
    private CANEncoder leftEncoder;
    private CANEncoder rightEncoder;
    private boolean straightDriving;
    private double straightDriveAngleSetpoint;
    private PIDController straightDrivePID;
    private final double STRAIGHT_DRIVE_KP = 0.015; // 0.02 works
    private final double STRAIGHT_DRIVE_KI = 0;
    private final double STRAIGHT_DRIVE_KD = 0;
    private CANPIDController leftPID;
    private final double LEFT_KP = 0.002; // last tried: 0.0001
    private final double LEFT_KI = 0;
    private final double LEFT_KD = 0;
    private final double LEFT_KF = 0.000268; // with slower paths?: 0.00075
    private CANPIDController rightPID;
    private final double RIGHT_KP = 0.0015; // last tried: 0.0001
    private final double RIGHT_KI = 0;
    private final double RIGHT_KD = 0;
    private final double RIGHT_KF = 0.000232; // with slower paths?: 0.00075
    private DoubleSolenoid doubleSolenoid;
    private boolean highGear;

    // Singleton
    private static SparkTankDriveBase instance;
    public static SparkTankDriveBase getInstance() {
        if (instance == null) {
            instance = new SparkTankDriveBase();
        }
        return instance;
    }

    private SparkTankDriveBase() {
        try {
            navx = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException e) {
            DriverStation.reportError("Error initializing the navX over SPI: " + e.toString(), e.getStackTrace());
        }

        leftMaster = new CANSparkMax(Config.Ports.SparkTank.LEFT_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSlave1 = new CANSparkMax(Config.Ports.SparkTank.LEFT_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSlave2 = new CANSparkMax(Config.Ports.SparkTank.LEFT_3, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMaster = new CANSparkMax(Config.Ports.SparkTank.RIGHT_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSlave1 = new CANSparkMax(Config.Ports.SparkTank.RIGHT_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSlave2 = new CANSparkMax(Config.Ports.SparkTank.RIGHT_3, CANSparkMaxLowLevel.MotorType.kBrushless);

        leftMaster.restoreFactoryDefaults();
        rightMaster.restoreFactoryDefaults();
        leftMaster.setInverted(true);
        leftEncoder = leftMaster.getEncoder(EncoderType.kHallSensor, 4096);
        rightEncoder = rightMaster.getEncoder(EncoderType.kHallSensor, 4096);
        leftMaster.setSmartCurrentLimit(80);
        leftMaster.setSecondaryCurrentLimit(60);
        rightMaster.setSmartCurrentLimit(80);
        rightMaster.setSecondaryCurrentLimit(60);
        leftMaster.setOpenLoopRampRate(0);
        leftMaster.setClosedLoopRampRate(0);
        rightMaster.setOpenLoopRampRate(0);
        rightMaster.setClosedLoopRampRate(0);

        straightDrivePID = new PIDController(STRAIGHT_DRIVE_KP, STRAIGHT_DRIVE_KI, STRAIGHT_DRIVE_KD);
        straightDrivePID.setTolerance(0.4);

        leftPID = leftMaster.getPIDController();
        leftPID.setP(LEFT_KP);
        leftPID.setI(LEFT_KI);
        leftPID.setD(LEFT_KD);
        leftPID.setFF(LEFT_KF);
        rightPID = rightMaster.getPIDController();
        rightPID.setP(RIGHT_KP);
        rightPID.setI(RIGHT_KI);
        rightPID.setD(RIGHT_KD);
        rightPID.setFF(RIGHT_KF);

        leftSlave1.follow(leftMaster);
        leftSlave2.follow(leftMaster);
        rightSlave1.follow(rightMaster);
        rightSlave2.follow(rightMaster);

        doubleSolenoid = new DoubleSolenoid(
            Config.Settings.SPARK_TANK_ENABLED ? Config.Ports.SparkTank.PCM : Config.Ports.TalonTank.PCM,
            Config.Ports.SparkTank.SOLENOID_FORWARD,
            Config.Ports.SparkTank.SOLENOID_REVERSE
        );

        highGear = false;
    }

    @Override
    public void tankDrive(double leftSpeed, double rightSpeed) {
        rightMaster.set(0.976 * -leftSpeed);
        leftMaster.set(-rightSpeed);
        straightDriving = false;
    }

    @Override
    public void toggleGearing() {
        if (highGear) {
            setLowGear();
        } else {
            setHighGear();
        }
    }

    @Override
    public void setHighGear() {
        doubleSolenoid.set(DoubleSolenoid.Value.kForward);
        highGear = true;
    }

    @Override
    public void setLowGear() {
        doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
        highGear = false;
    }

    @Override
    public boolean isHighGear() {
        return highGear;
    }

    @Override
    public boolean isLowGear() {
        return !highGear;
    }

    @Override
    public void straightDrive(double speed) {
        straightDrive(speed, true);
    }

    @Override
    public void straightDrive(double speed, boolean newAngle) {
        if (!straightDriving) {
            straightDriving = true;
            straightDriveAngleSetpoint = newAngle ? navx.getAngle() : straightDriveAngleSetpoint;
            straightDrivePID.setSetpoint(straightDriveAngleSetpoint);
        }

        double error = straightDrivePID.calculate(navx.getAngle());
        leftMaster.set(MathUtil.clamp(speed + error, -1, 1));
        rightMaster.set(MathUtil.clamp(0.976 * speed - error, -1, 1));
    }

    @Override
    public void stop() {
        tankDrive(0, 0);
    }

    @Override
    public void pointTurn(double speed) {
        leftMaster.set(speed);
        rightMaster.set(-speed);
    }

    @Override
    public double getAngle() {
        return navx.getAngle();
    }

    @Override
    public double getLeftEncoderPosition() {
        double encoderValue = leftEncoder.getPosition();

        if (highGear) {
            return Units.inchesToMeters(encoderValue * 5 * Math.PI / 4.17);
        } else {
            return Units.inchesToMeters(encoderValue * 5 * Math.PI / 11.03);
        }
    }

    @Override
    public double getRightEncoderPosition() {
        double encoderValue = rightEncoder.getPosition();

        if (highGear) {
            return Units.inchesToMeters(encoderValue * 5 * Math.PI / 4.17);
        } else {
            return Units.inchesToMeters(encoderValue * 5 * Math.PI / 11.03);
        }
    }

    @Override
    public void resetEncoders() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }

    @Override
    public void setReferences(double leftMetersPerSecond, double rightMetersPerSecond) {
        double leftSpeed = metersPerSecondToRPM(leftMetersPerSecond);
        leftPID.setReference(leftSpeed, ControlType.kVelocity);
        System.out.println("Left RPM: " + leftSpeed);

        double rightSpeed = metersPerSecondToRPM(rightMetersPerSecond);
        rightPID.setReference(rightSpeed, ControlType.kVelocity);
        System.out.println("Right RPM: " + rightSpeed);

        straightDriving = false;
    }

    @Override
    public Rotation2d getGyroHeading() {
        return Rotation2d.fromDegrees(-navx.getRate());
    }

    @Override
    public double metersPerSecondToRPM(double metersPerSecond) {
        if (highGear) {
            return 4.17 * 60 * Units.metersToInches(metersPerSecond) / (5 * Math.PI);
        } else {
            return 11.03 * 60 * Units.metersToInches(metersPerSecond) / (5 * Math.PI);
        }
    }

}
