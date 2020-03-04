package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Subsystems;
import frc.robot.subsystems.Turret;
import frc.robot.util.PeriodicTimer;

public class SecondSickoShoot extends Action {

    private PeriodicTimer intakeTimer;
    private PeriodicTimer shooterTimer;
    private double seconds;
    private boolean gapAligned;

    public SecondSickoShoot(double seconds) {
        super();
        intakeTimer = new PeriodicTimer();
        shooterTimer = new PeriodicTimer();
        this.seconds = seconds;
        gapAligned = false;
    }

    @Override
    public void init() {
        intakeTimer.reset();
        shooterTimer.reset();
    }

    @Override
    public void periodic() {
        if (!intakeTimer.hasElapsed(4)) {
            Subsystems.indexer.setMode(Indexer.Mode.SMART_INTAKE);
            shooterTimer.reset();
        } else if (!Subsystems.indexer.isGapAligned() && !gapAligned) {
            Subsystems.indexer.setMode(Indexer.Mode.GAP_ALIGNMENT);
            gapAligned = true;
            shooterTimer.reset();
        } else {
            Subsystems.indexer.setMode(Indexer.Mode.SICKO_SHOOT);
        }
    }

    @Override
    public boolean isDone() {
        return shooterTimer.hasElapsed(seconds);
    }

    @Override
    public void done() {
        Subsystems.intake.setMode(Intake.Mode.OFF);
        Subsystems.indexer.setMode(Indexer.Mode.OFF);
        Subsystems.shooter.stop();
        Subsystems.turret.setMode(Turret.Mode.OFF);
    }

}
