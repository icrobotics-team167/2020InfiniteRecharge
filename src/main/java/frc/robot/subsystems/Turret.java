package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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
        motor.setNeutralMode(NeutralMode.Brake);
        pid = new PIDController(0.065, 0.002, 0.002);
        pid.setSetpoint(-1);
        pid.setTolerance(0.4);
        limelight = Limelight.getInstance();
    }

    public void autoAlign() {
        limelight.update();
        double tx = limelight.tx();
        double output = pid.calculate(tx);
        SmartDashboard.putNumber("Turret PID Output", output);
        motor.set(ControlMode.PercentOutput, MathUtil.clamp(output, -1, 1));
    }

    public void turnClockwise(double speed) {
        motor.set(ControlMode.PercentOutput, -speed);
    }

    public void turnCounterclockwise(double speed) {
        motor.set(ControlMode.PercentOutput, speed);
    }

    public void stop() {
        motor.set(ControlMode.PercentOutput, 0);
    }

}
