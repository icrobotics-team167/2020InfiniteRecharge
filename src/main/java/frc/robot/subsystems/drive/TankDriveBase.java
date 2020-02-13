package frc.robot.subsystems.drive;

public interface TankDriveBase {

    void tankDrive(double leftSpeed, double rightSpeed);
    void toggleGearing();
    void setHighGear();
    void setLowGear();
    boolean isHighGear();
    boolean isLowGear();

}
