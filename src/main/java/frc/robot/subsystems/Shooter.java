package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
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
    private CANPIDController leftPID;
    private CANPIDController rightPID;

    private Shooter() {
        leftMotorController = new CANSparkMax(Config.Ports.Shooter.LEFT, MotorType.kBrushless);
        rightMotorController = new CANSparkMax(Config.Ports.Shooter.RIGHT, MotorType.kBrushless);
        leftMotorController.restoreFactoryDefaults();
        rightMotorController.restoreFactoryDefaults();
        leftMotorController.setIdleMode(IdleMode.kCoast);
        rightMotorController.setIdleMode(IdleMode.kCoast);

        leftEncoder = leftMotorController.getEncoder(EncoderType.kHallSensor, 4096);
        rightEncoder = rightMotorController.getEncoder(EncoderType.kHallSensor, 4096);
        leftEncoder.setInverted(false);
        rightEncoder.setInverted(false);

        leftPID = leftMotorController.getPIDController();
        rightPID = rightMotorController.getPIDController();

        leftPID.setP(0.00005);
        leftPID.setI(0.000001);
        leftPID.setD(0);
        leftPID.setIZone(0);
        leftPID.setFF(0);
        leftPID.setOutputRange(-1, 1);

        rightPID.setP(0.00005);
        rightPID.setI(0.000001);
        rightPID.setD(0);
        rightPID.setIZone(0);
        rightPID.setFF(0);
        rightPID.setOutputRange(-1, 1);
    }

    public void bangBangDrive(int targetRPM) {
        double actualRPM = (leftEncoder.getVelocity() + rightEncoder.getVelocity()) / 2;
        SmartDashboard.putNumber("Shooter RPM", actualRPM);

        if (actualRPM > targetRPM) {
            leftMotorController.set(0);
            rightMotorController.set(0);
        } else {
            leftMotorController.set(-1);
            rightMotorController.set(1);
        }
    }

    public void pidDrive(int targetRPM) {
        leftPID.setReference(targetRPM, ControlType.kVelocity);
        rightPID.setReference(targetRPM, ControlType.kVelocity);
    }

    public void testMotors() {
        leftMotorController.set(-0.2);
        rightMotorController.set(0.2);
        printEncoderValues();
    }

    public void printEncoderValues() {
        SmartDashboard.putNumber("Left Shooter Encoder", leftEncoder.getVelocity());
        SmartDashboard.putNumber("Right Shooter Encoder", rightEncoder.getVelocity());
    }

}
