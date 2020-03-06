package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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
    private CANSparkMax motor;
    private PIDController pid;
    private Limelight limelight;

    private Turret() {
        motor = new CANSparkMax(Config.Ports.TURRET, MotorType.kBrushed);
        motor.restoreFactoryDefaults();
        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        motor.setInverted(true);
        motor.setOpenLoopRampRate(0);
        motor.setClosedLoopRampRate(0);
        motor.setSmartCurrentLimit(80);
        motor.setSecondaryCurrentLimit(40);
        pid = new PIDController(0.065, 0.002, 0.002, Config.Settings.CPU_PERIOD);
        pid.setSetpoint(0.4);
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
        if (mode == Turret.Mode.AUTO_ALIGN) {
            limelight.setVisionMode();
        } else {
            limelight.setCameraMode();
        }
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }

    public boolean isAligned() {
        return pid.atSetpoint();
    }

    private void autoAlign() {
        double tx = limelight.tx();
        double output = pid.calculate(tx);
        SmartDashboard.putNumber("Turret PID Output", output);
        motor.set(MathUtil.clamp(output, -1, 1));
    }

    private void turnClockwise(double speed) {
        motor.set(-speed);
    }

    private void turnCounterclockwise(double speed) {
        motor.set(speed);
    }

    private void stop() {
        motor.set(0);
    }

}
