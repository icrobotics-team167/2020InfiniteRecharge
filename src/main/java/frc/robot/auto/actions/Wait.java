package frc.robot.auto.actions;

import frc.robot.auto.Action;

public class Wait extends Action {

    private int milliseconds;
    private long startTime;

    public Wait(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    @Override
    public void init() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void periodic() {}

    @Override
    public boolean isDone() {
        return startTime + milliseconds >= System.currentTimeMillis();
    }

    @Override
    public void done() {}

}
