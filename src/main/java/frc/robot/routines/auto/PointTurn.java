package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;

public class PointTurn extends Action {

    private double degreesClockwise;
    private double speed;
    private double timeoutSeconds;
    private double initialAngle;
    private PeriodicTimer timer;

    public PointTurn(double degreesClockwise, double speed) {
        this(degreesClockwise, speed, -1);
    }

    public PointTurn(double degreesClockwise, double speed, double timeoutSeconds) {
        super();
        this.degreesClockwise = degreesClockwise;
        this.speed = speed;
        this.timeoutSeconds = timeoutSeconds;
        initialAngle = 0;
        timer = new PeriodicTimer();
    }

    @Override
    public void init() {
        initialAngle = Subsystems.driveBase.getAngle();
        timer.reset();
    }

    @Override
    public void periodic() {
        if (timeoutSeconds >= 0 && timer.hasElapsed(timeoutSeconds)) {
            state = AutoState.EXIT;
        }
        Subsystems.driveBase.pointTurn(speed);
    }

    @Override
    public boolean isDone() {
        if (speed > 0) {
            return Subsystems.driveBase.getAngle() - initialAngle >= degreesClockwise;
        } else if (speed < 0) {
            return Subsystems.driveBase.getAngle() - initialAngle <= -degreesClockwise;
        } else {
            return true;
        }
    }

    @Override
    public void done() {
        Subsystems.driveBase.stop();
    }

}
