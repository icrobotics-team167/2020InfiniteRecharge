package frc.robot.subsystems.drive;

import edu.wpi.first.wpilibj.geometry.Rotation2d;

public interface TankDriveBase {

    // Teleop drive
    void tankDrive(double leftSpeed, double rightSpeed);

    // Gearing
    void toggleGearing();
    void setHighGear();
    void setLowGear();
    boolean isHighGear();
    boolean isLowGear();

    // Auto
    void straightDrive(double speed);
    void pointTurn(double speed);
    double getAngle();
    double getLeftEncoderPosition();
    double getRightEncoderPosition();
    void resetEncoders();
    void setReferences(double leftSpeed, double rightSpeed);
    Rotation2d getGyroHeading();

    // Utility
    double metersPerSecondToRPM(double metersPerSecond);

}
