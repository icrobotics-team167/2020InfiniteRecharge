package frc.robot.routines.teleop;

import frc.robot.controls.inputs.ControlScheme;
import frc.robot.routines.Action;

public class Teleop extends Action {

    protected ControlScheme controller;

    protected Teleop(ControlScheme controller) {
        this.controller = controller;
    }

    @Override
    public void init() {

    }

    @Override
    public void periodic() {

    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void done() {

    }

}
