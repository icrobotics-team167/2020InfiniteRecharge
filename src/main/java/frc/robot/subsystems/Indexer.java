package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

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
        SMART_SHOOT
    }

    private CANSparkMax turnMotorController;
    private CANEncoder turnEncoder;
    private TalonSRX liftMotorController;
    private PeriodicTimer startupTimer;
    private PeriodicTimer antiJamTimer;
    private PeriodicTimer liftTimer;
    private Mode mode;

    private Indexer() {
        turnMotorController = new CANSparkMax(Config.Ports.Indexer.TURN_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        turnMotorController.restoreFactoryDefaults();
        turnMotorController.setIdleMode(CANSparkMax.IdleMode.kBrake);
        turnMotorController.setInverted(true);
        turnMotorController.setOpenLoopRampRate(0);
        turnMotorController.setClosedLoopRampRate(0);
        turnMotorController.setSmartCurrentLimit(80);
        turnMotorController.setSecondaryCurrentLimit(40);

        turnEncoder = turnMotorController.getEncoder();

        liftMotorController = new TalonSRX(Config.Ports.Indexer.LIFT_MOTOR);
        liftMotorController.setInverted(true);
        liftMotorController.setNeutralMode(NeutralMode.Brake);

        startupTimer = new PeriodicTimer();
        antiJamTimer = new PeriodicTimer();

        mode = Mode.OFF;
    }

    public void run() {
        switch (mode) {
            case OFF:
                turnMotorController.set(0);
                liftMotorController.set(ControlMode.PercentOutput, 0);
                return;
            case FORWARD:
                turnMotorController.set(0.2);
                liftMotorController.set(ControlMode.PercentOutput, 0);
                return;
            case REVERSE:
                turnMotorController.set(-0.2);
                liftMotorController.set(ControlMode.PercentOutput, 0);
                return;
            case SMART_INTAKE:
                liftMotorController.set(ControlMode.PercentOutput, 0);
                if (!startupTimer.hasElapsed(0.3)) {
                    turnMotorController.set(0.25);
                    antiJamTimer.reset();
                } else if (turnEncoder.getVelocity() < 300 && !antiJamTimer.hasElapsed(2)) {
                    turnMotorController.set(-0.15);
                } else if (turnEncoder.getVelocity() < 300) {
                    turnMotorController.set(0.25);
                    antiJamTimer.reset();
                    startupTimer.reset();
                } else {
                    turnMotorController.set(0.25);
                    antiJamTimer.reset();
                }
                return;
            case SHOOT_FORWARD:
                turnMotorController.set(0.5);
                liftMotorController.set(ControlMode.PercentOutput, 1);
                return;
            case SHOOT_REVERSE:
                turnMotorController.set(-0.5);
                liftMotorController.set(ControlMode.PercentOutput, 1);
                return;
            case SMART_SHOOT:
                if (Subsystems.shooter.getRPM() < 4900 && !startupTimer.hasElapsed(2.5)) {
                    liftMotorController.set(ControlMode.PercentOutput, 0);
                    turnMotorController.set(0);
                } else if (!startupTimer.hasElapsed(2.65)) {
                    liftMotorController.set(ControlMode.PercentOutput, 1);
                    turnMotorController.set(0);
                } else if (!startupTimer.hasElapsed(3.05)) {
                    liftMotorController.set(ControlMode.PercentOutput, 1);
                    turnMotorController.set(0.5);
                } else if (turnEncoder.getVelocity() < 300 && !antiJamTimer.hasElapsed(2)) {
                    liftMotorController.set(ControlMode.PercentOutput, -0.08);
                    turnMotorController.set(-0.1);
                } else if (turnEncoder.getVelocity() < 300) {
                    liftMotorController.set(ControlMode.PercentOutput, 1);
                    turnMotorController.set(0);
                    antiJamTimer.reset();
                    startupTimer.reset();
                } else {
                    liftMotorController.set(ControlMode.PercentOutput, 1);
                    turnMotorController.set(0.5);
                    antiJamTimer.reset();
                }
                return;
            default:
                turnMotorController.set(0);
                liftMotorController.set(ControlMode.PercentOutput, 0);
                return;
        }
    }

    public void setMode(Mode mode) {
        if (mode != this.mode) {
            this.mode = mode;
            startupTimer.reset();
        }
    }

    public Mode getMode() {
        return mode;
    }

}
