package frc.robot.routines;

import frc.robot.routines.auto.AutoState;

public abstract class Action {

    public abstract void init();
    public abstract void periodic();
    public abstract boolean isDone();
    public abstract void done();

    public void exec() {
        if (getState() == AutoState.READY) {
            init();
            setState(AutoState.PERIODIC);
        } else if (getState() == AutoState.FAILED) {
            setState(AutoState.DONE);
        } else if (getState() == AutoState.PERIODIC) {
            periodic();
        } else if (getState() == AutoState.DONE) {
            return;
        }

        if (isDone()) {
            setState(AutoState.DONE);
        }
    }

    private AutoState state;

    public AutoState getState() {
        return state;
    }

    public void setState(AutoState state) {
        this.state = state;
    }
}
