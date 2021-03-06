package frc.robot.subsystems;

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
        SINGLE_TURN("SINGLE_TURN"),
        GAP_ALIGNMENT("GAP_ALIGNMENT"),
        SICKO_SHOOT("SICKO_SHOOT"),
        MANUAL("MANUAL");

        public final String name;

        Mode(String name) {
            this.name = name;
        }
    }

    private CANSparkMax turnMotorController;
    private CANEncoder turnEncoder;
    private DigitalInput limitSwitch;
    private boolean hasCompletedSingleTurn;
    private boolean gapAligned;
    private CANSparkMax liftMotorController;
    private Servo servo;
    private PeriodicTimer startupTimer;
    private PeriodicTimer liftTimer;
    private PeriodicTimer antiJamTimer;
    private Mode mode;
    private double manualTurnSpeed;
    private double manualLiftSpeed;
    private int antiJamServoInverted;

    private Indexer() {
        turnMotorController = new CANSparkMax(Config.Ports.Indexer.TURN_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        turnMotorController.restoreFactoryDefaults();
        turnMotorController.setIdleMode(CANSparkMax.IdleMode.kBrake);
        turnMotorController.setInverted(true);
        turnMotorController.setOpenLoopRampRate(0.75);
        turnMotorController.setClosedLoopRampRate(0.75);
        turnMotorController.setSmartCurrentLimit(80);
        turnMotorController.setSecondaryCurrentLimit(40);

        turnEncoder = turnMotorController.getEncoder();

        hasCompletedSingleTurn = false;

        limitSwitch = new DigitalInput(Config.Ports.Indexer.LIMIT_SWITCH);
        gapAligned = false;

        liftMotorController = new CANSparkMax(Config.Ports.Indexer.LIFT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        liftMotorController.restoreFactoryDefaults();
        liftMotorController.setIdleMode(CANSparkMax.IdleMode.kBrake);
        liftMotorController.setInverted(false);
        liftMotorController.setOpenLoopRampRate(0);
        liftMotorController.setClosedLoopRampRate(0);
        liftMotorController.setSmartCurrentLimit(80);
        liftMotorController.setSecondaryCurrentLimit(40);

        servo = new Servo(Config.Ports.Indexer.SERVO);

        startupTimer = new PeriodicTimer();
        liftTimer = new PeriodicTimer();
        antiJamTimer = new PeriodicTimer();

        mode = Mode.OFF;

        manualTurnSpeed = 0;
        manualLiftSpeed = 0;
        antiJamServoInverted = 1;
    }

    public void run() {
        switch (mode) {
            case OFF:
                turnMotorController.set(0);
                liftMotorController.set(0);
                servo.set(0.5);
                liftTimer.reset();
                return;
            case SMART_INTAKE:
                servo.set(antiJamServoInverted * 1);
                if (!startupTimer.hasElapsed(1)) {
                    turnMotorController.set(0.15);
                    liftMotorController.set(0);
                    antiJamTimer.reset();
                } else if (turnEncoder.getVelocity() < 900 && !antiJamTimer.hasElapsed(1.5)) {
                    turnMotorController.set(-0.15);
                    liftMotorController.set(0);
                } else if (turnEncoder.getVelocity() < 900) {
                    turnMotorController.set(0.15);
                    liftMotorController.set(0);
                    antiJamTimer.reset();
                    startupTimer.reset();
                } else {
                    turnMotorController.set(0.15);
                    liftMotorController.set(0);
                    antiJamTimer.reset();
                }
                liftTimer.reset();
                return;
            case SINGLE_TURN:
                liftMotorController.set(0);
                servo.set(0.5);
                if (!startupTimer.hasElapsed(0.5)) {
                    hasCompletedSingleTurn = false;
                    turnMotorController.set(0.5);
                } else {
                    hasCompletedSingleTurn = true;
                    turnMotorController.set(0);
                }
                liftTimer.reset();
                return;
            case GAP_ALIGNMENT:
                if (gapAligned || !limitSwitch.get()) {
                    gapAligned = true;
                    servo.set(0.5);
                    turnMotorController.set(0);
                } else {
                    gapAligned = false;
                    servo.set(antiJamServoInverted * 1);
                    turnMotorController.set(0.15);
                }
                liftMotorController.set(0);
                liftTimer.reset();
                return;
            case SICKO_SHOOT:
                servo.set(antiJamServoInverted * 1);
                if (!liftTimer.hasElapsed(0.3)) {
                    liftMotorController.set(1);
                    turnMotorController.set(0);
                    antiJamTimer.reset();
                } else if (!startupTimer.hasElapsed(1)) {
                    liftMotorController.set(1);
                    turnMotorController.set(0.55);
                    antiJamTimer.reset();
                } else if (turnEncoder.getVelocity() < 3100 && !antiJamTimer.hasElapsed(1.5)) {
                    liftMotorController.set(0);
                    turnMotorController.set(-0.2);
                } else if (turnEncoder.getVelocity() < 3100) {
                    liftMotorController.set(1);
                    turnMotorController.set(0);
                    antiJamTimer.reset();
                    startupTimer.reset();
                } else {
                    liftMotorController.set(1);
                    turnMotorController.set(0.55);
                    antiJamTimer.reset();
                }
                return;
            case MANUAL:
                servo.set(antiJamServoInverted * 1);
                turnMotorController.set(manualTurnSpeed);
                liftMotorController.set(manualLiftSpeed);
                liftTimer.reset();
                return;
            default:
                turnMotorController.set(0);
                liftMotorController.set(0);
                liftTimer.reset();
                return;
        }
    }

    public boolean isLimitSwitchAligned() {
        return !limitSwitch.get();
    }

    public boolean hasCompletedSingleTurn() {
        return hasCompletedSingleTurn;
    }

    public boolean isGapAligned() {
        return gapAligned;
    }

    public boolean isRunningAntiJam() {
        return antiJamTimer.hasElapsed(0.04);
    }

    public boolean isShooting() {
        return mode == Mode.SICKO_SHOOT;
    }

    public double getTurnRPM() {
        return turnEncoder.getVelocity();
    }

    public void setMode(Mode mode) {
        if (mode == Mode.GAP_ALIGNMENT) {
            turnMotorController.setOpenLoopRampRate(0);
            turnMotorController.setClosedLoopRampRate(0);
        } else {
            turnMotorController.setOpenLoopRampRate(0.75);
            turnMotorController.setClosedLoopRampRate(0.75);
        }
        if (mode != this.mode) {
            startupTimer.reset();
        }
        if (mode != Mode.OFF) {
            gapAligned = false;
            hasCompletedSingleTurn = false;
        }
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }

    public void setManualTurnSpeed(double speed) {
        manualTurnSpeed = speed;
    }

    public void setManualLiftSpeed(double speed) {
        manualLiftSpeed = speed;
    }

    public void forwardAntiJamServo() {
        antiJamServoInverted = 1;
    }

    public void reverseAntiJamServo() {
        antiJamServoInverted = -1;
    }

}
