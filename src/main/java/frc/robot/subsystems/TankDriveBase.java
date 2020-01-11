package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.Config;

public class TankDriveBase {

    private AHRS navx;
    private CANSparkMax[] leftMotorGroup;
    private CANSparkMax[] rightMotorGroup;

    // Singleton
    private static TankDriveBase instance;
    public static TankDriveBase getInstance() {
        if (instance == null) {
            instance = new TankDriveBase();
        }
        return instance;
    }

    private TankDriveBase() {
        try {
            navx = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException e) {
            DriverStation.reportError("Error initializing the navX over SPI: " + e.toString(), e.getStackTrace());
        }

        leftMotorGroup = new CANSparkMax[4];
        leftMotorGroup[0] = new CANSparkMax(Config.Ports.Tank.LEFT_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftMotorGroup[1] = new CANSparkMax(Config.Ports.Tank.LEFT_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftMotorGroup[2] = new CANSparkMax(Config.Ports.Tank.LEFT_3, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftMotorGroup[3] = new CANSparkMax(Config.Ports.Tank.LEFT_4, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMotorGroup = new CANSparkMax[4];
        rightMotorGroup[0] = new CANSparkMax(Config.Ports.Tank.RIGHT_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMotorGroup[1] = new CANSparkMax(Config.Ports.Tank.RIGHT_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMotorGroup[2] = new CANSparkMax(Config.Ports.Tank.RIGHT_3, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMotorGroup[3] = new CANSparkMax(Config.Ports.Tank.RIGHT_4, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        for (CANSparkMax motorController : leftMotorGroup) {
            motorController.set(leftSpeed);
        }
        for (CANSparkMax motorController : rightMotorGroup) {
            motorController.set(rightSpeed);
        }
    }

}
