package frc.robot.subsystems.drive;

import edu.wpi.first.wpilibj.geometry.Rotation2d;

public interface TankDriveBase {

    void tankDrive(double leftSpeed, double rightSpeed);
    void toggleGearing();
    void setHighGear();
    void setLowGear();
    boolean isHighGear();
    boolean isLowGear();
    double getLeftSpeed();
    double getRightSpeed();
    void setReferences(double leftSpeed, double rightSpeed);
    Rotation2d getGyroHeading();
}
