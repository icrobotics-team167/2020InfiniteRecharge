package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.util.PeriodicTimer;

public class Wait extends Action {

    private PeriodicTimer timer;
    private double milliseconds;

    public Wait(int milliseconds) {
        timer = new PeriodicTimer();
        this.milliseconds = milliseconds;
    }

    @Override
    public void init() {
        timer.reset();
    }

    @Override
    public void periodic() {}

    @Override
    public boolean isDone() {
        return timer.hasElapsed(milliseconds / 1000);
    }

    @Override
    public void done() {}

}
