package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;

public class SingleIndexerTurn extends Action {

    private PeriodicTimer timer;

    public SingleIndexerTurn() {
        timer = new PeriodicTimer();
    }

    @Override
    public void init() {
        timer.reset();
        Subsystems.indexer.setMode(Indexer.Mode.SINGLE_TURN);
    }

    @Override
    public void periodic() {
        
    }

    @Override
    public boolean isDone() {
        return Subsystems.indexer.hasCompletedSingleTurn();
    }

    @Override
    public void done() {
        Subsystems.indexer.setMode(Indexer.Mode.OFF);
    }

}