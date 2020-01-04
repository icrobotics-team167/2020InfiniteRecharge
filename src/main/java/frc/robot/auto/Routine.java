package frc.robot.auto;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Routine extends Action {

    private Queue<Action> actions;
    private Action currentAction;

    public Routine(Action... actions) {
        this.actions = new LinkedList<>(Arrays.asList(actions));
    }

    @Override
    public void init() {
        currentAction = actions.poll();
    }

    @Override
    public void periodic() {
        if (currentAction != null) {
            currentAction.exec();
            if (currentAction.isDone()) {
                currentAction = actions.poll();
            }
        }
    }

    @Override
    public boolean isDone() {
        return currentAction == null;
    }

    @Override
    public void done() {
        System.out.println("Finished auto routine");
    }

}
