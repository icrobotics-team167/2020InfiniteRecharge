package frc.robot.routines;

import frc.robot.routines.auto.AutoState;

public abstract class Action {

    public abstract void init();
    public abstract void periodic();
    public abstract boolean isDone();
    public abstract void done();

    public void exec() {
        if (getState() == AutoState.READY) {
            setState(AutoState.PERIODIC);
            init();
        } else if (getState() == AutoState.FAILED) {
            setState(AutoState.DONE);
        } else if (getState() == AutoState.PERIODIC) {
            periodic();
        } else if (getState() == AutoState.CLEANUP) {
            done();
            setState(AutoState.DONE);
            return;
        } else {
            return;
        }

        if (isDone()) {
            setState(AutoState.CLEANUP);
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
