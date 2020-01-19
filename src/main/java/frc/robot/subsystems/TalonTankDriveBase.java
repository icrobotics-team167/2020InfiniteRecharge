package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.EncoderType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.Config;

public class TalonTankDriveBase {

    private AHRS navx;
    private TalonSRX[] leftMotorGroup;
    private TalonSRX[] rightMotorGroup;
    // private CANEncoder[] leftEncoders;
    // private CANEncoder[] rightEncoders;

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
        leftMotorGroup[0] = new TalonSRX(3);
        leftMotorGroup[1] = new TalonSRX(4);
        leftMotorGroup[2] = new TalonSRX(9);
        rightMotorGroup = new TalonSRX[3];
        rightMotorGroup[0] = new TalonSRX(6);
        rightMotorGroup[1] = new TalonSRX(7);
        // rightMotorGroup[2] = new TalonSRX(8);
        rightMotorGroup[2] = new TalonSRX(50);

        // leftEncoders = new CANEncoder[3];
        // rightEncoders = new CANEncoder[3];
        // for (int i = 0; i <= 3; i++) {
        //     leftEncoders[i] = leftMotorGroup[i].getEncoder(EncoderType.kQuadrature, 4096);
        //     rightEncoders[i] = rightMotorGroup[i].getEncoder(EncoderType.kQuadrature, 4096);
        // }
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        for (TalonSRX motorController : leftMotorGroup) {
            motorController.set(ControlMode.PercentOutput, leftSpeed);
        }
        for (TalonSRX motorController : rightMotorGroup) {
            motorController.set(ControlMode.PercentOutput, rightSpeed);
        }
    }

}
