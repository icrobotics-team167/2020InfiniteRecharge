package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config;

public class Shooter {

    private static Shooter instance;
    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    private TalonSRX leftMotorController;
    private TalonSRX rightMotorController;
    private Encoder encoder;

    private Shooter() {
        leftMotorController = new TalonSRX(Config.Ports.Shooter.LEFT);
        rightMotorController = new TalonSRX(Config.Ports.Shooter.RIGHT);
        leftMotorController.setNeutralMode(NeutralMode.Coast);
        rightMotorController.setNeutralMode(NeutralMode.Coast);
        encoder = new Encoder(
            Config.Ports.Shooter.ENCODER_A,
            Config.Ports.Shooter.ENCODER_B,
            Config.Ports.Shooter.ENCODER_I
        );
        encoder.setDistancePerPulse((double) 60 / 2048);
    }

    public void drive(int targetRPM) {
        double actualRPM = encoder.getRate();
        SmartDashboard.putNumber("Shooter RPM", actualRPM);

        if (actualRPM > targetRPM) {
            leftMotorController.set(ControlMode.PercentOutput, 0);
            rightMotorController.set(ControlMode.PercentOutput, 0);
        } else {
            leftMotorController.set(ControlMode.PercentOutput, -1);
            rightMotorController.set(ControlMode.PercentOutput, 1);
        }
    }

}
