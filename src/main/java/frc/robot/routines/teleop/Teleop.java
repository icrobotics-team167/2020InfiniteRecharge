package frc.robot.routines.teleop;

import frc.robot.controllers.Controller;
import frc.robot.routines.Action;

public class Teleop extends Action {

    protected Controller controller;

    protected Teleop(Controller controller) {
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
