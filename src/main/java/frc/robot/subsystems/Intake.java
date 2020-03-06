package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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
    private CANSparkMax motor;
    private Mode mode;
    private boolean extended;
    private double manualSpeed;

    private Intake() {
        solenoid = new Solenoid(
            Config.Settings.SPARK_TANK_ENABLED ? Config.Ports.SparkTank.PCM : Config.Ports.TalonTank.PCM,
            Config.Ports.Intake.SOLENOID
        );
        motor = new CANSparkMax(Config.Ports.Intake.MOTOR, MotorType.kBrushless);
        motor.restoreFactoryDefaults();
        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        motor.setInverted(false);
        motor.setOpenLoopRampRate(0);
        motor.setClosedLoopRampRate(0);
        motor.setSmartCurrentLimit(80);
        motor.setSecondaryCurrentLimit(40);
        mode = Mode.OFF;
        extended = false;
    }

    public void run() {
        switch (mode) {
            case OFF:
                motor.set(0);
                break;
            case FORWARD:
                if (extended) {
                    motor.set(0.95);
                } else {
                    motor.set(0.4);
                }
                break;
            case REVERSE:
                if (extended) {
                    motor.set(-0.6);
                } else {
                    motor.set(-0.3);
                }
                break;
            case MANUAL:
                motor.set(manualSpeed);
                break;
            default:
                motor.set(0);
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
