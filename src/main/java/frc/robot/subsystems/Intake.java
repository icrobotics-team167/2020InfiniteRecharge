package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Config;

public class Intake {

    private static Intake instance;
    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    private DoubleSolenoid doubleSolenoid;
    private boolean extended;
    private TalonSRX motor;
    private double currentSpeed;

    private Intake() {
        doubleSolenoid = new DoubleSolenoid(
            Config.Settings.SPARK_TANK_ENABLED ? Config.Ports.SparkTank.PCM : Config.Ports.TalonTank.PCM,
            Config.Ports.Intake.SOLENOID_FORWARD,
            Config.Ports.Intake.SOLENOID_REVERSE
        );
        extended = false;
        motor = new TalonSRX(Config.Ports.Intake.MOTOR);
        motor.setNeutralMode(NeutralMode.Brake);
        currentSpeed = 0;
    }

    public void runForward() {
        motor.set(ControlMode.PercentOutput, -0.65);
        currentSpeed = -0.65;
    }

    public void runReverse() {
        motor.set(ControlMode.PercentOutput, 0.65);
        currentSpeed = 0.65;
    }

    public void stop() {
        motor.set(ControlMode.PercentOutput, 0);
        currentSpeed = 0;
    }

    public void toggleExtension() {
        if (extended) {
            retract();
        } else {
            extend();
        }
    }

    public void retract() {
        doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
        extended = false;
    }

    public void extend() {
        doubleSolenoid.set(DoubleSolenoid.Value.kForward);
        extended = true;
    }

    public boolean isStopped() {
        return currentSpeed == 0;
    }

    public boolean isRunningForward() {
        return currentSpeed == -0.65;
    }

    public boolean isRunningReverse() {
        return currentSpeed == 0.65;
    }

    public boolean isRetracted() {
        return !extended;
    }

    public boolean isExtended() {
        return extended;
    }

}
