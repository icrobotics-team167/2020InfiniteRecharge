package frc.robot.auto;

public abstract class Action {

    private boolean init = false;
    private boolean finished = false;

    public abstract void init();
    public abstract void periodic();
    public abstract boolean isDone();
    public abstract void done();

    public void exec() {
        if (!init) {
            init();
            init = true;
            return;
        }
        if (isDone() && !finished) {
            done();
            finished = true;
            return;
        }
        periodic();
    }

}
