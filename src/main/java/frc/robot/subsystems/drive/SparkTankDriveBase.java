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
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.util.Units;
import frc.robot.Config;

public class SparkTankDriveBase implements TankDriveBase {

    private AHRS navx;
    private CANSparkMax[] leftMotorGroup;
    private CANSparkMax[] rightMotorGroup;
    private CANEncoder[] leftEncoders;
    private CANEncoder[] rightEncoders;
    private CANPIDController leftPID;
    private CANPIDController rightPID;
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

        leftMotorGroup = new CANSparkMax[3];
        leftMotorGroup[0] = new CANSparkMax(Config.Ports.SparkTank.LEFT_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftMotorGroup[1] = new CANSparkMax(Config.Ports.SparkTank.LEFT_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftMotorGroup[2] = new CANSparkMax(Config.Ports.SparkTank.LEFT_3, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMotorGroup = new CANSparkMax[3];
        rightMotorGroup[0] = new CANSparkMax(Config.Ports.SparkTank.RIGHT_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMotorGroup[1] = new CANSparkMax(Config.Ports.SparkTank.RIGHT_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMotorGroup[2] = new CANSparkMax(Config.Ports.SparkTank.RIGHT_3, CANSparkMaxLowLevel.MotorType.kBrushless);

        leftMotorGroup[0].restoreFactoryDefaults();
        rightMotorGroup[0].restoreFactoryDefaults();
        leftEncoders[0] = leftMotorGroup[0].getEncoder(EncoderType.kHallSensor, 4096);
        rightEncoders[0] = rightMotorGroup[0].getEncoder(EncoderType.kHallSensor, 4096);
        leftMotorGroup[0].setSmartCurrentLimit(80);
        leftMotorGroup[0].setSecondaryCurrentLimit(60);
        rightMotorGroup[0].setSmartCurrentLimit(80);
        rightMotorGroup[0].setSecondaryCurrentLimit(60);
        leftMotorGroup[0].setOpenLoopRampRate(0);
        leftMotorGroup[0].setClosedLoopRampRate(0);
        rightMotorGroup[0].setOpenLoopRampRate(0);
        rightMotorGroup[0].setClosedLoopRampRate(0);

        leftPID = leftMotorGroup[0].getPIDController();
        rightPID = rightMotorGroup[0].getPIDController();

        leftMotorGroup[1].follow(leftMotorGroup[0]);
        leftMotorGroup[2].follow(leftMotorGroup[0]);
        rightMotorGroup[1].follow(rightMotorGroup[0]);
        rightMotorGroup[2].follow(rightMotorGroup[0]);

        doubleSolenoid = new DoubleSolenoid(
            Config.Settings.SPARK_TANK_ENABLED ? Config.Ports.SparkTank.PCM : Config.Ports.TalonTank.PCM,
            Config.Ports.SparkTank.SOLENOID_FORWARD,
            Config.Ports.SparkTank.SOLENOID_REVERSE
        );

        highGear = false;
    }

    @Override
    public void tankDrive(double leftSpeed, double rightSpeed) {
        for (CANSparkMax motorController : leftMotorGroup) {
            motorController.set(-leftSpeed);
        }
        for (CANSparkMax motorController : rightMotorGroup) {
            motorController.set(rightSpeed);
        }
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
    public double getLeftEncoderPosition() {
        double encoderValue = leftEncoders[0].getPosition();

        if (highGear) {
            return Units.inchesToMeters(encoderValue * 5 * Math.PI / 4.17);
        } else {
            return Units.inchesToMeters(encoderValue * 5 * Math.PI / 11.03);
        }
    }

    @Override
    public double getRightEncoderPosition() {
        double encoderValue = rightEncoders[0].getPosition();

        if (highGear) {
            return Units.inchesToMeters(encoderValue * 5 * Math.PI / 4.17);
        } else {
            return Units.inchesToMeters(encoderValue * 5 * Math.PI / 11.03);
        }
    }

    @Override
    public void setReferences(double leftMetersPerSecond, double rightMetersPerSecond) {
        leftPID.setReference(metersPerSecondToRPM(leftMetersPerSecond), ControlType.kVelocity);
        rightPID.setReference(metersPerSecondToRPM(rightMetersPerSecond), ControlType.kVelocity);
    }

    @Override
    public Rotation2d getGyroHeading() {
        return Rotation2d.fromDegrees(-navx.getRate());
    }

    private double metersPerSecondToRPM(double metersPerSecond) {
        if (highGear) {
            return 60 * Units.metersToInches(metersPerSecond) / (5 * Math.PI * 11.03);
        } else {
            return 60 * Units.metersToInches(metersPerSecond) / (5 * Math.PI * 4.17);
        }
    }

    public void testMotor() {
        rightMotorGroup[2].set(1);
    }

}
