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
    void resetEncoders();
    double getLeftEncoderPosition();
    double getRightEncoderPosition();
    void setReferences(double leftSpeed, double rightSpeed);
    Rotation2d getGyroHeading();

}
