package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import frc.robot.Config;

public class Indexer {

    private static Indexer instance;
    public static Indexer getInstance() {
        if (instance == null) {
            instance = new Indexer();
        }
        return instance;
    }

    private CANSparkMax turnMotorController;
    private double currentSpeed;
    private TalonSRX liftMotorController;
    private boolean shooting;

    private Indexer() {
        turnMotorController = new CANSparkMax(Config.Ports.Indexer.TURN_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        turnMotorController.restoreFactoryDefaults();
        turnMotorController.setIdleMode(CANSparkMax.IdleMode.kBrake);
        turnMotorController.setInverted(false);
        turnMotorController.setOpenLoopRampRate(0);
        turnMotorController.setClosedLoopRampRate(0);
        turnMotorController.setSmartCurrentLimit(80);
        turnMotorController.setSecondaryCurrentLimit(40);
        currentSpeed = 0;

        liftMotorController = new TalonSRX(Config.Ports.Indexer.LIFT_MOTOR);
        liftMotorController.setInverted(false);
        shooting = false;
    }

    public void turnIntakeSpeed() {
        turnMotorController.set(0.2);
        currentSpeed = 0.2;
    }

    public void turnReverseSpeed() {
        turnMotorController.set(-0.15);
        currentSpeed = -0.15;
    }

    public void turnShootingSpeed() {
        turnMotorController.set(0.6);
        currentSpeed = 0.6;
    }

    public void stopTurning() {
        turnMotorController.set(0);
        currentSpeed = 0;
    }

    public void shoot() {
        liftMotorController.set(ControlMode.PercentOutput, 0.6);
    }

    public void stopShooting() {
        liftMotorController.set(ControlMode.PercentOutput, 0);
    }

    public boolean isIntakeSpeed() {
        return currentSpeed == 0.2;
    }

    public boolean isReverseSpeed() {
        return currentSpeed == -0.15;
    }

    public boolean isShootingSpeed() {
        return currentSpeed == 0.6;
    }

    public boolean isStopped() {
        return currentSpeed == 0;
    }

    public boolean isShooting() {
        return shooting;
    }

}
