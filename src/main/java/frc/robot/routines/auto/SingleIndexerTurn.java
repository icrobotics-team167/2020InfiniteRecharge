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
        Subsystems.indexer.setManualTurnSpeed(0.5);
        Subsystems.indexer.setManualLiftSpeed(0);
        Subsystems.indexer.forwardAntiJamServo();
        Subsystems.indexer.setMode(Indexer.Mode.MANUAL);
    }

    @Override
    public void periodic() {
        
    }

    @Override
    public boolean isDone() {
        return timer.hasElapsed(0.5);
    }

    @Override
    public void done() {
        Subsystems.indexer.setManualTurnSpeed(0);
        Subsystems.indexer.forwardAntiJamServo();
        Subsystems.indexer.setMode(Indexer.Mode.OFF);
    }

}