package frc.robot.routines;

import frc.robot.controls.inputs.ControlScheme;
import frc.robot.subsystems.Subsystems;

public class Teleop {

    private ControlScheme controls;

    public Teleop(ControlScheme controls) {
        this.controls = controls;
    }

    public void init() {
    }

    public void periodic() {
        Subsystems.driveBase.tankDrive(controls.getTankLeftSpeed(), controls.getTankRightSpeed());
    }

}
