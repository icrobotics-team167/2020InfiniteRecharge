package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.Config;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climber {

    private static Climber instance;
    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }
        return instance;
    }

    private CANSparkMax extensionMotor;
    private TalonSRX winchMotor;

    private Climber() {
        extensionMotor = new CANSparkMax(Config.Ports.Climb.EXTENSION, MotorType.kBrushless);
        extensionMotor.restoreFactoryDefaults();
        extensionMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        extensionMotor.setInverted(false);
        extensionMotor.setOpenLoopRampRate(0);
        extensionMotor.setClosedLoopRampRate(0);
        extensionMotor.setSmartCurrentLimit(80);
        extensionMotor.setSecondaryCurrentLimit(40);
        
        winchMotor = new TalonSRX(Config.Ports.Climb.WINCH);
    }

    public void raiseExtension() {
        extensionMotor.set(0.5);
    }

    public void lowerExtension() {
        extensionMotor.set(-0.5);
    }

    public void stopExtension() {
        extensionMotor.set(0);
    }

    public void climbUp() {
        winchMotor.set(ControlMode.PercentOutput, 0.3);
    }

    public void stopClimb() {
        winchMotor.set(ControlMode.PercentOutput, 0);
    }

}
