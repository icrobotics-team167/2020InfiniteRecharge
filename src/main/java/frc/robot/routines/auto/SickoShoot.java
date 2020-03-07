package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;

public class SickoShoot extends Action {

    private PeriodicTimer timer;
    private double seconds;
    private double delay;

    public SickoShoot(double seconds) {
        this(seconds, 2);
    }

    public SickoShoot(double seconds, double delay) {
        super();
        timer = new PeriodicTimer();
        this.seconds = seconds;
        this.delay = delay;
    }

    @Override
    public void init() {
        timer.reset();
    }

    @Override
    public void periodic() {
        if (!timer.hasElapsed(delay)) {
            Subsystems.indexer.setMode(Indexer.Mode.OFF);
        } else {
            Subsystems.indexer.setMode(Indexer.Mode.SICKO_SHOOT);
        }
    }

    @Override
    public boolean isDone() {
        return timer.hasElapsed(seconds + delay);
    }

    @Override
    public void done() {
        Subsystems.intake.setMode(Intake.Mode.FORWARD);
        Subsystems.indexer.setMode(Indexer.Mode.SMART_INTAKE);
    }

}
