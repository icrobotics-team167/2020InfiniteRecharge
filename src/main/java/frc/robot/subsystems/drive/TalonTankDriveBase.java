package frc.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.Config;

public class TalonTankDriveBase implements TankDriveBase {

    private AHRS navx;
    private TalonSRX[] leftMotorGroup;
    private TalonSRX[] rightMotorGroup;
    private Encoder leftEncoder;
    private Encoder rightEncoder;

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
        // This Talon is disabled because one of the Talons on the left is broken
        // rightMotorGroup[2] = new TalonSRX(Config.Ports.TalonTank.RIGHT_3);
        rightMotorGroup[2] = new TalonSRX(50);

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
    }

    @Override
    public void tankDrive(double leftSpeed, double rightSpeed) {
        for (TalonSRX motorController : leftMotorGroup) {
            motorController.set(ControlMode.PercentOutput, -leftSpeed);
        }
        for (TalonSRX motorController : rightMotorGroup) {
            motorController.set(ControlMode.PercentOutput, rightSpeed);
        }
    }

}
