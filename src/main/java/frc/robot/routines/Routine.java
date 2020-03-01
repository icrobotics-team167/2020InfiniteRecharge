package frc.robot.routines;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import frc.robot.routines.auto.Auto;
import frc.robot.routines.auto.AutoState;

public class Routine extends Action {

    private Queue<Action> actions;
    private Action currentAction;

    public Routine(Action... actions) {
        this.actions = new LinkedList<>(Arrays.asList(actions));
        setState(AutoState.READY);
    }

    @Override
    public void init() {
        currentAction = actions.poll();
    }

    @Override
    public void periodic() {
        if (currentAction != null) {
            if (currentAction instanceof Auto) {
                System.out.println("auto");
            }
            currentAction.exec();
            if (currentAction.getState() == AutoState.EXIT) {
                state = AutoState.EXIT;
            }
            if (currentAction.getState() == AutoState.FINISHED) {
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
    }

}
