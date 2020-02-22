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
        TURN_OFF_LIFT_FORWARD,
        TURN_OFF_LIFT_REVERSE,
        TURN_FORWARD_LIFT_OFF,
        TURN_FORWARD_LIFT_FORWARD,
        TURN_FORWARD_LIFT_REVERSE,
        TURN_REVERSE_LIFT_OFF,
        TURN_REVERSE_LIFT_FORWARD,
        TURN_REVERSE_LIFT_REVERSE,
        SMART_INTAKE,
        LIFT_FORWARD,
        LIFT_REVERSE,
        GAP_ALIGNMENT,
        SMART_SHOOT
    }

    private CANSparkMax turnMotorController;
    private CANEncoder turnEncoder;
    private DigitalInput limitSwitch;
    private boolean gapAligned;
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
        turnMotorController.setInverted(true);
        turnMotorController.setOpenLoopRampRate(0);
        turnMotorController.setClosedLoopRampRate(0);
        turnMotorController.setSmartCurrentLimit(80);
        turnMotorController.setSecondaryCurrentLimit(40);

        turnEncoder = turnMotorController.getEncoder();

        limitSwitch = new DigitalInput(Config.Ports.Indexer.LIMIT_SWITCH);
        gapAligned = false;

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
            case TURN_OFF_LIFT_FORWARD:
                turnMotorController.set(0);
                liftMotorController.set(ControlMode.PercentOutput, 0.35);
                servo.set(1);
                liftTimer.reset();
                return;
            case TURN_OFF_LIFT_REVERSE:
                turnMotorController.set(0);
                liftMotorController.set(ControlMode.PercentOutput, -0.35);
                servo.set(1);
                liftTimer.reset();
                return;
            case TURN_FORWARD_LIFT_OFF:
                turnMotorController.set(0.15);
                liftMotorController.set(ControlMode.PercentOutput, 0);
                servo.set(1);
                liftTimer.reset();
                return;
            case TURN_FORWARD_LIFT_FORWARD:
                turnMotorController.set(0.15);
                liftMotorController.set(ControlMode.PercentOutput, 0.35);
                servo.set(1);
                liftTimer.reset();
                return;
            case TURN_FORWARD_LIFT_REVERSE:
                turnMotorController.set(0.15);
                liftMotorController.set(ControlMode.PercentOutput, -0.35);
                servo.set(1);
                liftTimer.reset();
                return;
            case TURN_REVERSE_LIFT_OFF:
                turnMotorController.set(-0.15);
                liftMotorController.set(ControlMode.PercentOutput, 0);
                servo.set(1);
                liftTimer.reset();
                return;
            case TURN_REVERSE_LIFT_FORWARD:
                turnMotorController.set(-0.15);
                liftMotorController.set(ControlMode.PercentOutput, 0.35);
                servo.set(1);
                liftTimer.reset();
                return;
            case TURN_REVERSE_LIFT_REVERSE:
                turnMotorController.set(-0.15);
                liftMotorController.set(ControlMode.PercentOutput, -0.35);
                servo.set(1);
                liftTimer.reset();
                return;
            case SMART_INTAKE:
                servo.set(1);
                if (!startupTimer.hasElapsed(0.3)) {
                    turnMotorController.set(0.15);
                    liftMotorController.set(ControlMode.PercentOutput, 0);
                    antiJamTimer.reset();
                } else if (turnEncoder.getVelocity() < 30 && !antiJamTimer.hasElapsed(2)) {
                    turnMotorController.set(-0.15);
                    liftMotorController.set(ControlMode.PercentOutput, -0.15);
                } else if (turnEncoder.getVelocity() < 30) {
                    turnMotorController.set(0.15);
                    liftMotorController.set(ControlMode.PercentOutput, 0);
                    antiJamTimer.reset();
                    startupTimer.reset();
                } else {
                    turnMotorController.set(0.15);
                    liftMotorController.set(ControlMode.PercentOutput, 0);
                    antiJamTimer.reset();
                }
                liftTimer.reset();
                return;
            case GAP_ALIGNMENT:
                servo.set(1);
                System.out.println("gap alignment mode");
                if (gapAligned || !limitSwitch.get()) {
                    System.out.println("aligned");
                    gapAligned = true;
                    turnMotorController.set(0);
                    liftMotorController.set(ControlMode.PercentOutput, 0.35);
                } else {
                    System.out.println("spinning");
                    turnMotorController.set(0.15);
                    liftMotorController.set(ControlMode.PercentOutput, 0);
                    liftTimer.reset();
                }
            case SMART_SHOOT:
                servo.set(1);
                if (!liftTimer.hasElapsed(0.6)) {
                    liftMotorController.set(ControlMode.PercentOutput, 0.35);
                    turnMotorController.set(0);
                    antiJamTimer.reset();
                } else if (!startupTimer.hasElapsed(1.2)) {
                    liftMotorController.set(ControlMode.PercentOutput, 0.35);
                    turnMotorController.set(0.25);
                    antiJamTimer.reset();
                } else if (turnEncoder.getVelocity() < 40 && !antiJamTimer.hasElapsed(2)) {
                    liftMotorController.set(ControlMode.PercentOutput, -0.15);
                    turnMotorController.set(-0.15);
                } else if (turnEncoder.getVelocity() < 40) {
                    liftMotorController.set(ControlMode.PercentOutput, 0.35);
                    turnMotorController.set(0);
                    antiJamTimer.reset();
                    startupTimer.reset();
                } else {
                    liftMotorController.set(ControlMode.PercentOutput, 0.35);
                    turnMotorController.set(0.25);
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

    public boolean isGapAligned() {
        return gapAligned;
    }

    public void setMode(Mode mode) {
        System.out.println("changed mode");
        if (mode != this.mode) {
            startupTimer.reset();
            gapAligned = false;
        }
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }

}
