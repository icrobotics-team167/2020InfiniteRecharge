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
        OFF("OFF"),
        SMART_INTAKE("SMART_INTAKE"),
        GAP_ALIGNMENT("GAP_ALIGNMENT"),
        SMART_SHOOT("SMART_SHOOT"),
        MANUAL("MANUAL");

        public final String name;

        Mode(String name) {
            this.name = name;
        }
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

    private double liftSpeed;
    private double indexerSpeed;

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
            case SMART_INTAKE:
                servo.set(1);
                if (!startupTimer.hasElapsed(0.3)) {
                    turnMotorController.set(0.15);
                    liftMotorController.set(ControlMode.PercentOutput, 0);
                    antiJamTimer.reset();
                } else if (turnEncoder.getVelocity() < 30 && !antiJamTimer.hasElapsed(2)) {
                    turnMotorController.set(-0.15);
                    liftMotorController.set(ControlMode.PercentOutput, 0);
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
                if (gapAligned || !limitSwitch.get()) {
                    gapAligned = true;
                    servo.set(0.5);
                    turnMotorController.set(0);
                    liftMotorController.set(ControlMode.PercentOutput, 0);
                    liftTimer.reset();
                } else {
                    gapAligned = false;
                    servo.set(1);
                    turnMotorController.set(0.15);
                    liftMotorController.set(ControlMode.PercentOutput, 0);
                    liftTimer.reset();
                }
                return;
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
                    liftMotorController.set(ControlMode.PercentOutput, 0);
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
            case MANUAL:
                liftMotorController.set(ControlMode.PercentOutput, liftSpeed);
                turnMotorController.set(indexerSpeed);
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
        System.out.println("Changed indexer mode to Indexer.Mode." + mode.name);
        if (mode != this.mode) {
            startupTimer.reset();
        }
        if (mode != Mode.OFF) {
            gapAligned = false;
        }
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }

    public void setLiftSpeed(double speed) {
        liftSpeed = speed;
    } 

    public void setIndexerSpeed(double speed) {
        indexerSpeed = speed;
    }

}
