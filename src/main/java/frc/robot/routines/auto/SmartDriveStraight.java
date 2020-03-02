package frc.robot.routines.auto;

import edu.wpi.first.wpilibj.util.Units;
import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;

public class SmartDriveStraight extends Action {

    private double meters;
    private double speed;
    private double timeoutSeconds;
    private double leftEncoderInitialPosition;
    private double rightEncoderInitialPosition;
    private double minSpeed;
    private double maxSpeed;
    private double speedRange;
    private double accelerationMeters;
    private PeriodicTimer timer;

    public SmartDriveStraight(double inches, double speed) {
        this(inches, speed, -1);
    }

    public SmartDriveStraight(double inches, double speed, double timeoutSeconds) {
        super();
        meters = Units.inchesToMeters(inches);
        this.speed = speed;
        this.timeoutSeconds = timeoutSeconds;
        leftEncoderInitialPosition = 0;
        rightEncoderInitialPosition = 0;

        speed = Math.abs(speed);

        minSpeed = 0.3 * speed;
        if (minSpeed < 0.1) {
            minSpeed = 0.1;
        }
        if (minSpeed > speed) {
            minSpeed = speedRange;
        }

        accelerationMeters = Math.abs(Units.feetToMeters(2 * speed));
        if (2 * accelerationMeters > meters) {
            accelerationMeters = meters / 2;
        }

        double maxAcceleration = Math.min((speed - minSpeed) / accelerationMeters, 0.35);
        maxSpeed = minSpeed + accelerationMeters * maxAcceleration;

        speedRange = maxSpeed - minSpeed;

        timer = new PeriodicTimer();
    }

    @Override
    public void init() {
        leftEncoderInitialPosition = Subsystems.driveBase.getLeftEncoderPosition();
        rightEncoderInitialPosition = Subsystems.driveBase.getRightEncoderPosition();
        timer.reset();
    }

    @Override
    public void periodic() {
        if (timeoutSeconds >= 0 && timer.hasElapsed(timeoutSeconds)) {
            state = AutoState.EXIT;
        }
        double leftEncoderPosition = Subsystems.driveBase.getLeftEncoderPosition();
        double rightEncoderPosition = Subsystems.driveBase.getRightEncoderPosition();
        double metersTraveled = Math.max(leftEncoderPosition, rightEncoderPosition);
        if (speed > 0) {
            if (metersTraveled > meters - accelerationMeters) {
                Subsystems.driveBase.straightDrive(minSpeed + (((meters - metersTraveled) / accelerationMeters)) * speedRange);
            } else if (metersTraveled < accelerationMeters) {
                Subsystems.driveBase.straightDrive(minSpeed + (metersTraveled / accelerationMeters) * speedRange);
            } else {
                Subsystems.driveBase.straightDrive(maxSpeed);
            }
        } else if (speed < 0) {
            if (Math.abs(metersTraveled) > meters - accelerationMeters) {
                Subsystems.driveBase.straightDrive(minSpeed + (((meters - metersTraveled) / accelerationMeters)) * speedRange);
            } else if (Math.abs(metersTraveled) < accelerationMeters) {
                Subsystems.driveBase.straightDrive(minSpeed + (metersTraveled / accelerationMeters) * speedRange);
            } else {
                Subsystems.driveBase.straightDrive(maxSpeed);
            }
        } else {
            Subsystems.driveBase.straightDrive(0);
        }
    }

    @Override
    public boolean isDone() {
        double leftEncoderPosition = Subsystems.driveBase.getLeftEncoderPosition();
        double rightEncoderPosition = Subsystems.driveBase.getRightEncoderPosition();
        if (speed > 0) {
            return leftEncoderPosition - leftEncoderInitialPosition >= meters || rightEncoderPosition - rightEncoderInitialPosition >= meters;
        } else if (speed < 0) {
            return leftEncoderPosition - leftEncoderInitialPosition <= -meters || rightEncoderPosition - rightEncoderInitialPosition <= -meters;
        } else {
            return true;
        }
    }

    @Override
    public void done() {
        Subsystems.driveBase.stop();
    }

}
