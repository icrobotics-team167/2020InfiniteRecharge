package frc.robot.routines.auto;

import frc.robot.Config;
import frc.robot.routines.Action;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Subsystems;
import frc.robot.subsystems.Turret;
import frc.robot.util.PeriodicTimer;

public class SickoShoot extends Action {

    private PeriodicTimer timer;
    private double seconds;

    public SickoShoot(double seconds) {
        super();
        timer = new PeriodicTimer();
        this.seconds = seconds;
    }

    @Override
    public void init() {
        timer.reset();
    }

    @Override
    public void periodic() {
        if (!timer.hasElapsed(2)) {
            Subsystems.indexer.setMode(Indexer.Mode.OFF);
        } else {
            Subsystems.indexer.setMode(Indexer.Mode.SICKO_SHOOT);
        }
    }

    @Override
    public boolean isDone() {
        return timer.hasElapsed(seconds + 2);
    }

    @Override
    public void done() {
        Subsystems.intake.setMode(Intake.Mode.FORWARD);
        Subsystems.indexer.setMode(Indexer.Mode.SMART_INTAKE);
    }

}
