package frc.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import frc.robot.Config;

public class TalonTankDriveBase implements TankDriveBase {

    private AHRS navx;
    private TalonSRX[] leftMotorGroup;
    private TalonSRX[] rightMotorGroup;
    private Encoder leftEncoder;
    private Encoder rightEncoder;
    private Solenoid solenoid;
    private boolean highGear;

    // Singleton
    private static TalonTankDriveBase instance;
    public static TalonTankDriveBase getInstance() {
        if (instance == null) {
            instance = new TalonTankDriveBase();
        }
        return instance;
    }

    private TalonTankDriveBase() {
        try {
            navx = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException e) {
            DriverStation.reportError("Error initializing the navX over SPI: " + e.toString(), e.getStackTrace());
        }

        leftMotorGroup = new TalonSRX[3];
        leftMotorGroup[0] = new TalonSRX(Config.Ports.TalonTank.LEFT_1);
        leftMotorGroup[1] = new TalonSRX(Config.Ports.TalonTank.LEFT_2);
        leftMotorGroup[2] = new TalonSRX(Config.Ports.TalonTank.LEFT_3);
        rightMotorGroup = new TalonSRX[3];
        rightMotorGroup[0] = new TalonSRX(Config.Ports.TalonTank.RIGHT_1);
        rightMotorGroup[1] = new TalonSRX(Config.Ports.TalonTank.RIGHT_2);
        rightMotorGroup[2] = new TalonSRX(Config.Ports.TalonTank.RIGHT_3);

        leftEncoder = new Encoder(
            Config.Ports.TalonTank.LEFT_ENCODER_A,
            Config.Ports.TalonTank.LEFT_ENCODER_B,
            Config.Ports.TalonTank.LEFT_ENCODER_REVERSED
        );
        rightEncoder = new Encoder(
            Config.Ports.TalonTank.RIGHT_ENCODER_A,
            Config.Ports.TalonTank.RIGHT_ENCODER_B,
            Config.Ports.TalonTank.RIGHT_ENCODER_REVERSED
        );

        solenoid = new Solenoid(
            Config.Settings.SPARK_TANK_ENABLED ? Config.Ports.SparkTank.PCM : Config.Ports.TalonTank.PCM,
            Config.Ports.TalonTank.SOLENOID
        );
        highGear = false;
    }

    @Override
    public void tankDrive(double leftSpeed, double rightSpeed) {
        System.out.println(leftSpeed);
        for (TalonSRX motorController : leftMotorGroup) {
            motorController.set(ControlMode.PercentOutput, -leftSpeed);
        }
        for (TalonSRX motorController : rightMotorGroup) {
            motorController.set(ControlMode.PercentOutput, rightSpeed);
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
        solenoid.set(false);
        highGear = true;
    }

    @Override
    public void setLowGear() {
        solenoid.set(true);
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
    public double getLeftSpeed() {
        return 0;
    }

    @Override
    public double getRightSpeed() {
        return 0;
    }

    @Override
    public void setReferences(double leftSpeed, double rightSpeed) {
        return;
    }

    @Override
    public Rotation2d getGyroHeading() {
        return Rotation2d.fromDegrees(-navx.getRate());
    }
}
