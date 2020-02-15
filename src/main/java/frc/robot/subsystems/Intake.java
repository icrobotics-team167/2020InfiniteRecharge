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
        OFF_UP,
        OFF_DOWN,
        INTAKE_DOWN,
        REVERSE_UP,
        REVERSE_DOWN
    }

    private Solenoid solenoid;
    private TalonSRX motor;
    private Mode mode;
    private boolean downPosition;

    private Intake() {
        solenoid = new Solenoid(
            Config.Settings.SPARK_TANK_ENABLED ? Config.Ports.SparkTank.PCM : Config.Ports.TalonTank.PCM,
            Config.Ports.Intake.SOLENOID
        );
        motor = new TalonSRX(Config.Ports.Intake.MOTOR);
        motor.setInverted(true);
        motor.setNeutralMode(NeutralMode.Brake);
        mode = Mode.OFF_UP;
        downPosition = false;
    }

    public void run() {
        switch (mode) {
            case OFF_UP:
                motor.set(ControlMode.PercentOutput, 0);
                if (downPosition) {
                    solenoid.set(false);
                }
                return;
            case OFF_DOWN:
                motor.set(ControlMode.PercentOutput, 0);
                if (!downPosition) {
                    solenoid.set(true);
                }
                return;
            case INTAKE_DOWN:
                motor.set(ControlMode.PercentOutput, 0.65);
                if (!downPosition) {
                    solenoid.set(true);
                }
                return;
            case REVERSE_UP:
                motor.set(ControlMode.PercentOutput, -0.3);
                if (downPosition) {
                    solenoid.set(false);
                }
                return;
            case REVERSE_DOWN:
                motor.set(ControlMode.PercentOutput, -0.65);
                if (!downPosition) {
                    solenoid.set(true);
                }
                return;
            default:
                motor.set(ControlMode.PercentOutput, 0);
                return;
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

}
