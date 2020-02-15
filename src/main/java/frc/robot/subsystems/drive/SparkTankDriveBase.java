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
import frc.robot.Config;

public class SparkTankDriveBase implements TankDriveBase {

    private AHRS navx;
    private CANSparkMax[] leftMotorGroup;
    private CANSparkMax[] rightMotorGroup;
    private CANEncoder[] leftEncoders;
    private CANEncoder[] rightEncoders;
    private DoubleSolenoid doubleSolenoid;
    private boolean highGear;
    private CANPIDController[] leftControllers;
    private CANPIDController[] rightControllers;

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

        leftEncoders = new CANEncoder[3];
        rightEncoders = new CANEncoder[3];
        for (int i = 0; i <= 2; i++) {
            leftEncoders[i] = leftMotorGroup[i].getEncoder(EncoderType.kHallSensor, 4096);
            rightEncoders[i] = rightMotorGroup[i].getEncoder(EncoderType.kHallSensor, 4096);
        }

        doubleSolenoid = new DoubleSolenoid(
            Config.Settings.SPARK_TANK_ENABLED ? Config.Ports.SparkTank.PCM : Config.Ports.TalonTank.PCM,
            Config.Ports.SparkTank.SOLENOID_FORWARD,
            Config.Ports.SparkTank.SOLENOID_REVERSE
        );
        highGear = false;

        leftControllers[0] = leftMotorGroup[0].getPIDController();
        leftControllers[1] = leftMotorGroup[1].getPIDController();
        leftControllers[2] = leftMotorGroup[2].getPIDController();
        rightControllers[0] = rightMotorGroup[0].getPIDController();
        rightControllers[1] = rightMotorGroup[1].getPIDController();
        rightControllers[2] = rightMotorGroup[2].getPIDController();
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

    public void testMotor() {
        rightMotorGroup[2].set(1);
    }

    @Override
    public double getLeftSpeed() {
        double speedAverage = (leftEncoders[0].getVelocity() + leftEncoders[1].getVelocity() + leftEncoders[2].getVelocity()) / 3;
        return 0.39898226700466 * (speedAverage / 60);
    }

    @Override
    public double getRightSpeed() {
        double speedAverage = (rightEncoders[0].getVelocity() + rightEncoders[1].getVelocity() + rightEncoders[2].getVelocity()) / 3;
        return 0.39898226700466 * (speedAverage / 60);
    }

    @Override
    public void setReferences(double leftSpeed, double rightSpeed) {
        for (CANPIDController controller : leftControllers) {
            controller.setReference(leftSpeed, ControlType.kSmartVelocity);
        }
        for (CANPIDController controller : rightControllers) {
            controller.setReference(rightSpeed, ControlType.kSmartVelocity);
        }
    }

    @Override
    public Rotation2d getGyroHeading() {
        return Rotation2d.fromDegrees(-navx.getRate());
    }
}
