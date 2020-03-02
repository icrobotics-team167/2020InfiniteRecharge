package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Config;

public class Shooter {

    private static Shooter instance;
    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    private boolean on;
    private int targetRPM;
    private CANSparkMax leftMotorController;
    private CANSparkMax rightMotorController;
    private CANEncoder leftEncoder;
    private CANEncoder rightEncoder;

    private Shooter() {
        leftMotorController = new CANSparkMax(Config.Ports.Shooter.LEFT, MotorType.kBrushless);
        rightMotorController = new CANSparkMax(Config.Ports.Shooter.RIGHT, MotorType.kBrushless);
        leftMotorController.restoreFactoryDefaults();
        rightMotorController.restoreFactoryDefaults();
        leftMotorController.setIdleMode(IdleMode.kCoast);
        rightMotorController.setIdleMode(IdleMode.kCoast);
        leftMotorController.setInverted(false);
        rightMotorController.setInverted(true);
        leftMotorController.setOpenLoopRampRate(0);
        rightMotorController.setOpenLoopRampRate(0);
        leftMotorController.setClosedLoopRampRate(0);
        rightMotorController.setClosedLoopRampRate(0);
        leftMotorController.setSmartCurrentLimit(80);
        rightMotorController.setSmartCurrentLimit(80);
        leftMotorController.setSecondaryCurrentLimit(40);
        rightMotorController.setSecondaryCurrentLimit(40);

        leftEncoder = leftMotorController.getEncoder();
        rightEncoder = rightMotorController.getEncoder();
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);

        on = false;
        targetRPM = Config.Settings.REGULAR_SHOOTING_RPM;
    }

    public void run() {
        if (on) {
            int actualRPM = (int) leftEncoder.getVelocity();

            if (actualRPM <= targetRPM) {
                leftMotorController.set(1);
                rightMotorController.set(1);
            } else {
                leftMotorController.set(0);
                rightMotorController.set(0);
            }
        }
    }

    public void setTargetRPM(int targetRPM) {
        this.targetRPM = targetRPM;
    }

    public void toggle() {
        on = !on;
    }

    public void start() {
        on = true;
    }

    public void stop() {
        on = false;
    }

    public void testLeft() {
        leftMotorController.set(0.2);
    }

    public void testRight() {
        rightMotorController.set(0.2);
    }

    public int getTargetRPM() {
        return targetRPM;
    }

    public int getRPM() {
        int leftRPM = (int) leftEncoder.getVelocity();
        int rightRPM = (int) rightEncoder.getVelocity();
        return Math.max(leftRPM, rightRPM);
    }

    public boolean isInStoppedMode() {
        return !on;
    }

    public boolean isInShootingMode() {
        return on;
    }

    public boolean isStopped() {
        return getRPM() <= 0;
    }

    public boolean isUpToSpeed() {
        return getRPM() >= targetRPM - 400;
    }

    public double getLeftVoltage() {
        return leftMotorController.getBusVoltage();
    }

    public double getRightVoltage() {
        return rightMotorController.getBusVoltage();
    }

}
