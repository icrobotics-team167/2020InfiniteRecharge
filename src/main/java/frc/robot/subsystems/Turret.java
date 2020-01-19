package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Config;

public class Turret {

    private static Turret instance;
    public static Turret getInstance() {
        if (instance == null) {
            instance = new Turret();
        }
        return instance;
    }

    private TalonSRX motor;
    private PIDController pid;
    private Limelight limelight;

    private Turret() {
        motor = new TalonSRX(Config.Ports.TURRET);
        // Working values
        // pid = new PIDController(0.065, 0.002, 0.005); 
        // pid.setSetpoint(0);
        // pid.setTolerance(0.6);
        pid = new PIDController(0.065, 0.002, 0.002); 
        pid.setSetpoint(0);
        pid.setTolerance(0.5);
        limelight = Limelight.getInstance();
    }
    
    public void trackTarget() {
        limelight.update();
        double tx = limelight.tx();
        // if (Math.abs(tx) < 3) {
        //     return;
        // }
        // motor.set(ControlMode.PercentOutput, MathUtil.clamp(-tx / 10, -1, 1));
        double output = pid.calculate(limelight.tx());
        SmartDashboard.putNumber("PID Output", output);
        motor.set(ControlMode.PercentOutput, MathUtil.clamp(output, -1, 1));
    }

    public void turnClockwise(double speed) {
        motor.set(ControlMode.PercentOutput, -speed);
    }

    public void turnCounterclockwise(double speed) {
        motor.set(ControlMode.PercentOutput, speed);
    }

    public void stopTracking() {
        motor.set(ControlMode.PercentOutput, 0);
    }

}