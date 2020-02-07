package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config;

public class Shooter {

    private static Shooter instance;
    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

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
        leftMotorController.setOpenLoopRampRate(Config.Settings.CPU_PERIOD);
        rightMotorController.setOpenLoopRampRate(Config.Settings.CPU_PERIOD);
        leftMotorController.setSmartCurrentLimit(150);
        rightMotorController.setSmartCurrentLimit(150);
        leftMotorController.setSecondaryCurrentLimit(150);
        rightMotorController.setSecondaryCurrentLimit(150); 
        

        leftEncoder = leftMotorController.getEncoder();
        rightEncoder = rightMotorController.getEncoder();
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }

    public void drive(int targetRPM) {
        int actualRPM = (int) leftEncoder.getVelocity();

        if (actualRPM <= targetRPM) {
            leftMotorController.set(0.75);
            rightMotorController.set(0.75);
        } else {
            leftMotorController.set(0);
            rightMotorController.set(0);
        }
    }

    boolean fullSpeed = true;
    public void test(int targetRPM) {
        int actualRPM = (int) leftEncoder.getVelocity();
        System.out.println("Actual RPM: " + actualRPM);
        System.out.println("Target RPM: " + targetRPM);
        SmartDashboard.putNumber("Shooter RPM", actualRPM);

        if (!fullSpeed && actualRPM > targetRPM) {
            fullSpeed = true;
            leftMotorController.set(0);
            rightMotorController.set(0);
        } else if (fullSpeed && actualRPM <= 0) {
            fullSpeed = false;
            leftMotorController.set(0.8);
            rightMotorController.set(0.8);
        } else if (!fullSpeed && actualRPM <= targetRPM) {
            leftMotorController.set(0.8);
            rightMotorController.set(0.8);
        } else if (fullSpeed && actualRPM > targetRPM) {
            leftMotorController.set(0);
            rightMotorController.set(0);
        }
    }

    public void stop() {
        // Lower than 0 to prevent misreading RPM from running the motors at full power
        // e.g. if the actual RPM is misread at -5, so it won't run at full power
        // drive(0);
        leftMotorController.set(0);
        rightMotorController.set(0);
    }

    public void testLeft() {
        leftMotorController.set(0.2);
    }

    public void testRight() {
        rightMotorController.set(0.2);
    }

    public void printEncoderValues() {
        SmartDashboard.putNumber("Shooter RPM", (int) leftEncoder.getVelocity());
        SmartDashboard.putNumber("Left Shooter Amps", leftMotorController.getOutputCurrent());
        SmartDashboard.putNumber("Right Shooter Amps", rightMotorController.getOutputCurrent());
    }

    public void logData() {
        SmartDashboard.putNumber("Left Applied Output", leftMotorController.getAppliedOutput());
        SmartDashboard.putNumber("Right Applied Output", rightMotorController.getAppliedOutput());
        SmartDashboard.putNumber("Left Faults", leftMotorController.getFaults());
        SmartDashboard.putNumber("Right Faults", rightMotorController.getFaults());
        SmartDashboard.putNumber("Left Sticky Faults", leftMotorController.getStickyFaults());
        SmartDashboard.putNumber("Right Sticky Faults", rightMotorController.getStickyFaults());
        SmartDashboard.putBoolean("Left Is Follower", leftMotorController.isFollower());
        SmartDashboard.putBoolean("Right Is Follower", rightMotorController.isFollower());
        SmartDashboard.putNumber("Left Motor Velocity", leftEncoder.getVelocity());
        SmartDashboard.putNumber("Right Motor Velocity", rightEncoder.getVelocity());
        SmartDashboard.putNumber("Left Motor Temperature", leftMotorController.getMotorTemperature());
        SmartDashboard.putNumber("Right Motor Temperature", rightMotorController.getMotorTemperature());
        SmartDashboard.putNumber("Left Motor Voltage", leftMotorController.getBusVoltage());
        SmartDashboard.putNumber("Right Motor Voltage", rightMotorController.getBusVoltage());
        SmartDashboard.putNumber("Left Motor Current", leftMotorController.getOutputCurrent());
        SmartDashboard.putNumber("Right Motor Current", rightMotorController.getOutputCurrent());
        SmartDashboard.putNumber("Left Motor Position", leftEncoder.getPosition());
        SmartDashboard.putNumber("Right Motor Position", rightEncoder.getPosition());
    }

}
