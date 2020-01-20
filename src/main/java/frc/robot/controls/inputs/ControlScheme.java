package frc.robot.controls.inputs;

public interface ControlScheme {

    double getTankLeftSpeed();
    double getTankRightSpeed();

    boolean doRunIntakeForward();
    boolean doRumIntakeReverse();

    boolean doToggleTurretAutoAlign();
    boolean doTurnTurretClockwise();
    boolean doTurnTurretCounterclockwise();

//    double getTankLeftSpeed();
//    double getTankRightSpeed();
//
//    double getSwerveHorizontalSpeed();
//    double getSwerveVerticalSpeed();
//    double getSwerveAngularSpeed();
//
//    double getIntakeSpeed();
//
//    boolean trackTarget();
//    double getClockwiseTurretSpeed();
//    double getCounterClockwiseTurretSpeed();
//    // boolean toggleLimelightMode();

}
