package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.Config;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Climb {

    private static Climb instance;
    public static Climb getInstance() {
        if (instance == null) {
            instance = new Climb();
        }
        return instance;
    }


    private Servo lower;
    private Servo upper;
    private TalonSRX winch;

    private boolean raised = false;;

    private Climb() {
        lower = new Servo(Config.Ports.Climb.LOWER);
        upper = new Servo(Config.Ports.Climb.UPPER);

        winch = new TalonSRX(Config.Ports.Climb.WINCH);
    }

    public void raise() {
        lower.setAngle(55); // 90 deg
        upper.setAngle(120); // 180 deg

        raised = true;
    }

    public void lower() {
        lower.setAngle(0);
        upper.setAngle(0);
    }

    public void pull() {
        winch.set(ControlMode.PercentOutput, 0.5);
    }

    public void down() {
        winch.set(ControlMode.PercentOutput, -0.5);
    }

    public void stop() {
        winch.set(ControlMode.PercentOutput, 0);
    }

    public boolean isRaised() {
        return raised;
    }

    public void reset() {
        lower();
        raised = false;
    }
}
