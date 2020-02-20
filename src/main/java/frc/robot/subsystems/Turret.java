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

    public static enum Mode {
        OFF,
        AUTO_ALIGN,
        TURN_CLOCKWISE,
        TURN_COUNTERCLOCKWISE
    }

    private Mode mode;
    private TalonSRX motor;
    private PIDController pid;
    private Limelight limelight;

    private Turret() {
        motor = new TalonSRX(Config.Ports.TURRET);
        motor.setNeutralMode(NeutralMode.Brake);
        pid = new PIDController(0.065, 0.002, 0.002, Config.Settings.CPU_PERIOD);
        pid.setSetpoint(1);
        pid.setTolerance(0.4);
        limelight = Limelight.getInstance();
        mode = Mode.OFF;
    }

    public void run() {
        switch (mode) {
            case OFF:
                stop();
                break;
            case AUTO_ALIGN:
                autoAlign();
                break;
            case TURN_CLOCKWISE:
                turnClockwise(0.3);
                break;
            case TURN_COUNTERCLOCKWISE:
                turnCounterclockwise(0.3);
                break;
            default:
                stop();
                break;
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }

    public void autoAlign() {
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
