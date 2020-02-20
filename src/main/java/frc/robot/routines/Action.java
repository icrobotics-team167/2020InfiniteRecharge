package frc.robot.routines;

import frc.robot.routines.auto.AutoState;

public abstract class Action {

    public abstract void init();
    public abstract void periodic();
    public abstract boolean isDone();
    public abstract void done();

    public void exec() {
        System.out.println("0");
        if (getState() == AutoState.READY) {
            System.out.println("1");
            setState(AutoState.PERIODIC);
            init();
        } else if (getState() == AutoState.FAILED) {
            System.out.println("2");
            setState(AutoState.DONE);
        } else if (getState() == AutoState.PERIODIC) {
            System.out.println("3");
            periodic();
        } else if (getState() == AutoState.DONE) {
            System.out.println("4");
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
