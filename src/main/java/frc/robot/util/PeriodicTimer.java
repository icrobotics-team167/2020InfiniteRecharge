package frc.robot.util;

import edu.wpi.first.wpilibj.Timer;

public class PeriodicTimer {

    private double startTime;
    private boolean hasElapsedOnceFlag = false;

    public PeriodicTimer() {
        reset();
    }

    public void reset() {
        startTime = Timer.getFPGATimestamp();
        hasElapsedOnceFlag = false;
    }

    public boolean hasElapsed(double seconds) {
        return Timer.getFPGATimestamp() - startTime >= seconds;
    }

    public boolean hasElapsedOnce(double seconds) {
        if (!hasElapsedOnceFlag && hasElapsed(seconds)) {
            hasElapsedOnceFlag = true;
            return true;
        }
        return false;
    }

    public double get() {
        return Timer.getFPGATimestamp();
    }
}
