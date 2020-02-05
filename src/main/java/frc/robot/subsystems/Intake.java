package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Config;

public class Intake {

    private static Intake instance;
    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    private TalonSRX motor;

    private Intake() {
        motor = new TalonSRX(Config.Ports.INTAKE);
        motor.setNeutralMode(NeutralMode.Brake);
    }

    public void runForward() {
        motor.set(ControlMode.PercentOutput, -0.65);
    }

    public void runReverse() {
        motor.set(ControlMode.PercentOutput, 0.65);
    }

    public void stop() {
        motor.set(ControlMode.PercentOutput, 0);
    }

}
