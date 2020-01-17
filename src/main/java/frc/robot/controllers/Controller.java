package frc.robot.controllers;

public interface Controller {

    double getTankLeftSpeed();
    double getTankRightSpeed();

    double getSwerveHorizontalSpeed();
    double getSwerveVerticalSpeed();
    double getSwerveAngularSpeed();

    double getIntakeSpeed();

}
