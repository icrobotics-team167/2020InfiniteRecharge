package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpiutil.math.MathUtil;
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
    private int targetRPM;
    private double currentSpeed;
    private PIDController pid;

    private Shooter() {
        // leftMotorController = new TalonSRX(Config.Ports.SHOOTER_LEFT);
        // rightMotorController = new TalonSRX(Config.Ports.SHOOTER_RIGHT);
        // leftMotorController.setNeutralMode(NeutralMode.Coast);
        // rightMotorController.setNeutralMode(NeutralMode.Coast);
        encoder = new Encoder(6, 7, 8);
        encoder.setDistancePerPulse((double) 60 / 2048);
        targetRPM = 10000;
        currentSpeed = 0;
        pid = new PIDController(0.0015, 0.007, 0.0002);
        pid.setTolerance(5);
    }

    public void drive() {
        pid.setSetpoint(targetRPM);
        double actualRPM = encoder.getRate();
        SmartDashboard.putNumber("Shooter RPM", actualRPM);
        currentSpeed += pid.calculate(actualRPM);
        currentSpeed = MathUtil.clamp(currentSpeed, 0, 1);
        // leftMotorController.set(ControlMode.PercentOutput, currentSpeed);
        // rightMotorController.set(ControlMode.PercentOutput, -currentSpeed);
    }

    public void setSpeed(int rpm) {
        targetRPM = rpm;
    }

}
