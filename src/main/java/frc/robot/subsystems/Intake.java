package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Config;

public class Intake {

    private static Intake instance;
    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    public static enum Mode {
        OFF,
        FORWARD,
        REVERSE,
        MANUAL
    }

    private Solenoid solenoid;
    private TalonSRX motor;
    private Mode mode;
    private boolean extended;
    private double manualSpeed;

    private Intake() {
        solenoid = new Solenoid(
            Config.Settings.SPARK_TANK_ENABLED ? Config.Ports.SparkTank.PCM : Config.Ports.TalonTank.PCM,
            Config.Ports.Intake.SOLENOID
        );
        motor = new TalonSRX(Config.Ports.Intake.MOTOR);
        motor.setInverted(true);
        motor.setNeutralMode(NeutralMode.Brake);
        mode = Mode.OFF;
        extended = false;
    }

    public void run() {
        switch (mode) {
            case OFF:
                motor.set(ControlMode.PercentOutput, 0);
                break;
            case FORWARD:
                if (extended) {
                    motor.set(ControlMode.PercentOutput, 0.651);
                } else {
                    motor.set(ControlMode.PercentOutput, 0.4);
                }
                break;
            case REVERSE:
                if (extended) {
                    motor.set(ControlMode.PercentOutput, -0.6);
                } else {
                    motor.set(ControlMode.PercentOutput, -0.3);
                }
                break;
            case MANUAL:
                motor.set(ControlMode.PercentOutput, manualSpeed);
                break;
            default:
                motor.set(ControlMode.PercentOutput, 0);
                break;
        }
    }

    public void setMode(Mode mode) {
        if (mode != this.mode) {
            this.mode = mode;
        }
    }

    public Mode getMode() {
        return mode;
    }

    public void extend() {
        solenoid.set(true);
        extended = true;
    }

    public void retract() {
        solenoid.set(false);
        extended = false;
    }

    public void toggleExtension() {
        if (extended) {
            retract();
        } else {
            extend();
        }
    }

    public void setManualSpeed(double speed) {
        manualSpeed = speed;
    }

}
