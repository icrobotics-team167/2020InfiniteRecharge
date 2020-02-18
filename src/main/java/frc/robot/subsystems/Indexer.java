package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import frc.robot.Config;
import frc.robot.util.PeriodicTimer;

public class Indexer {

    private static Indexer instance;
    public static Indexer getInstance() {
        if (instance == null) {
            instance = new Indexer();
        }
        return instance;
    }

    public static enum Mode {
        OFF,
        FORWARD,
        REVERSE,
        SMART_INTAKE,
        SHOOT_FORWARD,
        SHOOT_REVERSE,
        SHOOTER_STARTUP,
        SMART_SHOOT
    }

    private CANSparkMax turnMotorController;
    private CANEncoder turnEncoder;
    private DigitalInput limitSwitch;
    private TalonSRX liftMotorController;
    private Servo servo;
    private PeriodicTimer startupTimer;
    private PeriodicTimer liftTimer;
    private PeriodicTimer antiJamTimer;
    private Mode mode;

    private Indexer() {
        turnMotorController = new CANSparkMax(Config.Ports.Indexer.TURN_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        turnMotorController.restoreFactoryDefaults();
        turnMotorController.setIdleMode(CANSparkMax.IdleMode.kBrake);
        turnMotorController.setInverted(false);
        turnMotorController.setOpenLoopRampRate(0);
        turnMotorController.setClosedLoopRampRate(0);
        turnMotorController.setSmartCurrentLimit(80);
        turnMotorController.setSecondaryCurrentLimit(40);

        turnEncoder = turnMotorController.getEncoder();

        limitSwitch = new DigitalInput(Config.Ports.Indexer.LIMIT_SWITCH);

        liftMotorController = new TalonSRX(Config.Ports.Indexer.LIFT_MOTOR);
        liftMotorController.setInverted(true);
        liftMotorController.setNeutralMode(NeutralMode.Brake);

        servo = new Servo(Config.Ports.Indexer.SERVO);

        startupTimer = new PeriodicTimer();
        liftTimer = new PeriodicTimer();
        antiJamTimer = new PeriodicTimer();

        mode = Mode.OFF;
    }

    public void run() {
        switch (mode) {
            case OFF:
                turnMotorController.set(0);
                liftMotorController.set(ControlMode.PercentOutput, 0);
                servo.set(0.5);
                liftTimer.reset();
                return;
            case FORWARD:
                turnMotorController.set(0.15);
                liftMotorController.set(ControlMode.PercentOutput, 0);
                servo.set(1);
                liftTimer.reset();
                return;
            case REVERSE:
                turnMotorController.set(-0.2);
                liftMotorController.set(ControlMode.PercentOutput, 0);
                servo.set(1);
                liftTimer.reset();
                return;
            case SMART_INTAKE:
                servo.set(1);
                liftMotorController.set(ControlMode.PercentOutput, 0);
                if (!startupTimer.hasElapsed(0.3)) {
                    turnMotorController.set(0.15);
                    antiJamTimer.reset();
                } else if (turnEncoder.getVelocity() < 30 && !antiJamTimer.hasElapsed(1.3)) {
                    turnMotorController.set(-0.10);
                } else if (turnEncoder.getVelocity() < 30) {
                    turnMotorController.set(0.15);
                    antiJamTimer.reset();
                    startupTimer.reset();
                } else {
                    turnMotorController.set(0.15);
                    antiJamTimer.reset();
                }
                liftTimer.reset();
                return;
            case SHOOT_FORWARD:
                servo.set(1);
                turnMotorController.set(0.5);
                liftMotorController.set(ControlMode.PercentOutput, 1);
                liftTimer.reset();
                return;
            case SHOOT_REVERSE:
                servo.set(1);
                turnMotorController.set(-0.5);
                liftMotorController.set(ControlMode.PercentOutput, 1);
                liftTimer.reset();
                return;
            case SHOOTER_STARTUP:
                servo.set(1);
                if (!limitSwitch.get()) {
                    turnMotorController.set(0.15);
                    liftMotorController.set(ControlMode.PercentOutput, 0);
                    liftTimer.reset();
                } else {
                    turnMotorController.set(0);
                    liftMotorController.set(ControlMode.PercentOutput, 1);
                }
            case SMART_SHOOT:
                servo.set(1);
                if (!liftTimer.hasElapsed(1.25)) {
                    liftMotorController.set(ControlMode.PercentOutput, 1);
                    turnMotorController.set(0);
                    antiJamTimer.reset();
                } else if (!startupTimer.hasElapsed(2.5)) {
                    liftMotorController.set(ControlMode.PercentOutput, 1);
                    turnMotorController.set(0.375);
                    antiJamTimer.reset();
                } else if (turnEncoder.getVelocity() < 75 && !antiJamTimer.hasElapsed(1.3)) {
                    liftMotorController.set(ControlMode.PercentOutput, -0.08);
                    turnMotorController.set(-0.1);
                } else if (turnEncoder.getVelocity() < 75) {
                    liftMotorController.set(ControlMode.PercentOutput, 1);
                    turnMotorController.set(0);
                    antiJamTimer.reset();
                    startupTimer.reset();
                } else {
                    liftMotorController.set(ControlMode.PercentOutput, 1);
                    turnMotorController.set(0.375);
                    antiJamTimer.reset();
                }
                return;
            default:
                turnMotorController.set(0);
                liftMotorController.set(ControlMode.PercentOutput, 0);
                liftTimer.reset();
                return;
        }
    }

    public boolean isReadyToShoot() {
        return limitSwitch.get();
    }

    public void setMode(Mode mode) {
        if (mode != this.mode) {
            this.mode = mode;
            startupTimer.reset();
            if (mode == Mode.SHOOTER_STARTUP) {
                liftTimer.reset();
            }
        }
    }

    public Mode getMode() {
        return mode;
    }

}
